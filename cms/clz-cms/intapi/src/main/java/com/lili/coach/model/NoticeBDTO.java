package com.lili.coach.model;

import java.util.Date;

import com.lili.cms.entity.BasePagedEntity;

/**
 * 公告实体
 * @author yu.y
 *
 */
public class NoticeBDTO extends BasePagedEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7806656452300621853L;
	
	/**
	 * 公告id
	 */
	private Integer noticeId;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 内容
	 */
	private String content;
	
	/**
	 * 公告类别（0-今日重点播报、1-优惠活动、2-订单消息、）
	 */
	private Integer type;
	
	/**
	 * 创建时间
	 */
	private Date time;
	
	/**
	 * 公告创建者id
	 */
	private Integer adminId;
	
	/**
	 * 通知对象 (0所有1教练 2学员)
	 */
	private Integer userType;
	
	/**
	 * 用户id
	 */
	private Long userId;
	
	/**
	 * 附加字段：采用json格式保存url等信息
	 */
	private String extra;
	/**
	 * 是否已删除
	 */
	private Byte isdel;

	public Integer getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public Byte getIsdel() {
		return isdel;
	}

	public void setIsdel(Byte isdel) {
		this.isdel = isdel;
	}

}
