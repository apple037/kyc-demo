package com.wanda.kyc.utils;

import java.util.regex.Pattern;


public class RegularUtil {

	private RegularUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static final String REGEX_TIME_UNIT = "^[1-9]\\d*\\.[5]$|0\\.[5]$|^[1-9]\\d*$";
	public static final String REGEX_ABS = "^(?!(0[0-9]{0,}$))[0-9]{1,}[.]{0,}[0-9]{0,}$";
	public static final String REGEX_NATURAL = "^\\+?[1-9][0-9]*$";

	/**
	 * 是否為0.5的倍數
	 * 
	 * @param text
	 * @return
	 */
	public static boolean timeUnit(String text) {
		return Pattern.matches(REGEX_TIME_UNIT, text);
	}

	/**
	 * 是否為大於0的數
	 * 
	 * @param text
	 * @return
	 */
	public static boolean abs(String text) {
		return Pattern.matches(REGEX_ABS, text);
	}

	/**
	 * 是否為正整數
	 * 
	 * @param text
	 * @return
	 */
	public static boolean natural(String text) {
		return Pattern.matches(REGEX_NATURAL, text);
	}

	public static boolean match(String regex, String text) {
		return Pattern.compile(regex).matcher(text).find();
	}

	public static void main(String[] args) {
		System.out.println(match("mail|phone", "mail"));
		System.out.println(match("mail|phone", "phone"));
		System.out.println(match("mail|phone", "ddd"));
	}

}
