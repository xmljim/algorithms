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

import io.xmljim.algorithms.functions.financial.ContributionBalance;
import io.xmljim.algorithms.functions.financial.PaymentFrequency;

class ContributionBalanceImpl extends AbstractRetirementBalance implements ContributionBalance {
    private final double selfContributionPct;
    private final double employerContributionPct;
    private final double currentSalary;
    private final double colaPct;
    private final double estimatedSelfContribution;
    private final double estimatedEmployerContribution;
    private final PaymentFrequency contributionFrequency;

    public ContributionBalanceImpl(final int year, final double balance, final double interest, final double weightedGrowthRate,
                                   final double selfContributionPct, final double employerContributionPct, final double currentSalary,
                                   final double colaPct, final double estimatedSelfContribution, final double estimatedEmployerContribution,
                                   final PaymentFrequency contributionFrequency) {

        super("contribution", year, balance, interest, weightedGrowthRate);
        this.selfContributionPct = selfContributionPct;
        this.employerContributionPct = employerContributionPct;
        this.currentSalary = currentSalary;
        this.colaPct = colaPct;
        this.estimatedSelfContribution = estimatedSelfContribution;
        this.estimatedEmployerContribution = estimatedEmployerContribution;
        this.contributionFrequency = contributionFrequency;
    }

    @Override
    public double getSelfContributionPct() {
        return selfContributionPct;
    }

    @Override
    public double getEmployerContributionPct() {
        return employerContributionPct;
    }

    @Override
    public double getCurrentSalary() {
        return currentSalary;
    }

    @Override
    public double getColaPct() {
        return colaPct;
    }

    @Override
    public double getEstimatedSelfContribution() {
        return estimatedSelfContribution;
    }

    @Override
    public double getEstimatedEmployerContribution() {
        return estimatedEmployerContribution;
    }

    @Override
    public PaymentFrequency getContributionFrequency() {
        return contributionFrequency;
    }

    @Override
    public String toString() {
        return "ContributionBalanceImpl{" +
                "selfContributionPct=" + selfContributionPct +
                ", employerContributionPct=" + employerContributionPct +
                ", currentSalary=" + currentSalary +
                ", colaPct=" + colaPct +
                ", estimatedSelfContribution=" + estimatedSelfContribution +
                ", estimatedEmployerContribution=" + estimatedEmployerContribution +
                ", estimatedRetirementBalance=" + super.getBalance() +
                ", interest=" + super.getInterestAccrued() +
                ", year=" + super.getYear() +
                '}';
    }
}
