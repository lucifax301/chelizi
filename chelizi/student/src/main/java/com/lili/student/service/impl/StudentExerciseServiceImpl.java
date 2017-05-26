package com.lili.student.service.impl;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.lili.common.constant.JpushConstant;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.student.dto.*;
import com.lili.student.manager.StudentManager;
import com.lili.student.mapper.dao.*;
import com.lili.student.service.StudentExerciseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * Created by ZLong on 2016/六月/15.
 * <p/>
 * 文科练习
 */
public class StudentExerciseServiceImpl implements StudentExerciseService {

    private static Logger logger = LoggerFactory.getLogger(StudentExerciseServiceImpl.class);

    @Autowired
    private StudentManager studentManager;

    @Autowired
    private ExerciseCollectionMapper exerciseCollectionMapper;
    @Autowired
    private ExerciseErrorMapper exerciseErrorMapper;
    @Autowired
    private ExerciseExamMapper exerciseExamMapper;
    @Autowired
    private ExerciseExamDetailMapper exerciseExamDetailMapper;
    
	@Resource(name = "jpushProducer")
	private DefaultMQProducer jpushProducer;

    /**
     * 学员提交我的错题
     *
     * @param version
     * @param studentId
     * @param datas     错题数据
     * @return
     */
    @Override
    public ReqResult submitmyerrques(String version, long studentId, List<ExerciseError> datas) {
        ReqResult result = ReqResult.getFailed();
        if (null != datas) {
            Student student = studentManager.getStudentInfo(studentId);
            if (null != student) {
                for (ExerciseError data : datas) {
                    ExerciseErrorKey exerciseErrorKey = new ExerciseErrorKey();
                    exerciseErrorKey.setStudentId(studentId);
                    exerciseErrorKey.setQid(data.getQid());

                    ExerciseError exerciseError = exerciseErrorMapper.selectByPrimaryKey(exerciseErrorKey);

                    boolean isNew = false;
                    if (null == exerciseError) {
                        isNew = true;
                        exerciseError = new ExerciseError();
                        exerciseError.setStudentId(studentId);
                        exerciseError.setQid(data.getQid());
                    }

                    exerciseError.setSubject(data.getSubject());
                    exerciseError.setChapter(data.getChapter());
                    exerciseError.setAnsSta(data.getAnsSta());
                    exerciseError.setUpdateTime(new Date());

                    if (isNew) {
                        exerciseErrorMapper.insert(exerciseError);
                    } else {
                        exerciseErrorMapper.updateByPrimaryKey(exerciseError);
                    }
                }
                result = ReqResult.getSuccess();
            } else {
                result.setCode(ResultCode.ERRORCODE.NO_USER);
                result.setMsgInfo(ResultCode.ERRORINFO.NO_USER);
            }
        }
        return result;
    }

    /**
     * 学员移除某个错题
     *
     * @param version
     * @param studentId
     * @param qid       题目Id
     * @return
     */
    @Override
    public ReqResult removemyerrorques(String version, long studentId, String qid) {
        ReqResult result = ReqResult.getFailed();
        Student student = studentManager.getStudentInfo(studentId);
        if (null != student) {
            ExerciseErrorKey exerciseErrorKey = new ExerciseErrorKey();
            exerciseErrorKey.setStudentId(studentId);
            exerciseErrorKey.setQid(qid);

            if (exerciseErrorMapper.deleteByPrimaryKey(exerciseErrorKey) > 0) {
                result = ReqResult.getSuccess();
            }
        } else {
            result.setCode(ResultCode.ERRORCODE.NO_USER);
            result.setMsgInfo(ResultCode.ERRORINFO.NO_USER);
        }
        return result;
    }

    /**
     * 学员获取我的错题
     *
     * @param version
     * @param studentId
     * @return
     */
    @Override
    public ReqResult getmyerrques(String version, long studentId) {
        ReqResult result = ReqResult.getFailed();
        Student student = studentManager.getStudentInfo(studentId);
        if (null != student) {
            List<ExerciseError> datas = exerciseErrorMapper.selectByStudentId(studentId);
            result = ReqResult.getSuccess();

            Map<String, Object> map = new HashMap<>();
            map.put("list", datas);
            result.setData(map);
        } else {
            result.setCode(ResultCode.ERRORCODE.NO_USER);
            result.setMsgInfo(ResultCode.ERRORINFO.NO_USER);
        }
        return result;
    }

    /**
     * 学员提交我的收藏
     *
     * @param version
     * @param studentId
     * @param qid       题目Id
     * @param subject   1表示科目一,4表示科目四
     * @param chapter   1~7表示章节一到七
     * @param ansSta    最近回答对错记录 数字1为对,2为错,0为未答过
     * @return
     */
    @Override
    public ReqResult submitmycollection(String version, long studentId, String qid, int subject, int chapter, int ansSta) {
        ReqResult result = ReqResult.getFailed();
        Student student = studentManager.getStudentInfo(studentId);
        if (null != student) {
            ExerciseCollectionKey exerciseCollectionKey = new ExerciseCollectionKey();
            exerciseCollectionKey.setStudentId(studentId);
            exerciseCollectionKey.setQid(qid);


            ExerciseCollection exerciseCollection = exerciseCollectionMapper.selectByPrimaryKey(exerciseCollectionKey);

            boolean isNew = false;
            if (null == exerciseCollection) {
                isNew = true;
                exerciseCollection = new ExerciseCollection();
                exerciseCollection.setStudentId(studentId);
                exerciseCollection.setQid(qid);
            }

            exerciseCollection.setSubject(subject);
            exerciseCollection.setChapter(chapter);
            exerciseCollection.setAnsSta(ansSta);
            exerciseCollection.setUpdateTime(new Date());

            int op = 0;
            if (isNew) {
                op = exerciseCollectionMapper.insert(exerciseCollection);
            } else {
                op = exerciseCollectionMapper.updateByPrimaryKey(exerciseCollection);
            }

            if (op > 0) {
                result = ReqResult.getSuccess();
            }
        } else {
            result.setCode(ResultCode.ERRORCODE.NO_USER);
            result.setMsgInfo(ResultCode.ERRORINFO.NO_USER);
        }
        return result;
    }

    /**
     * 学员取消收藏
     *
     * @param version
     * @param studentId
     * @param qid       题目Id
     * @return
     */
    @Override
    public ReqResult cancelmycollection(String version, long studentId, String qid) {
        ReqResult result = ReqResult.getFailed();
        Student student = studentManager.getStudentInfo(studentId);
        if (null != student) {
            ExerciseCollectionKey exerciseCollectionKey = new ExerciseCollectionKey();
            exerciseCollectionKey.setStudentId(studentId);
            exerciseCollectionKey.setQid(qid);

            if (exerciseCollectionMapper.deleteByPrimaryKey(exerciseCollectionKey) > 0) {
                result = ReqResult.getSuccess();
            }
        } else {
            result.setCode(ResultCode.ERRORCODE.NO_USER);
            result.setMsgInfo(ResultCode.ERRORINFO.NO_USER);
        }
        return result;
    }

    /**
     * 学员获取我的收藏
     *
     * @param version
     * @param studentId
     * @return
     */
    @Override
    public ReqResult getmycollection(String version, long studentId) {
        ReqResult result = ReqResult.getFailed();
        Student student = studentManager.getStudentInfo(studentId);
        if (null != student) {
            List<ExerciseCollection> datas = exerciseCollectionMapper.selectByStudentId(studentId);
            result = ReqResult.getSuccess();
            Map<String, Object> map = new HashMap<>();
            map.put("list", datas);
            result.setData(map);
        } else {
            result.setCode(ResultCode.ERRORCODE.NO_USER);
            result.setMsgInfo(ResultCode.ERRORINFO.NO_USER);
        }
        return result;
    }

    /**
     * 学员提交我的模拟考试记录
     *
     * @param version
     * @param studentId
     * @param datas     考试数据
     * @return
     */
    @Override
    public ReqResult submitmockexamrecord(String version, long studentId, List<ExerciseExam> datas) {
        ReqResult result = ReqResult.getFailed();

        try {
			if (null != datas) {
			    Student student = studentManager.getStudentInfo(studentId);
			    if (null != student) {

			        for (ExerciseExam data : datas) {

			            ExerciseExam exerciseExam = new ExerciseExam();
			            exerciseExam.setStudentId(studentId);
			            exerciseExam.setScore(data.getScore());
			            exerciseExam.setExamtime(data.getExamtime());
			            exerciseExam.setUsetime(data.getUsetime());
			            exerciseExam.setSubject(data.getSubject());

			            if (exerciseExamMapper.insert(exerciseExam) > 0) {
			                List<ExerciseExamDetail> exerciseExamDetails = data.getMeqList();
			                if (null != exerciseExamDetails && exerciseExamDetails.size() > 0) {
			                    for (ExerciseExamDetail detail : exerciseExamDetails) {
			                        detail.setExerciseExamId(exerciseExam.getId());
			                        exerciseExamDetailMapper.insert(detail);
			                    }
			                }
			            }
			            result = ReqResult.getSuccess();
			        }
			        
			        //判断是否成绩超过94分3次，如果是推送消息
			        ExerciseExam record = new ExerciseExam();
			        record.setStudentId(student.getStudentId());
			        Integer count = exerciseExamMapper.queryExerScoreSum(record);
			        if(count == 3){
			        	JpushMsg jmsg = new JpushMsg();
			        	jmsg.setAlter("模拟考试达标，可以预约科目一啦！");
			        	jmsg.setUserId(student.getStudentId());
			        	jmsg.setOperate(JpushConstant.OPERATE.STUCOURSEONO);
			        	Message jpush = new Message();
			        	jpush.setTopic(jpushProducer.getCreateTopicKey());
			        	jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
			        	jpush.setBody(SerializableUtil.serialize(jmsg));
			        	jpushProducer.send(jpush);
			        }
			        
			    } else {
			        result.setCode(ResultCode.ERRORCODE.NO_USER);
			        result.setMsgInfo(ResultCode.ERRORINFO.NO_USER);
			    }
			}
		} catch (MQClientException e) {
			e.printStackTrace();
			result.setCode(ResultCode.ERRORCODE.EXCEPTION);
			result.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
	        return result;
		} catch (RemotingException e) {
			e.printStackTrace();
			result.setCode(ResultCode.ERRORCODE.EXCEPTION);
			result.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
	        return result;
		} catch (MQBrokerException e) {
			e.printStackTrace();
			result.setCode(ResultCode.ERRORCODE.EXCEPTION);
			result.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
	        return result;
		} catch (InterruptedException e) {
			e.printStackTrace();
			result.setCode(ResultCode.ERRORCODE.EXCEPTION);
			result.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
	        return result;
		}
        return result;
    }

    /**
     * 学员获取我的模拟考试记录
     *
     * @param version
     * @param studentId
     * @param subject   1表示科目一,4表示科目四
     * @param pageNo    分页 第几页
     * @param pageSize  分页 每页条目
     * @return
     */
    @Override
    public ReqResult getmockexamrecord(String version, long studentId, int subject, int pageNo, int pageSize) {
        ReqResult result = ReqResult.getFailed();
        Student student = studentManager.getStudentInfo(studentId);
        if (null != student) {


            Map<String, Integer> qStatus = new HashMap<String, Integer>();

            ExerciseExamPage page = new ExerciseExamPage(studentId, subject, pageNo, pageSize);
            List<ExerciseExam> exams = exerciseExamMapper.selectByStudentIdWithPage(page);

            // int undoNum = 0;
            int corrNum = 0;
            int errNum = 0;

            if (null != exams && exams.size() > 0) {
                // undoNum = exerciseExamDetailMapper.countExerciseExamDetailAnsStaByStudentIdAndAnsSta(new ExerciseExamAnsSta(studentId, subject, 0));
                for (ExerciseExam exam : exams) {
                    List<ExerciseExamDetail> details = exerciseExamDetailMapper.selectByExerciseExamId(exam.getId());
                    if (null != details && details.size() > 0) {
                        exam.setMeqList(details);
                    }
                }
            }
            corrNum = exerciseExamDetailMapper.countExerciseExamDetailAnsStaByStudentIdAndAnsSta(new ExerciseExamAnsSta(studentId, subject, 1));
            errNum = exerciseExamDetailMapper.countExerciseExamDetailAnsStaByStudentIdAndAnsSta(new ExerciseExamAnsSta(studentId, subject, 2));

            Map<String, Object> map = new HashMap<>();

            map.put("corrNum", corrNum);
            map.put("errNum", errNum);
            if (null != exams && exams.size() > 0) {
                map.put("list", exams);
            }

            result = ReqResult.getSuccess();
            result.setData(map);

        } else {
            result.setCode(ResultCode.ERRORCODE.NO_USER);
            result.setMsgInfo(ResultCode.ERRORINFO.NO_USER);
        }
        return result;
    }
}
