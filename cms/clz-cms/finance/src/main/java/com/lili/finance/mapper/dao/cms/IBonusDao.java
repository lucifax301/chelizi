package com.lili.finance.mapper.dao.cms;



import java.util.List;
import java.util.Map;

import com.lili.finance.vo.BonusVo;

/**
 * 奖金DAO
 * @author lzb
 *
 */
public interface IBonusDao {
	/**
	 * 查询
	 * @param bonus
	 * @return
	 */
	List<BonusVo> queryList(BonusVo bonusVo);
	
	BonusVo queryBonus(BonusVo bonusVo);
	
	void updateState(Map<String, Object> params);
	
    int deleteByPrimaryKey(Integer id);

    int insert(BonusVo record);

    int insertSelective(BonusVo record);

    BonusVo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BonusVo record);

    int updateByPrimaryKey(BonusVo record);

    Long getYesterdayMoney();

	BonusVo queryBonusInfo(BonusVo bonusVo);
    
}