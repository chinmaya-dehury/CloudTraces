const existingHeadersRepository = require('../models/existingHeaders');

const processDataCollection = async ({ uuid, existingHeadersId, insertTime }) => {
    const result = {
        insertedRows: 0,
        errors: []
    };

    const existingHeader = await existingHeadersRepository.findExistingHeadersById(existingHeadersId);

    if (!existingHeader.length) {
        result.errors.push(`Existing headers with id ${existingHeadersId} not found`);
        return result;
    }
}

module.exports = {
  processDataCollection,
};
