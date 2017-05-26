package com.lili.share.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.BeanCopy;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.share.dao.mapper.ShareMapper;
import com.lili.share.dao.po.SharePo;
import com.lili.share.dao.po.ShareQuery;
import com.lili.share.service.IShareService;
import com.lili.share.vo.ShareVo;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;

public class ShareServiceConImpl implements IShareService {

	Logger logger = LoggerFactory.getLogger(ShareServiceConImpl.class);

	@Autowired
	private ShareMapper shareMapper;
	@Autowired
	private StudentManager studentManager;

	/**
	 * 查看分享列表
	 */
	@Override
	public ReqResult getMineShareList(String userId, String userType, String pageSize, String pageIndex) {
		ReqResult reqResult = new ReqResult();
		reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
		reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
		logger.info("*********************getMineShareList* userId=" + userId + ",userType=" + userType );
		if (userId == null || "".equals(userId)) {
			reqResult.setCode(ResultCode.ERRORCODE.USERID_IS_NULL);
			reqResult.setMsgInfo(ResultCode.ERRORINFO.USERID_IS_NULL);
			return reqResult;
		}
		if (userType == null || "".equals(userType)) {
			reqResult.setCode(ResultCode.ERRORCODE.USERTYPE_IS_NULL);
			reqResult.setMsgInfo(ResultCode.ERRORINFO.USERTYPE_IS_NULL);
			return reqResult;
		}
		
		try {
			SharePo sharePo = new SharePo();
			sharePo.setSendUser(Long.valueOf(userId));
			sharePo.setSendType(Integer.valueOf(userType));
			//sharePo.setRecevieState(1);
			ShareQuery query = new ShareQuery();
			query.setSendUser(userId);
			query.setSendType(userType);
			//query.setRecevieState("1");
			query.setPageSize(Integer.valueOf(pageSize));
			query.setPageIndex(Integer.valueOf(pageIndex));
			List<SharePo>  shareList = shareMapper.queryByObjectAnd(sharePo, query);
			logger.info("*************************** shareSize = " + shareList.size());
			if(shareList.size() > 0) {
				List<ShareVo> shareVo = BeanCopy.copyList(shareList, ShareVo.class, BeanCopy.COPYNOTNULL);
				for(int i=0;i<shareVo.size();i++){
					ShareVo sv = shareVo.get(i);
					Long sid =sv.getRecevieUser();
					if(null != sid){
						Student s = studentManager.getStudentInfo(sid);
						sv.setRecevieName(s.getName());
						sv.setRegName(s.getName());
						sv.setRegPic(s.getHeadIcon());
						shareVo.set(i, sv);
					}
				}
				reqResult = ReqResult.getSuccess();
				//可获得总金额
				Integer totalMoney = shareMapper.queryTotalMoney(sharePo);
				reqResult.setData("totalMoney",totalMoney);
				//共邀请多少人
				Integer totalPerson = shareMapper.queryTotalPerson(sharePo);
				reqResult.setData("totalPerson",totalPerson);
				//实际报名多少人
				Integer realRis = shareMapper.queryRealRis(sharePo);
				reqResult.setData("realRis",realRis);
				//实际获得金额
				Integer realMoney = shareMapper.queryRealMoney(sharePo);
				reqResult.setData("realMoney",realMoney);
				reqResult.setData("pageIndex",pageIndex);
				reqResult.setData(shareVo);
				
				return reqResult;
			}
		} 
		catch (NumberFormatException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			reqResult.setCode(ResultCode.ERRORCODE.PARAMERROR);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
            return reqResult;
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			reqResult.setCode(ResultCode.ERRORCODE.PARAMERROR);
            reqResult.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
            return reqResult;
		}

		return reqResult;
	}

}
