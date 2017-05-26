package com.lili.student.mapper.dao;


import java.util.List;

import com.lili.student.dto.SubjectVideo;

public interface SubjectVideoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SubjectVideo record);

    int insertSelective(SubjectVideo record);

    SubjectVideo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SubjectVideo record);

    int updateByPrimaryKey(SubjectVideo record);
    
	List<SubjectVideo> querySubjectVideoList(SubjectVideo subjectVideo);
}