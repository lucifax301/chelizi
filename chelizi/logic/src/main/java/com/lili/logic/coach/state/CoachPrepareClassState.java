/**
 * 
 */
package com.lili.logic.coach.state;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.coach.dto.Coach;
import com.lili.coach.manager.CoachManager;
import com.lili.coach.service.CoachService;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.logic.service.IStudentStateManager;
import com.lili.logic.service.OrderLogic;
import com.lili.logic.state.AbstractCoachState;
import com.lili.order.vo.OrderVo;
import com.lili.student.vo.UserStateVo;

/**
 * @author linbo
 * 教练准备上课状态
 */
public class CoachPrepareClassState extends AbstractCoachState
{
    @Autowired
    private CoachService coachService;
    @Autowired
    private CoachManager coachManager;
    @Autowired
    private OrderLogic orderLogic;
    @Autowired
    private IStudentStateManager stateManager; 
    @Override
    public ReqResult handleDoCourseStatus(String coachId, String orderId, String studentId, String status, String tokenId)
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
        //当前状态只能接受上课消息
        if (st == ReqConstants.COACH_STATUS_ON_CLASS)
        {
            r = coachService.doCourseStatus(coachId, orderId, studentId, status, tokenId);
            if(r.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS))
            {
                Coach coach = new Coach();
                coach.setCoachId(Long.parseLong(coachId));
                coach.setWstate(ReqConstants.COACH_STATUS_ON_CLASS);
                coach.setEventDesc(orderId);

                coachManager.updateCoach(coach);
            }
        }
        
        return r;
    }
    
    @Override
    public ReqResult handleCancelOrder(OrderVo orderVo, String retype, String reason, String userId, String userType,
            String tokenId, boolean haveCancel)
    {
        ReqResult r = ReqResult.getSuccess();
        try
        {
            if (haveCancel)
            {
                // 状态变更
                Coach info = new Coach();
                info.setCoachId(orderVo.getCoachId());
                info.setWstate(ReqConstants.COACH_STATUS_ON_WORK);
                info.setEventDesc("");
                coachManager.updateCoach(info);
            }
            else
            {
                if (userType.equals("1"))
                {
                    r = orderLogic.cancelOrder(orderVo.getOrderId(), retype, reason, userId, userType, tokenId);
                }

                if (r.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS))
                {
                    // 状态变更
                    Coach info = new Coach();
                    info.setCoachId(orderVo.getCoachId());
                    info.setWstate(ReqConstants.COACH_STATUS_ON_WORK);
                    info.setEventDesc("");
                    coachManager.updateCoach(info);
                    
                    r = stateManager.handleCancelOrder(orderVo.getOrderId(), retype, reason, String.valueOf(orderVo.getStudentId()), "1", tokenId, true);
                }
            }
        }
        catch (Exception e)
        {
            logger.error("handleCancelOrder:" + orderVo, e);
            r.setCode(ResultCode.ERRORCODE.EXCEPTION);
            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }
        return r;
    }

    @Override
    public ReqResult getCurStateContext(String coachId)
    {
        ReqResult r = super.getCurStateContext(coachId);
        UserStateVo stateVo = (UserStateVo) r.getResult().get(ResultCode.RESULTKEY.DATA);
        Coach info = coachManager.getCoachInfo(Long.parseLong(coachId));
        if (stateVo.getState() == ReqConstants.COACH_STATUS_OFF_WORK)
        {
            stateVo.setState(ReqConstants.COACH_STATUS_PREPARE_CLASS);
            OrderVo orderVo = getCurOrderVo(info);
            stateVo.setOrderVo(orderVo);
            r.setData(stateVo);
        }
        return r;
    }
}
