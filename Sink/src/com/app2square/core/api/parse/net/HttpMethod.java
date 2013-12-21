/**
 * 
 */
package com.app2square.core.api.parse.net;

import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.net.HttpConnectionFactory.Callback;

/**
 * @author cs
 * 
 */
public interface HttpMethod {

    void execute();

    String method();

    String url();

    Json.Object payload();

    Callback callback();
}