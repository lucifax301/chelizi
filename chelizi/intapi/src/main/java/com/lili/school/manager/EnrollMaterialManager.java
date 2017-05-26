package com.lili.school.manager;

import java.util.List;

import com.lili.school.dto.EnrollMaterial;
import com.lili.school.dto.EnrollMaterialExample;

public interface EnrollMaterialManager {
	/**
	 * 新增报名资料。某一个城市的报名套餐中，针对不同类型的人群，有不同的资料要求。
	 * @param record
	 * @return
	 */
	public int addEnrollMaterial(EnrollMaterial record);
	/**
	 * 更新报名资料。主键更新
	 * @param record
	 * @return
	 */
	public int updateEnrollMaterial(EnrollMaterial record);
	/**
	 * 获取报名资料列表
	 * @param example
	 * @return
	 */
	public List<EnrollMaterial> getEnrollMaterial(EnrollMaterialExample example);
}
