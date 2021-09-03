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

import io.xmljim.algorithms.functions.financial.DistributionBalance;
import io.xmljim.algorithms.functions.impl.AbstractFunction;
import io.xmljim.algorithms.functions.impl.provider.NameConstants;
import io.xmljim.algorithms.model.ScalarParameter;

    /*
    - current balance (b)
    - annualized base distribution value (from amortize) (dr)
    - inflation rate (i)
    - retirementYear (ry)
    - currentYear (cy)
    - post-retirement interest (it)


    b' = (b - (dr * (1 + i)^cy-ry)) * (1 + it)
     */

class DistributionBalanceFunction extends AbstractFunction<DistributionBalance> {
    private DistributionBalance result;

    /**
     *
     * @param currentBalance
     * @param amortizedValue
     * @param inflation
     * @param retirementInterest
     * @param retirementYear
     * @param currentYear
     */
    public DistributionBalanceFunction(ScalarParameter currentBalance, ScalarParameter amortizedValue, ScalarParameter inflation,
                                       ScalarParameter retirementInterest, ScalarParameter retirementYear, ScalarParameter currentYear) {

        super(FinancialFunctions.DISTRIBUTION_BALANCE_FUNCTION, currentBalance, amortizedValue, inflation, retirementInterest, retirementYear, currentYear);
    }

    private DistributionBalance getResult() {
        double currentBalance = getDouble(NameConstants.FIN_CURRENT_401K_BALANCE);
        double annualizedBase = getDouble(NameConstants.FIN_ANNUAL_DISTRIBUTION);
        double inflation = getDouble(NameConstants.FIN_INFLATION_RATE);
        double retirementInterestRate = getDouble(NameConstants.FIN_WEIGHTED_GROWTH_RATE);
        int retirementYear = getInteger(NameConstants.FIN_RETIREMENT_START_YEAR);
        int currentYear = getInteger(NameConstants.FIN_CURRENT_YEAR);




        double estDistribution = annualizedBase * Math.pow(1 + inflation, currentYear - retirementYear);

        double realDistribution = 0.0;
        double newBalance = 0.0;
        double interest = 0.0;
        if (estDistribution < currentBalance) {
            interest = (currentBalance - estDistribution) * retirementInterestRate;
            realDistribution = estDistribution;

        } else {
            realDistribution = currentBalance;
        }

        newBalance = (currentBalance - realDistribution) + interest;

        return new DistributionBalanceImpl(currentYear, newBalance, interest, retirementInterestRate, realDistribution, inflation);
    }


    @Override
    public DistributionBalance compute() {
        if (result == null) {
            result = getResult();
        }

        return result;
    }
}
