package com.lili.finance.vo;

import java.util.Date;

import com.lili.cms.entity.BasePagedEntity;

public class BonusVo  extends BasePagedEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5744455082983293632L;

	private Integer id;

    private String bonusName;

    private Integer bonusNum;

    private Integer status;

    private Integer bonusMoney;

    private String creator;

    private Date createTime;

    private String verifier;

    private Date verifieTime;

    private String award;

    private Date awardTime;

    private String remark;
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBonusName() {
        return bonusName;
    }

    public void setBonusName(String bonusName) {
        this.bonusName = bonusName == null ? null : bonusName.trim();
    }

    public Integer getBonusNum() {
        return bonusNum;
    }

    public void setBonusNum(Integer bonusNum) {
        this.bonusNum = bonusNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getBonusMoney() {
        return bonusMoney;
    }

    public void setBonusMoney(Integer bonusMoney) {
        this.bonusMoney = bonusMoney;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getVerifier() {
        return verifier;
    }

    public void setVerifier(String verifier) {
        this.verifier = verifier == null ? null : verifier.trim();
    }

    public Date getVerifieTime() {
        return verifieTime;
    }

    public void setVerifieTime(Date verifieTime) {
        this.verifieTime = verifieTime;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award == null ? null : award.trim();
    }

    public Date getAwardTime() {
        return awardTime;
    }

    public void setAwardTime(Date awardTime) {
        this.awardTime = awardTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

}