package com.lili.finance.mapper.dao.common;

import java.util.List;
import java.util.Map;

import com.lili.finance.vo.SchDeposit;

public interface SchDepositDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SchDeposit record);

    int insertSelective(SchDeposit record);

    SchDeposit selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(SchDeposit record);

	SchDeposit querySchDeposit(SchDeposit schDeposit);

	List<SchDeposit> queryBatchDepositList(SchDeposit depositVo);

	int queryLeftDepositCount(SchDeposit depositVo);

	List<SchDeposit> queryBatchDepositLimitList(SchDeposit schdeposit);

	List<SchDeposit> queryDepositInfo(List<Integer> ids);

	void tellerTransferUpdateStatus(List<SchDeposit> depositUpdate);

	void updateStatusList(List<SchDeposit> depositUpdate);

	void updateStatus(SchDeposit schDeposit);

	List<SchDeposit> queryHandleList(Map<String, Object> params);

	void sysHandleStatus(List<SchDeposit> updateDepositList);
}