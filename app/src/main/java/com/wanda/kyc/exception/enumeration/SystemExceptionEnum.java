package com.wanda.kyc.exception.enumeration;


import com.wanda.kyc.exception.IRuntimeExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum SystemExceptionEnum implements IRuntimeExceptionEnum {

	//@off
	PARAM_ERROR("ES_0001", "PARAM_ERROR", "參數錯誤"),
	INSERT_ERROR("ES_0002", "INSERT_ERROR", "新增失敗"),
	UPDATE_ERROR("ES_0003", "UPDATE_ERROR", "修改失敗"),
	DATA_ERROR("ES_0004", "DATA_ERROR", "資料錯誤"),
	UPLOAD_ERROR("ES_0005", "UPLOAD_ERROR", "上傳檔案失敗"),
	UPLOAD_FILE_NOT_SUPPORTED("ES_0006", "UPLOAD_FILE_NOT_SUPPORTED", "上傳檔案不支援此檔案類型"),
	NO_DATA("ES_0007","NO_DATA","查無資料"),
	MAX_UPLOAD_SIZE_EXCEEDED("ES_0008", "MAX_UPLOAD_SIZE_EXCEEDED", "超過最大上傳大小"),
	
	;

	private String code;
	private String message;
	private String memo;
	
	//@on
}
