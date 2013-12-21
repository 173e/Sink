/**
 * 
 */
package com.app2square.core.api.parse.java.integration;

import java.util.List;

import junit.framework.Assert;

import com.app2square.core.api.parse.datatypes.ParseData;
import com.app2square.core.api.parse.object.ParseObject;
import com.app2square.core.api.parse.query.Criteria;
import com.app2square.core.api.parse.query.ParseObjectQueryCallback;
import com.app2square.core.api.parse.query.Query;

/**
 * @author cs
 * 
 */
public class QueriesTest extends AbstractIntegrationTest {

    public void testSimpleQuery() throws Exception {

        final ParseData data = new ParseData();
        data.put("name", "value");
        final ParseObject create = create("Game", data);

        final Query query = Query.query("Game", Criteria.where("name").is("value"));
        this.factory.query("Game", query, new ParseObjectQueryCallback() {

            public void error(final Throwable e) {

            }

            public void done(final List<ParseObject> objects, final long count) {
                Assert.assertTrue(objects.size() > 0);
            }
        });
    }
}
