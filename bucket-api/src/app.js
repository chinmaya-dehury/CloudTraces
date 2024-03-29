const express = require('express');
const swaggerUi = require("swagger-ui-express");
const basicAuth = require('express-basic-auth');
const api = require('./api/controllers');
const morgan = require('morgan');
const { validateTraceDataQuery } = require('./validator/TraceDataQueryValidator');
const app = express();
const port = 6003;
const errorHandler = fn => (req, res, next) => {
    Promise.resolve(fn(req, res, next))
        .catch(err => {
            const timestamp = new Date();
            // log the error
            console.error(err)
            res.status(500).send(
                {
                    errors: [ 'Internal Server Error' ],
                    timestamp: timestamp.toISOString()
                }
            );
        });
};

require('dotenv').config();

const swaggerDocument = require('../swagger.json');

app.use(
    '/api-docs',
    swaggerUi.serve,
    swaggerUi.setup(swaggerDocument, { explorer: true })
);
app.use(express.json());
app.use(morgan('tiny'));
app.use(basicAuth({
    users: { admin: process.env.API_PASSWORD },
    challenge: true,
    unauthorizedResponse: getUnauthorizedResponse
}));


app.get('/status', errorHandler(api.getStatus));
app.get('/trace-data', validateTraceDataQuery, errorHandler(api.getTraceData));

app.listen(port, () => {
    console.log(`App is running on port ${port}`);
});

function getUnauthorizedResponse(req) {
    return JSON.stringify(req.auth ? `Credentials for ${req.auth.user} rejected` : 'Unauthorized');
}
