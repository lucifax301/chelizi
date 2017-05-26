package com.lili.coach.manager;

import java.util.List;

import com.lili.coach.dto.BrandCar;
import com.lili.coach.dto.Coach;
import com.lili.coach.dto.CoachAccount;
import com.lili.coach.dto.CourseS;
import com.lili.coach.dto.School;
import com.lili.coach.vo.CoachInfoVo;
import com.lili.coach.vo.CoachStatusRecord;
import com.lili.coach.vo.CoachVo;
import com.lili.order.vo.CoachClassTemp;
import com.lili.order.vo.CoachClassTempNameVo;
import com.lili.order.vo.CoachClassTempQuery;
import com.lili.order.vo.CoachClassTempVo;

public interface CoachManager {

    /**
     * 获取教练列表
     *
     * @return
     */
    List<Coach> getCoach();
    
    /**
     * 获取教练列表
     *
     * @return
     */
    List<Coach> getCoach(Coach coach);

    /**
     * 根据id获取教练信息
     *
     * @param id
     * @return
     */
    Coach getCoachInfo(long id);

    /**
     * 获取页面所需教练信息
     *
     * @param id
     * @return
     */
    CoachInfoVo getCoachInfoVo(long id);

    /**
     * 根据手机号获取用户
     *
     * @param mobile
     * @return
     */
    Coach getCoachByPhoneNum(String mobile);

    /**
     * 获取教练数量
     *
     * @return
     */
    long getCount(Coach coach);

    /**
     * 新增教练信息
     *
     * @param coach
     * @return
     */
    long addCoach(Coach coach);

    /**
     * 更新教练信息
     *
     * @param coach
     * @return
     */
    long updateCoach(Coach coach);

    /**
     * 根据id删除教练信息
     *
     * @param id
     * @return
     */
    long deleteCoach(long id);

    /**
     * 校验手机号码是否存在
     *
     * @param phoneNum
     * @return
     */
    boolean isExist(String phoneNum);

    /**
     * 根据学生id查询关联教练的信息
     *
     * @param sid
     * @return
     */
    List<Coach> getCoachesByStudentId(long sid,byte type);
    
    /**
     * 获取特约教练
     *
     * @param sid
     * @return
     */
    List<Coach> getTYCoaches();

    /**
     * 根据教练id获取教练金额
     *
     * @param coachId
     * @return
     */
    CoachAccount getCoachMoney(Long coachId);

    /**
     * 更新教练帐户
     *
     * @param coachAccount
     * @return
     */
    void updateCoachAccount(CoachAccount coachAccount);

    /**
     * 根据教练id获取课程信息
     *
     * @param coachId
     * @return
     */
    List<CourseS> getCourseSById(long coachId);

    /**
     * 给教练添加帐户信息
     *
     * @param c
     */
    void addCoachAccount(CoachAccount c);

    List<CoachVo> getCoachesByIds(List<Long> coachIds);

    /**
     * 判断用户密码是否正确
     *
     * @param pw
     * @param userId
     * @return
     */
    long checkCoachCorrectPw(String pw, Long userId);

    /**
     * 设置用户密码
     *
     * @param pw
     * @param userId
     */
    void updateCoachPassword(String pw, Long userId);

    /**
     * 获取常用车辆
     *
     * @param isCommon
     * @param seqNum
     * @return
     */
    List<BrandCar> getBrandCar(String isCommon, String seqNum);

    /**
     * 批量新增教练信息，cms使用
     *
     * @param coaches
     * @return
     */
    long addCoachList(List<Coach> coaches);

    /**
     * 给单个教练发奖金
     *
     * @param coachAccount
     * @return
     */
    boolean payCoachesBonuses(CoachAccount coachAccount);

    /**
     * 给多个教练发奖金
     *
     * @param coachAccounts
     * @return
     */
    boolean payCoachesBonuses(List<CoachAccount> coachAccounts);

    /**
     *  查询排班模板
     * @param coachClassTemp
     * @return
     */
	List<CoachClassTempQuery> queryTemplate(CoachClassTemp coachClassTemp);

	/**
	 * 保存排班模板
	 * @param classTempList
	 */
	boolean saveClassTemp(List<CoachClassTempVo> classTempList);
	/**
	 * 校验是否存在同名的排班模板
	 * @param classTempList
	 */
	Integer isExitClassTempName(CoachClassTempVo coachClassTempVo);
	
	/**
	 * 修改排班模板
	 * @param classTempList
	 */
	boolean updateClassTemp(List<CoachClassTempVo> classTempList);

	/**
	 * 关闭排班模板
	 * @param tempId
	 */
	boolean closeClassTemp(Integer tempId);

	List<CoachClassTempNameVo> queryTemplateName(CoachClassTemp coachClassTemp);


    School getSchool(int schoolId);
    
    /**
     * 根据推送类型获取要推送的教练名单
     *
     * @param coachId
     * @return
     */
    List<CoachVo> getPushCoachList(Integer pushType);

    public void addCoachStatusRecord(CoachStatusRecord record);
    
    /**
     * 添加收车数目
     * @param coach
     */
    public void addCoachInCount(CoachStatusRecord record);
    /**
     * 添加出车数目
     * @param coach
     */
    public void addCoachOutCount(CoachStatusRecord record);
    /**
     * 获取最近一条出收车记录
     * @param record
     * @return
     */
    public CoachStatusRecord getLatestStatusRecord(CoachStatusRecord record );
    /**
     * 添加登录次数
     * @param coach
     */
    public void addLoginCount(Coach coach);
}
