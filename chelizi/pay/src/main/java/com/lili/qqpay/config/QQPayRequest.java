/**
 * 
 */
package com.lili.qqpay.config;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import com.tencent.common.HttpUtil;
import com.tencent.common.Util;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

/**
 * @author linbo
 *
 */
public class QQPayRequest
{
    public String sendPost(String api_url, Object xmlObj) throws UnrecoverableKeyException, KeyManagementException,
            NoSuchAlgorithmException, KeyStoreException, IOException
    {
        // 解决XStream对出现双下划线的bug
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));

        // 将要提交给API的数据对象转换成XML格式数据Post给API
        //String postDataXML = xStreamForRequestPostData.toXML(xmlObj);
        xStreamForRequestPostData.alias("root", QQPayInitReq.class);
        xStreamForRequestPostData.aliasPackage("root", "com.lili.qqpay.config.QQPayInitReq");
        String newPostData = xStreamForRequestPostData.toXML(xmlObj);
        
        
        Util.log("API，POST过去的数据是：");
        Util.log(newPostData);

        // 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        String response = HttpUtil.doPost(api_url, newPostData, "UTF-8");
        return response;
    }
}
