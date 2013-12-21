/**
 * 
 */
package com.app2square.core.api.parse;

import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.json.JsonParseErrorWrapper;
import com.app2square.core.api.parse.net.HttpConnectionFactory;
import com.app2square.core.api.parse.net.HttpMethodFactory;
import com.app2square.core.api.parse.net.UrlEncoder;
import com.app2square.core.api.parse.object.ParseObjectCallback;
import com.app2square.core.api.parse.object.operation.ParseOperation;
import com.app2square.core.api.parse.object.operation.ParseOperationExecutor;
import com.app2square.core.api.parse.object.operation.ParseOperationExecutorImpl;
import com.app2square.core.api.parse.object.operation.ParseOperationFactory;
import com.app2square.core.api.parse.query.ParseObjectQueryCallback;
import com.app2square.core.api.parse.query.ParseQueryFactory;
import com.app2square.core.api.parse.query.Query;
import com.app2square.core.api.parse.user.ParseUserFactory;

/**
 * This class defines the entry point of the mobile parse.com API. <br/>
 * To get started you need to create a {@code ParseFactory} object with following elements:
 * 
 * <pre>
 * <ul>
 *  <li>the specific implementation of the connection factory ({@link HttpConnectionFactory}), </li>
 *  <li>the json module ({@link Json}), </li>
 *  <li>the url encoder ({@link UrlEncoder}), </li>
 *  <li>and the application Id and rest API key of your parse.com account.</li>
 * </ul>
 * </pre>
 * 
 * @author cs
 */
public class ParseFactory {

    /** defining the url of the API */
    private static final String API_URL = "https://api.parse.com";

    /** defines the version of the API */
    public static final String API_VERSION = "/1/";

    private final ParseOperationExecutor parseOperationExecutor;

    private final ParseOperationFactory parseOperationBatchFactory;

    private final ParseOperationFactory parseOperationExecutorFactory;

    private final ParseQueryFactory parseQueryFactory;

    private final ParseUserFactory parseUserFactory;

    private final HttpMethodFactory methodFactory;

    /**
     * Creating a new parse factory.
     * 
     * @param connectionFactory the specific implementation of the connetion factory.
     * @param json the json implementation.
     * @param encoder the encoder implementation.
     * @param applicationId your parse.com application id
     * @param restApiKey your rest api key.
     */
    public ParseFactory(final HttpConnectionFactory connectionFactory, final Json json, final UrlEncoder encoder,
            final String applicationId, final String restApiKey) {
        final Json jsonImpl = new JsonParseErrorWrapper(json);
        this.methodFactory = httpMethodFactory(connectionFactory, jsonImpl, applicationId, restApiKey);
        this.parseOperationExecutor = new ParseOperationExecutorImpl(this.methodFactory, jsonImpl);
        this.parseOperationBatchFactory = new ParseOperationFactory(this.methodFactory, jsonImpl,
                ParseOperationExecutor.NOP);
        this.parseOperationExecutorFactory = new ParseOperationFactory(this.methodFactory, jsonImpl,
                this.parseOperationExecutor);
        this.parseUserFactory = new ParseUserFactory(jsonImpl, this.methodFactory, encoder, this.parseOperationExecutor);
        this.parseQueryFactory = new ParseQueryFactory(jsonImpl, this.methodFactory, encoder);
    }

    public void query(final String className, final Query query, final ParseObjectQueryCallback callback) {
        this.parseOperationExecutor.execute(this.parseQueryFactory.query(className, query, callback));
    }

    public void retreive(final String className, final String objectId, final ParseObjectCallback callback) {
        this.parseOperationExecutor.execute(this.parseQueryFactory.retrieve(className, objectId, callback));
    }

    public void batchExecute(final ParseOperation... operations) {
        this.parseOperationExecutor.execute(operations);
    }

    public ParseOperationFactory op() {
        return this.parseOperationExecutorFactory;
    }

    public ParseOperationFactory batch() {
        return this.parseOperationBatchFactory;
    }

    public ParseUserFactory user() {
        return this.parseUserFactory;
    }

    private HttpMethodFactory httpMethodFactory(final HttpConnectionFactory connectionFactory, final Json json,
            final String applicationId, final String restApiKey) {
        final HttpMethodFactory methodFactory = new HttpMethodFactory(ParseFactory.API_URL + ParseFactory.API_VERSION,
                connectionFactory, json);
        methodFactory.addHeader("X-Parse-Application-Id", applicationId);
        methodFactory.addHeader("X-Parse-REST-API-Key", restApiKey);
        return methodFactory;
    }
}
