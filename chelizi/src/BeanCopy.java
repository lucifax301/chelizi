package com.lili.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class BeanCopy {
	public static final int copyAll=0;
	public static final int copyNotNull=1;
	public static final int copySome=2;
	public static final int copy2Null=3;
	/**
	 * 只复制源对象的非空字段到目标对象
	 * @param src
	 * @param target
	 * @return
	 * @throws Exception
	 */
	public static <T> T copyNotNull(Object src, Class<T> target) throws Exception {
		T t=target.newInstance();
		Field[] sfs=src.getClass().getDeclaredFields();
		for(int i=0;i<sfs.length;i++){
			Field sf=sfs[i];
			Field tf=target.getDeclaredField(sf.getName());
			if(tf!=null){
				sf.setAccessible(true);
				Object sv=sf.get(src);
				if(sv!=null){
					setValue(t,tf,sv);
				}
			}
		}
		return t;
	}
	/**
	 * 复制源对象所有字段的值到目标对象
	 * @param src
	 * @param target
	 * @return
	 * @throws Exception
	 */
	public static <T> T copyAll(Object src, Class<T> target) throws Exception {
		T t=target.newInstance();
		Field[] sfs=src.getClass().getDeclaredFields();
		for(int i=0;i<sfs.length;i++){
			Field sf=sfs[i];
			Field tf=target.getDeclaredField(sf.getName());
			if(tf!=null){
				sf.setAccessible(true);
				Object sv=sf.get(src);
				setValue(t,tf,sv);
			}
		}
		return t;
	}
	/**
	 * 只从源对象复制制定字段到目标对象
	 * @param src
	 * @param target
	 * @param fields:制定需要复制的字段，以都好分割
	 * @return
	 */
	public static <T> T copySome(Object src, Class<T> target,String fields){
		return null;
	}
	/**
	 * 只将目标对象为空的字段从源对象复制过来
	 * @param src
	 * @param target
	 * @return
	 */
	public static <T> T copy2Null(Object src, Class<T> target){
		return null;
	}

	/**
	 * 从源数组
	 * @param src
	 * @param target
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T>  copyList(Object src, Class<T> target,int type,String fields) throws Exception {
		List<T> list=new ArrayList<T>();
		@SuppressWarnings("unchecked")
		List<Object> srcList=(List<Object>)src;
		for(int i=0;i<srcList.size();i++){
			switch(type)
			{
				case(copyNotNull):
					T t1=copyNotNull(srcList.get(i),target);
				    list.add(t1);
				    break;
				case(copySome):
					T t2=null;
					if(fields==null|| fields.trim().isEmpty()){
						t2=copyAll(srcList.get(i),target);
					} else {
						t2=copySome(srcList.get(i),target,fields);
					}
				    list.add(t2);
				    break;
				case(copy2Null):
					T t3=copy2Null(srcList.get(i),target);
				    list.add(t3);
				    break;
				default:
					T t=copyAll(srcList.get(i),target);
				    list.add(t);
				    break;
			}
		}
		return list;
	}
	private static <T> void setValue(T t,Field tf,Object sv) throws Exception {
		tf.setAccessible(true);
		tf.set(t, sv);
	}
}
