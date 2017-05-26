package com.lili.coach.mapper.dao;


import java.util.List;

import com.lili.order.vo.CoachClassTemp;
import com.lili.order.vo.CoachClassTempNameVo;
import com.lili.order.vo.CoachClassTempQuery;
import com.lili.order.vo.CoachClassTempVo;

public interface CoachClassTempMapper {
    int deleteByPrimaryKey(Integer id);
    
    int deleteByTempId(Integer tempId);

    int insert(CoachClassTempVo record);

    int insertSelective(CoachClassTempVo record);

    CoachClassTempVo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CoachClassTempVo record);

    int updateByPrimaryKey(CoachClassTempVo record);

	List<CoachClassTempQuery> queryTemplate(CoachClassTemp coachClassTemp);

	void saveClassTemp(List<CoachClassTempVo> classTempList);

	void closeClassTemp(List<CoachClassTempVo> classTempList);

	void updateClassTemp(List<CoachClassTempVo> classTempList);

	List<CoachClassTempNameVo> queryTemplateName(CoachClassTemp coachClassTempVo);

	Integer queryIsExitClassTempName(CoachClassTempVo coachClassTempVo);
}