package com.lili.file.manager.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.file.dto.HtmlObject;
import com.lili.file.dto.HtmlObjectExample;
import com.lili.file.manager.HtmlObjectManager;
import com.lili.file.mapper.HtmlObjectMapper;

public class HtmlObjectManagerImpl implements HtmlObjectManager {
	@Autowired
	private HtmlObjectMapper htmlObjectMapper;

	@Override
	public List<HtmlObject> getHtmlObject(int pageNo, int pageSize) {
		HtmlObjectExample example = new HtmlObjectExample();
		RowBounds rowBounds = new RowBounds(pageNo,pageSize);
		return htmlObjectMapper.selectByExampleWithBLOBsWithRowbounds( example,  rowBounds);
	}

	@Override
	public int addHtmlObject(HtmlObject record) {
		return htmlObjectMapper.insertSelective(record);
	}

	@Override
	public int updateHtmlObject(HtmlObject record) {
		return htmlObjectMapper.updateByPrimaryKeyWithBLOBs(record);
	}
	
	
	
}
