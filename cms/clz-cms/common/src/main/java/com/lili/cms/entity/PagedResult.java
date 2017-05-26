package com.lili.cms.entity;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能概要：
 * 
 * @author linbingwen
 * @since  2015年10月23日 
 */
public class PagedResult<T> extends PagedResultEntity {
	
	/*serialVersionUID*/
	private static final long serialVersionUID = 1L;
	//数据
	private List<T> dataList;
	//请求第几页
	private long pageNo;
	//一页多少条数据
	private long pageSize;
	//总共有多少条数据
	private long total;
	//总共有多少页
	private long pages;

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public long getPageNo() {
		return pageNo;
	}

	public void setPageNo(long pageNo) {
		this.pageNo = pageNo;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getPages() {
		return pages;
	}

	public void setPages(long pages) {
		this.pages = pages;
	}
	
}

abstract class PagedResultEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private static Map<Class<?>,PropertyInfo[]> class2Props = new HashMap<Class<?>,PropertyInfo[]>(128);
	
	@Override
	public String toString() {
		PropertyInfo[] props = class2Props.get(this.getClass());
		if( props == null ){
			props = getProps(this.getClass());
		}
		
		StringBuilder   builder = new StringBuilder(1024);
		boolean isFirst = true;
		for (int i = 0, n = props.length; i < n; i++) {
			try {
				PropertyInfo propInfo = props[i];				
				
				Object value = propInfo.getMethod.invoke(this, new Object[0]);
				if (isFirst)
					isFirst = false;
				else
					builder.append(",");
				builder.append(propInfo.propName);
				builder.append(":");
				if (value instanceof String)
					builder.append("\"");
				builder.append(value);
				if (value instanceof String)
					builder.append("\"");				
			} catch (Exception e) {
				// ignore
			}
		}
		return "{" + builder.toString() + "}";
	}

	private static PropertyInfo[] getProps(Class<? extends PagedResultEntity> clazz) {
		PropertyInfo[] props;
		Method[] allMethods = clazz.getMethods(); 
		List<PropertyInfo> propList = new ArrayList<PropertyInfo>();
		
		for (int i = 0, n = allMethods.length; i < n; i++) {
			try {
				Method method = allMethods[i];
				if ((method.getModifiers() & Modifier.PUBLIC) == 1
						&& method.getDeclaringClass() != Object.class
						&& (method.getParameterTypes() == null || method
								.getParameterTypes().length == 0)) {
					String methodName = method.getName();
					if (methodName.startsWith("get") || methodName.startsWith("is") ) {
						PropertyInfo propInfo = new PropertyInfo();										
						propInfo.getMethod = method;
						if (methodName.startsWith("get")) {
							propInfo.propName = methodName.substring(3, 4).toLowerCase()
									+ methodName.substring(4);
						} else if (methodName.startsWith("is")) {
							propInfo.propName = methodName.substring(2, 3).toLowerCase()
									+ methodName.substring(3);
						}				
						propList.add(propInfo);
					}
				}					
			}catch(Exception e){					
			}
		}
		
		props =  new PropertyInfo[propList.size()];
		propList.toArray(props);
		class2Props.put(clazz, props);
		return props;
	}
	
	static class PropertyInfo{
		Method getMethod;
		String propName;		
	}

}
