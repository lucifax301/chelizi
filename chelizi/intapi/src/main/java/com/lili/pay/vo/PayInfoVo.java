/**
 * 
 */
package com.lili.pay.vo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author linbo
 * 
 */
public class PayInfoVo implements Serializable
{
    private static final long serialVersionUID = -4084223703231453164L;
 
    private String nonce_str = "";
    private String sign = "";
    private String prepay_id = "";
    private String appId = "";
    private String partnerid="";
    private String packageString = "";
    private String timestamp="";
    
  
    /**
     * @return the nonce_str
     */
    public String getNonce_str()
    {
        return nonce_str;
    }
    /**
     * @param nonce_str the nonce_str to set
     */
    public void setNonce_str(String nonce_str)
    {
        this.nonce_str = nonce_str;
    }
    /**
     * @return the sign
     */
    public String getSign()
    {
        return sign;
    }
    /**
     * @param sign the sign to set
     */
    public void setSign(String sign)
    {
        this.sign = sign;
    }
  
    /**
     * @return the appId
     */
    public String getAppId()
    {
        return appId;
    }
    /**
     * @param appId the appId to set
     */
    public void setAppId(String appId)
    {
        this.appId = appId;
    }
    /**
     * @return the partnerid
     */
    public String getPartnerid()
    {
        return partnerid;
    }
    /**
     * @param partnerid the partnerid to set
     */
    public void setPartnerid(String partnerid)
    {
        this.partnerid = partnerid;
    }
    /**
     * @return the packageString
     */
    public String getPackageString()
    {
        return packageString;
    }
    /**
     * @param packageString the packageString to set
     */
    public void setPackageString(String packageString)
    {
        this.packageString = packageString;
    }
    /**
     * @return the timestamp
     */
    public String getTimestamp()
    {
        return timestamp;
    }
    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }
    /**
     * @return the prepay_id
     */
    public String getPrepay_id()
    {
        return prepay_id;
    }
    /**
     * @param prepay_id the prepay_id to set
     */
    public void setPrepay_id(String prepay_id)
    {
        this.prepay_id = prepay_id;
    }
    /**
     * @return
     */
    public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<String, Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if(obj!=null){
                    map.put(field.getName(), obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
