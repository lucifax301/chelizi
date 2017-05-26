/**
 * 
 */
package com.tencent.protocol.callback.protocol;

/**
 * @author linbo
 * 返还给微信的消息体
 */
public class ReturnToWXReq
{
    private String return_code = "";
    private String return_msg = "";
    
    
    /**
     * @param return_code
     * @param return_msg
     */
    public ReturnToWXReq(String return_code, String return_msg)
    {
        super();
        this.return_code = return_code;
        this.return_msg = return_msg;
    }
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
    
    
}
