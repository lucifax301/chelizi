package com.lili.log.model;

import java.util.Date;

import com.lili.cms.entity.BaseEntity;
import com.lili.user.model.User;

public class LogCommon extends BaseEntity implements Cloneable{
	private static final long serialVersionUID = 1L;

	private Integer id;

	/**
	 * 对应权限的id
	 */
    private Integer menuId;

    private String menuName;

    private String url;

    /**
     * 操作类型 0 其他, 1 增加，2修改，3删除
     */
    private Integer action;

    private String userName;

    private String userId;

    private Date operateTime;

    /**
     * 来源：1喱喱，2教练端
     */
    private Integer channel;

    private String ip;

    /**
     * 操作记录关联表ID
     */
    private String relateId;

    private Integer tableId;

    private String relateTable;

    /**
     * 操作状态：1成功，2失败
     */
    private Integer status;

    /**
     * WEB用户端只需要看到这个备注即可
     */
    private String remark;
    
    private String detail;
    
    

    public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getRelateId() {
        return relateId;
    }

    public void setRelateId(String relateId) {
        this.relateId = relateId == null ? null : relateId.trim();
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public String getRelateTable() {
        return relateTable;
    }

    public void setRelateTable(String relateTable) {
        this.relateTable = relateTable == null ? null : relateTable.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }


	public Object clone() {
		LogCommon o = null;
		try {
			o = (LogCommon)super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}
}