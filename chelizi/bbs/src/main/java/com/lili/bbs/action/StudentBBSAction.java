package com.lili.bbs.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.redisson.RedissonClient;
import org.redisson.core.RLock;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.lili.authcode.dto.Notice;
import com.lili.authcode.manager.NoticeManager;
import com.lili.bbs.dto.Bbs;
import com.lili.bbs.dto.BbsInform;
import com.lili.bbs.dto.BbsPraise;
import com.lili.bbs.dto.BbsReply;
import com.lili.bbs.dto.BbsTopic;
import com.lili.bbs.dto.BbsWeskit;
import com.lili.bbs.manager.IBBSManager;
import com.lili.bbs.vo.BbsDetailMessage;
import com.lili.bbs.vo.BbsMessage;
import com.lili.common.constant.JpushConstant;
import com.lili.common.constant.OrderConstant;
import com.lili.common.util.DateUtil;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.StringUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.util.redis.RedisKeys;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;

/**
 * 学员端处理
 * @author lzb
 *
 */
public class StudentBBSAction extends BBSAction {
	Logger logger = Logger.getLogger(StudentBBSAction.class);
	
    @Autowired
    private RedisUtil redisUtil;
    
	@Autowired
	IBBSManager bbsManager;
	
    @Resource(name="bbsRehandleProducer")
    DefaultMQProducer bbsRehandleProducer;
    
    @Resource(name = "jpushProducer")
  	DefaultMQProducer jpushProducer;
    
    @Autowired
    NoticeManager noticeManager;
    
    @Autowired
    private RedissonClient redissonClient;
	
	
	/**
	 * 获取更多心情
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<BbsMessage> getBBSList(String userId, Integer pageSize, Integer pageIndex) throws Exception {		
		Integer start = 0;
		Integer end = pageSize -1 ;
		if ("1".equals(pageIndex) || pageIndex == 1) {
			start = 0;
		}
		else {
			start = (pageIndex - 1) * pageSize;
		}
		List<BbsMessage> bbsList = redisUtil.zRevRange(REDISKEY.BBS_STUDENT_LIST, start, end);
		logger.info("************************************* bbsList : " + bbsList.size());
		if (bbsList == null || bbsList.size() == 0 || (bbsList != null &&  bbsList.size()%pageSize != 0)) { 
			Integer total = redisUtil.get(REDISKEY.BBS_SUM);
			if (total != null && total != bbsList.size()) {
				bbsList = bbsManager.getBBSList(new BbsMessage(), pageSize, pageIndex);
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
						Long sizeOld = redisUtil.zCard(REDISKEY.BBS_STUDENT_LIST);
		        		logger.info("***************************************doReleaseAction sizeOld : " + sizeOld);
		            	
		        		redisUtil.zRemove(REDISKEY.BBS_STUDENT_LIST,  bbsMsg);
		    			redisUtil.zRemoveByScore(REDISKEY.BBS_STUDENT_LIST, formatter.parse(ctime).getTime(), formatter.parse(ctime).getTime());
		            	
		            	Long sizeNew = redisUtil.zCard(REDISKEY.BBS_STUDENT_LIST);
		        		logger.info("***************************************doReleaseAction sizeNew : " + sizeNew);
		        		
		        		if (sizeOld != null && sizeNew != null && sizeOld == sizeNew) {
		        			logger.info("***************************************doReleaseAction delete BBS_STUDENT_LIST" );
		        			redisUtil.zRemove(REDISKEY.BBS_STUDENT_LIST,  bbsMsg);
			    			redisUtil.zRemoveByScore(REDISKEY.BBS_STUDENT_LIST, formatter.parse(ctime).getTime(), formatter.parse(ctime).getTime());
		        		}
						
						redisUtil.zAdd(REDISKEY.BBS_STUDENT_LIST, bbsMsg, formatter.parse(ctime).getTime()); //重新加一遍，覆盖原来的
						
					}
					bbsList = redisUtil.zRevRange(REDISKEY.BBS_STUDENT_LIST, start, end);
				}
			}
		}
		
		if (StringUtil.isNotNullAndNotEmpty(userId)) {
			if (bbsList != null && bbsList.size() > 0) {
				/*
				 *  判断当前用户是否有点赞
				 *  从当前用户点赞过的记录对比:效率会更快
				 */
				BbsPraise praise = new BbsPraise();
				praise.setUserId(Long.parseLong(userId));
				List<BbsPraise> praiseList = redisUtil.get(REDISKEY.BBS_STUDENT_PRAISE_LIST + userId);
				if (praiseList != null && praiseList.size() > 0) {
					for (BbsMessage bbsMsg : bbsList) {
						boolean isPraise = false;
						for (BbsPraise bbsPraise : praiseList) {
							if (bbsPraise.getBbsId().equals(bbsMsg.getId())) {
								isPraise = true;
								break;
							}
						}
						if (isPraise) {
							bbsMsg.setIsPraise(1);
						}
						else {
							bbsMsg.setIsPraise(0);
						}
					}
				}
			}
		}
		return bbsList;
	}
	
	/**
	 * 发布心情
	 * @param bbsMsg
	 * @return
	 * @throws Exception
	 */
	@Override
	public ReqResult doReleaseAction(BbsMessage bbsMsg) throws Exception {
		ReqResult r = ReqResult.getSuccess();
		
		try {
			// 插入缓存
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
			String ctime = formatter.format(date);
			bbsMsg.setCtime(TimeUtil.parseDate(ctime, "yyyy-MM-dd HH:mm:ss SSS"));
			
			 RLock lock = null;
			Integer bbsId = redisUtil.get(REDISKEY.BBS_ID);
			if (bbsId == null) { //第一次的时候
				bbsId = bbsManager.getMaxBBSId();
				if (bbsId == null) {
					bbsId = 0;
				}
			}
			else {
				bbsId = redisUtil.get(REDISKEY.BBS_ID);
			}
			lock = redissonClient.getLock(RedisKeys.REDISKEY.BBS_ID + ".get." + bbsId);
            boolean hasLock = lock.tryLock(1, 10, TimeUnit.SECONDS);//1s等待，10s超时，防止死锁

            if (hasLock) {
            	bbsMsg.setId(bbsId + 1); 
            	
            	BbsWeskit bbsWeskit = new BbsWeskit();
				bbsWeskit.setUserId(bbsMsg.getUserId());
				bbsWeskit.setUserType(bbsMsg.getUserType());
				BbsWeskit isWeckit = bbsManager.isWeskit(bbsWeskit);
				if (isWeckit != null) {
					if (isWeckit.getChannel() == 3) {
						bbsMsg.setChannel(3); //如果是管理员
					}
					else {
						bbsMsg.setChannel(2); //如果是马甲
					}
				}
            	
            	Long sizeOld = redisUtil.zCard(REDISKEY.BBS_STUDENT_LIST);
        		logger.info("***************************************doReleaseAction sizeOld : " + sizeOld);
            	
            	redisUtil.zAdd(REDISKEY.BBS_STUDENT_LIST, bbsMsg, formatter.parse(ctime).getTime());
            	
            	Long sizeNew = redisUtil.zCard(REDISKEY.BBS_STUDENT_LIST);
        		logger.info("***************************************doReleaseAction sizeNew : " + sizeNew);
        		
        		if (sizeOld != null && sizeNew != null && sizeOld == sizeNew) {
        			logger.info("***************************************doReleaseAction delete BBS_STUDENT_LIST" );
        			initBbsMessageList(150, 1);
        		}
            	
            	/**
            	 * 发送到消息队列，异步处理到持久DB层
            	 */
            	Message msg=new Message();
            	msg.setTopic(bbsRehandleProducer.getCreateTopicKey());
            	msg.setTags(OrderConstant.RMQTAG.BBS_TOPIC_SEND);
            	msg.setBody(SerializableUtil.serialize(bbsMsg));
            	bbsRehandleProducer.send(msg);
            	logger.info("***************************************** bbsProducer has sent a message.  bbs: "+ bbsMsg);
            	
            	redisUtil.delete(REDISKEY.BBS_ID + ".get." + bbsId);
            } else {
              r.setCode(ResultCode.ERRORCODE.ORDER_LOCK);
              r.setMsgInfo(ResultCode.ERRORINFO.ORDER_LOCK);
          }
            
           
		} 
		catch (Exception e) {
			logger.error("***************************************** StudentBBSAction doReleaseAction Exception : " +  e.getMessage());
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.BBS_OPEN_CONTENT_ERROR);
			r.setMsgInfo(ResultCode.ERRORINFO.BBS_OPEN_CONTENT_ERROR);
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
	 * 回复
	 * @param bbsReply
	 * @return
	 * @throws Exception
	 */
	@Override
	public ReqResult doReplyAction(BbsReply bbsReply) throws Exception {
		ReqResult r = ReqResult.getSuccess();
		
		BbsMessage bs = new BbsMessage();
		bs.setId(bbsReply.getBbsId());
		BbsMessage bbsMessage = bbsManager.getBBSMessage(bs);
		if (bbsMessage != null) {
			/**
			 *  如果是马甲，马甲回复数+1
			 *  如果是用户，刷新缓存，插入评论
			 */
			BbsWeskit bbsWeskit = new BbsWeskit();
			bbsWeskit.setUserId(bbsReply.getUserId());
			bbsWeskit.setUserType(2);
			BbsWeskit isWeckit = bbsManager.isWeskit(bbsWeskit);
			if (isWeckit != null) {
				if (isWeckit.getChannel() == 3) {
					bbsReply.setChannel(3); //如果是管理员
				}
				else {
					bbsReply.setChannel(2); //如果是马甲
				}
				isWeckit.setReplyNum(isWeckit.getReplyNum() +1);
				bbsManager.updateBBSWeskit(isWeckit);
			}
			else {
				bbsReply.setChannel(1);
			}
			
			// 更新缓存，回复+1
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
			String ctime = "";
			if (bbsMessage.getIsTopGlobal() > 0 || bbsMessage.getIsTopGroup() > 0) {
				ctime = formatter.format(DateUtil.getNextTenYear(bbsMessage.getCtime()));
			}
			else {
				ctime = formatter.format(bbsMessage.getCtime());
			}
			
			Long sizeOld = redisUtil.zCard(REDISKEY.BBS_STUDENT_LIST);
			logger.info("***************************************doReplyAction sizeOld : " + sizeOld);
			
			redisUtil.zRemove(REDISKEY.BBS_STUDENT_LIST,  bbsMessage);
			redisUtil.zRemoveByScore(REDISKEY.BBS_STUDENT_LIST, formatter.parse(ctime).getTime(), formatter.parse(ctime).getTime());
			
			Integer reply = bbsMessage.getReply()+1;
			bbsMessage.setReply(reply);
			redisUtil.zAdd(REDISKEY.BBS_STUDENT_LIST, bbsMessage, formatter.parse(ctime).getTime());
			
			Long sizeNew = redisUtil.zCard(REDISKEY.BBS_STUDENT_LIST);
			logger.info("***************************************doReplyAction sizeNew : " + sizeNew);
			
			if (bbsReply.getByreId() != null && bbsReply.getByreId() > 0) {
				BbsReply twoReply = new BbsReply();
				twoReply.setId(bbsReply.getByreId());
				BbsReply replyTwoInfo = bbsManager.getReplyById(twoReply);
				
				if (replyTwoInfo != null) {
					bbsReply.setByreUserId(replyTwoInfo.getUserId());
					bbsReply.setByreUserType(replyTwoInfo.getUserType());
					bbsReply.setByreUserName(replyTwoInfo.getUserName());
					bbsReply.setByreContent(replyTwoInfo.getReplyContent());
				}
			}
			// 如果被回复的是发帖人
			bbsReply.setBbsUserId(bbsMessage.getUserId());
			bbsReply.setBbsUserType(bbsMessage.getUserType());
			bbsReply.setBbsUserName(bbsMessage.getUserName());
			
			//如果被回复的是
			
			bbsReply.setContent(bbsMessage.getContent());
			
			// 保存回复
			bbsManager.saveBBSReply(bbsReply);
			
			// 主贴评论+1
			Bbs bbs = new Bbs();
			bbs.setId(bbsReply.getBbsId());
			bbsManager.updateBBSReply(bbs);
			
			if (sizeOld != null && sizeNew != null && sizeOld >= 0 && sizeNew>= 0 && sizeNew !=sizeOld) {
				initBbsMessageList(150, 1);
				logger.info("***************************************doReplyAction delete BBS_STUDENT_LIST  " );
			}
			// 保存到消息中心
			Notice notice = new Notice();
			if (bbsReply.getByreId() != null && bbsReply.getByreId() > 0) { 
				notice.setTitle("有人回复了你的评论");
				notice.setContent("有人回复了你的评论");
			}
			else {
				notice.setTitle("有人回复了你的帖子");
				notice.setContent("有人回复了你的帖子");
			}
			notice.setUserType((byte)2);
			notice.setType((byte) 6);  
			notice.setTime(new Date());
			
			// 推送给发帖人
			try {
				if (bbsReply.getByreId() != null && bbsReply.getByreId() > 0) { // 推送给一级回复人
					if (!bbsReply.getUserId().equals(bbsReply.getByreUserId())) { // 回复人及一级回复人是否同一人
						notice.setUserId(bbsReply.getByreUserId());
						
						JpushMsg jmsg = new JpushMsg();
						jmsg.setAlter(bbsReply.getUserName() + "回复了你的评论！");
						jmsg.setUserId(bbsReply.getByreUserId()); // 推送给一级回复人
						jmsg.setOrderId(bbsReply.getByreUserId() + JpushConstant.OPERATE.BBS_STU_REPLY);
						jmsg.setOperate(JpushConstant.OPERATE.BBS_STU_REPLY);
						Message jpush = new Message();
						jpush.setKeys(bbsReply.getByreUserId() + JpushConstant.OPERATE.BBS_STU_REPLY);
						jpush.setTopic(jpushProducer.getCreateTopicKey());
						jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
						jpush.setBody(SerializableUtil.serialize(jmsg));
						jpushProducer.send(jpush);
						noticeManager.addNotice(notice);
					}
				}
				else { //推送给一级发帖人
					if (!bbsMessage.getUserId().equals(bbsReply.getUserId())) { // 发帖人及回复人是否同一人
						notice.setUserId(bbsMessage.getUserId());
						
						JpushMsg jmsg = new JpushMsg();
						jmsg.setAlter(bbsReply.getUserName() + "回复了你的帖子！");
						jmsg.setUserId(bbsMessage.getUserId());
						jmsg.setOrderId(bbsMessage.getUserId() + JpushConstant.OPERATE.BBS_STU_REPLY);
						jmsg.setOperate(JpushConstant.OPERATE.BBS_STU_REPLY);
						Message jpush = new Message();
						jpush.setKeys(bbsMessage.getUserId() + JpushConstant.OPERATE.BBS_STU_REPLY);
						jpush.setTopic(jpushProducer.getCreateTopicKey());
						jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
						jpush.setBody(SerializableUtil.serialize(jmsg));
						jpushProducer.send(jpush);
						noticeManager.addNotice(notice);
					}
				}
			}
			catch (Exception e) {
				logger.error("**********************************　StudentBBSAction Reply opertion Jpush Error!, Exception : " +  e.getMessage());
			}
		}
		else {
			r.setCode(ResultCode.ERRORCODE.BBS_IS_NOT_EXIT);
			r.setMsgInfo(ResultCode.ERRORINFO.BBS_IS_NOT_EXIT);
		}
		
		return r;
	}

	/**
	 * 点赞
	 * @param bbsPraise
	 * @return
	 * @throws Exception
	 */
	@Override
	public ReqResult doPraiseAction(BbsPraise bbsPraise) throws Exception {
		ReqResult r = ReqResult.getSuccess();
		
		BbsMessage bs = new BbsMessage();
		bs.setId(bbsPraise.getBbsId());
		BbsMessage bbsMessage = bbsManager.getBBSMessage(bs);
		if (bbsMessage != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
			String ctime = "";
			if (bbsMessage.getIsTopGlobal() > 0 || bbsMessage.getIsTopGroup() > 0) {
				ctime = formatter.format(DateUtil.getNextTenYear(bbsMessage.getCtime()));
			}
			else {
				ctime = formatter.format(bbsMessage.getCtime());
			}
			
			// 更新缓存，点赞+1
			Long sizeOld = redisUtil.zCard(REDISKEY.BBS_STUDENT_LIST);
			logger.info("***************************************doPraiseAction sizeOld : " + sizeOld);
			
			redisUtil.zRemove(REDISKEY.BBS_STUDENT_LIST,  bbsMessage);
			redisUtil.zRemoveByScore(REDISKEY.BBS_STUDENT_LIST, formatter.parse(ctime).getTime(), formatter.parse(ctime).getTime());
			
			Integer praise = bbsMessage.getPraise() + 1;
			bbsMessage.setPraise(praise);
			redisUtil.zAdd(REDISKEY.BBS_STUDENT_LIST, bbsMessage, formatter.parse(ctime).getTime());
			
			Long sizeNew =  redisUtil.zCard(REDISKEY.BBS_STUDENT_LIST);
			logger.info("***************************************doPraiseAction sizeNew : " + sizeNew);
			
			// 点赞表插或更新记录 bbs主表+1
			bbsPraise.setPraiseUserId(bbsMessage.getUserId());
			bbsPraise.setPraiseUserType(bbsMessage.getUserType());
			bbsPraise.setPraiseContent(bbsMessage.getContent());
			bbsManager.saveBBSPraise(bbsPraise);
			
			redisUtil.delete(REDISKEY.BBS_STUDENT_PRAISE_LIST + bbsPraise.getUserId());
			BbsPraise userpraise = new BbsPraise();
			userpraise.setUserId(bbsPraise.getUserId());
			List<BbsPraise> praiseList = bbsManager.getBBSPraiseList(userpraise, null, null);
			redisUtil.set(REDISKEY.BBS_STUDENT_PRAISE_LIST + bbsPraise.getUserId(), praiseList);
			
			if (sizeOld != null && sizeNew != null && sizeOld >= 0 && sizeNew >= 0 && sizeOld !=sizeNew) {
				initBbsMessageList(150, 1);
				logger.info("***************************************doPraiseAction delete BBS_STUDENT_LIST" );
			}
			
			Notice notice = new Notice();
			notice.setTitle("有人点赞了你的帖子");
			notice.setUserId(bbsMessage.getUserId());
			notice.setUserType((byte)2);
			notice.setType((byte) 6);  
			notice.setContent("有人点赞了你的帖子");
			notice.setTime(new Date());
			noticeManager.addNotice(notice);
			
			// 推送给被点赞人
			if (!bbsMessage.getUserId().equals(bbsPraise.getUserId())) {
				try {
					JpushMsg jmsg = new JpushMsg();
					jmsg.setAlter(bbsPraise.getUserName() +"点赞了你的帖子！");
					jmsg.setUserId(bbsMessage.getUserId());
					jmsg.setOrderId(bbsMessage.getUserId() + JpushConstant.OPERATE.BBS_STU_PRAISE);
					jmsg.setOperate(JpushConstant.OPERATE.BBS_STU_PRAISE);
					Message jpush = new Message();
					jpush.setKeys(bbsMessage.getUserId()+JpushConstant.OPERATE.BBS_STU_PRAISE);
					jpush.setTopic(jpushProducer.getCreateTopicKey());
					jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
					jpush.setBody(SerializableUtil.serialize(jmsg));
					jpushProducer.send(jpush);
				}
				catch (Exception e) {
					logger.error("*************************************** doPraiseAction Exception :  " +  e.getMessage() );
					e.printStackTrace();
				}
			}
		}
		return r;
	}

	/**
	 * 获取帖子详情
	 */
	@Override
	public ReqResult getBbsDetail(BbsDetailMessage bbsDetailMessage, String userId) throws Exception {
		ReqResult r = ReqResult.getSuccess();
		BbsDetailMessage bbsDetailMsg = bbsManager.getBBSDetail(bbsDetailMessage, userId);
		if (bbsDetailMsg != null) {
			r.setData(bbsDetailMsg);
		}
		else {
			initBbsMessageList(150, 1);
			r.setCode(ResultCode.ERRORCODE.BBS_IS_NOT_EXIT);
			r.setMsgInfo(ResultCode.ERRORINFO.BBS_IS_NOT_EXIT);
			return r;
		}
		return r;
	}

	/**
	 * 获取回复列表
	 */
	@Override
	public ReqResult getBBSReplyList(BbsReply reply, Integer pageSize, Integer pageIndex) throws Exception {
		ReqResult r = ReqResult.getSuccess();
		List<BbsReply> bbsReplyMsg = bbsManager.getBBSReplyList(reply, pageSize, pageIndex);
		if (bbsReplyMsg != null && bbsReplyMsg.size() > 0) {
			r.setData(bbsReplyMsg);
		}
		return r;
	}

	/**
	 * 获取点赞列表
	 */
	@Override
	public ReqResult getBBSPraiseList(BbsPraise praise, Integer pageSize, Integer pageIndex) throws Exception {
		ReqResult r = ReqResult.getSuccess();
		List<BbsPraise> bbsDetailMsg = bbsManager.getBBSPraiseList(praise, pageSize, pageIndex);
		if (bbsDetailMsg != null && bbsDetailMsg.size() > 0) {
			r.setData(bbsDetailMsg);
		}
		return r;
	}

	/**
	 * 举报
	 */
	@Override
	public ReqResult informTopic(BbsInform inform) throws Exception {
		ReqResult r = ReqResult.getSuccess();
		
		/**
		 * 判断是否已举报
		 */
		BbsInform isInform = bbsManager.getInformInfo(inform);
		if (isInform != null) {
			r.setCode(ResultCode.ERRORCODE.BBS_IS_INFORM);
			r.setMsgInfo(ResultCode.ERRORINFO.BBS_IS_INFORM);
			return r;
		}
		
		//判断帖子是否还存在
		Bbs bbs = new Bbs();
		bbs.setId(inform.getBbsId());
		bbs.setIsInform(1);
		bbs.setIsDel(0);
		Bbs isExitBbs  = bbsManager.getBBSInfo(bbs);
		if (isExitBbs == null) {
			r.setCode(ResultCode.ERRORCODE.BBS_IS_NOT_EXIT);
			r.setMsgInfo(ResultCode.ERRORINFO.BBS_IS_NOT_EXIT);
			return r;
		}
		
		/**
		 * 主帖子+1
		 * 举报详情插入
		 */
		bbsManager.updateBBSInform(bbs);
		bbsManager.saveBBSInform(inform);
		
		return r;
	}

	/**
	 * 我的帖子
	 */
	@Override
	public ReqResult getMyBBSList(BbsMessage bmsg, Integer pageSize, Integer pageIndex) throws Exception {
		ReqResult r = ReqResult.getSuccess();
		bmsg.setIsDel(0);
		List<BbsMessage> bmsgList = bbsManager.getMyBBSList(bmsg, pageSize, pageIndex);
		if (bmsgList != null && bmsgList.size() > 0) {
			r.setData(bmsgList);
		}
		return r;
	}

	/**
	 * 回复我的
	 */
	@Override
	public ReqResult getMyBBSReplyList(Long userId, Integer userType, Integer pageSize, Integer pageIndex)
			throws Exception {
		ReqResult r = ReqResult.getSuccess();
		BbsReply bbsReply = new BbsReply();
		bbsReply.setBbsUserId(userId);
		bbsReply.setBbsUserType(userType);
		bbsReply.setByreUserId(userId);
		bbsReply.setByreUserType(userType);
		bbsReply.setIsNotice(1);
		List<BbsReply> replyList = bbsManager.getBBSReplyMeList(bbsReply, pageSize, pageIndex);
		if (replyList != null && replyList.size() > 0) {
			r.setData(replyList);
		}
		return r;
	}

	/**
	 * 点赞我的
	 */
	@Override
	public ReqResult getMyBBSPraiseList(Long userId, Integer userType, Integer pageSize, Integer pageIndex)
			throws Exception {
		ReqResult r = ReqResult.getSuccess();
		
		BbsPraise bbsPraise = new BbsPraise();
		bbsPraise.setPraiseUserId(userId);
		bbsPraise.setPraiseUserType(userType);
		bbsPraise.setIsNotice(1);
		List<BbsPraise> praiseList =bbsManager.getBBSPraiseList(bbsPraise, pageSize, pageIndex);
		if (praiseList != null && praiseList.size() > 0) {
			r.setData(praiseList);
		}
		return r;
	}

	/**
	 * 获取话题
	 */
	@Override
	public List<BbsTopic> getBBSTopic() throws Exception {
		List<BbsTopic> bbsList = redisUtil.get(REDISKEY.BBS_STUDENT_TOPIC_LIST);
		if (bbsList == null || bbsList.size() == 0) {
			BbsTopic bbsTopic = new BbsTopic();
			bbsTopic.setUserType(2);
			bbsList = bbsManager.getBBSTopicList(bbsTopic, null, null);
			if (bbsList != null && bbsList.size() > 0 ) {
				redisUtil.set(REDISKEY.BBS_STUDENT_TOPIC_LIST, bbsList);
			}
		}
		return bbsList;
	}

}
