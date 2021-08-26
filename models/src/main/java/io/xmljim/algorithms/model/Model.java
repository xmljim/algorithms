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

package io.xmljim.algorithms.model;

import java.util.stream.Stream;

/**
 * A model represents a computed set of coefficients that can be
 * used to solve for input values.
 */
public interface Model extends Parameterized {

    /**
     * Return a named coefficient
     * @param name the coefficient name
     * @param <T> the assigned type of the coefficient
     * @return the named coefficient or null if it doesn't exist
     */
    <T> Coefficient<T> getCoefficient(String name);

    /**
     * Return a named coefficient's value
     * @param name the coefficient name
     * @param <T> the assigned type of the coefficient
     * @return the named coefficient's value or null if it doesn't exist
     */
    default <T> T getCoefficientValue(String name) {
        T value = null;
        Coefficient<T> coefficient = getCoefficient(name);
        if (coefficient != null) {
            value = coefficient.getValue();
        }

        return value;
    }

    /**
     * Return a stream of all coefficients in the model
     * @return a stream of coefficients
     */
    Stream<Coefficient<?>> coefficients();

    /**
     * invoke a solution for the model after initialization
     */
    void solve();

}
