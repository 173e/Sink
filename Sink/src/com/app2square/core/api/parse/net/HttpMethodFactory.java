/**
 * 
 */
package com.app2square.core.api.parse.net;

import java.util.HashMap;
import java.util.Map;

import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.net.HttpConnectionFactory.Callback;

/**
 * @author cs
 * 
 */
public class HttpMethodFactory {

    private final HttpConnectionFactory factory;

    private final Map<String, String> headers = new HashMap<String, String>();

    private final Json json;

    private final String baseUrl;

    public HttpMethodFactory(final String baseUrl, final HttpConnectionFactory factory, final Json json) {
        this.baseUrl = baseUrl;
        this.factory = factory;
        this.json = json;
    }

    public HttpMethod get(final String url, final Callback callback) {
        return new HttpGetMethod(this.factory, this.headers, this.baseUrl + url, callback);
    }

    public HttpMethod delete(final String url, final Callback callback) {
        return new HttpDeleteMethod(this.factory, this.headers, this.baseUrl + url, callback);
    }

    public HttpMethod post(final String url, final Json.Object payload, final Callback callback) {
        return new HttpPostMethod(this.factory, this.json, this.headers, this.baseUrl + url, payload, callback);
    }

    public HttpMethod put(final String url, final Json.Object payload, final Callback callback) {
        return new HttpPutMethod(this.factory, this.json, this.headers, this.baseUrl + url, payload, callback);
    }

    public void addHeader(final String name, final String value) {
        this.headers.put(name, value);
    }

    public void removeHeader(final String name) {
        this.headers.remove(name);
    }
}
