/**
 * 
 */
package com.app2square.core.api.parse.user;

import java.util.List;

/**
 * @author cs
 * 
 */
public interface ParseUserQueryCallback {

    ParseUserQueryCallback NOP = new ParseUserQueryCallback() {

        public void error(final Throwable e) {
        }

        public void done(final List<ParseUser> objects, final long count) {
        }
    };

    void error(Throwable e);

    /**
     * @param objects
     * @param count will only be set, when the query contains the count attribute.
     */
    void done(List<ParseUser> objects, long count);
}
