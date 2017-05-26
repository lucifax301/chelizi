package com.lili.coach.service;

import java.util.Date;
import java.util.List;

import com.lili.cms.entity.ResponseMessage;
import com.lili.coach.dto.CarCheck;
import com.lili.coach.model.Coach;
import com.lili.coach.model.CoachBDTO;
import com.lili.coach.model.CoachVo;
import com.lili.log.model.LogCommon;
import com.lili.student.model.StudentNBDTO;


public  interface CMSCoachService   {

	/**
	 * 删除学员和教练的对应关系
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Long delSC(StudentNBDTO dto) throws Exception;

	/**
	 * 插入学员和教练的对应关系
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Long insertSC(StudentNBDTO dto) throws Exception;

	/**
	 * 更新学员表中的教练id
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Long updateSC(StudentNBDTO dto) throws Exception;

	public ResponseMessage findOne(long coachId) throws Exception;

	/**
	 * 计算教练总数和应付款
	 * @return
	 */
	public ResponseMessage findAccountVo() throws Exception;

	/**
	 * 分页
	 * @param name
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public  ResponseMessage findBatch(CoachBDTO dto) throws Exception;

	public  ResponseMessage findCoachCar(long coachId) throws Exception;

	public  Coach findByPhone(String phoneNum) throws Exception;

	public  ResponseMessage updateOne(LogCommon logCommon,Coach coach) throws Exception;

	public  ResponseMessage deleteCCRelation(LogCommon logCommon,long coachId,long carId) throws Exception;

	public  ResponseMessage updateCCRelation(LogCommon logCommon,long c_carId,long o_carId,long coachId) throws Exception;

	public  ResponseMessage insertOne(LogCommon logCommon,Coach coach) throws Exception;

	public  ResponseMessage insertCSRelation(LogCommon logCommon,String studentIds,long coachId) throws Exception;

	public  String findCoachInfo(CoachBDTO dto, String operateType) throws Exception;

	public  List<CoachVo> getExportSource(CoachBDTO dto) throws Exception ;

	public Long updateStates(String recombinateStrByComma) throws Exception;
	
	public  Integer queryTotalCoach(CoachBDTO dto) throws Exception;

	public Long insertCoachInfo(Coach coach);

	  /**
     * 锁定教练
     * @param userId    用户id
     * @param state        0-正常；1-临时封号；2-永久封号
     * @param reviveTime    解除封号的时间
     * @param note    封号理由
     * @return 0-操作成功；1-操作失败
     */
    public ResponseMessage lockCoach(long userId,int state,Date reviveTime,String note) throws Exception;  

	/**
	* 批量锁定教练
	* @param ids    用户id
	* @param state        0-正常；1-临时封号；2-永久封号
	* @param reviveTime    解除封号的时间
	* @param note    封号理由
	* @return 0-操作成功；1-操作失败
	*/
    public ResponseMessage lockCoaches(String ids,int state,Date reviveTime,String note) throws Exception; 
    
    /*
     * 通过手机号和身份证查找教练，其中一个匹配则返回
     */
    public List<Coach> findByNums(Coach coach) throws Exception;

	public String regCoachQuery(CoachBDTO dto);

	public String queryRegCoachDetail(CoachBDTO dto);

	public String checkRegCoach(CoachBDTO dto, String verifier);

	public String updateRegCheck(String userId, String drPhoto, String drPhoto2, String isNewDrPhoto, String cityName, List<CarCheck> carJsonList);

	public ResponseMessage getCoach(CoachBDTO dto) throws Exception;
  
  
}

