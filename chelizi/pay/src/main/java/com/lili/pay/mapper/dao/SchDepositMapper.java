package com.lili.pay.mapper.dao;


import com.lili.pay.dto.SchDeposit;

import java.util.List;

public interface SchDepositMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SchDeposit record);

    int insertSelective(SchDeposit record);

    SchDeposit selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(SchDeposit record);

	SchDeposit querySchDeposit(SchDeposit schDeposit);

	List<SchDeposit> queryBatchDepositList(SchDeposit depositVo);

	int queryLeftDepositCount(SchDeposit depositVo);

	List<SchDeposit> queryDepositInfo(List<Integer> ids);

	void tellerTransferUpdateStatus(List<SchDeposit> depositUpdate);

	void updateStatusList(List<SchDeposit> depositUpdate);

	void updateStatus(SchDeposit schDeposit);
}