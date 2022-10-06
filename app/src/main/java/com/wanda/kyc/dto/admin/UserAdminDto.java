package com.wanda.kyc.dto.admin;

import lombok.Data;

@Data
public class UserAdminDto {
    private Long id;
    private String username;
    private String password;
    private String type;
}
