
/*
 * Copyright 2021 Jim Earley (xml.jim@gmail.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify, merge,
 * publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons
 * to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package io.xmljim.algorithms.model.util;

import java.text.DecimalFormat;
import java.text.Format;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A scalar represents any value that can be interpreted as a number.
 * Internally, the class can transform specific types into numeric representations
 * e.g., dates ({@link LocalDate}), date-times ({@link LocalDateTime}, booleans.
 * classes that are subclasses of {@link Number} are stored without transformation.
 */

@SuppressWarnings("unused")
public final class Scalar extends Number implements Comparable<Scalar> {

    private final Number internalValue;
    private final Object refValue;

    /**
     * Private constructor. Use {@link #of} methods
     *
     * @param refValue      the underlying object containing the scalar value
     * @param internalValue the numeric representation of the object
     */
    private Scalar(Object refValue, Number internalValue) {
        this.refValue = refValue;
        this.internalValue = internalValue;
    }

    /**
     * Creates a scalar instance from a number
     *
     * @param number a number subclass
     * @return the scalar for the number
     */
    public static Scalar of(Number number) {
        return new Scalar(number, number);
    }

    /**
     * Return a scalar of a local date
     *
     * @param localDate the date value
     * @return a new scalar
     * @see SerialTemporal#of(LocalDate)
     */
    public static Scalar of(LocalDate localDate) {
        return new Scalar(localDate, SerialTemporal.of(localDate));
    }

    /**
     * Return a scalar of a local date time
     *
     * @param localDateTime the local date time
     * @return a new scalar
     * @see SerialTemporal#of(LocalDateTime)
     */
    public static Scalar of(LocalDateTime localDateTime) {
        return new Scalar(localDateTime, SerialTemporal.of(localDateTime));
    }

    /**
     * Return a scalar of a boolean value. Internally the boolean value
     * is transformed into {@code 0-true} or {@code 1-false}
     *
     * @param booleanValue the boolean value
     * @return a new scalar
     */
    public static Scalar of(Boolean booleanValue) {
        return new Scalar(booleanValue, booleanValue ? 0 : 1);
    }

    /**
     * Return the scalar as a casted number value
     *
     * @param <T> the number type
     * @return the numeric value
     */
    @SuppressWarnings("unchecked")
    public <T extends Number> T asNumber() {
        return (T) internalValue;
    }

    /**
     * Return the scalar as a boolean
     *
     * @return {@code true} if the numeric value is 0, otherwise false;
     */
    public Boolean asBoolean() {
        return internalValue.intValue() == 0;
    }

    /**
     * Return the scalar as a double value
     *
     * @return the double value
     */
    public double asDouble() {
        return asNumber().doubleValue();
    }

    /**
     * Return the scalar as an integer
     *
     * @return the integer value
     */
    public int asInt() {
        return asNumber().intValue();
    }

    /**
     * Return the scalar as a long
     *
     * @return the long value
     */
    public long asLong() {
        return asNumber().longValue();
    }

    @Override
    public String toString() {
        //return refValue.toString();
        return new DecimalFormat("0.000###########").format(internalValue);
    }

    /**
     * Apply a formatter to the underlying data object and return the formatted string
     *
     * @param format the formatter
     * @return the formatted string
     */
    public String format(Format format) {
        return format.format(refValue);
    }

    public boolean equals(Object object) {
        if (object instanceof Scalar) {
            return asDouble() == ((Scalar) object).asDouble();
        }
        return false;
    }

    @Override
    public int compareTo(final Scalar o) {
        return Scalar.of(equals(o)).asInt();
    }

    @Override
    public int intValue() {
        return asInt();
    }

    @Override
    public long longValue() {
        return asLong();
    }

    @Override
    public float floatValue() {
        return internalValue.floatValue();
    }

    @Override
    public double doubleValue() {
        return asDouble();
    }
}
