package com.lili.coach.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 教练实体
 * @author yu.y
 *
 */
public class CoachCheck implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1897878707369930375L;

	private Long coachId;
	
    private String drPhoto;

    private String drPhoto2;
    
    private Integer isNewDrPhoto;
    
    private Integer checkDrState;
    
    private String checkDrRemark;
    
    private Integer cityId;
    
    private String cityName;

    private List<CarCheck> carCheckList;

	public Long getCoachId() {
		return coachId;
	}

	public void setCoachId(Long coachId) {
		this.coachId = coachId;
	}

	public String getDrPhoto() {
		return drPhoto;
	}

	public void setDrPhoto(String drPhoto) {
		this.drPhoto = drPhoto;
	}

	public String getDrPhoto2() {
		return drPhoto2;
	}

	public void setDrPhoto2(String drPhoto2) {
		this.drPhoto2 = drPhoto2;
	}

	public Integer getIsNewDrPhoto() {
		return isNewDrPhoto;
	}

	public void setIsNewDrPhoto(Integer isNewDrPhoto) {
		this.isNewDrPhoto = isNewDrPhoto;
	}

	public Integer getCheckDrState() {
		return checkDrState;
	}

	public void setCheckDrState(Integer checkDrState) {
		this.checkDrState = checkDrState;
	}

	public String getCheckDrRemark() {
		return checkDrRemark;
	}

	public void setCheckDrRemark(String checkDrRemark) {
		this.checkDrRemark = checkDrRemark;
	}

	public List<CarCheck> getCarCheckList() {
		return carCheckList;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public void setCarCheckList(List<CarCheck> carCheckList) {
		this.carCheckList = carCheckList;
	} 

}
