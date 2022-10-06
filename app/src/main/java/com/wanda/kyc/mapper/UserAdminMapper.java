package com.wanda.kyc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanda.kyc.dto.admin.UserAdmin;
import com.wanda.kyc.dto.admin.UserAdminDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* @author yasushi
* @description 针对表【user_admin】的数据库操作Mapper
* @createDate 2022-04-12 14:29:10
* @Entity com.wanda.nft.domain.UserAdmin
*/
@Mapper
public interface UserAdminMapper extends BaseMapper<UserAdmin> {

    @Select("select * from admin where username = #{account}")
    UserAdminDto getAdmin(@Param("account") String account);
}




