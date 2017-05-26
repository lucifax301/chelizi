package com.lili.authcode.manager.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.lili.authcode.dto.Notice;
import com.lili.authcode.dto.NoticeExample;
import com.lili.authcode.manager.NoticeManager;
import com.lili.authcode.mapper.NoticeMapper;
import com.lili.authcode.mapper.NoticeVoMapper;
import com.lili.coach.dto.Coach;
import com.lili.common.constant.JpushConstant;
import com.lili.common.constant.NoticeType;
import com.lili.common.util.Page;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.student.dto.Student;

public class NoticeManagerImpl implements NoticeManager {
	private static Logger logger = LoggerFactory.getLogger(NoticeManagerImpl.class);

	@Autowired
	NoticeMapper noticeMapper;
	@Autowired
	NoticeVoMapper noticeVoMapper;
	@Autowired
	RedisUtil redisUtil;

	@Resource(name = "jpushProducer")
	DefaultMQProducer jpushProducer;
	
	@Override
	public void addNotice(Notice notice) {
		noticeMapper.insertSelective(notice);
	}

	/**
	 * 2.1之前版本使用
	 */
	@Override
	public Page<Notice> getNoticesByStudentId(Long studentId, Integer schoolId, Integer cityId, byte type, Date etime,
			int pageNo, int pageSize) {
		try {
			if (null == etime) {
				etime = new SimpleDateFormat("yyyy-mm-dd").parse("2010-01-01");
			}
			NoticeExample example = new NoticeExample();
			example.createCriteria().andUserTypeEqualTo(NoticeType.ISDEL_0) // 全体用户
					.andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0).andEtimeGreaterThanOrEqualTo(etime);
			example.or(example.createCriteria().andUserTypeEqualTo((byte) 2) // 全体学员
					.andUserIdIsNull().andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0)
					.andEtimeGreaterThanOrEqualTo(etime));
			if (null != studentId) {
				example.or(example.createCriteria().andUserTypeEqualTo((byte) 2) // 指定学员
						.andUserIdEqualTo(studentId).andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0)
						.andEtimeGreaterThanOrEqualTo(etime));
			}
			if (null != schoolId) {
				example.or(example.createCriteria().andUserTypeEqualTo((byte) 4) // 驾校学员
						.andUserIdEqualTo(schoolId.longValue()).andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0)
						.andEtimeGreaterThanOrEqualTo(etime));
			}
			if (null != cityId) {
				example.or(example.createCriteria().andUserTypeEqualTo((byte) 6) // 城市学员
						.andUserIdEqualTo(cityId.longValue()).andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0)
						.andEtimeGreaterThanOrEqualTo(etime));
			}

			int total = noticeMapper.countByExample(example);
			if (pageNo < 0) {
				pageNo = 1;
			}
			if (pageSize < 0) {
				pageSize = 100;
			}
			example.setOrderByClause("noticeId desc");
			RowBounds rowBounds = new RowBounds((pageNo - 1) * pageSize, pageSize);// (offset,limit)
			List<Notice> dataList = noticeMapper.selectByExampleWithBLOBsWithRowbounds(example, rowBounds);
			return new Page<Notice>(dataList, pageNo, pageSize, total);
		} catch (Exception e) {
			logger.error("NoticeManagerImpl-->" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 2.1版本 type ：0-今日重点播报、1-优惠活动、2-订单消息 3-喱喱头条、4-我的消息 5-系统消息 utype:1-驾校学员
	 * 2-喱喱学员 isdel 0-已发布 usertype 10-cms配置学员 9-cms配置教练
	 * cityId、schoolId、utype、applyexam四个参数所有组合情况过滤 userIdstrs单独过滤 不交叉
	 * schoolId、userIdstrs用like '%,id,%'匹配 applyexam用 like
	 * '%|appelyexam,applystate|%'匹配
	 */
	@Override
	public Page<Notice> getNotices(Student student, byte type, int pageNo, int pageSize) {
		Long studentId = null;
		String utype = "";
		String applyexam = null;
		if (student != null) {
			studentId = student.getStudentId();
			applyexam = "|" + student.getApplyexam() + "," + student.getApplystate() + "|";
			if (student.getIsImport() == 1) {
				utype = String.valueOf(NoticeType.UTYPE_1);
			} else {
				utype =String.valueOf(NoticeType.UTYPE_2);
			}
		}
		try {
			NoticeExample example = new NoticeExample();
			if (type == NoticeType.TYPE_5) {
				example.createCriteria().andTypeEqualTo(NoticeType.TYPE_5).andUserTypeEqualTo(NoticeType.USERTYPE_10).andIsdelEqualTo(NoticeType.ISDEL_0)
						.andUserIdIsNull();
				example.or(example.createCriteria().andTypeEqualTo(NoticeType.TYPE_5).andUserTypeEqualTo(NoticeType.USERTYPE_2)
						.andIsdelEqualTo(NoticeType.ISDEL_0).andUserIdEqualTo(studentId));
			} else if (type == NoticeType.TYPE_2) {
				example.createCriteria().andTypeEqualTo((byte) 2).andIsdelEqualTo(NoticeType.ISDEL_0).andUserTypeEqualTo(NoticeType.USERTYPE_2)
						.andUserIdEqualTo(studentId);
			} else if (type == NoticeType.TYPE_3) {
				List<Notice> list=redisUtil.get(REDISKEY.NOTICE_TOP);
				if(list==null){
					example.createCriteria().andTypeEqualTo((byte) 3).andIsdelEqualTo(NoticeType.ISDEL_0);
					example.setOrderByClause("noticeId desc");
					 list = noticeMapper.selectByExample(example);
					if(list.size()>0){
						redisUtil.set(REDISKEY.NOTICE_TOP, list);
					}
				}
				
				int start=(pageNo-1)*pageSize;
				int end=start+pageSize;
				List<Notice> resultList=new ArrayList<>();
				for(int i=start;i<list.size() && i<end;i++){
					resultList.add(list.get(i));
				}
				return new Page<Notice>(resultList, pageNo, pageSize, list.size());
			//	example.createCriteria().andTypeEqualTo((byte) 3).andIsdelEqualTo(NoticeType.ISDEL_0);
			} else if (type == NoticeType.TYPE_1 || type == NoticeType.TYPE_4) {
				// 无条件限制所有学员 16.cityId=null schoolId=null applyexam=null
				// utype==null
				example.createCriteria().andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0).andUserTypeEqualTo(NoticeType.USERTYPE_10)
						.andCityIdIsNull().andUtypeIsNull().andApplyexamIsNull().andSchoolIdIsNull()
						.andUserIdStrsIsNull();
				// 有id限制用户
				example.or(example.createCriteria().andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0)
						.andUserTypeEqualTo(NoticeType.USERTYPE_10).andUserIdStrsLike("%," + studentId + ",%"));
				// cityId==null schoolId==null
				// 1.cityId=null schoolId=null applyexam=null utype!=null
				// //2.cityId=null schoolId=null applyexam!=null utype!=null
				// //3.cityId=null schoolId=null applyexam!=null utype==null
				example.or(example.createCriteria().andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0).andUserIdStrsIsNull()
						.andUserTypeEqualTo(NoticeType.USERTYPE_10).andCityIdIsNull().andSchoolIdIsNull()
						.andUtypeLike("%" + utype + "%").andApplyexamIsNull());
				example.or(example.createCriteria().andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0).andUserIdStrsIsNull()
						.andUserTypeEqualTo(NoticeType.USERTYPE_10).andCityIdIsNull().andSchoolIdIsNull()
						.andUtypeLike("%" + utype + "%").andApplyexamLike("%" + applyexam + "%"));
				example.or(example.createCriteria().andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0).andUserIdStrsIsNull()
						.andUserTypeEqualTo(NoticeType.USERTYPE_10).andCityIdIsNull().andSchoolIdIsNull().andUtypeIsNull()
						.andApplyexamLike("%" + applyexam + "%"));

				// studentId!=null
				// 4.cityId=null schoolId!=null applyexam=null utype!=null
				// //5.cityId=null schoolId!=null applyexam!=null utype!=null
				// //6.cityId=null schoolId!=null applyexam!=null utype==null
				// //7.cityId=null schoolId!=null applyexam==null utype==null
				example.or(example.createCriteria().andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0).andUserIdStrsIsNull()
						.andUserTypeEqualTo(NoticeType.USERTYPE_10).andCityIdIsNull()
						.andSchoolIdLike("%," + student.getSchoolId() + ",%").andUtypeLike("%" + utype + "%")
						.andApplyexamIsNull());
				example.or(example.createCriteria().andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0).andUserIdStrsIsNull()
						.andUserTypeEqualTo(NoticeType.USERTYPE_10).andCityIdIsNull()
						.andSchoolIdLike("%," + student.getSchoolId() + ",%").andUtypeLike("%" + utype + "%")
						.andApplyexamLike("%" + applyexam + "%"));
				example.or(example.createCriteria().andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0).andUserIdStrsIsNull()
						.andUserTypeEqualTo(NoticeType.USERTYPE_10).andCityIdIsNull()
						.andSchoolIdLike("%," + student.getSchoolId() + ",%").andUtypeIsNull()
						.andApplyexamLike("%" + applyexam + "%"));
				example.or(example.createCriteria().andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0).andUserIdStrsIsNull()
						.andUserTypeEqualTo(NoticeType.USERTYPE_10).andCityIdIsNull()
						.andSchoolIdLike("%," + student.getSchoolId() + ",%").andUtypeIsNull().andApplyexamIsNull());

				// cityId!=null
				// 8.cityId!=null schoolId=null applyexam=null utype!=null
				// //9.cityId!=null schoolId=null applyexam!=null utype!=null
				// //10.cityId!=null schoolId=null applyexam!=null utype==null
				// //11.cityId!=null schoolId=null applyexam==null utype==null
				example.or(example.createCriteria().andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0).andUserIdStrsIsNull()
						.andUserTypeEqualTo(NoticeType.USERTYPE_10).andCityIdLike("%" + student.getCityId() + "%")
						.andSchoolIdIsNull().andUtypeLike("%" + utype + "%").andApplyexamIsNull());
				example.or(example.createCriteria().andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0).andUserIdStrsIsNull()
						.andUserTypeEqualTo(NoticeType.USERTYPE_10).andCityIdLike("%" + student.getCityId() + "%")
						.andSchoolIdIsNull().andUtypeLike("%" + utype + "%").andApplyexamLike("%" + applyexam + "%"));
				example.or(example.createCriteria().andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0).andUserIdStrsIsNull()
						.andUserTypeEqualTo(NoticeType.USERTYPE_10).andCityIdLike("%" + student.getCityId() + "%")
						.andSchoolIdIsNull().andUtypeIsNull().andApplyexamLike("%" + applyexam + "%"));
				example.or(example.createCriteria().andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0).andUserIdStrsIsNull()
						.andUserTypeEqualTo(NoticeType.USERTYPE_10).andCityIdLike("%" + student.getCityId() + "%")
						.andSchoolIdIsNull().andUtypeIsNull().andApplyexamIsNull());

				// cityId!=null schoolId!=null
				// 12.cityId!=null schoolId!=null applyexam=null utype!=null
				// //13.cityId!=null schoolId!=null applyexam!=null utype!=null
				// //14.cityId!=null schoolId!=null applyexam!=null utype==null
				// //15.cityId!=null schoolId!=null applyexam==null utype==null
				example.or(example.createCriteria().andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0).andUserIdStrsIsNull()
						.andUserTypeEqualTo(NoticeType.USERTYPE_10).andCityIdLike("%" + student.getCityId() + "%")
						.andSchoolIdLike("%," + student.getSchoolId() + ",%").andUtypeLike("%" + utype + "%")
						.andApplyexamIsNull());
				example.or(example.createCriteria().andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0).andUserIdStrsIsNull()
						.andUserTypeEqualTo(NoticeType.USERTYPE_10).andCityIdLike("%" + student.getCityId() + "%")
						.andSchoolIdLike("%," + student.getSchoolId() + ",%").andUtypeLike("%" + utype + "%")
						.andApplyexamLike("%" + applyexam + "%"));
				example.or(example.createCriteria().andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0).andUserIdStrsIsNull()
						.andUserTypeEqualTo(NoticeType.USERTYPE_10).andCityIdLike("%" + student.getCityId() + "%")
						.andSchoolIdLike("%," + student.getSchoolId() + ",%").andUtypeIsNull()
						.andApplyexamLike("%" + applyexam + "%"));
				example.or(example.createCriteria().andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0).andUserIdStrsIsNull()
						.andUserTypeEqualTo(NoticeType.USERTYPE_10).andCityIdLike("%" + student.getCityId() + "%")
						.andSchoolIdLike("%," + student.getSchoolId() + ",%").andUtypeIsNull().andApplyexamIsNull());
			}

			int total = noticeMapper.countByExample(example);
			example.setOrderByClause("noticeId desc");
			RowBounds rowBounds = new RowBounds((pageNo - 1) * pageSize, pageSize);// (offset,limit)
			List<Notice> dataList = noticeMapper.selectByExampleWithRowbounds(example, rowBounds);
			return new Page<Notice>(dataList, pageNo, pageSize, total);
		} catch (Exception e) {
			logger.error("NoticeManagerImpl-->" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Notice> getNoticeIndex(Student student) {
		List<Notice> list = new ArrayList<>();
		long studentId=student.getStudentId();
		list=noticeVoMapper.getNoticeStudentIndex(studentId);
		// 系统消息、订单消息、活动消息、我的消息各取第一条数据给首页
		Page<Notice> pageList1 = getNotices(student, NoticeType.TYPE_1, 1, 1);
		Page<Notice> pageList4 = getNotices(student, NoticeType.TYPE_4, 1, 1);
		
		/**
		 * 获取最新的点赞或回复记录
		 */
		Notice notice = new Notice();
		notice.setType((byte)6);
		notice.setUserId(student.getStudentId());
		notice.setUserType((byte)2);
		notice.setIsdel((byte)0);
		Notice noticeInfo = noticeVoMapper.queryNoticeInfo(notice);
		if (noticeInfo != null) {
			list.add(noticeInfo);
		}
		
		if (pageList1.getDataList().size() > 0) {
			list.add(pageList1.getDataList().get(0));
		}
		if (pageList4.getDataList().size() > 0) {
			list.add(pageList4.getDataList().get(0));
		}
		return list;
	}

	@Override
	public Page<Notice> getNoticesByCoachId(Long coachId, Integer schoolId, Integer cityId, byte type, Boolean isVip,
			Date etime, int pageNo, int pageSize) {
		try {
			if (null == etime) {
				etime = new SimpleDateFormat("yyyy-mm-dd").parse("2010-01-01");
			}
			NoticeExample example = new NoticeExample();
			example.createCriteria().andUserTypeEqualTo(NoticeType.ISDEL_0) // 全体用户
					.andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0).andEtimeGreaterThanOrEqualTo(etime);
			example.or(example.createCriteria().andUserTypeEqualTo((byte) 1) // 全体教练
					.andUserIdIsNull().andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0)
					.andEtimeGreaterThanOrEqualTo(etime));
			if (null != coachId) {
				example.or(example.createCriteria().andUserTypeEqualTo((byte) 1) // 指定教练
						.andUserIdEqualTo(coachId).andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0)
						.andEtimeGreaterThanOrEqualTo(etime));
			}
			if (null != schoolId) {
				example.or(example.createCriteria().andUserTypeEqualTo((byte) 3) // 驾校教练
						.andUserIdEqualTo(schoolId.longValue()).andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0)
						.andEtimeGreaterThanOrEqualTo(etime));
			}
			if (null != cityId) {
				example.or(example.createCriteria().andUserTypeEqualTo((byte) 5) // 城市教练
						.andUserIdEqualTo(cityId.longValue()).andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0)
						.andEtimeGreaterThanOrEqualTo(etime));
			}
			if (null != isVip && isVip == true) {
				example.or(example.createCriteria().andUserTypeEqualTo((byte) 7) // 特约教练
						.andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0).andEtimeGreaterThanOrEqualTo(etime));
			}
			if (isVip != null && isVip == false) {
				example.or(example.createCriteria().andUserTypeEqualTo((byte) 8) // 普通教练
						.andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0).andEtimeGreaterThanOrEqualTo(etime));
			}

			int total = noticeMapper.countByExample(example);
			if (pageNo < 0) {
				pageNo = 1;
			}
			if (pageSize < 0) {
				pageSize = 100;
			}
			example.setOrderByClause("noticeId desc");
			RowBounds rowBounds = new RowBounds((pageNo - 1) * pageSize, pageSize);// (offset,limit)
			List<Notice> dataList = noticeMapper.selectByExampleWithBLOBsWithRowbounds(example, rowBounds);
			return new Page<Notice>(dataList, pageNo, pageSize, total);
		} catch (Exception e) {
			logger.error("NoticeManagerImpl-->" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * utype:3-特约教练 4-普通教练 type:0-今日重点播报、1-优惠活动、2-订单消息 3-喱喱头条、4-我的消息 5-系统消息
	 * 通过cityId、schoolId、utype三个条件所有组合情况 userIdStrs单独处理不和其他三个条件交叉
	 * schoolId、userIdstrs用like '%,id,%'匹配
	 * 
	 * @return
	 */
	@Override
	public Page<Notice> getNoticesByCoachId(Coach coach, byte type, int pageNo, int pageSize) {
		try {
			long coachId = coach.getCoachId();
			String utype = "";
			if ("1".equals(coach.getExtra())) {
				utype = String.valueOf(NoticeType.UTYPE_3);
			} else {
				utype = String.valueOf(NoticeType.UTYPE_4);
			}
			NoticeExample example = new NoticeExample();
			if (type == NoticeType.TYPE_5) {
				example.createCriteria().andTypeEqualTo(NoticeType.TYPE_5).andUserTypeEqualTo(NoticeType.USERTYPE_9).andIsdelEqualTo(NoticeType.ISDEL_0)
						.andUserIdIsNull();
				example.or(example.createCriteria().andTypeEqualTo(NoticeType.TYPE_5).andUserTypeEqualTo(NoticeType.USERTYPE_1)
						.andIsdelEqualTo(NoticeType.ISDEL_0).andUserIdEqualTo(coachId));
			} else if (type == NoticeType.TYPE_2) {
				example.createCriteria().andTypeEqualTo(NoticeType.TYPE_2).andIsdelEqualTo(NoticeType.ISDEL_0).andUserTypeEqualTo(NoticeType.USERTYPE_1)
						.andUserIdEqualTo(coachId);
			} else if (type == NoticeType.TYPE_1 || type == NoticeType.TYPE_4) {

				example.createCriteria().andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0).andUserTypeEqualTo(NoticeType.USERTYPE_9)
						.andCityIdIsNull().andSchoolIdIsNull().andUtypeIsNull().andUserIdStrsIsNull();
				example.or(example.createCriteria().andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0)
						.andUserTypeEqualTo(NoticeType.USERTYPE_9).andUserIdStrsLike("%," + coachId + ",%"));
				// cityId==null schoolId==null
				example.or(example.createCriteria().andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0).andUserIdStrsIsNull()
						.andUserTypeEqualTo(NoticeType.USERTYPE_9).andCityIdIsNull().andSchoolIdIsNull()
						.andUtypeLike("%" + utype + "%"));
				// cityId==null schoolId!=null int schoolId=coach.getSchoolId();

				example.or(example.createCriteria().andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0).andUserIdStrsIsNull()
						.andUserTypeEqualTo(NoticeType.USERTYPE_9).andCityIdIsNull()
						.andSchoolIdLike("%," + coach.getSchoolId() + ",%").andUtypeLike("%" + utype + "%"));
				example.or(example.createCriteria().andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0).andUserIdStrsIsNull()
						.andUserTypeEqualTo(NoticeType.USERTYPE_9).andCityIdIsNull()
						.andSchoolIdLike("%," + coach.getSchoolId() + ",%").andUtypeIsNull());
				// cityId!=null schoolId==null
				example.or(example.createCriteria().andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0).andUserIdStrsIsNull()
						.andUserTypeEqualTo(NoticeType.USERTYPE_9).andCityIdLike("%" + coach.getCityId() + "%").andSchoolIdIsNull()
						.andUtypeLike("%" + utype + "%"));
				example.or(example.createCriteria().andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0).andUserIdStrsIsNull()
						.andUserTypeEqualTo(NoticeType.USERTYPE_9).andCityIdLike("%" + coach.getCityId() + "%").andSchoolIdIsNull()
						.andUtypeIsNull());
				// cityId!=null schoolId!=null
				example.or(example.createCriteria().andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0).andUserIdStrsIsNull()
						.andUserTypeEqualTo(NoticeType.USERTYPE_9).andCityIdLike("%" + coach.getCityId() + "%")
						.andSchoolIdLike("%," + coach.getSchoolId() + ",%").andUtypeLike("%" + utype + "%"));
				example.or(example.createCriteria().andTypeEqualTo(type).andIsdelEqualTo(NoticeType.ISDEL_0).andUserIdStrsIsNull()
						.andUserTypeEqualTo(NoticeType.USERTYPE_9).andCityIdLike("%" + coach.getCityId() + "%")
						.andSchoolIdLike("%," + coach.getSchoolId() + ",%").andUtypeIsNull());
			}

			int total = noticeMapper.countByExample(example);
			if (pageNo < 0) {
				pageNo = 1;
			}
			if (pageSize < 0) {
				pageSize = 100;
			}
			example.setOrderByClause("noticeId desc");
			RowBounds rowBounds = new RowBounds((pageNo - 1) * pageSize, pageSize);// (offset,limit)
			List<Notice> dataList = noticeMapper.selectByExampleWithRowbounds(example, rowBounds);
			return new Page<Notice>(dataList, pageNo, pageSize, total);
		} catch (Exception e) {
			logger.error("NoticeManagerImpl-->" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Notice> getNoticeCoachIndex(Coach coach) {
		List<Notice> list = new ArrayList<>();
		// 系统消息、订单消息、活动消息、我的消息各取第一条数据给首页
		long coachId = coach.getCoachId();
		list=noticeVoMapper.getNoticeCoachIndex(coachId);
		Page<Notice> pageList1 = getNoticesByCoachId(coach, NoticeType.TYPE_1, 1, 1);
		if (pageList1.getDataList().size() > 0) {
			list.add(pageList1.getDataList().get(0));
		}
		return list;
	}

	@Override
	public int getTotalCount() {
		NoticeExample example = new NoticeExample();
		return noticeMapper.countByExample(example);
	}

	@Override
	public Page<Notice> getNotice(Notice notice, int pageNo, int pageSize) {
		NoticeExample example = new NoticeExample();
		NoticeExample.Criteria criteria = example.createCriteria();
		if (notice.getType() != null) {
			criteria.andTypeEqualTo(notice.getType());
		}
		if (notice.getUserType() != null) {
			criteria.andUserTypeEqualTo(notice.getUserType());
		}
		if (notice.getUserType() == null) {
			List<Byte> list = new ArrayList<>();
			list.add(NoticeType.USERTYPE_9);
			list.add(NoticeType.USERTYPE_10);
			criteria.andUserTypeIn(list);
		}
		if (notice.getIsdel() != null && !notice.getIsdel().equals("")) {
			criteria.andIsdelEqualTo(notice.getIsdel());
		}
		if (notice.getTitle() != null && !notice.getTitle().equals("")) {
			criteria.andTitleLike("%" + notice.getTitle() + "%");
		}
		if (notice.getEtime() != null && !notice.getEtime().equals("")) {
			criteria.andTimeLessThan(notice.getEtime());
		}
		if (notice.getTime() != null && !notice.getTime().equals("")) {
			criteria.andTimeGreaterThanOrEqualTo(notice.getTime());
		}
		criteria.andIsdelNotEqualTo((byte) 1);
		int total = noticeMapper.countByExample(example);
		example.setOrderByClause("time desc");
		RowBounds rowBounds = new RowBounds((pageNo - 1) * pageSize, pageSize);// (offset,limit)
		List<Notice> dataList = noticeMapper.selectByExampleWithRowbounds(example, rowBounds);
		return new Page<Notice>(dataList, pageNo, pageSize, total);
	}

	public int addCmsNotice(Notice notice) {
		// 获取noticeId返回前端使用
		int noticeId = noticeVoMapper.queryMaxIdAddOne();
		notice.setNoticeId(noticeId);
		if(notice.getType()==NoticeType.TYPE_3){
			notice.setIsdel(NoticeType.ISDEL_0); 
			sendMessageTouTiao(notice);
			redisUtil.delete(REDISKEY.NOTICE_TOP);
		}else{
			notice.setIsdel(NoticeType.ISDEL_2);
		}
		noticeMapper.insertSelective(notice);
		return noticeId;
	}

	public int updateNotice( Notice notice) {
		
		noticeVoMapper.updateNoticeById(notice); // 备注： 不能更新clickNum字段, app端累计+1时没清缓存, cms中读取得clickNum缓存不是最新的
        redisUtil.delete(REDISKEY.NOTICE + notice.getNoticeId());

        //只推送cms配置过来的消息  系统更新消息过滤掉
		if (notice.getIsdel()!=null && notice.getIsdel() == NoticeType.ISDEL_0 && notice.getType()!=NoticeType.TYPE_3 && (notice.getUserType() == NoticeType.USERTYPE_9 || notice.getUserType() ==  NoticeType.USERTYPE_10)) {// 0为发布状态
			sendMessage(notice);
		}
		if (notice.getIsdel()!=null && notice.getIsdel() == NoticeType.ISDEL_0 && notice.getType()==NoticeType.TYPE_3 && (notice.getUserType() == NoticeType.USERTYPE_9 || notice.getUserType() ==  NoticeType.USERTYPE_10)) {// 0为发布状态
			sendMessageTouTiao(notice);
			redisUtil.delete(REDISKEY.NOTICE_TOP);
		}
		
		return 1;

	}

	public Notice getNoticeById(String noticeId) {
		Notice notice = redisUtil.get(REDISKEY.NOTICE + noticeId);
		if (notice == null) {
			notice = (Notice) noticeMapper.selectByPrimaryKey(Integer.parseInt(noticeId));
			redisUtil.set(REDISKEY.NOTICE + noticeId, notice);
		}
		return notice;
	}

	public int updateState(String noticeId, String isdel,String type) {
		Notice notice=getNoticeById(noticeId);
		if (isdel.equals("0") && !"3".equals(type)) {// 0为发布状态  type=3 喱喱头条
			sendMessage(notice);
		}
		if (isdel.equals("0") && "3".equals(type)) {// 0为发布状态  type=3 喱喱头条
			sendMessageTouTiao(notice);
			redisUtil.delete(REDISKEY.NOTICE_TOP);
			
		}
		notice.setNoticeId(Integer.parseInt(noticeId));
		notice.setIsdel((byte) Integer.parseInt(isdel));
		notice.setTime(new Date());
		redisUtil.delete(REDISKEY.NOTICE + notice.getNoticeId());
		return noticeMapper.updateByPrimaryKeySelective(notice);
	}

	public void noticeAddClickNum(String noticeId) {
		noticeVoMapper.noticeAddClickNum(noticeId);
	}

	public Notice getNoticeByOrderId(String orderId, byte usertype) {
		NoticeExample example = new NoticeExample();
		example.createCriteria().andOrderIdEqualTo(orderId).andUserTypeEqualTo(usertype);
		List<Notice> list = noticeMapper.selectByExample(example);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public void sendMessage(Notice notice) {
		try {

			List<String> cityId = new ArrayList<>();
			String utype = "";
			List<String> schoolId = new ArrayList<>();
			List<String> applyexam = new ArrayList<>();
			List<String> userIdStrs = new ArrayList<>();

			String cityIdStr = notice.getCityId();
			if (cityIdStr != null && !cityIdStr.equals("")) {
				String args[] = cityIdStr.split(",");
				for (int i = 0; i < args.length; i++) {
					cityId.add(args[i]);
				}
			}

			String utypeStr = notice.getUtype();
			if (utypeStr != null && !utypeStr.equals("")) {
				// 目前前端只有两种状态 两个全选时不做过滤 只考虑只选一个的
				if (utypeStr.indexOf(",") == -1) {
					utype = utypeStr;
				}
			}

			String applyexamStr = notice.getApplyexam();
			if (applyexamStr != null && !applyexamStr.equals("")) {
				applyexamStr.substring(1, applyexamStr.length() - 1); // 去除首尾的|
				String args[] = applyexamStr.split("\\|");
				for (int i = 0; i < args.length; i++) {
					applyexam.add(args[i]);
				}
			}

			String schoolIdStr = notice.getSchoolId();
			if (schoolIdStr != null && !schoolIdStr.equals("")) {
				schoolIdStr = schoolIdStr.substring(1, schoolIdStr.length() - 1);
				String args[] = schoolIdStr.split(",");
				for (int i = 0; i < args.length; i++) {
					schoolId.add(args[i]);
				}
			}

			String userIdStrsStr = notice.getUserIdStrs();
			if (userIdStrsStr != null && !userIdStrsStr.equals("")) {
				userIdStrsStr = userIdStrsStr.substring(1, userIdStrsStr.length() - 1);
				String args[] = userIdStrsStr.split(",");
				for (int i = 0; i < args.length; i++) {
					userIdStrs.add(args[i]);
				}
			}

			// 通过过滤条件获取要发送的userId
			Map<String, Object> map = new HashMap<>();
			map.put("cityId", cityId);
			map.put("utype", utype);
			map.put("applyexam", applyexam);
			map.put("schoolId", schoolId);
			map.put("userIdStrs", userIdStrs);
			Set<Long> userIds = new HashSet<>();
			String sendMessageType = "";
			
			
			if (notice.getUserType() == 10) {
				sendMessageType = JpushConstant.RMQTAG.JPUSH2STU;
				Set<Long> userAll= noticeVoMapper.getNoticeStudentIds(map);
				//过滤在线用户
				userIds =redisUtil.getOnlineUserId(REDISKEY.STUDENT_TOKEN+"*",REDISKEY.STUDENT_TOKEN);
				userIds.retainAll(userAll);
			} else if (notice.getUserType() == 9) {
				sendMessageType = JpushConstant.RMQTAG.JPUSH2COACH;
				Set<Long> userAll = noticeVoMapper.getNoticeCoachIds(map);
				//过滤在线用户
				userIds =redisUtil.getOnlineUserId(REDISKEY.COACH_TOKEN+"*",REDISKEY.COACH_TOKEN);
				userIds.retainAll(userAll);
				
			}
			JpushMsg jmsg = null;
			Iterator<Long> it = userIds.iterator();
			
			while (it.hasNext()) {
				String id = String.valueOf(it.next());
				if (jmsg == null) {
					jmsg = new JpushMsg();
					jmsg.setAlter(notice.getTitle());
					Map<String, String> extras = new HashMap<String, String>();
					extras.put("noticeId", String.valueOf(notice.getNoticeId()));
					extras.put("type", String.valueOf(notice.getType()));
					jmsg.setExtras(extras);
					jmsg.addUser(Long.parseLong(id));
					jmsg.setOperate(JpushConstant.OPERATE.MESSAGE);
				} else {
					jmsg.addUser(Long.parseLong(id));
				}
				if (jmsg.getUserIds().size() >= 500) {
					Message jpush = new Message();
					jpush.setKeys(String.valueOf(notice.getNoticeId()));
					jpush.setTopic(jpushProducer.getCreateTopicKey());
					jpush.setTags(sendMessageType);
					jpush.setBody(SerializableUtil.serialize(jmsg));
					jpushProducer.send(jpush);
					jmsg = null;
				}
			}

			// 极光推送 发送消息
			if (jmsg != null) {
				Message jpush = new Message();
				jpush.setKeys(String.valueOf(notice.getNoticeId()));
				jpush.setTopic(jpushProducer.getCreateTopicKey());
				jpush.setTags(sendMessageType);
				jpush.setBody(SerializableUtil.serialize(jmsg));
				jpushProducer.send(jpush);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 喱喱头条全量推送
	 */
	public void sendMessageTouTiao(Notice notice) {
		try {
			JpushMsg jmsg = new JpushMsg();
			Set<Long> userIds = new HashSet<>();
			userIds.add((long) 68828005);
			userIds.add((long) 2);
			jmsg.setAlter(notice.getTitle());
			Map<String, String> extras = new HashMap<String, String>();
			extras.put("noticeId", String.valueOf(notice.getNoticeId()));
			extras.put("type", String.valueOf(notice.getType()));
			jmsg.setExtras(extras);
			//全量推送
			jmsg.setSendAll(JpushConstant.RMQTAG.JPUSH2STU);
			
			jmsg.setOperate(JpushConstant.OPERATE.TOPMESSAGE);
			Message jpush = new Message();
			jpush.setKeys(String.valueOf(notice.getNoticeId()));
			jpush.setTopic(jpushProducer.getCreateTopicKey());
			jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
			jpush.setBody(SerializableUtil.serialize(jmsg));
			jpushProducer.send(jpush);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
