/**
 * 
 */
package com.app2square.core.api.parse.query;

import com.app2square.core.api.parse.json.Json;

/**
 * @author cs
 * 
 */
public class Query {

    private final String className;

    private final StringBuilder order = new StringBuilder();

    private final StringBuilder include = new StringBuilder();

    private final StringBuilder keys = new StringBuilder();

    private String limit = "";

    private String skip = "";

    private boolean count = false;

    private final Criteria criteria;

    public static final Query query(final String className, final Criteria criteria) {
        return new Query(className, criteria);
    }

    private Query(final String className, final Criteria criteria) {
        this.className = className;
        this.criteria = criteria;
    }

    public Query orderAsc(final String name) {
        if (this.order.length() != 0) {
            this.order.append(",");
        }
        this.order.append(name);
        return this;
    }

    public Query orderDesc(final String name) {
        if (this.order.length() != 0) {
            this.order.append(",");
        }
        this.order.append("-").append(name);
        return this;
    }

    public Query include(final String include) {
        if (this.include.length() != 0) {
            this.include.append(",");
        }
        this.include.append(include);
        return this;
    }

    public Query keys(final String name) {
        if (this.keys.length() != 0) {
            this.keys.append(",");
        }
        this.keys.append(name);
        return this;
    }

    public Query limit(final int limit) {
        this.limit = String.valueOf(limit);
        return this;
    }

    public Query skip(final int skip) {
        this.skip = String.valueOf(skip);
        return this;
    }

    public Query count() {
        this.count = true;
        return this;
    }

    public String buildKeys() {
        return this.keys.toString();
    }

    public String buildOrder() {
        return this.order.toString();
    }

    public String buildInclude() {
        return this.include.toString();
    }

    public String buildLimit() {
        return this.limit;
    }

    public String buildSkip() {
        return this.skip;
    }

    public String buildCount() {
        return this.count ? "1" : "";
    }

    public Json.Object buildWhere() {
        return this.criteria.build();
    }

    public String className() {
        return this.className;
    }
}
