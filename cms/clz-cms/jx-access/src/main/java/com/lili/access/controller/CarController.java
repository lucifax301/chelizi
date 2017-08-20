package com.lili.access.controller;

import java.io.UnsupportedEncodingException;
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
import com.lili.log.model.LogCommon;
import com.lili.school.model.Car;
import com.lili.school.model.CarBDTO;
import com.lili.school.model.CarNBDTO;
import com.lili.school.service.CMSCarService;


@Controller
@Scope("prototype")
@RequestMapping("/car")
public class CarController extends BaseController{
	@Autowired
	CMSCarService cmsCarService;

	public static final String[] excelHeader = { "车牌号", "所属驾校", "车型类别", "驾驶类别", "里程(公里)","汽车等级","行驶证编号"};  
	
	@RequestMapping(value="/view", method=RequestMethod.GET)
    @ResponseBody
    public String view(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsCarService.findOne(getParamLong(request, "carId")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}


	@RequestMapping(value="/batch", method= RequestMethod.GET)
    @ResponseBody
    public String batch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			CarBDTO dto = (CarBDTO) buildObject(request, CarBDTO.class);
			return cmsCarService.findBatch(dto).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value="/add", method=RequestMethod.POST)
    @ResponseBody
    public String add(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			Car car = (Car) buildObject(request, Car.class);
			LogCommon log = initLogParams(request, LogConstant.MENU_ID_CAR, LogConstant.ACTION_ADD);
			return cmsCarService.insertOne(log,car).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	

	@RequestMapping(value="/coach", method=RequestMethod.GET)
    @ResponseBody
    public String coach(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			CarNBDTO dto = (CarNBDTO) buildObject(request, CarNBDTO.class);
			return cmsCarService.findOneForCoach(dto).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	@RequestMapping(value="/update", method= RequestMethod.POST)
    @ResponseBody
    public String update(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			Car car = (Car) buildObject(request, Car.class);
			LogCommon log = initLogParams(request, LogConstant.MENU_ID_CAR, LogConstant.ACTION_UPDATE);
			return cmsCarService.updateOne(log,car).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	
	@RequestMapping(value="/delete", method= RequestMethod.POST)
    @ResponseBody
    public String del(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			Car car = (Car) buildObject(request, Car.class);
			LogCommon log = initLogParams(request, LogConstant.MENU_ID_CAR, LogConstant.ACTION_UPDATE);
			return cmsCarService.deleteOne(log,car).build();
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
			CarBDTO dto = (CarBDTO) buildObject(request, CarBDTO.class);
			 List<Car> carList = cmsCarService.getExportSource(dto);
			Workbook wb = getWorkbook(carList);
			sendExcel(response, wb, Constant.SHEET_CAR_FILE_NAME);
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	
	public Workbook getWorkbook(List<Car> carList){

		 Workbook wb = null; 
			try {
				wb = new SXSSFWorkbook(100);  //内存里一次只留多少行,几十万行无压力，不怕OOM
				Sheet sheet = wb.createSheet(Constant.SHEET_CAR_FILE_NAME);  //设置工作表标题
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
				Car car = null;
				for (int i = 0; i < carList.size(); i++) {  
				    row = sheet.createRow(i + 1);  
				    row.setRowStyle(style);
				    car = carList.get(i);  
				    row.createCell(0).setCellValue(car.getCarNo());  
				    row.createCell(1).setCellValue(car.getSchoolName());  
				    row.createCell(2).setCellValue(car.getCarType());  
				    row.createCell(3).setCellValue((car.getDriveType() == 2)?"C2":"C1");  
				    row.createCell(4).setCellValue(car.getMileage());  
				    row.createCell(5).setCellValue(car.getCarLevelEnum(car.getCarLevel()));   
				    row.createCell(6).setCellValue(car.getDriveNumber());   
				}
			} 
			catch (Exception e) {
				access.error("|||exception e :" + e.getMessage() + " when export car");
			}  
		
		
		return wb;
	}
}
