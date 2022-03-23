const db = require('../db');
const format = require('pg-format');

const insertIntoServerlessPlatform = async (castedData) => {
    const columns = getPropertyNames(castedData);
    const formattedCastedData = arrayOfObjectsTo2dArray(castedData);

    try {
        const { rows } = await db.query(
            format('INSERT INTO serverless_platform(%s) VALUES %L', columns, formattedCastedData)
        );
        return rows;
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
    insertIntoServerlessPlatform,
};
