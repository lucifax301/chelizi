/**
 * 
 */
package com.tencent.protocol.callback.protocol;

/**
 * @author linbo
 *
 */
public class PayCallbackResData
{
    //协议层
    private String return_code = "";
    private String return_msg = "";

    //协议返回的具体数据（以下字段在return_code 为SUCCESS 的时候有返回）
    private String appid = "";
    private String mch_id = "";
    private String nonce_str = "";
    private String sign = "";
    private String result_code = "";
    private String err_code = "";
    private String err_code_des = "";
    private String device_info = "";

    //业务返回的具体数据（以下字段在return_code 和result_code 都为SUCCESS 的时候有返回）
    private String openid = "";
    private String is_subscribe = "";
    private String trade_type = "";
    private String bank_type = "";
    private String total_fee = "";
    private String cash_fee = "";
    private String coupon_fee = "";
    private String fee_type = "";
    private String transaction_id = "";
    private String out_trade_no = "";
    private String attach = "";
    private String time_end = "";
    
    /**
     * @return the return_code
     */
    public String getReturn_code()
    {
        return return_code;
    }
    /**
     * @param return_code the return_code to set
     */
    public void setReturn_code(String return_code)
    {
        this.return_code = return_code;
    }
    /**
     * @return the return_msg
     */
    public String getReturn_msg()
    {
        return return_msg;
    }
    /**
     * @param return_msg the return_msg to set
     */
    public void setReturn_msg(String return_msg)
    {
        this.return_msg = return_msg;
    }
    /**
     * @return the appid
     */
    public String getAppid()
    {
        return appid;
    }
    /**
     * @param appid the appid to set
     */
    public void setAppid(String appid)
    {
        this.appid = appid;
    }
    /**
     * @return the mch_id
     */
    public String getMch_id()
    {
        return mch_id;
    }
    /**
     * @param mch_id the mch_id to set
     */
    public void setMch_id(String mch_id)
    {
        this.mch_id = mch_id;
    }
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
     * @return the result_code
     */
    public String getResult_code()
    {
        return result_code;
    }
    /**
     * @param result_code the result_code to set
     */
    public void setResult_code(String result_code)
    {
        this.result_code = result_code;
    }
    /**
     * @return the err_code
     */
    public String getErr_code()
    {
        return err_code;
    }
    /**
     * @param err_code the err_code to set
     */
    public void setErr_code(String err_code)
    {
        this.err_code = err_code;
    }
    /**
     * @return the err_code_des
     */
    public String getErr_code_des()
    {
        return err_code_des;
    }
    /**
     * @param err_code_des the err_code_des to set
     */
    public void setErr_code_des(String err_code_des)
    {
        this.err_code_des = err_code_des;
    }
    /**
     * @return the device_info
     */
    public String getDevice_info()
    {
        return device_info;
    }
    /**
     * @param device_info the device_info to set
     */
    public void setDevice_info(String device_info)
    {
        this.device_info = device_info;
    }
    /**
     * @return the openid
     */
    public String getOpenid()
    {
        return openid;
    }
    /**
     * @param openid the openid to set
     */
    public void setOpenid(String openid)
    {
        this.openid = openid;
    }
    /**
     * @return the is_subscribe
     */
    public String getIs_subscribe()
    {
        return is_subscribe;
    }
    /**
     * @param is_subscribe the is_subscribe to set
     */
    public void setIs_subscribe(String is_subscribe)
    {
        this.is_subscribe = is_subscribe;
    }
    /**
     * @return the trade_type
     */
    public String getTrade_type()
    {
        return trade_type;
    }
    /**
     * @param trade_type the trade_type to set
     */
    public void setTrade_type(String trade_type)
    {
        this.trade_type = trade_type;
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
     * @return the total_fee
     */
    public String getTotal_fee()
    {
        return total_fee;
    }
    /**
     * @param total_fee the total_fee to set
     */
    public void setTotal_fee(String total_fee)
    {
        this.total_fee = total_fee;
    }
    /**
     * @return the cash_fee
     */
    public String getCash_fee()
    {
        return cash_fee;
    }
    /**
     * @param cash_fee the cash_fee to set
     */
    public void setCash_fee(String cash_fee)
    {
        this.cash_fee = cash_fee;
    }
    /**
     * @return the coupon_fee
     */
    public String getCoupon_fee()
    {
        return coupon_fee;
    }
    /**
     * @param coupon_fee the coupon_fee to set
     */
    public void setCoupon_fee(String coupon_fee)
    {
        this.coupon_fee = coupon_fee;
    }
    /**
     * @return the fee_type
     */
    public String getFee_type()
    {
        return fee_type;
    }
    /**
     * @param fee_type the fee_type to set
     */
    public void setFee_type(String fee_type)
    {
        this.fee_type = fee_type;
    }
    /**
     * @return the transaction_id
     */
    public String getTransaction_id()
    {
        return transaction_id;
    }
    /**
     * @param transaction_id the transaction_id to set
     */
    public void setTransaction_id(String transaction_id)
    {
        this.transaction_id = transaction_id;
    }
    /**
     * @return the out_trade_no
     */
    public String getOut_trade_no()
    {
        return out_trade_no;
    }
    /**
     * @param out_trade_no the out_trade_no to set
     */
    public void setOut_trade_no(String out_trade_no)
    {
        this.out_trade_no = out_trade_no;
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
     * @return the time_end
     */
    public String getTime_end()
    {
        return time_end;
    }
    /**
     * @param time_end the time_end to set
     */
    public void setTime_end(String time_end)
    {
        this.time_end = time_end;
    }
}
