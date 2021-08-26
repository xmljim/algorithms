
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

import io.xmljim.algorithms.functions.impl.AbstractFunctionFactory;
import io.xmljim.algorithms.functions.impl.provider.NameConstants;
import io.xmljim.algorithms.functions.provider.FunctionProvider;
import io.xmljim.algorithms.functions.statistics.LinearRegressionModel;
import io.xmljim.algorithms.functions.statistics.Statistics;
import io.xmljim.algorithms.model.*;
import io.xmljim.algorithms.model.util.Scalar;

public class StatisticsImpl extends AbstractFunctionFactory implements Statistics {
    public StatisticsImpl(final FunctionProvider functionProvider) {
        super(functionProvider);
    }

    @Override
    public ScalarFunction sum(final ScalarVector vector) {
        return sum(vector, vector.getVariable());
    }

    @Override
    public ScalarFunction sum(final ScalarVector vector, final String variable) {
        ScalarVectorParameter parameter = getModelProvider().getParameterFactory().createParameter(vector.getName(), variable, vector);
        return new SumFunction(parameter, variable);
    }

    @Override
    public ScalarFunction mean(final ScalarVector vector) {
        return mean(vector, vector.getVariable());
    }

    @Override
    public ScalarFunction mean(final ScalarVector vector, final String variable) {
        ScalarVectorParameter parameter = getModelProvider().getParameterFactory().createParameter(vector.getName(), variable, vector);
        return new MeanFunction(parameter, variable);
    }

    @Override
    public ScalarFunction median(final ScalarVector vector) {
        return median(vector, vector.getName());
    }

    @Override
    public ScalarFunction median(final ScalarVector vector, final String variable) {
        ScalarVectorParameter parameter = getModelProvider().getParameterFactory().createParameter(vector.getName(), variable, vector);
        return new MedianFunction(parameter, variable);
    }

    @Override
    public ScalarFunction variance(final ScalarVector vector) {
        return variance(vector, vector.getVariable());
    }

    @Override
    public ScalarFunction variance(final ScalarVector vector, final String variable) {
        ScalarVectorParameter parameter = getModelProvider().getParameterFactory().createParameter(vector.getName(), variable, vector);
        return new VarianceFunction(parameter, variable);
    }

    @Override
    public ScalarFunction variance(final ScalarVector vector, final ScalarFunction meanFunction) {

        return variance(vector, meanFunction, meanFunction.getVariable());
    }

    @Override
    public ScalarFunction variance(final ScalarVector vector, final ScalarFunction meanFunction, final String variable) {
        ScalarVectorParameter vectorParameter = getModelProvider().getParameterFactory().createParameter(vector.getName(), variable, vector);
        ScalarFunctionParameter functionParameter = getModelProvider().getParameterFactory().createParameter(meanFunction.getName(), variable, meanFunction);
        return new VarianceFunction(vectorParameter, functionParameter, variable);
    }

    @Override
    public ScalarFunction standardDeviation(final ScalarFunction varianceFunction) {
        return standardDeviation(varianceFunction, varianceFunction.getVariable());
    }

    @Override
    public ScalarFunction standardDeviation(final ScalarVector vectorParameter) {
        return standardDeviation(vectorParameter, vectorParameter.getVariable());
    }

    @Override
    public ScalarFunction standardDeviation(final ScalarFunction varianceFunction, final String variable) {
        ScalarFunctionParameter functionParameter = getModelProvider().getParameterFactory().createParameter(variable, varianceFunction);
        return new StandardDeviationFunction(functionParameter, variable);
    }

    @Override
    public ScalarFunction standardDeviation(final ScalarVector vector, final String variable) {
        ScalarVectorParameter vectorParameter = getModelProvider().getParameterFactory().createParameter(variable, vector);
        return new StandardDeviationFunction(vectorParameter, variable);
    }

    @Override
    public ScalarFunction covariance(final ScalarVector vectorX, final ScalarVector vectorY) {
        ScalarVectorParameter vectorXParam = getModelProvider().getParameterFactory().createParameter(vectorX.getName(), NameConstants.X_VARIABLE, vectorX);
        ScalarVectorParameter vectorYParam = getModelProvider().getParameterFactory().createParameter(vectorY.getName(), NameConstants.Y_VARIABLE, vectorY);
        return new CovarianceFunction(vectorXParam, vectorYParam);
    }

    public ScalarFunction covariance(final ScalarVector vectorX, final ScalarVector vectorY, ScalarFunction meanX, ScalarFunction meanY) {
        ScalarVectorParameter vectorXParam = getModelProvider().getParameterFactory().createParameter(NameConstants.VECTOR, NameConstants.X_VARIABLE, vectorX);
        ScalarVectorParameter vectorYParam = getModelProvider().getParameterFactory().createParameter(NameConstants.VECTOR, NameConstants.Y_VARIABLE, vectorY);
        ScalarFunctionParameter meanXParam = getModelProvider().getParameterFactory().createParameter(meanX.getName(), NameConstants.X_VARIABLE, meanX);
        ScalarFunctionParameter meanYParam = getModelProvider().getParameterFactory().createParameter(meanY.getName(), NameConstants.Y_VARIABLE, meanY);

        return new CovarianceFunction(vectorXParam, vectorYParam, meanXParam, meanYParam);
    }

    @Override
    public ScalarFunction slope(final ScalarFunction varianceX, final ScalarFunction covariance) {
        ScalarFunctionParameter varianceXParameter = getModelProvider().getParameterFactory().createParameter(varianceX.getName(), NameConstants.X_VARIABLE, varianceX);
        ScalarFunctionParameter covarianceParameter = getModelProvider().getParameterFactory().createParameter(covariance);

        return new SlopeFunction(covarianceParameter, varianceXParameter);
    }

    @Override
    public ScalarFunction intercept(final ScalarFunction meanX, final ScalarFunction meanY, final ScalarFunction slopeFunction) {
        ScalarFunctionParameter meanXParam = getModelProvider().getParameterFactory().createParameter(meanX.getName(),  NameConstants.X_VARIABLE, meanX);
        ScalarFunctionParameter meanYParam = getModelProvider().getParameterFactory().createParameter(meanY.getName(),  NameConstants.Y_VARIABLE, meanY);
        ScalarFunctionParameter slopeParam = getModelProvider().getParameterFactory().createParameter(slopeFunction);

        return new InterceptFunction(meanXParam, meanYParam, slopeParam);
    }

    @Override
    public ScalarFunction residualSumOfSquares(final ScalarVector vectorX, final ScalarVector vectorY, final ScalarFunction slopeFunction, final ScalarFunction interceptFunction) {
        ScalarVectorParameter vectorXParameter = getModelProvider().getParameterFactory().createParameter(NameConstants.VECTOR, NameConstants.X_VARIABLE, vectorX);
        ScalarVectorParameter vectorYParameter = getModelProvider().getParameterFactory().createParameter(NameConstants.VECTOR, NameConstants.Y_VARIABLE, vectorY);
        ScalarFunctionParameter slopeParameter = getModelProvider().getParameterFactory().createParameter(slopeFunction);
        ScalarFunctionParameter interceptParameter = getModelProvider().getParameterFactory().createParameter(interceptFunction);

        return new ResidualSumOfSquaresFunction(vectorXParameter, vectorYParameter, slopeParameter, interceptParameter);
    }

    @Override
    public ScalarFunction totalSumOfSquares(final ScalarVector vector, final ScalarFunction mean) {
        ScalarVectorParameter vectorXParameter = getModelProvider().getParameterFactory().createParameter(NameConstants.VECTOR, vector);
        ScalarFunctionParameter meanYParameter = getModelProvider().getParameterFactory().createParameter(mean.getName(), mean);

        return new TotalSumOfSquaresFunction(vectorXParameter, meanYParameter);
    }

    @Override
    public ScalarFunction rSquared(final ScalarFunction residualVarianceFunction, final ScalarFunction totalVarianceFunction) {
        ScalarFunctionParameter residualVarianceParameter = getModelProvider().getParameterFactory().createParameter(residualVarianceFunction);
        ScalarFunctionParameter totalVarianceParameter = getModelProvider().getParameterFactory().createParameter(totalVarianceFunction);

        return new RSquaredFunction(residualVarianceParameter, totalVarianceParameter);
    }

    @Override
    public ScalarFunction meanSquaredError(final ScalarVector vectorX, final ScalarVector vectorY, final ScalarFunction slopeFunction, final ScalarFunction interceptFunction) {
        ScalarVectorParameter vectorXParameter = getModelProvider().getParameterFactory().createParameter(NameConstants.VECTOR, NameConstants.X_VARIABLE, vectorX);
        ScalarVectorParameter vectorYParameter = getModelProvider().getParameterFactory().createParameter(NameConstants.VECTOR, NameConstants.Y_VARIABLE, vectorY);
        ScalarFunctionParameter slopeParameter = getModelProvider().getParameterFactory().createParameter(slopeFunction.getName(), slopeFunction);
        ScalarFunctionParameter interceptParameter = getModelProvider().getParameterFactory().createParameter(interceptFunction.getName(), interceptFunction);

        return new MeanSquaredErrorFunction(vectorXParameter, vectorYParameter, slopeParameter, interceptParameter);
    }

    @Override
    public ScalarFunction slopeStandardError(final ScalarFunction sumOfSquaresX, final ScalarFunction meanSquaredError) {
        ScalarFunctionParameter sumSquaresXParameter = getModelProvider().getParameterFactory().createParameter(sumOfSquaresX.getName(), NameConstants.X_VARIABLE, sumOfSquaresX);
        ScalarFunctionParameter meanSquareErrorParameter = getModelProvider().getParameterFactory().createParameter(meanSquaredError.getName(), meanSquaredError);

        return new SlopeStandardErrorFunction(sumSquaresXParameter, meanSquareErrorParameter);
    }

    @Override
    public ScalarFunction interceptStandardError(final ScalarFunction meanSquaredErrorFunction, final ScalarFunction meanXFunction, final ScalarFunction sumOfSquaresXFunction, final Scalar count) {
        ScalarFunctionParameter meanSquaredErrorParameter = getModelProvider().getParameterFactory().createParameter(meanSquaredErrorFunction.getName(), meanSquaredErrorFunction);
        ScalarFunctionParameter meanXParameter = getModelProvider().getParameterFactory().createParameter(meanXFunction.getName(), NameConstants.X_VARIABLE, meanXFunction);
        ScalarFunctionParameter sumOfSquaresXParameter = getModelProvider().getParameterFactory().createParameter(sumOfSquaresXFunction.getName(), NameConstants.X_VARIABLE, sumOfSquaresXFunction);
        ScalarParameter countParameter = getModelProvider().getParameterFactory().createParameter(NameConstants.COUNT, count);

        return new InterceptStandardErrorFunction(meanSquaredErrorParameter, meanXParameter, sumOfSquaresXParameter, countParameter);
    }

    @Override
    public ScalarFunction slopeTStatistic(final ScalarFunction slopeFunction, final ScalarFunction slopeStandardError) {
        ScalarFunctionParameter slopeParameter = getModelProvider().getParameterFactory().createParameter(slopeFunction);
        ScalarFunctionParameter slopeSEParameter = getModelProvider().getParameterFactory().createParameter(slopeStandardError);

        return new TStatisticSlopeFunction(slopeParameter, slopeSEParameter);
    }

    @Override
    public ScalarFunction slopePValue(final int degreesOfFreedom, final ScalarFunction slopeTStatistic) {
        ScalarParameter dfParam = getModelProvider().getParameterFactory().createParameter(NameConstants.DEGREES_OF_FREEDOM, Scalar.of(degreesOfFreedom));
        ScalarFunctionParameter slopeTParam = getModelProvider().getParameterFactory().createParameter(slopeTStatistic);
        return new PSlopeFunction(dfParam, slopeTParam);
    }

    @Override
    public LinearRegressionModel linearRegression(final Matrix matrix) {
        MatrixParameter matrixParameter = getModelProvider().getParameterFactory().createParameter(NameConstants.MATRIX, matrix);
        return new LinearRegressionModelImpl(matrixParameter);
    }

    @Override
    public LinearRegressionModel linearRegression(final Matrix matrix, final int setX, final int setY) {
        MatrixParameter matrixParameter = getModelProvider().getParameterFactory().createParameter(NameConstants.MATRIX, matrix);
        ScalarParameter columnXParameter = getModelProvider().getParameterFactory().createParameter(NameConstants.COLUMN, NameConstants.X_VARIABLE, setX);
        ScalarParameter columnYParameter = getModelProvider().getParameterFactory().createParameter(NameConstants.COLUMN, NameConstants.Y_VARIABLE, setY);
        return new LinearRegressionModelImpl(matrixParameter, columnXParameter, columnYParameter);
    }

    @Override
    public LinearRegressionModel linearRegression(final ScalarVector vectorX, final ScalarVector vectorY) {
        ScalarVectorParameter vectorXParameter = getModelProvider().getParameterFactory().createParameter(NameConstants.VECTOR, NameConstants.X_VARIABLE, vectorX);
        ScalarVectorParameter vectorYParameter = getModelProvider().getParameterFactory().createParameter(NameConstants.VECTOR, NameConstants.Y_VARIABLE, vectorY);
        return new LinearRegressionModelImpl(vectorXParameter, vectorYParameter);
    }
}
