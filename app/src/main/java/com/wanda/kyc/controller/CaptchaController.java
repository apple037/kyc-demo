package com.wanda.kyc.controller;

import com.wanda.kyc.base.BaseController;
import com.wanda.kyc.service.CaptchaService;
import com.wanda.kyc.utils.captcha.AbstractCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


@Slf4j
@RestController
public class CaptchaController extends BaseController {

	@Autowired
	private CaptchaService captchaService;

	// 生成圖形驗證碼
	@GetMapping("/captcha")
	public void captcha(@RequestParam String time, HttpServletResponse response) {
		AbstractCaptcha captcha = captchaService.captcha(time);
		writeImage(response, captcha.getBufferedImage());
	}


}
