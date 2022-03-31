const findTraceData = (req) => {
    const result = {
        rows: [],
        errors: [],
        timestamp: new Date().toISOString()
    };

    const traceTbl = getTblNameFromTraceType(req.query.traceType);

    if (!traceTbl) {
        result.errors.push(`Trace type ${req.query.traceType} does not exist`);
        return result;
    }

    return result;
};

const getTblNameFromTraceType = (traceType) => {
    switch (traceType) {
        case 'serverless_platform':
            return 'serverless_platform';
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
