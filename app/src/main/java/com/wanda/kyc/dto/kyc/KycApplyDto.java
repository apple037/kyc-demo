package com.wanda.kyc.dto.kyc;

import com.wanda.kyc.constant.AuditStatusConst;
import lombok.Data;

@Data
public class KycApplyDto {
    private Long id;

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

    private Long legalComplianceReason;

    private Long riskReason;

    private String status;

    private Long idFront;

    private Long idBack;

    private Long idHand;

    private Long secondId;

    private Long bankAccount;

    private String phone;

    private String address;

    private String purpose;

    private String purposeOther;
}
