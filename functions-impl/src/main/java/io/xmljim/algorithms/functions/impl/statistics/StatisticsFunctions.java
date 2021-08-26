
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

enum StatisticsFunctions implements StatisticsType {
    SUM("sum", "SUM"),
    MEAN("mean", "MEAN"),
    MEDIAN("median", "MEDIAN"),
    VARIANCE("variance", "VAR"),
    STANDARD_DEVIATION("standardDeviation", "STDDEV"),
    COVARIANCE("covariance", "COV"),
    SLOPE("slope", "B"),
    INTERCEPT("intercept", "a"),
    SSE("residualsumOfSquares", "SSR"),
    SST("totalSumOfSquares", "SST"),
    R_SQUARED("rSquared", "R-SQUARED"),
    SLOPE_STD_ERROR("slopeStandardError", "SE Slope"),
    INTERCEPT_STD_ERROR("interceptStandardError", "SE Intercept"),
    MSE("meanSquaredError", "MSE"),
    LINEAR_REGRESSION_MODEL("linearRegressionModel", "LRM"),
    T_SLOPE("TSlope", "t (slope)"),
    T_INTERCEPT("TIntercept", "t (intercept)"),
    P_SLOPE("PSlope", "p (slope)")
    ;

    private final String name;
    private final String label;

    StatisticsFunctions(String name, String label) {
        this.name = name;
        this.label = label;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
