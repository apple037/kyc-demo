package com.wanda.kyc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanda.kyc.dto.KycApply;
import com.wanda.kyc.dto.kyc.KycApplyDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface KycApplyMapper extends BaseMapper<KycApply> {
    @Select("select * from kyc_apply where member_id = #{id}")
    KycApplyDto findByMemberId(@Param("id") Long id);

    @Select("select * from kyc_apply where member_id = #{id}")
    KycApply findByMemberId2(@Param("id") Long id);

    @Select("select * from kyc_apply where member_id = #{id}")
    KycApply GetApplyByMemberId(@Param("id") Long id);

    @Select("select count(*) from kyc_apply where status = #{status}")
    Integer getStatusCount(@Param("status") String status);
}
