package com.wanda.kyc.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class GoogleLoginDto {
	
	private String email;
	private String name;
	private boolean emailVerified;
}
