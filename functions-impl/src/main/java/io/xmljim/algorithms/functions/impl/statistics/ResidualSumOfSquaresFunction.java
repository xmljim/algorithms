
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
import io.xmljim.algorithms.model.ScalarVector;
import io.xmljim.algorithms.model.ScalarVectorParameter;
import io.xmljim.algorithms.model.util.Scalar;

import java.util.stream.IntStream;

/**
 * Calculate the residual sum of squares, also know as the Error Sum of Squares (SSE)
 */
class ResidualSumOfSquaresFunction extends AbstractScalarFunction {
    Scalar result;

    public ResidualSumOfSquaresFunction(ScalarVectorParameter vectorX, ScalarVectorParameter vectorY, ScalarFunctionParameter slopeFunction, ScalarFunctionParameter interceptFunction) {
        super(StatisticsFunctions.SSE, vectorX, vectorY, slopeFunction, interceptFunction);
    }



    /**
     * σ2ŷ = Σ(ŷi - yi)2
     *
     * Where:
     *
     * ŷi = βxi + α
     * @return
     */
    Scalar computeResidualVariance() {
        ScalarVector vectorX = getValue(NameConstants.VECTOR, NameConstants.X_VARIABLE);
        ScalarVector vectorY = getValue(NameConstants.VECTOR, NameConstants.Y_VARIABLE);
        ScalarFunction slopeFx = getValue(StatisticsFunctions.SLOPE.getName());
        ScalarFunction interceptFx = getValue(StatisticsFunctions.INTERCEPT.getName());

        double slope = slopeFx.compute().asDouble();
        double intercept = interceptFx.compute().asDouble();

        double residualVariance = IntStream.range(0, vectorX.length())
                .mapToDouble(i -> {
                    double yHat = slope * vectorX.get(i).asDouble() + intercept;
                    return Math.pow(yHat - vectorY.get(i).asDouble(), 2);
                }).sum();

        return Scalar.of(residualVariance);
    }

    @Override
    public Scalar compute() {
        if (result == null) {
            result = computeResidualVariance();
        }
        return result;
    }
}
