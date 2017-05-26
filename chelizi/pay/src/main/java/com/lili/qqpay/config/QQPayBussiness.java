/**
 * 
 */
package com.lili.qqpay.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lili.common.vo.ReqResult;
import com.tencent.common.MD5;
import com.tencent.common.Util;
/**
 * @author linbo
 * QQ支付操作
 */
public class QQPayBussiness
{
    private static Logger logger = LoggerFactory.getLogger(QQPayBussiness.class);
    
    /**
     * 初始化QQ支付接口
     * @param qqPayInitReq
     * @return
     */
    public static ReqResult doQQPayInit(QQPayInitReq qqPayInitReq, String url)
    {
        ReqResult reqResult = ReqResult.getFailed();
        try
        {
            // 接受API返回
            String qqPayInitResponse;

            long costTimeStart = System.currentTimeMillis();

            logger.info("支付API返回的数据如下：");
            qqPayInitResponse = new QQPayRequest().sendPost(url, qqPayInitReq);

            long costTimeEnd = System.currentTimeMillis();
            long totalTimeCost = costTimeEnd - costTimeStart;
            logger.info("api请求总耗时：" + totalTimeCost + "ms");

            // 打印回包数据
            logger.info(qqPayInitResponse);

            // 将从API返回的XML数据映射到Java对象
            QQPayInitRsp qqPayInitResp = (QQPayInitRsp) Util.getObjectFromXML(
                    qqPayInitResponse, QQPayInitRsp.class, "root");
            
            if (qqPayInitResp != null)
            {
                if (qqPayInitResp.getToken_id() != null && !qqPayInitResp.getToken_id().isEmpty())
                {
                    reqResult = ReqResult.getSuccess();
                    reqResult.setMsgInfo(qqPayInitResp.getToken_id());
                    return reqResult;
                }
                else
                {
                    reqResult.setMsgInfo(qqPayInitResp.getErr_info());
                }
            }

        }
        catch (Exception e)
        {
            logger.error("doQQPayInit:" + qqPayInitReq, e);
        }
        return reqResult;
    }
    
    public static String getSign(Map<String,Object> map, String key){
        ArrayList<String> list = new ArrayList<String>();
        for(Map.Entry<String,Object> entry:map.entrySet()){
            if(!entry.getValue().equals("")){
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        
        String result = sb.toString();
        //Util.log("Sign Before MD5:" + result);
        result += "key=" + key;
        //Util.log("Sign Before MD5:" + result);
        result = MD5.MD5Encode(result).toUpperCase();
        //Util.log("Sign Result:" + result);
        return result;
    }
}
