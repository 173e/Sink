/**
 * 
 */
package com.app2square.core.api.parse.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.app2square.core.api.parse.net.HttpConnectionFactory;

/**
 * @author cs
 * 
 */
public class DefaultConnectionFactory implements HttpConnectionFactory {

    private final HttpClient httpClient;

    public DefaultConnectionFactory() {
        this.httpClient = new DefaultHttpClient();
    }

    public void get(final String url, final Map<String, String> headers, final Callback callback) {
        handleRequest(new HttpGet(url), headers, callback);
    }

    public void post(final String url, final String payload, final Map<String, String> headers, final Callback callback) {
        final HttpPost request = new HttpPost(url);
        try {
            request.setEntity(new StringEntity(payload));
        } catch (final UnsupportedEncodingException e) {
            callback.onExceptionThrown(e);
        }
        handleRequest(request, headers, callback);
    }

    public void put(final String url, final String payload, final Map<String, String> headers, final Callback callback) {
        final HttpPut request = new HttpPut(url);
        try {
            request.setEntity(new StringEntity(payload));
        } catch (final UnsupportedEncodingException e) {
            callback.onExceptionThrown(e);
        }
        handleRequest(request, headers, callback);
    }

    public void delete(final String url, final Map<String, String> headers, final Callback callback) {
        final HttpEntityEnclosingRequestBase request = new HttpEntityEnclosingRequestBase() {

            @Override
            public String getMethod() {
                return "DELETE";
            }
        };
        request.setURI(URI.create(url));
        handleRequest(request, headers, callback);
    }

    private void handleRequest(final HttpUriRequest request, final Map<String, String> headers, final Callback callback) {

        for (final Map.Entry<String, String> header : headers.entrySet()) {
            request.addHeader(header.getKey(), header.getValue());
        }

        try {
            final HttpResponse response = this.httpClient.execute(request);
            final BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            final StringBuffer responseBuffer = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                responseBuffer.append(line);
            }

            callback.onSucceed(response.getStatusLine().getStatusCode(), responseBuffer.toString());

        } catch (final ClientProtocolException e) {
            callback.onExceptionThrown(e);
        } catch (final IOException e) {
            callback.onExceptionThrown(e);
        }
    }
}
