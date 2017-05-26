package com.lili.student.service;

import com.lili.common.vo.ReqResult;
import com.lili.student.dto.ExerciseError;
import com.lili.student.dto.ExerciseExam;

import java.util.List;

/**
 * Created by ZLong on 2016/六月/17.
 */
public interface StudentExerciseService {
    ReqResult submitmyerrques(String version, long studentId, List<ExerciseError> datas);

    ReqResult removemyerrorques(String version, long studentId, String qid);

    ReqResult getmyerrques(String version, long studentId);

    ReqResult submitmycollection(String version, long studentId, String qid, int subject, int chapter, int ansSta);

    ReqResult cancelmycollection(String version, long studentId, String qid);

    ReqResult getmycollection(String version, long studentId);

    ReqResult submitmockexamrecord(String version, long studentId,  List<ExerciseExam> datas);

    ReqResult getmockexamrecord(String version, long studentId, int subject, int pageNo, int pageSize);
}
