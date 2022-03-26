CREATE TABLE "data_collector_logs"
(
    id                  SERIAL PRIMARY KEY NOT NULL,
    uuid                VARCHAR(255)       NOT NULL,
    existing_header_id  BIGINT,
    inserted_rows_count BIGINT,
    create_time         TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time         TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    delete_time         TIMESTAMP                   DEFAULT NULL
);
