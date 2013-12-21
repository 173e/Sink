/**
 * 
 */
package com.app2square.core.api.parse.user;

import com.app2square.core.api.parse.datatypes.ParseData;
import com.app2square.core.api.parse.json.Json.Object;
import com.app2square.core.api.parse.object.AbstractParseDataObject;

/**
 * @author cs
 * 
 */
public class ParseUser extends AbstractParseDataObject {

    private final String sessionToken;

    public ParseUser(final Object jsonObject, final ParseData data) {
        super(jsonObject, data);
        this.sessionToken = jsonObject.getPrimitive("sessionToken") != null ? jsonObject.getPrimitive("sessionToken")
                .asString() : null;
    }

    public String sessionToken() {
        return this.sessionToken;
    }
}
