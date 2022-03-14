const generalisedTables = {
    serverless_platform: {
        columns: ['function_name', 'count', 'memory_mb'],
    },
    cloud_storage: {
        columns: ['read', 'write', 'blob_type', 'blob_bytes'],
    },
    cloud_cluster: {
        columns: ['plan_cpu', 'plan_disk', 'event_type'],
    }
};

module.exports = {
  generalisedTables,
};
