const db = require('../db');

const findLatestInsertedRows = async (dynamicTableName, insertTime) => {
    try {
        const { rows } = await db.query(
            `SELECT * FROM ${dynamicTableName} WHERE insert_time >= '${insertTime}'`
        );
        return rows;
    } catch (error) {
        throw error;
    }
};

module.exports = {
    findLatestInsertedRows
}
