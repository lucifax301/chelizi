/**
 *
 */
package com.lili.coupon.service;

import com.lili.common.vo.ReqResult;
import com.lili.coupon.dto.CCondition;
import com.lili.coupon.dto.CStock;
import com.lili.coupon.dto.Coupon;
import com.lili.coupon.dto.StudentCoupon;
import com.lili.order.vo.OrderVo;

/**
 * @author linbo
 */
public interface CouponService {

    /**
     * 添加优惠券模板
     *
     * @param coupon
     * @return
     */
    ReqResult addCoupon(Coupon coupon, CStock stock);

    /**
     * 激活优惠券的使用
     *
     * @param coupon
     * @param isActive
     * @return
     */
    ReqResult activeCoupon(Coupon coupon, boolean isActive);

    /**
     * 更新优惠券模板信息
     *
     * @param coupon
     * @return
     */
    ReqResult updateCoupon(Coupon coupon, CStock stock);


    /**
     * 更新优惠券模板信息
     *
     * @param coupon
     * @return
     */
    ReqResult updateCoupon(Coupon coupon);

    /**
     * 批量更新优惠券库存,批量激活或者停止发放
     *
     * @param ids     优惠券列表
     * @param isexist 0=不生效，1=生效
     * @return
     */
    ReqResult updateStockBatch(String[] ids, int isexist);

    /**
     * 增加优惠券的限制条件
     *
     * @param condition
     * @return
     */
    ReqResult addCondition(CCondition condition);

    /**
     * 更新优惠券的限制条件
     *
     * @param condition
     * @return
     */
    ReqResult updateCondition(CCondition condition);

    /**
     * 使库存激活或者失效
     *
     * @param stockId
     * @param isExist
     * @return
     */
    boolean ableStock(int stockId, boolean isExist);

    /**
     * 创建优惠券库存
     *
     * @param eventTopic
     * @param total
     * @param couponTempId
     * @param user
     * @return
     */
    boolean creatCouponStock(String eventTopic, int total, int couponTempId, String user);

    /**
     * 通过cdKey换发优惠券给用户
     *
     * @param userId  用户id
     * @param cdKey   卡券code,以开头两位识别卡券的类型.<br/>
     *                10=内部券,11=QQ卡券
     * @param keyType
     * @return
     */
    ReqResult exchangeCouponByCdKey(long userId, String cdKey, int keyType);

    /**
     * 指定学员发送templateId的优惠券
     * @param templateId
     * @param studentId
     * @param jpush
     * @return
     */
    public ReqResult genStudentCouponAndNotify(String templateId, long studentId,boolean jpush);
    /**
     * 生成优惠券并通知客户端
     *
     * @param cStock
     * @param studentId
     */
    ReqResult genStudentCouponAndNotify(CStock cStock, long studentId);
    
    /**
     * 手动生成优惠券并通知客户端,不需要用户符合得到券的规则
     *
     * @param cStock
     * @param studentId
     */
    ReqResult genStudentCouponAndNotifyDirect(CStock cStock, long studentId);

    /**
     * 取得自己所有优惠券(分页)
     *
     * @param studentId
     * @param page
     * @param isUsed
     * @param isValid
     * @param tokenId
     * @return
     */
    ReqResult getAllStudentCoupons(long studentId, int page, boolean isUsed, boolean isValid, String tokenId);

    /**
     * 取单个优惠券详情
     *
     * @param studentId
     * @param coupnId
     * @return
     */
    ReqResult getStuCouponDetail(long studentId, long coupnId);

    /**
     * 取得本次订单中最合适的优惠券
     *
     * @param studentId
     * @param courseName
     * @return
     */
    ReqResult getSuitableCouponsForPay(long studentId, String courseName, int coursePrice, int courseCount,
                                       int shuttleFee, int insurance);

    ReqResult getSuitableCouponsForPay2(String userId, String userType,
                                        String orderId, String orderType, String orderPrice);

    /**
     * 取当前学员可用优惠券的数量
     *
     * @param student
     * @return
     */
    int getUseableCouponCount(long student);

    /**
     * 通过手机号码领取优惠券
     *
     * @param phoneNum
     * @param couponTmpId
     * @return
     */
    ReqResult obtainCouponByPhone(String phoneNum, String couponTmpId);
    
    /**
     * 深圳南山半马优惠券
     *
     * @param phoneNum
     * @param couponTmpId
     * @return
     */
    ReqResult obtainCouponNsbm(String userId);

    /**
     * 订单取消时恢复优惠券
     *
     * @param orderVo
     * @return
     */
    void recoverCoupon(OrderVo orderVo);

    /**
     * 更新库存(cms更新数据，激活库存)
     *
     * @param stock
     * @return
     */
    ReqResult updateCouponStock(CStock stock);

    /**
     * 使用优惠券()
     *
     * @param studentCoupon
     * @param orderVo
     * @param isUse         true:使用掉优惠券 false:测试使用优惠券
     * @return -1：优惠券不能使用 ; >=0:可以使用，使用后抵扣多少钱
     */
    int useCoupon(StudentCoupon studentCoupon, OrderVo orderVo, boolean isUse);

    /**
     * 更新学员优惠券,目前主要为支持优惠券注销
     *
     * @param studentCoupon
     * @return
     */
    ReqResult updateStudentCoupon(StudentCoupon studentCoupon);


    Coupon getCouponById(String coupontmpid);

    /**
     * 检查或使用优惠券()
     *
     * @param studentCouponId
     * @param orderVo
     * @param isUse           true:使用掉优惠券 false:测试使用优惠券
     * @return -1：优惠券不能使用 ; >=0:可以使用，使用后抵扣多少钱
     */
    int useCoupon(long studentCouponId, OrderVo orderVo, boolean isUse);

    /**
     * 直接使用优惠券，没有检查
     *
     * @param orderVo
     */
    void useCouponWithoutCheck(OrderVo orderVo);


    /**
     * 审核通过/审核不过 优惠券模板
     *
     * @param coupon
     * @return
     */
    ReqResult auditCoupon(Coupon coupon);

	String handleCouponTmp(String couponTmpId, String couponNum, Long studentId);
}
