/**
 * 
 */
package com.app2square.core.api.parse.datatypes;

import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.json.Json.Element;

/**
 * @author cs
 * 
 */
public class PrimitiveType implements DataType {

    private final Json.Primitive data;

    public PrimitiveType(final Json.Primitive object) {
        this.data = object;
    }

    public PrimitiveType(final Number number) {
        this.data = new Json.Primitive(number);
    }

    public PrimitiveType(final String value) {
        this.data = new Json.Primitive(value);
    }

    public PrimitiveType(final Boolean value) {
        this.data = new Json.Primitive(value);
    }

    public Element toJsonElement() {
        return this.data;
    }

    public Object value() {
        return this.data.asObject();
    }

    public boolean isNumber() {
        return this.data.isNumber();
    }

    public Number asNumber() {
        return this.data.asNumber();
    }

    public boolean isBoolean() {
        return this.data.isBoolean();
    }

    public Boolean asBoolean() {
        return this.data.asBoolean();
    }

    public boolean isString() {
        return this.data.isString();
    }

    public String asString() {
        return this.data.asString();
    }

    @Override
    public int hashCode() {
        return this.data.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        return this == obj || obj instanceof PrimitiveType && this.data.equals(((PrimitiveType) obj).data);
    }
}
