package com.lili.school.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.PageUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.school.manager.CMSFieldManager;
import com.lili.school.mapper.dao.FieldMapper;
import com.lili.school.mapper.dao.SchoolMapper;
import com.lili.school.model.Field;
import com.lili.school.model.FieldBDTO;
import com.lili.school.model.School;
import com.lili.school.model.SchoolBDTO;

public class CMSFieldManagerImpl implements CMSFieldManager{

	@Autowired
	FieldMapper fieldMapper;
	
	@Autowired
	SchoolMapper schoolMapper;
	
	@Autowired
	private RedisUtil redisUtil;
	
	@Override
	public List<Field> findExportBatch(FieldBDTO dto) throws Exception {
		return fieldMapper.findExportBatch(dto);
	}

	@Override
	public Field findOne(long fieldId) throws Exception {
		return fieldMapper.findOne(fieldId);
	}

	@Override
	public PagedResult<Field> findBatch(FieldBDTO dto) throws Exception {
		PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
		return BeanUtil.toPagedResult((fieldMapper.findBatch(dto)));
	}

	@Override
	public long insertSelective(Field field) throws Exception {
		redisUtil.delete(REDISKEY.WECHAT_TRFIELD_LIST + field.getSchoolId());
		if (field.getSchoolId() != null) {
			School school = schoolMapper.findSchoolById(field.getSchoolId());
			if (school != null) {
				Integer trfieldCount = school.getTrfieldCount();
				if (trfieldCount == null) {
					trfieldCount = 0;
				}
				SchoolBDTO dto = new SchoolBDTO();
				dto.setSchoolId(field.getSchoolId());
				dto.setTrfieldCount(trfieldCount + 1);
				schoolMapper.updateScholInfo(dto );
			}
		}
		return fieldMapper.insertSelective(field);
	}

	@Override
	public long updateOne(Field field) throws Exception {
		redisUtil.delete(REDISKEY.WECHAT_TRFIELD_LIST + field.getSchoolId());
		return fieldMapper.updateOne(field);
	}

	@Override
	public Integer findTotalField(FieldBDTO dto) throws Exception {
		return fieldMapper.findTotalField(dto);
	}

	@Override
	public Field queryFiled(Field field) {
		return fieldMapper.queryFiled(field);
	}

	@Override
	public List<FieldBDTO> queryFieldIsUseList(String id) {
		List<FieldBDTO> fieldList = new ArrayList<FieldBDTO>();
		try {
			fieldList = fieldMapper.queryFieldIsUseList(id);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return fieldList;
	}

	@Override
	public List<FieldBDTO> queryFieldIsDelList(String id) {
		List<FieldBDTO> fieldList = new ArrayList<FieldBDTO>();
		try {
			fieldList =fieldMapper.queryFieldIsDelList(id);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return fieldList;
	}

	@Override
	public Long insertFieldInfo(Field field) {
		Long result = null;
		try {
			result = fieldMapper.insertSelective(field);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Field findOne(FieldBDTO dto) {
		return fieldMapper.findOneByBDTO(dto);
	}
	
	
}
