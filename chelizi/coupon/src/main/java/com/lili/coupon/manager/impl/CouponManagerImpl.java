/**
 *
 */
package com.lili.coupon.manager.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lili.coupon.condition.ConditionManager;
import com.lili.coupon.dto.CCondition;
import com.lili.coupon.dto.CStock;
import com.lili.coupon.dto.Coupon;
import com.lili.coupon.dto.StudentCoupon;
import com.lili.coupon.mapper.dao.CStockMapper;
import com.lili.coupon.mapper.dao.CouponMapper;
import com.lili.coupon.mapper.dao.CouponPage;
import com.lili.coupon.mapper.dao.StudentCouponMapper;

/**
 * @author linbo
 */
@Service("couponManager")
public class CouponManagerImpl implements CouponManager {
    @Autowired
    private StudentCouponMapper studentCouponMapper;
    @Autowired
    private ConditionManager conditionManager;

    @Autowired
    private CStockMapper cStockMapper;

    @Autowired
    private CouponMapper couponMapper;

    @Override
    public boolean addCoupon(Coupon coupon, CStock stock) {
        return ((couponMapper.insert(coupon) > 0) && (cStockMapper.insert(stock) > 0));
    }

    @Override
    public long addStudentCoupon(StudentCoupon studentCoupon) {
        studentCouponMapper.insert(studentCoupon);
        return studentCoupon.getCouponid();
    }

    @Override
    public boolean addCondition(CCondition condition) {
        return conditionManager.addCondition(condition);
    }

    @Override
    public List<CStock> getAllSuitableStock(String eventType) {
        //先取该事件所有有效的库存
        return cStockMapper.getAllValidStock(eventType);
    }

    @Override
    public Coupon getCouponById(String couponTmpId) {
        return couponMapper.selectByPrimaryKey(couponTmpId);
    }

    @Override
    public CStock getStockByCouponTmpId(String couponTmpId) {
        return cStockMapper.getStockByCouponTmpId(couponTmpId);
    }

    @Override
    public List<StudentCoupon> getStudentAllCouponByPage(long studentId, int page, boolean isUsed, boolean isValid) {
        CouponPage couponPage = new CouponPage(page, 10, studentId, isUsed, isValid);
        return studentCouponMapper.getStudentAllCouponByPage(couponPage);
    }

    @Override
    public List<StudentCoupon> getStudentAllCouponsCanUse(long studentId) {
        return studentCouponMapper.getAllCouponsCanUse(studentId);
    }

    @Override
    public StudentCoupon getStudentCouponById(long studentCouponId, long studentId) {
        return studentCouponMapper.selectByPrimaryKey(studentCouponId, studentId);
    }

    @Override
    public int getUseableCouponCount(long studentId) {
        return studentCouponMapper.getUseableCouponCount(studentId);
    }

    @Override
    public void recoverCoupon(long coupon, long studentId) {
        StudentCoupon studentCoupon = getStudentCouponById(coupon, studentId);
        if (studentCoupon != null) {
            //先恢复优惠券状态
            if (studentCouponMapper.recoverCoupon(coupon, studentId) > 0) {
                //再恢复库存
                recoverStock(studentCoupon.getStockid());
            }
        }
    }

    @Override
    public boolean activeStock(CStock stock, boolean isActive) {
        stock.setIsexist((byte) (isActive == true ? 1 : 0));
        return cStockMapper.updateByPrimaryKey(stock) >= 0;
    }

    @Override
    public void recoverStock(int stockId) {
        cStockMapper.recoverStock(stockId);
    }
    
    @Override
    public void recoverMoreStock(int stockId,int num) {
        cStockMapper.recoverMoreStock(stockId,num);
    }


    @Override
    public boolean updateCoupon(Coupon coupon, CStock stock) {
        return ((couponMapper.updateByPrimaryKey(coupon) > 0) && cStockMapper.updateByPrimaryKey(stock) > 0);
    }

    @Override
    public boolean updateStudentCoupon(StudentCoupon studentCoupon) {
        return studentCouponMapper.updateByPrimaryKeySelective(studentCoupon) > 0;
    }

    @Override
    public int useOneStock(CStock cStock) {
        return cStockMapper.useStock(cStock.getStockid());
    }
    
    @Override
    public int useMoreStock(CStock cStock,int num) {
        return cStockMapper.useMoreStock(cStock.getStockid(),num);
    }

	@Override
	public boolean updateCoupon(Coupon coupon) {
		return couponMapper.updateByPrimaryKey(coupon) > 0;
	}
	
	@Override
	public int updateCouponSelective(Coupon coupon) {
		return couponMapper.updateByPrimaryKeySelective(coupon);
	}

	@Override
	public boolean updateStockBatch(String[] ids, int isexist) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		params.put("isexist", isexist);
		
		return cStockMapper.updateStockBatch(params) > 0;
	}

	@Override
	public boolean updateCouponStock(CStock stock) {
		return cStockMapper.updateByPrimaryKey(stock) > 0;
	}

	@Override
	public List<Coupon> getCouponByExtra(String extra) {
		return couponMapper.selectByExtra(extra);
	}

	@Override
	public int selectIsExitCount(long studentId, String couponTmpId) {
		return studentCouponMapper.selectIsExitCount(studentId, couponTmpId);
	}

	@Override
	public long addStudentCouponList(List<StudentCoupon> studentCouponList) {
		return  studentCouponMapper.insertList(studentCouponList);
	       
	}

	@Override
	public int selectIsUseCount(String couponTmpId) {
		return studentCouponMapper.selectIsUseCount(couponTmpId);
	}
	
	@Override
	public int checkExistCoupon(String userId,String couponTmpIds){
		
		return studentCouponMapper.checkExistCoupon(userId,couponTmpIds);
		
	}


}
