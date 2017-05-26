package com.lili.coach.service;


import com.lili.authcode.dto.Notice;
import com.lili.cms.entity.ResponseMessage;


public interface ICmsNoticeService {
	
	 /**
     * 获取公告列表
     * @return
     */
    public ResponseMessage getNotice(Notice notice,int pageNo,int pageSize);  
    /**
     * 新增公告信息
     * @param notice
     * @return
     */
    public ResponseMessage addNotice(Notice notice);  
    /**
     * 更新公告信息，如果需要撤销某个消息，将isdel字段设置为1即可
     * @param notice
     * @return
     */
    public ResponseMessage updateNotice(Notice notice);
    
    public ResponseMessage findBatch(Notice dto) throws Exception;
    
    public ResponseMessage getCount() throws Exception;
    
    public ResponseMessage getNoticeById(String noticeId);  
    
    public ResponseMessage updateState(String noticeId,String isdel,String type);  

    
}
