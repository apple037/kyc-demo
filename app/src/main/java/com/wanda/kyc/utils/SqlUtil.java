package com.wanda.kyc.utils;

public class SqlUtil {

	private SqlUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static final String MARK_PERCENT = "%";

	public static String prefixLike(String data) {
		return MARK_PERCENT.concat(data);
	}

	public static String suffixLike(String data) {
		return data.concat(MARK_PERCENT);
	}

	public static String like(String data) {
		return MARK_PERCENT.concat(data).concat(MARK_PERCENT);
	}
}
