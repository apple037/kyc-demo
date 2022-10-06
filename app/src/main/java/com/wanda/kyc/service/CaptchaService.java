package com.wanda.kyc.service;

import com.wanda.kyc.constant.ExpireTimeConst;
import com.wanda.kyc.utils.RedisUtil;
import com.wanda.kyc.utils.captcha.AbstractCaptcha;
import com.wanda.kyc.utils.captcha.CustomCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CaptchaService {

	@Autowired
	private RedisService redisService;
	
	/**
	 * 圖形驗證碼生成
	 *
	 * @param captchaName the captcha name
	 * @return the abstract captcha
	 */
	public AbstractCaptcha captcha(String captchaName) {
		String captchaKey = RedisUtil.captcha(captchaName);
		AbstractCaptcha captcha = new CustomCaptcha();
		redisService.stringSet(captchaKey, captcha.getVerifyCode(), ExpireTimeConst.CAPTCHA_EXPIRE_TIME);
		return captcha;
	}
}
