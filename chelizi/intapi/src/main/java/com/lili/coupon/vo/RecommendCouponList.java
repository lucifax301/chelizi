/**
 * 
 */
package com.lili.coupon.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author linbo
 * 推荐的优惠券
 */
public class RecommendCouponList implements Serializable
{
    private static final long serialVersionUID = 1918025272460294725L;

    //可用的优惠券列表
    private List<CouponVo> couponVos;
    
    //当前推荐的优惠券
    private CouponVo couponVo;

    /**
     * @return the couponVos
     */
    public List<CouponVo> getCouponVos()
    {
        return couponVos;
    }

    /**
     * @param couponVos the couponVos to set
     */
    public void setCouponVos(List<CouponVo> couponVos)
    {
        this.couponVos = couponVos;
    }

    /**
     * @return the couponVo
     */
    public CouponVo getCouponVo()
    {
        return couponVo;
    }

    /**
     * @param couponVo the couponVo to set
     */
    public void setCouponVo(CouponVo couponVo)
    {
        this.couponVo = couponVo;
    }
}
