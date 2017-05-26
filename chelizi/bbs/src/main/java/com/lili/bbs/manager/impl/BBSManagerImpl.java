package com.lili.bbs.manager.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.lili.bbs.dao.mapper.BbsBlacklistMapper;
import com.lili.bbs.dao.mapper.BbsExtendMapper;
import com.lili.bbs.dao.mapper.BbsInformMapper;
import com.lili.bbs.dao.mapper.BbsMapper;
import com.lili.bbs.dao.mapper.BbsPraiseMapper;
import com.lili.bbs.dao.mapper.BbsReplyMapper;
import com.lili.bbs.dao.mapper.BbsTopicMapper;
import com.lili.bbs.dao.mapper.BbsWeskitMapper;
import com.lili.bbs.dao.mapper.BbsWordMapper;
import com.lili.bbs.dto.Bbs;
import com.lili.bbs.dto.BbsBlacklist;
import com.lili.bbs.dto.BbsExtend;
import com.lili.bbs.dto.BbsInform;
import com.lili.bbs.dto.BbsPraise;
import com.lili.bbs.dto.BbsReply;
import com.lili.bbs.dto.BbsTopic;
import com.lili.bbs.dto.BbsWeskit;
import com.lili.bbs.dto.BbsWord;
import com.lili.bbs.manager.IBBSManager;
import com.lili.bbs.vo.BbsDetailMessage;
import com.lili.bbs.vo.BbsMessage;
import com.lili.coach.dto.Coach;
import com.lili.coach.manager.CoachManager;
import com.lili.common.constant.JpushConstant;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.DateUtil;
import com.lili.common.util.MyRowBounds;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.StringUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;

public class BBSManagerImpl implements IBBSManager {
	
	Logger logger = Logger.getLogger(BBSManagerImpl.class);
	
	@Autowired
	BbsBlacklistMapper bbsBlacklistMapper;
	
	@Autowired
	BbsExtendMapper bbsExtendMapper;
	
	@Autowired
	BbsInformMapper bbsInformMapper;
	
	@Autowired
	BbsMapper bbsMapper;
	
	@Autowired
	BbsPraiseMapper bbsPraiseMapper;
	
	@Autowired
	BbsReplyMapper bbsReplyMapper;
	
	@Autowired
	BbsTopicMapper bbsTopicMapper;
	
	@Autowired
	BbsWeskitMapper bbsWeckitMapper;
	
	@Autowired
	BbsWordMapper bbsWordMapper;
	
	@Autowired
	StudentManager studentManager;
	
	@Autowired
	CoachManager coachManager;
	
	@Autowired
	RedisUtil redisUtil;
	
    @Resource(name = "jpushProducer")
  	DefaultMQProducer jpushProducer;
	
	@Override
	public List<BbsMessage> getBBSList(BbsMessage bbsMessage, Integer pageSize, Integer pageIndex) {
		if (pageIndex != null && pageIndex != null ) {
			MyRowBounds myRowBounds = new MyRowBounds(pageIndex, pageSize);
			bbsMessage.setMyRowBounds(myRowBounds);
		}
		return bbsMapper.getBbsList(bbsMessage);
	}
	
	@Override
	public List<BbsMessage> getMyBBSList(BbsMessage bbsMessage, Integer pageSize, Integer pageIndex) {
		if (pageIndex != null && pageIndex != null ) {
			MyRowBounds myRowBounds = new MyRowBounds(pageIndex, pageSize);
			bbsMessage.setMyRowBounds(myRowBounds);
		}
		return bbsMapper.getMyBBSList(bbsMessage);
	}
	
	@Override
	public List<BbsMessage> getCMSBbsList(BbsMessage bbsMessage, Integer pageSize, Integer pageIndex) {
		if (pageIndex != null && pageIndex != null ) {
			MyRowBounds myRowBounds = new MyRowBounds(pageIndex, pageSize);
			bbsMessage.setMyRowBounds(myRowBounds);
		}
		if (bbsMessage.getStatus() != null && bbsMessage.getStatus() ==2) { //举报
			bbsMessage.setStatus(null);
		}
		else {
			bbsMessage.setIsInform(null);
		}
		
		return bbsMapper.getCMSBbsList(bbsMessage);
	}

	@Transactional
	@Override
	public BbsDetailMessage getBBSDetail(BbsDetailMessage bbsDetailMessage, String userId) {
		BbsMessage bmsg  = new BbsMessage();
		bmsg.setId(bbsDetailMessage.getId());
		BbsMessage bbsMessage  =  bbsMapper.queryBBSDetail(bmsg);
		if (bbsMessage == null) {
			return null;
		}
		
		try {
			bbsDetailMessage = BeanCopy.copyNotNull(bbsMessage, BbsDetailMessage.class);
		} catch (Exception e) {
			logger.info("*********************** getBBSDetail BeanCopy Error: " + e.getMessage());
		}
		
		BbsPraise praise = new BbsPraise();
		praise.setBbsId(bbsDetailMessage.getId());
		MyRowBounds myRowBounds = new MyRowBounds(1, 4);
		bbsMessage.setMyRowBounds(myRowBounds);
		List<BbsPraise> praiseList = bbsPraiseMapper.getPraiseList(praise);
		if (praiseList != null && praiseList.size() > 0) {
			bbsDetailMessage.setPraiseList(praiseList);
		}
		
		/**
		 * 判断用户是否有点赞
		 */
		if (StringUtil.isNotNullAndNotEmpty(userId)) {
			BbsPraise bbsPraise = new BbsPraise();
			bbsPraise.setBbsId(bbsDetailMessage.getId());
			bbsPraise.setUserId(Long.parseLong(userId));
			if (bbsPraiseMapper.countIsPraiseByUser(bbsPraise) > 0) {
				bbsDetailMessage.setIsPraise(1);
			}
			else {
				bbsDetailMessage.setIsPraise(0);
			}
		}
		
		return bbsDetailMessage;
	}

	@Override
	public void addPraiseOne(BbsMessage bbsMessage) {
		Bbs bbs = new Bbs();
		try {
			bbs = BeanCopy.copyAll(bbsMessage, bbs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		bbs.setMtime(new Date());
		bbsMapper.updateBBSPraise(bbs);
	}
	
	@Override
	public void updateBBSInform(Bbs bbs) {
		bbs.setMtime(new Date());
		bbsMapper.updateBBSInform(bbs);
	}

	@Override
	public void handleBbs(Bbs bbs) {
	}

	@Transactional
	@Override
	public List<BbsPraise> getBBSPraiseList(BbsPraise bbsPraise, Integer pageSize, Integer pageIndex) {
		if (pageIndex != null && pageIndex != null ) {
			MyRowBounds myRowBounds = new MyRowBounds(pageIndex, pageSize);
			bbsPraise.setMyRowBounds(myRowBounds);
		}
		return bbsPraiseMapper.getPraiseList(bbsPraise);
	}

	@Transactional
	@Override
	public void saveBBSPraise(BbsPraise bbsPraise) {
		int isPraise = bbsPraiseMapper.countIsPraiseByUser(bbsPraise);
		if (isPraise > 0) {
			logger.info("************************** User Has Praise : userId = " + bbsPraise.getUserId() + ",bbsId: " + bbsPraise.getBbsId());
			redisUtil.delete(REDISKEY.BBS_STUDENT_PRAISE_LIST + bbsPraise.getUserId());
			BbsPraise userpraise = new BbsPraise();
			userpraise.setUserId(bbsPraise.getUserId());
			List<BbsPraise> praiseList = bbsPraiseMapper.getPraiseList(userpraise);
			redisUtil.set(REDISKEY.BBS_STUDENT_PRAISE_LIST + bbsPraise.getUserId(), praiseList);
		}
		else {
			BbsWeskit bbsWeskit = new BbsWeskit();
			bbsWeskit.setUserId(bbsPraise.getUserId());
			bbsWeskit.setUserType(2);
			BbsWeskit isWeckit = bbsWeckitMapper.queryWeskit(bbsWeskit);
			if (isWeckit != null) {
				if (isWeckit.getChannel() == 3) {
					bbsPraise.setChannel(3); //如果是管理员
				}
				else {
					bbsPraise.setChannel(2); //如果是马甲
				}
			}
			else {
				bbsPraise.setChannel(1);
			}
			
			bbsPraise.setCtime(new Date());
			bbsPraiseMapper.insertSelective(bbsPraise);
			
			Bbs bbs = new Bbs();
			bbs.setId(bbsPraise.getBbsId());
			bbs.setMtime(new Date());
			bbsMapper.updateBBSPraise(bbs);
			
			//更新缓存
			BbsMessage bbsMessage = new BbsMessage();
			bbsMessage.setId(bbsPraise.getBbsId());
			bbsMessage = bbsMapper.queryBBSDetail(bbsMessage);
			if (bbsMessage != null) {
				Long sizeOld = redisUtil.zCard(REDISKEY.BBS_STUDENT_LIST);
				logger.info("***************************************updateBBSContentInfo sizeOld : " + sizeOld);
				
				SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
				String ctime = "";
				logger.info("**************************************** bbs : " + bbsMessage +", citme : "+ bbsMessage.getCtime());
				if (bbsMessage != null) {
					if (bbsMessage.getIsTopGlobal() != null && bbsMessage.getIsTopGlobal() > 0) {
						ctime = formatter.format(DateUtil.getNextTenYear(bbsMessage.getCtime()));
					}
					else if (bbsMessage.getIsTopGroup() != null && bbsMessage.getIsTopGroup() > 0) {
						ctime = formatter.format(DateUtil.getNextTenYear(bbsMessage.getCtime()));
					}
					else {
						ctime = formatter.format(bbsMessage.getCtime());
					}
				}
				redisUtil.zRemove(REDISKEY.BBS_STUDENT_LIST,  bbsMessage);
				try {
					redisUtil.zRemoveByScore(REDISKEY.BBS_STUDENT_LIST, formatter.parse(ctime).getTime(), formatter.parse(ctime).getTime());
					redisUtil.zAdd(REDISKEY.BBS_STUDENT_LIST, bbsMessage, formatter.parse(ctime).getTime());
				} catch (ParseException e) {
					logger.error("********************************* deleteReply Error : " + e.getMessage());
				}
				
				Long sizeNew = redisUtil.zCard(REDISKEY.BBS_STUDENT_LIST);
				logger.info("***************************************updateBBSContentInfo sizeNew : " + sizeNew);
				
				if (sizeOld != null && sizeNew != null && sizeOld >= 0 && sizeNew >= 0 && sizeNew !=sizeOld) {
					initBbsMessageList(150, 1);
					logger.info("***************************************updateBBSContentInfo delete BBS_STUDENT_LIST" );
				}
			}
		}
	}

	@Override
	public void updateBBSPraise(BbsPraise bbsPraise) {
		
	}

	@Override
	public List<BbsReply> getBBSReplyList(BbsReply bbsReply, Integer pageSize, Integer pageIndex) {
		if (pageIndex != null && pageIndex != null ) {
			MyRowBounds myRowBounds = new MyRowBounds(pageIndex, pageSize);
			bbsReply.setMyRowBounds(myRowBounds);
		}
		return bbsReplyMapper.getReplyList(bbsReply);
	}

	@Override
	public void saveBBSReply(BbsReply bbsReply) {
		bbsReply.setCtime(new Date());
		bbsReplyMapper.insertSelective(bbsReply);
	}

	@Override
	public void handleBBSReply(BbsReply bbsReply) {
		
	}

	@Override
	public List<BbsBlacklist> getBBSBlacklistList(BbsBlacklist bbsBlacklist, Integer pageSize, Integer pageIndex) {
		if (pageIndex != null && pageIndex != null ) {
			MyRowBounds myRowBounds = new MyRowBounds(pageIndex, pageSize);
			bbsBlacklist.setMyRowBounds(myRowBounds);
		}
		return bbsBlacklistMapper.getBbsBlacklistList(bbsBlacklist);
	}

	@Override
	public void saveBBSBlacklist(BbsBlacklist bbsBlacklist) {
		
	}

	@Override
	public void updateBBSBlacklist(BbsBlacklist bbsBlacklist) {
		
	}

	@Override
	public List<BbsWord> getBBSWordList(BbsWord bbsWord) {
		return bbsWordMapper.getBBSWordList(bbsWord);
	}

	@Transactional
	@Override
	public void saveBBSTopicInfo(BbsMessage bbsMessage) throws Exception {
		Bbs bbs = new Bbs();
		BbsExtend bbsExt = new BbsExtend();
		//bbsMessage.setId(null);
		bbs = BeanCopy.copyAll(bbsMessage, bbs);
		bbs.setCtime(new Date());
		bbsMapper.insertSelective(bbs);
		
		if (bbs != null && bbs.getId() != null) {
			bbsExt = BeanCopy.copyAll(bbsMessage, bbsExt);
			bbsExt.setBbsId(bbs.getId());
			bbsExtendMapper.insertSelective(bbsExt);
		}
		
		
		redisUtil.delete(REDISKEY.BBS_SUM);
		BbsMessage bsg = new BbsMessage();
		Integer total = bbsMapper.countBbsSize(bsg);
		redisUtil.set(REDISKEY.BBS_SUM, total);
		
		
		redisUtil.delete(REDISKEY.BBS_ID);
		redisUtil.set(REDISKEY.BBS_ID, bbs.getId());
	}
	
	@Transactional
	public void updateBBSContentInfo(BbsMessage bbsMessage) throws Exception {
		Bbs bbs = new Bbs();
		bbs = BeanCopy.copyAll(bbsMessage, bbs);
		bbs.setMtime(new Date());
		bbsMapper.updateByPrimaryKeySelective(bbs);
		
		if (bbs != null && bbs.getId() != null) {
			BbsExtend bbsExt = new BbsExtend();
			bbsExt = BeanCopy.copyAll(bbsMessage, bbsExt);
			bbsExt.setBbsId(bbs.getId());
			logger.info("************************************* bbsMessage pic : " + bbsMessage.getPic());
			logger.info("************************************* bbsExt pic : " + bbsExt.getPic());
			bbsExtendMapper.updateByBbsId(bbsExt);
		}
	
		bbs.setId(bbsMessage.getId());
		Bbs msg = bbsMapper.queryBBSInfo(bbs);
		// 更新缓存
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		String ctime = "";
		logger.info("**************************************** bbs : " + msg +", citme : "+ msg.getCtime());
		if (bbsMessage != null && msg != null) {
			if (bbsMessage.getIsTopGlobal() != null && bbsMessage.getIsTopGlobal() > 0) {
				ctime = formatter.format(DateUtil.getNextTenYear(msg.getCtime()));
			}
			else if (bbsMessage.getIsTopGroup() != null && bbsMessage.getIsTopGroup() > 0) {
				ctime = formatter.format(DateUtil.getNextTenYear(msg.getCtime()));
			}
			else {
				ctime = formatter.format(msg.getCtime());
			}
		}
		
		Long sizeOld = redisUtil.zCard(REDISKEY.BBS_STUDENT_LIST);
		logger.info("***************************************updateBBSContentInfo sizeOld : " + sizeOld);
		
		redisUtil.zRemove(REDISKEY.BBS_STUDENT_LIST,  bbsMessage);
		redisUtil.zRemoveByScore(REDISKEY.BBS_STUDENT_LIST, formatter.parse(ctime).getTime(), formatter.parse(ctime).getTime());
		
		redisUtil.zAdd(REDISKEY.BBS_STUDENT_LIST, bbsMessage, formatter.parse(ctime).getTime());
		
		Long sizeNew = redisUtil.zCard(REDISKEY.BBS_STUDENT_LIST);
		logger.info("***************************************updateBBSContentInfo sizeNew : " + sizeNew);
		
		if (sizeOld != null && sizeNew != null && sizeOld >= 0 && sizeNew >= 0 && sizeNew !=sizeOld) {
			initBbsMessageList(150, 1);
			logger.info("***************************************updateBBSContentInfo delete BBS_STUDENT_LIST" );
		}
	}
	
	/**
	 * 清除社区缓存，重新拉取
	 * @param pageSize
	 * @param pageIndex
	 * @throws Exception
	 */
	public void initBbsMessageList(Integer pageSize, Integer pageIndex) {
		redisUtil.delete(REDISKEY.BBS_STUDENT_LIST);
		
		BbsMessage bbsMessage = new BbsMessage();
		if (pageIndex != null && pageIndex != null ) {
			MyRowBounds myRowBounds = new MyRowBounds(pageIndex, pageSize);
			bbsMessage .setMyRowBounds(myRowBounds);
		}
		List<BbsMessage> bbsList = bbsMapper.getBbsList(bbsMessage);
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

	@Override
	public BbsWeskit isWeskit(BbsWeskit bbsWeskit) {
		return bbsWeckitMapper.queryWeskit(bbsWeskit);
	}

	@Override
	public void updateBBSWeskit(BbsWeskit bbsWeskit) {
		bbsWeskit.setMtime(new Date());
		bbsWeckitMapper.updateByPrimaryKey(bbsWeskit);
		
	}

	@Override
	public void saveBBSInform(BbsInform inform) {
		inform.setCtime(new Date());
		bbsInformMapper.insertSelective(inform);
	}

	@Override
	public List<BbsTopic> getBBSTopicList(BbsTopic bbsTopic, Integer pageSize, Integer pageIndex) {
		if (pageIndex != null && pageIndex != null ) {
			MyRowBounds myRowBounds = new MyRowBounds(pageIndex, pageSize);
			bbsTopic.setMyRowBounds(myRowBounds);
		}
		return bbsTopicMapper.queryBBSTopicList(bbsTopic);
	}

	@Override
	public Bbs getBBSInfo(Bbs bbs) {
		return bbsMapper.queryBBSInfo(bbs);
	}

	@Override
	public void updateBBSRecord(Bbs bbsInfo) {
		bbsInfo.setMtime(new Date());
		bbsMapper.updateByPrimaryKeySelective(bbsInfo);
	}

	@Override
	public Integer getMaxBBSId() {
		return bbsMapper.getMaxBBSId();
	}

	@Override
	public Integer countIsPraiseByUser(BbsPraise bbsPraise) {
		return bbsPraiseMapper.countIsPraiseByUser(bbsPraise);
	}

	@Override
	public BbsMessage getBBSMessage(BbsMessage bbsMsg) {
		return bbsMapper.queryBBSDetail(bbsMsg);
	}

	@Override
	public BbsPraise getPraiseInfo(BbsPraise bbsPraise) {
		return bbsPraiseMapper.queryPraiseInfo(bbsPraise);
	}

	@Override
	public BbsInform getInformInfo(BbsInform inform) {
		return bbsInformMapper.queryInformInfo(inform);
	}

	@Override
	public BbsReply getReplyInfo(BbsReply twoReply) {
		return bbsReplyMapper.getReplyInfo(twoReply);
	}

	@Override
	public void updateBBSReply(Bbs bbs) {
		bbs.setMtime(new Date());
		bbsMapper.updateBBSReply(bbs);
	}

	@Transactional
	@Override
	public int updateBBSOnly(Bbs bbs) {
		int r =  0;
		BbsMessage bmsg  = new BbsMessage();
		bmsg.setId(bbs.getId());
		BbsMessage bbsMessageOld  =  bbsMapper.queryBBSDetail(bmsg);
		if (bbsMessageOld != null) { //拿到原来的内容
			 if (bbs.getStatus() != null && bbs.getStatus() == 0) { //解除屏蔽操作
				 if (bbsMessageOld.getInform() != null && bbsMessageOld.getInform() > 0) {
					 bbs.setStatus(2); 
				 }
			 }
			bbs.setMtime(new Date());
			r =  bbsMapper.updateByPrimaryKeySelective(bbs);
			 
			//更新后重新获取更新内容
			BbsMessage bbsMessage  =  bbsMapper.queryBBSDetail(bmsg);
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
			String ctime = "";
			if (bbsMessage.getIsTopGlobal() > 0 || bbsMessage.getIsTopGroup() > 0) {
				ctime = formatter.format(DateUtil.getNextTenYear(bbsMessage.getCtime()));
			}
			else {
				ctime = formatter.format(bbsMessage.getCtime());
			}
			
			try {
				//更新缓存 --屏蔽只移除缓存，不更新
				Long sizeOld = redisUtil.zCard(REDISKEY.BBS_STUDENT_LIST);
				logger.info("***************************************updateBBSContentInfo sizeOld : " + sizeOld);
				
				redisUtil.zRemove(REDISKEY.BBS_STUDENT_LIST,  bbsMessageOld);
				redisUtil.zRemoveByScore(REDISKEY.BBS_STUDENT_LIST, formatter.parse(ctime).getTime(), formatter.parse(ctime).getTime());
				
				 if (bbs.getIsTopGlobal() != null  || bbs.getIsTopGroup() != null) { //状态一致，是置顶操作
					 redisUtil.zAdd(REDISKEY.BBS_STUDENT_LIST, bbsMessage, formatter.parse(ctime).getTime());
					 
					 Long sizeNew = redisUtil.zCard(REDISKEY.BBS_STUDENT_LIST);
					 logger.info("***************************************updateBBSContentInfo zhiding sizeNew : " + sizeNew);
					 // 置顶数组一致，只更新内容
					 if (sizeOld != null && sizeNew != null && sizeOld >= 0 && sizeNew >= 0 && sizeNew != sizeOld) {
						 initBbsMessageList(150, 1);
						 logger.info("***************************************updateBBSContentInfo delete BBS_STUDENT_LIST" );
					 }
				 }
				 else { //是屏蔽或解禁
					 String content = "";
					 if (bbsMessage.getContent() != null && !"".equals(bbsMessage.getContent())) {
						 if (bbsMessage.getContent().length() > 20) {
							 content = bbsMessage.getContent().substring(0, 20);
						 }
						 else {
							 content = bbsMessage.getContent();
						 }
					 }
					 if (StringUtil.isNullOrEmpty(content)) {
						 content = "[图片]";
					 }
					 if (bbs.getStatus() != null && bbs.getStatus() == 1) { //屏蔽操作
						 JpushMsg jmsg = new JpushMsg();
						 jmsg.setUserId(bbsMessage.getUserId());
						 jmsg.setOrderId(bbsMessage.getUserId() + JpushConstant.OPERATE.BBS_IS_DELETE);
						 jmsg.setOperate(JpushConstant.OPERATE.BBS_IS_DELETE);
						 jmsg.setAlter("您的帖子“" + content + "”存在违规行为，已经被管理员屏蔽！");
						 Message jpush = new Message();
						 jpush.setKeys(bbsMessage.getUserId() + JpushConstant.OPERATE.BBS_IS_DELETE);
						 jpush.setTopic(jpushProducer.getCreateTopicKey());
						 jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
						 jpush.setBody(SerializableUtil.serialize(jmsg));
						 jpushProducer.send(jpush);
						 
						 Long sizeNew = redisUtil.zCard(REDISKEY.BBS_STUDENT_LIST);
						 logger.info("***************************************updateBBSContentInfo pinbi sizeNew : " + sizeNew);
						 
						 if (sizeOld != null && sizeNew != null && sizeOld >= 0 && sizeNew >= 0 && sizeNew != (sizeOld -1)) {
							 initBbsMessageList(150, 1);
							 logger.info("***************************************updateBBSContentInfo pinbi delete BBS_STUDENT_LIST" );
						 }
					 }
					 else if (bbs.getStatus() != null && bbs.getStatus() == 0) { //解禁
						 JpushMsg jmsg = new JpushMsg();
						 jmsg.setUserId(bbsMessage.getUserId());
						 jmsg.setOrderId(bbsMessage.getUserId() + JpushConstant.OPERATE.BBS_IS_DELETE);
						 jmsg.setOperate(JpushConstant.OPERATE.BBS_IS_DELETE);
						 jmsg.setAlter("您的帖子“" + content + "”已经被管理员解禁！");
						 Message jpush = new Message();
						 jpush.setKeys(bbsMessage.getUserId() + JpushConstant.OPERATE.BBS_IS_DELETE);
						 jpush.setTopic(jpushProducer.getCreateTopicKey());
						 jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
						 jpush.setBody(SerializableUtil.serialize(jmsg));
						 jpushProducer.send(jpush);
						 redisUtil.zAdd(REDISKEY.BBS_STUDENT_LIST, bbsMessage, formatter.parse(ctime).getTime());
						 
						 Long sizeNew = redisUtil.zCard(REDISKEY.BBS_STUDENT_LIST);
						 logger.info("***************************************updateBBSContentInfo jie jin sizeNew : " + sizeNew);
						 // 解禁
						 if (sizeOld != null && sizeNew != null && sizeOld >= 0 && sizeNew >= 0 && sizeNew != sizeOld) {
							 initBbsMessageList(150, 1);
							 logger.info("***************************************updateBBSContentInfo  jie jin  delete BBS_STUDENT_LIST" );
						 }
					 }
				 }
				
			} catch (Exception e) {
				logger.error("********************************* updateBBSOnly Try Exception : " + e.getMessage());
			} 
		}
		
		return r;
	}

	@Transactional
	@Override
	public int newTopic(BbsTopic bbsTopic) {
		bbsTopic.setCtime(new Date());
		int a =  bbsTopicMapper.insertSelective(bbsTopic);
		
		redisUtil.delete(REDISKEY.BBS_STUDENT_TOPIC_LIST);
		
		BbsTopic topic = new BbsTopic();
		topic.setIsOpen(0);
		topic.setStatus(0);
		topic.setIsDel(0);
		List<BbsTopic> topicList = bbsTopicMapper.queryBBSTopicList(topic);
		if (topicList != null && topicList.size() != 0) {
			redisUtil.set(REDISKEY.BBS_STUDENT_TOPIC_LIST, topicList);
		}
		
		return a;
	}

	@Transactional
	@Override
	public int updateTopic(BbsTopic bbsTopic) {
		 BbsTopic topicOld = bbsTopicMapper.queryBBSTopic(bbsTopic);
		
		 bbsTopic.setMtime(new Date());
		 int r = bbsTopicMapper.updateByPrimaryKeySelective(bbsTopic);
		 
		 redisUtil.delete(REDISKEY.BBS_STUDENT_TOPIC_LIST);
		 /**
		  * 被屏蔽的话题不可见，解除屏蔽 - 更新圈子缓存
		  */
		 if (bbsTopic != null && bbsTopic.getStatus() != null && topicOld.getStatus() != bbsTopic.getStatus()) {
			 Bbs bbs = new Bbs();
			 bbs.setTopicId(bbsTopic.getId());
			 if (bbsTopic.getStatus() == 0 ) { //解除屏蔽
				 bbs.setStatus(0); // 解除屏蔽
			 }
			 if (bbsTopic.getStatus() ==1 ) { //屏蔽
				 bbs.setStatus(1); // 屏蔽
			 }
			 bbsMapper.updateBbsTopic(bbs);
			 //清除圈子列表缓存，重新拉去数据
			 initBbsMessageList(150, 1);
		 }
		 
		 BbsTopic topic = new BbsTopic();
		 topic.setIsOpen(0);
		 topic.setStatus(0);
		 topic.setIsDel(0);
		 List<BbsTopic> topicList =bbsTopicMapper.queryBBSTopicList(topic);
		 if (topicList != null && topicList.size() > 0 ) {
			 redisUtil.set(REDISKEY.BBS_STUDENT_TOPIC_LIST, topicList);
		 }
		return r ;
	}

	@Override
	public List<BbsWord> getWordList(BbsWord bbsWord, Integer pageSize, Integer pageIndex) {
		if (pageIndex != null && pageIndex != null ) {
			MyRowBounds myRowBounds = new MyRowBounds(pageIndex, pageSize);
			bbsWord.setMyRowBounds(myRowBounds);
		}
		return bbsWordMapper.getBBSWordList(bbsWord);
	}

	@Transactional
	@Override
	public int addWord(BbsWord bbsWord) {
		int r  = 0;
		bbsWord.setCtime(new Date());
		BbsWord  isWord = bbsWordMapper.queryBbsWord(bbsWord);
		if (isWord != null ) {
			bbsWord.setIsDel(0);
			bbsWord.setMtime(new Date());
			r = bbsWordMapper.updateByPrimaryKeySelective(bbsWord);
		}
		else {
			bbsWord.setCtime(new Date());
			r =  bbsWordMapper.insertSelective(bbsWord);
		}
		
		/**
		 * 更新缓存
		 */
		redisUtil.delete(REDISKEY.BBS_FILTER_WORD_LIST);
		List<BbsWord> wordList = bbsWordMapper.getBBSWordList(new BbsWord());
		if (wordList != null && wordList.size() != 0) {
			redisUtil.set(REDISKEY.BBS_FILTER_WORD_LIST, wordList);
		}
		return r;
	}

	@Transactional
	@Override
	public int delWord(BbsWord bbsWord) {
		bbsWord.setMtime(new Date());
		int r =  bbsWordMapper.updateByPrimaryKeySelective(bbsWord);
		/**
		 * 更新缓存
		 */
		redisUtil.delete(REDISKEY.BBS_FILTER_WORD_LIST);
		List<BbsWord> wordList = bbsWordMapper.getBBSWordList(new BbsWord());
		if (wordList != null && wordList.size() != 0) {
			redisUtil.set(REDISKEY.BBS_FILTER_WORD_LIST, wordList);
		}
		return r;
	}

	@Override
	public List<BbsWeskit> getBbsWeskit(BbsWeskit bbsWeskit, Integer pageSize, Integer pageIndex) {
		if (pageIndex != null && pageIndex != null ) {
			MyRowBounds myRowBounds = new MyRowBounds(pageIndex, pageSize);
			bbsWeskit.setMyRowBounds(myRowBounds);
		}
		return bbsWeckitMapper.queryWeskitList(bbsWeskit);
	}

	@Transactional
	@Override
	public int addWeskiter(BbsWeskit bbsWeskit) {
		long userId = 0l;
		Integer userType = 2;
		if (bbsWeskit != null && bbsWeskit.getUserType() != null) {
			if (bbsWeskit.getUserType() == 1) { //教练
				userType = 1;
				Coach coach = new Coach();
				coach.setName(bbsWeskit.getUserName());
				coach.setSex(bbsWeskit.getSex());
				coach.setHeadIcon(bbsWeskit.getHeadIcon());
				coach.setRegisterTime(new Date());
				coachManager.addCoach(coach);
				userId = coach.getCoachId();
			}
			else {
				Student student = new Student();
				student.setName(bbsWeskit.getUserName());
				student.setSex(bbsWeskit.getSex().byteValue());
				student.setHeadIcon(bbsWeskit.getHeadIcon());
				student.setRegisterTime(new Date());
				studentManager.addStudent(student);
				if (student != null && student.getStudentId() != null) {
					userId = student.getStudentId();
				}
				else {
					student = studentManager.getStudentNear(student);
					userId = student.getStudentId();
				}
				logger.info("**************************************** studentId : " + userId);
			}
		}
		else {
			Student student = new Student();
			student.setName(bbsWeskit.getUserName());
			student.setSex(bbsWeskit.getSex().byteValue());
			student.setHeadIcon(bbsWeskit.getHeadIcon());
			student.setRegisterTime(new Date());
			userId = studentManager.addStudent(student);
			if (student != null && student.getStudentId() != null) {
				userId = student.getStudentId();
			}
			else {
				student = studentManager.getStudentNear(student);
				userId = student.getStudentId();
			}
			logger.info("**************************************** studentId : " + userId);
		}
		bbsWeskit.setUserId(userId);
		bbsWeskit.setUserType(userType);
		bbsWeskit.setCtime(new Date());
		return bbsWeckitMapper.insertSelective(bbsWeskit);
	}

	@Override
	public List<BbsBlacklist> getBlackList(BbsBlacklist bbsBlacklist, Integer pageSize, Integer pageIndex) {
		if (pageIndex != null && pageIndex != null ) {
			MyRowBounds myRowBounds = new MyRowBounds(pageIndex, pageSize);
			bbsBlacklist.setMyRowBounds(myRowBounds);
		}
		return bbsBlacklistMapper.getBbsBlacklistList(bbsBlacklist);
	}

	@Transactional
	@Override
	public int addBlacker(BbsBlacklist bbsBlacklist) {
		int r  = 0;
		BbsBlacklist  isBlack = bbsBlacklistMapper.queryBbsBlacklist(bbsBlacklist);
		if (isBlack != null ) {
			isBlack.setIsDel(0);
			bbsBlacklist.setMtime(new Date());
			r = bbsBlacklistMapper.updateByPrimaryKeySelective(bbsBlacklist);
		}
		else {
			bbsBlacklist.setCtime(new Date());
			r =  bbsBlacklistMapper.insertSelective(bbsBlacklist);
		}
		/**
		 * 更新缓存
		 */
		if (bbsBlacklist.getUserType() == 2) {
			redisUtil.delete(REDISKEY.BBS_STUDENT_BLACK_LIST);
			BbsBlacklist studentBlackList  = new BbsBlacklist();
			studentBlackList.setUserType(2);
			List<BbsBlacklist> blackList = bbsBlacklistMapper.getBbsBlacklistList(studentBlackList);
			if (blackList != null && blackList.size() != 0) {
				redisUtil.set(REDISKEY.BBS_STUDENT_BLACK_LIST, blackList);
			}
		}
		else {
			redisUtil.delete(REDISKEY.BBS_COACH_BLACK_LIST);
			BbsBlacklist coachBlackList  = new BbsBlacklist();
			coachBlackList.setUserType(1);
			List<BbsBlacklist> blackList = bbsBlacklistMapper.getBbsBlacklistList(coachBlackList);
			if (blackList != null && blackList.size() != 0) {
				redisUtil.set(REDISKEY.BBS_COACH_BLACK_LIST, blackList);
			}
		}
		
		return r;
	}

	@Transactional
	@Override
	public int banBlacker(BbsBlacklist bbsBlacklist) {
		bbsBlacklist.setMtime(new Date());
		int r =  bbsBlacklistMapper.updateByPrimaryKeySelective(bbsBlacklist);
		/**
		 * 更新缓存
		 */
		if (bbsBlacklist.getUserType() == 2) {
			redisUtil.delete(REDISKEY.BBS_STUDENT_BLACK_LIST);
			BbsBlacklist studentBlackList  = new BbsBlacklist();
			studentBlackList.setUserType(2);
			List<BbsBlacklist> blackList = bbsBlacklistMapper.getBbsBlacklistList(studentBlackList);
			if (blackList != null && blackList.size() != 0) {
				redisUtil.set(REDISKEY.BBS_STUDENT_BLACK_LIST, blackList);
			}
		}
		else {
			redisUtil.delete(REDISKEY.BBS_COACH_BLACK_LIST);
			BbsBlacklist coachBlackList  = new BbsBlacklist();
			coachBlackList.setUserType(1);
			List<BbsBlacklist> blackList = bbsBlacklistMapper.getBbsBlacklistList(coachBlackList);
			if (blackList != null && blackList.size() != 0) {
				redisUtil.set(REDISKEY.BBS_COACH_BLACK_LIST, blackList);
			}
		}
		
		return r;
	}

	@Override
	public BbsBlacklist getBBSBlacklist(BbsBlacklist bbsBlacklist) {
		return bbsBlacklistMapper.queryBbsBlacklist(bbsBlacklist);
	}

	@Override
	public BbsWord getBbsWord(BbsWord bbsWord) {
		return bbsWordMapper.queryBbsWord(bbsWord);
	}

	@Override
	public BbsTopic getBbsTopic(BbsTopic bbsTopic) {
		return bbsTopicMapper.queryBBSTopic(bbsTopic);
	}

	@Override
	public Integer countBbsSize(BbsMessage bbsMessage) {
		return bbsMapper.countBbsSize(bbsMessage);
	}

	@Override
	public Integer countBbsTopicSize(BbsTopic bbsTopic) {
		return bbsTopicMapper.countBbsTopicSize(bbsTopic);
	}

	@Override
	public Integer countBbsWordSize(BbsWord bbsWord) {
		return bbsWordMapper.countBbsWordSize(bbsWord);
	}

	@Override
	public Integer countBbsWeskitSize(BbsWeskit bbsWeskit) {
		return bbsWeckitMapper.countBbsWeskitSize(bbsWeskit);
	}

	@Override
	public Integer countBbsBlackSize(BbsBlacklist bbsBlacklist) {
		return bbsBlacklistMapper.countBbsBlackSize(bbsBlacklist);
	}

	@Override
	public Integer countBbsReplySize(BbsReply reply) {
		return bbsReplyMapper.countBbsReplySize(reply);
	}

	@Transactional
	@Override
	public Integer deleteReply(BbsReply bbsReply) {
		int a =  bbsReplyMapper.updateByPrimaryKeySelective(bbsReply);
		
		//删除评论 主贴-1
		BbsMessage bbsMessage = new BbsMessage();
		bbsMessage.setId(bbsReply.getBbsId());
		bbsMessage = bbsMapper.queryBBSDetail(bbsMessage);
		if (bbsMessage != null) {
			if (bbsMessage.getReply() != null && bbsMessage.getReply() > 0) {
				Bbs bbs = new Bbs();
				bbs.setId(bbsReply.getBbsId());
				bbsMapper.updateBBSReplySubtract(bbs);
			}
			
			Long sizeOld = redisUtil.zCard(REDISKEY.BBS_STUDENT_LIST);
			logger.info("***************************************updateBBSContentInfo sizeOld : " + sizeOld);
			
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
			String ctime = "";
			logger.info("**************************************** bbs : " + bbsMessage +", citme : "+ bbsMessage.getCtime());
			if (bbsMessage != null) {
				if (bbsMessage.getIsTopGlobal() != null && bbsMessage.getIsTopGlobal() > 0) {
					ctime = formatter.format(DateUtil.getNextTenYear(bbsMessage.getCtime()));
				}
				else if (bbsMessage.getIsTopGroup() != null && bbsMessage.getIsTopGroup() > 0) {
					ctime = formatter.format(DateUtil.getNextTenYear(bbsMessage.getCtime()));
				}
				else {
					ctime = formatter.format(bbsMessage.getCtime());
				}
			}
			try {
				Integer reply = bbsMessage.getReply();
				redisUtil.zRemove(REDISKEY.BBS_STUDENT_LIST,  bbsMessage);
				redisUtil.zRemoveByScore(REDISKEY.BBS_STUDENT_LIST, formatter.parse(ctime).getTime(), formatter.parse(ctime).getTime());
				
				if (reply!= null && reply > 0) {
					bbsMessage.setReply(reply -1 );
				}
				redisUtil.zAdd(REDISKEY.BBS_STUDENT_LIST, bbsMessage, formatter.parse(ctime).getTime());
			} catch (ParseException e) {
				logger.error("********************************* deleteReply Error : " + e.getMessage());
			}
			
			Long sizeNew = redisUtil.zCard(REDISKEY.BBS_STUDENT_LIST);
			logger.info("***************************************updateBBSContentInfo sizeNew : " + sizeNew);
			
			if (sizeOld != null && sizeNew != null && sizeOld >= 0 && sizeNew >= 0 && sizeNew !=sizeOld) {
				initBbsMessageList(150, 1);
				logger.info("***************************************updateBBSContentInfo delete BBS_STUDENT_LIST" );
			}
		}
		
		return  a;
	}

	@Override
	public BbsReply getReplyById(BbsReply bbsReply) {
		return bbsReplyMapper.selectByPrimaryKey(bbsReply.getId());
	}

	@Override
	public List<BbsWeskit> getUnPraiseWeskitList(BbsWeskit bbsWeskit, Integer pageSize, Integer pageIndex) {
		if (pageIndex != null && pageIndex != null ) {
			MyRowBounds myRowBounds = new MyRowBounds(pageIndex, pageSize);
			bbsWeskit.setMyRowBounds(myRowBounds);
		}
		return bbsWeckitMapper.queryUnPraiseWeskitList(bbsWeskit);
	}

	@Override
	public Integer countBbsUnPraiseWeskitSize(BbsWeskit bbsWeskit) {
		return bbsWeckitMapper.countBbsUnPraiseWeskitSize(bbsWeskit);
	}

	@Transactional
	@Override
	public Integer updateBbsUserInfo(Long userId, Integer userType, String name, String headIcon, String phoneNo) {
		
		if (StringUtil.isNotNullAndNotEmpty(name) || StringUtil.isNotNullAndNotEmpty(headIcon)) {
			//更新bbs表姓名及头像
			BbsMessage bbsMsg = new BbsMessage();
			bbsMsg.setUserId(userId);
			bbsMsg.setUserType(userType);
			List<BbsMessage> isBbs = bbsMapper.getBbsList(bbsMsg);
			if (isBbs != null && isBbs.size() > 0) {
				Bbs bbs = new Bbs();
				bbs .setUserName(name);
				bbs.setHeadIcon(headIcon);
				bbs.setUserId(userId);
				bbs.setUserType(userType);
				bbsMapper.updateBbsNameOrHeadIconByUser(bbs);
			}
			
			//更新评论姓名及头像
			BbsReply reply = new BbsReply();
			reply.setUserId(userId);
			reply.setUserType(userType);
			List<BbsReply> isReply = bbsReplyMapper.getReplyList(reply);
			if (isReply != null && isReply.size() > 0) {
				BbsReply bbsReply = new BbsReply();
				bbsReply.setUserId(userId);
				bbsReply.setUserType(userType);
				bbsReply.setHeadIcon(headIcon);
				bbsReply.setUserName(name);
				bbsReplyMapper.updateReplyUserInfo(bbsReply);
			}
			
			reply = new BbsReply();
			reply.setByreUserId(userId);
			reply.setByreUserType(userType);
			List<BbsReply> isReplyTwo = bbsReplyMapper.getReplyList(reply);
			if (isReplyTwo != null && isReplyTwo.size() > 0) {
				BbsReply replyTwo = new BbsReply();
				replyTwo.setByreUserId(userId);
				replyTwo.setByreUserName(name);
				replyTwo.setByreUserType(userType);
				bbsReplyMapper.updateReplyUserInfo(replyTwo);
			}
			
			reply = new BbsReply();
			reply.setBbsUserId(userId);
			reply.setBbsUserType(userType);
			List<BbsReply> isReplyBbs = bbsReplyMapper.getReplyList(reply);
			if (isReplyBbs != null && isReplyBbs.size() > 0) {
				BbsReply replyBbs = new BbsReply();
				replyBbs.setBbsUserId(userId);
				replyBbs.setBbsUserName(name);
				replyBbs.setBbsUserType(userType);
				bbsReplyMapper.updateReplyUserInfo(replyBbs);
			}
			
			//更新点赞姓名及头像
			BbsPraise bbsPraise = new BbsPraise();
			bbsPraise.setUserId(userId);
			bbsPraise.setUserType(userType);
			List<BbsPraise> isPraise = bbsPraiseMapper.getPraiseList(bbsPraise);
			if (isPraise != null){
				BbsPraise praise = new BbsPraise();
				praise.setUserId(userId);
				praise.setUserType(userType);
				praise.setUserName(name);
				praise.setHeadIcon(headIcon);
				bbsPraiseMapper.updatePraiseUserInfo(praise);
			}
			
			//更新举报姓名及头像
			BbsInform bbsInform = new BbsInform();
			bbsInform.setUserId(userId);
			bbsInform.setUserType(userType);
			List<BbsInform> informList = bbsInformMapper.queryInformList(bbsInform);
			if (informList != null && informList.size() > 0) {
				BbsInform inform = new BbsInform();
				inform.setUserId(userId);
				inform.setUserType(userType);
				inform.setHeadIcon(headIcon);
				inform.setUserName(name);
				bbsInformMapper.updateInformUserInfo(inform);
			}
		}
		
		if (StringUtil.isNotNullAndNotEmpty(name) || StringUtil.isNotNullAndNotEmpty(phoneNo)) {
			//更新黑名单姓名及头像
			BbsBlacklist black = new BbsBlacklist();
			black.setUserId(userId);
			black.setUserType(userType);
			BbsBlacklist isBlack = bbsBlacklistMapper.queryBbsBlacklist(black);
			if (isBlack != null) {
				black.setName(name);
				black.setPhoneNum(phoneNo);
				bbsBlacklistMapper.updateBlackUserInfo(black);
			}
		}
		
		return 1;
	}

	@Override
	public int updateTopicBbsNum(BbsTopic topic) {
		return bbsTopicMapper.updateTopicBbsNum(topic);
	}

	@Override
	public List<BbsReply> getBBSReplyMeList(BbsReply bbsReply, Integer pageSize, Integer pageIndex) {
		if (pageIndex != null && pageIndex != null ) {
			MyRowBounds myRowBounds = new MyRowBounds(pageIndex, pageSize);
			bbsReply.setMyRowBounds(myRowBounds);
		}
		return bbsReplyMapper.getReplyMeList(bbsReply);
	}


}
