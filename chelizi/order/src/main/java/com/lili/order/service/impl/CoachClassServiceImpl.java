package com.lili.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.coach.dto.Car;
import com.lili.coach.dto.Coach;
import com.lili.coach.dto.Trfield;
import com.lili.coach.manager.CarManager;
import com.lili.coach.manager.CoachManager;
import com.lili.coach.manager.TrfieldManager;
import com.lili.common.constant.OrderConstant;
import com.lili.common.constant.RedisExpireConstant;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.DateUtil;
import com.lili.common.util.StringUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.util.VersionUtil;
import com.lili.common.util.redis.RedisKeys;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ResultCode;
import com.lili.file.manager.FileManager;
import com.lili.file.vo.CoursenewVo;
import com.lili.order.dao.mapper.CoachClassMapper;
import com.lili.order.dao.mapper.CoachClassPriceMapper;
import com.lili.order.dao.po.CoachClassPo;
import com.lili.order.dao.po.CoachClassPricePo;
import com.lili.order.service.CoachClassService;
import com.lili.order.service.OrderService;
import com.lili.order.service.PlantClassService;
import com.lili.order.vo.CoachClassPriceVo;
import com.lili.order.vo.CoachClassQuery;
import com.lili.order.vo.CoachClassVo;
import com.lili.order.vo.OrderQuery;
import com.lili.order.vo.OrderVo;
import com.lili.order.vo.PlantClassQuery;
import com.lili.order.vo.PlantClassVo;
import com.lili.student.dto.Student;
import com.lili.student.vo.MycoachesVo;

public class CoachClassServiceImpl implements CoachClassService {
	private Logger log = Logger.getLogger(CoachClassService.class);
	
	@Autowired
	CoachClassMapper coachclassMapper;
	@Autowired
	RedisUtil redisUtil;
	@Autowired 
	private PlantClassService  plantClassService;
	@Autowired
	private TrfieldManager trfieldManager;
	@Autowired
	private CoachManager coachManager;
	@Autowired
	private CarManager carManager;
	@Autowired
	private FileManager fileManager;
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CoachClassPriceMapper coachClassPriceMapper;
	
	public void addCoachClass(CoachClassVo coachclassVo) throws Exception {
		CoachClassPo po = BeanCopy.copyAll(coachclassVo, CoachClassPo.class);
		coachclassMapper.addCoachClass(po);
		delCach(coachclassVo);
	}
	private void delCach(CoachClassVo one){
		// 更新缓存
		try {
			if(one.getCstart() == null ){
				CoachClassVo vo=this.queryCoachClassById(one.getCcid(), new CoachClassQuery());
				BeanCopy.copy2Null(vo, one);
			}	
			String justdate = TimeUtil.getDateFormat(one.getCstart(),
					"yyyy-MM-dd");
			String key = this.getClass().getSimpleName() + "_" + justdate + "_"
					+ one.getCoachId() + "_" + OrderConstant.ISDEL.NOTDELETE + "_"
					+ one.getCtype();
			redisUtil.delete(key);
			key = this.getClass().getSimpleName() + "_" + justdate + "_"
					+ one.getCoachId() + "_" + null + "_"
					+ one.getCtype();
			redisUtil.delete(key);
			key = this.getClass().getSimpleName() + "_" + justdate + "_"
					
					+ one.getCoachId() + "_" + OrderConstant.ISDEL.NOTDELETE + "_"
					+ null;
			redisUtil.delete(key);
			key = this.getClass().getSimpleName() + "_" + justdate + "_"
					+ one.getCoachId() + "_" + null + "_"
					+ null;
			redisUtil.delete(key);
		} catch (Exception e) {
			log.error(one+" delCach exception:"+e.getMessage(),e);
		}
	}
	private void delCach(List<CoachClassVo> list){
			for(CoachClassVo one:list) {
				delCach(one);
			}
	}
	public void addCoachClassList(List<CoachClassVo> coachclassVoList) throws Exception {
		List<CoachClassPo> poList=BeanCopy.copyList(coachclassVoList,CoachClassPo.class,BeanCopy.COPYALL);
		coachclassMapper.addCoachClassList(poList);
		delCach(coachclassVoList);
	}
	public void delCoachClassById(Integer ccid) throws Exception {
		coachclassMapper.delCoachClassById(ccid);
	}
	public void delCoachClassByIds(List<Integer> ids) throws Exception {
		coachclassMapper.delCoachClassByIds(ids);
	}
	public void delCoachClassByObj(CoachClassVo coachclassVo) throws Exception {
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		coachclassMapper.delCoachClassByObj(po);
	}
	public void saveCoachClass(CoachClassVo coachclassVo) throws Exception {
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		if(po.getCcid()==null) {
			coachclassMapper.saveCoachClass(po);
		} else {
			coachclassMapper.updateByCcid(po,po.getCcid());
		}
		delCach(coachclassVo);
	}
	public void saveCoachClassList(List<CoachClassVo> coachclassVoList) throws Exception {
		List<CoachClassPo> poList=BeanCopy.copyList(coachclassVoList,CoachClassPo.class,BeanCopy.COPYALL);
		coachclassMapper.saveCoachClassList(poList);
		delCach(coachclassVoList);
	}
	public int updateByCcid(CoachClassVo coachclassVo,Integer ccid) throws Exception {
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		delCach(coachclassVo);
		return coachclassMapper.updateByCcid(po,ccid);
	}
	public int updateByCtype(CoachClassVo coachclassVo,Integer ctype) throws Exception {
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		return coachclassMapper.updateByCtype(po,ctype);
	}
	public int updateByCoachId(CoachClassVo coachclassVo,Long coachId) throws Exception {
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		return coachclassMapper.updateByCoachId(po,coachId);
	}
	public int updateByCstart(CoachClassVo coachclassVo,Date cstart) throws Exception {
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		return coachclassMapper.updateByCstart(po,cstart);
	}
	public int updateByCend(CoachClassVo coachclassVo,Date cend) throws Exception {
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		return coachclassMapper.updateByCend(po,cend);
	}
	public int updateByRstart(CoachClassVo coachclassVo,Date rstart) throws Exception {
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		return coachclassMapper.updateByRstart(po,rstart);
	}
	public int updateByRend(CoachClassVo coachclassVo,Date rend) throws Exception {
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		return coachclassMapper.updateByRend(po,rend);
	}
	public int updateByOrderId(CoachClassVo coachclassVo,String orderId) throws Exception {
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		return coachclassMapper.updateByOrderId(po,orderId);
	}
	public int updateByMaxNum(CoachClassVo coachclassVo,Integer maxNum) throws Exception {
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		return coachclassMapper.updateByMaxNum(po,maxNum);
	}
	public int updateByBookNum(CoachClassVo coachclassVo,Integer bookNum) throws Exception {
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		return coachclassMapper.updateByBookNum(po,bookNum);
	}
	public int updateByCarId(CoachClassVo coachclassVo,Integer carId) throws Exception {
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		return coachclassMapper.updateByCarId(po,carId);
	}
	public int updateByCarName(CoachClassVo coachclassVo,String carName) throws Exception {
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		return coachclassMapper.updateByCarName(po,carName);
	}
	public int updateByCarImg(CoachClassVo coachclassVo,String carImg) throws Exception {
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		return coachclassMapper.updateByCarImg(po,carImg);
	}
	public int updateByCarNo(CoachClassVo coachclassVo,String carNo) throws Exception {
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		return coachclassMapper.updateByCarNo(po,carNo);
	}
	public int updateByCourseId(CoachClassVo coachclassVo,String courseId) throws Exception {
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		return coachclassMapper.updateByCourseId(po,courseId);
	}
	public int updateByCourseName(CoachClassVo coachclassVo,String courseName) throws Exception {
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		return coachclassMapper.updateByCourseName(po,courseName);
	}
	public int updateByPlaceId(CoachClassVo coachclassVo,Integer placeId) throws Exception {
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		return coachclassMapper.updateByPlaceId(po,placeId);
	}
	public int updateByPlaceName(CoachClassVo coachclassVo,String placeName) throws Exception {
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		return coachclassMapper.updateByPlaceName(po,placeName);
	}
	public int updateByPrice(CoachClassVo coachclassVo,Integer price) throws Exception {
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		return coachclassMapper.updateByPrice(po,price);
	}
	public int updateByDltype(CoachClassVo coachclassVo,Integer dltype) throws Exception {
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		return coachclassMapper.updateByDltype(po,dltype);
	}
	public int updateByTid(CoachClassVo coachclassVo,Integer tid) throws Exception {
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		return coachclassMapper.updateByTid(po,tid);
	}
	public int updateByIsdel(CoachClassVo coachclassVo,Integer isdel) throws Exception {
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		return coachclassMapper.updateByIsdel(po,isdel);
	}
	public int updateNotNullByObject(CoachClassVo coachclassVo,CoachClassVo search) throws Exception {
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		CoachClassPo searchPo=BeanCopy.copyAll(search,CoachClassPo.class);
		return coachclassMapper.updateNotNullByObject(po,searchPo);
	}
	public int updateAllByObject(CoachClassVo coachclassVo,CoachClassVo search) throws Exception {
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		CoachClassPo searchPo=BeanCopy.copyAll(search,CoachClassPo.class);
		return coachclassMapper.updateAllByObject(po,searchPo);
	}
	public CoachClassVo queryCoachClassById(Integer ccid,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachClassPo po=coachclassMapper.queryCoachClassById(ccid,postSql,sqlFileld);
		CoachClassVo vo=null;
		if(po!=null){
			vo=BeanCopy.copyAll(po,CoachClassVo.class);
		}
		return vo;
	}
	public List<CoachClassVo> queryCoachClassByIds(List<Integer> ids,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachClassPo> poList=coachclassMapper.queryCoachClassByIds(ids,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachClassVo> queryByObjectAnd(CoachClassVo coachclassVo,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		List<CoachClassPo> poList=coachclassMapper.queryByObjectAnd(po,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachClassVo> queryByObjectOr(CoachClassVo coachclassVo,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		List<CoachClassPo> poList=coachclassMapper.queryByObjectOr(po,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachClassVo> queryByCcid(Integer ccid,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachClassPo> poList=coachclassMapper.queryByCcid(ccid,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachClassVo> queryByCtype(Integer ctype,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachClassPo> poList=coachclassMapper.queryByCtype(ctype,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachClassVo> queryByCoachId(Long coachId,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachClassPo> poList=coachclassMapper.queryByCoachId(coachId,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachClassVo> queryByCstart(Date cstart,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachClassPo> poList=coachclassMapper.queryByCstart(cstart,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachClassVo> queryByCend(Date cend,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachClassPo> poList=coachclassMapper.queryByCend(cend,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachClassVo> queryByRstart(Date rstart,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachClassPo> poList=coachclassMapper.queryByRstart(rstart,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachClassVo> queryByRend(Date rend,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachClassPo> poList=coachclassMapper.queryByRend(rend,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachClassVo> queryByOrderId(String orderId,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachClassPo> poList=coachclassMapper.queryByOrderId(orderId,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachClassVo> queryByMaxNum(Integer maxNum,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachClassPo> poList=coachclassMapper.queryByMaxNum(maxNum,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachClassVo> queryByBookNum(Integer bookNum,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachClassPo> poList=coachclassMapper.queryByBookNum(bookNum,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachClassVo> queryByCarId(Integer carId,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachClassPo> poList=coachclassMapper.queryByCarId(carId,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachClassVo> queryByCarName(String carName,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachClassPo> poList=coachclassMapper.queryByCarName(carName,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachClassVo> queryByCarImg(String carImg,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachClassPo> poList=coachclassMapper.queryByCarImg(carImg,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachClassVo> queryByCarNo(String carNo,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachClassPo> poList=coachclassMapper.queryByCarNo(carNo,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachClassVo> queryByCourseId(String courseId,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachClassPo> poList=coachclassMapper.queryByCourseId(courseId,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachClassVo> queryByCourseName(String courseName,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachClassPo> poList=coachclassMapper.queryByCourseName(courseName,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachClassVo> queryByPlaceId(Integer placeId,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachClassPo> poList=coachclassMapper.queryByPlaceId(placeId,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachClassVo> queryByPlaceName(String placeName,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachClassPo> poList=coachclassMapper.queryByPlaceName(placeName,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachClassVo> queryByPrice(Integer price,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachClassPo> poList=coachclassMapper.queryByPrice(price,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachClassVo> queryByDltype(Integer dltype,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachClassPo> poList=coachclassMapper.queryByDltype(dltype,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachClassVo> queryByTid(Integer tid,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachClassPo> poList=coachclassMapper.queryByTid(tid,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachClassVo> queryByIsdel(Integer isdel,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<CoachClassPo> poList=coachclassMapper.queryByIsdel(isdel,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachClassVo> queryBetween(CoachClassVo coachclassVo,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		List<CoachClassPo> poList=coachclassMapper.queryByNew0(po,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	/**
	 * 查询指定日期的教练排班信息, 此方法一般是用来判断时间冲突
	 * @param coachId
	 * @param date：指定日期格式yyyy-MM-dd
	 * @param isDel：查所有请传null，查有效请传0
	 * @param ctype:班次类别，不传查所有类型
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<CoachClassVo> queryByCoachDateWithNoPrice(Date date,Long coachId,Integer isDel,Integer ctype,Integer pageIndex,Integer pageSize) throws Exception {
		String justdate=TimeUtil.getDateFormat(date, "yyyy-MM-dd");
		String key=this.getClass().getSimpleName()+"_"+justdate+"_"+coachId+"_"+isDel+"_"+ctype;
		Date now=new Date();
		String nowdate=TimeUtil.getDateFormat(now, "yyyy-MM-dd");
		
		List<CoachClassVo> voList=null;
		if(pageIndex==null || pageSize==null){
			voList=redisUtil.get(key);
		}
		if(voList==null){
			CoachClassQuery coachclassQuery=new CoachClassQuery();
			coachclassQuery.setorderBy("order by cstart ASC");
			if(pageIndex!=null){
				coachclassQuery.setPageIndex(pageIndex);
				coachclassQuery.setPageSize(pageSize);
			} else {
				coachclassQuery.setPageIndex(1);
				coachclassQuery.setPageSize(80);
			}
			String postSql=coachclassQuery.getSqlPost();
			String sqlFileld=coachclassQuery.getSqlField();
			CoachClassPo po=new CoachClassPo();
			po.setCoachId(coachId);
			if(ctype!=null){
				po.setCtype(ctype);
			}
			if(StringUtil.isNotNullAndNotEmpty(justdate)) {
				po.setCstart(TimeUtil.parseDate(justdate+" 00:00:00"));
				
//				if(nowdate.equals(justdate)){//今天
//					po.setCstart(now);
//				}
				
				po.setCend(TimeUtil.parseDate(justdate+" 23:59:59"));
			}
			if(isDel==null){
				po.setIsdel(null);
			} else {
				po.setIsdel(Integer.valueOf(isDel));
			}
			List<CoachClassPo> poList=coachclassMapper.queryByNew1(po,postSql,sqlFileld);
			BeanCopy.setListField(poList, "price", null);//驾校学员预约不需要付费，奖金额置为空
			if(poList!=null && !poList.isEmpty()){
				voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
				if(pageIndex==null|| pageSize==null ){
					//redisUtil.set(key, voList, RedisExpireConstant.ORDERTIME.COACHCLASS);
					redisUtil.set(key, voList, 60*5);
				}
			} else {
				voList=new ArrayList<CoachClassVo>();
			}
		}
		return voList;
	}
	
	/**
	 * 获取教练所有有价格的排班（教练自己的排班），返回的数据有price
	 * @param date
	 * @param coachId
	 * @param isDel
	 * @param ctype
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<CoachClassVo> queryByCoachDateWithPrice(Date date,Long coachId,Integer isDel,Integer ctype,Integer pageIndex,Integer pageSize) throws Exception {
		String justdate=TimeUtil.getDateFormat(date, "yyyy-MM-dd");
		String key=this.getClass().getSimpleName()+"_"+justdate+"_"+coachId+"_"+isDel+"_"+ctype;
		Date now=new Date();
		String nowdate=TimeUtil.getDateFormat(now, "yyyy-MM-dd");
		
		List<CoachClassVo> voList=null;
//		if(pageIndex==null || pageSize==null){
//			voList=redisUtil.get(key);
//		}
		if(voList==null){
			CoachClassQuery coachclassQuery=new CoachClassQuery();
			coachclassQuery.setorderBy("order by cstart ASC");
			if(pageIndex!=null){
				coachclassQuery.setPageIndex(pageIndex);
				coachclassQuery.setPageSize(pageSize);
			} else {
				coachclassQuery.setPageIndex(1);
				coachclassQuery.setPageSize(80);
			}
			String postSql=coachclassQuery.getSqlPost();
			String sqlFileld=coachclassQuery.getSqlField();
			CoachClassPo po=new CoachClassPo();
			po.setCoachId(coachId);
			if(ctype!=null){
				po.setCtype(ctype);
			}
			if(StringUtil.isNotNullAndNotEmpty(justdate)) {
				po.setCstart(TimeUtil.parseDate(justdate+" 00:00:00"));
				
				if(nowdate.equals(justdate)){//今天
					po.setCstart(now);
				}
				
				po.setCend(TimeUtil.parseDate(justdate+" 23:59:59"));
			}
			if(isDel==null){
				po.setIsdel(null);
			} else {
				po.setIsdel(Integer.valueOf(isDel));
			}
			List<CoachClassPo> poList=coachclassMapper.queryByNew8(po,postSql,sqlFileld);
			
			if(poList!=null && !poList.isEmpty()){
				voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
				if(pageIndex==null|| pageSize==null ){
					//24小时太久额，改成5分钟
					//redisUtil.set(key, voList, RedisExpireConstant.ORDERTIME.COACHCLASS);
					//redisUtil.set(key, voList, 60*5);
				}
			} else {
				voList=new ArrayList<CoachClassVo>();
			}
		}
		return voList;
	}
	
	
	public List<CoachClassVo> queryByCoachDateWithPrice(Date date,List<Long> coachIdes,Integer isDel,Integer ctype,Integer pageIndex,Integer pageSize) throws Exception {
		String justdate=TimeUtil.getDateFormat(date, "yyyy-MM-dd");
		
		Date now=new Date();
		String nowdate=TimeUtil.getDateFormat(now, "yyyy-MM-dd");
		List<CoachClassVo> result=new ArrayList();
		List<CoachClassVo> voList=null;
//		if(pageIndex==null || pageSize==null){
//			voList=redisUtil.get(key);
//		}
		List<Long> left=new ArrayList();
		if(voList==null){
			CoachClassQuery coachclassQuery=new CoachClassQuery();
			
			String sql=" and coach_id in ( ";
			for(int i=0;i<coachIdes.size();i++){
				String key=this.getClass().getSimpleName()+"_"+justdate+"_"+coachIdes.get(i)+"_"+isDel+"_"+ctype+"_withprice";
				
				List<CoachClassVo> vlist=redisUtil.get(key);
				
				if(vlist==null){
					sql=sql+coachIdes.get(i);
					if(i<coachIdes.size()-1) sql=sql+",";
					left.add(coachIdes.get(i));
				}else{
					result.addAll(vlist);
				}
			}
			if(sql.endsWith(",")){
				sql=sql.substring(0,sql.length()-1);
			}
			sql=sql+" )";
			if(left.size()>0){
				coachclassQuery.setGroupBy(sql);
				coachclassQuery.setorderBy("order by coach_id asc, cstart ASC");
				if(pageIndex!=null){
					coachclassQuery.setPageIndex(pageIndex);
					coachclassQuery.setPageSize(pageSize);
				} else {
					coachclassQuery.setPageIndex(1);
					coachclassQuery.setPageSize(80);
				}
				String postSql=coachclassQuery.getSqlPost();
				String sqlFileld=coachclassQuery.getSqlField();
				CoachClassPo po=new CoachClassPo();
				
				if(ctype!=null){
					po.setCtype(ctype);
				}
				if(StringUtil.isNotNullAndNotEmpty(justdate)) {
					po.setCstart(TimeUtil.parseDate(justdate+" 00:00:00"));
					
					if(nowdate.equals(justdate)){//今天
						po.setCstart(now);
					}
					
					po.setCend(TimeUtil.parseDate(justdate+" 23:59:59"));
				}
				if(isDel==null){
					po.setIsdel(null);
				} else {
					po.setIsdel(Integer.valueOf(isDel));
				}
				List<CoachClassPo> poList=coachclassMapper.queryByNew8(po,postSql,sqlFileld);
				
				if(poList!=null && !poList.isEmpty()){
					voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
					result.addAll(voList);
					if(pageIndex==null|| pageSize==null ){
						//24小时太久额，改成5分钟
						//redisUtil.set(key, voList, RedisExpireConstant.ORDERTIME.COACHCLASS);
						//redisUtil.set(key, voList, 60*5);
					}
				} else {
					voList=new ArrayList<CoachClassVo>();
				}
			}
		}
		return result;
	}
	
	public List<CoachClassVo> queryByCoachDateWithPriceOrNot(Date date,List<Long> coachIdes,Integer isDel,Integer ctype,Integer pageIndex,Integer pageSize) throws Exception {
		String justdate=TimeUtil.getDateFormat(date, "yyyy-MM-dd");
		
		Date now=new Date();
		String nowdate=TimeUtil.getDateFormat(now, "yyyy-MM-dd");
		List<CoachClassVo> result=new ArrayList();
		List<CoachClassVo> voList=null;
//		if(pageIndex==null || pageSize==null){
//			voList=redisUtil.get(key);
//		}
		List<Long> left=new ArrayList();
		if(voList==null){
			CoachClassQuery coachclassQuery=new CoachClassQuery();
			
			String sql=" and coach_id in ( ";
			for(int i=0;i<coachIdes.size();i++){
				String key=this.getClass().getSimpleName()+"_"+justdate+"_"+coachIdes.get(i)+"_"+isDel+"_"+ctype+"_withprice_ornot";
				List<CoachClassVo> vlist=redisUtil.get(key);
				
				if(vlist==null){
					sql=sql+coachIdes.get(i);
					if(i<coachIdes.size()-1) sql=sql+",";
					left.add(coachIdes.get(i));
				}else{
					result.addAll(vlist);
				}
			}
			if(sql.endsWith(",")){
				sql=sql.substring(0,sql.length()-1);
			}
			sql=sql+" )";
			if(left.size()>0){
				coachclassQuery.setGroupBy(sql);
				coachclassQuery.setorderBy("order by coach_id asc, cstart ASC");
				if(pageIndex!=null){
					coachclassQuery.setPageIndex(pageIndex);
					coachclassQuery.setPageSize(pageSize);
				} else {
					coachclassQuery.setPageIndex(1);
					coachclassQuery.setPageSize(80);
				}
				String postSql=coachclassQuery.getSqlPost();
				String sqlFileld=coachclassQuery.getSqlField();
				CoachClassPo po=new CoachClassPo();
				
				if(ctype!=null){
					po.setCtype(ctype);
				}
				if(StringUtil.isNotNullAndNotEmpty(justdate)) {
					po.setCstart(TimeUtil.parseDate(justdate+" 00:00:00"));
					
					if(nowdate.equals(justdate)){//今天
						po.setCstart(now);
					}
					
					po.setCend(TimeUtil.parseDate(justdate+" 23:59:59"));
				}
				if(isDel==null){
					po.setIsdel(null);
				} else {
					po.setIsdel(Integer.valueOf(isDel));
				}
				List<CoachClassPo> poList=coachclassMapper.queryByNew1(po,postSql,sqlFileld);
				
				if(poList!=null && !poList.isEmpty()){
					voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
					result.addAll(voList);
					if(pageIndex==null|| pageSize==null ){
						//24小时太久额，改成5分钟
						//redisUtil.set(key, voList, RedisExpireConstant.ORDERTIME.COACHCLASS);
						//redisUtil.set(key, voList, 60*5);
					}
				} else {
					voList=new ArrayList<CoachClassVo>();
				}
			}
		}
		return result;
	}
	
	public List<CoachClassVo> queryByCoachDateWithPrice(Date date,Date endDate, List<Long> coachIdes,Integer isDel,Integer ctype,Integer pageIndex,Integer pageSize) throws Exception {
		String justdate=TimeUtil.getDateFormat(date, "yyyy-MM-dd");
		
		Date now=new Date();
		String nowdate=TimeUtil.getDateFormat(now, "yyyy-MM-dd");
		List<CoachClassVo> result=new ArrayList();
		List<CoachClassVo> voList=null;
//		if(pageIndex==null || pageSize==null){
//			voList=redisUtil.get(key);
//		}
		List<Long> left=new ArrayList();
		if(voList==null){
			CoachClassQuery coachclassQuery=new CoachClassQuery();
			
			String sql=" and coach_id in ( ";
			for(int i=0;i<coachIdes.size();i++){
				String key=this.getClass().getSimpleName()+"_"+justdate+"_"+coachIdes.get(i)+"_"+isDel+"_"+ctype+"_withprice";
				List<CoachClassVo> vlist=redisUtil.get(key);
				
				if(vlist==null){
					sql=sql+coachIdes.get(i);
					if(i<coachIdes.size()-1) sql=sql+",";
					left.add(coachIdes.get(i));
				}else{
					result.addAll(vlist);
				}
			}
			if(sql.endsWith(",")){
				sql=sql.substring(0,sql.length()-1);
			}
			sql=sql+" )";
			if(left.size()>0){
				coachclassQuery.setGroupBy(sql);
				coachclassQuery.setorderBy("order by coach_id asc, cstart ASC");
				if(pageIndex!=null){
					coachclassQuery.setPageIndex(pageIndex);
					coachclassQuery.setPageSize(pageSize);
				} else {
					coachclassQuery.setPageIndex(1);
					coachclassQuery.setPageSize(80);
				}
				String postSql=coachclassQuery.getSqlPost();
				String sqlFileld=coachclassQuery.getSqlField();
				CoachClassPo po=new CoachClassPo();
				
				if(ctype!=null){
					po.setCtype(ctype);
				}
				if(StringUtil.isNotNullAndNotEmpty(justdate)) {
					po.setCstart(TimeUtil.parseDate(justdate+" 00:00:00"));
					
					if(nowdate.equals(justdate)){//今天
						po.setCstart(now);
					}
					
					//po.setCend(TimeUtil.parseDate(justdate+" 23:59:59"));
					po.setCend(endDate);
				}
				if(isDel==null){
					po.setIsdel(null);
				} else {
					po.setIsdel(Integer.valueOf(isDel));
				}
				List<CoachClassPo> poList=coachclassMapper.queryByNew8(po,postSql,sqlFileld);
				
				if(poList!=null && !poList.isEmpty()){
					voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
					result.addAll(voList);
					if(pageIndex==null|| pageSize==null ){
						//24小时太久额，改成5分钟
						//redisUtil.set(key, voList, RedisExpireConstant.ORDERTIME.COACHCLASS);
						//redisUtil.set(key, voList, 60*5);
					}
				} else {
					voList=new ArrayList<CoachClassVo>();
				}
			}
		}
		return result;
	}
	
	/**
	 * 获取教练当天排班
	 * @param date
	 * @param coachId
	 * @param isDel
	 * @param ctype
	 * @param drType 驾考类型
	 * @param courseId 课程id
	 * @param subjectId 科目d
	 * @return
	 * @throws Exception
	 */
	public List<CoachClassVo> queryByCoachDate(Date date,Long coachId,Integer isDel,Integer ctype,Integer drType,Integer courseId,Integer subjectId) throws Exception {
		String justdate=TimeUtil.getDateFormat(date, "yyyy-MM-dd");
		String key=this.getClass().getSimpleName()+"_"+justdate+"_"+coachId+"_"+isDel+"_"+ctype;
		List<CoachClassVo> voList=null;
		
		if(voList==null){
			CoachClassQuery coachclassQuery=new CoachClassQuery();
			coachclassQuery.setorderBy("order by cstart ASC");
			
			String postSql=coachclassQuery.getSqlPost();
			String sqlFileld=coachclassQuery.getSqlField();
			CoachClassPo po=new CoachClassPo();
			po.setCoachId(coachId);
			if(ctype!=null){
				po.setCtype(ctype);
			}
			if(StringUtil.isNotNullAndNotEmpty(justdate)) {
				po.setCstart(TimeUtil.parseDate(justdate+" 00:00:00"));
				po.setCend(TimeUtil.parseDate(justdate+" 23:59:59"));
			}
			if(isDel==null){
				po.setIsdel(null);
			} else {
				po.setIsdel(Integer.valueOf(isDel));
			}
			
			if(drType!=null){
				po.setDltype(drType);
			}
			
			/**
			 * course_id 值参考t_s_coursenew 表里的数据
			 */
			if(subjectId!=null){//选择了科目
				if(courseId!=null){//选择了课程
//					if(subjectId==2){//科目2
//						coachclassQuery.setGroupBy("");
//					}else if(subjectId==3){//科目3
//						
//					}else if(subjectId==5){//陪驾
//						
//					}
					coachclassQuery.setGroupBy(" course_id = "+courseId);
				}else{
					if(subjectId==2){//科目2
						coachclassQuery.setGroupBy(" course_id in (1,2,6,11,12,16) ");
					}else if(subjectId==3){//科目3
						coachclassQuery.setGroupBy(" course_id in (3,4,7,13,14,17) ");
					}else if(subjectId==5){//陪驾
						coachclassQuery.setGroupBy(" course_id in (5,15) ");
					}
					
				}
			}
			
			List<CoachClassPo> poList=coachclassMapper.queryByNew1(po,postSql,sqlFileld);
			BeanCopy.setListField(poList, "price", null);//驾校学员预约不需要付费，奖金额置为空
			if(poList!=null && !poList.isEmpty()){
				voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
				
			} else {
				voList=new ArrayList<CoachClassVo>();
			}
		}
		return voList;
	}
	
	/**
	 * 获取教练所有排班，不管是否有价格
	 */
	public List<CoachClassVo> queryByCoachDateAllWithNoPrice(Date date,Long coachId,Integer isDel,Integer ctype,Integer pageIndex,Integer pageSize) throws Exception {
		List<CoachClassVo> list=this.queryByCoachDateWithNoPrice(date, coachId, isDel, ctype, pageIndex, pageSize);
//		if(list!=null && !list.isEmpty()){
//			String ccidsnull="and ccid in (";
//			String ccids=ccidsnull;
//			int i=0;
//			for(;i<list.size()-1;i++){
//				CoachClassVo one=list.get(i);
//				if(one.getIsdel()!=OrderConstant.ISDEL.DELETE && one.getBookNum()>0){
//					ccids +=one.getCcid()+",";
//				}
//			}
//			CoachClassVo one=list.get(i);
//			if(one.getIsdel()!=OrderConstant.ISDEL.DELETE && one.getBookNum()>0){
//				ccids +=one.getCcid()+")";
//			} else if(!ccids.equals(ccidsnull)){
//				if(ccids.endsWith(",")){
//					ccids =ccids.substring(0,ccids.length()-1)+")";
//				} else {
//					ccids +=")";
//				}
//			}
//			Map<Integer,CoachClassVo> map=BeanCopy.getFromList(list, "ccid");
//			if(!ccids.equals(ccidsnull)){
//				PlantClassQuery query=new PlantClassQuery();
//				query.setGroupBy(ccids+" and isdel!="+OrderConstant.ISDEL.DELETE);
//				query.setPageSize(500);
//				query.setorderBy("order by gtime desc");
//				PlantClassVo search=new PlantClassVo(); 
//				search.setIsdel(null);
//				List<PlantClassVo> pcList=plantClassService.queryByObjectAnd(search, query);
//				for(PlantClassVo pc:pcList){
//					CoachClassVo cc=map.get(pc.getCcid());
//					if( cc.getPlantClassList()==null){
//						List<PlantClassVo> mypc=new ArrayList<PlantClassVo>();
//						mypc.add(pc);
//						cc.setPlantClassList(mypc);
//					} else {
//						cc.getPlantClassList().add(pc);
//					}
//				}
//			}
//		}
		fillPlantData(list);
		return list;
	}
	
	/**
	 * 填充每个教练排班的学员排班明细记录
	 */
	@Override
	public List<CoachClassVo> fillPlantData(List<CoachClassVo> list) throws Exception{
		if(list!=null && !list.isEmpty()){
			String ccidsnull="and ccid in (";
			String ccids=ccidsnull;
			int i=0;
			for(;i<list.size()-1;i++){
				CoachClassVo one=list.get(i);
				if(one.getIsdel()!=OrderConstant.ISDEL.DELETE && one.getBookNum()>0){
					ccids +=one.getCcid()+",";
				}
			}
			CoachClassVo one=list.get(i);
			if(one.getIsdel()!=OrderConstant.ISDEL.DELETE && one.getBookNum()>0){
				ccids +=one.getCcid()+")";
			} else if(!ccids.equals(ccidsnull)){
				if(ccids.endsWith(",")){
					ccids =ccids.substring(0,ccids.length()-1)+")";
				} else {
					ccids +=")";
				}
			}
			Map<Integer,CoachClassVo> map=BeanCopy.getFromList(list, "ccid");
			if(!ccids.equals(ccidsnull)){
				PlantClassQuery query=new PlantClassQuery();
				query.setGroupBy(ccids+" and isdel!="+OrderConstant.ISDEL.DELETE);
				query.setPageSize(500);
				query.setorderBy("order by gtime desc");
				PlantClassVo search=new PlantClassVo(); 
				search.setIsdel(null);
				List<PlantClassVo> pcList=plantClassService.queryByObjectAnd(search, query);
				for(PlantClassVo pc:pcList){
					CoachClassVo cc=map.get(pc.getCcid());
					if( cc.getPlantClassList()==null){
						List<PlantClassVo> mypc=new ArrayList<PlantClassVo>();
						mypc.add(pc);
						cc.setPlantClassList(mypc);
					} else {
						cc.getPlantClassList().add(pc);
					}
				}
			}
		}
		return list;
	}
	
	@Deprecated
	public List<CoachClassVo> queryBookClassByCoachDate(String date,Long coachId,Integer isDel,int pageIndex,int pageSize) throws Exception {
		return queryByCoachDateWithNoPrice(TimeUtil.parseDate(date),coachId,isDel,OrderConstant.OTYPE.BOOKORDER,pageIndex,pageSize);
	}
	
	/**
	 * 获取教练所有排班，不管是否有价格
	 */
	public Map<Long,List<CoachClassVo>> queryByCoachDateWitNoPrice(Date date,List<Long> coachIds) throws Exception{
		Map<Long,List<CoachClassVo>> result=new HashMap<Long,List<CoachClassVo>>();
		for(Long id:coachIds){
			List<CoachClassVo> list=queryByCoachDateWithNoPrice(date,id,OrderConstant.ISDEL.NOTDELETE,null,null,null);
			if(list!=null && !list.isEmpty()) {
				result.put(id, list);
			}
		}
		return result;
	}
	
	@Override
	public Map<Long,List<CoachClassVo>> queryByCoachDateWithPrice(Date date,List<Long> coachIds) throws Exception{
		Map<Long,List<CoachClassVo>> result=new HashMap<Long,List<CoachClassVo>>();
//		for(Long id:coachIds){
//			List<CoachClassVo> list=queryByCoachDateWithPrice(date,id,OrderConstant.ISDEL.NOTDELETE,null,null,null);
//			if(list!=null && !list.isEmpty()) {
//				result.put(id, list);
//			}
//		}
//		return result;
		
		if(coachIds==null||coachIds.size()==0){
			return result;
		}
		
		List<CoachClassVo> ccvs=this.queryByCoachDateWithPrice(date, coachIds, OrderConstant.ISDEL.NOTDELETE, null, null, null);
		for(CoachClassVo vo:ccvs){
			if(!result.containsKey(vo.getCoachId())){
				List<CoachClassVo> list=new ArrayList();
				list.add(vo);
				result.put(vo.getCoachId(), list);
			}else{
				result.get(vo.getCoachId()).add(vo);
			}
		}
		for(Long coachid:coachIds){
			if(!result.containsKey(coachid)){//排版为空的教练
				result.put(coachid, new ArrayList());
			}
		}
		String justdate=TimeUtil.getDateFormat(date, "yyyy-MM-dd");
		java.util.Iterator<Long> it=result.keySet().iterator();
		while(it.hasNext()){
			Long coachid=it.next();
			String key=this.getClass().getSimpleName()+"_"+justdate+"_"+coachid+"_"+OrderConstant.ISDEL.NOTDELETE+"_"+null+"_withprice";
			if(redisUtil.get(key)==null)
			redisUtil.set(key, result.get(coachid), 3600);
		}
		return result;
	}
	
	@Override
	public Map<Long,List<CoachClassVo>> queryByCoachDateWithPriceOrNot(Date date,List<Long> coachIds) throws Exception{
		Map<Long,List<CoachClassVo>> result=new HashMap<Long,List<CoachClassVo>>();
		
		if(coachIds==null||coachIds.size()==0){
			return result;
		}
		
		List<CoachClassVo> ccvs=this.queryByCoachDateWithPriceOrNot(date, coachIds, OrderConstant.ISDEL.NOTDELETE, null, null, null);
		for(CoachClassVo vo:ccvs){
			if(!result.containsKey(vo.getCoachId())){
				List<CoachClassVo> list=new ArrayList();
				list.add(vo);
				result.put(vo.getCoachId(), list);
			}else{
				result.get(vo.getCoachId()).add(vo);
			}
		}
		for(Long coachid:coachIds){
			if(!result.containsKey(coachid)){//排版为空的教练
				result.put(coachid, new ArrayList());
			}
		}
		String justdate=TimeUtil.getDateFormat(date, "yyyy-MM-dd");
		java.util.Iterator<Long> it=result.keySet().iterator();
		while(it.hasNext()){
			Long coachid=it.next();
			String key=this.getClass().getSimpleName()+"_"+justdate+"_"+coachid+"_"+OrderConstant.ISDEL.NOTDELETE+"_"+null+"_withprice_ornot";
			if(redisUtil.get(key)==null)
			redisUtil.set(key, result.get(coachid), 60);
		}
		return result;
	}
	
	@Override
	public Map<Long,List<CoachClassVo>> queryByCoachDateWithPrice(Date date,Date endDate,List<Long> coachIds) throws Exception{
		Map<Long,List<CoachClassVo>> result=new HashMap<Long,List<CoachClassVo>>();
//		for(Long id:coachIds){
//			List<CoachClassVo> list=queryByCoachDateWithPrice(date,id,OrderConstant.ISDEL.NOTDELETE,null,null,null);
//			if(list!=null && !list.isEmpty()) {
//				result.put(id, list);
//			}
//		}
//		return result;
		
		if(coachIds==null||coachIds.size()==0){
			return result;
		}
		
		List<CoachClassVo> ccvs=this.queryByCoachDateWithPrice(date, endDate,coachIds, OrderConstant.ISDEL.NOTDELETE, null, null, null);
		for(CoachClassVo vo:ccvs){
			if(!result.containsKey(vo.getCoachId())){
				List<CoachClassVo> list=new ArrayList();
				list.add(vo);
				result.put(vo.getCoachId(), list);
			}else{
				result.get(vo.getCoachId()).add(vo);
			}
		}
		for(Long coachid:coachIds){
			if(!result.containsKey(coachid)){//排版为空的教练
				result.put(coachid, new ArrayList());
			}
		}
		String justdate=TimeUtil.getDateFormat(date, "yyyy-MM-dd");
		java.util.Iterator<Long> it=result.keySet().iterator();
		while(it.hasNext()){
			Long coachid=it.next();
			String key=this.getClass().getSimpleName()+"_"+justdate+"_"+coachid+"_"+OrderConstant.ISDEL.NOTDELETE+"_"+null+"_withprice";
			if(redisUtil.get(key)==null)
			redisUtil.set(key, result.get(coachid), 60);
		}
		return result;
	}
	
	@Override
	public Map<Long,List<CoachClassVo>> queryByCoachDateWithPrice(Date date,List<Long> coachIds,Integer ctype) throws Exception{
		Map<Long,List<CoachClassVo>> result=new HashMap<Long,List<CoachClassVo>>();
//		for(Long id:coachIds){
//			List<CoachClassVo> list=queryByCoachDateWithPrice(date,id,OrderConstant.ISDEL.NOTDELETE,ctype,null,null);
//			if(list!=null && !list.isEmpty()) {
//				result.put(id, list);
//			}
//		}
		
		if(coachIds==null||coachIds.size()==0){
			return result;
		}
		
		List<CoachClassVo> ccvs=this.queryByCoachDateWithPrice(date, coachIds, OrderConstant.ISDEL.NOTDELETE, ctype, null, null);
		for(CoachClassVo vo:ccvs){
			if(!result.containsKey(vo.getCoachId())){
				List<CoachClassVo> list=new ArrayList();
				list.add(vo);
				result.put(vo.getCoachId(), list);
			}else{
				result.get(vo.getCoachId()).add(vo);
			}
		}
		for(Long coachid:coachIds){
			if(!result.containsKey(coachid)){//排版为空的教练
				result.put(coachid, new ArrayList());
			}
		}
		String justdate=TimeUtil.getDateFormat(date, "yyyy-MM-dd");
		java.util.Iterator<Long> it=result.keySet().iterator();
		while(it.hasNext()){
			Long coachid=it.next();
			String key=this.getClass().getSimpleName()+"_"+justdate+"_"+coachid+"_"+OrderConstant.ISDEL.NOTDELETE+"_"+ctype+"_withprice";
			if(redisUtil.get(key)==null)
			redisUtil.set(key, result.get(coachid), 3600*5);
		}
		
		return result;
	}
	
	@Deprecated
	@Override
	public Map<Long,List<CoachClassVo>> queryByCoachDate(Date date,List<Long> coachIds,Integer ctype) throws Exception{
		Map<Long,List<CoachClassVo>> result=new HashMap<Long,List<CoachClassVo>>();
		for(Long id:coachIds){
			List<CoachClassVo> list=queryByCoachDateWithNoPrice(date,id,OrderConstant.ISDEL.NOTDELETE,ctype,null,null);
			if(list!=null && !list.isEmpty()) {
				result.put(id, list);
			}
		}
		return result;
	}
			
	public List<CoachClassVo> queryByNew2(CoachClassVo coachclassVo,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		List<CoachClassPo> poList=coachclassMapper.queryByNew2(po,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachClassVo> queryByNew3(CoachClassVo coachclassVo,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		List<CoachClassPo> poList=coachclassMapper.queryByNew3(po,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachClassVo> queryByNew4(CoachClassVo coachclassVo,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		List<CoachClassPo> poList=coachclassMapper.queryByNew4(po,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachClassVo> queryByNew5(CoachClassVo coachclassVo,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		List<CoachClassPo> poList=coachclassMapper.queryByNew5(po,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachClassVo> queryByNew6(CoachClassVo coachclassVo,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		List<CoachClassPo> poList=coachclassMapper.queryByNew6(po,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<CoachClassVo> queryByNew7(CoachClassVo coachclassVo,CoachClassQuery coachclassQuery) throws Exception {
		String postSql=coachclassQuery.getSqlPost();
		String sqlFileld=coachclassQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		CoachClassPo po=BeanCopy.copyAll(coachclassVo,CoachClassPo.class);
		List<CoachClassPo> poList=coachclassMapper.queryByNew7(po,postSql,sqlFileld);
		List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	/**
	 * 判断教练是否空闲
	 * @param list
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public CoachClassVo isCoachIdle(List<CoachClassVo> list,Integer exceptCcid,String exceptOrderId,Date start,Date end,Boolean isBook) throws Exception {
		CoachClassVo buz=null;
		CoachClassVo mineClass=null;
		//如果不清楚，则默认使用更严格的规则
		if(isBook==null){
			isBook=false;
		}
		if(list==null||list.isEmpty()){
			return buz;
		} else {
			for(CoachClassVo one:list){
				if((exceptCcid!=null && exceptCcid.intValue()==one.getCcid())||(exceptOrderId!=null && exceptOrderId.equals(one.getOrderId()))){
					mineClass=one;
					if(mineClass.getCtype()==OrderConstant.OTYPE.BOOKORDER){
						isBook=true;
					}
				}
			}
			for(CoachClassVo one:list){
				//自己的班次不需要判断
				if(exceptCcid!=null && exceptCcid.intValue()==one.getCcid()){
					if(log.isDebugEnabled()){
						log.debug(exceptCcid+" is mine ccid, so donot compare while isCoachIdle="+one.getCoachId());
					}
					continue;
				}
				if(exceptOrderId!=null && exceptOrderId.equals(one.getOrderId())){
					if(log.isDebugEnabled()){
						log.debug(exceptOrderId+" is mine orderId, so donot compare while isCoachIdle="+one.getCoachId());
					}
					continue;
				}
				long woda=TimeUtil.calcDistanceMillis(one.getCend(),start);
				//提前下课以实际下课时间为准
				if(one.getRend()!=null){
					woda=TimeUtil.calcDistanceMillis(one.getRend(),start);
				}
				long tada=TimeUtil.calcDistanceMillis(end,one.getCstart());
				//提前上课以实际上课时间为准
				if(one.getRstart()!=null){
					tada=TimeUtil.calcDistanceMillis(end,one.getRstart());
				}
				//教练接单：预约课不能接课前15分钟结束的现约单（但可接预约单）
				if(!isBook && one.getCtype()==OrderConstant.OTYPE.BOOKORDER){
					tada -=900000;
				}
				if(woda<0&&tada<0){
					buz=one;
					break;
				}
			}
		}
		return buz;
	}
	public OrderVo isStudentIdle(List<OrderVo> list,String exceptOrderId,Date start,Date end,Boolean isBook) throws Exception {
		OrderVo buz=null;
		OrderVo mineOrder=null;
		//如果不清楚，则默认使用更严格的规则
		if(isBook==null){
			isBook=false;
		}
		if(list==null||list.isEmpty()){
			return buz;
		} else {
			for(OrderVo one:list){
				if(exceptOrderId!=null && one.getOrderId().equals(exceptOrderId)){
					mineOrder=one;
					if(mineOrder.getOtype()==OrderConstant.OTYPE.BOOKORDER){
						isBook=true;
					}
				}
			}
			for(OrderVo one:list){
				//自己的班次不需要判断
				if(exceptOrderId!=null && exceptOrderId.equals(one.getOrderId())){
					continue;
				}
				long woda=TimeUtil.calcDistanceMillis(one.getPend(),start);
				//提前下课以实际下课时间为准
				if(one.getRend()!=null){
					woda=TimeUtil.calcDistanceMillis(one.getRend(),start);
				}
				long tada=TimeUtil.calcDistanceMillis(end,one.getPstart());
				//提前上课以实际上课时间为准
				if(one.getRstart()!=null){
					tada=TimeUtil.calcDistanceMillis(end,one.getRstart());
				}
				//学员现约课结束后15分钟内不能下预约单（但可下现约单)
				if(!isBook && one.getOtype()==OrderConstant.OTYPE.BOOKORDER){
					tada -=900000;
				}
				if(woda<0&&tada<0){
					buz=one;
					break;
				}
			}
		}
		return buz;
	}
	/**
	 * 
	 * @param coachId
	 * @param exceptCcid:例外的ccid，也就是说不需要判断的ccid，教练编辑的时候必传
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public CoachClassVo isCoachIdle(Long coachId,Integer exceptCcid,String exceptOrderId,Date start,Date end,Boolean isBook) throws Exception {
		List<CoachClassVo> list=queryByCoachDateWithNoPrice(start, coachId,OrderConstant.ISDEL.NOTDELETE ,null,1, 30);
		return this.isCoachIdle(list, exceptCcid,exceptOrderId,start, end,isBook);
	}
	public CoachClassVo isCoachIdle(Long coachId,Integer exceptCcid,String exceptOrderId,Date start,Boolean isBook) throws Exception{
		if(start==null){
			start=new Date();
		}
		CoachClassVo result=null;
		List<CoachClassVo> list=queryByCoachDateWithNoPrice(start, coachId,OrderConstant.ISDEL.NOTDELETE ,null,null, null);
		if(list!=null && list.size()>0){
			CoachClassVo cc=null;
			for(CoachClassVo one:list){
				if((exceptCcid!=null && exceptCcid.intValue()==one.getCcid()) ||(exceptOrderId!=null && exceptOrderId.equals(one.getOrderId()))){
					cc=one;
				}
			}
			if(cc!=null){
				Date end=DateUtil.dateAfterMilliSecond(start, (int)TimeUtil.calcDistanceMillis(cc.getCstart(), cc.getCend()));
				result=isCoachIdle(list, exceptCcid,exceptOrderId,start, end,isBook);
			}
		}
		return result;
	}
	
	public String arrangeClass(CoachClassVo coachClassVo) {
		try {
			if (coachClassVo.getCtype() != OrderConstant.OTYPE.BOOKORDER) {
				log.error("can't save not book arrange=" + coachClassVo);
				return ResultCode.ERRORCODE.ORDER_NOT_OPERATE;
			}
			CoachClassVo old=null; 
			//前提条件删除需要判断
			if (coachClassVo.getCcid() != null) {
				old=this.queryCoachClassById(coachClassVo.getCcid(),new CoachClassQuery());
				if(coachClassVo.getIsdel() == OrderConstant.ISDEL.DELETE && old.getIsdel()!=OrderConstant.ISDEL.DELETE) {
					if(old.getBookNum()>0) {
						log.error("can't delete because of has student:"
								+ coachClassVo);
						return ResultCode.ERRORCODE.ORDER_CLASS_OCCUPY;
					}
				}
				BeanCopy.copy2Null(old, coachClassVo);
			//设置排班时间判断
			} else {
				//1.人数判断
				if(coachClassVo.getMaxNum()<1||coachClassVo.getMaxNum()>4){
					log.error("can't set because maxNum>4:"
							+ coachClassVo);
					return ResultCode.ERRORCODE.ORDER_CLASSTIMEOUT;
				}
				//2.起始时间判断
				Date now=new Date();
				long timeLong=TimeUtil.calcDistanceMillis(now,coachClassVo.getCstart());
				if(timeLong<=0 || timeLong>432000000) {
					log.error(coachClassVo+" can't set because class now has start or out of 5day="+timeLong);
					return ResultCode.ERRORCODE.PARAMERROR;
				}
				//2.人数与时间的判断：直接设置正确即可
				if(coachClassVo.getCend() == null){
					Date end=DateUtil.dateAfterMinute(coachClassVo.getCstart(), 60*coachClassVo.getMaxNum());
					coachClassVo.setCend(end);
				}
			}
			
			//删除状态只能如下两种
			if(coachClassVo.getIsdel() != OrderConstant.ISDEL.DELETE && coachClassVo.getIsdel() != OrderConstant.ISDEL.NOTDELETE){
				log.error(coachClassVo+" can't set because deleteState is error.");
				return ResultCode.ERRORCODE.PARAMERROR;
			}
			//非删除需要判断时间冲突
			if(coachClassVo.getIsdel() != OrderConstant.ISDEL.DELETE){
				CoachClassVo buz=this.isCoachIdle(coachClassVo.getCoachId(),coachClassVo.getCcid(), coachClassVo.getOrderId(), coachClassVo.getCstart(),coachClassVo.getCend(),true);
				if(buz!=null){
					log.error(coachClassVo+" can't set because buz="+buz);
					return ResultCode.ERRORCODE.ORDER_COACH_ARRANGEFUL;
				}
			}
			//时间余量设置
			coachClassVo.setRstart(coachClassVo.getCstart());
			coachClassVo.setRend(coachClassVo.getCend());
			
			// 1.冗余教练信息
			// 2.冗余汽车信息
			Car car = carManager.getCarInfo(coachClassVo.getCarId());
			if (car != null) {
				coachClassVo.setCarName(car.getCarType());
				coachClassVo.setCarImg(String.valueOf(car.getCarLevel()));
				coachClassVo.setCarNo(car.getCarNo());
			}
			// 3.冗余课程信息
			CoursenewVo course = fileManager.getCoursenewBycourseid(Integer
					.valueOf(coachClassVo.getCourseId()));
			if (course != null) {
				coachClassVo.setCourseName(course.getCoursenewname());
			}
			// 4.冗余场地
			if (coachClassVo.getPlaceId() != null) {
				Trfield tr = trfieldManager.getTrfieldInfo(coachClassVo
						.getPlaceId());
				if (tr != null) {
					coachClassVo.setPlaceName(tr.getName());
					coachClassVo.setLge(tr.getLge());
					coachClassVo.setLae(tr.getLae());
				}
			}
			saveCoachClass(coachClassVo);
			
			// 2016-8-4 推送教练优化：增加所有教练5天内的排班缓存
			String key = RedisKeys.REDISKEY.TEYUE_COACH_CLASS + coachClassVo.getCoachId(); //从缓存中获取特约教练，特约教练一般固定，目前有20个
			
			//获取教练排班
			CoachClassQuery coachclassQuery=new CoachClassQuery();
			coachclassQuery.setorderBy("order by cstart ASC");
			coachclassQuery.setPageIndex(1);
			coachclassQuery.setPageSize(80);
			String postSql=coachclassQuery.getSqlPost();
			String sqlFileld=coachclassQuery.getSqlField();
			CoachClassPo po=new CoachClassPo();
			po.setCoachId(coachClassVo.getCoachId());
			po.setCtype(OrderConstant.OTYPE.BOOKORDER);
			Date now=new Date();
			String justStart = TimeUtil.getDateFormat(now,"yyyy-MM-dd HH:mm:ss");
			String justEnd=TimeUtil.getDateFormat(DateUtil.dateAfterMinute(now, 1440*5),"yyyy-MM-dd")+" 23:59:59";
			po.setCstart(TimeUtil.parseDate(justStart));
			po.setCend(TimeUtil.parseDate(justEnd));
			po.setIsdel(OrderConstant.ISDEL.NOTDELETE);
			List<CoachClassPo> poList=coachclassMapper.queryByNew1(po,postSql,sqlFileld);
			List<CoachClassVo> voList=new ArrayList<CoachClassVo>();
			if(poList!=null && !poList.isEmpty()){
				voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL); //由于PO不是序列化的，复制到序列化实体
				redisUtil.delete(key); //删除原缓存
				redisUtil.set(key, voList, 3600 * 5); //更新缓存
			}
			
			CoachClassPo onepo=new CoachClassPo();
			String oneday=TimeUtil.getDateFormat(coachClassVo.getCstart(),"yyyy-MM-dd");
			String onekey=this.getClass().getSimpleName()+"_"+oneday+"_"+coachClassVo.getCoachId()+"_"+OrderConstant.ISDEL.NOTDELETE+"_"+OrderConstant.OTYPE.BOOKORDER+"_withprice";
//			String onejustStart = TimeUtil.getDateFormat(coachClassVo.getCstart(),"yyyy-MM-dd")+" 00:00:00";
//			String onejustEnd=TimeUtil.getDateFormat(coachClassVo.getCstart(),"yyyy-MM-dd")+" 23:59:59";
//			onepo.setCstart(TimeUtil.parseDate(onejustStart));
//			onepo.setCend(TimeUtil.parseDate(onejustEnd));
//			onepo.setIsdel(OrderConstant.ISDEL.NOTDELETE);
//			onepo.setCoachId(coachClassVo.getCoachId());
//			onepo.setCtype(OrderConstant.OTYPE.BOOKORDER);
//			List<CoachClassPo> onepoList=coachclassMapper.queryByNew8(po,postSql,sqlFileld);
//			if(onepoList!=null && !onepoList.isEmpty()){
//				List<CoachClassVo> onevoList=BeanCopy.copyList(onepoList,CoachClassVo.class,BeanCopy.COPYALL); //由于PO不是序列化的，复制到序列化实体
//				redisUtil.delete(onekey); //删除原缓存
//				redisUtil.set(onekey, onevoList, 3600*5); //更新缓存
//			}
			redisUtil.delete(onekey); //删除原缓存
			
			return ResultCode.ERRORCODE.SUCCESS;
		} catch (Exception e) {
			log.error("arrangeClass Exception=" + e.getMessage(), e);
			return ResultCode.ERRORCODE.EXCEPTION;
		}
	}
	/**
	 * 
	 * @param coachId
	 * @param studentId只有学生查询的时候才需要传，教练查询请传null.
	 * @param days需要查询排班的天数.
	 * @return
	 *  date为日期
	 *  arrange为当天排班数量，0则未开放
	 *  student为当天已约学生数量，如果不为0则为有约
	 *  arrange不为0且studnet为0则开放未约
	 */
	public List<Map<String,Long>> getCoach5Date(long coachId,Long studentId, int days)  {
		List<Map<String,Long>> arrange=new ArrayList<Map<String,Long>>(); 
		try {
			Date now=new Date();
			for(int i=0;i<days;i++){
				Date date=DateUtil.dateAfterMinute(now, 1440*i);
				Map<String,Long> map=new HashMap<String,Long>();
				map.put("date", date.getTime());
				List<CoachClassVo> list=queryByCoachDateWithNoPrice(date, coachId,OrderConstant.ISDEL.NOTDELETE ,OrderConstant.OTYPE.BOOKORDER,null, null);
				if(list!=null && !list.isEmpty()){
					if(studentId==null){
						long total=0l;
						for(CoachClassVo one:list){
								total +=one.getBookNum();
						}
						map.put("arrange", Long.valueOf(list.size()));
						map.put("student", total);
					} else {
						PlantClassQuery pq=new PlantClassQuery();
						String ccidin="and ccid in (";
						int j=0;
						for(;j<list.size()-1;j++){
							ccidin+=list.get(j).getCcid()+",";
						}
						ccidin+=list.get(j).getCcid()+")";
						pq.setGroupBy(ccidin);
						PlantClassVo search=new PlantClassVo();
						search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
						search.setStudentId(studentId);
					    List<PlantClassVo> pcList=plantClassService.queryByObjectAnd(search, pq);
					    map.put("arrange", Long.valueOf(list.size()));
					    if(pcList==null){
					    	map.put("student", 0l);
					    } else {
					    	map.put("student", Long.valueOf(pcList.size()));
					    }
					}
				} else {
					map.put("arrange", 0l);
					map.put("student", 0l);
				}
				arrange.add(map);
			}
		} catch(Exception e){
			log.error("getCoach5Date Exception:"+e.getMessage(),e);
		}
		return arrange;
	}
	/**
	 * 
	 * @param coachIds 
	 * @param studentId
	 * @return
	 *  arrange代表教练排班数量
	 *  maxnum代表教练最多可待人数
	 *  booknum代表教练目前已约人数
	 *  mine代表我已约课数
	 *  1.arrage为0表示该教练未开放排班，不可预约
	 *  2.maxnum=booknum为教练被学生约满，不可预约
	 *  3.mine=arrage表示该学生约了该教练所有排班，不可预约
	 *  4.mine>0但mine<arrage代表预约成功，且可继续预约
	 * @throws Exception
	 */
	public List<Map<String,Long>> getCoachsArrange(List<Long> coachIds,Long studentId) throws Exception {
		int FINDDATE=5;
		List<Map<String,Long>> arrange=new ArrayList<Map<String,Long>>();
		for(Long coachId:coachIds) {
			Map<String,Long> map=new HashMap<String,Long>();
			//获取教练排班
			CoachClassQuery coachclassQuery=new CoachClassQuery();
			coachclassQuery.setorderBy("order by cstart ASC");
			coachclassQuery.setPageIndex(1);
			coachclassQuery.setPageSize(80);
			String postSql=coachclassQuery.getSqlPost();
			String sqlFileld=coachclassQuery.getSqlField();
			CoachClassPo po=new CoachClassPo();
			po.setCoachId(coachId);
			po.setCtype(OrderConstant.OTYPE.BOOKORDER);
			Date now=new Date();
			String justStart = TimeUtil.getDateFormat(now,"yyyy-MM-dd HH:mm:ss");
			String justEnd=TimeUtil.getDateFormat(DateUtil.dateAfterMinute(now, 1440*FINDDATE),"yyyy-MM-dd")+" 23:59:59";
			po.setCstart(TimeUtil.parseDate(justStart));
			po.setCend(TimeUtil.parseDate(justEnd));
			po.setIsdel(OrderConstant.ISDEL.NOTDELETE);
			List<CoachClassPo> poList=coachclassMapper.queryByNew1(po,postSql,sqlFileld);
			map.put("coachId", coachId);
			long max=0;
			long book=0;
			long ar=0;
			if(poList!=null && !poList.isEmpty()){
				for(int i=0;poList!=null && i<poList.size();i++){
					max+=poList.get(i).getMaxNum();
					book=poList.get(i).getBookNum();
					
				}
				
				map.put("arrange", (long)poList.size());
				map.put("maxnum", max);
				map.put("booknum", book);
				PlantClassQuery pq=new PlantClassQuery();
				String ccidin="and ccid in (";
				int j=0;
				for(;j<poList.size()-1;j++){
					ccidin+=poList.get(j).getCcid()+",";
				}
				ccidin+=poList.get(j).getCcid()+")";
				pq.setGroupBy(ccidin);
				PlantClassVo search=new PlantClassVo();
				search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
				search.setStudentId(studentId);
			    List<PlantClassVo> pcList=plantClassService.queryByObjectAnd(search, pq);
			    if(pcList!=null&& !pcList.isEmpty()) {
			    	map.put("mine", (long)pcList.size());
			    } else {
			    	map.put("mine", 0l);
			    }
			} else {
				map.put("arrange", ar);
				map.put("maxnum", max);
				map.put("booknum", book);
				map.put("mine", 0l);
			}
			arrange.add(map);
		}
		return arrange;
	}
	
	/**
	 * 获取教练所有有价格的排班
	 */
	@Override
	public List<MycoachesVo> getCoachsArrangeWithPrice(List<MycoachesVo> ms,List<Long> coachIds,Long studentId,Date date,String courseId) throws Exception {
		String justdate=TimeUtil.getDateFormat(date, "yyyy-MM-dd");
		Date now=new Date();
		String nowdate=TimeUtil.getDateFormat(now, "yyyy-MM-dd");
		
		List<Map<String,Long>> arrange=new ArrayList<Map<String,Long>>();
		for(Long coachId:coachIds) {
			Map<String,Long> map=new HashMap<String,Long>();
			//获取教练排班
			CoachClassQuery coachclassQuery=new CoachClassQuery();
			coachclassQuery.setorderBy("order by cstart ASC");
			coachclassQuery.setPageIndex(1);
			coachclassQuery.setPageSize(80);
			if(courseId!=null)
			coachclassQuery.setGroupBy(" and course_id="+courseId+" ");
			String postSql=coachclassQuery.getSqlPost();
			String sqlFileld=coachclassQuery.getSqlField();
			CoachClassPo po=new CoachClassPo();
			po.setCoachId(coachId);
			po.setCtype(OrderConstant.OTYPE.BOOKORDER);
			
			//String justStart = TimeUtil.getDateFormat(now,"yyyy-MM-dd HH:mm:ss");
			//String justEnd=TimeUtil.getDateFormat(DateUtil.dateAfterMinute(now, 1440),"yyyy-MM-dd")+" 23:59:59";
			String justStart=justdate+" 00:00:00";
			if(nowdate.equals(justdate)){//今天从当前时间开始算
				justStart = TimeUtil.getDateFormat(now,"yyyy-MM-dd HH:mm:ss");
			}
			String justEnd=justdate+" 23:59:59";
			po.setCstart(TimeUtil.parseDate(justStart));
			po.setCend(TimeUtil.parseDate(justEnd));
			po.setIsdel(OrderConstant.ISDEL.NOTDELETE);
			//获取时间段里某课程的排班教练数据
			List<CoachClassPo> poList=coachclassMapper.queryByNew8(po,postSql,sqlFileld);
			map.put("coachId", coachId);
			long max=0;
			long book=0;
			long ar=0;
			long mi=0;
			if(poList!=null && !poList.isEmpty()){
				for(int i=0;poList!=null && i<poList.size();i++){
					max+=poList.get(i).getMaxNum();
					book=poList.get(i).getBookNum();
					
				}
				
				map.put("arrange", (long)poList.size());
				map.put("maxnum", max);
				map.put("booknum", book);
				PlantClassQuery pq=new PlantClassQuery();
				String ccidin="and ccid in (";
				int j=0;
				for(;j<poList.size()-1;j++){
					ccidin+=poList.get(j).getCcid()+",";
				}
				ccidin+=poList.get(j).getCcid()+")";
				pq.setGroupBy(ccidin);
				PlantClassVo search=new PlantClassVo();
				search.setIsdel(OrderConstant.ISDEL.NOTDELETE);
				search.setStudentId(studentId);
				//获取某排班的学员排班数据
			    List<PlantClassVo> pcList=plantClassService.queryByObjectAnd(search, pq);
			    if(pcList!=null&& !pcList.isEmpty()) {
			    	map.put("mine", (long)pcList.size());
			    	mi=(long)pcList.size();
			    } else {
			    	map.put("mine", 0l);
			    	mi=0l;
			    }
			} else {
				map.put("arrange", ar);
				map.put("maxnum", max);
				map.put("booknum", book);
				map.put("mine", 0l);
				mi=0l;
			}
			arrange.add(map);
			
			List<CoachClassVo> voList=null;
			
			if(poList!=null && !poList.isEmpty()){
				voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
				
			} else {
				voList=new ArrayList<CoachClassVo>();
			}
			
			for(MycoachesVo vo:ms){
				if(vo.getCoachId().longValue()==coachId.longValue()){
					vo.setClasses(voList);
					if (ar == 0l)
                    {
                        vo.setOpenType(0); // 未开放排班
                    }
                    else if (mi > 0)
                    {
                        vo.setOpenType(2); // 预约成功
                    }
                    else
                    {
                        vo.setOpenType(1); // 可以预约
                    }
					break;
				}
			}
			
			
		}
		return ms;
	}
	
	public OrderVo isStudentIdle(Long studentId,String orderId,Date start,Date end,Boolean isBook) throws Exception {
		OrderQuery query=new OrderQuery();
		query.setPageSize(60);
		query.setGroupBy("and order_state in (1,2,3) and pend >"+System.currentTimeMillis());
		query.setorderBy("order by pstart desc");
		List<OrderVo> list=orderService.queryByStudentId(studentId, query);
		return isStudentIdle(list, orderId, start, end,isBook);
	}
	/**
	 * 
	 * @param studentId
	 * @param orderId
	 * @param rstart:该订单的实际开始时间，不传为当前时间
	 * @return
	 * @throws Exception
	 */
	public OrderVo isStudentIdle(Long studentId,String orderId,Date rstart,Boolean isBook) throws Exception {
		if(studentId==null || StringUtil.isNullOrEmpty(orderId)){
			log.error(studentId+" isStudentIdle is emtpty with "+orderId);
			return new OrderVo();
		}
		//默认当前
		if(rstart==null){
			rstart=new Date();
		}
		OrderVo result=null;
		OrderQuery query=new OrderQuery();
		query.setPageSize(70);
		query.setGroupBy("and order_state in (1,2,3) and pend >"+TimeUtil.getDateToMillis(rstart));
		query.setorderBy("order by pstart desc");
		List<OrderVo> list=orderService.queryByStudentId(studentId, query);
		OrderVo vo=null;
		if(list!=null){
			for(OrderVo one:list){
				if(one.getOrderId().equals(orderId)){
					vo=one;
				}
			}
		}
		if(vo==null){
			vo=orderService.queryOrderById(orderId, new OrderQuery());
		}
		
		if(vo!=null){
			Date rend=DateUtil.dateAfterMilliSecond(rstart, (int)TimeUtil.calcDistanceMillis(vo.getPstart(), vo.getPend()));
			result=isStudentIdle(list, orderId, rstart, rend,isBook);
		}
		return result;
	}
	
	@Override
	public List<CoachClassVo> queryByCoachDateLiliAll(Date date, Long coachId, Integer isDel, Integer ctype, Integer pageIndex, Integer pageSize, String v, Student s) throws Exception {
		List<CoachClassVo> list=this.queryByCoachDateLili(date, coachId, isDel, ctype, pageIndex, pageSize, v, s);
		
		if(list!=null && !list.isEmpty()){
			String ccidsnull="and ccid in (";
			String ccids=ccidsnull;
			int i=0;
			for(;i<list.size()-1;i++){
				CoachClassVo one=list.get(i);
				if(one.getIsdel()!=OrderConstant.ISDEL.DELETE && one.getBookNum()>0){
					ccids +=one.getCcid()+",";
				}
			}
			CoachClassVo one=list.get(i);
			if(one.getIsdel()!=OrderConstant.ISDEL.DELETE && one.getBookNum()>0){
				ccids +=one.getCcid()+")";
			} else if(!ccids.equals(ccidsnull)){
				if(ccids.endsWith(",")){
					ccids =ccids.substring(0,ccids.length()-1)+")";
				} else {
					ccids +=")";
				}
			}
			Map<Integer,CoachClassVo> map=BeanCopy.getFromList(list, "ccid");
			if(!ccids.equals(ccidsnull)){
				PlantClassQuery query=new PlantClassQuery();
				query.setGroupBy(ccids+" and isdel!="+OrderConstant.ISDEL.DELETE);
				query.setPageSize(500);
				query.setorderBy("order by gtime desc");
				PlantClassVo search=new PlantClassVo(); 
				search.setIsdel(null);
				List<PlantClassVo> pcList=plantClassService.queryByObjectAnd(search, query);
				for(PlantClassVo pc:pcList){
					CoachClassVo cc=map.get(pc.getCcid());
					if( cc.getPlantClassList()==null){
						List<PlantClassVo> mypc=new ArrayList<PlantClassVo>();
						mypc.add(pc);
						cc.setPlantClassList(mypc);
					} else {
						cc.getPlantClassList().add(pc);
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * 查询指定日期的教练排班信息 含价钱
	 * @param coachId
	 * @param date：指定日期格式yyyy-MM-dd
	 * @param isDel：查所有请传null，查有效请传0
	 * @param ctype:班次类别，不传查所有类型
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	private List<CoachClassVo> queryByCoachDateLili(Date date, Long coachId, Integer isDel, Integer ctype,
			Integer pageIndex, Integer pageSize,  String v, Student s) throws Exception {
		String justdate=TimeUtil.getDateFormat(date, "yyyy-MM-dd");
		String key=this.getClass().getSimpleName()+"_"+justdate+"_"+coachId+"_"+isDel+"_"+ctype;
		
		List<CoachClassVo> voList=null;
		if(pageIndex==null || pageSize==null){
			voList=redisUtil.get(key);
		}
		if(voList==null){
			CoachClassQuery coachclassQuery=new CoachClassQuery();
			coachclassQuery.setorderBy("order by cstart ASC");
			if(pageIndex!=null){
				coachclassQuery.setPageIndex(pageIndex);
				coachclassQuery.setPageSize(pageSize);
			} else {
				coachclassQuery.setPageIndex(1);
				coachclassQuery.setPageSize(80);
			}
			String postSql=coachclassQuery.getSqlPost();
			String sqlFileld=coachclassQuery.getSqlField();
			CoachClassPo po=new CoachClassPo();
			po.setCoachId(coachId);
			if(ctype!=null){
				po.setCtype(ctype);
			}
			if(StringUtil.isNotNullAndNotEmpty(justdate)) {
				po.setCstart(TimeUtil.parseDate(justdate+" 00:00:00"));
				po.setCend(TimeUtil.parseDate(justdate+" 23:59:59"));
			}
			if(isDel==null){
				po.setIsdel(null);
			} else {
				po.setIsdel(Integer.valueOf(isDel));
			}
//			if (s.getDrType() != null) {
//				po.setDltype(Integer.valueOf(s.getDrType()));
//			}
			
			List<CoachClassPo> poList= null;
			//20160624付费预约，只针对1.8.0及更高版本有效，只针对喱喱学员有效
    		if((null != v && !"".equals(v) && (VersionUtil.compareVersion(v, "1.8.0") < 0))) {
    			poList=coachclassMapper.queryByNew9(po,postSql,sqlFileld); //把教练排班没有价格的查询返回,由于低与1.8版本的喱喱学员屏蔽了我的教练，该方法一般不会被调用
    		}
    		else {
    			if(!(VersionUtil.compareVersion(v, "2.1.0") >= 0)){
	    			//modify by devil 20161029 改成所有都可以约
	    			int c1 = s.getCourse1() == null ? 0 : s.getCourse1(); // 科目一成绩
	    			String flowNo = s.getFlowNo(); // 流水
	    			if(s.getCheckDriveIdState() == 2) { //有驾照认证 
	    				poList=coachclassMapper.queryByNew8(po,postSql,sqlFileld); //返回有价格的排班(防止教练在低版本有排班后升级版本，再排班有价格的，导致学员拉取到无价格的班次)
	    			}
	    			else { //无驾照认证按照流水处理
	    				if ( flowNo == null || "".equals(flowNo)) { //新用户、报名无流水，只能约科目二基础
	    					poList=coachclassMapper.queryByNew10(po,postSql,sqlFileld); //返回科目二基础
	    				}
	    				else if (flowNo != null && !"".equals(flowNo) && c1 < 90) { //报名有流水且科目一未过，不能约科目三
	    					poList=coachclassMapper.queryByNew11(po,postSql,sqlFileld); //返回科目二所有课程
	    				}
	    				else if( c1 >= 90) { //考过科目一，所有都可以约
	    					poList=coachclassMapper.queryByNew8(po,postSql,sqlFileld); //返回有价格的排班(防止教练在低版本有排班后升级版本，再排班有价格的，导致学员拉取到无价格的班次)
	    				}
	    			}
    			}else{
    				poList=coachclassMapper.queryByNew8(po,postSql,sqlFileld);
    			}
    			
    		}
			if(poList!=null && !poList.isEmpty()){
				voList=BeanCopy.copyList(poList,CoachClassVo.class,BeanCopy.COPYALL);
				if(pageIndex==null|| pageSize==null ){
					redisUtil.set(key, voList, RedisExpireConstant.ORDERTIME.COACHCLASS);
				}
			} else {
				voList=new ArrayList<CoachClassVo>();
			}
		}
		return voList;
	}
	
	@Override
	public List<CoachClassPriceVo> queryCoachPrice(Long coachId, Date date,  String courseId, String colId,
			String dftype, String ctype, String calId){
		List<CoachClassPriceVo> classPriceList = null;
		try {
			int year=DateUtil.getYear(date);
			int month=DateUtil.getMonth(date);
			int day=DateUtil.getDay(date);
			int hour=DateUtil.getHour(date);
			int week=DateUtil.getWeek(date);
			if (week == 0) {
				week = 7;//周日
			}
			CoachClassPricePo coachClassPricePo = new CoachClassPricePo();
			Coach coachInfo = coachManager.getCoachInfo(coachId);
			if(coachInfo.getCityId() != null && !"".equals(coachInfo.getCityId())){
				coachClassPricePo.setCityId(coachInfo.getCityId());
			}
			else {
				log.error("************************** Query Coach City Error ! Counld Not Find City By CoachId is :" + coachId);
			}
			String justdate=TimeUtil.getDateFormat(date, "yyyy-MM-dd");
			String key = RedisKeys.REDISKEY.CITY_PRICE +"_"+coachInfo.getCityId()+ "_" + justdate + "_" + courseId+"_"+dftype+"_"+ctype; //城市ID +日期+课程ID+ 档位 + 预约
			
			classPriceList=redisUtil.get(key);//从缓存里取
			if(classPriceList == null){
				coachClassPricePo.setCourseId(courseId);
				coachClassPricePo.setCoachId(coachId.intValue());
				if (!"".equals(colId) && colId != null) {
					coachClassPricePo.setColId(Integer.valueOf(colId));
				}
				if (!"".equals(dftype) && dftype != null) {
					coachClassPricePo.setDftype(Integer.valueOf(dftype));
				}
				if (!"".equals(ctype) && ctype != null) {
					coachClassPricePo.setCtype(Integer.valueOf(ctype));
				}
				if (!"".equals(calId) && calId != null) {
					coachClassPricePo.setCalId(Integer.valueOf(calId));
				}
				
				List<CoachClassPricePo> coachPriceList = coachClassPriceMapper.queryCoachPriceList(coachClassPricePo); 
				List<CoachClassPricePo> coachClassPriceList = new ArrayList<CoachClassPricePo>(); 
				//根据查询的结果按日期长度优化取值
				CoachClassPricePo coachClassPrice = null;
				if(null != coachPriceList && coachPriceList.size()>0){
					boolean isNewPice = false;
					for(int i=0;i<coachPriceList.size();i++) {
						String[] tstart=coachPriceList.get(i).getTstart().split("-");
						String[] tend=coachPriceList.get(i).getTend().split("-");
						coachClassPrice = new CoachClassPricePo();
						if(tstart.length==4 &&
								year>=Integer.valueOf(tstart[0]) && year<=Integer.valueOf(tend[0]) 
								&& month >=Integer.valueOf(tstart[1]) && month<=Integer.valueOf(tend[1]) 
								&& day>=Integer.valueOf(tstart[2]) && day<=Integer.valueOf(tend[2])
								&& hour>=Integer.valueOf(tstart[3]) && hour<=Integer.valueOf(tend[3])) {
							isNewPice = true;
							coachClassPrice = coachPriceList.get(i);
							if (coachClassPrice.getPrice() == null || coachClassPrice.getPrice() <= 0) {
								coachClassPrice.setPrice(OrderConstant.defaultPrice);//设置默认值
							} 
							//长度最大优先
						} else if (tstart.length==3 && 
								month >=Integer.valueOf(tstart[0]) && month<=Integer.valueOf(tend[0]) 
								&& day>=Integer.valueOf(tstart[1]) && day<=Integer.valueOf(tend[1])
								&& hour>=Integer.valueOf(tstart[2]) && hour<=Integer.valueOf(tend[2])) {
							coachClassPrice = coachPriceList.get(i);
							isNewPice = true;
							if (coachClassPrice.getPrice() == null || coachClassPrice.getPrice() <= 0) {
								coachClassPrice.setPrice(OrderConstant.defaultPrice);//设置默认值
							} 
							//长度最大优先
						} else if (tstart.length==2 &&
								week >=Integer.valueOf(tstart[0]) && week <=Integer.valueOf(tend[0]) 
								&& hour>=Integer.valueOf(tstart[1]) && hour<=Integer.valueOf(tend[1])) {
							//平时价格
							if (!isNewPice) {
								coachClassPrice = coachPriceList.get(i);
								if (coachClassPrice.getPrice() == null || coachClassPrice.getPrice() <= 0) {
									coachClassPrice.setPrice(OrderConstant.defaultPrice);//设置默认值
								} 
							}
							//长度最大优先
						} 
						if (coachClassPrice.getPrice() != null) {
							coachClassPriceList.add(coachClassPrice);
						}
					}
				}
				if (coachClassPriceList.size() > 0){
					classPriceList = BeanCopy.copyList(coachClassPriceList,CoachClassPriceVo.class,BeanCopy.COPYNOTNULL);
					redisUtil.set(key, classPriceList, RedisExpireConstant.ORDERTIME.COACHCLASS);
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classPriceList;
	}
	
	@Override
	public int queryBookNumByCoachId(Long coachId, CoachClassQuery coachclassQuery) {
		Integer bookNum = 0;
		try {
			String postSql=coachclassQuery.getSqlPost();
			String sqlFileld=coachclassQuery.getSqlField();
			if(postSql==null) {
				postSql="";
			}
			bookNum = coachclassMapper.queryBookNumByCoachId(coachId,postSql,sqlFileld);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookNum;
	}
	
	@Override
	public Integer queryBookNumByCoachId2(Long coachId, CoachClassQuery coachclassQuery) {
		Integer bookNum = 0;
		try {
			String postSql=coachclassQuery.getSqlPost();
			String sqlFileld=coachclassQuery.getSqlField();
			if(postSql==null) {
				postSql="";
			}
			bookNum = coachclassMapper.queryBookNumByCoachId2(coachId,postSql,sqlFileld);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookNum;
	}
	@Override
	public Integer queryBookNumByOrderCcId(String orderId, Integer ccid, Integer otype, CoachClassQuery coachclassQuery) {
		Integer bookNum = 0;
		try {
			String postSql=coachclassQuery.getSqlPost();
			String sqlFileld=coachclassQuery.getSqlField();
			if(postSql==null) {
				postSql="";
			}
			  if(otype == 1) {//现约根据订单号查询上课人数
				  bookNum = coachclassMapper.queryBookNumByOrderId(orderId,postSql,sqlFileld);
	          }
	          if(otype == 3){//预约根据ccid查询上课人数
	        	  bookNum = coachclassMapper.queryBookNumByCcId(ccid,postSql,sqlFileld);
	          }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookNum;
	}
}
