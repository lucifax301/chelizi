package com.lili.user.model;

import com.lili.cms.entity.BasePagedEntity;

public class UserBDTO extends BasePagedEntity {

	private static final long serialVersionUID = 1L;

	private long id;
	
	private String userName;
	
	private String account;
	
	private String password;
	
	private String email;
	
	private String phoneNum;
	
	private int enabled;
	
	private int roleId;
	
	public UserBDTO(){}
	
	public UserBDTO(String account,String password){
		this.account = account;
		this.password = password;
	}

	
	
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	
}
