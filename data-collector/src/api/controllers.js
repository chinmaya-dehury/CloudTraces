const { validationResult } = require("express-validator");

const getStatus = (req, res) => {
    res.status(200).json({ ok: true });
};

const collectData = (req, res) => {
    const errors = validationResult(req);

    if (!errors.isEmpty()) {
        return res.status(400).json({ errors: errors.array() });
    }
};

module.exports = {
    getStatus,
    collectData
}
