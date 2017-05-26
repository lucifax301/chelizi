package com.lili.school.manager;

import java.util.List;

import com.lili.common.vo.ReqResult;
import com.lili.school.dto.EnrollPurpose;
import com.lili.school.dto.WechatCoachClassDto;
import com.lili.school.dto.WechatEnrollStudentDto;
import com.lili.school.dto.WechatMycoachesDto;
import com.lili.school.dto.WechatPlantClassDto;
import com.lili.school.vo.WechatCoachClass;
import com.lili.school.vo.WechatEnrollClass;
import com.lili.school.vo.WechatEnrollClassVo;
import com.lili.school.vo.WechatEnrollStudent;
import com.lili.school.vo.WechatMycoaches;
import com.lili.school.vo.WechatOrder;
import com.lili.school.vo.WechatPlantClass;
import com.lili.school.vo.WechatStudent;
import com.lili.school.vo.WechatTemplate;
import com.lili.school.vo.WechatTemplateLog;

public interface WechatSchoolManager {

	ReqResult addEnrollVisCard(List<WechatEnrollClass> enrollJsonList);

	List<WechatEnrollClass> queryWechatEnrollClassList(WechatEnrollClass wechatEnrollClass);

	ReqResult inportStudent(Long coachId, List<WechatMycoaches> weStuJsonList);

	ReqResult inportStudentOne(WechatMycoaches mycoaches);

	List<WechatCoachClass> queryIsExitClass(WechatCoachClass wechatCoachClass);

	ReqResult addWechatClass(Long coachId, WechatCoachClass wechatCoachClass);

	List<WechatEnrollStudent> queryWechatEnroll(WechatEnrollStudentDto wechatEnrollStudentDto);

	WechatStudent queryWechatStudent(WechatStudent wechatStudent);

	Integer queryOnClassNum(WechatPlantClass wechatPlantClass);

	List<WechatCoachClass> queryWechatClass(WechatCoachClass wechatCoachClass);

	WechatMycoaches queryMyWechatCoach(WechatMycoaches wechatMycoaches);

	WechatEnrollStudent queryEnrollStudentInfo(WechatEnrollStudent record);

	WechatEnrollClass queryWechatEnrollClass(WechatEnrollClass recordClass);

	WechatEnrollStudent queryWechatEnrollStudent(WechatEnrollStudent recordStudent);

	WechatCoachClass queryNearWechatCoachClass(WechatCoachClass recordClass);

	Integer subEnrollPurpose(WechatEnrollStudent wechatEnrollStudent);

	int handClass(WechatPlantClass wechatPlantClass);

	int updateWechatMyCoach(WechatMycoaches wechatMycoaches);

	void insertWxTemplateLog(WechatTemplateLog wechatTemplateLog);

	int insertWechatStudent(WechatStudent wechatStudentInfo);

	WechatTemplate queryWechatTemplate(WechatTemplate wechatTemplate);

	WechatCoachClass queryWechatCoachClass(WechatCoachClass recordClass);

	int insertWechatPlantClass(WechatPlantClass plantClass);

	int inserWechatOrder(WechatOrder wechatOrder);

	List<WechatPlantClass> queryWechatPlantClassDtoList(WechatPlantClassDto record);

	WechatPlantClass queryWechatPlantClass(WechatPlantClass record);

	ReqResult updateWechatClass(WechatOrder wechatOrder, WechatCoachClass wechatCoachClass);

	List<WechatPlantClass> queryWechatPlantClassList(WechatPlantClass record);

	int updateWechatCoachClass(WechatCoachClass recordClass);

	int updateWechatOrder(WechatOrder order);

	int updateWechatPlantClass(WechatPlantClass plantClass);

	WechatOrder queryWechatOrder(WechatOrder orderInfo);

	int insertWechatMycoaches(WechatMycoaches myCoach);

	Integer countMyStudentNum(WechatMycoaches wechatStudentDto);

	int updateWechatStudent(WechatStudent wechatStudent);

	List<WechatCoachClass> queryCoachClassByCcidIn(WechatCoachClassDto coachClassDto);

	Integer countNewStudent(WechatMycoachesDto mycoach);

	int updateWechatEnrollStudent(WechatEnrollStudent updateEnrollStudent);

	List<WechatMycoaches> queryWechatMycoachesList(WechatMycoachesDto myCoachDto);

	int updateWechatMycoaches(WechatMycoaches mycoach);

	List<WechatOrder> queryWechatOrderList(WechatOrder order);

	int deleteWechatStudent(WechatStudent exitOpenIdStu);

	WechatMycoaches queryMyWechatBoundCoach(WechatMycoachesDto wechatMycoachesDto);

	List<WechatEnrollClass> queryClassGroupByCoachList(WechatEnrollClass wechatEnrollClass);

	ReqResult subEnrollPurposeNew(EnrollPurpose enrollPurpose);

	EnrollPurpose getEnrollPurpose(EnrollPurpose enrollPurpose);

	WechatEnrollClass getMinPrice(WechatEnrollClassVo enrollClass);


}
