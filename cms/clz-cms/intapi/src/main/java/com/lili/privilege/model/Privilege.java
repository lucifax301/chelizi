package com.lili.privilege.model;

import java.io.Serializable;
import java.util.List;

public class Privilege implements Serializable{

	private int id;
	
	private String name;
	
	private int pid;
	
	private String url;
	
	private int enabled;
	
	private String remark;
	
	private int menuOrder;
	
	private int layoutOrder;
	
	private boolean layoutChecked;
	
	private int level;
	
	private String icon;
	
	/**
	 * 0--接口,1--菜单,2--按钮  3--导航
	 */
	private int type;
	
	private String ajax;
	
	
	public String getAjax() {
		return ajax;
	}

	public void setAjax(String ajax) {
		this.ajax = ajax;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	private List<Privilege> privileges;
	
	

	public List<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isLayoutChecked() {
		return layoutChecked;
	}

	public void setLayoutChecked(boolean layoutChecked) {
		this.layoutChecked = layoutChecked;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(int menuOrder) {
		this.menuOrder = menuOrder;
	}

	public int getLayoutOrder() {
		return layoutOrder;
	}

	public void setLayoutOrder(int layoutOrder) {
		this.layoutOrder = layoutOrder;
	}
	
	
}
