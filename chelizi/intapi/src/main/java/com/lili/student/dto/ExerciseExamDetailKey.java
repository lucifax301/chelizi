package com.lili.student.dto;

import java.io.Serializable;

public class ExerciseExamDetailKey implements Serializable {
    private Integer exerciseExamId;

    private String qid;

    private static final long serialVersionUID = 1L;

    public Integer getExerciseExamId() {
        return exerciseExamId;
    }

    public void setExerciseExamId(Integer exerciseExamId) {
        this.exerciseExamId = exerciseExamId;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid == null ? null : qid.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", exerciseExamId=").append(exerciseExamId);
        sb.append(", qid=").append(qid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}