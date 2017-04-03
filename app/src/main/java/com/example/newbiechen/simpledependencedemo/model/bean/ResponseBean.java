package com.example.newbiechen.simpledependencedemo.model.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by newbiechen on 17-4-3.
 */

public class ResponseBean {
    /**
     * isError : false
     * results : string
     */
    @SerializedName("error")
    private boolean isError;
    private String results;

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        this.isError = error;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }
}
