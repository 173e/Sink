/**
 * 
 */
package com.app2square.core.api.parse.datatypes;

import java.util.ArrayList;
import java.util.List;

import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.json.Json.Element;

/**
 * @author cs
 * 
 */
public class ListType implements DataType {

    public static final ListType EMPTY = new ListType();

    private final Json.Array data;

    public ListType() {
        this.data = new Json.Array();
    }

    public ListType(final List<?> list) {
        this.data = new Json.Array();
        for (final Object element : list) {
            this.data.add(new Json.Primitive(element));
        }
    }

    public <T extends DataType> ListType(final List<T> list, final Class<T> clazz) {
        this.data = new Json.Array();
        for (final DataType element : list) {
            this.data.add(element.toJsonElement());
        }
    }

    public ListType(final Json.Array object) {
        this.data = object;
    }

    public Element toJsonElement() {
        return this.data;
    }

    public void addAll(final ListType list) {
        this.data.addAll(list.data);
    }

    public boolean removeAll(final ListType list) {
        return this.data.removeAll(list.data);
    }

    public void addUnique(final ListType list) {
        for (final Json.Element newValue : list.data) {
            if (!this.data.contains(newValue)) {
                this.data.add(newValue);
            }
        }
    }

    public List<String> stringList() {
        final List<String> list = new ArrayList<String>();
        for (final Json.Element value : this.data) {
            if (value instanceof Json.Primitive && ((Json.Primitive) value).isString()) {
                list.add(((Json.Primitive) value).asString());
            }
        }
        return list;
    }

    public List<PointerType> pointerTypeList() {
        final List<PointerType> list = new ArrayList<PointerType>();
        for (final Json.Element value : this.data) {
            if (value instanceof Json.Object) {
                list.add(new PointerType((Json.Object) value));
            }
        }
        return list;
    }
}
