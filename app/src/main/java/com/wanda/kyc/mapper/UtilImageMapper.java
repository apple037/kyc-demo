package com.wanda.kyc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanda.kyc.dto.UtilImage;
import org.apache.ibatis.annotations.*;

/**
 * @author yasushi
 * @description 针对表【util_image】的数据库操作Mapper
 * @createDate 2022-04-12 14:29:10
 * @Entity com.wanda.nft.domain.UtilImage
 */
@Mapper
public interface UtilImageMapper extends BaseMapper<UtilImage> {

    /*
     * UserAppMapper.findDtoById2()使用
     */
    @Select("SELECT `url` FROM util_image WHERE `id` = #{id}")
    String findUrlById(@Param("id") Long id);

    @Insert("INSERT util_image (url) values " +
            "(#{url})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertImage(UtilImage image);

    @Select("SELECT `url` FROM util_image WHERE `apply_id` = #{applyId}")
    String findUrlByApplyId(@Param("applyId") Long applyId);
}




