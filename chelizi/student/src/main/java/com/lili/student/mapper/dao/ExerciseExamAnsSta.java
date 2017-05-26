package com.lili.student.mapper.dao;

/**
 * Created by ZLong on 2016/六月/17.
 */
public class ExerciseExamAnsSta {

    private long studentId;
    private int subject;
    private int ansSta;

    public ExerciseExamAnsSta(long studentId, int subject, int ansSta) {
        this.studentId = studentId;
        this.subject = subject;
        this.ansSta = ansSta;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public int getSubject() {
        return subject;
    }

    public void setSubject(int subject) {
        this.subject = subject;
    }

    public int getAnsSta() {
        return ansSta;
    }

    public void setAnsSta(int ansSta) {
        this.ansSta = ansSta;
    }
}
