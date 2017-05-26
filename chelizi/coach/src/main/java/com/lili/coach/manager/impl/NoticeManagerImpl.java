//package com.lili.coach.manager.impl;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.lili.coach.dto.Notice;
//import com.lili.coach.manager.NoticeManager;
//import com.lili.coach.mapper.dao.NoticeMapper;
//import com.lili.coach.mapper.dao.NoticePage;
//
//public class NoticeManagerImpl implements NoticeManager {
//
//	@Autowired
//	private NoticeMapper noticeMapper;
//	
//	@Override
//	public List<Notice> getNotice(Notice notice) {
//		return noticeMapper.getAll(notice);
//	}
//
//	@Override
//	public Notice getNoticeInfo(Integer id) {
//		return noticeMapper.selectByPrimaryKey(id);
//	}
//
//	@Override
//	public int getCount() {
//		return noticeMapper.countAll();
//	}
//
//	@Override
//	public int addNotice(Notice notice) {
//		return noticeMapper.insertSelective(notice);
//	}
//
//	@Override
//	public int updateNotice(Notice notice) {
//		return noticeMapper.updateByPrimaryKeySelective(notice);
//	}
//
//	@Override
//	public int deleteNotice(Integer id) {
//		return noticeMapper.deleteByPrimaryKey(id);
//	}
//
//	@Override
//	public List<Notice> getNoticByUserId(Long userId, int userType,
//			int pageNum, int pageSize,String time) {
//		NoticePage page = new NoticePage(pageNum, pageSize, userId, userType,time);
//		return noticeMapper.getNoticByUserId(page);
//	}
//
//}
