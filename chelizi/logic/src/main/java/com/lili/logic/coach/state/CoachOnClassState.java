/**
 * 
 */
package com.lili.logic.coach.state;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.coach.dto.Coach;
import com.lili.coach.manager.CoachManager;
import com.lili.common.constant.OrderConstant;
import com.lili.common.util.TimeUtil;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.logic.state.AbstractCoachState;
import com.lili.order.vo.OrderVo;
import com.lili.student.vo.UserStateVo;

/**
 * @author linbo
 *         教练上课中状态
 */
public class CoachOnClassState extends AbstractCoachState
{
    @Autowired
    private CoachManager coachManager;

    @Override
    public ReqResult handleDoCourseStatus(String coachId, String orderId, String studentId, String status,
            String tokenId)
    {
        ReqResult r = ReqResult.getFailed();
        byte st = 0;
        try
        {
            st = Byte.parseByte(status.trim());
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
            r.setCode(ResultCode.ERRORCODE.PARAMERROR);
            r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
            return r;
        }
        // 当前状态只能接受学员下课消息
        if (st == ReqConstants.STUDENT_STATUS_OFF_CLASS)
        {
            Coach coach = new Coach();
            coach.setCoachId(Long.parseLong(coachId));
            coach.setWstate(ReqConstants.COACH_STATUS_OFF_WORK);
            coach.setEventDesc(orderId);
            coachManager.updateCoach(coach);
            r = ReqResult.getSuccess();
        }

        return r;
    }

    @Override
    public ReqResult getCurStateContext(String coachId)
    {
        ReqResult r = super.getCurStateContext(coachId);
        Coach info = coachManager.getCoachInfo(Long.parseLong(coachId));
                     
        UserStateVo stateVo = (UserStateVo) r.getResult().get(ResultCode.RESULTKEY.DATA);
        if (stateVo.getState() != ReqConstants.COACH_BOOK_CLASS_IN)
        {
            stateVo.setState(ReqConstants.COACH_STATUS_ON_CLASS);
            OrderVo orderVo = getCurOrderVo(info);
            if (orderVo == null)
            {
                logger.error("order is null but state is coach on class:" + coachId);
                
                Coach coach = new Coach();
                coach.setCoachId(Long.parseLong(coachId));
                coach.setWstate(ReqConstants.COACH_STATUS_OFF_WORK);
                coach.setEventDesc("");
                coachManager.updateCoach(coach);
                
                stateVo.setState(ReqConstants.COACH_STATUS_OFF_WORK);
                stateVo.setOrderVo(orderVo);
                r.setData(stateVo);
                return r;
            }
            Date now = new Date();
            long curTimeGap = TimeUtil.calcDistanceMillis(now, orderVo.getPend());
            if (orderVo != null && orderVo.getRend() != null)
            {
                curTimeGap = TimeUtil.calcDistanceMillis(now, orderVo.getRend());
            }
            //允许2秒误差
            if (curTimeGap < 2000 && orderVo.getOrderState() == OrderConstant.ORDERSTATE.INCLASS)
            {
                Coach coach = new Coach();
                coach.setCoachId(Long.parseLong(coachId));
                coach.setWstate(ReqConstants.COACH_STATUS_OFF_WORK);
                coach.setEventDesc("");
                coachManager.updateCoach(coach);
                
                stateVo.setState(ReqConstants.COACH_STATUS_OFF_WORK);
                orderVo.setOrderState(OrderConstant.ORDERSTATE.COMPLETE);
                orderVo.setRend(now);
                try
                {
                    orderService.updateByOrderId(orderVo, orderVo.getOrderId());
                }
                catch (Exception e)
                {
                    logger.error("order:" + orderVo, e);
                }
            }
            stateVo.setOrderVo(orderVo);
            r.setData(stateVo);
        }
        else
        {
            logger.error("is a big bug");
            //如果出现这种情况，则认为是因为现约的订单课程没有及时结束
        }
        
        return r;
    }
    
}
