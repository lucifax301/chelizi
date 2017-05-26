package com.lili.school.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.PageUtil;
import com.lili.school.manager.SchAccountManager;
import com.lili.school.mapper.dao.SchAccountMapper;
import com.lili.school.mapper.dao.SchoolExtendMapper;
import com.lili.school.model.SchAccount;
import com.lili.school.model.SchoolExtend;

public class SchAccountMangerImpl implements SchAccountManager {
	
	@Autowired
	SchAccountMapper schAccountMapper;
	
	@Autowired
	SchoolExtendMapper schoolExtendMapper;

	@Override
	public void applyAccount(SchAccount schAccount, SchoolExtend schoolExtend) throws Exception {
		try {
			SchAccount exitAccount = schAccountMapper.queryBySchoolId(schAccount);
			if(exitAccount == null){
				schAccountMapper.insertSelective(schAccount);
			}
			
			SchoolExtend isExit = schoolExtendMapper.queryBySchoolId(schoolExtend);
			if ( isExit != null) {//是否有原记录，更新为不启用
				 SchoolExtend schoolIsDel = new SchoolExtend();
				 schoolIsDel.setSchoolId(schoolExtend.getSchoolId());
				 schoolIsDel.setIsDel(1);
				schoolExtendMapper.updateIsDel(schoolIsDel);
			}
			schoolExtendMapper.insertSelective(schoolExtend);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void changeSchoolInfo(SchoolExtend schoolExtend) {
		try {
			schoolExtendMapper.insertSelective(schoolExtend);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public SchAccount queryAccount(SchAccount schAccount) throws Exception {
		try {
			return schAccountMapper.queryAccount(schAccount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public SchoolExtend queryExtendInfo(SchoolExtend schoolExtend) throws Exception {
		try {
			return schoolExtendMapper.queryExtendInfo(schoolExtend);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public SchAccount queryByPasswd(SchAccount schAccount) throws Exception {
		try {
			return schAccountMapper.queryByPasswd(schAccount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateSchoolAccount(SchAccount schYKAccount) {
		try {
			schAccountMapper.updateSchoolAccount(schYKAccount);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updatePasswd(SchAccount dto) {
		try {
			schAccountMapper.updatePasswd(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateMobile(SchAccount dto) {
		try {
			schAccountMapper.updateMobile(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updatepPotocolOpen(SchoolExtend schoolExtend) {
		try {
			schoolExtendMapper.updatepPotocolOpen(schoolExtend);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void closeRemark(SchoolExtend schoolExtend) {
		try {
			schoolExtendMapper.updateIsChange(schoolExtend);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateIsChange(SchoolExtend schoolExtend) {
		try {
			schoolExtendMapper.updateIsChange(schoolExtend);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updatePotocolOpenInit(SchoolExtend schoolExtend) {
		try {
			schoolExtendMapper.updatePotocolOpenInit(schoolExtend);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public SchAccount queryBySchoolId(SchAccount schAccount) {
		SchAccount schAccountRe = null;
		try {
			schAccountRe =  schAccountMapper.queryBySchoolId(schAccount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return schAccountRe;
	}

	@Override
	public SchAccount getSchoolIdMoney(long schoolId) {
		try {
			SchAccount schAccount = new SchAccount();
			schAccount.setSchoolId(schoolId);
			return schAccountMapper.queryBySchoolId(schAccount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addMoneyBack(int money, long schoolId) {
		try {
			schAccountMapper.addMoneyBack(money, schoolId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public PagedResult<SchoolExtend> queryBurse(SchoolExtend dto) {
		try {
			PageUtil.startPage(dto.getPageNo(),  dto.getPageSize());
			return BeanUtil.toPagedResult(schoolExtendMapper.queryBurse(dto));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<SchoolExtend> querySchoolBurseList(String id) {
		try {
			return schoolExtendMapper.querySchoolBurseList(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	@Override
	public void batchUpdateSchoolExtendList(List<SchoolExtend> updateSchoolExtendList, Integer checkSign) {
		try {
			if (checkSign == 1) {//同意变更：更新原审核通过的为不启用
				schoolExtendMapper.batchUpdateNotUsePassList(updateSchoolExtendList);
			}
			else if (checkSign == 3) {//审核通过：更新原审核不通过的为不启用
				schoolExtendMapper.batchUpdateNotUseList(updateSchoolExtendList);
			}
			schoolExtendMapper.batchUpdateSchoolExtendList(updateSchoolExtendList);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Long querySchoolMoney() {
		try {
			return schAccountMapper.querySchoolMoney();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Long queryLiliWalletMoney() {
		try {
			return schAccountMapper.queryLiliWalletMoney();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
