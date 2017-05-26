package com.lili.bbs.dao.mapper;

import java.util.List;

import com.lili.bbs.dto.BbsInform;

public interface BbsInformMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BbsInform record);

    int insertSelective(BbsInform record);

    BbsInform selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BbsInform record);

    int updateByPrimaryKey(BbsInform record);

	BbsInform queryInformInfo(BbsInform inform);

	void updateInformUserInfo(BbsInform inform);

	List<BbsInform> queryInformList(BbsInform bbsInform);
}