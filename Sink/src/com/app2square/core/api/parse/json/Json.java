/**
 * 
 */
package com.app2square.core.api.parse.json;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author cs
 * 
 */
public interface Json {

    interface Element {

    }

    public static final class Primitive implements Element {

        private final java.lang.Object value;

        public Primitive(final java.lang.Object object) {

            if (object instanceof Number || object instanceof Boolean || object instanceof String
                    || object instanceof Character) {
                this.value = object;
            } else {
                throw new IllegalArgumentException();
            }

        }

        public boolean isNumber() {
            return this.value instanceof Number;
        }

        public Number asNumber() {
            if (this.value instanceof Number) {
                return (Number) this.value;
            }
            return null;
        }

        public Boolean asBoolean() {
            if (this.value instanceof Boolean) {
                return (Boolean) this.value;
            }
            return null;
        }

        public boolean isBoolean() {
            return this.value instanceof Boolean;
        }

        public String asString() {
            if (this.value instanceof String) {
                return (String) this.value;
            }
            return null;
        }

        public boolean isString() {
            return this.value instanceof String;
        }

        public Character asCharacter() {
            if (this.value instanceof Character) {
                return (Character) this.value;
            }
            return null;
        }

        public java.lang.Object asObject() {
            return this.value;
        }

        @Override
        public int hashCode() {
            return this.value != null ? this.value.hashCode() : super.hashCode();
        }

        @Override
        public boolean equals(final java.lang.Object obj) {
            return obj instanceof Json.Primitive && ((Json.Primitive) obj).value.equals(this.value);
        }
    }

    public static final class Array extends ArrayList<Json.Element> implements Element {

        private static final long serialVersionUID = -7522443864599576221L;

    }

    public static final class Object implements Element {

        // Using linked hashmap here, to have elements ordered for unit tests.
        private final Map<String, Element> elements = new LinkedHashMap<String, Json.Element>();

        public void put(final String name, final Element value) {
            this.elements.put(name, value);
        }

        public void remove(final String name) {
            this.elements.remove(name);
        }

        public Element get(final String name) {
            return this.elements.get(name);
        }

        public Primitive getPrimitive(final String name) {
            final Element element = get(name);
            if (element instanceof Primitive) {
                return (Primitive) element;
            }
            return null;
        }

        public Set<Entry<String, Element>> entrySet() {
            return this.elements.entrySet();
        }
    }

    Json.Element parse(String string) throws JsonParseException;

    String toJsonString(Object object);
}