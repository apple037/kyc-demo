package com.wanda.kyc.dto.app;

import com.wanda.kyc.constant.AdminTypeConst;
import com.wanda.kyc.constant.AuditStatusConst;
import lombok.Data;

@Data
public class UserSearchDto {
    private String username;
    private String identityCode;
    private String status;
    private String page;
    private String size;
    private String type;
}
