package com.lili.finance.manager.cms;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.lili.cms.entity.PagedResult;
import com.lili.finance.model.common.UserMoney;
import com.lili.finance.vo.BonusDetailVo;
import com.lili.finance.vo.BonusVo;

public interface IBonusManager {
	/**
	 * 解析excel
	 */
	public abstract BonusVo uploadExcel(BonusVo bonus);
	
	/**
	 * 解析excel
	 */
	public abstract String uploadExcelDetail(File outfile,String fileName, String creator);
	
	public String uploadExcelDetailNew(byte[] b, String fileName, String creator);

	/**s
	 * 查询奖金列表
	 * @param params
	 * @return
	 */
	public abstract PagedResult<BonusVo> queryBonusList(BonusVo bonusVo);
	
	/**
	 * 奖金明细列表
	 * @param params
	 * @return
	 */
	public abstract PagedResult<BonusDetailVo> queryBonusDetailList(BonusDetailVo bonusDetailVo);
	
	/**
	 * 确认提交
	 * @param ids
	 */
    public abstract  void submitBonusStatus(Map<String, Object> params);
    
    /**
     * 发放奖金
     * @param ids
     */
    public abstract  void grantBonus(Map<String, Object> params);
   
    
    /**
     * 删除奖金
     * @param ids
     */
    public abstract  void deleteBonusInfo(Integer id);
    
    public abstract List<BonusDetailVo> queryBonusDetailListInfo(Map<String, Object> params);
    
    /**
     * 发放奖金明细状态
     * @param paramsDetail 
     * @param ids
     */
    public abstract  void updateBonusDetail(Map<String, Object> params, Map<String, Object> paramsDetail);
    
	public abstract  void  grantBonusDetail(Map<String, Object> params, Map<String, Object> paramsDetail, List<UserMoney> userMoneyList) ;

	public abstract  Integer queryRepeat(Map<String, Object> params);

	public abstract BonusVo queryBonusInfo(BonusVo bonusVo);
}
