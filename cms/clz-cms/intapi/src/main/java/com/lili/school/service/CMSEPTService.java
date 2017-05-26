package com.lili.school.service;

import com.lili.cms.entity.ResponseMessage;
import com.lili.school.dto.EnrollPackageTemplateExample;
import com.lili.school.dto.EnrollPackageTemplateWithBLOBs;
import com.lili.school.model.EptwbBDTO;

public interface CMSEPTService {

	/**
	 * 
	 * @param carId
	 * @return
	 * @throws Exception
	 */
	public  ResponseMessage findOne(Integer ttid) throws Exception;

	/**
	 * 参数
	 * @param params
	 * @return
	 */
	public  ResponseMessage findBatch(EptwbBDTO dto) throws Exception;


	/**
	 * 参数
	 * @param params
	 * @return
	 */
	public  ResponseMessage insertOne(EnrollPackageTemplateWithBLOBs record) throws Exception;

	/**
	 * 参数
	 * @param params
	 * @return
	 */
	public  ResponseMessage updateOne(EnrollPackageTemplateWithBLOBs record,EnrollPackageTemplateExample example) throws Exception;

	/**
	 * 激活报名包
	 * @param params
	 * @return
	 */
	public  ResponseMessage activeOne(Integer ttid) throws Exception;
	

	/**
	 * 停用报名包
	 * @param params
	 * @return
	 */
	public  ResponseMessage cancleOne(Integer ttid) throws Exception;

	/**
	 * 获取七牛图片上传public token
	 * @return
	 * @throws Exception
	 */
	public  ResponseMessage getToken() throws Exception;
}
