/**
 * 
 */
package com.app2square.core.api.parse.json;

/**
 * @author cs
 * 
 */
public class JsonParseException extends Exception {

    /** */
    private static final long serialVersionUID = -8591541790941931185L;

    public JsonParseException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public JsonParseException(final String message) {
        super(message);
    }

    public JsonParseException(final Throwable cause) {
        super(cause);
    }

}
