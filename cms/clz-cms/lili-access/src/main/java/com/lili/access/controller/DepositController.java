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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lili.cms.constant.Constant;
import com.lili.cms.util.DateUtil;
import com.lili.cms.util.StringUtil;
import com.lili.finance.service.ICmsDepositService;
import com.lili.finance.vo.DepositVo;
import com.lili.user.model.User;


/**
 * 提现控制台
 * 
 * @author lzb
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/bankDeposit")
public class DepositController extends BaseController {
	Logger logger = Logger.getLogger(DepositController.class);
	
 	@Autowired
	ICmsDepositService depositService;

	/**
	 * 查询
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ResponseBody
	public String query(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			DepositVo depositVo = (DepositVo) buildObject(request, DepositVo.class);
			String status = request.getParameter("status");
			if (status != null && !"".equals(status)) {
				depositVo.setCheckStatus(Integer.valueOf(status));
			}
			depositVo.setMobile(request.getParameter("phoneNo"));
			resp = depositService.query(depositVo);
		} catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 提现成功
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@Transactional
	@RequestMapping(value = "/pass", method = RequestMethod.POST)
	@ResponseBody
	public String pass(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String resp = null;
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/json"); 
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			String checker = user.getAccount(); 
			String checkRemark  = request.getParameter("checkRemark");
			String id = request.getParameter("idList");
			resp = depositService.pass(checker, checkRemark, id).build();
		} catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 提现失败/财务拒绝
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@Transactional
	@RequestMapping(value = "/reject", method = RequestMethod.POST)
	@ResponseBody
	public String reject(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String resp = null;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json"); 
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			String checker = user.getAccount(); 
			String checkRemark  = request.getParameter("checkRemark");
			String id = request.getParameter("idList");
			String handlerType = request.getParameter("handlerType");
			resp = depositService.reject(checker, checkRemark, id, handlerType).build();
		} catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 下载TXT
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/downLoad", method = RequestMethod.GET)
	@ResponseBody
	public void downLoad(HttpServletRequest request, HttpServletResponse response) {
		
		OutputStream os = null;
		BufferedOutputStream bos=null;
		OutputStreamWriter writer = null;
		BufferedWriter bw = null;
		try {
			DepositVo depositVo = (DepositVo) buildObject(request, DepositVo.class);
			depositVo.setMobile(request.getParameter("phoneNo"));
			String status = request.getParameter("status");
			if (status != null && !"".equals(status)) {
				depositVo.setCheckStatus(Integer.valueOf(status));
			}
			Date date = new Date(); 
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS"); 
	    	String time = dateFormat.format(date) ;
			String fileName =  new String(Constant.SHEET_DEPOSIT_FILE_NAME.getBytes("UTF-8"),"ISO8859-1")  + time + ".txt";
			
			Integer totalMoney = depositService.queryTotalMoney(depositVo);
			List<DepositVo> depositList = depositService.queryDownDepositList(depositVo);
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
	        bw.write(String.valueOf(totalMoney));//总金额
	        bw.write("|");
	        bw.write(String.valueOf(depositList.size()));//总笔数
	        bw.write("|");
	        bw.write("|");
	        bw.write("\r\n");
	        //请求文件体
	        for (int i = 0; i < depositList.size(); i++) {
	        	   bw.write(Constant.MERCHANT_CODE);//商户代码
		           bw.write("|");
		           bw.write(StringUtil.getUUID());	//商户订单
		           bw.write("|");
		           bw.write(Constant.CNY_CODE);//人民币
		           bw.write("|");
		           bw.write(String.valueOf(depositList.get(i).getMoney()));	//单笔金额
		           bw.write("|");
		           bw.write("000000");//产品类型，可选
		           bw.write("|");
		           bw.write(Constant.BANK_CARD_TYPE);//账号类型 01 银行卡 02存折
		           bw.write("|");
		           bw.write(depositList.get(i).getBankCard());//账号
		           bw.write("|");
		           bw.write(depositList.get(i).getCardName());//户名
		           bw.write("|");
		           //bw.write(deposit.getBankCard());//开户行代码
		           bw.write("|");
		           //bw.write(deposit.getBankCard());//开户行省
		           bw.write("|");
		          // bw.write(deposit.getBankCard());//开户行市
		           bw.write("|");
		           if(depositList.get(i).getBankName() == null || "".equals(depositList.get(i).getBankName())){
		           }
		           else {
		        	   bw.write(depositList.get(i).getBankName());//开户行名称
		           }
		           bw.write("|");
		           if(depositList.get(i).getIdNumber() == null || "".equals(depositList.get(i).getIdNumber())){
		        	   bw.write("|");
		           }
		           else {
		        	   bw.write(Constant.IDENTITY_CARD);//证件类型
		        	   bw.write("|");
		        	   bw.write(depositList.get(i).getIdNumber());//证件号码
		           }
		           bw.write("|");
		           if(depositList.get(i).getMobile() == null || "".equals(depositList.get(i).getMobile())){
		           }
		           else {
		        	   bw.write(depositList.get(i).getMobile());//手机号
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
			e.printStackTrace();
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
				e.printStackTrace();
	        }
	    }
	}

	/**
	 * 下载EXCEL
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/downLoadExcel", method = RequestMethod.GET)
	@ResponseBody
	public void downLoadExcel(HttpServletRequest request, HttpServletResponse response) {
		OutputStream os = null;
		try {
			DepositVo depositVo = (DepositVo) buildObject(request, DepositVo.class);
			depositVo.setMobile(request.getParameter("phoneNo"));
			String status = request.getParameter("status");
			if (status != null && !"".equals(status)) {
				depositVo.setCheckStatus(Integer.valueOf(status));
			}
			Date date = new Date(); 
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS"); 
			String time = dateFormat.format(date) ;
			String fileName =  new String(Constant.SHEET_DEPOSIT_FILE_NAME.getBytes("UTF-8"),"ISO8859-1")  + time + ".xls";
			
			List<DepositVo> depositList = depositService.queryDownDepositList(depositVo);
			Workbook wb = export(depositList);  
			os = response.getOutputStream();  
			response.setHeader("Content-disposition", "attachment;filename=" +fileName);  
			response.setContentType("application/vnd.ms-excel");  
			wb.write(os);  
			
			os.flush();  
			os.close();  
		} 
		catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
		}
		finally {
			try {
				if(os != null) {
					os.close();
				}
			} 
			catch (IOException e) {
				logger.error("********************************* error："+e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/**
	 * 财务确认
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@Transactional
	@RequestMapping(value = "/financePass", method = RequestMethod.POST)
	@ResponseBody
	public String financePass(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String resp = null;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/json"); 
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			String checker = user.getAccount(); 
			String checkRemark  = request.getParameter("checkRemark");
			String id = request.getParameter("idList");
			resp = depositService.financePass(checker, checkRemark, id).build();
		} catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 出纳转账
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@Transactional
	@RequestMapping(value = "/tellerTransfer", method = RequestMethod.POST)
	@ResponseBody
	public String tellerTransfer(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/json"); 
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			String transfer = user.getAccount(); 
			String id = request.getParameter("idList");
			resp = depositService.tellerTransfer(transfer, id).build();
		} 
		catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}
	
	public static final String[] excelHeader = { "申请时间", "申请人", "金额", "提现到", "卡号", "财务反馈"};  
	
	public Workbook  export(List<DepositVo> list) {
		 Workbook wb = null; 
			try {
				wb = new SXSSFWorkbook(100);  //内存里一次只留多少行,几十万行无压力，不怕OOM
				Sheet sheet = wb.createSheet(Constant.SHEET_DEPOSIT_INFO);  //设置工作表标题
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
				sheet.setColumnWidth(2, 3000);
				sheet.setColumnWidth(3, 3000);
				sheet.setColumnWidth(4, 6000);
				sheet.setColumnWidth(5, 3000);
				
				HashMap<Integer,String> map = new HashMap<Integer,String>();
				map.put(0, "审核中");
				map.put(1, "提现成功");
				map.put(2, "交易失败");
				map.put(3, "已确认");
				map.put(4, "银行处理中");
				map.put(5, "银行处理失败");
				
				// 遍历集合数据，产生数据行 
				DepositVo DepositVo = null;
				String money = null;
				//String monstr = null;
				for (int i = 0; i < list.size(); i++) {  
				    row = sheet.createRow(i + 1);  
				    DepositVo = list.get(i);  
				    money = DepositVo.getMoney().toString();
				    row.createCell(0).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DepositVo.getApplyTime()));  
				    row.createCell(1).setCellValue(DepositVo.getCardName());  
				    row.createCell(2).setCellValue(money);  
				    row.createCell(3).setCellValue(DepositVo.getType());  
				    row.createCell(4).setCellValue(DepositVo.getBankCard());  
				    row.createCell(5).setCellValue(map.get(DepositVo.getCheckStatus()));  
				}
			} 
			catch (Exception e) {
				e.printStackTrace();
			}  
	        return wb;  
	}

}
