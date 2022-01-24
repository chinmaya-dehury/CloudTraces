CREATE TYPE trace_types AS ENUM (
    'serverless_platform',
    'cloud_storage',
    'cloud_cluster',
    'cloud_instances',
    'machines',
    'cloud_job',
    'resources',
    'collections'
    );

CREATE TABLE "existing_headers"
(
    id                  SERIAL PRIMARY KEY NOT NULL,
    provider            VARCHAR(255)       NOT NULL,
    headers             TEXT[],
    dynamic_schema_name VARCHAR(255),
    trace_type          trace_types        NOT NULL,
    target_schema       VARCHAR(255)       NOT NULL,
    create_time         TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time         TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    delete_time         TIMESTAMP                   DEFAULT NULL
);

CREATE TRIGGER  existing_headers_trigger
    BEFORE UPDATE
    ON "existing_headers"
    FOR EACH ROW
EXECUTE PROCEDURE upd_timestamp();

CREATE UNIQUE INDEX headers_uindex ON "existing_headers" (headers);
CREATE UNIQUE INDEX dynamic_schema_name_uindex ON "existing_headers" (dynamic_schema_name);
