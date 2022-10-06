package com.wanda.kyc.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class LoginReqDto {
	
	@NotBlank
	private String type;
	@NotBlank
	private String account;
	@NotBlank
	private String password;
	@NotBlank
	private String code;

}
