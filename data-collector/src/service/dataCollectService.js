const existingHeadersRepository = require('../repository/existingHeadersRepository');
const dynamicTableRepository = require('../repository/dynamicTableRepository');
const levenshteinService = require('../service/levenshteinService');
const { generalisedTables } = require('../constants/constants');

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

    const { target_table_name, dynamic_table_name, file_headers } = existingHeader[0];

    const formattedInsertTime = new Date(insertTime)
        .toISOString()
        .replace('T', ' ')
        .replace('Z', '');

    const latestInsertedRows = await dynamicTableRepository.findLatestInsertedRows(dynamic_table_name, formattedInsertTime);

    if (!latestInsertedRows.length) {
        result.errors.push('No records have been inserted lately');
        return result;
    }

    const similarColumns = findSimilarColumns(target_table_name, file_headers);

    return result;
}

const findSimilarColumns = (targetTblName, dynamicTblColsAsString) => {
    const dynamicTblColumns = dynamicTblColsAsString.split(',');
    const generalisedTblColumns = getGeneralisedTblColumns(targetTblName);

    return levenshteinService.getSimilarColumns(generalisedTblColumns, dynamicTblColumns);
};

const getGeneralisedTblColumns = (targetTbl) => {
    return generalisedTables[targetTbl].columns;
};

module.exports = {
  processDataCollection,
};
