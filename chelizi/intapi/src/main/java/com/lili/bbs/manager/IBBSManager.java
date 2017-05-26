package com.lili.bbs.manager;

import java.util.List;

import com.lili.bbs.dto.Bbs;
import com.lili.bbs.dto.BbsBlacklist;
import com.lili.bbs.dto.BbsInform;
import com.lili.bbs.dto.BbsPraise;
import com.lili.bbs.dto.BbsReply;
import com.lili.bbs.dto.BbsTopic;
import com.lili.bbs.dto.BbsWeskit;
import com.lili.bbs.dto.BbsWord;
import com.lili.bbs.vo.BbsDetailMessage;
import com.lili.bbs.vo.BbsMessage;

public interface IBBSManager {

	/**
	 * 获取分页数据（N条）
	 * 分页数据放缓存，首页数据从第一页10条获取，客户端自行判断要拿多少条
	 * N条之后数据从DB里获取（考虑效率问题，会不会慢）
	 * @param pageIndex 
	 * @param pageSize 
	 * @param bbs
	 * @return
	 */
	List<BbsMessage> getBBSList(BbsMessage bbsMessage, Integer pageSize, Integer pageIndex);
	
	List<BbsMessage> getMyBBSList(BbsMessage bbsMessage, Integer pageSize, Integer pageIndex);
	
	List<BbsMessage> getCMSBbsList(BbsMessage bbsMessage, Integer pageSize, Integer pageIndex);
	
	/**
	 * 获取帖子详情
	 * 点赞列表显示4个，回复分页显示
	 * @param userId 
	 * @param bbs
	 * @return
	 */
	BbsDetailMessage getBBSDetail(BbsDetailMessage bbsDetailMessage, String userId);
	
	/**
	 * 发帖
	 * 如果是马甲号，马甲发帖数+1
	 * 如果是用户，刷新缓存
	 * @param bbs
	 */
	void addPraiseOne(BbsMessage bbsMessage);
	
	
	/**
	 * 处理：删除、屏蔽、举报
	 * 仅发帖人可以删除，删除后所有人不可见，逻辑删除，isdel=1
	 * 仅CMS可操作屏蔽，屏蔽后仅发帖自己可见，我的帖子里获取  status = 1
	 * 举报所有人可见：举报数+1，status = 2
	 * CMS操作屏蔽时，判断用户一周内屏蔽次数，超过4次禁言
	 * @param bbs
	 */
	void handleBbs(Bbs bbs);
	
	/**
	 * 获取点赞列表、获取点赞我的列表
	 * 分页处理
	 * @param bbsPraise
	 * @param pageIndex 
	 * @param pageSize 
	 * @return
	 */
	List<BbsPraise> getBBSPraiseList(BbsPraise bbsPraise, Integer pageSize, Integer pageIndex);
	
	/**
	 * 点赞处理
	 * bbs主表+1
	 * 点赞表插或更新记录
	 * @param bbsPraise
	 */
	void saveBBSPraise(BbsPraise bbsPraise);
	
	/**
	 * 取消点赞
	 * bbs主表-1
	 * 点赞表更新记录
	 * @param bbsPraise
	 */
	void updateBBSPraise(BbsPraise bbsPraise);
	
	/**
	 * 获取回复列表
	 * 分页处理
	 * @param bbsReply
	 * @param pageIndex 
	 * @param pageSize 
	 * @return
	 */
	List<BbsReply> getBBSReplyList(BbsReply bbsReply, Integer pageSize, Integer pageIndex);
	
	/**
	 * 增加回复
	 * 如果是马甲，马甲回复数+1
	 * 如果是用户，刷新缓存
	 * @param bbsReply
	 */
	void saveBBSReply(BbsReply bbsReply);
	
	/**
	 * 处理：删除、屏蔽、举报
	 * 删除后所有人不可见，逻辑删除，isdel=1
	 * 屏蔽后仅自己可见，我的回复里获取  status = 1
	 * 举报所有人可见：举报数+1，status = 2
	 * @param bbs
	 */
	void handleBBSReply(BbsReply bbsReply);
	
	/**
	 * 查询黑名单
	 * 分页
	 * @param bbsBlacklist
	 * @param object2 
	 * @param object 
	 * @return
	 */
	List<BbsBlacklist> getBBSBlacklistList(BbsBlacklist bbsBlacklist, Integer pageSize, Integer pageIndex);
	
	/**
	 * 添加黑名单
	 * @param bbsBlacklist
	 */
	void saveBBSBlacklist(BbsBlacklist bbsBlacklist);
	
	/**
	 *更新黑名单 
	 * @param bbsBlacklist
	 */
	void updateBBSBlacklist(BbsBlacklist bbsBlacklist);

	List<BbsWord> getBBSWordList(BbsWord bbsWord);

	void saveBBSTopicInfo(BbsMessage bbsMessage) throws Exception;
	
	void updateBBSContentInfo(BbsMessage bbsMessage) throws Exception;

	BbsWeskit isWeskit(BbsWeskit bbsWeckit);

	void updateBBSWeskit(BbsWeskit bbsWeckit);

	void updateBBSInform(Bbs bbs);

	void saveBBSInform(BbsInform inform);

	List<BbsTopic> getBBSTopicList(BbsTopic bbsTopic, Integer pageSize, Integer pageIndex);

	Bbs getBBSInfo(Bbs bbs);

	void updateBBSRecord(Bbs bbsInfo);

	Integer getMaxBBSId();

	Integer countIsPraiseByUser(BbsPraise bbsPraise);

	BbsMessage getBBSMessage(BbsMessage bbsMsg);

	BbsPraise getPraiseInfo(BbsPraise bbsPraise);

	BbsInform getInformInfo(BbsInform inform);

	BbsReply getReplyInfo(BbsReply twoReply);

	void updateBBSReply(Bbs bbs);

	int updateBBSOnly(Bbs bbs);
	
	int newTopic(BbsTopic bbsTopic);
	
	int updateTopic(BbsTopic bbsTopic);
	
	List<BbsWord> getWordList(BbsWord bbsWord,Integer pageSize, Integer pageIndex);
	
	int addWord(BbsWord bbsWord);

	int delWord(BbsWord bbsWord);
	
	List<BbsWeskit> getBbsWeskit(BbsWeskit bbsWeskit, Integer pageSize, Integer pageIndex);
	
	int addWeskiter(BbsWeskit bbsWeskit);
	
	List<BbsBlacklist> getBlackList(BbsBlacklist bbsBlacklist, Integer pageSize, Integer pageIndex);
	
	int addBlacker(BbsBlacklist bbsBlacklist);

	int banBlacker(BbsBlacklist bbsBlacklist);

	BbsBlacklist getBBSBlacklist(BbsBlacklist bbsBlacklist);

	BbsWord getBbsWord(BbsWord bbsWord);

	BbsTopic getBbsTopic(BbsTopic bbsTopic);

	Integer countBbsSize(BbsMessage bbsMessage);

	Integer countBbsTopicSize(BbsTopic bbsTopic);

	Integer countBbsWordSize(BbsWord bbsWord);

	Integer countBbsWeskitSize(BbsWeskit bbsWeskit);

	Integer countBbsBlackSize(BbsBlacklist bbsBlacklist);

	Integer countBbsReplySize(BbsReply reply);
	
	Integer deleteReply(BbsReply bbsReply);

	BbsReply getReplyById(BbsReply bbsReply);
	
	List<BbsWeskit> getUnPraiseWeskitList(BbsWeskit bbsWeskit, Integer pageSize, Integer pageIndex);

	Integer countBbsUnPraiseWeskitSize(BbsWeskit bbsWeskit);

	Integer updateBbsUserInfo(Long userId, Integer userType, String name, String headIcon, String phoneNo);

	int updateTopicBbsNum(BbsTopic topic);

	List<BbsReply> getBBSReplyMeList(BbsReply bbsReply, Integer pageSize, Integer pageIndex);
}
