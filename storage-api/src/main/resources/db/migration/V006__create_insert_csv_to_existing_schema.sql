CREATE OR REPLACE FUNCTION insert_csv_to_existing_schema(
    target_table TEXT,
    csv_file_url TEXT,
    delimiter TEXT)
    RETURNS BOOLEAN AS

$BODY$

DECLARE

    curl_url TEXT;

BEGIN
    curl_url := format('curl "%s"', csv_file_url);
    EXECUTE format('COPY %s FROM PROGRAM %L WITH DELIMITER ''%s'' QUOTE ''"'' CSV ', target_table, curl_url, delimiter);

    RETURN TRUE;
END;

$BODY$
    LANGUAGE plpgsql VOLATILE
                     COST 100;
ALTER FUNCTION insert_csv_to_existing_schema(text, text, text)
    OWNER TO admin;
