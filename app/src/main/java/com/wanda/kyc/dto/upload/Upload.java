package com.wanda.kyc.dto.upload;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class Upload {

	@NotNull
	private String type;
	@NotNull
	private String fileName;

}
