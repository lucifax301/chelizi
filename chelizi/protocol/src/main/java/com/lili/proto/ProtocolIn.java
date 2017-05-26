package com.lili.proto;

public final class ProtocolIn
{
    /** 教练登陆 */
    public static final short COACH_LOGIN = 0x0001;
    
    /** 教练位置数据上报*/
    public static final short USER_REPORT = 0x0002;
    
    /** 学员教练查询*/
    public static final short USER_SEARCH = 0x0003;
    
    /**心跳协议*/
    public static final short PING = 0x0004;
}
