CREATE TABLE "file_meta"
(
    id                SERIAL PRIMARY KEY NOT NULL,
    file_name         VARCHAR(255)       NOT NULL,
    file_size         BIGINT                      DEFAULT 0,
    file_format       VARCHAR(255)       NOT NULL,
    provider          VARCHAR(255)       NOT NULL,
    headers_table_id INT                NOT NULL,
    upload_time       TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time       TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    delete_time       TIMESTAMP                   DEFAULT NULL,
    CONSTRAINT fk_headers_table
        FOREIGN KEY (headers_table_id)
            REFERENCES "existing_headers" (id)
);

CREATE TRIGGER file_meta_trigger
    BEFORE UPDATE
    ON "file_meta"
    FOR EACH ROW
EXECUTE PROCEDURE upd_timestamp();
