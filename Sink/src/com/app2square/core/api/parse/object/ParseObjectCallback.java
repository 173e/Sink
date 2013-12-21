/**
 * 
 */
package com.app2square.core.api.parse.object;

import com.app2square.core.api.parse.query.ParseCallback;

/**
 * @author cs
 * 
 */
public interface ParseObjectCallback extends ParseCallback<ParseObject> {

    ParseObjectCallback NOP = new ParseObjectCallback() {
        public void error(final Throwable e) {
        }

        public void done(final ParseObject object) {
        }
    };
}
