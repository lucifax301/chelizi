package com.lili.school.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EnrollTheoryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EnrollTheoryExample() {
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

        public Criteria andTheoryIdIsNull() {
            addCriterion("theoryId is null");
            return (Criteria) this;
        }

        public Criteria andTheoryIdIsNotNull() {
            addCriterion("theoryId is not null");
            return (Criteria) this;
        }

        public Criteria andTheoryIdEqualTo(Integer value) {
            addCriterion("theoryId =", value, "theoryId");
            return (Criteria) this;
        }

        public Criteria andTheoryIdNotEqualTo(Integer value) {
            addCriterion("theoryId <>", value, "theoryId");
            return (Criteria) this;
        }

        public Criteria andTheoryIdGreaterThan(Integer value) {
            addCriterion("theoryId >", value, "theoryId");
            return (Criteria) this;
        }

        public Criteria andTheoryIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("theoryId >=", value, "theoryId");
            return (Criteria) this;
        }

        public Criteria andTheoryIdLessThan(Integer value) {
            addCriterion("theoryId <", value, "theoryId");
            return (Criteria) this;
        }

        public Criteria andTheoryIdLessThanOrEqualTo(Integer value) {
            addCriterion("theoryId <=", value, "theoryId");
            return (Criteria) this;
        }

        public Criteria andTheoryIdIn(List<Integer> values) {
            addCriterion("theoryId in", values, "theoryId");
            return (Criteria) this;
        }

        public Criteria andTheoryIdNotIn(List<Integer> values) {
            addCriterion("theoryId not in", values, "theoryId");
            return (Criteria) this;
        }

        public Criteria andTheoryIdBetween(Integer value1, Integer value2) {
            addCriterion("theoryId between", value1, value2, "theoryId");
            return (Criteria) this;
        }

        public Criteria andTheoryIdNotBetween(Integer value1, Integer value2) {
            addCriterion("theoryId not between", value1, value2, "theoryId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdIsNull() {
            addCriterion("schoolId is null");
            return (Criteria) this;
        }

        public Criteria andSchoolIdIsNotNull() {
            addCriterion("schoolId is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolIdEqualTo(Integer value) {
            addCriterion("schoolId =", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdNotEqualTo(Integer value) {
            addCriterion("schoolId <>", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdGreaterThan(Integer value) {
            addCriterion("schoolId >", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("schoolId >=", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdLessThan(Integer value) {
            addCriterion("schoolId <", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdLessThanOrEqualTo(Integer value) {
            addCriterion("schoolId <=", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdIn(List<Integer> values) {
            addCriterion("schoolId in", values, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdNotIn(List<Integer> values) {
            addCriterion("schoolId not in", values, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdBetween(Integer value1, Integer value2) {
            addCriterion("schoolId between", value1, value2, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdNotBetween(Integer value1, Integer value2) {
            addCriterion("schoolId not between", value1, value2, "schoolId");
            return (Criteria) this;
        }

        public Criteria andClassDateIsNull() {
            addCriterion("classDate is null");
            return (Criteria) this;
        }

        public Criteria andClassDateIsNotNull() {
            addCriterion("classDate is not null");
            return (Criteria) this;
        }

        public Criteria andClassDateEqualTo(Date value) {
            addCriterion("classDate =", value, "classDate");
            return (Criteria) this;
        }

        public Criteria andClassDateNotEqualTo(Date value) {
            addCriterion("classDate <>", value, "classDate");
            return (Criteria) this;
        }

        public Criteria andClassDateGreaterThan(Date value) {
            addCriterion("classDate >", value, "classDate");
            return (Criteria) this;
        }

        public Criteria andClassDateGreaterThanOrEqualTo(Date value) {
            addCriterion("classDate >=", value, "classDate");
            return (Criteria) this;
        }

        public Criteria andClassDateLessThan(Date value) {
            addCriterion("classDate <", value, "classDate");
            return (Criteria) this;
        }

        public Criteria andClassDateLessThanOrEqualTo(Date value) {
            addCriterion("classDate <=", value, "classDate");
            return (Criteria) this;
        }

        public Criteria andClassDateIn(List<Date> values) {
            addCriterion("classDate in", values, "classDate");
            return (Criteria) this;
        }

        public Criteria andClassDateNotIn(List<Date> values) {
            addCriterion("classDate not in", values, "classDate");
            return (Criteria) this;
        }

        public Criteria andClassDateBetween(Date value1, Date value2) {
            addCriterion("classDate between", value1, value2, "classDate");
            return (Criteria) this;
        }

        public Criteria andClassDateNotBetween(Date value1, Date value2) {
            addCriterion("classDate not between", value1, value2, "classDate");
            return (Criteria) this;
        }

        public Criteria andClassTimeIsNull() {
            addCriterion("classTime is null");
            return (Criteria) this;
        }

        public Criteria andClassTimeIsNotNull() {
            addCriterion("classTime is not null");
            return (Criteria) this;
        }

        public Criteria andClassTimeEqualTo(String value) {
            addCriterion("classTime =", value, "classTime");
            return (Criteria) this;
        }

        public Criteria andClassTimeNotEqualTo(String value) {
            addCriterion("classTime <>", value, "classTime");
            return (Criteria) this;
        }

        public Criteria andClassTimeGreaterThan(String value) {
            addCriterion("classTime >", value, "classTime");
            return (Criteria) this;
        }

        public Criteria andClassTimeGreaterThanOrEqualTo(String value) {
            addCriterion("classTime >=", value, "classTime");
            return (Criteria) this;
        }

        public Criteria andClassTimeLessThan(String value) {
            addCriterion("classTime <", value, "classTime");
            return (Criteria) this;
        }

        public Criteria andClassTimeLessThanOrEqualTo(String value) {
            addCriterion("classTime <=", value, "classTime");
            return (Criteria) this;
        }

        public Criteria andClassTimeLike(String value) {
            addCriterion("classTime like", value, "classTime");
            return (Criteria) this;
        }

        public Criteria andClassTimeNotLike(String value) {
            addCriterion("classTime not like", value, "classTime");
            return (Criteria) this;
        }

        public Criteria andClassTimeIn(List<String> values) {
            addCriterion("classTime in", values, "classTime");
            return (Criteria) this;
        }

        public Criteria andClassTimeNotIn(List<String> values) {
            addCriterion("classTime not in", values, "classTime");
            return (Criteria) this;
        }

        public Criteria andClassTimeBetween(String value1, String value2) {
            addCriterion("classTime between", value1, value2, "classTime");
            return (Criteria) this;
        }

        public Criteria andClassTimeNotBetween(String value1, String value2) {
            addCriterion("classTime not between", value1, value2, "classTime");
            return (Criteria) this;
        }

        public Criteria andClassNameIsNull() {
            addCriterion("className is null");
            return (Criteria) this;
        }

        public Criteria andClassNameIsNotNull() {
            addCriterion("className is not null");
            return (Criteria) this;
        }

        public Criteria andClassNameEqualTo(String value) {
            addCriterion("className =", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameNotEqualTo(String value) {
            addCriterion("className <>", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameGreaterThan(String value) {
            addCriterion("className >", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameGreaterThanOrEqualTo(String value) {
            addCriterion("className >=", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameLessThan(String value) {
            addCriterion("className <", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameLessThanOrEqualTo(String value) {
            addCriterion("className <=", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameLike(String value) {
            addCriterion("className like", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameNotLike(String value) {
            addCriterion("className not like", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameIn(List<String> values) {
            addCriterion("className in", values, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameNotIn(List<String> values) {
            addCriterion("className not in", values, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameBetween(String value1, String value2) {
            addCriterion("className between", value1, value2, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameNotBetween(String value1, String value2) {
            addCriterion("className not between", value1, value2, "className");
            return (Criteria) this;
        }

        public Criteria andClassPlaceIsNull() {
            addCriterion("classPlace is null");
            return (Criteria) this;
        }

        public Criteria andClassPlaceIsNotNull() {
            addCriterion("classPlace is not null");
            return (Criteria) this;
        }

        public Criteria andClassPlaceEqualTo(String value) {
            addCriterion("classPlace =", value, "classPlace");
            return (Criteria) this;
        }

        public Criteria andClassPlaceNotEqualTo(String value) {
            addCriterion("classPlace <>", value, "classPlace");
            return (Criteria) this;
        }

        public Criteria andClassPlaceGreaterThan(String value) {
            addCriterion("classPlace >", value, "classPlace");
            return (Criteria) this;
        }

        public Criteria andClassPlaceGreaterThanOrEqualTo(String value) {
            addCriterion("classPlace >=", value, "classPlace");
            return (Criteria) this;
        }

        public Criteria andClassPlaceLessThan(String value) {
            addCriterion("classPlace <", value, "classPlace");
            return (Criteria) this;
        }

        public Criteria andClassPlaceLessThanOrEqualTo(String value) {
            addCriterion("classPlace <=", value, "classPlace");
            return (Criteria) this;
        }

        public Criteria andClassPlaceLike(String value) {
            addCriterion("classPlace like", value, "classPlace");
            return (Criteria) this;
        }

        public Criteria andClassPlaceNotLike(String value) {
            addCriterion("classPlace not like", value, "classPlace");
            return (Criteria) this;
        }

        public Criteria andClassPlaceIn(List<String> values) {
            addCriterion("classPlace in", values, "classPlace");
            return (Criteria) this;
        }

        public Criteria andClassPlaceNotIn(List<String> values) {
            addCriterion("classPlace not in", values, "classPlace");
            return (Criteria) this;
        }

        public Criteria andClassPlaceBetween(String value1, String value2) {
            addCriterion("classPlace between", value1, value2, "classPlace");
            return (Criteria) this;
        }

        public Criteria andClassPlaceNotBetween(String value1, String value2) {
            addCriterion("classPlace not between", value1, value2, "classPlace");
            return (Criteria) this;
        }

        public Criteria andContactNameIsNull() {
            addCriterion("contactName is null");
            return (Criteria) this;
        }

        public Criteria andContactNameIsNotNull() {
            addCriterion("contactName is not null");
            return (Criteria) this;
        }

        public Criteria andContactNameEqualTo(String value) {
            addCriterion("contactName =", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameNotEqualTo(String value) {
            addCriterion("contactName <>", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameGreaterThan(String value) {
            addCriterion("contactName >", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameGreaterThanOrEqualTo(String value) {
            addCriterion("contactName >=", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameLessThan(String value) {
            addCriterion("contactName <", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameLessThanOrEqualTo(String value) {
            addCriterion("contactName <=", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameLike(String value) {
            addCriterion("contactName like", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameNotLike(String value) {
            addCriterion("contactName not like", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameIn(List<String> values) {
            addCriterion("contactName in", values, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameNotIn(List<String> values) {
            addCriterion("contactName not in", values, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameBetween(String value1, String value2) {
            addCriterion("contactName between", value1, value2, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameNotBetween(String value1, String value2) {
            addCriterion("contactName not between", value1, value2, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactMobileIsNull() {
            addCriterion("contactMobile is null");
            return (Criteria) this;
        }

        public Criteria andContactMobileIsNotNull() {
            addCriterion("contactMobile is not null");
            return (Criteria) this;
        }

        public Criteria andContactMobileEqualTo(String value) {
            addCriterion("contactMobile =", value, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileNotEqualTo(String value) {
            addCriterion("contactMobile <>", value, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileGreaterThan(String value) {
            addCriterion("contactMobile >", value, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileGreaterThanOrEqualTo(String value) {
            addCriterion("contactMobile >=", value, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileLessThan(String value) {
            addCriterion("contactMobile <", value, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileLessThanOrEqualTo(String value) {
            addCriterion("contactMobile <=", value, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileLike(String value) {
            addCriterion("contactMobile like", value, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileNotLike(String value) {
            addCriterion("contactMobile not like", value, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileIn(List<String> values) {
            addCriterion("contactMobile in", values, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileNotIn(List<String> values) {
            addCriterion("contactMobile not in", values, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileBetween(String value1, String value2) {
            addCriterion("contactMobile between", value1, value2, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andContactMobileNotBetween(String value1, String value2) {
            addCriterion("contactMobile not between", value1, value2, "contactMobile");
            return (Criteria) this;
        }

        public Criteria andCardTypeIsNull() {
            addCriterion("cardType is null");
            return (Criteria) this;
        }

        public Criteria andCardTypeIsNotNull() {
            addCriterion("cardType is not null");
            return (Criteria) this;
        }

        public Criteria andCardTypeEqualTo(Byte value) {
            addCriterion("cardType =", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotEqualTo(Byte value) {
            addCriterion("cardType <>", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeGreaterThan(Byte value) {
            addCriterion("cardType >", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("cardType >=", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLessThan(Byte value) {
            addCriterion("cardType <", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLessThanOrEqualTo(Byte value) {
            addCriterion("cardType <=", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeIn(List<Byte> values) {
            addCriterion("cardType in", values, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotIn(List<Byte> values) {
            addCriterion("cardType not in", values, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeBetween(Byte value1, Byte value2) {
            addCriterion("cardType between", value1, value2, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("cardType not between", value1, value2, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardDescIsNull() {
            addCriterion("cardDesc is null");
            return (Criteria) this;
        }

        public Criteria andCardDescIsNotNull() {
            addCriterion("cardDesc is not null");
            return (Criteria) this;
        }

        public Criteria andCardDescEqualTo(String value) {
            addCriterion("cardDesc =", value, "cardDesc");
            return (Criteria) this;
        }

        public Criteria andCardDescNotEqualTo(String value) {
            addCriterion("cardDesc <>", value, "cardDesc");
            return (Criteria) this;
        }

        public Criteria andCardDescGreaterThan(String value) {
            addCriterion("cardDesc >", value, "cardDesc");
            return (Criteria) this;
        }

        public Criteria andCardDescGreaterThanOrEqualTo(String value) {
            addCriterion("cardDesc >=", value, "cardDesc");
            return (Criteria) this;
        }

        public Criteria andCardDescLessThan(String value) {
            addCriterion("cardDesc <", value, "cardDesc");
            return (Criteria) this;
        }

        public Criteria andCardDescLessThanOrEqualTo(String value) {
            addCriterion("cardDesc <=", value, "cardDesc");
            return (Criteria) this;
        }

        public Criteria andCardDescLike(String value) {
            addCriterion("cardDesc like", value, "cardDesc");
            return (Criteria) this;
        }

        public Criteria andCardDescNotLike(String value) {
            addCriterion("cardDesc not like", value, "cardDesc");
            return (Criteria) this;
        }

        public Criteria andCardDescIn(List<String> values) {
            addCriterion("cardDesc in", values, "cardDesc");
            return (Criteria) this;
        }

        public Criteria andCardDescNotIn(List<String> values) {
            addCriterion("cardDesc not in", values, "cardDesc");
            return (Criteria) this;
        }

        public Criteria andCardDescBetween(String value1, String value2) {
            addCriterion("cardDesc between", value1, value2, "cardDesc");
            return (Criteria) this;
        }

        public Criteria andCardDescNotBetween(String value1, String value2) {
            addCriterion("cardDesc not between", value1, value2, "cardDesc");
            return (Criteria) this;
        }

        public Criteria andTotalIsNull() {
            addCriterion("total is null");
            return (Criteria) this;
        }

        public Criteria andTotalIsNotNull() {
            addCriterion("total is not null");
            return (Criteria) this;
        }

        public Criteria andTotalEqualTo(Integer value) {
            addCriterion("total =", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotEqualTo(Integer value) {
            addCriterion("total <>", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalGreaterThan(Integer value) {
            addCriterion("total >", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalGreaterThanOrEqualTo(Integer value) {
            addCriterion("total >=", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalLessThan(Integer value) {
            addCriterion("total <", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalLessThanOrEqualTo(Integer value) {
            addCriterion("total <=", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalIn(List<Integer> values) {
            addCriterion("total in", values, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotIn(List<Integer> values) {
            addCriterion("total not in", values, "total");
            return (Criteria) this;
        }

        public Criteria andTotalBetween(Integer value1, Integer value2) {
            addCriterion("total between", value1, value2, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotBetween(Integer value1, Integer value2) {
            addCriterion("total not between", value1, value2, "total");
            return (Criteria) this;
        }

        public Criteria andAbsenceIsNull() {
            addCriterion("absence is null");
            return (Criteria) this;
        }

        public Criteria andAbsenceIsNotNull() {
            addCriterion("absence is not null");
            return (Criteria) this;
        }

        public Criteria andAbsenceEqualTo(Integer value) {
            addCriterion("absence =", value, "absence");
            return (Criteria) this;
        }

        public Criteria andAbsenceNotEqualTo(Integer value) {
            addCriterion("absence <>", value, "absence");
            return (Criteria) this;
        }

        public Criteria andAbsenceGreaterThan(Integer value) {
            addCriterion("absence >", value, "absence");
            return (Criteria) this;
        }

        public Criteria andAbsenceGreaterThanOrEqualTo(Integer value) {
            addCriterion("absence >=", value, "absence");
            return (Criteria) this;
        }

        public Criteria andAbsenceLessThan(Integer value) {
            addCriterion("absence <", value, "absence");
            return (Criteria) this;
        }

        public Criteria andAbsenceLessThanOrEqualTo(Integer value) {
            addCriterion("absence <=", value, "absence");
            return (Criteria) this;
        }

        public Criteria andAbsenceIn(List<Integer> values) {
            addCriterion("absence in", values, "absence");
            return (Criteria) this;
        }

        public Criteria andAbsenceNotIn(List<Integer> values) {
            addCriterion("absence not in", values, "absence");
            return (Criteria) this;
        }

        public Criteria andAbsenceBetween(Integer value1, Integer value2) {
            addCriterion("absence between", value1, value2, "absence");
            return (Criteria) this;
        }

        public Criteria andAbsenceNotBetween(Integer value1, Integer value2) {
            addCriterion("absence not between", value1, value2, "absence");
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

        public Criteria andStateEqualTo(Integer value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Integer value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Integer value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Integer value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Integer value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Integer> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Integer> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Integer value1, Integer value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Integer value1, Integer value2) {
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