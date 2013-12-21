/**
 * 
 */
package com.app2square.core.api.parse.query;

import com.app2square.core.api.parse.datatypes.ParseData;
import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.json.Json.Object;
import com.app2square.core.api.parse.net.DefaultHttpConnectionCallback;
import com.app2square.core.api.parse.net.DefaultHttpConnectionCallback.JsonObjectCallback;
import com.app2square.core.api.parse.net.HttpConnectionFactory.Callback;
import com.app2square.core.api.parse.net.HttpMethod;
import com.app2square.core.api.parse.net.HttpMethodFactory;
import com.app2square.core.api.parse.object.ParseObject;
import com.app2square.core.api.parse.object.ParseObjectCallback;
import com.app2square.core.api.parse.object.operation.ParseOperation;

/**
 * @author cs
 * 
 */
public class RetreiveOperation implements ParseOperation {

    private final Json json;
    private final String className;
    private final String objectId;
    private final ParseObjectCallback callback;
    private final HttpMethodFactory factory;

    public RetreiveOperation(final Json json, final HttpMethodFactory factory, final String className,
            final String objectId, final ParseObjectCallback callback) {
        this.json = json;
        this.factory = factory;
        this.className = className;
        this.objectId = objectId;
        this.callback = callback;
    }

    public HttpMethod http() {
        return this.factory.get("classes/" + this.className + "/" + this.objectId, callback());
    }

    private Callback callback() {

        return new DefaultHttpConnectionCallback(this.json, new JsonObjectCallback() {

            public void onSucceed(final Object object) {
                final ParseData data = new ParseData(object);
                RetreiveOperation.this.callback.done(new ParseObject(RetreiveOperation.this.className, object, data));
            }

            public void onExceptionThrown(final Throwable e) {
                RetreiveOperation.this.callback.error(e);
            }
        });
    }
}