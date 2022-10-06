package com.wanda.kyc.dto.user;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class AdminUser extends User {

	// 後台角色 role_id (redis)
	private String adminRole;
	// 信箱 (redis)
	private String email;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
