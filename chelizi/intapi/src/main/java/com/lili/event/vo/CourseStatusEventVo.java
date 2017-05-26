/**
 * 
 */
package com.lili.event.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.lili.order.vo.OrderVo;

/**
 * @author linbo
 *
 */
public class CourseStatusEventVo implements Serializable
{
    private static final long serialVersionUID = -1611229852950574959L;
    private long coachId; 
    private String orderId; 
    private int targetType;
    //下课的订单列表，如果是预约则可能存在多个订单，现约则只有一个订单
    private List<OrderVo> orderList;
    //是不是自动下课生成的消息，默认非自动下课
    private boolean autoGen=false;
    
    /**
     * @return the coachId
     */
    public long getCoachId()
    {
        return coachId;
    }
    /**
     * @param coachId the coachId to set
     */
    public void setCoachId(long coachId)
    {
        this.coachId = coachId;
    }
    /**
     * @return the studentId
     */
    public long getStudentId()
    {
        return studentId;
    }
    /**
     * @param studentId the studentId to set
     */
    public void setStudentId(long studentId)
    {
        this.studentId = studentId;
    }
    private long studentId; 
    private String status;
    private Date time;
  
    /**
     * @return the orderId
     */
    public String getOrderId()
    {
        return orderId;
    }
    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    /**
     * @return the status
     */
    public String getStatus()
    {
        return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(String status)
    {
        this.status = status;
    }
    /**
     * @return the time
     */
    public Date getTime()
    {
        return time;
    }
    /**
     * @param time the time to set
     */
    public void setTime(Date time)
    {
        this.time = time;
    }
    /**
     * @return the targetType
     */
    public int getTargetType()
    {
        return targetType;
    }
    /**
     * @param targetType the targetType to set
     */
    public void setTargetType(int targetType)
    {
        this.targetType = targetType;
    }
	public List<OrderVo> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<OrderVo> orderList) {
		this.orderList = orderList;
	}
	public boolean isAutoGen() {
		return autoGen;
	}
	public void setAutoGen(boolean autoGen) {
		this.autoGen = autoGen;
	}
}
