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

public class ResponseServlet extends HttpServlet {
	
	 Logger logger = Logger.getLogger(ResponseServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String PaymentUrl = "chinapay.payment.url";
	private static final String KEY_CHINAPAY_PUBKEY_FILEPATH = "chinapay.pubkey.filepath";

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String PubKeyPath = null;
		String pay_url = null;
		Properties config = Config.getInstance().getProperties();
		PubKeyPath = config.getProperty(KEY_CHINAPAY_PUBKEY_FILEPATH);// chinapay.merkey.filepath
		pay_url = config.getProperty(PaymentUrl); // chinapay.payment.url

		request.setCharacterEncoding("GBK");
		logger.info(pay_url);
		logger.info(PubKeyPath);

		// 获取页面数据
		String merId = request.getParameter("merId"); // 15
		String merDate = request.getParameter("merDate");
		String merSeqId = request.getParameter("merSeqId");
		String cardNo = request.getParameter("cardNo");
		String usrName = request.getParameter("usrName"); // Test data!
		String openBank = request.getParameter("openBank");
		String prov = request.getParameter("prov");
		String city = request.getParameter("city");// 3
		String transAmt = request.getParameter("transAmt");
		String purpose = request.getParameter("purpose");
		String flag = request.getParameter("flag");
		String version = request.getParameter("version");
		String chkValue = request.getParameter("chkValue");
		String signFlag = "1";
		String subBank = request.getParameter("subBank");
		String termType = request.getParameter("termType");

		// 连接Chinapay控台
		HttpClient httpClient = new HttpClient();
		logger.info("HttpClient方法创建！");
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");
		String url = pay_url;
		logger.info(url);
		PostMethod postMethod = new PostMethod(url);
		logger.info("Post方法创建！");

		// 填入各个表单域的值
		NameValuePair[] data = { new NameValuePair("merId", merId), new NameValuePair("merDate", merDate),
				new NameValuePair("merSeqId", merSeqId), new NameValuePair("cardNo", cardNo),
				new NameValuePair("usrName", usrName), new NameValuePair("openBank", openBank),
				new NameValuePair("prov", prov), new NameValuePair("city", city),
				new NameValuePair("transAmt", transAmt), new NameValuePair("purpose", purpose),
				new NameValuePair("subBank", subBank), new NameValuePair("flag", flag),
				new NameValuePair("version", version), new NameValuePair("chkValue", chkValue),
				new NameValuePair("termType", termType), new NameValuePair("signFlag", signFlag) };

		//logger.info(data);
		logger.info("Ora交易提交报文：merId=" + merId + "&merDate=" + merDate + "&merSeqId=" + merSeqId + "&cardNo="
				+ cardNo + "&usrName=" + usrName + "&openBank=" + openBank + "&prov=" + prov + "&city=" + city
				+ "&transAmt=" + transAmt + "&purpose=" + purpose + "&subBank=" + subBank + "&flag=" + flag
				+ "&version=" + version + "&chkValue=" + chkValue + "&signFlag=" + signFlag + "&termType=" + termType);

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
		StringBuffer html = new StringBuffer();
		while ((tempBf = reader.readLine()) != null) {

			html.append(tempBf);
		}
		String resMes = html.toString();
		logger.info("交易返回报文：" + resMes);
		int dex = resMes.lastIndexOf("&");

		// 拆分页面应答数据
		String str[] = resMes.split("&");
		logger.info(str.length);	
		if(str.length == 10 || str.length == 11){
			int Res_Code = 0;
			int Res_merId = 0;
			int Res_merDate = 0;
			int Res_merSeqId = 0;
			int Res_cpDate = 0;
			int Res_cpSeqId = 0;
			int Res_transAmt = 0;
			int Res_stat = 0;
			int Res_cardNo = 0;
			int Res_msg = 0;
			int Res_chkValue = 0;
			
			 String responseCode = "";
			 String MerId = "";
			 String MerDate = "";
			 String MerSeqId = "";
			 String CpDate = "";
			 String CpSeqId = "";
			 String TransAmt = "";
			 String Stat = "";
			 String CardNo = "";
			 String Message = "";
			 String ChkValue = "";
			//提取返回数据
			if(str.length == 10 ){
					 Res_Code = str[0].indexOf("=");
					 Res_merId = str[1].indexOf("=");
					 Res_merDate = str[2].indexOf("=");
					 Res_merSeqId = str[3].indexOf("=");
					 Res_cpDate = str[4].indexOf("=");
					 Res_cpSeqId = str[5].indexOf("=");
					 Res_transAmt = str[6].indexOf("=");
					 Res_stat = str[7].indexOf("=");
					 Res_cardNo = str[8].indexOf("=");
					 Res_chkValue = str[9].indexOf("=");
					  responseCode = str[0].substring(Res_Code+1);
					  MerId = str[1].substring(Res_merId+1);
					  MerDate = str[2].substring(Res_merDate+1);
					  MerSeqId = str[3].substring(Res_merSeqId+1);
					  CpDate = str[4].substring(Res_cpDate+1);
					  CpSeqId = str[5].substring(Res_cpSeqId+1);
					  TransAmt = str[6].substring(Res_transAmt+1);
					  Stat = str[7].substring(Res_stat+1);
					  CardNo = str[8].substring(Res_cardNo+1);
					  ChkValue = str[9].substring(Res_chkValue+1);
				}else if( str.length ==11){
						 Res_Code = str[0].indexOf("=");
						 Res_merId = str[1].indexOf("=");
						 Res_merDate = str[2].indexOf("=");
						 Res_merSeqId = str[3].indexOf("=");
						 Res_cpDate = str[4].indexOf("=");
						 Res_cpSeqId = str[5].indexOf("=");
						 Res_transAmt = str[6].indexOf("=");
						 Res_stat = str[7].indexOf("=");
						 Res_cardNo = str[8].indexOf("=");
						 Res_msg = str[9].indexOf("=");
						 Res_chkValue = str[10].indexOf("=");	
							
						  responseCode = str[0].substring(Res_Code+1);
						  MerId = str[1].substring(Res_merId+1);
						  MerDate = str[2].substring(Res_merDate+1);
						  MerSeqId = str[3].substring(Res_merSeqId+1);
						  CpDate = str[4].substring(Res_cpDate+1);
						  CpSeqId = str[5].substring(Res_cpSeqId+1);
						  TransAmt = str[6].substring(Res_transAmt+1);
						  Stat = str[7].substring(Res_stat+1);
						  CardNo = str[8].substring(Res_cardNo+1);
						  Message = str[9].substring(Res_msg+1);
						  ChkValue = str[10].substring(Res_chkValue+1);
			
				}
	
		
		
		logger.info("responseCode=" + responseCode);
		logger.info("merId=" + MerId);
		logger.info("merDate=" + MerDate);
		logger.info("merSeqId=" + MerSeqId);
		logger.info("transAmt=" + TransAmt);
		logger.info("cpDate=" + CpDate);
		logger.info("cpSeqId=" + CpSeqId);
		logger.info("Stat=" + Stat);
		logger.info("cardNo=" + CardNo);
		logger.info("Message=" + Message);
		logger.info("chkValue=" + ChkValue);
		
		
		String msg = resMes.substring(0,dex);
		String plainData = new String(Base64.encode(msg.getBytes()));
		logger.info("需要验签的字段：" + msg);

			// 传入显示页面的数据准备
			TransactionBean pay = new TransactionBean();
			pay.setResponseCode(responseCode);
			pay.setMerId(MerId);
			pay.setMerDate(MerDate);
			pay.setMerSeqId(MerSeqId);
			pay.setCpDate(CpDate);
			pay.setCpSeqId(CpSeqId);
			pay.setTransAmt(TransAmt);
			pay.setStat(Stat);
			pay.setCardNo(CardNo);
			pay.setTermType(Message);
			pay.setData(resMes);

			request.setAttribute("payInput", pay);

			// 对收到的ChinaPay应答传回的域段进行验签
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
				return;
			}

			SecureLink sl = new SecureLink(key);
			res = sl.verifyAuthToken(plainData, ChkValue);
			logger.info(res);
			if (res) {
				logger.info("验签数据正确!");
				request.getRequestDispatcher("./TransactionSuccess.jsp").forward(request, response);
			} else {
				logger.info("签名数据不匹配！");
				request.getRequestDispatcher("./VerifyFail.jsp").forward(request, response);
			}
			return;
		}

		// 交易失败应答
		if (str.length == 2) {
			int Res_Code = str[0].indexOf("=");
			int Res_chkValue = str[1].indexOf("=");

			String responseCode = str[0].substring(Res_Code + 1);
			String ChkValue = str[1].substring(Res_chkValue + 1);
			logger.info("responseCode=" + responseCode);
			logger.info("chkValue=" + ChkValue);

			String plainData = str[0];
			String plainData1 = new String(Base64.encode(plainData.getBytes()));
			logger.info("需要验签的字段：" + plainData);

			TransactionBean pay = new TransactionBean();
			pay.setResponseCode(responseCode);
			pay.setData(resMes);
			request.setAttribute("payInput", pay);

			// 对收到的ChinaPay应答传回的域段进行验签
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
				return;
			}

			SecureLink sl = new SecureLink(key);
			res = sl.verifyAuthToken(plainData1, ChkValue);
			logger.info(res);
			if (res) {
				logger.info("验签数据正确!");
				request.getRequestDispatcher("./TransactionFail.jsp").forward(request, response);

			} else {
				logger.info("签名数据不匹配！");
				request.getRequestDispatcher("./VerifyFail.jsp").forward(request, response);
			}
			return;
		}

	}

	/**
	 * 重写实现方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public TransactionBean doPostTransaction(TransactionBean resTransBean)
			throws ServletException, IOException {
		TransactionBean transactionBean = new TransactionBean();
		String PubKeyPath = null;
		String pay_url = null;
		Properties config = Config.getInstance().getProperties();
		PubKeyPath = config.getProperty(KEY_CHINAPAY_PUBKEY_FILEPATH);// chinapay.merkey.filepath
		pay_url = config.getProperty(PaymentUrl); // chinapay.payment.url

//		request.setCharacterEncoding("GBK");
		logger.info(pay_url);
		logger.info(PubKeyPath);

		// 获取页面数据
		String merId =resTransBean.getMerId(); // 15
		String merDate = resTransBean.getMerDate();
		String merSeqId = resTransBean.getMerSeqId();
		String cardNo = resTransBean.getCardNo();
		String usrName = resTransBean.getUserName(); // Test data!
		String openBank = resTransBean.getOpenBank();
		String prov = resTransBean.getProv();
		logger.info("*************************************** prov ：" + prov);
		String city = resTransBean.getCity();// 3
		logger.info("*************************************** city ：" + city);
		String transAmt = resTransBean.getTransAmt();
		String purpose = resTransBean.getPurpose();
		logger.info("*************************************** purpose ：" + purpose);
		String flag = resTransBean.getFlag();
		String version = resTransBean.getVersion();
		String chkValue = resTransBean.getChkValue();
		String signFlag = "1";
		String subBank = resTransBean.getSubBank();
		String termType = resTransBean.getTermType();

		// 连接Chinapay控台
		HttpClient httpClient = new HttpClient();
		logger.info("HttpClient方法创建！");
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");
		String url = pay_url;
		logger.info(url);
		PostMethod postMethod = new PostMethod(url);
		logger.info("Post方法创建！");

		// 填入各个表单域的值
		NameValuePair[] data = { new NameValuePair("merId", merId), new NameValuePair("merDate", merDate),
				new NameValuePair("merSeqId", merSeqId), new NameValuePair("cardNo", cardNo),
				new NameValuePair("usrName", usrName), new NameValuePair("openBank", openBank),
				new NameValuePair("prov", prov), new NameValuePair("city", city),
				new NameValuePair("transAmt", transAmt), new NameValuePair("purpose", purpose),
				new NameValuePair("subBank", subBank), new NameValuePair("flag", flag),
				new NameValuePair("version", version), new NameValuePair("chkValue", chkValue),
				new NameValuePair("termType", termType), new NameValuePair("signFlag", signFlag) };

		logger.info(data);
		logger.info("Ora交易提交报文：merId=" + merId + "&merDate=" + merDate + "&merSeqId=" + merSeqId + "&cardNo="
				+ cardNo + "&usrName=" + usrName + "&openBank=" + openBank + "&prov=" + prov + "&city=" + city
				+ "&transAmt=" + transAmt + "&purpose=" + purpose + "&subBank=" + subBank + "&flag=" + flag
				+ "&version=" + version + "&chkValue=" + chkValue + "&signFlag=" + signFlag + "&termType=" + termType);

		// 将表单的值放入postMethod中
		postMethod.setRequestBody(data);
		// 执行postMethod
		try {
			httpClient.executeMethod(postMethod);
		} 
		catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 读取内容
		InputStream resInputStream = null;
		StringBuffer html = new StringBuffer();
		BufferedReader reader = null;
		try {
			resInputStream = postMethod.getResponseBodyAsStream();
			// 处理内容
			reader = new BufferedReader(new InputStreamReader(resInputStream));
			String tempBf = null;
			while ((tempBf = reader.readLine()) != null) {
	
				html.append(tempBf);
			}
			resInputStream.close();
			reader.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try {
				if(resInputStream != null){
					resInputStream.close();
				}
				if(reader != null){
					reader.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		String resMes = html.toString();
		logger.info("交易返回报文：" + resMes);
		int dex = resMes.lastIndexOf("&");

		// 拆分页面应答数据
		String str[] = resMes.split("&");
		logger.info(str.length);

		// 提取返回数据
		if (str.length == 10) {
			int Res_Code = str[0].indexOf("=");
			int Res_merId = str[1].indexOf("=");
			int Res_merDate = str[2].indexOf("=");
			int Res_merSeqId = str[3].indexOf("=");
			int Res_cpDate = str[4].indexOf("=");
			int Res_cpSeqId = str[5].indexOf("=");
			int Res_transAmt = str[6].indexOf("=");
			int Res_stat = str[7].indexOf("=");
			int Res_cardNo = str[8].indexOf("=");
			int Res_chkValue = str[9].indexOf("=");

			String responseCode = str[0].substring(Res_Code + 1);
			String MerId = str[1].substring(Res_merId + 1);
			String MerDate = str[2].substring(Res_merDate + 1);
			String MerSeqId = str[3].substring(Res_merSeqId + 1);
			String CpDate = str[4].substring(Res_cpDate + 1);
			String CpSeqId = str[5].substring(Res_cpSeqId + 1);
			String TransAmt = str[6].substring(Res_transAmt + 1);
			String Stat = str[7].substring(Res_stat + 1);
			String CardNo = str[8].substring(Res_cardNo + 1);
			String ChkValue = str[9].substring(Res_chkValue + 1);
			
			transactionBean.setResponseCode(responseCode);
			transactionBean.setMerId(MerId);
			transactionBean.setMerDate(MerDate);
			transactionBean.setMerSeqId(MerSeqId);
			transactionBean.setCpDate(CpDate);
			transactionBean.setCpSeqId(CpSeqId);
			transactionBean.setTransAmt(TransAmt);
			transactionBean.setStat(Stat);
			transactionBean.setChkValue(ChkValue);
			transactionBean.setCardNo(CardNo);

			logger.info("responseCode=" + responseCode);
			logger.info("merId=" + MerId);
			logger.info("merDate=" + MerDate);
			logger.info("merSeqId=" + MerSeqId);
			logger.info("transAmt=" + TransAmt);
			logger.info("cpDate=" + CpDate);
			logger.info("cpSeqId=" + CpSeqId);
			logger.info("Stat=" + Stat);
			logger.info("cardNo=" + CardNo);
			logger.info("chkValue=" + ChkValue);

			String msg = resMes.substring(0, dex);
			String plainData = new String(Base64.encode(msg.getBytes()));
			logger.info("需要验签的字段：" + msg);

			// 传入显示页面的数据准备
			TransactionBean pay = new TransactionBean();
			pay.setResponseCode(responseCode);
			pay.setMerId(MerId);
			pay.setMerDate(MerDate);
			pay.setMerSeqId(MerSeqId);
			pay.setCpDate(CpDate);
			pay.setCpSeqId(CpSeqId);
			pay.setTransAmt(TransAmt);
			pay.setStat(Stat);
			pay.setCardNo(CardNo);
			pay.setData(resMes);

			//request.setAttribute("payInput", pay);

			// 对收到的ChinaPay应答传回的域段进行验签
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
				return transactionBean;
			}

			SecureLink sl = new SecureLink(key);
			res = sl.verifyAuthToken(plainData, ChkValue);
			logger.info(res);
			if (res) {
				logger.info("验签数据正确!");
				//request.getRequestDispatcher("./TransactionSuccess.jsp").forward(request, response);
			} else {
				logger.info("签名数据不匹配！");
				//request.getRequestDispatcher("./VerifyFail.jsp").forward(request, response);
			}
			return transactionBean;
		}

		// 交易失败应答
		if (str.length == 2) {
			int Res_Code = str[0].indexOf("=");
			int Res_chkValue = str[1].indexOf("=");

			String responseCode = str[0].substring(Res_Code + 1);
			String ChkValue = str[1].substring(Res_chkValue + 1);
			logger.info("responseCode=" + responseCode);
			logger.info("chkValue=" + ChkValue);

			String plainData = str[0];
			String plainData1 = new String(Base64.encode(plainData.getBytes()));
			logger.debug("需要验签的字段：" + plainData);

			TransactionBean pay = new TransactionBean();
			pay.setResponseCode(responseCode);
			pay.setData(resMes);
			//request.setAttribute("payInput", pay);

			// 对收到的ChinaPay应答传回的域段进行验签
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
				logger.debug("build error!");
				return transactionBean;
			}

			SecureLink sl = new SecureLink(key);
			res = sl.verifyAuthToken(plainData1, ChkValue);
			logger.debug(res);
			if (res) {
				logger.debug("验签数据正确!");
				//request.getRequestDispatcher("./TransactionFail.jsp").forward(request, response);

			} else {
				logger.debug("签名数据不匹配！");
				//request.getRequestDispatcher("./VerifyFail.jsp").forward(request, response);
			}
			return transactionBean;
		}
		return transactionBean;

	}

}
