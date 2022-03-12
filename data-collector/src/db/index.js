const { Pool } = require('pg');
const pool = new Pool({
    user: process.env.POSTGRES_USERNAME,
    host: process.env.DB_HOST,
    database: process.env.POSTGRES_DB,
    password: process.env.POSTGRES_PSWD,
    port: process.env.DB_PORT
});

module.exports = {
  async query(text, params) {
      const start = Date.now();

      try {
          const res = await pool.query(text, params);
          const duration = Date.now() - start;

          console.log('executed query', {text, duration, rows: res.rowCount});

          return res;
      } catch (error) {
          console.error('Error in query', {text});
          throw error;
      }
  }
};
