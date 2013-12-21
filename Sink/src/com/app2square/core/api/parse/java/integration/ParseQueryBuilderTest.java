/**
 * 
 */
package com.app2square.core.api.parse.java.integration;

import java.util.ArrayList;
import java.util.Arrays;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.app2square.core.api.parse.datatypes.ListType;
import com.app2square.core.api.parse.datatypes.PointerType;
import com.app2square.core.api.parse.java.JavaJson;
import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.query.Criteria;
import com.app2square.core.api.parse.query.Query;

/**
 * @author cs
 * 
 */
public class ParseQueryBuilderTest extends TestCase {

    private Json json;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.json = new JavaJson();
    }

    public void testSimpleWhere() throws Exception {

        Assert.assertEquals(
                "{\"playerName\":\"Sean Plott\",\"cheatMode\":false}",
                this.json.toJsonString(Query.query("",
                        Criteria.where("playerName").is("Sean Plott").and("cheatMode").is(false)).buildWhere()));
    }

    public void testGteLteWhere() throws Exception {

        Assert.assertEquals(
                "{\"score\":{\"$gte\":1000,\"$lte\":3000}}",
                this.json.toJsonString(Query.query("",
                        Criteria.where("score").isGreaterOrEqualThan(1000).isLessOrEqualThan(3000)).buildWhere()));
    }

    public void testContainedIn() throws Exception {

        Assert.assertEquals("{\"score\":{\"$in\":[1,3,5,7,9]}}", this.json.toJsonString(Query.query(
                "",
                Criteria.where("score").containedIn(
                        new ListType(new ArrayList(Arrays.asList(new Integer[] { 1, 3, 5, 7, 9 }))))).buildWhere()));

    }

    public void testContainedNotIn() throws Exception {

        Assert.assertEquals(
                "{\"playerName\":{\"$nin\":[\"Jonathan Walsh\",\"Dario Wunsch\",\"Shawn Simon\"]}}",
                this.json.toJsonString(Query.query(
                        "",
                        Criteria.where("playerName").notContainedIn(
                                new ListType(new ArrayList(Arrays.asList(new String[] { "Jonathan Walsh",
                                        "Dario Wunsch", "Shawn Simon" }))))).buildWhere()));
    }

    public void testExists() throws Exception {

        Assert.assertEquals("{\"score\":{\"$exists\":true}}",
                this.json.toJsonString(Query.query("", Criteria.where("score").exists()).buildWhere()));

    }

    public void testNotExists() throws Exception {

        Assert.assertEquals("{\"score\":{\"$exists\":false}}",
                this.json.toJsonString(Query.query("", Criteria.where("score").notExists()).buildWhere()));
    }

    public void testSelect() throws Exception {

        Assert.assertEquals(
                "{\"hometown\":{\"$select\":{\"query\":{\"className\":\"Team\",\"where\":{\"winPct\":{\"$gt\":0.5}}},\"key\":\"city\"}}}",
                this.json.toJsonString(Query.query(
                        "",
                        Criteria.where("hometown").select(
                                Query.query("Team", Criteria.where("winPct").isGreaterThan(0.5)), "city")).buildWhere()));
    }

    public void testRelational() throws Exception {

        //        Assert.assertEquals("{\"post\":{\"__type\":\"Pointer\",\"className\":\"Post\",\"objectId\":\"8TOXdXf3tz\"}}",
        //                this.json.toJsonString(Query
        //                        .query("", Criteria.where("post").is(new PointerType("Post", "8TOXdXf3tz"))).buildWhere()));

        final String jsonString = this.json.toJsonString(Query.query("",
                Criteria.where("post").is(new PointerType("Post", "8TOXdXf3tz"))).buildWhere());

        Assert.assertTrue(jsonString.contains("\"__type\":\"Pointer\""));
        Assert.assertTrue(jsonString.contains("\"objectId\":\"8TOXdXf3tz\""));
        Assert.assertTrue(jsonString.contains("\"className\":\"Post\""));

    }

    public void testRelationalInQuery() throws Exception {
        //        Assert.assertEquals(
        //                "{\"post\":{\"$inQuery\":{\"where\":{\"image\":{\"$exists\":true}},\"className\":\"Post\"}}}",
        //                this.json.toJsonString(Query.query("",
        //                        Criteria.where("post").inQuery(Query.query("Post", Criteria.where("image").exists())))
        //                        .buildWhere()));

        final String jsonString = this.json.toJsonString(Query.query("",
                Criteria.where("post").inQuery(Query.query("Post", Criteria.where("image").exists()))).buildWhere());
        Assert.assertTrue(jsonString.contains("\"$inQuery\":"));
        Assert.assertTrue(jsonString.contains("\"where\":{\"image\":{\"$exists\":true}}"));
        Assert.assertTrue(jsonString.contains("\"className\":\"Post\""));

    }

    public void testRelationalNotInQuery() throws Exception {

        //        Assert.assertEquals(
        //                "{\"post\":{\"$notInQuery\":{\"where\":{\"image\":{\"$exists\":true}},\"className\":\"Post\"}}}",
        //                this.json.toJsonString(Query.query("",
        //                        Criteria.where("post").notInQuery(Query.query("Post", Criteria.where("image").exists())))
        //                        .buildWhere()));
        //        
        final String jsonString = this.json.toJsonString(Query.query("",
                Criteria.where("post").notInQuery(Query.query("Post", Criteria.where("image").exists()))).buildWhere());
        Assert.assertTrue(jsonString.contains("\"$notInQuery\":"));
        Assert.assertTrue(jsonString.contains("\"where\":{\"image\":{\"$exists\":true}}"));
        Assert.assertTrue(jsonString.contains("\"className\":\"Post\""));

    }

    public void testRelatedTo() throws Exception {

        //        Assert.assertEquals(
        //                "{\"$relatedTo\":{\"object\":{\"__type\":\"Pointer\",\"className\":\"Post\",\"objectId\":\"8TOXdXf3tz\"},\"key\":\"likes\"}}",
        //                this.json.toJsonString(Query.query("",
        //                        Criteria.whereRelatedTo(new PointerType("Post", "8TOXdXf3tz"), "likes")).buildWhere()));

        final String jsonString = this.json.toJsonString(Query.query("",
                Criteria.whereRelatedTo(new PointerType("Post", "8TOXdXf3tz"), "likes")).buildWhere());
        Assert.assertTrue(jsonString.contains("$relatedTo"));
        Assert.assertTrue(jsonString.contains("\"__type\":\"Pointer\""));
        Assert.assertTrue(jsonString.contains("\"className\":\"Post\""));
        Assert.assertTrue(jsonString.contains("\"objectId\":\"8TOXdXf3tz\""));
        Assert.assertTrue(jsonString.contains("\"key\":\"likes\""));

    }

    public void testCompoundQueries() throws Exception {

        Assert.assertEquals("{\"$or\":[{\"wins\":{\"$gt\":150}},{\"wins\":{\"$lt\":5}}]}",
                this.json.toJsonString(Query.query("",
                        Criteria.or(Criteria.where("wins").isGreaterThan(150), Criteria.where("wins").isLessThan(5)))
                        .buildWhere()));

    }
}
