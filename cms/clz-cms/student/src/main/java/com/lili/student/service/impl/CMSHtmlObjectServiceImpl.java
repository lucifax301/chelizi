package com.lili.student.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.file.dto.HtmlObject;
import com.lili.file.manager.HtmlObjectManager;
import com.lili.student.manager.CMSHtmlObjectManager;
import com.lili.student.model.HtmlObjectBDTO;
import com.lili.student.service.CMSHtmlObjectService;

public class CMSHtmlObjectServiceImpl implements CMSHtmlObjectService{

	@Autowired
	CMSHtmlObjectManager cmsHtmlObjectManager;
	@Autowired
	HtmlObjectManager htmlObjectManager;

	@Override
	public ResponseMessage findOne(Integer id) throws Exception {
		HtmlObject htmlObject = cmsHtmlObjectManager.findOne(id);
		return new ResponseMessage().addResult("pageData", htmlObject);
	}

	@Override
	public ResponseMessage findBatch(HtmlObjectBDTO dto) throws Exception {
		PagedResult<HtmlObject> batch = cmsHtmlObjectManager.findBatch(dto);
		return new ResponseMessage().addResult("pageData", batch);
	}

	@Override
	public ResponseMessage insertOne(HtmlObject htmlObject) throws Exception {
		int count = htmlObjectManager.addHtmlObject(htmlObject);
		if(count <= 0){
			return new ResponseMessage("插入失败");
		}
		return new ResponseMessage();
	}

	@Override
	public ResponseMessage updateOne(HtmlObject htmlObject) throws Exception {
		int count = htmlObjectManager.updateHtmlObject(htmlObject);
		if(count <= 0){
			return new ResponseMessage("更新失败");
		}
		return new ResponseMessage();
	}

}
