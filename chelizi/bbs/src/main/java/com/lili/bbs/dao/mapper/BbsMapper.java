package com.lili.bbs.dao.mapper;

import java.util.List;

import com.lili.bbs.dto.Bbs;
import com.lili.bbs.vo.BbsMessage;

public interface BbsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Bbs record);

    int insertSelective(Bbs record);

    Bbs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Bbs record);

    int updateByPrimaryKey(Bbs record);

	List<BbsMessage> getBbsList(BbsMessage bbsMessage);
	
	List<BbsMessage> getCMSBbsList(BbsMessage bbsMessage);

	void updateBBSInform(Bbs bbs);

	void updateBBSPraise(Bbs bbs);

	BbsMessage queryBBSDetail(BbsMessage bmsg);

	Bbs queryBBSInfo(Bbs bbs);

	Integer getMaxBBSId();

	void updateBBSReply(Bbs bbs);
	
	void updateBBSReplySubtract(Bbs bbs);

	Integer countBbsSize(BbsMessage bbsMessage);

	void updateBbsNameOrHeadIconByUser(Bbs bbs);
	
	void updateBbsTopic(Bbs bbs);

	List<BbsMessage> getMyBBSList(BbsMessage bbsMessage);
}