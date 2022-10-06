package com.wanda.kyc.utils;

import org.springframework.util.StringUtils;


public class PrivacyUtil {

	private PrivacyUtil() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * 保留字串剩餘後幾碼，前面被*掉
	 * 
	 * @param str 欲碼字串
	 * @param count 剩餘幾碼
	 * @return
	 */
	public static String retainLastYardsByCount(String str, int count) {
		if (!StringUtils.hasLength(str) || count > str.length()) {
			return str;
		}
		int replaceCount = str.length() - count;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < replaceCount; i++) {
			sb.append("*");
		}
		String strh = str.substring(str.length() - count, str.length());

		return sb.append(strh).toString();

	}

	/**
	 * 保留字串剩餘前後幾碼，其它被*掉
	 * 
	 * @param str 欲碼字串
	 * @param frontCount 前面保留幾碼
	 * @param lastCount 後面保留幾碼
	 * @return
	 */
	public static String retainFrontAndLastYardsByCount(String str, int frontCount, int lastCount) {
		if (!StringUtils.hasLength(str) || (frontCount + lastCount) > str.length()) {
			return str;
		}
		int replaceCount = str.length() - (frontCount + lastCount);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < replaceCount; i++) {
			sb.append("*");
		}
		String frontStr = str.substring(0, frontCount);
		String lastStr = str.substring(str.length() - lastCount);

		return frontStr.concat(sb.append(lastStr).toString());

	}

	public static String privacyApiKey(String apiKey) {
		return retainFrontAndLastYardsByCount(apiKey, 3, 3);
	}

	// public static void main(String[] args) {
	// // System.out.print(retainLastYardsByCount("123456789", 4));
	// System.out.print(retainFrontAndLastYardsByCount("123456789", 3, 3));
	// }
}
