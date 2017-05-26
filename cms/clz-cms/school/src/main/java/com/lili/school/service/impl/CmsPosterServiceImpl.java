package com.lili.school.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.constant.MessageCode;
import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.common.vo.ReqResult;
import com.lili.file.dto.Poster;
import com.lili.file.manager.PosterManager;
import com.lili.file.service.FileService;
import com.lili.school.manager.IPosterManager;
import com.lili.school.model.PosterDTO;
import com.lili.school.service.ICmsPosterService;

public class CmsPosterServiceImpl implements ICmsPosterService {
	
	@Autowired
	PosterManager posterManager;
	
	@Autowired
	IPosterManager cmsPosterManager;
	
	@Autowired
	FileService fileService;

	@Override
	public String query(Integer type, Integer isDel) {
		String resp =null;
		PagedResult<PosterDTO> batch = null;
		try {
			PosterDTO poster = new PosterDTO();
			poster.setType(type);
			poster.setIsdel(isDel);
			batch = cmsPosterManager.queryList(poster );
			resp = new ResponseMessage().addResult("pageData", batch).build();
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		
		return resp;
	}

	@Override
	public ResponseMessage addPoster(Integer type, String imgName, String posterPic) {
		try {
			 Poster pos = new Poster();
			 pos.setType(type);
			 pos.setPostername(imgName);
			 pos.setPosterpic(posterPic);
			 pos.setPosterpic2(posterPic);
			 pos.setPosterpic3(posterPic);
			 posterManager.postPoster(pos);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		 return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	@Override
	public ResponseMessage updatePoster(Integer type, Integer isDel, String imgName, String posterPic) {
		try {
			 Poster pos = new Poster();
			 pos.setType(type);
			 pos.setIsdel(isDel);
			 if(imgName != null && imgName != ""){
				 pos.setPostername(imgName);
			 }
			 if(posterPic != null && posterPic != ""){
				 pos.setPosterpic(posterPic);
				 pos.setPosterpic2(posterPic);
				 pos.setPosterpic3(posterPic);
			 }
			 posterManager.putPoster(pos);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		 return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	@Override
	public String getUptoken() {
		String token =null;
		try {
			ReqResult r = fileService.getUptoken(null, null, null);
			if(r != null){
				token = r.getResult().get("upToken").toString();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}

}
