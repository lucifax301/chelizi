/**
 * 
 */
package com.lili.coupon.condition;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.coupon.dto.CCondition;
import com.lili.coupon.mapper.dao.StudentCouponMapper;
import com.lili.order.vo.OrderVo;
import com.lili.student.dto.Student;

/**
 * @author linbo
 * 持有数量做限制
 */
public class LimitGenCondition extends AbstractCCondition
{
    @Autowired
    private StudentCouponMapper studentCouponMapper;
    
    @Override
    public boolean checkCondition(CCondition condition, Student student, OrderVo orderVo)
    {
        String countStr = condition.getParam1();
        String tempIdStr = condition.getCoupontmpid();
        int count = Integer.parseInt(countStr);
        
        int curCount = studentCouponMapper.selectCurCount(student.getStudentId(), tempIdStr);
        
        return curCount < count;
    }
}
