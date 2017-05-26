package com.lili.school.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.cms.util.PicUtil;
import com.lili.school.dto.EnrollPackageTemplateExample;
import com.lili.school.dto.EnrollPackageTemplateWithBLOBs;
import com.lili.school.manager.CMSEPTManager;
import com.lili.school.manager.EnrollPackageTemplateManager;
import com.lili.school.model.EnrollPackageTemplateVo;
import com.lili.school.model.EptwbBDTO;
import com.lili.school.service.CMSEPTService;

public class CMSEPTServiceImpl implements CMSEPTService{

	@Autowired
	CMSEPTManager cmsEPTManager;
	@Autowired
	EnrollPackageTemplateManager enrollPackageTemplateManager;
	
	@Override
	public ResponseMessage findOne(Integer ttid) throws Exception {
		EnrollPackageTemplateVo ept = cmsEPTManager.findOne(ttid);
		if(ept != null){
			ept.setProcPic(PicUtil.getPicFromQN(ept.getProcPic()));
			ept.setExtra(PicUtil.getPicFromQN(ept.getExtra()));
			ept.setIcon(PicUtil.getPicFromQN(ept.getIcon()));
		}
		return new ResponseMessage().addResult("pageData", ept);
	}

	@Override
	public ResponseMessage findBatch(EptwbBDTO dto) throws Exception {
		PagedResult<EnrollPackageTemplateWithBLOBs> batch = cmsEPTManager.findBatch(dto);
		return new ResponseMessage().addResult("pageData", batch);
	}

	@Override
	public ResponseMessage insertOne(EnrollPackageTemplateWithBLOBs record)
			throws Exception {
		int id = enrollPackageTemplateManager.addEnrollPackageTemplate(record);
		if(id <= 0){
			return new ResponseMessage("插入失败");
		}
		return new ResponseMessage();
	}

	@Override
	public ResponseMessage updateOne(EnrollPackageTemplateWithBLOBs record,
			EnrollPackageTemplateExample example) throws Exception {
		int id = enrollPackageTemplateManager.updateEnrollPackageTemplate(record, example);
		if(id <= 0){
			return new ResponseMessage("更新失败");
		}
		return new ResponseMessage();
	}

	@Override
	public ResponseMessage activeOne(Integer ttid) throws Exception {
		List<Integer> idList = new ArrayList<Integer>();
		idList.add(ttid);
		int count = enrollPackageTemplateManager.updateEnrollPackageTemplate(idList, (byte)0);
		if(count <= 0){
			return new ResponseMessage("激活失败");
		}
		return new ResponseMessage();
	}

	@Override
	public ResponseMessage cancleOne(Integer ttid) throws Exception {
		List<Integer> idList = new ArrayList<Integer>();
		idList.add(ttid);
		int count = enrollPackageTemplateManager.updateEnrollPackageTemplate(idList, (byte)1);
		if(count <= 0){
			return new ResponseMessage("停用失败");
		}
		return new ResponseMessage();
	}

	@Override
	public ResponseMessage getToken() throws Exception {
		 return new ResponseMessage().addResult("pageData", PicUtil.getPublicToken());
	}



}
