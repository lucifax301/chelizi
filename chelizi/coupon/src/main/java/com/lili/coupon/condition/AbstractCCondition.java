/**
 * 
 */
package com.lili.coupon.condition;

import com.lili.coupon.dto.CCondition;
import com.lili.order.vo.OrderVo;
import com.lili.student.dto.Student;

/**
 * @author linbo
 * 优惠券的条件，生成条件和使用条件都在这里
 */
public abstract class AbstractCCondition
{
    public abstract boolean checkCondition(CCondition condition, Student student, OrderVo orderVo);
}
