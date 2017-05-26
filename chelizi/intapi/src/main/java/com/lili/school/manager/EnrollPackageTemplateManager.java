package com.lili.school.manager;

import java.util.List;

import com.lili.school.dto.EnrollPackageTemplate;
import com.lili.school.dto.EnrollPackageTemplateExample;
import com.lili.school.dto.EnrollPackageTemplateWithBLOBs;

public interface EnrollPackageTemplateManager {
	/**
	 * 添加报名套餐
	 * @param record
	 * @return
	 */
	public int addEnrollPackageTemplate(EnrollPackageTemplateWithBLOBs record);
	/**
	 * 更新报名套餐	激活，停用，修改
	 * @param record
	 * @param example
	 * @return
	 */
	public int updateEnrollPackageTemplate(EnrollPackageTemplateWithBLOBs record,EnrollPackageTemplateExample example);
	/**
	 * 更新报名套餐  
	 * @param ttid 套餐id列表
	 * @param isdel	0-正常，激活；1-删除，停用
	 * @return
	 */
	public int updateEnrollPackageTemplate(List<Integer> ttid,byte isdel);
	/**
	 * 获取报名套餐
	 * @param example
	 * @return
	 */
	public List<EnrollPackageTemplateWithBLOBs> getEnrollPackageTemplate(EnrollPackageTemplateExample example);
	
	/**
	 * 根据报名套餐id获取城市id
	 * @param ttid
	 * @return
	 */
	public int getCityByTtid(int ttid);
	
}
