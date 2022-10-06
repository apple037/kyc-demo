package com.wanda.kyc.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.wanda.kyc.constant.DefaultValueConst;

import java.io.IOException;
import java.math.BigDecimal;


public class BigDecimalSerialize extends JsonSerializer<BigDecimal> {

	@Override
	public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
		if (value != null) {
			gen.writeString(AmtUtil.formatZero(value));
		} else {
			gen.writeString(DefaultValueConst.ZERO);
		}
	}

}
