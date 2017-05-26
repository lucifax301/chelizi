package com.lili.order.manager;


import java.util.List;

import com.lili.cms.entity.PagedResult;
import com.lili.order.vo.EnrollOrderBalanceVo;
import com.lili.order.vo.EnrollOrderVo;

/**
 * 报名订单
 * @author lzb
 *
 */
public interface IEnrollOrderManager {

	/**
	 * 分页
	 * @param params
	 * @return
	 */
	public abstract PagedResult<EnrollOrderVo> queryEnrollOrderList(EnrollOrderVo enrollOrderVo);
	
	/**
	 * 详情
	 * @param params
	 * @return
	 */
	public abstract EnrollOrderVo queryDetailInfo(String orderId);

	/**
	 * 更新报名订单
	 * @param enrollOrder
	 */
	public abstract void updateEnrollOrder(String orderId);
	
	/**
	 * 查询学员报名城市
	 * @param enrollOrderVo
	 * @return
	 */
	public abstract EnrollOrderVo queryEnrollOrderByStudentId(EnrollOrderVo enrollOrderVo);

	/**
	 * 查询报名费结算
	 * @param enrollOrderBalanceVo
	 * @return
	 */
	public abstract PagedResult<EnrollOrderBalanceVo> queryEnrollOrderBalanceList(EnrollOrderBalanceVo enrollOrderBalanceVo);

	public abstract List<EnrollOrderVo> downLoadExl(EnrollOrderVo enrollOrderVo);

	public abstract List<EnrollOrderBalanceVo> payDownLoadExl(EnrollOrderBalanceVo enrollOrderVo);

}
