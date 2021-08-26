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

import io.xmljim.algorithms.functions.impl.AbstractScalarFunction;
import io.xmljim.algorithms.functions.impl.provider.NameConstants;
import io.xmljim.algorithms.model.ScalarFunction;
import io.xmljim.algorithms.model.ScalarFunctionParameter;
import io.xmljim.algorithms.model.util.Scalar;

/**
 * Function for calculating the standard error of the slope (estimate).
 *
 * <p>
 *    <pre>
 *        s<sub>&#x03B2;</sub> = sqrt(MSE) / sqrt(SS<sub>x</sub>)
 *
 *        Where:
 *
 *        MSE (mean squared error) = &#x03A3;(&#x177;<sub>i</sub> - y<sub>i</sub>)<sup>2</sup> / (n - 2)
 *        SS<sub>x</sub> (sum of squares of x) = &#x03A3;(x<sub>i</sub> - x&#x0304;)<sup>2</sup>
 *    </pre>
 *
 * </p>
 */
class SlopeStandardErrorFunction extends AbstractScalarFunction {
    private Scalar result;

    public SlopeStandardErrorFunction(ScalarFunctionParameter sumOfSquaresX, ScalarFunctionParameter meanSquaredErrorY) {
        super(StatisticsFunctions.SLOPE_STD_ERROR, sumOfSquaresX, meanSquaredErrorY);
    }

    private Scalar computeSlopeStandardError() {
        ScalarFunction sumOfSquaresXFx = getValue(StatisticsFunctions.SST.getName(), NameConstants.X_VARIABLE);
        ScalarFunction meanSquareErrorYFx = getValue(StatisticsFunctions.MSE.getName());
        double sumOfSquaresX = sumOfSquaresXFx.compute().asDouble();
        double meanSquareErrorY = meanSquareErrorYFx.compute().asDouble();

        return Scalar.of(Math.sqrt(meanSquareErrorY) / Math.sqrt(sumOfSquaresX));
        /*
        ScalarVector vectorX = getValue(NameConstants.VECTOR, NameConstants.X_VARIABLE);
        ScalarVector vectorY = getValue(NameConstants.VECTOR, NameConstants.Y_VARIABLE);
        double meanX = getValue(StatisticsFunctions.MEAN.getName(), NameConstants.X_VARIABLE);
        double slope = getValue(StatisticsFunctions.SLOPE.getName());
        double intercept = getValue(StatisticsFunctions.INTERCEPT.getName());

        double yhatSumSquares = IntStream.range(0, vectorX.length())
                .mapToDouble(i -> {
                    double yHat = slope * vectorX.get(i).asDouble() + intercept;
                    return Math.pow(yHat - vectorY.get(i).asDouble(), 2);
                }).sum();

        double xSumSquares = vectorX.stream().mapToDouble(x -> {
            return Math.pow(x.asDouble() - meanX, 2);
        }).sum();


        int degreesOfFreedom = vectorX.length() - 2;

        return Scalar.of(Math.sqrt(yhatSumSquares / degreesOfFreedom) / Math.sqrt(xSumSquares));

         */


    }

    @Override
    public Scalar compute() {
        if (result == null) {
            result = computeSlopeStandardError();
        }

        return result;
    }
}
