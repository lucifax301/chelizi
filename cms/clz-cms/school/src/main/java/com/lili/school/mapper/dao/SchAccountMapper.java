package com.lili.school.mapper.dao;

import com.lili.school.model.SchAccount;

public interface SchAccountMapper {
	
	SchAccount queryAccount(SchAccount schAccount);
	
	SchAccount queryBySchoolId(SchAccount record);
	
	SchAccount queryByPasswd(SchAccount record);
	
    int insert(SchAccount record);

    int insertSelective(SchAccount record);

    int updateByPrimaryKeySelective(SchAccount record);

    int updateByPrimaryKey(SchAccount record);

	void updateSchoolAccount(SchAccount schYKAccount);

	void updatePasswd(SchAccount dto);
	
	void updateMobile(SchAccount dto);

	void addMoneyBack(int money, long schoolId);

	SchAccount getSchoolIdMoney(long schoolId);

	Long querySchoolMoney();

	Long queryLiliWalletMoney();
    
}