package com.lili.student.manager.impl;

import com.lili.common.util.redis.RedisUtil;
import com.lili.student.dto.StudentVipExample;
import com.lili.student.dto.StudentVipWithBLOBs;
import com.lili.student.dto.VipCoupon;
import com.lili.student.manager.StudentVipManager;
import com.lili.student.mapper.dao.StudentVipMapper;
import com.lili.student.mapper.dao.VipCouponMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StudentVipManagerImpl implements StudentVipManager {
	@Autowired
	private StudentVipMapper studentVipMapper;
	@Autowired
	private VipCouponMapper vipCouponMapper;

	@Autowired
	RedisUtil redisUtil;
	
	@Override
	public StudentVipWithBLOBs  getStudentVip(int vipId) {
		//redisUtil.get(REDISKEY.STUDENT_VIP_INFO + vipId);
		return studentVipMapper.selectByPrimaryKey(vipId);
	}

	@Override
	public List<StudentVipWithBLOBs> getStudentVip(StudentVipExample example) {
		return studentVipMapper.selectByExampleWithBLOBs(example);
	}

	@Override
	public int postStudentVip(StudentVipWithBLOBs record) {
		return studentVipMapper.insertSelective(record);
	}

	@Override
	public int putStudentVip(StudentVipWithBLOBs record) {
		return studentVipMapper.updateByPrimaryKeyWithBLOBs(record);
	}

    @Override
    public List<VipCoupon> selectValidByVipCouponByVipPackageId(String vipPackageId) {
        return vipCouponMapper.selectValidByVipPackageId(vipPackageId);
    }
}
