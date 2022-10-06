package com.wanda.kyc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanda.kyc.dto.KycAuditReason;
import com.wanda.kyc.dto.KycAuditReasonType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface KycReasonTypeMapper extends BaseMapper<KycAuditReasonType> {
    @Select("select * from kyc_reason_type where code = #{type}")
    KycAuditReasonType getType(@Param("type") String type);
}
