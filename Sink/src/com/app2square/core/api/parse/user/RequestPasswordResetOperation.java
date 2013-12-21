/**
 * 
 */
package com.app2square.core.api.parse.user;

import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.net.HttpConnectionFactory;
import com.app2square.core.api.parse.net.HttpConnectionFactory.Callback;
import com.app2square.core.api.parse.net.HttpMethod;
import com.app2square.core.api.parse.net.HttpMethodFactory;
import com.app2square.core.api.parse.object.operation.ParseOperation;

/**
 * @author cs
 * 
 */
class RequestPasswordResetOperation implements ParseOperation {

    private final ParseUserCallback callback;
    private final String email;
    private final HttpMethodFactory factory;

    RequestPasswordResetOperation(final HttpMethodFactory factory, final String email, final ParseUserCallback callback) {
        this.factory = factory;
        this.email = email;
        this.callback = callback;
    }

    public HttpMethod http() {
        return this.factory.post("requestPasswordReset", payload(), callback());
    }

    private Json.Object payload() {
        final Json.Object object = new Json.Object();
        object.put("email", new Json.Primitive(this.email));
        return object;
    }

    private Callback callback() {
        return new HttpConnectionFactory.Callback() {

            public void onExceptionThrown(final Throwable e) {
                RequestPasswordResetOperation.this.callback.error(e);
            }

            public void onSucceed(final int statusCode, final String response) {
                RequestPasswordResetOperation.this.callback.done(null);
            }
        };
    }
}
