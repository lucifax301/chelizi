package com.lili.user.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.PageUtil;
import com.lili.user.manager.CMSSchoolDataManager;
import com.lili.user.mapper.dao.SchoolDataMapper;
import com.lili.user.model.CarFile;
import com.lili.user.model.CarFileBDTO;
import com.lili.user.model.CoachFile;
import com.lili.user.model.CoachFileBDTO;
import com.lili.user.model.FieldFile;
import com.lili.user.model.FieldFileBDTO;
import com.lili.user.model.SchoolDataFile;
import com.lili.user.model.SchoolDataFileBDTO;
import com.lili.user.model.StudentFile;
import com.lili.user.model.StudentFileBDTO;

public class CMSSchoolDataManagerImpl implements CMSSchoolDataManager{

	@Autowired
	SchoolDataMapper schoolDataMapper;
	
	@Override
	public Long insertCarFile(CarFile file) {
		return schoolDataMapper.insertCarFile(file);
	}

	@Override
	public Long insertCoachFile(CoachFile file) {
		return schoolDataMapper.insertCoachFile(file);
	}

	@Override
	public Long insertStudentFile(StudentFile file) {
		return schoolDataMapper.insertStudentFile(file);
	}

	@Override
	public Long insertFieldFile(FieldFile file) {
		return schoolDataMapper.insertFieldFile(file);
	}

	@Override
	public Long insertSchoolDataFile(SchoolDataFile file) {
		return schoolDataMapper.insertSchoolDataFile(file);
	}

	@Override
	public PagedResult<StudentFile> findStudentBatch(StudentFileBDTO dto) {
		PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
		return BeanUtil.toPagedResult((schoolDataMapper.findStudentBatch(dto)));
	}

	@Override
	public PagedResult<SchoolDataFile> findSchoolDataBatch(SchoolDataFileBDTO dto) {
		PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
		return BeanUtil.toPagedResult((schoolDataMapper.findSchoolDataBatch(dto)));
	}

	@Override
	public PagedResult<CarFile> findCarBatch(CarFileBDTO dto) {
		PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
		return BeanUtil.toPagedResult((schoolDataMapper.findCarBatch(dto)));
	}

	@Override
	public PagedResult<FieldFile> findFieldBatch(FieldFileBDTO dto) {
		PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
		return BeanUtil.toPagedResult((schoolDataMapper.findFieldBatch(dto)));
	}

	@Override
	public PagedResult<CoachFile> findCoachBatch(CoachFileBDTO dto) {
		PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
		return BeanUtil.toPagedResult((schoolDataMapper.findCoachBatch(dto)));
	}

	

}
