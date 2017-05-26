package com.lili.user.manager;


import com.lili.cms.entity.PagedResult;
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

public interface CMSSchoolDataManager {

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
	

	public PagedResult<StudentFile> findStudentBatch(StudentFileBDTO dto);
	
	public PagedResult<SchoolDataFile> findSchoolDataBatch(SchoolDataFileBDTO dto);
	
	public PagedResult<CarFile> findCarBatch(CarFileBDTO dto);
	
	public PagedResult<FieldFile> findFieldBatch(FieldFileBDTO dto);
	
	public PagedResult<CoachFile> findCoachBatch(CoachFileBDTO dto);
}
