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
import com.app2square.core.api.parse.net.UrlEncoder;
import com.app2square.core.api.parse.object.operation.ParseOperation;

/**
 * @author cs
 * 
 */
class LoginUserOperation implements ParseOperation {

    private final ParseUserCallback callback;
    private final Json json;
    private final String username;
    private final String password;
    private final UrlEncoder encoder;
    private final HttpMethodFactory factory;

    LoginUserOperation(final Json json, final HttpMethodFactory factory, final UrlEncoder encoder,
            final String username, final String password, final ParseUserCallback callback) {
        this.json = json;
        this.factory = factory;
        this.encoder = encoder;
        this.username = username;
        this.password = password;
        this.callback = callback;
    }

    public HttpMethod http() {
        return this.factory.get(
                "login?username=" + this.encoder.encode(this.username) + "&password="
                        + this.encoder.encode(this.password), callback());
    }

    private Callback callback() {
        return new DefaultHttpConnectionCallback(this.json, new JsonObjectCallback() {

            public void onSucceed(final Object object) {
                LoginUserOperation.this.callback.done(new ParseUser(object, new ParseData(object)));
            }

            public void onExceptionThrown(final Throwable e) {
                LoginUserOperation.this.callback.error(e);
            }
        }, 200);
    }
}
