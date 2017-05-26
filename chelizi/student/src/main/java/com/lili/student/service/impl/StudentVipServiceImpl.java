package com.lili.student.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lili.common.util.StringUtil;
import com.lili.common.util.redis.RedisKeys;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.coupon.service.CouponService;
import com.lili.student.dto.*;
import com.lili.student.manager.StudentManager;
import com.lili.student.manager.StudentVipManager;
import com.lili.student.mapper.dao.VipChargeDiscountMapper;
import com.lili.student.mapper.dao.VipPackageMapper;
import com.lili.student.service.StudentService;
import com.lili.student.service.StudentVipService;
import com.lili.student.vo.ChargeDiscountVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StudentVipServiceImpl implements StudentVipService {

    @Autowired
    RedisUtil redisUtil;
    private Logger logger = LoggerFactory.getLogger(StudentVipService.class);
    @Autowired
    private CouponService couponService;
    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentManager studentManager;
    @Autowired
    private StudentVipManager studentVipManager;
    @Autowired
    private VipPackageMapper vipPackageMapper;

    @Autowired
    private VipChargeDiscountMapper vipChargeDiscountMapper;

    @Override
    public ReqResult addVipStudent(String mobile, String name, String codeInput, Integer vipId) {
        //验证码检查
        String authcode = redisUtil.get(RedisKeys.REDISKEY.STUDENT_AUTHCODE + mobile);
        if (authcode == null || !authcode.equals(codeInput.trim())) {    // 验证码错误
            ReqResult r = new ReqResult();
            r.setCode(ResultCode.ERRORCODE.AUTHCODE_ERROR);
            r.setMsgInfo(ResultCode.ERRORINFO.AUTHCODE_ERROR);
            return r;
        }

        ReqResult result = ReqResult.getFailed();

        StudentVip studentVip = studentManager.getStudentVipInfoByVipId(vipId);
        if (null != studentVip) {//检查大客户信息

            Student student = studentManager.getStudentByPhoneNum(mobile);
            if (null == student) {//检查用户是否已经注册过了

                result = studentService.addStudentAndLogin(mobile, codeInput, "");
                if (result.isSuccess()) {
                    student = studentManager.getStudentByPhoneNum(mobile);
                    student.setName(name);
                    student.setVipId(vipId);
                    studentManager.updateStudent(student);
                }
            } else {
                result.setCode(ResultCode.ERRORCODE.USER_EXIST);
                result.setMsgInfo(ResultCode.ERRORINFO.USER_EXIST);
            }
        } else {
            result.setCode(ResultCode.ERRORCODE.NO_VIP_USER);
            result.setMsgInfo(ResultCode.ERRORINFO.NO_VIP_USER);
        }
        return result;
    }


    @Override
    public StudentVipWithBLOBs getStudentVip(int vipId) {
        return studentVipManager.getStudentVip(vipId);
    }

    @Override
    public ReqResult getStudentVipByStudentId(long studentId) {
        ReqResult r = ReqResult.getSuccess();

        Student student = studentManager.getStudentInfo(studentId);
        if (null != student) {
            Integer vipId = student.getVipId();
            if (null != vipId && vipId != 0) {
                StudentVipWithBLOBs studentVip = getStudentVip(vipId.intValue());

                //填充大客户的活动信息
                VipPackage vipPackage = getVipPackage(student.getVipPackageId());
                if (null != vipPackage) {
                    String json = vipPackage.getExtra();
                    JSONObject node = JSON.parseObject(json);
                    node.put("isValid", vipPackage.getIsvalid() == 1);
                    studentVip.setExtra(node.toJSONString());
                }

                r.setData(studentVip);
            } else {
                r.setCode(ResultCode.ERRORCODE.NO_VIP_USER);
                r.setMsgInfo(ResultCode.ERRORINFO.NO_VIP_USER);
            }
        } else {
            r.setCode(ResultCode.ERRORCODE.NO_VIP_USER);
            r.setMsgInfo(ResultCode.ERRORINFO.NO_VIP_USER);
        }
        return r;
    }

    public VipPackage getVipPackage(String vipPackageId) {
        return vipPackageMapper.selectByPrimaryKey(vipPackageId);
    }

    @Override
    public List<VipChargeDiscount> getChargeDiscountListByPackageId(String vipPackageId) {
        return vipChargeDiscountMapper.selectValidByvipPackageId(vipPackageId);
    }

    @Override
    public ReqResult getChargeDiscountListStudentId(long studentId) {

        ReqResult sr = ReqResult.getSuccess();
        try {
            Student student = studentManager.getStudentInfo(studentId);
            if (null != student) {
                String vipPackageId = student.getVipPackageId();
                if (StringUtil.isNotNullAndNotEmpty(vipPackageId)) {
                    VipPackage vipPackage = vipPackageMapper.selectByPrimaryKey(vipPackageId);
                    if (null != vipPackage && vipPackage.getIsvalid() == 1) {
                        List<VipChargeDiscount> chargeDiscouts = vipChargeDiscountMapper.selectValidByvipPackageId(vipPackageId);

                        if (null != chargeDiscouts && chargeDiscouts.size() > 0) {
                            //由小到大排序
                            Collections.sort(chargeDiscouts, new Comparator<VipChargeDiscount>() {
                                @Override
                                public int compare(VipChargeDiscount o1, VipChargeDiscount o2) {
                                    return o1.getLimitMoney() - o2.getLimitMoney();
                                }
                            });

                            ChargeDiscountVo vo = new ChargeDiscountVo();
                            vo.setType(1);
                            vo.setDesc("其他金额不予赠送");
                            vo.setList(chargeDiscouts);

                            sr.setData(vo);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("getChargeDiscountListStudentId error! studentId:{}", studentId, e);
        }
        return sr;
    }

    @Override
    public ReqResult updateVipStudentVipPackageId(String mobile, String vipPackageId) {
        ReqResult result = ReqResult.getFailed();
        if (StringUtil.isNullOrEmpty(vipPackageId)) {
            result = ReqResult.getParamError();
            return result;
        }

        Student student = studentManager.getStudentByPhoneNum(mobile);
        if (null != student && null != student.getVipId()) {//当该学员为大客户时,才有优惠

            List<VipChargeDiscount> chargeDiscountList = getChargeDiscountListByPackageId(vipPackageId);
            List<VipCoupon> vipCouponList = studentVipManager.selectValidByVipCouponByVipPackageId(vipPackageId);
            if ((null != chargeDiscountList && chargeDiscountList.size() > 0) || (null != vipCouponList && vipCouponList.size() > 0)) {

                //更新优惠套餐信息
                student.setVipPackageId(vipPackageId);
                if (studentManager.updateStudent(student) > 0) {
                    result = ReqResult.getSuccess();

                    if (null != vipCouponList && vipCouponList.size() > 0) {
                        sendCoupon2VipStudent(mobile, vipCouponList);//自动发优惠券
                    }
                }
            } else {
                result.setCode(ResultCode.ERRORCODE.CHARGE_DISCOUNT_UNFOUND);
                result.setMsgInfo(ResultCode.ERRORINFO.CHARGE_DISCOUNT_UNFOUND);
            }
        } else {
            result.setCode(ResultCode.ERRORCODE.NO_USER);
            result.setMsgInfo(ResultCode.ERRORINFO.NO_USER);
        }
        return result;
    }

    private void sendCoupon2VipStudent(String phoneNum, List<VipCoupon> vipCoupon) {
        for (VipCoupon vc : vipCoupon) {
            couponService.obtainCouponByPhone(phoneNum, vc.getCouponTmpId());
        }
    }
}
