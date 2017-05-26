package com.lili.sync.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.lili.cms.util.BeanCopy;
import com.lili.common.util.redis.RedisUtil;
import com.lili.school.dto.EnrollProgressTemplate;
import com.lili.school.dto.EnrollProgressUser;
import com.lili.sync.dto.SGCar;
import com.lili.sync.dto.SGCertificate;
import com.lili.sync.dto.SGCoach;
import com.lili.sync.dto.SGOfficial;
import com.lili.sync.dto.SGStudent;
import com.lili.sync.mapper.CLZDataMapper;
import com.lili.sync.sgmapper.SGDataMapper;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:spring-init.xml")
public class SyncJob {

	private final Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private SGDataMapper sgDataMapper;
	@Autowired
	private CLZDataMapper clzDataMapper;
	@Autowired
	private RedisUtil redisUtil;
	
	public void syncCoachData(){
		try {
			int count = 0;
			//设置离职教练状态
			List<SGCoach> sgDelCoachs = sgDataMapper.selectDelCoach();
			if (sgDelCoachs != null && sgDelCoachs.size() > 0) {
				List<SGCoach> delCoachs = clzDataMapper.selectDelCoach(sgDelCoachs);
				if (delCoachs != null && delCoachs.size() > 0) {
					count = clzDataMapper.updateDelCoach(delCoachs);
					//处理离职教练的车辆和学员
					String coachIds = "";
					for (SGCoach coach: delCoachs){
						coachIds = coachIds + coach.getCoachId() + ",";
					}
					clzDataMapper.delDelCoachInfo(coachIds.substring(0, coachIds.length()-1));
					log.info("######################## updateDelCoach : " + delCoachs.size() + " coaches have quitted the job ");
					//删除离职教练info缓存
					for (SGCoach delCoach : delCoachs){
						redisUtil.delete("coach.vo." + delCoach.getCoachId());
						redisUtil.delete("coach.dto." + delCoach.getCoachId());
						redisUtil.delete("user.car.list." + delCoach.getCoachId());
					}
					
				}
			}
			//更新并插入新入职的教练
			List<SGCoach> sgCoachs = sgDataMapper.selectCoach();
			String idNumber = null;
			for (SGCoach sgCoach : sgCoachs) {
				idNumber = sgCoach.getIdNumber();
				if (idNumber != null && idNumber.length() > 6)
					sgCoach.setPassword(idNumber.substring(idNumber.length() - 6, idNumber.length()).toUpperCase());
				else
					sgCoach.setPassword("123456");
			}
			if (sgCoachs != null && sgCoachs.size() > 0){
				count = clzDataMapper.updateCoach(sgCoachs);
				log.info("######################## updateCoach : have handled " + sgCoachs.size() + "coaches info to our database ");
				count = clzDataMapper.insertCoach(sgCoachs);
				log.info("######################## insertCoach : have handled " + sgCoachs.size() + "coaches info to our database ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("######################## syncCoachData Exception : " + e.getMessage());
		}
	}
	
	public void syncStudentData(){
		try {
			//先更新数据库状态数据
			clzDataMapper.deleteTempTable();//前期定位问题，把每次同步完成后的临时数据保存一天
			sgDataMapper.updateStudentState();
			
			int count = 0;
			int size = 0, offset = 0, jxTotal = 0;

			//从深港数据库取出所有学员,插入到lili数据库的临时表
//			List<SGStudent> sgStudents = sgDataMapper.selectJXStudent();
//			List<SGStudent> sgCLZStudents = sgDataMapper.selectCLZStudent();
//			sgStudents.addAll(sgCLZStudents);
//			if (sgStudents != null && sgStudents.size() > 0){
//				size = sgStudents.size();
//				while (offset + 10000 < size) {
//					subStudentList = sgStudents.subList(offset, offset + 10000);
//					count = clzDataMapper.insertTempStudent(subStudentList);
//					log.info("######################## insertTempStudent : have inserted 10000 students info.");
//					offset+=10000;
//				}
//				if (offset < size ){
//					subStudentList = sgStudents.subList(offset, size);
//					count = clzDataMapper.insertTempStudent(subStudentList);
//					log.info("######################## insertTempStudent : have inserted " + subStudentList.size() + " students info.");
//				}
//				log.info("######################## insertTempStudent : have totally inserted " + sgStudents.size() + " students info.");
//			}
			//之前的同步太消耗内存，瞬时的内存需求比较大
			size = sgDataMapper.selectJXStudentTotal();
			if (size > 0) {
				int pageSize = 10000;
				while (offset + pageSize < size) {
					List<SGStudent> sgStudents = sgDataMapper.selectJXStudentLimit(offset, pageSize);
					if (sgStudents != null && sgStudents.size() > 0) {
						clzDataMapper.insertTempStudent(sgStudents);
						jxTotal += sgStudents.size();
						log.info("######################## insertTempStudent : have inserted " + sgStudents.size() + " students info.");
						sgStudents.clear();
					}
					offset += pageSize;
				}
				if (offset < size) {
					List<SGStudent> sgStudents = sgDataMapper.selectJXStudentLimit(offset, size + 1);
					if (sgStudents != null && sgStudents.size() > 0) {
						clzDataMapper.insertTempStudent(sgStudents);
						jxTotal += sgStudents.size();
						log.info("######################## insertTempStudent : have inserted " + sgStudents.size() + " students info.");
						sgStudents.clear();
					}
				}
				log.info("######################## insertTempStudent : have totally inserted " + jxTotal + " students info.");
			}
			//驾校学员同步
			count = clzDataMapper.updateJXStudentState();
			log.info("######################## updateJXStudentState : have updated " + count + " JX students state info.");
			count = clzDataMapper.updateJXStudent();
			log.info("######################## updateJXStudent : have updated " + count + " JX students info.");
			count = clzDataMapper.insertJXStudent();
			log.info("######################## insertJXStudent : have inserted " + count + " JX students info.");
			
			//从深港数据库取出喱喱学员的进度数据,插入到lili数据库的临时表
			List<SGCertificate> sgCertificates = sgDataMapper.selectCertificate();
			List<SGCertificate> subCertificates = null;
			offset = 0;
			if (sgCertificates != null && sgCertificates.size() > 0){
				size = sgCertificates.size();
				while (offset + 10000 < size) {
					subCertificates = sgCertificates.subList(offset, offset + 10000);
					count = clzDataMapper.insertCertificate(subCertificates);
					log.info("######################## insertCertificate : have inserted 10000 certificates info.");
					subCertificates.clear();
					offset+=10000;
				}
				if (offset < size ){
					subCertificates = sgCertificates.subList(offset, size);
					count = clzDataMapper.insertCertificate(subCertificates);
					log.info("######################## insertCertificate : have inserted " + subCertificates.size() + " certificates info.");
					subCertificates.clear();
				}
				log.info("######################## insertCertificate : have totally inserted " + sgCertificates.size() + " certificates info.");
				sgCertificates.clear();
			}
			
			List<SGOfficial> sgOfficials = sgDataMapper.selectOfficial();
			List<SGOfficial> subOfficials = null;
			offset = 0;
			if (sgOfficials != null && sgOfficials.size() > 0){
				size = sgOfficials.size();
				while (offset + 10000 < size) {
					subOfficials = sgOfficials.subList(offset, offset + 10000);
					count = clzDataMapper.insertOfficial(subOfficials);
					log.info("######################## insertOfficial : have inserted 10000 officials info.");
					subOfficials.clear();
					offset+=10000;
				}
				if (offset < size ){
					subOfficials = sgOfficials.subList(offset, size);
					count = clzDataMapper.insertOfficial(subOfficials);
					log.info("######################## insertOfficial : have inserted " + subOfficials.size() + " officials info.");
					subOfficials.clear();
				}
				log.info("######################## insertOfficial : have totally inserted " + sgOfficials.size() + " officials info.");
				sgOfficials.clear();
			}
			
			//更新jx_progress的进度状态为喱喱的进度状态
			clzDataMapper.updateProgressState();
			log.info("######################## updateProgressState : set tempTable progress state compeleted");
			//把jx_progress的进度对应学员的studentId设置进来
			clzDataMapper.updateTempStudentIdByIdNumber();
			clzDataMapper.updateTempStudentIdByPhone();
			log.info("######################## updateTempStudentId : set tempTable studentId compeleted");
			//取出喱喱库中每一个喱喱学员进度的最后更新时间
			clzDataMapper.updateLastUpdateTime();
			log.info("######################## updateLastUpdateTime : set last updated time compeleted");
			//取出temp的学员进度数据，转换，并插入到喱喱进度表
			List<EnrollProgressTemplate> epts = clzDataMapper.selectProgressTemplate();
			Map<Integer, EnrollProgressTemplate> mapTemplate = new HashMap<>();
			for (EnrollProgressTemplate ept : epts)
				mapTemplate.put(ept.getStepId(), ept);
			List<SGOfficial> progressList = clzDataMapper.selectNeedInsertProgress();
			//该状态的描述
			String doc = "";
			String docparsed = "";
			//该阶段是否还需要显示
			Byte needDisplay = null;
			//当前需要处理记录的进度状态
			int applyexam;
			int applystate;
			//当前进度的模版
			EnrollProgressTemplate ept;
			//模版可能用到的参数
			Map<Integer, String> params = new HashMap<>();
			//格式化时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//分别保存插入和更新数据的List
			List<EnrollProgressUser> insertProgress = new ArrayList<>();
			List<EnrollProgressUser> updateProgress = new ArrayList<>();
			count = 0;
			for (SGOfficial progress : progressList){
				params.clear();
				applyexam = progress.getApplyexam();
				applystate = progress.getApplystate();
				ept = mapTemplate.get(applyexam);
				if (applystate == 0){
					doc = ept.getNoneDoc();
					needDisplay = ept.getNoneRec();
				} else if (applystate == 1) {
					doc = ept.getSubmitDoc();
					needDisplay = ept.getSubmitRec();
					if (applyexam == 101) {
						params.put(1, sdf.format(progress.getDatatime()));
						params.put(2, progress.getPlace());
					} else if (applyexam == 501) {
						doc = "您已成功预约长考，考试日期为" + sdf.format(progress.getDatatime());
					}
				} else if (applystate == 100){
					doc = ept.getSuccDoc();
					needDisplay = ept.getSuccRec();
					if (applyexam == 6) {
						params.put(1, progress.getSchool());
						if (progress.getDriveType() == null) progress.setDriveType("C1");//兼容极少数的因为数据问题导致找不到学员驾驶类型的情况
						params.put(2, progress.getDriveType());
						params.put(3, progress.getState());
					} else if (applyexam == 401 || applyexam == 601) {
						params.put(1, sdf.format(progress.getDatatime()));
						params.put(2, progress.getPlace());
					} else if (applyexam == 301 || applyexam == 701){
						params.put(1, sdf.format(progress.getDatatime()));
						doc = doc.replaceAll("考试场地为\\{2\\}，", "");
					} else {
						params.put(1, progress.getDatatime()==null?"":sdf.format(progress.getDatatime()));
					}
				} else if (applystate == 101) {
					doc = ept.getFailDoc();
					needDisplay = ept.getFailRec();
					params.put(1, progress.getDatatime()==null?"":sdf.format(progress.getDatatime()));
				} else {
					continue;
				}
				docparsed = parseDocParams(doc, params);
				if (applyexam == progress.getStep_id() && progress.getStep_state() != 101 && progress.getStep_state() != 100){
					EnrollProgressUser epu = new EnrollProgressUser();
					epu.setResult(docparsed);
					epu.setNeedShow(needDisplay);
					epu.setProcessState((byte)applystate);
					epu.setCpid(progress.getCpid());
					updateProgress.add(epu);
					epu = null;
				} else {
					EnrollProgressUser epu = new EnrollProgressUser();
					BeanCopy.copyNotNull(ept, epu);
					epu.setStudentId(progress.getStudent_id());
					epu.setResult(docparsed);
					epu.setNeedShow(needDisplay);
					epu.setProcessState((byte)applystate);
					epu.setCpid(null);
					epu.setCtime(new Date());
					epu.setSchoolId(progress.getSchoolId());
					epu.setCuid(0L);
					insertProgress.add(epu);
					//System.out.println(count + ":" + epu.getResult() + " " + epu.getStudentId());
					count++;
					epu = null;
				}
			}
			//更新进度
			if (!updateProgress.isEmpty())
				clzDataMapper.updateProgress(updateProgress);
			log.info("######################## updateProgress : have updated " + updateProgress.size() + " CLZ student progress info");
			//插入进度
			if (!insertProgress.isEmpty())
				clzDataMapper.insertProgress(insertProgress);
			log.info("######################## insertProgress : have inserted " + insertProgress.size() + " CLZ student progress info");
			//更新temp表的学员最新学车状态
			count = clzDataMapper.updateCLZTempStudentState();
			log.info("######################## updateCLZTempStudentState : have updated " + count + " CLZ student temptable state info");
			//更新喱喱学员
			count = clzDataMapper.updateCLZStudent();
			log.info("######################## updateCLZStudent : have updated " + count + " CLZ student info in our DB.");
//			//清空temp table
//			clzDataMapper.deleteTempTable();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("######################## syncStudentData Exception : " + e.getMessage());
		}
	}
	
	private String parseDocParams(String doc, Map<Integer, String> params) {
		if(null == doc || !doc.contains("{") || null == params || params.size() == 0){
			return doc;
		}
		try {
			for(Integer s:params.keySet()){
				doc = doc.replaceAll("\\{".concat(s.toString()).concat("\\}"), params.get(s));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return doc;
		}
		return doc;
	}
	
	public void syncCSRelations(){
		try {
			int count = 0;
			count = clzDataMapper.deleteCS();
			log.info("######################## deleteCS : delete from t_u_mycoaches " + count + " CSRelation data");
			count = clzDataMapper.insertCS();
			log.info("######################## insertCS : insert into t_u_mycoaches " + count + " CSRelation data");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("######################## syncCSRelations Exception : " + e.getMessage());
		}
	}
	
	public void syncCarData(){
		try {
			int count = 0;
			List<SGCar> sgCars = sgDataMapper.selectCar();
			if (sgCars != null && sgCars.size() > 0){
				count = clzDataMapper.updateCar(sgCars);
				log.info("######################## updateCar : update " + count + " car info");
				count = clzDataMapper.insertCar(sgCars);
				log.info("######################## insertCar : insert " + count + " car info");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("######################## syncCarData Exception : " + e.getMessage());
		}
	}
	
	public void syncCCRelations(){
		try {
			int count = 0;
			count = clzDataMapper.deleteCC();
			log.info("######################## deleteCC : delete from t_u_coachcar " + count + " CCRelation data");
			count = clzDataMapper.insertCC();
			log.info("######################## insertCC : insert into t_u_coachcar " + count + " CCRelation data");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("######################## syncCCRelations Exception : " + e.getMessage());
		}
	}
	
//	@Test
//	public void test(){
//		Date start = new Date();
//		int count = 0;
//		int size = 0, offset = 0, jxTotal = 0;
//		size = sgDataMapper.selectJXStudentTotal();
//		if (size > 0) {
//			int pageSize = 10000;
//			while (offset + pageSize < size) {
//				List<SGStudent> sgStudents = sgDataMapper.selectJXStudentLimit(offset, pageSize);
//				if (sgStudents != null && sgStudents.size() > 0) {
//					clzDataMapper.insertTempStudent(sgStudents);
//					jxTotal += sgStudents.size();
//					log.info("######################## insertTempStudent : have inserted " + sgStudents.size() + " students info.");
//					sgStudents.clear();
//				}
//				offset += pageSize;
//			}
//			if (offset < size) {
//				List<SGStudent> sgStudents = sgDataMapper.selectJXStudentLimit(offset, size + 1);
//				if (sgStudents != null && sgStudents.size() > 0) {
//					clzDataMapper.insertTempStudent(sgStudents);
//					jxTotal += sgStudents.size();
//					log.info("######################## insertTempStudent : have inserted " + sgStudents.size() + " students info.");
//					sgStudents.clear();
//				}
//			}
//			log.info("######################## insertTempStudent : have totally inserted " + jxTotal + " students info.");
//		}
//		Date end = new Date();
//		Long cost = end.getTime() - start.getTime();
//		log.info("start : " + start + "\n" + "end : " + end + "\n" + "cost : " + cost);
//	}
	
	public void syncTask(){
		Date start = new Date();
		syncCoachData();
		syncStudentData();
		syncCSRelations();
		syncCarData();
		syncCCRelations();
		Date end = new Date();
		Long cost = end.getTime() - start.getTime();
		log.info("start : " + start + "\n" + "end : " + end + "\n" + "cost : " + cost);
	}
}
