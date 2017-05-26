/**
 * 
 */
package com.lili.coupon.condition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lili.coupon.dto.CCondition;
import com.lili.order.vo.OrderVo;
import com.lili.student.dto.Student;

/**
 * @author linbo
 *
 */
public class CourseCondition extends AbstractCCondition
{
    private static Logger logger = LoggerFactory.getLogger(CourseCondition.class);
    
    /* (non-Javadoc)
     * @see com.lili.coupon.condition.AbstractCCondition#checkCondition(com.lili.coupon.dto.CCondition, long, java.lang.String)
     */
    @Override
    public boolean checkCondition(CCondition condition, Student student, OrderVo orderVo)
    {
        try
        {
            String param = condition.getParam1();
            String courseIds [] = param.split("\\|");
            
            if (orderVo != null)
            {
                for (String course : courseIds)
                {
                    if (course.equals(orderVo.getCourseId()))
                    {
                        return true;
                    }
                }
            }
            return false;
        }
        catch (Exception e)
        {
            logger.error("checkCondition:" + student + "," + orderVo + "," + condition.getConditionid(), e);
        }
        return false;
    }

}
