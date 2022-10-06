package com.wanda.kyc.config;

import com.alibaba.fastjson.JSON;
import com.wanda.kyc.constant.LanguageConst;
import com.wanda.kyc.exception.SystemRuntimeException;
import com.wanda.kyc.exception.enumeration.SystemExceptionEnum;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;


@Slf4j
@Setter
@Validated
@Configuration
@ConfigurationProperties(prefix = "env.platform")
@ConditionalOnProperty(value = "env.switch.platform", havingValue = "true", matchIfMissing = true)
public class Platform {

	@NotNull
	private String profile;
	@NotNull
	private String code;
	@NotNull
	private String enName;
	@NotNull
	private String twName;
	@NotNull
	private String cnName;

	public String getProfile() {
		return this.profile;
	}

	public String getCode() {
		return this.code;
	}

	public String getPlatformName() {
		return this.enName;
	}

	public String getPlatformName(String lang) {
		switch (lang) {
			case LanguageConst.EN_US:
				return this.enName;
			case LanguageConst.ZH_TW:
				return this.twName;
			default:
				log.error("不支援此種語系:[{}]", lang);
				throw new SystemRuntimeException(SystemExceptionEnum.DATA_ERROR);
		}
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
