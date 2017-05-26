/**
 * 
 */
package com.lili.pay.bill.qq;

import com.lili.qqpay.config.QQPayBussiness;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author linbo
 * 财付通请求初始化订单的参数
 */
public class QQPayInitReq
{
    private String ver;
    private String charset;
    private String bank_type;
    private String desc;
    private String purchaser_id;
    private String pay_channel;
    private String bargainor_id;
    private String sp_billno;
    private int total_fee;
    private int fee_type;
    private String notify_url;
    private String attach;
    private String time_start;
    private String time_expire;
    private String sign;
    
    /**
     * @param desc
     * @param sp_billno
     * @param total_fee
     * @param notify_url
     * @param attach
     * @param time_start
     * @param time_expire
     */
    public QQPayInitReq(String bargainorid, String desc, String sp_billno, int total_fee, String notify_url, String attach,
            String time_start, String time_expire, String key)
    {
        super();
        
        //目前版本号2.0
        setVer("1.0");
        
        //默认UTF-8
        setCharset("1");
        
        //银行类型
        setBank_type("0");
        
        //商品描述
        setDesc(desc);
        
        //买方QQ号,不填
        setPurchaser_id("");
        
        //支付渠道,1为QQ钱包
        setPay_channel("1");
        
        //商户号
        setBargainor_id(bargainorid);
        
        //商户订单号
        setSp_billno(sp_billno);
        
        //总金额，单位分
        setTotal_fee(total_fee);
        
        //币种，1人民币
        setFee_type(1);
        
        //通知地址
        setNotify_url(notify_url);
        
        //附加消息
        setAttach(attach);
        
        //订单生成时间
        setTime_start(time_start);
        
        //订单超时时间
        setTime_expire(time_expire);
        
        //根据API给的签名规则进行签名
        String sign = QQPayBussiness.getSign(toMap(), key);
        setSign(sign);//把签名数据设置到Sign这个属性中
    }
    /**
     * @return the ver
     */
    public String getVer()
    {
        return ver;
    }
    /**
     * @param ver the ver to set
     */
    public void setVer(String ver)
    {
        this.ver = ver;
    }
    /**
     * @return the charset
     */
    public String getCharset()
    {
        return charset;
    }
    /**
     * @param charset the charset to set
     */
    public void setCharset(String charset)
    {
        this.charset = charset;
    }
    /**
     * @return the bank_type
     */
    public String getBank_type()
    {
        return bank_type;
    }
    /**
     * @param bank_type the bank_type to set
     */
    public void setBank_type(String bank_type)
    {
        this.bank_type = bank_type;
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
     * @return the purchaser_id
     */
    public String getPurchaser_id()
    {
        return purchaser_id;
    }
    /**
     * @param purchaser_id the purchaser_id to set
     */
    public void setPurchaser_id(String purchaser_id)
    {
        this.purchaser_id = purchaser_id;
    }
    /**
     * @return the bargainor_id
     */
    public String getBargainor_id()
    {
        return bargainor_id;
    }
    /**
     * @param bargainor_id the bargainor_id to set
     */
    public void setBargainor_id(String bargainor_id)
    {
        this.bargainor_id = bargainor_id;
    }
    /**
     * @return the sp_billno
     */
    public String getSp_billno()
    {
        return sp_billno;
    }
    /**
     * @param sp_billno the sp_billno to set
     */
    public void setSp_billno(String sp_billno)
    {
        this.sp_billno = sp_billno;
    }
    /**
     * @return the total_fee
     */
    public int getTotal_fee()
    {
        return total_fee;
    }
    /**
     * @param total_fee the total_fee to set
     */
    public void setTotal_fee(int total_fee)
    {
        this.total_fee = total_fee;
    }
    /**
     * @return the fee_type
     */
    public int getFee_type()
    {
        return fee_type;
    }
    /**
     * @param fee_type the fee_type to set
     */
    public void setFee_type(int fee_type)
    {
        this.fee_type = fee_type;
    }
    /**
     * @return the notify_url
     */
    public String getNotify_url()
    {
        return notify_url;
    }
    /**
     * @param notify_url the notify_url to set
     */
    public void setNotify_url(String notify_url)
    {
        this.notify_url = notify_url;
    }
    /**
     * @return the attach
     */
    public String getAttach()
    {
        return attach;
    }
    /**
     * @param attach the attach to set
     */
    public void setAttach(String attach)
    {
        this.attach = attach;
    }
    /**
     * @return the time_start
     */
    public String getTime_start()
    {
        return time_start;
    }
    /**
     * @param time_start the time_start to set
     */
    public void setTime_start(String time_start)
    {
        this.time_start = time_start;
    }
    /**
     * @return the time_expire
     */
    public String getTime_expire()
    {
        return time_expire;
    }
    /**
     * @param time_expire the time_expire to set
     */
    public void setTime_expire(String time_expire)
    {
        this.time_expire = time_expire;
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
     * @return the pay_channel
     */
    public String getPay_channel()
    {
        return pay_channel;
    }
    /**
     * @param pay_channel the pay_channel to set
     */
    public void setPay_channel(String pay_channel)
    {
        this.pay_channel = pay_channel;
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
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "QQPayInitReq [ver=" + ver + ", charset=" + charset + ", bank_type=" + bank_type + ", desc=" + desc
                + ", purchaser_id=" + purchaser_id + ", pay_channel=" + pay_channel + ", bargainor_id=" + bargainor_id
                + ", sp_billno=" + sp_billno + ", total_fee=" + total_fee + ", fee_type=" + fee_type + ", notify_url="
                + notify_url + ", attach=" + attach + ", time_start=" + time_start + ", time_expire=" + time_expire
                + ", sign=" + sign + "]";
    }
}
