package com.lili.bbs.service;

import com.lili.bbs.vo.BbsMessage;
import com.lili.common.vo.ReqResult;

public interface IBBSService {

	ReqResult getBBSList(String userId, Integer userType, Integer pageSize, Integer pageIndex);

	ReqResult reply(Long userId, Integer userType, String userName, String headIcon, Integer bbsId, String cityId,
			String cityName,String byreId,  String byreUserId, String byreUserName, String byreUserType, 
			String byreContent, String replyContent, String opertionType);

	ReqResult praise(Long userId, Integer userType, String userName, String headIcon, Integer bbsId,
			String praiseUserId, String praiseUserType, Integer isDel);

	ReqResult newBBSContent(Long userId, Integer userType, String userName, String headIcon, Integer classify, String cityId,
			String cityName, String topicId, String topicName, String titleType, String isDel, String title, String content, String pic);

	ReqResult getBBSDetailInfo(String userId, Integer userType, Integer bbsId);
	
	ReqResult getBBSReplyList(String userId, Integer userType, Integer bbsId, Integer pageSize, Integer pageIndex);

	ReqResult getBBSPraiseList(String userId, Integer userType, Integer bbsId, Integer pageSize, Integer pageIndex);

	ReqResult inform(Long userId, Integer userType, String userName, String headIcon,String reason, Integer bbsId, String title,
			String content, String isDel);

	ReqResult getMyBBSList(Long userId, Integer userType, Integer pageSize, Integer pageIndex);

	ReqResult getMyBBSReplyList(Long userId, Integer userType, Integer pageSize, Integer pageIndex);

	ReqResult getMyBBSPraiseList(Long userId, Integer userType, Integer pageSize, Integer pageIndex);

	ReqResult getBBSTopic(Integer userType);

	ReqResult delTopic(Long userId, Integer userType, Integer bbsId);

	ReqResult editBBSContent(BbsMessage bbsMessage);

}
