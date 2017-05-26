package com.lili.school.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.constant.Constant;
import com.lili.cms.constant.MessageCode;
import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.order.service.CommentTagService;
import com.lili.order.vo.CommentTagVo;
import com.lili.school.manager.ICommentTagManager;
import com.lili.school.model.CommentTag;
import com.lili.school.service.ICmsCommentTagService;

public class CommentTagServiceImpl implements ICmsCommentTagService {
	Logger logger = Logger.getLogger(CommentTagServiceImpl.class);
	
	@Autowired
	ICommentTagManager commentTagManager;
	
	@Autowired
	CommentTagService commentTagService;

	@Override
	public String query(CommentTag commentTag) {
		String resp =null;
		PagedResult<CommentTag> batch = null;
		try {
			batch = commentTagManager.queryCommentTagList(commentTag);
			resp = new ResponseMessage().addResult("pageData", batch).build();
		} 
		catch (Exception e) {
			logger.error("************************************ error: " + e.getMessage());
		}
		return resp;
	}

	@Override
	public ResponseMessage isUseOrDel(String checker, String id, Integer isdel) {
		try {
			String[] idList  = id.split(",");
			if (isdel == 0) {
				List<CommentTag> comTagList = commentTagManager.queryComTagList(id);
				for (int i =0; i< comTagList.size(); i++) {
					if(comTagList.get(i).getIsdel() == Constant.IS_USE){
						return new ResponseMessage(MessageCode.MSG_HANDE_CF);
					}
				}
			}
			else if (isdel ==1 ) {
				List<CommentTag> comTagList = commentTagManager.queryComTagList(id);
				for (int i =0; i< comTagList.size(); i++) {
					if(comTagList.get(i).getIsdel() == Constant.IS_DEL){
						return new ResponseMessage(MessageCode.MSG_HANDE_CF);
					}
				}
			}
			else {
				return new ResponseMessage(MessageCode.MSG_REQUEST);
			}
			
			CommentTagVo commentTagVo = null;
			Integer ctid;
			for (int i =0; i< idList.length; i++) {
				ctid = Integer.valueOf(idList[i]);
				commentTagVo = new CommentTagVo();
				commentTagVo.setIsdel(isdel);
				commentTagVo.setMuid(Integer.valueOf(checker));
				commentTagService.updateByCtid(commentTagVo, ctid);//调APP接口批量更新
			}
			
		} 
		catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	@Override
	public ResponseMessage update(CommentTagVo commentTagVo) {
		//调APP接口
		try {
			commentTagService.updateByCtid(commentTagVo, commentTagVo.getCtid());
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("********************************* error："+e.getMessage());
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
		
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	@Override
	public ResponseMessage addCommentTag(CommentTagVo commentTagVo) {
		//调APP接口
		try {
			commentTagService.addCommentTag(commentTagVo);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("********************************* error："+e.getMessage());
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
		
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

}
