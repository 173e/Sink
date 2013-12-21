/**
 * 
 */
package com.app2square.core.api.parse.object.operation;

import com.app2square.core.api.parse.net.HttpConnectionFactory;
import com.app2square.core.api.parse.net.HttpConnectionFactory.Callback;
import com.app2square.core.api.parse.net.HttpMethod;
import com.app2square.core.api.parse.net.HttpMethodFactory;
import com.app2square.core.api.parse.object.ParseObjectCallback;

/**
 * @author cs
 * 
 */
class DeleteOperation implements ParseOperation {

    private final ParseObjectCallback callback;
    private final String objectId;
    private final String className;
    private final HttpMethodFactory factory;

    DeleteOperation(final HttpMethodFactory factory, final String className, final String objectId,
            final ParseObjectCallback callback) {
        this.factory = factory;
        this.className = className;
        this.objectId = objectId;
        this.callback = callback;
    }

    public HttpMethod http() {
        return this.factory.delete("classes/" + this.className + "/" + this.objectId, callback());
    }

    private Callback callback() {
        return new HttpConnectionFactory.Callback() {

            public void onExceptionThrown(final Throwable e) {
                DeleteOperation.this.callback.error(e);
            }

            public void onSucceed(final int statusCode, final String response) {
                if (statusCode != 200) {
                    DeleteOperation.this.callback.error(new IllegalStateException(
                            "Expected HTTP status code 200, but was: " + statusCode + "; Error message: " + response));
                }
                // TODO own callback for delete operations. 
                DeleteOperation.this.callback.done(null);
            }
        };
    }
}
