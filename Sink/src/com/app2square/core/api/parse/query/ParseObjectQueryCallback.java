/**
 * 
 */
package com.app2square.core.api.parse.query;

import java.util.List;

import com.app2square.core.api.parse.object.ParseObject;

/**
 * @author cs
 * 
 */
public interface ParseObjectQueryCallback {

    ParseObjectQueryCallback NOP = new ParseObjectQueryCallback() {

        public void error(final Throwable e) {
        }

        public void done(final List<ParseObject> objects, final long count) {
        }
    };

    void error(Throwable e);

    /**
     * @param objects
     * @param count will only be set, when the query contains the count attribute.
     */
    void done(List<ParseObject> objects, long count);
}
