package com.wanda.kyc.utils;

import com.wanda.kyc.exception.SystemRuntimeException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public class AesUtil {

	private AesUtil() {
		throw new IllegalStateException("Utility class");
	}

	private static final String RNG_ALGORITHM = "SHA1PRNG";
	private static final String KEY_ALGORITHM = "AES";
	private static final int KEY_SIZE = 256;

	private static SecretKey getKey(String key) throws NoSuchAlgorithmException {
		// 創建安全隨機數生成器
		SecureRandom random = SecureRandom.getInstance(RNG_ALGORITHM);
		// 設置 密鑰key的字節數組 作為安全隨機數生成器的種子
		random.setSeed(key.getBytes(StandardCharsets.UTF_8));
		// 創建AES算法生成器
		KeyGenerator gen = KeyGenerator.getInstance(KEY_ALGORITHM);
		// 初始化算法生成器
		gen.init(KEY_SIZE, random);
		// 生成 AES密鑰對象
		return gen.generateKey();
	}

	public static String encrypt(String plainText, String key) {
		try {
			Key secretKey = getKey(key);
			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] result = cipher.doFinal(Base64Util.encodeBytes(plainText));
			return new String(Base64.encodeBase64(result), StandardCharsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
			throw SystemRuntimeException.systemError();
		}

	}

	public static String decrypt(String encryptText, String key) {
		try {
			Key secretKey = getKey(key);
			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] result = cipher.doFinal(Base64Util.decodeBytes(encryptText));
			return Base64Util.decode(new String(result, StandardCharsets.UTF_8));
		} catch (Exception e) {
			e.printStackTrace();
			throw SystemRuntimeException.systemError();
		}

	}

	public static void main(String[] args) throws Exception {
		String plainText = "test測試test測試test測";
		String key = "12345";
		String encryptText = encrypt(plainText, key);
		System.out.println(encryptText);
		System.out.println(decrypt(encryptText, "12345"));
	}

}
