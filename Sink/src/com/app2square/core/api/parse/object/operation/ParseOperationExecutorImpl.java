/**
 * 
 */
package com.app2square.core.api.parse.object.operation;

import com.app2square.core.api.parse.ParseFactory;
import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.json.JsonParseException;
import com.app2square.core.api.parse.net.HttpConnectionFactory;
import com.app2square.core.api.parse.net.HttpConnectionFactory.Callback;
import com.app2square.core.api.parse.net.HttpMethodFactory;

/**
 * @author cs
 * 
 */
public class ParseOperationExecutorImpl implements ParseOperationExecutor {

    private final Json json;
    private final HttpMethodFactory factory;

    public ParseOperationExecutorImpl(final HttpMethodFactory factory, final Json json) {
        this.factory = factory;
        this.json = json;
    }

    /**
     * {@inheritDoc}
     */
    public ParseOperation[] execute(final ParseOperation... operations) {

        if (operations.length == 1) {
            operations[0].http().execute();
        } else {

            final Json.Array array = new Json.Array();

            for (final ParseOperation operation : operations) {
                final Json.Object operationElement = new Json.Object();

                operationElement.put("method", new Json.Primitive(operation.http().method()));
                operationElement.put("path", new Json.Primitive(ParseFactory.API_VERSION + operation.http().url()));
                operationElement.put("body", operation.http().payload());

                array.add(operationElement);
            }

            final Json.Object payload = new Json.Object();
            payload.put("requests", array);
            this.factory.post("batch", payload, batchCallback(operations)).execute();
        }
        return operations;
    }

    private Callback batchCallback(final ParseOperation[] operations) {
        return new HttpConnectionFactory.Callback() {

            public void onSucceed(final int statusCode, final String response) {

                try {
                    final Json.Array object = (Json.Array) ParseOperationExecutorImpl.this.json.parse(response);

                    for (int i = 0; i < operations.length; i++) {
                        final ParseOperation operation = operations[i];
                        // TODO onSucceed callback with Json object to avoid convert from string, to String, from string
                        operation
                                .http()
                                .callback()
                                .onSucceed(
                                        statusCode,
                                        ParseOperationExecutorImpl.this.json
                                                .toJsonString((Json.Object) ((Json.Object) object.get(i))
                                                        .get("success")));
                    }

                } catch (final JsonParseException e) {
                    onExceptionThrown(e);
                }
            }

            public void onExceptionThrown(final Throwable e) {
                for (final ParseOperation operation : operations) {
                    operation.http().callback().onExceptionThrown(e);
                }
            }
        };
    }
}
