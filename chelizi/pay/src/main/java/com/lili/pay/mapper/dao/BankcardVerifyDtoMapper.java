package com.lili.pay.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lili.pay.dto.BankcardVerifyDto;

/**
 * 
 * @author lzb
 *
 */
public interface BankcardVerifyDtoMapper {
	
	/**
	 * 是否已存在绑定记录
	 * @param userIdV 
	 * @param userTypeV 
	 * @param bankCard
	 * @param userId
	 * @param userType
	 * @param name
	 * @return
	 */
	BankcardVerifyDto queryIsExitBoundRecord(@Param("userType")Integer userType, @Param("userId")Long userId, @Param("bankCard") String bankCard);
	
	/**
	 * 获取绑定银行卡 
	 * @param userId
	 * @param userType
	 * @return
	 */
	List<BankcardVerifyDto> queryBoundBankCardList(@Param("userId") Integer userId,@Param("userType")  Integer userType);
	
	/**
	 * 查询绑定的银行卡张数
	 * @param userId
	 * @param userType
	 * @return
	 */
	int queryBankSize(@Param("userId") Long userId,@Param("userType")  Integer userType);
	
	
	BankcardVerifyDto queryIsExitBoundBankCard(@Param("userType") Integer userType, @Param("userId") Integer userId, @Param("bankCard") String bankCard);
	/**
	 * 解除绑定银行卡
	 * @param record
	 */
	void updateByUserId(BankcardVerifyDto record);
	
	void deleteByPrimaryKey(Integer id);

	void insert(BankcardVerifyDto record);

	void insertSelective(BankcardVerifyDto record);

    BankcardVerifyDto selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(BankcardVerifyDto record);

    void updateByPrimaryKey(BankcardVerifyDto record);





}