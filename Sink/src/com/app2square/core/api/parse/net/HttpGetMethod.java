/**
 * 
 */
package com.app2square.core.api.parse.net;

import java.util.Map;

import com.app2square.core.api.parse.net.HttpConnectionFactory.Callback;

/**
 * @author cs
 * 
 */
public class HttpGetMethod extends AbstractHttpMethod implements HttpMethod {

    protected HttpGetMethod(final HttpConnectionFactory factory, final Map<String, String> headers, final String url,
            final Callback callback) {
        super(factory, headers, url, callback);
    }

    public void execute() {
        this.connectionFactory.get(url(), headers(), callback());
    }

    public String method() {
        return "GET";
    }
}
