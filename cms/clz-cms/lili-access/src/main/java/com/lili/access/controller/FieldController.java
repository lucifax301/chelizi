package com.lili.access.controller;

import java.util.HashMap;
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
import com.lili.school.model.Field;
import com.lili.school.model.FieldBDTO;
import com.lili.school.service.CMSFieldService;

@Controller
@Scope("prototype")
@RequestMapping("/field")
public class FieldController extends BaseController{
	
	@Autowired
	CMSFieldService cmsFieldService;

	public static final String[] excelHeader = { "训练场", "所属驾校", "所属城市", "位置", "训练场面积",  "联系电话", "状态"};  

	
	@RequestMapping(value="/view", method=RequestMethod.GET)
    @ResponseBody
    public String view(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsFieldService.findOne(getParamLong(request, "fieldId")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}


	@RequestMapping(value="/add", method=RequestMethod.POST)
    @ResponseBody
    public String add(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			Field field = (Field) buildObject(request, Field.class);
			LogCommon log = initLogParams(request, LogConstant.MENU_ID_FIELD, LogConstant.ACTION_ADD);
			return cmsFieldService.insertOne(log,field).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value="/batch", method= RequestMethod.GET)
    @ResponseBody
    public String batch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			FieldBDTO dto = (FieldBDTO) buildObject(request, FieldBDTO.class);
			String schoolNo = request.getParameter("schoolNo");
			if(schoolNo!=null && !"".equals(schoolNo)){
				dto.setSchoolId(Long.valueOf(schoolNo));
			}
			return cmsFieldService.findBatch(dto).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	


	@RequestMapping(value="/update", method= RequestMethod.POST)
    @ResponseBody
    public String update(HttpServletRequest request,HttpServletResponse response) throws Exception{

		try {
			Field field = (Field) buildObject(request, Field.class);
			LogCommon log = initLogParams(request, LogConstant.MENU_ID_FIELD, LogConstant.ACTION_UPDATE);
			return cmsFieldService.updateOne(log,field).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value = "/export-excel", method = RequestMethod.GET)	
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
		try {
			FieldBDTO dto = (FieldBDTO) buildObject(request, FieldBDTO.class);
			Workbook wb = getWorkbook(cmsFieldService.getExportSource(dto));
			sendExcel(response, wb, Constant.SHEET_FIELD_FILE_NAME);
		} catch (Exception e) {
			access.error(e.getMessage());
		}
	}
	
	/**
	 * 训练场激活/停用
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/isDel", method= RequestMethod.POST)
    @ResponseBody
    public String isDel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			String id = request.getParameter("idList");
			String isDel = request.getParameter("isDel");
			
			return cmsFieldService.isUseOrDel(id, isDel).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	
	public Workbook getWorkbook(List<Field> list){

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
				sheet.setColumnWidth(1, 6000);
				sheet.setColumnWidth(2, 3000);
				sheet.setColumnWidth(3, 6000);
				sheet.setColumnWidth(4, 6000);
				sheet.setColumnWidth(5, 5000);
				sheet.setColumnWidth(6, 3000);
				
				HashMap<Integer,String> map = new HashMap<Integer,String>();
				map.put(0, "启用中");
				map.put(1, "已停用");//0代表未删除，1代表已删除
				
				// 遍历集合数据，产生数据行 
				Field field = null;
				for (int i = 0; i < list.size(); i++) {  
				    row = sheet.createRow(i + 1);  
				    row.setRowStyle(style);
				    field = list.get(i);  
				    row.createCell(0).setCellValue(field.getName());  
				    row.createCell(1).setCellValue(field.getSchoolName());  
				    row.createCell(2).setCellValue(field.getCity());  
				    row.createCell(3).setCellValue(field.getPosDesc());  
				    row.createCell(4).setCellValue(field.getReverseLim());  
				    row.createCell(5).setCellValue(field.getPhoneNum());   
				    row.createCell(6).setCellValue(map.get(field.getIsdel()));   
				}
			} 
			catch (Exception e) {
				access.error("|||exception e :" + e.getMessage() + " when export coach");
			}  
		
		
		return wb;
	}
}
