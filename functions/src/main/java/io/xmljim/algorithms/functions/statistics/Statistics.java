
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

package io.xmljim.algorithms.functions.statistics;

import io.xmljim.algorithms.model.Matrix;
import io.xmljim.algorithms.model.ScalarFunction;
import io.xmljim.algorithms.model.ScalarParameter;
import io.xmljim.algorithms.model.ScalarVector;
import io.xmljim.algorithms.model.util.Scalar;

public interface Statistics {

    /**
     * Function to calculate the sum of all values in a scalar data sequence
     * <p>
     *     <pre>
     *         &#x03A3;<sub>y</sub> = y<sub>1</sub> + y<sub>2</sub>...+ y<sub>n</sub>
     *     </pre>
     * </p>
     *
     * @param vector the scalar vector containing the values to sum
     * @return a function to calculate the sum of all values. The function returns
     * a {@link Scalar} value
     */
    ScalarFunction sum(ScalarVector vector);

    /**
     * Function to calculate the sum of all values in a scalar data sequence
     * <p>
     *     <pre>
     *         &#x03A3;<sub>y</sub> = y<sub>1</sub> + y<sub>2</sub>...+ y<sub>n</sub>
     *     </pre>
     * </p>
     *
     * @param vector the scalar vector containing the values to sum
     * @param variable the variable to assign to this function
     * @return a function to calculate the sum of all values. The function returns
     * a {@link Scalar} value
     */
    ScalarFunction sum(ScalarVector vector, String variable);

    /**
     * Function to calculate the mean of all values in a scalar data sequence
     * <p>
     *     <pre>
     *         y&#x0304; = &#x03A3;<sub>y</sub> / n<sub>y</sub>
     *     </pre>
     * </p>
     *
     * @param vector vector the scalar vector containing the values to average
     * @return a function to compute the mean from all values in a scalar data sequence.
     *  The function returns a {@link Scalar} value
     */
    ScalarFunction mean(ScalarVector vector);

    /**
     * Function to calculate the mean of all values in a scalar data sequence
     * <p>
     *     <pre>
     *         y&#x0304; = &#x03A3;<sub>y</sub> / n<sub>y</sub>
     *     </pre>
     * </p>
     *
     * @param vector vector the scalar vector containing the values to average
     * @param variable the variable to assign to the function
     * @return a function to compute the mean from all values in a scalar data sequence.
     *  The function returns a {@link Scalar} value
     */
    ScalarFunction mean(ScalarVector vector, String variable);

    /**
     * Return the function to compute the median value from a given sample population
     *
     * @param vector the population data
     * @return The equation that will compute the median coefficient
     */
    ScalarFunction median(ScalarVector vector);

    /**
     * Return the function to compute the median value from a given sample population
     *
     * @param vector the population data
     * @param variable the variable to assign to the function
     * @return The equation that will compute the median coefficient
     */
    ScalarFunction median(ScalarVector vector, String variable);

    /**
     * Return the function to compute the variance from a data set.
     * The algorithm is the sum of the squares of the deviations of each item from the data set's mean.
     * <pre>
     *    	&#x03C3;<sup>2</sup><sub>y</sub> = &#x3A3;(y<sub>i</sub> - y&#x0304;)<sup>2</sup> / (n - 1)
     * </pre>
     *
     * @param vector the population data
     * @return the equation to compute the variance
     */
    ScalarFunction variance(ScalarVector vector);

    /**
     * Return the function to compute the variance from a data set.
     * The algorithm is the sum of the squares of the deviations of each item from the data set's mean.
     * <pre>
     *    	&#x03C3;<sup>2</sup><sub>y</sub> = &#x3A3;(y<sub>i</sub> - y&#x0304;)<sup>2</sup> / (n - 1)
     * </pre>
     *
     * @param vector the population data
     * @param variable the variable to assign to the function
     * @return the equation to compute the variance
     */
    ScalarFunction variance(ScalarVector vector, String variable);

    /**
     * Return the function to compute the variance from a data set.
     * The algorithm is the sum of the squares of the deviations of each item from the data set's mean.
     * <pre>
     *    	&#x03C3;<sup>2</sup><sub>y</sub> = &#x3A3;(y<sub>i</sub> - y&#x0304;)<sup>2</sup> / (n - 1)
     * </pre>
     *
     * @param vector the population data
     * @param meanFunction the function reference for the mean of this vector
     * @return the equation to compute the variance
     */
    ScalarFunction variance(ScalarVector vector, ScalarFunction meanFunction);

    /**
     * Return the function to compute the variance from a data set.
     * The algorithm is the sum of the squares of the deviations of each item from the data set's mean.
     * <pre>
     *    	&#x03C3;<sup>2</sup><sub>y</sub> = &#x3A3;(y<sub>i</sub> - y&#x0304;)<sup>2</sup> / (n - 1)
     * </pre>
     *
     * @param vector the population data
     * @param meanFunction the function reference for the mean of this vector
     * @param variable the variable to assign to this function
     * @return the equation to compute the variance
     */
    ScalarFunction variance(ScalarVector vector, ScalarFunction meanFunction, String variable);

    /**
     * Return the equation to compute the standard deviation from a sample population
     *
     * <pre>
     *
     *     &#x03C3<sub>y</sub> = &#x221A;(&#x03A3;(y<sub>i</sub> - y&#x0304;)<sup>2</sup> / (n - 1))
     *
     *     or simply, the square root of the variance function
     *
     *     &#x221A;&#x03C3;<sup>2</sup><sub>y</sub>
     * </pre>
     *
     * @param varianceFunction the Scalar coefficient value computed from the {@link #variance(ScalarVector)} function
     * @return the equation to compute standard deviation
     */
    ScalarFunction standardDeviation(ScalarFunction varianceFunction);

    /**
     * Return the equation to compute the standard deviation from a sample population
     *
     * <pre>
     *
     *     &#x03C3<sub>y</sub> = &#x221A;(&#x03A3;(y<sub>i</sub> - y&#x0304;)<sup>2</sup> / (n - 1))
     *
     *     or simply, the square root of the variance function
     *
     *     &#x221A;&#x03C3;<sup>2</sup><sub>y</sub>
     * </pre>
     *
     * @param varianceFunction the Scalar coefficient value computed from the {@link #variance(ScalarVector)} function
     * @param variable the variable assign to the function
     * @return the equation to compute standard deviation
     */
    ScalarFunction standardDeviation(ScalarFunction varianceFunction, String variable);

    /**
     * Return the equation to compute the standard deviation from a sample population
     *
     * <pre>
     *
     *     &#x03C3<sub>y</sub> = &#x221A;(&#x03A3;(y<sub>i</sub> - y&#x0304;)<sup>2</sup> / (n - 1))
     *
     *     or simply, the square root of the variance function
     *
     *     &#x221A;&#x03C3;<sup>2</sup><sub>y</sub>
     * </pre>
     *
     * @param vectorParameter parameter containing {@link io.xmljim.algorithms.model.ScalarVector} of population values
     * @return the standard deviation equation
     */
    ScalarFunction standardDeviation(ScalarVector vectorParameter);

    /**
     * Return the equation to compute the standard deviation from a sample population
     *
     * <pre>
     *
     *     &#x03C3<sub>y</sub> = &#x221A;(&#x03A3;(y<sub>i</sub> - y&#x0304;)<sup>2</sup> / (n - 1))
     *
     *     or simply, the square root of the variance function
     *
     *     &#x221A;&#x03C3;<sup>2</sup><sub>y</sub>
     * </pre>
     *
     * @param vector parameter containing {@link io.xmljim.algorithms.model.ScalarVector} of population values
     * @param variable the variable to assign to the function
     * @return the standard deviation equation
     */
    ScalarFunction standardDeviation(ScalarVector vector, String variable);

    /**
     * Returns the covariance between two data sets.
     *
     * <pre>
     *     &#x03C3<sub>XY</sub> = &#x3A3;((x<sub>i</sub> - x&#x0304;)(y<sub>i</sub> - y&#x0304;)) / (n - 1)
     * </pre>
     *
     * @param vectorX contains the {@link io.xmljim.algorithms.model.ScalarVector} of the independent (x) variable values
     * @param vectorY contains the {@link io.xmljim.algorithms.model.ScalarVector} of the dependent (y) variable values
     * @return the covariance equation
     */
    ScalarFunction covariance(ScalarVector vectorX, ScalarVector vectorY);

    /**
     * Returns the covariance between two data sets.
     *
     * <pre>
     *     &#x03C3<sub>XY</sub> = &#x3A3;((x<sub>i</sub> - x&#x0304;)(y<sub>i</sub> - y&#x0304;)) / (n - 1)
     * </pre>
     *
     * @param vectorX contains the {@link io.xmljim.algorithms.model.ScalarVector} of the independent (x) variable values
     * @param vectorY contains the {@link io.xmljim.algorithms.model.ScalarVector} of the dependent (y) variable values
     * @param meanX the function reference to the mean coefficient for the independent (x) variable
     * @param meanY the function reference to the mean coefficient for the dependent(y) variable
     * @return the covariance equation
     */
    ScalarFunction covariance(final ScalarVector vectorX, final ScalarVector vectorY, ScalarFunction meanX, ScalarFunction meanY);

    /**
     * Returns the slope of regression line
     * <pre>
     *     &#x03B1; = &#x03C3;<sup>2</sup><sub>x</sub> / &#x03C3<sub>XY</sub>
     * </pre>
     *
     * @param varianceX  the variance coefficient for the independent (x) variable obtained from {@link #variance(ScalarVector)}
     * @param covariance the covariance of the independent (x) and dependent variables obtained from the {@link #covariance(ScalarVector, ScalarVector)}
     * @return the slope equation
     */
    ScalarFunction slope(ScalarFunction varianceX, ScalarFunction covariance);


    /**
     * Computes the y-intercept for a given set of two variables, x and y
     * <pre>
     *     &#x03B2; = y&#x0304; - &#x03B1; * x&#x0304;
     * </pre>
     *
     * @param meanX       the vector containing values for the independent variable (x). Required to access the {@link #mean(ScalarVector)} coefficient
     * @param meanY       the vector containing values for the dependent variable (y). Required to access the {@link #mean(ScalarVector)} coefficient
     * @param slopeFunction the slope coefficient obtained from {@link #slope(ScalarFunction, ScalarFunction)}
     * @return the intercept equation
     */
    ScalarFunction intercept(ScalarFunction meanX, ScalarFunction meanY, ScalarFunction slopeFunction);


    /**
     * Returns the residual (sum of squares) between the predicted dependent variable value (&#x177;) and
     * the actual y value
     * <pre>
     *     SSR = &#x03A3;(&#x177;<sub>i</sub> - y<sub>i</sub>)<sup>2</sup>
     *
     *     Where:
     *
     *     &#x177;<sub>i</sub> = &#x3B2x<sub>i</sub> + &#x3B1
     * </pre>
     *
     * @param vectorX              vector containing the values for the independent variable (x)
     * @param vectorY              vector containing the values for the dependent variable (y)
     * @param slopeCoefficient     the slope coefficient obtained from the {@link #slope(ScalarFunction, ScalarFunction)} equation
     * @param interceptCoefficient the intercept coefficient obtained from the {@link #intercept(ScalarFunction, ScalarFunction, ScalarFunction)}  equation
     * @return the residual variance equation
     */
    ScalarFunction residualSumOfSquares(ScalarVector vectorX, ScalarVector vectorY, ScalarFunction slopeCoefficient, ScalarFunction interceptCoefficient);

    /**
     * Returns the total variation between a variable's observed and mean values
     *
     * <pre>
     *     SST = &#x3A3;(y<sub>i</sub> - y&#x0304;)<sup>2</sup>
     * </pre>
     *
     * @param vector         the vector of values for a given variable
     * @param mean           the mean of all values for the given variable. See {@link #mean(ScalarVector)}
     * @return the total variance equation
     */
    ScalarFunction totalSumOfSquares(ScalarVector vector, ScalarFunction mean);

    /**
     * Returns the goodness of fit of a regression model against two variables, x and y
     * using the model's {@link #residualSumOfSquares(ScalarVector, ScalarVector, ScalarFunction, ScalarFunction)}
     * and the dependent variable's {@link #totalSumOfSquares(ScalarVector, ScalarFunction)}
     *
     * <pre>
     *     R<sup>2</sup> = 1 - (SSR / SST)
     * </pre>
     *
     * @param residualVarianceFunction the residual variance coefficient, obtained from the {@link #residualSumOfSquares(ScalarVector, ScalarVector, ScalarFunction, ScalarFunction)} function
     * @param totalVarianceFunction    the total variance coefficient, obtained from the {@link #totalSumOfSquares(ScalarVector, ScalarFunction)} function
     * @return the r-squared function
     */
    ScalarFunction rSquared(ScalarFunction residualVarianceFunction, ScalarFunction totalVarianceFunction);

    /**
     * Function calculating the mean standard error (MSE) for a given set of independent (x) and dependent (y) variables, using the equation:
     * 
     * <p>
     *     <pre>
     *         MSE = &#x03A3;(&#x177;<sub>i</sub> - y<sub>i</sub>)<sup>2</sup> / (n - 2)
     *
     *     Where:
     *
     *         &#x177;<sub>i</sub> = &#x3B2x<sub>i</sub> + &#x3B1
     *     </pre>
     * </p>
     * @param vectorX the vector for the independent variable (x)
     * @param vectorY the vector for the dependent variable (y)
     * @param slopeFunction the slope function. See {@link #slope(ScalarFunction, ScalarFunction)}
     * @param interceptFunction the intercept function. See {@link #intercept(ScalarFunction, ScalarFunction, ScalarFunction)}
     * @return the mean standard error function
     */
    ScalarFunction meanSquaredError(ScalarVector vectorX, ScalarVector vectorY, ScalarFunction slopeFunction, ScalarFunction interceptFunction);

    /**
     * Function to calculate the standard error for the slope, using the equation:
     *
     * <p>
     *     <pre>
     *        s<sub>&#x03B2;</sub> = sqrt(MSE) / sqrt(SS<sub>x</sub>)
     *
     *        Where:
     *
     *        MSE (mean squared error) = &#x03A3;(&#x177;<sub>i</sub> - y<sub>i</sub>)<sup>2</sup> / (n - 2)
     *        SS<sub>x</sub> (sum of squares of x) = &#x03A3;(x<sub>i</sub> - x&#x0304;)<sup>2</sup>
     *     </pre>
     * </p>
     * @param sumOfSquaresX the sum of squares for the independent variable (x). See {@link #totalSumOfSquares(ScalarVector, ScalarFunction)}
     * @param meanSquaredError the mean squared error. See {@link #meanSquaredError(ScalarVector, ScalarVector, ScalarFunction, ScalarFunction)}
     * @return the slope standard error function
     */
    ScalarFunction slopeStandardError(ScalarFunction sumOfSquaresX, ScalarFunction meanSquaredError);

    /**
     * Function to calculate the standard error for the intercept, using the equation
     *
     * <p>
     *     <pre>
     *         s<sub>&#x03B1;</sub> = sqrt(MSE * ( 1/n + (x&#x0304;<sup>2</sup> / SS<sub>x</sub>)))
     *
     *         Where:
     *         MSE (mean squared error) = &#x03A3;(&#x177;<sub>i</sub> - y<sub>i</sub>)<sup>2</sup> / (n - 2)
     *         SS<sub>x</sub> (sum of squares of x) = &#x03A3;(x<sub>i</sub> - x&#x0304;)<sup>2</sup>
     *     </pre>
     * </p>
     * @param meanSquaredErrorFunction the mean squared error function. See {@link #meanSquaredError(ScalarVector, ScalarVector, ScalarFunction, ScalarFunction)}
     * @param meanXFunction the mean of the independent variable (x).  See {@link #mean(ScalarVector)}
     * @param sumOfSquaresXFunction the sum of squares of the independent variable (x). See {@link #totalSumOfSquares(ScalarVector, ScalarFunction)}
     * @param count the size of the total population
     * @return the intercept standard error function
     */
    ScalarFunction interceptStandardError(ScalarFunction meanSquaredErrorFunction, ScalarFunction meanXFunction, ScalarFunction sumOfSquaresXFunction, Scalar count);

    ScalarFunction slopeTStatistic(ScalarFunction slopeFunction, ScalarFunction slopeStandardError);

    ScalarFunction slopePValue(int degreesOfFreedom, ScalarFunction slopeTStatistic);

    /**
     * Create a linear regression model from a matrix of values. By default, the model will use the first column (0)
     * as the independent variable (x), and the next column (1) as the dependent variable (y).
     * Use {@link #linearRegression(Matrix, int, int)} if you want to choose different columns
     * @param matrix the matrix containing the data
     * @return a linear regression model, solved for a given independent and dependent variable
     */
    LinearRegressionModel linearRegression(Matrix matrix);

    /**
     * Create a linear regression model from a matrix of values.
     * @param matrix the matrix containing the data
     * @param setX the column index containing the values for the independent variable (x)
     * @param setY the column index containing the values for the dependent variable (y)
     * @return a linear regression model, solved for a given independent and dependent variable
     */
    LinearRegressionModel linearRegression(Matrix matrix, int setX, int setY);

    /**
     * Create a linear regression model for two sets of values, one for the independent variable (x),
     * and another for the dependent variable (y)
     * @param vectorX the data for the independent variable (x)
     * @param vectorY the data for the dependent variable (y)
     * @return a linear regression model, solved for a given independent and dependent variable
     */
    LinearRegressionModel linearRegression(ScalarVector vectorX, ScalarVector vectorY);

}
