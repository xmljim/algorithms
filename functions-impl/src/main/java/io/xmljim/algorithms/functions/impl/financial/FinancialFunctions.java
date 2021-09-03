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

enum FinancialFunctions implements FinancialType {
    RETIREMENT_CONTRIBUTION_MODEL("retirementContributionModel", "Retirement Contribution Estimates"),
    CONTRIBUTION_BALANCE_FUNCTION("calculateContributionFunction", "Calculate Contribution Balance"),
    CONTRIBUTION_SCHEDULE("contributionSchedule", "Contribution Schedule"),
    CONTRIBUTION_BALANCE("contributionBalance", "Contribution Balance"),
    RETIREMENT_DISTRIBUTION_MODEL("retirementDistributionModel", "Retirement Distribution Estimates"),
    DISTRIBUTION_BALANCE_FUNCTION("calculateDistributionFunction", "Calculate Distribution Balance"),
    DISTRIBUTION_SCHEDULE("distributionSchedule", "Distribution Schedule"),
    DISTRIBUTION_YEARS("distributionLengthYears", "Distribution Number of Years"),
    DISTRIBUTION_LAST_YEAR("distributionLastYear", "Distribution Last Year"),
    WEIGHTED_GROWTH("calculateWeightedGrowthRate", "Weighted Growth Rate"),
    TOTAL_INTEREST("totalInterest", "Total Interest Accrued"),
    TOTAL_SELF_CONTRIBUTION("totalSelfContribution", "Total Self Contribution"),
    TOTAL_EMPL_CONTRIBUTION("totalEmplContribution", "Total Employer Contribution"),
    LAST_SALARY("lastSalary", "Last Salary"),
    AMORTIZE("amortize", "Amoritization Value"),
    RETIREMENT_MODEL("retirementModel", "Retirement Model"),
    RETIREMENT_SCHEDULE("retirementSchedule", "Retirement Schedule"),
    RETIREMENT_DEPLETION_YEAR("retirementDepletionYear", "Retirement Depletion Year"),
    RETIREMENT_INCOME_PCT("retirementIncomePct", "Retirement Income Replacement Percentage"),
    RETIREMENT_ANNUAL_DISTRIBUTION("retirementBaseAnnualDistribution", "Base Retirement Annual Distribution")
    ;

    private final String name;
    private final String label;

    FinancialFunctions(String name, String label) {
        this.name = name;
        this.label = label;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
