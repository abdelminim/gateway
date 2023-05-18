/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.services.entity.request;

import com.app.services.entity.Response;

/**
 *
 * @author Saeed Fathi
 */
public class RefreshResponse extends Response {

    private String token;

    public RefreshResponse() {
    }

    public RefreshResponse(Integer responseCode) {
        super(responseCode);
    }

    public RefreshResponse(String token, Integer responseCode) {
        super(responseCode);
        this.token = token;
    }

    public RefreshResponse(String token, Integer responseCode, String responseMsg) {
        super(responseCode, responseMsg);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
