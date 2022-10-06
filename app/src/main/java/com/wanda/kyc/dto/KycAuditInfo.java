package com.wanda.kyc.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "kyc_audit_info")
public class KycAuditInfo {
    @TableId(type= IdType.AUTO)
    private Long id;

    private Long applyId;

    private String type;

    private String imageIds;

    private String customerMemo;

    private String legalMemo;
}
