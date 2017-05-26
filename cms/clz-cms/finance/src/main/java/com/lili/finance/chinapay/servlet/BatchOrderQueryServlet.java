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

import com.lili.finance.chinapay.model.bean.TransactionBean;
import com.lili.finance.chinapay.util.Config;

import chinapay.Base64;
import chinapay.PrivateKey;
import chinapay.SecureLink;

public class BatchOrderQueryServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;
	private static final String KEY_CHINAPAY_MERKEY_FILEPATH = "chinapay.merkey.filepath";
	private static final String KEY_CHINAPAY_MERID = "chinapay.merid";

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("GBK");
		String MerKeyPath = null;
		String merId = null;
		Properties config = Config.getInstance().getProperties();
		MerKeyPath = config.getProperty(KEY_CHINAPAY_MERKEY_FILEPATH);
		merId = config.getProperty(KEY_CHINAPAY_MERID);

		// 查询订单数据准备
//		String merId = request.getParameter("MerId");
		String fromDate = request.getParameter("FromDate");// 8
		String toDate = request.getParameter("ToDate");
		String stat = request.getParameter("Stat");
		String version = "20090501";
		
		String Data = merId + fromDate + toDate + stat + version;
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
		
		TransactionBean charge = new TransactionBean();
		charge.setMerId(merId);
		charge.setFromDate(fromDate);
		charge.setToDate(toDate);
		charge.setStat(stat);
		charge.setVersion(version);
		charge.setChkValue(chkValue);
		

		request.setAttribute("chargeInput", charge);
		request.getRequestDispatcher("./BatchOrderCommit.jsp").forward(request, response);
		return;

}

}
