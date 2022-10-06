package com.wanda.kyc.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "kyc_reason")
public class KycAuditReason {
    @TableId(type= IdType.AUTO)
    private Long id;

    private Long docId;

    private Long type;

    private String description;

    private Long point;
}
