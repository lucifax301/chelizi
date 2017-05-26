/**
 * 
 */
package com.lili.student.mapper.dao.common;

import java.util.List;

import com.lili.student.model.RechargePlanBDTO;
import com.lili.student.model.RechargePlanVo;
import com.lili.student.model.VipCompanyBDTO;
import com.lili.student.model.VipCompanyVo;
import com.lili.student.model.VipCustomBDTO;
import com.lili.student.model.VipCustomVo;


public interface VipMapper {

	/**
	 * 获取大客户公司列表
	 * @param dto
	 * @return
	 */
	public List<VipCompanyVo> findCompanyBatch(VipCompanyBDTO dto);

	/**
	 * 获取大客户员工
	 * @param dto
	 * @return
	 */
	public List<VipCustomVo> findCustomBatch(VipCustomBDTO dto);
	
	/**
	 * 查询充值送方案列表
	 * @param dto
	 * @return
	 */
	public List<RechargePlanVo> findChargePlanBatch(RechargePlanBDTO dto);
	
	
}
