package com.castrec.stephane.androidnotev2.model;

/**
 * Created by sca on 18/09/17.
 */

public class HttpResult {
    public final int status;
    public final String json;

    public HttpResult(int status, String json) {
        this.status = status;
        this.json = json;
    }
}
