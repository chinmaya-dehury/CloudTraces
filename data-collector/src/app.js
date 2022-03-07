const express = require('express');
const basicAuth = require('express-basic-auth');
const api = require('./api/controllers');
const morgan = require('morgan');
const { body } = require("express-validator");
const app = express();
const port = 6002;

require('dotenv').config();

app.use(morgan('tiny'));
app.use(basicAuth({
    users: { admin: process.env.API_PASSWORD },
    challenge: true,
    unauthorizedResponse: getUnauthorizedResponse
}));

app.get('/status', api.getStatus);
app.post(
    '/collect-data',
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
    api.collectData
);

app.listen(port, () => {
   console.log(`App is running on port ${port}`);
});

function getUnauthorizedResponse(req) {
    return JSON.stringify(req.auth ? `Credentials ${req.auth.user}:${req.auth.password} rejected` : 'Unauthorized');
}
