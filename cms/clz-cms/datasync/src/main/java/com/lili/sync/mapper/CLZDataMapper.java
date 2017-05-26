package com.lili.sync.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lili.school.dto.EnrollProgressTemplate;
import com.lili.school.dto.EnrollProgressUser;
import com.lili.sync.dto.SGCar;
import com.lili.sync.dto.SGCertificate;
import com.lili.sync.dto.SGCoach;
import com.lili.sync.dto.SGOfficial;
import com.lili.sync.dto.SGStudent;

public interface CLZDataMapper {
	
	public List<SGCoach> selectDelCoach(List<SGCoach> sgCoachs);
	public int updateDelCoach(List<SGCoach> sgCoachs);
	public int delDelCoachInfo(@Param("coachIds") String coachIds);
	public int updateCoach(List<SGCoach> sgCoachs);
	public int insertCoach(List<SGCoach> sgCoachs);
	
	public int insertTempStudent(List<SGStudent> sgStudents);
	public int updateJXStudentState();
	public int insertJXStudent();
	public int updateJXStudent();
	public int insertCertificate(List<SGCertificate> sgCertificates);
	public int insertOfficial(List<SGOfficial> sgOfficials);
	public int updateProgressState();
	public int updateTempStudentIdByIdNumber();
	public int updateTempStudentIdByPhone();
	public int updateLastUpdateTime();
	public List<EnrollProgressTemplate> selectProgressTemplate();
	public List<SGOfficial> selectNeedInsertProgress();
	public int insertProgress(List<EnrollProgressUser> epus);
	public int updateProgress(List<EnrollProgressUser> epus);
	public int updateCLZTempStudentState();
	public int updateCLZStudent();
	public int deleteTempTable();
	
	public int deleteCS();
	public int insertCS();
	public int updateCar(List<SGCar> sgCars);
	public int insertCar(List<SGCar> sgCars);
	public int deleteCC();
	public int insertCC();
}
