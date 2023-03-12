package com.example.ihuntwithjavalins.common;

/**
 * Interface used to handle result of DB operation.
 * Idea from Well Fed project example
 * @param <T> Placeholder for object type
 */
public interface OnCompleteListener<T> {
    /**
     * Called when a DB operation is completed
     * @param item the object
     * @param success true if operation succeeded, false otherwise
     */
    void onComplete(T item, Boolean success);
}
