package com.lili.cms.entity;
/*
 * @(#)ObjectHelper.java Feb 13, 2014
 * Copyright(c) 1987-2014, Fadebead Goth. All rights reserved.
 */

import static java.util.regex.Pattern.compile;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public final class ObjectHelper {

  private static Pattern PATTERN_GET = compile("^public java.lang.String .+get[A-Z].+\\(\\)$");
  private static Pattern PATTERN_SET = compile("^public void .+set[A-Z].+\\(java.lang.String\\)$");

  private ObjectHelper() {}

  /**
   * Only trim String member in given object.
   * 
   * @param obj
   */
  public static Object trim(final Object obj) {
    final ObjectHelper objTrim = new ObjectHelper();
    return objTrim.trim0(obj);
  }
  
  public static <T> List<T> initObjectList(List<T> objectList){
	  for(T obj : objectList){
		  initObject(obj);
	  }
	  return objectList;
  }

  /**
   * 遍历对象的所有属性,并赋初始值
 * @param <T>
   * @param object
 * @return 
   */
  public static <T>  T initObject(T object){
	  Field[] field = object.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
      try {
          for (int j = 0; j < field.length; j++) { // 遍历所有属性
              String name = field[j].getName(); // 获取属性的名字
              name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
              String type = field[j].getGenericType().toString(); // 获取属性的类型
              if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                  Method m = object.getClass().getMethod("get" + name);
                  String value = (String) m.invoke(object); // 调用getter方法获取属性值
                  if (value == null) {
                      m = object.getClass().getMethod("set"+name,String.class);
                      m.invoke(object, "");
                  }
              }
              if (type.equals("class java.lang.Integer")) {
                  Method m = object.getClass().getMethod("get" + name);
                  Integer value = (Integer) m.invoke(object);
                  if (value == null) {
                      m = object.getClass().getMethod("set"+name,Integer.class);
                      m.invoke(object, 0);
                  }
              }
              if (type.equals("class java.lang.Boolean")) {
                  Method m = object.getClass().getMethod("get" + name);
                  Boolean value = (Boolean) m.invoke(object);
                  if (value == null) {
                      m = object.getClass().getMethod("set"+name,Boolean.class);
                      m.invoke(object, false);
                  }
              }
              if (type.equals("class java.util.Date")) {
                  Method m = object.getClass().getMethod("get" + name);
                  Date value = (Date) m.invoke(object);
                  if (value == null) {
                      m = object.getClass().getMethod("set"+name,Date.class);
                      m.invoke(object, new Date());
                  }
              }
          }
      } catch (NoSuchMethodException e) {
          e.printStackTrace();
      } catch (SecurityException e) {
          e.printStackTrace();
      } catch (IllegalAccessException e) {
          e.printStackTrace();
      } catch (IllegalArgumentException e) {
          e.printStackTrace();
      } catch (InvocationTargetException e) {
          e.printStackTrace();
      }
      return object;
  }
  
  private Object trim0(final Object obj) {
    if (null == obj) {
      return obj;
    }

    final Method[] methods = obj.getClass().getMethods();
    final Map<String, Method> getMethods = new HashMap<String, Method>();
    final Map<String, Method> setMethods = new HashMap<String, Method>();
    for (Method method : methods) {
      // Filter getter method, public String getXXX()
      if (PATTERN_GET.matcher(method.toGenericString()).matches()) {
        getMethods.put(method.getName(), method);
      }
      // Filter setter method, public void setXXX(String)
      else if (PATTERN_SET.matcher(method.toGenericString()).matches()) {
        setMethods.put(method.getName(), method);
      }
    }

    try {
      for (Map.Entry<String, Method> entry : getMethods.entrySet()) {
        // Find the corresponding setter method.
        final char[] getterChars = entry.getKey().toCharArray();
        getterChars[0] = 's';

        final String setterName = new String(getterChars);
        final Method setMethod = setMethods.get(setterName);
        if (null != setMethod) {
          final String value = (String) entry.getValue().invoke(obj);
          if (null != value) {
            setMethod.invoke(obj, value.trim());
          }
        }
      }
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      e.printStackTrace();
    }
    return obj;
  }
}
