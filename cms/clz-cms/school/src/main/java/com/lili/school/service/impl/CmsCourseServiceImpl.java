package com.lili.school.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.constant.Constant;
import com.lili.cms.constant.MessageCode;
import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.file.dto.Coursenew;
import com.lili.file.manager.CoursenewManager;
import com.lili.school.manager.ICourseManager;
import com.lili.school.model.CourseNewDTO;
import com.lili.school.service.ICmsCourseService;

public class CmsCourseServiceImpl implements ICmsCourseService {
	Logger logger = Logger.getLogger(CmsCourseServiceImpl.class);
	
	@Autowired
	ICourseManager cmsCourseManager;
	
	@Autowired
	CoursenewManager coursenewManager;
	
	@Override
	public String query(CourseNewDTO course) {
		String resp =null;
		PagedResult<CourseNewDTO> batch = null;
		try {
			batch = cmsCourseManager.queryCourseList(course);
			resp = new ResponseMessage().addResult("pageData", batch).build();
		} 
		catch (Exception e) {
			logger.error("************************************ error: " + e.getMessage());
		}
		return resp;
	}

	/**
	 * 批量激活、停用课程
	 */
	@Override
	public ResponseMessage isUseOrDel(String checker, String remark, String id, Integer isdel) {
		try {
			String[] idList  = id.split(",");
			if (isdel == 0) {
				List<CourseNewDTO> courseList = cmsCourseManager.queryCourseList(id);
				for (int i =0; i< courseList.size(); i++) {
					if(courseList.get(i).getIsdel() == Constant.IS_USE){
						return new ResponseMessage(MessageCode.MSG_HANDE_CF);
					}
				}
			}
			else if (isdel ==1 ) {
				List<CourseNewDTO> courseList = cmsCourseManager.queryCourseList(id);
				for (int i =0; i< courseList.size(); i++) {
					if(courseList.get(i).getIsdel() == Constant.IS_DEL){
						return new ResponseMessage(MessageCode.MSG_HANDE_CF);
					}
				}
			}
			else {
				return new ResponseMessage(MessageCode.MSG_REQUEST);
			}
			
			List<String> coursenewNo = new ArrayList<String>();
			for (int i =0; i< idList.length; i++) {
				coursenewNo.add(idList[i]);
			}
			
			//调APP接口批量更新
			int r = coursenewManager.putCoursenew(coursenewNo, (byte)isdel.intValue());
			if(r > 0){
				logger.info("-------------------------------- CmsCourseServiceImpl isUseOrDel  Success!");
				return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
			}
			else {
				return new ResponseMessage(MessageCode.MSG_FAIL);
			}
		} 
		catch (NumberFormatException e) {
			logger.error("********************************* error："+e.getMessage());
			return new ResponseMessage(MessageCode.MSG_FAIL);
		} 
	}

	/**
	 * 修改课程
	 */
	@Override
	public ResponseMessage update(String checker, Coursenew coursenew) {
		
		//调APP接口
		try {
			coursenewManager.putCoursenew(coursenew);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("********************************* error："+e.getMessage());
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
		
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	/**
	 * 新增课程
	 */
	@Override
	public ResponseMessage addCourse(String checker, Coursenew coursenew) {
		//调APP接口
		try {
			coursenewManager.postCoursenew(coursenew);
		} 
		catch (Exception e) {
			e.printStackTrace();
			logger.error("********************************* error："+e.getMessage());
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	@Override
	public List<CourseNewDTO> queryCourseNewList(CourseNewDTO courseNew) {
		
		return cmsCourseManager.queryCourseNewList(courseNew);
	}

}
