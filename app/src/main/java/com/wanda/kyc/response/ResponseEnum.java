package com.wanda.kyc.response;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ResponseEnum {

	// @off
	
	SUCCESS("G_0000","SUCCESS"),
	
	VALID_ERROR("G_0001", "VALID_ERROR"),
	DATABASE_DATA_ERROR("G_0002", "DATABASE_DATA_ERROR"),
	REQUEST_FAIL("G_0003", "REQUEST_FAIL"),
	NOT_FOUND_URL("G_0004", "NOT_FOUND_URL"),
	
	SYSTEM_ERROR("G_9999", "UNKNOWN_FATAL_ERROR");
	
	private String code;
	private String message;
	
	// @on

}
