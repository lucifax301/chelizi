/**
 * 
 */
package com.lili.pay.bill.alipay;

import com.alipay.config.AlipayConfig;
import com.alipay.sign.AlipaySubmit;
import com.lili.pay.bill.alipay.AlipayData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author linbo
 *
 */
public class TestZFBBill {
      
	public static String qryAlipaylos(String gmt_start_time, String gmt_end_time) {
		// ***AlipaySearch 记录了支付宝查询后的帐务明细列表
		//List<?> alitemp = new ArrayList<Object>();

		String page_no = "1";
		// //////////////// 把请求参数打包成数组,发给支付宝
		Map<String, String> sParaTemp = new HashMap<String, String>();
		// 输入必须输参数
		sParaTemp.put("service", "account.page.query");
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("_input_charset", "utf-8");
		sParaTemp.put("page_no", page_no);
		sParaTemp.put("gmt_start_time", gmt_start_time);
		sParaTemp.put("gmt_end_time", gmt_end_time);
		// 打包参数，用作验签的时候用

		URL url = null;
		URLConnection connec = null;
		BufferedReader reader = null;
		InputStream input = null;
		try {

			// ***************拼接发送给支付宝的地址
			StringBuffer urlBuffer = new StringBuffer();
			urlBuffer.append("https://mapi.alipay.com/gateway.do?");
			// _input_charset
			urlBuffer.append("_input_charset=utf-8");
			// sign
			urlBuffer.append("&sign=").append(AlipaySubmit.buildRequestMysign(sParaTemp, "MD5"));
			// gmt_end_time
			urlBuffer.append("&gmt_end_time=").append(URLEncoder.encode(gmt_end_time, "utf-8"));
			// sign_type
			urlBuffer.append("&sign_type=").append("MD5");
			// service
			urlBuffer.append("&service=").append("account.page.query");
			// partner
			urlBuffer.append("&partner=").append(AlipayConfig.partner);
			// page_no
			urlBuffer.append("&page_no=").append(page_no);
			// gmt_start_time
			urlBuffer.append("&gmt_start_time=").append(
					URLEncoder.encode(gmt_start_time, "utf-8"));

			// ***********将拼接好的地址转换为一个URL,然后执行openConnection方法，打开发完支付宝
			url = new URL(urlBuffer.toString());
			System.out.println(urlBuffer.toString());
			connec = url.openConnection();

			// ***********将返回的URLConnection获取 getInputStream返回的字符流InputStream
			input = connec.getInputStream();

			// *********将返回的字符流转换为BufferedReader
			reader = new BufferedReader(new InputStreamReader(input, "utf-8"));
			StringBuffer sb = new StringBuffer();
			String strTemp = null;
			// ******** 将BufferedReader 转化为StringBuffer
			while ((strTemp = reader.readLine()) != null) {
				sb.append(strTemp);
			}
			// StringBuffer转化为String
			String res = sb.toString();
			// 转化成一个xml格式的文档，碰到“><”就换行
			String rexml = res.replace("><", ">\n<");
			return rexml;
			// 解析返回的xml，获取返回的支付宝帐务列表
			// alitemp = jiexiAlis(rexml);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e.getMessage());
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(qryAlipaylos("2016-02-15 01:01:01", "2016-02-16 01:01:01"));
	}

	public static List<AlipayData> jiexiAlis(String str) {

        List<AlipayData> exlist = new ArrayList<AlipayData>();
       //采用 Document方式解析XML
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // 通过解析器工厂创建解析 器
            DocumentBuilder db = dbf.newDocumentBuilder();
            // 将该字符串转为InputStream流
            InputStream iStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
            // 获取xml Document
            Document dm = db.parse(iStream);
            // 得到所有Route节点
            NodeList routes = dm
                    .getElementsByTagName("AccountQueryAccountLogVO");
            if(routes.getLength()>0){
            for (int i = 0; i < routes.getLength(); i++) {
                Element element = (Element) routes.item(i);
                // 获取Route节点的 具体描述 属性

                // 余额
                String balance = element.getElementsByTagName("balance")
                        .item(0).getFirstChild().getNodeValue();
                // 收入金额
                String income = element.getElementsByTagName("income").item(0)
                        .getFirstChild().getNodeValue();
                // 支出金额
                String outcome = element.getElementsByTagName("outcome")
                        .item(0).getFirstChild().getNodeValue();
                // 付款时间
                String trans_date = element.getElementsByTagName("trans_date")
                        .item(0).getFirstChild().getNodeValue();
                // 买家支付宝人民币资金帐号 （user_id+0156）
                String buyer_account = element
                        .getElementsByTagName("buyer_account").item(0)
                        .getFirstChild().getNodeValue();
                // 币种 156-人民币
                String currency = element.getElementsByTagName("currency")
                        .item(0).getFirstChild().getNodeValue();
                // 充值网银流水号
                String deposit_bank_no = element
                        .getElementsByTagName("deposit_bank_no").item(0)
                        .getFirstChild().getNodeValue();
                // 订单名称
                String goods_title = element
                        .getElementsByTagName("goods_title").item(0)
                        .getFirstChild().getNodeValue();
                // 支付宝帐务序列号
                String iw_account_log_id = element
                        .getElementsByTagName("iw_account_log_id").item(0)
                        .getFirstChild().getNodeValue();
                // 备注
                String memo = element.getElementsByTagName("memo").item(0)
                        .getFirstChild().getNodeValue();
                // 商城订单号
                String merchant_out_order_no = element
                        .getElementsByTagName("merchant_out_order_no").item(0)
                        .getFirstChild().getNodeValue();
                // 合作者身份id
                String partner_id = element.getElementsByTagName("partner_id")
                        .item(0).getFirstChild().getNodeValue();
                // 该笔业务对应的支付宝收费费率
                String rate =null;
                if(element.getElementsByTagName("rate").item(0).getFirstChild().getNodeValue()!=null){
                    rate = element.getElementsByTagName("rate").item(0)
                            .getFirstChild().getNodeValue();    
                }
                // 买家支付宝资金帐号（卖家+0156）
                String seller_account = element
                        .getElementsByTagName("seller_account").item(0)
                        .getFirstChild().getNodeValue();
                // 卖家姓名
                String seller_fullname = element
                        .getElementsByTagName("seller_fullname").item(0)
                        .getFirstChild().getNodeValue();
                // 交易服务费
                String service_fee = element
                        .getElementsByTagName("service_fee").item(0)
                        .getFirstChild().getNodeValue();
                // 交易服务费率
                String service_fee_ratio = element
                        .getElementsByTagName("service_fee_ratio").item(0)
                        .getFirstChild().getNodeValue();
                // 支付宝签约接口
                String sign_product_name = element
                        .getElementsByTagName("sign_product_name").item(0)
                        .getFirstChild().getNodeValue();
                // 子业务类型
                String sub_trans_code_msg = element
                        .getElementsByTagName("sub_trans_code_msg").item(0)
                        .getFirstChild().getNodeValue();
                // 交易总金额
                String total_fee = element.getElementsByTagName("total_fee")
                        .item(0).getFirstChild().getNodeValue();
                // 支付宝交易号
                String trade_no = element.getElementsByTagName("trade_no")
                        .item(0).getFirstChild().getNodeValue();
                // 累计退款金额
                String trade_refund_amount = element
                        .getElementsByTagName("trade_refund_amount").item(0)
                        .getFirstChild().getNodeValue();
                // 业务类型
                String trans_code_msg = element
                        .getElementsByTagName("trans_code_msg").item(0)
                        .getFirstChild().getNodeValue();

                AlipayData e = new AlipayData();
                e.setBalance(balance);
                e.setBuyer_account(buyer_account);
                e.setCurrency(currency);
                e.setDeposit_bank_no(deposit_bank_no);
                e.setGoods_title(goods_title);
                e.setIncome(income);
                e.setIw_account_log_id(iw_account_log_id);
                e.setMemo(memo);
                e.setMerchant_out_order_no(merchant_out_order_no);
                e.setOutcome(outcome);
                e.setPartner_id(partner_id);
                e.setRate(rate);
                e.setSeller_account(seller_account);
                e.setSeller_fullname(seller_fullname);
                e.setService_fee(service_fee);
                e.setService_fee_ratio(service_fee_ratio);
                e.setSign_product_name(sign_product_name);
                e.setSub_trans_code_msg(sub_trans_code_msg);
                e.setTotal_fee(total_fee);
                e.setTrade_no(trade_no);
                e.setTrade_refund_amount(trade_refund_amount);
                e.setTrans_code_msg(trans_code_msg);
                e.setTrans_date(trans_date);
                exlist.add(e);
            }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
       //返回支付宝的帐务明细列表
        return exlist;
    } 
}
