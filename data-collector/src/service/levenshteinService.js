const levenshtein = require('js-levenshtein');

const getSimilarColumns = (generalisedTblCols, dynamicTblCols) => {
    const res = [];

    if (dynamicTblCols && generalisedTblCols && dynamicTblCols.length && generalisedTblCols.length) {
        generalisedTblCols.forEach(genCol => {
            const colsAndDistances = [];

            dynamicTblCols.forEach(dynCol => {
                const levenshteinDis = levenshtein(genCol, dynCol);

                colsAndDistances.push(
                    {
                        dynamicCol: dynCol,
                        generalisedCol: genCol,
                        distance: levenshteinDis
                    }
                );
            });

            const minDistanceCol = colsAndDistances.reduce((prev, current) => {
                return (prev.distance < current.distance) ? prev : current;
            });

            res.push(minDistanceCol);
        });

        return res;
    }
};

module.exports = {
    getSimilarColumns
}
