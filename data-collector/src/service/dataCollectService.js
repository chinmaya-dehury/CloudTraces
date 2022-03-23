const { findExistingHeadersById } = require('../repository/existingHeadersRepository');
const { findLatestInsertedRows } = require('../repository/dynamicTableRepository');
const { getSimilarColumns } = require('./columnDistanceService');
const { generalisedTables } = require('../constants/constants');
const { ColumnPointers, findColumnPointersByExistingHeaderId } = require("../repository/columnPointersRepository");
const { castColumnsData } = require('../helpers/columnDataCastHelper');
const { insertIntoServerlessPlatform } = require('../repository/generalisedTableRepository');

const processDataCollection = async ({ existingHeadersId, insertTime }) => {
    const result = {
        insertedRows: 0,
        errors: []
    };

    const existingHeader = await findExistingHeadersById(existingHeadersId);

    if (!existingHeader.length) {
        result.errors.push(`Existing headers with id ${existingHeadersId} not found`);
        return result;
    }

    const formattedInsertTime = new Date(insertTime)
        .toISOString()
        .replace('T', ' ')
        .replace('Z', '');

    const lastInserted = await findLatestInsertedRows(
        existingHeader[0].dynamic_table_name,
        formattedInsertTime
    );

    if (!lastInserted.length) {
        result.errors.push('No records have been inserted lately');
        return result;
    }

    const similarColumns = await findSimilarColumns(existingHeader[0], existingHeadersId);

    if (!similarColumns.length) {
        result.errors.push('No similar columns found');
        return result;
    }

    const castedData = castColumnsData(similarColumns, lastInserted, existingHeader[0]);

    if (!castedData || !castedData.length) {
        result.errors.push('No data was casted');
        return result;
    }

    return result;
}

const findSimilarColumns = async ({ target_table_name, file_headers }, existingHeaderId) => {
    let columnPointers = await findColumnPointersByExistingHeaderId(existingHeaderId);

    if (columnPointers === null || !columnPointers.length) {
        const dynamicTblColumns = file_headers.split(',');
        const generalisedTblColumns = getGeneralisedTblColumns(target_table_name);
        const similarColumns = getSimilarColumns(generalisedTblColumns, dynamicTblColumns);

        if (similarColumns && !similarColumns.length || (dynamicTblColumns.length !== similarColumns.length)) {
            return [];
        }

        try {
            columnPointers = new ColumnPointers(existingHeaderId, JSON.stringify(similarColumns));
            await columnPointers.saveColumnPointers();

            return similarColumns;
        } catch (err) {
            throw err;
        }
    }

    return columnPointers[0].column_pointers;
};

const getGeneralisedTblColumns = (targetTbl) => {
    return generalisedTables[targetTbl].columnsData.map(colData => colData.column);
};

module.exports = {
  processDataCollection,
};
