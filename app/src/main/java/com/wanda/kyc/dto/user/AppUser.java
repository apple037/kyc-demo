package com.wanda.kyc.dto.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class AppUser extends User {

	// 名稱 (redis)
	private String name;
	// 錢包地址 (redis)
	private String address;
	// 信箱
	private String email;
	@JSONField(serialize = false)
	private Long id;

	public Long getId() {
		if (id == null)
			id = new Long(getUserId());
		return id;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
