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
import io.xmljim.algorithms.functions.financial.PaymentFrequency;

class DistributionBalanceImpl extends AbstractRetirementBalance implements DistributionBalance {
    private final double annualDistributionAmount;
    private final double inflationRate;
    private PaymentFrequency paymentFrequency;
    private double periodicDistributionAmount;

    public DistributionBalanceImpl(final int year, final double balance, final double interest, final double weightedGrowthRate, final double distributionAmount,
                                   final double inflationRate) {
        super(year, balance, interest, weightedGrowthRate);
        this.annualDistributionAmount = distributionAmount;
        this.inflationRate = inflationRate;
    }

    @Override
    public double getAnnualDistributionAmount() {
        return annualDistributionAmount;
    }

    @Override
    public double getInflationRate() {
        return inflationRate;
    }

    @Override
    public PaymentFrequency getDistributionFrequency() {
        return paymentFrequency;
    }

    @Override
    public double getPeriodicDistributionAmount() {
        return periodicDistributionAmount;
    }

    @Override
    public String toString() {
        return "DistributionBalanceImpl{" +
                "annualDistributionAmount=" + annualDistributionAmount +
                ", inflationRate=" + inflationRate +
                ", balance=" + super.getBalance() +
                ", interest=" + super.getInterestAccrued() +
                ", year=" + super.getYear() +
                "} ";
    }
}
