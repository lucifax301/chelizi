package com.lili.user.manager;

import java.util.List;

import com.lili.cms.entity.PagedResult;
import com.lili.user.model.User;
import com.lili.user.model.UserBDTO;

public interface CMSUserManager {

	/**
	 * 登录校验
	 * @param user
	 * @return
	 */
	public User checkUserInfo(User user);
	
	public User findById(Long userId);

	public User findByAccount(String account);

	public Long updatePassword(User user);

    boolean insertOne(User record);

    boolean updateOne(User record);
    
    public  PagedResult<User> findUser(UserBDTO user);

    public  PagedResult<User> findAvailUser(UserBDTO user);
}
