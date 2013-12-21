/**
 * 
 */
package com.app2square.core.api.parse.user;

import com.app2square.core.api.parse.datatypes.ParseData;
import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.json.Json.Object;
import com.app2square.core.api.parse.net.DefaultHttpConnectionCallback;
import com.app2square.core.api.parse.net.DefaultHttpConnectionCallback.JsonObjectCallback;
import com.app2square.core.api.parse.net.HttpConnectionFactory.Callback;
import com.app2square.core.api.parse.net.HttpMethod;
import com.app2square.core.api.parse.net.HttpMethodFactory;
import com.app2square.core.api.parse.object.operation.ParseOperation;

/**
 * @author cs
 * 
 */
class SignUpUserOperation implements ParseOperation {

    private final ParseData data;
    private final ParseUserCallback callback;
    private final Json json;
    private final HttpMethodFactory factory;

    SignUpUserOperation(final Json json, final HttpMethodFactory factory, final ParseData data,
            final ParseUserCallback callback) {
        this.json = json;
        this.factory = factory;
        this.data = data;
        this.callback = callback;
    }

    public HttpMethod http() {
        return this.factory.post("users", this.data.toJsonObject(), callback());
    }

    private Callback callback() {
        return new DefaultHttpConnectionCallback(this.json, new JsonObjectCallback() {

            public void onSucceed(final Object object) {
                final ParseUser user = new ParseUser(object, SignUpUserOperation.this.data);
                SignUpUserOperation.this.callback.done(user);
            }

            public void onExceptionThrown(final Throwable e) {
                SignUpUserOperation.this.callback.error(e);
            }
        });
    }
}
