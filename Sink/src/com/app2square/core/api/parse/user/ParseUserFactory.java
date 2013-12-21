/**
 * 
 */
package com.app2square.core.api.parse.user;

import com.app2square.core.api.parse.datatypes.ParseData;
import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.net.HttpMethodFactory;
import com.app2square.core.api.parse.net.UrlEncoder;
import com.app2square.core.api.parse.object.operation.ParseOperation;
import com.app2square.core.api.parse.object.operation.ParseOperationExecutor;

/**
 * @author cs
 * 
 */
public class ParseUserFactory {

    private static final String SESSION_TOKEN = "X-Parse-Session-Token";

    private final Json json;
    private final UrlEncoder encoder;
    private final ParseOperationExecutor executor;
    private final HttpMethodFactory factory;

    public ParseUserFactory(final Json json, final HttpMethodFactory factory, final UrlEncoder encoder,
            final ParseOperationExecutor executor) {
        this.json = json;
        this.factory = factory;
        this.encoder = encoder;
        this.executor = executor;
    }

    public ParseOperation signUp(final ParseData data, final ParseUserCallback callback) {
        return this.executor.execute(new SignUpUserOperation(this.json, this.factory, data, setSessionToken(callback)))[0];
    }

    public void login(final String username, final String password, final ParseUserCallback callback) {
        this.executor.execute(new LoginUserOperation(this.json, this.factory, this.encoder, username, password,
                setSessionToken(callback)));
    }

    public ParseOperation update(final ParseUser user, final ParseData data, final ParseUserCallback callback) {
        return this.executor.execute(new UpdateUserOperation(this.json, this.factory, user, data, callback))[0];
    }

    public ParseOperation delete(final String objectId, final ParseUserCallback callback) {
        return this.executor.execute(new DeleteUserOperation(this.factory, objectId, callback))[0];
    }

    public void logout() {
        this.factory.removeHeader(ParseUserFactory.SESSION_TOKEN);
    }

    public void requestPasswordReset(final String email, final ParseUserCallback callback) {
        this.executor.execute(new RequestPasswordResetOperation(this.factory, email, callback));
    }

    private ParseUserCallback setSessionToken(final ParseUserCallback callback) {
        return new ParseUserCallback() {

            public void error(final Throwable e) {
                callback.error(e);
            }

            public void done(final ParseUser user) {
                ParseUserFactory.this.factory.addHeader(ParseUserFactory.SESSION_TOKEN, user.sessionToken());
                callback.done(user);
            }
        };
    }
}
