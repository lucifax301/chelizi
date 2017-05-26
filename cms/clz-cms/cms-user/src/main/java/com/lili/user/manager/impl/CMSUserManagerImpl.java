package com.lili.user.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.user.manager.CMSUserManager;
import com.lili.user.mapper.dao.UserMapper;
import com.lili.user.model.User;
import com.lili.user.model.UserBDTO;

public class CMSUserManagerImpl implements CMSUserManager{

	@Autowired
	UserMapper userMapper;

	@Override
	public User checkUserInfo(User user) {
		return userMapper.checkUserInfo(user);
	}

	@Override
	public User findById(Long userId) {
		return userMapper.findById(userId);
	}

	@Override
	public User findByAccount(String account) {
		return userMapper.findByAccount(account);
	}

	@Override
	public Long updatePassword(User user) {
		return userMapper.updatePassword(user);
	}

	@Override
	public boolean insertOne(User record) {
		return userMapper.insertSelective(record) > 0;
	}

	@Override
	public boolean updateOne(User record) {
		return userMapper.updateByPrimaryKey(record) > 0;
	}

	public  PagedResult<User> findUser(UserBDTO user){
		if(user.getPageSize()==0)user.setPageSize(10);
		user.setStartIndex((user.getPageNo()-1)*user.getPageSize());
		List<User> users=userMapper.findUser(user);
		PagedResult<User> result = new PagedResult<User>();
        result.setPageNo(user.getPageNo());
        result.setPageSize(user.getPageSize());
        result.setDataList(users);
        int total=userMapper.findUserTotal(user);
        result.setTotal(total);
        result.setPages((total/user.getPageSize())+1);
		return result;
		 
	}

	@Override
	public PagedResult<User> findAvailUser(UserBDTO user) {
		if(user.getPageSize()==0)user.setPageSize(10);
		user.setStartIndex((user.getPageNo()-1)*user.getPageSize());
		List<User> users=userMapper.findAvailUser(user);
		PagedResult<User> result = new PagedResult<User>();
        result.setPageNo(user.getPageNo());
        result.setPageSize(user.getPageSize());
        result.setDataList(users);
        int total=userMapper.findAvailUserTotal(user);
        result.setTotal(total);
        result.setPages((total/user.getPageSize())+1);
		return result;
	}
	
	
}
