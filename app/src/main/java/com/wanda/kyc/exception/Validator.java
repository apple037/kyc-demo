package com.wanda.kyc.exception;

import java.util.List;


public class Validator {

	private Validator() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * 檢核是true 丟錯
	 *
	 * @param bool
	 * @param runtimeExceptionEnum
	 */
	public static void isTrueThrow(boolean bool, IRuntimeExceptionEnum runtimeExceptionEnum) {
		if (bool) {
			throw new SystemRuntimeException(runtimeExceptionEnum);
		}
	}

	/**
	 * 檢核是false 丟錯
	 *
	 * @param bool
	 * @param runtimeExceptionEnum
	 */
	public static void isFalseThrow(boolean bool, IRuntimeExceptionEnum runtimeExceptionEnum) {
		if (!bool) {
			throw new SystemRuntimeException(runtimeExceptionEnum);
		}
	}

	/**
	 * 檢核物件為空 丟錯
	 * @param object
	 * @param runtimeExceptionEnum
	 */
	public static void isNullThrow(Object object, IRuntimeExceptionEnum runtimeExceptionEnum) {
		if (null == object) {
			throw new SystemRuntimeException(runtimeExceptionEnum);
		}
	}

	/**
	 * 檢核物件不為空 丟錯
	 * @param object
	 * @param runtimeExceptionEnum
	 */
	public static void isNotNullThrow(Object object, IRuntimeExceptionEnum runtimeExceptionEnum) {
		if (null != object) {
			throw new SystemRuntimeException(runtimeExceptionEnum);
		}
	}

	/**
	 * 檢核物件不為空 丟錯
	 * @param str
	 * @param runtimeExceptionEnum
	 */
	public static void isEmptyStringThrow(String str, IRuntimeExceptionEnum runtimeExceptionEnum) {
		if (null == str || str.isEmpty()) {
			throw new SystemRuntimeException(runtimeExceptionEnum);
		}
	}

	/**
	 * 檢核物件不為空 丟錯
	 * @param str
	 * @param runtimeExceptionEnum
	 */
	public static void isNotEmptyStringThrow(String str, IRuntimeExceptionEnum runtimeExceptionEnum) {
		if (null != str && !str.isEmpty()) {
			throw new SystemRuntimeException(runtimeExceptionEnum);
		}
	}

	/**
	 * 檢核物件不為空 丟錯
	 * @param list
	 * @param runtimeExceptionEnum
	 */
	public static void isEmptyListThrow(List<?> list, IRuntimeExceptionEnum runtimeExceptionEnum) {
		if (null == list || list.isEmpty()) {
			throw new SystemRuntimeException(runtimeExceptionEnum);
		}
	}

	/**
	 * 檢核物件不為空 丟錯
	 * @param list
	 * @param runtimeExceptionEnum
	 */
	public static void isNotEmptyListThrow(List<?> list, IRuntimeExceptionEnum runtimeExceptionEnum) {
		if (!list.isEmpty()) {
			throw new SystemRuntimeException(runtimeExceptionEnum);
		}
	}

}
