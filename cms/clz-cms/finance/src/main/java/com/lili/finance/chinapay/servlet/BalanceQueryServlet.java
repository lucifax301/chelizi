package com.lili.finance.chinapay.servlet;


/**
 * @author huang.xuting
 *
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.lili.finance.chinapay.model.bean.TransactionBean;
import com.lili.finance.chinapay.util.Config;

import chinapay.Base64;
import chinapay.PrivateKey;
import chinapay.SecureLink;

@SuppressWarnings("serial")
public class BalanceQueryServlet extends HttpServlet {
	
	private static final String KEY_CHINAPAY_MERID = "chinapay.merid";
	private static final String PaymentUrl = "chinapay.BalanceQuery.url";
	private static final String KEY_CHINAPAY_MERKEY_FILEPATH = "chinapay.merkey.filepath";
	private static final String KEY_CHINAPAY_PUBKEY_FILEPATH = "chinapay.pubkey.filepath";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String MerKeyPath = null;
		String PubKeyPath = null;
		String pay_url = null;
		Properties config = Config.getInstance().getProperties();
		MerKeyPath = config.getProperty(KEY_CHINAPAY_MERKEY_FILEPATH);
		PubKeyPath = config.getProperty(KEY_CHINAPAY_PUBKEY_FILEPATH); 
		pay_url = config.getProperty(PaymentUrl);                      

		System.out.println(PubKeyPath);
		
		request.setCharacterEncoding("GBK");
		
		String merId = config.getProperty(KEY_CHINAPAY_MERID);
		String version = "20090501";
		String signFlag = "1";
		
		String Data = merId + version;
		System.out.println("字符串数据拼装结果：" + Data);
		String plainData = new String(Base64.encode(Data.getBytes()));
		System.out.println("转换成Base64后数据：" + plainData);
		
        //签名
		String chkValue = null;
		int KeyUsage = 0;
		PrivateKey key = new PrivateKey();
	    key.buildKey(merId, KeyUsage, MerKeyPath);
		SecureLink sl = new SecureLink(key);
		chkValue = sl.Sign(plainData);
		System.out.println("签名内容:"+ chkValue);
		
		
		HttpClient httpClient = new HttpClient();
		System.out.println("HttpClient方法创建！");
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");
		String url = pay_url;
		System.out.println(url);
		PostMethod postMethod = new PostMethod(url);
		System.out.println("Post方法创建！");
		//填入各个表单域的值
		NameValuePair[] data = { 
				new NameValuePair("merId", merId),
 				new NameValuePair("version", version),
				new NameValuePair("chkValue",chkValue),
				new NameValuePair("signFlag", signFlag)
		};
		
		//System.out.println(data);
		
		// 将表单的值放入postMethod中
		postMethod.setRequestBody(data);
		// 执行postMethod
		try {
			httpClient.executeMethod(postMethod);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 读取内容
		InputStream resInputStream = null;
		try {
			resInputStream = postMethod.getResponseBodyAsStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 处理内容
		BufferedReader reader = new BufferedReader(new InputStreamReader(resInputStream));
		String tempBf = null;
		StringBuffer html=new StringBuffer(); 
		while((tempBf = reader.readLine()) != null){ 
			
			html.append(tempBf); 
		}
		String resMes = html.toString();
		System.out.println("备付金余额查询返回报文：" + resMes);
		int dex = resMes.lastIndexOf("|");
		String Res_Code = resMes.substring(0,3);
			
		//提取返回数据
		if(Res_Code.equals("000")){
			String Res_merId = resMes.substring(4,19);
			String Res_merAmt = resMes.substring(20,dex);
			String Res_chkValue = resMes.substring(dex+1);
		
		
		System.out.println("Res_Code=" + Res_Code);
		System.out.println("Res_merId=" + Res_merId);
		System.out.println("Res_merAmt=" + Res_merAmt);
		System.out.println("Res_chkValue=" + Res_chkValue);
		
		String plainData1 = resMes.substring(0,dex+1);
		System.out.println("需要验签的字段：" + plainData1);
		String plainData2 = new String(Base64.encode(plainData1.getBytes()));
		System.out.println("转换成Base64后数据：" + plainData2);
		
		TransactionBean pay = new TransactionBean();
		pay.setResponseCode(Res_Code);
		pay.setMerId(Res_merId);
		pay.setMerAmt(Res_merAmt);
		pay.setChkValue(Res_chkValue);
		pay.setData(resMes);
		
		request.setAttribute("payInput", pay);

		//对收到的ChinaPay应答传回的域段进行验签
		boolean buildOK = false;
		boolean res = false;
		try {
			buildOK = key.buildKey("999999999999999", KeyUsage, PubKeyPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!buildOK) {
			System.out.println("build error!");
			return;
		}
		res=sl.verifyAuthToken(plainData2,Res_chkValue);
		System.out.println(res);
		if (res){
			System.out.println("验签数据正确!");
		    request.getRequestDispatcher("./BalanceQueryReturn.jsp").forward(request, response);
		}
		else {
			System.out.println("签名数据不匹配！");
			request.getRequestDispatcher("./VerifyFail.jsp").forward(request, response);
		}
		return;
			}
		
		
		else {
			String Res_chkValue = resMes.substring(dex+1);
		
			System.out.println("Res_Code=" + Res_Code);
			System.out.println("Res_chkValue=" + Res_chkValue);
			
			String plainData1 = resMes.substring(0,dex+1);
			System.out.println("需要验签的字段：" + plainData1);
			String plainData2 = new String(Base64.encode(plainData1.getBytes()));
			System.out.println("转换成Base64后数据：" + plainData2);
			
			TransactionBean pay = new TransactionBean();
			pay.setResponseCode(Res_Code);
			pay.setData(resMes);
			request.setAttribute("payInput", pay);

			//对收到的ChinaPay应答传回的域段进行验签
			boolean buildOK = false;
			boolean res = false;
			try {
				buildOK = key.buildKey("999999999999999", KeyUsage, PubKeyPath);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!buildOK) {
				System.out.println("build error!");
				return;
			}
			res=sl.verifyAuthToken(plainData2,Res_chkValue);
			System.out.println(res);
			if (res){
				System.out.println("验签数据正确!");
			    request.getRequestDispatcher("./QueryFail.jsp").forward(request, response);
			}
			else {
				System.out.println("签名数据不匹配！");
				request.getRequestDispatcher("./VerifyFail.jsp").forward(request, response);
			}
			return;
			}

	
			

		
	}

}
