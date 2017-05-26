package com.lili.coupon.mapper.dao;

import com.lili.coupon.dto.CStock;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CStockMapper {
    int deleteByPrimaryKey(Integer stockid);

    List<CStock> getAllValidStock(String eventType);

    CStock getStockByCouponTmpId(String couponTmpId);

    int insert(CStock record);

    int insertSelective(CStock record);

    void recoverStock(int stockid);
    
    void recoverMoreStock(@Param("stockid")int stockid,@Param("num")int num);

    CStock selectByPrimaryKey(Integer stockid);

    int updateByPrimaryKey(CStock record);

    int updateByPrimaryKeySelective(CStock record);

    int useStock(int stockid);
    
    int useMoreStock(@Param("stockid")int stockid,@Param("num")int num);

    /**
     * 批量更新优惠券库存,批量激活或者停止发放
     * @param ids 优惠券列表
     * @param isexist 0=不生效，1=生效
     * @return
     */
    int updateStockBatch(HashMap<String,Object> params);
//    int updateStockBatch(String[] ids,int isexist);
}