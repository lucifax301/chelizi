package com.lili.file.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.lili.coach.manager.CoachManager;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.VersionUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.configfile.manager.ConfigFileManager;
import com.lili.configfile.vo.ConfigFileVo;
import com.lili.file.dto.Coursenew;
import com.lili.file.dto.CoursenewExample;
import com.lili.file.dto.Feedback;
import com.lili.file.dto.TitleType;
import com.lili.file.dto.TypeContent;
import com.lili.file.manager.ConfigManager;
import com.lili.file.manager.FileManager;
import com.lili.file.mapper.CoursenewMapper;
import com.lili.file.mapper.FeedbackMapper;
import com.lili.file.mapper.TitleTypeMapper;
import com.lili.file.mapper.TypeContentMapper;
import com.lili.file.vo.ConfigVo;
import com.lili.file.vo.CoursenewVo;
import com.lili.file.vo.TitleTypeVo;
import com.lili.file.vo.TypeContentVo;
import com.lili.student.dto.MicroClass;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;

public class FileManagerImpl implements FileManager {
	private static Logger logger = LoggerFactory.getLogger(FileManagerImpl.class);
	@Autowired
	CoursenewMapper coursenewMapper;
	@Autowired
	private FeedbackMapper feedbackMapper;
	@Autowired
	private StudentManager studentManager;
	@Autowired
	private CoachManager coachManager;
	@Autowired
	private RedisUtil redisUtil;
	@Value("${file_course_notice_3}")
	private String file_course_notice_3 = "";
	@Value("${file_course_notice_4}")
	private String file_course_notice_4 = "";
	@Value("${file_course_notice_5}")
	private String file_course_notice_5 = "";
	@Value("${file_course_notice_6}")
	private String file_course_notice_6 = "";
	@Autowired
	private ConfigFileManager configFileManager;
	@Autowired
	ConfigManager configManager;
	
	@Autowired
	TypeContentMapper typeContentMapper;
	
	@Autowired
	TitleTypeMapper titleTypeMapper;
	
	
	@Override
	public ReqResult getCoursenew(String userId, String userType,String cityId,String v) {
		ReqResult r = ReqResult.getSuccess();
		List<Coursenew> cs = redisUtil.get(REDISKEY.COURSES);
		if(null == cs){
			CoursenewExample cexample = new CoursenewExample();
			CoursenewExample.Criteria criteria = cexample.createCriteria();
			criteria.andIsdelEqualTo(ReqConstants.OPERATE_DELETE_NO);
			cexample.setOrderByClause("subjectId");
			cs = coursenewMapper.selectByExample(cexample);
			redisUtil.set(REDISKEY.COURSES,cs);
		}
		//20160326如果是教练，则添加区域划分课程
		/*if(null != userType && userType.equals(ReqConstants.USER_TYPE_COACH +"")){
			Coach co = coachManager.getCoachInfo(Long.parseLong(userId));
			String city = String.valueOf(co.getCityId());
			if(null != city && !"".equals(city)){
				for(int i=0;i<cs.size();i++){
					Coursenew csi= cs.get(i);
					//如果该课程不在教练所在城市中，则删除该课程
					if(!csi.getCitys().contains(city)){
						cs.remove(i);
						i --;
					}
				}
			}
		}*/
		if(null != cityId && !"".equals(cityId)){
			for(int i=0;i<cs.size();i++){
				Coursenew csi= cs.get(i);
				//如果该课程不在教练所在城市中，则删除该课程
				if(!csi.getCitys().contains(cityId)){
					cs.remove(i);
					i--;
				}
			}
		}
		List<CoursenewVo> csv = new ArrayList<CoursenewVo>();
		if(null != cs && cs.size() >0){
			try {
				csv = BeanCopy.copyList(cs, CoursenewVo.class, BeanCopy.COPYNOTNULL);
				
			} catch (Exception e) {
				logger.error("FileManagerImpl--getCoursenew--"+ e);
			}
		}
		
		if(userType != null && !"".equals(userType)){
			//20160524学员端新增文科题，只针对1.7.0及更高版本有效，只针对学员有效
			if((null != v && !"".equals(v) && (VersionUtil.compareVersion(v, "1.7.0") < 0))
					||(ReqConstants.USER_TYPE_STUDENT != Integer.parseInt(userType))){
				for(int i=0;i<csv.size();i++){
					CoursenewVo tmp = csv.get(i);
					if(tmp.getSubjectid() == ReqConstants.SUBJECT_TYPE_ONE){
						csv.remove(i);
						i --;
					}
				}
			}
		}
		//如果是学员，则需要添加能否预约学习
		if(null != userType && userType.equals(ReqConstants.USER_TYPE_STUDENT +"")  && null != userId){
			Student st = studentManager.getStudentInfo(Long.parseLong(userId));
			String flowNo = st.getFlowNo();
			String drLicence = st.getDrLicence();
			Short course1 = st.getCourse1();
			Short course2 = st.getCourse2();
			Short course4 = st.getCourse4();
			Integer applyexam = st.getApplyexam();
			//20160504变更不开放的提示，兼容之前学员端版本为1.6.3
	        if(null != v && !"".equals(v) && (VersionUtil.compareVersion(v, "1.6.3") > 0) ){
				//extra 0-开放；1-提示不能预约；2-提示上传驾照
	        	//extra 3-报名还在受理中；4-加油考过科目一就可以约科目三啦；5-考过科目一，就可以约科目三啦；6-拿到驾照就可以约陪驾啦；10-报名引导弹窗
	        	//新增extra2字段，用于返回描述信息
				//（1）如果有驾照认证，则都可以预约
//				if(null != drLicence && !"".equals(drLicence.trim())){
//					//
//				}
//				// 20160517 新增如果是科目4考过了，则可以预约所有的，陪驾提示上传驾照
//				else if(null !=course4 && course4 != 0 ){
//					for(int i=0;i<csv.size();i++){
//						CoursenewVo cv = csv.get(i);
//						if(cv.getSubjectid() == ReqConstants.SUBJECT_TYPE_PEIJIA){
//							// 20160729 陪驾放开限制
//							if ((VersionUtil.compareVersion(v, "1.8.1") < 0)) {
//								cv.setExtra("2");
//							}
//							else {
//								cv.setExtra("0");
//							}
//							csv.set(i, cv);
//						}
//					}
//				}
//				//（2）如果科目一或科目二有分数，则不能预约陪驾
//				else if((null !=course1 && course1 != 0 )||(null !=course2 && course2 != 0)){
//					for(int i=0;i<csv.size();i++){
//						CoursenewVo cv = csv.get(i);
//						if(cv.getSubjectid() == ReqConstants.SUBJECT_TYPE_PEIJIA){
//							// 20160729 陪驾放开限制
//							if ((VersionUtil.compareVersion(v, "1.8.1") < 0)) {
//								cv.setExtra("1");
//							}
//							else {
//								cv.setExtra("0");
//							}
//							cv.setExtra2(file_course_notice_6);
//							csv.set(i, cv);
//						}
//					}
//				}
//				//（3）如果有流水号，不能预约陪驾和科目三
//				else if(null != flowNo && !"".equals(flowNo.trim())){
//					for(int i=0;i<csv.size();i++){
//						CoursenewVo cv = csv.get(i);
//						if(cv.getSubjectid() == ReqConstants.SUBJECT_TYPE_THREE){
//							cv.setExtra("1");
//							cv.setExtra2(file_course_notice_5);
//							csv.set(i, cv);
//						}else if(cv.getSubjectid() == ReqConstants.SUBJECT_TYPE_PEIJIA){
//							// 20160729 陪驾放开限制
//							if ((VersionUtil.compareVersion(v, "1.8.1") < 0)) {
//								cv.setExtra("1");
//							}
//							else {
//								cv.setExtra("0");
//							}
//							cv.setExtra2(file_course_notice_6);
//							csv.set(i, cv);
//						}
//					}
//				}
//				//（4）没有流水号，但是已经报名啦
//				else if(null != applyexam && applyexam > ReqConstants.PROG_STAGE_BEGIN){
//					for(int i=0;i<csv.size();i++){
//						CoursenewVo cv = csv.get(i);
//						if(cv.getSubjectid() == ReqConstants.SUBJECT_TYPE_TWO
//								&& cv.getCoursetemid() != ReqConstants.COURSE_TYPE_C1_TWO_BASIC 
//								&& cv.getCoursetemid() != ReqConstants.COURSE_TYPE_C2_TWO_BASIC ){
//							cv.setExtra("1");
//							cv.setExtra2(file_course_notice_3);
//							csv.set(i, cv);
//						}else if (cv.getSubjectid() == ReqConstants.SUBJECT_TYPE_THREE ){
//							cv.setExtra("1");
//							cv.setExtra2(file_course_notice_5);
//							csv.set(i, cv);
//						}else if (cv.getSubjectid() == ReqConstants.SUBJECT_TYPE_PEIJIA ){
//							// 20160729 陪驾放开限制
//							if ((VersionUtil.compareVersion(v, "1.8.1") < 0)) {
//								cv.setExtra("1");
//							}
//							else {
//								cv.setExtra("0");
//							}
//							cv.setExtra2(file_course_notice_6);
//							csv.set(i, cv);
//						}
//					}
//				}
//				//（5）到了这里，说明是新注册用户，陪驾提示需要上传驾照；只能预约科二的基础；其他提示不能预约
//				else {
//					for(int i=0;i<csv.size();i++){
//						CoursenewVo cv = csv.get(i);
//						if(cv.getSubjectid() == ReqConstants.SUBJECT_TYPE_THREE ||
//								(cv.getSubjectid() == ReqConstants.SUBJECT_TYPE_TWO
//								&& cv.getCoursetemid() != ReqConstants.COURSE_TYPE_C1_TWO_BASIC 
//								&& cv.getCoursetemid() != ReqConstants.COURSE_TYPE_C2_TWO_BASIC )){
//							cv.setExtra("3");
//							csv.set(i, cv);
//						}else if (cv.getSubjectid() == ReqConstants.SUBJECT_TYPE_PEIJIA ){
//							// 20160729 陪驾放开限制
//							if ((VersionUtil.compareVersion(v, "1.8.1") < 0)) {
//								cv.setExtra("2");
//							}
//							else {
//								cv.setExtra("0");
//							}
//							csv.set(i, cv);
//						}
//					}
//				}
	        }else{
	        	// 1.6.3版本之前，用之前的版本格式
				//extra 0-开放；1-提示不能预约；2-提示上传驾照
				//如果有驾照认证，则都可以预约
				if(null != drLicence && !"".equals(drLicence.trim())){
					//
				}
				//如果有流水号，不能预约陪驾和科目三
				else if(null != flowNo && !"".equals(flowNo.trim())){
					for(int i=0;i<csv.size();i++){
						CoursenewVo cv = csv.get(i);
						if(cv.getSubjectid() == ReqConstants.SUBJECT_TYPE_THREE 
								|| cv.getSubjectid() == ReqConstants.SUBJECT_TYPE_PEIJIA){
							// 20160729 陪驾放开限制
							if ((VersionUtil.compareVersion(v, "1.8.1") < 0)) {
								cv.setExtra("1");
							}
							else {
								cv.setExtra("0");
							}
							csv.set(i, cv);
						}
					}
				}
				//如果科目一或科目二有分数，则不能预约陪驾
				else if((null !=course1 && course1 != 0 )||(null !=course2 && course2 != 0)){
					for(int i=0;i<csv.size();i++){
						CoursenewVo cv = csv.get(i);
						if(cv.getSubjectid() == ReqConstants.SUBJECT_TYPE_PEIJIA){
							// 20160729 陪驾放开限制
							if ((VersionUtil.compareVersion(v, "1.8.1") < 0)) {
								cv.setExtra("1");
							}
							else {
								cv.setExtra("0");
							}
							csv.set(i, cv);
						}
					}
				}
				//到了这里，说明是新注册用户，陪驾提示需要上传驾照；只能预约科二的基础；其他提示不能预约
				else {
					for(int i=0;i<csv.size();i++){
						CoursenewVo cv = csv.get(i);
						if(cv.getSubjectid() == ReqConstants.SUBJECT_TYPE_THREE ){
							cv.setExtra("1");
							csv.set(i, cv);
						}else if (cv.getSubjectid() == ReqConstants.SUBJECT_TYPE_PEIJIA ){
							// 20160729 陪驾放开限制
							if ((VersionUtil.compareVersion(v, "1.8.1") < 0)) {
								cv.setExtra("2");
							}
							else {
								cv.setExtra("0");
							}
							csv.set(i, cv);
						}else if (cv.getSubjectid() == ReqConstants.SUBJECT_TYPE_TWO
								&& cv.getCoursetemid() != ReqConstants.COURSE_TYPE_C1_TWO_BASIC 
								&& cv.getCoursetemid() != ReqConstants.COURSE_TYPE_C2_TWO_BASIC ){
							cv.setExtra("1");
							csv.set(i, cv);
						}
					}
				}
	        }
				
		}
		r.setData(csv);
		return r;
	}
	
	@Override
	public ReqResult getCoursenew(String cityId,String v) {
		ReqResult r = ReqResult.getSuccess();
		List<Coursenew> cs = redisUtil.get(REDISKEY.COURSES);
		if(null == cs){
			CoursenewExample cexample = new CoursenewExample();
			CoursenewExample.Criteria criteria = cexample.createCriteria();
			criteria.andIsdelEqualTo(ReqConstants.OPERATE_DELETE_NO);
			cexample.setOrderByClause("subjectId");
			cs = coursenewMapper.selectByExample(cexample);
			redisUtil.set(REDISKEY.COURSES,cs);
		}
		if(null != cityId && !"".equals(cityId)){
			for(int i=0;i<cs.size();i++){
				Coursenew csi= cs.get(i);
				//如果该课程不在教练所在城市中，则删除该课程
				if(!csi.getCitys().contains(cityId)){
					cs.remove(i);
					i--;
				}
			}
		}
		List<CoursenewVo> csv = new ArrayList<CoursenewVo>();
		if(null != cs && cs.size() >0){
			try {
				csv = BeanCopy.copyList(cs, CoursenewVo.class, BeanCopy.COPYNOTNULL);
				
			} catch (Exception e) {
				logger.error("FileManagerImpl--getCoursenew--"+ e);
			}
		}
		
		r.setData(csv);
		return r;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public ReqResult getCoursenewTree(String userId, String userType){
		ReqResult r = ReqResult.getSuccess();
		ReqResult dataold = this.getCoursenew(userId, userType,null,null);
		List<CoursenewVo> cvs = (List<CoursenewVo>) dataold.getResult().get(ResultCode.RESULTKEY.DATA);
		Map<String,Map<String,Map<String,CoursenewVo>>> data = new HashMap<String, Map<String,Map<String,CoursenewVo>>>();
		Map<String,Map<String,CoursenewVo>> sub1 = new HashMap<String, Map<String,CoursenewVo>>();
		Map<String,Map<String,CoursenewVo>> sub2 = new HashMap<String, Map<String,CoursenewVo>>();
		for(int i=0;i<cvs.size();i++){
			CoursenewVo cv = cvs.get(i);
			Byte drtype = cv.getDrtype();
			Integer subjectid = cv.getSubjectid();
			Integer courseid = cv.getCoursetemid();
			String subjectName = cv.getLevel1();
			String courseName = cv.getLevel2();
			// 1.按照驾驶类型分类
			if(drtype == ReqConstants.DRIVE_TYPE_C1){
				// 2.按照科目分类
				if(sub1.containsKey(subjectName)){
					Map<String,CoursenewVo> courseids1 = sub1.get(subjectName);
					// 3.按照课程添加
					if(!courseids1.containsKey(courseName)){
						courseids1.put(courseName, cv);
					}
					
				}else{
					Map<String,CoursenewVo> courseids = new HashMap<String, CoursenewVo>();
					courseids.put(courseName, cv);
					sub1.put(subjectName, courseids);
				}
				
			}else if (drtype == ReqConstants.DRIVE_TYPE_C2){
				// 2.按照科目分类
				if(sub2.containsKey(subjectName)){
					Map<String,CoursenewVo> courseids1 = sub2.get(subjectName);
					// 3.按照课程添加
					if(!courseids1.containsKey(courseName)){
						courseids1.put(courseName, cv);
					}
					
				}else{
					Map<String,CoursenewVo> courseids = new HashMap<String, CoursenewVo>();
					courseids.put(courseName, cv);
					sub2.put(subjectName, courseids);
				}
			}
			
		}
		data.put("C1", sub1);
		data.put("C2", sub2);
/*		Map<Byte,Map<Integer,Map<Integer,CoursenewVo>>> data = new HashMap<Byte, Map<Integer,Map<Integer,CoursenewVo>>>();
		Map<Integer,Map<Integer,CoursenewVo>> sub1 = new HashMap<Integer, Map<Integer,CoursenewVo>>();
		Map<Integer,Map<Integer,CoursenewVo>> sub2 = new HashMap<Integer, Map<Integer,CoursenewVo>>();
		for(int i=0;i<cvs.size();i++){
			CoursenewVo cv = cvs.get(i);
			Byte drtype = cv.getDrtype();
			Integer subjectid = cv.getSubjectid();
			Integer courseid = cv.getCoursetemid();
			// 1.按照驾驶类型分类
			if(drtype == ReqConstants.DRIVE_TYPE_C1){
				// 2.按照科目分类
				if(sub1.containsKey(subjectid)){
					Map<Integer,CoursenewVo> courseids1 = sub1.get(subjectid);
					// 3.按照课程添加
					if(!courseids1.containsKey(courseid)){
						courseids1.put(courseid, cv);
					}
					
				}else{
					Map<Integer,CoursenewVo> courseids = new HashMap<Integer, CoursenewVo>();
					courseids.put(courseid, cv);
					sub1.put(subjectid, courseids);
				}
				
			}else if (drtype == ReqConstants.DRIVE_TYPE_C2){
				// 2.按照科目分类
				if(sub2.containsKey(subjectid)){
					Map<Integer,CoursenewVo> courseids1 = sub2.get(subjectid);
					// 3.按照课程添加
					if(!courseids1.containsKey(courseid)){
						courseids1.put(courseid, cv);
					}
					
				}else{
					Map<Integer,CoursenewVo> courseids = new HashMap<Integer, CoursenewVo>();
					courseids.put(courseid, cv);
					sub2.put(subjectid, courseids);
				}
			}
			
		}
		data.put((byte) ReqConstants.DRIVE_TYPE_C1, sub1);
		data.put((byte) ReqConstants.DRIVE_TYPE_C2, sub2);
*/		
		r.setData(data);
		return r;
	}
	
/*	@Override
	public ReqResult getPoster(String userType) {
		ReqResult r = ReqResult.getSuccess();
		PosterExample example = new PosterExample();
		PosterExample.Criteria criteria = example.createCriteria();
		criteria.andIsdelEqualTo(ReqConstants.OPERATE_DELETE_NO);
		Byte type =1; //如果没有传参数，默认是教练端
		if(null != userType && !"".equals(userType)){
			type = Byte.parseByte(userType);
		}
		List<Byte> types = new ArrayList<Byte>();
		types.add(type);
		types.add((byte)0);
		criteria.andTypeIn(types);
		example.setOrderByClause("posterId desc limit 1");
		List<Poster> pos = posterMapper.selectByExample(example);
		PosterVo posvo = new PosterVo();
		try {
			posvo = BeanCopy.copyNotNull(pos, PosterVo.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(null != pos){
			r.setData(pos.get(0));
		}else {
			r = ReqResult.getFailed();
		}
		return r;
	}*/

	@Override
	public ReqResult postFeedback(String fbtitle, String fbcontent,
			String fbpicture, String userId, String userType) {
		ReqResult r = ReqResult.getSuccess();
		Feedback fb = new Feedback();
		fb.setFbtitle(fbtitle);
		fb.setFbcontent(fbcontent);
		fb.setFbpicture(fbpicture);
		fb.setUserid(Long.parseLong(userId));
		fb.setUsertype(Byte.parseByte(userType));
		feedbackMapper.insertSelective(fb);
		return r;
	}

	@Override
	public CoursenewVo getCoursenewBycourseid(int courseTmpId) {
		CoursenewVo cv = redisUtil.get(REDISKEY.COURSES_ITEM + courseTmpId);
		if(null == cv){
			//Coursenew cs = coursenewMapper.selectByCourseid(courseTmpId);			
			CoursenewExample cexample = new CoursenewExample();
			CoursenewExample.Criteria criteria = cexample.createCriteria();
			criteria.andIsdelEqualTo(ReqConstants.OPERATE_DELETE_NO);
			criteria.andCoursetemidEqualTo(courseTmpId);
			List<Coursenew> css = coursenewMapper.selectByExample(cexample);
			
			cv = new CoursenewVo();
			if(null != css && css.size()>0){
				Coursenew cs = css.get(0);
				try {
					cv = BeanCopy.copyNotNull(cs, CoursenewVo.class);
					redisUtil.set(REDISKEY.COURSES_ITEM + courseTmpId,cv);
				} catch (Exception e) {
					logger.error("FileManagerImpl--getCoursenew--"+ e);
				}
			}
		}
		return cv;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, CoursenewVo> getCoursenewMap() {
		Map<Integer, CoursenewVo> mcvs = redisUtil.get(REDISKEY.COURSES_MAP);
		if(null == mcvs){
			List<CoursenewVo> cvs = (List<CoursenewVo>) this.getCoursenew(null, null,null,null).getResult().get(ResultCode.RESULTKEY.DATA);
			mcvs = new HashMap<Integer, CoursenewVo>();
			for(int i=0;i<cvs.size();i++){
				CoursenewVo cv = cvs.get(i);
				Integer id = cv.getCoursetemid();
				mcvs.put(id, cv);
			}
			redisUtil.set(REDISKEY.COURSES_MAP,mcvs);
			
		}
		return mcvs;
	}

	/**
	 * cityId可以认为是文件名，与userType组合为完整的文件名称，目前处理对象为txt文件
	 */
	@Override
	public ReqResult getConfigFile(String cityId, String userId, String userType, String menu, String type, String channel) {
		ReqResult r = ReqResult.getSuccess();
		r.setData("cityId", cityId);
		r.setData("userType", userType);
		
		List<ConfigFileVo> configFileList = null;
		List<MicroClass> microClassList = null;
		if (channel == null || "".equals(channel)) {
			channel = "1"; //channel默认1- 通用配置
		}
		String key = REDISKEY.CONFIG_FILE + userType+ "."+ cityId + "."+ channel; //缓存 config.file.1.100100
		
		if ("1".equals(channel)) { //通用配置
			configFileList = redisUtil.get(key);
			if (configFileList == null) { //如果缓存没有内容，直接读取文件写入缓存
				logger.info("***************************** Get key :"+ key +" Cache is Null, Get For File Start !");
				String path = getConfigFilePath(userType);
				if (path != null && !"".equals(path)) {
					r = configFileManager.getConfigFileInfo(path, cityId, userType, key, channel);
				}
				return r;
			}
			else {
				r.setData(configFileList);
			}
		}
		else if ("2".equals(channel)) { //广告
			microClassList = redisUtil.get(key);
			if (microClassList == null) { //如果缓存没有内容，直接读取文件写入缓存
				logger.info("***************************** Get Cache is Null, Get For File Start !");
				String path = getConfigFilePath(userType);
				if (path != null && !"".equals(path)) {
					r = configFileManager.getConfigFileInfo(path, cityId, userType, key, channel);
				}
				return r;
			}
			else {
				r.setData(microClassList);
			}
		}
		return r;
	}

	/**
	 * 获取配置文件路径
	 * @param userType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getConfigFilePath(String userType) {
		String path = null;
		ReqResult configR =  configManager.getConfigs(null, userType, null);
		if (configR != null && configR.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)) {
			List<ConfigVo> configList = ( List<ConfigVo>) configR.getResult().get(ResultCode.RESULTKEY.DATA);
			for (int i = 0; i < configList.size(); i++) {
				ConfigVo cvo = configList.get(i);
				if (REDISKEY.CONFIG_FILE.equals(cvo.getName())) {
					path = cvo.getValue(); //获取配置文件路径
					logger.info("***************************** path : " + path);
					break;
				}
			}
		}
		return path;
	}

	@Override
	public ReqResult getTitel(String typeId, String channel, String name) {
		ReqResult r = ReqResult.getSuccess();
		try {
			TitleType titleType = new TitleType();
			if (typeId != null && !"".equals(typeId)) {
				titleType.setId(Integer.parseInt(typeId));
			}
			if (channel != null && !"".equals(channel)) {
				titleType.setChannel(Integer.parseInt(channel));
			}
			else { //默认为1
				titleType.setChannel(2);
			}
			if (name != null && !"".equals(name)) {
				titleType.setName(name);
			}
			List<TitleType> titelList = titleTypeMapper.queryTitleList(titleType);
			List<TitleTypeVo> ttv = new ArrayList<TitleTypeVo>();
			if(null != titelList && titelList.size() >0){
				ttv = BeanCopy.copyList(titelList, TitleTypeVo.class, BeanCopy.COPYNOTNULL);
			}
			r.setData(ttv);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public ReqResult getContent(String id) {
		ReqResult r = ReqResult.getSuccess();
		try {
			TypeContent tc = typeContentMapper.selectByPrimaryKey(Integer.parseInt(id));
			TypeContentVo tcv = new TypeContentVo();
			if (tc != null) {
				tcv = BeanCopy.copyAll(tc, tcv);
			}
			r.setData(tcv);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return r;
	}
	
}
