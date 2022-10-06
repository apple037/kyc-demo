package com.wanda.kyc.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class AmtUtil {

	public static int AMOUNT_SCALE;

	@Value("${amount.scale:2}")
	public void setAmountScale(int amountScale) {
		AmtUtil.AMOUNT_SCALE = amountScale;
	}

	public static BigDecimal format(BigDecimal amount) {
		return new BigDecimal(amount.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
	}

	public static String format(String amount) {
		return new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
	}

	public static String format(String amount, int scale) {
		return new BigDecimal(amount).setScale(scale, BigDecimal.ROUND_HALF_UP).toPlainString();
	}

	public static String formatByPlatformScale(String amount) {
		return new BigDecimal(amount).setScale(AMOUNT_SCALE, BigDecimal.ROUND_HALF_UP).toPlainString();
	}

	public static String formatByPlatformScale(BigDecimal amount) {
		return amount.setScale(AMOUNT_SCALE, BigDecimal.ROUND_HALF_UP).toPlainString();
	}

	public static String divide(String inAmount, String divisor) {
		return divide(inAmount, divisor, 2);
	}

	public static String divide(String inAmount, String divisor, int digit) {
		return new BigDecimal(inAmount).divide(new BigDecimal(divisor), digit, BigDecimal.ROUND_HALF_UP).toPlainString();
	}
	public static String divide(String inAmount, String divisor, int digit, int mode) {
		return new BigDecimal(inAmount).divide(new BigDecimal(divisor), digit, mode).toPlainString();
	}

	public static BigDecimal divide(BigDecimal inAmount, BigDecimal divisor) {
		return divide(inAmount, divisor, 2);
	}

	public static BigDecimal divide(BigDecimal inAmount, BigDecimal divisor, int digit) {
		return inAmount.divide(divisor, digit, BigDecimal.ROUND_HALF_UP);
	}
	public static BigDecimal divide(BigDecimal inAmount, BigDecimal divisor, int digit,int mode) {
		return inAmount.divide(divisor, digit, mode);
	}

	public static String multiply(String inAmount, String multiple, int digit) {
		return new BigDecimal(inAmount).multiply(new BigDecimal(multiple)).setScale(digit, BigDecimal.ROUND_HALF_UP).toPlainString();
	}

	public static BigDecimal multiply(BigDecimal inAmount, BigDecimal multiple, int digit) {
		return inAmount.multiply(multiple).setScale(digit, BigDecimal.ROUND_HALF_UP);
	}

	public static String multiply(String inAmount, String multiple) {
		return multiply(inAmount, multiple, 2);
	}

	public static BigDecimal multiply(BigDecimal inAmount, BigDecimal multiple) {
		return multiply(inAmount, multiple, 2);
	}

	public static String add(String a, String b) {
		return new BigDecimal(a).add(new BigDecimal(b)).toPlainString();
	}

	public static String add(String a, String b, int digit) {
		return new BigDecimal(a).add(new BigDecimal(b)).setScale(digit, BigDecimal.ROUND_HALF_UP).toPlainString();
	}

	public static BigDecimal add(BigDecimal a, BigDecimal b) {
		return a.add(b);
	}

	public static BigDecimal add(BigDecimal a, BigDecimal b, int digit) {
		return a.add(b).setScale(digit, BigDecimal.ROUND_HALF_UP);
	}

	public static String add(String... amounts) {
		String outAmount = "0";
		for (String amt : amounts) {
			outAmount = add(outAmount, amt);
		}
		return outAmount;
	}

	public static String subtract(String a, String b) {
		return new BigDecimal(a).subtract(new BigDecimal(b)).toPlainString();
	}

	public static String subtract(String a, String b, int digit) {
		return new BigDecimal(a).subtract(new BigDecimal(b)).setScale(digit, BigDecimal.ROUND_HALF_UP).toPlainString();
	}

	public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
		return a.subtract(b);
	}

	public static BigDecimal subtract(BigDecimal a, BigDecimal b, int digit) {
		return a.subtract(b).setScale(digit, BigDecimal.ROUND_HALF_UP);
	}

	// 等於
	public static boolean eq(String a, String b) {
		return new BigDecimal(a).compareTo(new BigDecimal(b)) == 0;
	}

	// 不等於
	public static boolean ne(String a, String b) {
		return new BigDecimal(a).compareTo(new BigDecimal(b)) != 0;
	}

	// 大於
	public static boolean gt(String a, String b) {
		return new BigDecimal(a).compareTo(new BigDecimal(b)) == 1;
	}

	// 大於等於
	public static boolean gte(String a, String b) {
		return new BigDecimal(a).compareTo(new BigDecimal(b)) != -1;
	}

	// 小於
	public static boolean lt(String a, String b) {
		return new BigDecimal(a).compareTo(new BigDecimal(b)) == -1;
	}

	// 小於等於
	public static boolean lte(String a, String b) {
		return new BigDecimal(a).compareTo(new BigDecimal(b)) != 1;
	}

	// 絕對值
	public static String abs(String amount) {
		return new BigDecimal(amount).abs().toPlainString();
	}

	// 絕對負值
	public static String negative(String amount) {
		return new BigDecimal(amount).abs().negate().toPlainString();
	}

	public static boolean limit(String min, String max, String value) {
		return gte(value, min) && lte(value, max);
	}

	public static String formatZero(String value) {
		return new BigDecimal(value).stripTrailingZeros().toPlainString();
	}

	// 等於
	public static boolean eq(BigDecimal a, BigDecimal b) {
		return a.compareTo(b) == 0;
	}

	// 不等於
	public static boolean ne(BigDecimal a, BigDecimal b) {
		return a.compareTo(b) != 0;
	}

	// 大於
	public static boolean gt(BigDecimal a, BigDecimal b) {
		return a.compareTo(b) == 1;
	}

	// 大於等於
	public static boolean gte(BigDecimal a, BigDecimal b) {
		return a.compareTo(b) != -1;
	}

	// 小於
	public static boolean lt(BigDecimal a, BigDecimal b) {
		return a.compareTo(b) == -1;
	}

	// 小於等於
	public static boolean lte(BigDecimal a, BigDecimal b) {
		return a.compareTo(b) != 1;
	}

	// 絕對值
	public static BigDecimal abs(BigDecimal amount) {
		return amount.abs();
	}

	// 絕對負值
	public static BigDecimal negative(BigDecimal amount) {
		return amount.abs().negate();
	}

	public static boolean limit(BigDecimal min, BigDecimal max, BigDecimal value) {
		return gte(value, min) && lte(value, max);
	}

	public static String formatZero(BigDecimal value) {
		return value.stripTrailingZeros().toPlainString();
	}

}