/**
 *
 */
package com.lili.common.constant;

/**
 * 交易类型	
 * A<--->B
 */
public enum MoneyChange {
    CHARGE(0, "充值"),
    WITHDRAW(1, "提现"),
    AWARD(2, "奖金"),
    SUBSIDY(3, "补贴"),
    FORFEIT(12, "罚款"),
    
    COUPON_FEE(14, "优惠券使用"),
    CHARGE_DISCOUNT(18, "充值送现金"),
    
    CLASS_FEE(5, "支付课时费"),
    CLASS_BROKERAGE(8, "课时费佣金"),
    CLASS_REFUND(19, "课时费退款"),
    CLASS_REFUND_BROKERAGE(20, "课时费佣金退款"),
    CLASS_ORDER_HANGUP(15, "挂起订单"),
    CLASS_ORDER_HANGUP_FEE(16, "支付挂起订单"),
    
    SIGNUP_FEE(7, "支付报名费"),
    SIGNUP_BROKERAGE(9, "报名费佣金"),
    SIGNUP_REFUND(10, "报名费退款"),
    SIGNUP_REFUND_BROKERAGE(11, "报名费佣金退款"),
    SIGNUP_SETTLEMENT(13, "报名费结算"),
    SIGNUP_PENALTY(17, "报名违约金"),
   
    
    EXAMPLACE_FEE(21,"约考场费"),
    EXAMPLACE_REFUND(22,"约考场费退款"),
    EXAMPLACE_RETURNAWARD(25,"预约考场返金额"),
    
    SIGNUP_WX_FEE(23,"驾校点评报名费"),
    INSURANCE_FEE(24,"平安保险费"),
    SIGNUP_WXCOACH_FEE(25,"喱喱教练公众号报名费")
    ;
    
    
//	/**
//	 * 教练充值
//	 */
//    COACH_CHARGE(101, "教练充值"),
//    /**
//     * 教练提现
//     */
//    COACH_WITHDRAW(102, "教练提现"),
//    
//    /**
//     * 学员充值
//     */
//    STU_CHAREG(201,"学员充值"),
//    /**
//     * 学员提现
//     */
//    STU_WITHDRAW(202,"学员提现"),
//    /**
//     * 学员支付课时费
//     */
//    STU_PAY_CLASS(203,"学员支付课时费"),
//    /**
//     * 学员支付报名费
//     */
//    STU_PAY_ENROLL(204,"学员支付报名费"),
//    
//    /**
//     * 301,"驾校充值"
//     */
//    SCHOOL_CHARGE(301,"驾校充值"),
//    /**
//     * 302,"驾校提现"
//     */
//    SCHOOL_WITHDRAW(302,"驾校提现"),
//    /**
//     * 303,"驾校支付课时费佣金"
//     */
//    SCHOOL_PAY_BROKERAGE_CLASS_ORDER(303,"驾校支付课时费佣金"),
//    /**
//     * 304,"驾校支付报名费佣金"
//     */
//    SCHOOL_PAY_BROKERAGE_ENROLL_ORDER(304,"驾校支付报名费佣金"),
//    /**
//     * 305,"驾校支付罚款"
//     */
//    SCHOOL_PAY_FORFEIT(305,"驾校支付罚款"),
//    
//    /**
//     * 401,"客服发放优惠券"
//     */
//    SER_GEN_COUPON(401,"客服发放优惠券"),
//    /**
//     * 402,"客服发放补贴"
//     */
//    SER_GEN_SUBSIDY(402,"客服发放补贴"),
//    /**
//     * 403,"客服发奖金给教练"
//     */
//    SER_AWARD_COACH(403,"客服发奖金给教练"),
//    /**
//     * 404,"客服发奖金给学员"
//     */
//    SER_AWARD_STU(404,"客服发奖金给学员"),
//    /**
//     * 405,"客服挂起超时订单"
//     */
//    SER_HUNGUP_CLASS_ORDER(405,"客服挂起超时课时订单"),
//    /**
//     * 406,"客服结算报名订单"
//     */
//    SER_SETTLE_ENROLL_ORDER(406,"客服结算报名订单"),
//    /**
//     * 407,"客服退费课时费"
//     */
//    SER_REFUND_CLASS_ORDER(407,"客服退费课时费"),
//    /**
//     * 408,"客服退费报名费"
//     */
//    SER_REFUND_ENROLL_ORDER(408,"客服退费报名费"),
//    /**
//     * 409,"客服结算课时费佣金"
//     */
//    SER_BROKERAGE_CLASS_ORDER(409,"客服结算课时费佣金"),
//    /**
//     * 410,"客服结算报名费佣金"
//     */
//    SER_BROKERAGE_ENROLL_ORDER(410,"客服结算报名费佣金"),
//    /**
//     * 411,"客服对驾校罚款"
//     */
//    SER_FORFEIT_SCHOOL(411,"客服对驾校罚款");
    

    private byte type;
    private String desc;

    /**
     *
     */
    MoneyChange(int type, String desc) {
        this.type = (byte) type;
        this.desc = desc;
    }

    /**
     * @return the type
     */
    public byte getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(byte type) {
        this.type = type;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
