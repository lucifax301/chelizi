package com.lili.coach.manager;

import java.util.List;

import com.lili.coach.dto.Trfields;
import com.lili.coach.vo.TrfieldsVo;


public interface TrfieldsManager {

	/**
	 * 获取训练场列表
	 * @param trfields
	 * @return
	 */
	public List<Trfields> getTrfield(Trfields trfields);

	/**
	 * 根据id获取训练场信息
	 * @param id
	 * @return
	 */
	public Trfields getTrfieldInfo(int id);

	/**
	 * 获取训练场数量
	 * @return
	 */
	public int getCount();

	/**
	 * 新增训练场信息
	 * @param trfields
	 * @return
	 */
	public int addTrfields(Trfields trfields);

	/**
	 * 更新训练场信息
	 * @param trfield
	 * @return
	 */
	public int updateTrfields(Trfields trfields);

	/**
	 * 根据id删除车辆信息
	 * @param id
	 * @return
	 */
	public int deleteTrfields(int id);

	public List<TrfieldsVo> getTrfieldsSpecial(String keyword,String rid,String imei);
}
