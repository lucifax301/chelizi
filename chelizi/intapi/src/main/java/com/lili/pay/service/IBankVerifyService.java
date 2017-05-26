package com.lili.pay.service;

import com.lili.common.vo.ReqResult;

/**
 * 银行卡绑定服务接口
 * 
 * @author Administrator
 *
 */
public interface IBankVerifyService {

		/**
		 * 申请绑定银行卡
		 * @param bankName
		 * @param bankCard
		 * @param name
		 * @param mobile
		 * @param userId
		 * @param userType
		 * @param timestamp
		 * @param sign
		 * @return
		 */
		public ReqResult boundBankCard(String bankName, String bankCard, String name, String mobile, String userId,
				String userType, String timestamp, String sign);
	
		/**
		 * 查询已绑定银行卡
		 * @param userId
		 * @param userType
		 * @param timestamp
		 * @param sign
		 * @return
		 */
		public ReqResult queryBoundBankCard(String userId, String userType, String timestamp, String sign);
		
		
		/**
		 * 解除绑定银行卡
		 * @param pw
		 * @param bankCard
		 * @param userId
		 * @param userType
		 * @param timestamp
		 * @param sign
		 * @return
		 */
		public ReqResult removeBoundBankCard(String pw, String bankCard, String userId, String userType, String timestamp, String sign);
		
		
		/**
		 * 设置支付密码
		 * @param pw
		 * @param userId
		 * @param userType
		 * @param timestamp
		 * @param sign
		 * @return
		 */
		public ReqResult setPassword(String pw, String userId, String userType,String reqType, String timestamp, String sign);
		
		/**
		 * 设置支付密码
		 * @param pw
		 * @param userId
		 * @param userType
		 * @param timestamp
		 * @param sign
		 * @return
		 */
		public ReqResult validPassword(String pw, String userId, String userType, String timestamp, String sign);
		
		/**
		 * 获取银行卡开户行
		 * @param bankCard
		 * @return
		 */
		public ReqResult getBankInfo(String bankCard);

		/**
		 * 判断银行卡是否已绑定
		 * @param bankCard
		 * @param userId
		 * @param userType
		 * @return
		 */
		public ReqResult validBindBank(String bankCard, String userId, String userType);
}
