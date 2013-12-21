/**
 * 
 */
package com.app2square.core.api.parse.json;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.app2square.core.api.parse.json.Json.Element;
import com.app2square.core.api.parse.json.Json.Object;

/**
 * @author cs
 * 
 */
public abstract class AbstractJsonTest extends TestCase {

    private final Json json;

    public AbstractJsonTest(final Json json) {
        this.json = json;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testToJsonString() throws Exception {
        final Json.Object object = new Json.Object();
        object.put("testNumber", new Json.Primitive(123L));
        object.put("testString", new Json.Primitive("string"));
        final Json.Array array = new Json.Array();
        array.add(new Json.Primitive("value1"));
        array.add(new Json.Primitive("value2"));
        object.put("testArray", array);
        final Json.Object object1 = new Json.Object();
        object1.put("o1", new Json.Primitive("v1"));
        object1.put("o2", new Json.Primitive("v2"));
        object.put("testObject", object1);

        final Json.Array objectArray = new Json.Array();
        final Object objectArray1 = new Json.Object();
        objectArray1.put("test1", new Json.Primitive("value1"));
        objectArray1.put("test2", new Json.Primitive("value2"));
        array.add(objectArray1);
        object.put("objectArray", objectArray);

        final String jsonString = this.json.toJsonString(object);
        System.out.println(jsonString);
        final Element parse = this.json.parse(jsonString);
        Assert.assertTrue(parse instanceof Json.Object);
        final Json.Object parsedObject = (Json.Object) parse;
        Assert.assertEquals(123L, ((Json.Primitive) parsedObject.get("testNumber")).asNumber());
        Assert.assertEquals("string", ((Json.Primitive) parsedObject.get("testString")).asString());
        Assert.assertEquals("value1", ((Json.Primitive) ((Json.Array) parsedObject.get("testArray")).get(0)).asString());
        Assert.assertEquals("value2", ((Json.Primitive) ((Json.Array) parsedObject.get("testArray")).get(1)).asString());

        Assert.assertEquals(this.json.toJsonString(object),
                this.json.toJsonString((Json.Object) this.json.parse(this.json.toJsonString(object))));
    }
}
