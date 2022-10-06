package com.wanda.kyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;


@Service
public class LocaleMessageSourceService {

	@Autowired
	private MessageSource messageSource;

	/**
	 * @param code ：对应messages配置的key.
	 * @return
	 */
	public String getMessage(String lang, String code) {
		return getMessage(lang, code, null);
	}

	/**
	 *
	 * @param code ：对应messages配置的key.
	 * @param args : 数组参数.
	 * @return
	 */
	public String getMessage(String lang, String code, Object[] args) {
		return getMessage(lang, code, args, "NOT_MATCHED_KEY");
	}

	/**
	 *
	 * @param code ：对应messages配置的key.
	 * @param args : 数组参数.
	 * @param defaultMessage : 没有设置key的时候的默认值.
	 * @return
	 */
	public String getMessage(String lang, String code, Object[] args, String defaultMessage) {
		String[] split = lang.split("-");
		Locale locale = new Locale(split[0], split[1]);
		return messageSource.getMessage(code, args, defaultMessage, locale);
	}

}
