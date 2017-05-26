package com.lili.coach.manager;

import java.util.List;

import com.lili.coach.dto.ArrangeTime;


public interface ArrangeTimeManager {

	/**
	 * 获取排班时间列表
	 * @param arrangeTime
	 * @return
	 */
	public List<ArrangeTime> getArrangeTime(ArrangeTime arrangeTime);

	/**
	 * 根据id获取排班时间信息
	 * @param id
	 * @return
	 */
	public ArrangeTime getArrangeTimeInfo(int id);

	/**
	 * 获取排班时间数量
	 * @return
	 */
	public int getCount();

	/**
	 * 新增排班时间信息
	 * @param arrangeTime
	 * @return
	 */
	public int addArrangeTime(ArrangeTime arrangeTime);

	/**
	 * 更新排班时间信息
	 * @param arrangeTime
	 * @return
	 */
	public int updateArrangeTime(ArrangeTime arrangeTime);

	/**
	 * 根据id删除车辆信息
	 * @param id
	 * @return
	 */
	public int deleteArrangeTime(int id);
}
