/*
 * Copyright 2021 Jim Earley (xml.jim@gmail.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify, merge,
 * publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to
 * whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 */

package io.xmljim.algorithms.functions.impl.statistics;

import io.xmljim.algorithms.functions.impl.AbstractModel;
import io.xmljim.algorithms.functions.impl.provider.NameConstants;
import io.xmljim.algorithms.functions.statistics.LinearRegressionModel;
import io.xmljim.algorithms.model.*;
import io.xmljim.algorithms.model.util.Scalar;

class LinearRegressionModelImpl extends AbstractModel implements LinearRegressionModel {

    public LinearRegressionModelImpl(MatrixParameter matrixParameter) {
        super(StatisticsFunctions.LINEAR_REGRESSION_MODEL.getName(), matrixParameter);

    }

    public LinearRegressionModelImpl(MatrixParameter matrixParameter, ScalarParameter setX, ScalarParameter setY) {
        super(StatisticsFunctions.LINEAR_REGRESSION_MODEL.getName(), matrixParameter, setX, setY);
    }

    public LinearRegressionModelImpl(ScalarVectorParameter vectorX, ScalarVectorParameter vectorY) {
        super(StatisticsFunctions.LINEAR_REGRESSION_MODEL.getName(), vectorX, vectorY);
    }

    @Override
    public ScalarCoefficient getSlopeCoefficient() {
        Coefficient<?> coeff = getCoefficient(StatisticsFunctions.SLOPE.getName());
        return (ScalarCoefficient) coeff;
    }

    @Override
    public ScalarCoefficient getInterceptCoefficient() {
        Coefficient<?> coeff = getCoefficient(StatisticsFunctions.INTERCEPT.getName());
        return (ScalarCoefficient) coeff;
    }

    @Override
    public ScalarCoefficient getRSquaredCoefficient() {
        Coefficient<?> coeff = getCoefficient(StatisticsFunctions.R_SQUARED.getName());
        return (ScalarCoefficient) coeff;
    }

    @Override
    public ScalarCoefficient getSlopeStandardErrorCoefficient() {
        Coefficient<?> coefficient = getCoefficient(StatisticsFunctions.SLOPE_STD_ERROR.getName());
        return (ScalarCoefficient) coefficient;
    }

    @Override
    public ScalarCoefficient getInterceptStandardErrorCoefficient() {
        Coefficient<?> coefficient = getCoefficient(StatisticsFunctions.INTERCEPT_STD_ERROR.getName());
        return (ScalarCoefficient) coefficient;
    }

    @Override
    public ScalarCoefficient getSlopeTStatisticCoefficient() {
        Coefficient<?> coefficient = getCoefficient(StatisticsFunctions.P_SLOPE.getName());
        return (ScalarCoefficient) coefficient;
    }

    @Override
    public ScalarCoefficient getPSlopeCoefficient() {
        return null;
    }

    @Override
    public void solve() {
        ScalarVector vectorX = getVector(NameConstants.X_VARIABLE);
        ScalarVector vectorY = getVector(NameConstants.Y_VARIABLE);

        ScalarFunction meanX = getFunctionProvider().getStatistics().mean(vectorX, NameConstants.X_VARIABLE);
        ScalarFunction meanY = getFunctionProvider().getStatistics().mean(vectorY, NameConstants.Y_VARIABLE);
        ScalarFunction varianceX = getFunctionProvider().getStatistics().variance(vectorX, meanX);
        ScalarFunction covariance = getFunctionProvider().getStatistics().covariance(vectorX, vectorY);
        ScalarFunction slope = getFunctionProvider().getStatistics().slope(varianceX, covariance);
        ScalarFunction intercept = getFunctionProvider().getStatistics().intercept(meanX, meanY, slope);
        ScalarFunction residualSumSquares = getFunctionProvider().getStatistics().residualSumOfSquares(vectorX, vectorY, slope, intercept);
        ScalarFunction totalSumSquaresY = getFunctionProvider().getStatistics().totalSumOfSquares(vectorY, meanY);
        ScalarFunction totalSumSquaresX = getFunctionProvider().getStatistics().totalSumOfSquares(vectorX, meanX);
        ScalarFunction rSquared = getFunctionProvider().getStatistics().rSquared(residualSumSquares, totalSumSquaresY);
        ScalarFunction meanSquaredError = getFunctionProvider().getStatistics().meanSquaredError(vectorX, vectorY, slope, intercept);
        ScalarFunction slopeStandardError = getFunctionProvider().getStatistics().slopeStandardError(totalSumSquaresX, meanSquaredError);
        ScalarFunction interceptStandardError = getFunctionProvider().getStatistics().interceptStandardError(meanSquaredError, meanX, totalSumSquaresX, Scalar.of(vectorX.length()));
        ScalarFunction slopeTStatistic = getFunctionProvider().getStatistics().slopeTStatistic(slope, slopeStandardError);
        ScalarFunction slopePValue = getFunctionProvider().getStatistics().slopePValue(vectorX.length() - 2, slopeTStatistic);

        setCoefficient(StatisticsFunctions.SLOPE, slope);
        setCoefficient(StatisticsFunctions.INTERCEPT, intercept);
        setCoefficient(StatisticsFunctions.R_SQUARED, rSquared);
        setCoefficient(StatisticsFunctions.SLOPE_STD_ERROR, slopeStandardError);
        setCoefficient(StatisticsFunctions.INTERCEPT_STD_ERROR, interceptStandardError);
        setCoefficient(StatisticsFunctions.T_SLOPE, slopeTStatistic);
        setCoefficient(StatisticsFunctions.P_SLOPE, slopePValue);

    }

    private ScalarVector getVector(String variable) {
        ScalarVector vector;

        if (hasParameter(NameConstants.MATRIX)) {
            Matrix matrix = getValue(NameConstants.MATRIX);
            int defaultColumn = variable.equals(NameConstants.X_VARIABLE) ? 0 : 1;
            int column = -1;

            if (hasParameter(NameConstants.COLUMN, variable)) {
                column = getValue(NameConstants.COLUMN, variable);
            } else {
                column = defaultColumn;
            }

            vector = matrix.getColumn(column);

        } else {
            vector = getValue(NameConstants.VECTOR, variable);
        }

        return vector;
    }
}
