package com.wanda.kyc.dto.user;

import lombok.Data;


@Data
public class User {

	// 角色 (redis)
	private String role;
	// 登入帳號 (redis)
	private String userId;
	// token secret (redis)
	private String tokenSecret;

	// 操作ip 操作當下的
	private String ip;

}
