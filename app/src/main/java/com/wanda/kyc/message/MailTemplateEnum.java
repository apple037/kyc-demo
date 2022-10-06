package com.wanda.kyc.message;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum MailTemplateEnum {
	
	//@off
	
	// APP
	APP_VERIFY_CODE("EMAIL_TITLE.APP.VERIFY_CODE", "app-verifyCode-%s.html"),
	
	// 後台
	BACKSTAGE_VERIFY_CODE("EMAIL_TITLE.BACKSTAGE.CREATE_STAFF", "backstage-verifyCode-%s.html"),
	BACKSTAGE_CREATE_STAFF("EMAIL_TITLE.BACKSTAGE.VERIFY_CODE", "backstage-createStaff-%s.html"),
	
	;
	
	//@on

	private String titleI18nKey;
	private String templateName;

}
