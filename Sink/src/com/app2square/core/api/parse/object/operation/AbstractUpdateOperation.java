/**
 * 
 */
package com.app2square.core.api.parse.object.operation;

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
import com.app2square.core.api.parse.object.ParseObject;
import com.app2square.core.api.parse.object.ParseObjectCallback;

/**
 * @author cs
 * 
 */
abstract class AbstractUpdateOperation implements ParseOperation {

    private final ParseObject object;
    private final ParseObjectCallback callback;
    private final Json json;
    private final HttpMethodFactory factory;

    AbstractUpdateOperation(final Json json, final HttpMethodFactory factory, final ParseObject object,
            final ParseObjectCallback callback) {
        this.json = json;
        this.factory = factory;
        this.object = object;
        this.callback = callback;
    }

    public HttpMethod http() {
        return this.factory.put("classes/" + this.object.className() + "/" + this.object.objectId(), payload(),
                callback());
    }

    protected abstract Json.Object payload();

    private Callback callback() {
        return new DefaultHttpConnectionCallback(this.json, new JsonObjectCallback() {

            public void onSucceed(final Object object) {
                AbstractUpdateOperation.this.object.updated(
                        DateFormatter.toDate(object.getPrimitive(AbstractParseDataObject.UPDATED_AT).asString()),
                        addParseData(), removeParseData());
                AbstractUpdateOperation.this.callback.done(AbstractUpdateOperation.this.object);
            }

            public void onExceptionThrown(final Throwable e) {
                AbstractUpdateOperation.this.callback.error(e);
            }
        }, 200);
    }

    protected abstract ParseData addParseData();

    protected abstract ParseData removeParseData();

    protected Json json() {
        return this.json;
    }
}
