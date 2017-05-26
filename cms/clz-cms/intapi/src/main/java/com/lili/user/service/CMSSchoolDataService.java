package com.lili.user.service;

import java.io.File;

import com.lili.cms.entity.ResponseMessage;
import com.lili.user.model.CarFileBDTO;
import com.lili.user.model.CoachFileBDTO;
import com.lili.user.model.FieldFileBDTO;
import com.lili.user.model.SchoolDataFileBDTO;
import com.lili.user.model.StudentFileBDTO;
import com.lili.user.model.User;


public interface CMSSchoolDataService {

	/**
	 * 插入车辆数据
	 * @param file
	 * @param creator
	 * @return
	 * @throws Exception
	 */
	public  ResponseMessage importFile(File file,String fileName, User user) throws Exception;

	public ResponseMessage findStudentBatch(StudentFileBDTO dto);
	
	public ResponseMessage findSchoolDataBatch(SchoolDataFileBDTO dto);
	
	public ResponseMessage findCarBatch(CarFileBDTO dto);
	
	public ResponseMessage findFieldBatch(FieldFileBDTO dto);
	
	public ResponseMessage findCoachBatch(CoachFileBDTO dto);
}
