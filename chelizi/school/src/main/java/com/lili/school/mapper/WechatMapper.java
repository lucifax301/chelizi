package com.lili.school.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lili.coach.dto.Trfield;
import com.lili.school.dto.School;
import com.lili.school.dto.SchoolWithBLOBs;
import com.lili.school.dto.WechatComment;

public interface WechatMapper {

	  int countBySchoolId(int schoolId);
	  
	  int deleteOrder(long studentId);
	  
	  List<Trfield> searchTrfieldBySchoolId(int schoolId);
	  
	  List<SchoolWithBLOBs> searchSchool(@Param("schoolId")String schoolId);
	  
      int getAvgScore(String schoolId);
      
      List<WechatComment> getCommentListBySchoolId(@Param("schoolId")String schoolId,@Param("studentId")String studentId,@Param("start")int start,@Param("end")int end);

      List<Trfield> getTrfieldBySchoolId(@Param("schoolId")String schoolId,@Param("size")int size);
      
      int getTrfieldCount(String schoolId);

      SchoolWithBLOBs querySchoolInfo(School school);
      
	  List<School> searchSchoolHasClass(School school);

	  List<School> searchSchoolNoClass(School school);
}
