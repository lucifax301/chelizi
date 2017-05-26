package com.lili.pay.mapper.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lili.pay.dto.DepositDto;
import com.lili.pay.vo.DepositVo;

public interface DepositDtoMapper {
	
	List<DepositDto> queryDepositList(DepositDto depositDto);
	
	int queryLeftDepositCount(@Param("userId")Long userId,@Param("userType") String userType, @Param("startTime")String startTime, @Param("endTime")String endTime);
	
    int deleteByPrimaryKey(String waterid);

    int insert(DepositDto record);

    int insertSelective(DepositDto record);

    DepositDto selectByPrimaryKey(String waterid);

    int updateByPrimaryKeySelective(DepositDto record);

    int updateByPrimaryKey(DepositDto record);

	List<DepositDto> queryHasDeposit(DepositDto depositDto);

	void updateDepositList(List<DepositVo> depList);

}