package com.lili.school.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.school.dto.EnrollMaterialItems;
import com.lili.school.dto.EnrollMaterialItemsExample;
import com.lili.school.manager.EnrollMaterialItemManager;
import com.lili.school.mapper.EnrollMaterialItemsMapper;

public class EnrollMaterialItemManagerImpl implements EnrollMaterialItemManager {
	@Autowired
	private EnrollMaterialItemsMapper enrollMaterialItemsMapper;
	@Override
	public int addEnrollMaterialItems(EnrollMaterialItems record) {
		return enrollMaterialItemsMapper.insertSelective(record);
	}

	@Override
	public int updateEnrollMaterialItems(EnrollMaterialItems record) {
		return enrollMaterialItemsMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<EnrollMaterialItems> getEnrollMaterialItems(
			EnrollMaterialItemsExample example) {
		return enrollMaterialItemsMapper.selectByExample(example);
	}

}
