const db = require('../db');

const findExistingHeadersById = async (id) => {
    try {
        const { rows } = await db.query(
            `SELECT * FROM existing_headers WHERE id=${id} AND delete_time IS NULL`
        )
        return rows;
    } catch (err) {
        throw err;
    }
};

module.exports = {
    findExistingHeadersById
};
