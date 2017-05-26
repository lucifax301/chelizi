/**
 *
 */
package com.lili.coupon.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.lili.authcode.service.AuthcodeService;
import com.lili.common.constant.JpushConstant;
import com.lili.common.constant.OrderConstant;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.StringUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.coupon.condition.ConditionManager;
import com.lili.coupon.dto.CCondition;
import com.lili.coupon.dto.CStock;
import com.lili.coupon.dto.Coupon;
import com.lili.coupon.dto.StudentCoupon;
import com.lili.coupon.dto.Ticket;
import com.lili.coupon.manager.impl.CouponManager;
import com.lili.coupon.manager.impl.QQTicketManager;
import com.lili.coupon.qqticket.TickeyType;
import com.lili.coupon.service.CouponService;
import com.lili.coupon.type.CouponUseType;
import com.lili.coupon.type.NsbmCouponTmpId;
import com.lili.coupon.vo.CouponVo;
import com.lili.coupon.vo.RecommendCouponList;
import com.lili.order.vo.OrderVo;
import com.lili.school.manager.SchoolManager;
import com.lili.school.vo.EnrollOrderVo;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;
import com.lili.student.service.RechargeService;

/**
 * @author linbo
 */
public class CouponServiceImpl implements CouponService {
    private static Logger logger = LoggerFactory.getLogger(CouponService.class);

    @Autowired
    private AuthcodeService authcodeService;
    @Autowired
    protected RechargeService rechargeService;
    @Autowired
    private CouponManager couponManager;
    @Autowired
    private ConditionManager conditionManager;
    @Autowired
    private StudentManager studentManager;
    @Autowired
    private QQTicketManager qqTicketManager;

    @Autowired
    private SchoolManager schoolManager;

    @Autowired
    private RedisUtil redisUtil;

    @Resource(name = "jpushProducer")
    private DefaultMQProducer jpushProducer;

    private ReqResult validateCouponParam(Coupon coupon) {
        ReqResult rs = ReqResult.getSuccess();
        String name = coupon.getName();
        int indepentUse = coupon.getIndepentuse();
        int validityPeriod = coupon.getValidityperiod();
        int discount = coupon.getDiscount();
        if (StringUtil.isNullOrEmpty(name) //
                || (indepentUse != 0 && indepentUse != 1) //
                || validityPeriod < -1 //
                || (discount > 100 || discount < 0)//
                ) {
            rs = ReqResult.getParamError();
        }
        return rs;
    }

    /**
     * 添加优惠券模板
     *
     * @param coupon
     * @return
     */
    public ReqResult addCoupon(Coupon coupon, CStock stock) {
        ReqResult rs = ReqResult.getFailed();

        try {
            rs = validateCouponParam(coupon);
            if (rs.isSuccess()) {
                String coupontmpid = UUID.randomUUID().toString().replaceAll("-", "");
                coupon.setCoupontmpid(coupontmpid);
                stock.setCoupontempid(coupontmpid);
                /**
                 * 新增优惠券的时候,偶尔会出现已使用优惠券为Null
                 */
                stock.setHaveused(0);
                if (couponManager.addCoupon(coupon, stock)) {
                    rs = ReqResult.getSuccess();
                }
            }
        } catch (Exception e) {
            logger.error("add coupon fail. coupon:{}", coupon, e);
            rs.setCode(ResultCode.ERRORCODE.EXCEPTION);
            rs.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }
        return rs;
    }

    @Override
    public ReqResult activeCoupon(Coupon coupon, boolean isActive) {
        ReqResult rs = ReqResult.getFailed();

        try {
            String couponTmpId = coupon.getCoupontmpid();
            if (StringUtil.isNotNullAndNotEmpty(couponTmpId)) {
                CStock stock = couponManager.getStockByCouponTmpId(couponTmpId);
                if (null != stock) {
                    if (couponManager.activeStock(stock, isActive)) {
                        rs = ReqResult.getSuccess();
                    }
                } else {
                    rs.setCode(ResultCode.ERRORCODE.COUPON_UNFOUND);
                    rs.setMsgInfo(ResultCode.ERRORINFO.COUPON_UNFOUND);
                }
            } else {
                rs = ReqResult.getParamError();
            }
        } catch (Exception e) {
            logger.error("active coupon fail. coupon:{},isActive:{}", coupon, isActive, e);
            rs.setCode(ResultCode.ERRORCODE.EXCEPTION);
            rs.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }
        return rs;
    }

    @Override
    public ReqResult updateCoupon(Coupon coupon, CStock stock) {
        ReqResult rs = ReqResult.getFailed();
        try {
            rs = validateCouponParam(coupon);
            if (couponManager.updateCoupon(coupon, stock)) {
                rs = ReqResult.getSuccess();
            }
        } catch (Exception e) {
            logger.error("update coupon fail. coupon:{}", coupon, e);
            rs.setCode(ResultCode.ERRORCODE.EXCEPTION);
            rs.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }
        return rs;
    }

    private ReqResult valiateConditionParam(CCondition condition) {
        ReqResult rs = ReqResult.getSuccess();

        int type = condition.getType();
        String param1 = condition.getParam1();
        String param2 = condition.getParam2();

        switch (type) {
            case 0: {
                Date startDate = TimeUtil.parseDate(param1);
                Date endDate = TimeUtil.parseDate(param2);
                if (null == startDate || null == endDate) {
                    rs = ReqResult.getParamError();
                }
                break;
            }
            case 1: {
                if (StringUtil.isNullOrEmpty(param1)) {
                    rs = ReqResult.getParamError();
                }
                break;
            }
            case 2: {
                if (StringUtil.isNullOrEmpty(param1)) {
                    rs = ReqResult.getParamError();
                }
                break;
            }
            case 3: {
                try {
                    Integer.parseInt(param1);
                } catch (Exception e) {
                    rs = ReqResult.getParamError();
                }
                break;
            }
            case 4: {
                // TODO: 2016/五月/23 判断条件的参数
                break;
            }
            case 5: {
                // TODO: 2016/五月/23 判断条件的参数
                break;
            }
            default: {
                rs = ReqResult.getParamError();
            }
        }
        return rs;
    }

    @Override
    public ReqResult addCondition(CCondition condition) {
        ReqResult rs = ReqResult.getFailed();

        try {
            rs = valiateConditionParam(condition);
            if (rs.isSuccess()) {
                if (!couponManager.addCondition(condition)) {
                    rs = ReqResult.getFailed();
                }
            }
        } catch (Exception e) {
            logger.error("add coupon fail. condition:{}", condition, e);
            rs.setCode(ResultCode.ERRORCODE.EXCEPTION);
            rs.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }

        return rs;
    }

    @Override
    public ReqResult updateCondition(CCondition condition) {
        ReqResult rs = ReqResult.getFailed();
        try {
            rs = valiateConditionParam(condition);
            if (rs.isSuccess()) {
                if (!conditionManager.updateCondition(condition)) {
                    rs = ReqResult.getFailed();
                }
            }
        } catch (Exception e) {
            logger.error("update coupon fail. condition:{}", condition, e);
            rs.setCode(ResultCode.ERRORCODE.EXCEPTION);
            rs.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }
        return rs;
    }

    @Override
    public boolean ableStock(int stockId, boolean isExist) {
        // TODO: 2016/五月/23  使库存激活或者失效
        return false;
    }

    private int calcCouponValue(Coupon coupon, OrderVo order) {
        if (coupon == null || order == null) {
            return -1;
        }
        int useType = coupon.getType();
        int disCountValue = 0;
        switch (useType) {
            case CouponUseType.CASH_COUPON:
                int fee = order.getPrice() / order.getClzNum();
                float couponValue = coupon.getMoneyvalue();
                disCountValue = (int) (couponValue > fee ? fee : couponValue);
                break;
            case CouponUseType.CLASS_COUPON:
                disCountValue = order.getPrice() / order.getClzNum();
                break;
            case CouponUseType.DISCOUNT_COUPON:
                int fee1 = order.getPrice() / order.getClzNum();
                float lastFee = (100 - coupon.getDiscount()) * fee1 / 100;
                lastFee = lastFee < 0 ? 0 : lastFee;
                disCountValue = (int) (lastFee > fee1 ? fee1 : lastFee);
                break;
            default:
                break;
        }
        disCountValue = disCountValue > coupon.getLimitvalue() ? coupon.getLimitvalue() : disCountValue;
        return disCountValue;
    }

    /**
     * 验证优惠券是否还能使用
     *
     * @param studentCoupon
     * @param orderVo
     * @param coupon
     * @return
     */
    private boolean checkCoupon(StudentCoupon studentCoupon, OrderVo orderVo, Coupon coupon) {
        if (studentCoupon == null //
                || studentCoupon.getIsUsed() == 1//0=未使用，1=已使用
                || studentCoupon.getIsValid() == 0 //0=无效，1=有效
                || orderVo == null //
                || coupon == null //
                || coupon.getIsexist() == 0//0=不生效，1=生效
                ) {
            return false;
        }

        Student student = studentManager.getStudentInfo(studentCoupon.getStudentid());
        if (student != null) {
            //验证有效期
            if (!TimeUtil.isInDate(studentCoupon.getGettime(), studentCoupon.getExpiretime())) {
                return false;
            }
            //验证使用规则
            boolean isValid = conditionManager.checkExpressions(coupon.getUserule(), coupon.getCoupontmpid(), student, orderVo);
            return isValid;
        }
        return false;
    }

    @Override
    public boolean creatCouponStock(String eventTopic, int total, int couponTempId, String user) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ReqResult exchangeCouponByCdKey(long userId, String cdKey, int keyType) {
        // TODO: 2016/五月/17
        //参数验证
        if (StringUtil.isNullOrEmpty(cdKey) || cdKey.length() < 2) {
            return ReqResult.getParamError();
        }
        ReqResult result = ReqResult.getFailed();

        //检查是否已为注册学员
        Student student = studentManager.getStudentInfo(userId);
        if (null == student) {
            result.setCode(ResultCode.ERRORCODE.NO_USER);
            result.setMsgInfo(ResultCode.ERRORINFO.NO_USER);
            return result;
        }

        //确定卡券的类型
        TickeyType type = null;
        Ticket ticket = null;
        switch (cdKey.substring(0, 2)) {
            case "10": {//系统券
                type = TickeyType.CLZ;
                // TODO: 2016/五月/24 表示可以直接系统的发券
                break;
            }
            case "11": {//QQ卡券
                type = TickeyType.QQ;
                break;
            }
            default: {

            }
        }
        ticket = qqTicketManager.findQQTicketByCode(cdKey);

        if (null != ticket && null != type) {
            if (-1 == ticket.getStatus()) {
                result.setCode(ResultCode.ERRORCODE.COUPON_TICKET_INVALID);
                result.setMsgInfo(ResultCode.ERRORINFO.COUPON_TICKET_INVALID);
            } else if (0 == ticket.getStatus()) {
                String couponTmpId = ticket.getCouponTmpId();
                
                //判断学员是否已参加普惠
               Integer recordSum =  rechargeService.queryRechargeRecord(userId);
               if (recordSum > 0) {
            	   result.setCode(ResultCode.ERRORCODE.COUPON_IS_GET_LIMIT);
            	   result.setMsgInfo(ResultCode.ERRORINFO.COUPON_IS_GET_LIMIT);
            	   return result;
               }

                CStock cStock = null;
                // 检查优惠券的模板id是否正确
                if (StringUtil.isNotNullAndNotEmpty(couponTmpId)) {
                    cStock = couponManager.getStockByCouponTmpId(couponTmpId);
                }
                if (null == cStock || cStock.getIsexist() == 0) {
                    result.setCode(ResultCode.ERRORCODE.COUPON_UNFOUND);
                    result.setMsgInfo(ResultCode.ERRORINFO.COUPON_UNFOUND);
                } else {
                	//如果是系统券，判断cardid是否为空，分割内容1| 表示有关联券|第二张券ID
                	String secondCoupon = null;
                	String threeCouponTempId = null;
                	if (type == TickeyType.CLZ && !"".equals(ticket.getCardid()) && ticket.getCardid() != null) { 
                		String[] vlist = ticket.getCardid().split("\\|");
                		if(vlist.length > 0) {
                			if ("1".equals(vlist[0])) { //到了这里表示是组合使用，目前仅有千元大礼包活动
                				boolean register = ((student.getApplyexam() == 2 && student.getApplystate() == 100) || student.getApplyexam() > 2);
                				if (register) { //判断学员是否已报名，如果报名了，则不可兑换
                					 result.setCode(ResultCode.ERRORCODE.COUPON_IS_ENROLL_LIMIT);
                	                 result.setMsgInfo(ResultCode.ERRORINFO.COUPON_IS_ENROLL_LIMIT);
                	                 return result;
                				}
                				else {
                					cStock.setIsMore(vlist[0]);
                					if(vlist.length > 1) {
	                					if(vlist[1] != null && !"".equals(vlist[1])) {
	                						logger.info("**************************** secondCoupon : " + vlist[1]);
	                						secondCoupon= vlist[1];
	                						cStock.setSecCouponTempId(secondCoupon);
	                					}
                					}
                					if(vlist.length > 2) {
	                					if(vlist[2] != null && !"".equals(vlist[2])) {
	                						logger.info("**************************** secondCoupon : " + vlist[1]);
	                						threeCouponTempId= vlist[2];
	                						cStock.setThreeCouponTempId(threeCouponTempId);
	                					}
                					}
                				}
                			}
                		}
                	}
                	
                    result = genStudentCouponAndNotify(cStock, student.getStudentId());

                    if (result.isSuccess()) {//发券成功,去注销券
                        ReqResult tickeyResult = ReqResult.getFailed();
                        if (type == TickeyType.CLZ) {//系统券
                            // TODO: 2016/五月/24 表示可以使用系统的发券
                        	tickeyResult = qqTicketManager.useTicket(ticket, student);

                        } else if (type == TickeyType.QQ) {//QQ卡券
                            tickeyResult = qqTicketManager.useTicket(ticket, student);
                        } else {

                        }
                        if (!tickeyResult.isSuccess()) {
                            // FIXME: 2016/五月/25 当第三方平台注销失败时,优惠券的发送需要回滚
                        }
                    }
                }
            } else if (1 == ticket.getStatus()) {
                result.setCode(ResultCode.ERRORCODE.COUPON_TICKET_HAD_USE);
                result.setMsgInfo(ResultCode.ERRORINFO.COUPON_TICKET_HAD_USE);
            }
        } else {
            result.setCode(ResultCode.ERRORCODE.COUPON_TICKET_UNFOUND);
            result.setMsgInfo(ResultCode.ERRORINFO.COUPON_TICKET_UNFOUND);
        }


        if (!result.isSuccess()) {
            String code = result.getResult().get(ResultCode.RESULTKEY.CODE).toString();
            if (code.equals(ResultCode.ERRORCODE.COUPON_TICKET_HAD_USE)) {
                result.setMsgInfo("您输入的优惠码已经被使用");
            } else if (code.equals(ResultCode.ERRORCODE.COUPON_CONDITION_LIMIT)) {
                result.setMsgInfo("您输入的优惠码无效");
            } else if (code.equals(ResultCode.ERRORCODE.COUPON_CONDITION_LIMIT)
                    || code.equals(ResultCode.ERRORCODE.COUPON_TICKET_INVALID)
                    || code.equals(ResultCode.ERRORCODE.COUPON_TICKET_UNFOUND)) {
                result.setMsgInfo("您输入的优惠码无效");
            } else if (code.equals(ResultCode.ERRORCODE.COUPON_IS_GET_LIMIT)) {
            	result.setMsgInfo(ResultCode.ERRORINFO.COUPON_IS_GET_LIMIT);
            }  else {
                result.setMsgInfo("您输入的优惠码有误");
            }
        }


        return result;
    }
    
    /**
     * 生成一个Vo
     *
     * @param studentCoupon
     * @return
     */
    private CouponVo genCouponVo(StudentCoupon studentCoupon) {
        if (studentCoupon == null) {
            logger.error("studentCoupon is null when genCouponVo", new NullPointerException());
            return null;
        }
        CouponVo couponVo = new CouponVo();
        String couponTmpId = studentCoupon.getCoupontmpid();
        Coupon coupon = couponManager.getCouponById(couponTmpId);
        //增加统计已使用多少张
        if(coupon.getMaxNum() != null && coupon.getMaxNum() > 0){
        	Integer isUseNum = couponManager.selectIsUseCount(couponTmpId);
        	coupon.setUseNum(isUseNum);
        }
        if (coupon != null) {
            try {
                couponVo = BeanCopy.copyNotNull(coupon, couponVo);
                couponVo = BeanCopy.copyAll(studentCoupon, couponVo);
            } catch (Exception e) {
                logger.error("genCouponVo:" + studentCoupon, e);
            }
            return couponVo;
        }
        return null;
    }

    public ReqResult genStudentCouponAndNotify(CStock cStock, long studentId){
    	return genStudentCouponAndNotify(cStock, studentId,true);
    }
    
    public ReqResult genStudentCouponAndNotify(String templateId, long studentId,boolean jpush) {
    	CStock cStock = couponManager.getStockByCouponTmpId(templateId);
    	return genStudentCouponAndNotify(cStock, studentId,jpush);
    }
    	
    public ReqResult genStudentCouponAndNotify(CStock cStock, long studentId,boolean jpush) {
        ReqResult result = ReqResult.getFailed();
        try {
            if (cStock.getIsexist() == 1 && couponManager.useOneStock(cStock) > 0) {
                //到这一步说明库存检查通过
                String couponTempId = cStock.getCoupontempid();
                Coupon coupon = couponManager.getCouponById(couponTempId);
                Student student = studentManager.getStudentInfo(studentId);
                if (coupon != null) {
                    //检查生成规则
                    boolean genResult = conditionManager.checkExpressions(coupon.getGenrule(), couponTempId, student, null);
                    if (genResult) {
                    	//20160823增加优惠券数量发放功能
                    	//判断是否已领取同类型的活动礼包:根据优惠券模板extra字段判断，同类活动的extra一致，100的为本次三个活动
                    	if (coupon.getExtra() != null && !"".equals(coupon.getExtra())) {
                    		//根据extra查询优惠券模板表
                    		List<Coupon> couponList= couponManager.getCouponByExtra(coupon.getExtra());
                    		if (couponList != null && couponList.size() > 1) {
                    			for(Coupon isExitCou : couponList) {
                    				if (!"couponTempId".equals(isExitCou.getCoupontmpid())) {
                    					//判断是否已领取过相关活动券
                    					int isEixtCount = couponManager.selectIsExitCount(studentId, isExitCou.getCoupontmpid());
                    					if (isEixtCount > 0) {
                    						result.setCode(ResultCode.ERRORCODE.COUPON_IS_GET_LIMIT);
                                            result.setMsgInfo(ResultCode.ERRORINFO.COUPON_IS_GET_LIMIT);
                                            return result;
                    					}
                    				}
                    			}
                    		}
                    		
                    		//更新学员信息，标记已参与相关活动，屏蔽普惠
                    		if ( coupon.getIsUse() != null && coupon.getIsUse() ==0) {
                    			student.setVipPackageId("100");
                    			studentManager.updateStudent(student);
                    		}
                    	}
    					
                    	//查询生成规则的数量
                    	Integer oneNo = 0;
                    	Integer twoNo = 0;
                    	Integer threeNo = 0;
                    	if ("1".equals(cStock.getIsMore())) {//表示有关联券，判断优惠券发放数量
                    		oneNo = getCouponNum(coupon.getGenrule());
	                    	//第二张券数量
	                    	if (cStock.getSecCouponTempId() != null && !"".equals(cStock.getSecCouponTempId())) {
	                    		Coupon coupon2 = couponManager.getCouponById(cStock.getSecCouponTempId());
	                    		twoNo = getCouponNum(coupon2.getGenrule());
	                    	}
	                    	//第三张券数量
	                    	if (cStock.getThreeCouponTempId() != null && !"".equals(cStock.getThreeCouponTempId())) {
	                    		Coupon coupon3 = couponManager.getCouponById(cStock.getThreeCouponTempId());
	                    		threeNo = getCouponNum(coupon3.getGenrule());
	                    	}
                    	}
                    	
                        //生成了优惠券
                        StudentCoupon studentCoupon = new StudentCoupon();
                        StudentCoupon studentCouponTwo = new StudentCoupon();
                        StudentCoupon studentCouponThree = new StudentCoupon();
                        studentCoupon.setCoupontmpid(couponTempId);
                        
                        Date expireTime = null;

                        if (coupon.getValidityperiod() == -1) {
                            //有效期为-1时表示永久有效
                           // expireTime = TimeUtil.addDate(new Date(), 24 * 3600000 * 365 * 1000l);
                        	Calendar c = Calendar.getInstance();
            			    c.add(Calendar.YEAR, 10);
            			    expireTime = c.getTime();
                        } else if (coupon.getValidityperiod() == 0) {
                            //使用优惠券的过期时间
                            if (null != coupon.getExpireTime()) {
                                expireTime = coupon.getExpireTime();
                            } else {
                                expireTime = new Date();
                            }
                        } else {
                            //计算过期时间
                            expireTime = TimeUtil.addDate(new Date(), coupon.getValidityperiod() * 3600000l);
                            if (null != coupon.getExpireTime() && coupon.getExpireTime().getTime() < expireTime.getTime()) {
                                //当前优惠券有截止时间时,所发的优惠券过期时间不能超过截止时间
                                expireTime = coupon.getExpireTime();
                            }
                        }

                        studentCoupon.setExpiretime(expireTime);

                        studentCoupon.setGettime(new Date());
                        studentCoupon.setStockid(cStock.getStockid());
                        studentCoupon.setIsUsed((byte) 0);
                        studentCoupon.setIsValid((byte) 1);
                        studentCoupon.setOrderid("");
                        studentCoupon.setStudentid(studentId);
                        
                        
                        long couponid = 0;
                        List<StudentCoupon> studentCouponList = new ArrayList<StudentCoupon>();
                        if ("1".equals(cStock.getIsMore())) { //表示有多种券
                        	logger.info("**************************** First Coupon Num  : " + oneNo);
                        	studentCouponTwo.setGettime(new Date());
                        	studentCouponTwo.setStockid(cStock.getStockid());
                        	studentCouponTwo.setIsUsed((byte) 0);
                        	studentCouponTwo.setIsValid((byte) 1);
                        	studentCouponTwo.setOrderid("");
                        	studentCouponTwo.setStudentid(studentId);
                        	studentCouponTwo.setCoupontmpid(cStock.getSecCouponTempId());
                        	
                        	if (expireTime != null) {
                        		studentCoupon.setExpiretimestr((new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(expireTime));
                        		studentCouponTwo.setExpiretimestr((new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(expireTime));
                        		studentCouponThree.setExpiretimestr((new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(expireTime));
                        	}
                        	if (oneNo > 1) { //第一张券有多张情况
                        		for(int i = 0; i < oneNo; i++) {
                        			studentCouponList.add(studentCoupon);
                        		}
                        	}
                        	else { //第一张券只有一张，原始券，必须是有一张
                        		studentCouponList.add(studentCoupon);
                        	}
                        	if (twoNo > 0) { //第二张券有多张情况
                        		logger.info("****************************  Second Coupon Num  : " + twoNo);
                        		for(int j = 0; j < twoNo; j++) {
                        			studentCouponList.add(studentCouponTwo);
                            	}
                        	}
                        	else { //第二张券只有一张
                        		studentCouponList.add(studentCouponTwo);
                        	}
                        	if (threeNo > 0) {//第三张券必须是多张
                        		logger.info("****************************  Second Coupon Num  : " + twoNo);
                        		studentCouponThree.setGettime(new Date());
                        		studentCouponThree.setStockid(cStock.getStockid());
                        		studentCouponThree.setIsUsed((byte) 0);
                        		studentCouponThree.setIsValid((byte) 1);
                        		studentCouponThree.setOrderid("");
                        		studentCouponThree.setStudentid(studentId);
                        		studentCouponThree.setCoupontmpid(cStock.getThreeCouponTempId());
                        		
                        		for(int k = 0; k < threeNo; k++) {
                        			studentCouponList.add(studentCouponThree);
                        		}
                        	}
                        	couponid = couponManager.addStudentCouponList(studentCouponList);
                        }
                        else {
                        	couponid = couponManager.addStudentCoupon(studentCoupon);
                        }
                        if (couponid > 0) {
                        	if(jpush) {
	                            // 给客户端推送消息
	                            pushMsgToStudent(student, coupon, couponid);
                        	}
                            result = ReqResult.getSuccess();
                            result.setMsgInfo(coupon.getJpushmsg());
                            result.setData("money", coupon.getMoneyvalue());
                            result.setData("couponId", couponid);
                        } else {
                            //表示给学生添加失败
                        }
                    } else {
                        //如果没有生成成功，则库存恢复
                        couponManager.recoverStock(cStock.getStockid());

                        result.setCode(ResultCode.ERRORCODE.COUPON_CONDITION_LIMIT);
                        result.setMsgInfo(ResultCode.ERRORINFO.COUPON_CONDITION_LIMIT);
                    }
                } else {
                    result.setCode(ResultCode.ERRORCODE.COUPON_UNFOUND);
                    result.setMsgInfo(ResultCode.ERRORINFO.COUPON_UNFOUND);
                }
            } else {
                result.setCode(ResultCode.ERRORCODE.COUPON_TICKET_INVALID);
                result.setMsgInfo(ResultCode.ERRORINFO.COUPON_TICKET_INVALID);
            }
        } catch (Exception e) {
            logger.error("genStudentCouponAndNotify:" + cStock + "," + studentId, e);
            result.setCode(ResultCode.ERRORCODE.EXCEPTION);
        }

        return result;
    }

    public Integer getCouponNum(String expression) {
    	CCondition cCondition = null;
    	Integer num = 0;
    	 if (expression == null || expression.isEmpty()) {
             return num;
         }
         try {
			String toggle[] = expression.split("\\|");
			 String conditions[] = toggle[1].split(",");
			 for (int i = 0; i < conditions.length; i++) {
			     Integer conId = Integer.parseInt(conditions[i]);
			     cCondition = conditionManager.getConditionInfo(conId);
			     if (cCondition != null && cCondition.getType() ==(byte) 3) { //满足条件指定数量，且数量大于1时
			    	 if (cCondition.getParam1() != null && !"".equals(cCondition.getParam1()) &&  Integer.parseInt(cCondition.getParam1()) > 1) {
			    		 num = Integer.parseInt(cCondition.getParam1());
			    		 return num;
			    	 }
			     }
			 }
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return num;
	}

	@Override
    public ReqResult getAllStudentCoupons(long studentId, int page, boolean isUsed, boolean isValid, String tokenId) {
        ReqResult r = new ReqResult();
        if (!redisUtil.isExist(REDISKEY.STUDENT_TOKEN + studentId, tokenId)) {
            r.setCode(ResultCode.ERRORCODE.NEEDLOGIN);
            r.setMsgInfo(ResultCode.ERRORINFO.NEEDLOGIN);
            return r;
        }

        List<StudentCoupon> data = couponManager.getStudentAllCouponByPage(studentId, page, isUsed, isValid);
        List<CouponVo> dataVo = new ArrayList<CouponVo>();
        for (StudentCoupon coupon : data) {
            CouponVo couponVo = genCouponVo(coupon);
            if (couponVo != null) {
                dataVo.add(couponVo);
            }
        }
        r.setCode(ResultCode.ERRORCODE.SUCCESS);
        r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
        r.setData(dataVo);
        return r;

    }

    @Override
    public ReqResult getStuCouponDetail(long studentId, long coupnId) {
        ReqResult r = ReqResult.getSuccess();
        StudentCoupon stuCoupon = couponManager.getStudentCouponById(coupnId, studentId);
        CouponVo couponVo = genCouponVo(stuCoupon);
        r.setData(couponVo);
        return r;
    }

    @Override
    public ReqResult getSuitableCouponsForPay(long studentId, String courseId, int coursePrice, int courseCount,
                                              int shuttleFee, int insurance) {
        ReqResult reqResult = ReqResult.getSuccess();
        List<StudentCoupon> allCoupons = couponManager.getStudentAllCouponsCanUse(studentId);
        if (allCoupons == null || allCoupons.size() == 0) {
            return reqResult;
        }

        try {
            int maxValue = Integer.MIN_VALUE;
            CouponVo suitableCoupon = null;
            OrderVo orderVo = new OrderVo();
            orderVo.setCourseId(courseId);
            orderVo.setUnitPrice(coursePrice);
            orderVo.setPrice(coursePrice);
            orderVo.setTransPrice(shuttleFee);
            orderVo.setInsPrice(insurance);
            orderVo.setClzNum(courseCount);

            RecommendCouponList recommendCouponList = new RecommendCouponList();
            List<CouponVo> couponVos = new ArrayList<CouponVo>();
            recommendCouponList.setCouponVos(couponVos);
            for (StudentCoupon studentCoupon : allCoupons) {
                int value = useCoupon(studentCoupon, orderVo, false);
                if (value != -1) {
                    CouponVo couponVo = genCouponVo(studentCoupon);
                    if (value > maxValue) {
                        suitableCoupon = couponVo;
                        maxValue = value;
                    }

                    couponVo.setRealValue(value);
                    couponVos.add(couponVo);
                }
            }
            if (suitableCoupon != null) {
                recommendCouponList.setCouponVo(suitableCoupon);
                reqResult.setData(recommendCouponList);
                return reqResult;
            }
        } catch (Exception e) {
            logger.error("getSuitableCouponsForPay:" + studentId + "|courseName:" + courseId, e);
        }

        return reqResult;
    }

    /**
     * 只查询，没有使用。在支付的时候再真正使用该优惠券。
     */
    @Override
    public ReqResult getSuitableCouponsForPay2(String userId, String userType,
                                               String orderId, String orderType, String orderPrice) {
        ReqResult reqResult = ReqResult.getSuccess();
        long studentId = Long.parseLong(userId);

        Student student = studentManager.getStudentInfo(studentId);
        if (null == student) {
            return reqResult;
        }

//        String vipPackageId = student.getVipPackageId();
//        if (StringUtil.isNotNullAndNotEmpty(vipPackageId)) {
//            VipPackage vipPackage = studentVipService.getVipPackage(vipPackageId);
//            if (vipPackage.getIsDisableEnrollCoupon() == 1) {
//                //此优惠套餐不支持报名优惠券
//                return reqResult;
//            }
//        }

        List<StudentCoupon> allCoupons = couponManager.getStudentAllCouponsCanUse(studentId);
        if (allCoupons == null || allCoupons.size() == 0) {
            return reqResult;
        }
        //参数通过vo传递
        OrderVo orderVo = new OrderVo();
        orderVo.setOrderId(orderId);
        orderVo.setOtype(Integer.parseInt(orderType));
        orderVo.setStudentId(studentId);
        orderVo.setClzNum(1);//无意义，兼容之前的费用计算
        if (OrderConstant.OTYPE.ENROLL_ORDER == Integer.valueOf(orderType.trim())) {
            ReqResult tmp = schoolManager.getEnrollOrder(null, null, orderId);
            if (tmp != null && tmp.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)) {
                EnrollOrderVo eov = (EnrollOrderVo) tmp.getResult().get(ResultCode.RESULTKEY.DATA);
                orderVo.setPrice(eov.getPrice());
                orderVo.setCityId(eov.getCityId());//20160715添加城市校验功能
            }
        } else {
            orderVo.setPrice(Integer.parseInt(orderPrice));//其他订单暂时不需要查询
        }

        //推荐的优惠券列表
        RecommendCouponList recommendCouponList = new RecommendCouponList();
        List<CouponVo> couponVos = new ArrayList<CouponVo>();
        int maxValue = Integer.MIN_VALUE;
        //最佳优惠券
        CouponVo suitableCoupon = null;

        for (StudentCoupon studentCoupon : allCoupons) {
            String couponTmpId = studentCoupon.getCoupontmpid();
            Coupon coupon = couponManager.getCouponById(couponTmpId);
            //（1）首先根据优惠券使用条件过滤
            if (checkCoupon(studentCoupon, orderVo, coupon)) {
                //凡是符合券定义使用条件的都会查询出来，但要注意实际订单使用时，可能需要指定订单类型的券才可以，但目前是没有做区分！
                int value = calcCouponValue(coupon, orderVo);
                if (value != -1) {
                    CouponVo couponVo = genCouponVo(studentCoupon);
                    couponVo.setRealValue(value);
                    couponVos.add(couponVo);

                    if (value > maxValue) {
                        suitableCoupon = couponVo;
                        maxValue = value;
                    }
                }
            }
        }
        recommendCouponList.setCouponVos(couponVos);
        if (suitableCoupon != null) {
            recommendCouponList.setCouponVo(suitableCoupon);
            reqResult.setData(recommendCouponList);
            return reqResult;
        }

        return reqResult;
    }

    @Override
    public int getUseableCouponCount(long studentId) {
        return couponManager.getUseableCouponCount(studentId);
    }

    @Override
    public ReqResult obtainCouponByPhone(String phoneNum, String couponTmpId) {
        //参数验证
        if (StringUtil.isNullOrEmpty(phoneNum) || StringUtil.isNullOrEmpty(couponTmpId)) {
            return ReqResult.getParamError();
        }

        ReqResult result = ReqResult.getFailed();

        //检查是否已为注册学员
        Student student = studentManager.getStudentByPhoneNum(phoneNum);
        if (null == student) {
            result.setCode(ResultCode.ERRORCODE.NO_USER);
            result.setMsgInfo(ResultCode.ERRORINFO.NO_USER);
            return result;
        }

        // 检查优惠券的模板id是否正确
        CStock cStock = couponManager.getStockByCouponTmpId(couponTmpId);
        if (null == cStock || cStock.getIsexist() == 0) {
            result.setCode(ResultCode.ERRORCODE.COUPON_UNFOUND);
            result.setMsgInfo(ResultCode.ERRORINFO.COUPON_UNFOUND);
            return result;
        }

        //发券给学员
        result = genStudentCouponAndNotify(cStock, student.getStudentId());
        return result;
    }
    
    /**
     * 只针对南山半程马拉松送优惠券活动
     * @param userId
     * @return
     */
    @Override
    public ReqResult obtainCouponNsbm(String userId) {
    	ReqResult result = ReqResult.getFailed();
    	  
    	Date now=new Date();
    	Date enData=null;
    	try {
		    enData=new SimpleDateFormat("yyyy-MM-dd").parse("2016-12-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	if(now.getTime()>enData.getTime()){
    		  result.setCode(ResultCode.ERRORCODE.COUPON_TICKET_INVALID);
              result.setMsgInfo(ResultCode.ERRORINFO.COUPON_TICKET_INVALID);
              return result;
    	}
    	
        //参数验证
        if (StringUtil.isNullOrEmpty(userId) || StringUtil.isNullOrEmpty(userId)) {
            return ReqResult.getParamError();
        }

      

        //检查是否已为注册学员
        Student student = studentManager.getStudentInfo(Long.parseLong(userId));
        if (null == student) {
            result.setCode(ResultCode.ERRORCODE.NO_USER);
            result.setMsgInfo(ResultCode.ERRORINFO.NO_USER);
            return result;
        }
       

        // 检查优惠券的模板id是否正确
        List<String> couponTmpIds=new ArrayList<>();
        couponTmpIds.add(NsbmCouponTmpId.couponTmpId_300_bm);
        couponTmpIds.add(NsbmCouponTmpId.couponTmpId_25_2);
        couponTmpIds.add(NsbmCouponTmpId.couponTmpId_25_3);
        
        String ids="'"+NsbmCouponTmpId.couponTmpId_300_bm+"','"+NsbmCouponTmpId.couponTmpId_25_2+"','"+NsbmCouponTmpId.couponTmpId_25_3+"'";
        int num=couponManager.checkExistCoupon(userId,ids);
        if (num > 0) {
     	   result.setCode(ResultCode.ERRORCODE.COUPON_IS_GET_LIMIT);
     	   result.setMsgInfo(ResultCode.ERRORINFO.COUPON_IS_GET_LIMIT);
     	   return result;
        }

       
        for(int i=0;i<couponTmpIds.size();i++){
        	 CStock cStock = couponManager.getStockByCouponTmpId(couponTmpIds.get(i));
        	 if (null == cStock || cStock.getIsexist() == 0) {
                 result.setCode(ResultCode.ERRORCODE.COUPON_UNFOUND);
                 result.setMsgInfo(ResultCode.ERRORINFO.COUPON_UNFOUND);
                 break;
             }
        	 //发券给学员
        	 result = genStudentCouponAndNotifyNsbm(cStock, student.getStudentId());
        }

       
       
        return result;
    }
    
    /**
     * 南山半程马拉松送优惠券活动
     * @param userId
     * @return
     */
    public ReqResult genStudentCouponAndNotifyNsbm(CStock cStock, long studentId) {
        ReqResult result = ReqResult.getFailed();
        try {
            if (  (cStock.getIsexist() == 1 && NsbmCouponTmpId.couponTmpId_300_bm.equals(cStock.getCoupontempid()) && couponManager.useOneStock(cStock) > 0 ) 
            		||(cStock.getIsexist() == 1 && !NsbmCouponTmpId.couponTmpId_300_bm.equals(cStock.getCoupontempid()) && couponManager.useMoreStock(cStock,4) > 0 )
            		) {
                //到这一步说明库存检查通过
                String couponTempId = cStock.getCoupontempid();
                Coupon coupon = couponManager.getCouponById(couponTempId);
                Student student = studentManager.getStudentInfo(studentId);
                if (coupon != null) {
                    //检查生成规则
                    boolean genResult = conditionManager.checkExpressions(coupon.getGenrule(), couponTempId, student, null);
                    if (genResult) {

                        //生成了优惠券
                        StudentCoupon studentCoupon = new StudentCoupon();
                        studentCoupon.setCoupontmpid(couponTempId);
                        Date expireTime = null;

                        if (coupon.getValidityperiod() == -1) {
                            //有效期为-1时表示永久有效
                           // expireTime = TimeUtil.addDate(new Date(), 24 * 3600000 * 365 * 1000l);
                        	Calendar c = Calendar.getInstance();
            			    c.add(Calendar.YEAR, 10);
            			    expireTime = c.getTime();
                        } else if (coupon.getValidityperiod() == 0) {
                            //使用优惠券的过期时间
                            if (null != coupon.getExpireTime()) {
                                expireTime = coupon.getExpireTime();
                            } else {
                                expireTime = new Date();
                            }
                        } else {
                            //计算过期时间
                            expireTime = TimeUtil.addDate(new Date(), coupon.getValidityperiod() * 3600000l);
                            if (null != coupon.getExpireTime() && coupon.getExpireTime().getTime() < expireTime.getTime()) {
                                //当前优惠券有截止时间时,所发的优惠券过期时间不能超过截止时间
                                expireTime = coupon.getExpireTime();
                            }
                        }

                        studentCoupon.setExpiretime(expireTime);
                        studentCoupon.setGettime(new Date());
                        studentCoupon.setStockid(cStock.getStockid());
                        studentCoupon.setIsUsed((byte) 0);
                        studentCoupon.setIsValid((byte) 1);
                        studentCoupon.setOrderid("");
                        studentCoupon.setStudentid(studentId);
                        
                        
                        long couponid = 0;
                        List<StudentCoupon> studentCouponList = new ArrayList<StudentCoupon>();
                        if(!couponTempId.equals(NsbmCouponTmpId.couponTmpId_300_bm)){
                        	studentCoupon.setExpiretimestr((new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(expireTime));
                        	for(int k = 0; k < 4; k++) {
                    			studentCouponList.add(studentCoupon);
                    		}
                        	  couponid = couponManager.addStudentCouponList(studentCouponList);
                        }else{
                        	  couponid = couponManager.addStudentCoupon(studentCoupon);
                        }
                        
                        if (couponid > 0) {
	                        pushMsgToStudent(student, coupon, couponid);
                            result = ReqResult.getSuccess();
                            result.setMsgInfo(coupon.getJpushmsg());
                            result.setData("money", coupon.getMoneyvalue());
                            result.setData("couponId", couponid);
                        } else {
                            //表示给学生添加失败
                        }
                    } else {
                        //如果没有生成成功，则库存恢复
                    	if(couponTempId.equals(NsbmCouponTmpId.couponTmpId_300_bm)){
                    		 couponManager.recoverStock(cStock.getStockid());
                    	}else{
                    		 couponManager.recoverMoreStock(cStock.getStockid(),4);
                    	}

                        result.setCode(ResultCode.ERRORCODE.COUPON_CONDITION_LIMIT);
                        result.setMsgInfo(ResultCode.ERRORINFO.COUPON_CONDITION_LIMIT);
                    }
                } else {
                    result.setCode(ResultCode.ERRORCODE.COUPON_UNFOUND);
                    result.setMsgInfo(ResultCode.ERRORINFO.COUPON_UNFOUND);
                }
            } else {
                result.setCode(ResultCode.ERRORCODE.COUPON_TICKET_INVALID);
                result.setMsgInfo(ResultCode.ERRORINFO.COUPON_TICKET_INVALID);
            }
        } catch (Exception e) {
            logger.error("genStudentCouponAndNotify:" + cStock + "," + studentId, e);
            result.setCode(ResultCode.ERRORCODE.EXCEPTION);
        }

        return result;
    }

    /**
     * 成功获得优惠券，进行推送
     *
     * @param student
     * @param coupon
     */
    private void pushMsgToStudent(Student student, Coupon coupon, long stuCouponId) {
        Timer timer = new Timer();
        PushTask pushTask = new PushTask(student, coupon, stuCouponId);
        timer.schedule(pushTask, 5000);
    }

    @Override
    public void recoverCoupon(OrderVo orderVo) {
        if (orderVo == null || orderVo.getCoupon() == null || orderVo.getCoupon() == 0) {
            return;
        }
        couponManager.recoverCoupon(orderVo.getCoupon(), orderVo.getStudentId());
    }

    @Override
    public ReqResult updateCouponStock(CStock stock) {
        ReqResult rs = ReqResult.getFailed();
        try {
            if (couponManager.updateCouponStock(stock)) {
                rs = ReqResult.getSuccess();
            }
        } catch (Exception e) {
            logger.error("update stock fail. stock:{}", stock, e);
            rs.setCode(ResultCode.ERRORCODE.EXCEPTION);
            rs.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }
        return rs;
    }

    @Override
    public int useCoupon(StudentCoupon studentCoupon, OrderVo orderVo, boolean isUse) {
        try {
            String couponTmpId = studentCoupon.getCoupontmpid();
            Coupon coupon = couponManager.getCouponById(couponTmpId);
            if (checkCoupon(studentCoupon, orderVo, coupon)) {
                if (isUse) {
                    studentCoupon.setOrderid(orderVo.getOrderId());
                    studentCoupon.setUsetime(new Date());
                    studentCoupon.setIsUsed((byte) 1);
                    couponManager.updateStudentCoupon(studentCoupon);
                }

                //使用完优惠券，计算本张优惠券抵扣多少钱
                return calcCouponValue(coupon, orderVo);
            } else {
                return -1;
            }
        } catch (Exception e) {
            logger.error("studentCoupon id:" + studentCoupon.getCouponid(), e);
        }
        return 0;
    }

    @Override
    public int useCoupon(long studentCouponId, OrderVo orderVo, boolean isUse) {
        try {
            StudentCoupon studentCoupon = couponManager.getStudentCouponById(studentCouponId, orderVo.getStudentId());
            return useCoupon(studentCoupon, orderVo, isUse);
        } catch (Exception e) {
            logger.error("studentCoupon id:" + studentCouponId, e);
        }
        return 0;
    }

    @Override
    public void useCouponWithoutCheck(OrderVo orderVo) {
        if (orderVo == null || orderVo.getCoupon() == 0) {
            return;
        }
        StudentCoupon studentCoupon = new StudentCoupon();
        studentCoupon.setStudentid(orderVo.getStudentId());
        studentCoupon.setCouponid(orderVo.getCoupon());
        studentCoupon.setUsetime(new Date());
        studentCoupon.setIsUsed((byte) 1);
        couponManager.updateStudentCoupon(studentCoupon);
        
        StudentCoupon studentCouponInfo = couponManager.getStudentCouponById(orderVo.getCoupon(), orderVo.getStudentId());
        Coupon coupon = couponManager.getCouponById(studentCouponInfo.getCoupontmpid());
        //判断优惠券是否有限制，如果有，则判断数量是否最后一张，如果是，则更新isUse为1
        if (coupon.getMaxNum() != null && coupon.getMaxNum() > 0) {
        	//统计已用多少张
        	Integer isUseNum = couponManager.selectIsUseCount(studentCouponInfo.getCoupontmpid());
        	if (isUseNum.equals(coupon.getMaxNum())) {
        		//更新isExist = 0 置为无效
        		Coupon couponUp = new Coupon();
        		couponUp.setCoupontmpid(studentCouponInfo.getCoupontmpid());
        		couponUp.setIsexist((byte) 0);
        		couponManager.updateCouponSelective(couponUp);
        	}
        }
        //redisUtil.delete(REDISKEY.LOCK_COUPON + orderVo.getCoupon());
    }
    
    @Override
    public ReqResult auditCoupon(Coupon coupon) {
        return updateCoupon(coupon);
    }

    @Override
    public ReqResult updateCoupon(Coupon coupon) {
        ReqResult rs = ReqResult.getFailed();
        try {
            rs = validateCouponParam(coupon);
            if (couponManager.updateCoupon(coupon)) {
                rs = ReqResult.getSuccess();
            }
        } catch (Exception e) {
            logger.error("update coupon fail. coupon:{}", coupon, e);
            rs.setCode(ResultCode.ERRORCODE.EXCEPTION);
            rs.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }
        return rs;
    }

    @Override
    public ReqResult updateStudentCoupon(StudentCoupon studentCoupon) {
        ReqResult rs = ReqResult.getFailed();
        try {
            if (couponManager.updateStudentCoupon(studentCoupon)) {
                rs = ReqResult.getSuccess();
            }
        } catch (Exception e) {
            logger.error("update studentCoupon fail. studentCoupon:{}", studentCoupon, e);
            rs.setCode(ResultCode.ERRORCODE.EXCEPTION);
            rs.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }
        return rs;
    }

    @Override
    public ReqResult updateStockBatch(String[] ids, int isexist) {
        ReqResult rs = ReqResult.getFailed();
        try {
            if (ids != null && ids.length > 0
                    && couponManager.updateStockBatch(ids, isexist)) {
                rs = ReqResult.getSuccess();
            }
        } catch (Exception e) {
            logger.error("update updateCouponBatch fail. ids:{}", ids, e);
            rs.setCode(ResultCode.ERRORCODE.EXCEPTION);
            rs.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }
        return rs;
    }

    @Override
    public Coupon getCouponById(String coupontmpid) {
        return couponManager.getCouponById(coupontmpid);
    }

    private class PushTask extends TimerTask {
        private Student student;
        private Coupon coupon;
        private long stuCouponId;

        /**
         * @param student
         * @param coupon
         * @param stuCouponId
         */
        public PushTask(Student student, Coupon coupon, long stuCouponId) {
            super();
            this.student = student;
            this.coupon = coupon;
            this.stuCouponId = stuCouponId;
        }


        /* (non-Javadoc)
         * @see java.util.TimerTask#run()
         */
        @Override
        public void run() {
            //极光推送
            if (coupon.getJpushmsg() != null && !coupon.getJpushmsg().isEmpty()) {
                JpushMsg jmsg = new JpushMsg();
                jmsg.setAlter(coupon.getJpushmsg());
                jmsg.setUserId(student.getStudentId());
                jmsg.setOperate(JpushConstant.OPERATE.STUOBTAINNEWCOUPON);
                jmsg.getExtras().put("couponId", String.valueOf(stuCouponId));
                jmsg.getExtras().put("couponPic", coupon.getCouponpic());
                jmsg.getExtras().put("couponUrl", coupon.getCouponurl());

                Message jpush = new Message();
                jpush.setKeys(String.valueOf(coupon.getCoupontmpid()));
                jpush.setTopic(jpushProducer.getCreateTopicKey());
                jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
                jpush.setBody(SerializableUtil.serialize(jmsg));

                try {
                    jpushProducer.send(jpush);
                } catch (Exception e) {
                    logger.error("pushMsgToStudent:" + student + "|coupon:" + coupon, e);
                }
            }
            // 短信推送

            if (coupon.getSmsmsgtype() == 4) {
                try {
                    String name = coupon.getMoneyvalue() / 100 + "元";

                    int useType = coupon.getType();
                    switch (useType) {
                        case CouponUseType.CASH_COUPON:
                            name = name + "代金券";
                            break;
                        case CouponUseType.CLASS_COUPON:
                            name = name + "课时券";
                            break;
                        case CouponUseType.DISCOUNT_COUPON:
                            name = name + "折扣券";
                            break;
                        default:
                            break;
                    }

                    String place = "钱包";
                    Map<String, String> msgs = new HashMap<>();
                    msgs.put("name", name);
                    msgs.put("place", place);

                    authcodeService.sendMsg(ReqConstants.SHORT_MESSAGE_TYPE_ENROLL_COUPON, student.getPhoneNum(), msgs);

                } catch (Exception e) {
                    logger.error("send Msg error! coupon:{}, student:{}", coupon, student, e);
                }
            }
        }
    }

	@Override
	public ReqResult genStudentCouponAndNotifyDirect(CStock cStock,
			long studentId) {
		ReqResult result = ReqResult.getFailed();
        try {

            if (cStock.getIsexist() == 1 && couponManager.useOneStock(cStock) > 0) {

                //到这一步说明库存检查通过
                String couponTempId = cStock.getCoupontempid();
                Coupon coupon = couponManager.getCouponById(couponTempId);
                Student student = studentManager.getStudentInfo(studentId);
                if (coupon != null) {
                    //这里手动发放,则不需要将用户匹配问题再次确认,可随意发放,只要有库存
//                    boolean genResult = conditionManager.checkExpressions(coupon.getGenrule(), couponTempId, student, null);
                        //生成了优惠券
                        StudentCoupon studentCoupon = new StudentCoupon();
                        studentCoupon.setCoupontmpid(couponTempId);

                        Date expireTime = null;

                        if (coupon.getValidityperiod() == -1) {
                            //有效期为-1时表示永久有效
                            //expireTime = TimeUtil.addDate(new Date(), 24 * 3600000 * 365 * 1000l);
                        	Calendar c = Calendar.getInstance();
            			    c.add(Calendar.YEAR, 10);
            			    expireTime = c.getTime();
                        } else if (coupon.getValidityperiod() == 0) {
                            //使用优惠券的过期时间
                            if (null != coupon.getExpireTime()) {
                                expireTime = coupon.getExpireTime();
                            } else {
                                expireTime = new Date();
                            }
                        } else {
                            //计算过期时间
                            expireTime = TimeUtil.addDate(new Date(), coupon.getValidityperiod() * 3600000l);
                            if (null != coupon.getExpireTime() && coupon.getExpireTime().getTime() < expireTime.getTime()) {
                                //当前优惠券有截止时间时,所发的优惠券过期时间不能超过截止时间
                                expireTime = coupon.getExpireTime();
                            }
                        }

                        studentCoupon.setExpiretime(expireTime);

                        studentCoupon.setGettime(new Date());
                        studentCoupon.setStockid(cStock.getStockid());
                        studentCoupon.setIsUsed((byte) 0);
                        studentCoupon.setIsValid((byte) 1);
                        studentCoupon.setOrderid("");
                        studentCoupon.setStudentid(studentId);
                        long couponid = couponManager.addStudentCoupon(studentCoupon);
                        if (couponid > 0) {
                            // 给客户端推送消息
                            pushMsgToStudent(student, coupon, couponid);
                            result = ReqResult.getSuccess();
                            result.setMsgInfo(coupon.getJpushmsg());
                            result.setData("money", coupon.getMoneyvalue());
                        } else {
                            //表示给学生添加失败
                        }
                } else {
                    result.setCode(ResultCode.ERRORCODE.COUPON_UNFOUND);
                    result.setMsgInfo(ResultCode.ERRORINFO.COUPON_UNFOUND);
                }
            } else {
                result.setCode(ResultCode.ERRORCODE.COUPON_TICKET_INVALID);
                result.setMsgInfo(ResultCode.ERRORINFO.COUPON_TICKET_INVALID);
            }
        } catch (Exception e) {
            logger.error("genStudentCouponAndNotify:" + cStock + "," + studentId, e);
        }

        return result;
	}

	@Override
	public String handleCouponTmp(String couponTmpId, String couponNum, Long studentId) {
		Date expireTime = null;
		CStock cStock = null;
		Coupon coupon = null;
		StudentCoupon studentCoupon = null;
		String resultCode = ResultCode.ERRORCODE.SUCCESS;
		List<StudentCoupon> studentCouponList = new ArrayList<StudentCoupon>();
		try {
			coupon = couponManager.getCouponById(couponTmpId);
			cStock = couponManager.getStockByCouponTmpId(couponTmpId);
			studentCoupon = new StudentCoupon();
			studentCoupon.setCoupontmpid(couponTmpId);
			
			if (coupon.getValidityperiod() == -1) {
			    //有效期为-1时表示永久有效
				 //expireTime = TimeUtil.addDate(new Date(), 24 * 3600000 * 365 * 1000l);
				Calendar c = Calendar.getInstance();
			    c.add(Calendar.YEAR, 10);
			    expireTime = c.getTime();
			} else if (coupon.getValidityperiod() == 0) {
			    //使用优惠券的过期时间
			    if (null != coupon.getExpireTime()) {
			        expireTime = coupon.getExpireTime();
			    } else {
			        expireTime = new Date();
			    }
			} else {
			    //计算过期时间
			    expireTime = TimeUtil.addDate(new Date(), coupon.getValidityperiod() * 3600000l);
			    if (null != coupon.getExpireTime() && coupon.getExpireTime().getTime() < expireTime.getTime()) {
			        //当前优惠券有截止时间时,所发的优惠券过期时间不能超过截止时间
			        expireTime = coupon.getExpireTime();
			    }
			}
			logger.info("************************************* expireTime: " + expireTime);
			studentCoupon.setExpiretime(expireTime);
			studentCoupon.setGettime(new Date());
			studentCoupon.setStockid(cStock.getStockid());
			studentCoupon.setIsUsed((byte) 0);
			studentCoupon.setIsValid((byte) 1);
			studentCoupon.setOrderid("");
			studentCoupon.setStudentid(studentId);
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			studentCoupon.setExpiretimestr(formatter.format(expireTime));
			Integer couponNumInt = Integer.parseInt(couponNum);
			if (couponNumInt > 0) { //券有多张情况
				for(int i = 0; i < couponNumInt; i++) {
					studentCouponList.add(studentCoupon);
				}
				
				if (studentCouponList.size() > 0) { //批量插入优惠券
					//减去库存
					if (cStock.getIsexist() == 1 && cStock.getTotal() >= (cStock.getHaveused() + studentCouponList.size())) {
						if (couponManager.useMoreStock(cStock,studentCouponList.size()) > 0 ) {
							Long couponid = couponManager.addStudentCouponList(studentCouponList);
							logger.info("************************************* couponid : " + couponid +" use Size : " + studentCouponList.size());
							if (couponid > 0) {
							} 
							else {
								return ResultCode.ERRORCODE.COUPON_CONDITION_LIMIT;
							}
						}
					}
					else {
						logger.info("************************************* 优惠券库存不足  ");
						return ResultCode.ERRORCODE.COUPON_CONDITION_LIMIT;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultCode;
	}
}



