package com.wanda.kyc.constant;

public class VerifyConst {

	private VerifyConst() {
		throw new IllegalStateException("Constant class");
	}

	// 註冊
	public static final String ACTION_REGISTER = "register";
	// 忘記密碼
	public static final String ACTION_FORGET_PASSWORD = "forgetPsw";
	// 登入
	public static final String ACTION_LOGIN = "login";
	// 修改密碼
	public static final String ACTION_UPDATE_PASSWORD = "updatePsw";
	// 提幣
	public static final String ACTION_WITHDRAW = "withdraw";
	// 修改支付
	public static final String ACTION_PAYMENT = "payment";
	// 新增管理員
	public static final String ACTION_INSERT_ADMIN = "admin-insert";
	// 修改管理員
	public static final String ACTION_UPDATE_ADMIN= "admin-update";

}
