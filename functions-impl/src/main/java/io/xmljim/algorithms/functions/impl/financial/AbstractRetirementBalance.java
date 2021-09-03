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

import io.xmljim.algorithms.functions.financial.RetirementBalance;

abstract class AbstractRetirementBalance implements RetirementBalance {
    private final int year;
    private double balance;
    private double interest;
    private final double weightedGrowthRate;

    public AbstractRetirementBalance(final int year, final double balance, final double interest, final double weightedGrowthRate) {
        this.year = year;
        this.balance = balance;
        this.interest = interest;
        this.weightedGrowthRate = weightedGrowthRate;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public double getInterestAccrued() {
        return interest;
    }

    @Override
    public double getWeightedGrowthRate() {
        return weightedGrowthRate;
    }

    protected void updateBalanceWithInterest(double interest) {
        this.interest = interest;
        balance += interest;
    }

    @Override
    public String toString() {
        return "AbstractRetirementBalance{" +
                "year=" + year +
                ", balance=" + balance +
                ", interest=" + interest +
                ", weightedGrowthRate=" + weightedGrowthRate +
                '}';
    }
}
