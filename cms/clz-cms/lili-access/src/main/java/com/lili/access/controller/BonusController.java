package com.lili.access.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
import com.lili.cms.util.FileHandle;
import com.lili.finance.service.ICmsBonusService;
import com.lili.finance.vo.BonusDetailVo;
import com.lili.finance.vo.BonusVo;
import com.lili.user.model.User;


@Controller
@Scope("prototype")
@RequestMapping("/bonus")
public class BonusController  extends BaseController {
	Logger logger = Logger.getLogger(BonusController.class);

	@Autowired
	ICmsBonusService bonusService;

	/**
	 * 导入列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Object upload(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json"); 
		String resp = new String();
		    
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constant.USER_SESSION);
		String creator = user.getAccount(); 
		
	     //上传文件处理
	    String fileType = null;
	    InputStream is = null;
	    try {
	      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
	        //获得文件
	      MultipartFile multipartFile = multipartRequest.getFile("filename");
	        //获得文件名
	      String fileName = multipartFile.getOriginalFilename();
	      logger.info("***************************** FileName is : " + fileName );
	         //文件存放地址
	      String tagFileName = request.getSession().getServletContext().getRealPath("WEB-INF");
	      if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
	    	    logger.info("***************************** FileName is Error!" );
	    	    resp =new ResponseMessage(MessageCode.MSG_FAIL).build();
	            return 	resp;
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
	      tagFileName = tagFileName + "/uploadExcel/"+Constant.BONUS_FILE_NAME+ time + fileType;
	      logger.info("************************ tagFileName :"+ tagFileName);
	        //输入流
	      is = multipartFile.getInputStream();   
	      File outfile = FileHandle.upLoadFile( is, tagFileName);
	      
	      if (outfile.exists()) {
		      FileInputStream outfileis = new FileInputStream(outfile);
		      ByteArrayOutputStream out = new ByteArrayOutputStream();
	
		      byte[] buffer = new byte[outfileis.available()];
		      outfileis.read(buffer);
		      out.write(buffer);
		      out.close();
		      outfileis.close();
		      
		      //modify by devil 20161009, 传字节给dubbo服务
		      //resp = bonusService.upload(outfile, tagFileName, creator).build();
		      resp = bonusService.uploadNew(buffer, tagFileName, creator).build();
		      
		      if (outfile.isFile()) { // 判断是否为文件
					outfile.delete();
		      }
		      
		      if (resp != null) {
		    	  if("1".equals(resp)) {
		    		   resp =new ResponseMessage(MessageCode.MSG_FAIL).build();
			            return 	resp;
		    	  }
		    	  else {
			          resp =new ResponseMessage(0,MessageCode.MSG_SUCCESS).build();
			          return resp;
		    	  }
		      }
	      }else{
	    	  resp =new ResponseMessage(MessageCode.MSG_FAIL).build();
	          return 	resp;  
	      }
	      is.close();  
	    }
	  	catch (Exception e) {
	  		logger.error("************************************ error: " + e.getMessage());
	  		e.printStackTrace();
	    }
	    finally {
	    	try {
				if(is != null){
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    resp =new ResponseMessage(0,MessageCode.MSG_SUCCESS).build();
        return 	resp;
	}
	
	
	/**
	 * 获取列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ResponseBody
	public Object query(HttpServletRequest request, HttpServletResponse response) {
		try {
			BonusVo bonusVo = (BonusVo) buildObject(request, BonusVo.class);
			return bonusService.query(bonusVo);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 详情列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryDetail", method = RequestMethod.GET)
	@ResponseBody
	public Object queryDetail(HttpServletRequest request, HttpServletResponse response) {
		try {
			BonusDetailVo bonusDetailVo = new BonusDetailVo();
			bonusDetailVo.setPageNo(Integer.valueOf(request.getParameter("pageNo")));
			bonusDetailVo.setPageSize(Integer.valueOf(request.getParameter("pageSize")));
			bonusDetailVo.setBonusId(Integer.valueOf(request.getParameter("id")));
			return bonusService.queryDetail(bonusDetailVo);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
			BonusVo bonusVo = new BonusVo();
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			String verifier = user.getAccount(); 
			Integer id = Integer.valueOf(request.getParameter("id"));
			bonusVo.setVerifier(verifier);
			bonusVo.setId(id);
			resp =  bonusService.submit(bonusVo).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 发放奖金
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/grant", method = RequestMethod.POST)
	@ResponseBody
	public Object grant(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			BonusVo bonusVo = new BonusVo();
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			String verifier = user.getAccount(); 
			Integer bonusId = Integer.valueOf(request.getParameter("id"));
			bonusVo.setVerifier(verifier);
			bonusVo.setId(bonusId);
			resp =  bonusService.grant(bonusVo).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 删除
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			BonusVo bonusVo = new BonusVo();
			Integer id = Integer.valueOf(request.getParameter("id"));
			bonusVo.setId(id);
			resp =  bonusService.delete(bonusVo).build();
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
	@RequestMapping(value = "/downLoadTemplate", method = RequestMethod.GET)
	@ResponseBody
	public Object downLoadTemplate(HttpServletRequest request, HttpServletResponse response) {
		String String = null;
		OutputStream os = null;
		InputStream is = null;
		try {
			String fileName = new String(Constant.BONUS_FILE_NAME.getBytes("UTF-8"),"ISO8859-1") +  ".xlsx";
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
		    response.setHeader("Content-disposition", "attachment;filename=" + new String(Constant.BONUS_FILE_OUT_NAME.getBytes("UTF-8"),"ISO8859-1") + ".xlsx");  
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
	 * 财务拒绝
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/finReject", method = RequestMethod.POST)
	@ResponseBody
	public Object finReject(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			BonusVo bonusVo = new BonusVo();
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			String verifier = user.getAccount(); 
			bonusVo.setVerifier(verifier);
			bonusVo.setId(Integer.valueOf(request.getParameter("id")));
			bonusVo.setRemark(request.getParameter("remark"));
			resp =  bonusService.finReject(bonusVo).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

}
