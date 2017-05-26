package com.lili.authcode.manager;

import java.util.Date;
import java.util.List;

import com.lili.authcode.dto.Notice;
import com.lili.coach.dto.Coach;
import com.lili.common.util.Page;
import com.lili.student.dto.Student;

public interface NoticeManager {
	/**
	 * 学员获取系统消息内容
	 * @param studentId
	 * @param schoolId
	 * @param cityId
	 * @param type 消息中心类型为1；重点播报类型为0
	 * @param etime 截止时间，过了截止时间的消息不再显示
	 * @return
	 */
	public Page<Notice> getNoticesByStudentId(Long studentId,Integer schoolId,Integer cityId, byte type,Date etime, int pageNo, int pageSize);
	/**
	 * 学员获取系统消息内容2.1版本
	 * @param studentId
	 * @param schoolId
	 * @param cityId
	 * @param type 消息中心类型为1；重点播报类型为0
	 * @param etime 截止时间，过了截止时间的消息不再显示
	 * @return
	 */
	public Page<Notice> getNotices(Student student, byte type, int pageNo, int pageSize);
	
	/**
	 * 学员获取系统消息内容首页2.1版本
	 * 
	 */
	public List<Notice> getNoticeIndex(Student student);
	
	/**
	 * 教练获取系统消息内容首页2.1版本
	 * 
	 */
	public List<Notice> getNoticeCoachIndex(Coach coach);

	/**
	 * 教练获取系统消息内容
	 * @param coachId
	 * @param schoolId
	 * @param cityId
	 * @param type 消息中心类型为1；重点播报类型为0
	 * @param isVip 是否特约教练
	 * @param etime 截止时间，过了截止时间的消息不再显示
	 * @return
	 */
	public Page<Notice> getNoticesByCoachId(Long coachId,Integer schoolId,Integer cityId,byte type,Boolean isVip,Date etime, int pageNo, int pageSize);

	public Page<Notice> getNoticesByCoachId(Coach coach,byte type, int pageNo, int pageSize);
	
	/**
	 * 添加一条消息
	 * @param notice
	 */
	public void addNotice(Notice notice);
	
	/**
	 * 获取总数
	 * @return
	 */
	public int getTotalCount();
	
	public Page<Notice> getNotice(Notice notice,int pageNo,int pageSize);
	
	public int addCmsNotice(Notice notice);
	
	public int updateNotice(Notice notice);
	
	public Notice getNoticeById(String noticeId);
	
	public int updateState(String noticeId,String isdel,String type);
	
	public void sendMessage(Notice notice);
	
	public void noticeAddClickNum(String noticeId);
	
	public Notice getNoticeByOrderId(String orderId,byte usertype);
	
	
}
