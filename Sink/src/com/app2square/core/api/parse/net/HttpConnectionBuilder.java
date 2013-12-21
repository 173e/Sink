/**
 * 
 */
package com.app2square.core.api.parse.net;

import java.util.HashMap;
import java.util.Map;

import com.app2square.core.api.parse.net.HttpConnectionFactory.Callback;

/**
 * @author cs
 * 
 */
public class HttpConnectionBuilder {

    private final HttpConnectionFactory factory;

    private final Map<String, String> headers = new HashMap<String, String>();

    private final String baseUrl;

    private String payload;

    private Method method;

    private String url;

    public enum Method {
        GET, POST, DELETE, PUT;
    }

    public HttpConnectionBuilder(final String baseUrl, final HttpConnectionFactory factory) {
        this.baseUrl = baseUrl;
        this.factory = factory;
    }

    /**
     * Adding a new HTTP header element
     * @param name the name of the header element to add
     * @param value the value of the HTTP header element
     * @return this
     */
    public HttpConnectionBuilder addHeader(final String name, final String value) {
        this.headers.put(name, value);
        return this;
    }

    /**
     * Removing a HTTP header element
     * @param name the name of the header element to remove
     * @return this
     */
    public HttpConnectionBuilder removeHeader(final String name) {
        this.headers.remove(name);
        return this;
    }

    /**
     * Setting the http-method
     * 
     * @param method the method to set
     * @return this
     */
    public HttpConnectionBuilder withMethod(final Method method) {
        this.method = method;
        if (Method.POST == method || Method.PUT == method) {
            return addHeader("Content-Type", "application/json");
        }
        return this;
    }

    /**
     * Setting the payload.
     * 
     * @param payload the payload to set.
     * @return this
     */
    public HttpConnectionBuilder withPayload(final String payload) {
        System.out.println(payload);
        this.payload = payload;
        return this;
    }

    /**
     * Setting the url.
     * 
     * @param url the url to set.
     * @return this
     */
    public HttpConnectionBuilder withUrl(final String url) {
        this.url = url;
        return this;
    }

    /**
     * Executes the connection with the given method.
     * @param response the callback of the call
     */
    public void execute(final Method method, final String url, final Callback response) {
        final String finalUrl = this.baseUrl + this.url;

        switch (this.method) {
        case DELETE:
            this.factory.delete(finalUrl, this.headers, response);
            break;
        case GET:
            this.factory.get(finalUrl, this.headers, response);
            break;
        case POST:
            this.factory.post(finalUrl, this.payload, this.headers, response);
            break;
        case PUT:
            this.factory.put(finalUrl, this.payload, this.headers, response);
            break;
        }
    }
}
