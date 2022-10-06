package com.wanda.kyc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanda.kyc.dto.app.UserAppDto;
import com.wanda.kyc.dto.app.UserSearchDto;
import com.wanda.kyc.dto.kyc.KycApplyResp;
import com.wanda.kyc.dto.user.UserApp;
import com.wanda.kyc.provider.KycApplyProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author yasushi
 * @description 针对表【user_app】的数据库操作Mapper
 * @createDate 2022-04-12 14:29:10
 * @Entity com.wanda.nft.domain.UserApp
 */
@Mapper
public interface UserAppMapper extends BaseMapper<UserApp> {

    /*
     * 查詢使用者資料(*) 和 錢包餘額
     */
    @Select("SELECT * FROM member WHERE `id` = #{userId}")
    @Results(value = {
            @Result(property = "id", column = "id"),
    })
    UserAppDto findDtoById(@Param("userId") String userId);

    /*
     * 查詢使用者資料(name, email, homeAddress, loginIp, lastLoginAt, photoUrl(Util_image.url))
     */
    @Select("SELECT `id`, `username`, `member_id`, `register_at`, `identity_code`, `real_name` " +
            "FROM member WHERE `id` = #{userId}")
    @Results(value = {
    })
    UserAppDto findDtoById2(@Param("userId") String userId);

    /*
     * 查詢使用者資料(*) 和 錢包餘額 (login接口使用)
     */
    @Select("SELECT * FROM member WHERE `username` = #{account}")
    @Results(value = {
            @Result(property = "id", column = "id"),
    })
    UserAppDto findDtoByAccount(@Param("account") String account);

    @SelectProvider(type = KycApplyProvider.class, method = "findRecordByParam")
    List<KycApplyResp> getApplyList(@Param("param") UserSearchDto param);

}