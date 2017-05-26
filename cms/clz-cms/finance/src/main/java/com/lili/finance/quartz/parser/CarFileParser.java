package com.lili.finance.quartz.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
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

import com.lili.coach.dto.Car;
import com.lili.coach.manager.CarManager;
import com.lili.coach.model.Coach;
import com.lili.coach.service.CMSCoachService;
import com.lili.finance.manager.cms.IFileTaskManager;
import com.lili.finance.model.cms.TaskFailDetail;
import com.lili.finance.model.cms.TaskFile;

public class CarFileParser implements IFileParser {
	
	private static  final Logger logger = Logger.getLogger(CarFileParser.class);
	
/*	@Autowired
	CMSCarManager cmsCarManager;*/
	
	@Autowired
	CarManager carManager;
	
	@Autowired
	CMSCoachService cmsCoachService;
	
	@Autowired
	IFileTaskManager fileTaskManager;

	@Override
	public void parserFile(TaskFile taskFile) {
		logger.info("******************************** Handle CoachFileParser Start ! ******************************** ");
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
			Car car = null;
			DecimalFormat df = new DecimalFormat("0");
			//List<Coach> coachList = new ArrayList<Coach>();
			int sucSum = 0;
			int failSum = 0;
			HashMap<String,Byte> map = new HashMap<String,Byte>();
			map.put("C1", (byte) 1);
			map.put("C2", (byte) 2);
			
			Sheet sheet = wb.getSheetAt(0); // 获得第一个表单
			Iterator<Row> rows = sheet.rowIterator(); // 获得第一个表单的迭代器
			String coachPhone = null;
			while (rows.hasNext()) {  
				Row row = rows.next();  //获得行数据  
                if (row.getRowNum() >= 2) {  //从第三行开始读取
                	car = new Car(); //防止覆盖
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
               				if(cell.getColumnIndex() == 0) { //A车品牌
               					taskFailDetail.setRowA(String.valueOf(df.format(cell.getNumericCellValue())));
               					car.setCarType(String.valueOf(df.format(cell.getNumericCellValue())));
               				}
               				if(cell.getColumnIndex() == 1) { //B车牌
               					car.setCarNo(String.valueOf(df.format(cell.getNumericCellValue())));
               					taskFailDetail.setRowB(String.valueOf(df.format(cell.getNumericCellValue())));
               				}
               				else if(cell.getColumnIndex() == 2) {	//C汽油车/柴油车
               					taskFailDetail.setRowC(String.valueOf(df.format(cell.getNumericCellValue())));
               				}
               				else if(cell.getColumnIndex() == 3) {	//D车购买年份月份
               					taskFailDetail.setRowD(String.valueOf(df.format(cell.getNumericCellValue())));
               				}
               				else if(cell.getColumnIndex() == 4) {	//E驾驶类型
               					car.setDriveType(map.get(Integer.valueOf(df.format(cell.getNumericCellValue()))));
               					taskFailDetail.setRowE(String.valueOf(df.format(cell.getNumericCellValue())));
               				}
               				else if(cell.getColumnIndex() == 5) {	//F发动机号
               					taskFailDetail.setRowF(String.valueOf(df.format(cell.getNumericCellValue())));
               				}
               				else if(cell.getColumnIndex() == 6) {	//G车架号
               					taskFailDetail.setRowG(String.valueOf(df.format(cell.getNumericCellValue())));
               				}
               				else if(cell.getColumnIndex() == 7) {	//H教练ID
               					coachPhone = String.valueOf(df.format(cell.getNumericCellValue()));
               					taskFailDetail.setRowH(String.valueOf(df.format(cell.getNumericCellValue())));
               				}
               				else if(cell.getColumnIndex() == 8) {	//I教练名
               					taskFailDetail.setRowI(String.valueOf(df.format(cell.getNumericCellValue())));
               				}
                			break;  
                		case HSSFCell.CELL_TYPE_STRING:  
                			logger.info(cell.getStringCellValue());  
                			//cell读取列，第A列是0
                			if(cell.getColumnIndex() == 0) { //A车品牌
               					taskFailDetail.setRowA(cell.getStringCellValue().trim());
               					car.setCarType(cell.getStringCellValue().trim());
               				}
               				if(cell.getColumnIndex() == 1) { //B车牌
               					car.setCarNo(cell.getStringCellValue().trim());
               					taskFailDetail.setRowB(cell.getStringCellValue().trim());
               				}
               				else if(cell.getColumnIndex() == 2) {	//C汽油车/柴油车
               					taskFailDetail.setRowC(cell.getStringCellValue().trim());
               				}
               				else if(cell.getColumnIndex() == 3) {	//D车购买年份月份
               					taskFailDetail.setRowD(cell.getStringCellValue().trim());
               				}
               				else if(cell.getColumnIndex() == 4) {	//E驾驶类型
               					car.setDriveType(map.get(Integer.valueOf(cell.getStringCellValue().trim())));
               					taskFailDetail.setRowE(cell.getStringCellValue().trim());
               				}
               				else if(cell.getColumnIndex() == 5) {	//F发动机号
               					taskFailDetail.setRowF(cell.getStringCellValue().trim());
               				}
               				else if(cell.getColumnIndex() == 6) {	//G车架号
               					taskFailDetail.setRowG(cell.getStringCellValue().trim());
               				}
               				else if(cell.getColumnIndex() == 7) {	//H教练ID
               					coachPhone = cell.getStringCellValue().trim();
               					taskFailDetail.setRowH(cell.getStringCellValue().trim());
               				}
               				else if(cell.getColumnIndex() == 8) {	//I教练名
               					taskFailDetail.setRowI(cell.getStringCellValue().trim());
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
                	
                	if (car.getCarNo() != "") { 
                		//coachList.add(student); 	//读完一行加到数组里,批量操作使用，暂不使用
                		Coach coach = cmsCoachService.findByPhone(coachPhone);
                		int count = carManager.addCar(car, Long.valueOf(coach.getCoachId()));
                		if(count <= 0) {//插入失败记录
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
