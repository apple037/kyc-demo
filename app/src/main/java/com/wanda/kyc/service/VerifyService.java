package com.wanda.kyc.service;

import com.wanda.kyc.constant.ExpireTimeConst;
import com.wanda.kyc.exception.Validator;
import com.wanda.kyc.exception.enumeration.OperatorExceptionEnum;
import com.wanda.kyc.utils.AesUtil;
import com.wanda.kyc.utils.RedisUtil;
import com.wanda.kyc.utils.googleauth.GoogleAuthenticatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class VerifyService {

	@Value("${spring.application.name}")
	private String applicationName;

	@Autowired
	private RedisService redisService;

	/**
	 * 存驗證碼
	 * 
	 * @param account
	 * @param action
	 * @param verifyCode
	 */
	public void saveVerify(String account, String action, String verifyCode) {
		redisService.stringSet(RedisUtil.getKeyByVerifyAction(account, action), verifyCode, ExpireTimeConst.VERIFY_CODE_EXPIRE_TIME);
	}

	/**
	 * 比對驗證碼
	 * 
	 * @param account
	 */
	public void checkVerify(String account, String action, String verifyCodeByUser) {
		String redisKey = RedisUtil.getKeyByVerifyAction(account, action);
		String verifyCode = redisService.stringGet(redisKey);
		Validator.isNullThrow(verifyCode, OperatorExceptionEnum.VERIFY_CODE_EXPIRED);
		Validator.isFalseThrow(verifyCode.equals(verifyCodeByUser), OperatorExceptionEnum.VERIFY_CODE_ERROR);
		redisService.delKey(redisKey);
	}

	/**
	 * 暫時保存 已經確認過驗證碼的帳號
	 * 
	 * @param account
	 */
	public void saveVerifyCodePassed(String account) {
		redisService.stringSet(RedisUtil.verifyCodePassed(account), account, ExpireTimeConst.VERIFY_CODE_PASSED_EXPIRE_TIME);
	}

	/**
	 * 檢查 驗證碼通過 是否過期
	 * 
	 * @param account
	 */
	public void checkVerifyCodePassed(String account) {
		String redisKey = RedisUtil.verifyCodePassed(account);
		Validator.isFalseThrow(redisService.exist(redisKey), OperatorExceptionEnum.VERIFY_CODE_PASSED_EXPIRED);
		redisService.delKey(redisKey);
	}

	/**
	 * 存google驗證碼
	 * 
	 * @param account
	 * @param secretKey
	 */
	public void saveGoogleAuth(String account, String secretKey) {
		redisService.stringSet(RedisUtil.googleAuth(account), secretKey, ExpireTimeConst.GOOGLE_AUTH_EXPIRE_TIME);
	}

	/**
	 * 比對google驗証碼 綁定用
	 * 
	 * @param account
	 * @param code
	 * @return
	 */
	public String checkGoogleAuthTemp(String account, String code) {
		String redisKey = RedisUtil.googleAuth(account);
		String secretKey = redisService.stringGet(redisKey);
		Validator.isNullThrow(secretKey, OperatorExceptionEnum.GOOGLE_AUTH_EXPIRED);
		Validator.isFalseThrow(GoogleAuthenticatorUtils.verify(secretKey, code), OperatorExceptionEnum.GOOGLE_AUTH_ERROR);
		redisService.delKey(redisKey);
		return secretKey;
	}

	/**
	 * 檢查google驗證碼
	 * 
	 * @param googleAuth
	 * @param salt
	 * @param code
	 * @return
	 */
	public boolean checkGoogleAuth(String googleAuth, String salt, String code) {
		return GoogleAuthenticatorUtils.verify(AesUtil.decrypt(googleAuth, salt), code);
	}

	/**
	 * 暫時保存 已經確認過驗證碼的帳號
	 * 
	 * @param account
	 */
	public void saveGoogleAuthPassed(String account) {
		redisService.stringSet(RedisUtil.googleAuthPassed(account), account, ExpireTimeConst.GOOGLE_AUTH_EXPIRE_TIME);
	}

}
