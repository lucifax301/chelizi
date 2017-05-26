/**
 * 
 */
package com.lili.school.mapper.dao;

import java.util.List;

import com.lili.school.model.Field;
import com.lili.school.model.FieldBDTO;

public interface FieldMapper {
    
    public List<Field> findExportBatch(FieldBDTO dto);
	
	public Field findOne(long fieldId);

	public Field findOneByBDTO(FieldBDTO dto);
	
    public List<Field> findBatch(FieldBDTO dto);

	public long insertSelective(Field field);

	public long updateOne(Field field);
	
	public Integer findTotalField(FieldBDTO dto);

	public Field queryFiled(Field field);

	public List<FieldBDTO> queryFieldIsUseList(String id);

	public List<FieldBDTO> queryFieldIsDelList(String id);
	
}
