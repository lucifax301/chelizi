/**
 * 
 */
package com.lili.pay.bill.qq;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.tencent.common.MD5;

/**
 * @author linbo
 *
 */
public class QQDownBillReq {
	private String spid;			//商户号Id
	private String trans_time;		//订单日期
	private String stamp;			//当前时间
	private int	cft_signtype;		//返回结果签名类型
	private int mchtype;			//订单类型
	private String sign;			//签名
	
	/**
	 * @param spid
	 * @param trans_time
	 * @param stamp
	 * @param cft_signtype
	 * @param mchtype
	 */
	public QQDownBillReq(String spid, String trans_time, String stamp,
			int cft_signtype, int mchtype, String key) {
		super();
		this.spid = spid;
		this.trans_time = trans_time;
		this.stamp = stamp;
		this.cft_signtype = cft_signtype;
		this.mchtype = mchtype;
		
		//根据API给的签名规则进行签名
        String sign = getSign(spid, trans_time, stamp, cft_signtype, mchtype, key);
        setSign(sign);//把签名数据设置到Sign这个属性中
		
	}
	
	private String getSign(String spid, String trans_time, String stamp,
			int cft_signtype, int mchtype, String key)
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("spid=");
		stringBuilder.append(spid);
		stringBuilder.append("&");
		stringBuilder.append("trans_time=");
		stringBuilder.append(trans_time);
		stringBuilder.append("&");
		stringBuilder.append("stamp=");
		stringBuilder.append(stamp);
		stringBuilder.append("&");
		stringBuilder.append("cft_signtype=");
		stringBuilder.append(cft_signtype);
		stringBuilder.append("&");
		stringBuilder.append("mchtype=");
		stringBuilder.append(mchtype);
		stringBuilder.append("&");
		stringBuilder.append("key=");
		stringBuilder.append(key);
		return MD5.MD5Encode(stringBuilder.toString()).toLowerCase();
	}
	/**
	 * @return the spid
	 */
	public String getSpid() {
		return spid;
	}
	/**
	 * @param spid the spid to set
	 */
	public void setSpid(String spid) {
		this.spid = spid;
	}
	/**
	 * @return the trans_time
	 */
	public String getTrans_time() {
		return trans_time;
	}
	/**
	 * @param trans_time the trans_time to set
	 */
	public void setTrans_time(String trans_time) {
		this.trans_time = trans_time;
	}
	/**
	 * @return the stamp
	 */
	public String getStamp() {
		return stamp;
	}
	/**
	 * @param stamp the stamp to set
	 */
	public void setStamp(String stamp) {
		this.stamp = stamp;
	}
	/**
	 * @return the cft_signtype
	 */
	public int getCft_signtype() {
		return cft_signtype;
	}
	/**
	 * @param cft_signtype the cft_signtype to set
	 */
	public void setCft_signtype(int cft_signtype) {
		this.cft_signtype = cft_signtype;
	}
	/**
	 * @return the mchtype
	 */
	public int getMchtype() {
		return mchtype;
	}
	/**
	 * @param mchtype the mchtype to set
	 */
	public void setMchtype(int mchtype) {
		this.mchtype = mchtype;
	}
	
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
	
	public Map<String,String> toStringMap(){
        Map<String,String> map = new HashMap<String, String>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if(obj!=null){
                    map.put(field.getName(), String.valueOf(obj));
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
	
	/**
	 * @return the sign
	 */
	public String getSign() {
		return sign;
	}
	/**
	 * @param sign the sign to set
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}
}
