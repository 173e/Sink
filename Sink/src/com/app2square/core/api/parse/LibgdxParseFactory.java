/**
 * 
 */
package com.app2square.core.api.parse;

import com.app2square.core.api.parse.ParseFactory;
import com.app2square.core.api.parse.json.Json;
import com.app2square.core.api.parse.net.HttpConnectionFactory;
import com.app2square.core.api.parse.net.UrlEncoder;

/**
 * @author cs
 * 
 */
public class LibgdxParseFactory extends ParseFactory {

    public LibgdxParseFactory(final String applicationId, final String restApiKey) {
        super(LibgdxParseFactory.connectionFactory(), LibgdxParseFactory.json(), LibgdxParseFactory.encoder(),
                applicationId, restApiKey);
    }

    private static HttpConnectionFactory connectionFactory() {
        return new LibgdxHttpConnectionFactory();
    }

    private static Json json() {
        return new LibgdxJson();
    }

    private static UrlEncoder encoder() {
        return new UrlEncoder() {

            public String encode(final String s) {
                // TODO does libgdx the URL encoding in the net() backend? 
                return s;
            }
        };
    }
}
