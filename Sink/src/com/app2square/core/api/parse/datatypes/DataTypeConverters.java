/**
 * 
 */
package com.app2square.core.api.parse.datatypes;

import java.util.HashMap;
import java.util.Map;

import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.json.Json.Element;

/**
 * @author cs
 * 
 */
public class DataTypeConverters {

    private static final Map<String, DataTypeConverter> typeConverters = new HashMap<String, DataTypeConverter>() {
        {
            put("Date", new DataTypeConverter() {
                public DataType convert(final Element object) {
                    return new DateType((Json.Object) object);
                }
            });

            put("Pointer", new DataTypeConverter() {
                public DataType convert(final Element object) {
                    return new PointerType((Json.Object) object);
                }
            });

            put("Object", new DataTypeConverter() {
                public DataType convert(final Element object) {
                    return new ObjectType((Json.Object) object);
                }
            });

            put("Bytes", new DataTypeConverter() {
                public DataType convert(final Element object) {
                    return new BytesType((Json.Object) object);
                }
            });
        }
    };

    private static final Map<Class<? extends Json.Element>, DataTypeConverter> converters = new HashMap<Class<? extends Json.Element>, DataTypeConverter>();

    static {

        DataTypeConverters.converters.put(Json.Object.class, new DataTypeConverter() {
            public DataType convert(final Json.Element element) {

                final DataTypeConverter typeConverter = DataTypeConverters.typeConverters.get(((Json.Object) element)
                        .get("__type"));
                if (typeConverter == null) {
                    return new ObjectType((Json.Object) element);
                }
                return typeConverter.convert(element);
            }
        });

        DataTypeConverters.converters.put(Json.Array.class, new DataTypeConverter() {
            public DataType convert(final Json.Element object) {
                return new ListType((Json.Array) object);
            }
        });

        DataTypeConverters.converters.put(Json.Primitive.class, new DataTypeConverter() {
            public DataType convert(final Json.Element object) {
                return new PrimitiveType((Json.Primitive) object);
            }
        });
    }

    public static DataType convert(final Json.Element object) {
        final DataTypeConverter converter = DataTypeConverters.converters.get(object.getClass());
        if (converter == null) {
            throw new IllegalArgumentException("Unable to convert class " + object.getClass().getName());
        }
        return converter.convert(object);
    }
}
