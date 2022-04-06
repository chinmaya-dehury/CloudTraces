const { query, validationResult } = require("express-validator");

module.exports.validateTraceDataQuery = [
    query('traceType')
        .not()
        .isEmpty()
        .trim()
        .withMessage('Trace type must be specified'),
    (req, res, next) => {
        const errors = validationResult(req);

        if (!errors.isEmpty()) {
            return res.status(400).json({ errors: errors.array() });
        }

        next();
    },
];
