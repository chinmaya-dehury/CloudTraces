const buildForServerlessPlatform = (provider, memoryMb) => {
    let res = '';

    if (provider && (memoryMb && !isNaN(memoryMb))) {
        return `SELECT * from serverless_platform WHERE
                provider='${provider}' AND
                memory_mb=${memoryMb} AND delete_time IS NULL`;
    }

    if (provider) {
        res += 'provider=' + '\'' + provider + '\'' + ' AND ';
    }

    if (memoryMb && !isNaN(memoryMb)) {
        res += 'memory_mb=' + memoryMb + ' AND ';
    }

    return 'SELECT * FROM serverless_platform WHERE ' + res + ' delete_time IS NULL';
};

const buildForCloudStorage = (provider, blobType, blobBytes, read, write) => {
  let res = '';

  if (provider && blobType && !isNaN(blobBytes) && (isBooleanValue(read) && isBooleanValue(write))) {
      return `SELECT * FROM cloud_storage WHERE 
              provider='${provider}' AND 
              blob_type='${blobType}' AND
              blob_bytes=${blobBytes} AND
              read=${read} AND
              write=${write} AND delete_time IS NULL`;
  }

  if (provider) {
      res += 'provider=' + '\'' + provider + '\'' + ' AND ';
  }

  if (blobType) {
      res += 'blob_type=' + '\'' + blobType + '\'' + ' AND ';
  }

  if (!isNaN(blobBytes)) {
      res += 'blob_bytes=' + blobBytes + ' AND ';
  }

  if (isBooleanValue(read)) {
      res += 'read=' + read + ' AND ';
  }

  if (isBooleanValue(write)) {
      res += 'write=' + write + ' AND';
  }

  return 'SELECT * FROM cloud_storage WHERE ' + res + ' delete_time IS NULL';
};

const isBooleanValue = (value) => {
    return value === 'true' || value === 'false';
};

module.exports = {
    buildForServerlessPlatform,
    buildForCloudStorage,
};
