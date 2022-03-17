const generalisedTables = {
    serverless_platform: {
        columnsData: [
            {
                column: 'function_name',
                datatype: String
            },
            {
                column: 'count',
                datatype: BigInt
            },
            {
                column: 'memory_mb',
                datatype: BigInt
            }
        ],
    },
    cloud_storage: {
        columnsData: [
            {
                column: 'read',
                datatype: Boolean
            },
            {
                column: 'write',
                datatype: Boolean
            },
            {
                column: 'blob_type',
                datatype: String
            },
            {
                column: 'blob_bytes',
                datatype: BigInt
            }
        ],
    },
    cloud_cluster: {
        columns: [
            {
                column: 'plan_cpu',
                datatype: BigInt
            },
            {
                column: 'plan_disk',
                datatype: BigInt
            },
            {
                column: 'event_type',
                datatype: String
            }
        ],
    }
};

module.exports = {
  generalisedTables,
};
