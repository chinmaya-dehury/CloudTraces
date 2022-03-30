const express = require('express');
const basicAuth = require('express-basic-auth');
const api = require('./api/controllers');
const morgan = require('morgan');
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

app.use(express.json());
app.use(morgan('tiny'));
app.use(basicAuth({
    users: { admin: process.env.API_PASSWORD },
    challenge: true,
    unauthorizedResponse: getUnauthorizedResponse
}));


app.get('/status', errorHandler(api.getStatus));
app.listen(port, () => {
    console.log(`App is running on port ${port}`);
});

function getUnauthorizedResponse(req) {
    return JSON.stringify(req.auth ? `Credentials ${req.auth.user}:${req.auth.password} rejected` : 'Unauthorized');
}
