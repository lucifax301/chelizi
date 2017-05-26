package com.lili.sync.sgmapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lili.sync.dto.SGCar;
import com.lili.sync.dto.SGCertificate;
import com.lili.sync.dto.SGCoach;
import com.lili.sync.dto.SGOfficial;
import com.lili.sync.dto.SGStudent;

public interface SGDataMapper {

	public List<SGCoach> selectCoach();
	public List<SGCoach> selectDelCoach();
	public List<SGStudent> selectJXStudent();
	public List<SGStudent> selectJXStudentLimit(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);
	public int selectJXStudentTotal();
	public List<SGStudent> selectCLZStudent();
	public List<SGCar> selectCar();
	public List<SGOfficial> selectOfficial();
	public List<SGCertificate> selectCertificate();
	public int updateStudentState();
}
