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
import org.apache.commons.math3.distribution.TDistribution;

public class PSlopeFunction extends AbstractScalarFunction {
    private Scalar result;

    public PSlopeFunction(ScalarParameter degreesOfFreedom, ScalarFunctionParameter tStatisticParameter) {
        super(StatisticsFunctions.P_SLOPE, degreesOfFreedom, tStatisticParameter);
    }

    Scalar getResult() {
        Scalar dfValue = getValue(NameConstants.DEGREES_OF_FREEDOM);
        ScalarFunction tStatFunction = getValue(StatisticsFunctions.T_SLOPE.getName());
        double tStat = tStatFunction.compute().asDouble();

        TDistribution tDistribution = new TDistribution(dfValue.asDouble());
        double cp = tDistribution.cumulativeProbability(Math.abs(tStat));
        double p = 2 * (1 - cp);
        return Scalar.of(p);
    }

    @Override
    public Scalar compute() {
        if (result == null) {
            result = getResult();
        }
        return result;
    }
}
