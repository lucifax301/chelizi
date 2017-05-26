package com.lili.school.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.constant.Constant;
import com.lili.cms.constant.MessageCode;
import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.coach.dto.Trfield;
import com.lili.coach.manager.TrfieldManager;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.log.model.LogCommon;
import com.lili.school.manager.CMSFieldManager;
import com.lili.school.model.Field;
import com.lili.school.model.FieldBDTO;
import com.lili.school.service.CMSFieldService;

public class CMSFieldServiceImpl implements CMSFieldService{
	Logger logger = Logger.getLogger(CMSFieldServiceImpl.class);

	@Autowired
	RedisUtil redisUtil;
	
	protected final Logger access = Logger.getLogger(this.getClass());

	@Autowired
	CMSFieldManager cmsFieldManager;
	
	@Autowired
	TrfieldManager trfieldManager;
	
	@Override
	public ResponseMessage findBatch(FieldBDTO dto) throws Exception {
		PagedResult<Field> batch = cmsFieldManager.findBatch(dto);
		return new ResponseMessage().addResult("pageData", batch);
	}

	@Override
	public ResponseMessage findOne(long fieldId) throws Exception {
		Field field = cmsFieldManager.findOne(fieldId);
		return new ResponseMessage().addResult("field", field);
	}

	@Override
	public ResponseMessage insertOne(LogCommon logCommon,Field field) throws Exception {
		//校验是否已存在相同场地
		Field isExit = cmsFieldManager.queryFiled(field);
		if(isExit != null ){
			return new ResponseMessage(MessageCode.MSG_IS_EXIT_FIELD);
		}
		
		if(cmsFieldManager.insertSelective(field) <= 0){
			return new ResponseMessage("插入失败");
		}else {
			if(logCommon != null)
				logCommon.setRelateId(String.valueOf(field.getFieldId()));
			return new ResponseMessage();
		}
	}


	@Override
	public ResponseMessage updateOne(LogCommon logCommon,Field field) throws Exception {
		
		if(cmsFieldManager.updateOne(field) <= 0){
			return new ResponseMessage("更新失败");
		}else {
			if(logCommon != null)
				logCommon.setRelateId(String.valueOf(field.getFieldId()));
			return new ResponseMessage();
		}
	}

	@Override
	public List<Field> getExportSource(FieldBDTO dto) throws Exception {
		return cmsFieldManager.findExportBatch(dto);
	}

	@Override
	public Integer queryTotaField(FieldBDTO dto) throws Exception {
		return cmsFieldManager.findTotalField(dto);
	}

	@Override
	public ResponseMessage isUseOrDel(String id, String isDel) {
		String[] idList  = id.split(",");
		//校验状态是否一致
		if("0".equals(isDel)) {
			List<FieldBDTO> fieldList = cmsFieldManager.queryFieldIsUseList(id);
			for (int i =0; i< fieldList.size(); i++) {
				if(fieldList.get(i).getIsdel() == Constant.IS_USE){
					return new ResponseMessage(MessageCode.MSG_HANDE_CF);
				}
			}
		}
		else if("1".equals(isDel)) {
			List<FieldBDTO> fieldList = cmsFieldManager.queryFieldIsDelList(id);
			for (int i =0; i< fieldList.size(); i++) {
				if(fieldList.get(i).getIsdel() == Constant.IS_DEL){
					return new ResponseMessage(MessageCode.MSG_HANDE_CF);
				}
			}
		}
		
		//List<FieldBDTO> udapteField = new ArrayList<FieldBDTO>();
		Trfield trfield;
		Integer idI;
		Integer isDelInt = Integer.valueOf(isDel);
		for (int i =0; i< idList.length; i++) {
			idI = Integer.parseInt(idList[i]);
			trfield = new Trfield();
			trfield.setTrainFieldId(idI);
			trfield.setIsdel(isDelInt);
			try {
				Field field=cmsFieldManager.findOne(idI);
				redisUtil.delete(REDISKEY.WECHAT_TRFIELD_LIST + field.getSchoolId()); //驾校点评训练场从缓存中读取的
			} catch (Exception e) {
				e.printStackTrace();
			}
			//udapteField.add(field);
			//调APP更新启用状态
			trfieldManager.updateTrfield(trfield);
		}
		//bankCardManager.batchUpdateBankCardList(udapteField);
		logger.info("-------------------------------- BoundBankCardController Pass Update Success!");
		return new ResponseMessage(0, MessageCode.MSG_SUCCESS);
	}

	@Override
	public Field findOne(FieldBDTO dto) {
		return cmsFieldManager.findOne(dto);
	}

	
}
