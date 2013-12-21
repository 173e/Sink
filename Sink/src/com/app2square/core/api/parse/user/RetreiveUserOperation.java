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
class RetreiveUserOperation implements ParseOperation {

    private final Json json;
    private final String objectId;
    private final ParseUserCallback callback;
    private final HttpMethodFactory factory;

    RetreiveUserOperation(final Json json, final HttpMethodFactory factory, final String objectId,
            final ParseUserCallback callback) {
        this.json = json;
        this.factory = factory;
        this.objectId = objectId;
        this.callback = callback;
    }

    public HttpMethod http() {
        return this.factory.get("users/" + this.objectId, callback());
    }

    private Callback callback() {

        return new DefaultHttpConnectionCallback(this.json, new JsonObjectCallback() {

            public void onSucceed(final Object object) {
                final ParseData data = new ParseData(object);
                RetreiveUserOperation.this.callback.done(new ParseUser(object, data));
            }

            public void onExceptionThrown(final Throwable e) {
                RetreiveUserOperation.this.callback.error(e);
            }
        });
    }
}