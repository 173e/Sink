/**
 * 
 */
package com.app2square.core.api.parse.object.operation;

import com.app2square.core.api.parse.datatypes.DataType;
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
abstract class AbstractRelationOperation extends AbstractUpdateOperation {

    private final String fieldName;
    private final ListType value;
    private final ListType list;

    AbstractRelationOperation(final Json json, final HttpMethodFactory factory, final ParseObject object,
            final String fieldName, final ListType list, final ParseObjectCallback callback) {

        super(json, factory, object, callback);
        final DataType abstractValue = object.valueOf(fieldName);
        if (list.pointerTypeList().isEmpty()) {
            throw new IllegalArgumentException("No pointer objects found in list. ");
        }

        this.fieldName = fieldName;
        this.value = abstractValue == null ? ListType.EMPTY : (ListType) abstractValue;
        this.list = list;
    }

    @Override
    protected final Json.Object payload() {
        final Json.Object object = new Json.Object();
        final Json.Object operationObject = new Json.Object();
        operationObject.put("__op", new Json.Primitive(operation()));
        operationObject.put("objects", this.list.toJsonElement());
        object.put(this.fieldName, operationObject);
        return object;
    }

    protected abstract String operation();

    @Override
    protected final ParseData addParseData() {
        final ParseData data = new ParseData();
        listOperation(this.list, this.value);
        data.put(this.fieldName, this.value);
        return data;
    }

    @Override
    protected ParseData removeParseData() {
        return ParseData.EMPTY;
    }

    protected abstract void listOperation(ListType list, ListType value);
}
