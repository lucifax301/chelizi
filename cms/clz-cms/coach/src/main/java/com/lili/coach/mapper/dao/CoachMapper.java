/**
 * 
 */
package com.lili.coach.mapper.dao;

import java.util.HashMap;
import java.util.List;

import com.lili.coach.model.CCOVo;
import com.lili.coach.model.Coach;
import com.lili.coach.model.CoachAccount;
import com.lili.coach.model.CoachAccountVo;
import com.lili.coach.model.CoachBDTO;
import com.lili.coach.model.CoachRegist;
import com.lili.coach.model.CoachVo;
import com.lili.student.model.StudentNBDTO;
import com.lili.student.model.StudentVo;

public interface CoachMapper {

	public Coach findOne(long coachId) throws Exception;

	/**
	 * 通过手机号查学员，参数中包含学员ID，查其他学员有没有使用参数中的手机号
	 * 
	 * @param params
	 * @return
	 */
	public Coach findByPhone(String phoneNum) throws Exception;

	public List<Coach> findByNums(Coach coach) throws Exception;
	
	public List<Coach> getCoach(CoachBDTO dto) throws Exception;

	/**
	 * 删除学员和教练的对应关系
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Long delSC(StudentNBDTO dto) throws Exception;

	/**
	 * 插入学员和教练的对应关系
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Long insertSC(StudentNBDTO dto) throws Exception;

	/**
	 * 更新学员表中的教练id
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Long updateSC(StudentNBDTO dto) throws Exception;

	/**
	 * 批量修改教练状态为0
	 * 
	 * @param params
	 *            必含coachIds
	 * @return
	 */
	public Long updateStates(String coachIds) throws Exception;

	/**
	 * 查询教练关联的车、订单信息
	 * 
	 * @param params
	 * @return
	 */
	public List<CCOVo> findCoachCar(long coachId) throws Exception;

	public Coach findOneDetails(long coachId) throws Exception;

	/**
	 * 获取教练列表
	 * 
	 * @return
	 */
	public List<CoachVo> findBatch(CoachBDTO dto) throws Exception;

	/**
	 * 计算教练总数和应付款
	 * 
	 * @return
	 */
	public CoachAccountVo findAccountVo() throws Exception;

	public long updateOne(Coach coach) throws Exception;

	public long insertSelective(Coach coach) throws Exception;

	public Integer findTotalCoach(CoachBDTO dto) throws Exception;

	/**
	 * 批量插入教练和学员对应关系
	 * 
	 * @return
	 */
	public long insertCSRelation(List<StudentVo> list) throws Exception;

	/**
	 * 更新教练和车的关系
	 * 
	 * @param 包含o_carId教练原车、c_carId教练要绑定的车和coachId教练的id
	 * @return
	 */
	public long updateCCRelation(HashMap<String, Object> param) throws Exception;

	/**
	 * 更新教练和车的关系(已在数据库存在,但是isExist=1)
	 * 
	 * @param params
	 *            包含o_carId教练原车、c_carId教练要绑定的车和coachId教练的id
	 * @return
	 */
	public long updateOCCRelation(HashMap<String, Object> param) throws Exception;

	/**
	 * 删除教练和车的关系
	 * 
	 * @param params
	 *            包含carId教练车和coachId教练的coachId
	 * @return
	 */
	public long deleteCCRelation(HashMap<String, Object> param) throws Exception;

	/**
	 * 插入教练和车的关系
	 * 
	 * @param params
	 *            包含c_carId教练要绑定的车和coachId教练的id
	 * @return
	 */
	public long insertCCRelation(HashMap<String, Object> param) throws Exception;

	/**
	 * 查看车和教练的关系是否存在
	 * 
	 * @param params
	 *            包含carId教练要绑定的车和coachId教练的id
	 * @return
	 */
	public Integer findCCRelation(HashMap<String, Object> param) throws Exception;

	public CoachAccount findCoachInfo(CoachBDTO dto) throws Exception;

	public Long findMID(CoachBDTO dto) throws Exception;

	public long findBatchTotal(CoachBDTO dto) throws Exception;

	public void addCoachTeachTime(Coach coach) throws Exception;

	public int getCoachClassTime(CoachBDTO dto) throws Exception;

	public int getCoachClassCount(CoachBDTO dto) throws Exception;

	public int getCoachStudentCount(CoachBDTO dto) throws Exception;

	public List<CoachRegist> queryRegCoachList(CoachBDTO dto);

	public Coach queryRegCoachDetail(CoachBDTO dto);

	public Long updateRegCoach(CoachBDTO dto);

}
