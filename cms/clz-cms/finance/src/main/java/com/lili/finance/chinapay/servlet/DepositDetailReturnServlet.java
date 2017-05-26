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
public class DepositDetailReturnServlet extends HttpServlet {

	private static final String PaymentUrl = "chinapay.DepositDetailQuery.url";
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
		String version = request.getParameter("version");
		String type = request.getParameter("type");
		String chkValue = request.getParameter("chkValue");
		
		String signFlag = "1";
		
		
		
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
 				new NameValuePair("version", version),
 				new NameValuePair("type", type),
				new NameValuePair("chkValue",chkValue),
				new NameValuePair("signFlag",signFlag)
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
		tempBf = reader.readLine();
		String Res_Code = tempBf.substring(0,3);
		String resMes = tempBf;
		tempBf = reader.readLine();
		while(true){ 
			if(tempBf!=null){
				resMes += "\r\n" + tempBf;
				tempBf = reader.readLine();
			}else{
				break;
			}
		}
		System.out.println("备付金明细查询返回报文：" + resMes);
		int dex = resMes.length()-256;
		
		//提取返回数据
		if(Res_Code.equals("000")){
			String Res_chkValue = resMes.substring(dex);
		
		
		System.out.println("Res_Code=" + Res_Code);
		System.out.println("Res_chkValue=" + Res_chkValue);
		System.out.println("Res_chkValue.length=" + Res_chkValue.length());
		
		String plainData = resMes.substring(0,dex);
        System.out.println("需要验签的字段：" + plainData);
        String Data = new String(Base64.encode(plainData.getBytes()));
		
		TransactionBean pay = new TransactionBean();
		pay.setResponseCode(Res_Code);
		pay.setMerId(merId);
		pay.setTransAmt(plainData);
		pay.setData(resMes);
		pay.setChkValue(Res_chkValue);
		
		request.setAttribute("payInput", pay);

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
		res=sl.verifyAuthToken(Data,Res_chkValue);

		System.out.println(res);
		if (res){
			System.out.println("验签数据正确!");
		    request.getRequestDispatcher("./DepositDetailReturn.jsp").forward(request, response);
		}
		else {
			System.out.println("签名数据不匹配！");
			request.getRequestDispatcher("./VerifyFail.jsp").forward(request, response);
		}
		return;
		}
				
		else {
			String Res_chkValue = resMes.substring(dex);
			
			System.out.println("Res_Code=" + Res_Code);
			System.out.println("Res_chkValue=" + Res_chkValue);
			System.out.println("Res_chkValue.length=" + Res_chkValue.length());
			
			String plainData = resMes.substring(0,dex);
			System.out.println("需要验签的字段：" + plainData);
			String Data = new String(Base64.encode(plainData.getBytes()));
			
			TransactionBean pay = new TransactionBean();
			pay.setResponseCode(Res_Code);
			pay.setData(resMes);
			request.setAttribute("payInput", pay);

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
			res=sl.verifyAuthToken(Data,Res_chkValue);
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
