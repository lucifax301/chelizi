/**
 * 
 */
package com.lili.logic.student.state;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.logic.service.OrderLogic;
import com.lili.logic.state.AbstractStudentState;
import com.lili.logic.state.CoachStateManager;
import com.lili.order.vo.OrderVo;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;
import com.lili.student.vo.UserStateVo;

/**
 * @author linbo
 *
 */
public class WaitAcceptOrderState extends AbstractStudentState
{
    @Autowired
    private OrderLogic orderLogic;
    
    @Autowired
    private StudentManager studentMananger;
    
    @Autowired
    private CoachStateManager coachStateManager;
    
    /**
     * 教练成功接单后，学员的状态变更成上课准备
     */
    @Override
    public ReqResult handleAcceptOrder(String orderId, String userId, String studentId, String tokenId)
    {
        ReqResult reqResult = ReqResult.getSuccess();
        //状态变更
        Student info = studentMananger.getStudentInfo(Long.parseLong(studentId));
        info.setEventState((byte)ReqConstants.STUDNET_PREPARE_START_CLASS);
        info.setEventDesc(orderId);
        studentMananger.updateStudent(info);
        return reqResult;
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
                Student info = studentMananger.getStudentInfo(orderVo.getStudentId());
                info.setEventState((byte)ReqConstants.STUDENT_STATUS_OFF_CLASS);
                info.setEventDesc(orderVo.getOrderId());
                studentMananger.updateStudent(info);
            }
            else
            {
                if (userType.equals("2"))
                {
                    r = orderLogic.cancelOrder(orderVo.getOrderId(), retype, reason, userId, userType, tokenId);
                }

                if (r.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS))
                {
                    // 状态变更
                    Student info = studentMananger.getStudentInfo(Long.parseLong(userId));
                    info.setEventState((byte)ReqConstants.STUDENT_STATUS_OFF_CLASS);
                    info.setEventDesc(orderVo.getOrderId());
                    studentMananger.updateStudent(info);
                    
                    r = coachStateManager.handleCancelOrder(orderVo.getOrderId(), retype, reason, userId, userType, tokenId, true);
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

    /*
     * (non-Javadoc)
     * 
     * @see com.lili.logic.state.IStudentState#handleRefuseOrder(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public ReqResult handleRefuseOrder(String orderId, String studentId, String reason, String tokenId)
    {
        ReqResult reqResult = ReqResult.getSuccess();
        //状态变更
        Student info = studentMananger.getStudentInfo(Long.parseLong(studentId));
        info.setEventState((byte)ReqConstants.STUDENT_STATUS_OFF_CLASS);
        info.setEventDesc(orderId);
        studentMananger.updateStudent(info);
        return reqResult;
    }

    /* (non-Javadoc)
     * @see com.lili.logic.service.IStudentState#getCurStateContext(java.lang.String)
     */
    @Override
    public ReqResult getCurStateContext(String studentId)
    {
        ReqResult r = super.getCurStateContext(studentId);
        UserStateVo stateVo = (UserStateVo) r.getResult().get(ResultCode.RESULTKEY.DATA);
        if (stateVo.getState() == ReqConstants.STUDENT_STATUS_OFF_CLASS)
        {
            stateVo.setState(ReqConstants.STUDENT_WAIT_ACCEPT_ORDER);
            OrderVo orderVo = getCurOrderVo(Long.parseLong(studentId));
            stateVo.setOrderVo(orderVo);
            r.setData(stateVo);
        }
        return r;
    }
}
