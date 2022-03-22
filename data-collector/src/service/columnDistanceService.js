const distance = require('jaro-winkler');

const getSimilarColumns = (generalisedTblCols, dynamicTblCols) => {
    const res = [];

    if (dynamicTblCols && generalisedTblCols && dynamicTblCols.length && generalisedTblCols.length) {
        generalisedTblCols.forEach(genCol => {
            const colsAndDistances = [];

            dynamicTblCols.forEach(dynCol => {
                const jaroWinklerDist = distance(genCol, dynCol, { caseSensitive: false });

                colsAndDistances.push(
                    {
                        dynamicCol: dynCol,
                        generalisedCol: genCol,
                        distance: jaroWinklerDist
                    }
                );
            });

            const minDistanceCol = colsAndDistances.reduce((prev, current) => {
                return (current.distance > prev.distance) ? current : prev;
            });

            res.push(minDistanceCol);
        });

        return res.filter(col => col.distance >= 0.9);
    }
};

module.exports = {
    getSimilarColumns
}
