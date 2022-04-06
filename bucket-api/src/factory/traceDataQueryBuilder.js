const buildForServerlessPlatform = (provider, allocatedMb) => {
    let res = '';

    if (provider && (allocatedMb && !isNaN(allocatedMb))) {
        return `SELECT * from serverless_platform WHERE
                provider='${provider}' AND
                allocated_memory_mb=${allocatedMb} AND delete_time IS NULL`;
    }

    if (provider) {
        res += 'provider=' + '\'' + provider + '\'' + ' AND ';
    }

    if (allocatedMb && !isNaN(allocatedMb)) {
        res += 'allocated_memory_mb=' + allocatedMb + ' AND ';
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

const buildForCloudCluster = (planCpu, planDisk, eventType, provider) => {
    let res = '';

    if (!isNaN(planCpu) && !isNaN(planDisk) && eventType && provider) {
        return `SELECT * FROM cloud_cluster WHERE 
                plan_cpu=${planCpu} AND 
                plan_disk=${planDisk} AND
                event_type='${eventType}' AND
                provider='${provider}' AND delete_time IS NULL`;
    }

    if (!isNaN(planCpu)) {
        res += 'plan_cpu=' + planCpu + ' AND ';
    }

    if (!isNaN(planDisk)) {
        res += 'plan_disk=' + planDisk + ' AND ';
    }

    if (eventType) {
        res += 'event_type=' + '\'' + eventType + '\'' + ' AND ';
    }

    if (provider) {
        res += 'provider=' + '\'' + provider + '\'' + ' AND ';
    }

    return 'SELECT * FROM cloud_cluster WHERE ' + res + ' delete_time IS NULL';
};

const isBooleanValue = (value) => {
    return value === 'true' || value === 'false';
};

module.exports = {
    buildForServerlessPlatform,
    buildForCloudStorage,
    buildForCloudCluster,
};
