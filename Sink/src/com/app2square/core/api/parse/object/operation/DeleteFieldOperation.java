/**
 * 
 */
package com.app2square.core.api.parse.object.operation;

import com.app2square.core.api.parse.datatypes.ParseData;
import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.net.HttpMethodFactory;
import com.app2square.core.api.parse.object.ParseObject;
import com.app2square.core.api.parse.object.ParseObjectCallback;

/**
 * @author cs
 * 
 */
class DeleteFieldOperation extends AbstractUpdateOperation {

    private final String fieldName;

    DeleteFieldOperation(final Json json, final HttpMethodFactory factory, final ParseObject object,
            final String fieldName, final ParseObjectCallback callback) {
        super(json, factory, object, callback);
        this.fieldName = fieldName;
    }

    @Override
    public Json.Object payload() {
        final Json.Object object = new Json.Object();
        final Json.Object operation = new Json.Object();
        operation.put("__op", new Json.Primitive("Delete"));
        object.put(this.fieldName, operation);
        return object;
    }

    @Override
    protected ParseData addParseData() {
        return ParseData.EMPTY;
    }

    @Override
    protected ParseData removeParseData() {
        final ParseData data = new ParseData();
        data.put(this.fieldName, "");
        return data;
    }
}
