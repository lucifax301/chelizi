package com.lili.finance.mapper.dao.common;

import com.lili.coupon.dto.CStock;

import java.util.List;

public interface CMSCStockDao {
    int deleteByPrimaryKey(Integer stockid);

    List<CStock> getAllValidStock(String eventType);

    CStock getStockByCouponTmpId(String couponTmpId);

    int insert(CStock record);

    int insertSelective(CStock record);

    void recoverStock(int stockid);

    CStock selectByPrimaryKey(Integer stockid);

    int updateByPrimaryKey(CStock record);

    int updateByPrimaryKeySelective(CStock record);

    int useStock(int stockid);

    /**
     * 批量更新优惠券库存,批量激活或者停止发放
     * @param ids 优惠券列表
     * @param isexist 0=不生效，1=生效
     * @return
     */
    int updateStockBatch(String ids,int isexist);
    
    /**
     * 主要是为了获取监听事件
     * @return
     */
    List<CStock> findStockList();
}