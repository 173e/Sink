/**
 * 
 */
package com.app2square.core.api.parse.user;

import com.app2square.core.api.parse.datatypes.DateFormatter;
import com.app2square.core.api.parse.datatypes.ParseData;
import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.json.Json.Object;
import com.app2square.core.api.parse.net.DefaultHttpConnectionCallback;
import com.app2square.core.api.parse.net.DefaultHttpConnectionCallback.JsonObjectCallback;
import com.app2square.core.api.parse.net.HttpConnectionFactory.Callback;
import com.app2square.core.api.parse.net.HttpMethod;
import com.app2square.core.api.parse.net.HttpMethodFactory;
import com.app2square.core.api.parse.object.AbstractParseDataObject;
import com.app2square.core.api.parse.object.operation.ParseOperation;

/**
 * @author cs
 * 
 */
class UpdateUserOperation implements ParseOperation {

    private final ParseUser user;
    private final ParseUserCallback callback;
    private final Json json;
    private final HttpMethodFactory factory;
    private final ParseData data;

    UpdateUserOperation(final Json json, final HttpMethodFactory factory, final ParseUser user, final ParseData data,
            final ParseUserCallback callback) {
        this.json = json;
        this.factory = factory;
        this.user = user;
        this.callback = callback;
        this.data = data;
    }

    public HttpMethod http() {
        return this.factory.put("users/" + this.user.objectId(), this.data.toJsonObject(), callback());
    }

    private Callback callback() {
        return new DefaultHttpConnectionCallback(this.json, new JsonObjectCallback() {

            public void onSucceed(final Object object) {
                UpdateUserOperation.this.user.updated(
                        DateFormatter.toDate(object.getPrimitive(AbstractParseDataObject.UPDATED_AT).asString()),
                        UpdateUserOperation.this.data, ParseData.EMPTY);
                UpdateUserOperation.this.callback.done(UpdateUserOperation.this.user);
            }

            public void onExceptionThrown(final Throwable e) {
                UpdateUserOperation.this.callback.error(e);
            }
        });
    }
}