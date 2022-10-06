package com.wanda.kyc.constant;

public class ExpireTimeConst {

	private ExpireTimeConst() {
		throw new IllegalStateException("Constant class");
	}

	// 圖形驗證失效時間
	public static final int CAPTCHA_EXPIRE_TIME = 15 * 60 * 1000;

	// 驗證碼失效時間
	public static final int VERIFY_CODE_EXPIRE_TIME = 10 * 60 * 1000;

	// 已經驗過驗證碼 保留3分鐘
	public static final int VERIFY_CODE_PASSED_EXPIRE_TIME = 3 * 60 * 1000;

	// google驗證碼失效時間
	public static final int GOOGLE_AUTH_EXPIRE_TIME = 10 * 60 * 1000;

	// 修改google驗証提幣限制時間
	public static final int FORBID_WITHDRAW_TIME = 24 * 60 * 60 * 1000;

}
