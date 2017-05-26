package com.lili.access.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lili.access.util.AccessWebUtil;
import com.lili.cms.constant.Constant;
import com.lili.cms.entity.ResponseMessage;
import com.lili.cms.util.EncryptUtil;
import com.lili.common.util.redis.RedisKeys;
import com.lili.common.util.redis.RedisUtil;
import com.lili.exam.dto.ExamPlace;
import com.lili.exam.manager.ExamPlaceManager;
import com.lili.user.model.User;
import com.lili.user.service.CMSUserService;

@Controller
@Scope("prototype")
@RequestMapping("/user")
public class UserController extends BaseController{

	@Autowired
	private CMSUserService cmsUserService;
	@Autowired
	private ExamPlaceManager examPlaceManager;
	@Autowired
	RedisUtil redisUtil;
	

	@RequestMapping("/verify")
	@ResponseBody
    public String verify(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute(Constant.USER_SESSION);
		long schoolId = user.getSchoolId();
		ExamPlace ep = redisUtil.get(RedisKeys.REDISKEY.EXAM_PLACE_SCHOOL + schoolId);
		if(null == ep ){
			ep = examPlaceManager.getExamPlaceBySchoolId((int) schoolId);
		}
		final String resp = new ResponseMessage()
								.addResult("userName",  AccessWebUtil.getSessionUser(request)==null?"": AccessWebUtil.getSessionUser(request).getAccount())
								.addResult("schoolName", AccessWebUtil.getSessionAttStr(request, Constant.SCHOOL_SESSION))
								.addResult("schoolId", schoolId)
								.addResult("placeId", ep.getId())
								.addResult("placeName", ep.getName())
								.build();
		access.debug(resp);
		return resp;
	}
	

	@RequestMapping("/verify-password")
	@ResponseBody
    public String verifyPassword(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		User user = AccessWebUtil.getSessionUser(request);
		String password = getParamStr(request, "password");
		access.info(" !!! pwd = " + password + " !!!");

		//比较密码
		if(!user.getPassword().equals(buildEncryptedPassword(password,user.getPassword()))){
			final String resp = new ResponseMessage(0,AccessWebUtil.ERROR_WRONG_OLDPWD)
									.build();
			access.debug(resp);
			return resp;
		}
		
		final String resp = new ResponseMessage()
								.build();
		access.debug(resp);
		return resp;
	}
	

	@RequestMapping("/update-password")
	@ResponseBody
    public String updatePassword(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		User user = AccessWebUtil.getSessionUser(request);
		String password = getParamStr(request, "password");
		if(password.length() < 6 || password.length() > 16){
			final String resp = new ResponseMessage(AccessWebUtil.ERROR_STR)
									.build();
			access.debug(resp);
			return resp;
		}
		
//		if(!user.getPassword().equals(buildEncryptedPassword(password,user.getPassword()))){
//			final String resp = new ResponseMessage(AccessWebUtil.ERROR_WRONG_OLDPWD)
//									.build();
//			access.debug(resp);
//			return resp;
//		}
		
		user.setPassword(EncryptUtil.DSHA1(password));
		return cmsUserService.updatePassword(user).build();
	}


	/**
	 * 由pwd0原始密码双重加密
	 * @param pwd0 原始密码
	 * @param pwd2 数据库存储的双重加密的密码
	 * @return
	 */
	private String buildEncryptedPassword(String pwd0,String pwd2){
		return EncryptUtil.DSHA1(pwd0, getRamdon(pwd2));
	}
	

	/**
	 * 通过数据库密码获取随机数
	 * @param enPwd 数据库存储的双重加密的密码
	 * @return
	 */
	private String getRamdon(String enPwd){
		return enPwd.substring(enPwd.length()-6, enPwd.length());
	}
	

	@RequestMapping(value="/add", method=RequestMethod.GET)
    @ResponseBody
    public String add(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			User ept = (User) 
					buildObject(request, User.class);
			return cmsUserService.insertOne(ept).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	@RequestMapping(value="/update", method= RequestMethod.POST)
    @ResponseBody
    public String update(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			User role = (User)buildObject(request, User.class);
			return cmsUserService.updateOne(role).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	

	/**
	 * 停用账户
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/cancle", method= RequestMethod.POST)
    @ResponseBody
    public String cancle(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			User user = AccessWebUtil.getSessionUser(request);
			return cmsUserService.cancle(getParamLong(request, "userId"),user.getAccount()).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	
	/**
	 * 激活账户
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/active", method= RequestMethod.POST)
    @ResponseBody
    public String active(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			User user = AccessWebUtil.getSessionUser(request);
			return cmsUserService.active(getParamLong(request, "userId"),user.getAccount()).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
}
