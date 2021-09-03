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

import io.xmljim.algorithms.functions.financial.*;
import io.xmljim.algorithms.functions.impl.AbstractModel;
import io.xmljim.algorithms.functions.impl.provider.NameConstants;
import io.xmljim.algorithms.model.Coefficient;
import io.xmljim.algorithms.model.Parameter;
import io.xmljim.algorithms.model.ScalarCoefficient;
import io.xmljim.algorithms.model.ScalarParameter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class RetirementModelImpl extends AbstractModel implements RetirementModel {

    public RetirementModelImpl(ScalarParameter currentAge, ScalarParameter retirementAge, ScalarParameter currentSalary, ScalarParameter currentRetirementBalance,
                               ScalarParameter selfContributionPct, ScalarParameter employerContributionPct, ScalarParameter colaPct, ScalarParameter weightedGrowthRate,
                               ScalarParameter postRetirementInterest, Parameter<PaymentFrequency> distributionFrequency, ScalarParameter inflationRate, ScalarParameter duration,
                               ScalarParameter annualizedLastSalaryPct) {

        super(FinancialFunctions.RETIREMENT_MODEL.getName(), currentAge, retirementAge, currentSalary, currentRetirementBalance, selfContributionPct, employerContributionPct, colaPct,
                weightedGrowthRate, postRetirementInterest, distributionFrequency, inflationRate, duration, annualizedLastSalaryPct);
    }


    @Override
    public Coefficient<RetirementContributionModel> getRetirementContributionModelCoefficient() {
        Coefficient<?> coeff = getCoefficient(FinancialFunctions.RETIREMENT_CONTRIBUTION_MODEL.getName());
        return (Coefficient<RetirementContributionModel>) coeff;
    }

    @Override
    public Coefficient<RetirementDistributionModel> getRetirementDistributionModelCoefficient() {
        Coefficient<?> coeff = getCoefficient(FinancialFunctions.RETIREMENT_DISTRIBUTION_MODEL.getName());
        return (Coefficient<RetirementDistributionModel>) coeff;
    }

    @Override
    public Coefficient<List<RetirementBalance>> getRetirementTimelineCoefficient() {
        Coefficient<?> coeff = getCoefficient(FinancialFunctions.RETIREMENT_SCHEDULE.getName());
        return (Coefficient<List<RetirementBalance>>) coeff;
    }

    @Override
    public ScalarCoefficient getRetirementIncomePctCoefficient() {
        Coefficient<?> coeff = getCoefficient(FinancialFunctions.RETIREMENT_INCOME_PCT.getName());
        return (ScalarCoefficient) coeff;
    }

    @Override
    public ScalarCoefficient getRetirementBalanceDepletionYearCoefficient() {
        Coefficient<?> coeff = getCoefficient(FinancialFunctions.RETIREMENT_DEPLETION_YEAR.getName());
        return (ScalarCoefficient) coeff;
    }

    @Override
    public void solve() {
        //This will hold retirement balance entries from both the contribution and distribution models...
        List<RetirementBalance> retirementBalanceList = new ArrayList<>();


        RetirementContributionModel contributionModel = getFunctionProvider().getFinancial().retirementContributionModel(
                getInteger(NameConstants.FIN_AGE), getInteger(NameConstants.FIN_RETIREMENT_AGE), getDouble(NameConstants.FIN_CURRENT_SALARY),
                getDouble(NameConstants.FIN_EMPLOYEE_CONTRIB_PCT), getDouble(NameConstants.FIN_EMPLOYER_CONTRIB_PCT), getDouble(NameConstants.FIN_CURRENT_401K_BALANCE),
                getDouble(NameConstants.FIN_COLA_PCT), getDouble(NameConstants.FIN_WEIGHTED_GROWTH_RATE));

        contributionModel.solve();

        retirementBalanceList.addAll(contributionModel.getContributionTimeline());

        double retirementBalance = contributionModel.getBalance().asDouble();
        int retirementYear = LocalDate.now().getYear() + (getInteger(NameConstants.FIN_RETIREMENT_AGE) - getInteger(NameConstants.FIN_AGE));
        int duration = getInteger(NameConstants.FIN_RETIREMENT_DURATION);
        double postRetirementInterest = getDouble(NameConstants.FIN_POST_RETIRE_INTEREST);
        double inflationRate = getDouble(NameConstants.FIN_INFLATION_RATE);
        PaymentFrequency distributionFrequency = getValue(NameConstants.FIN_DISTRIBUTION_FREQUENCY);
        double annualizedPct = getDouble(NameConstants.FIN_LAST_SALARY_PCT);
        double annualizedDistribution = contributionModel.getLastSalary().asDouble() * annualizedPct;

        RetirementDistributionModel distributionModel =
                getFunctionProvider().getFinancial().retirementDistributionModel(retirementBalance, retirementYear, postRetirementInterest, distributionFrequency,
                        inflationRate, duration, annualizedDistribution);

        distributionModel.solve();
        retirementBalanceList.addAll(distributionModel.getDistributionSchedule());

        double incomeReplacementPct = distributionModel.getBaseYearAnnualDistribution().asDouble() / contributionModel.getLastSalary().asDouble();

        setCoefficient(FinancialFunctions.RETIREMENT_CONTRIBUTION_MODEL, contributionModel);
        setCoefficient(FinancialFunctions.RETIREMENT_DISTRIBUTION_MODEL, distributionModel);
        setCoefficient(FinancialFunctions.RETIREMENT_SCHEDULE, retirementBalanceList);
        setCoefficient(FinancialFunctions.RETIREMENT_INCOME_PCT, incomeReplacementPct);
        setCoefficient(FinancialFunctions.RETIREMENT_DEPLETION_YEAR, distributionModel.getLastDistributionYear());

    }
}
