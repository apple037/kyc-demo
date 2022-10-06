package com.wanda.kyc.exception.enumeration;

import com.wanda.kyc.exception.IRuntimeExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum KycExceptionEnum implements IRuntimeExceptionEnum {

	//@off
	KYC_APPLY_DUPLICATE_ERROR("KYC_0001", "KYC_APPLY_DUPLICATE_ERROR", "申請重複"),
	KYC_AUDIT_RESULT_MISSING_ERROR("KYC_0002", "KYC_AUDIT_RESULT_MISSING_ERROR", "審核結果遺失"),
	KYC_AUDIT_RESULT_TYPE_ERROR("KYC_0003", "KYC_AUDIT_RESULT_TYPE_ERROR", "審核結果參數錯誤"),
	KYC_AUDIT_FLOW_ERROR("KYC_0004", "KYC_AUDIT_FLOW_ERROR", "審核流程錯誤"),
	KYC_ADMIN_PERMISSION_REJECT("KYC_0005", "KYC_ADMIN_PERMISSION_REJECT", "帳號沒有權限"),
	;

	private String code;
	private String message;
	private String memo;

	//@on
}
