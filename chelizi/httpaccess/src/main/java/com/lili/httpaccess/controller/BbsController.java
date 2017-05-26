package com.lili.httpaccess.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lili.bbs.service.IBBSService;
import com.lili.coach.service.CoachService;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;

/**
 * 社区控制层
 * @author lzb
 *
 */
@Controller
@RequestMapping("/v1/bbs")
public class BbsController {
	Logger log = Logger.getLogger(BbsController.class);
	
	@Autowired
	private IBBSService bbsService;
	
	@Autowired
	private CoachService coachService;
	
    /**
     * 获取首页及更多内容
     * @param userId 用户ID
     * @param userType 用户类型：1-教练；2-学员
     * @param pageSize 分页
     * @param pageIndex 分页
     * @param timestamp 
     * @param sign 
     * @return
     */
	@RequestMapping(value = "/getBBSList", method = RequestMethod.GET)
	public  Object bbsList(@RequestParam(required=false) String userId, @RequestParam String userType, @RequestParam String pageSize, @RequestParam String pageIndex,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();
		try {
			r = bbsService.getBBSList(userId, Integer.parseInt(userType), Integer.parseInt(pageSize), Integer.parseInt(pageIndex));
		} 
		catch (Exception e) {
			log.error("controller: BbsController bbsList  failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	
	/**
	 * 发布心情、话题
	 * @param userId 用户ID
	 * @param userType 用户类型：1- 教练；2-学员
	 * @param userName 用户名称
	 * @param headIcon 头像
	 * @param classify 分类：1-学员社区；2-教练社区；3-公共区域
	 * @param cityId 城市ID
	 * @param cityName 城市名
	 * @param topicId 话题id
	 * @param topicName 话题名称
	 * @param titleType 标题类型：1-心情；2-话题
	 * @param isDel 操作删除：0-正常；1-删除
	 * @param title 标题
	 * @param content 内容
	 * @param pic 图片
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/newTopic", method = RequestMethod.POST)
	public  Object newTopic(@RequestParam String userId, @RequestParam String userType,  @RequestParam(required=false) String userName,  @RequestParam(required=false) String headIcon, 			
			@RequestParam String classify,  @RequestParam(required=false) String cityId,  @RequestParam(required=false) String cityName, @RequestParam(required=false)  String topicId, 
			@RequestParam(required=false) String topicName, @RequestParam(required=false) String titleType, @RequestParam(required=false) String isDel,  
			@RequestParam(required=false) String title,  @RequestParam String content,@RequestParam(required=false) String pic, @RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();
		try {
			r = bbsService.newBBSContent(Long.parseLong(userId), Integer.parseInt(userType), userName, headIcon, Integer.parseInt(classify), cityId, cityName, topicId,  topicName, titleType, isDel, title, content, pic);
		} 
		catch (Exception e) {
			log.error("controller: BbsController newTopic  failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 删除帖子
	 * @param userId
	 * @param userType
	 * @param bbsId
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/delTopic", method = RequestMethod.POST)
	public  Object delTopic(@RequestParam String userId, @RequestParam String userType,  @RequestParam String bbsId, @RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();
		try {
			r = bbsService.delTopic(Long.parseLong(userId), Integer.parseInt(userType), Integer.parseInt(bbsId));
		} 
		catch (Exception e) {
			log.error("controller: BbsController newTopic  failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	
	/**
	 * 获取话题栏
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/getBBSTopic", method = RequestMethod.GET)
	public  Object bbsTopic(@RequestParam(required=false) String userId, @RequestParam String userType, 
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();
		try {
			r = bbsService.getBBSTopic(Integer.parseInt(userType));
		} 
		catch (Exception e) {
			log.error("controller: BbsController bbsTopic  failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 评论
	 * @param userId 用户ID
	 * @param userType 用户类型：1-教练；2-学员
	 * @param userName 用户名
	 * @param headIcon  头像
	 * @param bbsId  帖子ID
	 * @param cityId  城市ID
	 * @param cityName  城市
	 * @param byreId 二级回复帖子ID
	 * @param byreUserId 二级回复人ID
	 * @param byreUserName 二级回复人姓名
	 * @param byreUserType 二级回复人类型：1-教练；2-学员
	 * @param byreContent 二级原回复内容
	 * @param replyContent 回复内容
	 * @param replyUserId 被回复人
	 * @param replyUserType 被回复类型1-教练；2-学员
	 * @param opertionType 操作类型：1-评论；2-删除评论
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public  Object reply(@RequestParam String userId, @RequestParam String userType,  @RequestParam(required=false) String userName,  @RequestParam(required=false) String headIcon, @RequestParam String bbsId,
			@RequestParam(required=false)  String cityId,  @RequestParam(required=false) String cityName,  @RequestParam(required=false) String byreId,  @RequestParam(required=false) String byreUserId,  
			@RequestParam(required=false) String byreUserName, @RequestParam(required=false) String byreUserType, @RequestParam(required=false) String byreContent, @RequestParam String replyContent, 
			 @RequestParam(required=false) String replyUserId,@RequestParam(required=false) String replyUserType,@RequestParam(required=false) String opertionType, 
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();
		try {
			r = bbsService.reply(Long.parseLong(userId), Integer.parseInt(userType),userName, headIcon,  Integer.parseInt(bbsId), cityId, cityName, byreId, byreUserId, byreUserName, byreUserType, byreContent, replyContent, opertionType);
		} 
		catch (Exception e) {
			log.error("controller: BbsController reply  failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 点赞
	 * @param userId 用户ID
	 * @param userType 用户类型：1-教练；2-学员
	 * @param userName 用户名称
	 * @param headIcon 用户头像
	 * @param bbsId BBSID
	 * @param praiseUserId 被点赞的用户ID
	 * @param praiseUserType 被点赞用户类型：1-教练；2-学员
	 * @param isDel 操作： 0- 点赞；1-取消点赞
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/praise", method = RequestMethod.POST)
	public  Object praise(@RequestParam String userId, @RequestParam String userType,  @RequestParam(required=false) String userName,  @RequestParam(required=false) String headIcon, 			
			@RequestParam String bbsId,  @RequestParam(required=false)  String praiseUserId,  @RequestParam(required=false)  String praiseUserType, @RequestParam String isDel, 
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();
		try {
			r = bbsService.praise(Long.parseLong(userId), Integer.parseInt(userType), userName, headIcon, Integer.parseInt(bbsId), praiseUserId, praiseUserType, Integer.parseInt(isDel));
		} 
		catch (Exception e) {
			log.error("controller: BbsController praise  failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	

	/**
	 *  获取帖子详情
	 * 点赞列表显示4个，回复分页显示
	 * @param userId 用户ID
	 * @param userType 用户类型： 1- 教练；2-学员
	 * @param bbsId 帖子ID
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/getBBSDetailInfo", method = RequestMethod.GET)
	public  Object bbsDetailInfo(@RequestParam(required=false) String userId, @RequestParam String userType, @RequestParam String bbsId, 
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();
		try {
			r = bbsService.getBBSDetailInfo(userId, Integer.parseInt(userType), Integer.parseInt(bbsId));
		} 
		catch (Exception e) {
			log.error("controller: BbsController bbsDetailInfo  failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 *  获取回复列表
	 * 点赞列表显示4个，回复分页显示
	 * @param userId 用户ID
	 * @param userType 用户类型： 1- 教练；2-学员
	 * @param bbsId 帖子ID
	 * @param pageSize 分页
	 * @param pageIndex 分页
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/getBBSReplyList", method = RequestMethod.GET)
	public  Object bbsReplyList(@RequestParam(required=false) String userId, @RequestParam String userType, @RequestParam String bbsId, 
			@RequestParam String pageSize, @RequestParam String pageIndex,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();
		try {
			r = bbsService.getBBSReplyList(userId, Integer.parseInt(userType), Integer.parseInt(bbsId), Integer.parseInt(pageSize), Integer.parseInt(pageIndex));
		} 
		catch (Exception e) {
			log.error("controller: BbsController bbsDetailInfo  failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 获取点赞列表
	 * @param userId 用户ID
	 * @param userType 用户类型：1-教练；2-学员
	 * @param bbsId 帖子ID
	 * @param pageSize 分页
	 * @param pageIndex 分页
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/getBBSPraiseList", method = RequestMethod.GET)
	public  Object bbsPraiseList(@RequestParam(required=false) String userId, @RequestParam String userType, @RequestParam String bbsId, 
			@RequestParam String pageSize, @RequestParam String pageIndex,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();
		try {
			r = bbsService.getBBSPraiseList(userId, Integer.parseInt(userType), Integer.parseInt(bbsId), Integer.parseInt(pageSize), Integer.parseInt(pageIndex));
		} 
		catch (Exception e) {
			log.error("controller: BbsController bbsPraiseList  failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 举报帖子
	 * @param userId 用户ID
	 * @param userType 用户类型： 1- 教练；2 - 学员
	 * @param userName 用户名称
	 * @param headIcon 头像
	 * @param reason 举报原因
	 * @param bbsId 帖子ID
	 * @param title 标题
	 * @param content 内容
	 * @param isDel 操作类型：0-举报；1-取消举报
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/inform", method = RequestMethod.POST)
	public  Object inform(@RequestParam String userId, @RequestParam String userType,  @RequestParam(required=false) String userName,  @RequestParam(required=false) String headIcon, 		
			@RequestParam(required=false) String reason,@RequestParam String bbsId,  @RequestParam(required=false) String title,  @RequestParam(required=false) String content, @RequestParam(required=false) String isDel, 
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();
		try {
			r = bbsService.inform(Long.parseLong(userId), Integer.parseInt(userType), userName, headIcon, reason, Integer.parseInt(bbsId), title, content, isDel);
		} 
		catch (Exception e) {
			log.error("controller: BbsController inform  failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 我的帖子
	 * @param userId
	 * @param userType
	 * @param pageSize
	 * @param pageIndex
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/getMyBBSList", method = RequestMethod.GET)
	public  Object myBBSList(@RequestParam String userId, @RequestParam String userType, @RequestParam String pageSize, @RequestParam String pageIndex,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();
		try {
			r = bbsService.getMyBBSList(Long.parseLong(userId), Integer.parseInt(userType),  Integer.parseInt(pageSize),  Integer.parseInt(pageIndex));
		} 
		catch (Exception e) {
			log.error("controller: BbsController myBBSList  failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 消息中心：回复我的
	 * @param userId
	 * @param userType
	 * @param pageSize
	 * @param pageIndex
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/getMyBBSReplyList", method = RequestMethod.GET)
	public  Object myBBSReplyList(@RequestParam String userId, @RequestParam String userType, @RequestParam String pageSize, @RequestParam String pageIndex,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();
		try {
			r = bbsService.getMyBBSReplyList(Long.parseLong(userId), Integer.parseInt(userType), Integer.parseInt(pageSize),  Integer.parseInt(pageIndex));
		} 
		catch (Exception e) {
			log.error("controller: BbsController myBBSList  failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 *  消息中心：点赞我的
	 * @param userId
	 * @param userType
	 * @param pageSize
	 * @param pageIndex
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/getMyBBSPraiseList", method = RequestMethod.GET)
	public  Object myBBSPraiseList(@RequestParam String userId, @RequestParam String userType, @RequestParam String pageSize, @RequestParam String pageIndex,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();
		try {
			r = bbsService.getMyBBSPraiseList(Long.parseLong(userId), Integer.parseInt(userType), Integer.parseInt(pageSize),  Integer.parseInt(pageIndex));
		} 
		catch (Exception e) {
			log.error("controller: BbsController myBBSList  failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	

	/**
	 * 获取注册城市信息
	 * @param cityId
	 * @param cityName
	 * @param pid
	 * @param rlevel
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/getRegistCity", method = RequestMethod.GET)
	@ResponseBody
	public Object getRegistCity(@RequestParam(required = false) String cityId,@RequestParam(required = false) String cityName,
			@RequestParam(required = false) String pid,@RequestParam(required = false) String rlevel,
				@RequestParam String timestamp,@RequestParam String sign ) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = coachService.getRegistCity(cityId, cityName, pid, rlevel);
		} catch (Exception e) {
			log.error("SchoolController: get enroll list=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
}
