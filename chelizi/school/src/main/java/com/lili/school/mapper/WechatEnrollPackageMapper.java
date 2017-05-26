package com.lili.school.mapper;

import com.lili.school.dto.WechatEnrollPackage;
import com.lili.school.dto.WechatEnrollPackageExample;
import com.lili.school.dto.WechatEnrollPackageWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WechatEnrollPackageMapper {
    int countByExample(WechatEnrollPackageExample example);

    int deleteByExample(WechatEnrollPackageExample example);

    int deleteByPrimaryKey(Integer ttid);

    int insert(WechatEnrollPackageWithBLOBs record);

    int insertSelective(WechatEnrollPackageWithBLOBs record);

    List<WechatEnrollPackageWithBLOBs> selectByExampleWithBLOBsWithRowbounds(WechatEnrollPackageExample example, RowBounds rowBounds);

    List<WechatEnrollPackageWithBLOBs> selectByExampleWithBLOBs(WechatEnrollPackageExample example);

    List<WechatEnrollPackage> selectByExampleWithRowbounds(WechatEnrollPackageExample example, RowBounds rowBounds);

    List<WechatEnrollPackage> selectByExample(WechatEnrollPackageExample example);

    WechatEnrollPackageWithBLOBs selectByPrimaryKey(Integer ttid);

    int updateByExampleSelective(@Param("record") WechatEnrollPackageWithBLOBs record, @Param("example") WechatEnrollPackageExample example);

    int updateByExampleWithBLOBs(@Param("record") WechatEnrollPackageWithBLOBs record, @Param("example") WechatEnrollPackageExample example);

    int updateByExample(@Param("record") WechatEnrollPackage record, @Param("example") WechatEnrollPackageExample example);

    int updateByPrimaryKeySelective(WechatEnrollPackageWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(WechatEnrollPackageWithBLOBs record);

    int updateByPrimaryKey(WechatEnrollPackage record);
}