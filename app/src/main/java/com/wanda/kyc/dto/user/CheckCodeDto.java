package com.wanda.kyc.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class CheckCodeDto {

    @NotNull
    @Pattern(regexp = "register|forgetPsw|payment|withdraw", flags = Pattern.Flag.UNICODE_CASE)
    private String action;

    @NotNull
    private String email;
    @NotBlank
    private String code;

}
