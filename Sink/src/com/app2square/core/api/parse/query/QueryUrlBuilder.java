/**
 * 
 */
package com.app2square.core.api.parse.query;

import java.util.ArrayList;
import java.util.List;

import com.app2square.core.api.parse.net.UrlEncoder;

/**
 * @author cs
 * 
 */
public class QueryUrlBuilder {

    private final List<String> parts = new ArrayList<String>();
    private final UrlEncoder encoder;

    public QueryUrlBuilder(final UrlEncoder encoder) {
        this.encoder = encoder;
    }

    public QueryUrlBuilder where(final String where) {
        this.parts.add("where=" + this.encoder.encode(where));
        return this;
    }

    public QueryUrlBuilder order(final String order) {
        if (!order.isEmpty()) {
            this.parts.add("order=" + this.encoder.encode(order));
        }
        return this;
    }

    public QueryUrlBuilder include(final String include) {
        if (!include.isEmpty()) {
            this.parts.add("include=" + this.encoder.encode(include));
        }
        return this;
    }

    public QueryUrlBuilder skip(final String skip) {
        if (!skip.isEmpty()) {
            this.parts.add("skip=" + this.encoder.encode(skip));
        }
        return this;
    }

    public QueryUrlBuilder count(final String count) {
        if (!count.isEmpty()) {
            this.parts.add("count=" + this.encoder.encode(count));
        }
        return this;
    }

    public QueryUrlBuilder limit(final String limit) {
        if (!limit.isEmpty()) {
            this.parts.add("limit=" + this.encoder.encode(limit));
        }
        return this;
    }

    public QueryUrlBuilder keys(final String keys) {
        if (!keys.isEmpty()) {
            this.parts.add("keys=" + this.encoder.encode(keys));
        }
        return this;
    }

    public String build() {
        if (this.parts.isEmpty()) {
            return "";
        }
        final StringBuilder builder = new StringBuilder("?");

        for (int i = 0; i < this.parts.size(); i++) {
            builder.append(this.parts.get(i));
            if (i < this.parts.size()) {
                builder.append("&");
            }
        }

        return builder.toString();
    }
}
