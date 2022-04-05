CREATE TABLE "serverless_platform"
(
    id                  SERIAL PRIMARY KEY NOT NULL,
    function_name       VARCHAR(255),
    count               BIGINT,
    allocated_memory_mb BIGINT,
    time                VARCHAR(255),
    provider            VARCHAR(255),
    create_time         TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time         TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    delete_time         TIMESTAMP                   DEFAULT NULL
);

CREATE TABLE "cloud_storage"
(
    id          SERIAL PRIMARY KEY NOT NULL,
    read        BOOLEAN,
    write       BOOLEAN,
    blob_type   VARCHAR(255),
    blob_bytes  BIGINT,
    provider    VARCHAR(255),
    create_time TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    delete_time TIMESTAMP                   DEFAULT NULL
);

CREATE TABLE "cloud_cluster"
(
    id          SERIAL PRIMARY KEY NOT NULL,
    plan_cpu    INT,
    plan_disk   BIGINT,
    event_type  VARCHAR(255),
    provider    VARCHAR(255),
    create_time TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    delete_time TIMESTAMP                   DEFAULT NULL
);

CREATE INDEX func_name_index ON "serverless_platform" (function_name);
CREATE INDEX serverless_platform_provider_index ON "serverless_platform" (provider);

CREATE INDEX cloud_storage_provider_index ON "cloud_storage" (provider);
CREATE INDEX blob_type_uindex ON "cloud_storage" (blob_type);

CREATE INDEX event_type_index ON "cloud_cluster" (event_type);
CREATE INDEX cloud_cluster_provider_index ON "cloud_cluster" (provider);

CREATE TRIGGER serverless_platform_trigger
    BEFORE UPDATE
    ON "serverless_platform"
    FOR EACH ROW
EXECUTE PROCEDURE upd_timestamp();

CREATE TRIGGER cloud_storage_trigger
    BEFORE UPDATE
    ON "cloud_storage"
    FOR EACH ROW
EXECUTE PROCEDURE upd_timestamp();

CREATE TRIGGER cloud_cluster_trigger
    BEFORE UPDATE
    ON "cloud_cluster"
    FOR EACH ROW
EXECUTE PROCEDURE upd_timestamp();
