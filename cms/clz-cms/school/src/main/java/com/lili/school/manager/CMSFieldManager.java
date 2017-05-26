package com.lili.school.manager;

import java.util.List;

import com.lili.cms.entity.PagedResult;
import com.lili.school.model.Field;
import com.lili.school.model.FieldBDTO;

public interface CMSFieldManager {

    public List<Field> findExportBatch(FieldBDTO dto) throws Exception;
	
	public Field findOne(long fieldId) throws Exception;
	
    public PagedResult<Field> findBatch(FieldBDTO dto) throws Exception;

	public long insertSelective(Field field) throws Exception;

	public long updateOne(Field field) throws Exception;
	
	public Integer findTotalField(FieldBDTO dto) throws Exception;

	public Field queryFiled(Field field);

	public List<FieldBDTO> queryFieldIsUseList(String id);

	public List<FieldBDTO> queryFieldIsDelList(String id);

	public Long insertFieldInfo(Field field);


	public Field findOne(FieldBDTO dto);
}
