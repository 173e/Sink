/**
 * 
 */
package com.app2square.core.api.parse.object.operation;

import com.app2square.core.api.parse.datatypes.ListType;
import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.net.HttpMethodFactory;
import com.app2square.core.api.parse.object.ParseObject;
import com.app2square.core.api.parse.object.ParseObjectCallback;

/**
 * @author cs
 */
class ListRemoveFieldOperation extends AbstractListOperation {

    ListRemoveFieldOperation(final Json json, final HttpMethodFactory factory, final ParseObject object,
            final String fieldName, final ListType list, final ParseObjectCallback callback) {
        super(json, factory, object, fieldName, list, callback);
    }

    @Override
    protected String operation() {
        return "Remove";
    }

    @Override
    protected void listOperation(final ListType list, final ListType value) {
        list.removeAll(value);
    }
}
