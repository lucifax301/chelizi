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
public interface IStudentState
{
    /**
     * 现约下单消息
     * @param orderVo
     * @param tokenId
     * @return
     */
    public ReqResult handleAddOrder(OrderVo orderVo, String tokenId);

    /**
     * 取消订单
     * @param orderId
     * @param retype
     * @param reason
     * @param userId
     * @param userType
     * @param tokenId
     * @return
     */
    public ReqResult handleCancelOrder(OrderVo orderId, String retype, String reason, String userId,String userType, String tokenId, boolean haveCancel);
    
    /**
     * 拒绝订单
     * @param orderId
     * @param userId
     * @param reason
     * @param tokenId
     * @return
     */
    public ReqResult handleRefuseOrder(String orderId,String userId, String reason, String tokenId);
    
    /**
     * 收到教练接单消息
     * @param orderId
     * @param userId
     * @param studentId
     * @param tokenId
     * @return
     */
    public ReqResult handleAcceptOrder(String orderId, String userId, String studentId, String tokenId);
    
    /**
     * 续课消息
     * @param orderId
     * @param userId
     * @param clzNum
     * @param price
     * @param tokenId
     * @return
     */
    public ReqResult handleContinueOrder(String orderId, String userId, String clzNum, String price, String tokenId);
    
    /**
     * 收到通知上下课消息
     */
    public ReqResult handleDoCourseStatus(String userId, String orderId,
            String coachId, String status);
    
    /**
     * 取当前状态的上下文
     * @param studentId
     * @return
     */
    public ReqResult getCurStateContext(String studentId);
 
}
