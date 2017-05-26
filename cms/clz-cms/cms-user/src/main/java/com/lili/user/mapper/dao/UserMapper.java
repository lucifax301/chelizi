package com.lili.user.mapper.dao;

import java.util.List;

import com.lili.user.model.User;
import com.lili.user.model.UserBDTO;

public interface UserMapper {

	/**
	 * 登录校验
	 * @param user
	 * @return
	 */
	public User checkUserInfo(User user);

	public User findById(Long userId);

	public User findByAccount(String account);

	public Long updatePassword(User user);

    int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);
	
	public  List<User> findUser(UserBDTO user);
	
	public  int findUserTotal(UserBDTO user);
	
	public  List<User> findAvailUser(UserBDTO user);
	
	public  int findAvailUserTotal(UserBDTO user);
	
}
