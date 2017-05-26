/**
 * 
 */
package com.lili.pay.config;

import java.util.Map;

/**
 * @author linbo
 * 支付的相关配置
 */
public class WXPayConfig
{
    //这个就是自己要保管好的私有Key了（切记只能放在自己的后台代码里，不能放在任何可能被看到源代码的客户端程序中）
    // 每次自己Post数据给API的时候都要用这个key来对所有字段进行签名，生成的签名会放在Sign这个字段，API收到Post数据的时候也会用同样的签名算法对Post过来的数据进行签名和验证
    // 收到API的返回的时候也要用这个key来对返回的数据算下签名，跟API的Sign数据进行比较，如果值不一致，有可能数据被第三方给篡改
    private Map<Integer, String> keysMap;

    //微信分配的公众号ID（开通公众号之后可以获取到）
    private Map<Integer, String> appIDMap;
    
    //商户号集合，微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
    private Map<Integer, String> mchIDMap; 

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
    
    //充值回调
    private String chargeNotifyUrl="";

    public String getMchId(int clientVer)
    {
        return mchIDMap.get(clientVer);
    }
    public String getKey(int clientVer)
    {
        return keysMap.get(clientVer);
    }

    public String getAppId(int clientVer)
    {
        return appIDMap.get(clientVer);
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

    /**
     * @return the chargeNotifyUrl
     */
    public String getChargeNotifyUrl()
    {
        return chargeNotifyUrl;
    }

    /**
     * @param chargeNotifyUrl the chargeNotifyUrl to set
     */
    public void setChargeNotifyUrl(String chargeNotifyUrl)
    {
        this.chargeNotifyUrl = chargeNotifyUrl;
    }

    /**
     * @return the keysMap
     */
    public Map<Integer, String> getKeysMap()
    {
        return keysMap;
    }

    /**
     * @param keysMap the keysMap to set
     */
    public void setKeysMap(Map<Integer, String> keysMap)
    {
        this.keysMap = keysMap;
    }

    /**
     * @return the appIDMap
     */
    public Map<Integer, String> getAppIDMap()
    {
        return appIDMap;
    }

    /**
     * @param appIDMap the appIDMap to set
     */
    public void setAppIDMap(Map<Integer, String> appIDMap)
    {
        this.appIDMap = appIDMap;
    }

    /**
     * @return the mchIDMap
     */
    public Map<Integer, String> getMchIDMap()
    {
        return mchIDMap;
    }

    /**
     * @param mchIDMap the mchIDMap to set
     */
    public void setMchIDMap(Map<Integer, String> mchIDMap)
    {
        this.mchIDMap = mchIDMap;
    }
    
}
