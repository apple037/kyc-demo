package com.wanda.kyc.constant;

public class TokenConst {

	private TokenConst() {
		throw new IllegalStateException("Constant class");
	}

	// token
	public static final String AUTHORIZATION = "Authorization";
	public static final String PREFIX = "Bearer ";
	public static final int PREFIX_LENGTH = 7;
	public static final int TOKEN_EXPIRE_TIME_BACKSTAGE = 10;
	public static final int TOKEN_EXPIRE_TIME_VIP = 30 * 60 * 1000;

	// 簽名
	public static final String TOKEN_SECRET = "tokenSecret";

	// user
	public static final String ROLE = "role";
	public static final String USER_ID = "userId";

	// memberUser
	public static final String REGISTER_MODE = "registerMode";
	public static final String EMAIL = "email";
	public static final String PHONE = "phone";
	public static final String SUPERIOR = "superior";

	// adminUser
	public static final String BACKSTAGE_ROLE = "backstageRole";

	// 角色
	public static final String ROLE_USER_ADMIN = "userAdmin";
	public static final String ROLE_USER_APP = "userApp";
	public static final String ROLE_VIP = "VIP-999";

}
