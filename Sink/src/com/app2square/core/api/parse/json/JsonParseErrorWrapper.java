/**
 * 
 */
package com.app2square.core.api.parse.json;

/**
 * This class delegates the parsing to the json implementation, filters error messages from parse.com
 * @author cs
 */
public class JsonParseErrorWrapper implements Json {

    private final Json json;

    public JsonParseErrorWrapper(final Json json) {
        this.json = json;
    }

    public Element parse(final String string) throws JsonParseException {

        final Json.Element element = this.json.parse(string);

        if (element instanceof Json.Object) {
            final Primitive error = ((Json.Object) element).getPrimitive("error");
            if (error != null && error.isString()) {
                throw new JsonParseException("parse.com error message: " + error);
            }
        }

        return element;
    }

    public String toJsonString(final Object object) {
        return this.json.toJsonString(object);
    }

}
