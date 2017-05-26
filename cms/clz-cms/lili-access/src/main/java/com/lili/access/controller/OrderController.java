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
import com.lili.order.model.InsuranceOrderBDTO;
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
	public static final String[] insuranceExcelHeader = { "订单号", "学员ID", "学员姓名", "学员电话", "城市ID","城市名称","保险金额(元)","保险年限","保单号","生效时间","失效时间","支付时间","支付状态","支付方式","理赔时间","理赔状态","理赔备注","退款时间","退款状态","退款备注","退款金额","回访时间","回访状态","回访备注","提交时间"};  
	
	/**
	 * 详情
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/view", method=RequestMethod.GET)
    @ResponseBody
    public String view(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsOrderService.findOne(getParamStr(request, "orderId")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 查看订单列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * 关闭订单
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/update-batch", method= RequestMethod.POST)
    @ResponseBody
    public String updateBatch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			LogCommon log = initLogParams(request, LogConstant.MENU_ID_ORDER, LogConstant.ACTION_UPDATE);
			return cmsOrderService.updateByIds(log,getParamStr(request, "orderIds"),getParamStr(request, "coachIds")
												,getParamStr(request, "studentIds"),getParamStr(request, "remark")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 导出数据
	 * @param request
	 * @param response
	 * @throws Exception
	 */
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
	 * 查询申请的退款
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
			String orderIdList = request.getParameter("orderIdList");
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			String asker = user.getAccount(); 
			return cmsOrderService.subRefund(orderIdList, asker).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 挂起订单
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/hangUp", method= RequestMethod.POST)
	@ResponseBody
	public String hangUp(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			String orderIdList = request.getParameter("orderIdList");
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			String asker = user.getAccount(); 
			return cmsOrderService.hangUp(orderIdList, asker, 1).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * CMS-订单调度-查询订单列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/schedule/orders", method= RequestMethod.GET)
	@ResponseBody
	public String getScheduleOrders(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			String stime = request.getParameter("stime");//下单时间段开始
			String etime = request.getParameter("etime");//下单时间段结束
			String state = request.getParameter("state");//调度状态：0-正常；1-已成功；2-已取消；3-已超时；-1-全部
			String lastMinutes = request.getParameter("lastMinutes");//持续时间，分钟
			String orderId = request.getParameter("orderId");//高级查询-订单号
			String stuName = request.getParameter("stuName");//高级查询-学员姓名
			String stuMobile = request.getParameter("stuMobile");//高级查询-学员手机号
			String pageNo = request.getParameter("pageNo");//分页-第几页
			String pageSize = request.getParameter("pageSize");//分页-每页数量
			
			
			return cmsOrderService.getScheduleOrders(stime,etime, state, lastMinutes, orderId,stuName,stuMobile,pageNo,pageSize).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	

	@RequestMapping(value="/schedule/notice", method= RequestMethod.GET)
	@ResponseBody
	public String getScheduleNotice(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			HttpSession session = request.getSession();
			return cmsOrderService.getScheduleNotice().build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	

	@RequestMapping(value="/schedule/coaches", method= RequestMethod.GET)
	@ResponseBody
	public String getScheduleCoaches(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			String orderId = request.getParameter("orderId");//订单号
			String dltype = request.getParameter("dltype");//驾考类型
			String isVip = request.getParameter("isVip");//是否特约教练
			String pageNo = request.getParameter("pageNo");//分页-第几页
			String pageSize = request.getParameter("pageSize");//分页-每页数量
			
			return cmsOrderService.getScheduleCoaches(orderId, dltype, isVip,pageNo,pageSize).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	@RequestMapping(value="/schedule/coach", method= RequestMethod.POST)
	@ResponseBody
	public String addScheduleCoach(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			String orderId = request.getParameter("orderId");//订单号
			String coachId = request.getParameter("coachId");
			String placeId = request.getParameter("placeId");
			String placeName = request.getParameter("placeName");
			String placeLge = request.getParameter("placeLge");
			String placeLae = request.getParameter("placeLae");
			
			return cmsOrderService.addScheduleCoach(orderId, coachId, placeId, placeName, placeLge, placeLae).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	
	/**
	 * 查看平安保险订单列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/insurance/list", method= RequestMethod.GET)
    @ResponseBody
    public String insuranceList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			InsuranceOrderBDTO dto = (InsuranceOrderBDTO) buildObject(request, InsuranceOrderBDTO.class);
			return cmsOrderService.findInsuranceList(dto).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
    }
	
	/**
	 * 更新平安保险订单列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/insurance/update", method= RequestMethod.POST)
    @ResponseBody
    public String updateInsurance(HttpServletRequest request,HttpServletResponse response,InsuranceOrderBDTO dto) throws Exception{
		try {
			return cmsOrderService.updateInsurance(dto).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
    }
	
	/**
	 * 通过订单号平安保险订单列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/insurance/findInsuranceById", method= RequestMethod.GET)
    @ResponseBody
    public String findInsuranceById(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			String insuranceId=request.getParameter("insuranceId");
			return cmsOrderService.findInsuranceById(insuranceId).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
    }
	
	/**
	 *  导出平安保险保单数据
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/insurance-export-excel", method = RequestMethod.GET)
	public void insuranceExportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			InsuranceOrderBDTO orderBDTO = (InsuranceOrderBDTO) buildObject(request, InsuranceOrderBDTO.class);
			List<InsuranceOrderBDTO> list = cmsOrderService.getInsuranceExportSource(orderBDTO);
			Workbook wb = getInsuranceWorkbook(list);
			sendExcel(response, wb, Constant.SHEET_STUDENT_INSURANCE_FILE_NAME);
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	public Workbook getInsuranceWorkbook(List<InsuranceOrderBDTO> list){

		 Workbook wb = null; 
			try {
				wb = new SXSSFWorkbook(100);  //内存里一次只留多少行,几十万行无压力，不怕OOM
				Sheet sheet = wb.createSheet(Constant.SHEET_STUDENT_INSURANCE_FILE_NAME);  //设置工作表标题
				Row row = sheet.createRow((int) 0);  
				CellStyle style = wb.createCellStyle();  
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);   // 水平居中

				// 产生表格标题行  
				for (int i = 0; i < insuranceExcelHeader.length; i++) {  
				    Cell cell = row.createCell(i);  
				    cell.setCellValue(insuranceExcelHeader[i]);  
				    cell.setCellStyle(style);  
				}  
				//第一个参数代表列id(从0开始),第2个参数代表宽度值
				sheet.setColumnWidth(0, 6000);
				sheet.setColumnWidth(2, 6000);
				sheet.setColumnWidth(7, 8000);
				
				// 遍历集合数据，产生数据行 
				InsuranceOrderBDTO order = null;
				for (int i = 0; i < list.size(); i++) {  
				    row = sheet.createRow(i + 1);  
				    row.setRowStyle(style);
				    order = list.get(i);  
				    row.createCell(0).setCellValue(order.getInsuranceId());  
				    row.createCell(1).setCellValue(order.getStudentId());  
				    row.createCell(2).setCellValue(order.getName());  
				    row.createCell(3).setCellValue(order.getPhoneNum());  
				    row.createCell(4).setCellValue(order.getCityId()==null?"":order.getCityId().toString());  
				    row.createCell(5).setCellValue(order.getCityName());  
				    row.createCell(6).setCellValue(order.getPrice()==null?0:order.getPrice()/100);  
				    row.createCell(7).setCellValue(order.getYear()==null?"":order.getYear().toString());  
				    row.createCell(8).setCellValue(order.getInsuranceNumber()); 
				    row.createCell(9).setCellValue(order.getEffectTime()==null?null:DateUtil.formatDatetime(order.getEffectTime()));  
				    row.createCell(10).setCellValue(order.getInvalidTime()==null?null:DateUtil.formatDatetime(order.getInvalidTime()));
				    row.createCell(11).setCellValue(order.getPayTime()==null?null:DateUtil.formatDatetime(order.getPayTime())); 
				    row.createCell(12).setCellValue(order.getPayState()==100?"支付成功":"未支付");  
				    row.createCell(13).setCellValue(order.getPayWay()); 
				    row.createCell(14).setCellValue(order.getCompensateTime()==null?null:DateUtil.formatDatetime(order.getCompensateTime()));  
				    row.createCell(15).setCellValue(order.getCompensate()==1?"已理赔":"未理赔");  
				    row.createCell(16).setCellValue(order.getCompensateRemark()); 
				    row.createCell(17).setCellValue(order.getRefundTime()==null?null:DateUtil.formatDatetime(order.getRefundTime())); 
				    row.createCell(18).setCellValue(order.getRefundState()==1?"已退款":"未退款");  
				    row.createCell(19).setCellValue(order.getRefundRemark()); 
				    row.createCell(20).setCellValue(order.getRefundPrice()==null?0:order.getRefundPrice()/100); 
				    row.createCell(21).setCellValue(order.getVisitTime()==null?null:DateUtil.formatDatetime(order.getVisitTime()));
				    row.createCell(22).setCellValue(order.getVisitState()==1?"已回访":"未回访");
				    row.createCell(23).setCellValue(order.getVisitRemark());
				    row.createCell(24).setCellValue(order.getOperationTime()==null?null:DateUtil.formatDatetime(order.getOperationTime()));  
				}
			} 
			catch (Exception e) {
				access.error("|||exception e :" + e.getMessage() + " when export order");
			}  
		
		
		return wb;
	}
	
	
	
}































