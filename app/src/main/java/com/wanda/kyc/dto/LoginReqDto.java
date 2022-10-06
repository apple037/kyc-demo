package com.wanda.kyc.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginReqDto {

    @NotBlank
    private String account;

    @NotBlank
    private String password;
}
