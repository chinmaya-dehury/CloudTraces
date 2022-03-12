const dataCollectService = require('../service/dataCollectService');

const getStatus = (req, res) => {
    res.status(200).json({ ok: true });
};

const collectData = async (req, res) => {
    const { rows, errors } = await dataCollectService.processDataCollection(req.body);

    if (errors && errors.length) {
        const timestamp = new Date();

        return res.status(400).json(
            {
                errors: errors,
                timestamp: timestamp.toISOString()
            }
        );
    }
    res.status(200).json({ ok: true });
};

module.exports = {
    getStatus,
    collectData
}
