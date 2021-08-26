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

package io.xmljim.algorithms.functions.statistics;

import io.xmljim.algorithms.model.Model;
import io.xmljim.algorithms.model.ScalarCoefficient;
import io.xmljim.algorithms.model.ScalarFunction;
import io.xmljim.algorithms.model.util.Scalar;

/**
 * A statistical model that solves for the linear relationship between two variables
 * with the equation:
 * <p>
 *     <pre>y = &#x03B1;x + &#x03B2</pre>
 * </p>
 * <p>Where:</p>
 * <p>y is the predicted value</p>
 * <p>x is the observed independent variable value</p>
 * <p>&#x03B1; is the slope (increase/decrease) of the relationship between x and y</p>
 * <p>&#x03B2; is the y-intercept constant</p>
 */
public interface LinearRegressionModel extends Model {

    /**
     * Return the slope coefficient (&#x03B2) computed from the function
     * {@link Statistics#slope(ScalarFunction, ScalarFunction)}
     * @return the slope coefficient
     */
    ScalarCoefficient getSlopeCoefficient();

    ScalarCoefficient getInterceptCoefficient();

    ScalarCoefficient getRSquaredCoefficient();

    ScalarCoefficient getSlopeStandardErrorCoefficient();

    ScalarCoefficient getInterceptStandardErrorCoefficient();

    ScalarCoefficient getSlopeTStatisticCoefficient();

    ScalarCoefficient getPSlopeCoefficient();

    default Scalar getSlope() {
        ScalarCoefficient slopeCoefficient = getSlopeCoefficient();
        if (slopeCoefficient != null) {
            return slopeCoefficient.getValue();
        }
        return null;
    }

    default Scalar getIntercept() {
        ScalarCoefficient interceptCoefficient = getInterceptCoefficient();
        if (interceptCoefficient != null) {
            return interceptCoefficient.getValue();
        }
        return null;
    }

    default Scalar getRSquared() {
        ScalarCoefficient rsquaredCoefficient = getRSquaredCoefficient();
        if (rsquaredCoefficient != null) {
            return rsquaredCoefficient.getValue();
        }

        return null;
    }

    default Scalar getSlopeStandardError() {
        ScalarCoefficient slopeSECoefficient = getSlopeStandardErrorCoefficient();
        if (slopeSECoefficient != null) {
            return slopeSECoefficient.getValue();
        }
        return null;
    }

    default Scalar getInterceptStandardError() {
        ScalarCoefficient interceptSECoefficient = getInterceptStandardErrorCoefficient();
        if (interceptSECoefficient != null) {
            return interceptSECoefficient.getValue();
        }
        return null;
    }

    default Scalar getSlopeTStatistic() {
        ScalarCoefficient slopeTCoefficient = getSlopeTStatisticCoefficient();
        if (slopeTCoefficient != null) {
            return slopeTCoefficient.getValue();
        }
        return null;
    }

    default Scalar getPSlopeValue() {
        ScalarCoefficient pValueCoefficient = getPSlopeCoefficient();
        if (pValueCoefficient != null) {
            return pValueCoefficient.getValue();
        }
        return null;
    }

    default Scalar predict(Scalar independentValue) {
        return Scalar.of(independentValue.asDouble() * getSlope().asDouble() + getIntercept().asDouble());
    }
}
