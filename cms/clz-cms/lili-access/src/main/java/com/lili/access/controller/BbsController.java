package com.lili.access.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lili.bbs.dto.Bbs;
import com.lili.bbs.dto.BbsBlacklist;
import com.lili.bbs.dto.BbsReply;
import com.lili.bbs.dto.BbsTopic;
import com.lili.bbs.dto.BbsWeskit;
import com.lili.bbs.dto.BbsWord;
import com.lili.bbs.vo.BbsMessage;
import com.lili.cms.constant.Constant;
import com.lili.cms.constant.MessageCode;
import com.lili.cms.entity.ResponseMessage;
import com.lili.coach.model.CoachBDTO;
import com.lili.student.model.StudentBDTO;
import com.lili.user.model.User;
import com.lili.user.service.ICmsBBSService;


@Controller
@Scope("prototype")
@RequestMapping("/bbs")
public class BbsController  extends BaseController {
	Logger logger = Logger.getLogger(BbsController.class);

	@Autowired
	ICmsBBSService cmsBbsService;
	
	/**
	 * 获取帖子列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getBBSList", method = RequestMethod.GET)
	@ResponseBody
	public Object getBBSList(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			BbsMessage bbsMessage = (BbsMessage) buildObject(request, BbsMessage.class);
			resp =  cmsBbsService.getBBSList(bbsMessage, request.getParameter("pageNo"), request.getParameter("pageSize"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 发帖
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/newBBS", method = RequestMethod.POST)
	@ResponseBody
	public Object newBBS(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			BbsMessage bbsMessage = (BbsMessage) buildObject(request, BbsMessage.class);
			resp =  cmsBbsService.newBBS(bbsMessage);
			
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			logger.info("********************************** CMS opention newBBS  for user : " + user.getAccount() + ", bbsId : " + bbsMessage.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 回复
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	@ResponseBody
	public Object reply(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			BbsReply bbsReply = (BbsReply) buildObject(request, BbsReply.class);
			
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			bbsReply.setCuid(user.getId());
			resp =  cmsBbsService.reply(bbsReply);
			
			logger.info("********************************** CMS opention reply  for user : " + user.getAccount() + ", bbsId : " + bbsReply.getBbsId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resp;
	}
	
	/**
	 * 编辑帖子
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/editBBSContent", method = RequestMethod.POST)
	@ResponseBody
	public Object editBBSContent(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			BbsMessage bbsMessage = (BbsMessage) buildObject(request, BbsMessage.class);
			logger.info("************************************* bbsMessage pic : " + bbsMessage.getPic());
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			bbsMessage.setMuid(user.getId());
			resp =  cmsBbsService.editBBSContent(bbsMessage);
			
			logger.info("********************************** CMS opention editBBSContent  for user : " + user.getAccount() + ", bbsId : " + bbsMessage.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 置顶- 话题置顶；全局置顶
	 * 屏蔽、解禁
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/stick", method = RequestMethod.POST)
	@ResponseBody
	public Object stick(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			Bbs bbs = (Bbs) buildObject(request, Bbs.class);
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			bbs.setMuid(user.getId());
			resp =  cmsBbsService.stick(bbs);
			
			logger.info("********************************** CMS opention stick  for user : " + user.getAccount() + ", bbsId : " + bbs.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	
	/**
	 * 获取帖子详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getBBSDetail", method = RequestMethod.GET)
	@ResponseBody
	public Object getBBSDetail(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			Integer bbsId = Integer.parseInt(request.getParameter("bbsId"));
			Integer userType = Integer.parseInt(request.getParameter("userType"));
			resp =  cmsBbsService.getBBSDetail(userType, bbsId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 获取帖子详情列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getBBSReplyList", method = RequestMethod.GET)
	@ResponseBody
	public Object getBBSReplyList(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			Integer bbsId = Integer.parseInt(request.getParameter("bbsId"));
			Integer userType = Integer.parseInt(request.getParameter("userType"));
			Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
			Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
			resp =  cmsBbsService.getBBSReplyList( request.getParameter("userId"), userType, bbsId, pageSize, pageNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 删除
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/replyDelete", method = RequestMethod.POST)
	@ResponseBody
	public Object replyDelete(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			BbsReply bbsReply = (BbsReply) buildObject(request, BbsReply.class);
			bbsReply.setMuid(user.getId());
			resp =  cmsBbsService.replyDelete(bbsReply);
			
			logger.info("********************************** CMS opention stick  for user : " + user.getAccount() + ", id : " + bbsReply.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 增加点赞数
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addPraise", method = RequestMethod.POST)
	@ResponseBody
	public Object addPraise(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);

			Integer bbsId = Integer.parseInt(request.getParameter("bbsId"));
			String praiseUserIdList = request.getParameter("praiseUserIdList");
			String userType = request.getParameter("userType");
			resp =  cmsBbsService.addPraise(bbsId, praiseUserIdList, userType);
			
			logger.info("********************************** CMS opention addPraise  for user : " + user.getAccount() + ", id : " + bbsId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 获取话题列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getTopicList", method = RequestMethod.GET)
	@ResponseBody
	public Object getTopicList(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			BbsTopic bbsTopic = (BbsTopic) buildObject(request, BbsTopic.class);
			resp =  cmsBbsService.getTopicList(bbsTopic, request.getParameter("pageNo"), request.getParameter("pageSize"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 创建话题
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/newTopic", method = RequestMethod.POST)
	@ResponseBody
	public Object newTopic(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			
			BbsTopic bbsTopic = (BbsTopic) buildObject(request, BbsTopic.class);
			if (bbsTopic != null && bbsTopic.getTopicName() == null && "".equals(bbsTopic.getTopicName())) {
				return new ResponseMessage(MessageCode.MSG_DATA_NONEXIST).build();
			}
			bbsTopic.setCuid(user.getId());
			bbsTopic.setCtime(new Date());
			resp =  cmsBbsService.newTopic(bbsTopic);
			
			logger.info("********************************** CMS opention newTopic  for user : " + user.getAccount() );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 编辑话题
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/editTopic", method = RequestMethod.POST)
	@ResponseBody
	public Object editTopic(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			
			BbsTopic bbsTopic = (BbsTopic) buildObject(request, BbsTopic.class);
			bbsTopic.setMuid(user.getId());
			bbsTopic.setMtime(new Date());
			resp =  cmsBbsService.editTopic(bbsTopic);
			
			logger.info("********************************** CMS opention editTopic  for user : " + user.getAccount() );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 屏蔽、禁言
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/shieldOrBanned", method = RequestMethod.POST)
	@ResponseBody
	public Object shieldOrBanned(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			
			BbsTopic bbsTopic = (BbsTopic) buildObject(request, BbsTopic.class);
			bbsTopic.setMuid(user.getId());
			bbsTopic.setMtime(new Date());
			resp =  cmsBbsService.shieldOrBanned(bbsTopic);
			
			logger.info("********************************** CMS opention shieldOrBanned  for user : " + user.getAccount() + ", bbsId :" + bbsTopic.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	


	/**
	 * 获取敏感词列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getWordList", method = RequestMethod.GET)
	@ResponseBody
	public Object getWordList(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			BbsWord bbsWord = (BbsWord) buildObject(request, BbsWord.class);
			resp =  cmsBbsService.getWordList(bbsWord, request.getParameter("pageNo"), request.getParameter("pageSize"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 添加敏感词
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addWord", method = RequestMethod.POST)
	@ResponseBody
	public Object addWord(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			
			BbsWord bbsWord = (BbsWord) buildObject(request, BbsWord.class);
			if (bbsWord.getWord() == null || "".equals(bbsWord.getWord())) {
				return new ResponseMessage(MessageCode.MSG_DATA_NONEXIST).build();
			}
			bbsWord.setCuid(user.getId());
			bbsWord.setCtime(new Date());
			resp =  cmsBbsService.addWord(bbsWord);
			
			logger.info("********************************** CMS opention delWord  for user : " + user.getAccount() );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 删除敏感词
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delWord", method = RequestMethod.POST)
	@ResponseBody
	public Object delWord(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			
			BbsWord bbsWord = (BbsWord) buildObject(request, BbsWord.class);
			bbsWord.setMuid(user.getId());
			bbsWord.setMtime(new Date());
			resp =  cmsBbsService.delWord(bbsWord);
			
			logger.info("********************************** CMS opention delWord  for user : " + user.getAccount());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 获取马甲列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getWeskitList", method = RequestMethod.GET)
	@ResponseBody
	public Object getWeskitList(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			BbsWeskit bbsWeskit = (BbsWeskit) buildObject(request, BbsWeskit.class);
			resp =  cmsBbsService.getWeskitList(bbsWeskit, request.getParameter("pageNo"), request.getParameter("pageSize"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 获取未点赞的马甲列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getUnPraiseWeskitList", method = RequestMethod.GET)
	@ResponseBody
	public Object getUnPraiseWeskitList(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			BbsWeskit bbsWeskit = (BbsWeskit) buildObject(request, BbsWeskit.class);
			Integer bbsId = Integer.parseInt(request.getParameter("bbsId"));
			bbsWeskit.setBbsId(bbsId);
			resp =  cmsBbsService.getUnPraiseWeskitList(bbsWeskit, request.getParameter("pageNo"), request.getParameter("pageSize"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 添加马甲号
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addWeskiter", method = RequestMethod.POST)
	@ResponseBody
	public Object addWeskiter(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			
			BbsWeskit bbsWeskit = (BbsWeskit) buildObject(request, BbsWeskit.class);
			bbsWeskit.setCuid(user.getId());
			bbsWeskit.setCtime(new Date());
			resp =  cmsBbsService.addWeskiter(bbsWeskit);
			
			logger.info("********************************** CMS opention addWeskiter  for user : " + user.getAccount() +", weskit : " + bbsWeskit.getUserName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 获取黑名单列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getBlackList", method = RequestMethod.GET)
	@ResponseBody
	public Object getBlackList(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			BbsBlacklist bbsBlacklist = (BbsBlacklist) buildObject(request, BbsBlacklist.class);
			resp =  cmsBbsService.getBlackList(bbsBlacklist, request.getParameter("pageNo"), request.getParameter("pageSize"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 添加黑名单
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addBlacker", method = RequestMethod.POST)
	@ResponseBody
	public Object addBlacker(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			
			BbsBlacklist bbsBlacklist = (BbsBlacklist) buildObject(request, BbsBlacklist.class);
			bbsBlacklist.setCuid(user.getId());
			bbsBlacklist.setCtime(new Date());
			resp =  cmsBbsService.addBlacker(bbsBlacklist);
			
			logger.info("********************************** CMS opention addBlacker  for user : " + user.getAccount() +", weskit : " + bbsBlacklist.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 解禁
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/banBlacker", method = RequestMethod.POST)
	@ResponseBody
	public Object banBlacker(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			
			BbsBlacklist bbsBlacklist = (BbsBlacklist) buildObject(request, BbsBlacklist.class);
			bbsBlacklist.setMuid(user.getId());
			bbsBlacklist.setMtime(new Date());
			resp =  cmsBbsService.banBlacker(bbsBlacklist);
			
			logger.info("********************************** CMS opention banBlacker  for user : " + user.getAccount() +", weskit : " + bbsBlacklist.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	
	@RequestMapping(value = "/getStudentList", method = RequestMethod.GET)
	@ResponseBody
	public Object getStudentList(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			StudentBDTO dto = (StudentBDTO) buildObject(request, StudentBDTO.class);
			resp =  cmsBbsService.getStudentList(dto, request.getParameter("pageNo"), request.getParameter("pageSize"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	@RequestMapping(value = "/getCoachList", method = RequestMethod.GET)
	@ResponseBody
	public Object getCoachList(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			CoachBDTO dto = (CoachBDTO) buildObject(request, CoachBDTO.class);
			resp =  cmsBbsService.getCoachList(dto, request.getParameter("pageNo"), request.getParameter("pageSize"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
}
