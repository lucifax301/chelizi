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
 *         判断区域
 */
public class RegionCondition extends AbstractCCondition {
	private static Logger logger = LoggerFactory.getLogger(RegionCondition.class);

    @Override
    public boolean checkCondition(CCondition condition, Student student, OrderVo orderVo) {

        try
        {
            String param = condition.getParam1();//指定区域使用优惠券，多个区域用|分割；区域使用区域代码标识。
            String[] citys = param.split("\\|");
            if (orderVo != null && orderVo.getCityId() != null)
            {
            	String cityStr = orderVo.getCityId().toString();
                for (String city : citys)
                {
                    if (city.equals(cityStr))
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
