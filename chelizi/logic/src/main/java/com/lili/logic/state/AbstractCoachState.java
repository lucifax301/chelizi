/**
 * 
 */
package com.lili.logic.state;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.coach.dto.Coach;
import com.lili.coach.manager.CoachManager;
import com.lili.common.constant.OrderConstant;
import com.lili.common.util.TimeUtil;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.logic.service.ICoachState;
import com.lili.order.service.OrderService;
import com.lili.order.service.PlantClassService;
import com.lili.order.vo.OrderQuery;
import com.lili.order.vo.OrderVo;
import com.lili.order.vo.PlantClassQuery;
import com.lili.order.vo.PlantClassVo;
import com.lili.student.vo.UserStateVo;

/**
 * @author linbo
 *
 */
public class AbstractCoachState implements ICoachState
{
    public static Logger logger = LoggerFactory.getLogger(AbstractCoachState.class);

    @Autowired
    protected OrderService orderService;

    @Autowired
    private CoachManager coachManager;

    @Autowired
    private PlantClassService plantClassService;
    
    /* (non-Javadoc)
     * @see com.lili.logic.service.ICoachState#handleDoStatus(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public ReqResult handleDoStatus(String coachId, String carId, String courses, String status, String tokenId)
    {
        ReqResult reqResult = ReqResult.getFailed();
        reqResult.setCode(ResultCode.ERRORCODE.STUDENT_STATE__NOT_SUPPORT);
        logger.error("curState:" + this.getClass().getName() + "|curAction:handleDoStatus" + "message:"
                + coachId + ":" + carId + ":" + courses + ":" + status);
        return reqResult;
    }

    /* (non-Javadoc)
     * @see com.lili.logic.service.ICoachState#handleAddOrder(com.lili.order.vo.OrderVo)
     */
    @Override
    public ReqResult handleAddOrder(OrderVo orderVo)
    {
        ReqResult reqResult = ReqResult.getFailed();
        reqResult.setCode(ResultCode.ERRORCODE.STUDENT_STATE__NOT_SUPPORT);
        logger.error("curState:" + this.getClass().getName() + "|curAction:handleAddOrder" + "message:"
                + orderVo.toString());
        return reqResult;
    }

    /* (non-Javadoc)
     * @see com.lili.logic.service.ICoachState#handleAcceptOrder(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public ReqResult handleAcceptOrder(String orderId, String coachId, String studentId, String tokenId)
    {
        ReqResult reqResult = ReqResult.getFailed();
        reqResult.setCode(ResultCode.ERRORCODE.STUDENT_STATE__NOT_SUPPORT);
        logger.error("curState:" + this.getClass().getName() + "|curAction:handleAcceptOrder" + "message:"
                + orderId + ":" + coachId + ":" + studentId);
        return reqResult;
    }

    /* (non-Javadoc)
     * @see com.lili.logic.service.ICoachState#handleDoCourseStatus(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public ReqResult handleDoCourseStatus(String coachId, String orderId, String studentId, String status, String tokenId)
    {
        ReqResult reqResult = ReqResult.getFailed();
        reqResult.setCode(ResultCode.ERRORCODE.STUDENT_STATE__NOT_SUPPORT);
        logger.error("curState:" + this.getClass().getName() + "|curAction:handleDoCourseStatus" + "message:"
                + studentId + ":" + orderId + ":" + coachId + ":" + status);
        return reqResult;
    }

    /* (non-Javadoc)
     * @see com.lili.logic.service.ICoachState#handleOrderOverTime(com.lili.order.vo.OrderVo)
     */
    @Override
    public void handleOrderOverTime(OrderVo orderVo)
    {
        logger.error("curState:" + this.getClass().getName() + "|curAction:handleOrderOverTime" + "message:"
                + orderVo.toString());
    }

    /* (non-Javadoc)
     * @see com.lili.logic.service.ICoachState#handleCancelOrder(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public ReqResult handleCancelOrder(OrderVo orderVo, String retype, String reason, String userId, String userType,
            String tokenId, boolean haveCancel)
    {
        ReqResult reqResult = ReqResult.getFailed();
        reqResult.setCode(ResultCode.ERRORCODE.STUDENT_STATE__NOT_SUPPORT);
        logger.error("curState:" + this.getClass().getName() + "|curAction:handleCancelOrder" + "orderState:" + orderVo
                + ":" + retype + ":" + reason + ":" + userId + ":" + userType);
        return reqResult;
    }

    /* (non-Javadoc)
     * @see com.lili.logic.service.ICoachState#handleRefuseOrder(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public ReqResult handleRefuseOrder(String orderId, String userId, String reason, String tokenId)
    {
        ReqResult reqResult = ReqResult.getFailed();
        reqResult.setCode(ResultCode.ERRORCODE.STUDENT_STATE__NOT_SUPPORT);
        logger.error("curState:" + this.getClass().getName() + "|curAction:handleRefuseOrder" + "orderState:" + orderId
                + ":" + reason + ":" + userId);
        return reqResult;
    }

    /* (non-Javadoc)
     * @see com.lili.logic.service.ICoachState#getCurStateContext(java.lang.String)
     */
    @Override
    public ReqResult getCurStateContext(String coachId)
    {
        ReqResult r = ReqResult.getSuccess();
        UserStateVo stateVo = new UserStateVo();
        stateVo.setState(ReqConstants.COACH_STATUS_OFF_WORK);
        try
        {
            // 最近的一个预约订单
            OrderVo orderVo = orderService.getCoachLastBooked(Long.parseLong(coachId));
            
            if (orderVo != null && orderVo.getOtype() == OrderConstant.OTYPE.BOOKORDER) {
            	//返回的排版只能是有效的
				PlantClassVo search=new PlantClassVo();
				search.setCcid(orderVo.getCcid());
				search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
                List<PlantClassVo> plist = plantClassService.queryByObjectAnd(search, new PlantClassQuery());
                orderVo.setPlantClassList(plist);
            }
            stateVo.setState(parseBookOrderState(orderVo));
            stateVo.setOrderVo(orderVo);
            r.setData(stateVo);
        }
        catch (Exception e)
        {
            logger.error("coachId:" + coachId, e);
        }
        return r;
    }
    
    public int parseBookOrderState(OrderVo orderVo)
    {
        if (orderVo == null || orderVo.getOtype() != OrderConstant.OTYPE.BOOKORDER)
        {
            return ReqConstants.COACH_STATUS_OFF_WORK;
        }

        // 当前时间距离预约订单的开始时间
        Date curTime = new Date();
        long curTimeGap = TimeUtil.calcDistanceMillis(curTime, orderVo.getPstart());
        if (orderVo.getRstart() != null)
        {
            curTimeGap = TimeUtil.calcDistanceMillis(curTime, orderVo.getRstart());
        }

        logger.error("curTimeGap:" + curTimeGap / 1000 + ":curTime:" + curTime);
        // 如果小于75分钟,表示预约准备上课(多2秒)
        if (curTimeGap < 4505000 && curTimeGap > 2000)
        {
            return ReqConstants.COACH_BOOK_CLASS_PREPARE;
        }
        else if (curTimeGap <= 2000)
        {
            curTimeGap = TimeUtil.calcDistanceMillis(curTime, orderVo.getPend());
            if (orderVo.getRend() != null)
            {
                curTimeGap = TimeUtil.calcDistanceMillis(curTime, orderVo.getRend());
            }
            if (curTimeGap > 0)
            {
                orderVo.setOrderState(OrderConstant.ORDERSTATE.INCLASS);
                return ReqConstants.COACH_BOOK_CLASS_IN;
            }
            else
            {
                logger.error(orderVo + " is over,but state is not over");
            }
        }

        return ReqConstants.COACH_STATUS_OFF_WORK;
    }
    
    protected String removeCancelOrderFromStateContext(String stateContext, String orderId)
    {
        if (stateContext == null || stateContext.isEmpty())
        {
            logger.error("stateContext is null when coach cancel order", new NullPointerException());
            return "";
        }
        String[] orderIds = stateContext.split("\\|");
        List<String> temps = new ArrayList<>();
        boolean haveFound = false;
        for (String string : orderIds)
        {
            if (string != null && !string.isEmpty())
            {
                if (string.equals(orderId))
                {
                    haveFound = true;
                }
                else
                {
                    temps.add(string);
                }
            }
        }
        
        if (haveFound)
        {
            StringBuffer tempOrderIds = new StringBuffer();
            for (int i = 0; i < temps.size() - 1; i++)
            {
                tempOrderIds.append(temps.get(i));
                tempOrderIds.append("|");
            }
            if (temps.size() >= 1)
            {
                tempOrderIds.append(temps.get(temps.size() - 1));
            }
            return tempOrderIds.toString();
        }
        else
        {
            logger.info("removeCancelOrderFromStateContext:" + stateContext + ":" + orderId);
        }
        return stateContext;
    }

    public OrderVo getCurOrderVo(Coach info)
    {
        String orderIds = info.getEventDesc();
        if (orderIds != null && !orderIds.isEmpty())
        {
            try
            {
                String [] orders = orderIds.split("\\|");
                String order = orders[0];
                OrderVo orderVo = orderService.queryOrderById(order,
                        new OrderQuery());
                return orderVo;
            }
            catch (Exception e)
            {
                logger.error("coachId:" + info, e);
            }
        }
        return null;
    }
}
