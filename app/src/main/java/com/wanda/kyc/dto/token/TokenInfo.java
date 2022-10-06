package com.wanda.kyc.dto.token;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TokenInfo {

    private String role;
    private Long userId;
    private String email;

}
