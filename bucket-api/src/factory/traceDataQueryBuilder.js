const buildForServerlessPlatform = (provider, memoryMb) => {
    let res = '';

    if (provider && (memoryMb && !isNaN(memoryMb))) {
        return `SELECT * from serverless_platform WHERE provider='${provider}' AND memory_mb=${memoryMb} AND delete_time IS NULL`;
    }

    if (provider) {
        res += "provider=" + '\'' + provider + '\'' + ' AND';
    }

    if (memoryMb && !isNaN(memoryMb)) {
        res += 'memory_mb=' + memoryMb + ' AND';
    }

    return 'SELECT * FROM serverless_platform WHERE ' + res + ' delete_time IS NULL';
};

module.exports = {
    buildForServerlessPlatform,
};
