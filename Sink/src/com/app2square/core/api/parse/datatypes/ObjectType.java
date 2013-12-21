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
public class ObjectType implements DataType {

    private final Json.Object object;

    public ObjectType(final Json.Object object) {
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
