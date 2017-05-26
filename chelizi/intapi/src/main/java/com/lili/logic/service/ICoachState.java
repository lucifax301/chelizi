/**
 * 
 */
package com.lili.logic.service;

import com.lili.common.vo.ReqResult;
import com.lili.order.vo.OrderVo;

/**
 * @author linbo
 *
 */
public interface ICoachState
{
    public ReqResult handleDoStatus(String coachId,String carId,String courses, String status, String tokenId);
    
    public ReqResult handleAddOrder(OrderVo orderVo);
    
    public ReqResult handleAcceptOrder(String orderId,String coachId,
            String studentId,String tokenId);
    
    public ReqResult handleDoCourseStatus(String userId, String orderId,
            String coachId, String status, String tokenId);
    
    public void handleOrderOverTime(OrderVo orderVo);
    
    public ReqResult handleCancelOrder(OrderVo orderVo, String retype, String reason, String userId,String userType, String tokenId, boolean haveCancel);
    
    public ReqResult handleRefuseOrder(String orderId,String userId, String reason, String tokenId);
    
    /**
     * 取当前状态的上下文
     * @param studentId
     * @return
     */
    public ReqResult getCurStateContext(String coachId);
}
