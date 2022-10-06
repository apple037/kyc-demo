package com.wanda.kyc.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;


public class RandomUtil {

	private RandomUtil() {
		throw new IllegalStateException("Utility class");
	}

	private static final char CHAR_UPPER_A = 65;
	private static final char CHAR_LOWER_A = 97;
	public static Random rand;

	static {
		try {
			rand = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 產生數字亂數
	 * 
	 * @param size
	 * @return
	 */
	public static String numRandom(int size) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(rand.nextInt(10));
		}
		return sb.toString();
	}

	/**
	 * 產生英文亂數
	 * 
	 * @param size
	 * @param isUpperCase
	 * @return
	 */
	public static String englishRandom(int size, boolean isUpperCase) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			char ch = (char) (rand.nextInt(26) + CHAR_LOWER_A);
			sb.append(ch);
		}
		return transform(sb.toString(), isUpperCase);
	}

	/**
	 * 產生數字英文混合
	 * 
	 * @param size
	 * @param isUpperCase
	 * @return
	 */
	public static String numEnglishMixRandom(int size, boolean isUpperCase) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			boolean isNum = rand.nextBoolean();
			if (isNum) {
				sb.append(rand.nextInt(10));
			} else {
				char ch = (char) (rand.nextInt(26) + CHAR_LOWER_A);
				sb.append(ch);
			}
		}
		return transform(sb.toString(), isUpperCase);
	}

	private static String transform(String text, boolean isUpperCase) {
		if (isUpperCase) {
			return text.toUpperCase();
		}
		return text;
	}

	// public static void main(String[] args) {
	// System.out.println("產生亂數");
	// System.out.println(numRandom(10));
	// System.out.println(numRandom(10));
	// System.out.println(numRandom(10));
	// System.out.println(numRandom(10));
	// System.out.println(numRandom(10));
	// System.out.println("");
	// System.out.println(englishRandom(10, false));
	// System.out.println(englishRandom(10, false));
	// System.out.println(englishRandom(10, false));
	// System.out.println(englishRandom(10, true));
	// System.out.println(englishRandom(10, true));
	//
	// System.out.println(rand.nextInt(1));
	// System.out.println(rand.nextInt(26));
	// System.out.println(rand.nextInt(26));
	// System.out.println(rand.nextInt(26)); // 0~25
	// System.out.println((char) (0 + CHAR_LOWER_A));
	// System.out.println((char) (25 + CHAR_LOWER_A));
	// System.out.println();
	// System.out.println(numEnglishMixRandom(10, false));
	// System.out.println(numEnglishMixRandom(10, false));
	// System.out.println(numEnglishMixRandom(10, false));
	// System.out.println(numEnglishMixRandom(10, true));
	// System.out.println(numEnglishMixRandom(10, true));
	// }
}
