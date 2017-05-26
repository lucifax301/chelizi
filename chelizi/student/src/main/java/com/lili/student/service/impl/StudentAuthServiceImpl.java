package com.lili.student.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.vo.ReqConstants;
import com.lili.student.dto.Student;
import com.lili.student.dto.StudentAuth;
import com.lili.student.dto.StudentAuthExample;
import com.lili.student.manager.StudentManager;
import com.lili.student.mapper.dao.StudentAuthMapper;
import com.lili.student.service.StudentAuthService;

public class StudentAuthServiceImpl implements StudentAuthService {
	//@Autowired
	//private StudentAuthManager studentAuthManager;
	@Autowired
	private StudentManager studentManager;
	@Autowired
	private StudentAuthMapper studentAuthMapper;

	@Override
	public int changeState(int type, int state, List<Long> ids, String remark) {
		//状态变更是否需要前置检查
		//（1）更新认证表状态
		StudentAuth studentAuth = new StudentAuth();
		studentAuth.setState(state);
		studentAuth.setRemark(remark);
		StudentAuthExample example = new StudentAuthExample();
		StudentAuthExample.Criteria criteria = example.createCriteria();
		criteria.andIdIn(ids);
		studentAuthMapper.updateByExampleSelective(studentAuth, example);
		//（2）获取学员id
		List<StudentAuth> sas = studentAuthMapper.selectByExample(example);
		for(StudentAuth sa :sas){
			Student student = new Student();
			student.setStudentId(sa.getStudentId());
			if(type == ReqConstants.CHECK_TYPE_IDCARD){
				if(state == ReqConstants.CHECK_STATE_SUCCESS){
					student.setName(sa.getAuthName());
					student.setIdNumber(sa.getFileNo());
					student.setIdPhotoFront(sa.getPhoto());
					student.setIdPhotoBack(sa.getPhoto2());
					student.setIdExpires(sa.getDeadTime());//身份证有效期
					student.setCheckIdState((byte)state);
				}else{
					student.setCheckIdState((byte)state);
				}
			}else if(type == ReqConstants.CHECK_TYPE_DRIVECARD){
				if(state == ReqConstants.CHECK_STATE_SUCCESS){
					student.setDrType(sa.getDrtype());
					student.setApplyCarType(sa.getDrtype()+"");
					student.setDrLicence(sa.getFileNo());
					student.setDrPhoto(sa.getPhoto());
					student.setDrPhoto2(sa.getPhoto2());
					student.setDrExpires(sa.getDeadTime());//驾照有效期
					student.setCheckDriveIdState((byte)state);
				}else{
					student.setCheckDriveIdState((byte)state);
				}
			}else{
				continue;
			}
			studentManager.updateStudent(student);
		}
		
		return 0;
	}

}
