-- Daily full-refresh backups for 3 tables (same schema)
-- Strategy:
--  - One backup table per source table, identical schema
--  - Every day at 09:00 Europe/Sarajevo: TRUNCATE backup tables, then INSERT
--    full source tables

-- Backup tables: identical structure to source
CREATE TABLE NBPT2.people_bkp AS
SELECT * FROM NBPT2.people WHERE 1 = 0;

CREATE INDEX NBPT2.idx_people_bkp_id ON NBPT2.people_bkp(id);

CREATE TABLE NBPT2.organizations_bkp AS
SELECT * FROM NBPT2.organizations WHERE 1 = 0;

CREATE INDEX NBPT2.idx_orgs_bkp_id ON NBPT2.organizations_bkp(id);

CREATE TABLE NBPT2.jobs_bkp AS
SELECT * FROM NBPT2.jobs WHERE 1 = 0;

CREATE INDEX NBPT2.idx_jobs_bkp_id ON NBPT2.jobs_bkp(id);

CREATE OR REPLACE PROCEDURE NBPT2.run_table_backups AS
BEGIN
    EXECUTE IMMEDIATE 'TRUNCATE TABLE NBPT2.people_bkp';
    INSERT INTO NBPT2.people_bkp
    SELECT * FROM NBPT2.people;

    EXECUTE IMMEDIATE 'TRUNCATE TABLE NBPT2.organizations_bkp';
    INSERT INTO NBPT2.organizations_bkp
    SELECT * FROM NBPT2.organizations;

    EXECUTE IMMEDIATE 'TRUNCATE TABLE NBPT2.jobs_bkp';
    INSERT INTO NBPT2.jobs_bkp
    SELECT * FROM NBPT2.jobs;

    COMMIT;
END;


BEGIN
    -- Drop/recreate the scheduler job to keep environments consistent.
    BEGIN
        DBMS_SCHEDULER.DROP_JOB(job_name => 'NBPT2.JOB_DAILY_TABLE_BACKUPS', force => TRUE);
    EXCEPTION
        WHEN OTHERS THEN
            IF SQLCODE != -27475 THEN
                RAISE;
            END IF;
    END;

    DBMS_SCHEDULER.CREATE_JOB (
        job_name        => 'NBPT2.JOB_DAILY_TABLE_BACKUPS',
        job_type        => 'STORED_PROCEDURE',
        job_action      => 'NBPT2.RUN_TABLE_BACKUPS',
        -- Anchor start_date in Europe/Sarajevo so BYHOUR=9 means 09:00 local time,
        -- with correct DST handling.
        start_date      => TO_TIMESTAMP_TZ('2025-01-01 09:00:00 Europe/Sarajevo',
                                           'YYYY-MM-DD HH24:MI:SS TZR'),
        repeat_interval => 'FREQ=DAILY;BYHOUR=9;BYMINUTE=0;BYSECOND=0',
        enabled         => TRUE,
        comments        => 'Daily full-refresh copy of 3 tables into *_BKP tables at 09:00 Europe/Sarajevo.'
    );
END;
