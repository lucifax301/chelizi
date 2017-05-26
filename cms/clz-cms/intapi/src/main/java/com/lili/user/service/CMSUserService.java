package com.lili.user.service;

import java.util.List;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.user.model.User;
import com.lili.user.model.UserBDTO;

public interface CMSUserService {
	
	/**
	 * 登录校验
	 * @param user
	 * @return
	 */
	public  ResponseMessage loginCheck(User user);

	public  User findById(Long userId);

	public  User findByAccount(String account);

	public  ResponseMessage updatePassword(User user);

	public  ResponseMessage insertOne(User user);

	public  ResponseMessage updateOne(User user);
	
	/**
	 * 停用账户
	 * @param userId
	 * @return
	 */
	public  ResponseMessage cancle(Long userId,String updator);
	
	/**
	 * 激活账户
	 * @param userId
	 * @return
	 */
	public  ResponseMessage active(Long userId,String updator);
	
	public  PagedResult<User> findUser(UserBDTO user);
	
	public  PagedResult<User> findAvailUser(UserBDTO user);
	
}
