package com.lili.user.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.bbs.dto.Bbs;
import com.lili.bbs.dto.BbsBlacklist;
import com.lili.bbs.dto.BbsPraise;
import com.lili.bbs.dto.BbsReply;
import com.lili.bbs.dto.BbsTopic;
import com.lili.bbs.dto.BbsWeskit;
import com.lili.bbs.dto.BbsWord;
import com.lili.bbs.manager.IBBSManager;
import com.lili.bbs.service.IBBSService;
import com.lili.bbs.vo.BbsDetailMessage;
import com.lili.bbs.vo.BbsMessage;
import com.lili.cms.constant.MessageCode;
import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.coach.dto.Coach;
import com.lili.coach.manager.CoachManager;
import com.lili.coach.model.CoachBDTO;
import com.lili.coach.service.CMSCoachService;
import com.lili.common.util.StringUtil;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;
import com.lili.student.model.StudentBDTO;
import com.lili.student.service.CMSStudentService;
import com.lili.user.service.ICmsBBSService;

public class CmsBBSServiceImpl implements ICmsBBSService {
	
	Logger logger = Logger.getLogger(CmsBBSServiceImpl.class);
	
	@Autowired
	IBBSManager bbsManager;
	
	@Autowired
	IBBSService bbsService;
	
    @Autowired
    StudentManager studentManager;
    
    @Autowired
    CoachManager coachManager;
    
    @Autowired
    CMSStudentService cmsStudentService;
    
    @Autowired
    CMSCoachService cmsCoachService;

    @Autowired
    RedisUtil redisUtil;
	  
	@Override
	public String getBBSList(BbsMessage bbsMessage, String pageNo, String pageSize) {
		String resp =null;
		if (StringUtil.isNullOrEmpty(pageNo)){
			pageNo = "0";
		}
		if (StringUtil.isNullOrEmpty(pageSize)){
			pageSize = "10";
		}
		List<BbsMessage> bbsMsgList =  bbsManager.getCMSBbsList(bbsMessage, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
		PagedResult<BbsMessage> result = new PagedResult<BbsMessage>();
		if (bbsMsgList != null && bbsMsgList.size() > 0) {
			Integer total = bbsManager.countBbsSize(bbsMessage);
	        result.setPageNo(Integer.parseInt(pageNo));
	        result.setPageSize(Integer.parseInt(pageSize));
	        result.setDataList(bbsMsgList);
	        result.setTotal(total);
	        result.setPages((total/Integer.parseInt(pageSize))+1);
	        resp = new ResponseMessage().addResult("pageData", result).build();
		}
		else {
			result.setTotal(0);
			resp = new ResponseMessage().addResult("pageData", result).build();
		}
		return resp;
	}

	@Override
	public String newBBS(BbsMessage bbsMessage) {
		String resp =null;
		String topicId = "";
		if (bbsMessage.getTopicId() != null ) {
			topicId = String.valueOf(bbsMessage.getTopicId());
		}
		String titleType = "";
		if (bbsMessage.getTopicId() != null ) {
			titleType = String.valueOf(bbsMessage.getTitleType());
		}
		
		//bbsMessage.setChannel(2);
		ReqResult result = bbsService.newBBSContent(bbsMessage.getUserId(), bbsMessage.getUserType(), bbsMessage.getUserName(),
				 bbsMessage.getHeadIcon(), bbsMessage.getClassify(), String.valueOf(bbsMessage.getCityId()), bbsMessage.getCityName(), topicId, 
				 bbsMessage.getTopicName(), titleType, "0", bbsMessage.getTitle(), bbsMessage.getContent(), bbsMessage.getPic());
		if (result.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)) {
			resp = new ResponseMessage().addResult("pageData", result.getResult().get("data")).build();
			return resp;
		}
		 return resp;
	}
	
	@Override
	public String newTopic(BbsTopic bbsTopic) {
		BbsTopic topic = new BbsTopic();
		if (bbsTopic != null && bbsTopic.getTopLevel() != null && bbsTopic.getTopLevel() != 0) {
			topic.setTopLevel(bbsTopic.getTopLevel());
			BbsTopic isExitLev = bbsManager.getBbsTopic(topic);
			if (isExitLev != null) {
				return new ResponseMessage(MessageCode.MSG_TOPIC_LEVEL_EXIST).build();
			}
		}
		
		topic = new BbsTopic();
		topic.setTopicName(bbsTopic.getTopicName());
		BbsTopic isExitName = bbsManager.getBbsTopic(topic);
		if (isExitName != null) {
			return new ResponseMessage(MessageCode.MSG_TOPIC_EXIST).build();
		}
		
		int  r = bbsManager.newTopic(bbsTopic);
		if (r > 0) {
			return new ResponseMessage(0,MessageCode.MSG_SUCCESS).build();
		}
		else {
			return new ResponseMessage(MessageCode.MSG_FAIL).build();
		}
	}
	

	@Override
	public String editTopic(BbsTopic bbsTopic) {
		BbsTopic topic = new BbsTopic();
		topic.setId(bbsTopic.getId());
		BbsTopic topicInfo = bbsManager.getBbsTopic(bbsTopic); //查原来的值
		if (topicInfo != null) {
			BbsTopic isTopic = new BbsTopic();
			if (topicInfo.getTopLevel() != bbsTopic.getTopLevel()) { //有变更优先级
				isTopic.setTopLevel(bbsTopic.getTopLevel());
				BbsTopic isExitLev = bbsManager.getBbsTopic(isTopic);
				if (isExitLev != null) {
					return new ResponseMessage(MessageCode.MSG_TOPIC_LEVEL_EXIST).build();
				}
			}
			if (!bbsTopic.getTopicName().equals(bbsTopic.getTopicName())) { //变更名称
				isTopic.setTopicName(bbsTopic.getTopicName());
				BbsTopic isExitName = bbsManager.getBbsTopic(isTopic);
				if (isExitName != null) {
					return new ResponseMessage(MessageCode.MSG_TOPIC_EXIST).build();
				}
			}
		}
		
		BbsTopic isTopic = bbsManager.getBbsTopic(bbsTopic);
		if (isTopic != null && isTopic.getId() != bbsTopic.getId()) {
			return new ResponseMessage(MessageCode.MSG_TOPIC_EXIST).build();
		}
		int  r = bbsManager.updateTopic(bbsTopic);
		if (r > 0) {
			return new ResponseMessage(0,MessageCode.MSG_SUCCESS).build();
		}
		else {
			return new ResponseMessage(MessageCode.MSG_FAIL).build();
		}
	}

	@Override
	public String reply(BbsReply bbsReply) {
		String resp =null;
		
		String byreId = null;
		if (bbsReply.getByreId() != null ) {
			byreId = String.valueOf(bbsReply.getByreId());
		}
		String byreUserId  = null;
		if (bbsReply.getByreUserId() != null ) {
			byreUserId = String.valueOf(bbsReply.getByreUserId());
		}
		String byreUserType  = null;
		if (bbsReply.getByreUserType() != null ) {
			byreUserType = String.valueOf(bbsReply.getByreUserType());
		}
		ReqResult result = bbsService.reply(bbsReply.getUserId(), bbsReply.getUserType(), bbsReply.getUserName(), bbsReply.getHeadIcon(), 
				bbsReply.getBbsId(), "100100", "深圳市", byreId, byreUserId, bbsReply.getByreUserName(), byreUserType, bbsReply.getByreContent(), bbsReply.getReplyContent(), "1");
		
		if(result.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)){
			resp = new ResponseMessage().addResult("pageData", result.getResult().get("data")).build();
			return resp;
		}
		return resp;
	}

	@Override
	public String editBBSContent(BbsMessage bbsMessage) {
		String resp =null;
		logger.info("************************************* bbsMessage pic : " + bbsMessage.getPic());
		
		ReqResult result = bbsService.editBBSContent(bbsMessage);
		if(result.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)){
			resp = new ResponseMessage().addResult("pageData", result.getResult().get("data")).build();
			return resp;
		}
		return resp;
	}

	@Override
	public String stick(Bbs bbs) {
		int r = bbsManager.updateBBSOnly(bbs);
		if (r > 0) {
			return new ResponseMessage(0,MessageCode.MSG_SUCCESS).build();
		}
		else {
			return new ResponseMessage(MessageCode.MSG_FAIL).build();
		}
	}

	@Override
	public String getBBSReplyList(String userId, Integer userType, Integer bbsId, Integer pageSize,
			Integer pageIndex) {
		String resp =null;
		ReqResult r =  bbsService.getBBSReplyList(userId, userType, bbsId, pageSize, pageIndex);
		PagedResult<BbsReply> result = new PagedResult<BbsReply>();
		if (r.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)) {
			List<BbsReply> bbsReplyList =  (List<BbsReply>) r.getResult().get(ResultCode.RESULTKEY.DATA);
			if (bbsReplyList != null && bbsReplyList.size() > 0) {
				BbsReply reply = new BbsReply();
				reply.setBbsId(bbsId);
				Integer total = bbsManager.countBbsReplySize(reply);
				result.setPageNo(pageIndex);
				result.setPageSize(pageSize);
				if (bbsReplyList != null && bbsReplyList.size() > 0) {
					result.setDataList(bbsReplyList);
				}
				result.setTotal(total);
				result.setPages((total/pageSize)+1);
				resp = new ResponseMessage().addResult("pageData", result).build();
			}
			else {
				result.setTotal(0);
				resp = new ResponseMessage().addResult("pageData", result).build();
			}
		}
		else {
			result.setTotal(0);
			resp = new ResponseMessage().addResult("pageData", result).build();
		}
		return resp;
	}

	@Override
	public String getTopicList(BbsTopic bbsTopic, String pageNo, String pageSize) {
		String resp =null;
		if (StringUtil.isNullOrEmpty(pageNo)){
			pageNo = "1";
		}
		if (StringUtil.isNullOrEmpty(pageSize)){
			pageSize = "10";
		}
		List<BbsTopic> bbsTopicList =  bbsManager.getBBSTopicList(bbsTopic, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
		PagedResult<BbsTopic> result = new PagedResult<BbsTopic>();
		if (bbsTopicList != null && bbsTopicList.size() > 0) {
			Integer total = bbsManager.countBbsTopicSize(bbsTopic);
			result.setPageNo(Integer.parseInt(pageNo));
			result.setPageSize(Integer.parseInt(pageSize));
			result.setDataList(bbsTopicList);
			result.setTotal(total);
			result.setPages((total/Integer.parseInt(pageSize))+1);
			resp = new ResponseMessage().addResult("pageData", result).build();
		}
		else {
			result.setTotal(0);
			resp = new ResponseMessage().addResult("pageData", result).build();
		}
		return resp;
	}
	
	@Override
	public String shieldOrBanned(BbsTopic bbsTopic) {
		int  r = bbsManager.updateTopic(bbsTopic);
		if (r > 0) {
			return new ResponseMessage(0,MessageCode.MSG_SUCCESS).build();
		}
		else {
			return new ResponseMessage(MessageCode.MSG_FAIL).build();
		}
	}

	@Override
	public String getWordList(BbsWord bbsWord, String pageNo, String pageSize) {
		String resp =null;
		if (StringUtil.isNullOrEmpty(pageNo)){
			pageNo = "1";
		}
		if (StringUtil.isNullOrEmpty(pageSize)){
			pageSize = "10";
		}
		List<BbsWord> bbsWordList =  bbsManager.getWordList(bbsWord, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
		PagedResult<BbsWord> result = new PagedResult<BbsWord>();
		if (bbsWordList != null && bbsWordList.size() > 0) {
			Integer total = bbsManager.countBbsWordSize(bbsWord);
	        result.setPageNo(Integer.parseInt(pageNo));
	        result.setPageSize(Integer.parseInt(pageSize));
	        result.setDataList(bbsWordList);
	        result.setTotal(total);
	        result.setPages((total/Integer.parseInt(pageSize))+1);
	        resp = new ResponseMessage().addResult("pageData", result).build();
		}
		else {
			result.setTotal(0);
			resp = new ResponseMessage().addResult("pageData", result).build();
		}
		return resp;
	}

	@Override
	public String addWord(BbsWord bbsWord) {
		String word = bbsWord.getWord();
		String[] wordStr = word.split(",");
		for (int i = 0; i < wordStr.length; i++) {
			if (wordStr[i] != null && wordStr[i].length() > 64) {
				return new ResponseMessage(MessageCode.MSG_WORD_OVER_LENGTH).build();
			}
			bbsWord.setWord(wordStr[i]);
			BbsWord isWord = bbsManager.getBbsWord(bbsWord);
			if (isWord != null) {
				return new ResponseMessage(MessageCode.MSG_WORD_EXIST).build();
			}
			else {
				bbsManager.addWord(bbsWord);
			}
			
		}
		
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS).build();
		
	}

	@Override
	public String delWord(BbsWord bbsWord) {
		int  r = bbsManager.delWord(bbsWord);
		if (r > 0) {
			return new ResponseMessage(0,MessageCode.MSG_SUCCESS).build();
		}
		else {
			return new ResponseMessage(MessageCode.MSG_FAIL).build();
		}
	}

	@Override
	public String getWeskitList(BbsWeskit bbsWeskit, String pageNo, String pageSize) {
		String resp =null;
		if (StringUtil.isNullOrEmpty(pageNo)){
			pageNo = "1";
		}
		if (StringUtil.isNullOrEmpty(pageSize)){
			pageSize = "10";
		}
		List<BbsWeskit> bbsWeskitList =  bbsManager.getBbsWeskit(bbsWeskit, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
		PagedResult<BbsWeskit> result = new PagedResult<BbsWeskit>();
		if (bbsWeskitList != null && bbsWeskitList.size() > 0) {
			Integer total = bbsManager.countBbsWeskitSize(bbsWeskit);
	        result.setPageNo(Integer.parseInt(pageNo));
	        result.setPageSize(Integer.parseInt(pageSize));
	        result.setDataList(bbsWeskitList);
	        result.setTotal(total);
	        result.setPages((total/Integer.parseInt(pageSize))+1);
	        resp = new ResponseMessage().addResult("pageData", result).build();
		}
		else {
			result.setTotal(0);
			resp = new ResponseMessage().addResult("pageData", result).build();
		}
		return resp;
	}

	@Override
	public String addWeskiter(BbsWeskit bbsWeskit) {
		int  r = bbsManager.addWeskiter(bbsWeskit);
		if (r > 0) {
			return new ResponseMessage(0,MessageCode.MSG_SUCCESS).build();
		}
		else {
			return new ResponseMessage(MessageCode.MSG_FAIL).build();
		}
	}

	@Override
	public String getBlackList(BbsBlacklist bbsBlacklist, String pageNo, String pageSize) {
		String resp =null;
		if (StringUtil.isNullOrEmpty(pageNo)){
			pageNo = "1";
		}
		if (StringUtil.isNullOrEmpty(pageSize)){
			pageSize = "10";
		}
		List<BbsBlacklist> bbsBlackArray =  bbsManager.getBlackList(bbsBlacklist, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
		PagedResult<BbsBlacklist> result = new PagedResult<BbsBlacklist>();
		if (bbsBlackArray != null && bbsBlackArray.size() > 0) {
			Integer total = bbsManager.countBbsBlackSize(bbsBlacklist);
	        result.setPageNo(Integer.parseInt(pageNo));
	        result.setPageSize(Integer.parseInt(pageSize));
	        result.setDataList(bbsBlackArray);
	        result.setTotal(total);
	        result.setPages((total/Integer.parseInt(pageSize))+1);
	        resp = new ResponseMessage().addResult("pageData", result).build();
		}
		else {
			result.setTotal(0);
			resp = new ResponseMessage().addResult("pageData", result).build();
		}
		return resp;
	}

	@Override
	public String addBlacker(BbsBlacklist bbsBlacklist) {
		//判断是否已存在
		BbsBlacklist isBlack = bbsManager.getBBSBlacklist(bbsBlacklist);
		if (isBlack != null) {
			return new ResponseMessage(MessageCode.MSG_USER_EXIST).build();
		}
		int  r = bbsManager.addBlacker(bbsBlacklist);
		if (r > 0) {
			return new ResponseMessage(0,MessageCode.MSG_SUCCESS).build();
		}
		else {
			return new ResponseMessage(MessageCode.MSG_FAIL).build();
		}
	}

	@Override
	public String banBlacker(BbsBlacklist bbsBlacklist) {
		int  r = bbsManager.banBlacker(bbsBlacklist);
		if (r > 0) {
			return new ResponseMessage(0,MessageCode.MSG_SUCCESS).build();
		}
		else {
			return new ResponseMessage(MessageCode.MSG_FAIL).build();
		}
	}

	@Override
	public String replyDelete(BbsReply bbsReply) {
		int  r = bbsManager.deleteReply(bbsReply);
		if (r > 0) {
			return new ResponseMessage(0,MessageCode.MSG_SUCCESS).build();
		}
		else {
			return new ResponseMessage(MessageCode.MSG_FAIL).build();
		}
	}

	@Override
	public String addPraise(Integer bbsId, String praiseUserIdList, String userType) {
		BbsMessage bbsMsg = new BbsMessage();
		bbsMsg.setId(bbsId);
		BbsMessage bbsMessage = bbsManager.getBBSMessage(bbsMsg);
		if (bbsMessage != null) {
			BbsPraise bbsPraise = new BbsPraise();
			bbsPraise.setBbsId(bbsId);
			bbsPraise.setPraiseUserId(bbsMessage.getUserId());
			bbsPraise.setPraiseUserType(bbsMessage.getUserType());
			bbsPraise.setPraiseContent(bbsMessage.getContent());
			
			String[] userIdStr = praiseUserIdList.split(",");
			for (int i = 0; i < userIdStr.length; i++) {
				if (userIdStr[i] != null && !"".equals(userIdStr[i])) {
					if (Integer.parseInt(userType) == 1) {
						Coach coach = coachManager.getCoachInfo(Long.parseLong(userIdStr[i]));
						if (coach != null) {
							bbsPraise.setUserId(coach.getCoachId());
							bbsPraise.setUserType(Integer.parseInt(userType));
							bbsPraise.setUserName(coach.getName());
							bbsPraise.setHeadIcon(coach.getHeadIcon());
						}
						else {
							return new ResponseMessage(MessageCode.MSG_FAIL).build();
						}
					}
					else {
						Student student = studentManager.getStudentInfo(Long.parseLong(userIdStr[i]));
						if (student != null) {
							bbsPraise.setUserId(student.getStudentId());
							bbsPraise.setUserType(Integer.parseInt(userType));
							bbsPraise.setUserName(student.getName());
							bbsPraise.setHeadIcon(student.getHeadIcon());
						}
						else {
							return new ResponseMessage(MessageCode.MSG_FAIL).build();
						}
					}
					
					bbsManager.saveBBSPraise(bbsPraise);
				}
			}
			
		}
		else {
			return new ResponseMessage(MessageCode.MSG_FAIL).build();
		}
		
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS).build();
	}

	@Override
	public String getBBSDetail(Integer userType, Integer bbsId) {
		String resp =null;
		BbsDetailMessage bbsDetailMessage = new BbsDetailMessage(); 
		bbsDetailMessage.setId(bbsId);
	    BbsDetailMessage bbsDetail =  bbsManager.getBBSDetail(bbsDetailMessage, null);
		if (bbsDetail != null) {
			resp = new ResponseMessage().addResult("pageData", bbsDetail).build();
		}
		else {
			resp =  new ResponseMessage(0,MessageCode.MSG_SUCCESS).build();
		}
		return resp;
	}

	@Override
	public String getUnPraiseWeskitList(BbsWeskit bbsWeskit, String pageNo, String pageSize) {
		String resp = null;
		if (StringUtil.isNullOrEmpty(pageNo)) {
			pageNo = "1";
		}
		if (StringUtil.isNullOrEmpty(pageSize)) {
			pageSize = "1000";
		}
		List<BbsWeskit> bbsWeskitList =  bbsManager.getUnPraiseWeskitList(bbsWeskit, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
		PagedResult<BbsWeskit> result = new PagedResult<BbsWeskit>();
		if (bbsWeskitList != null && bbsWeskitList.size() > 0) {
			Integer total = bbsManager.countBbsUnPraiseWeskitSize(bbsWeskit);
	        result.setPageNo(Integer.parseInt(pageNo));
	        result.setPageSize(Integer.parseInt(pageSize));
	        result.setDataList(bbsWeskitList);
	        result.setTotal(total);
	        result.setPages((total/Integer.parseInt(pageSize))+1);
	        resp = new ResponseMessage().addResult("pageData", result).build();
		}
		else {
			result.setTotal(0);
			resp = new ResponseMessage().addResult("pageData", result).build();
		}
		return resp;
	}

	@Override
	public String getStudentList(StudentBDTO dto, String pageNo, String pageSize) throws Exception {
		if (StringUtil.isNotNullAndNotEmpty(pageNo) && StringUtil.isNotNullAndNotEmpty(pageSize)) {
			dto.setPageNo(Integer.parseInt(pageNo));
			dto.setPageSize(Integer.parseInt(pageSize));
		}
		return cmsStudentService.getStudent(dto).build();
	}
	
	@Override
	public String getCoachList(CoachBDTO dto, String pageNo, String pageSize) throws Exception {
		if (StringUtil.isNotNullAndNotEmpty(pageNo) && StringUtil.isNotNullAndNotEmpty(pageSize)) {
			dto.setPageNo(Integer.parseInt(pageNo));
			dto.setPageSize(Integer.parseInt(pageSize));
		}
		return cmsCoachService.getCoach(dto).build();
	}

}
