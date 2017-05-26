package com.lili.school.service;

import java.util.List;

import com.lili.cms.entity.ResponseMessage;
import com.lili.log.model.LogCommon;
import com.lili.school.model.Field;
import com.lili.school.model.FieldBDTO;

public interface CMSFieldService {

	public ResponseMessage findBatch(FieldBDTO dto) throws Exception;

	public ResponseMessage findOne(long fieldId) throws Exception;

	public ResponseMessage insertOne(LogCommon logCommon, Field field) throws Exception;

	public ResponseMessage updateOne(LogCommon logCommon, Field field) throws Exception;

	public List<Field> getExportSource(FieldBDTO dto) throws Exception;

	public Integer queryTotaField(FieldBDTO dto) throws Exception;

	public ResponseMessage isUseOrDel(String id, String isDel);
	


	public Field findOne(FieldBDTO dto);

}
