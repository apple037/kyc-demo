package com.wanda.kyc.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wanda.kyc.exception.IRuntimeExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseModel implements Serializable {

    private static final long serialVersionUID = -7147650333527797098L;

    private String code;
    private String message;
    private Object data;

    public static ResponseModel success() {
        return ResponseModel.builder()
                .code(ResponseEnum.SUCCESS.getCode())
                .message(ResponseEnum.SUCCESS.getMessage())
                .build();
    }

    public static ResponseModel success(Object data) {
        return ResponseModel.builder()
                .code(ResponseEnum.SUCCESS.getCode())
                .message(ResponseEnum.SUCCESS.getMessage())
                .data(data)
                .build();
    }

    public static ResponseModel error(IRuntimeExceptionEnum runtimeExceptionEnum) {
        return ResponseModel.builder()
                .code(runtimeExceptionEnum.getCode())
                .message(runtimeExceptionEnum.getMessage())
                .build();
    }

}

