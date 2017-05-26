package com.lili.user.model;

import com.lili.cms.entity.BaseEntity;

/**
 * 因为菜单和不同端有关,所以这里添加实体来传参
 * @author hughes
 *
 */
public class PermissionDTO extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private long userId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
}
