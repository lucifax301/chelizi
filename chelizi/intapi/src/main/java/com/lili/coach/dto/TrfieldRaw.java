package com.lili.coach.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TrfieldRaw implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6008844352700245181L;

	private Integer id;

    private String name;

    private String address;

    private String school;

    private String province;

    private String city;

    private String district;

    private String baiduaddr;

    private BigDecimal lge;

    private BigDecimal lae;

    private String mobile;

    private String imei;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school == null ? null : school.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    public String getBaiduaddr() {
        return baiduaddr;
    }

    public void setBaiduaddr(String baiduaddr) {
        this.baiduaddr = baiduaddr == null ? null : baiduaddr.trim();
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }
}