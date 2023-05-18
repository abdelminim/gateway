/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.services.entity.request;

import com.app.services.constants.ResponseCodes;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author Saeed Fathi
 */
public class FindUserRequest {

    @NotBlank(message = ResponseCodes.usernameIncorrect)
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
