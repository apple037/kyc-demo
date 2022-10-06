package com.wanda.kyc.dto.kyc;

import lombok.Data;

@Data
public class KycIdAuditReq {
    private Long memberId;
    private Boolean isPass;
}
