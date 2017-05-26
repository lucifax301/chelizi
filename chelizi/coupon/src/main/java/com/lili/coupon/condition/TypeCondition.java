package com.lili.coupon.condition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lili.coupon.dto.CCondition;
import com.lili.order.vo.OrderVo;
import com.lili.student.dto.Student;
/**
 * 订单类型条件检查，类型包括：
 * 1现约订单,2续课订单，3预约订单 ；11-报名订单；12-报考订单
 * @author yangpeng
 *
 */
public class TypeCondition extends AbstractCCondition{
	private static Logger logger = LoggerFactory.getLogger(TypeCondition.class);

	@Override
	public boolean checkCondition(CCondition condition, Student student, OrderVo orderVo) {
        try
        {
            String param = condition.getParam1();
            String types [] = param.split("\\|");
            
            if (orderVo != null && orderVo.getOtype() != null)
            {
                for (String type : types)
                {
                    if (type.equals(orderVo.getOtype().toString()))
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
