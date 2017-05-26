package com.lili.school.vo;


import java.io.Serializable;
import java.util.Date;

public class WechatMycoaches  implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 409984985129284846L;
    private Integer id;

    private Long studentId;
    
    private Long studentIdNew;

    private String name;

    private String phone;
    
    private String headIcon;

    private Integer state;

    private Long coachId;

    private Integer wxstatus;

    private Integer type;

    private Integer isdel;

    private Integer channel;

    private Integer istop;

    private Integer schoolId;

    private Integer drtype;
    
    private Integer isNew;

    private String coachRemark;

    private Date ctime;

    private Date mtime;
    
    private Integer onClassNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getStudentIdNew() {
		return studentIdNew;
	}

	public void setStudentIdNew(Long studentIdNew) {
		this.studentIdNew = studentIdNew;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getHeadIcon() {
		return headIcon;
	}

	public void setHeadIcon(String headIcon) {
		this.headIcon = headIcon;
	}

	public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getCoachId() {
        return coachId;
    }

    public void setCoachId(Long coachId) {
        this.coachId = coachId;
    }

    public Integer getWxstatus() {
        return wxstatus;
    }

    public void setWxstatus(Integer wxstatus) {
        this.wxstatus = wxstatus;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getIstop() {
        return istop;
    }

    public void setIstop(Integer istop) {
        this.istop = istop;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getDrtype() {
        return drtype;
    }

    public void setDrtype(Integer drtype) {
        this.drtype = drtype;
    }

    public String getCoachRemark() {
        return coachRemark;
    }

    public void setCoachRemark(String coachRemark) {
        this.coachRemark = coachRemark == null ? null : coachRemark.trim();
    }

    public Integer getIsNew() {
		return isNew;
	}

	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
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

	public Integer getOnClassNum() {
		return onClassNum;
	}

	public void setOnClassNum(Integer onClassNum) {
		this.onClassNum = onClassNum;
	}

}