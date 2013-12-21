/**
 * 
 */
package com.app2square.core.api.parse;

import java.util.Map;

import com.app2square.core.api.parse.net.HttpConnectionFactory;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;

/**
 * @author cs
 * 
 */
public class LibgdxHttpConnectionFactory implements HttpConnectionFactory {

    public void put(final String url, final String payload, final Map<String, String> headers, final Callback callback) {
        sendHttpRequest(HttpMethods.PUT, url, payload, headers, callback);
    }

    public void post(final String url, final String payload, final Map<String, String> headers, final Callback callback) {
        sendHttpRequest(HttpMethods.POST, url, payload, headers, callback);
    }

    public void get(final String url, final Map<String, String> headers, final Callback callback) {
        sendHttpRequest(HttpMethods.GET, url, "", headers, callback);
    }

    public void delete(final String url, final Map<String, String> headers, final Callback callback) {
        sendHttpRequest(HttpMethods.DELETE, url, "", headers, callback);
    }

    private void sendHttpRequest(final String method, final String url, final String payload,
            final Map<String, String> headers, final Callback callback) {
        final HttpRequest request = new HttpRequest(method);
        request.setUrl(url);
        request.setContent(payload);
        for (final Map.Entry<String, String> header : headers.entrySet()) {
            request.setHeader(header.getKey(), header.getValue());
        }
        Gdx.net.sendHttpRequest(request, new HttpResponseListener() {

            public void handleHttpResponse(final HttpResponse httpResponse) {
                callback.onSucceed(httpResponse.getStatus().getStatusCode(), httpResponse.getResultAsString());
            }

            public void failed(final Throwable t) {
                callback.onExceptionThrown(t);
            }
        });
    }
}
