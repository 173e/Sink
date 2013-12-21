/**
 * 
 */
package com.app2square.core.api.parse.user;

import com.app2square.core.api.parse.query.ParseCallback;

/**
 * @author cs
 * 
 */
public interface ParseUserCallback extends ParseCallback<ParseUser> {

    ParseUserCallback NOP = new ParseUserCallback() {
        public void error(final Throwable e) {
        }

        public void done(final ParseUser user) {
        }
    };

}
