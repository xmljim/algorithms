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
import io.xmljim.algorithms.functions.impl.AbstractFunctionFactory;
import io.xmljim.algorithms.functions.impl.provider.NameConstants;
import io.xmljim.algorithms.functions.provider.FunctionProvider;
import io.xmljim.algorithms.model.Function;
import io.xmljim.algorithms.model.Parameter;
import io.xmljim.algorithms.model.ScalarFunction;
import io.xmljim.algorithms.model.ScalarParameter;


/**
 * Implmentation of the {@link Financial} factory interface
 */
public class FinancialImpl extends AbstractFunctionFactory implements Financial {
    /**
     * Constructor
     * @param functionProvider the {@link FunctionProvider} implementation
     */
    public FinancialImpl(final FunctionProvider functionProvider) {
        super(functionProvider);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScalarFunction amortize(final double balance, final double interest, PaymentFrequency frequency, final int durationYear) {
        ScalarParameter balanceParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_CURRENT_401K_BALANCE, balance);
        ScalarParameter interestParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_POST_RETIRE_INTEREST, interest);
        Parameter<PaymentFrequency> frequencyParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_DISTRIBUTION_FREQUENCY, frequency);
        ScalarParameter durationParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_RETIREMENT_DURATION, durationYear);

        return new AmortizeFunction(balanceParam, interestParam, frequencyParam, durationParam);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScalarFunction weightedGrowth(final double stockRate, final double treasuryRate, final double proportion) {
        ScalarParameter stockRateParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_STOCK_GROWTH_RATE, stockRate);
        ScalarParameter treasuryRateParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_TREASURY_YIELD, treasuryRate);
        ScalarParameter investmentRatioParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_INVESTMENT_RATIO, proportion);

        return new WeightedGrowthFunction(stockRateParam, treasuryRateParam, investmentRatioParam);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RetirementContributionModel retirementContributionModel(final int currentAge, final int retirementAge, final double currentSalary,
                                                                   final double employeeContribution, final double employerContribution,
                                                                   final double currentBalance, final double colaPct, final double weightedGrowthRate,
                                                                   final PaymentFrequency contributionFrequency) {

        ScalarParameter currentAgeParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_AGE, currentAge);
        ScalarParameter retirementAgeParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_RETIREMENT_AGE, retirementAge);
        ScalarParameter currentSalaryParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_CURRENT_SALARY, currentSalary);
        ScalarParameter selfContribParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_EMPLOYEE_CONTRIB_PCT, employeeContribution);
        ScalarParameter emplContribParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_EMPLOYER_CONTRIB_PCT, employerContribution);
        ScalarParameter current401kBalanceParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_CURRENT_401K_BALANCE, currentBalance);
        ScalarParameter colaPctParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_COLA_PCT, colaPct);
        ScalarParameter weightedGrowthParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_WEIGHTED_GROWTH_RATE, weightedGrowthRate);
        Parameter<PaymentFrequency> contributionFrequencyParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_CONTRIBUTION_FREQUENCY, contributionFrequency);

        return new RetirementContributionModelImpl(currentAgeParam, retirementAgeParam, currentSalaryParam, selfContribParam, emplContribParam,
                current401kBalanceParam, colaPctParam, weightedGrowthParam, contributionFrequencyParam);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Function<ContributionBalance> contributionBalance(final double currentSalary, final double colaPct,
                                                             final double currentRetirementBalance, final double selfContributionPct,
                                                             final double employerContributionPct, final double weightedGrowthRate,
                                                             PaymentFrequency contributionFrequency,
                                                             final int currentYear, final int endYear) {

        ScalarParameter currentSalaryParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_CURRENT_SALARY, currentSalary);
        ScalarParameter colaPctParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_COLA_PCT, colaPct);
        ScalarParameter currentRetirementBalanceParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_CURRENT_401K_BALANCE, currentRetirementBalance);
        ScalarParameter selfContributionPctParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_EMPLOYEE_CONTRIB_PCT, selfContributionPct);
        ScalarParameter emplContributionPctParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_EMPLOYER_CONTRIB_PCT, employerContributionPct);
        ScalarParameter weightedGrowthRateParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_WEIGHTED_GROWTH_RATE, weightedGrowthRate);
        Parameter<PaymentFrequency> contributionFrequencyParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_CONTRIBUTION_FREQUENCY, contributionFrequency);
        ScalarParameter currentYearParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_CURRENT_YEAR, currentYear);
        ScalarParameter endYearParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_END_YEAR, endYear);

        return new ContributionBalanceFunction(currentSalaryParam, colaPctParam, currentRetirementBalanceParam, selfContributionPctParam,
                emplContributionPctParam, weightedGrowthRateParam, contributionFrequencyParam, currentYearParam, endYearParam);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Function<ContributionBalance> contributionBalance(final ContributionBalance previousBalance, final int endYear) {

        Parameter<ContributionBalance> contributionBalanceParameter = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_PREVIOUS_BALANCE, previousBalance);
        ScalarParameter endYearParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_END_YEAR, endYear);
        return new ContributionBalanceFunction(contributionBalanceParameter, endYearParam);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Function<DistributionBalance> distributionBalance(final double currentBalance, final double amortizedValue, final double inflation,
                                                             final double retirementInterest, final int retirementYear, final int currentYear,
                                                             PaymentFrequency distributionFrequency) {

        ScalarParameter currentBalanceParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_CURRENT_401K_BALANCE, currentBalance);
        ScalarParameter amortizedParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_ANNUAL_DISTRIBUTION, amortizedValue);
        ScalarParameter inflationParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_INFLATION_RATE, inflation);
        ScalarParameter retirementInterestParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_WEIGHTED_GROWTH_RATE, retirementInterest);
        ScalarParameter retirementYearParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_RETIREMENT_START_YEAR, retirementYear);
        ScalarParameter currentYearParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_CURRENT_YEAR, currentYear);
        Parameter<PaymentFrequency> distributionFrequencyParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_DISTRIBUTION_FREQUENCY, distributionFrequency);

        return new DistributionBalanceFunction(currentBalanceParam, amortizedParam, inflationParam, retirementInterestParam, retirementYearParam, currentYearParam, distributionFrequencyParam);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RetirementDistributionModel retirementDistributionModel(double startingBalance, int retirementYear, double interestRate, PaymentFrequency paymentFrequency,
                                                                   double inflationRate, int duration, double annualizedDistribution) {


        ScalarParameter currentBalanceParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_CURRENT_401K_BALANCE, startingBalance);
        ScalarParameter retirementYearParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_RETIREMENT_START_YEAR, retirementYear);
        ScalarParameter interestRateParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_POST_RETIRE_INTEREST, interestRate);
        ScalarParameter durationParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_RETIREMENT_DURATION, duration);
        ScalarParameter inflationRateParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_INFLATION_RATE, inflationRate);
        Parameter<PaymentFrequency> paymentFrequencyParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_DISTRIBUTION_FREQUENCY, paymentFrequency);
        ScalarParameter annualizedDistributionParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_ANNUAL_DISTRIBUTION, annualizedDistribution);

        return new RetirementDistributionModelImpl(currentBalanceParam, retirementYearParam, interestRateParam, paymentFrequencyParam, inflationRateParam, durationParam, annualizedDistributionParam);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RetirementModel retirementModel(final int currentAge, final int retirementAge, final double currentSalary, final double currentRetirementBalance,
                                           final double selfContributionPct, final double employerContributionPct, final double colaPct, final double weightedGrowthPct,
                                           final PaymentFrequency contributionFrequency, final double postRetirementInterest, final PaymentFrequency distributionFrequency,
                                           final double inflationRate, final int duration, final double annualizedLastSalaryPct) {

        ScalarParameter currentAgeParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_AGE, currentAge);
        ScalarParameter retirementAgeParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_RETIREMENT_AGE, retirementAge);
        ScalarParameter currentSalaryParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_CURRENT_SALARY, currentSalary);
        ScalarParameter currentRetirementBalanceParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_CURRENT_401K_BALANCE, currentRetirementBalance);
        ScalarParameter selfContributionPctParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_EMPLOYEE_CONTRIB_PCT, selfContributionPct);
        ScalarParameter emplContributionPctParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_EMPLOYER_CONTRIB_PCT, employerContributionPct);
        ScalarParameter colaPctParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_COLA_PCT, colaPct);
        ScalarParameter weightedGrowthParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_WEIGHTED_GROWTH_RATE, weightedGrowthPct);
        Parameter<PaymentFrequency> contributionFequencyParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_CONTRIBUTION_FREQUENCY, contributionFrequency);
        ScalarParameter postRetirementInterestParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_POST_RETIRE_INTEREST, postRetirementInterest);
        Parameter<PaymentFrequency> paymentFrequencyParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_DISTRIBUTION_FREQUENCY, distributionFrequency);
        ScalarParameter inflationRateParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_INFLATION_RATE, inflationRate);
        ScalarParameter durationParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_RETIREMENT_DURATION, duration);
        ScalarParameter incomeReplacementPctParam = getModelProvider().getParameterFactory().createParameter(NameConstants.FIN_LAST_SALARY_PCT, annualizedLastSalaryPct);

        return new RetirementModelImpl(currentAgeParam, retirementAgeParam, currentSalaryParam, currentRetirementBalanceParam, selfContributionPctParam, emplContributionPctParam,
                colaPctParam, weightedGrowthParam, contributionFequencyParam, postRetirementInterestParam, paymentFrequencyParam, inflationRateParam, durationParam, incomeReplacementPctParam);
    }
}
