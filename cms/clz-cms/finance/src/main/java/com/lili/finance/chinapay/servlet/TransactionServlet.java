package com.lili.finance.chinapay.servlet;
/**
 * @author huang.xuting
 *
 */
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lili.finance.chinapay.model.bean.TransactionBean;
import com.lili.finance.chinapay.util.Config;

import chinapay.Base64;
import chinapay.PrivateKey;
import chinapay.SecureLink;



public class TransactionServlet extends HttpServlet {
	

	 Logger logger = Logger.getLogger(TransactionServlet.class);

	
	private static final long serialVersionUID = -8900442198891786032L;
	/**
	 * Constructor of the object.
	 */
	private static final String KEY_CHINAPAY_MERID = "chinapay.merid";
	private static final String TrasCommit = "/TransactionCommit.jsp";
	private static final String PaymentUrl = "chinapay.payment.url";
	private static final String KEY_CHINAPAY_MERKEY_FILEPATH = "chinapay.merkey.filepath";

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		    request.setCharacterEncoding("GBK");
			String MerKeyPath = null;
			String pay_url = null;
			String merId = null;
			try {
				Properties config = Config.getInstance().getProperties();
				MerKeyPath = config.getProperty(KEY_CHINAPAY_MERKEY_FILEPATH);  //chinapay.merkey.filepath
				pay_url = config.getProperty(PaymentUrl);                      //chinapay.payment.url
				merId = config.getProperty(KEY_CHINAPAY_MERID);
			} catch (Exception e) {
				logger.info("私钥初始化失败！");
			}
			
			logger.info(MerKeyPath);
			
//			String merId = request.getParameter("MerId");  //15
			
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
			SimpleDateFormat sf2 = new SimpleDateFormat("yyyyMMdd");
			
			Date dt = new Date();
			String merDate = request.getParameter("MerDate"); //8
			if(merDate.isEmpty())merDate = sf2.format(dt);
			
			String merSeqId = request.getParameter("MerSeqId");
			if(merSeqId.isEmpty()){
				merSeqId = "00" + sf.format(dt);
			}
			
			String cardNo = request.getParameter("CardNo");
			
			String usrName = request.getParameter("UserName"); //中文
			logger.info(usrName);
			String openBank = request.getParameter("OpenBank");//中文
			logger.info(openBank);
			
			String prov = request.getParameter("Prov");  //中文
			String city = request.getParameter("City");  //中文
			logger.info(prov + "-" +city);
			
			String transAmt = request.getParameter("TransAmt");
			String purpose = request.getParameter("Purpose");  //中文
			logger.info(purpose);
			
            String subBank = request.getParameter("SubBank");  //中文
            logger.info(subBank);
            
			String flag = request.getParameter("Flag");
			String version = request.getParameter("version");
			logger.info(version);
			
			String termType = request.getParameter("termType");
			
			//签名数据组装
			TransactionBean pay = new TransactionBean();
			pay.setMerId(merId);
			pay.setMerDate(merDate);
			pay.setMerSeqId(merSeqId);
			pay.setCardNo(cardNo);
			pay.setUserName(usrName);
			pay.setOpenBank(openBank);
			pay.setProv(prov);
			pay.setCity(city);
			pay.setTransAmt(transAmt);
			pay.setPurpose(purpose);
			pay.setSubBank(subBank);
			pay.setFlag(flag);
			pay.setVersion(version);
			pay.setTermType(termType);
			
			String Data = pay.toString();
			logger.info("字符串数据拼装结果：" + Data);
			logger.info(pay.transString());
		//	String plainData = new String(Base64.encode(Data.getBytes()));
			String plainData = new String(Base64.encode(Data.getBytes("GBK")));
			logger.info("转换成Base64后数据：" + plainData);
			
            //签名
			String chkValue = null;
			int KeyUsage = 0;
			PrivateKey key = new PrivateKey();
		    boolean flage = key.buildKey(merId, KeyUsage, MerKeyPath);
		    if(flage == false){
		    	logger.info("buildkey error!");
		    	return;
		    }else{
		    	logger.info("============flage "+flage );
			    SecureLink sl = new SecureLink(key);
				logger.info("====date "+ plainData);
				chkValue = sl.Sign(plainData);
				logger.info("签名内容:"+ chkValue);
		    };
		    
			
			//跳转到确认页面
			pay.setChkValue(chkValue);
			request.setAttribute("payInput", pay);
			request.setAttribute("pay_url", pay_url);
			request.getRequestDispatcher(TrasCommit).forward(request, response);
			return;
		    		
		
	}	

	 public static String toUnicode(String zhStr) {
	        StringBuffer unicode = new StringBuffer();
	        for (int i = 0; i < zhStr.length(); i++) {
	            char c = zhStr.charAt(i);
	            unicode.append("\\u" + Integer.toHexString(c));
	        }
	       // System.out.println(unicode.toString());
	        //System.out.println(zhStr+"转换为unicode码成功！");
	        return unicode.toString();
	 }


	 /**
	  * 重写实现方法
	  * @param request
	  * @param response
	  * @return
	  * @throws ServletException
	  * @throws IOException
	  */
	public TransactionBean getChkValue(TransactionBean transBean)
			throws ServletException, IOException {
		
//		request.setCharacterEncoding("GBK");
		String MerKeyPath = null;
		String pay_url = null;
		String merId = null;
		TransactionBean transactionBean = new TransactionBean();
		try {
			Properties config = Config.getInstance().getProperties();
			MerKeyPath = config.getProperty(KEY_CHINAPAY_MERKEY_FILEPATH);  //chinapay.merkey.filepath
			pay_url = config.getProperty(PaymentUrl);                      //chinapay.payment.url
			logger.info("******************************************************************pay_url : " + pay_url);
			merId = config.getProperty(KEY_CHINAPAY_MERID);
			logger.info("****************************************************************** merId : " + merId);
			transactionBean.setMerId(merId);
		} catch (Exception e) {
			logger.info("私钥初始化失败！");
		}
		
		logger.info(MerKeyPath);
		
	//			String merId = request.getParameter("MerId");  //15
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyyMMdd");
		
		Date dt = new Date();
		String merDate = transBean.getMerDate(); //8
		if(merDate.isEmpty())merDate = sf2.format(dt);
		
		String merSeqId = transBean.getMerSeqId();
		if(merSeqId.isEmpty()){
			merSeqId = "00" + sf.format(dt);
		}
		
		String cardNo = transBean.getCardNo();
		
		String usrName = transBean.getUserName(); //中文
		logger.info(usrName);
		String openBank = transBean.getOpenBank();//中文
		logger.info(openBank);
		
		String prov = transBean.getProv();  //中文
		String city = transBean.getCity();  //中文
		logger.info("******************************************prov : " + prov + "- city :" +city);
		
		String transAmt = transBean.getTransAmt();
		String purpose = transBean.getPurpose();  //中文
		logger.info(purpose);
		
		String subBank = transBean.getSubBank();  //中文
		logger.info(subBank);
		
		String flag = transBean.getFlag();
		String version = transBean.getVersion();
		logger.info(version);
		
		String termType = transBean.getTermType();
		
		//签名数据组装
		TransactionBean pay = new TransactionBean();
		pay.setMerId(merId);
		pay.setMerDate(merDate);
		pay.setMerSeqId(merSeqId);
		pay.setCardNo(cardNo);
		pay.setUserName(usrName);
		pay.setOpenBank(openBank);
		pay.setProv(prov);
		pay.setCity(city);
		pay.setTransAmt(transAmt);
		pay.setPurpose(purpose);
		pay.setSubBank(subBank);
		pay.setFlag(flag);
		pay.setVersion(version);
		pay.setTermType(termType);
		
		String Data = pay.toString();
		logger.info("字符串数据拼装结果：" + Data);
		logger.info(pay.transString());
		logger.info("***********************  "+Data.getBytes());
		String plainData2 = new String(Base64.encode(Data.getBytes()));
		String plainData = new String(Base64.encode(Data.getBytes("GBK")));
		logger.info("转换成Base64后数据：" + plainData);
		logger.info("转换成Base64后数据2：" + plainData2);
		
		//签名
		String chkValue = null;
		int KeyUsage = 0;
		PrivateKey key = new PrivateKey();
		boolean flage = key.buildKey(merId, KeyUsage, MerKeyPath);
		if(flage == false){
			logger.info("buildkey error!");
			return transactionBean;
		}else{
			logger.info("============flage "+flage );
			SecureLink sl = new SecureLink(key);
			logger.info("====date "+ plainData);
			chkValue = sl.Sign(plainData);
			transactionBean.setChkValue(chkValue);
			logger.info("签名内容:"+ chkValue);
		};
		
		//跳转到确认页面
		pay.setChkValue(chkValue);
		return transactionBean;
	
	
}	
}
