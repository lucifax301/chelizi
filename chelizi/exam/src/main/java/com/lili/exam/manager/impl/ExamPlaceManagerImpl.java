package com.lili.exam.manager.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.BeanCopy;
import com.lili.common.util.Page;
import com.lili.common.util.StringUtil;
import com.lili.common.util.redis.RedisKeys;
import com.lili.common.util.redis.RedisUtil;
import com.lili.exam.dto.ExamPlace;
import com.lili.exam.dto.ExamPlaceCity;
import com.lili.exam.dto.ExamPlaceCityExample;
import com.lili.exam.dto.ExamPlaceExample;
import com.lili.exam.dto.ExamPlaceVo;
import com.lili.exam.manager.ExamPlaceManager;
import com.lili.exam.mapper.ExamPlaceCityMapper;
import com.lili.exam.mapper.ExamPlaceMapper;

public class ExamPlaceManagerImpl implements ExamPlaceManager {
	private static Logger log = LoggerFactory.getLogger(ExamPlaceManagerImpl.class);
	
	@Autowired
	ExamPlaceMapper examPlaceMapper;
	@Autowired
	RedisUtil redisUtil;
	@Autowired
	ExamPlaceCityMapper examPlaceCityMapper;
	
	@Override
	public int addExamPlace(ExamPlace examPlace) {
		return examPlaceMapper.insertSelective(examPlace);
	}
	
	@Override
	public int updateExamPlace(ExamPlace examPlace) {
		return examPlaceMapper.updateByPrimaryKeySelective(examPlace);
	}

	@Override
	public Page<ExamPlace> getExamPlaces(String name, String cityId,
			String schoolId, String pageNo, String pageSize) {
		try {
			ExamPlaceExample example = new ExamPlaceExample();
			ExamPlaceExample.Criteria criteria = example.createCriteria();
			if(StringUtil.isNotNullAndNotEmpty(cityId)){
				criteria.andCityIdEqualTo(Integer.parseInt(cityId.trim()));
			}
			if(StringUtil.isNotNullAndNotEmpty(schoolId)){
				criteria.andSchoolIdEqualTo(Integer.parseInt(schoolId.trim()));
			}
			if(StringUtil.isNotNullAndNotEmpty(name)){
				criteria.andNameLike(name.trim());
			}
			int total = examPlaceMapper.countByExample(example);
			int pNo =1;
			int pSize = 100;
			if(StringUtil.isNotNullAndNotEmpty(pageNo) && StringUtil.isNotNullAndNotEmpty(pageSize)){
				pNo = Integer.parseInt(pageNo.trim());
				if(pNo <= 0){
					pNo =1;
				}
				pSize =	Integer.parseInt(pageSize.trim());
				if(pSize <= 0){
					pSize =100;
				}
			}
			RowBounds rowBounds = new RowBounds((pNo -1)*pSize, pSize);//(offset,limit)
			List<ExamPlace> data = examPlaceMapper.selectByExampleWithRowbounds(example, rowBounds);
			return new Page<ExamPlace>(data,pNo,pSize,total);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ExamPlaceVo> getExamPlaces(String cityId,String type) {
		try {
			ExamPlaceExample example = new ExamPlaceExample();
			ExamPlaceExample.Criteria criteria = example.createCriteria();
			if(StringUtil.isNotNullAndNotEmpty(cityId)){
				criteria.andCityIdEqualTo(Integer.parseInt(cityId.trim()));
			}
			if(StringUtil.isNotNullAndNotEmpty(type)){
				criteria.andTypeLike("%"+ type.trim()+"%");
			}
			List<ExamPlace> list = examPlaceMapper.selectByExample(example);
			if(null != list && list.size()>0){
				List<ExamPlaceVo> data = BeanCopy.copyList(list, ExamPlaceVo.class, BeanCopy.COPYALL);
				return data;
			}
			
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ExamPlace getExamPlaceById(Integer placeId) {
		ExamPlace ep = redisUtil.get(RedisKeys.REDISKEY.EXAM_PLACE + placeId);
		if(null == ep){
			ep = examPlaceMapper.selectByPrimaryKey(placeId);
			redisUtil.setAll(RedisKeys.REDISKEY.EXAM_PLACE + placeId,ep,RedisKeys.EXPIRE.WEEK);
		}
		
		return ep;
	}
	
	@Override
	public List<ExamPlaceCity> getExamPlaceCity(){
		List<ExamPlaceCity> data = redisUtil.get(RedisKeys.REDISKEY.EXAM_PLACE_CITY);
		if(null == data){
			ExamPlaceCityExample example = new ExamPlaceCityExample();
			example.createCriteria().andIsdelEqualTo((byte) 0);
			data = examPlaceCityMapper.selectByExample(example);
			redisUtil.setAll(RedisKeys.REDISKEY.EXAM_PLACE_CITY, data,RedisKeys.EXPIRE.WEEK);
		}
		
		return data;
	}

	@Override
	public ExamPlace getExamPlaceBySchoolId(Integer schoolId) {
		ExamPlace ep = redisUtil.get(RedisKeys.REDISKEY.EXAM_PLACE_SCHOOL + schoolId);
		if(null == ep){
			ExamPlaceExample example = new ExamPlaceExample();
			example.createCriteria().andSchoolIdEqualTo(schoolId);
			List<ExamPlace> data = examPlaceMapper.selectByExample(example);
			if(null != data && data.size() >0){
				ep = data.get(0);
				redisUtil.setAll(RedisKeys.REDISKEY.EXAM_PLACE_SCHOOL + schoolId, ep,RedisKeys.EXPIRE.WEEK);
				return ep;
			}else {
				//这个驾校没有同名的考场，也记录一个空串到缓存，避免多次查询
				redisUtil.setAll(RedisKeys.REDISKEY.EXAM_PLACE_SCHOOL + schoolId, new ExamPlace(),RedisKeys.EXPIRE.WEEK);
				return new ExamPlace();
			}
		}
		return ep;
	}
	
	
	
	

}




















