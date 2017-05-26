/**
 * 
 */
package com.lili.logic.state;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.constant.OrderConstant;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.logic.service.IStudentStateManager;
import com.lili.logic.service.IStudentState;
import com.lili.logic.service.OrderLogic;
import com.lili.order.service.OrderService;
import com.lili.order.vo.OrderQuery;
import com.lili.order.vo.OrderVo;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;
import com.lili.student.service.StudentService;
import com.lili.student.vo.UserStateVo;

/**
 * @author linbo
 *         学员的状态机入口
 */
public class StudentStateManager implements IStudentStateManager
{
    @Autowired
    private StudentManager studentManager;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderLogic orderLogic;
    @Autowired
    private StudentService studentService;
    private static Logger logger = LoggerFactory.getLogger(StudentStateManager.class);

    // 学员状态
    @Resource(name = "duringClassState")
    public IStudentState DURING_CLASS_STATE;

    @Resource(name = "prepareStartClassState")
    public IStudentState PREPARE_START_CLASS_STATE;

    @Resource(name = "studentRestState")
    public IStudentState STUDENT_REST_STATE;

    @Resource(name = "waitAcceptOrderState")
    public IStudentState WAIT_ACCEPT_ORDER_STATE;

    private Map<Byte, IStudentState> stateMaps;

    /*
     * (non-Javadoc)
     * 
     * @see com.lili.logic.state.IStateManager#getCurStudentState(long)
     */
    public IStudentState getCurStudentState(long studentId)
    {
        Student student = studentManager.getStudentInfo(studentId);
        try
        {
            IStudentState studentState = stateMaps.get(student.getEventState());
            if (studentState != null)
            {
                return studentState;
            }
            else
            {
                logger.error("getCurStudentState:" + studentId + "|state:" + student.getEventState(),
                        new NullPointerException());
            }
        }
        catch (Exception e)
        {
            logger.error("getCurStudentState:" + studentId,
                    e);
        }
        return STUDENT_REST_STATE;
    }

    public ReqResult getBookState(String studentId)
    {
        ReqResult r = ReqResult.getSuccess();
        UserStateVo stateVo = new UserStateVo();
        stateVo.setState(ReqConstants.STUDENT_STATUS_OFF_CLASS);
        try
        {
            // 最近的一个预约订单
            OrderVo orderVo = orderService.getStuLastBooked(Long.parseLong(studentId));
            stateVo.setOrderVo(orderVo);
            stateVo.setState(AbstractStudentState.parseBookOrderState(orderVo));
            r.setData(stateVo);
        }
        catch (Exception e)
        {
            logger.error("studentId:" + studentId, e);
        }
        return r;
    }
    /*
     * (non-Javadoc)
     * 
     * @see com.lili.logic.service.IStateManager#handleAddOrder(java.lang.String, com.lili.order.vo.OrderVo,
     * java.lang.String)
     */
    @Override
    public ReqResult handleAddOrder(String studentId, OrderVo orderVo, String tokenId)
    {
        return getCurStudentState(Long.parseLong(studentId)).handleAddOrder(orderVo, tokenId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.lili.logic.service.IStateManager#handleCancelOrder(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public ReqResult handleCancelOrder(String orderId, String retype, String reason, String userId, String userType,
            String tokenId, boolean haveCancel)
    {
        ReqResult r = ReqResult.getFailed();

        OrderVo orderVo = null;
        try
        {
            orderVo = orderService.queryOrderById(orderId,
                    new OrderQuery());
            if (orderVo.getOtype()==OrderConstant.OTYPE.BOOKORDER) //预约不走状态机
            {
                return orderLogic.cancelOrder(orderId, retype, reason,userId,userType,tokenId);
            }
            return getCurStudentState(orderVo.getStudentId()).handleCancelOrder(orderVo, retype, reason,
                    String.valueOf(orderVo.getStudentId()), userType, tokenId, haveCancel);
        }
        catch (Exception e)
        {
            logger.error("handleCancelOrder:" + orderVo, e);
        }
        return r;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.lili.logic.service.IStateManager#handleRefuseOrder(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @Override
    public ReqResult handleRefuseOrder(String orderId, String userId, String reason, String tokenId)
    {
        OrderVo orderVo;
        try
        {
            orderVo = orderService.queryOrderById(orderId,
                    new OrderQuery());
            return getCurStudentState(orderVo.getStudentId()).handleRefuseOrder(orderId,
                    String.valueOf(orderVo.getStudentId()), reason, tokenId);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.lili.logic.service.IStateManager#handleAcceptOrder(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public ReqResult handleAcceptOrder(String orderId, String userId, String studentId, String tokenId)
    {
        return getCurStudentState(Long.parseLong(studentId)).handleAcceptOrder(orderId, userId, studentId, tokenId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.lili.logic.service.IStateManager#handleContinueOrder(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public ReqResult handleContinueOrder(String orderId, String userId, String clzNum, String price, String tokenId)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.lili.logic.service.IStateManager#handleJPushNotifyStartClass(com.lili.event.vo.CourseStatusEventVo)
     */
    @Override
    public ReqResult handleDoCourseStatus(String userId, String orderId,
            String coachId, String status)
    {
        ReqResult r = ReqResult.getFailed();
        OrderVo orderVo;
        try
        {
            orderVo = orderService.queryOrderById(orderId,
                    new OrderQuery());
            //20160322学员点击下课时，添加校验订单状态为上课中
            if(Integer.parseInt(status.trim())==ReqConstants.STUDENT_STATUS_OFF_CLASS){
            	if(orderVo.getOrderState() != OrderConstant.ORDERSTATE.INCLASS){
                    logger.error("curState:" + this.getClass().getName() + "|handleDoCourseStatus" + "message:"
                            + userId + ":" + orderId);
            		r.setCode(ResultCode.ERRORCODE.STUDENT_STATE__NOT_SUPPORT);
            		r.setMsgInfo(ResultCode.ERRORINFO.STUDENT_STATE__NOT_SUPPORT);
            		return r;
            	}
            }
            if (orderVo.getOtype() == OrderConstant.OTYPE.BOOKORDER && status.equals("0"))
            {
                ReqResult reqResult = getBookState(userId);
                UserStateVo stateVo = (UserStateVo) reqResult.getResult().get(ResultCode.RESULTKEY.DATA);
                if (stateVo.getState() == ReqConstants.STUDENT_BOOK_CLASS_IN)
                {
                    return studentService.doCourseStatus(String.valueOf(orderVo.getStudentId()), orderId, coachId, status);
                }
                else
                {
                    r = ReqResult.getFailed();
                    r.setCode(ResultCode.ERRORCODE.STUDENT_STATE__NOT_SUPPORT);
                    logger.error("curState:" + this.getClass().getName() + "|handleDoCourseStatus" + "message:"
                            + userId + ":" + orderId);
                    return r;
                }
            }
            return getCurStudentState(orderVo.getStudentId()).handleDoCourseStatus(
                    String.valueOf(orderVo.getStudentId()), orderId, coachId, status);
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
     * @see com.lili.logic.service.IStateManager#genStateContextDesc(java.lang.String)
     */
    @Override
    public ReqResult genStateContextDesc(String studentId)
    {
        return getCurStudentState(Long.parseLong(studentId)).getCurStateContext(studentId);
    }

    /**
     * @return the stateMaps
     */
    public Map<Byte, IStudentState> getStateMaps()
    {
        return stateMaps;
    }

    /**
     * @param stateMaps
     *            the stateMaps to set
     */
    public void setStateMaps(Map<Byte, IStudentState> stateMaps)
    {
        this.stateMaps = stateMaps;
    }
}
