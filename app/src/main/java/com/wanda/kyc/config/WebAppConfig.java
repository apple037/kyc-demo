package com.wanda.kyc.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class WebAppConfig implements WebMvcConfigurer {

	@Autowired
	private UserHandlerMethodArgumentResolver userHandlerMethodArgumentResolver;

	/**
	 * 註冊controller user參數解析器
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(userHandlerMethodArgumentResolver);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
