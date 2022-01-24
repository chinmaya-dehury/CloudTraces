CREATE TYPE event_types AS ENUM ('create', 'update', 'remove', 'delete');

CREATE TABLE "serverless_platform"
(
    id            SERIAL PRIMARY KEY NOT NULL,
    function_name VARCHAR(255),
    count         BIGINT,
    memory_mb     BIGINT,
    provider      VARCHAR(255),
    create_time   TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time   TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    delete_time   TIMESTAMP                   DEFAULT NULL
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
    event_type  event_types,
    create_time TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    delete_time TIMESTAMP                   DEFAULT NULL
);

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
