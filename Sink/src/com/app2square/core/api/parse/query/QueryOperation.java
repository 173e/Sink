/**
 * 
 */
package com.app2square.core.api.parse.query;

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
import com.app2square.core.api.parse.object.ParseObject;
import com.app2square.core.api.parse.object.operation.ParseOperation;

/**
 * @author cs
 * 
 */
public class QueryOperation implements ParseOperation {

    private final Json json;
    private final String className;
    private final Query query;
    private final ParseObjectQueryCallback callback;
    private final UrlEncoder encoder;
    private final HttpMethodFactory factory;

    public QueryOperation(final Json json, final HttpMethodFactory factory, final UrlEncoder encoder,
            final String className, final Query query, final ParseObjectQueryCallback callback) {
        this.json = json;
        this.factory = factory;
        this.encoder = encoder;
        this.className = className;
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

        return "classes/" + this.className + urlBuilder.build();
    }

    private Callback callback() {
        return new DefaultHttpConnectionCallback(this.json, new JsonObjectCallback() {

            public void onSucceed(final Object object) {

                final List<ParseObject> resultList = new ArrayList<ParseObject>();
                final Json.Array responseArray = (Json.Array) object.get("results");

                for (final Json.Element element : responseArray) {
                    if (element instanceof Json.Object) {
                        final Json.Object jsonObject = (Json.Object) element;
                        resultList.add(new ParseObject(QueryOperation.this.className, jsonObject, new ParseData(
                                jsonObject)));
                    }
                }
                long count = 0;
                if (object.get("count") instanceof Json.Primitive) {
                    count = ((Json.Primitive) object.get("count")).asNumber().longValue();
                }

                QueryOperation.this.callback.done(resultList, count);
            }

            public void onExceptionThrown(final Throwable e) {
                QueryOperation.this.callback.error(e);
            }
        });
    }
}
