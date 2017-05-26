package com.lili.exam.manager;

import com.lili.common.util.Page;
import com.lili.exam.dto.ExamPlaceWhitelist;

/**
 * 考场白名单管理
 * @author yangpeng
 *
 */
public interface ExamPlaceWhitelistManager {
	
	/**
	 * 分页获取白名单列表
	 * @param schoolId
	 * @param name
	 * @param mobile
	 * @param state
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Page<ExamPlaceWhitelist> getWhitelist(Integer schoolId,String name,String mobile,Byte state,String ctime,Integer pageNo,Integer pageSize);
	
	/**
	 * 添加白名单
	 * @param schoolId
	 * @param school
	 * @param name
	 * @param mobile
	 * @return
	 */
	Integer addWhitelist(Integer schoolId,String school,String name,String mobile);
	
	/**
	 * 删除白名单成员
	 * @param id
	 * @return
	 */
	Integer delWhitelist(Integer id);

	/**
	 * 修改白名单
	 * @param id
	 * @param name
	 * @param mobile
	 * @return
	 */
	Integer modifyWhitelist(Integer id, String name, String mobile);
	
}
