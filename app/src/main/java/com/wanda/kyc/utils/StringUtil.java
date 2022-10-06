package com.wanda.kyc.utils;


import static com.wanda.kyc.constant.DefaultValueConst.UNIT_USDT;

public class StringUtil {

	private StringUtil() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * 開頭大寫
	 * @param text
	 * @return
	 */
	public static String upperCaseFirst(String text) {
		char[] arr = text.toCharArray();
		arr[0] = Character.toUpperCase(arr[0]);
		return new String(arr);
	}

	public static String symbolBinanceTo(String text) {
		return text.split(UNIT_USDT)[0].concat("/").concat(UNIT_USDT);
	}

	public static void main(String[] args) {
		System.out.println(upperCaseFirst("binance"));
	}

}
