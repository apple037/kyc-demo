package com.wanda.kyc.exception.enumeration;


import com.wanda.kyc.exception.IRuntimeExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum OperatorExceptionEnum implements IRuntimeExceptionEnum {

	//@off
	// 帳號
	ACCOUNT_NOT_EXIST("EO_001_1", "ACCOUNT_NOT_EXIST", "帳號不存在"),
	ACCOUNT_STATUS_DISABLE("EO_001_2", "ACCOUNT_STATUS_DISABLE", "帳號被關閉"),
	PASSWORD_ERROR("EO_001_3", "PASSWORD_ERROR", "密碼錯誤"),
	OLD_PASSWORD_ERROR("EO_001_4", "OLD_PASSWORD_ERROR", "舊密碼錯誤"),
	OPERATE_PROTECTED("EO_001_5", "OPERATE_PROTECTED", "操作受保護"),

	// 圖形驗證碼
	CAPTURE_CODE_EXPIRED("EO_002_1", "CAPTURE_CODE_EXPIRED", "圖形驗證已過期"),
	CAPTURE_CODE_ERROR("EO_002_2", "CAPTURE_CODE_ERROR", "圖形驗證錯誤"),

	// google驗證
	GOOGLE_AUTH_NOT_EXIST("EO_003_1", "GOOGLE_AUTH_NOT_EXIST", "google驗證未設置"),
	GOOGLE_AUTH_ERROR("EO_003_2", "GOOGLE_AUTH_ERROR", "google驗證錯誤"),
	GOOGLE_AUTH_EXPIRED("EO_003_3", "GOOGLE_AUTH_EXPIRED", "google驗證碼已過期"),
	GOOGLE_AUTH_PASSED_EXPIRED("EO_005_4", "GOOGLE_AUTH_PASSED_EXPIRED", "驗證碼通過過期"),

	// 註冊
	ALREADY_HAVE_EMAIL_ERROR("EO_004_1", "ALREADY_HAVE_EMAIL_ERROR", "已有相同信箱被註冊"),
	ALREADY_HAVE_PHONE_ERROR("EO_004_2", "ALREADY_HAVE_PHONE_ERROR", "已有相同電話被註冊"),
	ALREADY_HAVE_NAME_ERROR("EO_004_3", "ALREADY_HAVE_NAME_ERROR", "已有相同暱稱"),
	INVITE_CODE_NOT_EXIST("EO_004_4", "INVITE_CODE_NOT_EXIST", "邀請碼不存在"),
	ALREADY_HAVE_ADDRESS_ERROR("EO_004_5", "ALREADY_HAVE_ADDRESS_ERROR", "已有相同錢包地址"),

	// 驗證碼
	VERIFY_CODE_EXPIRED("EO_005_1", "VERIFY_CODE_EXPIRED", "驗證碼已過期"),
	VERIFY_CODE_ERROR("EO_005_2", "VERIFY_CODE_ERROR", "驗證碼錯誤"),
	VERIFY_CODE_PASSED_EXPIRED("EO_005_3", "VERIFY_CODE_PASSED_EXPIRED", "驗證碼通過過期"),

	//錢包
	BALANCE_NOT_ENOUGH("EO_007_1", "BALANCE_NOT_ENOUGH", "餘額不足"),
	FORBID_WITHDRAW("EO_007_2", "FORBID_WITHDRAW", "因修改二步驗証，需等待24h才能提幣"),
	INNER_ADDRESS_CAN_NOT_WITHDRAW("EO_007_3", "INNER_ADDRESS_CAN_NOT_WITHDRAW", "內部地址無法提幣，可使用內部轉帳功能"),
	DAILY_WITHDRAW_TOTAL_OVER_LIMIT("EO_007_4", "DAILY_WITHDRAW_TOTAL_OVER_LIMIT", "提幣總額(每日)超出上限"),
	WALLET_NOT_EXIST("EO_007_5", "WALLET_NOT_EXIST", "錢包不存在"),


	;

	private String code;
	private String message;
	private String memo;

	//@on

}
