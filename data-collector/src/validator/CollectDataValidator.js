const { body, validationResult } = require("express-validator");

module.exports.validateCollectDataReqBody = [
    body('uuid')
        .not()
        .isEmpty()
        .trim()
        .escape()
        .withMessage('cannot be null or empty'),
    body('sourceSchema')
        .not()
        .isEmpty()
        .trim()
        .escape()
        .withMessage('cannot be null or empty'),
    body('targetSchema')
        .not()
        .isEmpty()
        .trim()
        .escape()
        .withMessage('cannot be null or empty'),
    body('provider')
        .not()
        .isEmpty()
        .trim()
        .escape()
        .withMessage('cannot be null or empty'),
    (req, res, next) => {
        const errors = validationResult(req);

        if (!errors.isEmpty()) {
            return res.status(400).json({ errors: errors.array() });
        }
        next();
    },
];
