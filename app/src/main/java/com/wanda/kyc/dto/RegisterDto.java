package com.wanda.kyc.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class RegisterDto {

    @NotBlank(groups = Register.class)
    @Email
    private String email;

    @NotBlank(groups = Register.class)
    private String password;

    //修改時使用
    private String userId;
    private String oldPassword;

    public interface Register {
    }

}
