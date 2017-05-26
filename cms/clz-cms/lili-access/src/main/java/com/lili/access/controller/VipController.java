package com.lili.access.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lili.cms.constant.Constant;
import com.lili.cms.entity.ResponseMessage;
import com.lili.cms.util.DateUtil;
import com.lili.cms.util.FileHandle;
import com.lili.common.vo.ResultCode;
import com.lili.student.dto.Student;
import com.lili.student.model.StudentVo;
import com.lili.student.model.StudentVo.StateEnum;
import com.lili.student.model.StudentVo.vStateEnum;
import com.lili.student.model.VipCustomExport;
import com.lili.student.model.VipCustomExportDetail;
import com.lili.student.service.CMSVipService;
import com.lili.student.vo.RechargeGearVo;
import com.lili.student.vo.RechargePlanVo;
import com.lili.student.vo.RechargeRecordVo;
import com.lili.student.vo.VipCompanyVo;
import com.lili.student.vo.VipCustomVo;
import com.lili.user.model.User;


@Controller
@Scope("prototype")
@RequestMapping("/vip")
public class VipController extends BaseController{
	
	Logger logger = Logger.getLogger(VipController.class);
	
	@Autowired
	CMSVipService cmsVipService;
	
	/**
	 * 导入学员做大客户学员数据<br>
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public String upload(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		InputStream is = null;
	      File file = null;

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		//获得文件
		MultipartFile multipartFile = multipartRequest.getFile("filename");
		//获得文件名
		String fileName = multipartFile.getOriginalFilename();
		access.info("***************************** FileName is : " + fileName );
		//文件存放地址
		String tagFileName = request.getSession().getServletContext().getRealPath("WEB-INF");
		if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
			access.info("***************************** FileName is Error!" );
			return 	null;
		}

		String time = DateUtil.currentDateTime();
		tagFileName = tagFileName + "/uploadExcel/"+Constant.VIP_STU_FILE_NAME+ time + (fileName.endsWith(".xls")?".xls":".xlsx");
		access.info("************************ tagFileName :"+ tagFileName);
		//输入流
		try {
			is = multipartFile.getInputStream();
			file =  FileHandle.upLoadFile( is, tagFileName);

			
		      if(file != null){
		    	  try {
		    		  access.info("reday to upload files");
					return cmsVipService.upload(file,tagFileName, getCurrentUser(request)).build();
				} catch (Exception e) {
				      return new ResponseMessage("错误:" + e.getMessage()).build();
				}
		      }
		} catch (IOException e) {
			access.error(" file import error ");
		}     finally {
			try {
				if(is != null){
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

	      return new ResponseMessage().build();
	      
	}
	
	/**
	 * 确认提交
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	@ResponseBody
	public Object submit(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			VipCustomExport vipCustomExport = new VipCustomExport();
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			String verifier = user.getAccount(); 
			Integer id = Integer.valueOf(request.getParameter("id"));
			vipCustomExport.setVerifier(verifier);
			vipCustomExport.setId(id);
			resp =  cmsVipService.submit(vipCustomExport, user.getId()).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 放弃提交
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/giveUp", method = RequestMethod.POST)
	@ResponseBody
	public Object giveUp(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			VipCustomExport vipCustomExport = new VipCustomExport();
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			String verifier = user.getAccount(); 
			Integer id = Integer.valueOf(request.getParameter("id"));
			vipCustomExport.setVerifier(verifier);
			vipCustomExport.setId(id);
			resp =  cmsVipService.giveUp(vipCustomExport).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 下载EXCEL模板
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	@ResponseBody
	public Object download(HttpServletRequest request, HttpServletResponse response) {
		String String = null;
		OutputStream os = null;
		InputStream is = null;
		try {
			String fileName = new String(Constant.VIP_STU_FILE_NAME.getBytes("UTF-8"),"ISO8859-1") +  ".xlsx";
			access.info("******************* fileName = " +fileName);
	        //设置文件类型  
	        response.setContentType("application/vnd.ms-excel");  
	        //设置Content-Disposition  
	        response.setHeader("Content-Disposition", "attachment;filename="+fileName);  
	        //读取目标文件，通过response将目标文件写到客户端  
	        //获取目标文件的绝对路径  
	        String fullFileName = request.getSession().getServletContext().getRealPath("WEB-INF") + "/download/" + fileName;  
	        //读取文件  
	        is = new FileInputStream(fullFileName);  
	        os = response.getOutputStream();  
	        //写文件  
	        byte buffer[] = new byte[4*1024];
		    int len = 0;
		    while ((len = is.read(buffer))!=-1) {
			   os.write(buffer,0,len);
		    }
		    response.setHeader("Content-disposition", "attachment;filename=" + new String(Constant.VIP_STU_FILE_OUT_NAME.getBytes("UTF-8"),"ISO8859-1") + ".xlsx");  
			response.setContentType("application/vnd.ms-excel");  
	        os.flush();  
	        is.close();  
	        os.close();  
		}
		catch (Exception e) {
			access.error("************************************ error: " + e.getMessage());
            return String;
		}
		finally {
			try {
				if (os != null) {
					os.close();
				}
				if (is != null) {
					is.close();
				}
			} 
			catch (IOException e) {
				access.error("************************************ error: " + e.getMessage());
	            return String;
			}
		}
		return String;
	}
	

	/**
	 * 微信添加大客户学员
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/add-wcustom", method=RequestMethod.POST)
    @ResponseBody
    public String addWCustomer(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			VipCustomVo vipCustomVo = (VipCustomVo) buildObject(request, VipCustomVo.class);
			return cmsVipService.addVipCustom(vipCustomVo, false,getCurrentUser(request)).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 添加大客户学员
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/add-custom", method=RequestMethod.POST)
    @ResponseBody
    public String addCustomer(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			VipCustomVo vipCustomVo = (VipCustomVo) buildObject(request, VipCustomVo.class);
			return cmsVipService.addVipCustom(vipCustomVo, true,getCurrentUser(request)).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	

	/**
	 * 添加大客户学员,新增学员
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/new-student", method=RequestMethod.POST)
    @ResponseBody
    public String addNewStudent(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			Student student = (Student) buildObject(request, Student.class);
			return cmsVipService.addVipCustom(student, getCurrentUser(request),getCurrentUser(request).getId()).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 添加大客户学员,新增学员
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/new-studentBatch", method=RequestMethod.POST)
	@ResponseBody
	public String addBatchNewStudent(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsVipService.addBatchVipCustom(getParamStrOrNull(request, "studentIds"),getParamStrOrNull(request, "mobiles"), getCurrentUser(request)).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 添加大客户(公司)
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/add-company", method=RequestMethod.POST)
    @ResponseBody
    public String addCompany(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			VipCompanyVo vo = (VipCompanyVo) buildObject(request, VipCompanyVo.class);
			return cmsVipService.createVipCompany(vo,getCurrentUser(request)).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 审核大客户员工通过
	 */
	@RequestMapping(value="/pass-custom", method=RequestMethod.POST)
    @ResponseBody
    public String passCustom(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsVipService.passCustom(getParamStrOrNull(request, "studentIds"), getCurrentUser(request)).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 审核大客户员工不通过
	 */
	@RequestMapping(value="/refuse-custom", method= RequestMethod.POST)
    @ResponseBody
    public String refuseCustom(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsVipService.refuseCustom(getParamStrOrNull(request, "studentIds"),getParamStrOrNull(request, "reason"), getCurrentUser(request)).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	
	

	/**
	 * 审核余额管理通过
	 */
	@RequestMapping(value="/pass-balance", method=RequestMethod.POST)
    @ResponseBody
    public String passBalance(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsVipService.passManualRechargeRecord(getParamStrOrNull(request, "rrids"), getCurrentUser(request).getId()).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 审核余额管理不通过
	 */
	@RequestMapping(value="/refuse-balance", method= RequestMethod.POST)
    @ResponseBody
    public String refuseBalance(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsVipService.refuseManualRechargeRecord(getParamStrOrNull(request, "rrids"),
					getParamStrOrNull(request, "reason"),getCurrentUser(request).getId()).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	

	/**
	 * 审核充值记录通过
	 */
	@RequestMapping(value="/pass-record", method=RequestMethod.POST)
    @ResponseBody
    public String passRecord(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsVipService.passRechargeRecord(getParamStrOrNull(request, "rcids"),getParamLong(request, "muid")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 审核充值记录不通过
	 */
	@RequestMapping(value="/refuse-record", method= RequestMethod.POST)
    @ResponseBody
    public String refuseRecord(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsVipService.refuseRechargeRecord(getParamStrOrNull(request, "rcids"),getParamStrOrNull(request, "reason"), getCurrentUser(request)).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	

	/**
	 * 激活充送方案
	 */
	@RequestMapping(value="/active-plan", method=RequestMethod.POST)
    @ResponseBody
    public String activeRechargePlan(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsVipService.activeRechargePlan(getParamStrOrNull(request, "ids"), getCurrentUser(request)).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 停用充送方案
	 */
	@RequestMapping(value="/inactive-plan", method= RequestMethod.POST)
    @ResponseBody
    public String inactiveRechargePlan(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsVipService.inactiveRechargePlan(getParamStrOrNull(request, "ids"), getCurrentUser(request)).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	
	/**
	 * 查看充值送套餐详情
	 */
	@RequestMapping(value="/recharge-plan", method= RequestMethod.GET)
    @ResponseBody
    public String getRechargePlan(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsVipService.getRechargePlan(getParamInt(request, "rcid"),getCurrentUser(request)).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	
	/**
	 * 添加套餐
	 * @param plan
	 * @return
	 */
	@RequestMapping(value="/add-plan", method= RequestMethod.POST)
    @ResponseBody
    public String addRechargePlan(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			RechargePlanVo vo = (RechargePlanVo) buildObject(request, RechargePlanVo.class);
			vo.setCuid(getCurrentUser(request).getId());
			vo.setMuid(getCurrentUser(request).getId());
			vo.setTstart(getParamDateOrNull(request, "tstart"));
			vo.setTend(getParamDateOrNull(request, "tend"));
			vo.setCityId(getParamStrOrNull(request, "cityId"));
			vo.setCityName(getParamStrOrNull(request, "cityName"));
			if(" ".equals(request.getParameter("cityId"))) {
				vo.setCityId(request.getParameter("cityId"));
			}
			if(" ".equals(request.getParameter("cityName"))) {
				vo.setCityName(request.getParameter("cityName"));
			}
			vo.setVtype(getParamIntOrNull(request, "vtype"));
			vo.setIsExitRercid(getParamIntOrNull(request, "isExitRercid"));
			vo.setRercid(getParamIntOrNull(request, "rercid"));
			vo.setRegNum(getParamIntOrNull(request, "regNum"));
			
			String rechargeGearInfo = request.getParameter("rechargeGearList");
			Gson gson = new Gson();
			String jsonStr = gson.toJson(rechargeGearInfo);  //javabean转换成json字符串 
			logger.info("********************************* jsonStr :" + jsonStr);
			jsonStr = jsonStr.replaceAll("\\\\","");//转义
			jsonStr = jsonStr.substring(1, jsonStr.length()-1);//去首位引号
			logger.info("********************************* jsonStr change :" + jsonStr);
			//json字符串转换成javabean对象  
			List<RechargeGearVo> rechargeGearList = gson.fromJson(jsonStr, new TypeToken<List<RechargeGearVo>>(){}.getType());  
			logger.info("********************** rechargeGearList : " + rechargeGearList);
			
			return cmsVipService.addRechargePlan(vo, rechargeGearList, getCurrentUser(request)).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	

	/**
	 * 修改套餐
	 * @param plan
	 * @return
	 */
	@RequestMapping(value="/edit-plan", method= RequestMethod.POST)
    @ResponseBody
    public String editRechargePlan(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			RechargePlanVo vo = (RechargePlanVo) buildObject(request, RechargePlanVo.class);
			vo.setCityId(getParamStrOrNull(request, "cityId"));
			vo.setCityName(getParamStrOrNull(request, "cityName"));
			if(" ".equals(request.getParameter("cityId"))){
				vo.setCityId(request.getParameter("cityId"));
			}
			if(" ".equals(request.getParameter("cityName"))){
				vo.setCityName(request.getParameter("cityName"));
			}
			vo.setMuid(getCurrentUser(request).getId());
			vo.setTstart(getParamDateOrNull(request, "tstart"));
			vo.setTend(getParamDateOrNull(request, "tend"));
			vo.setVtype(getParamIntOrNull(request, "vtype"));
			vo.setIsExitRercid(getParamIntOrNull(request, "isExitRercid"));
			vo.setRercid(getParamIntOrNull(request, "rercid"));
			vo.setRegNum(getParamIntOrNull(request, "regNum"));
			
			String rechargeGearInfo = request.getParameter("rechargeGearList");
			Gson gson = new Gson();
			String jsonStr = gson.toJson(rechargeGearInfo);  //javabean转换成json字符串 
			logger.info("********************************* jsonStr :" + jsonStr);
			jsonStr = jsonStr.replaceAll("\\\\","");//转义
			jsonStr = jsonStr.substring(1, jsonStr.length()-1);//去首位引号
			logger.info("********************************* jsonStr change :" + jsonStr);
			//json字符串转换成javabean对象  
			List<RechargeGearVo> rechargeGearList = gson.fromJson(jsonStr, new TypeToken<List<RechargeGearVo>>(){}.getType());  
			logger.info("********************** rechargeGearList : " + rechargeGearList);
			
			return cmsVipService.modifyRechargePlan(vo, rechargeGearList, getCurrentUser(request)).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	

	/**
	 * 增加手工调整学员余额的接口(余额管理)
	 * @param plan
	 * @return
	 */
	@RequestMapping(value="/add-record", method= RequestMethod.POST)
    @ResponseBody
    public String addRecord(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			
			logger.info("*************************** recharge : " +  request.getParameter("recharge"));
			RechargeRecordVo vo = (RechargeRecordVo) buildObject(request, RechargeRecordVo.class);
			logger.info("*************************** vo : " +  vo.getRecharge());
			if (vo.getRecharge() == null) {
				return new ResponseMessage("金额超限").build();
			}
			return cmsVipService.addRechargeRecord(vo,getCurrentUser(request)).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}

	/**
	 * 激活大客户公司
	 * @param coid
	 * @return
	 */
	@RequestMapping(value="/active-company", method= RequestMethod.POST)
    @ResponseBody
    public String activeVipCompany(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsVipService.activeVipCompany(getParamStrOrNull(request, "coids"), getCurrentUser(request)).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
/**
	 * 停用大客户公司
	 * @param coid
	 * @return
	 */
	@RequestMapping(value="/inactive-company", method= RequestMethod.POST)
    @ResponseBody
    public String inactiveVipCompany(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsVipService.inactiveVipCompany(getParamStrOrNull(request, "coids"), getCurrentUser(request)).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}

/**
	 * 修改大客户信息，其中状态不能修改。
	 * @param vipCompany
	 * @return
	 */
	@RequestMapping(value="/modify-company", method= RequestMethod.POST)
    @ResponseBody
    public String modifyVipCompany(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			VipCompanyVo vo = (VipCompanyVo) buildObject(request, VipCompanyVo.class);
			return cmsVipService.modifyVipCompany(vo, getCurrentUser(request)).build();
		} catch (UnsupportedEncodingException e) {
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
			VipCustomVo vo = (VipCustomVo) buildObject(request, VipCustomVo.class);
			Workbook wb = getWorkbook(cmsVipService.getExportCustomList(vo));
			sendExcel(response, wb, Constant.SHEET_VIP_STUDENT_FILE_NAME);
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	

	public Workbook getWorkbook(List<StudentVo> list){
		final String[] excelHeader = { "姓名", "电话", "性别","所属城市", "所属驾校","所学车型","充值送方案","身份证号","流水号","审核状态","账号状态"};  

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
						String sexStr = null;
						if(studentVo.getSex() != null){
							sexStr = (studentVo.getSex() == (byte)0)?"女":"男";
						}else {
							sexStr = "";
						}
						row.createCell(0).setCellValue(studentVo.getName());  
						row.createCell(1).setCellValue(studentVo.getPhoneNum());  
						row.createCell(2).setCellValue(sexStr);  
						row.createCell(3).setCellValue(studentVo.getCityName());  
						row.createCell(4).setCellValue(studentVo.getSchoolName());  
						row.createCell(4).setCellValue(studentVo.getCoachName());  
						row.createCell(5).setCellValue(("2".equals(studentVo.getApplyCarType()))?"C2":"C1");   
						row.createCell(6).setCellValue(studentVo.getRcname());   
						row.createCell(7).setCellValue(studentVo.getIdNumber());   
						row.createCell(8).setCellValue(studentVo.getFlowNo());  
						row.createCell(9).setCellValue(vStateEnum.getName(studentVo.getVstate()));  
						row.createCell(10).setCellValue(StateEnum.getName(studentVo.getState()));
					} catch (Exception e) {
						access.error("||| exception happened when getBalanceWorkbook :" + e.getMessage());
					}  
				}
			} 
			catch (Exception e) {
				access.error("|||exception e :" + e.getMessage() + " when export car");
			}  
		
		
		return wb;
	}
	
	
	/**
	 * 获取大客户学员列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/custom-batch", method= RequestMethod.GET)
    @ResponseBody
    public String customBatch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			VipCustomVo vo = (VipCustomVo) buildObject(request, VipCustomVo.class);
			String vipBigStr = getParamStrOrNull(request, "vipBig");
			Boolean vipBig;
			if("true".equals(vipBigStr)) {
				vipBig = true;
			}
			else if("false".equals(vipBigStr)){
				vipBig = false;
			}
			else {
				vipBig = null;
			}
			return cmsVipService.getCustomList(vo, getParamInt(request, "pageSize"), getParamInt(request, "pageNo"), vipBig).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	
	/**
	 * 获取大客户
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/company-view", method= RequestMethod.GET)
    @ResponseBody
    public String companyView(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			VipCompanyVo vo = (VipCompanyVo) buildObject(request, VipCompanyVo.class);
			return cmsVipService.getVipCompany(vo).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	
	/**
	 * 获取大客户列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/company-batch", method= RequestMethod.GET)
    @ResponseBody
    public String companyBatch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			VipCompanyVo vo = (VipCompanyVo) buildObject(request, VipCompanyVo.class);
			return cmsVipService.getVipCompanyList(vo, getParamInt(request, "pageSize"), getParamInt(request, "pageNo")).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	
	
	/**
	 * 获取充值送方案列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/plan-batch", method= RequestMethod.GET)
    @ResponseBody
    public String planBatch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			RechargePlanVo vo = (RechargePlanVo) buildObject(request, RechargePlanVo.class);
			return cmsVipService.getRechargePlanList(vo, getParamInt(request, "pageSize"), getParamInt(request, "pageNo")).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	/**
	 * 获取充值送记录列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/record-batch", method= RequestMethod.GET)
    @ResponseBody
    public String recordBatch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
				return cmsVipService.getRechargeRecord(getParamInt(request, "days"), getParamDateOrNull(request, "startTime"),getParamDateOrNull(request, "endTime"),
						getParamInt(request, "rcid")==0?null:getParamInt(request, "rcid"), getParamStrOrNull(request, "mobile")==""?null:getParamStrOrNull(request, "mobile")
								, getParamStrOrNull(request, "rcname")==""?null:getParamStrOrNull(request, "rcname")
								, getParamStrOrNull(request, "company"),  getParamIntOrNull(request, "vtype"), getParamIntOrNull(request, "vstate"), getParamInt(request, "pageNo"),  getParamInt(request, "pageSize"),false,  getParamInt(request, "utype")).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	
	/**
	 * 导出EXCEL
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/export-balance", method = RequestMethod.GET)
	public void exportBalanceExcel(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			Workbook wb = getBalanceWorkbook(cmsVipService.getExportRechargeRecord(getParamIntOrNull(request, "days"), getParamDateOrNull(request, "startTime"),getParamDateOrNull(request, "endTime"), getParamIntOrNull(request, "rcid"), getParamStrOrNull(request, "mobile")
					, getParamStrOrNull(request, "rcname")
					, getParamStrOrNull(request, "company"),  getParamIntOrNull(request, "vtype"), getParamIntOrNull(request, "vstate"),getParamIntOrNull(request, "pageNo"), getParamIntOrNull(request, "pageSize"), true));
			sendExcel(response, wb, Constant.SHEET_BALANCE_CHANGE);
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	

	public Workbook getBalanceWorkbook(List<com.lili.student.model.RechargeRecordVo> list){
		final String[] excelHeader = { "变更账户", "变更学员", "变更金额","变更理由", "审核状态","创建人","创建时间","审核通过时间","备注"};  
										
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
				com.lili.student.model.RechargeRecordVo vo = null;
				for (int i = 0,length = list.size(); i < length; i++) {    
				    try {
						row = sheet.createRow(i + 1);  
						row.setRowStyle(style);
						vo = list.get(i);  
						row.createCell(0).setCellValue(vo.getStudentId());  
						row.createCell(1).setCellValue(vo.getName());  
						row.createCell(2).setCellValue(vo.getRecharge());  
						row.createCell(3).setCellValue(vo.getRcname());  
						row.createCell(4).setCellValue(vStateEnum.getName(vo.getVstate()));  
						row.createCell(5).setCellValue(vo.getCreateUser());  
						row.createCell(6).setCellValue(vo.getCtime()==null?"":DateUtil.formatDate(vo.getCtime()));   
						row.createCell(7).setCellValue(vo.getGetTime()==null?"":DateUtil.formatDate(vo.getGetTime()));   
						row.createCell(8).setCellValue(vo.getReason());
					} catch (Exception e) {
						access.error("||| exception happened when getBalanceWorkbook :" + e.getMessage());
					}   
				}
			} 
			catch (Exception e) {
				access.error("|||exception e :" + e.getMessage() + " when export car");
			}  
		
		
		return wb;
	}
	

	/**
	 * 导出EXCEL
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/export-company", method = RequestMethod.GET)
	public void exportCompanyExcel(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			VipCompanyVo vo = (VipCompanyVo) buildObject(request, VipCompanyVo.class);
			Workbook wb = getCompanyWorkbook(cmsVipService.getExportVipCompanyList(vo, getParamInt(request, "pageSize"), getParamInt(request, "pageNo")));
			sendExcel(response, wb, Constant.SHEET_VIP_STUDENT_FILE_NAME);
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	

	public Workbook getCompanyWorkbook(List<com.lili.student.model.VipCompanyVo> list){
		final String[] excelHeader = { "大客户全称", "表示简称", "地区","客户经理", "联系电话","操作人","录入时间","是否可用"};  
									
										
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
				com.lili.student.model.VipCompanyVo vo = null;
				for (int i = 0,length = list.size(); i < length; i++) {    
				    try {
						row = sheet.createRow(i + 1);  
						row.setRowStyle(style);
						vo = list.get(i);  
						row.createCell(0).setCellValue(vo.getCompany());  
						row.createCell(1).setCellValue(vo.getShorter());  
						row.createCell(2).setCellValue(vo.getCity());  
						row.createCell(3).setCellValue(vo.getManger());  
						row.createCell(4).setCellValue(vo.getMobile());  
						row.createCell(5).setCellValue(vo.getCreateUser());  
						row.createCell(6).setCellValue(vo.getCtime()==null?"":DateUtil.formatDate(vo.getCtime()));   
						row.createCell(7).setCellValue(vo.getActive()==0?"未激活":"已激活");   
					} catch (Exception e) {
						access.error("||| exception happened when getBalanceWorkbook :" + e.getMessage());
					}   
				}
			} 
			catch (Exception e) {
				access.error("|||exception e :" + e.getMessage() + " when export car");
			}  
		
		
		return wb;
	}
	

	/**
	 * 获取余额变更记录列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/balance-batch", method= RequestMethod.GET)
    @ResponseBody
    public String balanceBatch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
				return cmsVipService.getRechargeRecord(getParamIntOrNull(request, "days"), getParamDateOrNull(request, "startTime"),getParamDateOrNull(request, "endTime"), getParamIntOrNull(request, "rcid"), getParamStrOrNull(request, "mobile")
						, getParamStrOrNull(request, "rcname")
						, getParamStrOrNull(request, "company"),  getParamIntOrNull(request, "vtype"), getParamIntOrNull(request, "vstate"),getParamIntOrNull(request, "pageNo"),  getParamIntOrNull(request, "pageSize"),true,getParamIntOrNull(request, "utype")).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	
	/**
	 * 大客户学员删除
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/custom-delete", method= RequestMethod.POST)
	@ResponseBody
	public String customDelete(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			VipCustomVo vo = (VipCustomVo) buildObject(request, VipCustomVo.class);
			return cmsVipService.customDelete(vo, getCurrentUser(request)).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 导入主表查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/custom-export", method= RequestMethod.GET)
	@ResponseBody
	public String customExport(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			VipCustomExport vo = (VipCustomExport) buildObject(request, VipCustomExport.class);
			return cmsVipService.customExport(vo);
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 导入明细查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/custom-exportDetail", method= RequestMethod.GET)
	@ResponseBody
	public String customExportDetail(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			VipCustomExportDetail vo = (VipCustomExportDetail) buildObject(request, VipCustomExportDetail.class);
			return cmsVipService.customExportDetail(vo);
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
}
