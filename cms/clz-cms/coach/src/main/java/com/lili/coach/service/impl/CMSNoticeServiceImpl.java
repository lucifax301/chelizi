package com.lili.coach.service.impl;


import org.springframework.beans.factory.annotation.Autowired;

import com.lili.authcode.dto.Notice;
import com.lili.authcode.manager.NoticeManager;
import com.lili.cms.entity.ResponseMessage;
import com.lili.coach.service.ICmsNoticeService;
import com.lili.common.util.Page;

public class CMSNoticeServiceImpl implements ICmsNoticeService{
	
	@Autowired
	NoticeManager noticeManager;

	@Override
	public ResponseMessage getNotice(Notice notice,int pageNo,int pageSize) {
		
		Page<Notice> page=noticeManager.getNotice(notice, pageNo, pageSize);
		return new ResponseMessage().addResult("pageData", page);
	}

	@Override
	public ResponseMessage addNotice(Notice notice) {
		int num=noticeManager.addCmsNotice(notice);
		return new ResponseMessage().addResult("pageData", num);
	}

	@Override
	public ResponseMessage updateNotice(Notice notice) {
		int num=noticeManager.updateNotice(notice);
		return new ResponseMessage().addResult("pageData", num);
	}

	@Override
	public ResponseMessage findBatch(Notice dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseMessage getCount() throws Exception {
		int count = noticeManager.getTotalCount();
		return new ResponseMessage().addResult("pageData", count);
	}
	
	@Override
	public ResponseMessage getNoticeById(String noticeId) {
		
		Notice notice=noticeManager.getNoticeById(noticeId);
		return new ResponseMessage().addResult("pageData", notice);
	}
	
	@Override
	public ResponseMessage updateState(String noticeId,String isdel,String type) {
		int num=noticeManager.updateState(noticeId,isdel,type);
		return new ResponseMessage().addResult("pageData", num);
	}

/*	@Autowired
	NoticeManager noticeManager;
	

	@Override
	public List<Notice> getNotice(Notice notice) {
		
		List<Notice> notices = noticeManager.getNotice(notice);
		return notices;
	}

	@Override
	public ResponseMessage addNotice(Notice notice) {
		int num= noticeManager.addNotice(notice);
		return new ResponseMessage().addResult("pageData", num);
	}

	@Override
	public ResponseMessage updateNotice(Notice notice) {
		int num = noticeManager.updateNotice(notice);
		return new ResponseMessage().addResult("pageData", num);
	}

	@Override
	public ResponseMessage findBatch(Notice dto) throws Exception {
		return null;
	}

	@Override
	public ResponseMessage getCount() throws Exception {
		int count = noticeManager.getCount();
		return new ResponseMessage().addResult("pageData", count);
	}*/
	
	

}
