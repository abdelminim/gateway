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
public class ChangePasswordRequest {

    @NotBlank(message = ResponseCodes.oldPasswordIncorrect)
    private String oldPassword;

    @NotBlank(message = ResponseCodes.newPasswordIncorrect)
    private String newPassword;

    public ChangePasswordRequest() {
    }

    public ChangePasswordRequest(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
