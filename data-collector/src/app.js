const express = require('express');
const app = express();
const port = 6002;

require('dotenv').config();
app.use(express.json());

app.listen(port, () => {
   console.log(`App is running on port ${port}`);
});
