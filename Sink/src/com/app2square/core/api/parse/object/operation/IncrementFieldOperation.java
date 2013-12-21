/**
 * 
 */
package com.app2square.core.api.parse.object.operation;

import com.app2square.core.api.parse.datatypes.DataType;
import com.app2square.core.api.parse.datatypes.ParseData;
import com.app2square.core.api.parse.datatypes.PrimitiveType;
import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.net.HttpMethodFactory;
import com.app2square.core.api.parse.object.ParseObject;
import com.app2square.core.api.parse.object.ParseObjectCallback;

/**
 * @author cs
 * 
 */
class IncrementFieldOperation extends AbstractUpdateOperation {

    private final String fieldName;
    private final int amount;
    private final Number value;

    IncrementFieldOperation(final Json json, final HttpMethodFactory factory, final ParseObject object,
            final String fieldName, final int amount, final ParseObjectCallback callback) {

        super(json, factory, object, callback);
        final DataType abstractValue = object.valueOf(fieldName);
        if (!(abstractValue instanceof PrimitiveType) && ((PrimitiveType) abstractValue).isNumber()) {
            throw new IllegalArgumentException(this.fieldName + " not of Type Number.");
        }
        this.fieldName = fieldName;
        this.value = ((PrimitiveType) abstractValue).asNumber();
        this.amount = amount;
    }

    @Override
    public Json.Object payload() {

        final Json.Object operationObject = new Json.Object();
        operationObject.put("__op", new Json.Primitive("Increment"));
        operationObject.put("amount", new Json.Primitive(Integer.valueOf(this.amount)));

        final Json.Object object = new Json.Object();
        object.put(this.fieldName, operationObject);

        return object;
    }

    @Override
    protected ParseData addParseData() {
        final ParseData data = new ParseData();
        data.put(this.fieldName, new PrimitiveType(this.value.longValue() + this.amount));
        return data;
    }

    @Override
    protected ParseData removeParseData() {
        return ParseData.EMPTY;
    }
}
