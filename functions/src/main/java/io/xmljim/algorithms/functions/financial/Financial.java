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

import io.xmljim.algorithms.model.ScalarFunction;

public interface Financial {

    /**
     * Calculate an amortized payment given a principle balance, an annual interest rate, payment frequency (annually),
     * the duration, and optionally weighted for inflation
     * @param balance the principle balance
     * @param interest the annual interest rate
     * @param annualPaymentFrequency payment frequency
     * @param durationYear the number of years payments are spread over
     * @param inflation a weight for inflation
     * @return a function to calculate an amortized payment
     */
    ScalarFunction amortize(double balance, double interest, int annualPaymentFrequency, int durationYear, double inflation);

    /**
     * Calculate a weighted growth rate.
     * <p>
     *     The weighted growth rate is calculated as:
     *     <pre>
     *         g<sub>weighted</sub> = (r<sub>stock</sub> * p) + (r<sub>bond</sub> * (1-p))
     *
     *         Where:
     *
     *         g<sub>weighted</sub> is the weighted growth rate
     *         r<sub>stock</sub> is the stock growth rate
     *         r<sub>bond</sub> is the bond growth rate
     *         p is the proportion weight applied to a growth rate
     *     </pre>
     * </p>
     * <p><strong>Example</strong></p>
     * <p>Assume the stock growth rate is 10%, and the average bond yield is 2%. Assume that
     *  the investor has a portfolio of 90% stocks and 10% bonds. The weighted growth rate
     *  would be:
     *  <pre>
     *      (.1 * .9) + (.02 * .1) = .092
     *  </pre>
     *
     *  Or a 9.2% weighted growth rate
     * </p>
     * @param stockRate the rate of growth in stocks
     * @param treasuryRate the rate of growth in bonds
     * @param proportion the proportion to apply to each rate. Value must be between 0.0 and 1.0
     * @return the function to calculate the weighted growth rate
     */
    ScalarFunction weightedGrowth(double stockRate, double treasuryRate, double proportion);
}
