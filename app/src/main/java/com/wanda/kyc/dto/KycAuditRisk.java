package com.wanda.kyc.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "kyc_audit_risk")
public class KycAuditRisk {
    @TableId(type= IdType.AUTO)
    private Long id;

    private Long memberId;

    private Long fact;

    private Long point;
}
