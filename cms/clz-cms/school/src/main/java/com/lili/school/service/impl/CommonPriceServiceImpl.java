package com.lili.school.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.constant.Constant;
import com.lili.cms.constant.MessageCode;
import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.order.service.CommonPriceService;
import com.lili.order.vo.CommonPriceVo;
import com.lili.school.manager.ICommonPriceManager;
import com.lili.school.model.CommonPrice;
import com.lili.school.service.ICmsCommonPriceService;

public class CommonPriceServiceImpl implements ICmsCommonPriceService {
	Logger logger = Logger.getLogger(CommonPriceServiceImpl.class);
	
	@Autowired
	ICommonPriceManager commonPriceManager;
	
	@Autowired
	CommonPriceService commonPriceService;

	@Override
	public String query(CommonPrice commonPrice) {
		String resp =null;
		PagedResult<CommonPrice> batch = null;
		try {
			batch = commonPriceManager.queryCommonPriceList(commonPrice);
			resp = new ResponseMessage().addResult("pageData", batch).build();
		} 
		catch (Exception e) {
			logger.error("************************************ error: " + e.getMessage());
		}
		return resp;
	}

	@Override
	public ResponseMessage isUseOrDel(String checker,  String id, Integer isdel) {
		try {
			String[] idList  = id.split(",");
			if (isdel == 0) {
				List<CommonPrice> comPriceList = commonPriceManager.queryComPriceList(id);
				for (int i =0; i< comPriceList.size(); i++) {
					if(comPriceList.get(i).getIsdel() == Constant.IS_USE){
						return new ResponseMessage(MessageCode.MSG_HANDE_CF);
					}
				}
			}
			else if (isdel ==1 ) {
				List<CommonPrice> comPriceList = commonPriceManager.queryComPriceList(id);
				for (int i =0; i< comPriceList.size(); i++) {
					if(comPriceList.get(i).getIsdel() == Constant.IS_DEL){
						return new ResponseMessage(MessageCode.MSG_HANDE_CF);
					}
				}
			}
			else {
				return new ResponseMessage(MessageCode.MSG_REQUEST);
			}
			
			CommonPriceVo commonPriceVo = null;
			for (int i =0; i< idList.length; i++) {
				commonPriceVo = new CommonPriceVo();
				commonPriceVo.setUpid(Integer.valueOf(idList[i]));
				commonPriceVo.setMuid(Integer.valueOf(checker));
				commonPriceService.updateByIsdel(commonPriceVo, isdel);//调APP接口批量更新
			}
			
		} 
		catch (NumberFormatException e) {
			logger.error("*********************************NumberFormatException error："+e.getMessage());
			return new ResponseMessage(MessageCode.MSG_FAIL);
		} 
		catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	@Override
	public ResponseMessage update(CommonPriceVo commonPriceVo) {
		//调APP接口
		try {
			commonPriceService.updateByUpid(commonPriceVo, commonPriceVo.getUpid());
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("********************************* error："+e.getMessage());
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
		
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	@Override
	public ResponseMessage check(CommonPriceVo commonPriceVo) {
		//调APP接口
		try {
			commonPriceService.updateByUpid(commonPriceVo, commonPriceVo.getUpid());
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("********************************* error："+e.getMessage());
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
		
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

	@Override
	public ResponseMessage addCourse(CommonPriceVo commonPriceVo) {
		//调APP接口
		try {
			commonPriceService.addCommonPrice(commonPriceVo);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("********************************* error："+e.getMessage());
			return new ResponseMessage(MessageCode.MSG_FAIL);
		}
		
		return new ResponseMessage(0,MessageCode.MSG_SUCCESS);
	}

}
