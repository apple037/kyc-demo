package com.wanda.kyc.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "kyc_reason_subtype")
public class KycAuditReasonSubtype {
    @TableId(type= IdType.AUTO)
    private Long id;

    private String code;

    private String description;

    private Long mainType;
}
