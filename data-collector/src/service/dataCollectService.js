const existingHeadersRepository = require('../repository/existingHeadersRepository');
const dynamicTableRepository = require('../repository/dynamicTableRepository');
const levenshteinService = require('../service/levenshteinService');
const { generalisedTables } = require('../constants/constants');
const { ColumnPointers, findColumnPointersByExistingHeaderId } = require("../repository/columnPointersRepository");

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

    const formattedInsertTime = new Date(insertTime)
        .toISOString()
        .replace('T', ' ')
        .replace('Z', '');

    const latestInsertedRows = await dynamicTableRepository.findLatestInsertedRows(
        existingHeader[0].dynamic_table_name,
        formattedInsertTime
    );

    if (!latestInsertedRows.length) {
        result.errors.push('No records have been inserted lately');
        return result;
    }

    const similarColumns = await findSimilarColumns(existingHeader[0], existingHeadersId);

    return result;
}

const findSimilarColumns = async ({ target_table_name, file_headers }, existingHeaderId) => {
    let columnPointers = await findColumnPointersByExistingHeaderId(existingHeaderId);

    if (columnPointers === null || !columnPointers.length) {
        const dynamicTblColumns = file_headers.split(',');
        const generalisedTblColumns = getGeneralisedTblColumns(target_table_name);
        const similarColumns = levenshteinService.getSimilarColumns(generalisedTblColumns, dynamicTblColumns);

        try {
            columnPointers = new ColumnPointers(existingHeaderId, JSON.stringify(similarColumns));
            await columnPointers.saveColumnPointers();

            return columnPointers[0].column_pointers;
        } catch (err) {
            throw err;
        }
    }

    return columnPointers[0].column_pointers;
};

const getGeneralisedTblColumns = (targetTbl) => {
    return generalisedTables[targetTbl].columns;
};

module.exports = {
  processDataCollection,
};
