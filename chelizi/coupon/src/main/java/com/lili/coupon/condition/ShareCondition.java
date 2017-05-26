/**
 * 
 */
package com.lili.coupon.condition;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.coupon.dto.CCondition;
import com.lili.order.vo.OrderVo;
import com.lili.share.manager.IShareManager;
import com.lili.student.dto.Student;

/**
 * @author linbo
 *
 */
public class ShareCondition extends AbstractCCondition
{
    @Autowired
    private IShareManager shareManager;

    /* (non-Javadoc)
     * @see com.lili.coupon.condition.AbstractCCondition#checkCondition(com.lili.coupon.dto.CCondition, long, java.lang.String)
     */
    @Override
    public boolean checkCondition(CCondition condition, Student student, OrderVo orderVo)
    {
        /*String couponTempId = condition.getParam1();
        return shareManager.queryIsExitShare(student.getPhoneNum(), couponTempId) > 0;*/
        return true;
    }

}
