-- Performance check for indexes added in:
--   src/main/resources/db/migration/V9__add_image_lookup_indexes.sql
--
-- How to use (Oracle SQL*Plus / SQLcl / SQL Developer):
--   1) Run this script BEFORE applying V9 (baseline).
--   2) Apply V9 (Flyway migrate).
--   3) Run this script again AFTER applying V9.
--
-- Compare:
--   - EXPLAIN PLAN output (FULL TABLE SCAN + SORT vs INDEX RANGE SCAN)
--   - Average elapsed time printed by DBMS_OUTPUT
--
-- Notes:
--   - Use a DB with enough rows for a visible difference.
--   - First run is usually slower (cache warm-up). Script runs 5 times; look at avg.

-- Provide existing IDs from your DB (or keep the defaults if those exist).
-- Note: define values WITHOUT quotes because the queries already wrap them in single quotes.
DEFINE ARTICLE_ID = 60000000-0000-0000-0000-000000000001
DEFINE ORGANIZATION_ID = 40000000-0000-0000-0000-000000000001

SET SERVEROUTPUT ON

-- Optional but recommended (lets optimizer pick the right plan):
-- BEGIN
--   DBMS_STATS.GATHER_TABLE_STATS(ownname => 'NBPT2', tabname => 'ARTICLE_IMAGES', cascade => TRUE);
--   DBMS_STATS.GATHER_TABLE_STATS(ownname => 'NBPT2', tabname => 'GALLERY_IMAGES', cascade => TRUE);
-- END;
-- /

PROMPT
PROMPT ===============================
PROMPT EXPLAIN PLAN: ARTICLE_IMAGES
PROMPT ===============================
EXPLAIN PLAN FOR
SELECT ai.id, ai.display_order
FROM NBPT2.article_images ai
WHERE ai.article_id = '&&ARTICLE_ID'
ORDER BY ai.display_order ASC;

SELECT *
FROM TABLE(DBMS_XPLAN.DISPLAY);

PROMPT
PROMPT ===============================
PROMPT TIMING: ARTICLE_IMAGES (5 runs)
PROMPT ===============================
DECLARE
  v_start    NUMBER;
  v_end      NUMBER;
  v_rows     NUMBER;
  v_total_cs NUMBER := 0;
BEGIN
  FOR i IN 1..5 LOOP
    v_rows := 0;
    v_start := DBMS_UTILITY.GET_TIME;

    FOR r IN (
      SELECT ai.id, ai.display_order
      FROM NBPT2.article_images ai
      WHERE ai.article_id = '&&ARTICLE_ID'
      ORDER BY ai.display_order ASC
    ) LOOP
      v_rows := v_rows + 1;
    END LOOP;

    v_end := DBMS_UTILITY.GET_TIME;
    v_total_cs := v_total_cs + (v_end - v_start);

    DBMS_OUTPUT.PUT_LINE('Run ' || i || ': rows=' || v_rows || ', elapsed_ms=' || ((v_end - v_start) * 10));
  END LOOP;

  DBMS_OUTPUT.PUT_LINE('Avg elapsed_ms=' || ((v_total_cs / 5) * 10));
END;
/

PROMPT
PROMPT ===============================
PROMPT EXPLAIN PLAN: GALLERY_IMAGES
PROMPT ===============================
EXPLAIN PLAN FOR
SELECT gi.id, gi.display_order
FROM NBPT2.gallery_images gi
WHERE gi.organization_id = '&&ORGANIZATION_ID'
ORDER BY gi.display_order ASC;

SELECT *
FROM TABLE(DBMS_XPLAN.DISPLAY);

PROMPT
PROMPT ===============================
PROMPT TIMING: GALLERY_IMAGES (5 runs)
PROMPT ===============================
DECLARE
  v_start    NUMBER;
  v_end      NUMBER;
  v_rows     NUMBER;
  v_total_cs NUMBER := 0;
BEGIN
  FOR i IN 1..5 LOOP
    v_rows := 0;
    v_start := DBMS_UTILITY.GET_TIME;

    FOR r IN (
      SELECT gi.id, gi.display_order
      FROM NBPT2.gallery_images gi
      WHERE gi.organization_id = '&&ORGANIZATION_ID'
      ORDER BY gi.display_order ASC
    ) LOOP
      v_rows := v_rows + 1;
    END LOOP;

    v_end := DBMS_UTILITY.GET_TIME;
    v_total_cs := v_total_cs + (v_end - v_start);

    DBMS_OUTPUT.PUT_LINE('Run ' || i || ': rows=' || v_rows || ', elapsed_ms=' || ((v_end - v_start) * 10));
  END LOOP;

  DBMS_OUTPUT.PUT_LINE('Avg elapsed_ms=' || ((v_total_cs / 5) * 10));
END;
/
