package com.wanda.kyc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanda.kyc.dto.KycAuditInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface KycAuditInfoMapper extends BaseMapper<KycAuditInfo> {
    @Select("select * from kyc_audit_info where apply_id = #{applyId}")
    List<KycAuditInfo> getList(@Param("applyId") Long applyId);

    @Select("select * from kyc_audit_info where apply_id = #{applyId} and type = #{type} and id = #{id}")
    KycAuditInfo getOne(@Param("applyId") Long applyId,@Param("type") String type,@Param("id") Long id);

    @Delete("delete from kyc_audit_info where apply_id = #{applyId}")
    void deleteList(@Param("applyId") Long applyId);
}
