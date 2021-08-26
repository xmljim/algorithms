
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

package io.xmljim.algorithms.model.provider;

import io.xmljim.algorithms.model.ScalarVector;
import io.xmljim.algorithms.model.Vector;
import io.xmljim.algorithms.model.util.Scalar;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

public interface VectorFactory {

    <T> Vector<T> createVector(String name, String variable, Stream<T> stream);

    default <T> Vector<T> createVector(String name, Stream<T> stream) {
        return createVector(name, null, stream);
    }

    default <T> Vector<T> createVector(String name, String variable, Collection<T> collection) {
        return createVector(name, variable, collection.stream());
    }

    default <T> Vector<T> createVector(String name, Collection<T> collection) {
        return createVector(name, null, collection);
    }

    default <T> Vector<T> createVector(String name, String variable, T... values) {
        return createVector(name, variable, Arrays.stream(values));
    }

    default <T> Vector<T> createVector(String name, T... values) {
        return createVector(name, values);
    }

    ScalarVector createScalarVector(String name, String variable, Stream<Number> stream);

    default ScalarVector createScalarVector(String name, Stream<Number> stream) {
        return createScalarVector(name, null, stream);
    }

    default ScalarVector createScalarVector(String name, String variable, Collection<Number> scalars) {
        return createScalarVector(name, variable, scalars.stream().map(Scalar::of));
    }

    default ScalarVector createScalarVector(String name, Collection<Number> scalars) {
        return createScalarVector(name, null, scalars);
    }

    default ScalarVector createScalarVector(String name, String variable, Number... numbers) {
        return createScalarVector(name, variable, Arrays.stream(numbers).map(Scalar::of));
    }

    default ScalarVector createScalarVector(String name, Number... numbers) {
        return createScalarVector(name, null, numbers);
    }
}
