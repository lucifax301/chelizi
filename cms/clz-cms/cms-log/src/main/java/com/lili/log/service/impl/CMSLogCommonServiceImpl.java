package com.lili.log.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.log.manager.CMSLogManager;
import com.lili.log.model.LogCommon;
import com.lili.log.model.LogCommonBDTO;
import com.lili.log.service.CMSLogCommonService;

public class CMSLogCommonServiceImpl implements CMSLogCommonService{

	@Autowired
	CMSLogManager cmsLogManager;
	
	@Override
	public ResponseMessage findLogList(LogCommonBDTO dto) {
		PagedResult<LogCommon> batch = cmsLogManager.findLogList(dto);
		return new ResponseMessage().addPagedResult(batch);
	}

	@Override
	public ResponseMessage insertLogList(List<LogCommon> logCommonList) {
		 cmsLogManager.inertLogList(logCommonList);
		 return new ResponseMessage();
	}

	@Override
	public ResponseMessage insertLogInfo(LogCommon logCommon) {
		cmsLogManager.inertLogInfo(logCommon);
		 return new ResponseMessage();
	}

	@Override
	public ResponseMessage insertOne(LogCommon logCommon) {
		Long count = cmsLogManager.addOne(logCommon);
		if(count != null && count > 0 ){
			 return new ResponseMessage();
		}else {
			 return new ResponseMessage("插入失败");
		}
	}

}
