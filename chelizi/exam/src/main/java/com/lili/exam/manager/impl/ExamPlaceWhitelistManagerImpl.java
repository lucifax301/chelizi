package com.lili.exam.manager.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.Page;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.exam.dto.ExamPlaceWhitelist;
import com.lili.exam.dto.ExamPlaceWhitelistExample;
import com.lili.exam.manager.ExamPlaceWhitelistManager;
import com.lili.exam.mapper.ExamPlaceWhitelistMapper;

public class ExamPlaceWhitelistManagerImpl implements ExamPlaceWhitelistManager {
	private static Logger log = LoggerFactory.getLogger(ExamPlaceWhitelistManagerImpl.class);
	@Autowired
	private ExamPlaceWhitelistMapper examPlaceWhilelistMapper;
	@Autowired
	RedisUtil redisUtil;

	@Override
	public Page<ExamPlaceWhitelist> getWhitelist(Integer schoolId, String name,
			String mobile, Byte state,String ctime, Integer pageNo, Integer pageSize) {
		try {
			ExamPlaceWhitelistExample example = new ExamPlaceWhitelistExample();
			ExamPlaceWhitelistExample.Criteria criteria = example.createCriteria();

			if(null != schoolId){
				criteria.andSchoolIdEqualTo(schoolId);
			}
			if(null != name && !"".equals(name.trim())){
				criteria.andNameLike("%" + name.trim() + "%");
			}
			if(null != mobile && !"".equals(mobile.trim())){
				criteria.andMobileLike("%" + mobile.trim() + "%");
			}
			if(null != state){
				criteria.andStateEqualTo(state);
			}
			if(null != ctime && !"".equals(ctime.trim())){
				String[] cdates = ctime.split(",");
		    	Date d0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cdates[0] + " 00:00:00");
		    	Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cdates[1] + " 23:59:59");
		    	criteria.andCtimeBetween(d0, d1);
			}
			
			int total = examPlaceWhilelistMapper.countByExample(example);
			example.setOrderByClause("schoolId");
			
			int pNo =1;
			int pSize = 100;
			if(null != pageNo){
				pNo = pageNo;
				if(pNo <= 0){
					pNo =1;
				}
				pSize =	pageSize;
				if(pSize <= 0){
					pSize =100;
				}
			}
			RowBounds rowBounds = new RowBounds((pNo -1)*pSize, pSize);//(offset,limit)
			
			List<ExamPlaceWhitelist> data = examPlaceWhilelistMapper.selectByExampleWithRowbounds(example, rowBounds);
			return new Page<ExamPlaceWhitelist>(data,pNo,pSize,total);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer addWhitelist(Integer schoolId, String school, String name,
			String mobile) {
		try {
			ExamPlaceWhitelist record = new ExamPlaceWhitelist();
			record.setSchool(school);
			record.setSchoolId(schoolId);
			record.setName(name);
			record.setMobile(mobile);
			Date date = new Date();
			record.setCtime(date);
			record.setMtime(date);
			examPlaceWhilelistMapper.insertSelective(record);
			redisUtil.delete(REDISKEY.EXAM_PLACE_WHITE + mobile); //20161121更新缓存
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}
	
	@Override
	public Integer modifyWhitelist(Integer id, String name,
			String mobile) {
		try {
			ExamPlaceWhitelist record = new ExamPlaceWhitelist();
			record.setId(id);
			record.setName(name);
			record.setMobile(mobile);
			Date date = new Date();
			record.setMtime(date);
			examPlaceWhilelistMapper.updateByPrimaryKeySelective(record);
			redisUtil.delete(REDISKEY.EXAM_PLACE_WHITE + mobile); //20161121更新缓存
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	@Override
	public Integer delWhitelist(Integer id) {
		try {
			ExamPlaceWhitelist record = examPlaceWhilelistMapper.selectByPrimaryKey(id);
			examPlaceWhilelistMapper.deleteByPrimaryKey(id);
			redisUtil.delete(REDISKEY.EXAM_PLACE_WHITE + record.getMobile()); //20161121更新缓存
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}



























