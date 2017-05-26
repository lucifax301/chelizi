package com.lili.coach.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.coach.dto.CourseS;
import com.lili.coach.manager.CourseSManager;
import com.lili.coach.mapper.dao.CourseSMapper;

public class CourseSManagerImpl implements CourseSManager, InitializingBean {

	@Autowired
	private CourseSMapper courseSMapper;
	
	private static Map<Integer, CourseS> courses = new HashMap<Integer, CourseS>(); 

	@Override
	public List<CourseS> getCourseS() {
		return courseSMapper.getAll();
	}

	@Override
	public CourseS getCourseSById(int courseTmpId) {
	    if (courses.size() == 0)
        {
	        List<CourseS> courseList = getCourseS();
	        for (CourseS courseS : courseList)
	        {
	            courses.put(courseS.getCourseTmpId(), courseS);
	        }
        }
		return courses.get(courseTmpId);
	}

	@Override
	public int getCourseSCount() {
		return courses.size();
	}

	@Override
	public int addCourseS(CourseS courseS) {
	    if (courseS != null)
        {
	        courses.put(courseS.getCourseTmpId(), courseS);
        }
		return courseSMapper.insert(courseS);
	}

	@Override
	public int updateCourseS(CourseS courseS) {
		return courseSMapper.updateByPrimaryKeySelective(courseS);
	}

	@Override
	public int deleteCourseS(int courseTmpId) {
	    courses.remove(courseTmpId);
		return courseSMapper.deleteByPrimaryKey(courseTmpId);
	}
    /* (non-Javadoc)
     * @see com.lili.coach.manager.CourseSManager#getAllCourseMap()
     */
    @Override
    public Map<Integer, CourseS> getAllCourseMap()
    {
        if (courses.size() == 0)
        {
            List<CourseS> courseList = getCourseS();
            for (CourseS courseS : courseList)
            {
                courses.put(courseS.getCourseTmpId(), courseS);
            }
        }
        return courses;
    }
    /* (non-Javadoc)
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception
    {
        List<CourseS> courseList = getCourseS();
        for (CourseS courseS : courseList)
        {
            courses.put(courseS.getCourseTmpId(), courseS);
        }
    }

}
