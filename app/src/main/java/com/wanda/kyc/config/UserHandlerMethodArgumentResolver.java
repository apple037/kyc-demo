package com.wanda.kyc.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.wanda.kyc.constant.TokenConst;
import com.wanda.kyc.dto.user.AdminUser;
import com.wanda.kyc.dto.user.AppUser;
import com.wanda.kyc.dto.user.User;
import com.wanda.kyc.service.RedisService;
import com.wanda.kyc.utils.IpUtil;
import com.wanda.kyc.utils.JwtUtil;
import com.wanda.kyc.utils.RedisUtil;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class UserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Autowired
	private RedisService redisService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return User.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {

		HttpServletRequest request = ((HttpServletRequest) webRequest.getNativeRequest());
		return getUser(request);
	}

	private User getUser(HttpServletRequest request) {
		String token = JwtUtil.getRequestToken(request);
		if (token == null || "".equals(token) || "null".equals(token))
			return null;
		String role = JwtUtil.getRole(token);
		String accountId = JwtUtil.getUserId(token);

		User user = null;
		if (TokenConst.ROLE_USER_APP.equals(role)) {
			user = redisService.hashGetAll(RedisUtil.memberUserInfoKey(accountId), AppUser.class);
		} else if (TokenConst.ROLE_USER_ADMIN.equals(role)) {
			user = redisService.hashGetAll(RedisUtil.adminUserInfoKey(accountId), AdminUser.class);
		}
		if (user != null)
			user.setIp(IpUtil.getRemoteIp(request));

		log.info("[controller解析器][從redis取得的基本資料]user:[{}]", user);

		return user;
	}

}
