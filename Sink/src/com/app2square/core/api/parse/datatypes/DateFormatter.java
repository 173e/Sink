/**
 * 
 */
package com.app2square.core.api.parse.datatypes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author cs
 * 
 */
public class DateFormatter {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public static final Date toDate(final String date) {
        try {
            return DateFormatter.DATE_FORMAT.parse(date);
        } catch (final ParseException e) {
            throw new IllegalArgumentException("Unable to parse date.", e);
        }

    }

    public static final String fromDate(final Date date) {
        return DateFormatter.DATE_FORMAT.format(date);
    }
}
