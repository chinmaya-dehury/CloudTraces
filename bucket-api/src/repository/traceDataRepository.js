const db = require('../db');
const { buildForServerlessPlatform, buildForCloudStorage, buildForCloudCluster} = require('../factory/traceDataQueryBuilder');

const findServerlessPlatformData = async ({ provider, allocatedMb }) => {
    const sqlQuery = buildForServerlessPlatform(provider, allocatedMb);

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

const findCloudCluster = async ({ planCpu, planDisk, eventType, provider }) => {
    const sqlQuery = buildForCloudCluster(planCpu, planDisk, eventType, provider);

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
    findCloudCluster,
};
