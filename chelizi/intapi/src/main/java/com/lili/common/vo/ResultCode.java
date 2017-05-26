package com.lili.common.vo;

import java.util.HashMap;
import java.util.Map;

public class ResultCode
{
	public static Map<String,String> codeInfo=new HashMap<String,String>();
	public static String getCodeInfo(String code){
		  return codeInfo.get(code);
	}

    public class RESULTKEY {
        public static final String CODE="code";
        public static final String MSGKEY="msgKey";
        public static final String MSGINFO="msgInfo";
        public static final String DATA="data";
    }
    public class ERRORCODE {
        public static final String SUCCESS="0";
        public static final String NEEDLOGIN="1";
        public static final String NOAUTH="2";
        public static final String PARAMERROR="3";
        public static final String FAILED="4";
        public static final String EXCEPTION="5";

        public static final String MOBILE_NUMBER_ERROR="10";
        public static final String AUTHCODE_ERROR="11";
        public static final String IDCARD_NUMBER_ERROR="12";
        public static final String PASSWORD_EMPTY_ERROR="13";
        public static final String PASSWORD_ERROR="14";
        public static final String NO_USER="15";
        public static final String COACH_CARD_ERROR="16";
        public static final String USER_EXIST="17";
        public static final String MOBILE_EXIST="18";
        public static final String IDCARD_NUMBER_EXIST="19";
        public static final String COACH_CARD_EXIST="20";
        public static final String DRIVE_CARD_EXIST="21";
        public static final String CLASS_PLAN_TIME_ERROR="22";
        public static final String SCORE_ZERO ="23";
        public static final String USER_LOCKED ="24";
        public static final String CONTENT_EMPTY_ERROR ="25";
        public static final String NO_VIP_USER ="26";
        public static final String CARNO_IS_EXIST = "27";
        public static final String CARNO_IS_ILLEGAL = "28";
        public static final String AUTO_PAYED = "29";
        public static final String OPENID_EXIST = "30";
        public static final String CAR_DRIVE_ERROR = "31";
        public static final String CARNO_LIST_IS_EXIST = "32";
        public static final String CAR_IS_LIMIT = "32";
        public static final String CARNO_IS_ILLEGAL_TWO = "32";
        public static final String VERSION_IS_LOW = "33";
        public static final String CAR_IS_NOT_CORRECT = "34";
        public static final String CAR_CANNOT_CHECK = "35";
        public static final String COACH_IS_NOT_IN_CITY = "36";
        public static final String NO_APPROPRIATE_CAR = "37";
        public static final String COACH_NOT_EXIST = "38";
        public static final String COACH_STUDENT_RELATIONSHIP_EXIST = "39";
        public static final String EXIST_COACH_RELATIONSHIP = "40";
        public static final String COACHCLASS_IS_NOT_IN_CITY = "41";

        public static final String IS_EXIT_STUDENT="3001";

        //订单错误码从5000到6000
        public static final String ORDER_NOTEXIST="5001";
        public static final String ORDER_CANCEL_ERROR="5002";
        public static final String ORDER_CANCEL_PAYERROR="5003";
        public static final String ORDER_FULL="5004";
        public static final String ORDER_PAY_SIGN_INVALID = "5005";
        public static final String ORDER_PAY_MONEY_NOTENOUGH = "5006";
        public static final String ORDER_CLOSED = "5007";
        public static final String ORDER_HASACCEPT="5008";
        public static final String ORDER_MONEY_MOTIFY = "5009";
        public static final String ORDER_COACH_FULL="5010";
        public static final String ORDER_CLASS_OCCUPY="5011";
        public static final String ORDER_PAYWAY_ISNULL = "5012";
        public static final String ORDER_NO_CANCEL = "5013";
        public static final String ORDER_NO_CLASS = "5014";
        public static final String ORDER_NO_BOOK = "5015";
        public static final String ORDER_NOT_COMMENT = "5016";
        public static final String ORDER_NOT_OPERATE = "5017";
        public static final String ORDER_NO_GIVE = "5018";
        public static final String ORDER_RESTRICT="5019";
        public static final String ORDER_NOT_SECOND="5020";
        public static final String ORDER_STU_BUZ="5021";
        public static final String ORDER_COACH_BUZ="5022";
        public static final String ORDER_STU_COUSER2_FINISHED="5023";
        public static final String ORDER_STU_COUSER3_FINISHED="5024";
        public static final String ORDER_HAS_ACCEPT="5025";
        public static final String VERSION_COACH_IS_LOW="5026";

        public static final String BANKNAME_IS_NULL="5025";
        public static final String BANKCARD_IS_NULL="5026";
        public static final String CARDNAME_IS_NULL="5027";
        public static final String USERID_IS_NULL="5028";
        public static final String USERTYPE_IS_NULL="5029";
        public static final String IS_BOUND_CARD="5030";
        public static final String TIME_IS_NULL="5031";
        public static final String MONEY_IS_NULL="5032";
        public static final String BANKCARD_IS_NOT_BOUND="5033";
        public static final String USERTYPE_IS_ERROR="5034";
        public static final String BALANCE_IS_INSUFFICIENT="5035";
        public static final String IS_NOT_BOUND_CARD="5036";
        public static final String PAGE_NO_IS_NULL="5037";
        public static final String PAGE_SIZE_IS_NULL="5038";
        public static final String DEPOSIT_LIST_IS_EMPTY="5039";
        public static final String MONEY_IS_ERROR="5040";
        public static final String DEPOSIT_IS_LIMIT="5041";
        public static final String ORDER_COACH_ARRANGEFUL="5042";
        public static final String BANKCARD_IS_ERROR="5043";
        public static final String ORDER_CLASSTIMEOUT="5044";
        public static final String PASSWORD_IS_NULL="5045";
        public static final String ACCOUNT_IS_STOP="5046";
        public static final String ORDER_NO_PRIVILEGE="5047";
        public static final String COACH_NO_AUTH="5048";
        
        

        public static final String PAY_VALUE_IS_NOT_CORRECT="5050";
        public static final String PAY_SIGNUP_IS_NOT_STUDENT="5051";
        public static final String PAY_MONEY_IS_NOT_ENOUGH="5052";
        public static final String PAY_ORDER_HAVE_PAY="5053";
        public static final String PAY_IS_NOT_SET_PW="5054";
        public static final String ORDER_TIMEOUT_CANCEL="5055";

        public static final String HAS_REGIST_PHONE="5056";
        public static final String IS_LILI_STUDENT="5057";
        public static final String ORDER_COUPON_CANNOTUSE="5058";
        public static final String ORDER_LOCK="5059";
        public static final String PW_ERROR_FIVE_OVER="5060";
        public static final String TIME_OUT_CLASS="5061";
        public static final String ORDER_IS_NOT_PAY="5062";
        public static final String ORDER_IS_NOT_PASE="5063";
        public static final String ORDER_IS_NOT_PASE_COURSER_ONE="5064";
        public static final String USER_IS_NOT_BM="5065";//如果使用该返回码，客户端将直接跳转到报名引导界面，请注意使用
        public static final String TEMP_NAME_IS_EIXT="5066";
        public static final String MONEY_IS_LESS="5067";
        public static final String MONEY_IS_MORE="5068";
        public static final String RECEIVE_EQUAL_SEND="5069";
        public static final String ORDER_SYSTEM_ERROR="5070";
        public static final String ORDER_NOT_BOUND="5071";
        public static final String HAS_ENROLL_INFO="5072";
        public static final String OVER_LIMIT_NUM="5073";
        public static final String ORDER_IS_CLOSE="5074";
        public static final String IS_EXIT_BOUND="5075";
        public static final String IS_EXIT_OPENID="5076";
        

        public static final String STUDENT_STATE__NOT_SUPPORT = "6001";
        public static final String BBS_IS_NOT_EXIT="6002";
        public static final String BBS_IS_NOT_USER="6003";
        public static final String BBS_IS_PRAISE="6004";
        public static final String BBS_IS_INFORM="6005";
        public static final String BBS_USER_IS_BLACK="6006";
        public static final String BBS_TOPIC_IS_DELETE="6007";
        public static final String BBS_CONTENT_IS_OVER_LENGTH="6008";
        public static final String BBS_OPEN_CONTENT_ERROR="6009";
        public static final String BBS_REPLY_CONTENT_IS_OVER_LENGTH="6010";
        public static final String BBS_REPLY_CONTENT_IS_ENPTY="6011";
        public static final String BBS_CONTENT_IS_ENPTY="6012";

        public static final String ENROLL_STAGE_ERROR_APPLY = "7001";
        public static final String ENROLL_CHECKOUT_NO_SCHOOL = "7002";

        //优惠券错误码 8000-8999
        public static final String COUPON_UNFOUND = "8002";
        public static final String COUPON_CONDITION_LIMIT = "8003";
        public static final String COUPON_TICKET_UNFOUND = "8004";//未找到所输入的卡券
        public static final String COUPON_TICKET_HAD_USE = "8005";//当前所输入的卡券已被使用
        public static final String COUPON_MOBILE_HAD_USE = "8007";//当前所输入的手机号已被使用
        public static final String COUPON_TICKET_INVALID = "8006";//当前所输入的卡券无效
        public static final String CHARGE_DISCOUNT_UNFOUND = "8007";//没有找到充值优惠
        public static final String VIP_COUPON_UNFOUND = "8008";//没有找到优惠券赠送
        public static final String COUPON_IS_ENROLL_LIMIT = "8009";
        public static final String COUPON_IS_USE_LIMIT = "8010";
        public static final String COUPON_IS_GET_LIMIT = "8011";
        
        //充值送错误吗 9000-9999
        public static final String RECHARGE_NEED_MOBILE="9000";
        public static final String RECHARGE_NEED_PLAN="9001";
        public static final String RECHARGE_AREAD_VIP="9002";
        public static final String RECHARGE_AREAD_STU="9003";
        public static final String RECHARGE_NOT_LILISTU="9004";
        public static final String RECHARGE_PLAN_EXIST="9005";
        public static final String RECHARGE_COMPANY_EXIST="9006";
        public static final String RECHARGE_GEAR_BEYOND="9007";
        public static final String RECHARGE_AREADY_VERIFY="9008";
        public static final String RECHARGE_ENROLL_CANNOTUSE="9009";
        public static final String RECHARGE_END_PLAN="9010";
        public static final String SUB_ENROLL_PURPOSE="9011";
        
    }
    public class ERRORINFO {
        public static final String SUCCESS="操作成功";
        public static final String NEEDLOGIN="该操作需要登录";
        public static final String NOAUTH="您没有该项操作的权利";
        public static final String PARAMERROR="操作参数错误，请更正后重试";
        public static final String FAILED="操作失败，请重试";
        //public static final String EXCEPTION="操作出现一个错误，请联系管理员解决";
        public static final String EXCEPTION="网络连接失败";

        public static final String MOBILE_NUMBER_ERROR="手机号码错误";
        public static final String AUTHCODE_ERROR="验证码错误";
        public static final String IDCARD_NUMBER_ERROR="身份证号码错误";
        public static final String PASSWORD_EMPTY_ERROR="密码不能为空";
        public static final String PASSWORD_ERROR="密码错误";
        public static final String NO_USER="用户不存在";
        public static final String COACH_CARD_ERROR="教练证号码错误";
        public static final String USER_EXIST="用户已存在";
        public static final String MOBILE_EXIST="该手机号已被注册，不允许重复注册";
        public static final String IDCARD_NUMBER_EXIST="该身份证已被注册，不允许重复注册";
        public static final String COACH_CARD_EXIST="该教练证已被注册，不允许重复注册";
        public static final String DRIVE_CARD_EXIST="该行驶证已被注册，不允许重复注册";
        public static final String CLASS_PLAN_TIME_ERROR="排班时间设置错误";
        public static final String SCORE_ZERO ="评分不能为空！";
        public static final String USER_LOCKED ="用户已锁定！";
        public static final String CONTENT_EMPTY_ERROR ="请提交修改内容！";
        public static final String NO_VIP_USER ="不存在大客户信息";
        public static final String CARNO_IS_EXIST = "该车牌号已被注册，不允许重复注册";
        public static final String CARNO_IS_ILLEGAL = "车牌号非法，请更正再注册";
        public static final String CARNO_IS_ILLEGAL_TWO = "请输入正确的车牌号码";
        public static final String AUTO_PAYED = "订单已自动支付成功";
        public static final String OPENID_EXIST = "该手机号已与其他账号绑定，请输入其它手机号";
        public static final String CAR_DRIVE_ERROR = "修改信息与已存在信息不匹配，请更正再提交";
        public static final String CARNO_LIST_IS_EXIST = "您已经添加过该车辆，不能重复添加哦。";
        public static final String CAR_IS_LIMIT = "您添加的车辆已达上限！";
        public static final String VERSION_IS_LOW = "您目前的软件版本过低，升级后才能享受充值优惠！";
        public static final String CAR_IS_NOT_CORRECT = "保存失败，该车牌号在平台已有记录，且您录入的车辆信息与之不符。";
        public static final String CAR_CANNOT_CHECK = "车辆信息与已有驾校车辆信息不一致，无法通过审核。";
        public static final String COACH_IS_NOT_IN_CITY = "您所在的城市暂未开通服务，我们正在努力开拓中，敬请期待。";
        public static final String COACHCLASS_IS_NOT_IN_CITY = "您所在的城市暂未开通计时排班服务，我们正在努力开拓中，敬请期待。";
        public static final String VERSION_COACH_IS_LOW = "您目前的软件版本过低，请升级才能接单！";
        public static final String COACH_NOT_EXIST = "教练不存在！";
        public static final String COACH_STUDENT_RELATIONSHIP_EXIST = "你已经与该教练绑定，无需重新绑定！";
        public static final String EXIST_COACH_RELATIONSHIP = "你目前已经与{1}教练绑定，需要解绑之后才能与新教练进行绑定！";
        
        public static final String IS_EXIT_STUDENT = "该学员已存在您的学员列表，无须再次导入";
        public static final String NO_APPROPRIATE_CAR = "亲爱的学员，您预约教练没有与您匹配的教练车，请换一个教练吧~";


        public static final String ORDER_NOTEXIST="订单不存在";
        public static final String ORDER_CANCEL_ERROR="订单取消错误";
        public static final String ORDER_CANCEL_PAYERROR="订单已取消，退款错误，请联系管理员。";
        public static final String ORDER_FULL="该班次已经约满，请约其他班次。";
        public static final String ORDER_PAY_SIGN_INVALID = "订单支付参数校验失败，可能被篡改";
        public static final String ORDER_PAY_MONEY_NOTENOUGH = "订单支付失败，余额不足";
        public static final String ORDER_CLOSED = "订单已关闭，请重新支付";
        public static final String ORDER_HASACCEPT="此单当前无法接单";
        public static final String ORDER_MONEY_MOTIFY = "此单价格被 修改";
        public static final String ORDER_PAYWAY_ISNULL = "支付方式没设置";
        public static final String ORDER_COACH_FULL="对不起，教练刚接其他单";
        public static final String ORDER_CLASS_OCCUPY="对不起，该班次已有学生预约";
        public static final String ORDER_NO_CANCEL = "对不起，该订单已经生效或者已取消";
        public static final String ORDER_NO_CLASS = "对不起，该课程未开始或者已缺课";
        public static final String ORDER_NO_BOOK = "对不起，该班次已经上课";
        public static final String ORDER_NOT_COMMENT = "对不起，该订单目前不能评价";
        public static final String ORDER_NOT_OPERATE = "对不起,该订单不能进行当前操作";
        public static final String ORDER_NO_GIVE = "请先支付或者取消此前有效订单后才能下单";
        public static final String ORDER_RESTRICT="因为其他订单未处理完毕，不能进行当前订单操作。";
        public static final String ORDER_NOT_SECOND="对不起，您已经预约了该课程";
        public static final String STUDENT_STATE__NOT_SUPPORT ="当前状态不支持该操作";
        public static final String ORDER_STU_BUZ="对不起，学员此时段忙";
        public static final String ORDER_COACH_BUZ="对不起，教练此时段忙";
        public static final String ORDER_STU_COUSER2_FINISHED="您的科目二技能已达标，无需继续预约学习";
        public static final String ORDER_STU_COUSER3_FINISHED="您的科目三技能已达标，无需继续预约学习";
        public static final String ORDER_COACH_ARRANGEFUL="对不起，教练排班时间冲突";
        public static final String ORDER_STUDENT_ARRANGEFUL="对不起，学员排班时间冲突";
        public static final String ORDER_CLASSTIMEOUT="课时时长超过最大限制";
        public static final String ORDER_ERROR="系统发生错误，接单失败";
        public static final String ORDER_NO_PRIVILEGE="此教练不能接所选课程";
        public static final String COACH_NO_AUTH="教练没有认证";
        public static final String ORDER_HAS_ACCEPT="非常抱歉，接单失败，该订单已被别的教练接单或学员已取消，请选择其他订单";
        public static final String ORDER_SYSTEM_ERROR="系统发生错误";
        public static final String ORDER_NOT_BOUND="没有在微信公众账号关注您，将收不到您的排班信息，是否短信通知学员？";
        public static final String HAS_ENROLL_INFO="您已报名，请勿重复报名";
        public static final String OVER_LIMIT_NUM="一次课程最多只能选择4位学员哦。";
        public static final String ORDER_IS_CLOSE="该排班已关闭";
        public static final String IS_EXIT_BOUND="该学员已存在您的学员列表中，无需再次导入。";
        public static final String IS_EXIT_OPENID="该微信号已绑定其他手机号。";

        public static final String BANKNAME_IS_NULL="开户行名称为空";
        public static final String BANKCARD_IS_NULL="银行卡号为空";
        public static final String CARDNAME_IS_NULL="持卡人姓名为空";
        public static final String USERID_IS_NULL="用户ID为空";
        public static final String USERTYPE_IS_NULL="用户类型为空";
        public static final String IS_BOUND_CARD="用户已绑定该银行卡";
        public static final String TIME_IS_NULL="日期为空";
        public static final String MONEY_IS_NULL="金额为空";
        public static final String BANKCARD_IS_NOT_BOUND="银行卡审核为7个工作日内，请耐心等待";
        public static final String USERTYPE_IS_ERROR="用户类型不正确";
        public static final String BALANCE_IS_INSUFFICIENT="账户余额不足";
        public static final String IS_NOT_BOUND_CARD="用户未绑定任何银行卡";
        public static final String PAGE_NO_IS_NULL="pageNo为空";
        public static final String PAGE_SIZE_IS_NULL="pageSize为空";
        public static final String DEPOSIT_LIST_IS_EMPTY="暂无提现记录";
        public static final String MONEY_IS_ERROR="输入的金额超过限制，请重新输入";
        public static final String DEPOSIT_IS_LIMIT="您的提现次数已用完，请下周再提现";
        public static final String BANKCARD_IS_ERROR="卡号错误";
        public static final String PASSWORD_IS_NULL="请设置支付密码后重试";
        public static final String ACCOUNT_IS_STOP= "您的账号状态存在异常，请退出登录";

        public static final String PAY_SIGNUP_IS_NOT_STUDENT="只有学员才能支付报名费";
        public static final String PAY_MONEY_IS_NOT_ENOUGH="余额不足";
        public static final String PAY_VALUE_IS_NOT_CORRECT="支付额度有误";
        public static final String PAY_IS_NOT_SET_PW="请先输入正确的钱包密码";
        public static final String PAY_ORDER_HAVE_PAY="订单已经支付过";
        public static final String ORDER_TIMEOUT_CANCEL="当前的订单临近上课时间不能取消，具体规则详情请参照帮助与反馈";
        public static final String HAS_REGIST_PHONE="已经被关联了";
        public static final String IS_LILI_STUDENT="已经是喱喱学员";
        public static final String ORDER_COUPON_CANNOTUSE="抱歉，该优惠券不适合当前使用";
        public static final String ORDER_LOCK="对不起，当前网络忙，请稍后再试";
        public static final String PW_ERROR_FIVE_OVER="支付密码错误次数已达5次，驾校账户已被锁定";
        public static final String TIME_OUT_CLASS="模版内有已过期时段，请修改后确认发布！";
        public static final String ORDER_IS_NOT_PAY="您有未支付的订单，请支付后再约课";
        public static final String ORDER_IS_NOT_PASE="您的报名还在受理中，报名成功后就可以约啦";
        public static final String ORDER_IS_NOT_PASE_COURSER_ONE="考过科目一，就可以约科目三啦";
        public static final String USER_IS_NOT_BM="请先报名";
        public static final String TEMP_NAME_IS_EIXT="已存在该模板名称";
        public static final String MONEY_IS_LESS="提现金额不能少于10元";
        public static final String MONEY_IS_MORE="提现金额不能超过5000元";
        public static final String RECEIVE_EQUAL_SEND="亲，分享不支持毛遂自荐哦";
        
        public static final String ENROLL_STAGE_ERROR_APPLY = "驾考报名失败，您已报名";
        public static final String ENROLL_CHECKOUT_NO_SCHOOL = "请先给学员分配驾校！";

        public static final String COUPON_UNFOUND = "没有找到优惠券";
        public static final String COUPON_CONDITION_LIMIT = "发券条件限制导致不可发券";
        public static final String COUPON_TICKET_UNFOUND = "未找到所输入的卡券";
        public static final String COUPON_TICKET_HAD_USE = "当前所输入的卡券已被使用";
        public static final String COUPON_MOBILE_HAD_USE = "当前所输入的手机号已被使用";
        public static final String COUPON_TICKET_INVALID = "当前所输入的卡券无效";
        public static final String CHARGE_DISCOUNT_UNFOUND = "没有找到充值优惠";
        public static final String VIP_COUPON_UNFOUND = "没有找到优惠券赠送";
        public static final String COUPON_IS_ENROLL_LIMIT = "很抱歉，新用户才能领取大礼包哦！";
        public static final String COUPON_IS_USE_LIMIT = "很抱歉，您来迟啦，名额已被抢光了，敬请期待下次活动！";
        public static final String COUPON_IS_GET_LIMIT = "您已经领取了同类型的活动券，不能重复领取！";

        public static final String RECHARGE_NEED_MOBILE="请填写手机号码";
        public static final String RECHARGE_NEED_PLAN="请选择有效的优惠方案";
        public static final String RECHARGE_END_PLAN="该优惠活动已经结束！";
        public static final String RECHARGE_AREAD_VIP="您已经是大客户，请勿重复申请";
        public static final String RECHARGE_AREAD_STU="您已是喱喱学员，请自行联系大客户经理（二维码发放人）人工处理。";
        public static final String RECHARGE_NOT_LILISTU="您选择的学员中有驾校学员";
        public static final String RECHARGE_PLAN_EXIST="该名称方案已经存在";
        public static final String RECHARGE_COMPANY_EXIST="该名称大客户已经存在";
        public static final String RECHARGE_GEAR_BEYOND="充送方案有效档位为0-10000元，请选择有效档位";
        public static final String RECHARGE_AREADY_VERIFY="记录已经审核，请勿重复审核";
        public static final String RECHARGE_ENROLL_CANNOTUSE="您已经报名，暂不享受该优惠哦！";
        
        public static final String SUB_ENROLL_PURPOSE="您已提交该报名意向，请勿重复提交！";
        
        /**
         * 社区
         */
        public static final String BBS_IS_NOT_EXIT="贴子已删除！";
        public static final String BBS_IS_NOT_USER="非发帖人不能操作删除！";
        public static final String BBS_IS_PRAISE="您已点赞该帖子！";
        public static final String BBS_IS_INFORM="您已举报该帖子！";
        public static final String BBS_USER_IS_BLACK="您已被关进小黑屋，不能发言！";
        public static final String BBS_TOPIC_IS_DELETE="该话题已删除，请重新选择！";
        public static final String BBS_CONTENT_IS_OVER_LENGTH="内容过长，请不要超过5000字！";
        public static final String BBS_CONTENT_IS_ENPTY="内容不能为空！";
        public static final String BBS_REPLY_CONTENT_IS_OVER_LENGTH="回复内容过长，请不要超过255字！";
        public static final String BBS_REPLY_CONTENT_IS_ENPTY="回复不能为空！";
        public static final String BBS_OPEN_CONTENT_ERROR="发帖异常！";
    }
    static {
    	codeInfo.put(ERRORCODE.CAR_CANNOT_CHECK, ERRORINFO.CAR_CANNOT_CHECK);
    	codeInfo.put(ERRORCODE.ORDER_NOTEXIST, ERRORINFO.ORDER_NOTEXIST);
    	codeInfo.put(ERRORCODE.ORDER_CANCEL_ERROR, ERRORINFO.ORDER_CANCEL_ERROR);
    	codeInfo.put(ERRORCODE.ORDER_CANCEL_PAYERROR, ERRORINFO.ORDER_CANCEL_PAYERROR);
    	codeInfo.put(ERRORCODE.ORDER_FULL, ERRORINFO.ORDER_FULL);
    	codeInfo.put(ERRORCODE.ORDER_HASACCEPT, ERRORINFO.ORDER_HASACCEPT);
    	codeInfo.put(ERRORCODE.ORDER_COACH_FULL, ERRORINFO.ORDER_COACH_FULL);
    	codeInfo.put(ERRORCODE.ORDER_CLASS_OCCUPY, ERRORINFO.ORDER_CLASS_OCCUPY);
    	codeInfo.put(ERRORCODE.ORDER_NO_CANCEL, ERRORINFO.ORDER_NO_CANCEL);
    	codeInfo.put(ERRORCODE.ORDER_NO_CLASS, ERRORINFO.ORDER_NO_CLASS);
    	codeInfo.put(ERRORCODE.ORDER_NO_BOOK, ERRORINFO.ORDER_NO_BOOK);
    	codeInfo.put(ERRORCODE.ORDER_NOT_COMMENT, ERRORINFO.ORDER_NOT_COMMENT);
    	codeInfo.put(ERRORCODE.ORDER_NOT_OPERATE, ERRORINFO.ORDER_NOT_OPERATE);
    	codeInfo.put(ERRORCODE.ORDER_NO_GIVE, ERRORINFO.ORDER_NO_GIVE);
    	codeInfo.put(ERRORCODE.ORDER_RESTRICT, ERRORINFO.ORDER_RESTRICT);
    	codeInfo.put(ERRORCODE.STUDENT_STATE__NOT_SUPPORT, ERRORINFO.STUDENT_STATE__NOT_SUPPORT);
    	codeInfo.put(ERRORCODE.ORDER_NOT_SECOND, ERRORINFO.ORDER_NOT_SECOND);
    	codeInfo.put(ERRORCODE.ORDER_STU_BUZ, ERRORINFO.ORDER_STU_BUZ);
    	codeInfo.put(ERRORCODE.ORDER_COACH_BUZ, ERRORINFO.ORDER_COACH_BUZ);

    	codeInfo.put(ERRORCODE.ORDER_MONEY_MOTIFY, ERRORINFO.ORDER_MONEY_MOTIFY);
    	codeInfo.put(ERRORCODE.BANKNAME_IS_NULL, ERRORINFO.BANKNAME_IS_NULL);
    	codeInfo.put(ERRORCODE.BANKCARD_IS_NULL, ERRORINFO.BANKCARD_IS_NULL);
    	codeInfo.put(ERRORCODE.CARDNAME_IS_NULL, ERRORINFO.CARDNAME_IS_NULL);
    	codeInfo.put(ERRORCODE.USERID_IS_NULL, ERRORINFO.USERID_IS_NULL);
    	codeInfo.put(ERRORCODE.USERTYPE_IS_NULL, ERRORINFO.USERTYPE_IS_NULL);
    	codeInfo.put(ERRORCODE.IS_BOUND_CARD, ERRORINFO.IS_BOUND_CARD);
    	codeInfo.put(ERRORCODE.TIME_IS_NULL, ERRORINFO.TIME_IS_NULL);
    	codeInfo.put(ERRORCODE.MONEY_IS_NULL, ERRORINFO.MONEY_IS_NULL);
    	codeInfo.put(ERRORCODE.BANKCARD_IS_NOT_BOUND, ERRORINFO.BANKCARD_IS_NOT_BOUND);
    	codeInfo.put(ERRORCODE.USERTYPE_IS_ERROR, ERRORINFO.USERTYPE_IS_ERROR);
    	codeInfo.put(ERRORCODE.BALANCE_IS_INSUFFICIENT, ERRORINFO.BALANCE_IS_INSUFFICIENT);
    	codeInfo.put(ERRORCODE.IS_NOT_BOUND_CARD, ERRORINFO.IS_NOT_BOUND_CARD);
    	codeInfo.put(ERRORCODE.PAGE_NO_IS_NULL, ERRORINFO.PAGE_NO_IS_NULL);
    	codeInfo.put(ERRORCODE.PAGE_SIZE_IS_NULL, ERRORINFO.PAGE_SIZE_IS_NULL);
    	codeInfo.put(ERRORCODE.DEPOSIT_LIST_IS_EMPTY, ERRORINFO.DEPOSIT_LIST_IS_EMPTY);
    	codeInfo.put(ERRORCODE.MONEY_IS_ERROR, ERRORINFO.MONEY_IS_ERROR);
    	codeInfo.put(ERRORCODE.DEPOSIT_IS_LIMIT, ERRORINFO.DEPOSIT_IS_LIMIT);
    	codeInfo.put(ERRORCODE.PASSWORD_IS_NULL, ERRORINFO.PASSWORD_IS_NULL);
    	codeInfo.put(ERRORCODE.ACCOUNT_IS_STOP, ERRORINFO.ACCOUNT_IS_STOP);
    	codeInfo.put(ERRORCODE.COUPON_CONDITION_LIMIT, ERRORINFO.COUPON_CONDITION_LIMIT);

    	codeInfo.put(ERRORCODE.PAY_VALUE_IS_NOT_CORRECT, ERRORINFO.PAY_VALUE_IS_NOT_CORRECT);
    	codeInfo.put(ERRORCODE.PAY_SIGNUP_IS_NOT_STUDENT, ERRORINFO.PAY_SIGNUP_IS_NOT_STUDENT);
    	codeInfo.put(ERRORCODE.PAY_MONEY_IS_NOT_ENOUGH, ERRORINFO.PAY_MONEY_IS_NOT_ENOUGH);
    	codeInfo.put(ERRORCODE.ORDER_COACH_ARRANGEFUL, ERRORINFO.ORDER_COACH_ARRANGEFUL);
    	codeInfo.put(ERRORCODE.PAY_ORDER_HAVE_PAY, ERRORINFO.PAY_ORDER_HAVE_PAY);
    	codeInfo.put(ERRORCODE.BANKCARD_IS_ERROR, ERRORINFO.BANKCARD_IS_ERROR);
    	codeInfo.put(ERRORCODE.PAY_IS_NOT_SET_PW, ERRORINFO.PAY_IS_NOT_SET_PW);
    	codeInfo.put(ERRORCODE.ORDER_CLASSTIMEOUT, ERRORINFO.ORDER_CLASSTIMEOUT);
    	codeInfo.put(ERRORCODE.ORDER_TIMEOUT_CANCEL,ERRORINFO.ORDER_TIMEOUT_CANCEL);
    	codeInfo.put(ERRORCODE.HAS_REGIST_PHONE,ERRORINFO.HAS_REGIST_PHONE);
    	codeInfo.put(ERRORCODE.IS_LILI_STUDENT,ERRORINFO.IS_LILI_STUDENT);
    	codeInfo.put(ERRORCODE.ORDER_COUPON_CANNOTUSE,ERRORINFO.ORDER_COUPON_CANNOTUSE);
    	codeInfo.put(ERRORCODE.RECHARGE_NEED_MOBILE,ERRORINFO.RECHARGE_NEED_MOBILE);
    	codeInfo.put(ERRORCODE.RECHARGE_NEED_PLAN,ERRORINFO.RECHARGE_NEED_PLAN);
    	codeInfo.put(ERRORCODE.RECHARGE_END_PLAN,ERRORINFO.RECHARGE_END_PLAN);
    	codeInfo.put(ERRORCODE.RECHARGE_AREAD_VIP,ERRORINFO.RECHARGE_AREAD_VIP);
    	codeInfo.put(ERRORCODE.RECHARGE_AREAD_STU,ERRORINFO.RECHARGE_AREAD_STU);
    	codeInfo.put(ERRORCODE.RECHARGE_NOT_LILISTU,ERRORINFO.RECHARGE_NOT_LILISTU);
    	codeInfo.put(ERRORCODE.RECHARGE_PLAN_EXIST,ERRORINFO.RECHARGE_PLAN_EXIST);
    	codeInfo.put(ERRORCODE.RECHARGE_COMPANY_EXIST,ERRORINFO.RECHARGE_COMPANY_EXIST);
    	codeInfo.put(ERRORCODE.RECHARGE_GEAR_BEYOND,ERRORINFO.RECHARGE_GEAR_BEYOND);
    	codeInfo.put(ERRORCODE.RECHARGE_AREADY_VERIFY,ERRORINFO.RECHARGE_AREADY_VERIFY);
    	codeInfo.put(ERRORCODE.RECHARGE_ENROLL_CANNOTUSE,ERRORINFO.RECHARGE_ENROLL_CANNOTUSE);
    	codeInfo.put(ERRORCODE.BBS_IS_NOT_EXIT,ERRORINFO.BBS_IS_NOT_EXIT);
    	codeInfo.put(ERRORCODE.BBS_IS_NOT_USER,ERRORINFO.BBS_IS_NOT_USER);
    	codeInfo.put(ERRORCODE.BBS_IS_PRAISE,ERRORINFO.BBS_IS_PRAISE);
    	codeInfo.put(ERRORCODE.BBS_IS_INFORM,ERRORINFO.BBS_IS_INFORM);
    	codeInfo.put(ERRORCODE.BBS_USER_IS_BLACK,ERRORINFO.BBS_USER_IS_BLACK);
    	codeInfo.put(ERRORCODE.BBS_TOPIC_IS_DELETE,ERRORINFO.BBS_TOPIC_IS_DELETE);
    	codeInfo.put(ERRORCODE.BBS_CONTENT_IS_OVER_LENGTH,ERRORINFO.BBS_CONTENT_IS_OVER_LENGTH);
    	codeInfo.put(ERRORCODE.BBS_OPEN_CONTENT_ERROR,ERRORINFO.BBS_OPEN_CONTENT_ERROR);
    	codeInfo.put(ERRORCODE.BBS_REPLY_CONTENT_IS_OVER_LENGTH,ERRORINFO.BBS_REPLY_CONTENT_IS_OVER_LENGTH);
    	codeInfo.put(ERRORCODE.BBS_REPLY_CONTENT_IS_ENPTY,ERRORINFO.BBS_REPLY_CONTENT_IS_ENPTY);
    	codeInfo.put(ERRORCODE.BBS_CONTENT_IS_ENPTY,ERRORINFO.BBS_CONTENT_IS_ENPTY);
    	codeInfo.put(ERRORCODE.SUB_ENROLL_PURPOSE,ERRORINFO.SUB_ENROLL_PURPOSE);
    }
}
