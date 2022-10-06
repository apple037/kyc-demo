package com.wanda.kyc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanda.kyc.dto.KycAuditReason;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface KycAuditReasonMapper extends BaseMapper<KycAuditReason> {
    @Select("select * from kyc_reason where type = #{type}")
    List<KycAuditReason> getReasons(@Param("type") Long type);
}
