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
 *         现约上课准备
 */
public class PrepareStartClassState extends AbstractStudentState
{
    @Autowired
    private OrderLogic orderLogic;

    @Autowired
    private StudentManager studentMananger;
    
    @Autowired
    private CoachStateManager coachStateManager;

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
                    
                    r = coachStateManager.handleCancelOrder(orderVo.getOrderId(), retype, reason, String.valueOf(orderVo.getCoachId()), "1", tokenId, true);
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
     * @see
     * com.lili.logic.state.IStudentState#handleJPushNotifyStartClass(com.alibaba.rocketmq.common.message.MessageExt)
     */
    @Override
    public ReqResult handleDoCourseStatus(String userId, String orderId,
            String coachId, String status)
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
        //只能接受教练上课的消息
        if (st == ReqConstants.COACH_STATUS_ON_CLASS)
        {
            // 如果是上课消息
            Student info = studentMananger.getStudentInfo(Long.parseLong(userId));
            info.setEventState((byte)ReqConstants.STUDENT_STATUS_ON_CLASS);
            info.setEventDesc(orderId);
            studentMananger.updateStudent(info);
            r = ReqResult.getSuccess();
        }
        
        return r;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.lili.logic.service.IStudentState#getCurStateContext(java.lang.String)
     */
    @Override
    public ReqResult getCurStateContext(String studentId)
    {
        ReqResult r = super.getCurStateContext(studentId);
        UserStateVo stateVo = (UserStateVo) r.getResult().get(ResultCode.RESULTKEY.DATA);
        if (stateVo.getState() == ReqConstants.STUDENT_STATUS_OFF_CLASS)
        {
            stateVo.setState(ReqConstants.STUDNET_PREPARE_START_CLASS);
            OrderVo orderVo = getCurOrderVo(Long.parseLong(studentId));
            stateVo.setOrderVo(orderVo);
            r.setData(stateVo);
        }
        return r;
    }

}
