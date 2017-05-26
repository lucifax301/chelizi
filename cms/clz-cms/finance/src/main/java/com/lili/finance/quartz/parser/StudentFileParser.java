package com.lili.finance.quartz.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.coach.model.Coach;
import com.lili.coach.service.CMSCoachService;
import com.lili.finance.manager.cms.IFileTaskManager;
import com.lili.finance.model.cms.TaskFailDetail;
import com.lili.finance.model.cms.TaskFile;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;

public class StudentFileParser implements IFileParser {

	private static  final Logger logger = Logger.getLogger(StudentFileParser.class);
	
	@Autowired
	StudentManager studentManager;
	
	@Autowired
	IFileTaskManager fileTaskManager;
	
	@Autowired
	CMSCoachService cmsCoachService;

	@Override
	public void parserFile(TaskFile taskFile) {
		logger.info("******************************** Handle StudentFileParser Start ! ******************************** ");
		Long startTime = System.currentTimeMillis();
		
		//更新文件为处理中
		isHandlerFile(taskFile, 2, null);
		
		FileInputStream fis = null;
		Workbook wb = null; // 创建Excel工作簿对象
		boolean isE2007 = false;
		File outfile =  new File(taskFile.getFileName());
		
		try {
			if (taskFile.getFileName().endsWith("xlsx")) {
				isE2007 = true;
			}
			// 根据文件格式(2003或者2007)来初始化
			fis = new FileInputStream(outfile);
			if (isE2007) {
				wb = new XSSFWorkbook(fis);
			} else {
				wb = new HSSFWorkbook(fis);
			}

			// 解析excel文件
			TaskFailDetail taskFailDetail = null;
			Student student = null;
			DecimalFormat df = new DecimalFormat("0");
			//List<Student> studentList = new ArrayList<Student>();
			int sucSum = 0;
			int failSum = 0;
			
			Sheet sheet = wb.getSheetAt(0); // 获得第一个表单
			Iterator<Row> rows = sheet.rowIterator(); // 获得第一个表单的迭代器
			String coachPhone = null;
			while (rows.hasNext()) {  
				Row row = rows.next();  //获得行数据  
                if (row.getRowNum() >= 2) {  //从第三行开始读取
                	student = new Student(); //防止覆盖
                	taskFailDetail = new TaskFailDetail();
                	taskFailDetail.setTaskId(taskFile.getId());
                	
                	logger.info("Row #" + row.getRowNum());  //获得行号从0开始  
                	Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器  
                	while (cells.hasNext()) {  
                		Cell cell = cells.next();  
                		logger.info("Cell #" + cell.getColumnIndex());  
                		switch (cell.getCellType()) {   //根据cell中的类型来输出数据  
                		case HSSFCell.CELL_TYPE_NUMERIC:  
                			logger.info(cell.getNumericCellValue());  
                			//cell读取列，第A列是0
               				if(cell.getColumnIndex() == 0) { //学员ID
               					taskFailDetail.setRowA(String.valueOf(df.format(cell.getNumericCellValue())));
               				}
               				if(cell.getColumnIndex() == 1) { //B学员姓名
               					student.setName(String.valueOf(df.format(cell.getNumericCellValue())));
               					taskFailDetail.setRowB(String.valueOf(df.format(cell.getNumericCellValue())));
               				}
               				else if(cell.getColumnIndex() == 2) {	//C报考车型
               					student.setDrType(Byte.valueOf(df.format(cell.getNumericCellValue())));
               					taskFailDetail.setRowC(String.valueOf(df.format(cell.getNumericCellValue())));
               				}
               				else if(cell.getColumnIndex() == 3) {	//D身份证号
               					student.setIdNumber(String.valueOf(df.format(cell.getNumericCellValue())));
               					taskFailDetail.setRowD(String.valueOf(df.format(cell.getNumericCellValue())));
               				}
               				else if(cell.getColumnIndex() == 4) {	//E性别
               					student.setSex(Byte.valueOf(df.format(cell.getNumericCellValue())));
               					taskFailDetail.setRowE(String.valueOf(df.format(cell.getNumericCellValue())));
               				}
               				else if(cell.getColumnIndex() == 5) {	//F年龄
               					student.setAge(Short.valueOf(df.format(cell.getNumericCellValue())));
               					taskFailDetail.setRowF(String.valueOf(df.format(cell.getNumericCellValue())));
               				}
               				else if(cell.getColumnIndex() == 6) {	//G户籍地
               					student.setHometown(String.valueOf(df.format(cell.getNumericCellValue())));
               					taskFailDetail.setRowG(String.valueOf(df.format(cell.getNumericCellValue())));
               				}
               				else if(cell.getColumnIndex() == 7) {	//H手机
               					student.setPhoneNum(String.valueOf(df.format(cell.getNumericCellValue())));
               					taskFailDetail.setRowH(String.valueOf(df.format(cell.getNumericCellValue())));
               				}
               				else if(cell.getColumnIndex() == 8) {	//I录入时间
               					taskFailDetail.setRowI(String.valueOf(df.format(cell.getNumericCellValue())));
               				}
               				else if(cell.getColumnIndex() == 9) {	//J教练手机
               					coachPhone =  String.valueOf(df.format(cell.getNumericCellValue()));
               					taskFailDetail.setRowJ(String.valueOf(df.format(cell.getNumericCellValue())));
               				}
               				else if(cell.getColumnIndex() == 10) {	//K教练名
               					taskFailDetail.setRowK(String.valueOf(df.format(cell.getNumericCellValue())));
               				}
               				else if(cell.getColumnIndex() == 11) {	//L报名流水号
               					student.setFlowNo(String.valueOf(df.format(cell.getNumericCellValue())));
               					taskFailDetail.setRowL(String.valueOf(df.format(cell.getNumericCellValue())));
               				}
               				else if(cell.getColumnIndex() == 12) {	//M科目一
               					student.setCourse1(Short.valueOf(df.format(cell.getNumericCellValue())));
               					taskFailDetail.setRowM(String.valueOf(df.format(cell.getNumericCellValue())));
               				}
               				else if(cell.getColumnIndex() == 13) {	//N科目二
               					student.setCourse2(Short.valueOf(df.format(cell.getNumericCellValue())));
               					taskFailDetail.setRowN(String.valueOf(df.format(cell.getNumericCellValue())));
               				}
               				else if(cell.getColumnIndex() == 14) {	//O科目三
               					student.setCourse3(Short.valueOf(df.format(cell.getNumericCellValue())));
               					taskFailDetail.setRowO(String.valueOf(df.format(cell.getNumericCellValue())));
               				}
               				else if(cell.getColumnIndex() == 15) {	//P科目四
               					student.setCourse4(Short.valueOf(df.format(cell.getNumericCellValue())));
               					taskFailDetail.setRowP(String.valueOf(df.format(cell.getNumericCellValue())));
               				}
               				else if(cell.getColumnIndex() == 16) {	//Q所在城市
               					student.setCityId(Integer.valueOf(df.format(cell.getNumericCellValue())));
               					taskFailDetail.setRowQ(String.valueOf(df.format(cell.getNumericCellValue())));
               				}
               				else if(cell.getColumnIndex() == 17) {	//R所属驾校
               					student.setSchoolId(Byte.valueOf(df.format(cell.getNumericCellValue())));
               					taskFailDetail.setRowR(String.valueOf(df.format(cell.getNumericCellValue())));
               				}
                			break;  
                		case HSSFCell.CELL_TYPE_STRING:  
                			logger.info(cell.getStringCellValue().trim());  
                			if(cell.getColumnIndex() == 0) { //学员ID
                				taskFailDetail.setRowA(cell.getStringCellValue().trim());
               				}
               				if(cell.getColumnIndex() == 1) { //B学员姓名
               					student.setName(cell.getStringCellValue().trim());
               					taskFailDetail.setRowB(cell.getStringCellValue().trim());
               				}
               				else if(cell.getColumnIndex() == 2) {	//C报考车型
               					student.setDrType(Byte.valueOf(cell.getStringCellValue().trim()));
               					taskFailDetail.setRowC(cell.getStringCellValue().trim());
               				}
               				else if(cell.getColumnIndex() == 3) {	//D身份证号
               					student.setIdNumber(cell.getStringCellValue().trim());
               					taskFailDetail.setRowD(cell.getStringCellValue().trim());
               				}
               				else if(cell.getColumnIndex() == 4) {	//E性别
               					student.setSex(Byte.valueOf(cell.getStringCellValue().trim()));
               					taskFailDetail.setRowE(cell.getStringCellValue().trim());
               				}
               				else if(cell.getColumnIndex() == 5) {	//F年龄
               					student.setAge(Short.valueOf(cell.getStringCellValue().trim()));
               					taskFailDetail.setRowF(cell.getStringCellValue().trim());
               				}
               				else if(cell.getColumnIndex() == 6) {	//G户籍地
               					student.setHometown(cell.getStringCellValue().trim());
               					taskFailDetail.setRowG(cell.getStringCellValue().trim());
               				}
               				else if(cell.getColumnIndex() == 7) {	//H手机
               					student.setPhoneNum(cell.getStringCellValue().trim());
               					taskFailDetail.setRowH(cell.getStringCellValue().trim());
               				}
               				else if(cell.getColumnIndex() == 8) {	//I录入时间
               					taskFailDetail.setRowI(cell.getStringCellValue().trim());
               				}
               				else if(cell.getColumnIndex() == 9) {	//J教练手机
               					coachPhone =  cell.getStringCellValue().trim();
               					taskFailDetail.setRowJ(cell.getStringCellValue().trim());
               				}
               				else if(cell.getColumnIndex() == 10) {	//K教练名
               					taskFailDetail.setRowK(cell.getStringCellValue().trim());
               				}
               				else if(cell.getColumnIndex() == 11) {	//L报名流水号
               					student.setFlowNo(cell.getStringCellValue().trim());
               					taskFailDetail.setRowL(cell.getStringCellValue().trim());
               				}
               				else if(cell.getColumnIndex() == 12) {	//M科目一
               					student.setCourse1(Short.valueOf(cell.getStringCellValue().trim()));
               					taskFailDetail.setRowM(cell.getStringCellValue().trim());
               				}
               				else if(cell.getColumnIndex() == 13) {	//N科目二
               					student.setCourse2(Short.valueOf(cell.getStringCellValue().trim()));
               					taskFailDetail.setRowN(cell.getStringCellValue().trim());
               				}
               				else if(cell.getColumnIndex() == 14) {	//O科目三
               					student.setCourse3(Short.valueOf(cell.getStringCellValue().trim()));
               					taskFailDetail.setRowO(cell.getStringCellValue().trim());
               				}
               				else if(cell.getColumnIndex() == 15) {	//P科目四
               					student.setCourse4(Short.valueOf(cell.getStringCellValue().trim()));
               					taskFailDetail.setRowP(cell.getStringCellValue().trim());
               				}
               				else if(cell.getColumnIndex() == 16) {	//Q所在城市
               					student.setCityId(Integer.valueOf(cell.getStringCellValue().trim()));
               					taskFailDetail.setRowQ(cell.getStringCellValue().trim());
               				}
               				else if(cell.getColumnIndex() == 17) {	//R所属驾校
               					student.setSchoolId(Byte.valueOf(cell.getStringCellValue().trim()));
               					taskFailDetail.setRowR(cell.getStringCellValue().trim());
               				}
                			break;  
                		case HSSFCell.CELL_TYPE_BOOLEAN:  //boolean
                			logger.info("************************ Boolean Value, Not Set value : " + cell.getBooleanCellValue());  
                			break;  
                		case HSSFCell.CELL_TYPE_FORMULA:  // 公式
                			logger.info("************************ Formula Value, Not Set value : " +cell.getCellFormula());  
                			break;  
                		default:  
                			logger.info("************************ Default Value, Not Set value : " + "unsuported sell type");  
                			break;  
                		}  
                	}
                	
                	if (student.getPhoneNum() != "") { 
                		//studentList.add(student); 	//读完一行加到数组里,批量操作使用，暂不使用
                		//根据教练手机查询教练id
                		Coach coach = cmsCoachService.findByPhone(coachPhone);
                		if(coach != null){
                			student.setStuCoachEmpID((int)coach.getCoachId());
                		}
                		student.setIsImport((byte)1);
						student.setImportSrc(taskFile.getSchoolName());
                		Long count = studentManager.addStudent(student);//插到数据表
                		if(count == null || count <= 0) {//插入失败记录
                			fileTaskManager.insertTaskFailDetail(taskFailDetail);
                			failSum ++;
            			}
                		else {
                			sucSum ++;
                		}
                	}
                }
			}
			Long endTime = System.currentTimeMillis();
			Long costTime = endTime - startTime;
			taskFile.setStatus(3);
			taskFile.setFailSum(failSum);
			taskFile.setSucSum(sucSum);
			taskFile.setSum(failSum + sucSum);
			taskFile.setCostTime(costTime.intValue());
			fileTaskManager.sysHandleStatus(taskFile);
			
			//成功后删除原文件，失败则保留
			
			fis.close();
		} 
		catch (FileNotFoundException e) {
			try {
				isHandlerFile(taskFile, 4, "FileNotFoundException : "+ e.getMessage());
				if (fis != null) {
					fis.close();
				}
			} 
			catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} 
		catch (IOException e) {
			try {
				isHandlerFile(taskFile, 4, "IOException : "+ e.getMessage());
				if (fis != null) {
					fis.close();
				}
			} 
			catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		catch (Exception e) {
			try {
				isHandlerFile(taskFile, 4, "IOException : "+ e.getMessage());
				if (fis != null) {
					fis.close();
				}
			} 
			catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	/**
	 * 更新文件处理状态
	 * @param taskFile
	 * @param status
	 * @param remark
	 */
	private void isHandlerFile(TaskFile taskFile, Integer status, String remark) {
		taskFile.setStatus(status);
		if (remark != null) {
			taskFile.setRemark(remark);
		}
		fileTaskManager.sysHandleStatus(taskFile);
	}

}
