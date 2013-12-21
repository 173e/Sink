/**
 * 
 */
package com.app2square.core.api.parse.net;

import java.util.Map;

import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.json.Json.Object;
import com.app2square.core.api.parse.net.HttpConnectionFactory.Callback;

/**
 * @author cs
 * 
 */
public class HttpPutMethod extends AbstractHttpMethod implements HttpMethod {

    private final Json.Object payload;
    private final Json json;

    protected HttpPutMethod(final HttpConnectionFactory factory, final Json json, final Map<String, String> headers,
            final String url, final Json.Object payload, final Callback callback) {
        super(factory, headers, url, callback);
        this.json = json;
        this.payload = payload;
    }

    public void execute() {
        this.connectionFactory.put(url(), this.json.toJsonString(this.payload), headers(), callback());
    }

    public String method() {
        return "PUT";
    }

    @Override
    public Object payload() {
        return this.payload;
    }
}
