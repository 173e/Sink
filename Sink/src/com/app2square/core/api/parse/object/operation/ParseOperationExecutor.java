/**
 * 
 */
package com.app2square.core.api.parse.object.operation;

/**
 * 
 * @author cs
 * 
 */
public interface ParseOperationExecutor {

    public ParseOperationExecutor NOP = new ParseOperationExecutor() {

        public ParseOperation[] execute(final ParseOperation... operations) {
            return operations;
        }
    };

    /**
     * Executes the operations.
     * @param operations the operations to execute.
     * @return the executed operations.
     */
    ParseOperation[] execute(final ParseOperation... operations);

}