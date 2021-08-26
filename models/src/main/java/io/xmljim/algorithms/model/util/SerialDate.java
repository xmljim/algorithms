
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A numeric representation of a {@link LocalDate}. The numeric representation
 * is stored as Epoch Seconds (the number of seconds since January 1, 1970).
 * SerialDate extends from number and can be used as a {@link Scalar} value
 */
@SuppressWarnings("unused")
public final class SerialDate extends SerialTemporal {
    //private static final LocalDate BASE_DATE = LocalDate.of(1970, 01, 01);

    /**
     * Private constructor. Use static #of methods
     *
     * @param epochSeconds The number of seconds since January 1, 1970
     * @param localDate    the local date instance
     */
    private SerialDate(Number epochSeconds, LocalDate localDate) {
        super(epochSeconds, localDate);
    }

    /**
     * Parse a date string into a local date and convert to a numeric representation
     *
     * @param dateString the date string
     * @return a new SerialDate instance
     */
    public static SerialDate of(String dateString) {
        LocalDate ld = LocalDate.parse(dateString);
        long epochSeconds = ld.toEpochDay() * DAY_SECONDS;
        return new SerialDate(epochSeconds, ld);
    }

    /**
     * Convert a LocalDate instance to a numeric representation
     *
     * @param localDate the localDate instance
     * @return a new SerialDate instance
     */
    public static SerialDate of(LocalDate localDate) {
        long epochSeconds = localDate.toEpochDay() * DAY_SECONDS;
        return new SerialDate(epochSeconds, localDate);
    }

    /**
     * Compute a serial date from the number of epoch seconds
     *
     * @param epochSeconds the number of seconds since January 1, 1970
     * @return a new SerialDate instance
     */
    public static SerialDate ofSeconds(long epochSeconds) {
        long remainder = epochSeconds % DAY_SECONDS;
        long epochDay = epochSeconds / DAY_SECONDS - remainder;
        return new SerialDate(epochSeconds - remainder, LocalDate.ofEpochDay(epochDay));
    }

    /**
     * Compute a serial date from the number of days since January 1, 1970
     *
     * @param epochDays the number of days since January 1, 1970
     * @return a new SerialDate instance
     */
    public static SerialDate ofDays(long epochDays) {
        long epochSeconds = epochDays * DAY_SECONDS;
        return new SerialDate(epochSeconds, LocalDate.ofEpochDay(epochDays));
    }

    /**
     * Return a formatted date string
     *
     * @return a formatted date
     */
    public String toDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        return formatter.format(toLocalDate());
    }

    /**
     * Returns the numeric value as a string
     *
     * @return the numeric value as a string
     */
    public String toString() {
        return String.valueOf(longValue());
    }
}
