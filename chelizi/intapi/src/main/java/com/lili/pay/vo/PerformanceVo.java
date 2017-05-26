package com.lili.pay.vo;

import java.io.Serializable;
import java.util.Date;

public class PerformanceVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1909224374913230697L;

	private Integer id;

    private Long coachId;

    private Integer performance;

    private Date date;

    private String course;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPerformance() {
        return performance;
    }

    public void setPerformance(Integer performance) {
        this.performance = performance;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course == null ? null : course.trim();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "PerformanceVo [id=" + id + ", coachId=" + coachId + ", performance=" + performance + ", date="
                + date + ", course=" + course + "]";
    }

    /**
     * @return the coachId
     */
    public Long getCoachId()
    {
        return coachId;
    }

    /**
     * @param coachId the coachId to set
     */
    public void setCoachId(Long coachId)
    {
        this.coachId = coachId;
    }
    
    
}