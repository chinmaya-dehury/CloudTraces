-- https://stackoverflow.com/questions/21018256/can-i-automatically-create-a-table-in-postgresql-from-a-csv-file-with-headers
CREATE OR REPLACE FUNCTION create_table_from_csv(
    target_table TEXT,
    csv_file_url TEXT,
    delimiter TEXT,
    col_count INTEGER)
    RETURNS BOOLEAN AS
$BODY$

DECLARE

    iter      INTEGER; -- dummy integer to iterate columns with
    col       TEXT; -- variable to keep the column name at each iteration
    col_first TEXT; -- first column name, e.g., top left corner on a csv file or spreadsheet
    curl_url  TEXT;

BEGIN
    SET SCHEMA 'public';

    CREATE TABLE temp_table ();

    -- add just enough number of columns
    FOR iter IN 1..col_count
        LOOP
            EXECUTE format('ALTER TABLE temp_table ADD COLUMN col_%s TEXT;', iter);
        END LOOP;

    -- copy the data from csv file
    curl_url := format('curl "%s"', csv_file_url);
    EXECUTE format('COPY temp_table FROM PROGRAM %L WITH DELIMITER ''%s'' QUOTE ''"'' CSV ', curl_url, delimiter);

    iter := 1;
    col_first := (SELECT col_1 FROM temp_table LIMIT 1);

    -- update the column names based on the first row which has the column names
    FOR col IN EXECUTE format(
            'select unnest(string_to_array(trim(temp_table::text, ''()''), '','')) from temp_table where col_1 = %L',
            col_first)
        LOOP
            EXECUTE format('alter table temp_table rename column col_%s to %s', iter, col);
            iter := iter + 1;
        END LOOP;

    -- delete the columns row
    EXECUTE format('delete from temp_table where %s = %L', col_first, col_first);
    -- add timestamp when the record was inserted
    EXECUTE format('ALTER TABLE temp_table ADD COLUMN insert_time timestamp DEFAULT CURRENT_TIMESTAMP;');
    -- change the temp table name to the name given as parameter, if not blank
    IF length(target_table) > 0 THEN
        EXECUTE format('ALTER TABLE temp_table RENAME TO %I', target_table);
    END IF;

    RETURN TRUE;
END;

$BODY$
    LANGUAGE plpgsql VOLATILE
                     COST 100;
ALTER FUNCTION create_table_from_csv(text, text, text, integer)
    OWNER TO admin;
