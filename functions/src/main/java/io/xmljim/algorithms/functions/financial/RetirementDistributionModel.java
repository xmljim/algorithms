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

import io.xmljim.algorithms.model.Coefficient;
import io.xmljim.algorithms.model.Model;
import io.xmljim.algorithms.model.ScalarCoefficient;
import io.xmljim.algorithms.model.util.Scalar;

import java.util.List;

/**
 * The retirement distribution model estimates the duration and distribution of
 * a retirement account over time.  Several factors influence the duration and the
 * amount of distribution over each year:
 * <ul>
 *     <li>The amount in the retirement balance influences the annual distribution over time</li>
 *     <li>Inflation: to keep pace with the same standard of living, distributions should keep up with inflation</li>
 *     <li>Post-retirement interest.  In general, the conservative recommendation is to assume a 4% interest on your retirement balance</li>
 *     <li>Note that the model does not attempt to account for other sources of retirement income (IRAs, Social Security)</li>
 * </ul>
 */
public interface RetirementDistributionModel extends Model {

    /**
     * The coefficient containing the timeline for the estimated annual distribution
     * @return The coefficient containing the timeline for the estimated annual distribution
     */
    Coefficient<List<DistributionBalance>> getDistributionScheduleCoefficient();

    /**
     * The coefficient containing the estimated number of years the retirement account will be
     * able to make annual distributions
     * @return the estimated distribution years coefficient
     */
    ScalarCoefficient getDistributionYearsCoefficient();


    /**
     * Coefficient that holds the annualized distribution for the start of retirement
     * @return Coefficient that holds the annualized distribution for the start of retirement
     */
    ScalarCoefficient getBaseYearAnnualDistributionCoefficient();

    /**
     * Coefficient that estimates the last distribution year
     * @return Coefficient that estimates the last distribution year
     */
    ScalarCoefficient getLastDistributionYearCoefficient();

    ScalarCoefficient getTotalDistributionsCoefficient();

    ScalarCoefficient getTotalInterestCoefficient();

    /**
     * Returns the estimated distribution schedule
     * @return the estimated distribution schedule
     */
    default List<DistributionBalance> getDistributionSchedule() {
        return getDistributionScheduleCoefficient().getValue();
    }

    /**
     * The estimated number of years the retirement account can sustain distributions
     * @return the number of years the retirement account will be able to pay.
     */
    default Scalar getDistributionYears() {
        return getDistributionYearsCoefficient().getValue();
    }

    /**
     * The estimated last distribution year
     * @return the estimated last distribution year
     */
    default Scalar getLastDistributionYear() {
        return getLastDistributionYearCoefficient().getValue();
    }

    /**
     * The computed annual distribution from a retirement account at the start of retirement.
     * Used to estimate future years' distributions, and can be used to compute the percentage
     * of working income replaced with the retirement account
     * @return the computed annual distribution from a retirement account at the start of retirement
     */
    default Scalar getBaseYearAnnualDistribution() {
        return getBaseYearAnnualDistributionCoefficient().getValue();
    }

    default Scalar getTotalDistributions() {
        return getTotalDistributionsCoefficient().getValue();
    }

    default Scalar getTotalInterest() {
        return getTotalInterestCoefficient().getValue();
    }
}
