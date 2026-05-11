-- DB-based scheduler for deleting expired jobs (Oracle).
--
-- Creates:
--  - Procedure: NBPT2.CLEANUP_EXPIRED_JOBS(p_retention_days)
--  - Scheduler job: NBPT2.JOB_CLEANUP_EXPIRED_JOBS (runs every 15 days)
--
-- Notes:
--  - Requires privileges: CREATE JOB (or MANAGE SCHEDULER) in schema NBPT2.
--  - If you use a different schema, adjust NBPT2.* accordingly.

-- 1) Procedure that performs the cleanup
CREATE OR REPLACE PROCEDURE NBPT2.CLEANUP_EXPIRED_JOBS(
    p_retention_days IN NUMBER DEFAULT 0
) AS
    v_cutoff TIMESTAMP;
BEGIN
    -- LocalDateTime in JPA maps to Oracle TIMESTAMP typically, so cast SYSTIMESTAMP.
    v_cutoff := CAST(SYSTIMESTAMP AS TIMESTAMP) - NUMTODSINTERVAL(p_retention_days, 'DAY');

    DELETE FROM NBPT2.JOBS
    WHERE EXPIRES_AT IS NOT NULL
      AND EXPIRES_AT < v_cutoff;

    COMMIT;
END;
/

-- 2) Drop existing scheduler job (idempotent)
BEGIN
    DBMS_SCHEDULER.DROP_JOB(
        job_name => 'NBPT2.JOB_CLEANUP_EXPIRED_JOBS',
        force    => TRUE
    );
EXCEPTION
    WHEN OTHERS THEN
        -- ORA-27475: "job does not exist"
        IF SQLCODE != -27475 THEN
            RAISE;
        END IF;
END;
/

-- 3) Create and enable scheduler job
BEGIN
    DBMS_SCHEDULER.CREATE_JOB(
        job_name            => 'NBPT2.JOB_CLEANUP_EXPIRED_JOBS',
        job_type            => 'STORED_PROCEDURE',
        job_action          => 'NBPT2.CLEANUP_EXPIRED_JOBS',
        number_of_arguments => 1,
        start_date          => SYSTIMESTAMP,
        repeat_interval     => 'FREQ=DAILY;INTERVAL=15',
        enabled             => FALSE,
        auto_drop           => FALSE,
        comments            => 'Deletes all expired jobs (retention days configurable)'
    );

    -- Set retention window (days)
    --  - 0 means: delete all expired rows (EXPIRES_AT < now)
    DBMS_SCHEDULER.SET_JOB_ARGUMENT_VALUE(
        job_name          => 'NBPT2.JOB_CLEANUP_EXPIRED_JOBS',
        argument_position => 1,
        argument_value    => 0
    );

    DBMS_SCHEDULER.ENABLE('NBPT2.JOB_CLEANUP_EXPIRED_JOBS');
END;
/

-- Optional: run once immediately (manual trigger)
-- BEGIN
--   NBPT2.CLEANUP_EXPIRED_JOBS(0);
-- END;
-- /

-- Optional: inspect job state
-- SELECT job_name, enabled, state, last_start_date, next_run_date
-- FROM user_scheduler_jobs
-- WHERE job_name = 'JOB_CLEANUP_EXPIRED_JOBS';
