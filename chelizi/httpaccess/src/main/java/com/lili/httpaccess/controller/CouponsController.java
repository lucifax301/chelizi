package com.lili.httpaccess.controller;

import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.coupon.service.CouponService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/v1/coupons")
public class CouponsController {
    @Autowired
    private CouponService couponService;

    private Logger log = Logger.getLogger(CouponsController.class);

    /**
     * 学员获取优惠劵信息
     *
     * @param userId
     * @param userType
     * @param pageNo
     * @param couponType
     * @param timestamp
     * @param sign
     * @param headers
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object getAllCoupons(@RequestParam String userId, @RequestParam String userType,
                                @RequestParam String pageNo, @RequestParam String couponType,
                                @RequestParam String timestamp, @RequestParam String sign,
                                @RequestHeader HttpHeaders headers) {
        ReqResult r = new ReqResult();
        try {
            String tokenId = headers.getFirst(ReqConstants.TOKEN);
            long sid = Long.parseLong(userId);
            int pNo = Integer.parseInt(pageNo);

            boolean isUsed = false;
            byte a = Byte.parseByte(couponType);
            if (a != ReqConstants.COUPON_TYPE_UNUSED) {
                isUsed = true;
            }

            boolean isValid = true;
            r = couponService.getAllStudentCoupons(sid, pNo, isUsed, isValid, tokenId);

        } catch (Exception e) {
            log.error("controller: coupons get student coupon failed=" + e.getMessage(), e);
            r.setCode(ResultCode.ERRORCODE.EXCEPTION);
            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }

        return r.getResult();
    }

    /**
     * 支付现约单时查看可用优惠券
     * @param userId
     * @param courseId
     * @param coursePrice
     * @param courseCount
     * @param timestamp
     * @param shuttleFee
     * @param insurance
     * @param sign
     * @param headers
     * @return
     */
    @RequestMapping(value = "/recommend", method = RequestMethod.GET)
    public Object recommendCoupons(@RequestParam String userId, @RequestParam String courseId,
                                   @RequestParam String coursePrice, @RequestParam String courseCount, @RequestParam String timestamp,
                                   @RequestParam String shuttleFee, @RequestParam String insurance, @RequestParam String sign,
                                   @RequestHeader HttpHeaders headers) {
        ReqResult r = new ReqResult();
        try {
            long studentId = Long.parseLong(userId);
            int iCoursePrice = Integer.parseInt(coursePrice);
            int iCourseCount = Integer.parseInt(courseCount);
            int iShuttleFee = Integer.parseInt(shuttleFee);
            int iInsurance = Integer.parseInt(insurance);
            r = couponService.getSuitableCouponsForPay(studentId, courseId, iCoursePrice, iCourseCount, iShuttleFee, iInsurance);

        } catch (Exception e) {
            log.error("controller: coupons get student coupon failed=" + e.getMessage(), e);
            r.setCode(ResultCode.ERRORCODE.EXCEPTION);
            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }

        return r.getResult();
    }

    /**
     * 报名支付报名费时查询可用优惠券
     * @param userId
     * @param userType
     * @param orderId
     * @param orderType
     * @param orderPrice
     * @param timestamp
     * @param sign
     * @return
     */
    @RequestMapping(value = "/recommend2", method = RequestMethod.GET)
    public Object recommendCoupons2(@RequestParam String userId, @RequestParam String userType,
                                    @RequestParam String orderId, @RequestParam String orderType, @RequestParam(required = false) String orderPrice,
                                    @RequestParam String timestamp, @RequestParam String sign) {
        ReqResult r = new ReqResult();
        try {
            r = couponService.getSuitableCouponsForPay2(userId, userType, orderId, orderType, orderPrice);

        } catch (Exception e) {
            log.error("controller: coupons get student coupon failed=" + e.getMessage(), e);
            r.setCode(ResultCode.ERRORCODE.EXCEPTION);
            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }

        return r.getResult();
    }

    @RequestMapping(value = "/obtainCouponByPhoneNum", method = RequestMethod.GET)
    public Object obtainCouponByPhoneNum(@RequestParam String phoneNum, @RequestParam String couponTmpId) {

        ReqResult r = new ReqResult();
        try {
            r = couponService.obtainCouponByPhone(phoneNum, couponTmpId);
        } catch (Exception e) {
            log.error("controller: obtain coupon failed=" + e.getMessage(), e);
            r.setCode(ResultCode.ERRORCODE.EXCEPTION);
            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }

        return r.getResult();
    }

    /**
     * 通过cdKey换取优惠券
     *
     * @param userId   用户id
     * @param userType 用户类型,1=教练,2=学员
     * @param cdKey    卡券code
     * @param keyType  //卡券类型,1=QQ卡券
     * @return
     */
    @RequestMapping(value = "/exchangeCouponByCdKey", method = RequestMethod.POST)
    public Object exchangeCouponByCdKey(
            @RequestParam String userId,//用户id
            @RequestParam String userType,//用户类型,1=教练,2=学员
            @RequestParam String cdKey,//卡券code
            @RequestParam String keyType //卡券类型,1=QQ卡券
    ) {
        ReqResult r = new ReqResult();
        try {
            long studentId = Long.parseLong(userId);
            int type = Integer.parseInt(keyType);
            //TODO 兑换优惠券
            if("深圳南山半马".equals(cdKey)){  //深圳南山半程马拉松送优惠券活动
            	r = couponService.obtainCouponNsbm(userId);
            }else{
            	r = couponService.exchangeCouponByCdKey(studentId, cdKey, type);
            }
           
        } catch (Exception e) {
            log.error("controller: exchange coupon by cdKey failed!", e);
            r.setCode(ResultCode.ERRORCODE.EXCEPTION);
            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }
        return r.getResult();
    }

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    public Object getOne(@RequestParam String userId, @RequestParam String userType, @RequestParam String couponId, @RequestParam String timestamp,
                         @RequestParam String sign, @RequestHeader HttpHeaders headers) {
        ReqResult r = new ReqResult();
        try {
            long studentId = Long.parseLong(userId);
            long stuCouponId = Long.parseLong(couponId);
            r = couponService.getStuCouponDetail(studentId, stuCouponId);
        } catch (Exception e) {
            log.error("controller: coupons get student coupon failed=" + e.getMessage(), e);
            r.setCode(ResultCode.ERRORCODE.EXCEPTION);
            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }

        return r.getResult();
    }
}
