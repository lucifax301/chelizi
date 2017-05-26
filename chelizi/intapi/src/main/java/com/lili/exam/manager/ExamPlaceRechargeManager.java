package com.lili.exam.manager;

import java.util.List;

import com.lili.exam.dto.ExamPlaceRechargeGears;
import com.lili.exam.dto.ExamPlaceRechargeSchool;

/**
 * 考场优惠方案管理
 * @author yangpeng
 *
 */
public interface ExamPlaceRechargeManager {
	/**
	 * 添加优惠方案
	 * @param name
	 * @param gears
	 * @param info
	 * @param muser
	 * @return
	 */
	public int addRecharge(String name,String gears,String info,String muser);
	
	/**
	 * 添加优惠方案
	 * @param name
	 * @param rechargeGearList
	 * @param info
	 * @param muser
	 * @return
	 */
	public Integer addRecharge(String name, List<ExamPlaceRechargeGears> rechargeGearList, String info, String muser);
	
	/**
	 * 添加优惠方案适用的驾校
	 * @param rid
	 * @param schoolId
	 * @param muser
	 * @return
	 */
	public ExamPlaceRechargeSchool addRechargeSchool(String rid, String schoolId, String muser);
	
}


























