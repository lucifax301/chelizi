package com.lili.coach.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lili.coach.dto.CarCheck;
import com.lili.coach.dto.Coach;
import com.lili.coach.dto.ErrorAppeal;
import com.lili.coach.vo.CoachStatusRecord;
import com.lili.common.vo.ReqResult;
import com.lili.order.vo.CoachClassTempVo;


public interface CoachService {
	
	public ReqResult addCoachAndLogin(String mobile,String codeInput,String password,String name) throws Exception;
	public ReqResult login(String mobile, String password);
	public ReqResult autoLogin(String coachId, String tokenId);
	public ReqResult logout(String coachId,String tokenId);
	public ReqResult updatePass(String coachId,String password, String headIcon, String name, String sex, String age, String mobile,String codeInput
			, String schoolName, String cityId, String cityName);	

	
	
	public ReqResult addIdCardInfo(String coachId, String name, String idCard,
			String picPath1, String picPath2, String tokenId);
	public ReqResult addCoachCardInfo(String coachId, String coachCardId,
			String teachArea, String carSchool, String picPath1, String picPath2, String tokenId)throws Exception;

	public ReqResult addCarInfo(String coachId, String carType,String licensePlateNumber,String drivingLicense,String picPath1, String drtype, 
			String brandName, String operateType, String carId) throws Exception;

	
	public ReqResult getBroadcast(String coachId, String tokenId);
	
	
	public ReqResult doStatus(String coachId,String carId,String courses, String status, String tokenId);
	public ReqResult doCourseStatus(String coachId,String orderId,String studentId, String status, String tokenId);


	public Boolean isExist(String coachId,String token);
	
	
	public ReqResult getWallet(String coachId);
	public ReqResult getBills(String coachId);
	public ReqResult getMessages(String userId, String userType, String pageNum, String pageSize, String time, String type);
	public ReqResult getNotices(String userId, String type, String pageNum, String pageSize);
	public ReqResult getNoticeIndex(String userId);
	public ReqResult getScope(String coachId, String tokenId);
	public ReqResult setScope(String coachId,String distance, String tokenId);
	public ReqResult getUserInfo(String coachId, String tokenId);
	public ReqResult getCarInfo(String coachId, String tokenId);
	
	
	public int addPerformanceAndMoney(String coachId, int performance, int money);
	
	public ReqResult getCoachsByIds(List<Long> coachIds);
	public ReqResult getTrfields(String coachId, String keyword, String rid);
	public ReqResult getStudentOne(String coachId, String studentId,
			String tokenId);
	public ReqResult updateHeadIcon(String userId, String picPath);
	
	public boolean isOnline(String coachId);
	public void setOnline(String coachId, boolean isOnline);
	public Map<Long, Boolean> isOnlineByIds(List<Long> coachIds);
	public ReqResult getRecords(String userId);
	public ReqResult postAgreement(String userId, String agreement);
	
	public ReqResult getBrandCarInfo(String coachId, String seqNum, String isCommon, String tokenId);
	public ReqResult checkpw(String userId, String userType, String passwd);
	public ReqResult addCoachVerfyMaterial(String userId, String userType,
			String coachCardId, String drType, String drPhoto, String drPhoto2,
			String coachCardPhoto);
	
	/**
	 * 更新教练资料，主键更新，如果更新密码需要md5加密
	 * @param coach
	 * @return
	 */
	public int updateCoach(Coach coach);
	/**
	 * 锁定教练
	 * @param userId	用户id
	 * @param state		0-正常；1-临时封号；2-永久封号
	 * @param reviveTime	解除封号的时间
	 * @param note	封号理由
	 * @return 0-操作成功；1-操作失败
	 */
	public int lockCoach(long userId,int state,Date reviveTime,String note);
	
	/**
	 * 保存、修改、关闭模板
	 * @param handleType
	 * @param classTempList
	 * @param tempId 
	 * @return
	 */
	public ReqResult saveClassTemp(String handleType, List<CoachClassTempVo> classTempList, String tempId);
	/**
	 * 查询模板
	 * @param userId
	 * @param tempId
	 * @param tempName
	 * @return
	 */
	public ReqResult queryTemplate(String userId, String tempId, String tempName);
	
	/**
	 * 按排版模板批量增加排班
	 * @param userId
	 * @param date
	 * @param tempId
	 * @return
	 */
	public ReqResult addClassForTemp(Long userId, Date date, String tempId);
	
	
	/**
	 * 获取个人信息错误信息申诉选项列表
	 * @return
	 */
	public ReqResult getErrorAppealItems();
	
	
	/**
	 * 提交错误信息申诉
	 * @return
	 */
	public ReqResult addErrorAppeal(ErrorAppeal errorAppeal);
	
	public void doInOutCarStatus(CoachStatusRecord record) throws Exception;
	
	/**
	 * 注册教练上传行驶、驾驶证件、车
	 * @param userId
	 * @param drPhoto
	 * @param drPhoto2
	 * @param drivePhoto
	 * @param drivePhoto2
	 * @param carNo
	 * @param drType
	 * @param carImg
	 * @return
	 */
	public ReqResult addRegCoach(String userId, String drPhoto, String drPhoto2, String isNewDrPhoto, String cityId, List<CarCheck> carJsonList);
	
	/**
	 * 查看信息验证
	 * @param userId
	 * @return
	 */
	public ReqResult checkInfo(String userId);
	
	/**
	 * 获取注册城市信息
	 * @param cityId
	 * @param cityName
	 * @param pid
	 * @param rlevel
	 * @return
	 */
	public ReqResult getRegistCity(String cityId, String cityName, String pid, String rlevel);
	
	public ReqResult carCheckPass(Long coachId, Integer checkDrState, String verifier);
	
	/**
	 * 微信通知模板发送通用接口
	 * @param token
	 * @param template
	 * @return
	 */
//	public boolean sendTemplateMsg(String token,Template template);
}
