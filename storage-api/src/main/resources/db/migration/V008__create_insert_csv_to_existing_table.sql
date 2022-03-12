CREATE OR REPLACE FUNCTION insert_csv_to_existing_schema(
    target_table TEXT,
    csv_file_url TEXT,
    delimiter TEXT)
    RETURNS BOOLEAN AS

$BODY$

DECLARE

    prepared_copy_csv_statement TEXT;

BEGIN
    SELECT prepare_csv_copy_query($1, $2, $3) INTO prepared_copy_csv_statement;

    EXECUTE format(prepared_copy_csv_statement);

    RETURN TRUE;
END;

$BODY$
    LANGUAGE plpgsql VOLATILE
                     COST 100;
ALTER FUNCTION insert_csv_to_existing_schema(text, text, text)
    OWNER TO admin;
