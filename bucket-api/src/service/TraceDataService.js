const { findServerlessPlatformData } = require('../repository/traceDataRepository');
const findTraceData = async (req) => {
    const result = {
        rows: [],
        errors: [],
        timestamp: new Date().toISOString()
    };

    const traceData = await getTblNameFromTraceType(req.query.traceType, req);

    if (!traceData) {
        result.errors.push(`Trace type '${req.query.traceType}' does not exist`);
        return result;
    }

    result.rows = traceData;

    return result;
};

const getServerlessPlatformData = async (req) => {
    return await findServerlessPlatformData(req);
};

const getTblNameFromTraceType = async (traceType, req) => {
    switch (traceType) {
        case 'serverless_platform':
            return await getServerlessPlatformData(req);
        case 'cloud_storage':
            return 'cloud_storage';
        case 'cloud_cluster':
            return 'cloud_cluster';
        default:
            return undefined;
    }
};

module.exports = {
  findTraceData,
};
