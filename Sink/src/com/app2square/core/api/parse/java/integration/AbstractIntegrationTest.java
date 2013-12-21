/**
 * 
 */
package com.app2square.core.api.parse.java.integration;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.app2square.core.api.parse.ParseFactory;
import com.app2square.core.api.parse.datatypes.ParseData;
import com.app2square.core.api.parse.java.DefaultConnectionFactory;
import com.app2square.core.api.parse.java.JavaJson;
import com.app2square.core.api.parse.net.UrlEncoder;
import com.app2square.core.api.parse.object.ParseObject;
import com.app2square.core.api.parse.object.ParseObjectCallback;
import com.app2square.core.api.parse.user.ParseUser;
import com.app2square.core.api.parse.user.ParseUserCallback;

/**
 * @author cs
 * 
 */
public abstract class AbstractIntegrationTest extends TestCase {

    public static final ParseObjectCallback TEST_CALLBACK = new ParseObjectCallback() {

        public void error(final Throwable e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

        public void done(final ParseObject object) {
            if (object != null) {
                System.out.println("TestCallback ok: " + object.objectId());
            }
        }
    };

    protected ParseFactory factory;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.factory = new ParseFactory(new DefaultConnectionFactory(), new JavaJson(), new UrlEncoder() {

            public String encode(final String s) {
                try {
                    return URLEncoder.encode(s, "UTF-8");
                } catch (final UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return s;
            }
        }, "RaWr6StbH8YsZH0qaindjiMLDUjK4LvyWnCfgSEn", "P004WYxqdhNO4phMODDFZOHjgLnBeXyH5LQcfNxD");
    }

    ParseObject returnObject;

    protected ParseObject create(final String className, final ParseData data) {

        this.returnObject = null;
        this.factory.op().create(className, data, new ParseObjectCallback() {

            public void error(final Throwable e) {
                e.printStackTrace();
            }

            public void done(final ParseObject object) {
                // Be aware, that this will only work in a synchron call...  
                AbstractIntegrationTest.this.returnObject = object;
                System.out.println("Saved: " + object.objectId());
            }
        });

        return this.returnObject;
    }

    private ParseUser user;

    protected ParseUser createUser(final ParseData data) {

        this.user = null;
        this.factory.user().signUp(data, new ParseUserCallback() {

            public void error(final Throwable e) {
                e.printStackTrace();
            }

            public void done(final ParseUser user) {
                // Be aware, that this will only work in a synchron call...  
                AbstractIntegrationTest.this.user = user;
                System.out.println("Saved: " + user.objectId());
            }
        });

        return this.user;
    }

    protected ParseObject retrieve(final String className, final String objectId) {

        this.returnObject = null;
        this.factory.retreive(className, objectId, new ParseObjectCallback() {

            public void error(final Throwable e) {
                e.printStackTrace();
            }

            public void done(final ParseObject object) {
                System.out.println("Received: " + object.objectId());
                AbstractIntegrationTest.this.returnObject = object;
            }
        });
        return this.returnObject;
    }
}
