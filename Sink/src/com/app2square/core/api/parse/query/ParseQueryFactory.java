/**
 * 
 */
package com.app2square.core.api.parse.query;

import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.net.HttpMethodFactory;
import com.app2square.core.api.parse.net.UrlEncoder;
import com.app2square.core.api.parse.object.ParseObjectCallback;

/**
 * @author cs
 * 
 */
public class ParseQueryFactory {

    private final Json json;
    private final UrlEncoder encoder;
    private final HttpMethodFactory factory;

    public ParseQueryFactory(final Json json, final HttpMethodFactory factory, final UrlEncoder encoder) {
        this.json = json;
        this.factory = factory;
        this.encoder = encoder;
    }

    public QueryOperation query(final String className, final Query query, final ParseObjectQueryCallback callback) {
        return new QueryOperation(this.json, this.factory, this.encoder, className, query, callback);
    }

    public RetreiveOperation retrieve(final String className, final String objectId, final ParseObjectCallback callback) {
        return new RetreiveOperation(this.json, this.factory, className, objectId, callback);
    }
}
