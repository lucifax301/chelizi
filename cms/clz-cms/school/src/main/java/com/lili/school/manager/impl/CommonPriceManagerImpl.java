package com.lili.school.manager.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.PageUtil;
import com.lili.school.manager.ICommonPriceManager;
import com.lili.school.mapper.dao.CommonPriceMapper;
import com.lili.school.model.CommonPrice;

public class CommonPriceManagerImpl implements ICommonPriceManager {
	Logger logger = Logger.getLogger(CommonPriceManagerImpl.class);
	
	@Autowired
	CommonPriceMapper commonPriceMapper;

	@Override
	public PagedResult<CommonPrice> queryCommonPriceList(CommonPrice commonPrice) {
		PageUtil.startPage(commonPrice.getPageNo(), commonPrice.getPageSize());
		return BeanUtil.toPagedResult(commonPriceMapper.queryCommonPriceList(commonPrice));
	}

	@Override
	public List<CommonPrice> queryComPriceList(String id) {
		List<CommonPrice> comPriceList = null;
		try {
			comPriceList = commonPriceMapper.queryComPriceList(id);
		} catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		}
		
		return comPriceList;
	}

}
