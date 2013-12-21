/**
 * 
 */
package com.app2square.core.api.parse.datatypes;

import com.app2square.core.api.parse.json.Json;

/**
 * @author cs
 * 
 */
public interface DataType {

    Json.Element toJsonElement();
}
