package com.lili.net;

/**
 * 辅助类
 * 
 */
public class MessageUtil
{

    /**
     * 消息创建
     * 
     * @param code
     * @param messageBuilder
     * @return
     */
    public static CommonMessage buildMessage(short code, byte[] bytes)
    {
        CommonMessage message = new CommonMessage(code);
        message.setBody(bytes);
        return message;
    }

    public static CommonMessage buildMessage(short code, int client,
            byte[] bytes)
    {
        // TODO:client 未使用
        CommonMessage message = new CommonMessage(code);
        message.setBody(bytes);
        return message;
    }

}
