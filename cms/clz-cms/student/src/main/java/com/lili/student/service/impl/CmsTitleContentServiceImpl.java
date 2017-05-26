package com.lili.student.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.constant.MessageCode;
import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.student.manager.CmsTitleContentManager;
import com.lili.student.model.TitleTypeVo;
import com.lili.student.model.TypeContentVo;
import com.lili.student.service.CmsTitleContentService;

public class CmsTitleContentServiceImpl implements CmsTitleContentService {
	
	@Autowired
	CmsTitleContentManager cmsTitleContentManager;

	@Override
	public ResponseMessage queryTitleList(TitleTypeVo tt) throws Exception {
		PagedResult<TitleTypeVo> batch = cmsTitleContentManager.queryTitleList(tt);
		return new ResponseMessage().addResult("pageData", batch);
	}

	@Override
	public ResponseMessage addTitle(TitleTypeVo tt) throws Exception {
		cmsTitleContentManager.addTitle(tt);
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	@Override
	public ResponseMessage deleteTitle(TitleTypeVo tt) throws Exception {
		cmsTitleContentManager.deleteTitle(tt);
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	@Override
	public ResponseMessage updateTitle(TitleTypeVo tt) throws Exception {
		cmsTitleContentManager.updateTitle(tt);
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	@Override
	public ResponseMessage queryContent(TypeContentVo tc) throws Exception {
		TypeContentVo tcVo = cmsTitleContentManager.queryContent(tc);
		return new ResponseMessage().addResult("pageData", tcVo);
	}

	@Override
	public ResponseMessage queryContentList(TypeContentVo tc) throws Exception {
		PagedResult<TypeContentVo> batch = cmsTitleContentManager.queryContentList(tc);
		return new ResponseMessage().addResult("pageData", batch);
	}

	@Override
	public ResponseMessage addContent(TypeContentVo tc) throws Exception {
		cmsTitleContentManager.addContent(tc);
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	@Override
	public ResponseMessage updateContent(TypeContentVo tc) throws Exception {
		cmsTitleContentManager.updateContent(tc);
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	@Override
	public ResponseMessage releaseContent(TypeContentVo tc) throws Exception {
		cmsTitleContentManager.releaseContent(tc);
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

}
