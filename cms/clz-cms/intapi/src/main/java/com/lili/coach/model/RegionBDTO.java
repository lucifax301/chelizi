package com.lili.coach.model;

import java.util.Date;

import com.lili.cms.entity.BasePagedEntity;

/**
 * 城市分页查询实体
 * @author yu.y
 *
 */
public class RegionBDTO  extends BasePagedEntity{

	private static final long serialVersionUID = 1L;

	/**
	 * 城市表主键
	 */
	private Integer rid;
	
	/**
	 * 区域名称
	 */
	private String region;
	
	/**
	 * 区域等级
	 */
	private Integer rlevel;
	
	/**
	 * 父节点id
	 */
	private Integer pid;
	
	/**
	 * 删除状态
	 */
	private Integer isdel;
	
	/**
	 * 创建人id
	 */
	private Integer cuid;
	
	/**
	 * 更新人id
	 */
	private Integer muid;
	
	/**
	 * 创建时间
	 */
	private Date ctime;
	
	/**
	 * mtime
	 */
	private Date mtime;

	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Integer getRlevel() {
		return rlevel;
	}

	public void setRlevel(Integer rlevel) {
		this.rlevel = rlevel;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}

	public Integer getCuid() {
		return cuid;
	}

	public void setCuid(Integer cuid) {
		this.cuid = cuid;
	}

	public Integer getMuid() {
		return muid;
	}

	public void setMuid(Integer muid) {
		this.muid = muid;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getMtime() {
		return mtime;
	}

	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}

}
