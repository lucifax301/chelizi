package com.lili.bbs.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.bbs.action.SensitiveWordFilter;
import com.lili.bbs.dto.Bbs;
import com.lili.bbs.dto.BbsBlacklist;
import com.lili.bbs.dto.BbsInform;
import com.lili.bbs.dto.BbsPraise;
import com.lili.bbs.dto.BbsReply;
import com.lili.bbs.dto.BbsTopic;
import com.lili.bbs.dto.BbsWord;
import com.lili.bbs.factory.BBSFactory;
import com.lili.bbs.manager.IBBSManager;
import com.lili.bbs.service.IBBSService;
import com.lili.bbs.vo.BbsDetailMessage;
import com.lili.bbs.vo.BbsMessage;
import com.lili.common.util.DateUtil;
import com.lili.common.util.StringUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;

public class BBSServiceImpl implements IBBSService {
	Logger logger = Logger.getLogger(BBSServiceImpl.class);
	
	@Autowired
	IBBSManager bbsManager;
	
	@Autowired
	StudentManager studentManager;
	
	@Autowired
	RedisUtil redisUtil;
	
	@Autowired
	BBSFactory bbsFactory;
	
	@Autowired
	SensitiveWordFilter sensitiveWordFilter;
	
	public static int minMatchTYpe = 1;      //最小匹配规则
	
	public static int maxMatchType = 2;      //最大匹配规则
	
	@Override
	public ReqResult newBBSContent(Long userId, Integer userType, String userName, String headIcon, Integer classify,
			String cityId, String cityName, String topicId, String topicName, String titleType, String isDel, String title, String content, String pic) {
		ReqResult  r = ReqResult.getSuccess();
		
		if (content != null && content.length() > 5000) {
			r.setCode(ResultCode.ERRORCODE.BBS_CONTENT_IS_OVER_LENGTH);
			r.setMsgInfo(ResultCode.ERRORINFO.BBS_CONTENT_IS_OVER_LENGTH);
			return r;
		}
		
		if (content == null || "".equals(content.trim())) {
			r.setCode(ResultCode.ERRORCODE.BBS_CONTENT_IS_ENPTY);
			r.setMsgInfo(ResultCode.ERRORINFO.BBS_CONTENT_IS_ENPTY);
			return r;
			
		}
		
		Student student = studentManager.getStudentInfo(userId);
		
		BbsMessage bbsMsg = new BbsMessage();
		bbsMsg.setUserId(userId);
		bbsMsg.setUserName(userName);
		bbsMsg.setHeadIcon(headIcon);
		if (StringUtil.isNullOrEmpty(headIcon)) {
			bbsMsg.setHeadIcon(student.getHeadIcon());
		}
		bbsMsg.setClassify(classify);
		if (StringUtil.isNullOrEmpty(cityId)) {
			bbsMsg.setCityId(1);
		}
		else {
			bbsMsg.setCityId(Integer.parseInt(cityId));
		}
		if (StringUtil.isNotNullAndNotEmpty(cityName)) {
			if (cityName.contains("市")) {
				cityName = cityName.replaceAll("市", "");
			}
		}
		bbsMsg.setCityName(cityName);
		if (StringUtil.isNotNullAndNotEmpty(topicId)) {
			bbsMsg.setTopicId(Integer.parseInt(topicId));
		}
		bbsMsg.setTopicName(topicName);
		if (StringUtil.isNotNullAndNotEmpty(titleType)) {
			bbsMsg.setTitleType(Integer.parseInt(titleType));
		}
		else {
			bbsMsg.setTitleType(1);
		}
		if (StringUtil.isNotNullAndNotEmpty(isDel)) {
			bbsMsg.setIsDel(Integer.parseInt(isDel));
		}
		bbsMsg.setTitle(title);
		bbsMsg.setPic(pic);
		try {
			// 黑名单校验
			r = validationBlackList(userId, userType);
			if (r.isSuccess()) {
				// 话题校验：是否被屏蔽、禁言
				r = validationtTopic(bbsMsg);
				if (r.isSuccess()){
					//关键字过滤
					content = filterWord(content);
					bbsMsg.setContent(content);
				}
				r = bbsFactory.getBBSAction(userType).doReleaseAction(bbsMsg);
			}
		} 
		
		catch (Exception e) {
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r;
	}
	
	/**
	 * 黑名单校验
	 * 黑名单变动不大，放redis
	 * @param bbsMsg
	 * @return
	 */
	public ReqResult validationBlackList(Long userId, Integer userType) {
		ReqResult  r = ReqResult.getSuccess();
		List<BbsBlacklist> blackList = redisUtil.get(REDISKEY.BBS_STUDENT_BLACK_LIST);
		if (blackList != null && blackList.size() > 0) {
			Date now = new Date();
			for (BbsBlacklist blacklist : blackList) {
				if (blacklist != null && blacklist.getUserId().equals(userId)) {
					if (blacklist.getMuteTime() != null && blacklist.getMuteTime().after(now)) {
						logger.info("*************************************** You Are In Black List! UserId : " + userId  + ", Ban Time : " + blacklist.getMuteTime());
						r.setCode(ResultCode.ERRORCODE.BBS_USER_IS_BLACK);
						r.setMsgInfo(ResultCode.ERRORINFO.BBS_USER_IS_BLACK);
						return r;
					}
					else { //时间为空表示永久禁言
						r.setCode(ResultCode.ERRORCODE.BBS_USER_IS_BLACK);
						r.setMsgInfo(ResultCode.ERRORINFO.BBS_USER_IS_BLACK);
						return r;
					}
				}
			}
		}
		else {
			blackList = bbsManager.getBBSBlacklistList(new BbsBlacklist(), null, null);
			if (blackList != null && blackList.size() > 0) {
				redisUtil.set(REDISKEY.BBS_STUDENT_BLACK_LIST, blackList);
			}
		}
		
		return r;
	}
	
	/**
	 *  话题校验：是否被屏蔽、禁言
	 * @param bbsMsg
	 * @return
	 */
	public ReqResult validationtTopic(BbsMessage bbsMsg) {
		ReqResult  r = ReqResult.getSuccess();
		List<BbsTopic> topicList = redisUtil.get(REDISKEY.BBS_STUDENT_TOPIC_LIST);
		if (topicList != null && topicList.size() > 0) {
			for (BbsTopic bbsTopic : topicList) {
				if (bbsTopic != null && bbsMsg != null && StringUtil.isNotNullAndNotEmpty(bbsMsg.getTopicName()) &&
						bbsMsg.getTopicName().equals(bbsTopic.getTopicName())) {
					if (bbsTopic.getIsDel() ==1 || bbsTopic.getIsOpen() ==1 || bbsTopic.getStatus() ==1) {
						r.setCode(ResultCode.ERRORCODE.BBS_TOPIC_IS_DELETE);
						r.setMsgInfo(ResultCode.ERRORINFO.BBS_TOPIC_IS_DELETE);
						return r;
					}
				}
			}
		}
		else {
			topicList = bbsManager.getBBSTopicList(new BbsTopic(), null, null);
			if (topicList != null && topicList.size() > 0) {
				redisUtil.set(REDISKEY.BBS_STUDENT_TOPIC_LIST, topicList);
			}
			for (BbsTopic bbsTopic : topicList) {
				if (bbsTopic != null && bbsMsg != null && StringUtil.isNotNullAndNotEmpty(bbsMsg.getTopicName()) &&
						bbsMsg.getTopicName().equals(bbsTopic.getTopicName())) {
					if (bbsTopic.getIsDel() ==1 || bbsTopic.getIsOpen() ==1 || bbsTopic.getStatus() ==1) {
						r.setCode(ResultCode.ERRORCODE.BBS_TOPIC_IS_DELETE);
						r.setMsgInfo(ResultCode.ERRORINFO.BBS_TOPIC_IS_DELETE);
						return r;
					}
				}
			}
		}
		
		return r;
	}
	
	/**
	 *  关键字过滤
	 * @param bbsMsg
	 * @return
	 */
	public String filterWord(String content) {
		List<BbsWord> bbsWordList = redisUtil.get(REDISKEY.BBS_FILTER_WORD_LIST);
		if (bbsWordList != null && bbsWordList.size() > 0) {
			content = sensitiveWordFilter.replaceSensitiveWord(content, minMatchTYpe, "*");
		}
		else {
			bbsWordList  = bbsManager.getBBSWordList(new BbsWord());
			if (bbsWordList != null && bbsWordList.size() > 0) {
				content = sensitiveWordFilter.replaceSensitiveWord(content, minMatchTYpe, "*");
			}
		}
		return content;
	}
	
	@Override
	public ReqResult getBBSTopic(Integer userType) {
		ReqResult r = ReqResult.getSuccess();
		
		List<BbsTopic> topicList = new ArrayList<BbsTopic>();
		try {
			topicList = bbsFactory.getBBSAction(userType).getBBSTopic();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (topicList != null && topicList.size() > 0) {
			r.setData(topicList);
		}
		return r;
	}

	@Override
	public ReqResult getBBSList(String userId, Integer userType, Integer pageSize, Integer pageIndex) {
		ReqResult r = ReqResult.getSuccess();
		try {
			List<BbsMessage> bbsList = bbsFactory.getBBSAction(userType).getBBSList(userId, pageSize, pageIndex);
			if (bbsList != null && bbsList.size() > 0) {
				r.setData(bbsList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
            return r;
		}
		
		return r;
	}

	@Override
	public ReqResult reply(Long userId, Integer userType, String userName, String headIcon, Integer bbsId, String cityId,
			String cityName,String byreId, String byreUserId, String byreUserName, String byreUserType, String byreContent,
			String replyContent, String opertionType) {
		ReqResult r = ReqResult.getSuccess();
		if (replyContent.length() > 255) {
			r.setCode(ResultCode.ERRORCODE.BBS_REPLY_CONTENT_IS_OVER_LENGTH);
			r.setMsgInfo(ResultCode.ERRORINFO.BBS_REPLY_CONTENT_IS_OVER_LENGTH);
			return r;
		}
		if (replyContent == null || "".equals(replyContent.trim())) {
			r.setCode(ResultCode.ERRORCODE.BBS_REPLY_CONTENT_IS_ENPTY);
			r.setMsgInfo(ResultCode.ERRORINFO.BBS_REPLY_CONTENT_IS_ENPTY);
			return r;
		}
		
		BbsReply bbsReply = new BbsReply();
		bbsReply.setUserId(userId);
		bbsReply.setUserType(2);
		bbsReply.setUserName(userName);
		Student student = studentManager.getStudentInfo(userId);
		if (StringUtil.isNullOrEmpty(headIcon)) {
			bbsReply.setHeadIcon(student.getHeadIcon());
		}
		else {
			bbsReply.setHeadIcon(headIcon);
		}
		bbsReply.setBbsId(bbsId);
		if (StringUtil.isNotNullAndNotEmpty(cityId)) {
			bbsReply.setCityId(Integer.parseInt(cityId));
		}
		bbsReply.setCityName(cityName);
		if (StringUtil.isNotNullAndNotEmpty(byreId)) {
			bbsReply.setByreId(Integer.parseInt(byreId));
		}
		if (StringUtil.isNotNullAndNotEmpty(byreUserId)) {
			bbsReply.setByreUserId(Long.parseLong(byreUserId));
			
		}
		if (StringUtil.isNotNullAndNotEmpty(byreUserType)) {
			bbsReply.setByreUserType(Integer.parseInt(byreUserType));
		}
		bbsReply.setByreUserName(byreUserName);
		bbsReply.setByreContent(byreContent);
		try {
			// 黑名单校验
			r = validationBlackList(userId, userType);
			if (r.isSuccess()) {
				//关键字过滤
				replyContent = filterWord(replyContent);
				bbsReply.setReplyContent(replyContent);
				r =  bbsFactory.getBBSAction(userType).doReplyAction(bbsReply);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r;
	}

	@Override
	public ReqResult praise(Long userId, Integer userType, String userName, String headIcon, Integer bbsId,
			String praiseUserId, String praiseUserType, Integer isDel) {
		ReqResult r = ReqResult.getSuccess();
		/**
		 * 初始化实体
		 */
		BbsPraise bbsPraise = new BbsPraise();
		bbsPraise.setUserId(userId);
		bbsPraise.setUserType(2);
		bbsPraise.setUserName(userName);
		Student student = studentManager.getStudentInfo(userId);
		if (StringUtil.isNullOrEmpty(headIcon)) {
			bbsPraise.setHeadIcon(student.getHeadIcon());
		}
		else {
			bbsPraise.setHeadIcon(headIcon);
		}
		bbsPraise.setBbsId(bbsId);
		bbsPraise.setIsDel(isDel);
		
		try {
			r =  bbsFactory.getBBSAction(userType).doPraiseAction(bbsPraise);
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r;
	}

	@Override
	public ReqResult getBBSDetailInfo(String userId, Integer userType, Integer bbsId) {
		ReqResult r = ReqResult.getSuccess();
		BbsDetailMessage bbsDetailMessage = new BbsDetailMessage();
		bbsDetailMessage.setId(bbsId);
		try {
			r = bbsFactory.getBBSAction(userType).getBbsDetail(bbsDetailMessage, userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}
	
	@Override
	public ReqResult getBBSReplyList(String userId, Integer userType, Integer bbsId, Integer pageSize,
			Integer pageIndex) {
		ReqResult r = ReqResult.getSuccess();
		BbsReply reply = new BbsReply();
		reply.setBbsId(bbsId);
		try {
			r = bbsFactory.getBBSAction(userType).getBBSReplyList(reply, pageSize, pageIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public ReqResult getBBSPraiseList(String userId, Integer userType, Integer bbsId, Integer pageSize,
			Integer pageIndex) {
		ReqResult r = ReqResult.getSuccess();
		BbsPraise praise =new  BbsPraise();
		praise.setBbsId(bbsId);
		try {
			r = bbsFactory.getBBSAction(userType).getBBSPraiseList(praise, pageSize, pageIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return r;
	}

	@Override
	public ReqResult inform(Long userId, Integer userType, String userName, String headIcon, String reason, Integer bbsId, String title,
			String content, String isDel) {
		ReqResult r = ReqResult.getSuccess();
		BbsInform inform = new BbsInform();
		inform.setBbsId(bbsId);
		inform.setUserId(userId);
		inform.setUserType(2);
		inform.setTitle(title);
		inform.setReason(reason);
		inform.setContent(content);
		if (StringUtil.isNotNullAndNotEmpty(isDel)) {
			inform.setIsDel(Integer.parseInt(isDel));
		}
		else {
			inform.setIsDel(0);
		}
		try {
			r = bbsFactory.getBBSAction(userType).informTopic(inform);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public ReqResult getMyBBSList(Long userId, Integer userType, Integer pageSize, Integer pageIndex) {
		ReqResult r = ReqResult.getSuccess();
		BbsMessage bmsg = new BbsMessage();
		bmsg.setUserId(userId);
		bmsg.setUserType(userType);
		try {
			r =  bbsFactory.getBBSAction(userType).getMyBBSList(bmsg,  pageSize, pageIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public ReqResult getMyBBSReplyList(Long userId, Integer userType, Integer pageSize, Integer pageIndex) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r =  bbsFactory.getBBSAction(userType).getMyBBSReplyList(userId, userType, pageSize, pageIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public ReqResult getMyBBSPraiseList(Long userId, Integer userType, Integer pageSize, Integer pageIndex) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r =  bbsFactory.getBBSAction(userType).getMyBBSPraiseList(userId, userType, pageSize, pageIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public ReqResult delTopic(Long userId, Integer userType, Integer bbsId) {
		ReqResult r = ReqResult.getSuccess();
		BbsMessage  bbsMsg = new BbsMessage();
		/**
		 * 判断发帖人是否本人、是否已删除
		 */
		bbsMsg.setId(bbsId);
		BbsMessage bbsMsgInfo = bbsManager.getBBSMessage(bbsMsg);
		if (bbsMsgInfo != null && bbsMsgInfo.getIsDel() == 0) {
			if (bbsMsgInfo.getUserId() != null && bbsMsgInfo.getUserId().equals(userId)) {
				SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
				String ctime = "";
				if (bbsMsgInfo.getIsTopGlobal() > 0 || bbsMsgInfo.getIsTopGroup() > 0) {
					ctime = formatter.format(DateUtil.getNextTenYear(bbsMsgInfo.getCtime()));
				}
				else {
					ctime = formatter.format(bbsMsgInfo.getCtime());
				}
				Long sizeOld = redisUtil.zCard(REDISKEY.BBS_STUDENT_LIST);
				logger.info("***************************************delTopic sizeOld : " + sizeOld);
				
				try {
					redisUtil.zRemove(REDISKEY.BBS_STUDENT_LIST, bbsMsgInfo);
					redisUtil.zRemoveByScore(REDISKEY.BBS_STUDENT_LIST, formatter.parse(ctime).getTime(), formatter.parse(ctime).getTime());
					redisUtil.zRemRange(REDISKEY.BBS_STUDENT_LIST, formatter.parse(ctime).getTime(), formatter.parse(ctime).getTime());
				} 
				catch (ParseException e) {
					e.printStackTrace();
					r.setCode(ResultCode.ERRORCODE.BBS_IS_NOT_EXIT);
					r.setMsgInfo(ResultCode.ERRORINFO.BBS_IS_NOT_EXIT);
					return r;
				}
				Long sizeNew = redisUtil.zCard(REDISKEY.BBS_STUDENT_LIST);
				logger.info("***************************************delTopic sizeOld : " + sizeOld);
				if (sizeOld != null && sizeNew != null && sizeOld >= 0 && sizeNew >= 0 && sizeOld != sizeNew -1) {
					initBbsMessageList(10, 1);
					logger.info("***************************************delTopic delete BBS_STUDENT_LIST  " );
				}
				
				Bbs bbsInfo = new Bbs();
				bbsInfo.setIsDel(1);
				bbsInfo.setId(bbsId);
				bbsManager.updateBBSRecord(bbsInfo);
			}
			else {
				r.setCode(ResultCode.ERRORCODE.BBS_IS_NOT_USER);
				r.setMsgInfo(ResultCode.ERRORINFO.BBS_IS_NOT_USER);
				return r;
			}
		}
		else {
			r.setCode(ResultCode.ERRORCODE.BBS_IS_NOT_EXIT);
			r.setMsgInfo(ResultCode.ERRORINFO.BBS_IS_NOT_EXIT);
			return r;
		}
		
		return r;
	}

	
	/**
	 * 清除社区缓存，重新拉取
	 * @param pageSize
	 * @param pageIndex
	 * @throws Exception
	 */
	public void initBbsMessageList(Integer pageSize, Integer pageIndex) {
		redisUtil.delete(REDISKEY.BBS_STUDENT_LIST);
		List<BbsMessage> bbsList =  bbsManager.getBBSList(new BbsMessage(), pageSize, pageIndex);
		if (bbsList != null && bbsList.size() > 0 ) {
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
			for (BbsMessage bbsMsg : bbsList) {
				String ctime = null; //如果有置顶，score标记为当前时间+10年
				if (bbsMsg.getIsTopGlobal() > 0 || bbsMsg.getIsTopGroup() > 0) {
					ctime = formatter.format(DateUtil.getNextTenYear(bbsMsg.getCtime()));
				}
				else {
					 ctime = formatter.format(bbsMsg.getCtime());
				}
				try {
					redisUtil.zAdd(REDISKEY.BBS_STUDENT_LIST, bbsMsg, formatter.parse(ctime).getTime());
				} catch (ParseException e) {
					logger.error("****************************************initBbsMessageList zAdd Error :  " + bbsMsg +", Exception : " + e.getMessage());
				}
			}
		}
	}
	
	/**
	 * 编辑帖子
	 */
	@Override
	public ReqResult editBBSContent(BbsMessage bbsMessage) {
		ReqResult r = ReqResult.getSuccess();
		try {
			logger.info("************************************* bbsMessage pic : " + bbsMessage.getPic());
			bbsManager.updateBBSContentInfo(bbsMessage);
			
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r;
	}


}
