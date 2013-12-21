/**
 * 
 */
package com.app2square.core.api.parse.datatypes;

import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.json.Json.Element;
import com.app2square.core.api.parse.json.Json.Object;

/**
 * @author cs
 * 
 */
public class PointerType implements DataType {

    private final Json.Object object;

    public PointerType(final String className, final String objectId) {
        this.object = new Json.Object();
        this.object.put("__type", new Json.Primitive("Pointer"));
        this.object.put("className", new Json.Primitive(className));
        this.object.put("objectId", new Json.Primitive(objectId));
    }

    public PointerType(final Object object) {
        final Element type = object.get("__type");
        if (type == null || !(type instanceof Json.Primitive) || !"Pointer".equals(((Json.Primitive) type).asString())) {
            throw new IllegalArgumentException("Object is not of Type Pointer.");
        }

        this.object = object;
    }

    public Element toJsonElement() {
        return this.object;
    }

    public String className() {
        return this.object.getPrimitive("className").asString();
    }

    public String objectId() {
        return this.object.getPrimitive("objectId").asString();
    }

}
