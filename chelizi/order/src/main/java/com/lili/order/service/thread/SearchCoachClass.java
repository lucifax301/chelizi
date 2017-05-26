package com.lili.order.service.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.lili.coach.vo.CoachVo;
import com.lili.common.constant.OrderConstant;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.DateUtil;
import com.lili.order.dto.StudentClass;
import com.lili.order.service.CoachClassService;
import com.lili.order.vo.CoachClassVo;

public class SearchCoachClass implements Callable<List<Long>> {
	private Logger log = Logger.getLogger(SearchCoachClass.class);

	private StudentClass studentClass;
	private List<Long> coachIds;
	private CoachClassService coachClassService;

	public SearchCoachClass(StudentClass studentClass, List<Long> coachIds,
			CoachClassService coachClassService) {
		this.studentClass = studentClass;
		this.coachIds = coachIds;
		this.coachClassService = coachClassService;
	}

	@Override
	public List<Long> call() throws Exception {
		long allStart=System.currentTimeMillis();
		List<Long> list = new ArrayList<Long>();
		try {
			Date pstart = studentClass.getCstart();
			Date pend = studentClass.getCend();
			// 获取教练班次信息
			if(coachIds!=null && !coachIds.isEmpty()){
				//获取教练所有排班，不管是否有价格
				Map<Long,List<CoachClassVo>> ccmap=coachClassService.queryByCoachDateWitNoPrice(new Date(), coachIds);
				for(Long oneId:coachIds){
					CoachClassVo buz=coachClassService.isCoachIdle(ccmap.get(oneId), null,null,pstart, pend,false);
					if(buz==null){
							list.add(oneId);
					} else {
						if(log.isDebugEnabled()){
							log.debug("SearchCoachClass-->coachId="+oneId+",search coach has class="+buz.getCcid());
						}
					}
				}
			}		

		} catch (Exception e) {
			log.error("SearchCoachClass-->Exception-->", e);
		}
		log.debug("SearchCoachClass-->studentId="+studentClass.getStudentId()+",seach end time cost="+(System.currentTimeMillis()-allStart));
		return list;
		
	}

}
