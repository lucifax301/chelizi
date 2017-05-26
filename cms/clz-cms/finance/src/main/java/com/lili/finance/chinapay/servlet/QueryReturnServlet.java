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
import org.apache.log4j.Logger;

import com.lili.finance.chinapay.model.bean.TransactionBean;
import com.lili.finance.chinapay.util.Config;

import chinapay.Base64;
import chinapay.PrivateKey;
import chinapay.SecureLink;

@SuppressWarnings("serial")
public class QueryReturnServlet extends HttpServlet {

	Logger logger = Logger.getLogger(QueryReturnServlet.class);
	
	private static final String PaymentUrl = "chinapay.query.url";
	private static final String KEY_CHINAPAY_MERKEY_FILEPATH = "chinapay.merkey.filepath";
	private static final String KEY_CHINAPAY_PUBKEY_FILEPATH = "chinapay.pubkey.filepath";
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String MerKeyPath = null;
		String PubKeyPath = null;
		String pay_url = null;
		Properties config = Config.getInstance().getProperties();
		MerKeyPath = config.getProperty(KEY_CHINAPAY_MERKEY_FILEPATH);
		PubKeyPath = config.getProperty(KEY_CHINAPAY_PUBKEY_FILEPATH); //chinapay.merkey.filepath
		pay_url = config.getProperty(PaymentUrl);                      //chinapay.payment.url

		System.out.println(MerKeyPath);
		System.out.println(PubKeyPath);
		
		request.setCharacterEncoding("GBK");
		
		String merId = request.getParameter("merId");  //15
		String merDate = request.getParameter("merDate");
		String merSeqId = request.getParameter("merSeqId"); 
		String version = request.getParameter("version");
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
				new NameValuePair("merDate", merDate),
				new NameValuePair("merSeqId", merSeqId),
 				new NameValuePair("version", version),
				new NameValuePair("chkValue",chkValue),
				new NameValuePair("signFlag", signFlag)
		};
		
		logger.info(data);
		
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
		System.out.println("单笔查询返回报文：" + resMes);
		int dex = resMes.lastIndexOf("|");
		String Res_Code = resMes.substring(0,3);
			
		//提取返回数据
			if(Res_Code.equals("000")){
				String Res_stat = resMes.substring(dex-2,dex-1);
				//String Res_stat = resMes.substring(dex-10,dex-9); linux
				String Res_chkValue = resMes.substring(dex+1);
		        System.out.println("Res_Code=" + Res_Code);
		        System.out.println("Res_stat=" + Res_stat);
		        System.out.println("Res_chkValue=" + Res_chkValue);
		
		String plainData = resMes.substring(0,dex+1);
		System.out.println("需要验签的字段：" + plainData);
		String Data = new String(Base64.encode(plainData.getBytes()));
		System.out.println("转换成Base64后数据：" + Data);
		
		TransactionBean pay = new TransactionBean();
		pay.setResponseCode(Res_Code);
		pay.setPurpose(plainData);
		pay.setStat(Res_stat);
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
		    request.getRequestDispatcher("./QueryReturn.jsp").forward(request, response);
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
			
			String plainData = resMes.substring(0,dex+1);
			System.out.println("需要验签的字段：" + plainData);
			String Data = new String(Base64.encode(plainData.getBytes()));
			System.out.println("转换成Base64后数据：" + Data);
			
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
	
	public TransactionBean doPostTransaction(TransactionBean transactionBean)
			throws ServletException, IOException {
		String MerKeyPath = null;
		String PubKeyPath = null;
		String pay_url = null;
		Properties config = Config.getInstance().getProperties();
		MerKeyPath = config.getProperty(KEY_CHINAPAY_MERKEY_FILEPATH);
		PubKeyPath = config.getProperty(KEY_CHINAPAY_PUBKEY_FILEPATH); //chinapay.merkey.filepath
		pay_url = config.getProperty(PaymentUrl);                      //chinapay.payment.url
		
		logger.info(MerKeyPath);
		logger.info(PubKeyPath);
		
		String merId = transactionBean.getMerId();  //15
		String merDate = transactionBean.getMerDate();
		String merSeqId = transactionBean.getMerSeqId();
		String version = transactionBean.getVersion();
		String chkValue = transactionBean.getChkValue();
		String signFlag = "1";
		
		HttpClient httpClient = new HttpClient();
		logger.info("HttpClient方法创建！");
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");
		String url = pay_url;
		logger.info(url);
		PostMethod postMethod = new PostMethod(url);
		logger.info("Post方法创建！");
		//填入各个表单域的值
		NameValuePair[] data = { 
				new NameValuePair("merId", merId),
				new NameValuePair("merDate", merDate),
				new NameValuePair("merSeqId", merSeqId),
				new NameValuePair("version", version),
				new NameValuePair("chkValue",chkValue),
				new NameValuePair("signFlag", signFlag)
		};
		
		logger.info(data);
		
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
		logger.info("单笔查询返回报文：" + resMes);
		int dex = resMes.lastIndexOf("|");
		String Res_Code = resMes.substring(0,3);
		
		//提取返回数据
		if(Res_Code.equals("000")){
			String Res_stat = resMes.substring(dex-2,dex-1);
			//String Res_stat = resMes.substring(dex-10,dex-9); linux
			String Res_chkValue = resMes.substring(dex+1);
			logger.info("Res_Code=" + Res_Code);
			logger.info("Res_stat=" + Res_stat);
			logger.info("Res_chkValue=" + Res_chkValue);
			
			String plainData = resMes.substring(0,dex+1);
			logger.info("需要验签的字段：" + plainData);
			String Data = new String(Base64.encode(plainData.getBytes()));
			logger.info("转换成Base64后数据：" + Data);
			
			TransactionBean pay = new TransactionBean();
			pay.setResponseCode(Res_Code);
			pay.setPurpose(plainData);
			pay.setStat(Res_stat);
			pay.setData(resMes);
			pay.setChkValue(Res_chkValue);
			
			
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
				logger.info("build error!");
				return pay;
			}
			
			SecureLink sl = new SecureLink(key);
			res=sl.verifyAuthToken(Data,Res_chkValue);
			logger.info(res);
			if (res){
				logger.info("验签数据正确!");
			}
			else {
				logger.info("签名数据不匹配！");
			}
			return pay;
		}
		
		
		else {
			String Res_chkValue = resMes.substring(dex+1);
			
			logger.info("Res_Code=" + Res_Code);
			logger.info("Res_chkValue=" + Res_chkValue);
			
			String plainData = resMes.substring(0,dex+1);
			logger.info("需要验签的字段：" + plainData);
			String Data = new String(Base64.encode(plainData.getBytes()));
			logger.info("转换成Base64后数据：" + Data);
			
			TransactionBean pay = new TransactionBean();
			pay.setResponseCode(Res_Code);
			pay.setData(resMes);
			
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
				logger.info("build error!");
				return pay;
			}
			
			SecureLink sl = new SecureLink(key);
			res=sl.verifyAuthToken(Data,Res_chkValue);
			logger.info(res);
			if (res){
				logger.info("验签数据正确!");
			}
			else {
				logger.info("签名数据不匹配！");
			}
			return pay;
		}
	}

}
