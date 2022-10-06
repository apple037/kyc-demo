package com.wanda.kyc.exception;


import com.wanda.kyc.response.ResponseEnum;
import lombok.Data;


@Data
public class SystemRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 6802948709543718808L;

	private final String code;

	public SystemRuntimeException(String code, String message) {
		super(message);
		this.code = code;
	}

	public SystemRuntimeException(IRuntimeExceptionEnum iRuntimeExceptionEnum) {
		this(iRuntimeExceptionEnum.getCode(), iRuntimeExceptionEnum.getMessage());
	}

	public static SystemRuntimeException requestFail() {
		return new SystemRuntimeException(ResponseEnum.REQUEST_FAIL.getCode(), ResponseEnum.REQUEST_FAIL.getMessage());
	}

	public static SystemRuntimeException systemError() {
		return new SystemRuntimeException(ResponseEnum.SYSTEM_ERROR.getCode(), ResponseEnum.SYSTEM_ERROR.getMessage());
	}

}
