package com.lili.coach.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.coach.dto.Trfields;
import com.lili.coach.manager.TrfieldsManager;
import com.lili.coach.mapper.dao.TrfieldsMapper;
import com.lili.coach.vo.TrfieldsVo;

public class TrfieldsManagerImpl implements TrfieldsManager {

	@Autowired
	private TrfieldsMapper trfieldsMapper;
	
	@Override
	public List<Trfields> getTrfield(Trfields trfields) {
		return trfieldsMapper.getAll(trfields);
	}

	@Override
	public Trfields getTrfieldInfo(int id) {
		return trfieldsMapper.selectByPrimaryKey(id);
	}

	@Override
	public int getCount() {
		return trfieldsMapper.countAll();
	}

	@Override
	public int addTrfields(Trfields trfields) {
		return trfieldsMapper.insertSelective(trfields);
	}

	@Override
	public int updateTrfields(Trfields trfields) {
		return trfieldsMapper.updateByPrimaryKeySelective(trfields);
	}

	@Override
	public int deleteTrfields(int id) {
		return trfieldsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<TrfieldsVo> getTrfieldsSpecial(String keyword,String rid,String imei) {
		return trfieldsMapper.getTrfieldsSpecial(keyword,rid,imei);
		
	}

	
}
