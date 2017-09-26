package com.lili.exam.manager.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.coach.dto.Car;
import com.lili.common.util.Page;
import com.lili.common.util.StringUtil;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.exam.dto.ExamVip;
import com.lili.exam.dto.ExamVipCoach;
import com.lili.exam.manager.ExamVipManager;
import com.lili.exam.mapper.ExamVipCoachMapper;
import com.lili.exam.mapper.ExamVipMapper;

public class ExamVipManagerImpl implements ExamVipManager {

	private static Logger log = LoggerFactory.getLogger(ExamVipManagerImpl.class);
	
	@Autowired
	private ExamVipMapper examVipMapper;
	
	@Autowired
	private ExamVipCoachMapper examVipCoachMapper;
	
	@Autowired
	private RedisUtil redisUtil;
	
	@Override
	public int addExamVip(ExamVip examVip) {
		return examVipMapper.insertExamVip(examVip);
	}

	@Override
	public int updateExamVip(ExamVip examVip) {
		return examVipMapper.updateExamVip(examVip);
	}
	
	@Override
	public int delExamVip(ExamVip examVip) {
		ExamVipCoach evc=new ExamVipCoach();
		evc.setVipId(examVip.getId());
		examVipCoachMapper.deleteExamVipCoachByVip(evc);
		return examVipMapper.deleteExamVip(examVip);
	}

	@Override
	public Page<ExamVip> getExamVip(ExamVip examVip,String pageNo, String pageSize) {
		int total = examVipMapper.countExamVip(examVip);
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
		List<ExamVip> data = examVipMapper.select(examVip, rowBounds);
		return new Page<ExamVip>(data,pNo,pSize,total);
	}
	
	@Override
	public int countExamVip(ExamVip examVip){
		return examVipMapper.countExamVip(examVip);
	}
	
	@Override
	public List<ExamVip> getExamVipList(ExamVip examVip,String pageNo, String pageSize) {
		
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
		List<ExamVip> data = examVipMapper.select(examVip, rowBounds);
		return data;
	}
	
	@Override
	public List<ExamVip> getExamVipList(ExamVip examVip) {
		
		List<ExamVip> data = examVipMapper.selectAll(examVip);
		return data;
	}

	@Override
	public ExamVip getExamVipOne(Integer id) {
		
		ExamVip e=redisUtil.get(REDISKEY.SCHOOL_VIP_INFO + id);
		if(e==null){
			ExamVip p=new ExamVip();
			p.setId(id);
			e= examVipMapper.selectByPrimaryKey(p);
			redisUtil.set(REDISKEY.SCHOOL_VIP_INFO + id, e,3600*24);
		}
		return e;
	}

	@Override
	public int addExamVipCoach(ExamVipCoach examVipCoach) {
		return examVipCoachMapper.insertExamVipCoach(examVipCoach);
	}

	@Override
	public int updateExamVipCoach(ExamVipCoach examVipCoach) {
		return examVipCoachMapper.updateExamVipCoach(examVipCoach);
	}

	@Override
	public int delExamVipCoach(ExamVipCoach examVipCoach) {
		ExamVipCoach exist=examVipCoachMapper.selectByPrimaryKey(examVipCoach);
		if(exist!=null){
			redisUtil.delete(REDISKEY.SCHOOL_VIP_COACH + exist.getSchoolId()+"."+exist.getMobile());
		}
		return examVipCoachMapper.deleteExamVipCoach(examVipCoach);
	}

	@Override
	public ExamVipCoach getExamVipCoach(Integer id) {
		ExamVipCoach p=new ExamVipCoach();
		p.setId(id);
		return examVipCoachMapper.selectByPrimaryKey(p);
	}
	
	@Override
	public ExamVipCoach getExamVipCoach(String mobile,int schoolId) {
		
		ExamVipCoach e=redisUtil.get(REDISKEY.SCHOOL_VIP_COACH + schoolId+"."+mobile);
		if(e==null){
			ExamVipCoach p=new ExamVipCoach();
			p.setMobile(mobile);
			p.setSchoolId(schoolId);
			e=examVipCoachMapper.get(p);
			redisUtil.set(REDISKEY.SCHOOL_VIP_COACH + schoolId+"."+mobile, e,3600*1);
		}
		return e;
	}

	@Override
	public Page<ExamVipCoach> getExamVipCoach(ExamVipCoach examVipCoach,
			String pageNo, String pageSize) {
		int total = examVipCoachMapper.countExamVipCoach(examVipCoach);
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
		List<ExamVipCoach> data = examVipCoachMapper.select(examVipCoach, rowBounds);
		return new Page<ExamVipCoach>(data,pNo,pSize,total);
	}

	
}
