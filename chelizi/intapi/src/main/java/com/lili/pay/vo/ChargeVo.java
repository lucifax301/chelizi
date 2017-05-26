/**
 * 
 */
package com.lili.pay.vo;

import java.io.Serializable;

/**
 * @author linbo
 *
 */
public class ChargeVo implements Serializable
{
    private static final long serialVersionUID = 8568043395396169406L;
    
    int chargeValue;
    String chargeType;
    String bankCard;
    long userId;
    int userType;
    String chargeId;
    
    /**
     * @return the chargeValue
     */
    public int getChargeValue()
    {
        return chargeValue;
    }
    /**
     * @param chargeValue the chargeValue to set
     */
    public void setChargeValue(int chargeValue)
    {
        this.chargeValue = chargeValue;
    }
    /**
     * @return the chargeType
     */
    public String getChargeType()
    {
        return chargeType;
    }
    /**
     * @param chargeType the chargeType to set
     */
    public void setChargeType(String chargeType)
    {
        this.chargeType = chargeType;
    }
    /**
     * @return the bankCard
     */
    public String getBankCard()
    {
        return bankCard;
    }
    /**
     * @param bankCard the bankCard to set
     */
    public void setBankCard(String bankCard)
    {
        this.bankCard = bankCard;
    }
    /**
     * @return the userId
     */
    public long getUserId()
    {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(long userId)
    {
        this.userId = userId;
    }
    /**
     * @return the userType
     */
    public int getUserType()
    {
        return userType;
    }
    /**
     * @param userType the userType to set
     */
    public void setUserType(int userType)
    {
        this.userType = userType;
    }
    /**
     * @return the chargeId
     */
    public String getChargeId()
    {
        return chargeId;
    }
    /**
     * @param chargeId the chargeId to set
     */
    public void setChargeId(String chargeId)
    {
        this.chargeId = chargeId;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "ChargeVo [chargeValue=" + chargeValue + ", chargeType=" + chargeType + ", bankCard=" + bankCard
                + ", userId=" + userId + ", userType=" + userType + ", chargeId=" + chargeId + "]";
    }
    
}
