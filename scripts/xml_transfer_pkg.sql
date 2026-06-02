CREATE OR REPLACE PACKAGE NBPT2.XML_TRANSFER_PKG AS
    PROCEDURE EXPORT_XML(
        p_mode   IN  VARCHAR2,
        p_target IN  VARCHAR2,
        p_xml    OUT CLOB
    );

    PROCEDURE IMPORT_XML(
        p_mode   IN VARCHAR2,
        p_target IN VARCHAR2,
        p_xml    IN CLOB
    );
END XML_TRANSFER_PKG;
/
CREATE OR REPLACE PACKAGE BODY NBPT2.XML_TRANSFER_PKG AS

    TYPE t_table_list IS TABLE OF VARCHAR2(128);

    --------------------------------------------------------------------
    -- VALIDACIJA
    --------------------------------------------------------------------
    FUNCTION normalize_name(p_value IN VARCHAR2) RETURN VARCHAR2 IS
    BEGIN
        RETURN UPPER(TRIM(p_value));
    END;

    FUNCTION is_allowed_table(p_table IN VARCHAR2) RETURN BOOLEAN IS
        v_table VARCHAR2(128) := normalize_name(p_table);
    BEGIN
        RETURN v_table IN (
            'MEDIA',
            'STAT_BOARDS',
            'STATS',
            'PEOPLE',
            'COMMITTEES',
            'COMMITTEE_MEMBERS',
            'ORGANIZATIONS',
            'GALLERY_IMAGES',
            'PACKAGE_TIERS',
            'PARTICIPATIONS',
            'ARTICLES',
            'ARTICLE_IMAGES',
            'JOBS',
            'JOB_TAGS',
            'MEDIA_OUTLETS',
            'MEDIA_PARTICIPATIONS'
        );
    END;

    PROCEDURE validate_table(p_table IN VARCHAR2) IS
    BEGIN
        IF p_table IS NULL OR NOT is_allowed_table(p_table) THEN
            RAISE_APPLICATION_ERROR(-20010, 'Invalid or unsupported table: ' || p_table);
        END IF;
    END;

    FUNCTION get_module_tables(p_module IN VARCHAR2) RETURN t_table_list IS
        v_module VARCHAR2(128) := normalize_name(p_module);
    BEGIN
        IF v_module = 'JOBS' THEN
            RETURN t_table_list(
                'MEDIA',
                'ORGANIZATIONS',
                'JOBS',
                'JOB_TAGS'
            );

        ELSIF v_module = 'CONTENT' THEN
            RETURN t_table_list(
                'MEDIA',
                'ARTICLES',
                'ARTICLE_IMAGES'
            );

        ELSIF v_module = 'ORGANIZATIONS' THEN
            RETURN t_table_list(
                'MEDIA',
                'ORGANIZATIONS',
                'GALLERY_IMAGES',
                'PACKAGE_TIERS',
                'PARTICIPATIONS'
            );

        ELSIF v_module = 'PEOPLE' THEN
            RETURN t_table_list(
                'MEDIA',
                'PEOPLE',
                'COMMITTEES',
                'COMMITTEE_MEMBERS'
            );

        ELSIF v_module = 'MEDIA' THEN
            RETURN t_table_list(
                'MEDIA',
                'MEDIA_OUTLETS',
                'MEDIA_PARTICIPATIONS'
            );

        ELSE
            RAISE_APPLICATION_ERROR(-20011, 'Invalid module: ' || p_module);
        END IF;
    END;

    FUNCTION get_all_tables RETURN t_table_list IS
    BEGIN
        RETURN t_table_list(
            'MEDIA',
            'STAT_BOARDS',
            'STATS',
            'PEOPLE',
            'COMMITTEES',
            'COMMITTEE_MEMBERS',
            'ORGANIZATIONS',
            'GALLERY_IMAGES',
            'PACKAGE_TIERS',
            'PARTICIPATIONS',
            'ARTICLES',
            'ARTICLE_IMAGES',
            'JOBS',
            'JOB_TAGS',
            'MEDIA_OUTLETS',
            'MEDIA_PARTICIPATIONS'
        );
    END;

    --------------------------------------------------------------------
    -- XML HELPERS
    --------------------------------------------------------------------
    PROCEDURE append_clob(
        p_clob IN OUT NOCOPY CLOB,
        p_text IN VARCHAR2
    ) IS
    BEGIN
        DBMS_LOB.WRITEAPPEND(p_clob, LENGTH(p_text), p_text);
    END;

    PROCEDURE append_clob_clob(
        p_clob  IN OUT NOCOPY CLOB,
        p_extra IN CLOB
    ) IS
        v_pos    INTEGER := 1;
        v_amount INTEGER := 16000;
        v_len    INTEGER;
        v_piece  VARCHAR2(32767);
    BEGIN
        IF p_extra IS NULL THEN
            RETURN;
        END IF;

        v_len := DBMS_LOB.GETLENGTH(p_extra);

        WHILE v_pos <= v_len LOOP
            v_piece := DBMS_LOB.SUBSTR(p_extra, v_amount, v_pos);

            IF v_piece IS NOT NULL THEN
                DBMS_LOB.WRITEAPPEND(
                    p_clob,
                    LENGTH(v_piece),
                    v_piece
                );
            END IF;

            v_pos := v_pos + v_amount;
        END LOOP;
    END;

    FUNCTION escape_xml(p_value IN VARCHAR2) RETURN VARCHAR2 IS
    BEGIN
        RETURN DBMS_XMLGEN.CONVERT(p_value, DBMS_XMLGEN.ENTITY_ENCODE);
    END;

    FUNCTION escape_sql(p_value IN VARCHAR2) RETURN VARCHAR2 IS
    BEGIN
        RETURN REPLACE(p_value, '''', '''''');
    END;

    --------------------------------------------------------------------
    -- EXPORT TABLE
    --------------------------------------------------------------------
    PROCEDURE EXPORT_TABLE(
        p_table IN VARCHAR2,
        p_xml   OUT CLOB
    ) IS
        v_ctx        DBMS_XMLGEN.ctxHandle;
        v_table      VARCHAR2(128) := normalize_name(p_table);
        v_table_xml  CLOB;
    BEGIN
        validate_table(v_table);

        DBMS_LOB.CREATETEMPORARY(p_xml, TRUE);

        append_clob(p_xml, '<Table name="' || v_table || '">');

        v_ctx := DBMS_XMLGEN.NEWCONTEXT(
            'SELECT * FROM NBPT2.' || v_table
        );

        DBMS_XMLGEN.SETROWSETTAG(v_ctx, 'Rows');
        DBMS_XMLGEN.SETROWTAG(v_ctx, 'Row');

        v_table_xml := DBMS_XMLGEN.GETXML(v_ctx);
        DBMS_XMLGEN.CLOSECONTEXT(v_ctx);

        v_table_xml := REPLACE(v_table_xml, '<?xml version="1.0"?>', '');
        v_table_xml := REPLACE(v_table_xml, '<?xml version="1.0" encoding="UTF-8"?>', '');

        append_clob_clob(p_xml, v_table_xml);
        append_clob(p_xml, '</Table>');
    END EXPORT_TABLE;

    --------------------------------------------------------------------
    -- EXPORT MODULE
    --------------------------------------------------------------------
    PROCEDURE EXPORT_MODULE(
        p_module IN VARCHAR2,
        p_xml    OUT CLOB
    ) IS
        v_tables t_table_list;
        v_part   CLOB;
    BEGIN
        v_tables := get_module_tables(p_module);

        DBMS_LOB.CREATETEMPORARY(p_xml, TRUE);

        append_clob(p_xml, '<Module name="' || normalize_name(p_module) || '">');

        FOR i IN 1 .. v_tables.COUNT LOOP
            EXPORT_TABLE(v_tables(i), v_part);
            append_clob_clob(p_xml, v_part);
        END LOOP;

        append_clob(p_xml, '</Module>');
    END EXPORT_MODULE;

    --------------------------------------------------------------------
    -- EXPORT ALL
    --------------------------------------------------------------------
    PROCEDURE EXPORT_ALL(
        p_xml OUT CLOB
    ) IS
        v_tables t_table_list;
        v_part   CLOB;
    BEGIN
        v_tables := get_all_tables;

        DBMS_LOB.CREATETEMPORARY(p_xml, TRUE);

        append_clob(p_xml, '<AllData>');

        FOR i IN 1 .. v_tables.COUNT LOOP
            EXPORT_TABLE(v_tables(i), v_part);
            append_clob_clob(p_xml, v_part);
        END LOOP;

        append_clob(p_xml, '</AllData>');
    END EXPORT_ALL;

    --------------------------------------------------------------------
    -- INSERT JEDNOG REDA IZ XML-A
    --------------------------------------------------------------------
    PROCEDURE insert_row_from_xml(
        p_table IN VARCHAR2,
        p_row   IN XMLTYPE
    ) IS
        v_table      VARCHAR2(128) := normalize_name(p_table);
        v_cols       CLOB;
        v_vals       CLOB;
        v_sql        CLOB;
        v_value      VARCHAR2(4000);
        v_first      BOOLEAN := TRUE;
    BEGIN
        validate_table(v_table);

        DBMS_LOB.CREATETEMPORARY(v_cols, TRUE);
        DBMS_LOB.CREATETEMPORARY(v_vals, TRUE);

        FOR c IN (
            SELECT column_name, data_type
            FROM all_tab_columns
            WHERE owner = 'NBPT2'
              AND table_name = v_table
            ORDER BY column_id
        ) LOOP
            BEGIN
                SELECT EXTRACTVALUE(p_row, '/Row/' || c.column_name)
                INTO v_value
                FROM dual;
            EXCEPTION
                WHEN OTHERS THEN
                    v_value := NULL;
            END;

            IF v_value IS NOT NULL THEN
                IF NOT v_first THEN
                    append_clob(v_cols, ', ');
                    append_clob(v_vals, ', ');
                END IF;

                append_clob(v_cols, c.column_name);

                IF c.data_type LIKE 'TIMESTAMP%' THEN
                    append_clob(
                        v_vals,
                        'TO_TIMESTAMP(''' || escape_sql(v_value) || ''', ''YYYY-MM-DD"T"HH24:MI:SS.FF'')'
                    );

                ELSIF c.data_type = 'DATE' THEN
                    append_clob(
                        v_vals,
                        'TO_DATE(''' || escape_sql(v_value) || ''', ''YYYY-MM-DD'')'
                    );

                ELSIF c.data_type IN ('NUMBER', 'FLOAT', 'INTEGER') THEN
                    append_clob(v_vals, escape_sql(v_value));

                ELSIF c.data_type = 'CLOB' THEN
                    append_clob(v_vals, 'TO_CLOB(''' || escape_sql(v_value) || ''')');

                ELSE
                    append_clob(v_vals, '''' || escape_sql(v_value) || '''');
                END IF;

                v_first := FALSE;
            END IF;
        END LOOP;

        IF v_first THEN
            RAISE_APPLICATION_ERROR(-20020, 'No valid columns found for table ' || v_table);
        END IF;

        v_sql := 'INSERT INTO NBPT2.' || v_table || ' (' || v_cols || ') VALUES (' || v_vals || ')';

        EXECUTE IMMEDIATE v_sql;
    END insert_row_from_xml;

    --------------------------------------------------------------------
    -- IMPORT TABLE
    --------------------------------------------------------------------
    PROCEDURE IMPORT_TABLE(
        p_table IN VARCHAR2,
        p_xml   IN CLOB
    ) IS
        v_table VARCHAR2(128) := normalize_name(p_table);
        v_doc   XMLTYPE;
    BEGIN
        validate_table(v_table);

        v_doc := XMLTYPE(p_xml);

        FOR r IN (
            SELECT VALUE(x) AS row_xml
            FROM TABLE(
                XMLSEQUENCE(
                    EXTRACT(
                        v_doc,
                        '//Table[@name="' || v_table || '"]/Rows/Row'
                    )
                )
            ) x
        ) LOOP
            insert_row_from_xml(v_table, r.row_xml);
        END LOOP;
    END IMPORT_TABLE;

    --------------------------------------------------------------------
    -- IMPORT MODULE
    --------------------------------------------------------------------
    PROCEDURE IMPORT_MODULE(
        p_module IN VARCHAR2,
        p_xml    IN CLOB
    ) IS
        v_tables t_table_list;
    BEGIN
        v_tables := get_module_tables(p_module);

        FOR i IN 1 .. v_tables.COUNT LOOP
            IMPORT_TABLE(v_tables(i), p_xml);
        END LOOP;
    END IMPORT_MODULE;

    --------------------------------------------------------------------
    -- IMPORT ALL
    --------------------------------------------------------------------
    PROCEDURE IMPORT_ALL(
        p_xml IN CLOB
    ) IS
        v_tables t_table_list;
    BEGIN
        v_tables := get_all_tables;

        FOR i IN 1 .. v_tables.COUNT LOOP
            IMPORT_TABLE(v_tables(i), p_xml);
        END LOOP;
    END IMPORT_ALL;

    --------------------------------------------------------------------
    -- GLAVNI EXPORT ENDPOINT ZA BACKEND
    --------------------------------------------------------------------
    PROCEDURE EXPORT_XML(
        p_mode   IN  VARCHAR2,
        p_target IN  VARCHAR2,
        p_xml    OUT CLOB
    ) IS
        v_mode VARCHAR2(20) := normalize_name(p_mode);
        v_body CLOB;
    BEGIN
        DBMS_LOB.CREATETEMPORARY(p_xml, TRUE);

        append_clob(p_xml, '<?xml version="1.0" encoding="UTF-8"?>');
        append_clob(p_xml, '<JobFairExport mode="' || v_mode || '"');

        IF p_target IS NOT NULL THEN
            append_clob(p_xml, ' target="' || escape_xml(normalize_name(p_target)) || '"');
        END IF;

        append_clob(p_xml, '>');

        IF v_mode = 'TABLE' THEN
            EXPORT_TABLE(p_target, v_body);

        ELSIF v_mode = 'MODULE' THEN
            EXPORT_MODULE(p_target, v_body);

        ELSIF v_mode = 'ALL' THEN
            EXPORT_ALL(v_body);

        ELSE
            RAISE_APPLICATION_ERROR(-20001, 'Invalid export mode: ' || p_mode);
        END IF;

        append_clob_clob(p_xml, v_body);
        append_clob(p_xml, '</JobFairExport>');
    END EXPORT_XML;

    --------------------------------------------------------------------
    -- GLAVNI IMPORT ENDPOINT ZA BACKEND
    --------------------------------------------------------------------
    PROCEDURE IMPORT_XML(
        p_mode   IN VARCHAR2,
        p_target IN VARCHAR2,
        p_xml    IN CLOB
    ) IS
        v_mode VARCHAR2(20) := normalize_name(p_mode);
    BEGIN
        SAVEPOINT import_start;

        IF v_mode = 'TABLE' THEN
            IMPORT_TABLE(p_target, p_xml);

        ELSIF v_mode = 'MODULE' THEN
            IMPORT_MODULE(p_target, p_xml);

        ELSIF v_mode = 'ALL' THEN
            IMPORT_ALL(p_xml);

        ELSE
            RAISE_APPLICATION_ERROR(-20002, 'Invalid import mode: ' || p_mode);
        END IF;

    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK TO import_start;
            RAISE_APPLICATION_ERROR(
                -20099,
                'XML import failed, rollback executed. Error: ' || SQLERRM
            );
    END IMPORT_XML;

END XML_TRANSFER_PKG;
/
