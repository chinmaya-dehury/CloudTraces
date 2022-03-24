const db = require('../db');
const format = require('pg-format');

const insertIntoGeneralisedTable = async (castedData, { target_table_name }) => {
    const columns = getPropertyNames(castedData);
    const formattedCastedData = arrayOfObjectsTo2dArray(castedData);

    try {
        const { rowCount } = await db.query(
            format('INSERT INTO %s(%s) VALUES %L', target_table_name, columns, formattedCastedData)
        );
        return rowCount;
    } catch (err) {
        throw err;
    }
};

const getPropertyNames = (arrayOfObjects) => {
    return Object.keys(arrayOfObjects.reduce((o, c) => Object.assign(o, c)));
};

const arrayOfObjectsTo2dArray = (arrayOfObjects) => {
    return arrayOfObjects.map(obj => Object.values(obj));
};

module.exports = {
    insertIntoGeneralisedTable,
};
