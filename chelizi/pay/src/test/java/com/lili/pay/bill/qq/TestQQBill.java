/**
 *
 */
package com.lili.pay.bill.qq;

import com.lili.common.util.TimeUtil;
import com.lili.pay.bill.qq.QQDownBillBussiness;
import com.lili.pay.bill.qq.QQDownBillReq;
import com.lili.pay.bill.qq.QQBillData;
import com.lili.pay.bill.qq.QQBillDetail;


/**
 * @author linbo
 *         测试QQ支付对帐单
 */
public class TestQQBill {

    /**
     * @param args
     */
    public static void main(String[] args) {
        QQDownBillReq qqDownBillReq = new QQDownBillReq("1309317901", "2016-02-18", String.valueOf(System.currentTimeMillis() / 1000L), 0, 1, "3db1ecfe440fd7feda170b46bae79d28");
        new QQDownBillBussiness().doQQDownloadBill(qqDownBillReq, "http://mch.tenpay.com/cgi-bin/mchdown_real_new.cgi", new QQDownBillBussiness.ResultListener() {

            @Override
            public void onDownloadBillSuccess(String response) {
                String[] lines = response.split("\n");
                QQBillData qqBillData = new QQBillData();

                for (int j = 1; j < (lines.length - 3); j++) {
                    String[] data = lines[j].split(",", -1);

                    QQBillDetail qqBillDetail = new QQBillDetail();
                    qqBillDetail.setPayDate(TimeUtil.parseDate(formatCell(data[0])));
                    qqBillDetail.setCftOrder(formatCell(data[1]));
                    qqBillDetail.setMchOrder(formatCell(data[2]));
                    qqBillDetail.setPayType(formatCell(data[3]));
                    qqBillDetail.setBankOrder(formatCell(data[4]));
                    qqBillDetail.setPayMoney((int) (Float.parseFloat(formatCell(data[5])) * 100));
                    qqBillDetail.setOrderState(formatCell(data[6]));
                    qqBillDetail.setPayDesc(formatCell(data[7]));
                    qqBillData.addBillDetail(qqBillDetail);
                }
                String[] lastData = lines[lines.length - 1].split(",", -1);
                qqBillData.setTotalBill(Integer.parseInt(lastData[0]));
                qqBillData.setTotalFee((int) (Float.parseFloat(lastData[1]) * 100));
            }

            private String formatCell(String cell) {
                return cell.replace("`", "").trim();
            }

            @Override
            public void onDownloadBillFail(String response) {
                System.out.println(response);
            }
        });
    }


}
