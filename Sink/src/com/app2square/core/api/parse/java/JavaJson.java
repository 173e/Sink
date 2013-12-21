/**
 * 
 */
package com.app2square.core.api.parse.java;

import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.json.JsonParseException;

/**
 * @author cs
 * 
 */
public class JavaJson implements Json {

    public Json.Element parse(final String string) throws JsonParseException {

        try {
            final java.lang.Object parse = new JSONParser().parse(string);
            if (parse instanceof JSONObject) {
                final JSONObject parsed = (JSONObject) parse;
                return Converter.toObject(parsed);
            } else if (parse instanceof JSONArray) {
                final JSONArray parsed = (JSONArray) parse;
                return Converter.toArray(parsed);
            } else {
                throw new UnsupportedOperationException();
            }

        } catch (final ParseException e) {
            throw new JsonParseException("Unable to parse string: " + string, e);
        } catch (final ClassCastException e) {
            throw new JsonParseException(e);
        }
    }

    public String toJsonString(final Json.Object object) {
        return JSONObject.toJSONString(Converter.from(object));
    }

    private static final class Converter {

        private static JSONArray from(final Json.Array array) {
            final JSONArray jsonArray = new JSONArray();
            for (final Json.Element element : array) {
                jsonArray.add(Converter.fromElement(element));
            }
            return jsonArray;
        }

        private static JSONObject from(final Json.Object element) {

            final JSONObject jsonObject = new JSONObject();
            for (final Map.Entry<String, Element> entry : element.entrySet()) {
                jsonObject.put(entry.getKey(), Converter.fromElement(entry.getValue()));
            }
            return jsonObject;
        }

        private static java.lang.Object fromElement(final Element element) {

            if (element instanceof Json.Primitive) {
                return ((Json.Primitive) element).asObject();
            } else if (element instanceof Json.Object) {
                return Converter.from((Json.Object) element);
            } else if (element instanceof Array) {
                return Converter.from((Array) element);
            } else {
                throw new IllegalArgumentException();
            }
        }

        private static Element toElement(final java.lang.Object object) {
            if (object instanceof JSONArray) {
                return Converter.toArray((JSONArray) object);
            } else if (object instanceof JSONObject) {
                return Converter.toObject((JSONObject) object);
            } else {
                return new Json.Primitive(object);
            }
        }

        private static Object toObject(final JSONObject jsonObject) {
            final Json.Object object = new Json.Object();
            for (final java.lang.Object entry : jsonObject.entrySet()) {
                final Map.Entry jsonElement = (Map.Entry) entry;
                object.put((String) jsonElement.getKey(), Converter.toElement(jsonElement.getValue()));
            }
            return object;
        }

        private static Array toArray(final JSONArray jsonArray) {
            final Json.Array array = new Json.Array();
            for (final java.lang.Object o : jsonArray) {
                array.add(Converter.toElement(o));
            }
            return array;
        }
    }
}
