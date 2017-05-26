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
public class BatchOrderReturnServlet extends HttpServlet {

	private static final String PaymentUrl = "chinapay.BatchOrderQuery.url";
	private static final String KEY_CHINAPAY_PUBKEY_FILEPATH = "chinapay.pubkey.filepath";
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String PubKeyPath = null;
		String pay_url = null;
		Properties config = Config.getInstance().getProperties();
		PubKeyPath = config.getProperty(KEY_CHINAPAY_PUBKEY_FILEPATH); 
		pay_url = config.getProperty(PaymentUrl);                      

		System.out.println(PubKeyPath);
		
		request.setCharacterEncoding("GBK");
		
		String merId = request.getParameter("merId");  //15
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		String stat = request.getParameter("stat");
		String version = request.getParameter("version");
		String chkValue = request.getParameter("chkValue");
		String signFlag = "1";
		System.out.println("verify:" + merId + fromDate + toDate + stat + version + chkValue);
		
		
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
				new NameValuePair("fromDate", fromDate),
				new NameValuePair("toDate", toDate),
				new NameValuePair("stat", stat),
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
		
		//对收到的ChinaPay应答传回的域段进行验签
		boolean buildOK = false;
		boolean res = false;
		int KeyUsage = 0;
		PrivateKey key = new PrivateKey();
		try {
			buildOK = key.buildKey("999999999999999", KeyUsage, PubKeyPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!buildOK) {
			System.out.println("build error!");
			return;
		}
		SecureLink sl = new SecureLink(key);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(resInputStream));
		String tempBf = null;
		String temp = null;
		String Res_chkValue = null;
		String resMes = "";
		int flag =0;
		tempBf = reader.readLine();
		String Res_Code = tempBf.substring(0,3);
		System.out.println("循环开始...");
		int j = 0;
		
		//逐行验签
		while(tempBf != null){ 
			
			System.out.println("Line = " + j);
			int dex = tempBf.lastIndexOf("|");
			temp = tempBf.substring(0,dex+1);
			Res_chkValue = tempBf.substring(dex+1);
			System.out.println(temp);
			System.out.println(Res_chkValue);
			String Data = new String(Base64.encode(temp.getBytes()));
			System.out.println("转换成Base64后数据：" + Data);
			res=sl.verifyAuthToken(Data,Res_chkValue);
			System.out.println(res);
			resMes += temp;
			if(res){
				j++;
			}
			else{
				flag ++;
			}			
			tempBf = reader.readLine();
		}
		
		System.out.println("Res_Code=" + Res_Code);	
		
		TransactionBean pay = new TransactionBean();
		pay.setResponseCode(Res_Code);
		pay.setMerId(merId);
		pay.setData(resMes);
		
		request.setAttribute("payInput", pay);
				
		if (flag == 0){
			System.out.println("验签数据正确!");
		    request.getRequestDispatcher("./BatchOrderReturn.jsp").forward(request, response);
		}
		else {
			System.out.println("签名数据不匹配！");
			request.getRequestDispatcher("./VerifyFail.jsp").forward(request, response);
		}
		return;		
	}

}
