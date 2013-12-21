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
public class HttpPostMethod extends AbstractHttpMethod implements HttpMethod {

    private final Json.Object payload;
    private final Json json;

    protected HttpPostMethod(final HttpConnectionFactory factory, final Json json, final Map<String, String> headers,
            final String url, final Json.Object payload, final Callback callback) {
        super(factory, headers, url, callback);
        this.json = json;
        this.payload = payload;
    }

    public void execute() {
        this.connectionFactory.post(url(), this.json.toJsonString(this.payload), headers(), callback());
    }

    public String method() {
        return "POST";
    }

    public Json.Object payload() {
        return this.payload;
    }
}
