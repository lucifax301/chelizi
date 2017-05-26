package com.lili.user.service;

import com.lili.bbs.dto.Bbs;
import com.lili.bbs.dto.BbsBlacklist;
import com.lili.bbs.dto.BbsReply;
import com.lili.bbs.dto.BbsTopic;
import com.lili.bbs.dto.BbsWeskit;
import com.lili.bbs.dto.BbsWord;
import com.lili.bbs.vo.BbsMessage;
import com.lili.coach.model.CoachBDTO;
import com.lili.student.model.StudentBDTO;

public interface ICmsBBSService {

	String getBBSList(BbsMessage bbsMessage, String pageNo, String pageSize);

	String newTopic(BbsTopic bbsTopic);

	String reply(BbsReply bbsReply);

	String editBBSContent(BbsMessage bbsMessage);

	String stick(Bbs bbs);

	String getTopicList(BbsTopic bbsTopic, String pageNo, String pageSize);

	String newBBS(BbsMessage bbsMessage);

	String editTopic(BbsTopic bbsTopic);

	String shieldOrBanned(BbsTopic bbsTopic);

	String getWordList(BbsWord bbsWord, String pageNo, String pageSize);

	String addWord(BbsWord bbsWord);

	String delWord(BbsWord bbsWord);

	String getWeskitList(BbsWeskit bbsWeskit, String pageNo, String pageSize);

	String addWeskiter(BbsWeskit bbsWeskit);

	String getBlackList(BbsBlacklist bbsBlacklist, String pageNo, String pageSize);

	String addBlacker(BbsBlacklist bbsBlacklist);

	String banBlacker(BbsBlacklist bbsBlacklist);

	String getBBSReplyList(String userId, Integer userType, Integer bbsId, Integer pageSize,
			Integer pageIndex);

	String replyDelete(BbsReply bbsReply);

	String addPraise(Integer bbsId, String userIdList, String userType);

	String getBBSDetail(Integer userType, Integer bbsId);

	String getUnPraiseWeskitList(BbsWeskit bbsWeskit, String pageNo, String pageSize);

	String getStudentList(StudentBDTO dto, String pageNo, String pageSize) throws Exception;

	String getCoachList(CoachBDTO dto, String pageNo, String pageSize) throws Exception;

}
