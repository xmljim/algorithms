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
import io.xmljim.algorithms.model.ScalarParameter;
import io.xmljim.algorithms.model.util.Scalar;

/**
 * Compute the standard error of the intercept
 * <p>
 *     <pre>
 *         s<sub>&#x03B1;</sub> = sqrt(MSE * ( 1/n + (x&#x0304;<sup>2</sup> / SS<sub>x</sub>)))
 *
 *         Where:
 *         MSE (mean squared error) = &#x03A3;(&#x177;<sub>i</sub> - y<sub>i</sub>)<sup>2</sup> / (n - 2)
 *         SS<sub>x</sub> (sum of squares of x) = &#x03A3;(x<sub>i</sub> - x&#x0304;)<sup>2</sup>
 *     </pre>
 * </p>
 */
class InterceptStandardErrorFunction extends AbstractScalarFunction {
    private Scalar result;

    public InterceptStandardErrorFunction(ScalarFunctionParameter meanSquaredErrorFunction, ScalarFunctionParameter meanXFunction, ScalarFunctionParameter sumOfSquaresX,
                                         ScalarParameter countParameter) {
        super(StatisticsFunctions.INTERCEPT_STD_ERROR, meanSquaredErrorFunction, meanXFunction, sumOfSquaresX, countParameter);
    }

    Scalar getResult() {
        ScalarFunction mseFx = getValue(StatisticsFunctions.MSE.getName());
        ScalarFunction meanXFx = getValue(StatisticsFunctions.MEAN.getName(), NameConstants.X_VARIABLE);
        ScalarFunction sumSquaresXFx = getValue(StatisticsFunctions.SST.getName(), NameConstants.X_VARIABLE);
        Scalar countVar = getValue(NameConstants.COUNT);

        double mse = mseFx.compute().asDouble();
        double meanX = meanXFx.compute().asDouble();
        double sumSquaresX = sumSquaresXFx.compute().asDouble();
        int count = countVar.asInt();

        double interceptVariance = mse * ((1 / count) +  (Math.pow(meanX, 2) / sumSquaresX));
        double seIntercept = Math.sqrt(interceptVariance);
        return Scalar.of(seIntercept);
    }

    @Override
    public Scalar compute() {
        if (result == null) {
            result = getResult();
        }

        return result;
    }
}
