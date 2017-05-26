/**
 * 
 */
package com.lili.qqpay.sdk;

/**
 * @author linbo
 * 财付通支付
 */
public class QQPayConfig
{
    //初始化地址
    private String initUrl = "http://myun.tenpay.com/cgi-bin/wappayv2.0/wappay_init.cgi";
    //商户号
    private String mchID = "1309317901";
    
    //后台通知地址
    private String notifyUrl = "http://218.17.39.227:9066/payaccess/v1/files/qqPayCallBack";
    
    //前台通知地址
    private String callBackUrl = "http://218.17.39.227:9066/payaccess/v1/files/qqPayCallBack";
    
    //key
    private String key = "3db1ecfe440fd7feda170b46bae79d28";

    /**
     * @return the initUrl
     */
    public String getInitUrl()
    {
        return initUrl;
    }

    /**
     * @param initUrl the initUrl to set
     */
    public void setInitUrl(String initUrl)
    {
        this.initUrl = initUrl;
    }

    /**
     * @return the mchID
     */
    public String getMchID()
    {
        return mchID;
    }

    /**
     * @param mchID the mchID to set
     */
    public void setMchID(String mchID)
    {
        this.mchID = mchID;
    }

    /**
     * @return the notifyUrl
     */
    public String getNotifyUrl()
    {
        return notifyUrl;
    }

    /**
     * @param notifyUrl the notifyUrl to set
     */
    public void setNotifyUrl(String notifyUrl)
    {
        this.notifyUrl = notifyUrl;
    }

    /**
     * @return the callBackUrl
     */
    public String getCallBackUrl()
    {
        return callBackUrl;
    }

    /**
     * @param callBackUrl the callBackUrl to set
     */
    public void setCallBackUrl(String callBackUrl)
    {
        this.callBackUrl = callBackUrl;
    }

    /**
     * @return the key
     */
    public String getKey()
    {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key)
    {
        this.key = key;
    }  
}
