package com.lili.access.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.lili.coach.model.Coach;
import com.lili.coach.model.CoachBDTO;
import com.lili.coach.model.CoachVo;
import com.lili.coach.service.CMSCoachService;
import com.lili.log.model.LogCommon;
import com.lili.student.model.StudentBDTO;
import com.lili.student.service.CMSStudentService;

@Controller
@Scope("prototype")
@RequestMapping("/coach")
public class CoachController extends BaseController{
	
	@Autowired
	CMSCoachService cmsCoachService;
	@Autowired
	CMSStudentService cmsStudentService;
	
//	@Autowired
//	BankCardVerifyManager bankCardVerifyManager;
	
//	@Autowired
//	UserMoneyManager userMoneyManager;


	public static final String[] excelHeader = { "姓名", "电话", "性别", "所教车型","绑定学员数", "身份证号","所属驾校","账号状态"};  
	
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
    @ResponseBody
    public String add(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			Coach coach = (Coach) buildObject(request, Coach.class);
			LogCommon log = initLogParams(request, LogConstant.MENU_ID_COACH, LogConstant.ACTION_ADD);
			return cmsCoachService.insertOne(log,coach).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value="/batch", method= RequestMethod.GET)
    @ResponseBody
    public String batch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			CoachBDTO dto = (CoachBDTO) buildObject(request, CoachBDTO.class);
			final String rsp = cmsCoachService.findBatch(dto).build();
			return rsp;
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	

	@RequestMapping(value="/car", method= RequestMethod.GET)
    @ResponseBody
    public String car(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsCoachService.findCoachCar(getParamLong(request, "coachId")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value="/view", method=RequestMethod.GET)
    @ResponseBody
    public String view(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsCoachService.findOne(getParamLong(request, "coachId")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}


	@RequestMapping(value="/new-stu", method= RequestMethod.POST)
    @ResponseBody
    public String newStu(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			LogCommon log = initLogParams(request, LogConstant.MENU_ID_COACH, LogConstant.ACTION_UPDATE);
			return cmsCoachService.insertCSRelation(log,getParamStr(request, "studentIds"),getParamLong(request, "coachId")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value="/del-rel", method= RequestMethod.POST)
    @ResponseBody
    public String delRelation(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			LogCommon log = initLogParams(request, LogConstant.MENU_ID_COACH, LogConstant.ACTION_UPDATE);
			return cmsCoachService.deleteCCRelation(log,getParamLong(request, "coachId"),getParamLong(request, "carId")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value="/new-car", method= RequestMethod.POST)
    @ResponseBody
    public String newCar(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			LogCommon log = initLogParams(request, LogConstant.MENU_ID_COACH, LogConstant.ACTION_UPDATE);
			return cmsCoachService.updateCCRelation(log,getParamLong(request, "c_carId"),getParamLong(request, "o_carId"),getParamLong(request, "coachId")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	
	@RequestMapping(value="/student", method= RequestMethod.GET)
    @ResponseBody
    public String student(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			StudentBDTO dto = (StudentBDTO) buildObject(request, StudentBDTO.class);
			return cmsStudentService.findStudentByCoach(dto).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value="/update", method= RequestMethod.POST)
    @ResponseBody
    public String update(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			Coach dto = (Coach) buildObject(request, Coach.class);
			LogCommon log = initLogParams(request, LogConstant.MENU_ID_COACH, LogConstant.ACTION_UPDATE);
			return cmsCoachService.updateOne(log,dto).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}

	}
	
	@RequestMapping(value="/account", method= RequestMethod.GET)
	@ResponseBody
	public String account(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			CoachBDTO dto = (CoachBDTO) buildObject(request, CoachBDTO.class);
			String operateType = request.getParameter("operateType");
			return cmsCoachService.findCoachInfo(dto, operateType); 
		} 
		catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 导出EXCEL
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
		try {
			CoachBDTO dto = (CoachBDTO) buildObject(request, CoachBDTO.class);
			Workbook wb = getWorkbook(cmsCoachService.getExportSource(dto));
			sendExcel(response, wb, Constant.SHEET_COACH_FILE_NAME);
		} catch (Exception e) {
			access.error(e.getMessage());
		}
	}
	

	
	public Workbook getWorkbook(List<CoachVo> list){

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
				CoachVo vo = null;
				for (int i = 0; i < list.size(); i++) {  
				    vo = list.get(i);  
				    if(vo.getCoachId() == 0)
				    	continue;
				    row = sheet.createRow(i + 1);  
				    row.setRowStyle(style);
				    row.createCell(0).setCellValue(vo.getName());  
				    row.createCell(1).setCellValue(vo.getPhoneNum());  
				    row.createCell(2).setCellValue((vo.getSex() == 0)?"女":"男");  
				    if(vo.getDriveType() != null){
				    	row.createCell(3).setCellValue((vo.getDriveType() == 2)?"C2":"C1");  
				    }
				    else {
				    	row.createCell(3).setCellValue("");
					}
				    row.createCell(4).setCellValue(vo.getStudentAmount());  
				    row.createCell(5).setCellValue(vo.getIdNumber());   
				    row.createCell(6).setCellValue(vo.getSchoolName());   
				    row.createCell(7).setCellValue("正常");   
				}
			} 
			catch (Exception e) {
				access.error("|||exception e :" + e.getMessage() + " when export coach");
			}  
		
		
		return wb;
	}
}
