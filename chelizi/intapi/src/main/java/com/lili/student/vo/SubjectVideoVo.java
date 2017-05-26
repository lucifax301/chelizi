package com.lili.student.vo;


import java.io.Serializable;
import java.util.Date;

public class SubjectVideoVo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6945727166026629666L;

	private Integer id;

    private Integer cityId;
    
    private Integer subject;
    
    private Integer examId;
    
    private Integer serialId;

    private Integer fileType;

    private String name;

    private String vtime;

    private String url;

    private String video2Img;

    private String video3Img;

    private Integer isDel;

    private String creator;

    private Date ctime;

    private String updater;

    private Date utime;
    
    private String actionRemark;
    
    private String standard;
    
    private String skill;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getExamId() {
		return examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	public Integer getSerialId() {
		return serialId;
	}

	public void setSerialId(Integer serialId) {
		this.serialId = serialId;
	}

	public Integer getSubject() {
        return subject;
    }

    public void setSubject(Integer subject) {
        this.subject = subject;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getVtime() {
        return vtime;
    }

    public void setVtime(String vtime) {
        this.vtime = vtime == null ? null : vtime.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getVideo2Img() {
        return video2Img;
    }

    public void setVideo2Img(String video2Img) {
        this.video2Img = video2Img == null ? null : video2Img.trim();
    }

    public String getVideo3Img() {
        return video3Img;
    }

    public void setVideo3Img(String video3Img) {
        this.video3Img = video3Img == null ? null : video3Img.trim();
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater == null ? null : updater.trim();
    }

    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public String getActionRemark() {
		return actionRemark;
	}

	public void setActionRemark(String actionRemark) {
		this.actionRemark = actionRemark;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}
}