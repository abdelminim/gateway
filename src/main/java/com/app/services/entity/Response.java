/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.services.entity;

import com.app.services.constants.ResponseCodes;
import com.google.gson.Gson;

/**
 *
 * @author Saeed Fathi
 */
public class Response {

    private Integer responseCode;
    private String responseMessage;

    public Response() {
    }

    public Response(Integer responseCode) {
        this.responseCode = responseCode;
        this.responseMessage = ResponseCodes.getDesc(responseCode);
    }

    public Response(Integer responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

}
