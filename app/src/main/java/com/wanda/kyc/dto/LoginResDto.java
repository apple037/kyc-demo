package com.wanda.kyc.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanda.kyc.utils.BigDecimalSerialize;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Builder
public class LoginResDto {

	private Long id;
	private String name;
	private String email;
	private String token;

}
