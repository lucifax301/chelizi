package com.lili.student.service;

import com.lili.cms.entity.ResponseMessage;
import com.lili.file.dto.HtmlObject;
import com.lili.student.model.HtmlObjectBDTO;

/**
 * 协议条款接口
 * @author hughes
 *
 */
public interface CMSHtmlObjectService {


	public  ResponseMessage findOne(Integer id) throws Exception;

	public  ResponseMessage findBatch(HtmlObjectBDTO dto) throws Exception;

	public  ResponseMessage insertOne(HtmlObject htmlObject) throws Exception;

	public  ResponseMessage updateOne(HtmlObject htmlObject) throws Exception;
	
}
