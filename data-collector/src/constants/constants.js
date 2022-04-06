const generalisedTables = {
    serverless_platform: {
        columnsData: [
            {
                column: 'function_name',
                datatype: 'string'
            },
            {
                column: 'count',
                datatype: 'number'
            },
            {
                column: 'allocated_memory_mb',
                datatype: 'number'
            },
            {
                column: 'time',
                datatype: 'string'
            }
        ],
    },
    cloud_storage: {
        columnsData: [
            {
                column: 'read',
                datatype: 'boolean'
            },
            {
                column: 'write',
                datatype: 'boolean'
            },
            {
                column: 'blob_type',
                datatype: 'string'
            },
            {
                column: 'blob_bytes',
                datatype: 'number'
            }
        ],
    },
    cloud_cluster: {
        columns: [
            {
                column: 'plan_cpu',
                datatype: 'number'
            },
            {
                column: 'plan_disk',
                datatype: 'number'
            },
            {
                column: 'event_type',
                datatype: 'string'
            }
        ],
    }
};

module.exports = {
  generalisedTables,
};
