package com.wanda.kyc.dto.kyc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class KycApplyResp {
    //會員ID
    private Long id;
    //帳戶類型
    private String memberId;
    //名稱
    private String username;
    //密碼
    @JsonIgnore
    private String password;
    private String realName;
    private String identityCode;
    private String birthdate;

    private String job;
    private String jobOther;

    private String title;
    private String titleOther;

    private Boolean isMultiNationality;
    private String multiOther;

    private String purpose;
    private String purposeOther;
    //註冊時間
    private String registerAt;
    //狀態
    private String status;
    //圖
    private Long idFront;

    private Long idBack;

    private Long idHand;

    private Long secondId;

    private Long bankAccount;
}
