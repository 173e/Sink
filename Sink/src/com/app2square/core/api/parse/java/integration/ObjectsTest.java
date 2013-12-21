/**
 * 
 */
package com.app2square.core.api.parse.java.integration;

import java.util.Arrays;

import junit.framework.Assert;

import com.app2square.core.api.parse.datatypes.ListType;
import com.app2square.core.api.parse.datatypes.ParseData;
import com.app2square.core.api.parse.datatypes.PointerType;
import com.app2square.core.api.parse.datatypes.PrimitiveType;
import com.app2square.core.api.parse.object.ParseObject;
import com.app2square.core.api.parse.object.ParseObjectCallback;

/**
 * @author cs
 * 
 */
public class ObjectsTest extends AbstractIntegrationTest {

    public void testSaveReceive() throws Exception {

        final ParseData data = new ParseData();
        data.put("name", "app2schnapsen");
        data.put("active", true);
        data.put("downloads", 55000);

        final ParseObject object = create("Game", data);
        Assert.assertNotNull(object.objectId());
        Assert.assertNotNull(object.createdAt());

        final ParseObject received = retrieve("Game", object.objectId());
        Assert.assertTrue(received != object);
        Assert.assertEquals("app2schnapsen", received.valueOf("name", PrimitiveType.class).value());
        Assert.assertEquals(55000L, received.valueOf("downloads", PrimitiveType.class).asNumber().longValue());
        Assert.assertEquals(Boolean.valueOf(true), received.valueOf("active", PrimitiveType.class).asBoolean());
    }

    public void testUpdate() throws Exception {

        final ParseData data = new ParseData();
        data.put("name", "app2schnapsen");

        final ParseObject object = create("Game", data);

        Assert.assertNotNull(object.objectId());
        Assert.assertNotNull(object.createdAt());
        Assert.assertNull(object.updatedAt());

        final ParseData updated = new ParseData();
        updated.put("name", "updated_name_app2schnapsen");

        this.factory.op().update(object, updated, AbstractIntegrationTest.TEST_CALLBACK);

        Assert.assertEquals("updated_name_app2schnapsen", object.valueOf("name", PrimitiveType.class).value());
        Assert.assertNotNull(object.updatedAt());

        final ParseObject received = retrieve("Game", object.objectId());
        Assert.assertEquals(object.valueOf("name", PrimitiveType.class), received.valueOf("name", PrimitiveType.class));
        Assert.assertEquals("updated_name_app2schnapsen", received.valueOf("name", PrimitiveType.class).value());
    }

    public void testIncrementDecrement() throws Exception {

        final ParseData data = new ParseData();
        data.put("name", "app2schnapsen");
        data.put("downloads", 100000);

        final ParseObject object = create("Game", data);

        this.factory.op().increment(object, "downloads", 3, AbstractIntegrationTest.TEST_CALLBACK);
        Assert.assertEquals(100003L, object.valueOf("downloads", PrimitiveType.class).asNumber().longValue());

        this.factory.op().increment(object, "downloads", 100, AbstractIntegrationTest.TEST_CALLBACK);

        Assert.assertEquals(100103L, object.valueOf("downloads", PrimitiveType.class).asNumber().longValue());
        Assert.assertEquals(100103L, retrieve("Game", object.objectId()).valueOf("downloads", PrimitiveType.class)
                .asNumber().longValue());

        this.factory.op().increment(object, "downloads", -5, AbstractIntegrationTest.TEST_CALLBACK);
        Assert.assertEquals(Long.valueOf(100098), object.valueOf("downloads", PrimitiveType.class).asNumber());

        Assert.assertEquals(100098L, retrieve("Game", object.objectId()).valueOf("downloads", PrimitiveType.class)
                .asNumber().longValue());
    }

    public void testListOperations() throws Exception {

        final ParseData data = new ParseData();
        data.put("name", "app2schnapsen");
        data.put("keywords", new ListType(Arrays.asList(new String[] { "cardgame", "austria", "beer" })));

        final ParseObject object = create("Game", data);

        Assert.assertEquals(Arrays.asList(new String[] { "cardgame", "austria", "beer" }),
                retrieve("Game", object.objectId()).valueOf("keywords", ListType.class).stringList());

        this.factory.op().listAdd(object, "keywords",
                new ListType(Arrays.asList(new String[] { "tradition", "coolgame" })),
                AbstractIntegrationTest.TEST_CALLBACK);

        Assert.assertEquals(Arrays.asList(new String[] { "cardgame", "austria", "beer", "tradition", "coolgame" }),
                retrieve("Game", object.objectId()).valueOf("keywords", ListType.class).stringList());

        this.factory.op().listAddUniqe(object, "keywords",
                new ListType(Arrays.asList(new String[] { "beer", "beisl" })), AbstractIntegrationTest.TEST_CALLBACK);

        Assert.assertEquals(
                Arrays.asList(new String[] { "cardgame", "austria", "beer", "tradition", "coolgame", "beisl" }),
                retrieve("Game", object.objectId()).valueOf("keywords", ListType.class).stringList());

        this.factory.op()
                .listRemove(object, "keywords", new ListType(Arrays.asList(new String[] { "beer", "coolgame" })),
                        AbstractIntegrationTest.TEST_CALLBACK);

        Assert.assertEquals(Arrays.asList(new String[] { "cardgame", "austria", "tradition", "beisl" }),
                retrieve("Game", object.objectId()).valueOf("keywords", ListType.class).stringList());
    }

    public void testRelations() throws Exception {

        final ParseData data = new ParseData();
        data.put("name", "app2schnapsen");
        final ParseObject game = create("Game", data);

        final ParseData playerData = new ParseData();
        data.put("name", "player1");
        final ParseObject player = create("Player", playerData);

        this.factory.op().addRelation(
                game,
                "players",
                new ListType(Arrays.asList(new PointerType[] { new PointerType("Player", player.objectId()) }),
                        PointerType.class), AbstractIntegrationTest.TEST_CALLBACK);

        final ParseObject retrieve = retrieve("Game", game.objectId());

        // TODO how to query relations? 
        //        Assert.assertEquals(Arrays.asList(new ParseObject[] { player }),
        //                retrieve("Game", game.objectId()).relations("players"));

        this.factory.op().removeRelation(
                game,
                "players",
                new ListType(Arrays.asList(new PointerType[] { new PointerType("Player", player.objectId()) }),
                        PointerType.class), AbstractIntegrationTest.TEST_CALLBACK);

        // TODO how to query relations? 
        //        Assert.assertEquals(Arrays.asList(new String[] {}), retrieve("Game", game.objectId()).relations("players"));

    }

    public void testDelete() throws Exception {

        final ParseData data = new ParseData();
        data.put("name", "app2schnapsen");
        data.put("field1", "field_toDelete");
        final ParseObject object = create("Game", data);

        Assert.assertNotNull(object.valueOf("field1"));

        this.factory.op().deleteField(object, "field1", AbstractIntegrationTest.TEST_CALLBACK);
        final String objectId = object.objectId();

        final ParseObject retrieve = retrieve("Game", objectId);
        Assert.assertNull(retrieve.valueOf("field1"));
        Assert.assertNull(object.valueOf("field1"));

        this.factory.op().delete(object.className(), objectId, AbstractIntegrationTest.TEST_CALLBACK);
        Assert.assertNull(retrieve("Game", objectId));
    }

    public void testBatch() throws Exception {

        final ParseData data = new ParseData();
        data.put("name", "app2schnapsen");

        this.factory.batchExecute(this.factory.batch().create("Game", data, new ParseObjectCallback() {

            public void error(final Throwable e) {
                e.printStackTrace();
            }

            public void done(final ParseObject object) {
                System.out.println("Saved1: " + object.objectId());
            }
        }), this.factory.batch().create("Game", data, new ParseObjectCallback() {

            public void error(final Throwable e) {
                e.printStackTrace();
            }

            public void done(final ParseObject object) {
                System.out.println("Saved2: " + object.objectId());
            }
        }));

    }
}
