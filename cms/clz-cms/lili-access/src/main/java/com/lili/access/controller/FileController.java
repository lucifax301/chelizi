package com.lili.access.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lili.cms.constant.Constant;
import com.lili.cms.constant.MessageCode;
import com.lili.cms.entity.ResponseMessage;
import com.lili.finance.service.ICmsFileTaskService;
import com.lili.finance.vo.TaskFileVo;
import com.lili.school.service.CMSSchoolService;
import com.lili.user.model.User;

/**
 * 上传文件通用处理
 * @author lzb
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/file")
public class FileController extends BaseController{
	
	private  final Logger logger = Logger.getLogger(FileController.class);
	
	@Autowired
	ICmsFileTaskService cmsFileTaskService;
	
	@Autowired
	CMSSchoolService cmsSchoolService;
	
	/**
	 * 上传Excel文件
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uploadExl", method = RequestMethod.POST)
	@ResponseBody
	public Object uploadExl(HttpServletRequest request, HttpServletResponse response) {
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/json"); 
	    String fileType = null;
	    String fileName = null;
	    String tagFileName = null;
	    OutputStream os = null;
	    InputStream is = null;
	    File outfile = null;
	    
		try {
			int index = Integer.valueOf(request.getParameter("upType"));	//上传文件类型1-教练信息，2-学员信息，3-训练场信息，4-车辆信息
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
			MultipartFile multipartFile = multipartRequest.getFile("filename");	//获得上传的文件
			fileName = multipartFile.getOriginalFilename();		//获得文件名
			logger.info("***************************** FileName is : " + fileName );
			
			logger.info("***************************** File Size is : "+ multipartFile.getSize());
			if (multipartFile.getSize() > 1024 * 1024) { //获取文件大小，待定
				return new ResponseMessage(601,MessageCode.FILE_SIZE_IS_OVER_LIMIT).build();
			}

			tagFileName = request.getSession().getServletContext().getRealPath("WEB-INF");	//文件存放地址
			if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
				logger.info("***************************** FileName is Error!" );
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
			tagFileName = tagFileName + "/uploadExcel/"+ UpType.getName(index) + time + fileType;
			logger.info("************************ tagFileName :"+ tagFileName); //服务器存放文件全路径

			is = multipartFile.getInputStream();   //输入流
			outfile = new File(tagFileName);
			os = new FileOutputStream(outfile);
			byte buffer[] = new byte[4*1024];
			int len = 0;
			while ((len = is.read(buffer))!=-1) {
				os.write(buffer,0,len);
			}
			
			//插入任务表
			TaskFileVo taskFileVo = new TaskFileVo();
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			Long schoolId =  user.getSchoolId();
			String schoolName = cmsSchoolService.findSchoolNameById(schoolId);
			taskFileVo.setSchoolId(schoolId.intValue());
			taskFileVo.setSchoolName(schoolName);
			taskFileVo.setFileType(index);
			taskFileVo.setFileName(fileName);
			taskFileVo.setFilePath(tagFileName);
			cmsFileTaskService.insert(taskFileVo );
			
			os.flush();
			os.close();
			is.close();
		}
		catch (FileNotFoundException e) {
			try {
				if (os != null) {
					os.close();
				}
				if(is != null){
					is.close();
				}
			} 
			catch (IOException e1) {
				logger.info("************************ FileNotFoundException :"+ e.getMessage()); 
				e1.printStackTrace();
			}
			e.printStackTrace();
		} 
		catch (IOException e) {
			try {
				if (os != null) {
					os.close();
				}
				if(is != null){
					is.close();
				}
			} 
			catch (IOException e1) {
				logger.info("************************ IOException :"+ e1.getMessage()); 
				e1.printStackTrace();
			}
			logger.info("************************ IOException :"+ e.getMessage()); 
			e.printStackTrace();
		}
		catch (Exception e) {
			try {
				if (os != null) {
					os.close();
				}
				if(is != null){
					is.close();
				}
			} 
			catch (IOException e1) {
				logger.info("************************ IOException :"+ e1.getMessage()); 
				e1.printStackTrace();
			}
			logger.info("************************ IOException :"+ e.getMessage()); 
			e.printStackTrace();
		}
		
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS).build();
	}
	
	/**
	 * 文件名
	 * @author lzb
	 *
	 */
	public enum UpType{
		COACH("coach_base",1), STUDENT("student_base",2), CAR("car_base",3), FIELD("field_base",4);
		
		private String name;
		private int index;
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
		
		private UpType(String name, int index){
			this.name = name;
			this.index = index;
		}
		
		public static String getName(int index) {
            for (UpType c : UpType.values()) {
                if (c.getIndex() == index) {
                    return c.name;
                }
            }
            return null;
        }
		
	}
	
	/**
	 * 获取上传照片的上传令牌
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param headers
	 * @return
	 */
	@RequestMapping(value = "/cmsUpToken", method = RequestMethod.GET)
	@ResponseBody
	public String getUptoken(HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String userId = request.getParameter("userId");
			String userType = request.getParameter("userType");
			String tokenId = null;
			return cmsFileTaskService.getUptoken(userId, userType, tokenId);
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/cmsUpPublicToken", method = RequestMethod.GET)
	@ResponseBody
	public String getUpPublicToken(HttpServletRequest request,HttpServletResponse response)  throws Exception {
		try {
			String userId = request.getParameter("userId");
			String userType = request.getParameter("userType");
			String tokenId = null;
			return cmsFileTaskService.getUpPublicToken(userId, userType, tokenId);
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	

	@RequestMapping(value = "/cmsDownUrl", method = RequestMethod.GET)
	@ResponseBody
	public String getDowntoken(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			String userId = request.getParameter("userId");
			String userType = request.getParameter("userType");
			String picType = request.getParameter("picType");
			String style = request.getParameter("style");
			String carId = request.getParameter("carId");
			String isCheckCar = request.getParameter("isCheckCar");
			return cmsFileTaskService.getDowntoken(userId, userType, picType, style, carId, isCheckCar); 
		} 
		catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/cmsDownUrlByKey", method = RequestMethod.GET)
	@ResponseBody
	public String getDownUrlByKey(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			String picName = request.getParameter("picName");
			return cmsFileTaskService.getDownUrlByKey(picName); 
		} 
		catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
}
