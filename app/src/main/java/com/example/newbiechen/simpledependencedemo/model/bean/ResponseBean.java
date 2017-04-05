package com.example.newbiechen.simpledependencedemo.model.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by newbiechen on 17-4-3.
 */

public class ResponseBean<T> {
    /**
     * isError : false
     * results : string
     */
    @SerializedName("error")
    private boolean isError;
    private T results;

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        this.isError = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
