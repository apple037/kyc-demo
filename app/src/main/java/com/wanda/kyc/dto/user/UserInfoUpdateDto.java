package com.wanda.kyc.dto.user;

import lombok.Data;

@Data
public class UserInfoUpdateDto {
    private Long memberId;

    private String realName;

    private String identityCode;

    private String birthdate;

    private String nationality;

    private String job;
    private String jobOther;

    private String title;
    private String titleOther;

    private Boolean isMultiNationality;
    private String multiOther;

    private String phone;

    private String address;

    private String purpose;
    private String purposeOther;
}
