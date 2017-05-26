package com.lili.bbs.dao.mapper;

import java.util.List;

import com.lili.bbs.dto.BbsWeskit;

public interface BbsWeskitMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BbsWeskit record);

    int insertSelective(BbsWeskit record);

    BbsWeskit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BbsWeskit record);

    int updateByPrimaryKey(BbsWeskit record);

	BbsWeskit queryWeskit(BbsWeskit bbsWeckit);

	List<BbsWeskit> queryWeskitList(BbsWeskit bbsWeskit);

	Integer countBbsWeskitSize(BbsWeskit bbsWeskit);

	List<BbsWeskit> queryUnPraiseWeskitList(BbsWeskit bbsWeskit);

	Integer countBbsUnPraiseWeskitSize(BbsWeskit bbsWeskit);

}