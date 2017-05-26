/**
 *
 */
package com.lili.coach.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lili.coach.dto.Coach;
import com.lili.coach.dto.CoachAccount;
import com.lili.coach.vo.CoachInfoVo;
import com.lili.coach.vo.CoachStatusRecord;
import com.lili.coach.vo.CoachVo;

public interface CoachMapper {

    /**
     * 根据主键删除教练信息
     *
     * @param coachId
     * @return
     */
    long deleteByPrimaryKey(Long coachId);

    /**
     * 新增教练信息
     *
     * @param coach
     * @return
     */
    long insert(Coach coach);

    /**
     * 新增教练信息并返回教练id
     *
     * @param coach
     * @return
     */
    long insertAndGetCoachId(Coach coach);

    /**
     * 新增教练信息
     *
     * @param coach
     * @return
     */
    long insertSelective(Coach coach);

    /**
     * 根据id获取教练信息
     *
     * @param coachId
     * @return
     */
    Coach selectByPrimaryKey(Long coachId);

    /**
     * 根据手机号获取教练信息
     *
     * @param mobile
     * @return
     */
    Coach selectByPhoneNum(String mobile);

    /**
     * 获取页面所需教练信息
     *
     * @param id
     * @return
     */
    CoachInfoVo selectCoachInfoVo(long id);

    /**
     * 修改教练信息
     *
     * @param coach
     * @return
     */
    long updateByPrimaryKeySelective(Coach coach);

    /**
     * 修改教练信息
     *
     * @param coach
     * @return
     */
    long updateByPrimaryKey(Coach coach);

    /**
     * 获取教练列表
     *
     * @return
     */
    List<Coach> getAll(Coach coach);

    /**
     * 获取教练数量
     *
     * @return
     */
    long countAll(Coach coach);

    /**
     * 通过关联表获取教练列表
     *
     * @param studentId
     * @return
     */
	List<Coach> getCoachesByStudentId(@Param("studentId")long studentId,@Param("type")byte type);
	
	/**
	 * 根据教练id获取教练金额
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
     * 插入教练帐户
     *
     * @param c
     */
    void insertCoachAccount(CoachAccount c);

    List<CoachVo> getCoachesByIds(List<Long> coachIds);


    /**
     * 用户密码是否正确
     *
     * @param passwd
     * @param coachId
     * @return
     */
    long checkCoachCorrectPw(@Param("passwd") String passwd, @Param("coachId") Long coachId);

    /**
     * 设置用户密码
     *
     * @param passwd
     * @param coachId
     */
    long updateCoachPassword(@Param("passwd") String passwd, @Param("coachId") Long coachId);

    /**
     * 查询未收车的教练
     * @return
     */
	List<CoachVo> queryByCoachIsNotOff();

	/**
	 * 查询未出车的教练
	 * @return
	 */
	List<CoachVo> queryByCoachIsNotOn();

	List<Coach> queryTYCoaches();
	
	public void insertStatusRecord(CoachStatusRecord record);
	
	public void addLoginCount(Coach coach);
	
	public void addCoachInCount(CoachStatusRecord record);
	
	public void addCoachOutCount(CoachStatusRecord record);
	
	public CoachStatusRecord getLatestStatusRecord(CoachStatusRecord record);
}
