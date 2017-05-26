package com.lili.school.mapper;

import java.util.List;

import com.lili.school.dto.WechatEnrollStudentDto;
import com.lili.school.vo.WechatEnrollStudent;

public interface WechatEnrollStudentMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(WechatEnrollStudent record);

    int insertSelective(WechatEnrollStudent record);

    WechatEnrollStudent selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(WechatEnrollStudent record);

    int updateByPrimaryKey(WechatEnrollStudent record);

	List<WechatEnrollStudent> queryEnrollStudentList(WechatEnrollStudentDto wechatEnrollStudentDto);

	WechatEnrollStudent queryEnrollStudentInfo(WechatEnrollStudent record);

	WechatEnrollStudent queryNewEnrollStudent(WechatEnrollStudent recordStudent);

	int updateWechatEnrollStudent(WechatEnrollStudent updateEnrollStudent);
}