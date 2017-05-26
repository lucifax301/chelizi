/**
 * 
 */
package com.lili.logic.student.state;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.constant.OrderConstant;
import com.lili.common.util.TimeUtil;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.logic.state.AbstractStudentState;
import com.lili.order.service.OrderService;
import com.lili.order.vo.OrderVo;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;
import com.lili.student.service.StudentService;
import com.lili.student.vo.UserStateVo;

import org.springframework.stereotype.Service;

/**
 * @author linbo
 *         现约上课中
 */
@Service
public class DuringClassState extends AbstractStudentState
{
    @Autowired
    private StudentManager studentMananger;

    @Autowired
    private StudentService studentService;

    @Autowired
    private OrderService orderService;
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
        // Student s = new Student();
        // s.setStudentId(Long.parseLong(userId));

        // 20151130目前只允许学员点下课
        if (st == ReqConstants.STUDENT_STATUS_OFF_CLASS)
        {
            r = studentService.doCourseStatus(userId, orderId, coachId, status);

            if (r.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS))
            {
                Student info = studentMananger.getStudentInfo(Long.parseLong(userId));
                info.setEventState((byte)ReqConstants.STUDENT_STATUS_OFF_CLASS);
                info.setEventDesc(orderId);
                studentMananger.updateStudent(info);
            }
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
        if (stateVo.getState() != ReqConstants.STUDENT_BOOK_CLASS_IN)
        {
            stateVo.setState(ReqConstants.STUDENT_STATUS_ON_CLASS);
            OrderVo orderVo = getCurOrderVo(Long.parseLong(studentId));
            if (orderVo == null)
            {
                logger.error("order is null but state is during class:" + studentId);
                stateVo.setState(ReqConstants.STUDENT_STATUS_OFF_CLASS);
                
                Student student = new Student();
                student.setStudentId(Long.parseLong(studentId));
                student.setEventState((byte)ReqConstants.STUDENT_STATUS_OFF_CLASS);
                student.setEventDesc("");
                studentMananger.updateStudent(student);
                
                stateVo.setOrderVo(orderVo);
                r.setData(stateVo);
                return r;
            }
            Date now = new Date();
            long curTimeGap = TimeUtil.calcDistanceMillis(now, orderVo.getPend());
            if (orderVo.getRend() != null)
            {
                curTimeGap = TimeUtil.calcDistanceMillis(now, orderVo.getRend());
            }
            //允许2秒误差
            if (curTimeGap < 2000 && orderVo.getOrderState() == OrderConstant.ORDERSTATE.INCLASS)
            {
                Student student = new Student();
                student.setStudentId(Long.parseLong(studentId));
                student.setEventState((byte)ReqConstants.STUDENT_STATUS_OFF_CLASS);
                student.setEventDesc("");
                studentMananger.updateStudent(student);
                
                stateVo.setState(ReqConstants.STUDENT_STATUS_OFF_CLASS);
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
        return r;
    }
}
