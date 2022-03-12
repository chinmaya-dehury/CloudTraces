const { body, validationResult } = require("express-validator");

module.exports.validateCollectDataReqBody = [
    body('uuid')
        .not()
        .isEmpty()
        .trim()
        .escape()
        .withMessage('cannot be null or empty'),
    body('existingHeadersId')
        .isInt({ min: 1 })
        .withMessage('cannot be less than 0 or must be an integer'),
    body('insertTime')
        .not()
        .isEmpty()
        .isISO8601().toDate()
        .withMessage('Must be a valid date time'),
    (req, res, next) => {
        const errors = validationResult(req);

        if (!errors.isEmpty()) {
            return res.status(400).json({ errors: errors.array() });
        }

        next();
    },
];
