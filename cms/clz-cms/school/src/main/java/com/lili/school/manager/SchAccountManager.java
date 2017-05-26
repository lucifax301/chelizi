package com.lili.school.manager;

import java.util.List;

import com.lili.cms.entity.PagedResult;
import com.lili.school.model.SchAccount;
import com.lili.school.model.SchoolExtend;

public interface SchAccountManager {
    
	public abstract  void applyAccount(SchAccount schAccount, SchoolExtend schoolExtend) throws Exception;
	
	public SchoolExtend  queryExtendInfo(SchoolExtend schoolExtend) throws Exception;
	
	public SchAccount  queryAccount(SchAccount schAccount) throws Exception;
	
	public SchAccount  queryByPasswd(SchAccount schAccount) throws Exception;

	public abstract void updateSchoolAccount(SchAccount schYKAccount);

	public abstract void updatePasswd(SchAccount dto);

	public abstract void updateMobile(SchAccount dto);

	public abstract void updatepPotocolOpen(SchoolExtend schoolExtend);

	public abstract void closeRemark(SchoolExtend schoolExtend);

	public abstract void updateIsChange(SchoolExtend schoolExtend);

	public abstract void updatePotocolOpenInit(SchoolExtend schoolExtend);

	public abstract void changeSchoolInfo(SchoolExtend schoolExtend);

	public abstract SchAccount queryBySchoolId(SchAccount dto);

	public abstract SchAccount getSchoolIdMoney(long schoolId);

	public abstract void addMoneyBack(int money, long schoolId);

	public abstract PagedResult<SchoolExtend> queryBurse(SchoolExtend dto);

	public abstract List<SchoolExtend> querySchoolBurseList(String id);

	public abstract void batchUpdateSchoolExtendList(List<SchoolExtend> updateSchoolExtendList, Integer checkSign);

	public abstract Long querySchoolMoney();

	public abstract Long queryLiliWalletMoney();


}
