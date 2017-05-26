/**
 * 
 */
package com.lili.pay.vo;

/**
 * @author linbo
 *         客户端版本
 */
public enum PayClientVer
{
    STUDENT_APPSTORE(0, "学员appStore版"),
    COACH_APPSOTRE(1, "教练appStore版"),
    STUDENT_ENTERPRISE(2, "学员企业版"),
    COACH_ENTERPRISE(3, "教练企业版"),
    WEB_WX(4,"微信公众帐号"),
	WX_COACH(5,"喱喱教练公众帐号"); 

    int type;
    String desc;

    /**
     * 
     */
    PayClientVer(int type, String desc)
    {
        this.type = type;
        this.desc = desc;
    }

    /**
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type
     *            the type to set
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
     * @param desc
     *            the desc to set
     */
    public void setDesc(String desc)
    {
        this.desc = desc;
    }
    
    public static PayClientVer parse(int type)
    {
        for (PayClientVer payClientVer : PayClientVer.values())
        {
            if (payClientVer.getType() == type)
            {
                return payClientVer;
            }
        }
        return STUDENT_APPSTORE;
    }
}
