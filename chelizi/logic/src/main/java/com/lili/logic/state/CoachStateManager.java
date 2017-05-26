/**
 * 
 */
package com.lili.logic.state;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.coach.dto.Coach;
import com.lili.coach.manager.CoachManager;
import com.lili.common.constant.OrderConstant;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.logic.service.ICoachState;
import com.lili.logic.service.ICoachStateManager;
import com.lili.logic.service.OrderLogic;
import com.lili.order.service.OrderService;
import com.lili.order.vo.OrderQuery;
import com.lili.order.vo.OrderVo;
import com.lili.student.vo.UserStateVo;

/**
 * @author linbo
 *
 */
public class CoachStateManager implements ICoachStateManager
{
    @Autowired
    private CoachManager coachManager;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderLogic orderLogic;

    private static Logger logger = LoggerFactory.getLogger(CoachStateManager.class);

    /**
     * 教练状态：收车(休息，默认值)
     */
    public ICoachState COACH_STATUS_OFF_WORK;
    /**
     * 教练状态：出车（听单中）
     */
    public ICoachState COACH_STATUS_ON_WORK;
    /**
     * 教练状态：上课中
     */
    public ICoachState COACH_STATUS_ON_CLASS;
    /**
     * 教练状态：上课准备
     */
    public ICoachState COACH_STATUS_PREPARE_CLASS;

    private Map<Integer, ICoachState> stateMaps;

    public ICoachState getCurCoachState(long coachId)
    {
        Coach student = coachManager.getCoachInfo(coachId);
        try
        {
            ICoachState coachState = stateMaps.get(student.getWstate());
            if (coachState != null)
            {
                return coachState;
            }
            else
            {
                logger.error("getCurCoachState:" + coachId + "|state:" + student.getWstate(),
                        new NullPointerException());
            }
        }
        catch (Exception e)
        {
            logger.error("getCurCoachState:" + coachId,
                    e);
        }
        return COACH_STATUS_OFF_WORK;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.lili.logic.service.ICoachStateManager#handleDoStatus(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public ReqResult handleDoStatus(String coachId, String carId, String courses, String status, String tokenId)
    {
        return getCurCoachState(Long.parseLong(coachId)).handleDoStatus(coachId, carId, courses, status, tokenId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.lili.logic.service.ICoachStateManager#handleAddOrder(com.lili.order.vo.OrderVo)
     */
    @Override
    public ReqResult handleAddOrder(OrderVo orderVo)
    {
        long coachId = orderVo.getCoachId();
        return getCurCoachState(coachId).handleAddOrder(orderVo);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.lili.logic.service.ICoachStateManager#handleAcceptOrder(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @Override
    public ReqResult handleAcceptOrder(String orderId, String coachId, String studentId, String tokenId)
    {
        return getCurCoachState(Long.parseLong(coachId)).handleAcceptOrder(orderId, coachId, studentId, tokenId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.lili.logic.service.ICoachStateManager#handleDoCourseStatus(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @Override
    public ReqResult handleDoCourseStatus(String coachId, String orderId, String studentId, String status, String tokenId)
    {
        ReqResult r = ReqResult.getFailed();
        OrderVo orderVo;
        try
        {
            orderVo = orderService.queryOrderById(orderId,
                    new OrderQuery());
            //20160322如果是教练要上课，则先检查订单的状态为可以上课
            if(Integer.parseInt(status.trim())==ReqConstants.COACH_STATUS_ON_CLASS){
            	if(orderVo.getOrderState() != OrderConstant.ORDERSTATE.ACCEPTORDER ){
            		return r;
            	}
            }
            if (orderVo.getOtype() == OrderConstant.OTYPE.NOWORDER)
            {
                return getCurCoachState(orderVo.getCoachId()).handleDoCourseStatus(
                        String.valueOf(orderVo.getCoachId()), orderId, studentId, status, tokenId);
            }
        }
        catch (Exception e)
        {
            logger.error("", e);
        }
        return r;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.lili.logic.service.ICoachStateManager#handleOrderOverTime(com.lili.order.vo.OrderVo)
     */
    @Override
    public void handleOrderOverTime(OrderVo orderVo)
    {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.lili.logic.service.ICoachStateManager#handleCancelOrder(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public ReqResult handleCancelOrder(String orderId, String retype, String reason, String userId, String userType,
            String tokenId, boolean haveCancel)
    {
        ReqResult reqResult = ReqResult.getFailed();
        OrderVo orderVo = null;
        try
        {
            orderVo = orderService.queryOrderById(orderId,
                    new OrderQuery());
            if (orderVo.getOtype() == OrderConstant.OTYPE.BOOKORDER) // 预约不走状态机
            {
                return orderLogic.cancelOrder(orderId, retype, reason, userId, userType, tokenId);
            }
            return getCurCoachState(orderVo.getCoachId()).handleCancelOrder(orderVo, retype, reason, String.valueOf(orderVo.getCoachId()),
                    userType, tokenId, haveCancel);
        }
        catch (Exception e)
        {
            logger.error("handleCancelOrder:" + orderVo, e);
        }
        return reqResult;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.lili.logic.service.ICoachStateManager#handleRefuseOrder(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @Override
    public ReqResult handleRefuseOrder(String orderId, String userId, String reason, String tokenId)
    {
        return getCurCoachState(Long.parseLong(userId)).handleRefuseOrder(orderId, userId, reason, tokenId);
    }

    /**
     * @return the stateMaps
     */
    public Map<Integer, ICoachState> getStateMaps()
    {
        return stateMaps;
    }

    /**
     * @param stateMaps
     *            the stateMaps to set
     */
    public void setStateMaps(Map<Integer, ICoachState> stateMaps)
    {
        this.stateMaps = stateMaps;
    }

    /* (non-Javadoc)
     * @see com.lili.logic.service.ICoachStateManager#genStateContextDesc(java.lang.String)
     */
    @Override
    public ReqResult genStateContextDesc(String userId)
    {
        ReqResult reqResult = getCurCoachState(Long.parseLong(userId)).getCurStateContext(userId);
        UserStateVo stateVo = (UserStateVo) reqResult.getResult().get(ResultCode.RESULTKEY.DATA);
        logger.info("reqResult:" + stateVo.getState() + ":" + stateVo.getOrderVo());
        return reqResult;
    }
}
