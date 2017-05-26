package com.lili.school.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class WechatEnrollSchoolVo implements Serializable{

	 private Integer schoolId;

	    private Integer cityId;

	    private String name;

	    private Integer score;

	    private String logo;

	    private String icon;

	    private Integer coachCount;

	    private Integer carCount;

	    private Integer trfieldCount;

	    private String mapImage;

	    private BigDecimal lge;

	    private BigDecimal lae;
	    
	    private String image;

	    private String schoolInfo;

	    private String regInfo;

	    
	    /*
	     * 报名人数
	     */
	    private int orderNum;
	    /*
	     * 最短距离
	     */
	    private int distance;
	    
	    public Integer getSchoolId() {
	        return schoolId;
	    }

	    public void setSchoolId(Integer schoolId) {
	        this.schoolId = schoolId;
	    }

	    public Integer getCityId() {
	        return cityId;
	    }

	    public void setCityId(Integer cityId) {
	        this.cityId = cityId;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name == null ? null : name.trim();
	    }

	    public Integer getScore() {
	        return score;
	    }

	    public void setScore(Integer score) {
	        this.score = score;
	    }

	    public String getLogo() {
	        return logo;
	    }

	    public void setLogo(String logo) {
	        this.logo = logo == null ? null : logo.trim();
	    }

	    public String getIcon() {
	        return icon;
	    }

	    public void setIcon(String icon) {
	        this.icon = icon == null ? null : icon.trim();
	    }

	    public Integer getCoachCount() {
	        return coachCount;
	    }

	    public void setCoachCount(Integer coachCount) {
	        this.coachCount = coachCount;
	    }

	    public Integer getCarCount() {
	        return carCount;
	    }

	    public void setCarCount(Integer carCount) {
	        this.carCount = carCount;
	    }

	    public Integer getTrfieldCount() {
	        return trfieldCount;
	    }

	    public void setTrfieldCount(Integer trfieldCount) {
	        this.trfieldCount = trfieldCount;
	    }

	    public String getMapImage() {
	        return mapImage;
	    }

	    public void setMapImage(String mapImage) {
	        this.mapImage = mapImage == null ? null : mapImage.trim();
	    }

	    public BigDecimal getLge() {
	        return lge;
	    }

	    public void setLge(BigDecimal lge) {
	        this.lge = lge;
	    }

	    public BigDecimal getLae() {
	        return lae;
	    }

	    public void setLae(BigDecimal lae) {
	        this.lae = lae;
	    }
	    
	    

	    public int getOrderNum() {
			return orderNum;
		}

		public void setOrderNum(int orderNum) {
			this.orderNum = orderNum;
		}

		public int getDistance() {
			return distance;
		}

		public void setDistance(int distance) {
			this.distance = distance;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public String getSchoolInfo() {
			return schoolInfo;
		}

		public void setSchoolInfo(String schoolInfo) {
			this.schoolInfo = schoolInfo;
		}

		public String getRegInfo() {
			return regInfo;
		}

		public void setRegInfo(String regInfo) {
			this.regInfo = regInfo;
		}
		
		
}
