package com.lili.student.dto;

import java.io.Serializable;
import java.util.Date;

public class ExerciseCollection extends ExerciseCollectionKey implements Serializable {
    private Integer subject;

    private Integer chapter;

    private Integer ansSta;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getSubject() {
        return subject;
    }

    public void setSubject(Integer subject) {
        this.subject = subject;
    }

    public Integer getChapter() {
        return chapter;
    }

    public void setChapter(Integer chapter) {
        this.chapter = chapter;
    }

    public Integer getAnsSta() {
        return ansSta;
    }

    public void setAnsSta(Integer ansSta) {
        this.ansSta = ansSta;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", subject=").append(subject);
        sb.append(", chapter=").append(chapter);
        sb.append(", ansSta=").append(ansSta);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}