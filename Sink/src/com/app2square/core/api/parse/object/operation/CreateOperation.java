package com.app2square.core.api.parse.object.operation;

import com.app2square.core.api.parse.datatypes.ParseData;
import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.json.Json.Object;
import com.app2square.core.api.parse.net.DefaultHttpConnectionCallback;
import com.app2square.core.api.parse.net.DefaultHttpConnectionCallback.JsonObjectCallback;
import com.app2square.core.api.parse.net.HttpConnectionFactory;
import com.app2square.core.api.parse.net.HttpMethod;
import com.app2square.core.api.parse.net.HttpMethodFactory;
import com.app2square.core.api.parse.object.ParseObject;
import com.app2square.core.api.parse.object.ParseObjectCallback;

/**
 * @author cs
 * 
 */
class CreateOperation implements ParseOperation {

    private final ParseData data;
    private final String className;
    private final ParseObjectCallback callback;
    private final Json json;
    private final HttpMethodFactory factory;

    CreateOperation(final Json json, final HttpMethodFactory factory, final String className, final ParseData data,
            final ParseObjectCallback callback) {
        this.json = json;
        this.factory = factory;
        this.className = className;
        this.data = data;
        this.callback = callback;
    }

    public HttpMethod http() {
        return this.factory.post("classes/" + this.className, this.data.toJsonObject(), callback());
    }

    private HttpConnectionFactory.Callback callback() {
        return new DefaultHttpConnectionCallback(this.json, new JsonObjectCallback() {
            public void onSucceed(final Object object) {
                CreateOperation.this.callback.done(new ParseObject(CreateOperation.this.className, object,
                        CreateOperation.this.data));
            }

            public void onExceptionThrown(final Throwable e) {
                CreateOperation.this.callback.error(e);
            }
        });

    }
}
