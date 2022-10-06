package com.wanda.kyc.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "kyc_reason_type")
public class KycAuditReasonType {
    @TableId(type= IdType.AUTO)
    private Long id;

    private String code;

    private String description;
}
