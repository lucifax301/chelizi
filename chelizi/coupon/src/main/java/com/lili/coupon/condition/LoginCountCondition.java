/**
 * 
 */
package com.lili.coupon.condition;

import com.lili.coupon.dto.CCondition;
import com.lili.order.vo.OrderVo;
import com.lili.student.dto.Student;

/**
 * @author linbo
 *
 */
public class LoginCountCondition extends AbstractCCondition
{
    /* (non-Javadoc)
     * @see com.lili.coupon.condition.AbstractCCondition#checkCondition(com.lili.coupon.dto.CCondition, com.lili.student.dto.Student, com.lili.order.vo.OrderVo)
     */
    @Override
    public boolean checkCondition(CCondition condition, Student student, OrderVo orderVo)
    {
        // TODO Auto-generated method stub
        return true;
    }

}
