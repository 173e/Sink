/**
 * 
 */
package com.app2square.core.api.parse.user;

import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.net.HttpMethodFactory;
import com.app2square.core.api.parse.net.UrlEncoder;
import com.app2square.core.api.parse.query.Query;

/**
 * @author cs
 * 
 */
class ParseQueryUserFactory {

    private final Json json;
    private final UrlEncoder encoder;
    private final HttpMethodFactory factory;

    ParseQueryUserFactory(final Json json, final HttpMethodFactory factory, final UrlEncoder encoder) {
        this.json = json;
        this.factory = factory;
        this.encoder = encoder;
    }

    public QueryUserOperation query(final Query query, final ParseUserQueryCallback callback) {
        return new QueryUserOperation(this.json, this.factory, this.encoder, query, callback);
    }

    public RetreiveUserOperation retrieve(final String objectId, final ParseUserCallback callback) {
        return new RetreiveUserOperation(this.json, this.factory, objectId, callback);
    }
}
