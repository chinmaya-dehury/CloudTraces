const dataCollectService = require('../service/dataCollectService');

const getStatus = (req, res) => {
    res.status(200).json({ ok: true });
};

const collectData = async (req, res) => {
    const response = await dataCollectService.processDataCollection(req.body);

    if (response.errors && response.errors.length) {
        return res.status(400).json(response);
    }

    res.status(200).json(response);
};

module.exports = {
    getStatus,
    collectData
}
