package com.lili.school.manager;

import com.lili.common.util.Page;
import com.lili.school.dto.EnrollImportRecord;

public interface EnrollSubjectManager {
	/**
	 * 执行数据导入、取消导入
	 * @param schoolId
	 * @param uuid
	 * @param state	1-导入；2-取消导入
	 * @param userName 操作人
	 * @return
	 */
	int changeUpdateState(Integer schoolId, String uuid, Integer state, String userName);

	/**
	 * 获取已导入的数据表
	 * @param schoolId
	 * @param type
	 * @param pageNo
	 * @param pageSize
	 * @param dateBegin
	 * @param dateEnd
	 * @return
	 */
	Page<EnrollImportRecord> getUpdateTables(int schoolId, String type, String pageNo, String pageSize,
			String dateBegin, String dateEnd);

	/**
	 * 获取某个表内容
	 * @param schoolId
	 * @param type
	 * @param pageNo
	 * @param pageSize
	 * @param tableNo
	 * @return
	 */
	Page getUpdateTablesOne(int schoolId, String type, String pageNo, String pageSize, String tableNo);

	/**
	 * 获取某个表简介
	 * @param schoolId
	 * @param tableNo
	 * @return
	 */
	EnrollImportRecord getUpdateTablesOneInfo(int schoolId, String tableNo);
	
	
	
	
	
	
	
}














