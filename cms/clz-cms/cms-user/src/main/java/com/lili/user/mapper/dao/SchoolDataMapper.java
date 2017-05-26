package com.lili.user.mapper.dao;

import java.util.List;

import com.lili.user.model.CarFile;
import com.lili.user.model.CarFileBDTO;
import com.lili.user.model.CoachFile;
import com.lili.user.model.CoachFileBDTO;
import com.lili.user.model.FieldFile;
import com.lili.user.model.FieldFileBDTO;
import com.lili.user.model.SchoolDataFile;
import com.lili.user.model.SchoolDataFileBDTO;
import com.lili.user.model.StudentFile;
import com.lili.user.model.StudentFileBDTO;

public interface SchoolDataMapper {

	/**
	 * 插入Excel实体
	 * @param carFile
	 * @return
	 */
	public Long insertCarFile(CarFile file);
    
	/**
	 * 插入Excel实体
	 * @param carFile
	 * @return
	 */
	public Long insertCoachFile(CoachFile file);
	
	/**
	 * 插入Excel实体
	 * @param carFile
	 * @return
	 */
	public Long insertStudentFile(StudentFile file);
	
	/**
	 * 插入Excel实体
	 * @param carFile
	 * @return
	 */
	public Long insertFieldFile(FieldFile file);
	
	/**
	 * 插入Excel实体
	 * @param carFile
	 * @return
	 */
	public Long insertSchoolDataFile(SchoolDataFile file);
	
	public List<StudentFile> findStudentBatch(StudentFileBDTO dto);
	
	public List<SchoolDataFile> findSchoolDataBatch(SchoolDataFileBDTO dto);
	
	public List<CarFile> findCarBatch(CarFileBDTO dto);
	
	public List<FieldFile> findFieldBatch(FieldFileBDTO dto);
	
	public List<CoachFile> findCoachBatch(CoachFileBDTO dto);
	
}
