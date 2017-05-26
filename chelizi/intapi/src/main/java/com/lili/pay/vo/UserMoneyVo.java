package com.lili.pay.vo;

import java.io.Serializable;
import java.util.Date;

public class UserMoneyVo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3090975780127229865L;

	private Integer id;

    private Long userid;

    private Byte usertype;

    private Byte operatetype;

    private Integer changevalue;

    private Date operatetime;

    private String payway;

    private Integer leftvalue;

    private String remark;

    private String orderId;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Byte getUsertype() {
        return usertype;
    }

    public void setUsertype(Byte usertype) {
        this.usertype = usertype;
    }

    public Byte getOperatetype() {
        return operatetype;
    }

    public void setOperatetype(Byte operatetype) {
        this.operatetype = operatetype;
    }

    public Integer getChangevalue() {
        return changevalue;
    }

    public void setChangevalue(Integer changevalue) {
        this.changevalue = changevalue;
    }

    public Date getOperatetime() {
        return operatetime;
    }

    public void setOperatetime(Date operatetime) {
        this.operatetime = operatetime;
    }

    public Integer getLeftvalue() {
        return leftvalue;
    }

    public void setLeftvalue(Integer leftvalue) {
        this.leftvalue = leftvalue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * @return the payway
     */
    public String getPayway()
    {
        return payway;
    }

    /**
     * @param payway the payway to set
     */
    public void setPayway(String payway)
    {
        this.payway = payway;
    }
    @Override
    public String toString()
    {
        return "UserMoneyVo [id=" + id + ", userid=" + userid + ", usertype=" + usertype + ", operatetype="
                + operatetype + ", changevalue=" + changevalue + ", operatetime=" + operatetime + ", payway=" + payway
                + ", leftvalue=" + leftvalue + ", remark=" + remark + "]";
    }

    /**
     * @return the orderId
     */
    public String getOrderId()
    {
        return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
}