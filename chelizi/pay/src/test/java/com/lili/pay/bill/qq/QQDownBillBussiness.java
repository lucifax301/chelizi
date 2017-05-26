/**
 * 
 */
package com.lili.pay.bill.qq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

/**
 * @author linbo
 *
 */
public class QQDownBillBussiness {
	
	private static Logger logger = LoggerFactory.getLogger(QQDownBillBussiness.class);
	
	public void doQQDownloadBill(QQDownBillReq qqDownBillReq, String url, ResultListener resultListener)
	{
		// 接受API返回
        String qqDownBillResponse="no data";

        long costTimeStart = System.currentTimeMillis();

        logger.info("下载对帐单返回的数据如下：");
        try {
			qqDownBillResponse = new QQDownBillRequest().sendPost(url, qqDownBillReq.toStringMap());
		} catch (UnrecoverableKeyException | KeyManagementException
				| NoSuchAlgorithmException | KeyStoreException | IOException e) {
			e.printStackTrace();
		}

        long costTimeEnd = System.currentTimeMillis();
        long totalTimeCost = costTimeEnd - costTimeStart;
        logger.info("api请求总耗时：" + totalTimeCost + "ms");

        // 打印回包数据
        logger.info(qqDownBillResponse);
        
        if (qqDownBillResponse.contains("html")) {
        	resultListener.onDownloadBillFail(qqDownBillResponse);
		}
        else {
			resultListener.onDownloadBillSuccess(qqDownBillResponse);
		}
	}
    
	public interface ResultListener{

        //下载对账单失败
        void onDownloadBillFail(String response);

        //下载对账单成功
        void onDownloadBillSuccess(String response);

    }
}
