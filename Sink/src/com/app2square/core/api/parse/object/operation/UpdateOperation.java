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
class UpdateOperation extends AbstractUpdateOperation {

    private final ParseData data;

    UpdateOperation(final Json json, final HttpMethodFactory factory, final ParseObject object, final ParseData data,
            final ParseObjectCallback callback) {
        super(json, factory, object, callback);
        this.data = data;
    }

    @Override
    protected Json.Object payload() {
        return this.data.toJsonObject();
    }

    @Override
    protected ParseData addParseData() {
        return this.data;
    }

    @Override
    protected ParseData removeParseData() {
        return ParseData.EMPTY;
    }
}
