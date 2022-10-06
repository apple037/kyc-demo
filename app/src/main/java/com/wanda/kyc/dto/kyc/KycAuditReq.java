package com.wanda.kyc.dto.kyc;

import lombok.Data;

@Data
public class KycAuditReq {
    private Long id;
    private Long applyId;
    private String type;
    private String imageIds;
    private String customerMemo;
    private String legalMemo;
}
