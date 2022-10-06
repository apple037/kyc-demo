package com.wanda.kyc.constant;

import java.util.HashMap;

public class UserAppStatusConst {

	private UserAppStatusConst() {
		throw new IllegalStateException("Constant class");
	}

	public static final String ENABLE = "ENABLE";
	public static final String DISABLE = "DISABLE";
//	public static final String UNABLE_TRADE = "UNABLE_TRADE";
//	public static final String UNABLE_LOGIN = "UNABLE_LOGIN";
//	public static final String HIDDEN = "HIDDEN";

	public static HashMap<String, String> canLoginMap = new HashMap<String, String>() {{
		put(ENABLE, ENABLE);
	}};

}
