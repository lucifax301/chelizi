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
public class UserStateVo implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private int state;
    
    private OrderVo orderVo;
    
    private String orders;
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

    /**
     * @return the orders
     */
    public String getOrders()
    {
        return orders;
    }

    /**
     * @param orders the orders to set
     */
    public void setOrders(String orders)
    {
        this.orders = orders;
    }
}
