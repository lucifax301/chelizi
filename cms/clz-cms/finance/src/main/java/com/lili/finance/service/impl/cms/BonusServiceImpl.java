package com.lili.finance.service.impl.cms;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.constant.Constant;
import com.lili.cms.constant.MessageCode;
import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.cms.util.DateUtil;
import com.lili.cms.util.StringUtil;
import com.lili.coach.dto.CoachAccount;
import com.lili.coach.manager.CoachManager;
import com.lili.coach.model.Coach;
import com.lili.coach.service.CMSCoachService;
import com.lili.coach.service.ICmsCoachAccountService;
import com.lili.common.util.redis.RedisKeys;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.finance.manager.cms.IBonusManager;
import com.lili.finance.manager.common.IUserMoneyManager;
import com.lili.finance.model.common.UserMoney;
import com.lili.finance.service.ICmsBonusService;
import com.lili.finance.vo.BonusDetailVo;
import com.lili.finance.vo.BonusVo;

/**
 * 奖金
 * 
 * @author lzb
 * 
 */
public class BonusServiceImpl implements ICmsBonusService {

	Logger logger = Logger.getLogger(BonusServiceImpl.class);

	@Autowired
	IBonusManager bonusManager;

	@Autowired
	IUserMoneyManager userMoneyManager;
	
	@Autowired
	ICmsCoachAccountService coachAccountService;
	
	@Autowired
	CMSCoachService coachService;
	
	@Autowired
	RedisUtil redisUtil;
	
	@Autowired
	CoachManager coachManager;
	
	@Override
	public ResponseMessage upload(File outfile, String tagFileName, String creator) {
		String resp = null;
		try {
	      resp = bonusManager.uploadExcelDetail(outfile, tagFileName, creator);
	      if(resp != null){
	    	  return new ResponseMessage(MessageCode.MSG_MONEY_OVER_LIMIT);
	      }
		} 
		catch (Exception e) {
			logger.error("************************************ error: " + e.getMessage());
			e.printStackTrace();
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}
	
	@Override
	public ResponseMessage uploadNew(byte[] b, String tagFileName, String creator) {
		String resp = null;
		try {
	      resp = bonusManager.uploadExcelDetailNew(b, tagFileName, creator);
	      if(resp != null){
	    	  return new ResponseMessage(MessageCode.MSG_MONEY_OVER_LIMIT);
	      }
		} 
		catch (Exception e) {
			logger.error("************************************ error: " + e.getMessage());
			e.printStackTrace();
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	/**
	 * 获取列表
	 */
	@Override
	public String query(BonusVo bonusVo) {
		String resp = null;
		PagedResult<BonusVo> bonusList = null;
		try {
			bonusList = bonusManager.queryBonusList(bonusVo);
			resp = new ResponseMessage().addResult("pageData", bonusList).build();
		} 
		catch (Exception e) {
			logger.error("************************************ error: " + e.getMessage());
		}
		return resp;
	}

	/**
	 * 详情列表
	 */
	@Override
	public String queryDetail(BonusDetailVo bonusDetailVo) {
		String resp = null;
		PagedResult<BonusDetailVo> bonusDetailList = null;
		try {
			bonusDetailList = bonusManager.queryBonusDetailList(bonusDetailVo);
			resp =  new ResponseMessage().addResult("pageData", bonusDetailList).build();
		} 
		catch (Exception e) {
			logger.error("************************************ error: " + e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 *  确认提交
	 */
	@Override
	public ResponseMessage submit(BonusVo bonusVo) {
		try {
			BonusVo bonusInfo = bonusManager.queryBonusInfo(bonusVo);
			if(bonusInfo.getStatus() != Constant.BONUS_INIT){
				return new ResponseMessage(MessageCode.MSG_DEPOSIT_STATUS_ERROR);
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", bonusVo.getId());
			map.put("status", 2);
			map.put("verifier", bonusVo.getVerifier());
			map.put("verifieTime", DateUtil.currentDatetime());
			bonusManager.submitBonusStatus(map);
			logger.info("-------------------------------- BonusController submit Success!");
		} 
		catch (Exception e) {
			logger.error("************************************ error: " + e.getMessage());
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}

		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}
	

	@Override
	public ResponseMessage grant(BonusVo bonusVo) {
		Integer id = null;
		try {
			//获取奖金表
			BonusVo bonusInfo = bonusManager.queryBonusInfo(bonusVo);
			logger.info("********************************bonusInfo " + bonusInfo);
			if(bonusInfo.getStatus() != Constant.BONUS_SURE){
				return new ResponseMessage(MessageCode.MSG_DEPOSIT_STATUS_ERROR);
			}
			 if (redisUtil.isExist(REDISKEY.DEPOST_APPLY + "bonus."+ id, String.valueOf(id))) {
				 return new ResponseMessage(MessageCode.MSG_DEPOSIT_STATUS_ERROR);
		     }
			 id =  bonusInfo.getId();
			 
			redisUtil.set(REDISKEY.DEPOST_APPLY + "bonus."+ id, id, RedisKeys.EXPIRE.HOUR);
			
			Map<String, Object> repeatMap  = new HashMap<String, Object>();
			repeatMap.put("bonusId", bonusVo.getId());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", bonusVo.getId());
			map.put("status", 3);
			map.put("verifier", bonusVo.getVerifier()); //奖金确认人
			map.put("verifieTime", DateUtil.currentDatetime()); //奖金确认时间
			
			//查询明细，打钱到教练账户，增加money日志
			Map<String, Object> detailMap = new HashMap<String, Object>();
			detailMap.put("bonusId", bonusVo.getId());
			List<BonusDetailVo> bonusDetailList = bonusManager.queryBonusDetailListInfo(detailMap); //奖金发放明细表，待发放
			if(null == bonusDetailList || bonusDetailList.size()==0){
				redisUtil.delete(REDISKEY.DEPOST_APPLY + "bonus."+ id);
				return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
			}
			int money;
			Map<String, Object> params = new HashMap<String, Object>();
			Map<String, Object> paramsDetail = new HashMap<String, Object>();
			CoachAccount coachAccountAdd = null;
			List<CoachAccount> coachAccountList = new ArrayList<CoachAccount>();
			for (BonusDetailVo bonusDetailVo : bonusDetailList) {
				money = bonusDetailVo.getMoney();  
				if (money > 200000 || money < 0) {
					logger.info("******************* Money is over!");
				}
				
				Coach coachInfo = coachService.findByPhone(bonusDetailVo.getPhoneNum());
				if (coachInfo == null) {	//如果未找到该教练，直接返回失败
					params.put("id", bonusVo.getId());
					params.put("status", 4);
					params.put("award", bonusVo.getVerifier());
					params.put("awardTime", DateUtil.currentDatetime());
					params.put("remark", bonusDetailVo.getPhoneNum() + " 无账户");
					
					paramsDetail.put("bonusId", bonusVo.getId());
					paramsDetail.put("status", 2);
					bonusManager.updateBonusDetail(params, paramsDetail);//更新主状态为失败
					redisUtil.delete(REDISKEY.DEPOST_APPLY + "bonus."+ id);
					return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
				}
					
				coachAccountAdd = new CoachAccount(); //防重复
				coachAccountAdd.setMoney(money);
				coachAccountAdd.setCoachId(coachInfo.getCoachId());
				coachAccountList.add(coachAccountAdd);
				
			}
			paramsDetail.put("bonusId", bonusVo.getId());
			paramsDetail.put("status", 1);
			
			boolean isSuc = coachManager.payCoachesBonuses(coachAccountList); //教练加奖金、流水；喱喱扣余额、流水。
			redisUtil.delete(REDISKEY.DEPOST_APPLY + "bonus."+ id);
			if(isSuc){
				bonusManager.grantBonusDetail(map,paramsDetail, null); //更新明细状态为成功,
				return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
			}
			else {
				return new ResponseMessage(MessageCode.MSG_FAIL);
			}
		} 
		catch (Exception e) {
			 redisUtil.delete(REDISKEY.DEPOST_APPLY + id);
			logger.error("************************************ error: " + e.getMessage());
			 return new ResponseMessage(MessageCode.MSG_FAIL);
		}
		
	}
	
	/**
	 * 记录金额操作日志
	 * @param userId
	 * @param userType
	 * @param leftMoney
	 * @param price
	 * @param moneyChange
	 * @param orderId
	 */
    public UserMoney addMoneyLog(Long userId, int userType,int leftMoney, Integer price,  String orderId)
    {
    	UserMoney userMoneyDto = new UserMoney();
        try {
			userMoneyDto.setChangeValue(price);
			userMoneyDto.setLeftValue(leftMoney);
			userMoneyDto.setOperateTime(new Date());
			userMoneyDto.setOperateType(2);
			userMoneyDto.setPayWay("system");
			userMoneyDto.setRemark(Constant.BONUS_MONEY);
			userMoneyDto.setUserId(userId);
			userMoneyDto.setUserType(userType);
			userMoneyDto.setOrderId(orderId);
			userMoneyDto.setStatus(1);
			userMoneyDto.setIsBalance(1);
			userMoneyDto.setIsEarning(1); //1-收入
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return userMoneyDto;
    }

	@Override
	public ResponseMessage delete(BonusVo bonusVo) {
		try {
			BonusVo bonusInfo = bonusManager.queryBonusInfo(bonusVo);
			if(bonusInfo.getStatus() != Constant.BONUS_INIT){
				return new ResponseMessage(MessageCode.MSG_DEPOSIT_STATUS_ERROR);
			}
			
			bonusManager.deleteBonusInfo(bonusVo.getId());
			logger.info("-------------------------------- BonusController Delete Success!");
		} 
		catch (Exception e) {
			logger.error("************************************ error: " + e.getMessage());
			 return new ResponseMessage(MessageCode.MSG_FAIL);
		}
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}


	@Override
	public ResponseMessage finReject(BonusVo bonusVo) {
		try {
			BonusVo bonusInfo = bonusManager.queryBonusInfo(bonusVo);
			if(bonusInfo.getStatus() != Constant.BONUS_SURE){
				return new ResponseMessage(MessageCode.MSG_DEPOSIT_STATUS_ERROR);
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", bonusVo.getId());
			map.put("status", 5);
			map.put("remark", bonusVo.getRemark());
			map.put("verifier", bonusVo.getVerifier());
			map.put("verifieTime", DateUtil.currentDatetime());
			bonusManager.submitBonusStatus(map);
			logger.info("-------------------------------- BonusController Delete Success!");
		} 
		catch (Exception e) {
			e.printStackTrace();
			logger.error("************************************ error: " + e.getMessage());
            return new ResponseMessage(MessageCode.MSG_FAIL);
		}
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

}
