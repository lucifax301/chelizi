package com.lili.pay.dto;

import java.util.Date;

public class PerformanceDto {
    private Integer id;

    private Long coachId;

    private Integer performance;

    private Date date;

    private String course;

    private String orderid;

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

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    /**
     * @return the coachid
     */
    public Long getCoachId()
    {
        return coachId;
    }

    /**
     * @param coachid the coachid to set
     */
    public void setCoachId(Long coachId)
    {
        this.coachId = coachId;
    }
}