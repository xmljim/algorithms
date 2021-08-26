
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
import io.xmljim.algorithms.functions.impl.FunctionException;
import io.xmljim.algorithms.functions.impl.provider.NameConstants;
import io.xmljim.algorithms.model.*;
import io.xmljim.algorithms.model.util.Scalar;

import java.util.Optional;
import java.util.stream.IntStream;

class CovarianceFunction extends AbstractScalarFunction {
    Scalar result;

    public CovarianceFunction(ScalarVectorParameter vectorXParameter, ScalarVectorParameter vectorYParameter) {
        super(StatisticsFunctions.COVARIANCE, vectorXParameter, vectorYParameter);
    }

    public CovarianceFunction(ScalarVectorParameter vectorXParameter, ScalarVectorParameter vectorYParameter, ScalarFunctionParameter meanX, ScalarFunctionParameter meanY) {
        super(StatisticsFunctions.COVARIANCE, vectorXParameter, vectorYParameter, meanX, meanY);
    }

    Scalar computeVariance() {
        ScalarVector vectorX = getVector(NameConstants.X_VARIABLE);
        ScalarVector vectorY = getVector(NameConstants.Y_VARIABLE);
        ScalarFunction meanXFunction = getOrCreateMeanFunction(vectorX, NameConstants.X_VARIABLE);
        ScalarFunction meanYFunction = getOrCreateMeanFunction(vectorY, NameConstants.Y_VARIABLE);

        double meanX = meanXFunction.compute().asDouble();
        double meanY = meanYFunction.compute().asDouble();

        double covariance = IntStream.range(0, (int)vectorX.length())
                .mapToDouble(i ->  (vectorX.get(i).asDouble() - meanX) * (vectorY.get(i).asDouble() - meanY))
                .sum() / (vectorX.length() - 1);

        return Scalar.of(covariance);
    }

    ScalarVector getVector(String variable) {
        ScalarVectorParameter vectorParameter =
                (ScalarVectorParameter) find(parameterVariableAndType(variable, ParameterTypes.SCALAR_VECTOR))
                .orElseThrow(() -> new FunctionException("Expected to find ScalarVectorParameter for variable " + variable + ", but it was not present"));

        return vectorParameter.getValue();
    }

    ScalarFunction getOrCreateMeanFunction(ScalarVector vector, String variable) {
        ScalarFunction meanFunction;

        Optional<Parameter<?>> meanParam = find(parameterNameVariableType(StatisticsFunctions.MEAN.getName(), variable, ParameterTypes.SCALAR_FUNCTION));

        if (meanParam.isPresent()) {
            meanFunction = ((ScalarFunctionParameter)meanParam.get()).getValue();
        } else {
            meanFunction = getFunctionProvider().getStatistics().mean(vector, variable);
        }

        return meanFunction;
    }

    @Override
    public Scalar compute() {
        if (result == null) {
            result = computeVariance();
        }
        return result;
    }
}
