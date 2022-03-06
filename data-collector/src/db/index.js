const { Pool } = require('pg');
const pool = new Pool();

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
