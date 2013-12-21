/**
 * 
 */
package com.app2square.core.api.parse.datatypes;

import com.app2square.core.api.parse.json.Json;

/**
 * @author cs
 * 
 */
public interface DataTypeConverter {

    /**
     * Converts the given object to an {@link DataType} object.
     * 
     * @param object the object to convert.
     * @return an AbstractDataType object.
     */
    DataType convert(Json.Element object);
}
