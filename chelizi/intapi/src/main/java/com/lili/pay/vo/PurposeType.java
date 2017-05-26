/**
 * 
 */
package com.lili.pay.vo;

/**
 * @author linbo
 *
 */
public enum PurposeType
{
    COURSE(0, "课时费"),
    CHARGE(1, "充值"),
    SIGNUP(2, "报名费"),
    WXACTIVITY(3, "公众号充值"),
	WXSIGNUP(4, "驾校点评报名费"),
	EXAMPLACE(5, "约考场费"),
	INSURANCE(6,"平安保险费"),
	WXCOACH(7,"喱喱教练公众号报名费"),
	SCHOOLSIGNUP(8, "找驾校报名费"),
	RECHARGE(9,"充值送现金");
    int type;
    String desc;
    
    /**
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * @return the desc
     */
    public String getDesc()
    {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    /**
     * 
     */
    private PurposeType(int type, String desc)
    {
        this.type = type;
        this.desc =desc;
    }
    
    public static PurposeType parse(int type)
    {
        for (PurposeType payGoal : PurposeType.values())
        {
            if (payGoal.getType() == type)
            {
                return payGoal;
            }
        }
        return COURSE;
    }
 
}
