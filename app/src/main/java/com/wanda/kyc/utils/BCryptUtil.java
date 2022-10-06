package com.wanda.kyc.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class BCryptUtil {

	private BCryptUtil() {
		throw new IllegalStateException("Utility class");
	}

	private static final BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();

	/**
	 * 加密
	 * 
	 * @param password
	 * @return
	 */
	public static String encode(String password) {
		return bcryptPasswordEncoder.encode(password);
	}

	/**
	 * 驗證
	 * 
	 * @param password
	 * @param encodedPassword
	 * @return
	 */
	public static boolean match(String password, String encodedPassword) {
		return bcryptPasswordEncoder.matches(password, encodedPassword);
	}
	//
	public static void main(String[] args) {
		String password = "123456";
		String hashCode = RandomUtil.numEnglishMixRandom(20, false); // salt
		String encodedPassword = encode("Hh123456" + "k70gaq5ls8ga785v5913"); // BCrypt 加密

		String text = "Hsiang123".concat("(").concat("650rlq80es531z28x327").concat(")");
		System.out.println(encode(text));
		// 測試
		// System.out.println(hashCode);
		// System.out.println(encodedPassword);
		// System.out.println(match("1234", encodedPassword));
		// System.out.println(match("12345", encodedPassword));
		// System.out.println(match("123456" + hashCode, encodedPassword));
	}

}
