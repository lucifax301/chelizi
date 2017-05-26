package com.lili.student.service.impl;

import com.lili.common.vo.ReqResult;
import com.lili.student.dto.ExerciseExam;
import com.lili.student.mapper.dao.ExerciseExamMapper;
import com.lili.student.service.StudentExerciseService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * StudentExercise Tester.
 *
 * @author ZLong
 * @version 1.0
 * @since <pre>六月 17, 2016</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-init.xml"})
public class StudentExerciseServiceImplTest {

    @Autowired
    private StudentExerciseService studentExerciseService;
    @Autowired
    private ExerciseExamMapper exerciseExamMapper;


    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getmyerrques(String version, long studentId)
     */
    @Test
    public void testGetmyerrques() throws Exception {
        ReqResult result = studentExerciseService.getmyerrques("", 100000005);
        System.out.println(result);
    }

    /**
     * Method: removemyerrorques(String version, long studentId, String qid)
     */
    @Test
    public void testRemovemyerrorques() throws Exception {
        ReqResult result = studentExerciseService.removemyerrorques("", 100000005, "1");
        System.out.println(result);
    }

    /**
     * Method: submitmycollection(String version, long studentId, String qid, int subject, int chapter, int ansSta)
     */
    @Test
    public void testSubmitcollection() throws Exception {
        ReqResult result = studentExerciseService.submitmycollection("", 100000005, "1", 1, 1, 1);
        System.out.println(result);
    }

    /**
     * Method: getmycollection(String version, long studentId)
     */
    @Test
    public void testGetcollection() throws Exception {
        ReqResult result = studentExerciseService.getmycollection("", 100115138);
        System.out.println(result);
    }

    /**
     * Method: cancelmycollection(String version, long studentId, String qid)
     */
    @Test
    public void testCancelcollection() throws Exception {
        ReqResult result = studentExerciseService.cancelmycollection("", 100000005, "1");
        System.out.println(result);
    }

    /**
     * Method: getmockexamrecord(String version, long studentId, int subject, int pageNo, int pageSize)
     */
    @Test
    public void testGetmockexamrecord() throws Exception {
        ReqResult result = studentExerciseService.getmockexamrecord("", 100115355, 1, 1, 100);
        System.out.println(result);
    }

    @Test
    public void testExerciseExamMapperInsert() {
        ExerciseExam exerciseExam = new ExerciseExam();
        exerciseExam.setStudentId(1L);
        exerciseExam.setScore(1);
        exerciseExam.setExamtime(1);
        exerciseExam.setUsetime(1);
        exerciseExam.setSubject(1);
        exerciseExamMapper.insert(exerciseExam);
        System.out.println(exerciseExam);
    }
} 
