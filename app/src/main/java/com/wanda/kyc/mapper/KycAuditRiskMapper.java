package com.wanda.kyc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanda.kyc.dto.KycAuditRisk;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface KycAuditRiskMapper extends BaseMapper<KycAuditRisk> {
    @Select("select * from kyc_audit_risk where member_id = #{memberId}")
    List<KycAuditRisk> getAllByMember(@Param("memberId") Long memberId);
    // 法遵審核紀錄
    @Delete("delete from kyc_audit_risk where member_id = #{memberId} and fact in (157,158)")
    void deleteByMemberIdOnlyLegal(@Param("memberId") Long memberId);

    // 風控審核紀錄
    @Delete("delete from kyc_audit_risk where member_id = #{memberId} and fact in (154,155,156)")
    void deleteByMemberIdOnlyRisk(@Param("memberId") Long memberId);
}
