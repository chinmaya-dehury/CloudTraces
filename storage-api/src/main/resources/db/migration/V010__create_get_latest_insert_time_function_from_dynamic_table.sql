CREATE OR REPLACE FUNCTION get_latest_insert_time(
    dynamic_table_name TEXT
) RETURNS VARCHAR AS

$BODY$
    DECLARE latest_insert_time TIMESTAMP;
    BEGIN
        EXECUTE format('SELECT insert_time FROM %s ORDER BY insert_time DESC NULLS LAST LIMIT 1;', $1) INTO latest_insert_time;
        RETURN latest_insert_time;
    END;
$BODY$
    LANGUAGE plpgsql;

ALTER FUNCTION get_latest_insert_time(text)
    OWNER TO admin;
