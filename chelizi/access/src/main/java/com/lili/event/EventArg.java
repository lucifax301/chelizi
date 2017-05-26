/**
 * Date: Mar 19, 2013
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.event;

import java.util.EventObject;

/**
 * 事件参数
 * 
 * @author jiayi.wei
 */
public class EventArg extends EventObject
{
    private static final long serialVersionUID = -3737140928956265655L;

    /** 事件类型 */
    private int eventType;

    /** 自定义参数 */
    private Object data;

    /** 触发器参数(如果事件会或发触发器的话，有可能会设置此参数) */
    private String triParams;

    /**
     * 事件构造器
     * 
     * @param source
     *            事件源
     * @param eventType
     *            事件类型
     */
    public EventArg(Object source, int eventType)
    {
        super(source);
        this.eventType = eventType;
    }

    /**
     * 事件构造器
     * 
     * @param source
     *            事件源
     * @param eventType
     *            事件类型
     * @param data
     *            自定义参数
     */
    public EventArg(Object source, int eventType, Object data)
    {
        super(source);
        this.eventType = eventType;
        this.data = data;
    }

    public EventArg(Object source, int eventType, Object... data)
    {
        super(source);
        this.eventType = eventType;
        this.data = data;
    }

    /**
     * 获取事件类型
     * 
     * @return 事件类型
     */
    public int getEventType()
    {
        return eventType;
    }

    /**
     * 获取自定义参数
     * 
     * @return 自定义参数
     */
    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }

    /**
     * @return the triParams
     */
    public String getTriParams()
    {
        return triParams;
    }

    /**
     * @param triParams
     *            the triParams to set
     */
    public void setTriParams(String triParams)
    {
        this.triParams = triParams;
    }
    
    public void setSource(Object source)
    {
        this.source = source;
    }

    /**
     * @param eventType the eventType to set
     */
    public void setEventType(int eventType)
    {
        this.eventType = eventType;
    }
    
    public void catchException()
    {
        
    }
}
