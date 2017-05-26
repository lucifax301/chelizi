package com.lili.school.model;

import com.lili.cms.entity.BasePagedEntity;

public class SchAccount extends BasePagedEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -975587660688272184L;

    private Integer money;

    private String passwd;
    
    private String passwdOld;
    
    private String mobile;
    
    private String codeInput;

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    public String getPasswdOld() {
		return passwdOld;
	}

	public void setPasswdOld(String passwdOld) {
		this.passwdOld = passwdOld;
	}
	
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

	public String getCodeInput() {
		return codeInput;
	}

	public void setCodeInput(String codeInput) {
		this.codeInput = codeInput;
	}
}