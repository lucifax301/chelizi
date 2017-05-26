package com.lili.access.controller;


import java.io.IOException;
import java.io.OutputStream;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lili.cms.constant.Constant;
import com.lili.order.service.ICmsEnrollOrderService;
import com.lili.order.vo.EnrollOrderVo;
import com.lili.user.model.User;


/**
 * 报名列表控制台
 * @author lzb
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/enrollOrder")
public class EnrollOrderController extends BaseController{
	Logger logger = Logger.getLogger(EnrollOrderController.class);
	
	@Autowired
	ICmsEnrollOrderService enrollOrderService;
	
	/**
	 * 获取报名列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ResponseBody
	public Object query(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			EnrollOrderVo enrollOrderVo = (EnrollOrderVo) buildObject(request, EnrollOrderVo.class);
			String cityId = request.getParameter("cityId");
			String payState = request.getParameter("payState");
			String isdel = request.getParameter("isdel");
			String dltype = request.getParameter("dltype");
			String orderState = request.getParameter("orderState");
			if(cityId != null && !"".equals(cityId)){
				enrollOrderVo.setCityId(Integer.valueOf(cityId));
			}
			if(payState != null && !"".equals(payState)){
				enrollOrderVo.setPayState(Integer.valueOf(payState));
			}
			if(isdel != null && !"".equals(isdel)){
				enrollOrderVo.setIsdel(Integer.valueOf(isdel));
			}
			if(dltype != null && !"".equals(dltype)){
				enrollOrderVo.setDltype(Integer.valueOf(dltype));
			}
			if(orderState != null && !"".equals(orderState)){
				enrollOrderVo.setOrderState(Byte.parseByte(orderState));
			}
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			Long schoolNo = user.getSchoolId(); 
			
			resp  = enrollOrderService.query(enrollOrderVo, schoolNo);
		} catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 报名详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	@ResponseBody
	public Object detail(@RequestParam String orderId) {
		String resp = null;
		try {
			resp = enrollOrderService.detail(orderId).build();
		} catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 下载EXCEL
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/downLoadExl", method = RequestMethod.GET)
	@ResponseBody
	public void  downLoadExl(HttpServletRequest request, HttpServletResponse response) {
		OutputStream out = null;
		try {
			EnrollOrderVo enrollOrderVo = (EnrollOrderVo) buildObject(request, EnrollOrderVo.class);
			Date date = new Date(); 
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS"); 
			String time = dateFormat.format(date) ;
			String fileName = new String(Constant.SHEET_ENROLL_ORDER.getBytes("UTF-8"),"ISO8859-1") + time + ".xls";
			
			List<EnrollOrderVo> batch  =  enrollOrderService.downLoadExl(enrollOrderVo);
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
	
	public static final String[] excelHeader = { "提交时间", "订单号", "学员", "电话号码", "驾考类型", "报名城市", "报名包全称", "金额", "支付状态", "支付方式", "订单状态", "支付时间"};  
	
	public Workbook export(List<EnrollOrderVo> list) {
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
			sheet.setColumnWidth(5, 3000);
			sheet.setColumnWidth(6, 3000);
			sheet.setColumnWidth(7, 3000);
			sheet.setColumnWidth(8, 3000);
			sheet.setColumnWidth(9, 3000);
			sheet.setColumnWidth(10, 3000);
			sheet.setColumnWidth(11, 3000);
			sheet.setColumnWidth(12, 3000);
			sheet.setColumnWidth(13, 6000);
			
			//0未开始，1已提交，100支付成功，101支付失败，102已结款，103退款中，104退款失败，105已退款
			HashMap<Integer,String> map = new HashMap<Integer,String>();
			map.put(0, "未开始");
			map.put(1, "已提交");
			map.put(100, "支付成功");
			map.put(101, "支付失败");
			map.put(102, "已结款");
			map.put(103, "退款中");
			map.put(104, "退款失败");
			map.put(105, "已退款");
			
			HashMap<Integer,String> delMap = new HashMap<Integer,String>();
			delMap.put(0, "正常");
			delMap.put(1, "已取消");
			
			HashMap<Integer,String> typeMap = new HashMap<Integer,String>();
			typeMap.put(1, "C1");
			typeMap.put(2, "C2");
			
			// 遍历集合数据，产生数据行 
			EnrollOrderVo enrollOrderVo = null;
			for (int i = 0; i < list.size(); i++) {  
			    row = sheet.createRow(i + 1);  
			    enrollOrderVo = list.get(i);  
			    row.createCell(0).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(enrollOrderVo.getCtime()));  
			    row.createCell(1).setCellValue(enrollOrderVo.getOrderId());  
			    row.createCell(2).setCellValue(enrollOrderVo.getName());  
			    row.createCell(3).setCellValue(enrollOrderVo.getPhoneNum());  
			    row.createCell(4).setCellValue(typeMap.get(enrollOrderVo.getDltype()));  
			    row.createCell(5).setCellValue(enrollOrderVo.getRegion());  
			    row.createCell(6).setCellValue(enrollOrderVo.getCityId());  
			    row.createCell(7).setCellValue(enrollOrderVo.getRegion());  
			    row.createCell(8).setCellValue(enrollOrderVo.getCityName());  
			    row.createCell(9).setCellValue(enrollOrderVo.getPrice());  
			    row.createCell(10).setCellValue(map.get(enrollOrderVo.getPayState()));  
			    row.createCell(11).setCellValue(enrollOrderVo.getPayWay());  
			    row.createCell(12).setCellValue(delMap.get(enrollOrderVo.getIsdel()));  
			    row.createCell(13).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(enrollOrderVo.getPayTime()));  
			}
		} 
		catch (Exception e) {
			logger.error("************************************ error: " + e.getMessage());
		}  
        return wb;  
	}
	

}
