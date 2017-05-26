package com.lili.user.model;

import java.util.Date;
import java.util.List;

import com.lili.cms.entity.BaseEntity;
import com.lili.privilege.model.Privilege;

public class User extends BaseEntity implements Cloneable {

	private static final long serialVersionUID = 1L;

	private long id;
	
	private String userName;
	
	private String account;
	
	private String password;
	
	private String email;
	
	private String phoneNum;
	
	private String creator;
	
	private String updator;
	
	private Date createTime;
	
	private Date updateTime;
	
	
	private List<Privilege> privileges;
	
	
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}

	/**
	 * 是否启用,0--启用,1--停用
	 */
	private Integer enabled;
	
	public User(){}
	
	public User(String account,String password){
		this.account = account;
		this.password = password;
	}

	
	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
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

	public Object clone() {
		User o = null;
		try {
			o = (User)super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}
	
}
