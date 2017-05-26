package com.lili.coach.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.lili.coach.dto.Trfield;
import com.lili.coach.manager.TrfieldManager;
import com.lili.coach.mapper.dao.TrfieldMapper;

public class TrfieldManagerImpl implements TrfieldManager {

	@Autowired
	private TrfieldMapper trfieldMapper;

	@Override
	public List<Trfield> getTrfield(Trfield trfield) {
		return trfieldMapper.getAll(trfield);
	}

	@Override
	public Trfield getTrfieldInfo(int id) {
		return trfieldMapper.selectByPrimaryKey(id);
	}

	@Override
	public int getCount() {
		return trfieldMapper.countAll();
	}

	@Override
	public int addTrfield(Trfield trfield) {
		return trfieldMapper.insert(trfield);
	}

	@Override
	public int updateTrfield(Trfield trfield) {
		return trfieldMapper.updateByPrimaryKeySelective(trfield);
	}

	@Override
	public int deleteTrfield(int id) {
		return trfieldMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int addTrfieldList(List<Trfield> trfields) {
		int rs = 0;
		for (int i = 0; i < trfields.size(); i++){
			rs += trfieldMapper.insert(trfields.get(i));
		}
		return rs;
	}

}
