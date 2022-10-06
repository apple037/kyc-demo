package com.wanda.kyc.utils;

import lombok.Getter;
import lombok.ToString;
import org.springframework.util.StringUtils;


@Getter
@ToString
public class Page {

	private String limit;
	private String offset;
	
	public Page(String page, String size) {
		if (StringUtils.isEmpty(page) || StringUtils.isEmpty(size)) {
			this.limit = "20";
			this.offset = "0";
		} else {
			this.limit = size;
			this.offset = AmtUtil.format(AmtUtil.multiply(AmtUtil.subtract(page, "1"), size), 0);
		}
	}
	
	public Integer getLimitInteger() {
		return Integer.parseInt(this.limit);
	}
	
	public Integer getOffsetInteger() {
		return Integer.parseInt(this.offset);
	}
	
}


