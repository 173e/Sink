/**
 * 
 */
package com.app2square.core.api.parse.object;

import com.app2square.core.api.parse.datatypes.ParseData;
import com.app2square.core.api.parse.json.Json;

/**
 * @author cs
 * 
 */
public class ParseObject extends AbstractParseDataObject {

    private final String className;

    public ParseObject(final String className, final Json.Object jsonObject, final ParseData data) {
        super(jsonObject, data);
        this.className = className;
    }

    public String className() {
        return this.className;
    }

}
