package com.lili.event.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.lili.exam.dto.ExamPlaceRechargeGears;

public class ExamPlaceRechargeVo implements Serializable {
    private Integer id;

    private String name;

    private String schools;

    private Integer schoolCount;

    private Byte state;

    private String info;

    private String cuser;

    private String muser;

    private Date ctime;

    private Date mtime;

    private String extra;
    
    private List<ExamPlaceRechargeGears> gears;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSchools() {
        return schools;
    }

    public void setSchools(String schools) {
        this.schools = schools == null ? null : schools.trim();
    }

    public Integer getSchoolCount() {
        return schoolCount;
    }

    public void setSchoolCount(Integer schoolCount) {
        this.schoolCount = schoolCount;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

    public String getCuser() {
        return cuser;
    }

    public void setCuser(String cuser) {
        this.cuser = cuser == null ? null : cuser.trim();
    }

    public String getMuser() {
        return muser;
    }

    public void setMuser(String muser) {
        this.muser = muser == null ? null : muser.trim();
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

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", schools=").append(schools);
        sb.append(", schoolCount=").append(schoolCount);
        sb.append(", state=").append(state);
        sb.append(", info=").append(info);
        sb.append(", cuser=").append(cuser);
        sb.append(", muser=").append(muser);
        sb.append(", ctime=").append(ctime);
        sb.append(", mtime=").append(mtime);
        sb.append(", extra=").append(extra);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

	public List<ExamPlaceRechargeGears> getGears() {
		return gears;
	}

	public void setGears(List<ExamPlaceRechargeGears> gears) {
		this.gears = gears;
	}
}