package com.lili.finance.manager.common.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.DateUtil;
import com.lili.cms.util.PageUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.coupon.dto.CCondition;
import com.lili.coupon.dto.CStock;
import com.lili.finance.manager.common.ICMSCouponManager;
import com.lili.finance.mapper.dao.common.CMSCStockDao;
import com.lili.finance.mapper.dao.common.CMSCouponDao;
import com.lili.finance.model.CConditionBDTO;
import com.lili.finance.model.CouponBTO;
import com.lili.finance.model.StudentCouponBDTO;
import com.lili.finance.vo.CouponVo;
import com.lili.finance.vo.StudentCouponVo;
import com.lili.school.model.CourseNewDTO;
import com.lili.school.model.Region;
import com.lili.school.service.CMSRegionService;
import com.lili.school.service.ICmsCourseService;

public class CMSCouponManagerImpl implements ICMSCouponManager{

	Logger logger = Logger.getLogger(CMSCouponManagerImpl.class);
	
	@Autowired
	CMSCouponDao cmsCouponDao;
	
	@Autowired
	CMSCStockDao cmsCStockDao;
	
	@Autowired
	CMSRegionService cmsRegionService;
	
	@Autowired
	ICmsCourseService cmsCourseService;
	
	@Autowired
	RedisUtil redisUtil;
	
	@Override
	public PagedResult<CCondition> findConditionBatch(CConditionBDTO dto) {
		try {
			PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
			List<CCondition> conList = cmsCouponDao.findConditionBatch(dto);
			
			Map<Integer, String> cityMap = queryCityMap();
			Map<Integer, String> courseMap = queryCourseMap();
			
			StringBuffer sb = null;
			CCondition condition = null;
			for (int i = 0; i < conList.size(); i++) {
				condition = conList.get(i);
				String param1[] = condition.getParam1().split("\\|");
				if (condition.getType() == (byte) 1) { //城市
					sb = new StringBuffer();
					for (int j =0; j < param1.length; j++) {
						Integer cityId = Integer.parseInt(param1[j]);
						sb.append(cityMap.get(cityId));
						if(j != param1.length -1){
							sb.append("、");
						}
					}
					conList.get(i).setParam1(sb.toString());
				}
				else if (condition.getType() == (byte) 2) { //科目
					sb = new StringBuffer();
					for (int j =0; j < param1.length; j++) {
						Integer courseId = Integer.parseInt(param1[j]);
						if (courseId <= 10) {
							sb.append("C1"+courseMap.get(courseId));
						}
						else {
							sb.append("C2" + courseMap.get(courseId));
						}
						if (j != param1.length -1) {
							sb.append("、");
						}
					}
					conList.get(i).setParam1(sb.toString());
				}
				else if (condition.getType() == (byte) 3) { //限领次数
					conList.get(i).setParam1("限领"+condition.getParam1() + "次");
				}
			}
			return BeanUtil.toPagedResult(conList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取城市映射，缓存一天处理
	 * @return
	 */
	private Map<Integer, String> queryCityMap() {
		Map<Integer, String> cityMap = null;
		try {
			String cityKey = REDISKEY.CITY_NAME_HASHMAP;
			if (redisUtil.isExist(cityKey)) {
				cityMap = redisUtil.get(cityKey);
			}
			if (cityMap == null) { //缓存有可能是空
				cityMap = new HashMap<Integer, String>();
				Region region= new Region();
				List<Region>  cityList = cmsRegionService.findAllCity(region);
				for (Region city : cityList) {
					cityMap.put(city.getRid(), city.getRegion());
				}
				cityMap.put(100102,"广州市");//广州市已逻辑删除，手工加到数组里
				
				redisUtil.set(cityKey, cityMap, 3600);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cityMap;
	}

	/**
	 * 获取课程映射，缓存一天处理
	 * @return
	 */
	private Map<Integer, String> queryCourseMap() {
		Map<Integer, String> courseMap = null;
		try {
			String courseKey = REDISKEY.COURSE_NAME_HASHMAP;
			if (redisUtil.isExist(courseKey)) {
				courseMap = redisUtil.get(courseKey);
			}
			if (courseMap == null) { //缓存有可能是空
				courseMap = new HashMap<Integer, String>();
				CourseNewDTO courseNew = new CourseNewDTO();
				courseNew.setIsdel(0);
				List<CourseNewDTO> courseList = cmsCourseService.queryCourseNewList(courseNew);
				for (CourseNewDTO course : courseList) {
					courseMap.put(course.getCourseTemId(), course.getCoursenewName());
				}
				
				redisUtil.set(courseKey, courseMap, 3600);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return courseMap;
	}

	@Override
	public PagedResult<CouponVo> findCouponBatch(CouponBTO dto) {
		PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
		return BeanUtil.toPagedResult((cmsCouponDao.findCouponBatch(dto)));
	}

	@Override
	public CouponVo findCoupon(String coupontmpid) {
		CouponVo coupon = null;;
		try {
			coupon = cmsCouponDao.findCoupon(coupontmpid);
			Map<Integer, String> cityMap = queryCityMap();
			Map<Integer, String> courseMap = queryCourseMap();
			
			if (coupon.getUserule() != null && !"".equals(coupon.getUserule())) {
				String rule = handlerRuleToStr(coupon.getUserule(), cityMap, courseMap).toString();
				coupon.setUseruleStr(rule);
			}
			if (coupon.getGenrule() != null && !"".equals(coupon.getGenrule())) {
				String rule = handlerRuleToStr(coupon.getGenrule(), cityMap, courseMap).toString();
				coupon.setGenruleStr(rule);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return coupon;
	}

	private StringBuffer handlerRuleToStr(String rule, Map<Integer, String> cityMap, Map<Integer, String> courseMap) {
		StringBuffer sb = null;
		StringBuffer sbNew = new StringBuffer();
		String gen[] = rule.split("\\|");
		String conditions[] = gen[1].split(",");
		CConditionBDTO dto = null;
	    for (int i = 0; i < conditions.length; i++) {
	    	dto = new CConditionBDTO();
	    	dto.setConditionid(Integer.parseInt(conditions[i]));
	    	CCondition condition = queryCondition(dto);
	    	String param1[] = condition.getParam1().split("\\|");
	    	if (condition.getType() == (byte) 1) { //城市
				sb = new StringBuffer();
				sb.append(condition.getConditionid() +" - ");
				for (int j =0; j < param1.length; j++) {
					Integer cityId = Integer.parseInt(param1[j]);
					sb.append(cityMap.get(cityId));
					if(j != param1.length -1){
						sb.append("、");
					}
				}
			}
			else if (condition.getType() == (byte) 2) { //科目
				sb = new StringBuffer();
				sb.append(condition.getConditionid() +" - ");
				for (int j =0; j < param1.length; j++) {
					Integer courseId = Integer.parseInt(param1[j]);
					if (courseId <= 10) {
						sb.append("C1"+courseMap.get(courseId));
					}
					else {
						sb.append("C2" + courseMap.get(courseId));
					}
					if(j != param1.length -1){
						sb.append("、");
					}
				}
			}
			else if (condition.getType() == (byte) 3) { //限领次数
				sb = new StringBuffer();
				sb.append(condition.getConditionid() +" - 限领"+condition.getParam1() + "次");
			}
			else { //时间、报名、分享
				sb = new StringBuffer();
				sb.append(condition.getConditionid() +" - "+condition.getParam1());
			}
	    	
	    	sbNew.append(sb.toString());
	    	if(i != conditions.length -1){
	    		sbNew.append("\n");
			}
	    }
		return sbNew;
	}

	/**
	 * 手动将学员使用的优惠券情况重置下
	 */
	@Override
	public PagedResult<StudentCouponVo> findStudentCouponBatch(StudentCouponBDTO dto) {
		PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
		List<StudentCouponVo> studentCouponVos = cmsCouponDao.findStudentCouponBatch(dto);
		
		for(StudentCouponVo studentCouponVo : studentCouponVos){
			if(dto.getType() != null && dto.getType() != 0){
				studentCouponVo.setType(dto.getType());
			}else {
				if(studentCouponVo.getIsValid() == (byte)0){
					studentCouponVo.setType(3);
					continue;
				}
				if(studentCouponVo.getUsetime() != null){
					studentCouponVo.setType(2);
					continue;
				}
				if(studentCouponVo.getExpiretime() != null && (studentCouponVo.getExpiretime().getTime() < DateUtil.getCurrentMillis())){
					studentCouponVo.setType(4);
					continue;
				}
				if(studentCouponVo.getUsetime() == null){
					studentCouponVo.setType(1);
					continue;
				}
			}
			
		}
		
		return BeanUtil.toPagedResult(studentCouponVos);
	}

	@Override
	public CStock findStockByCouponTmpId(String couponTmpId) {
		return cmsCStockDao.getStockByCouponTmpId(couponTmpId);
	}

	@Override
	public List<StudentCouponVo> findStudentCoupon(String couponId) {
		return cmsCouponDao.findStudentCoupon(couponId);
	}

	@Override
	public List<StudentCouponVo> queryCouponList(StudentCouponVo coupon) {
		return cmsCouponDao.queryCouponList(coupon);
	}

	@Override
	public List<CCondition> findConditionList(CConditionBDTO dto) {
		return cmsCouponDao.findConditionBatch(dto);
	}

	@Override
	public List<CStock> findStockList() {
		return cmsCStockDao.findStockList();
	}

	@Override
	public String queryCouponName(String couponId) {
		return cmsCouponDao.queryCouponName(couponId);
	}

	@Override
	public CCondition queryCondition(CConditionBDTO dto) {
		return cmsCouponDao.queryCondition(dto);
	}

	@Override
	public List<CouponVo> queryIsStatus(CouponBTO couponBTO) {
		return cmsCouponDao.queryCouponVoList(couponBTO);
	}

}
