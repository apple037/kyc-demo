package com.wanda.kyc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanda.kyc.dto.KycAuditReasonSubtype;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface KycReasonSubtypeMapper extends BaseMapper<KycAuditReasonSubtype> {
    @Select("select * from kyc_reason_subtype where main_type = #{mainType}")
    List<KycAuditReasonSubtype> getList(@Param("mainType") Long mainType);

}
