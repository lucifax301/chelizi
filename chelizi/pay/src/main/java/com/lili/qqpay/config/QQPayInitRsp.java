/**
 * 
 */
package com.lili.qqpay.config;

/**
 * @author linbo
 *
 */
public class QQPayInitRsp
{
    //初始化成功的时候返回token_id
    private String token_id;
    
    //初始化失败的时候返回错误信息
    private String err_info;

    /**
     * @return the token_id
     */
    public String getToken_id()
    {
        return token_id;
    }

    /**
     * @param token_id the token_id to set
     */
    public void setToken_id(String token_id)
    {
        this.token_id = token_id;
    }

    /**
     * @return the err_info
     */
    public String getErr_info()
    {
        return err_info;
    }

    /**
     * @param err_info the err_info to set
     */
    public void setErr_info(String err_info)
    {
        this.err_info = err_info;
    }
    
    
}
