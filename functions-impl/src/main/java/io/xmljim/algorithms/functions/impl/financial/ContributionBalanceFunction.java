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
import io.xmljim.algorithms.functions.impl.AbstractFunction;
import io.xmljim.algorithms.functions.impl.provider.NameConstants;
import io.xmljim.algorithms.model.Parameter;
import io.xmljim.algorithms.model.ScalarParameter;
import io.xmljim.algorithms.model.util.Scalar;

import java.time.LocalDate;

class ContributionBalanceFunction extends AbstractFunction<ContributionBalance> {

    private ContributionBalance result;

    public ContributionBalanceFunction(ScalarParameter currentSalary, ScalarParameter colaPct, ScalarParameter currentRetirementBalance,
                                       ScalarParameter employeeContribution, ScalarParameter employerContribution, ScalarParameter weightedGrowth,
                                       Parameter<PaymentFrequency> contributionFrequency, ScalarParameter currentYear, ScalarParameter endYear) {

        super(FinancialFunctions.CONTRIBUTION_BALANCE_FUNCTION, currentSalary, colaPct, currentRetirementBalance,
                employeeContribution, employerContribution, weightedGrowth, contributionFrequency, currentYear, endYear);

    }


    public ContributionBalanceFunction(Parameter<ContributionBalance> contributionBalanceParameter, ScalarParameter forYear) {
        super(FinancialFunctions.CONTRIBUTION_BALANCE_FUNCTION, contributionBalanceParameter, forYear);
        this.result = computeFromPreviousBalance(contributionBalanceParameter.getValue(), forYear.getValue());
    }

    private ContributionBalance createFromParameters() {
        int currentYear = getInteger(NameConstants.FIN_CURRENT_YEAR);
        double currentBalance = getDouble(NameConstants.FIN_CURRENT_401K_BALANCE);
        double interest = 0.0; //no interest computed yet
        double weightedGrowthRate = getDouble(NameConstants.FIN_WEIGHTED_GROWTH_RATE);
        double selfContributionPct = getDouble(NameConstants.FIN_EMPLOYEE_CONTRIB_PCT);
        double emplContributionPct = getDouble(NameConstants.FIN_EMPLOYER_CONTRIB_PCT);
        double currentSalary = getDouble(NameConstants.FIN_CURRENT_SALARY);
        double colaPct = getDouble(NameConstants.FIN_COLA_PCT);
        PaymentFrequency contributionFrequency = getValue(NameConstants.FIN_CONTRIBUTION_FREQUENCY);
        double estimatedSelfContribution = 0.0;
        double estimatedEmplContribution = 0.0;

        return new ContributionBalanceImpl(currentYear, currentBalance, interest, weightedGrowthRate,
                selfContributionPct, emplContributionPct, currentSalary, colaPct, estimatedSelfContribution, estimatedEmplContribution, contributionFrequency);
    }

    private ContributionBalance computeFromPreviousBalance(ContributionBalance balance, Scalar endYearValue) {
        ContributionBalance estimatedBalance;

        if (LocalDate.now().getYear() == endYearValue.asInt()) {
            estimatedBalance = preProcessCurrentContribution(balance);
        } else {
            estimatedBalance = incrementBalance(balance);
        }

        return estimatedBalance;
    }

    private ContributionBalance preProcessCurrentContribution(ContributionBalance contributionBalance) {
        double periodicSelfContributionAmt = (contributionBalance.getCurrentSalary() * contributionBalance.getSelfContributionPct()) / contributionBalance.getContributionFrequency().getAnnualFrequency();
        double periodicEmplContributionAmt = (contributionBalance.getCurrentSalary() * contributionBalance.getEmployerContributionPct()) / contributionBalance.getContributionFrequency().getAnnualFrequency();

        LocalDate now = LocalDate.now();
        int dayOfYear = now.getDayOfYear();
        int yearLength = now.lengthOfYear();
        double pctYearRemaining = 1 - ((double)dayOfYear/(double)yearLength);
        double remainingContributions = Math.floor(pctYearRemaining * contributionBalance.getContributionFrequency().getAnnualFrequency());

        double newSelfContribution = remainingContributions * periodicSelfContributionAmt;
        double newEmplContribution = remainingContributions * periodicEmplContributionAmt;

        double newInterest = contributionBalance.getBalance() * (contributionBalance.getWeightedGrowthRate() * pctYearRemaining);
        double newBalance = contributionBalance.getBalance() + newInterest + newSelfContribution + newEmplContribution;
        return new ContributionBalanceImpl(contributionBalance.getYear(), newBalance, newInterest, contributionBalance.getWeightedGrowthRate(), contributionBalance.getSelfContributionPct(),
                contributionBalance.getEmployerContributionPct(), contributionBalance.getCurrentSalary(), contributionBalance.getColaPct(),
                newSelfContribution, newEmplContribution, contributionBalance.getContributionFrequency());
    }

    private ContributionBalance incrementBalance(ContributionBalance balance) {
        int endYear = balance.getYear() + 1;

        double estSalary = balance.getCurrentSalary() * (1 + balance.getColaPct());
        double estInterest = balance.getBalance() * balance.getWeightedGrowthRate();
        double estSelfContrib = estSalary * balance.getSelfContributionPct();
        double estEmpContrib = estSalary * balance.getEmployerContributionPct();
        double estBalance = balance.getBalance() + estInterest + estSelfContrib + estEmpContrib;

        return new ContributionBalanceImpl(endYear, estBalance, estInterest, balance.getWeightedGrowthRate(), balance.getSelfContributionPct(),
                balance.getEmployerContributionPct(), estSalary, balance.getColaPct(), estSelfContrib, estEmpContrib, balance.getContributionFrequency());
    }


    private ContributionBalance getResult() {
        ContributionBalance balance = createFromParameters();
        Scalar endYear = getValue(NameConstants.FIN_END_YEAR);

        return computeFromPreviousBalance(balance, endYear);
    }


    @Override
    public ContributionBalance compute() {
        //only want to compute once
        if (result == null) {
            result = getResult();
        }

        return result;
    }
}
