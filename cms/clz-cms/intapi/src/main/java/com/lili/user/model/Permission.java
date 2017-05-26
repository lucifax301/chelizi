package com.lili.user.model;

import java.io.Serializable;


public class Permission implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;

    private String name;

    /**
     * 父ID，菜单的父子树目录，0未root
     */
    private long pid;

    private String url;

    /**
     * 类型,0--接口,1--菜单,2--按钮
     */
    private int type;

    /**
     * 是否启用,0--启用，1--停用
     */
    private int enabled;

    private String remark;
    
    /**
     * 0---全部，1---驾校端，2---车厘子端
     */
    private int channelType;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getChannelType() {
		return channelType;
	}

	public void setChannelType(int channelType) {
		this.channelType = channelType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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
}