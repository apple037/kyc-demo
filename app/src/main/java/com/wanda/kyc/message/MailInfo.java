package com.wanda.kyc.message;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.Map;


@Data
public class MailInfo {

	// 語言
	private String lang;
	// 寄給誰
	private String toEmail;
	// 何種模板
	private MailTemplateEnum mailTemplateEnum;
	// 信件動態參數
	private Map<String, String> templateParamMap;
	
	private MailInfo(String lang, String toEmail, MailTemplateEnum mailTemplateEnum, Map<String, String> templateParamMap) {
		this.lang = lang;
		this.toEmail = toEmail;
		this.mailTemplateEnum = mailTemplateEnum;
		this.templateParamMap = templateParamMap;
	}

	public static MailInfo create(String lang, String email, MailTemplateEnum mailTemplateEnum, Map<String, String> templateParamMap) {
		return new MailInfo(lang, email, mailTemplateEnum, templateParamMap);
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
