package com.lili.cms.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import com.lili.cms.entity.BasePagedEntity;

@SuppressWarnings("rawtypes")
public class ReflectUtil {

	protected static  final Logger access = Logger.getLogger("ReflectUtil");
	/**
	 * 正则表达式 用于匹配属性的第一个字母 {@value [a-zA-Z]} 
	 */
    private static final String REGEX = "[a-zA-Z]";
    
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		BasePagedEntity basePagedEntity = BasePagedEntity.class.newInstance();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("pageNo", 10);
		map.put("pageSize", 100);
		fillObject(basePagedEntity, map);
		System.out.println(basePagedEntity.getPageNo() + "," + basePagedEntity.getPageSize());
		
	}
    
    public static Object fillObject(Object obj,Map<String, Object> valMap){
    	 Iterator<Map.Entry<String, Object>> iterator = valMap.entrySet().iterator();  
         while (iterator.hasNext()) {  
             Map.Entry<String, Object> entry = iterator.next();  
             if(!StringUtil.isNull(entry.getValue()))
            	 setAttrributeValue(obj,entry.getKey() ,entry.getValue() );
         }  
         return obj;
    }
    
    
    
    public static void setAttrributeValue(Object obj,String attribute,Object value)
    {
        String method_name = convertToMethodName(attribute,obj.getClass(),true);
        Method[] methods = obj.getClass().getMethods();
        for (Method method : methods) {
            /**
             *     因为这里只是调用bean中属性的set方法，属性名称不能重复
             * 所以set方法也不会重复，所以就直接用方法名称去锁定一个方法
             * （注：在java中，锁定一个方法的条件是方法名及参数）
             * **/
            if(method.getName().equals(method_name))
            {
                Class[] parameterC = method.getParameterTypes();
                try {
                    /**如果是基本数据类型时（如int、float、double、byte、char、boolean）
                     * 需要先将Object转换成相应的封装类之后再转换成对应的基本数据类型
                     * 否则会报 ClassCastException**/
                    if(parameterC[0] == int.class || parameterC[0] == Integer.class)
                    {
                        method.invoke(obj,Integer.parseInt(value.toString()));
                        break;
                    }else if(parameterC[0] == float.class || parameterC[0] == Float.class){
                        method.invoke(obj, (Float.parseFloat(value.toString())));
                        break;
                    }else if(parameterC[0] == double.class || parameterC[0] == Double.class)
                    {
                        method.invoke(obj, ((Double.parseDouble(value.toString()))));
                        break;
                    }else if(parameterC[0] == byte.class || parameterC[0] == Byte.class)
                    {
                        method.invoke(obj, (Byte.parseByte(value.toString())));
                        break;
                    }else if(parameterC[0] == char.class || parameterC[0] == Character.class)
                    {
                        method.invoke(obj, (value.toString().charAt(0)));
                        break;
                    }else if(parameterC[0] == boolean.class || parameterC[0] == Boolean.class)
                    {
                        method.invoke(obj, ((Boolean.parseBoolean(value.toString()))));
                        break;
                    }else if(parameterC[0] == long.class || parameterC[0] == Long.class)
                    {
                        method.invoke(obj, ((Long.parseLong(value.toString()))));
                        break;
                    }else
                    {
                        method.invoke(obj,parameterC[0].cast(value));
                        break;
                    }
                }  catch (Exception e) {
                	access.error("|||method:" + method_name + ",value:" 
            				+ value!=null?"":value.toString()+"|||exception" + e.getMessage());
                } 
            }
        }
    }
    private static String convertToMethodName(String attribute,Class objClass,boolean isSet)
    {
        /** 通过正则表达式来匹配第一个字符 **/
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(attribute);
        StringBuilder sb = new StringBuilder();
        /** 如果是set方法名称 **/
        if(isSet)
        {
            sb.append("set");
        }else{
        /** get方法名称 **/
            try {
                Field attributeField = objClass.getDeclaredField(attribute);
                /** 如果类型为boolean **/
                if(attributeField.getType() == boolean.class||attributeField.getType() == Boolean.class)
                {
                    sb.append("is");
                }else
                {
                    sb.append("get");
                }
            } catch (Exception e) {
            	access.error("|||attribute:" + attribute + "|||exception" + e.getMessage());
            } 
        }
        /** 针对以下划线开头的属性 **/
        if(attribute.charAt(0)!='_' && m.find())
        {
            sb.append(m.replaceFirst(m.group().toUpperCase()));
        }else{
            sb.append(attribute);
        }
        return sb.toString();
    }
    public static Object getAttrributeValue(Object obj,String attribute)
    {
        String methodName = convertToMethodName(attribute, obj.getClass(), false);
        Object value = null;
        try {
            /** 由于get方法没有参数且唯一，所以直接通过方法名称锁定方法 **/
            Method methods = obj.getClass().getDeclaredMethod(methodName);
            if(methods != null)
            {
                value = methods.invoke(obj);
            }
        } catch (Exception e) {
        	access.error("|||attribute:" + attribute + "|||exception" + e.getMessage());
        } 
        return value;
    }

}