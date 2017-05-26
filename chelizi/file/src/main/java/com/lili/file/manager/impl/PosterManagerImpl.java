package com.lili.file.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.Page;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.file.dto.Poster;
import com.lili.file.dto.PosterExample;
import com.lili.file.manager.PosterManager;
import com.lili.file.mapper.PosterMapper;

public class PosterManagerImpl implements PosterManager {
	@Autowired
	private PosterMapper posterMapper;

	@Override
	public ReqResult getPoster(String userType) {
		ReqResult r = ReqResult.getSuccess();
		PosterExample example = new PosterExample();
		PosterExample.Criteria criteria = example.createCriteria();
		criteria.andIsdelEqualTo(ReqConstants.OPERATE_DELETE_NO);
		Byte type =1; //如果没有传参数，默认是教练端
		if(null != userType && !"".equals(userType)){
			type = Byte.parseByte(userType);
		}
		List<Byte> types = new ArrayList<Byte>();
		types.add(type);
		types.add((byte)0);
		criteria.andTypeIn(types);
		example.setOrderByClause("posterId desc limit 1");
		List<Poster> pos = posterMapper.selectByExample(example);
		if(null != pos && pos.size()>0){
			r.setData(pos.get(0));
		}else {
			r = ReqResult.getFailed();
		}
		return r;
	}
	

	@Override
	public Page<Poster> getPoster(PosterExample example, int pageNo,
			int pageSize) {
		int total = posterMapper.countByExample(example);
		RowBounds rowBounds = new RowBounds(pageNo,pageSize);
		List<Poster> list = posterMapper.selectByExampleWithRowbounds(example, rowBounds);
		return new Page(list,pageNo,pageSize,total);
	}


	@Override
	public List<Poster> getPoster(PosterExample example) {
		return posterMapper.selectByExample(example);
	}

	@Override
	public int postPoster(Poster pos) {
		return posterMapper.insertSelective(pos);
	}

	@Override
	public int putPoster(Poster pos) {
		return posterMapper.updateByPrimaryKeySelective(pos);
	}
	
	

}
