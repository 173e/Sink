/**
 * 
 */
package com.app2square.core.api.parse.object;

import java.util.Date;

import com.app2square.core.api.parse.datatypes.DataType;
import com.app2square.core.api.parse.datatypes.DateFormatter;
import com.app2square.core.api.parse.datatypes.ParseData;
import com.app2square.core.api.parse.json.Json;

/**
 * @author cs
 * 
 */
public abstract class AbstractParseDataObject {

    public static final String CREATED_AT = "createdAt";
    public static final String UPDATED_AT = "updatedAt";
    public static final String OBJECT_ID = "objectId";

    private final String objectId;

    private final Date createdAt;
    private Date updatedAt;

    private final ParseData data;

    public AbstractParseDataObject(final Json.Object jsonObject, final ParseData data) {
        this(jsonObject.getPrimitive(ParseObject.OBJECT_ID).asString(), DateFormatter.toDate(jsonObject.getPrimitive(
                ParseObject.CREATED_AT).asString()), data);
    }

    private AbstractParseDataObject(final String objectId, final Date createdAt, final ParseData data) {
        this.objectId = objectId;
        this.createdAt = createdAt;
        this.data = data;
    }

    public Date createdAt() {
        return this.createdAt;
    }

    public Date updatedAt() {
        return this.updatedAt;
    }

    public String objectId() {
        return this.objectId;
    }

    public DataType valueOf(final String key) {
        return this.data.get(key);
    }

    @SuppressWarnings("unchecked")
    public <T extends DataType> T valueOf(final String key, final Class<T> clazz) {
        return (T) this.data.get(key);
    }

    public void updated(final Date date, final ParseData data, final ParseData removed) {
        this.updatedAt = date;
        this.data.putAll(data);
        this.data.removeAll(removed);
    }

    @Override
    public boolean equals(final Object obj) {
        return super.equals(obj) || obj instanceof AbstractParseDataObject
                && this.objectId.equals(((AbstractParseDataObject) obj).objectId);
    }

    @Override
    public int hashCode() {
        return this.objectId.hashCode();
    }
}
