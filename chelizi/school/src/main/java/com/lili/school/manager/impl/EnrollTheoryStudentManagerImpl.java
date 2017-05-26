package com.lili.school.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.vo.ReqConstants;
import com.lili.school.dto.EnrollTheoryStudent;
import com.lili.school.dto.EnrollTheoryStudentExample;
import com.lili.school.manager.EnrollTheoryStudentManager;
import com.lili.school.mapper.EnrollTheoryStudentMapper;

public class EnrollTheoryStudentManagerImpl implements
		EnrollTheoryStudentManager {
	
	@Autowired
	private EnrollTheoryStudentMapper enrollTheoryStudentMapper;

	@Override
	public EnrollTheoryStudent getEnrollTheoryStudentByStudentId(long userId) {
		EnrollTheoryStudentExample example = new EnrollTheoryStudentExample();
		EnrollTheoryStudentExample.Criteria criteria = example.createCriteria();
		criteria.andStudentIdEqualTo(userId);
		criteria.andIsdelEqualTo(ReqConstants.OPERATE_DELETE_NO);
		List<EnrollTheoryStudent> ets = enrollTheoryStudentMapper.selectByExample(example);
		if(null != ets && ets.size()>0){
			return ets.get(0);
		}
		return null;
	}

}
