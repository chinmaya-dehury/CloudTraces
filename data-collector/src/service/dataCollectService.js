const existingHeadersRepository = require('../repository/existingHeadersRepository');
const dynamicTableRepository = require('../repository/dynamicTableRepository');

const processDataCollection = async ({ existingHeadersId, insertTime }) => {
    const result = {
        insertedRows: 0,
        errors: []
    };

    const existingHeader = await existingHeadersRepository.findExistingHeadersById(existingHeadersId);

    if (!existingHeader.length) {
        result.errors.push(`Existing headers with id ${existingHeadersId} not found`);
        return result;
    }

    // destruct row fields from DB
    const { dynamic_table_name } = existingHeader[0];

    const formattedInsertTime = new Date(insertTime)
        .toISOString()
        .replace('T', ' ')
        .replace('Z', '');

    const latestInsertedRows = await dynamicTableRepository.findLatestInsertedRows(dynamic_table_name, formattedInsertTime);

    if (!latestInsertedRows.length) {
        result.errors.push('No records have been inserted lately');
        return result;
    }

    return result;
}

module.exports = {
  processDataCollection,
};
