package com.wanda.kyc.dto.kyc;

import lombok.Data;

@Data
public class KycRiskAuditReq {
    private Long memberId;
    private Long reasonId;
    private Long docId;
}
