package com.lili.pay.mapper.dao;


import java.util.List;

import com.lili.coach.dto.CoachAccount;
import com.lili.pay.dto.UserMoneyDto;
import com.lili.pay.wallet.MoneyPage;
import com.lili.student.dto.StudentAccount;

public interface UserMoneyDtoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserMoneyDto record);

    int insertSelective(UserMoneyDto record);

    UserMoneyDto selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserMoneyDto record);

    int updateByPrimaryKey(UserMoneyDto record);
    
    List<UserMoneyDto> getUserMoneyDtoByPage(MoneyPage page);
    
    /**
     * 根据学员id获取学员金额
     * @param studentId
     * @return
     */
    StudentAccount getStudentMoney(Long studentId);
    
    /**
     * 更新学员帐户
     * @param studentAccount
     */
    void updateStudentMoney(StudentAccount studentAccount);
    
    /**
     * 添加学员帐户
     * @param studentAccount
     */
    void insertStudentAccount(StudentAccount studentAccount);
    
    /**
     * 根据教练id获取教练金额
     * @param coachId
     * @return
     */
    CoachAccount getCoachMoney(Long coachId);

    /**
     * 更新教练帐户
     * @param coachAccount
     * @return
     */
    void updateCoachAccount(CoachAccount coachAccount);

    /**
     * 插入教练帐户
     * @param c
     */
    void insertCoachAccount(CoachAccount c);

	int queryIsExitOrderId(String payOrderId);
}