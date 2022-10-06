package com.wanda.kyc.utils;


import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;


public class RsaUtil {

	private static final String KEY_ALGORITHM = "RSA";
	private static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
	private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

	private static final int KEY_SIZE = 1024;
	private static final int MAX_ENCRYPT_BLOCK = 117;
	private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 生成密钥对
     */
    public static Map<String, String> generateKeyPair() throws Exception {
		SecureRandom sr = new SecureRandom();
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(KEY_SIZE, sr);

		// 生成 RSA密鑰
		KeyPair kp = kpg.generateKeyPair();
		// 得到私鑰
		Key privateKey = kp.getPrivate();
		byte[] privateKeyBytes = privateKey.getEncoded();
		String pri = Base64Util.encode(privateKeyBytes);
		// 得到公鑰
		Key publicKey = kp.getPublic();
		byte[] publicKeyBytes = publicKey.getEncoded();
		String pub = Base64Util.encode(publicKeyBytes);

		Map<String, String> map = new HashMap<>();
		map.put("privateKey", pri);
		map.put("publicKey", pub);

		RSAPublicKey rsp = (RSAPublicKey) kp.getPublic();
		BigInteger bint = rsp.getModulus();
		byte[] b = bint.toByteArray();
		byte[] deBase64Value = Base64.encodeBase64(b);
		String retValue = new String(deBase64Value);
		map.put("modulus", retValue);

		return map;

    }

	/**
	 * @description RSA 公鑰加密
	 * @param paramSrc
	 * @param encodeType
	 * @return response
	 */
	public static String encrypt(String plainText, String publicKey) throws Exception {
		try {
			byte[] decoded = Base64Util.decodeBytes(publicKey);
			RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(decoded));
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			// 沒做分段加密 不能超過128
			// return Base64.encodeBase64String(cipher.doFinal(data.getBytes("UTF-8")));

			// 分段加密
			byte[] dataByte = Base64Util.encodeBytes(plainText);
			int inputLen = dataByte.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(dataByte, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(dataByte, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			byte[] encryptedData = out.toByteArray();
			out.close();
			return new String(Base64Util.encode(encryptedData));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}



	/**
	 * @description RSA 私鑰解密
	 * @param encryptText
	 * @param privateKey
	 * @return response
	 */
	public static String decrypt(String encryptText, String privateKey) throws Exception {
		try {
			RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(KEY_ALGORITHM)
					.generatePrivate(new PKCS8EncodedKeySpec(Base64Util.decodeBytes(privateKey)));
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, priKey);
			// 沒做分段解密 不能超過128
			// return new String(cipher.doFinal(inputByte));

			// 分段解密
			byte[] encryptByte = Base64Util.decodeBytes(encryptText);
			int inputLen = encryptByte.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(encryptByte, offSet, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(encryptByte, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			byte[] decryptedByte = out.toByteArray();
			out.close();
			return Base64Util.decode(new String(decryptedByte, StandardCharsets.UTF_8));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * RSA 私鑰簽名
	 * 
	 * @param plainText
	 * @param privateKey
	 * @return
	 * @author Hsiang
	 * @throws RuntimeException
	 * @since 2020/07/12
	 */
	public static String sign(String plainText, String privateKey) throws Exception {
		try {
			byte[] keyBytes = Base64Util.decodeBytes(privateKey);
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initSign(privateK);
			signature.update(plainText.getBytes(StandardCharsets.UTF_8));
			return Base64Util.encode(signature.sign());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * RSA 公鑰驗簽
	 * 
	 * @param data
	 * @param publicKey
	 * @param sign
	 * @return
	 * @author Hsiang
	 * @throws RuntimeException
	 * @since 2020/07/12
	 */
	public static boolean verify(String data, String publicKey, String sign) throws Exception {
		try {
			byte[] keyBytes = Base64Util.decodeBytes(publicKey);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			PublicKey publicK = keyFactory.generatePublic(keySpec);
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initVerify(publicK);
			signature.update(data.getBytes(StandardCharsets.UTF_8));
			return signature.verify(Base64Util.decodeBytes(sign));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private static String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ1qok0L6giuWOQ23EJ/jXUeJVP3mEOUHfj3fuHPlyT3ZJkI04uJuea/TEsk8/9LD2Ovyx2HZftwzIyp+baMANcwdNuzCIKPT3cmTCvAEqRwrSZuoFly4r92jnW9xkmeixOlU7NdYJz5nz0sOlhWaMec+hkd2+5sL3UMIkfYZTMpAgMBAAECgYACJH1pBwx8acMT+BsvXIUUXwCrD+emo9F0ngnAEQ9BlYxs+M3ITGuXVGs5aptXkjH3bWEaWcltjq96CqY3cdhhD/OEfJWB2Bb83FMSA+oO8dHippbJeg6cn8C7w2c3xbzOxLAIxS4n511HtKyhOerrN9Ev6k22R/u0L7EQUR6dwQJBANGmKCagjdPo8bQB1Annf4Hr7pEUNcPOS60iohem0Gi2MNEEqiGMAIuF/XXid+RxxwAd1TEpNhLzaNH9QfbYq40CQQDAODCmPMimf4ENSavOQUiOFgipecC3FyB0FLgO4bgTOcNnmBapMm8486HguOjl3pGfCkLsOymDI+Q4o0xt6LENAkApyn1mdcrP2zeTMmoaL1NzipxbvzOYfJ8JPEYjgNU6ilbg05U2aroRFfyYazSLDUMl+sxsqFJnJK5YbaugkUBhAkEAnNj88ZklzZ67SoFz/NCOiMp39PqRg5UOeUoyyonq710yEsUqsJOj6B/9VmbsPC46O0FfTG4WOJk/7+toQUMfvQJBALIe6FoR0R/nI9zjIBTRBUH1xteuWhFoWBhmpQN1/A7xV6rfKciCd2sJukknqi2MIadOVtombq0w1TdTg3QDnH4=";
	private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdaqJNC+oIrljkNtxCf411HiVT95hDlB34937hz5ck92SZCNOLibnmv0xLJPP/Sw9jr8sdh2X7cMyMqfm2jADXMHTbswiCj093JkwrwBKkcK0mbqBZcuK/do51vcZJnosTpVOzXWCc+Z89LDpYVmjHnPoZHdvubC91DCJH2GUzKQIDAQAB";

    public static void main(String[] args) throws Exception {
		// Map<String, String> pairMap = generateKeyPair();
		// System.out.println(pairMap.get("privateKey"));
		// System.out.println(pairMap.get("publicKey"));
		String encryptText = "Cz7As+4DjC7YDElQT6eitvuE6avuBn2TpkyXXMUbNLW74CYNW9yQaP9aCObGjv1y/D912g9J138M72TzctN+o04FEiqt9+htBSNdl2fK5Enodq6WaIHP89V5jR8OVSpsloC1q2Hq7hLnbllJa96Oux32A1haVMH51yNK/85cUTE=";
		System.out.println(decrypt(encryptText, privateKey));
	}
    
}
