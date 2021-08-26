
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
import io.xmljim.algorithms.model.*;
import io.xmljim.algorithms.model.util.Scalar;

class StandardDeviationFunction extends AbstractScalarFunction {
    Scalar result;

    StandardDeviationFunction(ScalarVectorParameter scalarVectorParameter) {
        super(StatisticsFunctions.STANDARD_DEVIATION, scalarVectorParameter);
    }

    StandardDeviationFunction(ScalarVectorParameter scalarVectorParameter, String variable) {
        super(StatisticsFunctions.STANDARD_DEVIATION, variable, scalarVectorParameter);
    }

    StandardDeviationFunction(ScalarFunctionParameter varianceFunctionParameter) {
        super(StatisticsFunctions.STANDARD_DEVIATION, varianceFunctionParameter);
    }

    StandardDeviationFunction(ScalarFunctionParameter varianceFunctionParameter, String variable) {
        super(StatisticsFunctions.STANDARD_DEVIATION, variable, varianceFunctionParameter);
    }

    Scalar computeStandardDeviation() {
        double variance = getVarianceFunction().compute().asDouble();
        return Scalar.of(Math.sqrt(variance));
    }

    ScalarFunction getVarianceFunction() {
        ScalarFunction varianceFunction;

        if (getParameter(0).getParameterType() == ParameterTypes.SCALAR_FUNCTION) {
            varianceFunction = getValue(0);
        } else {
            ScalarVector vector = getValue(0);
            varianceFunction = getFunctionProvider().getStatistics().variance(vector, getVariable());
        }

        return varianceFunction;
    }



    @Override
    public Scalar compute() {
        if (result == null) {
            result = computeStandardDeviation();
        }

        return result;
    }
}
