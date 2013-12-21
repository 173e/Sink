/**
 * 
 */
package com.app2square.core.api.parse.datatypes;

import biz.source_code.base64Coder.Base64Coder;

import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.json.Json.Element;

/**
 * @author cs
 * 
 */
public class BytesType implements DataType {

    private final Json.Object data;

    public BytesType(final byte[] bytes) {
        this.data = new Json.Object();
        this.data.put("__type", new Json.Primitive("Bytes"));
        this.data.put("base64", new Json.Primitive(Base64Coder.encodeLines(bytes)));
    }

    public BytesType(final Json.Object object) {
        this.data = object;
    }

    public Element toJsonElement() {
        return this.data;
    }

    public byte[] value() {
        return Base64Coder.decode(((Json.Primitive) this.data.get("base64")).asString());
    }
}
