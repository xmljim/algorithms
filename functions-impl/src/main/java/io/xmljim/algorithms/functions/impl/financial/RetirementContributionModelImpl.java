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
import io.xmljim.algorithms.functions.financial.RetirementContributionModel;
import io.xmljim.algorithms.functions.impl.AbstractModel;
import io.xmljim.algorithms.functions.impl.provider.NameConstants;
import io.xmljim.algorithms.model.*;
import io.xmljim.algorithms.model.util.Scalar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

class RetirementContributionModelImpl extends AbstractModel implements RetirementContributionModel {


    public RetirementContributionModelImpl(ScalarParameter currentAge, ScalarParameter retirementAge, ScalarParameter currentSalary,
                                           ScalarParameter employeeContribution, ScalarParameter employerContribution,
                                           ScalarParameter currentBalance, ScalarParameter colaPct, ScalarParameter weightedGrowthRate,
                                           Parameter<PaymentFrequency> contributionFrequency) {

        super(FinancialFunctions.RETIREMENT_CONTRIBUTION_MODEL.getName(), currentAge, retirementAge, currentSalary, employeeContribution,
                employerContribution, currentBalance, colaPct, weightedGrowthRate, contributionFrequency);

    }

    @Override
    @SuppressWarnings("unchecked")
    public Coefficient<List<ContributionBalance>> getContributionTimelineCoefficient() {
        Coefficient<?> coeff = getCoefficient(FinancialFunctions.CONTRIBUTION_SCHEDULE.getName());
        return (Coefficient<List<ContributionBalance>>) coeff;
    }

    @Override
    public ScalarCoefficient getBalanceCoefficient() {
        Coefficient<?> coeff = getCoefficient(FinancialFunctions.CONTRIBUTION_BALANCE.getName());
        return (ScalarCoefficient) coeff;
    }

    @Override
    public ScalarCoefficient getTotalInterestCoefficient() {
        Coefficient<?> coeff = getCoefficient(FinancialFunctions.TOTAL_INTEREST.getName());
        return (ScalarCoefficient) coeff;
    }

    @Override
    public ScalarCoefficient getTotalSelfContributionCoefficient() {
        Coefficient<?> coeff = getCoefficient(FinancialFunctions.TOTAL_SELF_CONTRIBUTION.getName());
        return (ScalarCoefficient) coeff;
    }

    @Override
    public ScalarCoefficient getTotalEmployerContributionCoefficient() {
        Coefficient<?> coeff = getCoefficient(FinancialFunctions.TOTAL_EMPL_CONTRIBUTION.getName());
        return (ScalarCoefficient) coeff;
    }

    @Override
    public ScalarCoefficient getLastSalaryCoefficient() {
        Coefficient<?> coeff = getCoefficient(FinancialFunctions.LAST_SALARY.getName());
        return (ScalarCoefficient) coeff;
    }

    @Override
    public void solve() {
        List<ContributionBalance> balanceSchedule = new ArrayList<>();

        int currentAge = getInteger(NameConstants.FIN_AGE);
        int retirementAge = getInteger(NameConstants.FIN_RETIREMENT_AGE);
        double currentSalary = getDouble(NameConstants.FIN_CURRENT_SALARY);
        double selfContributionPct = getDouble(NameConstants.FIN_EMPLOYEE_CONTRIB_PCT);
        double emplContributionPct = getDouble(NameConstants.FIN_EMPLOYER_CONTRIB_PCT);
        double currentBalance = getDouble(NameConstants.FIN_CURRENT_401K_BALANCE);
        double colaPct = getDouble(NameConstants.FIN_COLA_PCT);
        double weightedGrowthRate = getDouble(NameConstants.FIN_WEIGHTED_GROWTH_RATE);
        PaymentFrequency contributionFrequency = getValue(NameConstants.FIN_CONTRIBUTION_FREQUENCY);

        int currentYear = LocalDate.now().getYear();
        int yearCount = retirementAge - currentAge;

        Function<ContributionBalance> contributionBalance =
                getFunctionProvider().getFinancial().contributionBalance(currentSalary, colaPct, currentBalance, selfContributionPct, emplContributionPct,
                        weightedGrowthRate, contributionFrequency, currentYear, currentYear);

        balanceSchedule.add(contributionBalance.compute());

        final AtomicReference<ContributionBalance> currentRetirementBalance = new AtomicReference<>(contributionBalance.compute());
        final AtomicReference<Double> totalInterest = new AtomicReference<>(0.0);
        final AtomicReference<Double> totalSelfContribution = new AtomicReference<>(0.0);
        final AtomicReference<Double> totalEmplContribution = new AtomicReference<>(0.0);

        IntStream.range(0, yearCount).forEach(i -> {
            Function<ContributionBalance> newBalanceFx =
                    getFunctionProvider().getFinancial().contributionBalance(currentRetirementBalance.get(), currentYear + i + 1);
            ContributionBalance newBalance = newBalanceFx.compute();
            balanceSchedule.add(newBalance);
            currentRetirementBalance.set(newBalance);
            totalInterest.getAndUpdate(interest -> interest += newBalance.getInterestAccrued());
            totalSelfContribution.getAndUpdate(selfContrib -> selfContrib += newBalance.getEstimatedSelfContribution());
            totalEmplContribution.getAndUpdate(emplContrib -> emplContrib += newBalance.getEstimatedEmployerContribution());
        });

        setCoefficient(FinancialFunctions.CONTRIBUTION_SCHEDULE, balanceSchedule);
        setCoefficient(FinancialFunctions.CONTRIBUTION_BALANCE, Scalar.of(currentRetirementBalance.get().getBalance()));
        setCoefficient(FinancialFunctions.TOTAL_SELF_CONTRIBUTION, Scalar.of(totalSelfContribution.get()));
        setCoefficient(FinancialFunctions.TOTAL_EMPL_CONTRIBUTION, Scalar.of(totalEmplContribution.get()));
        setCoefficient(FinancialFunctions.TOTAL_INTEREST, Scalar.of(totalInterest.get()));
        setCoefficient(FinancialFunctions.LAST_SALARY, Scalar.of(currentRetirementBalance.get().getCurrentSalary()));

    }
}
