const db = require('../db');
const { buildForServerlessPlatform, buildForCloudStorage } = require('../factory/traceDataQueryBuilder');

const findServerlessPlatformData = async ({ provider, memoryMb }) => {
    const sqlQuery = buildForServerlessPlatform(provider, memoryMb);

    try {
        const { rows } = await db.query(sqlQuery);
        return rows;
    } catch (err) {
        throw err;
    }
};

const findCloudStorage = async ({ provider, blobType, blobBytes, read, write }) => {
    const sqlQuery = buildForCloudStorage(provider, blobType, blobBytes, read, write);

    try {
        const { rows } = await db.query(sqlQuery);
        return rows;
    } catch (err) {
        throw err;
    }
};

module.exports = {
    findServerlessPlatformData,
    findCloudStorage,
};
