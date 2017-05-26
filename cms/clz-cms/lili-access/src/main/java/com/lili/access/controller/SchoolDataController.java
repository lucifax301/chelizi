package com.lili.access.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import com.lili.cms.entity.ResponseMessage;
import com.lili.coach.model.CoachBDTO;
import com.lili.user.model.CarFileBDTO;
import com.lili.user.model.CoachFileBDTO;
import com.lili.user.model.FieldFileBDTO;
import com.lili.user.model.SchoolDataFileBDTO;
import com.lili.user.model.StudentFileBDTO;
import com.lili.user.service.CMSSchoolDataService;

/**
 * 驾校数据同步接口
 * @author hughes
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/school-data")
public class SchoolDataController  extends BaseController {
	Logger logger = Logger.getLogger(SchoolDataController.class);

	@Autowired
	CMSSchoolDataService cmsSchoolDataService;
	
	/**
	 * 上传包含驾校学员、教练等数据的Excel<br>
	 * 导入顺序：车、教练、车和教练的关系、学员、学员和教练的关系
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public String upload(HttpServletRequest request, HttpServletResponse response) {
		
	      File file = getUploadFile(request);

			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
			//获得文件
			MultipartFile multipartFile = multipartRequest.getFile("filename");
			//获得文件名
			String fileName = multipartFile.getOriginalFilename();
	      
	      if(file != null){
	    	  try {
				return cmsSchoolDataService.importFile(file,fileName,  AccessWebUtil.getSessionUser(request)).build();
			} catch (Exception e) {
				access.error("错误:" + e.getMessage());
			    return new ResponseMessage().build();
			}
	      }

	      return new ResponseMessage("错误:未检测到文件,请检查" ).build();
	      
	}
	
	
	
	/**
	 * 下载EXCEL模板
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/downLoadTemplate", method = RequestMethod.GET)
	@ResponseBody
	public Object downLoadTemplate(HttpServletRequest request, HttpServletResponse response) {
		String String = null;
		OutputStream os = null;
		InputStream is = null;
		try {
			String fileName = new String(Constant.SCHOOL_DATA_FILE_NAME.getBytes("UTF-8"),"ISO8859-1") +  ".xlsx";
			logger.info("******************* fileName = " +fileName);
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
		    response.setHeader("Content-disposition", "attachment;filename=" + new String(Constant.SCHOOL_DATA_FILE_OUT_NAME.getBytes("UTF-8"),"ISO8859-1") + ".xlsx");  
			response.setContentType("application/vnd.ms-excel");  
	        os.flush();  
	        is.close();  
	        os.close();  
		}
		catch (Exception e) {
			logger.error("************************************ error: " + e.getMessage());
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
				logger.error("************************************ error: " + e.getMessage());
	            return String;
			}
		}
		return String;
	}
	

	/**
	 * 导入的总体情况列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/data-batch", method= RequestMethod.GET)
    @ResponseBody
    public String dataBatch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			SchoolDataFileBDTO dto = (SchoolDataFileBDTO) buildObject(request, SchoolDataFileBDTO.class);
			final String rsp = cmsSchoolDataService.findSchoolDataBatch(dto).build();
			return rsp;
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 导入学员错误的列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/student-batch", method= RequestMethod.GET)
    @ResponseBody
    public String studentBatch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			StudentFileBDTO dto = (StudentFileBDTO) buildObject(request, StudentFileBDTO.class);
			final String rsp = cmsSchoolDataService.findStudentBatch(dto).build();
			return rsp;
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 导入教练错误的列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/coach-batch", method= RequestMethod.GET)
    @ResponseBody
    public String coachBatch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			CoachFileBDTO dto = (CoachFileBDTO) buildObject(request, CoachFileBDTO.class);
			final String rsp = cmsSchoolDataService.findCoachBatch(dto).build();
			return rsp;
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	

	/**
	 * 导入车辆错误的列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/car-batch", method= RequestMethod.GET)
    @ResponseBody
    public String carBatch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			CarFileBDTO dto = (CarFileBDTO) buildObject(request, CarFileBDTO.class);
			final String rsp = cmsSchoolDataService.findCarBatch(dto).build();
			return rsp;
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 导入场地错误的列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/field-batch", method= RequestMethod.GET)
    @ResponseBody
    public String fieldBatch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			FieldFileBDTO dto = (FieldFileBDTO) buildObject(request, FieldFileBDTO.class);
			final String rsp = cmsSchoolDataService.findFieldBatch(dto).build();
			return rsp;
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	

}
