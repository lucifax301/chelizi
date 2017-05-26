package com.lili.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanCopy {
	public static final int COPYNOTNULL = 0;
	public static final int COPYALL = 1;
	public static final int COPY2NULL = 2;
	/**
	 * 只复制源对象的非空字段到目标对象
	 * 
	 * @param src
	 * @param target
	 * @return
	 * @throws Exception
	 */
	public static <T> T copyNotNull(Object src, T target) throws Exception {
		T t = target;
		Field[] sfs = src.getClass().getDeclaredFields();
		for (int i = 0; i < sfs.length; i++) {
			Field sf = sfs[i];
			if (!"serialVersionUID".equals(sf.getName())) {
				Field tf = null;
				try {
					tf = target.getClass().getDeclaredField(sf.getName());
				} catch (Exception e) {
				}
				if (tf != null) {
					sf.setAccessible(true);
					Object sv = sf.get(src);
					if (sv != null) {
						setValue(t, tf, sv);
					}
				}
			}
		}
		return t;
	}

	public static <T> T copyNotNull(Object src, Class<T> target)
			throws Exception {
		T t = target.newInstance();
		Field[] sfs = src.getClass().getDeclaredFields();
		for (int i = 0; i < sfs.length; i++) {
			Field sf = sfs[i];
			if (!"serialVersionUID".equals(sf.getName())) {
				Field tf = null;
				try {
					tf = target.getDeclaredField(sf.getName());
				} catch (Exception e) {
				}
				if (tf != null) {
					sf.setAccessible(true);
					Object sv = sf.get(src);
					if (sv != null) {
						setValue(t, tf, sv);
					}
				}
			}
		}
		return t;
	}

	/**
	 * 复制源对象所有字段的值到目标对象
	 * 
	 * @param src
	 * @param target
	 * @return
	 * @throws Exception
	 */
	public static <T> T copyAll(Object src, T target) throws Exception {
		T t = target;
		Field[] sfs = src.getClass().getDeclaredFields();
		for (int i = 0; i < sfs.length; i++) {
			Field sf = sfs[i];
			if (!"serialVersionUID".equals(sf.getName())) {
				Field tf = null;
				try {
					tf = target.getClass().getDeclaredField(sf.getName());
				} catch (Exception e) {
				}
				if (tf != null) {
					sf.setAccessible(true);
					Object sv = sf.get(src);
					setValue(t, tf, sv);
				}
			}
		}
		return t;
	}

	public static <T> T copyAll(Object src, Class<T> target) throws Exception {
		T t = target.newInstance();
		Field[] sfs = src.getClass().getDeclaredFields();
		for (int i = 0; i < sfs.length; i++) {
			Field sf = sfs[i];
			if (!"serialVersionUID".equals(sf.getName())) {
				Field tf = null;
				try {
					tf = target.getDeclaredField(sf.getName());
				} catch (Exception e) {
				}
				if (tf != null) {
					sf.setAccessible(true);
					Object sv = sf.get(src);
					setValue(t, tf, sv);
				}
			}
		}
		return t;
	}

	/**
	 * 只从源对象复制制定字段到目标对象
	 * 
	 * @param src
	 * @param target
	 * @param fields
	 *            :制定需要复制的字段，以都好分割
	 * @return
	 */
	public static <T> T copySome(Object src, Class<T> target, String fields) {
		return null;
	}

	/**
	 * 只将目标对象为空的字段从源对象复制过来
	 * 
	 * @param src
	 * @param target
	 * @return
	 */
	public static <T> T copy2Null(Object src, T target) throws Exception {
		Field[] sfs = src.getClass().getDeclaredFields();
		for (int i = 0; i < sfs.length; i++) {
			Field sf = sfs[i];
			if (!"serialVersionUID".equals(sf.getName())) {
				Field tf = null;
				Object v = null;
				try {
					tf = target.getClass().getDeclaredField(sf.getName());
					if (tf != null) {
						tf.setAccessible(true);
						v = tf.get(target);
					}
				} catch (Exception e) {
				}
				if (tf != null && v == null) {
					sf.setAccessible(true);
					Object sv = sf.get(src);
					setValue(target, tf, sv);
				}
			}
		}
		return target;
	}

	/**
	 * 从源数组复制某些字段到目标数据。
	 * 
	 * @param src
	 * @param target
	 * @param type
	 * @return 实际从源数组复制了数据的所有目标对象列表，可能为源目标对象的子集，具体需要哪个数组请谨慎选择。
	 * @throws Exception
	 */
	public static <S,T> List<T> copyList(List<S> srcList, List<T> target, int type,String pk) throws Exception {
		if(srcList==null || target==null){
			return target;
		}
		List<T> list = new ArrayList<T>();
		Map<Object, S> pkmap = new HashMap<Object, S>();
		for (S one : srcList) {
			Field tf = one.getClass().getDeclaredField(pk);
			if (tf != null) {
				tf.setAccessible(true);
				pkmap.put(tf.get(one), one);
			}
		}
		for (int i = 0; i < target.size(); i++) {
			T one = target.get(i);
			Object key = null;
			Field sf = one.getClass().getDeclaredField(pk);
			if (sf != null) {
				sf.setAccessible(true);
				key = sf.get(one);
			} else {
				continue;
			}
			Object src=pkmap.get(key);
			if (src != null ) {
				switch (type) {
				case (COPYNOTNULL):
					T t1 = copyNotNull(src,one);
					list.add(t1);
					break;
				case (COPY2NULL):
					T t3 = copy2Null(src,one);
					list.add(t3);
					break;
				default:
					T t0 = copyAll(src,one);
					list.add(t0);
					break;
				}
			}
		}
		return list;
	}

	public static <S,T> List<T> copyList(List<S> srcList, Class<T> target, int type)
			throws Exception {
		if(srcList==null || target==null){
			return null;
		}
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < srcList.size(); i++) {
			switch (type) {

			case (COPYNOTNULL):
				T t1 = copyNotNull(srcList.get(i), target);
				list.add(t1);
				break;
			case (COPY2NULL):
				T t3 = copy2Null(srcList.get(i), target.newInstance());
				list.add(t3);
				break;
			default:
				T t = copyAll(srcList.get(i), target);
				list.add(t);
				break;
			}
		}
		return list;
	}

	public static <S,T> List<T> copyListByField(List<S> srcList, Class<T> target,
			String fields) throws Exception {
		if(srcList==null || target==null){
			return null;
		}
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < srcList.size(); i++) {
			T t2 = null;
			if (fields == null || fields.trim().isEmpty()) {
				t2 = copyAll(srcList.get(i), target.newInstance());
			} else {
				t2 = copySome(srcList.get(i), target, fields);
			}
			list.add(t2);
		}
		return list;
	}

	private static <T> void setValue(T t, Field tf, Object sv) throws Exception {
		String setMethodName = "set" + StringUtil.tuofeng(tf.getName(), true);
		if (sv == null) {
			tf.setAccessible(true);
			tf.set(t, sv);
		} else if (sv.getClass() == Long.class) {
			try {
				Method m=t.getClass().getDeclaredMethod(setMethodName, sv.getClass());
				if(m!=null){
					m.invoke(t, sv);
				}
			} catch (Exception e) {
				Method m=t.getClass().getDeclaredMethod(setMethodName, long.class);
				if(m!=null){
					m.invoke(t, sv);
				}
			}
		} else if (sv.getClass() == Integer.class) {
			try {
				Method m=t.getClass().getDeclaredMethod(setMethodName, sv.getClass());
				if(m!=null){
					m.invoke(t, sv);
				}
			} catch (Exception e) {
				Method m=t.getClass().getDeclaredMethod(setMethodName, int.class);
				if(m!=null){
					m.invoke(t, sv);
				}
			}
		} else if (sv.getClass() == Double.class) {
			try {
				Method m=t.getClass().getDeclaredMethod(setMethodName, sv.getClass());
				if(m!=null){
					m.invoke(t, sv);
				}
			} catch (Exception e) {
				Method m=t.getClass().getDeclaredMethod(setMethodName, double.class);
				if(m!=null){
					m.invoke(t, sv);
				}
			}
		} else if (sv.getClass() == Float.class) {
			try {
				Method m=t.getClass().getDeclaredMethod(setMethodName, sv.getClass());
				if(m!=null){
					m.invoke(t, sv);
				}
			} catch (Exception e) {
				Method m=t.getClass().getDeclaredMethod(setMethodName, float.class);
				if(m!=null){
					m.invoke(t, sv);
				}
						
			}
		} else {
			try {
				Method m = t.getClass().getDeclaredMethod(setMethodName,sv.getClass());
				if (m != null) {
					m.invoke(t, sv);
				}
			} catch (Exception e) {
				Method m = t.getClass().getDeclaredMethod(setMethodName,
						tf.getType());
				if (m != null) {
					m.invoke(t, sv);
				}
			}
		}
		// tf.setAccessible(true);
		// tf.set(t, sv);
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> getFieldList(Object src, String field)
			throws Exception {
		List<T> result = new ArrayList<T>();
		if (src == null || field == null || field.isEmpty()) {
			return result;
		}
		for (Object one : (List<Object>) src) {
			Field[] sfs = one.getClass().getDeclaredFields();
			for (int i = 0; i < sfs.length; i++) {
				Field sf = sfs[i];
				if (field.equals(sf.getName())) {
					sf.setAccessible(true);
					result.add((T)sf.get(one));
				}
			}
		}
		return result;
	}
	@SuppressWarnings("unchecked")
	public static <T,S> List<T> getNotNullFieldList(List<S> src, String field)
			throws Exception {
		List<T> result = new ArrayList<T>();
		if (src == null || src.isEmpty() || field == null || field.isEmpty()) {
			return result;
		}
		for (S one :  src) {
			Field[] sfs = one.getClass().getDeclaredFields();
			for (int i = 0; i < sfs.length; i++) {
				Field sf = sfs[i];
				if (field.equals(sf.getName())) {
					sf.setAccessible(true);
					T t=(T)sf.get(one);
					if(t!=null){
						result.add(t);
					}
				}
			}
		}
		return result;
	}
	public static <T,F> void setListField(List<T> src, String fieldName,F fieldValue)
			throws Exception {
		if (src == null || src.isEmpty()||fieldName==null||fieldName.isEmpty()) {
			return;
		}
		for (T one :  src) {
			Field[] sfs = one.getClass().getDeclaredFields();
			for (int i = 0; i < sfs.length; i++) {
				Field sf = sfs[i];
				if (fieldName.equals(sf.getName())) {
					sf.setAccessible(true);
					setValue(one,sf, fieldValue);
				}
			}
		}
	}
	@SuppressWarnings("unchecked")
	public static <K,V> Map<K,V> getFromList(List<V> list,String keyField){
		Map<K,V> map=new HashMap<K,V>();
		if(list==null || list.isEmpty()){
			return map;
		}
		for(V one:list){
			try {
				Field  sf = one.getClass().getDeclaredField(keyField);
				sf.setAccessible(true);
				map.put((K)sf.get(one), one);
			} catch (Exception e) {
			}
		}
		return map;
	}
	public static String toString(Object src) {
		if(src==null){
			return null;
		}
		String result="{";
		try {
			Field[] sfs = src.getClass().getDeclaredFields();
			for (int i = 0; i < sfs.length; i++) {
				Field sf = sfs[i];
				if (!"serialVersionUID".equals(sf.getName())) {
						sf.setAccessible(true);
						Object sv = sf.get(src);
						if(i!=0){
							result+=","+sf.getName()+"="+sv;
						} else {
							result+=sf.getName()+"="+sv;
						}		
				}
			}
			result+="}";
		} catch(Exception e){}
		return result;
	}
}
