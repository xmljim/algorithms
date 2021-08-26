
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

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * A numeric representation of a {@link LocalDateTime}. The numeric representation
 * is stored as Epoch Seconds (the number of seconds since January 1, 1970).
 * SerialDate extends from number and can be used as a {@link Scalar} value
 */
public final class SerialDateTime extends SerialTemporal {

    /**
     * Private constructor. Use #of methods to create instances
     *
     * @param epochSeconds the number of epoch seconds since January 1, 1970
     * @param ldt          the localDateTime instance
     */
    private SerialDateTime(Number epochSeconds, LocalDateTime ldt) {
        super(epochSeconds, ldt);
    }

    /**
     * Parse a date-time string into a LocalDateTime instance and transform to a numeric representation
     *
     * @param dateTimeString the date-time string to parse
     * @return a new SerialDateTime instance
     */
    public static SerialDateTime of(String dateTimeString) {
        LocalDateTime ldt = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_DATE_TIME);
        return new SerialDateTime(ldt.toEpochSecond(ZoneOffset.ofTotalSeconds(0)), ldt);
    }

    /**
     * Convert a LocalDateTime instance to a SerialDateTime instance
     *
     * @param localDateTime the date-time instance
     * @return a new SerialDateTime instance
     */
    public static SerialDateTime of(LocalDateTime localDateTime) {
        return new SerialDateTime(localDateTime.toEpochSecond(ZoneOffset.ofTotalSeconds(0)), localDateTime);
    }

    /**
     * Compute a LocalDateTime instance from epoch seconds and convert to a numeric representation
     *
     * @param epochSeconds the number of seconds since January 1, 1970
     * @return a new SerialDateTime instance
     */
    public static SerialDateTime ofSeconds(long epochSeconds) {
        LocalDateTime ldt = LocalDateTime.ofEpochSecond(epochSeconds, 0, ZoneOffset.ofTotalSeconds(0));
        return new SerialDateTime(epochSeconds, ldt);
    }

    /**
     * Return a formatted date-time string
     *
     * @return a formatted date-time
     */
    @SuppressWarnings("unused")
    @Override
    public String toDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return formatter.format(toLocalDateTime());
    }
}
