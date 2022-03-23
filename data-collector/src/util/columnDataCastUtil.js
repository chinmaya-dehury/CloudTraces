const { generalisedTables } = require('../constants/constants');
const upcast  = require('upcast');

const castColumnsData = (similarColumns, lastInsertedData, { target_table_name, provider }) => {
    const castedObjects = lastInsertedData.map(data => {
        const castedObj = {};

        similarColumns.forEach(col => {
            const column = getGeneralisedTblData(target_table_name, col.generalisedCol);
            let castedColData;

            if (!column) {
                console.error(`No columns data found for target table ${target_table_name}`);
                return;
            }

            castedColData = upcast.to(data[col.dynamicCol], column.datatype);

            if (!castedColData) {
                console.warn(`${data[col.dynamicCol]} was not casted to ${column.datatype}`);
                return;
            }

            castedObj[col.generalisedCol] = castedColData;
            castedObj['provider'] = provider;
        });

        return castedObj;
    });

    return castedObjects.filter(obj => Object.keys(obj).length !== 0);
};

const getGeneralisedTblData = (targetTblName, columnName) => {
    return generalisedTables[targetTblName].columnsData.find(obj => obj.column === columnName);
}

module.exports = {
    castColumnsData,
};
