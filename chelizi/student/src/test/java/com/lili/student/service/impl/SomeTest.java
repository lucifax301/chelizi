package com.lili.student.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lili.student.dto.ExerciseExam;

import java.util.List;

/**
 * Created by ZLong on 2016/六月/17.
 */
public class SomeTest {
    public static void main(String[] args) {

        String list = "[{\"score\":99,\"examtime\":\"1111\",\"useTime\":\"12121221\",\"subject\":1,\"meqList\":[{\"qid\":0,\"ansRec\":\"124\",\"ansSta\":0},{\"qid\":0,\"ansRec\":\"124\",\"ansSta\":0}]},{\"score\":99,\"examtime\":\"1111\",\"useTime\":\"12121221\",\"subject\":1,\"meqList\":[{\"qid\":0,\"ansRec\":\"124\",\"ansSta\":0},{\"qid\":0,\"ansRec\":\"124\",\"ansSta\":0}]}]";

        List<ExerciseExam> datas = JSON.parseObject(list, new TypeReference<List<ExerciseExam>>() {

        });

        System.out.println(datas);
    }
}
