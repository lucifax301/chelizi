package com.lili.student.service;

import com.lili.cms.entity.ResponseMessage;
import com.lili.student.model.TitleTypeVo;
import com.lili.student.model.TypeContentVo;

/**
 * 帮助中心接口
 * @author lzb
 *
 */
public interface CmsTitleContentService {


	public  ResponseMessage queryTitleList(TitleTypeVo tt) throws Exception;

	public  ResponseMessage addTitle(TitleTypeVo tt) throws Exception;

	public  ResponseMessage deleteTitle(TitleTypeVo tt) throws Exception;

	public  ResponseMessage updateTitle(TitleTypeVo tt) throws Exception;

	public ResponseMessage queryContent(TypeContentVo tc) throws Exception;

	public ResponseMessage queryContentList(TypeContentVo tc) throws Exception;

	public ResponseMessage addContent(TypeContentVo tc) throws Exception;

	public ResponseMessage updateContent(TypeContentVo tc) throws Exception;

	public ResponseMessage releaseContent(TypeContentVo tc) throws Exception;
	
}
