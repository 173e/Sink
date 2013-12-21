/**
 * 
 */
package com.app2square.core.api.parse.net;

import java.util.Map;

import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.net.HttpConnectionFactory.Callback;

/**
 * @author cs
 * 
 */
public abstract class AbstractHttpMethod {

    private final Map<String, String> headers;

    protected final HttpConnectionFactory connectionFactory;

    private final Callback callback;

    private final String url;

    protected AbstractHttpMethod(final HttpConnectionFactory factory, final Map<String, String> headers,
            final String url, final Callback callback) {
        this.connectionFactory = factory;
        this.headers = headers;
        this.url = url;
        this.callback = callback;
    }

    protected Map<String, String> headers() {
        return this.headers;
    }

    public String url() {
        return this.url;
    }

    public Callback callback() {
        return this.callback;
    }

    public Json.Object payload() {
        throw new UnsupportedOperationException();
    }
}
