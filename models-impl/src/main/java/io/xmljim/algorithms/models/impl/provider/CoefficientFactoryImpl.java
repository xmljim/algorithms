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

package io.xmljim.algorithms.models.impl.provider;

import io.xmljim.algorithms.model.Coefficient;
import io.xmljim.algorithms.model.ScalarCoefficient;
import io.xmljim.algorithms.model.provider.CoefficientFactory;
import io.xmljim.algorithms.model.util.Scalar;

class CoefficientFactoryImpl implements CoefficientFactory {
    @Override
    public <T> Coefficient<T> createCoefficient(final String name, final T value) {
        return new BaseCoefficientImpl<>(name, value);
    }

    @Override
    public <T> Coefficient<T> createCoefficient(final String name, final String label, final T value) {
        return new BaseCoefficientImpl<>(name, label, value);
    }

    @Override
    public ScalarCoefficient createCoefficient(final String name, final Scalar value) {
        return new ScalarCoefficientImpl(name, value);
    }

    @Override
    public ScalarCoefficient createCoefficient(final String name, final String label, final Scalar value) {
        return new ScalarCoefficientImpl(name, label, value);
    }
}
