const express = require('express');
const basicAuth = require('express-basic-auth');
const api = require('./api/controllers');
const morgan = require('morgan');
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
app.listen(port, () => {
   console.log(`App is running on port ${port}`);
});

function getUnauthorizedResponse(req) {
    return JSON.stringify(req.auth ? `Credentials ${req.auth.user}:${req.auth.password} rejected` : 'Unauthorized');
}
