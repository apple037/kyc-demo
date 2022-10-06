package com.wanda.kyc.constant;

public class StatusConst {

	private StatusConst() {
		throw new IllegalStateException("Constant class");
	}

	public static final String NORMAL = "NORMAL";
	// 審核中
	public static final String ON_AUDIT = "ON_AUDIT";
}
