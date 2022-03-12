CREATE TYPE trace_types AS ENUM (
    'SERVERLESS_PLATFORM',
    'CLOUD_STORAGE',
    'CLOUD_CLUSTER',
    'CLOUD_INSTANCES',
    'MACHINES',
    'CLOUD_JOB',
    'RESOURCES',
    'COLLECTIONS'
    );

CREATE TABLE "existing_headers"
(
    id                 SERIAL PRIMARY KEY NOT NULL,
    provider           VARCHAR(255),
    file_headers       VARCHAR            NOT NULL,
    dynamic_table_name VARCHAR(255),
    trace_type         trace_types        NOT NULL,
    target_table_name  VARCHAR(255),
    create_time        TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time        TIMESTAMP                   DEFAULT CURRENT_TIMESTAMP,
    delete_time        TIMESTAMP                   DEFAULT NULL
);

CREATE TRIGGER existing_headers_trigger
    BEFORE UPDATE
    ON "existing_headers"
    FOR EACH ROW
EXECUTE PROCEDURE upd_timestamp();

CREATE UNIQUE INDEX headers_uindex ON "existing_headers" (file_headers);
CREATE UNIQUE INDEX dynamic_table_name_uindex ON "existing_headers" (dynamic_table_name);
