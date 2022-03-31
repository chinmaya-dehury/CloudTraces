const { findTraceData } = require('../service/TraceDataService');

const getStatus = (req, res) => {
    res.status(200).json({ ok: true });
};

const getTraceData = (req, res) => {
    const response = findTraceData(req);

    if (response.errors && response.errors.length) {
        return res.status(400).json(response);
    }

    return res.status(200).json(response);
};


module.exports = {
    getStatus,
    getTraceData,
};
