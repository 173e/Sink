/**
 * 
 */
package com.app2square.core.api.parse.datatypes;

import java.util.Date;

import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.json.Json.Element;

/**
 * @author cs
 * 
 */
public class DateType implements DataType {

    private final Json.Object date;

    public DateType(final Date date) {
        this.date = new Json.Object();
        this.date.put("__type", new Json.Primitive("Date"));
        this.date.put("iso", new Json.Primitive(DateFormatter.fromDate(date)));
    }

    public DateType(final Json.Object object) {
        this.date = object;
    }

    public Element toJsonElement() {
        return this.date;
    }

    public Date value() {
        return DateFormatter.toDate(((Json.Primitive) this.date.get("iso")).asString());
    }
}
