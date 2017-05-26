package com.lili.school.mapper;

import com.lili.school.dto.WechatStudentDto;
import com.lili.school.vo.WechatStudent;

public interface WechatStudentMapper {
    int deleteByPrimaryKey(Long studentId);

    int insert(WechatStudent record);

    int insertSelective(WechatStudent record);

    WechatStudent selectByPrimaryKey(Long studentId);

    int updateByPrimaryKeySelective(WechatStudent record);

    int updateByPrimaryKey(WechatStudent record);

//	List<WechatStudent> queryWechatStudentList(WechatStudentDto wechatStudentDto);

	WechatStudent queryWechatStudent(WechatStudent wechatStudent);

	Integer countMyStudentNum(WechatStudentDto wechatStudentDto);
}