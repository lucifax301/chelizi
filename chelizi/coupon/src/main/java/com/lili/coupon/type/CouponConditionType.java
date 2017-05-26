/**
 * 
 */
package com.lili.coupon.type;

/**
 * @author linbo
 *
 */
public class CouponConditionType
{
    public final static int CHECK_TIME = 0;               //检查时间param1:startTime; param2:endTime
    public final static int CHECK_REGION = 1;             //指定区域param1:region1,region2,region3...
    public final static int CHECK_COURSE = 2;             //指定科目param1:course1,course2,course3...
    public final static int CHECK_LIMIT = 3;              //限领张数param1:count; param2:couponTmpId
    public final static int CHECK_SHARE = 4;              //领过分享param1:shareType
}
