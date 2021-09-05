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
import io.xmljim.algorithms.functions.financial.DistributionBalance;
import io.xmljim.algorithms.functions.financial.PaymentFrequency;
import io.xmljim.algorithms.functions.financial.RetirementDistributionModel;
import io.xmljim.algorithms.functions.impl.AbstractModel;
import io.xmljim.algorithms.functions.impl.provider.NameConstants;
import io.xmljim.algorithms.model.*;
import io.xmljim.algorithms.model.util.Scalar;

import java.util.ArrayList;
import java.util.List;

class RetirementDistributionModelImpl extends AbstractModel implements RetirementDistributionModel {
    private final List<DistributionBalance> distributionBalanceList = new ArrayList<>();

    /**
     * Constructor
     * @param startingBalance starting balance
     * @param retirementYear retirement year
     * @param interestRate the interest rate
     * @param paymentFrequency the distribution frequency
     * @param inflationRate the inflation rate
     * @param duration the duration.  Set to 0 if you want to estimate either by a defined annualized distribution.
     *                 If the annualized distribution is also 0, then the function will automatically calculate
     *                 the annualized distribution at 4% of the original retirement balance
     * @param annualizedDistribution if set to 0, then the distribution value will be calculated based on the specified
     *                               duration parameter. If both the duration and annualizedDistribution parameters
     *                               are set to 0, then the function will automatically calculate the annualized distribution
     *                               at 4% of the original retirement balance.
     *
     */
    public RetirementDistributionModelImpl(ScalarParameter startingBalance, ScalarParameter retirementYear, ScalarParameter interestRate,
                                           Parameter<PaymentFrequency> paymentFrequency, ScalarParameter inflationRate, ScalarParameter duration,
                                           ScalarParameter annualizedDistribution) {

        super(FinancialFunctions.RETIREMENT_DISTRIBUTION_MODEL.getName(),
                startingBalance, retirementYear, interestRate, paymentFrequency, inflationRate, duration, annualizedDistribution);
    }

    @Override
    public Coefficient<List<DistributionBalance>> getDistributionScheduleCoefficient() {
        Coefficient<?> coeff = getCoefficient(FinancialFunctions.DISTRIBUTION_SCHEDULE.getName());
        return (Coefficient<List<DistributionBalance>>) coeff;
    }

    @Override
    public ScalarCoefficient getDistributionYearsCoefficient() {
        Coefficient<?> coeff = getCoefficient(FinancialFunctions.DISTRIBUTION_YEARS.getName());
        return (ScalarCoefficient) coeff;
    }

    @Override
    public ScalarCoefficient getBaseYearAnnualDistributionCoefficient() {
        Coefficient<?> coeff = getCoefficient(FinancialFunctions.RETIREMENT_ANNUAL_DISTRIBUTION.getName());
        return (ScalarCoefficient) coeff;
    }

    @Override
    public ScalarCoefficient getLastDistributionYearCoefficient() {
        Coefficient<?> coeff = getCoefficient(FinancialFunctions.DISTRIBUTION_LAST_YEAR.getName());
        return (ScalarCoefficient) coeff;
    }

    @Override
    public ScalarCoefficient getTotalDistributionsCoefficient() {
        Coefficient<?> coeff = getCoefficient(FinancialFunctions.TOTAL_DISTRIBUTIONS.getName());
        return (ScalarCoefficient) coeff;
    }

    @Override
    public ScalarCoefficient getTotalInterestCoefficient() {
        Coefficient<?> coeff = getCoefficient(FinancialFunctions.TOTAL_INTEREST.getName());
        return (ScalarCoefficient) coeff;
    }

    @Override
    public void solve() {
        double currentBalance = getDouble(NameConstants.FIN_CURRENT_401K_BALANCE);
        int retirementYear = getInteger(NameConstants.FIN_RETIREMENT_START_YEAR);
        double interestRate = getDouble(NameConstants.FIN_POST_RETIRE_INTEREST);
        int duration = getInteger(NameConstants.FIN_RETIREMENT_DURATION);
        double inflationRate = getDouble(NameConstants.FIN_INFLATION_RATE);
        PaymentFrequency frequency = getValue(NameConstants.FIN_DISTRIBUTION_FREQUENCY);
        double annualizedDistribution = getDouble(NameConstants.FIN_ANNUAL_DISTRIBUTION);

        double amortizedAnnualDist = duration <= 0 ?
                (annualizedDistribution > 0 ? annualizedDistribution : currentBalance * .04)
                : getAmortizedDistribution(currentBalance, inflationRate, interestRate, duration, frequency);

        double updatedBalance = currentBalance;
        int currentYear = retirementYear;
        double totalDistributions = 0.0;
        double totalInterest = 0.0;

        while(updatedBalance > 0) {
            currentYear++;
            DistributionBalance distributionBalance = calculateNewBalance(updatedBalance, amortizedAnnualDist, inflationRate, interestRate, retirementYear, currentYear, frequency);
            distributionBalanceList.add(distributionBalance);
            updatedBalance = distributionBalance.getBalance();
            totalDistributions += distributionBalance.getAnnualDistributionAmount();
            totalInterest += distributionBalance.getInterestAccrued();
        }

        setCoefficient(FinancialFunctions.DISTRIBUTION_SCHEDULE, distributionBalanceList);
        setCoefficient(FinancialFunctions.DISTRIBUTION_YEARS, Scalar.of(currentYear - retirementYear));
        setCoefficient(FinancialFunctions.RETIREMENT_ANNUAL_DISTRIBUTION, Scalar.of(amortizedAnnualDist));
        setCoefficient(FinancialFunctions.DISTRIBUTION_LAST_YEAR, Scalar.of(currentYear));
        setCoefficient(FinancialFunctions.TOTAL_INTEREST, Scalar.of(totalInterest));
        setCoefficient(FinancialFunctions.TOTAL_DISTRIBUTIONS, Scalar.of(totalDistributions));
    }

    /**
     * Compute a distribution balance using the {@link io.xmljim.algorithms.functions.financial.Financial#distributionBalance(double, double, double, double, int, int, PaymentFrequency)}
     * function
     * @param currentBalance the current balance
     * @param amortizedValue the annualized distribution from the first retirement year
     * @param inflation the inflation rate
     * @param retirementInterest the interest rate on the retirement balance each year
     * @param retirementYear the year the investor retired
     * @param currentYear the current year to estimate
     * @return the distribution balance for a given year
     */
    private DistributionBalance calculateNewBalance(double currentBalance, double amortizedValue, double inflation, double retirementInterest, int retirementYear, int currentYear,
                                                    PaymentFrequency frequency) {

        Function<DistributionBalance> distFx = getFunctionProvider().getFinancial().distributionBalance(currentBalance, amortizedValue, inflation,
                retirementInterest, retirementYear, currentYear, frequency);

        return distFx.compute();
    }

    /**
     * Return the amortized annual distribution amount from the start of retirement
     * @param balance the retirement balance
     * @param duration the duration of the distributions in years
     * @return the amortized annual distribution amount
     */
    private double getAmortizedDistribution(double balance, double inflation, double interest, int duration, PaymentFrequency frequency) {
        double baseRate = interest * (1 - interest);
        ScalarFunction amortize = getFunctionProvider().getFinancial().amortize(balance, baseRate - inflation, frequency, duration);
        double result = amortize.compute().asDouble();

        return result * frequency.getAnnualFrequency();
    }

}
