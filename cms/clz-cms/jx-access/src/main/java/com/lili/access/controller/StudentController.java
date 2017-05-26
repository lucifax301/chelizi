package com.lili.access.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lili.access.util.AccessWebUtil;
import com.lili.cms.constant.Constant;
import com.lili.cms.constant.LogConstant;
import com.lili.cms.constant.MessageCode;
import com.lili.cms.entity.ResponseMessage;
import com.lili.cms.util.JsonUtil;
import com.lili.coach.service.CMSCoachService;
import com.lili.log.model.LogCommon;
import com.lili.student.model.Student;
import com.lili.student.model.StudentAuthVo;
import com.lili.student.model.StudentBDTO;
import com.lili.student.model.StudentNBDTO;
import com.lili.student.model.StudentStateVo;
import com.lili.student.model.StudentVo;
import com.lili.student.service.CMSStudentService;
import com.lili.user.model.User;

@Controller
@Scope("prototype")
@RequestMapping("/student")
public class StudentController extends BaseController{
	
	@Autowired
	CMSStudentService cmsStudentService;
	
	@Autowired
	CMSCoachService cmsCoachService;
	

	public static final String[] excelHeader = { "姓名", "电话", "性别", "所属驾校", "绑定教练","所学车型","身份证号","账号状态","流水号"};  
	
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
    @ResponseBody
    public String add(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			Student student = (Student) buildObject(request, Student.class);
			student.setSchoolId(AccessWebUtil.getSessionUser(request).getSchoolId());
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

	@RequestMapping(value="/lili-batch", method= RequestMethod.GET)
    @ResponseBody
    public String liliBatch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			StudentBDTO student = (StudentBDTO) buildObject(request, StudentBDTO.class);
			return cmsStudentService.findLiliBatch(student).build();
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
		 Sheet sheet = null;
		 Row row = null;
		 CellStyle style = null;
		 Cell cell = null;
			try {
				wb = new SXSSFWorkbook(100);  //内存里一次只留多少行,几十万行无压力，不怕OOM
				sheet = wb.createSheet(Constant.SHEET_STUDENT_FILE_NAME);  //设置工作表标题
				row = sheet.createRow((int) 0);  
				style = wb.createCellStyle();  
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);   // 水平居中

				// 产生表格标题行  
				for (int i = 0; i < excelHeader.length; i++) {  
				    cell = row.createCell(i);  
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
				    row = sheet.createRow(i + 1);  
				    row.setRowStyle(style);
				    studentVo = list.get(i);  
				    row.createCell(0).setCellValue(studentVo.getName());  
				    row.createCell(1).setCellValue(studentVo.getPhoneNum());  
				    row.createCell(2).setCellValue((studentVo.getSex() == 0)?"女":"男");  
				    row.createCell(3).setCellValue(studentVo.getSchoolName());  
				    row.createCell(4).setCellValue(studentVo.getCoachName());  
				    row.createCell(5).setCellValue(("2".equals(studentVo.getApplyCarType()))?"C2":"C1");   
				    row.createCell(6).setCellValue(studentVo.getIdNumber());   
				    row.createCell(7).setCellValue("正常");  
				    row.createCell(8).setCellValue(studentVo.getFlowNo());  
				}
			} 
			catch (Exception e) {
				access.error("|||exception e :" + e.getMessage() + " when export car");
			}  

		return wb;
	}
	
	/**
	 * 导入流水
	 * @throws IOException 
	 */
	@RequestMapping(value = "/uploadWater", method = RequestMethod.POST)
	public void uploadWater(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    Map<String, Object> map = new HashMap<String, Object>();
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/json"); 
	    FileInputStream fis = null;
	    try {
		    String type = request.getParameter("type");
		    access.info("******************************** type:"+type);
		    String fileType = null;
		    String fileName = null;
		    String tagFileName = null;
		    File outfile;
		    if ("1".equals(type)) { //上传文件
	    		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
	    		MultipartFile multipartFile = multipartRequest.getFile("filename");//获得文件
	    		fileName = multipartFile.getOriginalFilename();//获得文件名
	    		access.info("***************************** FileName is : " + fileName );
	    	
	    		tagFileName = request.getSession().getServletContext().getRealPath("WEB-INF");	//文件存放地址
	    		if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
	    			access.info("***************************** FileName is Error!" );
	    			map.put("code", 601);
	    			map.put("msg", MessageCode.MSG_FILE_ERR);
	    			access.info(JsonUtil.mapToJson(map));
	    			response.getWriter().write(JsonUtil.mapToJson(map));
	    			response.getWriter().flush();
	    			response.getWriter().close();
	    		}
	    		if (fileName.endsWith(".xls")){
	    			fileType = ".xls";
	    		}
	    		else {
	    			fileType = ".xlsx";
	    		}
	    		
	    		Date date = new Date(); 
	    		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS"); 
	    		String time = dateFormat.format(date) ;
	    		tagFileName = tagFileName + "/uploadExcel/"+Constant.SHEET_STUDENT_NAME+ time + fileType;
	    		access.info("************************ tagFileName :"+ tagFileName);
	    	
	    		InputStream is = multipartFile.getInputStream();   	//输入流
	    		outfile = new File(tagFileName);
	    		OutputStream os = null;
	    		try{
	    			os = new FileOutputStream(outfile);
	    			byte buffer[] = new byte[4*1024];
	    			int len = 0;
	    			while ((len = is.read(buffer))!=-1) {
	    				os.write(buffer,0,len);
	    			}
	    			os.flush();
	    			os.close();
	    			is.close();
	    		}
	    		catch (Exception e) {
	    			e.printStackTrace();
	    			access.error(e.getMessage());
	    			map.put("code", 601);
	    			map.put("msg", MessageCode.MSG_UP_FAIL);
	    			access.info(JsonUtil.mapToJson(map));
	    			response.getWriter().write(JsonUtil.mapToJson(map));
	    			response.getWriter().flush();
	    			response.getWriter().close();
	    		}
	    		finally {
	    			try {
	    				if(os != null){
	    					os.close();
	    				}
	    				if(is != null){
	    					is.close();
	    				}
	    			}
	    			catch(Exception e){
	    				e.printStackTrace();
	    				access.error(e.getMessage());
	    				map.put("code", 601);
		    			map.put("msg", MessageCode.MSG_UP_FAIL);
		    			access.info(JsonUtil.mapToJson(map));
		    			response.getWriter().write(JsonUtil.mapToJson(map));
		    			response.getWriter().flush();
		    			response.getWriter().close();
	    			}
	    		}		        
		    }
		    else {
		    	fileName = request.getParameter("tagFileName");
		    	access.info("***************************** fileName :" + fileName);
		    	outfile = new File(fileName);
		    }

		    // 解析文件
		    Workbook wb = null; //创建Excel工作簿对象	
			boolean isE2007 = false;
			if (fileName .endsWith("xlsx"))  {
				isE2007  = true;  
			}
            //根据文件格式(2003或者2007)来初始化  
			fis = new FileInputStream(outfile);
            if(isE2007)  {
                wb = new XSSFWorkbook(fis);  
            }
            else  {
            	wb = new HSSFWorkbook(fis);  
            }
            
            Student student = null;
            DecimalFormat df = new DecimalFormat("0");
            List<Student> studentList = new ArrayList<Student>();
            Set<String> set = new HashSet<String>();
            Sheet sheet = wb.getSheetAt(0);     //获得第一个表单  
            Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器  
            while (rows.hasNext()) {  
                Row row = rows.next();  //获得行数据  
                if (row.getRowNum() >= 2) {  //从第三行开始读取
                	student = new Student(); //防止覆盖
                	access.info("Row #" + row.getRowNum());  //获得行号从0开始  
                	Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器  
                	while (cells.hasNext()) {  
                		Cell cell = cells.next();  
                		access.info("Cell #" + cell.getColumnIndex());  
                		switch (cell.getCellType()) {   //根据cell中的类型来输出数据  
                		case HSSFCell.CELL_TYPE_NUMERIC:  
                			access.info(cell.getNumericCellValue());  
                			if ("1".equals(type)) {
	               				 if(cell.getColumnIndex() == 1) {	//身份证
	                					student.setIdNumber(String.valueOf(df.format(cell.getNumericCellValue())));
	                				}
	               			}
	               			else {
	               				if(cell.getColumnIndex() == 0) { //流水
	               					student.setFlowNo(String.valueOf(df.format(cell.getNumericCellValue())));
	               				}
	               				else if(cell.getColumnIndex() == 1) {	//身份证
	               					student.setIdNumber(String.valueOf(df.format(cell.getNumericCellValue())));
	               				}
	               			}
                			break;  
                		case HSSFCell.CELL_TYPE_STRING:  
                			access.info(cell.getStringCellValue());  
                			if ("1".equals(type)) {
	               				 if(cell.getColumnIndex() == 1) {	//身份证
	                					student.setIdNumber(cell.getStringCellValue().trim());
	                				}
	               			}
	               			else if ("2".equals(type)){
	               				if(cell.getColumnIndex() == 0) { //流水
	               					student.setFlowNo(cell.getStringCellValue().trim());
	               				}
	               				else if(cell.getColumnIndex() == 1) {	//身份证
	               					student.setIdNumber(cell.getStringCellValue().trim());
	               				}
	               			}
                			break;  
                		case HSSFCell.CELL_TYPE_BOOLEAN:  
                			access.info(cell.getBooleanCellValue());  
                			break;  
                		case HSSFCell.CELL_TYPE_FORMULA:  // 公式
                			access.info(cell.getCellFormula());  
                			break;  
                		default:  
                			access.info("unsuported sell type");  
                			break;  
                		}  
                	}
                	if (student.getFlowNo() != ""  && student.getIdNumber() != "") {
                		studentList.add(student); //读完一行加到数组里
                		set.add(student.getIdNumber());
                	}
                }
          }  
          
          //数据读取完毕，开始匹配
          int size = studentList.size();
          int setSize = set.size();//去重复总数
          access.info("****************************** Success Read Data! Size : " + size);
          if (size ==0) {
        	    map.put("code", 601);
			    map.put("success", "false");
			    map.put("msg", MessageCode.MSG_UN_READ);
			    access.info(JsonUtil.mapToJson(map));
	    	    response.getWriter().write(JsonUtil.mapToJson(map));
    			response.getWriter().flush();
    			response.getWriter().close();
          }
          List<Student> countStudent = cmsStudentService.queryIsExistNum(studentList); //已有流水且存在的
          if ("1".equals(type) && size > 0) {
        	  List<Student> exitNoStudent = cmsStudentService.queryIsNotExist(studentList); //存在的
        	  Integer count = countStudent.size();//统计有多少条已有流水
        	  Integer exitNo = exitNoStudent.size();//存在记录
    	     int notExit = setSize - exitNo;
    	    	if(notExit > 0){
    	    		map.put("code", 0);
    	    		map.put("success", "true");
    	    		if (count > 0) {
     	    			map.put("msg", MessageCode.MSG_READ  + size + MessageCode.MSG_NUM_AND + count +MessageCode.MSG_HAS_FLOW_NO + notExit +  MessageCode.MSG_NO_MATCH_UP);
     	    		}
    	    		else{
    	    			map.put("msg", MessageCode.MSG_READ  + size + MessageCode.MSG_NUM_AND + notExit + MessageCode.MSG_NO_MATCH_UP);
    	    		}
    	    		map.put("tagFileName", tagFileName);
    	    		access.info(JsonUtil.mapToJson(map));
		    	    response.getWriter().write(JsonUtil.mapToJson(map));
	    			response.getWriter().flush();
	    			response.getWriter().close();
    	    	}
    	    	else {
    	    		Integer statusError = cmsStudentService.queryIsHandle(studentList); //判断无流水号的学员状态是否受理中、受理失败，如果不是return
    	    		if (statusError >0) { 
    	    			map.put("code", 601);
    	    			map.put("success", "false");
    	    			map.put("msg", statusError + MessageCode.MSG_STATUS_ERROR);
    	    			if (outfile.isFile()) {   // 判断是否为文件  
    	    				outfile.delete();
    	    			} 
    	    			access.info(JsonUtil.mapToJson(map));
    	    			response.getWriter().write(JsonUtil.mapToJson(map));
    	    			response.getWriter().flush();
    	    			response.getWriter().close();
    	    		}
    	    		
    	    		map.put("code", 0);
    	    		map.put("success", "true");
    	    		tagFileName =  tagFileName.replaceAll("\\\\", "/"); //目录转换
    	    		 if (count == 0 && notExit > 0) {//匹配不到学员，是否确认上传
     	    			map.put("msg", MessageCode.MSG_READ  + size + MessageCode.MSG_NUM_AND + notExit + MessageCode.MSG_NO_MATCH_UP);
     	    			map.put("tagFileName", tagFileName);
     	    		}
    	    		 else if (count > 0 && notExit == 0) {//已有流水号，是否覆盖上传
    	    			map.put("msg", MessageCode.MSG_READ  + size + MessageCode.MSG_NUM_AND + count +MessageCode.MSG_HAS_NO_OVER);
    	    			map.put("tagFileName", tagFileName);
    	    		}
    	    		else if (count > 0 && notExit > 0) {//已有流水号，是否覆盖上传,条匹配不到学员，是否确认上传
    	    			map.put("msg", MessageCode.MSG_READ  + size + MessageCode.MSG_NUM_AND + count +MessageCode.MSG_HAS_FLOW_NO + notExit +  MessageCode.MSG_NO_MATCH_UP);
    	    			map.put("tagFileName", tagFileName);
    	    		}
    	    		else {
    	    			map.put("msg", MessageCode.MSG_READ + size + MessageCode.MSG_SURE_UP);
    	    			map.put("tagFileName", tagFileName);
    	    		}
    	    		
    	    		access.info(JsonUtil.mapToJson(map));
    	    		response.getWriter().write(JsonUtil.mapToJson(map));
    	    		response.getWriter().flush();
    	    		response.getWriter().close();
    	    	}
          }
          else if("2".equals(type) && size > 0) {
        	  List<Student> notFlowStudent = cmsStudentService.queryIsNotExistNum(studentList); //没有流水且状态为：受理中、受理失败
        	  LogCommon logCommon = new LogCommon();
        	  HttpSession session = request.getSession();
  			  User user = (User)session.getAttribute(Constant.USER_SESSION);
  			  logCommon.setSchoolId(user.getSchoolId());
  			  logCommon.setUserId(String.valueOf(user.getId()));
  			  logCommon.setUserName(user.getUserName());
        	  cmsStudentService.updateBatchFlowNo(studentList , notFlowStudent, logCommon);//批量更新表
          }
          else {
        	  access.info("*************************** 取消");
          }
            
           if (("2".equals(type) || "3".equals(type)) && outfile.exists()) {  // 不存在返回 false  
               if (outfile.isFile()) {   // 判断是否为文件  
                   outfile.delete();
               } 
           }  
           fis.close();
	     } 
	  	catch (Exception e) {
	  		 e.printStackTrace();
	  		access.error(e.getMessage());
	  		map.put("code", 601);
  	    	map.put("msg", "未读取到相关数据，请重新上传");
  	    	access.info(JsonUtil.mapToJson(map));
  	    	response.getWriter().write(JsonUtil.mapToJson(map));
			response.getWriter().flush();
			response.getWriter().close();
	     }
	    finally {
	    	try {
				if(fis != null){
					fis.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	    
		map.put("code", 0);
	    map.put("msg", "提交成功");
	    access.info(JsonUtil.mapToJson(map));
	    response.getWriter().write(JsonUtil.mapToJson(map));
		response.getWriter().flush();
		response.getWriter().close();
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
}
