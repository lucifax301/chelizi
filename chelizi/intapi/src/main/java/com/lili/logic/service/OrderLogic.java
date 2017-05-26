package com.lili.logic.service;

import java.util.Map;

import com.lili.common.vo.ReqResult;
import com.lili.order.vo.OrderVo;

public interface OrderLogic {
	public ReqResult reservation(String studentId, String lge, String lae,
			String coachStatus, String examType, String carLevel,
			String courseId, String pageNo, String pageSize, String tokenId)
			throws Exception;

	public ReqResult addOrder(OrderVo orderVo,String tokenId) throws Exception;

	public ReqResult acceptOrder(String orderId, String coachId,
			String studentId, String tokenId) throws Exception;

	public ReqResult cancelOrder(String orderId, String retype, String reseaon,
			String userId, String userType, String tokenId) throws Exception;

	public ReqResult refuseOrder(String orderId, String coachId,
			String reseaon, String tokenId) throws Exception;

	public ReqResult continuePrice(String orderId, String studentId,
			String clzNum, String tokenId) throws Exception;

	public ReqResult bookOrder(OrderVo orderVo,  String tokenId) throws Exception;

	public ReqResult continueOrder(String orderId, String studentId,
			String clzNum, String price, String tokenId) throws Exception;

	public ReqResult stuCommentCoach(String coachId, String studentId,
			String orderId, String score, String tagId, String oneWord,
			String tokenId, String anonymity) throws Exception;

	public ReqResult coachCommentStu(String coachId, String studentId,
			String orderId, Map<Integer, Integer> scores, String oneWord,
			String tokenId) throws Exception;

	public ReqResult getCommentTag(String userType, String courseId,
			String tokenId) throws Exception;

	public ReqResult getStudentScore(String studentId, String subjectId,
			String courseId,String sort,Integer dlType) throws Exception;

	public ReqResult getCoachStatistc(String coachId, String date,
			String tokenId) throws Exception;
	
	public ReqResult getStudentTag(String courseId, String subjectId,Integer cityId,Integer dftype, String v) throws Exception;
	public ReqResult getJpushObject(long userId,int userType,int operate,String timeStamp);

	public ReqResult getCommetTag(String courseId, String type);
}
