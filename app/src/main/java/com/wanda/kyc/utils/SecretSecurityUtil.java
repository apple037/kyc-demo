package com.wanda.kyc.utils;

public class SecretSecurityUtil {

	// 各種 加解密和驗證
	
	/**
	 * 密碼加密
	 * 
	 * @param password
	 * @param salt
	 * @return
	 */
	public static String passwordEncode(String password, String salt) {
		return BCryptUtil.encode(format(password, salt));
	}

	/**
	 * 密碼匹配使否正確
	 * 
	 * @param password
	 * @param salt
	 * @param encodedPassword
	 * @return
	 */
	public static boolean passwordMatch(String password, String salt, String encodedPassword) {
		return BCryptUtil.match(format(password, salt), encodedPassword);
	}

	private static String format(String plaintext, String salt) {
		return plaintext.concat("(").concat(salt).concat(")");
	}
	
	public static void main(String[] args) {
		String salt = GeneratorUtil.salt();
		System.out.println(salt);

		String password = "123456";
		String encodedPassword = passwordEncode(password, salt);
		System.out.println(encodedPassword);

		System.out.println(passwordMatch(password, salt, encodedPassword));
	}

}
