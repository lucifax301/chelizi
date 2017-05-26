/**
 * 
 */
package com.lili.logic.state;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.constant.OrderConstant;
import com.lili.common.util.TimeUtil;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.logic.service.IStudentState;
import com.lili.order.service.OrderService;
import com.lili.order.vo.OrderQuery;
import com.lili.order.vo.OrderVo;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;
import com.lili.student.vo.UserStateVo;

/**
 * @author linbo
 *
 */
public abstract class AbstractStudentState implements IStudentState
{
    public static Logger logger = LoggerFactory.getLogger(AbstractStudentState.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private StudentManager studentMananger;
    /*
     * (non-Javadoc)
     * 
     * @see com.lili.logic.state.IStudentState#handleAddOrder(com.lili.order.vo.OrderVo, java.lang.String)
     */
    @Override
    public ReqResult handleAddOrder(OrderVo orderVo, String tokenId)
    {
        ReqResult reqResult = ReqResult.getFailed();
        reqResult.setCode(ResultCode.ERRORCODE.STUDENT_STATE__NOT_SUPPORT);
        logger.error("curState:" + this.getClass().getName() + "|curAction:handleAddOrder" + "orderState:" + orderVo);
        return reqResult;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.lili.logic.state.IStudentState#handleCancelOrder(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public ReqResult handleCancelOrder(OrderVo orderId, String retype, String reason, String userId, String userType,
            String tokenId, boolean haveCancel)
    {
        ReqResult reqResult = ReqResult.getFailed();
        reqResult.setCode(ResultCode.ERRORCODE.STUDENT_STATE__NOT_SUPPORT);
        logger.error("curState:" + this.getClass().getName() + "|curAction:handleCancelOrder" + "orderState:" + orderId
                + ":" + retype + ":" + reason + ":" + userId + ":" + userType);
        return reqResult;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.lili.logic.state.IStudentState#handleRefuseOrder(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String)
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

    /*
     * (non-Javadoc)
     * 
     * @see com.lili.logic.state.IStudentState#handleAcceptOrder(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public ReqResult handleAcceptOrder(String orderId, String userId, String studentId, String tokenId)
    {
        ReqResult reqResult = ReqResult.getFailed();
        reqResult.setCode(ResultCode.ERRORCODE.STUDENT_STATE__NOT_SUPPORT);
        logger.error("curState:" + this.getClass().getName() + "|curAction:handleAcceptOrder" + "orderState:" + orderId
                + ":" + studentId + ":" + userId);
        return reqResult;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.lili.logic.state.IStudentState#handleContinueOrder(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @Override
    public ReqResult handleContinueOrder(String orderId, String userId, String clzNum, String price, String tokenId)
    {
        ReqResult reqResult = ReqResult.getFailed();
        reqResult.setCode(ResultCode.ERRORCODE.STUDENT_STATE__NOT_SUPPORT);
        logger.error("curState:" + this.getClass().getName() + "|curAction:handleContinueOrder" + "orderState:"
                + orderId
                + ":" + clzNum + ":" + userId + ":" + price);
        return reqResult;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.lili.logic.state.IStudentState#handleJPushNotifyStartClass(com.alibaba.rocketmq.common.message.MessageExt)
     */
    @Override
    public ReqResult handleDoCourseStatus(String userId, String orderId,
            String coachId, String status)
    {
        ReqResult reqResult = ReqResult.getFailed();
        reqResult.setCode(ResultCode.ERRORCODE.STUDENT_STATE__NOT_SUPPORT);
        logger.error("curState:" + this.getClass().getName() + "|curAction:handleJPushNotifyStartClass" + "message:"
                + userId + ":" + orderId);
        return reqResult;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.lili.logic.service.IStudentState#getCurStateContext(java.lang.String)
     */
    /**
     * 取预约订单状态
     */
    @Override
    public ReqResult getCurStateContext(String studentId)
    {
        ReqResult r = ReqResult.getSuccess();
        UserStateVo stateVo = new UserStateVo();
        stateVo.setState(ReqConstants.STUDENT_STATUS_OFF_CLASS);
        try
        {
            // 最近的一个预约订单
            OrderVo orderVo = orderService.getStuLastBooked(Long.parseLong(studentId));
            stateVo.setOrderVo(orderVo);
            stateVo.setState(parseBookOrderState(orderVo));
            r.setData(stateVo);
        }
        catch (Exception e)
        {
            logger.error("studentId:" + studentId, e);
        }
        return r;
    }

    public static int parseBookOrderState(OrderVo orderVo)
    {
        if (orderVo == null || orderVo.getOtype() != OrderConstant.OTYPE.BOOKORDER)
        {
            return ReqConstants.STUDENT_STATUS_OFF_CLASS;
        }

        // 当前时间距离预约订单的开始时间
        long curTimeGap = TimeUtil.calcDistanceMillis(new Date(), orderVo.getPstart());
        if (orderVo.getRstart() != null)
        {
            curTimeGap = TimeUtil.calcDistanceMillis(new Date(), orderVo.getRstart());
        }

        // 如果小于75分钟,表示预约准备上课
        if (curTimeGap < 4505000 && curTimeGap > 2000)
        {
            return ReqConstants.STUDENT_BOOK_CLASS_PREPARE;
        }
        else if (curTimeGap <= 2000)
        {
            curTimeGap = TimeUtil.calcDistanceMillis(new Date(), orderVo.getPend());
            if (orderVo.getRend() != null)
            {
                curTimeGap = TimeUtil.calcDistanceMillis(new Date(), orderVo.getRend());
            }
            if (curTimeGap > 0)
            {
                orderVo.setOrderState(OrderConstant.ORDERSTATE.INCLASS);
                return ReqConstants.STUDENT_BOOK_CLASS_IN;
            }
            else
            {
                logger.error(orderVo + " is over,but state is not over");
            }
        }

        return ReqConstants.STUDENT_STATUS_OFF_CLASS;
    }
    
    public OrderVo getCurOrderVo(long studentId)
    {
        Student info = studentMananger.getStudentInfo(studentId);
        String orderId = info.getEventDesc();
        if (orderId != null && !orderId.isEmpty())
        {
            try
            {
                OrderVo orderVo = orderService.queryOrderById(orderId,
                        new OrderQuery());
                return orderVo;
            }
            catch (Exception e)
            {
                logger.error("stuId:" + studentId, e);
            }
        }
        return null;
    }
}
