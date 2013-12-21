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
public class HttpDeleteMethod extends AbstractHttpMethod implements HttpMethod {

    protected HttpDeleteMethod(final HttpConnectionFactory factory, final Map<String, String> headers,
            final String url, final Callback callback) {
        super(factory, headers, url, callback);
    }

    public void execute() {
        this.connectionFactory.delete(url(), headers(), callback());
    }

    public String method() {
        return "DELETE";
    }

    public Json.Object payload() {
        throw new UnsupportedOperationException();
    }
}
