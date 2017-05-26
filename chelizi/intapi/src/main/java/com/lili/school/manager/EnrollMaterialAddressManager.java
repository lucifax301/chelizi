package com.lili.school.manager;

import java.util.List;

import com.lili.school.dto.EnrollMaterialAddress;
import com.lili.school.dto.EnrollMaterialAddressExample;

public interface EnrollMaterialAddressManager {
	/**
	 * 增加报名资料收件地址，注意与报名套餐一一对应
	 * @param record
	 * @return
	 */
	public int addEnrollMaterialAddress(EnrollMaterialAddress record);
	/**
	 * 修改收件地址，用主键更新
	 * @param record
	 * @return
	 */
	public int updateEnrollMaterialAddress(EnrollMaterialAddress record);
	/**
	 * 查询收件地址
	 * @param example
	 * @return
	 */
	public List<EnrollMaterialAddress> getEnrollMaterialAddress(EnrollMaterialAddressExample example);
}
