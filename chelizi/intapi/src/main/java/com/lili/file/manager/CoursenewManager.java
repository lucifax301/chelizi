package com.lili.file.manager;

import java.util.List;

import com.lili.file.dto.Coursenew;
import com.lili.file.dto.CoursenewExample;

public interface CoursenewManager {
	/**
	 * 查询课程
	 * @param example
	 * @return
	 */
	public List<Coursenew> getCoursenew(CoursenewExample example);
	/**
	 * 新增课程 coursetemid必须唯一，并注意是否同时有C1和C2两种课程
	 * @param course
	 * @return
	 */
	public int postCoursenew(Coursenew course);
	/**
	 * 修改课程,使用coursenewno标签进行更新
	 * @param course
	 * @return
	 */
	public int putCoursenew(Coursenew course);
	
	/**
	 * 修改课程，批量修改。
	 * @param record	设定参数变更
	 * @param example	设定课程检索条件
	 * @return
	 */
	public int putCoursenew(Coursenew record,CoursenewExample example);
	/**
	 * 修改课程，批量修改。
	 * @param coursenewNo	课程编号列表
	 * @param isdel			0-正常，启用；1-删除，停用
	 * @return
	 */
	public int putCoursenew(List<String> coursenewNo, byte isdel);
	
}
