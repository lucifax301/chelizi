package com.lili.access.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lili.cms.constant.Constant;
import com.lili.cms.util.DateUtil;
import com.lili.cms.util.StringUtil;
import com.lili.finance.service.ICmsBankCardVerifyService;
import com.lili.finance.vo.BankCardVerifyVo;
import com.lili.user.model.User;

/**
 * 绑定银行卡控制台
 * @author lzb
 */
@Controller
@Scope("prototype")
@RequestMapping("/boundBankCard")
public class BoundBankCardController extends BaseController {
	Logger logger = Logger.getLogger(BoundBankCardController.class);
	
	@Autowired
	ICmsBankCardVerifyService bankCardVerifyService;

	/**
	 * 获取银行卡绑定列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ResponseBody
	public Object query(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			BankCardVerifyVo bankCardVo = (BankCardVerifyVo) buildObject(request, BankCardVerifyVo.class);
			String status = request.getParameter("status");
			if(status != null && !"".equals(status)){
				bankCardVo.setStatus(Integer.valueOf(status));
			}
			resp = bankCardVerifyService.query(bankCardVo);
		} catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 成功
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/pass", method = RequestMethod.POST)
	@ResponseBody
	public Object pass(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			String checker = user.getAccount(); 
			String remark  = request.getParameter("checkRemark");
			String id = request.getParameter("idList");
			//String statusList = request.getParameter("statusList");
			resp = bankCardVerifyService.pass(checker, remark, id).build();
		} catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 失败
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/reject", method = RequestMethod.POST)
	@ResponseBody
	public Object reject(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			String checker = user.getAccount(); 
			String remark  = request.getParameter("checkRemark");
			String id = request.getParameter("idList");
			//String statusList = request.getParameter("statusList");
			resp = bankCardVerifyService.reject(checker, remark, id).build();
		} catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 下载txt
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/downLoad", method = RequestMethod.GET)
	@ResponseBody
	public void  downLoad(HttpServletRequest request, HttpServletResponse response) {
		OutputStream os = null;
		BufferedOutputStream bos=null;
		OutputStreamWriter writer = null;
		BufferedWriter bw = null;
		try {
			BankCardVerifyVo bankCardVo = (BankCardVerifyVo) buildObject(request, BankCardVerifyVo.class);
		    Date date = new Date(); 
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS"); 
	    	String time = dateFormat.format(date) ;
			String fileName = new String(Constant.SHEET_BOUND_BANK_FILE_NAME.getBytes("UTF-8"),"ISO8859-1") + time + ".txt";
			List<BankCardVerifyVo> bankCardList = bankCardVerifyService.downLoad(bankCardVo);
	        response.setHeader("Content-disposition", "attachment;filename=" + fileName);  
	        response.setContentType("text/plain");   
	        os = response.getOutputStream();  
	        
	        bos=new BufferedOutputStream(os);
	        writer = new OutputStreamWriter(bos);
	        bw = new BufferedWriter(writer);
	        //请求头文件
	        bw.write(Constant.MERCHANT_CODE);//商户或机构代码
	        bw.write("|");
	        bw.write(StringUtil.getNumber4Random());//批次号
	        bw.write("|");
	        bw.write(DateUtil.getTodayDate());//上传日期
	        bw.write("|");
	        bw.write(String.valueOf(bankCardList.size()));//总金额
	        bw.write("|");
	        bw.write(String.valueOf(bankCardList.size()));//总笔数
	        bw.write("|");
	        bw.write("|");
	        bw.write("\r\n");
	        //请求文件体
	        for (int i = 0; i < bankCardList.size(); i++) {
	        	   bw.write(Constant.MERCHANT_CODE);//商户代码
		           bw.write("|");
		           bw.write(StringUtil.getUUID());	//商户订单
		           bw.write("|");
		           bw.write(Constant.CNY_CODE);//人民币
		           bw.write("|");
		           bw.write(Constant.BANK_CARD_TEST);	//单笔金额 1分钱
		           bw.write("|");
		           bw.write("000000");//产品类型，可选
		           bw.write("|");
		           bw.write(Constant.BANK_CARD_TYPE);//账号类型 01 银行卡 02存折
		           bw.write("|");
		           bw.write(bankCardList.get(i).getBankNo());//账号
		           bw.write("|");
		           bw.write(bankCardList.get(i).getName());//户名
		           bw.write("|");
		           //bw.write(deposit.getBankCard());//开户行代码
		           bw.write("|");
		           //bw.write(deposit.getBankCard());//开户行省
		           bw.write("|");
		          // bw.write(deposit.getBankCard());//开户行市
		           bw.write("|");
		           if(bankCardList.get(i).getBankName() == null || "".equals(bankCardList.get(i).getBankName())){
		           }
		           else {
		        	   bw.write(bankCardList.get(i).getBankName());//开户行名称
		           }
		           bw.write("|");
		           if(bankCardList.get(i).getIdNumber() == null || "".equals(bankCardList.get(i).getIdNumber())){
		        	   bw.write("|");
		           }
		           else {
		        	   bw.write(Constant.IDENTITY_CARD);//证件类型
		        	   bw.write("|");
		        	   bw.write(bankCardList.get(i).getIdNumber());//证件号码
		           }
		           bw.write("|");
		           if(bankCardList.get(i).getMobile() == null || "".equals(bankCardList.get(i).getMobile())){
		           }
		           else {
		        	   bw.write(bankCardList.get(i).getMobile());//手机号
		           }
		           bw.write("|");
		           bw.write("|");
		           bw.write("|");
		           bw.write("|");
		           bw.write("|");
		           bw.write("|");
		           bw.write("|");
		           bw.write("\r\n");
	        }
	        bw.flush();
	        os.flush();  
	        bw.close();
	        os.close();  
		}
		catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
		}
		finally {
		   try {
	        	if(os != null) {
	        		os.close();
	        	}
	        	if(bos != null) {
	        		bos.close();
	        	}
	        	if(writer != null) {
	        		writer.close();
	        	}
	        	if(bw != null) {
	        		bw.close();
	        	}
	        } 
	        catch (IOException e) {
	        	logger.error("********************************* error："+e.getMessage());
	        }
	    }
	}
	
	/**
	 * 下载EXCEL
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/downLoadExcel", method = RequestMethod.GET)
	@ResponseBody
	public void  downLoadExcel(HttpServletRequest request, HttpServletResponse response) {
		OutputStream out = null;
		try {
			BankCardVerifyVo bankCardVo = (BankCardVerifyVo) buildObject(request, BankCardVerifyVo.class);
			Date date = new Date(); 
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS"); 
			String time = dateFormat.format(date) ;
			String fileName = new String(Constant.SHEET_BOUND_BANK_FILE_NAME.getBytes("UTF-8"),"ISO8859-1") + time + ".xls";
			
			List<BankCardVerifyVo> batch  =  bankCardVerifyService.downLoadExcel(bankCardVo);
			Workbook wb = export(batch);  
			out = response.getOutputStream();  
			response.setHeader("Content-disposition", "attachment;filename=" + fileName);  
			response.setContentType("application/vnd.ms-excel");  
			wb.write(out);  
			out.flush();  
			out.close();  
		}
		catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
		}
		finally {
			try {
				if(out != null){
					out.close();
				}
			} 
			catch (IOException e) {
				logger.error("********************************* error："+e.getMessage());
			}
		}
	}
	
	public static final String[] excelHeader = { "申请时间", "申请人", "开户行", "卡号", "财务反馈"};  
	
	public Workbook export(List<BankCardVerifyVo> list) {
		Workbook wb = null; 
		try {
			wb = new SXSSFWorkbook(100); //内存里一次只留 多少行,几十万行无压力，不怕OOM
			Sheet sheet = wb.createSheet(Constant.SHEET_BOUND_BANK_CARD);  //设置工作表标题
			Row row = sheet.createRow((int) 0);  
			CellStyle style = wb.createCellStyle();  
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);   // 水平居中

			// 产生表格标题行  
			for (int i = 0; i < excelHeader.length; i++) {  
			    Cell cell = row.createCell(i);  
			    cell.setCellValue(excelHeader[i]);  
			    cell.setCellStyle(style);  
			}  
			//第一个参数代表列id(从0开始),第2个参数代表宽度值
			sheet.setColumnWidth(0, 6000);
			sheet.setColumnWidth(1, 3000);
			sheet.setColumnWidth(2, 4000);
			sheet.setColumnWidth(3, 6000);
			sheet.setColumnWidth(4, 3000);
			
			HashMap<Integer,String> map = new HashMap<Integer,String>();
			map.put(0, "待反馈");
			map.put(1, "有效卡号");
			map.put(2, "无效卡号");
			
			// 遍历集合数据，产生数据行 
			BankCardVerifyVo bankCardVerify = null;
			for (int i = 0; i < list.size(); i++) {  
			    row = sheet.createRow(i + 1);  
			    bankCardVerify = list.get(i);  
			    row.createCell(0).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(bankCardVerify.getCreateTime()));  
			    row.createCell(1).setCellValue(bankCardVerify.getName());  
			    row.createCell(2).setCellValue(bankCardVerify.getBankName());  
			    row.createCell(3).setCellValue(bankCardVerify.getBankNo());  
			    row.createCell(4).setCellValue(map.get(bankCardVerify.getStatus()));  
			}
		} 
		catch (Exception e) {
			logger.error("************************************ error: " + e.getMessage());
		}  
        return wb;  
	}
	
}
