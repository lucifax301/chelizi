/**
 * 
 */
package com.lili.logic.student.state;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.StringUtil;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.logic.service.ICoachStateManager;
import com.lili.logic.service.OrderLogic;
import com.lili.logic.state.AbstractStudentState;
import com.lili.order.service.OrderService;
import com.lili.order.vo.OrderVo;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;

/**
 * @author linbo
 *
 */
public class StudentRestState extends AbstractStudentState
{
    @Autowired
    private OrderLogic orderLogic;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private StudentManager studentMananger;
    
    @Autowired
    private ICoachStateManager coachStateManager;
    
    @Override
    public ReqResult handleAddOrder(OrderVo orderVo, String tokenId)
    {
        ReqResult r = ReqResult.getFailed();
        try
        {	//先查看教练状态是否正常，再生成订单
			String orderId=orderVo.getOrderId();
			if(orderId==null|| orderId.length()<32) {
				orderId=StringUtil.getOrderId();
				orderVo.setOrderId(orderId);
			}
        	r = orderLogic.addOrder(orderVo, tokenId);
        	if (r.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)){
                if (r.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS))
                {
                    Student info = studentMananger.getStudentInfo(orderVo.getStudentId());
                    //状态变更
                    //info.setEventState(ReqConstants.STUDENT_WAIT_ACCEPT_ORDER);
                    info.setEventState((byte)ReqConstants.STUDNET_PREPARE_START_CLASS);
                    info.setEventDesc(orderVo.getOrderId());
                    studentMananger.updateStudent(info);
                    //教练执行状态变更
                    r = coachStateManager.handleAddOrder(orderVo);
                    r.setData(orderVo.getOrderId());
                }
        	}
            
        }
        catch (Exception e)
        {
            logger.error("handleAddOrder:" + orderVo, e);
            r.setCode(ResultCode.ERRORCODE.EXCEPTION);
            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }
        return r;
    }

    /* (non-Javadoc)
     * @see com.lili.logic.service.IStudentState#getCurStateContext(java.lang.String)
     */
    @Override
    public ReqResult getCurStateContext(String studentId)
    {
        ReqResult r = super.getCurStateContext(studentId);
        return r;
    }

}
