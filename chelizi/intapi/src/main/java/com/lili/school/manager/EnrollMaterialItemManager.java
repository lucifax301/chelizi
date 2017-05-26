package com.lili.school.manager;

import java.util.List;

import com.lili.school.dto.EnrollMaterialItems;
import com.lili.school.dto.EnrollMaterialItemsExample;

public interface EnrollMaterialItemManager {
	/**
	 * 新增资料条目
	 * @param record
	 * @return
	 */
	public int addEnrollMaterialItems(EnrollMaterialItems record);
	/**
	 * 更新资料条目，用主键更新
	 * @param record
	 * @return
	 */
	public int updateEnrollMaterialItems(EnrollMaterialItems record);
	/**
	 * 查询资料条目
	 * @param example
	 * @return
	 */
	public List<EnrollMaterialItems> getEnrollMaterialItems(EnrollMaterialItemsExample example);
}
