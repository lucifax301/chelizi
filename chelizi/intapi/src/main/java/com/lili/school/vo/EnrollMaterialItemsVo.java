package com.lili.school.vo;

import java.io.Serializable;
import java.util.Date;

public class EnrollMaterialItemsVo implements Serializable {

    private Integer pmid;

    private String name;

    private String pic;

    private String icon;

    private Byte isdel;

    private String description;
    
    private Integer state;

	public Integer getPmid() {
		return pmid;
	}

	public void setPmid(Integer pmid) {
		this.pmid = pmid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Byte getIsdel() {
		return isdel;
	}

	public void setIsdel(Byte isdel) {
		this.isdel = isdel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
    
	
}
