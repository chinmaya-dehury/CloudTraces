CREATE TABLE "column_pointers"
(
    id               SERIAL PRIMARY KEY NOT NULL,
    headers_table_id INT UNIQUE         NOT NULL,
    column_pointers  JSON               NOT NULL,
    upload_time      TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time      TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    delete_time      TIMESTAMP                   DEFAULT NULL,
    CONSTRAINT fk_headers_table
        FOREIGN KEY (headers_table_id)
            REFERENCES "existing_headers" (id)
);

CREATE TRIGGER column_pointers_trigger
    BEFORE UPDATE
    ON "column_pointers"
    FOR EACH ROW
EXECUTE PROCEDURE upd_timestamp();

CREATE UNIQUE INDEX headers_table_id_index ON "column_pointers" (headers_table_id);
