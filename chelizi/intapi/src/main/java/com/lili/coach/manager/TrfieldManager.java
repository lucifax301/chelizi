package com.lili.coach.manager;

import java.util.List;

import com.lili.coach.dto.Trfield;


public interface TrfieldManager {

	/**
	 * 获取训练场列表
	 * @param trfield
	 * @return
	 */
	public List<Trfield> getTrfield(Trfield trfield);

	/**
	 * 根据id获取训练场信息
	 * @param id
	 * @return
	 */
	public Trfield getTrfieldInfo(int id);

	/**
	 * 获取训练场数量
	 * @return
	 */
	public int getCount();

	/**
	 * 新增训练场信息
	 * @param trfield
	 * @return
	 */
	public int addTrfield(Trfield trfield);

	/**
	 * 更新训练场信息
	 * @param trfield
	 * @return
	 */
	public int updateTrfield(Trfield trfield);

	/**
	 * 根据id删除车辆信息
	 * @param id
	 * @return
	 */
	public int deleteTrfield(int id);
	
	/**
	 * 批量导入训练场信息，cms使用
	 * @param List<Trfield>
	 * @return
	 */
	public int addTrfieldList(List<Trfield> trfields);
}
