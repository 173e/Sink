/**
 * 
 */
package com.app2square.core.api.parse.object.operation;

import com.app2square.core.api.parse.datatypes.ListType;
import com.app2square.core.api.parse.datatypes.ParseData;
import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.net.HttpMethodFactory;
import com.app2square.core.api.parse.object.ParseObject;
import com.app2square.core.api.parse.object.ParseObjectCallback;

/**
 * @author cs
 * 
 */
public class ParseOperationFactory {

    private final Json json;
    private final HttpMethodFactory factory;
    private final ParseOperationExecutor executor;

    public ParseOperationFactory(final HttpMethodFactory factory, final Json json, final ParseOperationExecutor executor) {
        this.json = json;
        this.factory = factory;
        this.executor = executor;
    }

    public ParseOperation create(final String className, final ParseData data, final ParseObjectCallback callback) {
        return this.executor.execute(new CreateOperation(this.json, this.factory, className, data, callback))[0];
    }

    public ParseOperation update(final ParseObject object, final ParseData data, final ParseObjectCallback callback) {
        return this.executor.execute(new UpdateOperation(this.json, this.factory, object, data, callback))[0];
    }

    public ParseOperation delete(final String className, final String objectId, final ParseObjectCallback callback) {
        return this.executor.execute(new DeleteOperation(this.factory, className, objectId, callback))[0];
    }

    public ParseOperation deleteField(final ParseObject object, final String fieldName,
            final ParseObjectCallback callback) {
        return this.executor.execute(new DeleteFieldOperation(this.json, this.factory, object, fieldName, callback))[0];
    }

    public ParseOperation increment(final ParseObject object, final String fieldName, final int amount,
            final ParseObjectCallback callback) {
        return this.executor.execute(new IncrementFieldOperation(this.json, this.factory, object, fieldName, amount,
                callback))[0];
    }

    public ParseOperation listAdd(final ParseObject object, final String fieldName, final ListType list,
            final ParseObjectCallback callback) {
        return this.executor.execute(new ListAddFieldOperation(this.json, this.factory, object, fieldName, list,
                callback))[0];
    }

    public ParseOperation listAddUniqe(final ParseObject object, final String fieldName, final ListType list,
            final ParseObjectCallback callback) {
        return this.executor.execute(new ListAddUniqueFieldOperation(this.json, this.factory, object, fieldName, list,
                callback))[0];
    }

    public ParseOperation listRemove(final ParseObject object, final String fieldName, final ListType list,
            final ParseObjectCallback callback) {
        return this.executor.execute(new ListRemoveFieldOperation(this.json, this.factory, object, fieldName, list,
                callback))[0];
    }

    public ParseOperation addRelation(final ParseObject object, final String fieldName, final ListType list,
            final ParseObjectCallback callback) {
        return this.executor.execute(new AddRelationFieldOperation(this.json, this.factory, object, fieldName, list,
                callback))[0];
    }

    public ParseOperation removeRelation(final ParseObject object, final String fieldName, final ListType list,
            final ParseObjectCallback callback) {
        return this.executor.execute(new RemoveRelationFieldOperation(this.json, this.factory, object, fieldName, list,
                callback))[0];
    }
}
