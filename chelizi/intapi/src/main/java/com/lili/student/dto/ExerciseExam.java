package com.lili.student.dto;

import java.io.Serializable;
import java.util.List;

public class ExerciseExam implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Long studentId;
    private Integer score;
    private Integer examtime;
    private Integer usetime;
    private Integer subject;
    private List<ExerciseExamDetail> meqList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getExamtime() {
        return examtime;
    }

    public void setExamtime(Integer examtime) {
        this.examtime = examtime;
    }

    public Integer getUsetime() {
        return usetime;
    }

    public void setUsetime(Integer usetime) {
        this.usetime = usetime;
    }

    public Integer getSubject() {
        return subject;
    }

    public void setSubject(Integer subject) {
        this.subject = subject;
    }

    public List<ExerciseExamDetail> getMeqList() {
        return meqList;
    }

    public void setMeqList(List<ExerciseExamDetail> meqList) {
        this.meqList = meqList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", studentId=").append(studentId);
        sb.append(", score=").append(score);
        sb.append(", examtime=").append(examtime);
        sb.append(", usetime=").append(usetime);
        sb.append(", subject=").append(subject);
        sb.append(", meqList=").append(meqList);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}