/**
 * 
 */
package com.lili.pay.bill.yl;

import com.lili.ylpay.sdk.DemoBase;
import com.lili.ylpay.sdk.SDKConfig;
import com.lili.ylpay.sdk.SDKUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author linbo
 *
 */
public class TestYLBill {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		
		SDKConfig.getConfig().loadPropertiesFromSrc();
		String merId = "898440373990118";
		String settleDate = "0318";
		
		Map<String, String> data = new HashMap<String, String>();
		
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		data.put("version", SDKUtil.version);               //版本号 全渠道默认值
		data.put("encoding", "UTF-8");             //字符集编码 可以使用UTF-8,GBK两种方式
		data.put("signMethod", "01");                        //签名方法 目前只支持01-RSA方式证书加密
		data.put("txnType", "76");                           //交易类型 76-对账文件下载
		data.put("txnSubType", "01");                        //交易子类型 01-对账文件下载
		data.put("bizType", "000000");                       //业务类型，固定

		/***商户接入参数***/
		data.put("accessType", "0");                         //接入类型，商户接入填0，不需修改
		data.put("merId", merId);                	         //商户代码，请替换正式商户号测试，如使用的是自助化平台注册的777开头的商户号，该商户号没有权限测文件下载接口的，请使用测试参数里写的文件下载的商户号和日期测。如需777商户号的真实交易的对账文件，请使用自助化平台下载文件。
		data.put("settleDate", settleDate);                  //清算日期，如果使用正式商户号测试则要修改成自己想要获取对账文件的日期， 测试环境如果使用700000000000001商户号则固定填写0119
		data.put("txnTime",DemoBase.getCurrentTime());       //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		data.put("fileType", "00");                          //文件类型，一般商户填写00即可

		/**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/

		Map<String, String> submitFromData = SDKUtil.signData(data,SDKUtil.encoding_UTF8);	//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		
		String url = SDKConfig.getConfig().getFileTransUrl();								//获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.fileTransUrl
	
		String reqMessage = DemoBase.genHtmlResult(submitFromData);
		Map<String, String> resmap = SDKUtil.submitUrl(submitFromData, url,SDKUtil.encoding_UTF8);
		
		/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
		
		//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
		if(("00").equals(resmap.get("respCode"))){
			//交易成功，解析返回报文中的fileContent并默认落地成文件
			SDKUtil.deCodeFileContent(resmap,"/Users/Poorzerg/Downloads",SDKUtil.encoding_UTF8);
			String result = SDKUtil.getFileContent(resmap.get("fileContent"),SDKUtil.encoding_UTF8);
			System.out.println(result);
			System.out.println();
			//TODO
		}else{
			//其他应答码为失败请排查原因
			//TODO
		}
		
		
		String rspMessage = DemoBase.genHtmlResult(resmap);
		System.out.println(rspMessage);
		//resp.getWriter().write("对账文件下载交易</br>请求报文:<br/>"+reqMessage+"<br/>" + "应答报文:</br>"+rspMessage+"");
	}

}
