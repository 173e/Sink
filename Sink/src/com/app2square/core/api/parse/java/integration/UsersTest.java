/**
 * 
 */
package com.app2square.core.api.parse.java.integration;

import junit.framework.Assert;

import com.app2square.core.api.parse.datatypes.ParseData;
import com.app2square.core.api.parse.user.ParseUser;
import com.app2square.core.api.parse.user.ParseUserCallback;

/**
 * @author cs
 * 
 */
public class UsersTest extends AbstractIntegrationTest {

    public void testCreateUser() throws Exception {

        final ParseData data = new ParseData();
        data.put("username", "tester1");
        data.put("password", "11221122");
        data.put("phone", "sdfsdf");
        data.put("email", "csokop@app2square.com");

        final ParseUser user = createUser(data);

        Assert.assertNotNull(user);
        Assert.assertNotNull(user.createdAt());
        Assert.assertNotNull(user.objectId());
        Assert.assertNotNull(user.sessionToken());

        this.factory.user().login("tester1", "11221122", new ParseUserCallback() {

            public void error(final Throwable e) {
                e.printStackTrace();
            }

            public void done(final ParseUser user) {
                Assert.assertNotNull(user);
                Assert.assertNotNull(user.createdAt());
                Assert.assertNotNull(user.objectId());
                Assert.assertNotNull(user.sessionToken());
            }
        });

        this.factory.user().delete(user.objectId(), new ParseUserCallback() {

            public void error(final Throwable e) {
                Assert.fail(e.toString());
            }

            public void done(final ParseUser user) {

            }
        });
    }

    public void testLoginFail() throws Exception {

        this.factory.user().login("unknown", "11221122", new ParseUserCallback() {

            public void error(final Throwable e) {
                e.printStackTrace();
            }

            public void done(final ParseUser user) {
                Assert.fail("Unable to login");
            }
        });

    }

    public void testRequestPasswortReset() throws Exception {

        this.factory.user().requestPasswordReset("csokop123@app2square.com", new ParseUserCallback() {

            public void error(final Throwable e) {
                e.printStackTrace();
                Assert.fail("User not found expected");
            }

            public void done(final ParseUser user) {
            }
        });
    }
}
