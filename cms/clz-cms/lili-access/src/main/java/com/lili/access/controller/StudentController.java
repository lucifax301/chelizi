package com.lili.access.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
import com.lili.cms.entity.ResponseMessage;
import com.lili.cms.util.LogUtil;
import com.lili.coach.model.CoachBDTO;
import com.lili.coach.service.CMSCoachService;
import com.lili.common.vo.ReqResult;
import com.lili.log.model.LogCommon;
import com.lili.report.vo.StatisticsStudentProgress;
import com.lili.school.service.SchoolService;
import com.lili.student.model.Student;
import com.lili.student.model.StudentAuthVo;
import com.lili.student.model.StudentBDTO;
import com.lili.student.model.StudentNBDTO;
import com.lili.student.model.StudentStateVo;
import com.lili.student.model.StudentVo;
import com.lili.student.service.CMSStudentService;

@Controller
@Scope("prototype")
@RequestMapping("/student")
public class StudentController extends BaseController{
	
	@Autowired
	CMSStudentService cmsStudentService;
	@Autowired
	CMSCoachService cmsCoachService;
	@Autowired
	SchoolService schoolService;
	
	public static final String[] excelHeader = { "姓名", "电话", "性别", "所属驾校", "绑定教练","所学车型","身份证号","账号状态","流水号"};  
	
	public static final String[] excelHeader2 = { "姓名", "电话", "性别", "所属驾校", "绑定教练","所学车型","身份证号","学习状态","流水号","账号状态"};
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
    @ResponseBody
    public String add(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			Student student = (Student) buildObject(request, Student.class);
			student.setIsImport((byte)1);
			LogCommon log = initLogParams(request, LogConstant.MENU_ID_STUDENT, LogConstant.ACTION_ADD);
			return cmsStudentService.insertOne(log,student).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value="/view", method=RequestMethod.GET)
    @ResponseBody
    public String view(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			StudentNBDTO student = (StudentNBDTO) buildObject(request, StudentNBDTO.class);
			return  cmsStudentService.findOne(student).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 只通过手机号查询学员信息的接口
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/phone", method=RequestMethod.GET)
    @ResponseBody
    public String phone(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return  new ResponseMessage().addPagedResult(cmsStudentService.findByPhone(getParamStrOrNull(request, "phoneNum"))).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 获取报名包详情,目前在重置学员状态时候用到
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/pack-detail", method=RequestMethod.GET)
    @ResponseBody
    public String packDetail(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			  Map<String, Object> result = cmsStudentService.findPackDetail(getParamStr(request, "userId"),
					getParamStr(request, "userType"),getParamStr(request, "applyttid")).getResult();
			  return new ResponseMessage().build(result);
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	@RequestMapping(value="/update-auth", method=RequestMethod.POST)
    @ResponseBody
    public String updateAuth(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			StudentAuthVo student = (StudentAuthVo) buildObject(request, StudentAuthVo.class);
			LogCommon log = initLogParams(request, LogConstant.MENU_ID_STUDENT, LogConstant.ACTION_UPDATE);
			return cmsStudentService.updateAuthState(log,getParamInt(request, "type")
					,getParamStr(request, "ids"),student).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value="/img", method=RequestMethod.GET)
    @ResponseBody
    public String studentImg(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsStudentService.findImgById(getParamLong(request, "studentId")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value="/coach", method=RequestMethod.GET)
    @ResponseBody
    public String coach(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsStudentService.findCCO(getParamLong(request, "studentId")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	

	@RequestMapping(value="/lock-batch", method= RequestMethod.POST)
    @ResponseBody
    public String lockBatch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsStudentService.lockStudents(getParamStr(request, "ids"), getParamInt(request, "state"),
					getParamDate(request, "reviveTime"), getParamStr(request, "note")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value="/cer-batch", method= RequestMethod.GET)
    @ResponseBody
    public String cerBatch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			StudentBDTO student = (StudentBDTO) buildObject(request, StudentBDTO.class);
			return cmsStudentService.findCerBatch(student).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value="/lin-batch", method= RequestMethod.GET)
    @ResponseBody
    public String linBatch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			StudentBDTO student = (StudentBDTO) buildObject(request, StudentBDTO.class);
			return cmsStudentService.findLinBatch(student).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value="/lili-batch-theory", method= RequestMethod.GET)
    @ResponseBody
    public String liliBatchTheory(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			String theoryId=request.getParameter("theoryId");
			
			StudentBDTO student = (StudentBDTO) buildObject(request, StudentBDTO.class);
			return cmsStudentService.findLiliBatchTheory(student,Integer.parseInt(theoryId)).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	@RequestMapping(value="/lili-batch", method= RequestMethod.GET)
    @ResponseBody
    public String liliBatch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			String theoryId=request.getParameter("theoryId");
			
			StudentBDTO student = (StudentBDTO) buildObject(request, StudentBDTO.class);
			return cmsStudentService.findLiliBatch(student).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/*
	 * 关联了班别的学员
	 */
	@RequestMapping(value="/havePackageStudent", method= RequestMethod.GET)
    @ResponseBody
    public String havePackageStudent(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			StudentBDTO student = (StudentBDTO) buildObject(request, StudentBDTO.class);
			student.setSearchType(2);//已关联班别
			return cmsStudentService.havePackageStudent(student).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	/*
	 * 没有关联班别的学员
	 */
	@RequestMapping(value="/noRelevancePackageStudent", method= RequestMethod.GET)
    @ResponseBody
    public String noRelevancePackageStudent(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			StudentBDTO student = (StudentBDTO) buildObject(request, StudentBDTO.class);
			student.setSearchType(1);//没有关联班别
			return cmsStudentService.havePackageStudent(student).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/*
	 * 通过用户类型utype、电话号码查询用户信息
	 */
	@RequestMapping(value="/findStudentOrCoach", method= RequestMethod.GET)
    @ResponseBody
    public String findStudentOrCoach(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			String utype=request.getParameter("utype");
			if(utype.equals("1")){
				StudentBDTO student=new StudentBDTO();
				student.setPhoneNum(request.getParameter("phoneNum"));
			    String rsp=cmsStudentService.findLiliBatch(student).build();
			    return rsp;
			}else if(utype.equals("2")){
				CoachBDTO coachBDTO =new CoachBDTO();
				coachBDTO.setPhoneNum(request.getParameter("phoneNum"));
				String rsp = cmsCoachService.findBatch(coachBDTO).build();
				return rsp;
			}
			return "";
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value="/jx-batch-theory", method= RequestMethod.GET)
    @ResponseBody
    public String jxBatchTheory(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			String theoryId=request.getParameter("theoryId");
			StudentBDTO student = (StudentBDTO) buildObject(request, StudentBDTO.class);
			return cmsStudentService.findJxBatchTheory(student,Integer.parseInt(theoryId)).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	@RequestMapping(value="/jx-batch", method= RequestMethod.GET)
    @ResponseBody
    public String jxBatch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			
			StudentBDTO student = (StudentBDTO) buildObject(request, StudentBDTO.class);
			return cmsStudentService.findJxBatch(student).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value="/school-batch", method= RequestMethod.GET)
    @ResponseBody
    public String schoolBatch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			StudentBDTO student = (StudentBDTO) buildObject(request, StudentBDTO.class);
			return cmsStudentService.findUnSchoolBatch(student).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 未绑定教练的学员数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/no-coach", method= RequestMethod.GET)
    @ResponseBody
    public String noCoach(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			StudentBDTO student = (StudentBDTO) buildObject(request, StudentBDTO.class);
			return cmsStudentService.findUnboundBatch(student).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}


	@RequestMapping(value="/update", method= RequestMethod.POST)
    @ResponseBody
    public String update(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			Student student = (Student) buildObject(request, Student.class);
			LogCommon log = initLogParams(request, LogConstant.MENU_ID_STUDENT, LogConstant.ACTION_UPDATE);
			return cmsStudentService.updateOne(log,student).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	

	@RequestMapping(value="/lock", method= RequestMethod.POST)
    @ResponseBody
    public String lock(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsStudentService.lockStudent(getParamLong(request, "studentId"), getParamInt(request, "state"),
					getParamDate(request, "reviveTime"), getParamStr(request, "note")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	
	@RequestMapping(value="/account", method= RequestMethod.GET)
	@ResponseBody
	public String account(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			StudentBDTO student = (StudentBDTO) buildObject(request, StudentBDTO.class);
			String isEarning = request.getParameter("isEarning");
			String isBalance = request.getParameter("isBalance"); 
			String operateType = request.getParameter("operateType");
			String channel = request.getParameter("channel");
			return cmsStudentService.findStudentInfo(student, isEarning, isBalance, operateType, channel); 
		} 
		catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}


	@RequestMapping(value="/del-rel", method= RequestMethod.POST)
    @ResponseBody
    public String delRelation(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			LogCommon log = initLogParams(request, LogConstant.MENU_ID_STUDENT, LogConstant.ACTION_UPDATE);
			return cmsStudentService.deleteSCRelation(log,getParamLong(request, "coachId")
					,getParamLong(request, "id")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	

	@RequestMapping(value="/new-coach", method= RequestMethod.POST)
    @ResponseBody
    public String newCoach(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			LogCommon log = initLogParams(request, LogConstant.MENU_ID_STUDENT, LogConstant.ACTION_ADD);
			return cmsStudentService.updateSCRelation(log,getParamLong(request, "o_coachId")
					,getParamLong(request, "c_coachId"),getParamLong(request, "id")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 导出EXCEL
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			StudentBDTO student = (StudentBDTO) buildObject(request, StudentBDTO.class);
			Workbook wb = getWorkbook(cmsStudentService.findExportSource(student));
			sendExcel(response, wb, Constant.SHEET_STUDENT_FILE_NAME);
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	

	public Workbook getWorkbook(List<StudentVo> list){

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
				StudentVo studentVo = null;
				for (int i = 0,length = list.size(); i < length; i++) {    
				    try {
						row = sheet.createRow(i + 1);  
						row.setRowStyle(style);
						studentVo = list.get(i);  
						row.createCell(0).setCellValue(studentVo.getName());  
						row.createCell(1).setCellValue(studentVo.getPhoneNum());  
						row.createCell(2).setCellValue((studentVo.getSex() == (byte)0)?"女":"男");  
						row.createCell(3).setCellValue(studentVo.getSchoolName());  
						row.createCell(4).setCellValue(studentVo.getCoachName());  
						row.createCell(5).setCellValue(("2".equals(studentVo.getApplyCarType()))?"C2":"C1");   
						row.createCell(6).setCellValue(studentVo.getIdNumber());   
						row.createCell(7).setCellValue("正常");  
						row.createCell(8).setCellValue(studentVo.getFlowNo());
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
	
	public Workbook getProgressWorkbook(List<StudentVo> list){

		 Workbook wb = null; 
			try {
				wb = new SXSSFWorkbook(100);  //内存里一次只留多少行,几十万行无压力，不怕OOM
				Sheet sheet = wb.createSheet(Constant.SHEET_STUDENT_FILE_NAME);  //设置工作表标题
				Row row = sheet.createRow((int) 0);  
				CellStyle style = wb.createCellStyle();  
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);   // 水平居中

				// 产生表格标题行  
				for (int i = 0; i < excelHeader2.length; i++) {  
				    Cell cell = row.createCell(i);  
				    cell.setCellValue(excelHeader2[i]);  
				    cell.setCellStyle(style);  
				}  
				//第一个参数代表列id(从0开始),第2个参数代表宽度值
				sheet.setColumnWidth(0, 6000);
				sheet.setColumnWidth(2, 6000);
				sheet.setColumnWidth(7, 8000);
				
				// 遍历集合数据，产生数据行 
				StudentVo studentVo = null;
				for (int i = 0,length = list.size(); i < length; i++) {    
				    try {
						row = sheet.createRow(i + 1);  
						row.setRowStyle(style);
						studentVo = list.get(i);  
						row.createCell(0).setCellValue(studentVo.getName());  
						row.createCell(1).setCellValue(studentVo.getPhoneNum());  
						if(studentVo.getSex()==null)studentVo.setSex((byte)0);
						row.createCell(2).setCellValue((studentVo.getSex() == (byte)0)?"女":"男");  
						row.createCell(3).setCellValue(studentVo.getSchoolName());  
						row.createCell(4).setCellValue(studentVo.getCoachName());  
						row.createCell(5).setCellValue(("2".equals(studentVo.getApplyCarType()))?"C2":"C1");   
						row.createCell(6).setCellValue(studentVo.getIdNumber());
						if(studentVo.getApplyexam()==null)studentVo.setApplyexam(0);
						if(studentVo.getApplystate()==null)studentVo.setApplystate(0);
						row.createCell(7).setCellValue(getApplyStateText(studentVo.getApplyexam()+","+studentVo.getApplystate()));
						row.createCell(8).setCellValue(studentVo.getFlowNo());
						row.createCell(9).setCellValue("正常");
					} catch (Exception e) {
						access.error("|||exception e :" + LogUtil.getStackMsg(e) + " when export student");
					}  
				}
			} 
			catch (Exception e) {
				access.error("|||exception e :" + e.getMessage() + " when export student");
			}  
		
		
		return wb;
	}
	
	private String getApplyStateText(String type){
		String typeTex="";
		System.out.println("****************** "+type);
		switch (type) {
        case "-1,0":
            typeTex = "暂不报名";
            break;
        case "1,0":
            typeTex = "尚未报名";
            break;
        case "0,0":
            typeTex = "尚未报名";
            break;
        case "1,100":
            typeTex = "已报名";
            break;
        case "2,0":
            typeTex = "尚未支付";
            break;
        case "2,100":
            typeTex = "已支付";
            break;
        case "2,101":
            typeTex = "支付失败";
            break;
        case "3,0":
            typeTex = "尚未填写个人信息";
            break;
        case "3,100":
            typeTex = "已填写个人信息";
            break;
        case "4,0":
            typeTex = "未邮寄资料";
            break;
        case "4,1":
            typeTex = "资料已邮寄";
            break;
        case "4,100":
            typeTex = "资料齐全";
            break;
        case "4,101":
            typeTex = "资料不全";
            break;
        case "5,0":
            typeTex = "未交表";
            break;
        case "5,1":
            typeTex = "表已寄出";
            break;
        case "5,100":
            typeTex = "收表成功";
            break;
        case "6,0":
            typeTex = "已收表";
            break;
        case "6,1":
            typeTex = "受理中";
            break;
        case "6,100":
            typeTex = "受理成功";
            break;
        case "6,101":
            typeTex = "受理失败";
            break;
        case "101,0":
            typeTex = "报名成功";
            break;
        case "101,1":
            typeTex = "已约理论课";
            break;
        case "101,100":
            typeTex = "已上理论课";
            break;
        case "101,101":
            typeTex = "缺理论课";
            break;
        case "201,0":
            typeTex = "未模拟考试";
            break;
        case "201,100":
            typeTex = "模拟考试达标";
            break;
        case "201,101":
            typeTex = "模拟考试未达标";
            break;
        case "301,0":
            typeTex = "未约考科一";
            break;
        case "301,1":
            typeTex = "科一排队中";
            break;
        case "301,100":
            typeTex = "科一排队成功";
            break;
        case "301,101":
            typeTex = "科一排队失败";
            break;
        case "302,0":
            typeTex = "已约考科一";
            break;
        case "302,100":
            typeTex = "科一合格";
            break;
        case "302,101":
            typeTex = "科一不合格";
            break;
        case "401,0":
            typeTex = "未约科考二";
            break;
        case "401,1":
            typeTex = "科二排队中";
            break;
        case "401,100":
            typeTex = "科二排队成功";
            break;
        case "401,101":
            typeTex = "科二排队失败";
            break;
        case "402,0":
            typeTex = "已约考科二";
            break;
        case "402,100":
            typeTex = "科二合格";
            break;
        case "402,101":
            typeTex = "科二不合格";
            break;
        case "501,0":
            typeTex = "未约长考";
            break;
        case "501,1":
            typeTex = "已约长考";
            break;
        case "501,100":
            typeTex = "长训合格";
            break;
        case "501,101":
            typeTex = "长训不合格";
            break;
        case "601,0":
            typeTex = "未约考科三";
            break;
        case "601,1":
            typeTex = "科三排队中";
            break;
        case "601,100":
            typeTex = "科三排队成功";
            break;
        case "601,101":
            typeTex = "科三排队失败";
            break;
        case "602,0":
            typeTex = "已约考科三";
            break;
        case "602,100":
            typeTex = "科三合格";
            break;
        case "602,101":
            typeTex = "科三不合格";
            break;
        case "701,0":
            typeTex = "未约考科四";
            break;
        case "701,1":
            typeTex = "科四排队中";
            break;
        case "701,100":
            typeTex = "科四排队成功";
            break;
        case "701,101":
            typeTex = "科四排队失败";
            break;
        case "702,0":
            typeTex = "已约考科四";
            break;
        case "702,100":
            typeTex = "科四合格";
            break;
        case "702,101":
            typeTex = "科四不合格";
            break;
        case "801,0":
            typeTex = "已拿证";
            break;
        default:
            typeTex = "未报名";
            break;
    }
		return typeTex;
	}
	
	/**
	 * 分配驾校
	 * @throws IOException 
	 */
	@RequestMapping(value="/allot", method= RequestMethod.POST)
    @ResponseBody
	public String allot(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String studentIdList = request.getParameter("studentIdList");
			String region = request.getParameter("region");//cityId
			String schoolId = request.getParameter("schoolId");
			String schoolName = request.getParameter("schoolName");
			access.info("******************************studentIdList : "+ studentIdList + ",region:" + region +",schoolId: " + schoolId);
			return cmsStudentService.allot(studentIdList, region, schoolId, schoolName).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@RequestMapping(value="/reset-state", method= RequestMethod.POST)
    @ResponseBody
    public String resetState(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			StudentStateVo student = (StudentStateVo) buildObject(request, StudentStateVo.class);
			LogCommon log = initLogParams(request, LogConstant.MENU_ID_STUDENT, LogConstant.ACTION_UPDATE);
			return cmsStudentService.resetState(log,getParamInt(request, "applyttid"),
					getParamStr(request, "studentIds"),student).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	
	/**
	 * 某进度的学员列表
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/progressStudent", method= RequestMethod.GET)
    @ResponseBody
    public String progressStudent(StatisticsStudentProgress vo,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			StudentBDTO student = (StudentBDTO) buildObject(request, StudentBDTO.class);
			return cmsStudentService.findProgressStudent(vo).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 数据导出
	 * @param vo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/progressStudent/export", method= RequestMethod.GET)
    public void progressStudentExport(StatisticsStudentProgress vo,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		List list= cmsStudentService.findProgressStudentData(vo);
		Workbook wb = getProgressWorkbook(list);
		sendExcel(response, wb, Constant.SHEET_PROGRESS_STUDENT_FILE_NAME);
	}
	
	/**
	 * 学员进度明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/progress")
    @ResponseBody
    public String progress(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			String userType=request.getParameter("userType");
			String studentId=request.getParameter("studentId");
			ReqResult r=schoolService.getUserProgress(studentId, null, null);
			
			return (new ResponseMessage().addResult("data", r.getResult().get("data"))).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
}
