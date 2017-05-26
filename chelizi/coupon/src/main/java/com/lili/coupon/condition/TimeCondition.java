/**
 * 
 */
package com.lili.coupon.condition;

import java.util.Date;

import com.lili.common.util.TimeUtil;
import com.lili.coupon.dto.CCondition;
import com.lili.order.vo.OrderVo;
import com.lili.student.dto.Student;

/**
 * @author linbo
 *
 */
public class TimeCondition extends AbstractCCondition
{
    /* (non-Javadoc)
     * @see com.lili.coupon.condition.AbstractCCondition#checkCondition(com.lili.coupon.dto.CCondition)
     */
    @Override
    public boolean checkCondition(CCondition condition, Student student, OrderVo orderVo)
    {
        Date startTime = TimeUtil.parseDate(condition.getParam1());
        Date endTime = TimeUtil.parseDate(condition.getParam2());
        
        if (startTime == null || endTime == null)
        {
            return false;
        }
        
        return TimeUtil.isInDate(startTime, endTime);
    }
}
