/**
 *
 */
package com.lili.pay.bill.wx;

import com.tencent.WXPay;
import com.tencent.business.DownloadBillBusiness.ResultListener;
import com.tencent.protocol.downloadbill_protocol.DownloadBillReqData;
import com.tencent.protocol.downloadbill_protocol.DownloadBillResData;

/**
 * @author linbo 测试微信下载对帐单
 */
public class TestWXBill {

    /**
     * @param args
     */
    public static void main(String[] args) {
        DownloadBillReqData downloadBillReqData = new DownloadBillReqData(//
                "", //
                "20160320", //
                WXBillData.Type.SUCCESS.getName(), //
                "wx413688e9f31ae58e", //
                "f62b52937f57672cb5a565186f11407F", //
                "1280505801");
        try {
            WXPay.doDownloadBillBusiness(//
                    downloadBillReqData, //
                    "f62b52937f57672cb5a565186f11407F", //
                    "1280505801", //
                    new ResultListener() {

                        @Override
                        public void onFailByReturnCodeFail(DownloadBillResData downloadBillResData) {
                            System.out.println(downloadBillResData.getReturn_code());
                            System.out.println(downloadBillResData.getReturn_msg());
                        }

                        @Override
                        public void onFailByReturnCodeError(DownloadBillResData downloadBillResData) {
                            System.out.println(downloadBillResData.getReturn_code());
                            System.out.println(downloadBillResData.getReturn_msg());

                        }

                        @Override
                        public void onDownloadBillSuccess(String response) {
                            WXBillData wxBillData = new WXBillData(WXBillData.Type.SUCCESS);
                            wxBillData.parseResponse(response);
                            System.out.println(wxBillData);
                        }

                        @Override
                        public void onDownloadBillFail(String downloadBillResData) {
                            System.out.println(downloadBillResData);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
