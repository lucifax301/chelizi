package com.lili.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.user.manager.CMSUserManager;
import com.lili.user.model.User;
import com.lili.user.model.UserBDTO;
import com.lili.user.service.CMSUserService;

public class CMSUserServiceImpl implements CMSUserService{

	@Autowired
	CMSUserManager cmsUserManager;
	
	@Override
	public ResponseMessage loginCheck(User user) {
		User user1 = cmsUserManager.checkUserInfo(user);
		user.setPassword(user.getPassword());
		User result = cmsUserManager.checkUserInfo(user1);
		if(result != null){
			return new ResponseMessage();
		}
		return new ResponseMessage("用户不存在");
	}

	@Override
	public User findById(Long userId) {
		return cmsUserManager.findById(userId);
	}

	@Override
	public User findByAccount(String account) {
		return cmsUserManager.findByAccount(account);
	}

	@Override
	public ResponseMessage updatePassword(User user) {
		Long count = cmsUserManager.updatePassword(user);
		if(count <= 0){
			return new ResponseMessage("密码更新失败");
		}
		return new ResponseMessage();
	}

	@Override
	public ResponseMessage insertOne(User user) {
		User exist=cmsUserManager.findByAccount(user.getAccount());
		if(exist==null){
			if(cmsUserManager.insertOne(user)){
				return new ResponseMessage();
			}
			return new ResponseMessage("插入失败");
		}else{
			return new ResponseMessage("帐号已经存在");
		}
	}

	@Override
	public ResponseMessage updateOne(User user) {
		if(cmsUserManager.updateOne(user)){
			return new ResponseMessage();
		}
		return new ResponseMessage("更新失败");
	}

	@Override
	public ResponseMessage cancle(Long userId,String updator) {
		return updateAccountState(userId,1,updator);
	}

	@Override
	public ResponseMessage active(Long userId,String updator) {
		return updateAccountState(userId,0,updator);
	}

	/**
	 * 更新账户状态
	 * @param userId
	 * @param enabled
	 * @return
	 */
	private ResponseMessage updateAccountState(Long userId,int enabled,String updator) {
		User user = cmsUserManager.findById(userId);
		if(user == null){
			return new ResponseMessage("用户不存在");
		}
		user.setEnabled(enabled);
		user.setUpdator(updator);
		if(!cmsUserManager.updateOne(user)){
			return new ResponseMessage("更新失败");
		}
		return new ResponseMessage();
	}
	
	public  PagedResult<User> findUser(UserBDTO user){
		return  cmsUserManager.findUser(user);
	}

	@Override
	public PagedResult<User> findAvailUser(UserBDTO user) {
		return cmsUserManager.findAvailUser(user);
	}
	
	
}
