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
 *         教练出车听单中状态
 */
public class CoachOnWorkState extends AbstractCoachState
{
    @Autowired
    private CoachService coachService;

    @Autowired
    private CoachManager coachManager;

    @Autowired
    private OrderLogic orderLogic;
    
    @Autowired
    private IStudentStateManager stateManager; 

    /*
     * (non-Javadoc)
     * 
     * @see com.lili.logic.service.ICoachState#handleDoStatus(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @Override
    public ReqResult handleDoStatus(String coachId, String carId, String courses, String status, String tokenId)
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

        r = coachService.doStatus(coachId, carId, courses, status, tokenId);
        if (r.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS))
        {
            Coach coach = new Coach();
            coach.setCoachId(Long.parseLong(coachId));
            coach.setWstate((int) st);
            coach.setEventDesc("");
            //设置出车课程
            coach.setCourses(courses);
            //设置出车id //收车时可用为空
            try {
                int carIdn = (carId == null || carId.isEmpty()) ? 0 : Integer.parseInt(carId);
                if (carIdn != 0) {
                	coach.setCoachCarId(carIdn);
                }
            } catch (NumberFormatException e) {
                logger.error("carId:" + carId, e);
            }
            coachManager.updateCoach(coach);
        }
        

        return r;
    }
    
    @Override
    public ReqResult handleAcceptOrder(String orderId, String coachId, String studentId, String tokenId)
    {
        ReqResult reqResult = ReqResult.getFailed();
        try
        {
            reqResult = orderLogic.acceptOrder(orderId, coachId, studentId, tokenId);
            if (reqResult.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS))
            {
                Coach coach = new Coach();
                coach.setCoachId(Long.parseLong(coachId));
                coach.setWstate(ReqConstants.COACH_STATUS_PREPARE_CLASS);
                coach.setEventDesc(orderId);
                coachManager.updateCoach(coach);
            }
        }
        catch (Exception e)
        {
            logger.error("handleAcceptOrder:" + orderId + ":" + coachId + ":" + studentId, e);
        }
        return reqResult;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.lili.logic.service.ICoachState#handleAddOrder(com.lili.order.vo.OrderVo)
     */
    @Override
    public ReqResult handleAddOrder(OrderVo orderVo)
    {
        ReqResult reqResult = ReqResult.getFailed();
        //目前教练只能接一个单
        Coach coach = coachManager.getCoachInfo(orderVo.getCoachId());
        //20160317如果教练出的课程与订单中的课程不一致，则返回错误
        if(!coach.getCourses().contains(orderVo.getCourseId())){
        	reqResult.setCode(ResultCode.ERRORCODE.ORDER_COACH_BUZ);
        	reqResult.setMsgInfo(ResultCode.ERRORINFO.ORDER_COACH_BUZ);
        	return reqResult;
        }
        if (coach != null)
        {
            coach.setWstate(ReqConstants.COACH_STATUS_PREPARE_CLASS);
            coach.setEventDesc(orderVo.getOrderId());
            coachManager.updateCoach(coach);
            reqResult = ReqResult.getSuccess();
        }
        return reqResult;
    }

    @Override
    public void handleOrderOverTime(OrderVo orderVo)
    {
        logger.error("curState:" + this.getClass().getName() + "|curAction:handleOrderOverTime" + "message:"
                + orderVo.toString(),
                new NullPointerException());
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
                Coach info = coachManager.getCoachInfo(orderVo.getCoachId());
                info.setWstate(ReqConstants.COACH_STATUS_ON_WORK);
                info.setEventDesc(removeCancelOrderFromStateContext(info.getEventDesc(), orderVo.getOrderId()));
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
                    Coach info = coachManager.getCoachInfo(orderVo.getCoachId());
                    info.setWstate(ReqConstants.COACH_STATUS_ON_WORK);
                    info.setEventDesc(removeCancelOrderFromStateContext(info.getEventDesc(), orderVo.getOrderId()));
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
    public ReqResult handleRefuseOrder(String orderId, String userId, String reason, String tokenId)
    {
        ReqResult reqResult = ReqResult.getFailed();
        try
        {
            reqResult = orderLogic.refuseOrder(orderId, userId, reason, tokenId);
            if (reqResult.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS))
            {
                Coach coach = coachManager.getCoachInfo(Long.parseLong(userId));
                coach.setWstate(ReqConstants.COACH_STATUS_ON_WORK);
                coach.setEventDesc(removeCancelOrderFromStateContext(coach.getEventDesc(), orderId));
                coachManager.updateCoach(coach);
            }
        }
        catch (Exception e)
        {
            logger.error("handleRefuseOrder:" + orderId + ":" + userId + ":" + reason, e);
        }
        return reqResult;
    }
    @Override
    public ReqResult getCurStateContext(String coachId)
    {
        ReqResult r = super.getCurStateContext(coachId);
        UserStateVo stateVo = (UserStateVo) r.getResult().get(ResultCode.RESULTKEY.DATA);
        Coach info = coachManager.getCoachInfo(Long.parseLong(coachId));
        if (stateVo.getState() == ReqConstants.COACH_STATUS_OFF_WORK)
        {
            stateVo.setState(ReqConstants.COACH_STATUS_ON_WORK);
            OrderVo orderVo = getCurOrderVo(info);
            stateVo.setOrderVo(orderVo);
            if (orderVo != null)
            {
                stateVo.setOrders(removeCancelOrderFromStateContext(info.getEventDesc(), orderVo.getOrderId()));
            }
            
            r.setData(stateVo);
        }
        return r;
    }
    
}
