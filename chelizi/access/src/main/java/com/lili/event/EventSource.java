/**
 * Date: Mar 19, 2013
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 事件源。同一事件类型，支持多个监听者，并且监听者对象可以相同。
 * 
 * @author jiayi.wei
 */
public class EventSource implements IEventSource
{
    private static final Logger LOGGER = LoggerFactory
            .getLogger(EventSource.class);

    /**
     * 监听都列表。同一事件，可能存在多个相同的监听者。
     */
    private Map<Integer, Collection<IEventListener>> listeners;

    private ReadWriteLock lock;

    /**
     * 构造方法
     */
    public EventSource()
    {
        this.listeners = new ConcurrentHashMap<Integer, Collection<IEventListener>>();
        this.lock = new ReentrantReadWriteLock();
    }

    /**
     * 将监听者加入到指定事件类型的监听队列中。
     * 
     * @param eventType
     *            事件类型
     * @param listener
     *            监听者
     */
    public void addListener(int eventType, IEventListener listener)
    {
        if(listener == null)
            LOGGER.error("listener is null", new NullPointerException());
        
        Collection<IEventListener> lstns = this.listeners.get(eventType);
        if (lstns == null)
        {
            try
            {
                // 加写锁确保没有并发问题。
                this.lock.writeLock().lock();

                lstns = this.listeners.get(eventType);
                if (lstns == null)
                {
                    // lstns = new ConcurrentSkipListSet<IEventListener>();
                    lstns = new LinkedList<IEventListener>();
                    lstns.add(listener);
                    this.listeners.put(eventType, lstns);
                }
                else
                {
                    lstns.add(listener);
                }
            }
            catch (Exception e)
            {
                LOGGER.error("", e);
            }
            finally
            {
                this.lock.writeLock().unlock();
            }
        }
        else
        {
            lstns.add(listener);
        }

        LOGGER.debug("Added a listener: {}, {}", eventType, listener);
    }

    /**
     * 从指定事件类型的监听队列中移除指定的监听者。
     * 
     * @param eventType
     *            事件类型
     * @param listener
     *            监听者
     */
    public void removeListener(int eventType, IEventListener listener)
    {
        try
        {
            this.lock.writeLock().lock();

            Collection<IEventListener> lstns = this.listeners.get(eventType);
            if (lstns != null)
            {
                lstns.remove(listener);
            }
        }
        catch (Exception e)
        {
            LOGGER.error("", e);
        }
        finally
        {
            this.lock.writeLock().unlock();
        }

        LOGGER.debug("Removed a listener: {}, {}", eventType, listener);
    }

    /**
     * 通知监听者发生了事件。事件源由事件参数arg指定。
     * 
     * @param arg
     *            事件参数
     */
    public void notifyListeners(EventArg arg)
    {
        try
        {
            this.lock.writeLock().lock();

            Collection<IEventListener> lstns = this.listeners.get(arg
                    .getEventType());
            if (lstns != null)
            {
                lstns = new ArrayList<IEventListener>(lstns);
                for (IEventListener item : lstns)
                {
                    item.onEvent(arg);
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.error("", e);
        }
        finally
        {
            this.lock.writeLock().unlock();
        }
    }

    /**
     * 通知监听者发生了事件。事件源为当前this对象。
     * 
     * @param eventType
     *            事件类型
     */
    public void notifyListeners(int eventType)
    {
        try
        {
            this.lock.writeLock().lock();
            Collection<IEventListener> lstns =listeners.get(eventType);
            if (lstns != null)
            {
                 lstns = new ArrayList<IEventListener>(lstns);
                for (IEventListener item : lstns)
                {
                    item.onEvent(new EventArg(this, eventType));
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.error("", e);
        }
        finally
        {
            this.lock.writeLock().unlock();
        }
    }
}
