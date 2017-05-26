package com.lili.access.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.lili.cms.constant.LogConstant;
import com.lili.cms.util.DateUtil;
import com.lili.log.model.LogCommon;
import com.lili.order.model.Order;
import com.lili.order.model.OrderBDTO;
import com.lili.order.service.CMSOrderService;
import com.lili.order.vo.OrderRefundVo;
import com.lili.user.model.User;

@Controller
@Scope("prototype")
@RequestMapping("/order")
public class OrderController extends BaseController{

	@Autowired
	CMSOrderService cmsOrderService;

	public static final String[] excelHeader = { "订单号", "学员", "教练", "上课时间", "下课时间","课程","教练车","金额(元)","支付状态","订单状态"};  
	
	
	@RequestMapping(value="/view", method=RequestMethod.GET)
    @ResponseBody
    public String view(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsOrderService.findOne(getParamStr(request, "orderId")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}


	@RequestMapping(value="/batch", method= RequestMethod.GET)
    @ResponseBody
    public String batch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			OrderBDTO dto = (OrderBDTO) buildObject(request, OrderBDTO.class);
			return cmsOrderService.findBatch(dto).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
    }
	

	@RequestMapping(value="/update-batch", method= RequestMethod.POST)
    @ResponseBody
    public String updateBatch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			LogCommon log = initLogParams(request, LogConstant.MENU_ID_STUDENT, LogConstant.ACTION_UPDATE);
			return cmsOrderService.updateByIds(log,getParamStr(request, "orderIds"),getParamStr(request, "coachIds")
												,getParamStr(request, "studentIds"),getParamStr(request, "remark")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value = "/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			OrderBDTO dto = (OrderBDTO) buildObject(request, OrderBDTO.class);
			List<Order> list = cmsOrderService.getExportSource(dto);
			Workbook wb = getWorkbook(list);
			sendExcel(response, wb, Constant.SHEET_ORDER_FILE_NAME);
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	public Workbook getWorkbook(List<Order> list){

		 Workbook wb = null; 
			try {
				wb = new SXSSFWorkbook(100);  //内存里一次只留多少行,几十万行无压力，不怕OOM
				Sheet sheet = wb.createSheet(Constant.SHEET_STUDENT_FILE_NAME);  //设置工作表标题
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
				sheet.setColumnWidth(2, 6000);
				sheet.setColumnWidth(7, 8000);
				
				// 遍历集合数据，产生数据行 
				Order order = null;
				for (int i = 0; i < list.size(); i++) {  
				    row = sheet.createRow(i + 1);  
				    row.setRowStyle(style);
				    order = list.get(i);  
				    row.createCell(0).setCellValue(order.getOrderId());  
				    row.createCell(1).setCellValue(order.getStudentName());  
				    row.createCell(2).setCellValue(order.getCoachName());  
				    row.createCell(3).setCellValue(order.getRstart()==null?null:DateUtil.formatDatetime(order.getRstart()));  
				    row.createCell(4).setCellValue(order.getRend()==null?null:DateUtil.formatDatetime(order.getRend()));  
				    row.createCell(5).setCellValue(order.getCourseName());   
				    row.createCell(6).setCellValue(order.getCarNo());   
				    row.createCell(7).setCellValue(order.getPrice());  
				    row.createCell(8).setCellValue(order.getPayState()==null?null:order.getPayStateEnum(order.getPayState())); 
				    row.createCell(9).setCellValue(order.getOrderState()==null?null:order.getOrderStateEnum(order.getOrderState())); 
				}
			} 
			catch (Exception e) {
				access.error("|||exception e :" + e.getMessage() + " when export order");
			}  
		
		
		return wb;
	}
	
	/**
	 * 申请退款
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/refund", method= RequestMethod.POST)
	@ResponseBody
	public String refund(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			String orderId = request.getParameter("orderId");
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			String asker = user.getAccount(); 
			String refundMoney = request.getParameter("refundMoney");
			String remark = request.getParameter("remark");
			return cmsOrderService.refund(orderId, asker, refundMoney, remark).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 申请退款
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/queryRefund", method= RequestMethod.GET)
	@ResponseBody
	public String queryRefund(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			OrderRefundVo dto = (OrderRefundVo) buildObject(request, OrderRefundVo.class);
			return cmsOrderService.queryRefund(dto);
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 确认退款
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/subRefund", method= RequestMethod.POST)
	@ResponseBody
	public String subRefund(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			String orderId = request.getParameter("orderId");
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			String asker = user.getAccount(); 
			return cmsOrderService.subRefund(orderId, asker).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
}
