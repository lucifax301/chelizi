/**
 * 
 */
package com.lili.pay.config;

/**
 * @author linbo
 * 支付的相关配置
 */
public class PayConfig
{
    //这个就是自己要保管好的私有Key了（切记只能放在自己的后台代码里，不能放在任何可能被看到源代码的客户端程序中）
    // 每次自己Post数据给API的时候都要用这个key来对所有字段进行签名，生成的签名会放在Sign这个字段，API收到Post数据的时候也会用同样的签名算法对Post过来的数据进行签名和验证
    // 收到API的返回的时候也要用这个key来对返回的数据算下签名，跟API的Sign数据进行比较，如果值不一致，有可能数据被第三方给篡改
    private String key = "";

    //微信分配的公众号ID（开通公众号之后可以获取到）
    private String appID = "";

    //微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
    private String mchID = "";

    //HTTPS证书的本地路径
    private String certLocalPath = "";

    //HTTPS证书密码，默认密码等于商户号MCHID
    private String certPassword = "";

    //是否使用异步线程的方式来上报API测速，默认为异步模式
    private boolean useThreadToDoReport = true;

    //回调的接口
    private String notifyUrl = "";
    
    //机器IP
    private String ip = "";
    
    //交易类型
    private String tradeType = "";

    //订单失效时间
    private long orderExpire = 0;
    
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

    /**
     * @return the appID
     */
    public String getAppID()
    {
        return appID;
    }

    /**
     * @param appID the appID to set
     */
    public void setAppID(String appID)
    {
        this.appID = appID;
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
     * @return the certLocalPath
     */
    public String getCertLocalPath()
    {
        return certLocalPath;
    }

    /**
     * @param certLocalPath the certLocalPath to set
     */
    public void setCertLocalPath(String certLocalPath)
    {
        this.certLocalPath = certLocalPath;
    }

    /**
     * @return the certPassword
     */
    public String getCertPassword()
    {
        return certPassword;
    }

    /**
     * @param certPassword the certPassword to set
     */
    public void setCertPassword(String certPassword)
    {
        this.certPassword = certPassword;
    }

    /**
     * @return the useThreadToDoReport
     */
    public boolean isUseThreadToDoReport()
    {
        return useThreadToDoReport;
    }

    /**
     * @param useThreadToDoReport the useThreadToDoReport to set
     */
    public void setUseThreadToDoReport(boolean useThreadToDoReport)
    {
        this.useThreadToDoReport = useThreadToDoReport;
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
     * @return the ip
     */
    public String getIp()
    {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip)
    {
        this.ip = ip;
    }

    /**
     * @return the tradeType
     */
    public String getTradeType()
    {
        return tradeType;
    }

    /**
     * @param tradeType the tradeType to set
     */
    public void setTradeType(String tradeType)
    {
        this.tradeType = tradeType;
    }

    /**
     * @return the orderExpire
     */
    public long getOrderExpire()
    {
        return orderExpire;
    }

    /**
     * @param orderExpire the orderExpire to set
     */
    public void setOrderExpire(long orderExpire)
    {
        this.orderExpire = orderExpire;
    }
    
}
