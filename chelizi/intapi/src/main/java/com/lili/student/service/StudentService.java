package com.lili.student.service;

import com.lili.common.vo.ReqResult;
import com.lili.student.dto.MicroClass;
import com.lili.student.dto.Student;

import java.util.Date;

public interface StudentService {
	
	ReqResult addStudentAndLogin(String mobile, String codeInput,
								 String password);

	ReqResult login(String mobile, String password);
	ReqResult autoLogin(String studentId, String tokenId);
	ReqResult logout(String studentId, String tokenId);
	
	ReqResult updatePass(String studentId,
						 String password, String headIcon, String name, String sex, String age, String mobile, String codeInput);
	
	
	

	ReqResult addIdCardInfo(String studentId, String name,
							String idCard, String picPath1, String picPath2, String tokenId);
	

	ReqResult getStatus(String studentId, String tokenId);

	ReqResult getWallet(String studentId, String v);

	ReqResult getBills(String studentId, String userType);

	ReqResult getCoupons(String studentId, String tokenId);

	ReqResult getSkills(String studentId, String tokenId);

	ReqResult getCoaches(String studentId, String tokenId, Student s, Double double1, Double double2, String mark);
	ReqResult getCoachesOne(String studentId, String coachId, String tokenId);

    /**
     * 根据学员id获取其vip信息
     * @param studentId
     * @return
     */
	ReqResult getUserVipInfo(String studentId);

	Student getStudent(long studentId);

	ReqResult getUserInfo(String studentId, String tokenId);

	ReqResult updateHeadIcon(String userId, String picPath);
	
	ReqResult getStudentInfoByCoachId(String coachId, String tokenId, String pageNum, String pageSize);

	ReqResult getMessages(String userId, String userType, String pageNo, String pageSize, String time);
	
	ReqResult getNotices(String userId, String type, String pageNo, String pageSize);

	ReqResult getNoticeIndex(String userId);
	

	ReqResult doCourseStatus(String userId, String orderId,
							 String coachId, String status);
	
	int addMoney(long studentId, int money);

	ReqResult addDriveCard(String userId, String drType,
						   String drLicence, String drPhoto, String drPhoto2, String drExpires);

	ReqResult postAgreement(String userId, String agreement);

	ReqResult login2(String mobile, String code, String reqType);

	ReqResult postOpenId(String userId, String userType, String code);
	
	String getOpenId(String userId, String userType);
	
	/**
	 * 更新学员，主键更新，如果更新密码需要md5加密
	 * @param student
	 * @return
	 */
	int updateStudent(Student student);
	/**
	 * 锁定学员
	 * @param userId	用户id
	 * @param state		0-正常；1-临时封号；2-永久封号
	 * @param reviveTime	解除封号的时间
	 * @param note	封号理由
	 * @return 0-操作成功；1-操作失败
	 */
	public int lockStudent(long userId,int state,Date reviveTime,String note);

	/**
	 * 查询微课信息
	 * @param mocroClass
	 * @return
	 */
	ReqResult getMicroClass(MicroClass mocroClass);

	ReqResult getSubjectVideo(String userId, String userType, String cityId,String examId,String fileType, String subject, String id, String pageNo,
			String pageSize);


	ReqResult getBindState(String unionId, String openId, String accType);
	
	ReqResult bindThirdAccount(String unionId, String accType, String openId, String phoneNum, String code, String name, String headIcon);
	public ReqResult getCoaches(String studentId, String tokenId, Student s, Double lge, Double lae, String mark,Date date,String courseId,String coachName,String cityId);
	

	


}
