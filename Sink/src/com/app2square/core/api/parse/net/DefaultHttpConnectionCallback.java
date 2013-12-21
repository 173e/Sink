/**
 * 
 */
package com.app2square.core.api.parse.net;

import java.util.Arrays;
import java.util.List;

import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.json.JsonParseException;

/**
 * @author cs
 * 
 */
public class DefaultHttpConnectionCallback implements HttpConnectionFactory.Callback {

    private final JsonObjectCallback callback;
    private final Json json;
    private final List<Integer> expectedResponseCodes;

    public interface JsonObjectCallback {

        void onExceptionThrown(final Throwable e);

        void onSucceed(Json.Object object);
    }

    public DefaultHttpConnectionCallback(final Json json, final JsonObjectCallback callback,
            final Integer... expectedResponseCodes) {
        this.json = json;
        this.callback = callback;
        this.expectedResponseCodes = Arrays.asList(expectedResponseCodes);
    }

    public void onExceptionThrown(final Throwable e) {
        this.callback.onExceptionThrown(e);
    }

    public void onSucceed(final int statusCode, final String response) {

        if (this.expectedResponseCodes != null && !this.expectedResponseCodes.isEmpty()) {
            if (!this.expectedResponseCodes.contains(statusCode)) {
                onExceptionThrown(new IllegalStateException("Expected HTTP status code " + this.expectedResponseCodes
                        + ", but was: " + statusCode + "; Error message: " + response));
            }
        }

        try {
            this.callback.onSucceed((Json.Object) this.json.parse(response));
        } catch (final JsonParseException e) {
            this.callback.onExceptionThrown(e);
        }
    }
}
