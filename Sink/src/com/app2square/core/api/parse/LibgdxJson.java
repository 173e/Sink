/**
 * 
 */
package com.app2square.core.api.parse;

import java.util.Map;

import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.json.JsonParseException;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter.OutputType;

/**
 * @author cs
 * 
 */
public class LibgdxJson implements Json {

    private final com.badlogic.gdx.utils.Json libgdxJson;

    LibgdxJson() {
        this.libgdxJson = new com.badlogic.gdx.utils.Json(OutputType.json);
        this.libgdxJson.setSerializer(Json.Object.class, new LibgdxObjectSerializer());
        this.libgdxJson.setSerializer(Json.Array.class, new LibgdxArraySerializer());
        this.libgdxJson.setSerializer(Json.Primitive.class, new LibgdxPrimitiveSerializer());
    }

    public String toJsonString(final Json.Object object) {
        return this.libgdxJson.toJson(object);
    }

    public Element parse(final String string) throws JsonParseException {
        final ParseJsonReader jsonReader = new ParseJsonReader();
        jsonReader.parse(string);
        return jsonReader.element();
    }

    private static final class ParseJsonReader extends JsonReader {

        private static final class ParentChildElement {
            private final ParentChildElement parentElement;
            private final Json.Element childElement;

            public ParentChildElement(final ParentChildElement parentElement, final Json.Element childElement) {
                this.parentElement = parentElement;
                this.childElement = childElement;
            }
        }

        private ParentChildElement currentElement;

        @Override
        protected void startObject(final String name) {

            final Json.Object value = new Json.Object();
            if (this.currentElement != null) {
                add(name, value);
            }
            this.currentElement = new ParentChildElement(this.currentElement, value);
        }

        @Override
        protected void startArray(final String name) {

            final Json.Array array = new Json.Array();
            if (this.currentElement != null) {
                add(name, array);
            }
            this.currentElement = new ParentChildElement(this.currentElement, array);
        }

        @Override
        protected void pop() {
            if (this.currentElement.parentElement != null) {
                this.currentElement = this.currentElement.parentElement;
            }
        }

        @Override
        protected void string(final String name, final String value) {
            add(name, new Json.Primitive(value));
        }

        @Override
        protected void number(final String name, final double value) {
            add(name, new Json.Primitive(value));
        }

        @Override
        protected void number(final String name, final long value) {
            add(name, new Json.Primitive(value));
        }

        @Override
        protected void bool(final String name, final boolean value) {
            add(name, new Json.Primitive(value));
        }

        private void add(final String name, final Json.Element value) {
            if (this.currentElement.childElement instanceof Json.Object) {
                ((Json.Object) this.currentElement.childElement).put(name, value);
            } else if (this.currentElement.childElement instanceof Json.Array) {
                ((Json.Array) this.currentElement.childElement).add(value);
            } else {
                throw new IllegalArgumentException("Unable to parse Json String!");
            }
        }

        public Json.Element element() {
            return this.currentElement.childElement;
        }
    }

    private static class LibgdxObjectSerializer implements com.badlogic.gdx.utils.Json.Serializer<Json.Object> {

        public void write(final com.badlogic.gdx.utils.Json json, final Json.Object object, final Class knownType) {
            json.writeObjectStart();
            for (final Map.Entry<String, Json.Element> element : object.entrySet()) {
                json.writeValue(element.getKey(), element.getValue());
            }
            json.writeObjectEnd();
        }

        public Object read(final com.badlogic.gdx.utils.Json json, final JsonValue jsonData, final Class type) {
            throw new UnsupportedOperationException("");
        }

    }

    private static class LibgdxArraySerializer implements com.badlogic.gdx.utils.Json.Serializer<Json.Array> {

        public void write(final com.badlogic.gdx.utils.Json json, final Json.Array array, final Class knownType) {
            json.writeArrayStart();
            for (final Json.Element element : array) {
                json.writeValue(element);
            }
            json.writeArrayEnd();
        }

        public Array read(final com.badlogic.gdx.utils.Json json, final JsonValue jsonData, final Class type) {
            throw new UnsupportedOperationException("");
        }

    }

    private static class LibgdxPrimitiveSerializer implements com.badlogic.gdx.utils.Json.Serializer<Json.Primitive> {

        public void write(final com.badlogic.gdx.utils.Json json, final Json.Primitive primitive, final Class knownType) {
            json.writeValue(primitive.asObject());
        }

        public Json.Primitive read(final com.badlogic.gdx.utils.Json json, final JsonValue jsonData, final Class type) {
            throw new UnsupportedOperationException("");
        }
    }

}
