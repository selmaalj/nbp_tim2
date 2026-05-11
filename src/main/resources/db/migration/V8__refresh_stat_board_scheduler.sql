-- Refresh dashboard statistics (NBPT2.stats) for the StatBoard of a given year.
--
-- This migration creates:
--  - NBPT2.refresh_stat_board(p_year): recompute stats for the board with year = p_year
--  - NBPT2.refresh_current_stat_board: wrapper that refreshes the current year
--  - NBPT2.JOB_REFRESH_STAT_BOARD: DBMS_SCHEDULER job that runs every 24 hours

CREATE OR REPLACE PROCEDURE NBPT2.refresh_stat_board(
    p_year IN NUMBER
) AS
    v_board_id NBPT2.stat_boards.id%TYPE;

    v_active_jobs NUMBER;
    v_organizations NUMBER;
    v_media_outlets NUMBER;
    v_participations NUMBER;
    v_people NUMBER;

    FUNCTION uuid_str RETURN VARCHAR2 IS
        v_hex VARCHAR2(32);
    BEGIN
        v_hex := LOWER(RAWTOHEX(SYS_GUID()));
        RETURN SUBSTR(v_hex, 1, 8) || '-' ||
               SUBSTR(v_hex, 9, 4) || '-' ||
               SUBSTR(v_hex, 13, 4) || '-' ||
               SUBSTR(v_hex, 17, 4) || '-' ||
               SUBSTR(v_hex, 21, 12);
    END;
BEGIN
    SELECT id
      INTO v_board_id
      FROM NBPT2.stat_boards
     WHERE year = p_year;

    SELECT COUNT(*)
      INTO v_active_jobs
      FROM NBPT2.jobs
     WHERE expires_at IS NULL OR expires_at > SYSTIMESTAMP;

    SELECT COUNT(*)
      INTO v_organizations
      FROM NBPT2.organizations;

    SELECT COUNT(*)
      INTO v_media_outlets
      FROM NBPT2.media_outlets;

    SELECT COUNT(*)
      INTO v_participations
      FROM NBPT2.participations
     WHERE year = p_year;

    SELECT COUNT(*)
      INTO v_people
      FROM NBPT2.people;

    DELETE FROM NBPT2.stats WHERE board_id = v_board_id;

    INSERT INTO NBPT2.stats (id, board_id, label, value_int, value_text, plus, sort, icon)
    VALUES (uuid_str(), v_board_id, 'Active jobs', v_active_jobs, NULL, 1, 1, 'file');

    INSERT INTO NBPT2.stats (id, board_id, label, value_int, value_text, plus, sort, icon)
    VALUES (uuid_str(), v_board_id, 'Organizations', v_organizations, NULL, 1, 2, 'building');

    INSERT INTO NBPT2.stats (id, board_id, label, value_int, value_text, plus, sort, icon)
    VALUES (uuid_str(), v_board_id, 'Media outlets', v_media_outlets, NULL, 1, 3, 'chat');

    INSERT INTO NBPT2.stats (id, board_id, label, value_int, value_text, plus, sort, icon)
    VALUES (uuid_str(), v_board_id, 'Participations (' || p_year || ')', v_participations, NULL, 1, 4, 'presentation');

    INSERT INTO NBPT2.stats (id, board_id, label, value_int, value_text, plus, sort, icon)
    VALUES (uuid_str(), v_board_id, 'People', v_people, NULL, 1, 5, 'people');

    COMMIT;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- Board for requested year does not exist; do not fail the scheduler job.
        NULL;
END;
/

CREATE OR REPLACE PROCEDURE NBPT2.refresh_current_stat_board AS
BEGIN
    NBPT2.refresh_stat_board(EXTRACT(YEAR FROM SYSDATE));
END;
/

BEGIN
    -- Drop/recreate the scheduler job to keep environments consistent.
    BEGIN
        DBMS_SCHEDULER.DROP_JOB(job_name => 'NBPT2.JOB_REFRESH_STAT_BOARD', force => TRUE);
    EXCEPTION
        WHEN OTHERS THEN
            IF SQLCODE != -27475 THEN
                RAISE;
            END IF;
    END;

    DBMS_SCHEDULER.CREATE_JOB (
        job_name        => 'NBPT2.JOB_REFRESH_STAT_BOARD',
        job_type        => 'STORED_PROCEDURE',
        job_action      => 'NBPT2.REFRESH_CURRENT_STAT_BOARD',
        -- Anchor in Europe/Sarajevo timezone
        start_date      => TO_TIMESTAMP_TZ('2025-01-01 00:10:00 Europe/Sarajevo',
                                           'YYYY-MM-DD HH24:MI:SS TZR'),
        repeat_interval => 'FREQ=HOURLY;INTERVAL=24;BYMINUTE=0;BYSECOND=0',
        enabled         => TRUE,
        comments        => 'Refresh StatBoard stats every 24 hours for the current year.'
    );
END;
/
