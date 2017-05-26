package com.lili.bbs.dao.mapper;

import java.util.List;

import com.lili.bbs.dto.BbsReply;

public interface BbsReplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BbsReply record);

    int insertSelective(BbsReply record);

    BbsReply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BbsReply record);

    int updateByPrimaryKey(BbsReply record);

	List<BbsReply> getReplyList(BbsReply reply);

	BbsReply getReplyInfo(BbsReply twoReply);

	Integer countBbsReplySize(BbsReply reply);

	void updateReplyUserInfo(BbsReply reply);

	List<BbsReply> getReplyMeList(BbsReply bbsReply);

}