/**
 * 
 */
package com.lili.share.dao.mapper;

import java.util.Map;

import com.lili.share.dao.po.StudentPo;

public interface StudentMapper {

	
	/**
	 * 通过手机号查学员，参数中包含学员ID，查其他学员有没有使用参数中的手机号
	 * @param params
	 * @return
	 */
	public StudentPo queryByPhone(Map<String, Object> params);
	
    
}
