CREATE OR REPLACE FUNCTION prepare_csv_copy_query(
    target_table regclass,
    csv_file_url TEXT,
    delimiter TEXT)
    RETURNS text AS

$FUNC$
SELECT format('COPY %s(%s) FROM PROGRAM %L WITH DELIMITER ''%s'' QUOTE ''"'' CSV HEADER', $1,
              string_agg(quote_ident(attname), ', '), format('curl "%s"', $2), $3)
FROM pg_attribute
WHERE attrelid = $1
  AND attname != 'insert_time'
  AND NOT attisdropped -- no dropped (dead) columns
  AND attnum > 0; -- no system columns

$FUNC$
    LANGUAGE sql;
