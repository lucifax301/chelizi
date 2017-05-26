package com.lili.finance.chinapay.servlet;
/**
 * @author huang.xuting
 *
 */
import java.io.IOException;
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

public class QuerySendServlet extends HttpServlet {


	Logger logger = Logger.getLogger(QuerySendServlet.class);
	
	private static final long serialVersionUID = 1L;
	private static final String KEY_CHINAPAY_MERKEY_FILEPATH = "chinapay.merkey.filepath";
	private static final String KEY_CHINAPAY_MERID = "chinapay.merid";
	private static final String QUERY_JSP = "/QueryCommit.jsp";

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("GBK");
		String MerKeyPath = null;
		String merId = null;
		Properties config = Config.getInstance().getProperties();
		MerKeyPath = config.getProperty(KEY_CHINAPAY_MERKEY_FILEPATH);
		merId = config.getProperty(KEY_CHINAPAY_MERID);

		// ��ѯ��������׼��
//		String merId = request.getParameter("MerId");
		String merDate = request.getParameter("MerDate");// 8
		String merSeqId = request.getParameter("MerSeqId");
		String version = "20090501";
		
		String Data = merId + merDate + merSeqId + version;
		System.out.println("�ַ�������ƴװ�����" + Data);
		String plainData = new String(Base64.encode(Data.getBytes()));
		System.out.println("ת����Base64�����ݣ�" + plainData);
		
        //ǩ��
		String chkValue = null;
		int KeyUsage = 0;
		PrivateKey key = new PrivateKey();
	    key.buildKey(merId, KeyUsage, MerKeyPath);
		SecureLink sl = new SecureLink(key);
		chkValue = sl.Sign(plainData);
		System.out.println("ǩ������:"+ chkValue);
		
		TransactionBean query = new TransactionBean();
		query.setMerId(merId);
		query.setMerDate(merDate);
		query.setMerSeqId(merSeqId);
		query.setVersion(version);
		query.setChkValue(chkValue);
		

		request.setAttribute("queryInput", query);
		request.getRequestDispatcher(QUERY_JSP).forward(request, response);
		return;

}
	
	public  TransactionBean getChkValue(TransactionBean transactionBean)
			throws ServletException, IOException {
		
		String MerKeyPath = null;
		String merId = null;
		Properties config = Config.getInstance().getProperties();
		MerKeyPath = config.getProperty(KEY_CHINAPAY_MERKEY_FILEPATH);
		merId = config.getProperty(KEY_CHINAPAY_MERID);
		
		// ��ѯ��������׼��
		String merDate = transactionBean.getMerDate();
		String merSeqId = transactionBean.getMerSeqId();
		String version = "20150304";
		
		String Data = merId + merDate + merSeqId + version;
		logger.info("�ַ�������ƴװ�����" + Data);
		String plainData = new String(Base64.encode(Data.getBytes()));
		logger.info("ת����Base64�����ݣ�" + plainData);
		
		//ǩ��
		String chkValue = null;
		int KeyUsage = 0;
		PrivateKey key = new PrivateKey();
		key.buildKey(merId, KeyUsage, MerKeyPath);
		SecureLink sl = new SecureLink(key);
		chkValue = sl.Sign(plainData);
		logger.info("ǩ������:"+ chkValue);
		
		TransactionBean query = new TransactionBean();
		query.setMerId(merId);
		query.setMerDate(merDate);
		query.setMerSeqId(merSeqId);
		query.setVersion(version);
		query.setChkValue(chkValue);
		
		return query;
		
	}

}
