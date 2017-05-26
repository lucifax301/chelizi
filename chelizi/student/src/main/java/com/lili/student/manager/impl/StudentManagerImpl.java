package com.lili.student.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.lili.common.constant.OrderConstant;
import com.lili.common.util.MyRowBounds;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.StringUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqConstants;
import com.lili.pay.manager.MoneyManager;
import com.lili.student.dto.MicroClass;
import com.lili.student.dto.Student;
import com.lili.student.dto.StudentAccount;
import com.lili.student.dto.StudentVip;
import com.lili.student.dto.SubjectVideo;
import com.lili.student.manager.StudentManager;
import com.lili.student.mapper.dao.MicroClassMapper;
import com.lili.student.mapper.dao.StudentMapper;
import com.lili.student.mapper.dao.StudentPage;
import com.lili.student.mapper.dao.StudentVipMapper;
import com.lili.student.mapper.dao.SubjectVideoMapper;

//@Service("studentManager")
public class StudentManagerImpl implements StudentManager {

	private Logger log = Logger.getLogger(StudentManagerImpl.class);
	
	@Autowired
	private StudentMapper studentMapper;
    @Autowired
    private StudentVipMapper studentVipMapper;
    @Autowired
    private MoneyManager moneyManager;
    @Autowired
    private MicroClassMapper mocroClassMapper;
    @Autowired
    private SubjectVideoMapper subjectVideoMapper;
    
	@Resource(name = "studentProducer")
	DefaultMQProducer studentProducer;
	
	@Autowired
    RedisUtil redisUtil;
	
	@Override
	public List<Student> getStudent() {
		return studentMapper.getAll(null);
	}

	@Override
	public StudentVip getStudentVipInfoByStudentId(Long studentId) {
		StudentVip studentVip = null;

		Student student = getStudentInfo(studentId);
		if (null != student) {
			Integer vipId = student.getVipId();
			studentVip = getStudentVipInfoByVipId(vipId);
		}
		return studentVip;
	}

	@Override
	public StudentVip getStudentVipInfoByVipId(Integer vipId) {
		StudentVip studentVip = null;
		if (null != vipId) {
			studentVip = redisUtil.get(REDISKEY.STUDENT_VIP_INFO + vipId.intValue());
			if (null == studentVip) {
				studentVip = studentVipMapper.selectByPrimaryKey(vipId);
				if (null != studentVip) {
					redisUtil.set(REDISKEY.STUDENT_VIP_INFO + vipId, studentVip, 3600 * 24);
				}
			}
		}
		return studentVip;
	}

	@Override
	public Student getStudentInfo(long id) {
	    if (id == 0)
        {
            return null;
        }
	    Student student = redisUtil.get(REDISKEY.STUDENT_INFO + id);
	    if (student == null)
        {
	        student = studentMapper.selectByPrimaryKey(id);
	        if (student != null)
            {
		        String name = student.getName();
		        if(null == name || "".equals(name.trim())){
		        	student.setName(ReqConstants.USER_NAME_STUDENT_DEFAULT);
		        }
	            redisUtil.set(REDISKEY.STUDENT_INFO + id, student,3600*24);
            }
        }
		return student;
	}
	
	@Override
	public Student getStudentByPhoneNum(String phoneNum) {
		return studentMapper.selectByPhoneNum(phoneNum);
	}
	

	@Override
	public Student getStudentByIdNumber(String idNumber) {
		return studentMapper.selectByIdNumber(idNumber.trim());
	}

	@Override
	public long addStudent(Student student) {
		//return studentMapper.insertAndGetStudentId(student);
		return studentMapper.insertSelective(student);
	}

	@Override
	public long updateStudent(Student student) {
		//1. 不要做内存连续同步复制，防止内存异常导致问题，尽量直接从数据库中获取
		//2. 灰度要求，无论值是否存在，如果没有更新就必须删除
		long result=-1;
		try {
			if(student != null){
				//先更新后删除，防止异常
				result=studentMapper.updateByPrimaryKeySelective(student);
				redisUtil.delete(REDISKEY.STUDENT_INFO + student.getStudentId());
				log.info("************************************* delete key :"+ REDISKEY.STUDENT_INFO + student.getStudentId());
				student = studentMapper.selectByPrimaryKey(student.getStudentId());
		        if (student != null)
	            {
			        String name = student.getName();
			        if(null == name || "".equals(name.trim())){
			        	student.setName(ReqConstants.USER_NAME_STUDENT_DEFAULT);
			        }
			        
			        if (StringUtil.isNotNullAndNotEmpty(student.getName()) || StringUtil.isNotNullAndNotEmpty(student.getPhoneNum()) || StringUtil.isNotNullAndNotEmpty(student.getHeadIcon())) {
						Message msg=new Message();
						msg.setTopic(studentProducer.getCreateTopicKey());
						msg.setTags(OrderConstant.RMQTAG.BBS_UPDATE_USER_INFO);
						msg.setBody(SerializableUtil.serialize(student));
						studentProducer.send(msg);
			        }
			        
		            redisUtil.set(REDISKEY.STUDENT_INFO + student.getStudentId(), student,3600*24);
	            }
			}
		} catch (Exception e) {
			log.error("************************************ updateStudent Exception :" +  e.getMessage());
			e.printStackTrace();
		}
		return result;

	}

	@Override
	public long deleteStudent(long id) {
	    redisUtil.delete(REDISKEY.STUDENT_INFO + id);
		return studentMapper.deleteByPrimaryKey(id);
	}

	@Override
	public long getCount() {
		return studentMapper.countAll(null);
	}

	@Override
	public boolean isExist(String phoneNum) {
		Student student = new Student();
		student.setPhoneNum(phoneNum);
		
		long count = studentMapper.countAll(student);

		return count > 0;
		
	}

	@Override
	public StudentAccount getStudentMoney(Long studentId) {
		return studentMapper.getStudentMoney(studentId);
	}

	@Override
	public List<Student> getStudentByCoachId(Long coachId, int pageNum, int pageSize) {
		StudentPage page = new StudentPage(pageNum, pageSize, coachId);
		return studentMapper.getStudentByCoachId(page);
	}

    /* (non-Javadoc)
     * @see com.lili.student.manager.StudentManager#updateStudentMoney(com.lili.student.dto.StudentAccount)
     */
    @Override
    public void updateStudentAccount(StudentAccount studentAccount)
    {
        if (studentAccount != null)
        {
            studentMapper.updateStudentMoney(studentAccount);
        }
    }

    /* (non-Javadoc)
     * @see com.lili.student.manager.StudentManager#insertStudentAccount(com.lili.student.dto.StudentAccount)
     */
    @Override
    public void insertStudentAccount(StudentAccount studentAccount)
    {
        if (studentAccount != null)
        {
            studentMapper.insertStudentAccount(studentAccount);
        }
    }

	@Override
	public long checkStudentCorrectPw(String passwd, Long studentId) {
		return  studentMapper.checkStudentCorrectPw(passwd, studentId);
	}

	@Override
	public void updateStudentPassword(String passwd, Long studentId) {
			studentMapper.updateStudentPassword(passwd, studentId);
	}

	@Override
	public List<Student> getStudentsByIds(List<Long> stuIds) {
		return studentMapper.getStudentsByIds(stuIds);
		
	}

	@Override
	public long addStudentList(List<Student> students) {
		int rs = 0;
		for (int i = 0; i < students.size(); i++){
			rs += studentMapper.insertSelective(students.get(i));
		}
		return rs;
	}

	@Override
	public boolean payStudentMoney(StudentAccount studentAccount) {
		try {
            moneyManager.addStudentMoney(studentAccount.getStudentId(), studentAccount.getMoney());
        } catch (Exception e) {
        	e.printStackTrace();
            return false;
        }
        return true;
	}

	@Override
	public boolean payStudentMoney(List<StudentAccount> studentAccount) {
		try {
			if (studentAccount != null) {
				 for (StudentAccount c : studentAccount) {
					 payStudentMoney(c);
			     }
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<MicroClass> getMicroClass(MicroClass mocroClass) {
		List<MicroClass> mcList = new ArrayList<MicroClass>();
		try {
			mcList = mocroClassMapper.selectByLevelTwo(mocroClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mcList;
	}
	
	public List<Student> queryByObjectAnd(Student student,int start,int pageSize){
		return studentMapper.queryByObjectAnd(student, " and vipPackageId is null limit "+start+","+pageSize+" ");
	}
	public int countByObjectAnd(Student student){
		return studentMapper.countByObjectAnd(student, " and vipPackageId is null");
	}

	@Override
	public List<SubjectVideo> getSubjectVideo(String cityId,String examId,String subject, String fileType,String id, String pageNo, String pageSize) {

		SubjectVideo subjectVideo = new SubjectVideo();
		if (id != null && !"".equals(id)) {
			List<SubjectVideo> subjectList = new ArrayList<SubjectVideo>();
			subjectVideo = subjectVideoMapper.selectByPrimaryKey(Integer.parseInt(id));
			subjectList.add(subjectVideo);
			return subjectList;
		}
		else {
			MyRowBounds myRowBounds = new MyRowBounds();
			if(!"".equals(pageNo) && pageNo != null && !"".equals(pageSize) && pageSize != null){
				myRowBounds = new MyRowBounds(Integer.parseInt(pageNo),Integer.parseInt(pageSize));
				subjectVideo.setMyRowBounds(myRowBounds );
			}
			
			if(cityId != null && !"".equals(cityId)) {
				subjectVideo.setCityId(Integer.parseInt(cityId));
			}
			if(examId != null && !"".equals(examId)) {
				subjectVideo.setExamId(Integer.parseInt(examId));
			}
			if(fileType != null && !"".equals(fileType)) {
				subjectVideo.setFileType(Integer.parseInt(fileType));
			}
			subjectVideo.setSubject(Integer.parseInt(subject));
			
			return subjectVideoMapper.querySubjectVideoList(subjectVideo);
		}
	}

	
	public List<Student> queryByUnionId(String unionId, String accType){
		if ("1".equals(accType.trim()))
			return studentMapper.queryByObjectAnd(new Student(), "and unionId = '" + unionId + "'");
		else if ("2".equals(accType.trim()))
			return studentMapper.queryByObjectAnd(new Student(), "and qqOpenId = '" + unionId + "'");
		else 
			return null;
	}

	@Override
	public List<String> getNewRegisterMobile(String date1, String date2,
			Integer schoolId) {
		return studentMapper.selectNewRegisterMobile(date1, date2, schoolId);
	}

	@Override
	public Student getStudentNear(Student student) {
		return studentMapper.getStudentNear(student);
	}
	
	
	
	

}
