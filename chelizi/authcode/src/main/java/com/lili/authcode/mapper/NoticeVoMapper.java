package com.lili.authcode.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lili.authcode.dto.Notice;

public interface NoticeVoMapper {
 
	public int queryMaxIdAddOne();
	
	/**
	 * 根据消息中心配置获取符合条件学员Id进行推送
	 * @param idNumber
	 * @return
	 */
	public Set<Long> getNoticeStudentIds(Map<String,Object> map);
	
	/**
	 * 根据消息中心配置获取符合条件教练Id进行推送
	 * @param idNumber
	 * @return
	 */
	public Set<Long> getNoticeCoachIds(Map<String,Object> map);
	
	public void noticeAddClickNum(String noticeId);
	
	public void updateNoticeById(Notice notice);
	
	public List<Notice> getNoticeCoachIndex(long coachId);
	
	public List<Notice> getNoticeStudentIndex(long studentId);

	public Notice queryNoticeInfo(Notice notice);
}
