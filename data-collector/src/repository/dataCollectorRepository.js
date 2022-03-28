const db = require('../db');

const insertDataCollectionResults = async (uuid, existingHeadersId, insertedRows) => {
    try {
        const { rows } = await db.query(
            `INSERT INTO data_collector_logs(uuid, existing_header_id, inserted_rows_count) VALUES ($1, $2, $3)`,
            [uuid, existingHeadersId, insertedRows]
        );
        return rows;
    } catch (err) {
        throw err;
    }
};

module.exports = {
    insertDataCollectionResults,
};
