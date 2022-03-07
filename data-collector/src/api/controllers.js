const getStatus = (req, res) => {
    res.status(200).json({ ok: true });
};

const collectData = (req, res) => {
    res.status(200).json({ ok: true });
};

module.exports = {
    getStatus,
    collectData
}
