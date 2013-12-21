/**
 * 
 */
package com.app2square.core.api.parse.query;

import java.util.ArrayList;
import java.util.List;

import com.app2square.core.api.parse.datatypes.ListType;
import com.app2square.core.api.parse.datatypes.PointerType;
import com.app2square.core.api.parse.json.Json;

/**
 * @author cs
 * 
 */
public class Criteria {

    private final List<Criteria> criterias;

    private final Json.Object object = new Json.Object();
    private Json.Element value = null;

    private final String key;

    private Criteria(final String key, final List<Criteria> criterias) {
        this.key = key;
        this.criterias = criterias;
    }

    public static Criteria where(final String key) {
        final Criteria criteria = new Criteria(key, new ArrayList<Criteria>());
        criteria.criterias.add(criteria);
        return criteria;
    }

    public static Criteria whereRelatedTo(final PointerType object, final String key) {
        final Criteria criteria = new Criteria("$relatedTo", new ArrayList<Criteria>());
        criteria.criterias.add(criteria);
        criteria.object.put("object", object.toJsonElement());
        criteria.object.put("key", new Json.Primitive(key));
        return criteria;
    }

    public static Criteria or(final Criteria... criterias) {

        final Criteria criteria = new Criteria("$or", new ArrayList<Criteria>());
        criteria.criterias.add(criteria);
        criteria.value = new Json.Array();
        for (final Criteria c : criterias) {
            ((Json.Array) criteria.value).add(c.build());
        }
        return criteria;
    }

    public final Criteria isLessThan(final Number number) {
        this.object.put("$lt", new Json.Primitive(number));
        return this;
    }

    public final Criteria isLessOrEqualThan(final Number number) {
        this.object.put("$lte", new Json.Primitive(number));
        return this;
    }

    public final Criteria isGreaterThan(final Number number) {
        this.object.put("$gt", new Json.Primitive(number));
        return this;
    }

    public final Criteria isGreaterOrEqualThan(final Number number) {
        this.object.put("$gte", new Json.Primitive(number));
        return this;
    }

    public final Criteria isNotEqualTo(final Object object) {
        this.object.put("$ne", new Json.Primitive(object));
        return this;
    }

    public final Criteria containedIn(final ListType list) {
        this.object.put("$in", list.toJsonElement());
        return this;
    }

    public final Criteria notContainedIn(final ListType list) {
        this.object.put("$nin", list.toJsonElement());
        return this;
    }

    public final Criteria exists() {
        this.object.put("$exists", new Json.Primitive(true));
        return this;
    }

    public final Criteria notExists() {
        this.object.put("$exists", new Json.Primitive(false));
        return this;
    }

    public final Criteria arrayKey(final Number number) {
        this.object.put("$arrayKey", new Json.Primitive(number));
        return this;
    }

    public final Criteria arrayKeyAll(final ListType list) {
        final Json.Object object = new Json.Object();
        object.put("$all", list.toJsonElement());
        this.object.put("$arrayKey", object);
        return this;
    }

    public final Criteria select(final Query innerQuery, final String key) {

        final Json.Object query = new Json.Object();
        query.put("query", innerQueryToJson(innerQuery));
        query.put("key", new Json.Primitive(key));
        this.object.put("$select", query);
        return this;
    }

    public final Criteria inQuery(final Query innerQuery) {
        this.object.put("$inQuery", innerQueryToJson(innerQuery));
        return this;
    }

    public final Criteria notInQuery(final Query innerQuery) {
        this.object.put("$notInQuery", innerQueryToJson(innerQuery));
        return this;
    }

    private Json.Object innerQueryToJson(final Query innerQuery) {
        final Json.Object innerQueryObject = new Json.Object();
        innerQueryObject.put("className", new Json.Primitive(innerQuery.className()));
        innerQueryObject.put("where", innerQuery.buildWhere());
        return innerQueryObject;
    }

    public final Criteria is(final Object object) {
        this.value = new Json.Primitive(object);
        return this;
    }

    public final Criteria is(final PointerType object) {
        this.value = object.toJsonElement();
        return this;
    }

    public Criteria and(final String key) {
        final Criteria criteria = new Criteria(key, this.criterias);
        this.criterias.add(criteria);
        return criteria;
    }

    Json.Object build() {
        final Json.Object object = new Json.Object();
        if (!this.criterias.isEmpty()) {
            for (int i = 0; i < this.criterias.size(); i++) {
                final Criteria criteria = this.criterias.get(i);
                if (this.value != null) {
                    object.put(criteria.key, criteria.value);
                } else {
                    object.put(criteria.key, criteria.object);
                }
            }
        }
        return object;
    }

}
