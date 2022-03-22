const { generalisedTables } = require('../constants/constants');
const upcast  = require('upcast');

const castColumnsData = (similarColumns, lastInsertedData, { target_table_name, provider }) => {
    const castedObjects = lastInsertedData.map(data => {
        const castedObj = {};

        similarColumns.forEach(col => {
            const column = getGeneralisedTblData(target_table_name, col.generalisedCol);
            let castedColData;

            if (!column) {
                return;
            }

            try {
                castedColData = upcast.to(data[col.dynamicCol], column.datatype);
            } catch (castErr) {
                console.error(castErr);
                return;
            }

            if (!castedColData) {
                return;
            }

            castedObj[col.generalisedCol] = upcast.to(data[col.dynamicCol], column.datatype);
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
