package com.wanda.kyc.utils;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;


public class Base64Util {

	private Base64Util() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * @description base64 編碼
	 * @return 回傳byte[]
	 **/
	public static byte[] encodeBytes(String str) {
		return Base64.encodeBase64(str.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * @description base64 解碼
	 * @return 回傳byte[]
	 **/
	public static byte[] decodeBytes(String str) {
		return Base64.decodeBase64(str.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * @description base64 編碼
	 * @return 回傳string
	 * @author Hsiang
	 * @throws RuntimeException
	 * @since 2020/08/31
	 **/
	public static String encode(byte[] bytes) {
		return new String(Base64.encodeBase64(bytes), StandardCharsets.UTF_8);
	}

	/**
	 * @description base64 解碼
	 * @return 回傳string
	 **/
	public static String decode(byte[] bytes) {
		return new String(Base64.decodeBase64(bytes), StandardCharsets.UTF_8);
	}

	/**
	 * @description base64 編碼
	 * @return 回傳string
	 **/
	public static String encode(String str) {
		return new String(Base64.encodeBase64(str.getBytes(StandardCharsets.UTF_8)));
	}

	/**
	 * @description base64 解碼
	 * @return 回傳string
	 **/
	public static String decode(String str) {
		return new String(Base64.decodeBase64(str.getBytes(StandardCharsets.UTF_8)));
	}

	public static void main(String[] args) {
		String text = "測試";
		String encodeText = encode(text);
		System.out.println(encodeText);
		System.out.println(decode(encodeText));
	}

}
