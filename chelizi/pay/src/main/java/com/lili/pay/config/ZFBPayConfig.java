/**
 * 
 */
package com.lili.pay.config;

/**
 * @author linbo
 * 支付宝配置
 */
public class ZFBPayConfig
{
    //↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    // 合作身份者ID，以2088开头由16位纯数字组成的字符串
    private String partner = "2088111797257831";
    
    // 支付宝的公钥，无需修改该值
    private String ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

    // 调试用，创建TXT日志文件夹路径
    private String log_path = "\\log";

    // 字符编码格式 目前支持 gbk 或 utf-8
    private String input_charset = "utf-8";
    
    // 签名方式 不需修改
    private String sign_type = "RSA";

    private String seller = "sz-chelizi@qq.com";
    
    private String callback_url = "";
    
    private String chargeCallback_url="";
    
    /**
     * @return the partner
     */
    public String getPartner()
    {
        return partner;
    }

    /**
     * @param partner the partner to set
     */
    public void setPartner(String partner)
    {
        this.partner = partner;
    }

    /**
     * @return the ali_public_key
     */
    public String getAli_public_key()
    {
        return ali_public_key;
    }

    /**
     * @param ali_public_key the ali_public_key to set
     */
    public void setAli_public_key(String ali_public_key)
    {
        this.ali_public_key = ali_public_key;
    }

    /**
     * @return the log_path
     */
    public String getLog_path()
    {
        return log_path;
    }

    /**
     * @param log_path the log_path to set
     */
    public void setLog_path(String log_path)
    {
        this.log_path = log_path;
    }

    /**
     * @return the input_charset
     */
    public String getInput_charset()
    {
        return input_charset;
    }

    /**
     * @param input_charset the input_charset to set
     */
    public void setInput_charset(String input_charset)
    {
        this.input_charset = input_charset;
    }

    /**
     * @return the sign_type
     */
    public String getSign_type()
    {
        return sign_type;
    }

    /**
     * @param sign_type the sign_type to set
     */
    public void setSign_type(String sign_type)
    {
        this.sign_type = sign_type;
    }

    /**
     * @return the seller
     */
    public String getSeller()
    {
        return seller;
    }

    /**
     * @param seller the seller to set
     */
    public void setSeller(String seller)
    {
        this.seller = seller;
    }

    /**
     * @return the callback_url
     */
    public String getCallback_url()
    {
        return callback_url;
    }

    /**
     * @param callback_url the callback_url to set
     */
    public void setCallback_url(String callback_url)
    {
        this.callback_url = callback_url;
    }

    /**
     * @return the chargeCallback_url
     */
    public String getChargeCallback_url()
    {
        return chargeCallback_url;
    }

    /**
     * @param chargeCallback_url the chargeCallback_url to set
     */
    public void setChargeCallback_url(String chargeCallback_url)
    {
        this.chargeCallback_url = chargeCallback_url;
    }
    
    
}
