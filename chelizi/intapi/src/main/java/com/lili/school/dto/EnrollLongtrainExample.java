package com.lili.school.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EnrollLongtrainExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EnrollLongtrainExample() {
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

        public Criteria andLtIdIsNull() {
            addCriterion("ltId is null");
            return (Criteria) this;
        }

        public Criteria andLtIdIsNotNull() {
            addCriterion("ltId is not null");
            return (Criteria) this;
        }

        public Criteria andLtIdEqualTo(Integer value) {
            addCriterion("ltId =", value, "ltId");
            return (Criteria) this;
        }

        public Criteria andLtIdNotEqualTo(Integer value) {
            addCriterion("ltId <>", value, "ltId");
            return (Criteria) this;
        }

        public Criteria andLtIdGreaterThan(Integer value) {
            addCriterion("ltId >", value, "ltId");
            return (Criteria) this;
        }

        public Criteria andLtIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ltId >=", value, "ltId");
            return (Criteria) this;
        }

        public Criteria andLtIdLessThan(Integer value) {
            addCriterion("ltId <", value, "ltId");
            return (Criteria) this;
        }

        public Criteria andLtIdLessThanOrEqualTo(Integer value) {
            addCriterion("ltId <=", value, "ltId");
            return (Criteria) this;
        }

        public Criteria andLtIdIn(List<Integer> values) {
            addCriterion("ltId in", values, "ltId");
            return (Criteria) this;
        }

        public Criteria andLtIdNotIn(List<Integer> values) {
            addCriterion("ltId not in", values, "ltId");
            return (Criteria) this;
        }

        public Criteria andLtIdBetween(Integer value1, Integer value2) {
            addCriterion("ltId between", value1, value2, "ltId");
            return (Criteria) this;
        }

        public Criteria andLtIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ltId not between", value1, value2, "ltId");
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

        public Criteria andCoachIdIsNull() {
            addCriterion("coachId is null");
            return (Criteria) this;
        }

        public Criteria andCoachIdIsNotNull() {
            addCriterion("coachId is not null");
            return (Criteria) this;
        }

        public Criteria andCoachIdEqualTo(Integer value) {
            addCriterion("coachId =", value, "coachId");
            return (Criteria) this;
        }

        public Criteria andCoachIdNotEqualTo(Integer value) {
            addCriterion("coachId <>", value, "coachId");
            return (Criteria) this;
        }

        public Criteria andCoachIdGreaterThan(Integer value) {
            addCriterion("coachId >", value, "coachId");
            return (Criteria) this;
        }

        public Criteria andCoachIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("coachId >=", value, "coachId");
            return (Criteria) this;
        }

        public Criteria andCoachIdLessThan(Integer value) {
            addCriterion("coachId <", value, "coachId");
            return (Criteria) this;
        }

        public Criteria andCoachIdLessThanOrEqualTo(Integer value) {
            addCriterion("coachId <=", value, "coachId");
            return (Criteria) this;
        }

        public Criteria andCoachIdIn(List<Integer> values) {
            addCriterion("coachId in", values, "coachId");
            return (Criteria) this;
        }

        public Criteria andCoachIdNotIn(List<Integer> values) {
            addCriterion("coachId not in", values, "coachId");
            return (Criteria) this;
        }

        public Criteria andCoachIdBetween(Integer value1, Integer value2) {
            addCriterion("coachId between", value1, value2, "coachId");
            return (Criteria) this;
        }

        public Criteria andCoachIdNotBetween(Integer value1, Integer value2) {
            addCriterion("coachId not between", value1, value2, "coachId");
            return (Criteria) this;
        }

        public Criteria andCoachSexIsNull() {
            addCriterion("coachSex is null");
            return (Criteria) this;
        }

        public Criteria andCoachSexIsNotNull() {
            addCriterion("coachSex is not null");
            return (Criteria) this;
        }

        public Criteria andCoachSexEqualTo(Byte value) {
            addCriterion("coachSex =", value, "coachSex");
            return (Criteria) this;
        }

        public Criteria andCoachSexNotEqualTo(Byte value) {
            addCriterion("coachSex <>", value, "coachSex");
            return (Criteria) this;
        }

        public Criteria andCoachSexGreaterThan(Byte value) {
            addCriterion("coachSex >", value, "coachSex");
            return (Criteria) this;
        }

        public Criteria andCoachSexGreaterThanOrEqualTo(Byte value) {
            addCriterion("coachSex >=", value, "coachSex");
            return (Criteria) this;
        }

        public Criteria andCoachSexLessThan(Byte value) {
            addCriterion("coachSex <", value, "coachSex");
            return (Criteria) this;
        }

        public Criteria andCoachSexLessThanOrEqualTo(Byte value) {
            addCriterion("coachSex <=", value, "coachSex");
            return (Criteria) this;
        }

        public Criteria andCoachSexIn(List<Byte> values) {
            addCriterion("coachSex in", values, "coachSex");
            return (Criteria) this;
        }

        public Criteria andCoachSexNotIn(List<Byte> values) {
            addCriterion("coachSex not in", values, "coachSex");
            return (Criteria) this;
        }

        public Criteria andCoachSexBetween(Byte value1, Byte value2) {
            addCriterion("coachSex between", value1, value2, "coachSex");
            return (Criteria) this;
        }

        public Criteria andCoachSexNotBetween(Byte value1, Byte value2) {
            addCriterion("coachSex not between", value1, value2, "coachSex");
            return (Criteria) this;
        }

        public Criteria andCoachIdCardIsNull() {
            addCriterion("coachIdCard is null");
            return (Criteria) this;
        }

        public Criteria andCoachIdCardIsNotNull() {
            addCriterion("coachIdCard is not null");
            return (Criteria) this;
        }

        public Criteria andCoachIdCardEqualTo(String value) {
            addCriterion("coachIdCard =", value, "coachIdCard");
            return (Criteria) this;
        }

        public Criteria andCoachIdCardNotEqualTo(String value) {
            addCriterion("coachIdCard <>", value, "coachIdCard");
            return (Criteria) this;
        }

        public Criteria andCoachIdCardGreaterThan(String value) {
            addCriterion("coachIdCard >", value, "coachIdCard");
            return (Criteria) this;
        }

        public Criteria andCoachIdCardGreaterThanOrEqualTo(String value) {
            addCriterion("coachIdCard >=", value, "coachIdCard");
            return (Criteria) this;
        }

        public Criteria andCoachIdCardLessThan(String value) {
            addCriterion("coachIdCard <", value, "coachIdCard");
            return (Criteria) this;
        }

        public Criteria andCoachIdCardLessThanOrEqualTo(String value) {
            addCriterion("coachIdCard <=", value, "coachIdCard");
            return (Criteria) this;
        }

        public Criteria andCoachIdCardLike(String value) {
            addCriterion("coachIdCard like", value, "coachIdCard");
            return (Criteria) this;
        }

        public Criteria andCoachIdCardNotLike(String value) {
            addCriterion("coachIdCard not like", value, "coachIdCard");
            return (Criteria) this;
        }

        public Criteria andCoachIdCardIn(List<String> values) {
            addCriterion("coachIdCard in", values, "coachIdCard");
            return (Criteria) this;
        }

        public Criteria andCoachIdCardNotIn(List<String> values) {
            addCriterion("coachIdCard not in", values, "coachIdCard");
            return (Criteria) this;
        }

        public Criteria andCoachIdCardBetween(String value1, String value2) {
            addCriterion("coachIdCard between", value1, value2, "coachIdCard");
            return (Criteria) this;
        }

        public Criteria andCoachIdCardNotBetween(String value1, String value2) {
            addCriterion("coachIdCard not between", value1, value2, "coachIdCard");
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

        public Criteria andCarTypeIsNull() {
            addCriterion("carType is null");
            return (Criteria) this;
        }

        public Criteria andCarTypeIsNotNull() {
            addCriterion("carType is not null");
            return (Criteria) this;
        }

        public Criteria andCarTypeEqualTo(String value) {
            addCriterion("carType =", value, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeNotEqualTo(String value) {
            addCriterion("carType <>", value, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeGreaterThan(String value) {
            addCriterion("carType >", value, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeGreaterThanOrEqualTo(String value) {
            addCriterion("carType >=", value, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeLessThan(String value) {
            addCriterion("carType <", value, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeLessThanOrEqualTo(String value) {
            addCriterion("carType <=", value, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeLike(String value) {
            addCriterion("carType like", value, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeNotLike(String value) {
            addCriterion("carType not like", value, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeIn(List<String> values) {
            addCriterion("carType in", values, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeNotIn(List<String> values) {
            addCriterion("carType not in", values, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeBetween(String value1, String value2) {
            addCriterion("carType between", value1, value2, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeNotBetween(String value1, String value2) {
            addCriterion("carType not between", value1, value2, "carType");
            return (Criteria) this;
        }

        public Criteria andDrTypeIsNull() {
            addCriterion("drType is null");
            return (Criteria) this;
        }

        public Criteria andDrTypeIsNotNull() {
            addCriterion("drType is not null");
            return (Criteria) this;
        }

        public Criteria andDrTypeEqualTo(Byte value) {
            addCriterion("drType =", value, "drType");
            return (Criteria) this;
        }

        public Criteria andDrTypeNotEqualTo(Byte value) {
            addCriterion("drType <>", value, "drType");
            return (Criteria) this;
        }

        public Criteria andDrTypeGreaterThan(Byte value) {
            addCriterion("drType >", value, "drType");
            return (Criteria) this;
        }

        public Criteria andDrTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("drType >=", value, "drType");
            return (Criteria) this;
        }

        public Criteria andDrTypeLessThan(Byte value) {
            addCriterion("drType <", value, "drType");
            return (Criteria) this;
        }

        public Criteria andDrTypeLessThanOrEqualTo(Byte value) {
            addCriterion("drType <=", value, "drType");
            return (Criteria) this;
        }

        public Criteria andDrTypeIn(List<Byte> values) {
            addCriterion("drType in", values, "drType");
            return (Criteria) this;
        }

        public Criteria andDrTypeNotIn(List<Byte> values) {
            addCriterion("drType not in", values, "drType");
            return (Criteria) this;
        }

        public Criteria andDrTypeBetween(Byte value1, Byte value2) {
            addCriterion("drType between", value1, value2, "drType");
            return (Criteria) this;
        }

        public Criteria andDrTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("drType not between", value1, value2, "drType");
            return (Criteria) this;
        }

        public Criteria andCarrysIsNull() {
            addCriterion("carrys is null");
            return (Criteria) this;
        }

        public Criteria andCarrysIsNotNull() {
            addCriterion("carrys is not null");
            return (Criteria) this;
        }

        public Criteria andCarrysEqualTo(String value) {
            addCriterion("carrys =", value, "carrys");
            return (Criteria) this;
        }

        public Criteria andCarrysNotEqualTo(String value) {
            addCriterion("carrys <>", value, "carrys");
            return (Criteria) this;
        }

        public Criteria andCarrysGreaterThan(String value) {
            addCriterion("carrys >", value, "carrys");
            return (Criteria) this;
        }

        public Criteria andCarrysGreaterThanOrEqualTo(String value) {
            addCriterion("carrys >=", value, "carrys");
            return (Criteria) this;
        }

        public Criteria andCarrysLessThan(String value) {
            addCriterion("carrys <", value, "carrys");
            return (Criteria) this;
        }

        public Criteria andCarrysLessThanOrEqualTo(String value) {
            addCriterion("carrys <=", value, "carrys");
            return (Criteria) this;
        }

        public Criteria andCarrysLike(String value) {
            addCriterion("carrys like", value, "carrys");
            return (Criteria) this;
        }

        public Criteria andCarrysNotLike(String value) {
            addCriterion("carrys not like", value, "carrys");
            return (Criteria) this;
        }

        public Criteria andCarrysIn(List<String> values) {
            addCriterion("carrys in", values, "carrys");
            return (Criteria) this;
        }

        public Criteria andCarrysNotIn(List<String> values) {
            addCriterion("carrys not in", values, "carrys");
            return (Criteria) this;
        }

        public Criteria andCarrysBetween(String value1, String value2) {
            addCriterion("carrys between", value1, value2, "carrys");
            return (Criteria) this;
        }

        public Criteria andCarrysNotBetween(String value1, String value2) {
            addCriterion("carrys not between", value1, value2, "carrys");
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

        public Criteria andFailedIsNull() {
            addCriterion("failed is null");
            return (Criteria) this;
        }

        public Criteria andFailedIsNotNull() {
            addCriterion("failed is not null");
            return (Criteria) this;
        }

        public Criteria andFailedEqualTo(Integer value) {
            addCriterion("failed =", value, "failed");
            return (Criteria) this;
        }

        public Criteria andFailedNotEqualTo(Integer value) {
            addCriterion("failed <>", value, "failed");
            return (Criteria) this;
        }

        public Criteria andFailedGreaterThan(Integer value) {
            addCriterion("failed >", value, "failed");
            return (Criteria) this;
        }

        public Criteria andFailedGreaterThanOrEqualTo(Integer value) {
            addCriterion("failed >=", value, "failed");
            return (Criteria) this;
        }

        public Criteria andFailedLessThan(Integer value) {
            addCriterion("failed <", value, "failed");
            return (Criteria) this;
        }

        public Criteria andFailedLessThanOrEqualTo(Integer value) {
            addCriterion("failed <=", value, "failed");
            return (Criteria) this;
        }

        public Criteria andFailedIn(List<Integer> values) {
            addCriterion("failed in", values, "failed");
            return (Criteria) this;
        }

        public Criteria andFailedNotIn(List<Integer> values) {
            addCriterion("failed not in", values, "failed");
            return (Criteria) this;
        }

        public Criteria andFailedBetween(Integer value1, Integer value2) {
            addCriterion("failed between", value1, value2, "failed");
            return (Criteria) this;
        }

        public Criteria andFailedNotBetween(Integer value1, Integer value2) {
            addCriterion("failed not between", value1, value2, "failed");
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