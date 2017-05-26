package com.lili.exam.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExamPlaceOrderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ExamPlaceOrderExample() {
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

        public Criteria andOrderIdIsNull() {
            addCriterion("orderId is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("orderId is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("orderId =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(String value) {
            addCriterion("orderId <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(String value) {
            addCriterion("orderId >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("orderId >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(String value) {
            addCriterion("orderId <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(String value) {
            addCriterion("orderId <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLike(String value) {
            addCriterion("orderId like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotLike(String value) {
            addCriterion("orderId not like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<String> values) {
            addCriterion("orderId in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<String> values) {
            addCriterion("orderId not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(String value1, String value2) {
            addCriterion("orderId between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(String value1, String value2) {
            addCriterion("orderId not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andClassIdIsNull() {
            addCriterion("classId is null");
            return (Criteria) this;
        }

        public Criteria andClassIdIsNotNull() {
            addCriterion("classId is not null");
            return (Criteria) this;
        }

        public Criteria andClassIdEqualTo(Integer value) {
            addCriterion("classId =", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdNotEqualTo(Integer value) {
            addCriterion("classId <>", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdGreaterThan(Integer value) {
            addCriterion("classId >", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("classId >=", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdLessThan(Integer value) {
            addCriterion("classId <", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdLessThanOrEqualTo(Integer value) {
            addCriterion("classId <=", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdIn(List<Integer> values) {
            addCriterion("classId in", values, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdNotIn(List<Integer> values) {
            addCriterion("classId not in", values, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdBetween(Integer value1, Integer value2) {
            addCriterion("classId between", value1, value2, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdNotBetween(Integer value1, Integer value2) {
            addCriterion("classId not between", value1, value2, "classId");
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

        public Criteria andPlaceNameIsNull() {
            addCriterion("placeName is null");
            return (Criteria) this;
        }

        public Criteria andPlaceNameIsNotNull() {
            addCriterion("placeName is not null");
            return (Criteria) this;
        }

        public Criteria andPlaceNameEqualTo(String value) {
            addCriterion("placeName =", value, "placeName");
            return (Criteria) this;
        }

        public Criteria andPlaceNameNotEqualTo(String value) {
            addCriterion("placeName <>", value, "placeName");
            return (Criteria) this;
        }

        public Criteria andPlaceNameGreaterThan(String value) {
            addCriterion("placeName >", value, "placeName");
            return (Criteria) this;
        }

        public Criteria andPlaceNameGreaterThanOrEqualTo(String value) {
            addCriterion("placeName >=", value, "placeName");
            return (Criteria) this;
        }

        public Criteria andPlaceNameLessThan(String value) {
            addCriterion("placeName <", value, "placeName");
            return (Criteria) this;
        }

        public Criteria andPlaceNameLessThanOrEqualTo(String value) {
            addCriterion("placeName <=", value, "placeName");
            return (Criteria) this;
        }

        public Criteria andPlaceNameLike(String value) {
            addCriterion("placeName like", value, "placeName");
            return (Criteria) this;
        }

        public Criteria andPlaceNameNotLike(String value) {
            addCriterion("placeName not like", value, "placeName");
            return (Criteria) this;
        }

        public Criteria andPlaceNameIn(List<String> values) {
            addCriterion("placeName in", values, "placeName");
            return (Criteria) this;
        }

        public Criteria andPlaceNameNotIn(List<String> values) {
            addCriterion("placeName not in", values, "placeName");
            return (Criteria) this;
        }

        public Criteria andPlaceNameBetween(String value1, String value2) {
            addCriterion("placeName between", value1, value2, "placeName");
            return (Criteria) this;
        }

        public Criteria andPlaceNameNotBetween(String value1, String value2) {
            addCriterion("placeName not between", value1, value2, "placeName");
            return (Criteria) this;
        }

        public Criteria andSchoolIsNull() {
            addCriterion("school is null");
            return (Criteria) this;
        }

        public Criteria andSchoolIsNotNull() {
            addCriterion("school is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolEqualTo(String value) {
            addCriterion("school =", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolNotEqualTo(String value) {
            addCriterion("school <>", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolGreaterThan(String value) {
            addCriterion("school >", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolGreaterThanOrEqualTo(String value) {
            addCriterion("school >=", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolLessThan(String value) {
            addCriterion("school <", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolLessThanOrEqualTo(String value) {
            addCriterion("school <=", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolLike(String value) {
            addCriterion("school like", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolNotLike(String value) {
            addCriterion("school not like", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolIn(List<String> values) {
            addCriterion("school in", values, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolNotIn(List<String> values) {
            addCriterion("school not in", values, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolBetween(String value1, String value2) {
            addCriterion("school between", value1, value2, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolNotBetween(String value1, String value2) {
            addCriterion("school not between", value1, value2, "school");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Byte value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Byte value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Byte value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Byte value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Byte value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Byte> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Byte> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Byte value1, Byte value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andDrtypeIsNull() {
            addCriterion("drtype is null");
            return (Criteria) this;
        }

        public Criteria andDrtypeIsNotNull() {
            addCriterion("drtype is not null");
            return (Criteria) this;
        }

        public Criteria andDrtypeEqualTo(Byte value) {
            addCriterion("drtype =", value, "drtype");
            return (Criteria) this;
        }

        public Criteria andDrtypeNotEqualTo(Byte value) {
            addCriterion("drtype <>", value, "drtype");
            return (Criteria) this;
        }

        public Criteria andDrtypeGreaterThan(Byte value) {
            addCriterion("drtype >", value, "drtype");
            return (Criteria) this;
        }

        public Criteria andDrtypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("drtype >=", value, "drtype");
            return (Criteria) this;
        }

        public Criteria andDrtypeLessThan(Byte value) {
            addCriterion("drtype <", value, "drtype");
            return (Criteria) this;
        }

        public Criteria andDrtypeLessThanOrEqualTo(Byte value) {
            addCriterion("drtype <=", value, "drtype");
            return (Criteria) this;
        }

        public Criteria andDrtypeIn(List<Byte> values) {
            addCriterion("drtype in", values, "drtype");
            return (Criteria) this;
        }

        public Criteria andDrtypeNotIn(List<Byte> values) {
            addCriterion("drtype not in", values, "drtype");
            return (Criteria) this;
        }

        public Criteria andDrtypeBetween(Byte value1, Byte value2) {
            addCriterion("drtype between", value1, value2, "drtype");
            return (Criteria) this;
        }

        public Criteria andDrtypeNotBetween(Byte value1, Byte value2) {
            addCriterion("drtype not between", value1, value2, "drtype");
            return (Criteria) this;
        }

        public Criteria andCoachIdIsNull() {
            addCriterion("coachId is null");
            return (Criteria) this;
        }

        public Criteria andCoachIdIsNotNull() {
            addCriterion("coachId is not null");
            return (Criteria) this;
        }

        public Criteria andCoachIdEqualTo(Long value) {
            addCriterion("coachId =", value, "coachId");
            return (Criteria) this;
        }

        public Criteria andCoachIdNotEqualTo(Long value) {
            addCriterion("coachId <>", value, "coachId");
            return (Criteria) this;
        }

        public Criteria andCoachIdGreaterThan(Long value) {
            addCriterion("coachId >", value, "coachId");
            return (Criteria) this;
        }

        public Criteria andCoachIdGreaterThanOrEqualTo(Long value) {
            addCriterion("coachId >=", value, "coachId");
            return (Criteria) this;
        }

        public Criteria andCoachIdLessThan(Long value) {
            addCriterion("coachId <", value, "coachId");
            return (Criteria) this;
        }

        public Criteria andCoachIdLessThanOrEqualTo(Long value) {
            addCriterion("coachId <=", value, "coachId");
            return (Criteria) this;
        }

        public Criteria andCoachIdIn(List<Long> values) {
            addCriterion("coachId in", values, "coachId");
            return (Criteria) this;
        }

        public Criteria andCoachIdNotIn(List<Long> values) {
            addCriterion("coachId not in", values, "coachId");
            return (Criteria) this;
        }

        public Criteria andCoachIdBetween(Long value1, Long value2) {
            addCriterion("coachId between", value1, value2, "coachId");
            return (Criteria) this;
        }

        public Criteria andCoachIdNotBetween(Long value1, Long value2) {
            addCriterion("coachId not between", value1, value2, "coachId");
            return (Criteria) this;
        }

        public Criteria andCoachNameIsNull() {
            addCriterion("coachName is null");
            return (Criteria) this;
        }

        public Criteria andCoachNameIsNotNull() {
            addCriterion("coachName is not null");
            return (Criteria) this;
        }

        public Criteria andCoachNameEqualTo(String value) {
            addCriterion("coachName =", value, "coachName");
            return (Criteria) this;
        }

        public Criteria andCoachNameNotEqualTo(String value) {
            addCriterion("coachName <>", value, "coachName");
            return (Criteria) this;
        }

        public Criteria andCoachNameGreaterThan(String value) {
            addCriterion("coachName >", value, "coachName");
            return (Criteria) this;
        }

        public Criteria andCoachNameGreaterThanOrEqualTo(String value) {
            addCriterion("coachName >=", value, "coachName");
            return (Criteria) this;
        }

        public Criteria andCoachNameLessThan(String value) {
            addCriterion("coachName <", value, "coachName");
            return (Criteria) this;
        }

        public Criteria andCoachNameLessThanOrEqualTo(String value) {
            addCriterion("coachName <=", value, "coachName");
            return (Criteria) this;
        }

        public Criteria andCoachNameLike(String value) {
            addCriterion("coachName like", value, "coachName");
            return (Criteria) this;
        }

        public Criteria andCoachNameNotLike(String value) {
            addCriterion("coachName not like", value, "coachName");
            return (Criteria) this;
        }

        public Criteria andCoachNameIn(List<String> values) {
            addCriterion("coachName in", values, "coachName");
            return (Criteria) this;
        }

        public Criteria andCoachNameNotIn(List<String> values) {
            addCriterion("coachName not in", values, "coachName");
            return (Criteria) this;
        }

        public Criteria andCoachNameBetween(String value1, String value2) {
            addCriterion("coachName between", value1, value2, "coachName");
            return (Criteria) this;
        }

        public Criteria andCoachNameNotBetween(String value1, String value2) {
            addCriterion("coachName not between", value1, value2, "coachName");
            return (Criteria) this;
        }

        public Criteria andCoachMobileIsNull() {
            addCriterion("coachMobile is null");
            return (Criteria) this;
        }

        public Criteria andCoachMobileIsNotNull() {
            addCriterion("coachMobile is not null");
            return (Criteria) this;
        }

        public Criteria andCoachMobileEqualTo(String value) {
            addCriterion("coachMobile =", value, "coachMobile");
            return (Criteria) this;
        }

        public Criteria andCoachMobileNotEqualTo(String value) {
            addCriterion("coachMobile <>", value, "coachMobile");
            return (Criteria) this;
        }

        public Criteria andCoachMobileGreaterThan(String value) {
            addCriterion("coachMobile >", value, "coachMobile");
            return (Criteria) this;
        }

        public Criteria andCoachMobileGreaterThanOrEqualTo(String value) {
            addCriterion("coachMobile >=", value, "coachMobile");
            return (Criteria) this;
        }

        public Criteria andCoachMobileLessThan(String value) {
            addCriterion("coachMobile <", value, "coachMobile");
            return (Criteria) this;
        }

        public Criteria andCoachMobileLessThanOrEqualTo(String value) {
            addCriterion("coachMobile <=", value, "coachMobile");
            return (Criteria) this;
        }

        public Criteria andCoachMobileLike(String value) {
            addCriterion("coachMobile like", value, "coachMobile");
            return (Criteria) this;
        }

        public Criteria andCoachMobileNotLike(String value) {
            addCriterion("coachMobile not like", value, "coachMobile");
            return (Criteria) this;
        }

        public Criteria andCoachMobileIn(List<String> values) {
            addCriterion("coachMobile in", values, "coachMobile");
            return (Criteria) this;
        }

        public Criteria andCoachMobileNotIn(List<String> values) {
            addCriterion("coachMobile not in", values, "coachMobile");
            return (Criteria) this;
        }

        public Criteria andCoachMobileBetween(String value1, String value2) {
            addCriterion("coachMobile between", value1, value2, "coachMobile");
            return (Criteria) this;
        }

        public Criteria andCoachMobileNotBetween(String value1, String value2) {
            addCriterion("coachMobile not between", value1, value2, "coachMobile");
            return (Criteria) this;
        }

        public Criteria andCoachImgIsNull() {
            addCriterion("coachImg is null");
            return (Criteria) this;
        }

        public Criteria andCoachImgIsNotNull() {
            addCriterion("coachImg is not null");
            return (Criteria) this;
        }

        public Criteria andCoachImgEqualTo(String value) {
            addCriterion("coachImg =", value, "coachImg");
            return (Criteria) this;
        }

        public Criteria andCoachImgNotEqualTo(String value) {
            addCriterion("coachImg <>", value, "coachImg");
            return (Criteria) this;
        }

        public Criteria andCoachImgGreaterThan(String value) {
            addCriterion("coachImg >", value, "coachImg");
            return (Criteria) this;
        }

        public Criteria andCoachImgGreaterThanOrEqualTo(String value) {
            addCriterion("coachImg >=", value, "coachImg");
            return (Criteria) this;
        }

        public Criteria andCoachImgLessThan(String value) {
            addCriterion("coachImg <", value, "coachImg");
            return (Criteria) this;
        }

        public Criteria andCoachImgLessThanOrEqualTo(String value) {
            addCriterion("coachImg <=", value, "coachImg");
            return (Criteria) this;
        }

        public Criteria andCoachImgLike(String value) {
            addCriterion("coachImg like", value, "coachImg");
            return (Criteria) this;
        }

        public Criteria andCoachImgNotLike(String value) {
            addCriterion("coachImg not like", value, "coachImg");
            return (Criteria) this;
        }

        public Criteria andCoachImgIn(List<String> values) {
            addCriterion("coachImg in", values, "coachImg");
            return (Criteria) this;
        }

        public Criteria andCoachImgNotIn(List<String> values) {
            addCriterion("coachImg not in", values, "coachImg");
            return (Criteria) this;
        }

        public Criteria andCoachImgBetween(String value1, String value2) {
            addCriterion("coachImg between", value1, value2, "coachImg");
            return (Criteria) this;
        }

        public Criteria andCoachImgNotBetween(String value1, String value2) {
            addCriterion("coachImg not between", value1, value2, "coachImg");
            return (Criteria) this;
        }

        public Criteria andCoachTypeIsNull() {
            addCriterion("coachType is null");
            return (Criteria) this;
        }

        public Criteria andCoachTypeIsNotNull() {
            addCriterion("coachType is not null");
            return (Criteria) this;
        }

        public Criteria andCoachTypeEqualTo(Byte value) {
            addCriterion("coachType =", value, "coachType");
            return (Criteria) this;
        }

        public Criteria andCoachTypeNotEqualTo(Byte value) {
            addCriterion("coachType <>", value, "coachType");
            return (Criteria) this;
        }

        public Criteria andCoachTypeGreaterThan(Byte value) {
            addCriterion("coachType >", value, "coachType");
            return (Criteria) this;
        }

        public Criteria andCoachTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("coachType >=", value, "coachType");
            return (Criteria) this;
        }

        public Criteria andCoachTypeLessThan(Byte value) {
            addCriterion("coachType <", value, "coachType");
            return (Criteria) this;
        }

        public Criteria andCoachTypeLessThanOrEqualTo(Byte value) {
            addCriterion("coachType <=", value, "coachType");
            return (Criteria) this;
        }

        public Criteria andCoachTypeIn(List<Byte> values) {
            addCriterion("coachType in", values, "coachType");
            return (Criteria) this;
        }

        public Criteria andCoachTypeNotIn(List<Byte> values) {
            addCriterion("coachType not in", values, "coachType");
            return (Criteria) this;
        }

        public Criteria andCoachTypeBetween(Byte value1, Byte value2) {
            addCriterion("coachType between", value1, value2, "coachType");
            return (Criteria) this;
        }

        public Criteria andCoachTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("coachType not between", value1, value2, "coachType");
            return (Criteria) this;
        }

        public Criteria andCarNoIsNull() {
            addCriterion("carNo is null");
            return (Criteria) this;
        }

        public Criteria andCarNoIsNotNull() {
            addCriterion("carNo is not null");
            return (Criteria) this;
        }

        public Criteria andCarNoEqualTo(String value) {
            addCriterion("carNo =", value, "carNo");
            return (Criteria) this;
        }

        public Criteria andCarNoNotEqualTo(String value) {
            addCriterion("carNo <>", value, "carNo");
            return (Criteria) this;
        }

        public Criteria andCarNoGreaterThan(String value) {
            addCriterion("carNo >", value, "carNo");
            return (Criteria) this;
        }

        public Criteria andCarNoGreaterThanOrEqualTo(String value) {
            addCriterion("carNo >=", value, "carNo");
            return (Criteria) this;
        }

        public Criteria andCarNoLessThan(String value) {
            addCriterion("carNo <", value, "carNo");
            return (Criteria) this;
        }

        public Criteria andCarNoLessThanOrEqualTo(String value) {
            addCriterion("carNo <=", value, "carNo");
            return (Criteria) this;
        }

        public Criteria andCarNoLike(String value) {
            addCriterion("carNo like", value, "carNo");
            return (Criteria) this;
        }

        public Criteria andCarNoNotLike(String value) {
            addCriterion("carNo not like", value, "carNo");
            return (Criteria) this;
        }

        public Criteria andCarNoIn(List<String> values) {
            addCriterion("carNo in", values, "carNo");
            return (Criteria) this;
        }

        public Criteria andCarNoNotIn(List<String> values) {
            addCriterion("carNo not in", values, "carNo");
            return (Criteria) this;
        }

        public Criteria andCarNoBetween(String value1, String value2) {
            addCriterion("carNo between", value1, value2, "carNo");
            return (Criteria) this;
        }

        public Criteria andCarNoNotBetween(String value1, String value2) {
            addCriterion("carNo not between", value1, value2, "carNo");
            return (Criteria) this;
        }

        public Criteria andDeviceIsNull() {
            addCriterion("device is null");
            return (Criteria) this;
        }

        public Criteria andDeviceIsNotNull() {
            addCriterion("device is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceEqualTo(String value) {
            addCriterion("device =", value, "device");
            return (Criteria) this;
        }

        public Criteria andDeviceNotEqualTo(String value) {
            addCriterion("device <>", value, "device");
            return (Criteria) this;
        }

        public Criteria andDeviceGreaterThan(String value) {
            addCriterion("device >", value, "device");
            return (Criteria) this;
        }

        public Criteria andDeviceGreaterThanOrEqualTo(String value) {
            addCriterion("device >=", value, "device");
            return (Criteria) this;
        }

        public Criteria andDeviceLessThan(String value) {
            addCriterion("device <", value, "device");
            return (Criteria) this;
        }

        public Criteria andDeviceLessThanOrEqualTo(String value) {
            addCriterion("device <=", value, "device");
            return (Criteria) this;
        }

        public Criteria andDeviceLike(String value) {
            addCriterion("device like", value, "device");
            return (Criteria) this;
        }

        public Criteria andDeviceNotLike(String value) {
            addCriterion("device not like", value, "device");
            return (Criteria) this;
        }

        public Criteria andDeviceIn(List<String> values) {
            addCriterion("device in", values, "device");
            return (Criteria) this;
        }

        public Criteria andDeviceNotIn(List<String> values) {
            addCriterion("device not in", values, "device");
            return (Criteria) this;
        }

        public Criteria andDeviceBetween(String value1, String value2) {
            addCriterion("device between", value1, value2, "device");
            return (Criteria) this;
        }

        public Criteria andDeviceNotBetween(String value1, String value2) {
            addCriterion("device not between", value1, value2, "device");
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

        public Criteria andFavorUseIsNull() {
            addCriterion("favorUse is null");
            return (Criteria) this;
        }

        public Criteria andFavorUseIsNotNull() {
            addCriterion("favorUse is not null");
            return (Criteria) this;
        }

        public Criteria andFavorUseEqualTo(Integer value) {
            addCriterion("favorUse =", value, "favorUse");
            return (Criteria) this;
        }

        public Criteria andFavorUseNotEqualTo(Integer value) {
            addCriterion("favorUse <>", value, "favorUse");
            return (Criteria) this;
        }

        public Criteria andFavorUseGreaterThan(Integer value) {
            addCriterion("favorUse >", value, "favorUse");
            return (Criteria) this;
        }

        public Criteria andFavorUseGreaterThanOrEqualTo(Integer value) {
            addCriterion("favorUse >=", value, "favorUse");
            return (Criteria) this;
        }

        public Criteria andFavorUseLessThan(Integer value) {
            addCriterion("favorUse <", value, "favorUse");
            return (Criteria) this;
        }

        public Criteria andFavorUseLessThanOrEqualTo(Integer value) {
            addCriterion("favorUse <=", value, "favorUse");
            return (Criteria) this;
        }

        public Criteria andFavorUseIn(List<Integer> values) {
            addCriterion("favorUse in", values, "favorUse");
            return (Criteria) this;
        }

        public Criteria andFavorUseNotIn(List<Integer> values) {
            addCriterion("favorUse not in", values, "favorUse");
            return (Criteria) this;
        }

        public Criteria andFavorUseBetween(Integer value1, Integer value2) {
            addCriterion("favorUse between", value1, value2, "favorUse");
            return (Criteria) this;
        }

        public Criteria andFavorUseNotBetween(Integer value1, Integer value2) {
            addCriterion("favorUse not between", value1, value2, "favorUse");
            return (Criteria) this;
        }

        public Criteria andFavorGenIsNull() {
            addCriterion("favorGen is null");
            return (Criteria) this;
        }

        public Criteria andFavorGenIsNotNull() {
            addCriterion("favorGen is not null");
            return (Criteria) this;
        }

        public Criteria andFavorGenEqualTo(Integer value) {
            addCriterion("favorGen =", value, "favorGen");
            return (Criteria) this;
        }

        public Criteria andFavorGenNotEqualTo(Integer value) {
            addCriterion("favorGen <>", value, "favorGen");
            return (Criteria) this;
        }

        public Criteria andFavorGenGreaterThan(Integer value) {
            addCriterion("favorGen >", value, "favorGen");
            return (Criteria) this;
        }

        public Criteria andFavorGenGreaterThanOrEqualTo(Integer value) {
            addCriterion("favorGen >=", value, "favorGen");
            return (Criteria) this;
        }

        public Criteria andFavorGenLessThan(Integer value) {
            addCriterion("favorGen <", value, "favorGen");
            return (Criteria) this;
        }

        public Criteria andFavorGenLessThanOrEqualTo(Integer value) {
            addCriterion("favorGen <=", value, "favorGen");
            return (Criteria) this;
        }

        public Criteria andFavorGenIn(List<Integer> values) {
            addCriterion("favorGen in", values, "favorGen");
            return (Criteria) this;
        }

        public Criteria andFavorGenNotIn(List<Integer> values) {
            addCriterion("favorGen not in", values, "favorGen");
            return (Criteria) this;
        }

        public Criteria andFavorGenBetween(Integer value1, Integer value2) {
            addCriterion("favorGen between", value1, value2, "favorGen");
            return (Criteria) this;
        }

        public Criteria andFavorGenNotBetween(Integer value1, Integer value2) {
            addCriterion("favorGen not between", value1, value2, "favorGen");
            return (Criteria) this;
        }

        public Criteria andFavorLeftIsNull() {
            addCriterion("favorLeft is null");
            return (Criteria) this;
        }

        public Criteria andFavorLeftIsNotNull() {
            addCriterion("favorLeft is not null");
            return (Criteria) this;
        }

        public Criteria andFavorLeftEqualTo(Integer value) {
            addCriterion("favorLeft =", value, "favorLeft");
            return (Criteria) this;
        }

        public Criteria andFavorLeftNotEqualTo(Integer value) {
            addCriterion("favorLeft <>", value, "favorLeft");
            return (Criteria) this;
        }

        public Criteria andFavorLeftGreaterThan(Integer value) {
            addCriterion("favorLeft >", value, "favorLeft");
            return (Criteria) this;
        }

        public Criteria andFavorLeftGreaterThanOrEqualTo(Integer value) {
            addCriterion("favorLeft >=", value, "favorLeft");
            return (Criteria) this;
        }

        public Criteria andFavorLeftLessThan(Integer value) {
            addCriterion("favorLeft <", value, "favorLeft");
            return (Criteria) this;
        }

        public Criteria andFavorLeftLessThanOrEqualTo(Integer value) {
            addCriterion("favorLeft <=", value, "favorLeft");
            return (Criteria) this;
        }

        public Criteria andFavorLeftIn(List<Integer> values) {
            addCriterion("favorLeft in", values, "favorLeft");
            return (Criteria) this;
        }

        public Criteria andFavorLeftNotIn(List<Integer> values) {
            addCriterion("favorLeft not in", values, "favorLeft");
            return (Criteria) this;
        }

        public Criteria andFavorLeftBetween(Integer value1, Integer value2) {
            addCriterion("favorLeft between", value1, value2, "favorLeft");
            return (Criteria) this;
        }

        public Criteria andFavorLeftNotBetween(Integer value1, Integer value2) {
            addCriterion("favorLeft not between", value1, value2, "favorLeft");
            return (Criteria) this;
        }

        public Criteria andFavorInfoIsNull() {
            addCriterion("favorInfo is null");
            return (Criteria) this;
        }

        public Criteria andFavorInfoIsNotNull() {
            addCriterion("favorInfo is not null");
            return (Criteria) this;
        }

        public Criteria andFavorInfoEqualTo(String value) {
            addCriterion("favorInfo =", value, "favorInfo");
            return (Criteria) this;
        }

        public Criteria andFavorInfoNotEqualTo(String value) {
            addCriterion("favorInfo <>", value, "favorInfo");
            return (Criteria) this;
        }

        public Criteria andFavorInfoGreaterThan(String value) {
            addCriterion("favorInfo >", value, "favorInfo");
            return (Criteria) this;
        }

        public Criteria andFavorInfoGreaterThanOrEqualTo(String value) {
            addCriterion("favorInfo >=", value, "favorInfo");
            return (Criteria) this;
        }

        public Criteria andFavorInfoLessThan(String value) {
            addCriterion("favorInfo <", value, "favorInfo");
            return (Criteria) this;
        }

        public Criteria andFavorInfoLessThanOrEqualTo(String value) {
            addCriterion("favorInfo <=", value, "favorInfo");
            return (Criteria) this;
        }

        public Criteria andFavorInfoLike(String value) {
            addCriterion("favorInfo like", value, "favorInfo");
            return (Criteria) this;
        }

        public Criteria andFavorInfoNotLike(String value) {
            addCriterion("favorInfo not like", value, "favorInfo");
            return (Criteria) this;
        }

        public Criteria andFavorInfoIn(List<String> values) {
            addCriterion("favorInfo in", values, "favorInfo");
            return (Criteria) this;
        }

        public Criteria andFavorInfoNotIn(List<String> values) {
            addCriterion("favorInfo not in", values, "favorInfo");
            return (Criteria) this;
        }

        public Criteria andFavorInfoBetween(String value1, String value2) {
            addCriterion("favorInfo between", value1, value2, "favorInfo");
            return (Criteria) this;
        }

        public Criteria andFavorInfoNotBetween(String value1, String value2) {
            addCriterion("favorInfo not between", value1, value2, "favorInfo");
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

        public Criteria andDelayIsNull() {
            addCriterion("delay is null");
            return (Criteria) this;
        }

        public Criteria andDelayIsNotNull() {
            addCriterion("delay is not null");
            return (Criteria) this;
        }

        public Criteria andDelayEqualTo(Integer value) {
            addCriterion("delay =", value, "delay");
            return (Criteria) this;
        }

        public Criteria andDelayNotEqualTo(Integer value) {
            addCriterion("delay <>", value, "delay");
            return (Criteria) this;
        }

        public Criteria andDelayGreaterThan(Integer value) {
            addCriterion("delay >", value, "delay");
            return (Criteria) this;
        }

        public Criteria andDelayGreaterThanOrEqualTo(Integer value) {
            addCriterion("delay >=", value, "delay");
            return (Criteria) this;
        }

        public Criteria andDelayLessThan(Integer value) {
            addCriterion("delay <", value, "delay");
            return (Criteria) this;
        }

        public Criteria andDelayLessThanOrEqualTo(Integer value) {
            addCriterion("delay <=", value, "delay");
            return (Criteria) this;
        }

        public Criteria andDelayIn(List<Integer> values) {
            addCriterion("delay in", values, "delay");
            return (Criteria) this;
        }

        public Criteria andDelayNotIn(List<Integer> values) {
            addCriterion("delay not in", values, "delay");
            return (Criteria) this;
        }

        public Criteria andDelayBetween(Integer value1, Integer value2) {
            addCriterion("delay between", value1, value2, "delay");
            return (Criteria) this;
        }

        public Criteria andDelayNotBetween(Integer value1, Integer value2) {
            addCriterion("delay not between", value1, value2, "delay");
            return (Criteria) this;
        }

        public Criteria andDelayInfoIsNull() {
            addCriterion("delayInfo is null");
            return (Criteria) this;
        }

        public Criteria andDelayInfoIsNotNull() {
            addCriterion("delayInfo is not null");
            return (Criteria) this;
        }

        public Criteria andDelayInfoEqualTo(String value) {
            addCriterion("delayInfo =", value, "delayInfo");
            return (Criteria) this;
        }

        public Criteria andDelayInfoNotEqualTo(String value) {
            addCriterion("delayInfo <>", value, "delayInfo");
            return (Criteria) this;
        }

        public Criteria andDelayInfoGreaterThan(String value) {
            addCriterion("delayInfo >", value, "delayInfo");
            return (Criteria) this;
        }

        public Criteria andDelayInfoGreaterThanOrEqualTo(String value) {
            addCriterion("delayInfo >=", value, "delayInfo");
            return (Criteria) this;
        }

        public Criteria andDelayInfoLessThan(String value) {
            addCriterion("delayInfo <", value, "delayInfo");
            return (Criteria) this;
        }

        public Criteria andDelayInfoLessThanOrEqualTo(String value) {
            addCriterion("delayInfo <=", value, "delayInfo");
            return (Criteria) this;
        }

        public Criteria andDelayInfoLike(String value) {
            addCriterion("delayInfo like", value, "delayInfo");
            return (Criteria) this;
        }

        public Criteria andDelayInfoNotLike(String value) {
            addCriterion("delayInfo not like", value, "delayInfo");
            return (Criteria) this;
        }

        public Criteria andDelayInfoIn(List<String> values) {
            addCriterion("delayInfo in", values, "delayInfo");
            return (Criteria) this;
        }

        public Criteria andDelayInfoNotIn(List<String> values) {
            addCriterion("delayInfo not in", values, "delayInfo");
            return (Criteria) this;
        }

        public Criteria andDelayInfoBetween(String value1, String value2) {
            addCriterion("delayInfo between", value1, value2, "delayInfo");
            return (Criteria) this;
        }

        public Criteria andDelayInfoNotBetween(String value1, String value2) {
            addCriterion("delayInfo not between", value1, value2, "delayInfo");
            return (Criteria) this;
        }

        public Criteria andPriceTotalIsNull() {
            addCriterion("priceTotal is null");
            return (Criteria) this;
        }

        public Criteria andPriceTotalIsNotNull() {
            addCriterion("priceTotal is not null");
            return (Criteria) this;
        }

        public Criteria andPriceTotalEqualTo(Integer value) {
            addCriterion("priceTotal =", value, "priceTotal");
            return (Criteria) this;
        }

        public Criteria andPriceTotalNotEqualTo(Integer value) {
            addCriterion("priceTotal <>", value, "priceTotal");
            return (Criteria) this;
        }

        public Criteria andPriceTotalGreaterThan(Integer value) {
            addCriterion("priceTotal >", value, "priceTotal");
            return (Criteria) this;
        }

        public Criteria andPriceTotalGreaterThanOrEqualTo(Integer value) {
            addCriterion("priceTotal >=", value, "priceTotal");
            return (Criteria) this;
        }

        public Criteria andPriceTotalLessThan(Integer value) {
            addCriterion("priceTotal <", value, "priceTotal");
            return (Criteria) this;
        }

        public Criteria andPriceTotalLessThanOrEqualTo(Integer value) {
            addCriterion("priceTotal <=", value, "priceTotal");
            return (Criteria) this;
        }

        public Criteria andPriceTotalIn(List<Integer> values) {
            addCriterion("priceTotal in", values, "priceTotal");
            return (Criteria) this;
        }

        public Criteria andPriceTotalNotIn(List<Integer> values) {
            addCriterion("priceTotal not in", values, "priceTotal");
            return (Criteria) this;
        }

        public Criteria andPriceTotalBetween(Integer value1, Integer value2) {
            addCriterion("priceTotal between", value1, value2, "priceTotal");
            return (Criteria) this;
        }

        public Criteria andPriceTotalNotBetween(Integer value1, Integer value2) {
            addCriterion("priceTotal not between", value1, value2, "priceTotal");
            return (Criteria) this;
        }

        public Criteria andCouponTotalIsNull() {
            addCriterion("couponTotal is null");
            return (Criteria) this;
        }

        public Criteria andCouponTotalIsNotNull() {
            addCriterion("couponTotal is not null");
            return (Criteria) this;
        }

        public Criteria andCouponTotalEqualTo(Integer value) {
            addCriterion("couponTotal =", value, "couponTotal");
            return (Criteria) this;
        }

        public Criteria andCouponTotalNotEqualTo(Integer value) {
            addCriterion("couponTotal <>", value, "couponTotal");
            return (Criteria) this;
        }

        public Criteria andCouponTotalGreaterThan(Integer value) {
            addCriterion("couponTotal >", value, "couponTotal");
            return (Criteria) this;
        }

        public Criteria andCouponTotalGreaterThanOrEqualTo(Integer value) {
            addCriterion("couponTotal >=", value, "couponTotal");
            return (Criteria) this;
        }

        public Criteria andCouponTotalLessThan(Integer value) {
            addCriterion("couponTotal <", value, "couponTotal");
            return (Criteria) this;
        }

        public Criteria andCouponTotalLessThanOrEqualTo(Integer value) {
            addCriterion("couponTotal <=", value, "couponTotal");
            return (Criteria) this;
        }

        public Criteria andCouponTotalIn(List<Integer> values) {
            addCriterion("couponTotal in", values, "couponTotal");
            return (Criteria) this;
        }

        public Criteria andCouponTotalNotIn(List<Integer> values) {
            addCriterion("couponTotal not in", values, "couponTotal");
            return (Criteria) this;
        }

        public Criteria andCouponTotalBetween(Integer value1, Integer value2) {
            addCriterion("couponTotal between", value1, value2, "couponTotal");
            return (Criteria) this;
        }

        public Criteria andCouponTotalNotBetween(Integer value1, Integer value2) {
            addCriterion("couponTotal not between", value1, value2, "couponTotal");
            return (Criteria) this;
        }

        public Criteria andPayTotalIsNull() {
            addCriterion("payTotal is null");
            return (Criteria) this;
        }

        public Criteria andPayTotalIsNotNull() {
            addCriterion("payTotal is not null");
            return (Criteria) this;
        }

        public Criteria andPayTotalEqualTo(Integer value) {
            addCriterion("payTotal =", value, "payTotal");
            return (Criteria) this;
        }

        public Criteria andPayTotalNotEqualTo(Integer value) {
            addCriterion("payTotal <>", value, "payTotal");
            return (Criteria) this;
        }

        public Criteria andPayTotalGreaterThan(Integer value) {
            addCriterion("payTotal >", value, "payTotal");
            return (Criteria) this;
        }

        public Criteria andPayTotalGreaterThanOrEqualTo(Integer value) {
            addCriterion("payTotal >=", value, "payTotal");
            return (Criteria) this;
        }

        public Criteria andPayTotalLessThan(Integer value) {
            addCriterion("payTotal <", value, "payTotal");
            return (Criteria) this;
        }

        public Criteria andPayTotalLessThanOrEqualTo(Integer value) {
            addCriterion("payTotal <=", value, "payTotal");
            return (Criteria) this;
        }

        public Criteria andPayTotalIn(List<Integer> values) {
            addCriterion("payTotal in", values, "payTotal");
            return (Criteria) this;
        }

        public Criteria andPayTotalNotIn(List<Integer> values) {
            addCriterion("payTotal not in", values, "payTotal");
            return (Criteria) this;
        }

        public Criteria andPayTotalBetween(Integer value1, Integer value2) {
            addCriterion("payTotal between", value1, value2, "payTotal");
            return (Criteria) this;
        }

        public Criteria andPayTotalNotBetween(Integer value1, Integer value2) {
            addCriterion("payTotal not between", value1, value2, "payTotal");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNull() {
            addCriterion("payTime is null");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNotNull() {
            addCriterion("payTime is not null");
            return (Criteria) this;
        }

        public Criteria andPayTimeEqualTo(Date value) {
            addCriterion("payTime =", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotEqualTo(Date value) {
            addCriterion("payTime <>", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThan(Date value) {
            addCriterion("payTime >", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("payTime >=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThan(Date value) {
            addCriterion("payTime <", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThanOrEqualTo(Date value) {
            addCriterion("payTime <=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeIn(List<Date> values) {
            addCriterion("payTime in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotIn(List<Date> values) {
            addCriterion("payTime not in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeBetween(Date value1, Date value2) {
            addCriterion("payTime between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotBetween(Date value1, Date value2) {
            addCriterion("payTime not between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayWayIsNull() {
            addCriterion("payWay is null");
            return (Criteria) this;
        }

        public Criteria andPayWayIsNotNull() {
            addCriterion("payWay is not null");
            return (Criteria) this;
        }

        public Criteria andPayWayEqualTo(String value) {
            addCriterion("payWay =", value, "payWay");
            return (Criteria) this;
        }

        public Criteria andPayWayNotEqualTo(String value) {
            addCriterion("payWay <>", value, "payWay");
            return (Criteria) this;
        }

        public Criteria andPayWayGreaterThan(String value) {
            addCriterion("payWay >", value, "payWay");
            return (Criteria) this;
        }

        public Criteria andPayWayGreaterThanOrEqualTo(String value) {
            addCriterion("payWay >=", value, "payWay");
            return (Criteria) this;
        }

        public Criteria andPayWayLessThan(String value) {
            addCriterion("payWay <", value, "payWay");
            return (Criteria) this;
        }

        public Criteria andPayWayLessThanOrEqualTo(String value) {
            addCriterion("payWay <=", value, "payWay");
            return (Criteria) this;
        }

        public Criteria andPayWayLike(String value) {
            addCriterion("payWay like", value, "payWay");
            return (Criteria) this;
        }

        public Criteria andPayWayNotLike(String value) {
            addCriterion("payWay not like", value, "payWay");
            return (Criteria) this;
        }

        public Criteria andPayWayIn(List<String> values) {
            addCriterion("payWay in", values, "payWay");
            return (Criteria) this;
        }

        public Criteria andPayWayNotIn(List<String> values) {
            addCriterion("payWay not in", values, "payWay");
            return (Criteria) this;
        }

        public Criteria andPayWayBetween(String value1, String value2) {
            addCriterion("payWay between", value1, value2, "payWay");
            return (Criteria) this;
        }

        public Criteria andPayWayNotBetween(String value1, String value2) {
            addCriterion("payWay not between", value1, value2, "payWay");
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
        
        public Criteria andCodeEqualTo(Integer value) {
            addCriterion("code =", value, "code");
            return (Criteria) this;
        }
        
        public Criteria andCodeValidEqualTo(Byte value) {
            addCriterion("codeValid =", value, "codeValid");
            return (Criteria) this;
        }
        
        public Criteria multiColumnOrClause() {
        	addCriterion("(state between 1 and 3 or (state = 4 and payTotal <> 0))");
            return (Criteria)this;
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