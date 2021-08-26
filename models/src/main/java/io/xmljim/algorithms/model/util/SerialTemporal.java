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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The base, abstract class for serializing dates and date-times to a numeric representation.
 * All subclasses of this class extend from Number, which allow for use as {@link Scalar} values
 */
@SuppressWarnings("unused")
public abstract class SerialTemporal extends Number implements Comparable<SerialTemporal> {

    private final Number epochSeconds;
    private final Temporal dateTime;

    protected static final long DAY_SECONDS = 24 * 60 * 60;

    /**
     * Constructor
     *
     * @param serialTime the numeric representation of the date/time instance
     * @param dateTime   the temporal instance (e.g., LocalDate, LocalDateTime
     */
    public SerialTemporal(Number serialTime, Temporal dateTime) {
        this.dateTime = dateTime;
        this.epochSeconds = serialTime;
    }

    /**
     * Evaluates a date string and determines whether to instantiate as a date or date-time
     * representation
     *
     * @param temporalString the date string
     * @return a new SerialTemporal instance
     */
    public static SerialTemporal of(String temporalString) {
        DateTimeFormatter formatter = getDateTimeFormatter(temporalString);

        if (formatter == DateTimeFormatter.ISO_DATE) {
            return SerialDate.of(temporalString);
        } else {
            return SerialDateTime.of(temporalString);
        }
    }

    /**
     * Convert a LocalDate into a serial numeric instance
     *
     * @param localDate the local date
     * @return a new SerialTemporal instance
     */
    public static SerialTemporal of(LocalDate localDate) {
        return SerialDate.of(localDate);
    }

    /**
     * Convert a LocalDateTime instance into a serial numeric instance
     *
     * @param localDateTime the local date-time
     * @return a new SerialTemporal instance
     */
    public static SerialTemporal of(LocalDateTime localDateTime) {
        return SerialDateTime.of(localDateTime);
    }

    /**
     * Evaluates the epoch seconds and creates an instance based on the
     * modulus of the seconds.  If the modulus of epoch seconds to number
     * of seconds within a day is 0, then it will be converted to a date,
     * otherwise to a date-time
     *
     * @param epochSeconds the number of seconds since January 1, 1970
     * @return a new SerialTemporal instance
     */
    public static SerialTemporal ofSeconds(long epochSeconds) {
        if (epochSeconds % DAY_SECONDS == 0) {
            return SerialDate.ofSeconds(epochSeconds);
        } else {
            return SerialDateTime.ofSeconds(epochSeconds);
        }
    }

    /**
     * Add days to a temporal instance and return a new instance
     *
     * @param days the number of days to add
     * @return a new SerialTemporal instance
     */
    public SerialTemporal addDays(int days) {
        if (dateTime instanceof LocalDate) {
            return SerialDate.of(toLocalDate().plusDays(days));
        } else {
            return SerialDateTime.of(toLocalDateTime().plusDays(days));
        }
    }

    /**
     * Convert the temporal instance to a LocalDate instance
     *
     * @return a new LocalDate instance
     */
    public LocalDate toLocalDate() {
        return LocalDate.of(dateTime.get(ChronoField.YEAR), dateTime.get(ChronoField.MONTH_OF_YEAR), dateTime.get(ChronoField.DAY_OF_MONTH));
    }

    /**
     * Convert a temporal instance to a LocalDateTime instance
     *
     * @return a new LocalDateTime instance
     */
    public LocalDateTime toLocalDateTime() {
        return LocalDateTime.of(dateTime.get(ChronoField.YEAR),
                dateTime.get(ChronoField.MONTH_OF_YEAR),
                dateTime.get(ChronoField.DAY_OF_MONTH),
                dateTime.get(ChronoField.HOUR_OF_DAY),
                dateTime.get(ChronoField.MINUTE_OF_HOUR),
                dateTime.get(ChronoField.SECOND_OF_MINUTE));
    }


    @Override
    public int intValue() {
        return epochSeconds.intValue();
    }

    @Override
    public long longValue() {
        return epochSeconds.longValue();
    }

    @Override
    public float floatValue() {
        return epochSeconds.floatValue();
    }

    @Override
    public double doubleValue() {
        return epochSeconds.doubleValue();
    }

    /**
     * Return a formatted date string
     *
     * @return a formatted date(-time) string
     */
    public abstract String toDateString();

    private static DateTimeFormatter getDateTimeFormatter(String dateTimeString) {
        String dateBase = "\\d{4}-\\d{2}-\\d{2}";
        String dateFormat = "^" + dateBase + "$";

        String dateTimeFormat = "^" + dateBase + "T\\d{2}:\\d{2}:\\d{2}" + "$";

        Pattern datePattern = Pattern.compile(dateFormat);
        Matcher dateMatcher = datePattern.matcher(dateTimeString);

        if (dateMatcher.matches()) {
            return DateTimeFormatter.ISO_DATE;
        } else {
            Pattern dateTimePattern = Pattern.compile(dateTimeFormat);
            Matcher dateTimeMatcher = dateTimePattern.matcher(dateTimeString);
            if (dateTimeMatcher.matches()) {
                return DateTimeFormatter.ISO_DATE_TIME;
            }
        }

        throw new IllegalArgumentException("Invalid Date/DateTime String");
    }

    public boolean equals(Object object) {
        if (object instanceof SerialTemporal) {
            return toLocalDateTime().equals(((SerialTemporal) object).toLocalDateTime());
        }

        return false;
    }

    @Override
    public int compareTo(final SerialTemporal o) {
        return Scalar.of(equals(o)).asInt();
    }
}
