const db = require('../db');

function ColumnPointers(headersTableId, columnPointers) {
    this.headersTableId = headersTableId;
    this.columnPointers = columnPointers;
}

const findColumnPointersByExistingHeaderId = async (headerId) => {
    try {
        const { rows } = await db.query(
            `SELECT * FROM column_pointers WHERE headers_table_id=${headerId} AND delete_time IS NULL`
        );
        return rows;
    } catch (err) {
        throw err;
    }
};

ColumnPointers.prototype.saveColumnPointers = async function () {
    try {
        const { rows } = await db.query(
            `INSERT INTO column_pointers(headers_table_id, column_pointers) VALUES ($1, $2)`,
            [this.headersTableId, this.columnPointers]
        );
        return rows;
    } catch (err) {
        throw err;
    }
}

module.exports = {
    ColumnPointers,
    findColumnPointersByExistingHeaderId
};
