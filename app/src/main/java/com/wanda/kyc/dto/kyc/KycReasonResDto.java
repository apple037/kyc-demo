package com.wanda.kyc.dto.kyc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KycReasonResDto {
    private Long id;
    private String code;
    private String type;
    private String subtype;
    private String description;
    private Long point;
}
