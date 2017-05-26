package com.lili.student.dto;

import java.io.Serializable;

public class ExerciseExamDetail extends ExerciseExamDetailKey implements Serializable {
    private String ansRec;

    private Integer ansSta;

    private static final long serialVersionUID = 1L;

    public String getAnsRec() {
        return ansRec;
    }

    public void setAnsRec(String ansRec) {
        this.ansRec = ansRec == null ? null : ansRec.trim();
    }

    public Integer getAnsSta() {
        return ansSta;
    }

    public void setAnsSta(Integer ansSta) {
        this.ansSta = ansSta;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", ansRec=").append(ansRec);
        sb.append(", ansSta=").append(ansSta);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}