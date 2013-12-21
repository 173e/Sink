/**
 * 
 */
package com.app2square.core.api.parse.user;

import com.app2square.core.api.parse.net.HttpConnectionFactory;
import com.app2square.core.api.parse.net.HttpConnectionFactory.Callback;
import com.app2square.core.api.parse.net.HttpMethod;
import com.app2square.core.api.parse.net.HttpMethodFactory;
import com.app2square.core.api.parse.object.operation.ParseOperation;

/**
 * @author cs
 * 
 */
class DeleteUserOperation implements ParseOperation {

    private final ParseUserCallback callback;
    private final String objectId;
    private final HttpMethodFactory factory;

    DeleteUserOperation(final HttpMethodFactory factory, final String objectId, final ParseUserCallback callback) {
        this.factory = factory;
        this.objectId = objectId;
        this.callback = callback;
    }

    public HttpMethod http() {
        return this.factory.delete("users/" + this.objectId, callback());
    }

    private Callback callback() {
        return new HttpConnectionFactory.Callback() {

            public void onExceptionThrown(final Throwable e) {
                DeleteUserOperation.this.callback.error(e);
            }

            public void onSucceed(final int statusCode, final String response) {
                // TODO own callback for delete operations. 
                DeleteUserOperation.this.callback.done(null);
            }
        };
    }
}
