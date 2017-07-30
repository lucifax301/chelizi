package com.lili.exam.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExamPlaceClassExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ExamPlaceClassExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPlaceIdIsNull() {
            addCriterion("placeId is null");
            return (Criteria) this;
        }

        public Criteria andPlaceIdIsNotNull() {
            addCriterion("placeId is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }
        
        public Criteria andPlaceIdEqualTo(Integer value) {
            addCriterion("placeId =", value, "placeId");
            return (Criteria) this;
        }

        public Criteria andPlaceIdNotEqualTo(Integer value) {
            addCriterion("placeId <>", value, "placeId");
            return (Criteria) this;
        }

        public Criteria andPlaceIdGreaterThan(Integer value) {
            addCriterion("placeId >", value, "placeId");
            return (Criteria) this;
        }

        public Criteria andPlaceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("placeId >=", value, "placeId");
            return (Criteria) this;
        }

        public Criteria andPlaceIdLessThan(Integer value) {
            addCriterion("placeId <", value, "placeId");
            return (Criteria) this;
        }

        public Criteria andPlaceIdLessThanOrEqualTo(Integer value) {
            addCriterion("placeId <=", value, "placeId");
            return (Criteria) this;
        }

        public Criteria andPlaceIdIn(List<Integer> values) {
            addCriterion("placeId in", values, "placeId");
            return (Criteria) this;
        }

        public Criteria andPlaceIdNotIn(List<Integer> values) {
            addCriterion("placeId not in", values, "placeId");
            return (Criteria) this;
        }

        public Criteria andPlaceIdBetween(Integer value1, Integer value2) {
            addCriterion("placeId between", value1, value2, "placeId");
            return (Criteria) this;
        }

        public Criteria andPlaceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("placeId not between", value1, value2, "placeId");
            return (Criteria) this;
        }

        public Criteria andPstartIsNull() {
            addCriterion("pstart is null");
            return (Criteria) this;
        }

        public Criteria andPstartIsNotNull() {
            addCriterion("pstart is not null");
            return (Criteria) this;
        }

        public Criteria andPstartEqualTo(Date value) {
            addCriterion("pstart =", value, "pstart");
            return (Criteria) this;
        }

        public Criteria andPstartNotEqualTo(Date value) {
            addCriterion("pstart <>", value, "pstart");
            return (Criteria) this;
        }

        public Criteria andPstartGreaterThan(Date value) {
            addCriterion("pstart >", value, "pstart");
            return (Criteria) this;
        }

        public Criteria andPstartGreaterThanOrEqualTo(Date value) {
            addCriterion("pstart >=", value, "pstart");
            return (Criteria) this;
        }

        public Criteria andPstartLessThan(Date value) {
            addCriterion("pstart <", value, "pstart");
            return (Criteria) this;
        }

        public Criteria andPstartLessThanOrEqualTo(Date value) {
            addCriterion("pstart <=", value, "pstart");
            return (Criteria) this;
        }

        public Criteria andPstartIn(List<Date> values) {
            addCriterion("pstart in", values, "pstart");
            return (Criteria) this;
        }

        public Criteria andPstartNotIn(List<Date> values) {
            addCriterion("pstart not in", values, "pstart");
            return (Criteria) this;
        }

        public Criteria andPstartBetween(Date value1, Date value2) {
            addCriterion("pstart between", value1, value2, "pstart");
            return (Criteria) this;
        }

        public Criteria andPstartNotBetween(Date value1, Date value2) {
            addCriterion("pstart not between", value1, value2, "pstart");
            return (Criteria) this;
        }

        public Criteria andPendIsNull() {
            addCriterion("pend is null");
            return (Criteria) this;
        }

        public Criteria andPendIsNotNull() {
            addCriterion("pend is not null");
            return (Criteria) this;
        }

        public Criteria andPendEqualTo(Date value) {
            addCriterion("pend =", value, "pend");
            return (Criteria) this;
        }

        public Criteria andPendNotEqualTo(Date value) {
            addCriterion("pend <>", value, "pend");
            return (Criteria) this;
        }

        public Criteria andPendGreaterThan(Date value) {
            addCriterion("pend >", value, "pend");
            return (Criteria) this;
        }

        public Criteria andPendGreaterThanOrEqualTo(Date value) {
            addCriterion("pend >=", value, "pend");
            return (Criteria) this;
        }

        public Criteria andPendLessThan(Date value) {
            addCriterion("pend <", value, "pend");
            return (Criteria) this;
        }

        public Criteria andPendLessThanOrEqualTo(Date value) {
            addCriterion("pend <=", value, "pend");
            return (Criteria) this;
        }

        public Criteria andPendIn(List<Date> values) {
            addCriterion("pend in", values, "pend");
            return (Criteria) this;
        }

        public Criteria andPendNotIn(List<Date> values) {
            addCriterion("pend not in", values, "pend");
            return (Criteria) this;
        }

        public Criteria andPendBetween(Date value1, Date value2) {
            addCriterion("pend between", value1, value2, "pend");
            return (Criteria) this;
        }

        public Criteria andPendNotBetween(Date value1, Date value2) {
            addCriterion("pend not between", value1, value2, "pend");
            return (Criteria) this;
        }

        public Criteria andRstartIsNull() {
            addCriterion("rstart is null");
            return (Criteria) this;
        }

        public Criteria andRstartIsNotNull() {
            addCriterion("rstart is not null");
            return (Criteria) this;
        }

        public Criteria andRstartEqualTo(Date value) {
            addCriterion("rstart =", value, "rstart");
            return (Criteria) this;
        }

        public Criteria andRstartNotEqualTo(Date value) {
            addCriterion("rstart <>", value, "rstart");
            return (Criteria) this;
        }

        public Criteria andRstartGreaterThan(Date value) {
            addCriterion("rstart >", value, "rstart");
            return (Criteria) this;
        }

        public Criteria andRstartGreaterThanOrEqualTo(Date value) {
            addCriterion("rstart >=", value, "rstart");
            return (Criteria) this;
        }

        public Criteria andRstartLessThan(Date value) {
            addCriterion("rstart <", value, "rstart");
            return (Criteria) this;
        }

        public Criteria andRstartLessThanOrEqualTo(Date value) {
            addCriterion("rstart <=", value, "rstart");
            return (Criteria) this;
        }

        public Criteria andRstartIn(List<Date> values) {
            addCriterion("rstart in", values, "rstart");
            return (Criteria) this;
        }

        public Criteria andRstartNotIn(List<Date> values) {
            addCriterion("rstart not in", values, "rstart");
            return (Criteria) this;
        }

        public Criteria andRstartBetween(Date value1, Date value2) {
            addCriterion("rstart between", value1, value2, "rstart");
            return (Criteria) this;
        }

        public Criteria andRstartNotBetween(Date value1, Date value2) {
            addCriterion("rstart not between", value1, value2, "rstart");
            return (Criteria) this;
        }

        public Criteria andRendIsNull() {
            addCriterion("rend is null");
            return (Criteria) this;
        }

        public Criteria andRendIsNotNull() {
            addCriterion("rend is not null");
            return (Criteria) this;
        }

        public Criteria andRendEqualTo(Date value) {
            addCriterion("rend =", value, "rend");
            return (Criteria) this;
        }

        public Criteria andRendNotEqualTo(Date value) {
            addCriterion("rend <>", value, "rend");
            return (Criteria) this;
        }

        public Criteria andRendGreaterThan(Date value) {
            addCriterion("rend >", value, "rend");
            return (Criteria) this;
        }

        public Criteria andRendGreaterThanOrEqualTo(Date value) {
            addCriterion("rend >=", value, "rend");
            return (Criteria) this;
        }

        public Criteria andRendLessThan(Date value) {
            addCriterion("rend <", value, "rend");
            return (Criteria) this;
        }

        public Criteria andRendLessThanOrEqualTo(Date value) {
            addCriterion("rend <=", value, "rend");
            return (Criteria) this;
        }

        public Criteria andRendIn(List<Date> values) {
            addCriterion("rend in", values, "rend");
            return (Criteria) this;
        }

        public Criteria andRendNotIn(List<Date> values) {
            addCriterion("rend not in", values, "rend");
            return (Criteria) this;
        }

        public Criteria andRendBetween(Date value1, Date value2) {
            addCriterion("rend between", value1, value2, "rend");
            return (Criteria) this;
        }

        public Criteria andRendNotBetween(Date value1, Date value2) {
            addCriterion("rend not between", value1, value2, "rend");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andDurationIsNull() {
            addCriterion("duration is null");
            return (Criteria) this;
        }

        public Criteria andDurationIsNotNull() {
            addCriterion("duration is not null");
            return (Criteria) this;
        }

        public Criteria andDurationEqualTo(Integer value) {
            addCriterion("duration =", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationNotEqualTo(Integer value) {
            addCriterion("duration <>", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationGreaterThan(Integer value) {
            addCriterion("duration >", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationGreaterThanOrEqualTo(Integer value) {
            addCriterion("duration >=", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationLessThan(Integer value) {
            addCriterion("duration <", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationLessThanOrEqualTo(Integer value) {
            addCriterion("duration <=", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationIn(List<Integer> values) {
            addCriterion("duration in", values, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationNotIn(List<Integer> values) {
            addCriterion("duration not in", values, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationBetween(Integer value1, Integer value2) {
            addCriterion("duration between", value1, value2, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationNotBetween(Integer value1, Integer value2) {
            addCriterion("duration not between", value1, value2, "duration");
            return (Criteria) this;
        }

        public Criteria andOuterPriceIsNull() {
            addCriterion("outerPrice is null");
            return (Criteria) this;
        }

        public Criteria andOuterPriceIsNotNull() {
            addCriterion("outerPrice is not null");
            return (Criteria) this;
        }

        public Criteria andOuterPriceEqualTo(Integer value) {
            addCriterion("outerPrice =", value, "outerPrice");
            return (Criteria) this;
        }

        public Criteria andOuterPriceNotEqualTo(Integer value) {
            addCriterion("outerPrice <>", value, "outerPrice");
            return (Criteria) this;
        }

        public Criteria andOuterPriceGreaterThan(Integer value) {
            addCriterion("outerPrice >", value, "outerPrice");
            return (Criteria) this;
        }

        public Criteria andOuterPriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("outerPrice >=", value, "outerPrice");
            return (Criteria) this;
        }

        public Criteria andOuterPriceLessThan(Integer value) {
            addCriterion("outerPrice <", value, "outerPrice");
            return (Criteria) this;
        }

        public Criteria andOuterPriceLessThanOrEqualTo(Integer value) {
            addCriterion("outerPrice <=", value, "outerPrice");
            return (Criteria) this;
        }

        public Criteria andOuterPriceIn(List<Integer> values) {
            addCriterion("outerPrice in", values, "outerPrice");
            return (Criteria) this;
        }

        public Criteria andOuterPriceNotIn(List<Integer> values) {
            addCriterion("outerPrice not in", values, "outerPrice");
            return (Criteria) this;
        }

        public Criteria andOuterPriceBetween(Integer value1, Integer value2) {
            addCriterion("outerPrice between", value1, value2, "outerPrice");
            return (Criteria) this;
        }

        public Criteria andOuterPriceNotBetween(Integer value1, Integer value2) {
            addCriterion("outerPrice not between", value1, value2, "outerPrice");
            return (Criteria) this;
        }

        public Criteria andInnerPriceIsNull() {
            addCriterion("innerPrice is null");
            return (Criteria) this;
        }

        public Criteria andInnerPriceIsNotNull() {
            addCriterion("innerPrice is not null");
            return (Criteria) this;
        }

        public Criteria andInnerPriceEqualTo(Integer value) {
            addCriterion("innerPrice =", value, "innerPrice");
            return (Criteria) this;
        }

        public Criteria andInnerPriceNotEqualTo(Integer value) {
            addCriterion("innerPrice <>", value, "innerPrice");
            return (Criteria) this;
        }

        public Criteria andInnerPriceGreaterThan(Integer value) {
            addCriterion("innerPrice >", value, "innerPrice");
            return (Criteria) this;
        }

        public Criteria andInnerPriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("innerPrice >=", value, "innerPrice");
            return (Criteria) this;
        }

        public Criteria andInnerPriceLessThan(Integer value) {
            addCriterion("innerPrice <", value, "innerPrice");
            return (Criteria) this;
        }

        public Criteria andInnerPriceLessThanOrEqualTo(Integer value) {
            addCriterion("innerPrice <=", value, "innerPrice");
            return (Criteria) this;
        }

        public Criteria andInnerPriceIn(List<Integer> values) {
            addCriterion("innerPrice in", values, "innerPrice");
            return (Criteria) this;
        }

        public Criteria andInnerPriceNotIn(List<Integer> values) {
            addCriterion("innerPrice not in", values, "innerPrice");
            return (Criteria) this;
        }

        public Criteria andInnerPriceBetween(Integer value1, Integer value2) {
            addCriterion("innerPrice between", value1, value2, "innerPrice");
            return (Criteria) this;
        }

        public Criteria andInnerPriceNotBetween(Integer value1, Integer value2) {
            addCriterion("innerPrice not between", value1, value2, "innerPrice");
            return (Criteria) this;
        }

        public Criteria andMinHoursIsNull() {
            addCriterion("minHours is null");
            return (Criteria) this;
        }

        public Criteria andMinHoursIsNotNull() {
            addCriterion("minHours is not null");
            return (Criteria) this;
        }

        public Criteria andMinHoursEqualTo(Integer value) {
            addCriterion("minHours =", value, "minHours");
            return (Criteria) this;
        }

        public Criteria andMinHoursNotEqualTo(Integer value) {
            addCriterion("minHours <>", value, "minHours");
            return (Criteria) this;
        }

        public Criteria andMinHoursGreaterThan(Integer value) {
            addCriterion("minHours >", value, "minHours");
            return (Criteria) this;
        }

        public Criteria andMinHoursGreaterThanOrEqualTo(Integer value) {
            addCriterion("minHours >=", value, "minHours");
            return (Criteria) this;
        }

        public Criteria andMinHoursLessThan(Integer value) {
            addCriterion("minHours <", value, "minHours");
            return (Criteria) this;
        }

        public Criteria andMinHoursLessThanOrEqualTo(Integer value) {
            addCriterion("minHours <=", value, "minHours");
            return (Criteria) this;
        }

        public Criteria andMinHoursIn(List<Integer> values) {
            addCriterion("minHours in", values, "minHours");
            return (Criteria) this;
        }

        public Criteria andMinHoursNotIn(List<Integer> values) {
            addCriterion("minHours not in", values, "minHours");
            return (Criteria) this;
        }

        public Criteria andMinHoursBetween(Integer value1, Integer value2) {
            addCriterion("minHours between", value1, value2, "minHours");
            return (Criteria) this;
        }

        public Criteria andMinHoursNotBetween(Integer value1, Integer value2) {
            addCriterion("minHours not between", value1, value2, "minHours");
            return (Criteria) this;
        }

        public Criteria andC1IsNull() {
            addCriterion("c1 is null");
            return (Criteria) this;
        }

        public Criteria andC1IsNotNull() {
            addCriterion("c1 is not null");
            return (Criteria) this;
        }

        public Criteria andC1EqualTo(Integer value) {
            addCriterion("c1 =", value, "c1");
            return (Criteria) this;
        }

        public Criteria andC1NotEqualTo(Integer value) {
            addCriterion("c1 <>", value, "c1");
            return (Criteria) this;
        }

        public Criteria andC1GreaterThan(Integer value) {
            addCriterion("c1 >", value, "c1");
            return (Criteria) this;
        }

        public Criteria andC1GreaterThanOrEqualTo(Integer value) {
            addCriterion("c1 >=", value, "c1");
            return (Criteria) this;
        }

        public Criteria andC1LessThan(Integer value) {
            addCriterion("c1 <", value, "c1");
            return (Criteria) this;
        }

        public Criteria andC1LessThanOrEqualTo(Integer value) {
            addCriterion("c1 <=", value, "c1");
            return (Criteria) this;
        }

        public Criteria andC1In(List<Integer> values) {
            addCriterion("c1 in", values, "c1");
            return (Criteria) this;
        }

        public Criteria andC1NotIn(List<Integer> values) {
            addCriterion("c1 not in", values, "c1");
            return (Criteria) this;
        }

        public Criteria andC1Between(Integer value1, Integer value2) {
            addCriterion("c1 between", value1, value2, "c1");
            return (Criteria) this;
        }

        public Criteria andC1NotBetween(Integer value1, Integer value2) {
            addCriterion("c1 not between", value1, value2, "c1");
            return (Criteria) this;
        }

        public Criteria andC1innerIsNull() {
            addCriterion("c1inner is null");
            return (Criteria) this;
        }

        public Criteria andC1innerIsNotNull() {
            addCriterion("c1inner is not null");
            return (Criteria) this;
        }

        public Criteria andC1innerEqualTo(Integer value) {
            addCriterion("c1inner =", value, "c1inner");
            return (Criteria) this;
        }

        public Criteria andC1innerNotEqualTo(Integer value) {
            addCriterion("c1inner <>", value, "c1inner");
            return (Criteria) this;
        }

        public Criteria andC1innerGreaterThan(Integer value) {
            addCriterion("c1inner >", value, "c1inner");
            return (Criteria) this;
        }

        public Criteria andC1innerGreaterThanOrEqualTo(Integer value) {
            addCriterion("c1inner >=", value, "c1inner");
            return (Criteria) this;
        }

        public Criteria andC1innerLessThan(Integer value) {
            addCriterion("c1inner <", value, "c1inner");
            return (Criteria) this;
        }

        public Criteria andC1innerLessThanOrEqualTo(Integer value) {
            addCriterion("c1inner <=", value, "c1inner");
            return (Criteria) this;
        }

        public Criteria andC1innerIn(List<Integer> values) {
            addCriterion("c1inner in", values, "c1inner");
            return (Criteria) this;
        }

        public Criteria andC1innerNotIn(List<Integer> values) {
            addCriterion("c1inner not in", values, "c1inner");
            return (Criteria) this;
        }

        public Criteria andC1innerBetween(Integer value1, Integer value2) {
            addCriterion("c1inner between", value1, value2, "c1inner");
            return (Criteria) this;
        }

        public Criteria andC1innerNotBetween(Integer value1, Integer value2) {
            addCriterion("c1inner not between", value1, value2, "c1inner");
            return (Criteria) this;
        }

        public Criteria andC1outerIsNull() {
            addCriterion("c1outer is null");
            return (Criteria) this;
        }

        public Criteria andC1outerIsNotNull() {
            addCriterion("c1outer is not null");
            return (Criteria) this;
        }

        public Criteria andC1outerEqualTo(Integer value) {
            addCriterion("c1outer =", value, "c1outer");
            return (Criteria) this;
        }

        public Criteria andC1outerNotEqualTo(Integer value) {
            addCriterion("c1outer <>", value, "c1outer");
            return (Criteria) this;
        }

        public Criteria andC1outerGreaterThan(Integer value) {
            addCriterion("c1outer >", value, "c1outer");
            return (Criteria) this;
        }

        public Criteria andC1outerGreaterThanOrEqualTo(Integer value) {
            addCriterion("c1outer >=", value, "c1outer");
            return (Criteria) this;
        }

        public Criteria andC1outerLessThan(Integer value) {
            addCriterion("c1outer <", value, "c1outer");
            return (Criteria) this;
        }

        public Criteria andC1outerLessThanOrEqualTo(Integer value) {
            addCriterion("c1outer <=", value, "c1outer");
            return (Criteria) this;
        }

        public Criteria andC1outerIn(List<Integer> values) {
            addCriterion("c1outer in", values, "c1outer");
            return (Criteria) this;
        }

        public Criteria andC1outerNotIn(List<Integer> values) {
            addCriterion("c1outer not in", values, "c1outer");
            return (Criteria) this;
        }

        public Criteria andC1outerBetween(Integer value1, Integer value2) {
            addCriterion("c1outer between", value1, value2, "c1outer");
            return (Criteria) this;
        }

        public Criteria andC1outerNotBetween(Integer value1, Integer value2) {
            addCriterion("c1outer not between", value1, value2, "c1outer");
            return (Criteria) this;
        }

        public Criteria andC1bookIsNull() {
            addCriterion("c1book is null");
            return (Criteria) this;
        }

        public Criteria andC1bookIsNotNull() {
            addCriterion("c1book is not null");
            return (Criteria) this;
        }

        public Criteria andC1bookEqualTo(Integer value) {
            addCriterion("c1book =", value, "c1book");
            return (Criteria) this;
        }

        public Criteria andC1bookNotEqualTo(Integer value) {
            addCriterion("c1book <>", value, "c1book");
            return (Criteria) this;
        }

        public Criteria andC1bookGreaterThan(Integer value) {
            addCriterion("c1book >", value, "c1book");
            return (Criteria) this;
        }

        public Criteria andC1bookGreaterThanOrEqualTo(Integer value) {
            addCriterion("c1book >=", value, "c1book");
            return (Criteria) this;
        }

        public Criteria andC1bookLessThan(Integer value) {
            addCriterion("c1book <", value, "c1book");
            return (Criteria) this;
        }

        public Criteria andC1bookLessThanOrEqualTo(Integer value) {
            addCriterion("c1book <=", value, "c1book");
            return (Criteria) this;
        }

        public Criteria andC1bookIn(List<Integer> values) {
            addCriterion("c1book in", values, "c1book");
            return (Criteria) this;
        }

        public Criteria andC1bookNotIn(List<Integer> values) {
            addCriterion("c1book not in", values, "c1book");
            return (Criteria) this;
        }

        public Criteria andC1bookBetween(Integer value1, Integer value2) {
            addCriterion("c1book between", value1, value2, "c1book");
            return (Criteria) this;
        }

        public Criteria andC1bookNotBetween(Integer value1, Integer value2) {
            addCriterion("c1book not between", value1, value2, "c1book");
            return (Criteria) this;
        }

        public Criteria andC1bookInnerIsNull() {
            addCriterion("c1bookInner is null");
            return (Criteria) this;
        }

        public Criteria andC1bookInnerIsNotNull() {
            addCriterion("c1bookInner is not null");
            return (Criteria) this;
        }

        public Criteria andC1bookInnerEqualTo(Integer value) {
            addCriterion("c1bookInner =", value, "c1bookInner");
            return (Criteria) this;
        }

        public Criteria andC1bookInnerNotEqualTo(Integer value) {
            addCriterion("c1bookInner <>", value, "c1bookInner");
            return (Criteria) this;
        }

        public Criteria andC1bookInnerGreaterThan(Integer value) {
            addCriterion("c1bookInner >", value, "c1bookInner");
            return (Criteria) this;
        }

        public Criteria andC1bookInnerGreaterThanOrEqualTo(Integer value) {
            addCriterion("c1bookInner >=", value, "c1bookInner");
            return (Criteria) this;
        }

        public Criteria andC1bookInnerLessThan(Integer value) {
            addCriterion("c1bookInner <", value, "c1bookInner");
            return (Criteria) this;
        }

        public Criteria andC1bookInnerLessThanOrEqualTo(Integer value) {
            addCriterion("c1bookInner <=", value, "c1bookInner");
            return (Criteria) this;
        }

        public Criteria andC1bookInnerIn(List<Integer> values) {
            addCriterion("c1bookInner in", values, "c1bookInner");
            return (Criteria) this;
        }

        public Criteria andC1bookInnerNotIn(List<Integer> values) {
            addCriterion("c1bookInner not in", values, "c1bookInner");
            return (Criteria) this;
        }

        public Criteria andC1bookInnerBetween(Integer value1, Integer value2) {
            addCriterion("c1bookInner between", value1, value2, "c1bookInner");
            return (Criteria) this;
        }

        public Criteria andC1bookInnerNotBetween(Integer value1, Integer value2) {
            addCriterion("c1bookInner not between", value1, value2, "c1bookInner");
            return (Criteria) this;
        }

        public Criteria andC1bookOuterIsNull() {
            addCriterion("c1bookOuter is null");
            return (Criteria) this;
        }

        public Criteria andC1bookOuterIsNotNull() {
            addCriterion("c1bookOuter is not null");
            return (Criteria) this;
        }

        public Criteria andC1bookOuterEqualTo(Integer value) {
            addCriterion("c1bookOuter =", value, "c1bookOuter");
            return (Criteria) this;
        }

        public Criteria andC1bookOuterNotEqualTo(Integer value) {
            addCriterion("c1bookOuter <>", value, "c1bookOuter");
            return (Criteria) this;
        }

        public Criteria andC1bookOuterGreaterThan(Integer value) {
            addCriterion("c1bookOuter >", value, "c1bookOuter");
            return (Criteria) this;
        }

        public Criteria andC1bookOuterGreaterThanOrEqualTo(Integer value) {
            addCriterion("c1bookOuter >=", value, "c1bookOuter");
            return (Criteria) this;
        }

        public Criteria andC1bookOuterLessThan(Integer value) {
            addCriterion("c1bookOuter <", value, "c1bookOuter");
            return (Criteria) this;
        }

        public Criteria andC1bookOuterLessThanOrEqualTo(Integer value) {
            addCriterion("c1bookOuter <=", value, "c1bookOuter");
            return (Criteria) this;
        }

        public Criteria andC1bookOuterIn(List<Integer> values) {
            addCriterion("c1bookOuter in", values, "c1bookOuter");
            return (Criteria) this;
        }

        public Criteria andC1bookOuterNotIn(List<Integer> values) {
            addCriterion("c1bookOuter not in", values, "c1bookOuter");
            return (Criteria) this;
        }

        public Criteria andC1bookOuterBetween(Integer value1, Integer value2) {
            addCriterion("c1bookOuter between", value1, value2, "c1bookOuter");
            return (Criteria) this;
        }

        public Criteria andC1bookOuterNotBetween(Integer value1, Integer value2) {
            addCriterion("c1bookOuter not between", value1, value2, "c1bookOuter");
            return (Criteria) this;
        }

        public Criteria andC2IsNull() {
            addCriterion("c2 is null");
            return (Criteria) this;
        }

        public Criteria andC2IsNotNull() {
            addCriterion("c2 is not null");
            return (Criteria) this;
        }

        public Criteria andC2EqualTo(Integer value) {
            addCriterion("c2 =", value, "c2");
            return (Criteria) this;
        }

        public Criteria andC2NotEqualTo(Integer value) {
            addCriterion("c2 <>", value, "c2");
            return (Criteria) this;
        }

        public Criteria andC2GreaterThan(Integer value) {
            addCriterion("c2 >", value, "c2");
            return (Criteria) this;
        }

        public Criteria andC2GreaterThanOrEqualTo(Integer value) {
            addCriterion("c2 >=", value, "c2");
            return (Criteria) this;
        }

        public Criteria andC2LessThan(Integer value) {
            addCriterion("c2 <", value, "c2");
            return (Criteria) this;
        }

        public Criteria andC2LessThanOrEqualTo(Integer value) {
            addCriterion("c2 <=", value, "c2");
            return (Criteria) this;
        }

        public Criteria andC2In(List<Integer> values) {
            addCriterion("c2 in", values, "c2");
            return (Criteria) this;
        }

        public Criteria andC2NotIn(List<Integer> values) {
            addCriterion("c2 not in", values, "c2");
            return (Criteria) this;
        }

        public Criteria andC2Between(Integer value1, Integer value2) {
            addCriterion("c2 between", value1, value2, "c2");
            return (Criteria) this;
        }

        public Criteria andC2NotBetween(Integer value1, Integer value2) {
            addCriterion("c2 not between", value1, value2, "c2");
            return (Criteria) this;
        }

        public Criteria andC2innerIsNull() {
            addCriterion("c2inner is null");
            return (Criteria) this;
        }

        public Criteria andC2innerIsNotNull() {
            addCriterion("c2inner is not null");
            return (Criteria) this;
        }

        public Criteria andC2innerEqualTo(Integer value) {
            addCriterion("c2inner =", value, "c2inner");
            return (Criteria) this;
        }

        public Criteria andC2innerNotEqualTo(Integer value) {
            addCriterion("c2inner <>", value, "c2inner");
            return (Criteria) this;
        }

        public Criteria andC2innerGreaterThan(Integer value) {
            addCriterion("c2inner >", value, "c2inner");
            return (Criteria) this;
        }

        public Criteria andC2innerGreaterThanOrEqualTo(Integer value) {
            addCriterion("c2inner >=", value, "c2inner");
            return (Criteria) this;
        }

        public Criteria andC2innerLessThan(Integer value) {
            addCriterion("c2inner <", value, "c2inner");
            return (Criteria) this;
        }

        public Criteria andC2innerLessThanOrEqualTo(Integer value) {
            addCriterion("c2inner <=", value, "c2inner");
            return (Criteria) this;
        }

        public Criteria andC2innerIn(List<Integer> values) {
            addCriterion("c2inner in", values, "c2inner");
            return (Criteria) this;
        }

        public Criteria andC2innerNotIn(List<Integer> values) {
            addCriterion("c2inner not in", values, "c2inner");
            return (Criteria) this;
        }

        public Criteria andC2innerBetween(Integer value1, Integer value2) {
            addCriterion("c2inner between", value1, value2, "c2inner");
            return (Criteria) this;
        }

        public Criteria andC2innerNotBetween(Integer value1, Integer value2) {
            addCriterion("c2inner not between", value1, value2, "c2inner");
            return (Criteria) this;
        }

        public Criteria andC2outerIsNull() {
            addCriterion("c2outer is null");
            return (Criteria) this;
        }

        public Criteria andC2outerIsNotNull() {
            addCriterion("c2outer is not null");
            return (Criteria) this;
        }

        public Criteria andC2outerEqualTo(Integer value) {
            addCriterion("c2outer =", value, "c2outer");
            return (Criteria) this;
        }

        public Criteria andC2outerNotEqualTo(Integer value) {
            addCriterion("c2outer <>", value, "c2outer");
            return (Criteria) this;
        }

        public Criteria andC2outerGreaterThan(Integer value) {
            addCriterion("c2outer >", value, "c2outer");
            return (Criteria) this;
        }

        public Criteria andC2outerGreaterThanOrEqualTo(Integer value) {
            addCriterion("c2outer >=", value, "c2outer");
            return (Criteria) this;
        }

        public Criteria andC2outerLessThan(Integer value) {
            addCriterion("c2outer <", value, "c2outer");
            return (Criteria) this;
        }

        public Criteria andC2outerLessThanOrEqualTo(Integer value) {
            addCriterion("c2outer <=", value, "c2outer");
            return (Criteria) this;
        }

        public Criteria andC2outerIn(List<Integer> values) {
            addCriterion("c2outer in", values, "c2outer");
            return (Criteria) this;
        }

        public Criteria andC2outerNotIn(List<Integer> values) {
            addCriterion("c2outer not in", values, "c2outer");
            return (Criteria) this;
        }

        public Criteria andC2outerBetween(Integer value1, Integer value2) {
            addCriterion("c2outer between", value1, value2, "c2outer");
            return (Criteria) this;
        }

        public Criteria andC2outerNotBetween(Integer value1, Integer value2) {
            addCriterion("c2outer not between", value1, value2, "c2outer");
            return (Criteria) this;
        }

        public Criteria andC2bookIsNull() {
            addCriterion("c2book is null");
            return (Criteria) this;
        }

        public Criteria andC2bookIsNotNull() {
            addCriterion("c2book is not null");
            return (Criteria) this;
        }

        public Criteria andC2bookEqualTo(Integer value) {
            addCriterion("c2book =", value, "c2book");
            return (Criteria) this;
        }

        public Criteria andC2bookNotEqualTo(Integer value) {
            addCriterion("c2book <>", value, "c2book");
            return (Criteria) this;
        }

        public Criteria andC2bookGreaterThan(Integer value) {
            addCriterion("c2book >", value, "c2book");
            return (Criteria) this;
        }

        public Criteria andC2bookGreaterThanOrEqualTo(Integer value) {
            addCriterion("c2book >=", value, "c2book");
            return (Criteria) this;
        }

        public Criteria andC2bookLessThan(Integer value) {
            addCriterion("c2book <", value, "c2book");
            return (Criteria) this;
        }

        public Criteria andC2bookLessThanOrEqualTo(Integer value) {
            addCriterion("c2book <=", value, "c2book");
            return (Criteria) this;
        }

        public Criteria andC2bookIn(List<Integer> values) {
            addCriterion("c2book in", values, "c2book");
            return (Criteria) this;
        }

        public Criteria andC2bookNotIn(List<Integer> values) {
            addCriterion("c2book not in", values, "c2book");
            return (Criteria) this;
        }

        public Criteria andC2bookBetween(Integer value1, Integer value2) {
            addCriterion("c2book between", value1, value2, "c2book");
            return (Criteria) this;
        }

        public Criteria andC2bookNotBetween(Integer value1, Integer value2) {
            addCriterion("c2book not between", value1, value2, "c2book");
            return (Criteria) this;
        }

        public Criteria andC2bookInnerIsNull() {
            addCriterion("c2bookInner is null");
            return (Criteria) this;
        }

        public Criteria andC2bookInnerIsNotNull() {
            addCriterion("c2bookInner is not null");
            return (Criteria) this;
        }

        public Criteria andC2bookInnerEqualTo(Integer value) {
            addCriterion("c2bookInner =", value, "c2bookInner");
            return (Criteria) this;
        }

        public Criteria andC2bookInnerNotEqualTo(Integer value) {
            addCriterion("c2bookInner <>", value, "c2bookInner");
            return (Criteria) this;
        }

        public Criteria andC2bookInnerGreaterThan(Integer value) {
            addCriterion("c2bookInner >", value, "c2bookInner");
            return (Criteria) this;
        }

        public Criteria andC2bookInnerGreaterThanOrEqualTo(Integer value) {
            addCriterion("c2bookInner >=", value, "c2bookInner");
            return (Criteria) this;
        }

        public Criteria andC2bookInnerLessThan(Integer value) {
            addCriterion("c2bookInner <", value, "c2bookInner");
            return (Criteria) this;
        }

        public Criteria andC2bookInnerLessThanOrEqualTo(Integer value) {
            addCriterion("c2bookInner <=", value, "c2bookInner");
            return (Criteria) this;
        }

        public Criteria andC2bookInnerIn(List<Integer> values) {
            addCriterion("c2bookInner in", values, "c2bookInner");
            return (Criteria) this;
        }

        public Criteria andC2bookInnerNotIn(List<Integer> values) {
            addCriterion("c2bookInner not in", values, "c2bookInner");
            return (Criteria) this;
        }

        public Criteria andC2bookInnerBetween(Integer value1, Integer value2) {
            addCriterion("c2bookInner between", value1, value2, "c2bookInner");
            return (Criteria) this;
        }

        public Criteria andC2bookInnerNotBetween(Integer value1, Integer value2) {
            addCriterion("c2bookInner not between", value1, value2, "c2bookInner");
            return (Criteria) this;
        }

        public Criteria andC2bookOuterIsNull() {
            addCriterion("c2bookOuter is null");
            return (Criteria) this;
        }

        public Criteria andC2bookOuterIsNotNull() {
            addCriterion("c2bookOuter is not null");
            return (Criteria) this;
        }

        public Criteria andC2bookOuterEqualTo(Integer value) {
            addCriterion("c2bookOuter =", value, "c2bookOuter");
            return (Criteria) this;
        }

        public Criteria andC2bookOuterNotEqualTo(Integer value) {
            addCriterion("c2bookOuter <>", value, "c2bookOuter");
            return (Criteria) this;
        }

        public Criteria andC2bookOuterGreaterThan(Integer value) {
            addCriterion("c2bookOuter >", value, "c2bookOuter");
            return (Criteria) this;
        }

        public Criteria andC2bookOuterGreaterThanOrEqualTo(Integer value) {
            addCriterion("c2bookOuter >=", value, "c2bookOuter");
            return (Criteria) this;
        }

        public Criteria andC2bookOuterLessThan(Integer value) {
            addCriterion("c2bookOuter <", value, "c2bookOuter");
            return (Criteria) this;
        }

        public Criteria andC2bookOuterLessThanOrEqualTo(Integer value) {
            addCriterion("c2bookOuter <=", value, "c2bookOuter");
            return (Criteria) this;
        }

        public Criteria andC2bookOuterIn(List<Integer> values) {
            addCriterion("c2bookOuter in", values, "c2bookOuter");
            return (Criteria) this;
        }

        public Criteria andC2bookOuterNotIn(List<Integer> values) {
            addCriterion("c2bookOuter not in", values, "c2bookOuter");
            return (Criteria) this;
        }

        public Criteria andC2bookOuterBetween(Integer value1, Integer value2) {
            addCriterion("c2bookOuter between", value1, value2, "c2bookOuter");
            return (Criteria) this;
        }

        public Criteria andC2bookOuterNotBetween(Integer value1, Integer value2) {
            addCriterion("c2bookOuter not between", value1, value2, "c2bookOuter");
            return (Criteria) this;
        }

        public Criteria andInnerExpireIsNull() {
            addCriterion("innerExpire is null");
            return (Criteria) this;
        }

        public Criteria andInnerExpireIsNotNull() {
            addCriterion("innerExpire is not null");
            return (Criteria) this;
        }

        public Criteria andInnerExpireEqualTo(Integer value) {
            addCriterion("innerExpire =", value, "innerExpire");
            return (Criteria) this;
        }

        public Criteria andInnerExpireNotEqualTo(Integer value) {
            addCriterion("innerExpire <>", value, "innerExpire");
            return (Criteria) this;
        }

        public Criteria andInnerExpireGreaterThan(Integer value) {
            addCriterion("innerExpire >", value, "innerExpire");
            return (Criteria) this;
        }

        public Criteria andInnerExpireGreaterThanOrEqualTo(Integer value) {
            addCriterion("innerExpire >=", value, "innerExpire");
            return (Criteria) this;
        }

        public Criteria andInnerExpireLessThan(Integer value) {
            addCriterion("innerExpire <", value, "innerExpire");
            return (Criteria) this;
        }

        public Criteria andInnerExpireLessThanOrEqualTo(Integer value) {
            addCriterion("innerExpire <=", value, "innerExpire");
            return (Criteria) this;
        }

        public Criteria andInnerExpireIn(List<Integer> values) {
            addCriterion("innerExpire in", values, "innerExpire");
            return (Criteria) this;
        }

        public Criteria andInnerExpireNotIn(List<Integer> values) {
            addCriterion("innerExpire not in", values, "innerExpire");
            return (Criteria) this;
        }

        public Criteria andInnerExpireBetween(Integer value1, Integer value2) {
            addCriterion("innerExpire between", value1, value2, "innerExpire");
            return (Criteria) this;
        }

        public Criteria andInnerExpireNotBetween(Integer value1, Integer value2) {
            addCriterion("innerExpire not between", value1, value2, "innerExpire");
            return (Criteria) this;
        }

        public Criteria andFavorTypeIsNull() {
            addCriterion("favorType is null");
            return (Criteria) this;
        }

        public Criteria andFavorTypeIsNotNull() {
            addCriterion("favorType is not null");
            return (Criteria) this;
        }

        public Criteria andFavorTypeEqualTo(Integer value) {
            addCriterion("favorType =", value, "favorType");
            return (Criteria) this;
        }

        public Criteria andFavorTypeNotEqualTo(Integer value) {
            addCriterion("favorType <>", value, "favorType");
            return (Criteria) this;
        }

        public Criteria andFavorTypeGreaterThan(Integer value) {
            addCriterion("favorType >", value, "favorType");
            return (Criteria) this;
        }

        public Criteria andFavorTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("favorType >=", value, "favorType");
            return (Criteria) this;
        }

        public Criteria andFavorTypeLessThan(Integer value) {
            addCriterion("favorType <", value, "favorType");
            return (Criteria) this;
        }

        public Criteria andFavorTypeLessThanOrEqualTo(Integer value) {
            addCriterion("favorType <=", value, "favorType");
            return (Criteria) this;
        }

        public Criteria andFavorTypeIn(List<Integer> values) {
            addCriterion("favorType in", values, "favorType");
            return (Criteria) this;
        }

        public Criteria andFavorTypeNotIn(List<Integer> values) {
            addCriterion("favorType not in", values, "favorType");
            return (Criteria) this;
        }

        public Criteria andFavorTypeBetween(Integer value1, Integer value2) {
            addCriterion("favorType between", value1, value2, "favorType");
            return (Criteria) this;
        }

        public Criteria andFavorTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("favorType not between", value1, value2, "favorType");
            return (Criteria) this;
        }

        public Criteria andFavorInIsNull() {
            addCriterion("favorIn is null");
            return (Criteria) this;
        }

        public Criteria andFavorInIsNotNull() {
            addCriterion("favorIn is not null");
            return (Criteria) this;
        }

        public Criteria andFavorInEqualTo(Integer value) {
            addCriterion("favorIn =", value, "favorIn");
            return (Criteria) this;
        }

        public Criteria andFavorInNotEqualTo(Integer value) {
            addCriterion("favorIn <>", value, "favorIn");
            return (Criteria) this;
        }

        public Criteria andFavorInGreaterThan(Integer value) {
            addCriterion("favorIn >", value, "favorIn");
            return (Criteria) this;
        }

        public Criteria andFavorInGreaterThanOrEqualTo(Integer value) {
            addCriterion("favorIn >=", value, "favorIn");
            return (Criteria) this;
        }

        public Criteria andFavorInLessThan(Integer value) {
            addCriterion("favorIn <", value, "favorIn");
            return (Criteria) this;
        }

        public Criteria andFavorInLessThanOrEqualTo(Integer value) {
            addCriterion("favorIn <=", value, "favorIn");
            return (Criteria) this;
        }

        public Criteria andFavorInIn(List<Integer> values) {
            addCriterion("favorIn in", values, "favorIn");
            return (Criteria) this;
        }

        public Criteria andFavorInNotIn(List<Integer> values) {
            addCriterion("favorIn not in", values, "favorIn");
            return (Criteria) this;
        }

        public Criteria andFavorInBetween(Integer value1, Integer value2) {
            addCriterion("favorIn between", value1, value2, "favorIn");
            return (Criteria) this;
        }

        public Criteria andFavorInNotBetween(Integer value1, Integer value2) {
            addCriterion("favorIn not between", value1, value2, "favorIn");
            return (Criteria) this;
        }

        public Criteria andFavorOutIsNull() {
            addCriterion("favorOut is null");
            return (Criteria) this;
        }

        public Criteria andFavorOutIsNotNull() {
            addCriterion("favorOut is not null");
            return (Criteria) this;
        }

        public Criteria andFavorOutEqualTo(Integer value) {
            addCriterion("favorOut =", value, "favorOut");
            return (Criteria) this;
        }

        public Criteria andFavorOutNotEqualTo(Integer value) {
            addCriterion("favorOut <>", value, "favorOut");
            return (Criteria) this;
        }

        public Criteria andFavorOutGreaterThan(Integer value) {
            addCriterion("favorOut >", value, "favorOut");
            return (Criteria) this;
        }

        public Criteria andFavorOutGreaterThanOrEqualTo(Integer value) {
            addCriterion("favorOut >=", value, "favorOut");
            return (Criteria) this;
        }

        public Criteria andFavorOutLessThan(Integer value) {
            addCriterion("favorOut <", value, "favorOut");
            return (Criteria) this;
        }

        public Criteria andFavorOutLessThanOrEqualTo(Integer value) {
            addCriterion("favorOut <=", value, "favorOut");
            return (Criteria) this;
        }

        public Criteria andFavorOutIn(List<Integer> values) {
            addCriterion("favorOut in", values, "favorOut");
            return (Criteria) this;
        }

        public Criteria andFavorOutNotIn(List<Integer> values) {
            addCriterion("favorOut not in", values, "favorOut");
            return (Criteria) this;
        }

        public Criteria andFavorOutBetween(Integer value1, Integer value2) {
            addCriterion("favorOut between", value1, value2, "favorOut");
            return (Criteria) this;
        }

        public Criteria andFavorOutNotBetween(Integer value1, Integer value2) {
            addCriterion("favorOut not between", value1, value2, "favorOut");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Byte value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Byte value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Byte value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Byte value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Byte value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Byte value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Byte> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Byte> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Byte value1, Byte value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Byte value1, Byte value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andCtimeIsNull() {
            addCriterion("ctime is null");
            return (Criteria) this;
        }

        public Criteria andCtimeIsNotNull() {
            addCriterion("ctime is not null");
            return (Criteria) this;
        }

        public Criteria andCtimeEqualTo(Date value) {
            addCriterion("ctime =", value, "ctime");
            return (Criteria) this;
        }

        public Criteria andCtimeNotEqualTo(Date value) {
            addCriterion("ctime <>", value, "ctime");
            return (Criteria) this;
        }

        public Criteria andCtimeGreaterThan(Date value) {
            addCriterion("ctime >", value, "ctime");
            return (Criteria) this;
        }

        public Criteria andCtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("ctime >=", value, "ctime");
            return (Criteria) this;
        }

        public Criteria andCtimeLessThan(Date value) {
            addCriterion("ctime <", value, "ctime");
            return (Criteria) this;
        }

        public Criteria andCtimeLessThanOrEqualTo(Date value) {
            addCriterion("ctime <=", value, "ctime");
            return (Criteria) this;
        }

        public Criteria andCtimeIn(List<Date> values) {
            addCriterion("ctime in", values, "ctime");
            return (Criteria) this;
        }

        public Criteria andCtimeNotIn(List<Date> values) {
            addCriterion("ctime not in", values, "ctime");
            return (Criteria) this;
        }

        public Criteria andCtimeBetween(Date value1, Date value2) {
            addCriterion("ctime between", value1, value2, "ctime");
            return (Criteria) this;
        }

        public Criteria andCtimeNotBetween(Date value1, Date value2) {
            addCriterion("ctime not between", value1, value2, "ctime");
            return (Criteria) this;
        }

        public Criteria andMtimeIsNull() {
            addCriterion("mtime is null");
            return (Criteria) this;
        }

        public Criteria andMtimeIsNotNull() {
            addCriterion("mtime is not null");
            return (Criteria) this;
        }

        public Criteria andMtimeEqualTo(Date value) {
            addCriterion("mtime =", value, "mtime");
            return (Criteria) this;
        }

        public Criteria andMtimeNotEqualTo(Date value) {
            addCriterion("mtime <>", value, "mtime");
            return (Criteria) this;
        }

        public Criteria andMtimeGreaterThan(Date value) {
            addCriterion("mtime >", value, "mtime");
            return (Criteria) this;
        }

        public Criteria andMtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("mtime >=", value, "mtime");
            return (Criteria) this;
        }

        public Criteria andMtimeLessThan(Date value) {
            addCriterion("mtime <", value, "mtime");
            return (Criteria) this;
        }

        public Criteria andMtimeLessThanOrEqualTo(Date value) {
            addCriterion("mtime <=", value, "mtime");
            return (Criteria) this;
        }

        public Criteria andMtimeIn(List<Date> values) {
            addCriterion("mtime in", values, "mtime");
            return (Criteria) this;
        }

        public Criteria andMtimeNotIn(List<Date> values) {
            addCriterion("mtime not in", values, "mtime");
            return (Criteria) this;
        }

        public Criteria andMtimeBetween(Date value1, Date value2) {
            addCriterion("mtime between", value1, value2, "mtime");
            return (Criteria) this;
        }

        public Criteria andMtimeNotBetween(Date value1, Date value2) {
            addCriterion("mtime not between", value1, value2, "mtime");
            return (Criteria) this;
        }

        public Criteria andExtraIsNull() {
            addCriterion("extra is null");
            return (Criteria) this;
        }

        public Criteria andExtraIsNotNull() {
            addCriterion("extra is not null");
            return (Criteria) this;
        }

        public Criteria andExtraEqualTo(String value) {
            addCriterion("extra =", value, "extra");
            return (Criteria) this;
        }

        public Criteria andExtraNotEqualTo(String value) {
            addCriterion("extra <>", value, "extra");
            return (Criteria) this;
        }

        public Criteria andExtraGreaterThan(String value) {
            addCriterion("extra >", value, "extra");
            return (Criteria) this;
        }

        public Criteria andExtraGreaterThanOrEqualTo(String value) {
            addCriterion("extra >=", value, "extra");
            return (Criteria) this;
        }

        public Criteria andExtraLessThan(String value) {
            addCriterion("extra <", value, "extra");
            return (Criteria) this;
        }

        public Criteria andExtraLessThanOrEqualTo(String value) {
            addCriterion("extra <=", value, "extra");
            return (Criteria) this;
        }

        public Criteria andExtraLike(String value) {
            addCriterion("extra like", value, "extra");
            return (Criteria) this;
        }

        public Criteria andExtraNotLike(String value) {
            addCriterion("extra not like", value, "extra");
            return (Criteria) this;
        }

        public Criteria andExtraIn(List<String> values) {
            addCriterion("extra in", values, "extra");
            return (Criteria) this;
        }

        public Criteria andExtraNotIn(List<String> values) {
            addCriterion("extra not in", values, "extra");
            return (Criteria) this;
        }

        public Criteria andExtraBetween(String value1, String value2) {
            addCriterion("extra between", value1, value2, "extra");
            return (Criteria) this;
        }

        public Criteria andExtraNotBetween(String value1, String value2) {
            addCriterion("extra not between", value1, value2, "extra");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}