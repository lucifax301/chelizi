package com.lili.student.dto;

import java.io.Serializable;
import java.util.Date;

public class Mycoaches implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	private Integer id;

	/**
	 * 学员id
	 */
    private Long studentid;

    /**
     * 教练id
     */
    private Long coachid;

    /**
     * 记录创建时间，默认为当前时间
     */
    private Date createtime;
    
    /**
     * 关联状态：0-正常关联；1-取消关联',默认为0
     */
    private Byte status;

    /**
     * 关联类型：0-自动创建；1-系统录入；2-客服录入；默认为0
     */
    private Byte type;
    
    /**
     * 数据同步类型(1更新2插入)
     */
    private Integer syncType;
    
    /**
     * 数据最后一次同步时间
     */
    private Date syncTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getStudentid() {
        return studentid;
    }

    public void setStudentid(Long studentid) {
        this.studentid = studentid;
    }

    public Long getCoachid() {
        return coachid;
    }

    public void setCoachid(Long coachid) {
        this.coachid = coachid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

	public Integer getSyncType() {
		return syncType;
	}

	public void setSyncType(Integer syncType) {
		this.syncType = syncType;
	}

	public Date getSyncTime() {
		return syncTime;
	}

	public void setSyncTime(Date syncTime) {
		this.syncTime = syncTime;
	}
}