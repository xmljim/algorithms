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

package io.xmljim.algorithms.functions.impl.financial;

import io.xmljim.algorithms.functions.impl.AbstractScalarFunction;
import io.xmljim.algorithms.functions.impl.provider.NameConstants;
import io.xmljim.algorithms.model.ScalarParameter;
import io.xmljim.algorithms.model.util.Scalar;

class WeightedGrowthFunction extends AbstractScalarFunction {
    public WeightedGrowthFunction(ScalarParameter stockGrowthRateParam, ScalarParameter treasuryYieldParam, ScalarParameter proportionStocks) {
        super(FinancialFunctions.WEIGHTED_GROWTH, stockGrowthRateParam, treasuryYieldParam, proportionStocks);
    }

    @Override
    public Scalar compute() {
        double stockRate = ((Scalar)getValue(NameConstants.FIN_STOCK_GROWTH_RATE)).asDouble();
        double treasuryYield = ((Scalar)getValue(NameConstants.FIN_TREASURY_YIELD)).asDouble();
        double investmentRatio = ((Scalar)getValue(NameConstants.FIN_INVESTMENT_RATIO)).asDouble();

        return Scalar.of((stockRate * investmentRatio) + (treasuryYield * (1 - investmentRatio)));
    }
}
