package com.lili.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.redisson.codec.MsgPackJacksonCodec;


public class XmlProcessor {

	/** 
     * 解析微信xml消息 
     * @param strXml 
     * @return 
     */  
    public static Object getMsgEntity(String strXml ,Class<?> clazz){ 
    	Object msg = null;  
        try {  
            if (strXml.length() <= 0 || strXml == null)  
                return null;  	               
            Document document = DocumentHelper.parseText(strXml);  
            Element root = document.getRootElement();  
            Iterator<?> iter = root.elementIterator();  
            //利用反射机制，调用set方法  
            msg = clazz.newInstance();
            while(iter.hasNext()){  
                Element ele = (Element)iter.next();  
                Field field = clazz.getDeclaredField(ele.getName());  
                Method method = clazz.getDeclaredMethod("set"+ele.getName(), field.getType());  
                method.invoke(msg, ele.getText());  
            }  
        } catch (Exception e) {  
            // TODO: handle exception  
            System.out.println("xml 格式异常: "+ strXml);  
            e.printStackTrace();  
        }  
        return msg;  
    }  
    
    
    /** 
     * 解析微信xml消息 
     * @param strXml 
     * @return 
     */  
    public static Map<String, String> getMsgMap(String strXml){ 
    	Map<String, String> msg = new HashMap<>();
    	try {
    		if (strXml.length() <= 0 || strXml == null)  
                return null;  	               
            Document document = DocumentHelper.parseText(strXml);  
            Element root = document.getRootElement();  
            Iterator<?> iter = root.elementIterator(); 
            
            while (iter.hasNext()) {
				Element element = (Element) iter.next();
				msg.put(element.getName(), element.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return msg;
    }
}
