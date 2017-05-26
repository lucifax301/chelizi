package com.lili.access.controller;

import java.io.IOException;
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
import com.lili.cms.entity.ResponseMessage;
import com.lili.cms.util.LogUtil;
import com.lili.report.service.ICmsStatisticsTotalLiliService;
import com.lili.report.vo.StatisticsCoachVo;
import com.lili.report.vo.StatisticsOrderVo;
import com.lili.report.vo.StatisticsStudentProgress;
import com.lili.report.vo.StatisticsStudentVo;
import com.lili.student.model.StudentVo;
import com.lili.student.service.CMSStudentService;
import com.lili.user.model.User;

/**
 * 教务报表
 * 
 * @author lzb
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/statistics-total")
public class RStatisticsTotalController extends BaseController {
	
	private static Logger logger=Logger.getLogger(RStatisticsTotalController.class);

	public static final String[] excelHeader = { "阶段", "状态", "人数"};
	
	@Autowired
	CMSStudentService cmsStudentService;
	
	@Autowired
	ICmsStatisticsTotalLiliService statisticsTotalLiliService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public String list(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			String schoolId  =  String.valueOf(user.getSchoolId());
			resp = statisticsTotalLiliService.list(schoolId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 订单统计
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/order", method = RequestMethod.GET)
	@ResponseBody
	public String order(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		String resp = null;
		try {
			StatisticsOrderVo statisticsOrderVo = (StatisticsOrderVo) buildObject(request, StatisticsOrderVo.class);
			resp = statisticsTotalLiliService.order(statisticsOrderVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 学员统计
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/student", method = RequestMethod.GET)
	@ResponseBody
	public String student(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String resp = null;
		try {
			StatisticsStudentVo staStudentVo = (StatisticsStudentVo) buildObject(request, StatisticsStudentVo.class);
			resp = statisticsTotalLiliService.student(staStudentVo);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	@RequestMapping(value = "/progress/student/report", method = RequestMethod.GET)
	@ResponseBody
	public String studentProgressReport(StatisticsStudentProgress vo,HttpServletRequest request, HttpServletResponse response) throws IOException {
		String resp = null;
		try {
			
			ResponseMessage message = cmsStudentService.studentProgressReport(vo);
			resp=message.build();
		}
		catch (Exception e) {
			ResponseMessage message=new ResponseMessage().addResult("code", "1");
			resp=message.build();
			logger.warn(LogUtil.getStackMsg(e));
		}
		return resp;
	}
	
	@RequestMapping(value = "/progress/student", method = RequestMethod.GET)
	@ResponseBody
	public String studentProgress(StatisticsStudentProgress vo,HttpServletRequest request, HttpServletResponse response) throws IOException {
		String resp = null;
		try {
			
			ResponseMessage message = cmsStudentService.studentProgress(vo);
			resp=message.build();
		}
		catch (Exception e) {
			ResponseMessage message=new ResponseMessage().addResult("code", "1");
			resp=message.build();
			logger.warn(LogUtil.getStackMsg(e));
		}
		return resp;
	}
	
	@RequestMapping(value = "/progress/export", method = RequestMethod.GET)
	public void studentProgressExport(StatisticsStudentProgress vo,HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<StatisticsStudentProgress> list= cmsStudentService.studentProgressData(vo);
		Workbook wb = getWorkbook(list);
		sendExcel(response, wb, Constant.SHEET_STUDENT_PROGRESS_FILE_NAME);
	}
	
	public Workbook getWorkbook(List<StatisticsStudentProgress> list){

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
				
				
				// 遍历集合数据，产生数据行 
				StatisticsStudentProgress studentVo = null;
				for (int i = 0,length = list.size(); i < length; i++) {    
				    try {
						row = sheet.createRow(i + 1);  
						row.setRowStyle(style);
						studentVo = list.get(i);  
						row.createCell(0).setCellValue(getApplyexam(studentVo.getApplyexam()));  
						row.createCell(1).setCellValue(studentVo.getApplystate());  
						row.createCell(2).setCellValue(studentVo.getCount());  
						
					} catch (Exception e) {
						access.error("|||exception e :" + e.getMessage() + " when export student");
					}  
				}
			} 
			catch (Exception e) {
				access.error("|||exception e :" + e.getMessage() + " when export student");
			}  
		
		
		return wb;
	}
	
	private String getApplyexam(int type){
		String typeTex="";
		switch (type) {
        case 1:
            typeTex = "报名";
            break;
        case 2:
            typeTex = "支付";
            break;
        case 3:
            typeTex = "个人信息";
            break;
        case 4:
            typeTex = "邮寄资料";
            break;
        case 5:
            typeTex = "交表到驾校";
            break;
        case 6:
            typeTex = "递表受理";
            break;
        case 101:
            typeTex = "理论课";
            break;
        case 201:
            typeTex = "模拟考试";
            break;
        case 301:
            typeTex = "科目一排队";
            break;
        case 302:
            typeTex = "科目一";
            break;
		case 401:
            typeTex = "科目二排队";
            break;
        case 402:
            typeTex = "科目二";
            break;
        case 501:
            typeTex = "长考";
            break;
        case 601:
            typeTex = "科目三排队";
            break;
        case 602:
            typeTex = "科目三";
            break;
        case 701:
            typeTex = "科目四排队";
            break;
        case 702:
            typeTex = "科目四";
            break;
        case 801:
            typeTex = "拿证";
            break;
        
		}
		return typeTex;
	}

	/**
	 * 教练统计
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/coach", method = RequestMethod.GET)
	@ResponseBody
	public String coach(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String resp = null;
		try {
			StatisticsCoachVo statisticsCoachVo = (StatisticsCoachVo) buildObject(request, StatisticsCoachVo.class);
			resp = statisticsTotalLiliService.coach(statisticsCoachVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
}
