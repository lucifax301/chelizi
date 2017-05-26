package com.lili.bbs.dao.mapper;
import com.lili.bbs.dto.BbsExtend;

public interface BbsExtendMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BbsExtend record);

    int insertSelective(BbsExtend record);

    BbsExtend selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BbsExtend record);

    int updateByPrimaryKey(BbsExtend record);

	 int updateByBbsId(BbsExtend bbsExt);
}