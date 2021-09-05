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

package io.xmljim.algorithms.functions.financial;

import io.xmljim.algorithms.model.*;
import io.xmljim.algorithms.model.util.Scalar;

import java.util.List;

/**
 * A <em>metamodel</em> that encompasses the estimations from both the {@link RetirementContributionModel}
 * and {@link RetirementDistributionModel} into a single set of estimations for retirement starting from
 * now until all funds are depleted
 */
public interface RetirementModel extends Model {


    /**
     * Return the coefficient holding the entire contribution/distribution timeline balances over time
     * @return the coefficient holding the entire contribution/distribution timeline balances over time
     */
    public Coefficient<List<RetirementBalance>> getRetirementTimelineCoefficient();

    /**
     * Coefficient for the percentage of income the retirement account will replace at retirement
     * @return Coefficient for the percentage of income the retirement account will replace at retirement
     */
    public ScalarCoefficient getRetirementIncomePctCoefficient();

    /**
     * Coefficient estimating the year when all retirement funds will be depleted
     * @return Coefficient estimating the year when all retirement funds will be depleted
     */
    public ScalarCoefficient getRetirementBalanceDepletionYearCoefficient();

    public ScalarCoefficient getRetirementYearCoefficient();

    public ScalarCoefficient getBalanceAtRetirementCoefficient();

    public ScalarCoefficient getTotalInterestCoefficient();

    public ScalarCoefficient getTotalDistributionsCoefficient();

    public ScalarCoefficient getTotalSelfContributionsCoefficient();

    public ScalarCoefficient getTotalEmployerContributionsCoefficient();

    public ScalarCoefficient getTotalDistributionYearsCoefficient();

    public ScalarCoefficient getBaseAnnualDistributionCoefficient();

    RetirementContributionModel getContributionModel();

    RetirementDistributionModel getDistributionModel();



    default List<RetirementBalance> getRetirementTimeline() {
        return getRetirementTimelineCoefficient().getValue();
    }

    default Scalar getRetirementIncomePct() {
        return getRetirementIncomePctCoefficient().getValue();
    }

    default Scalar getRetirementBalanceDepletionYear() {
        return getRetirementBalanceDepletionYearCoefficient().getValue();
    }

    default Scalar getRetirementYear() {
        return getRetirementYearCoefficient().getValue();
    }

    default Scalar getBalanceAtRetirement() {
        return getBalanceAtRetirementCoefficient().getValue();
    }

    default Scalar getTotalInterest() {
        return getTotalInterestCoefficient().getValue();
    }

    default Scalar getTotalDistributions() {
        return getTotalDistributionsCoefficient().getValue();
    }

    default Scalar getTotalSelfContributions() {
        return getTotalSelfContributionsCoefficient().getValue();
    }

    default Scalar getTotalEmployerContributions() {
        return getTotalEmployerContributionsCoefficient().getValue();
    }

    default Scalar getTotalDistributionYears() {
        return getTotalDistributionYearsCoefficient().getValue();
    }

    default Scalar getBaseAnnualContribution() {
        return getBaseAnnualDistributionCoefficient().getValue();
    }
}
