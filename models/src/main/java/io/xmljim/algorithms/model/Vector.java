
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

import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * A sequence of values. Values can be accessed by position or through
 * streams, and also contain helper methods for sorting and splicing.
 * The vector is "storage-agnostic", meaning that it leaves that to the
 * specific implementation (e.g., arrays, lists, sets) to address.
 *
 * @param <T> The value type
 */
@SuppressWarnings("unused")
public interface Vector<T> extends VariableEntity, Iterable<T> {

    /**
     * Return the first value in the vector
     *
     * @return the first value
     */
    T first();

    /**
     * Return the last value in the vector
     *
     * @return the last value in the vector
     */
    T last();

    /**
     * Return the value at a given position
     *
     * @param index the position within the vector
     * @return the value at a given position
     */
    T get(long index);

    /**
     * Return the vector length
     *
     * @return the vector length
     */
    int length();

    /**
     * Returns whether the vector does not contain values
     *
     * @return {@code true} if the vector does not contain any values; {@code false} otherwise
     */
    default boolean isEmpty() {
        return length() == 0;
    }

    /**
     * Return a stream for the vector
     *
     * @return the vector stream
     */
    Stream<T> stream();

    /**
     * Returns a sorted stream
     *
     * @return a sorted stream. Note that the underlying order within the vector is not modified.
     */
    default Stream<T> sorted() {
        return stream().sorted();
    }

    /**
     * Return a sorted stream using a specified comparator
     *
     * @param comparator the comparator to apply for sorting
     * @return a sorted stream. Note that the underlying order within the vector is not modified.
     */
    default Stream<T> sorted(Comparator<T> comparator) {
        return stream().sorted(comparator);
    }

    /**
     * Return a subset of a vector from the a given position to the end of the vector
     *
     * @param startIndex the starting position.
     * @return a spliced vector
     */
    default Stream<T> splice(int startIndex) {
        return IntStream.range(startIndex, (int)length()).mapToObj(this::get);
    }

    /**
     * Return a subset of a vector from a given starting position and length
     *
     * @param startIndex the starting position to begin splicing the vector
     * @param length     the number of elements to include including the starting position value
     * @return a spliced vector
     */
    default Stream<T> splice(int startIndex, int length) {
        return IntStream.range(startIndex, startIndex + length).mapToObj(this::get);
    }
}
