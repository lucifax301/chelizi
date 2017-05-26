package com.lili.file.manager;

import java.util.List;

import com.lili.file.dto.HtmlObject;

public interface HtmlObjectManager {
	/**
	 * 分页获取html对象
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<HtmlObject> getHtmlObject(int pageNo, int pageSize);
	
	/**
	 * 新增html对象
	 * @param record
	 * @return
	 */
	public int addHtmlObject(HtmlObject record);
	
	/**
	 * 更改html对象,主键更新
	 * @param record
	 * @return
	 */
	public int updateHtmlObject(HtmlObject record);
	
}
