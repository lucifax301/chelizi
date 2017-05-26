package com.lili.proto;

public final class ProtocolOut
{
    /** 测试登陆 */
    public static final short COACH_LOGIN_ACK = 0x0001;
    
    /**教练上报*/
    public static final short COACH_REPORT_ACK = 0x0002;
    
    /**搜寻附近教练*/
    public static final short USER_SEARCH_ACK = 0x0003; 
    
    /**心跳协议*/
    public static final short USER_PING_ACK = 0x0004; 
}
