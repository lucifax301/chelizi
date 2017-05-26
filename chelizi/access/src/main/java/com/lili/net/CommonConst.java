/**
 * Date: Mar 15, 2013
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.net;

/**
 * 公共常量定义
 * 
 * @author jiayi.wei
 */
public final class CommonConst
{
    /**
     * 防止实例化
     */
    private CommonConst()
    {
    }

    /** 客户端连接 */
    public static final String CLIENT_CONNECTION = "ClientConnection";

    /** 服务器连接器 */
    public static final String SERVER_CONNECTOR = "ServerConnector";

    /** 用户名 */
    public static final String USER_NAME = "UserName";

    /** 加密密钥 */
    public static final String ENCRYPTION_KEY = "EncryptionKey";

    /** 解密密钥 */
    public static final String DECRYPTION_KEY = "DecryptionKey";

    public static final int F_SERVER = 1;// 战斗服
    public static final int S_SERVER = 2;// 场景服
    public static final int F_S_SERVER = 3;// 场景服，战斗服皆可

}
