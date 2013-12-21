/**
 * 
 */
package com.app2square.core.api.parse.query;

/**
 * @author cs
 * 
 */
public interface ParseCallback<T> {

    void error(Throwable e);

    void done(T object);

}
