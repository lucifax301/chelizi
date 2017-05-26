/**
 *
 */
package com.lili.pay.bill.wx;

import com.lili.common.util.TimeUtil;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linbo 微信支付帐单详情
 */
public class WXBillData {
    private Type type = Type.ALL;
    private int totalBill; // 总交易单数
    private int totalMoney; // 总交易额
    private int refundMoney; // 总退款金额
    private int redpacketMoney; // 总代金券或立减优惠退款金额
    private int totalFee; // 手续费总金额
    private List<WXBillDetail> billDetails = new ArrayList<>();// 具体的详情数据

    public WXBillData(Type type) {
        this.type = type;
    }

    /**
     * @return the billDetails
     */
    public List<WXBillDetail> getBillDetails() {
        return billDetails;
    }

    /**
     * @return the redpacketMoney
     */
    public int getRedpacketMoney() {
        return redpacketMoney;
    }

    /**
     * @return the refundMoney
     */
    public int getRefundMoney() {
        return refundMoney;
    }

    /**
     * @return the totalBill
     */
    public int getTotalBill() {
        return totalBill;
    }

    /**
     * @return the totalFee
     */
    public int getTotalFee() {
        return totalFee;
    }

    /**
     * @return the totalMoney
     */
    public int getTotalMoney() {
        return totalMoney;
    }

    public Type getType() {
        return type;
    }

    public void parseResponse(String response) {

        String[] lines = response.split("\n");

        switch (type) {
            case ALL: {
                parseResponseAll(lines);
                break;
            }
            case SUCCESS: {
                parseResponseSuccess(lines);
                break;
            }
            case REFUND: {
                parseResponseRefund(lines);
                break;
            }
            default:
                break;
        }
    }

    private void parseResponseAll(String[] lines) {
        // TODO Auto-generated method stub
    }

    private void parseResponseRefund(String[] lines) {
        // TODO Auto-generated method stub
    }

    private String formatCell(String cell) {
        return cell.replace("`", "").trim();
    }

    private void parseResponseSuccess(String[] lines) {
        // 第一行为标题数据，从第二行开始是详情数据，最后两行是汇总数据相关的
        for (int i = 1; i < lines.length - 3; i++) {
            String[] datas = lines[i].split(",");

            WXBillDetail billDetail = new WXBillDetail();
            billDetail.setTradetime(TimeUtil.parseDate(formatCell(datas[0])));
            billDetail.setGhid(formatCell(datas[1]));
            billDetail.setMchid(formatCell(datas[2]));
            billDetail.setSubmch(formatCell(datas[3]));
            billDetail.setDeviceid(formatCell(datas[4]));
            billDetail.setWxorder(formatCell(datas[5]));
            billDetail.setBzorder(formatCell(datas[6]));
            billDetail.setOpenid(formatCell(datas[7]));
            billDetail.setTradetype(formatCell(datas[8]));
            billDetail.setTradestatus(formatCell(datas[9]));
            billDetail.setBank(formatCell(datas[10]));
            billDetail.setCurrency(formatCell(datas[11]));
            billDetail.setTotalmoney((int) Float.parseFloat(formatCell(datas[12])) * 100);
            billDetail.setRedpacketmoney((int) Float.parseFloat(formatCell(datas[13])) * 100);
            billDetail.setProductname(formatCell(datas[14]));
            billDetail.setBzdatapacket(formatCell(datas[15]).replace("\\\\", ""));
            billDetail.setFee((int) Float.parseFloat(formatCell(datas[16])) * 100);
            billDetail.setRate(formatCell(datas[17]));

            billDetails.add(billDetail);
        }

        // 处理最后一行的汇总金额
        String lastDatas[] = lines[lines.length - 1].split(",");

        totalBill = Integer.parseInt(lastDatas[0].substring(1).trim());
        totalMoney = (int) Float.parseFloat(lastDatas[1].substring(1).trim()) * 100;
        refundMoney = (int) Float.parseFloat(lastDatas[2].substring(1).trim()) * 100;
        redpacketMoney = (int) Float.parseFloat(lastDatas[3].substring(1).trim()) * 100;
        totalFee = (int) Float.parseFloat(lastDatas[4].substring(1).trim()) * 100;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public enum Type {
        /**
         * 交易的所有对账单
         */
        ALL("ALL"), //
        /**
         * 交易成功的对账单
         */
        SUCCESS("SUCCESS"),
        /**
         * 交易失败的对账单
         */
        REFUND("REFUND");

        private String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
