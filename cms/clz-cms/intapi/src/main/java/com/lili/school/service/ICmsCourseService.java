package com.lili.school.service;

import java.util.List;

import com.lili.cms.entity.ResponseMessage;
import com.lili.file.dto.Coursenew;
import com.lili.school.model.CourseNewDTO;

/**
 * 课程接口
 * @author lzb
 *
 */
public interface ICmsCourseService {

    public String query(CourseNewDTO courseNew);
    
    public ResponseMessage isUseOrDel(String checker, String remark, String id, Integer isdel);
    
    public ResponseMessage update(String checker, Coursenew coursenew);
    
    public ResponseMessage addCourse(String checker, Coursenew coursenew);
    
    List<CourseNewDTO> queryCourseNewList(CourseNewDTO courseNew);

}
