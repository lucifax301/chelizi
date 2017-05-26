/**
 * 
 */
package com.lili.student.vo;

import java.io.Serializable;

import com.lili.order.vo.OrderVo;


/**
 * @author linbo
 *
 */
public class StudentStateVo implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private int state;
    
    private OrderVo orderVo;
    
    /**
     * @return the state
     */
    public int getState()
    {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(int state)
    {
        this.state = state;
    }

    /**
     * @return the orderVo
     */
    public OrderVo getOrderVo()
    {
        return orderVo;
    }

    /**
     * @param orderVo the orderVo to set
     */
    public void setOrderVo(OrderVo orderVo)
    {
        this.orderVo = orderVo;
    }
}
