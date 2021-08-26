
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
import io.xmljim.algorithms.model.*;
import io.xmljim.algorithms.model.util.Scalar;

import java.util.Optional;

class VarianceFunction extends AbstractScalarFunction {
    private Scalar result;

    public VarianceFunction(ScalarVectorParameter vectorParameter) {
        super(StatisticsFunctions.VARIANCE, vectorParameter);
    }

    public VarianceFunction(ScalarVectorParameter vectorParameter, String variable) {
        super(StatisticsFunctions.VARIANCE, variable, vectorParameter);
    }

    public VarianceFunction(ScalarVectorParameter vectorParameter, ScalarFunctionParameter meanFunction) {
        super(StatisticsFunctions.VARIANCE, vectorParameter, meanFunction);
    }

    public VarianceFunction(ScalarVectorParameter vectorParameter, ScalarFunctionParameter meanFunction, String variable) {
        super(StatisticsFunctions.VARIANCE, variable, vectorParameter, meanFunction);
    }

    Scalar computeVariance() {
        ScalarVectorParameter vectorParameter = (ScalarVectorParameter) find(parameterType(ParameterTypes.SCALAR_VECTOR))
                .orElseThrow(() -> new FunctionException("Missing ScalarVector parameter"));

        ScalarVector vector = vectorParameter.getValue();
        ScalarFunction meanFunction = getOrCreateMeanFunction(vector);
        double mean = meanFunction.compute().asDouble();

        double variance = vector.stream().mapToDouble(e -> Math.pow(e.asDouble() - mean, 2)).sum() / (vector.length() - 1);
        return Scalar.of(variance);
    }

    private ScalarFunction getOrCreateMeanFunction(ScalarVector vector) {
        ScalarFunction meanFunction;

        Optional<Parameter<?>> meanFunctionParameter = find(parameterNameAndType(StatisticsFunctions.MEAN.getName(), ParameterTypes.SCALAR_FUNCTION));
        if (meanFunctionParameter.isPresent()) {
            meanFunction = (ScalarFunction) meanFunctionParameter.get().getValue();
        } else {
            meanFunction = getFunctionProvider().getStatistics().mean(vector, getVariable());
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
