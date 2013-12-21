/**
 * 
 */
package com.app2square.core.api.parse.datatypes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.json.Json.Element;

/**
 * @author cs
 * 
 */
public class ParseData {

    public static final ParseData EMPTY = new ParseData(new Json.Object());

    final Map<String, DataType> data = new HashMap<String, DataType>();

    public ParseData() {

    }

    public ParseData(final Json.Object object) {
        final Iterator<Map.Entry<String, Json.Element>> iter = object.entrySet().iterator();
        while (iter.hasNext()) {
            final Entry<String, Element> element = iter.next();
            put(element.getKey(), DataTypeConverters.convert(element.getValue()));
        }
    }

    public void put(final String fieldName, final DataType data) {
        this.data.put(fieldName, data);
    }

    public Json.Object toJsonObject() {

        final Json.Object object = new Json.Object();

        for (final Map.Entry<String, DataType> element : this.data.entrySet()) {
            object.put(element.getKey(), element.getValue().toJsonElement());
        }
        return object;
    }

    public void putAll(final ParseData data) {
        this.data.putAll(data.data);
    }

    public void removeAll(final ParseData data) {
        for (final String key : data.data.keySet()) {
            this.data.remove(key);
        }
    }

    public DataType get(final String key) {
        return this.data.get(key);
    }

    public void put(final String fieldName, final String value) {
        this.data.put(fieldName, new PrimitiveType(value));
    }

    public void put(final String fieldName, final Number value) {
        this.data.put(fieldName, new PrimitiveType(value));
    }

    public void put(final String fieldName, final Boolean value) {
        this.data.put(fieldName, new PrimitiveType(value));
    }

}
