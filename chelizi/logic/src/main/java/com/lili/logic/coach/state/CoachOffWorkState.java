/**
 * 
 */
package com.lili.logic.coach.state;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.coach.dto.Coach;
import com.lili.coach.manager.CoachManager;
import com.lili.coach.service.CoachService;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.logic.state.AbstractCoachState;
import com.lili.order.vo.WaitOrderVo;

/**
 * @author linbo
 * 教练休息状态
 */
public class CoachOffWorkState extends AbstractCoachState
{
    @Autowired
    private CoachService coachService;
    
    @Autowired
    private CoachManager coachManager;
    
    /* (non-Javadoc)
     * @see com.lili.logic.service.ICoachState#handleDoStatus(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public ReqResult handleDoStatus(String coachId, String carId, String courses, String status, String tokenId)
    {
        ReqResult r = ReqResult.getFailed();

        byte st = 0;
        try
        {
        	Coach coachReg = coachManager.getCoachInfo(Long.parseLong(coachId));
        	if (coachReg != null && coachReg.getIsImport() != null && coachReg.getIsImport() == 0 ) {
        		//20161104城市判断，先写死，后期需修改
    			if (coachReg.getCityId() == null || (coachReg.getCityId() != null && coachReg.getCityId() != 100100 &&  
    					coachReg.getCityId() != 100101 && coachReg.getCityId() != 102100 && coachReg.getCityId() != 103100)) {
    				r.setCode(ResultCode.ERRORCODE.COACH_IS_NOT_IN_CITY);
    				r.setMsgInfo(ResultCode.ERRORINFO.COACH_IS_NOT_IN_CITY);
    				return r;
    			}
    		}
    		
            WaitOrderVo waitOrderVo = orderService.getCoachWait(Long.parseLong(coachId), null);
            if(waitOrderVo != null && waitOrderVo.getWaitComment() != null)
            {
                r.setCode(ResultCode.ERRORCODE.STUDENT_STATE__NOT_SUPPORT);
                return r;
            }
            st = Byte.parseByte(status.trim());
        }
        
        catch (Exception e)
        {
            logger.error("handleDoStatus:" + coachId + "," + carId + "," + courses + "," + status, e);
            r.setCode(ResultCode.ERRORCODE.PARAMERROR);
            r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
            return r;
        }
        if (st == ReqConstants.COACH_STATUS_ON_WORK)
        {
            r = coachService.doStatus(coachId, carId, courses, status, tokenId);
            if (r.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS))
            {
                Coach coach = new Coach();
                coach.setCoachId(Long.parseLong(coachId));
                coach.setWstate(ReqConstants.COACH_STATUS_ON_WORK);
                //设置出车课程
                coach.setCourses(courses);
                //设置出车id //收车时可用为空
                try {
                    coach.setCoachCarId(Integer.parseInt(carId));
                } catch (NumberFormatException e) {
                    logger.error("carId:" + carId, e);
                }
                coach.setEventDesc("");
                coachManager.updateCoach(coach);
            }
        }
        else
        {
            super.handleDoStatus(coachId, carId, courses, status, tokenId);
        }

        return r;
    }
    
    @Override
    public ReqResult getCurStateContext(String coachId)
    {
        ReqResult r = super.getCurStateContext(coachId);
        return r;
    }
}
