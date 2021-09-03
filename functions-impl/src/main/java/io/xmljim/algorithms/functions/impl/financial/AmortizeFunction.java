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

import io.xmljim.algorithms.functions.financial.PaymentFrequency;
import io.xmljim.algorithms.functions.impl.AbstractScalarFunction;
import io.xmljim.algorithms.functions.impl.provider.NameConstants;
import io.xmljim.algorithms.model.Parameter;
import io.xmljim.algorithms.model.ScalarParameter;
import io.xmljim.algorithms.model.util.Scalar;

class AmortizeFunction extends AbstractScalarFunction {
    private Scalar result;

    //final double balance, final double interest, PaymentFrequency frequency, final int durationYear, final double inflation, final double retirementSTartYear

    public AmortizeFunction(ScalarParameter balance, ScalarParameter interest, Parameter<PaymentFrequency> frequencyParameter, ScalarParameter duration) {
        super(FinancialFunctions.AMORTIZE, balance, interest, frequencyParameter, duration);
    }

    private Scalar getResult() {
        double amount = getDouble(NameConstants.FIN_CURRENT_401K_BALANCE);
        double interest = getDouble(NameConstants.FIN_POST_RETIRE_INTEREST);
        PaymentFrequency frequency = getValue(NameConstants.FIN_DISTRIBUTION_FREQUENCY);
        int duration = getInteger(NameConstants.FIN_RETIREMENT_DURATION);

        double intervalRate = interest / frequency.getAnnualFrequency();
        int totalDistributions = duration * frequency.getAnnualFrequency();

        double value = amount / ((Math.pow(1 + intervalRate, totalDistributions) - 1) / (intervalRate * Math.pow(1 + intervalRate, totalDistributions)));

        return Scalar.of(value);
    }

    @Override
    public Scalar compute() {
        if (result == null) {
            result = getResult();
        }

        return result;
    }
}
