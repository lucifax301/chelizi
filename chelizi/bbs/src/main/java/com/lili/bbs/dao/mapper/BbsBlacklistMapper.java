package com.lili.bbs.dao.mapper;


import java.util.List;

import com.lili.bbs.dto.BbsBlacklist;

public interface BbsBlacklistMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BbsBlacklist record);

    int insertSelective(BbsBlacklist record);

    BbsBlacklist selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BbsBlacklist record);

    int updateByPrimaryKey(BbsBlacklist record);

	List<BbsBlacklist> getBbsBlacklistList(BbsBlacklist bbsBlacklist);

	BbsBlacklist queryBbsBlacklist(BbsBlacklist bbsBlacklist);

	Integer countBbsBlackSize(BbsBlacklist bbsBlacklist);

	void updateBlackUserInfo(BbsBlacklist black);

}