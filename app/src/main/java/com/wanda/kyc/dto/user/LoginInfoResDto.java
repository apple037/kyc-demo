package com.wanda.kyc.dto.user;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class LoginInfoResDto {

	private String account;
	private String username;
	private String email;
	private String token;

}
