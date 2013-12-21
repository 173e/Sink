/**
 * 
 */
package com.app2square.core.api.parse.net;

import java.util.Map;

/**
 * @author cs
 * 
 */
public interface HttpConnectionFactory {

    interface Callback {

        void onExceptionThrown(Throwable e);

        void onSucceed(int statusCode, String response);
    }

    void get(String url, Map<String, String> headers, Callback callback);

    void post(String url, String payload, Map<String, String> headers, Callback callback);

    void put(String url, String payload, Map<String, String> headers, Callback callback);

    void delete(String url, Map<String, String> headers, Callback callback);
}
