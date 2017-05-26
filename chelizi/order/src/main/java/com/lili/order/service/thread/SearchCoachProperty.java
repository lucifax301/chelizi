package com.lili.order.service.thread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.lili.coach.manager.CoachManager;
import com.lili.coach.vo.CoachVo;
import com.lili.common.util.StringUtil;
import com.lili.common.vo.ReqConstants;
import com.lili.order.dto.StudentClass;

public class SearchCoachProperty implements Callable<List<CoachVo>> {
	private Logger log = Logger.getLogger(SearchCoachProperty.class);
	
	private StudentClass studentClass;
	private List<Long> coachIds;
	private CoachManager coachManager;

	public SearchCoachProperty(StudentClass studentClass, List<Long> coachIds,
			CoachManager coachManager) {
		this.studentClass = studentClass;
		this.coachIds = coachIds;
		this.coachManager = coachManager;
	}

	@Override
	public List<CoachVo> call() throws Exception {
		long allStart=System.currentTimeMillis();
		List<CoachVo> list = new ArrayList<CoachVo>();
		try {
			//学员排班参数
			byte scDltype = studentClass.getDltype().byteValue();
			String scCourseId = studentClass.getCourseId().toString();
			
			//这里默认了这些教练都是有车的。正常出了车的教练都是有车的，如果没有车，会有异常。
			List<CoachVo> coachList=coachManager.getCoachesByIds(coachIds);
			for(CoachVo one:coachList){
				// 有的教练虽然出车了，但因为数据异常，车不见了。需要过滤掉，以避免异常。
				if(null == one.getDriveType()){
					log.debug("SearchCoachProperty-->coachId="+one.getCoachId()+",search no car!");
					continue;
				}
				int dltype=one.getDriveType().intValue(); //教练车型，通过默认教练车关联查询得到的
				String courses=one.getCourses();
				int wstate=one.getWstate();
				//0.接单距离
				if(null != one.getAcceptOrderDis() && null!=one.getDistance() &&
						one.getAcceptOrderDis() < one.getDistance()){
					if(log.isDebugEnabled()){
						log.debug("SearchCoachProperty-->coachId="+one.getCoachId()+",search real distance="+one.getDistance()+",and param="+one.getAcceptOrderDis());
					}
					continue;
				}
				//1.档位
				if(dltype != scDltype){
					if(log.isDebugEnabled()){
						log.debug("SearchCoachProperty-->coachId="+one.getCoachId()+",search real dltype="+dltype+",and param="+scDltype);
					}
					continue;
				}
				//2.科目	//为空不过滤
				if(StringUtil.isNotNullAndNotEmpty(scCourseId)){
					if(StringUtil.isNotNullAndNotEmpty(courses)){
						List<String> clist=Arrays.asList(courses.split(","));
						if(!clist.contains(scCourseId)){
							if(log.isDebugEnabled()){
								log.debug("SearchCoachProperty-->coachId="+one.getCoachId()+",search real courseid="+courses+",and param="+scCourseId);
							}
							continue;
						}
					}else {
						if(log.isDebugEnabled()){
							log.debug("SearchCoachProperty-->coachId="+one.getCoachId()+",search real courseid=null"+",and param="+scCourseId);
						}
						continue;
					}
				}
				//3.出车
/*				if(wstate != ReqConstants.COACH_STATUS_ON_WORK) {
					if(log.isDebugEnabled()){
						log.debug("SearchCoachProperty-->coachId="+one.getCoachId()+",search real coach is not work="+wstate);
					}
					continue;
				}*/
				list.add(one);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug("SearchCoachProperty-->studentId="+studentClass.getStudentId()+",seach end time cost="+(System.currentTimeMillis()-allStart));
		return list;
	}

}


























