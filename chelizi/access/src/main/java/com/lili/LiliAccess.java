package com.lili;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lili.common.util.Config;
import com.lili.component.CommandComponent;
import com.lili.component.ComponentManager;
import com.lili.component.DefaultMinaComponent;

public class LiliAccess
{
    private static final Logger LOGGER = LoggerFactory
        .getLogger(LiliAccess.class);

    /**
     * 单例加载器
     */
    private static class LazyHolder
    {
        private static final LiliAccess INSTANCE = new LiliAccess();
    }

    /**
     * 获取实例
     * 
     * @return
     */
    public static LiliAccess getInstance()
    {
        return LazyHolder.INSTANCE;
    }

    private static ClassPathXmlApplicationContext context = null;

    
    public static ClassPathXmlApplicationContext getDubboContext()
    {
        return context;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        long time = System.currentTimeMillis();
        LOGGER.info("liliAccess is starting...");
        if (args.length <= 0)
        {
            LOGGER.error("Please input the global config path.");
            System.exit(1);
        }
        // 初始化配置管理器。
        if (!Config.initConfig(args[0]))
        {
            LOGGER.error("liliAccess has started failed because of initing config.");
            System.exit(1);
        }
        Runtime.getRuntime().addShutdownHook(
                new ShutdownHooker(LiliAccess.getInstance()));
        
        context =  new ClassPathXmlApplicationContext(
                new String[] { "applicationContext.xml" });
        context.start();
        
        if (LiliAccess.getInstance().LoadComponents() == false)
        {
            LOGGER.error("liliAccess has started failed");
            System.exit(1);
        }

        LOGGER.info(String.format(
                "liliAccess has started successfully, taken %d millis.",
                System.currentTimeMillis() - time));
    }

    private boolean LoadComponents()
    {
        try
        {
            // 初始化组件处理器
            if (ComponentManager.getInstance().init() == false)
            {
                return false;
            }
            
            //　添加组件
            if (!ComponentManager.getInstance().addComponent(
                    DefaultMinaComponent.class.getName()))
                return false;
            if (!ComponentManager.getInstance().addComponent(
                    CommandComponent.class.getName()))
                return false;
                      
            // 启动
            if (ComponentManager.getInstance().start() == false)
            {
                return false;
            }
        }
        catch (Exception e)
        {
            LOGGER.error("liliAccess LoadComponents error:", e);
        }
        return true;
    }

    public void callBackStop()
    {
        context.close();
        LOGGER.error("access is forced to stop..");
    }

   
}
