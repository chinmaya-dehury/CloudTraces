const db = require('../db');
const { buildForServerlessPlatform } = require('../factory/traceDataQueryBuilder');

const findServerlessPlatformData = async (req) => {
    const provider = req.query.provider;
    const memoryMb = req.query.memoryMb;
    const sqlQuery = buildForServerlessPlatform(provider, memoryMb);

    try {
        const { rows } = await db.query(sqlQuery);
        return rows;
    } catch (err) {
        throw err;
    }
};

module.exports = {
    findServerlessPlatformData,
};
