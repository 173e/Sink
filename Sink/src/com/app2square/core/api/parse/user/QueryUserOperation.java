/**
 * 
 */
package com.app2square.core.api.parse.user;

import java.util.ArrayList;
import java.util.List;

import com.app2square.core.api.parse.datatypes.ParseData;
import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.json.Json.Object;
import com.app2square.core.api.parse.net.DefaultHttpConnectionCallback;
import com.app2square.core.api.parse.net.DefaultHttpConnectionCallback.JsonObjectCallback;
import com.app2square.core.api.parse.net.HttpConnectionFactory.Callback;
import com.app2square.core.api.parse.net.HttpMethod;
import com.app2square.core.api.parse.net.HttpMethodFactory;
import com.app2square.core.api.parse.net.UrlEncoder;
import com.app2square.core.api.parse.object.operation.ParseOperation;
import com.app2square.core.api.parse.query.Query;
import com.app2square.core.api.parse.query.QueryUrlBuilder;

/**
 * @author cs
 * 
 */
class QueryUserOperation implements ParseOperation {

    private final Json json;
    private final Query query;
    private final ParseUserQueryCallback callback;
    private final UrlEncoder encoder;
    private final HttpMethodFactory factory;

    QueryUserOperation(final Json json, final HttpMethodFactory factory, final UrlEncoder encoder, final Query query,
            final ParseUserQueryCallback callback) {
        this.json = json;
        this.factory = factory;
        this.encoder = encoder;
        this.query = query;
        this.callback = callback;
    }

    public HttpMethod http() {
        return this.factory.get(url(), callback());
    }

    private String url() {

        final QueryUrlBuilder urlBuilder = new QueryUrlBuilder(this.encoder);
        urlBuilder.where(this.json.toJsonString(this.query.buildWhere()))// 
                .order(this.query.buildOrder())//
                .include(this.query.buildInclude())//
                .limit(this.query.buildLimit())//
                .skip(this.query.buildSkip())//
                .count(this.query.buildCount())//
                .keys(this.query.buildKeys());

        return "users/" + urlBuilder.build();
    }

    private Callback callback() {
        return new DefaultHttpConnectionCallback(this.json, new JsonObjectCallback() {

            public void onSucceed(final Object responseObject) {
                final List<ParseUser> resultList = new ArrayList<ParseUser>();
                final Json.Array responseArray = (Json.Array) responseObject.get("results");

                for (final Json.Element element : responseArray) {
                    if (element instanceof Json.Object) {
                        final Json.Object object = (Json.Object) element;
                        resultList.add(new ParseUser(object, new ParseData(object)));
                    }
                }
                long count = 0;
                if (responseObject.get("count") instanceof Json.Primitive) {
                    count = ((Json.Primitive) responseObject.get("count")).asNumber().longValue();
                }

                QueryUserOperation.this.callback.done(resultList, count);
            }

            public void onExceptionThrown(final Throwable e) {
                QueryUserOperation.this.callback.error(e);
            }
        });
    }
}
