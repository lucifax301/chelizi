package com.lili.exam.manager;

import java.util.List;

import com.lili.common.util.Page;
import com.lili.exam.dto.ExamPlace;
import com.lili.exam.dto.ExamPlaceCity;
import com.lili.exam.dto.ExamPlaceVo;

/**
 * 考场管理
 * @author yangpeng
 *
 */
public interface ExamPlaceManager {
	/**
	 * CMS:添加考场信息
	 * @param examPlace
	 * @return
	 */
	int addExamPlace (ExamPlace examPlace);
	
	/**
	 * CMS:修改考场信息
	 * @param examPlace
	 * @return
	 */
	int updateExamPlace(ExamPlace examPlace);
	
	/**
	 * CMS:查询考场
	 * @param name
	 * @param city
	 * @param school
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Page<ExamPlace> getExamPlaces(String name, String cityId, String schoolId,String pageNo, String pageSize);
	
	/**
	 * APP：根据城市id和科目类型获取考场信息
	 * @param cityId	城市id
	 * @param type		科目：2-科目二；3-科目三
	 * @return
	 */
	List<ExamPlaceVo> getExamPlaces(String cityId,String type);
	
	/**
	 * 根据id获取考场信息
	 * @param placeId
	 * @return
	 */
	ExamPlace getExamPlaceById(Integer placeId);

	/**
	 * 获取约考场城市
	 * @return
	 */
	List<ExamPlaceCity> getExamPlaceCity();
	
	/**
	 * 考场作为一个特殊的驾校，id映射关系需求重新取一下；这样不好，后面应该把考场独立出来
	 * @param schoolId
	 * @return
	 */
	ExamPlace getExamPlaceBySchoolId(Integer schoolId);
	
}













