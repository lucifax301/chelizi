package com.lili.student.mapper.dao;

/**
 * Created by ZLong on 2016/六月/17.
 */
public class ExerciseExamPage {
    private long studentId;
    private int subject;
    private int pageNo;
    private int pageSize;
    private int startIndex;

    public ExerciseExamPage(long studentId, int subject, int pageNo, int pageSize) {
        this.studentId = studentId;
        this.subject = subject;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.startIndex = pageSize * (pageNo - 1);
    }

    public int getSubject() {
        return subject;
    }

    public void setSubject(int subject) {
        this.subject = subject;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }
}
