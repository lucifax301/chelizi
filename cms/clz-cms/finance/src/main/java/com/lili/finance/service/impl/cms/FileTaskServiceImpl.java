package com.lili.finance.service.impl.cms;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.ResponseMessage;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.file.service.FileService;
import com.lili.finance.manager.cms.IFileTaskManager;
import com.lili.finance.model.cms.TaskFile;
import com.lili.finance.service.ICmsFileTaskService;
import com.lili.finance.vo.TaskFileVo;

public class FileTaskServiceImpl implements ICmsFileTaskService {
	
	@Autowired
	IFileTaskManager fileTaskManager;
	
	@Autowired
	FileService fileService;

	@Override
	public String insert(TaskFileVo taskFileVo) {
		TaskFile taskFile = new TaskFile();
		taskFile.setSchoolId(taskFileVo.getSchoolId());
		taskFile.setSchoolName(taskFileVo.getSchoolName());
		taskFile.setFileType(taskFileVo.getFileType());
		taskFile.setFileName(taskFileVo.getFileName());
		taskFile.setFilePath(taskFileVo.getFilePath());
		fileTaskManager.insertTaskFail(taskFile);
		return null;
	}

	@Override
	public String getUptoken(String userId, String userType, String tokenId) {
		String resp = null;
		try {
			ReqResult result =  fileService.getUptoken(userId, userType, tokenId);
			if(result.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)){
				resp = new ResponseMessage().addResult("pageData", result.getResult().get("data")).build();
				return resp;
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return  new ResponseMessage("获取文件上传凭证失败").build();
	}
	
	@Override
	public String getUpPublicToken(String userId, String userType, String tokenId) {
		String resp = null;
		try {
			ReqResult result =  fileService.getUpPublicToken(userId, userType, tokenId);
			if(result.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)){
				resp = new ResponseMessage().addResult("pageData", result.getResult().get("data")).build();
				return resp;
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return  new ResponseMessage("获取文件上传凭证失败").build();
	}

	@Override
	public String getDowntoken(String userId, String userType, String picType, String style, String carId, String isCheckCar) {
		String resp = null;
		try {
			boolean isTrue = false;
			if (isCheckCar != null && !"".equals(isCheckCar)) {
				isTrue = Boolean.getBoolean(isCheckCar);
			}
			ReqResult result =  fileService.getDownUrl(userId, userType, picType, style, carId, isTrue);
			if(result.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)){
				resp = new ResponseMessage().addResult("pageData", result.getResult().get("data")).build();
				return resp;
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return  new ResponseMessage("获取文件上传凭证失败").build();
	}

	@Override
	public String getDownUrlByKey(String picName) {
		String resp = null;
		try {
			ReqResult result =  fileService.getDownUrlByKey(null, null, picName, null);
			if(result.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)){
				resp = new ResponseMessage().addResult("pageData", result.getResult().get("data")).build();
				return resp;
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return  new ResponseMessage("获取图片链接失败").build();
	}

}
