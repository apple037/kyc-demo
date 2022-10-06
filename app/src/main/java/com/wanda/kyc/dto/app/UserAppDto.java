package com.wanda.kyc.dto.app;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserAppDto {

    //會員ID
    private Long id;
    //帳戶類型
    private String memberId;
    //名稱
    private String username;
    //密碼
    private String password;
    private String realName;
    private String identityCode;
    //註冊時間
    private String registerAt;
    private String salt;
    //狀態
    private String status;

}
