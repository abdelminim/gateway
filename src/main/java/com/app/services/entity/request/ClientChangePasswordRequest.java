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
public class ClientChangePasswordRequest extends ChangePasswordRequest {

    @NotBlank(message = ResponseCodes.usernameIncorrect)
    private String username;

    public ClientChangePasswordRequest(String username, String oldPassword, String newPassword) {
        super(oldPassword, newPassword);
        this.username = username;
    }

    public ClientChangePasswordRequest() {
    }

    public ClientChangePasswordRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
