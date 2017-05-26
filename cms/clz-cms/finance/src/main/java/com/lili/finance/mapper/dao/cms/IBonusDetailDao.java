package com.lili.finance.mapper.dao.cms;



import java.util.List;
import java.util.Map;

import com.lili.finance.model.cms.BonusDetail;
import com.lili.finance.vo.BonusDetailVo;

/**
 * 奖金明细DAO
 * @author lzb
 *
 */
public interface IBonusDetailDao {
	
	/**
	 * 查询
	 * @param bonus
	 * @return
	 */
	List<BonusDetailVo> queryList(BonusDetailVo bonusDetaiVol);
	
	List<BonusDetailVo> queryRepeatList(Map<String, Object> params);
	
	Integer queryRepeat(Map<String, Object> params);
	
	void insertList(List<BonusDetail> bonusDetail);
	
    void  deleteByBonusId(Integer bonusId);
    
    void updateStatus(Map<String, Object> params);
    
    int deleteByPrimaryKey(Integer id);

    int insert(BonusDetail record);

    int insertSelective(BonusDetail record);

    BonusDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BonusDetail record);

    int updateByPrimaryKey(BonusDetail record);
}