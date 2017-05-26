package com.lili.coach.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CarExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CarExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andCarIdIsNull() {
            addCriterion("carId is null");
            return (Criteria) this;
        }

        public Criteria andCarIdIsNotNull() {
            addCriterion("carId is not null");
            return (Criteria) this;
        }

        public Criteria andCarIdEqualTo(Integer value) {
            addCriterion("carId =", value, "carId");
            return (Criteria) this;
        }

        public Criteria andCarIdNotEqualTo(Integer value) {
            addCriterion("carId <>", value, "carId");
            return (Criteria) this;
        }

        public Criteria andCarIdGreaterThan(Integer value) {
            addCriterion("carId >", value, "carId");
            return (Criteria) this;
        }

        public Criteria andCarIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("carId >=", value, "carId");
            return (Criteria) this;
        }

        public Criteria andCarIdLessThan(Integer value) {
            addCriterion("carId <", value, "carId");
            return (Criteria) this;
        }

        public Criteria andCarIdLessThanOrEqualTo(Integer value) {
            addCriterion("carId <=", value, "carId");
            return (Criteria) this;
        }

        public Criteria andCarIdIn(List<Integer> values) {
            addCriterion("carId in", values, "carId");
            return (Criteria) this;
        }

        public Criteria andCarIdNotIn(List<Integer> values) {
            addCriterion("carId not in", values, "carId");
            return (Criteria) this;
        }

        public Criteria andCarIdBetween(Integer value1, Integer value2) {
            addCriterion("carId between", value1, value2, "carId");
            return (Criteria) this;
        }

        public Criteria andCarIdNotBetween(Integer value1, Integer value2) {
            addCriterion("carId not between", value1, value2, "carId");
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

        public Criteria andAgeIsNull() {
            addCriterion("age is null");
            return (Criteria) this;
        }

        public Criteria andAgeIsNotNull() {
            addCriterion("age is not null");
            return (Criteria) this;
        }

        public Criteria andAgeEqualTo(Integer value) {
            addCriterion("age =", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeNotEqualTo(Integer value) {
            addCriterion("age <>", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeGreaterThan(Integer value) {
            addCriterion("age >", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeGreaterThanOrEqualTo(Integer value) {
            addCriterion("age >=", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeLessThan(Integer value) {
            addCriterion("age <", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeLessThanOrEqualTo(Integer value) {
            addCriterion("age <=", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeIn(List<Integer> values) {
            addCriterion("age in", values, "age");
            return (Criteria) this;
        }

        public Criteria andAgeNotIn(List<Integer> values) {
            addCriterion("age not in", values, "age");
            return (Criteria) this;
        }

        public Criteria andAgeBetween(Integer value1, Integer value2) {
            addCriterion("age between", value1, value2, "age");
            return (Criteria) this;
        }

        public Criteria andAgeNotBetween(Integer value1, Integer value2) {
            addCriterion("age not between", value1, value2, "age");
            return (Criteria) this;
        }

        public Criteria andMileageIsNull() {
            addCriterion("mileage is null");
            return (Criteria) this;
        }

        public Criteria andMileageIsNotNull() {
            addCriterion("mileage is not null");
            return (Criteria) this;
        }

        public Criteria andMileageEqualTo(Integer value) {
            addCriterion("mileage =", value, "mileage");
            return (Criteria) this;
        }

        public Criteria andMileageNotEqualTo(Integer value) {
            addCriterion("mileage <>", value, "mileage");
            return (Criteria) this;
        }

        public Criteria andMileageGreaterThan(Integer value) {
            addCriterion("mileage >", value, "mileage");
            return (Criteria) this;
        }

        public Criteria andMileageGreaterThanOrEqualTo(Integer value) {
            addCriterion("mileage >=", value, "mileage");
            return (Criteria) this;
        }

        public Criteria andMileageLessThan(Integer value) {
            addCriterion("mileage <", value, "mileage");
            return (Criteria) this;
        }

        public Criteria andMileageLessThanOrEqualTo(Integer value) {
            addCriterion("mileage <=", value, "mileage");
            return (Criteria) this;
        }

        public Criteria andMileageIn(List<Integer> values) {
            addCriterion("mileage in", values, "mileage");
            return (Criteria) this;
        }

        public Criteria andMileageNotIn(List<Integer> values) {
            addCriterion("mileage not in", values, "mileage");
            return (Criteria) this;
        }

        public Criteria andMileageBetween(Integer value1, Integer value2) {
            addCriterion("mileage between", value1, value2, "mileage");
            return (Criteria) this;
        }

        public Criteria andMileageNotBetween(Integer value1, Integer value2) {
            addCriterion("mileage not between", value1, value2, "mileage");
            return (Criteria) this;
        }

        public Criteria andInnerEnvirIsNull() {
            addCriterion("innerEnvir is null");
            return (Criteria) this;
        }

        public Criteria andInnerEnvirIsNotNull() {
            addCriterion("innerEnvir is not null");
            return (Criteria) this;
        }

        public Criteria andInnerEnvirEqualTo(Integer value) {
            addCriterion("innerEnvir =", value, "innerEnvir");
            return (Criteria) this;
        }

        public Criteria andInnerEnvirNotEqualTo(Integer value) {
            addCriterion("innerEnvir <>", value, "innerEnvir");
            return (Criteria) this;
        }

        public Criteria andInnerEnvirGreaterThan(Integer value) {
            addCriterion("innerEnvir >", value, "innerEnvir");
            return (Criteria) this;
        }

        public Criteria andInnerEnvirGreaterThanOrEqualTo(Integer value) {
            addCriterion("innerEnvir >=", value, "innerEnvir");
            return (Criteria) this;
        }

        public Criteria andInnerEnvirLessThan(Integer value) {
            addCriterion("innerEnvir <", value, "innerEnvir");
            return (Criteria) this;
        }

        public Criteria andInnerEnvirLessThanOrEqualTo(Integer value) {
            addCriterion("innerEnvir <=", value, "innerEnvir");
            return (Criteria) this;
        }

        public Criteria andInnerEnvirIn(List<Integer> values) {
            addCriterion("innerEnvir in", values, "innerEnvir");
            return (Criteria) this;
        }

        public Criteria andInnerEnvirNotIn(List<Integer> values) {
            addCriterion("innerEnvir not in", values, "innerEnvir");
            return (Criteria) this;
        }

        public Criteria andInnerEnvirBetween(Integer value1, Integer value2) {
            addCriterion("innerEnvir between", value1, value2, "innerEnvir");
            return (Criteria) this;
        }

        public Criteria andInnerEnvirNotBetween(Integer value1, Integer value2) {
            addCriterion("innerEnvir not between", value1, value2, "innerEnvir");
            return (Criteria) this;
        }

        public Criteria andPerformanceIsNull() {
            addCriterion("performance is null");
            return (Criteria) this;
        }

        public Criteria andPerformanceIsNotNull() {
            addCriterion("performance is not null");
            return (Criteria) this;
        }

        public Criteria andPerformanceEqualTo(Integer value) {
            addCriterion("performance =", value, "performance");
            return (Criteria) this;
        }

        public Criteria andPerformanceNotEqualTo(Integer value) {
            addCriterion("performance <>", value, "performance");
            return (Criteria) this;
        }

        public Criteria andPerformanceGreaterThan(Integer value) {
            addCriterion("performance >", value, "performance");
            return (Criteria) this;
        }

        public Criteria andPerformanceGreaterThanOrEqualTo(Integer value) {
            addCriterion("performance >=", value, "performance");
            return (Criteria) this;
        }

        public Criteria andPerformanceLessThan(Integer value) {
            addCriterion("performance <", value, "performance");
            return (Criteria) this;
        }

        public Criteria andPerformanceLessThanOrEqualTo(Integer value) {
            addCriterion("performance <=", value, "performance");
            return (Criteria) this;
        }

        public Criteria andPerformanceIn(List<Integer> values) {
            addCriterion("performance in", values, "performance");
            return (Criteria) this;
        }

        public Criteria andPerformanceNotIn(List<Integer> values) {
            addCriterion("performance not in", values, "performance");
            return (Criteria) this;
        }

        public Criteria andPerformanceBetween(Integer value1, Integer value2) {
            addCriterion("performance between", value1, value2, "performance");
            return (Criteria) this;
        }

        public Criteria andPerformanceNotBetween(Integer value1, Integer value2) {
            addCriterion("performance not between", value1, value2, "performance");
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

        public Criteria andCarLevelIsNull() {
            addCriterion("carLevel is null");
            return (Criteria) this;
        }

        public Criteria andCarLevelIsNotNull() {
            addCriterion("carLevel is not null");
            return (Criteria) this;
        }

        public Criteria andCarLevelEqualTo(Integer value) {
            addCriterion("carLevel =", value, "carLevel");
            return (Criteria) this;
        }

        public Criteria andCarLevelNotEqualTo(Integer value) {
            addCriterion("carLevel <>", value, "carLevel");
            return (Criteria) this;
        }

        public Criteria andCarLevelGreaterThan(Integer value) {
            addCriterion("carLevel >", value, "carLevel");
            return (Criteria) this;
        }

        public Criteria andCarLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("carLevel >=", value, "carLevel");
            return (Criteria) this;
        }

        public Criteria andCarLevelLessThan(Integer value) {
            addCriterion("carLevel <", value, "carLevel");
            return (Criteria) this;
        }

        public Criteria andCarLevelLessThanOrEqualTo(Integer value) {
            addCriterion("carLevel <=", value, "carLevel");
            return (Criteria) this;
        }

        public Criteria andCarLevelIn(List<Integer> values) {
            addCriterion("carLevel in", values, "carLevel");
            return (Criteria) this;
        }

        public Criteria andCarLevelNotIn(List<Integer> values) {
            addCriterion("carLevel not in", values, "carLevel");
            return (Criteria) this;
        }

        public Criteria andCarLevelBetween(Integer value1, Integer value2) {
            addCriterion("carLevel between", value1, value2, "carLevel");
            return (Criteria) this;
        }

        public Criteria andCarLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("carLevel not between", value1, value2, "carLevel");
            return (Criteria) this;
        }

        public Criteria andDriveTypeIsNull() {
            addCriterion("driveType is null");
            return (Criteria) this;
        }

        public Criteria andDriveTypeIsNotNull() {
            addCriterion("driveType is not null");
            return (Criteria) this;
        }

        public Criteria andDriveTypeEqualTo(Byte value) {
            addCriterion("driveType =", value, "driveType");
            return (Criteria) this;
        }

        public Criteria andDriveTypeNotEqualTo(Byte value) {
            addCriterion("driveType <>", value, "driveType");
            return (Criteria) this;
        }

        public Criteria andDriveTypeGreaterThan(Byte value) {
            addCriterion("driveType >", value, "driveType");
            return (Criteria) this;
        }

        public Criteria andDriveTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("driveType >=", value, "driveType");
            return (Criteria) this;
        }

        public Criteria andDriveTypeLessThan(Byte value) {
            addCriterion("driveType <", value, "driveType");
            return (Criteria) this;
        }

        public Criteria andDriveTypeLessThanOrEqualTo(Byte value) {
            addCriterion("driveType <=", value, "driveType");
            return (Criteria) this;
        }

        public Criteria andDriveTypeIn(List<Byte> values) {
            addCriterion("driveType in", values, "driveType");
            return (Criteria) this;
        }

        public Criteria andDriveTypeNotIn(List<Byte> values) {
            addCriterion("driveType not in", values, "driveType");
            return (Criteria) this;
        }

        public Criteria andDriveTypeBetween(Byte value1, Byte value2) {
            addCriterion("driveType between", value1, value2, "driveType");
            return (Criteria) this;
        }

        public Criteria andDriveTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("driveType not between", value1, value2, "driveType");
            return (Criteria) this;
        }

        public Criteria andDriveNumberIsNull() {
            addCriterion("driveNumber is null");
            return (Criteria) this;
        }

        public Criteria andDriveNumberIsNotNull() {
            addCriterion("driveNumber is not null");
            return (Criteria) this;
        }

        public Criteria andDriveNumberEqualTo(String value) {
            addCriterion("driveNumber =", value, "driveNumber");
            return (Criteria) this;
        }

        public Criteria andDriveNumberNotEqualTo(String value) {
            addCriterion("driveNumber <>", value, "driveNumber");
            return (Criteria) this;
        }

        public Criteria andDriveNumberGreaterThan(String value) {
            addCriterion("driveNumber >", value, "driveNumber");
            return (Criteria) this;
        }

        public Criteria andDriveNumberGreaterThanOrEqualTo(String value) {
            addCriterion("driveNumber >=", value, "driveNumber");
            return (Criteria) this;
        }

        public Criteria andDriveNumberLessThan(String value) {
            addCriterion("driveNumber <", value, "driveNumber");
            return (Criteria) this;
        }

        public Criteria andDriveNumberLessThanOrEqualTo(String value) {
            addCriterion("driveNumber <=", value, "driveNumber");
            return (Criteria) this;
        }

        public Criteria andDriveNumberLike(String value) {
            addCriterion("driveNumber like", value, "driveNumber");
            return (Criteria) this;
        }

        public Criteria andDriveNumberNotLike(String value) {
            addCriterion("driveNumber not like", value, "driveNumber");
            return (Criteria) this;
        }

        public Criteria andDriveNumberIn(List<String> values) {
            addCriterion("driveNumber in", values, "driveNumber");
            return (Criteria) this;
        }

        public Criteria andDriveNumberNotIn(List<String> values) {
            addCriterion("driveNumber not in", values, "driveNumber");
            return (Criteria) this;
        }

        public Criteria andDriveNumberBetween(String value1, String value2) {
            addCriterion("driveNumber between", value1, value2, "driveNumber");
            return (Criteria) this;
        }

        public Criteria andDriveNumberNotBetween(String value1, String value2) {
            addCriterion("driveNumber not between", value1, value2, "driveNumber");
            return (Criteria) this;
        }

        public Criteria andDrivePhotoIsNull() {
            addCriterion("drivePhoto is null");
            return (Criteria) this;
        }

        public Criteria andDrivePhotoIsNotNull() {
            addCriterion("drivePhoto is not null");
            return (Criteria) this;
        }

        public Criteria andDrivePhotoEqualTo(String value) {
            addCriterion("drivePhoto =", value, "drivePhoto");
            return (Criteria) this;
        }

        public Criteria andDrivePhotoNotEqualTo(String value) {
            addCriterion("drivePhoto <>", value, "drivePhoto");
            return (Criteria) this;
        }

        public Criteria andDrivePhotoGreaterThan(String value) {
            addCriterion("drivePhoto >", value, "drivePhoto");
            return (Criteria) this;
        }

        public Criteria andDrivePhotoGreaterThanOrEqualTo(String value) {
            addCriterion("drivePhoto >=", value, "drivePhoto");
            return (Criteria) this;
        }

        public Criteria andDrivePhotoLessThan(String value) {
            addCriterion("drivePhoto <", value, "drivePhoto");
            return (Criteria) this;
        }

        public Criteria andDrivePhotoLessThanOrEqualTo(String value) {
            addCriterion("drivePhoto <=", value, "drivePhoto");
            return (Criteria) this;
        }

        public Criteria andDrivePhotoLike(String value) {
            addCriterion("drivePhoto like", value, "drivePhoto");
            return (Criteria) this;
        }

        public Criteria andDrivePhotoNotLike(String value) {
            addCriterion("drivePhoto not like", value, "drivePhoto");
            return (Criteria) this;
        }

        public Criteria andDrivePhotoIn(List<String> values) {
            addCriterion("drivePhoto in", values, "drivePhoto");
            return (Criteria) this;
        }

        public Criteria andDrivePhotoNotIn(List<String> values) {
            addCriterion("drivePhoto not in", values, "drivePhoto");
            return (Criteria) this;
        }

        public Criteria andDrivePhotoBetween(String value1, String value2) {
            addCriterion("drivePhoto between", value1, value2, "drivePhoto");
            return (Criteria) this;
        }

        public Criteria andDrivePhotoNotBetween(String value1, String value2) {
            addCriterion("drivePhoto not between", value1, value2, "drivePhoto");
            return (Criteria) this;
        }

        public Criteria andUsePersonIsNull() {
            addCriterion("UsePerson is null");
            return (Criteria) this;
        }

        public Criteria andUsePersonIsNotNull() {
            addCriterion("UsePerson is not null");
            return (Criteria) this;
        }

        public Criteria andUsePersonEqualTo(String value) {
            addCriterion("UsePerson =", value, "usePerson");
            return (Criteria) this;
        }

        public Criteria andUsePersonNotEqualTo(String value) {
            addCriterion("UsePerson <>", value, "usePerson");
            return (Criteria) this;
        }

        public Criteria andUsePersonGreaterThan(String value) {
            addCriterion("UsePerson >", value, "usePerson");
            return (Criteria) this;
        }

        public Criteria andUsePersonGreaterThanOrEqualTo(String value) {
            addCriterion("UsePerson >=", value, "usePerson");
            return (Criteria) this;
        }

        public Criteria andUsePersonLessThan(String value) {
            addCriterion("UsePerson <", value, "usePerson");
            return (Criteria) this;
        }

        public Criteria andUsePersonLessThanOrEqualTo(String value) {
            addCriterion("UsePerson <=", value, "usePerson");
            return (Criteria) this;
        }

        public Criteria andUsePersonLike(String value) {
            addCriterion("UsePerson like", value, "usePerson");
            return (Criteria) this;
        }

        public Criteria andUsePersonNotLike(String value) {
            addCriterion("UsePerson not like", value, "usePerson");
            return (Criteria) this;
        }

        public Criteria andUsePersonIn(List<String> values) {
            addCriterion("UsePerson in", values, "usePerson");
            return (Criteria) this;
        }

        public Criteria andUsePersonNotIn(List<String> values) {
            addCriterion("UsePerson not in", values, "usePerson");
            return (Criteria) this;
        }

        public Criteria andUsePersonBetween(String value1, String value2) {
            addCriterion("UsePerson between", value1, value2, "usePerson");
            return (Criteria) this;
        }

        public Criteria andUsePersonNotBetween(String value1, String value2) {
            addCriterion("UsePerson not between", value1, value2, "usePerson");
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

        public Criteria andSyncTypeIsNull() {
            addCriterion("syncType is null");
            return (Criteria) this;
        }

        public Criteria andSyncTypeIsNotNull() {
            addCriterion("syncType is not null");
            return (Criteria) this;
        }

        public Criteria andSyncTypeEqualTo(Byte value) {
            addCriterion("syncType =", value, "syncType");
            return (Criteria) this;
        }

        public Criteria andSyncTypeNotEqualTo(Byte value) {
            addCriterion("syncType <>", value, "syncType");
            return (Criteria) this;
        }

        public Criteria andSyncTypeGreaterThan(Byte value) {
            addCriterion("syncType >", value, "syncType");
            return (Criteria) this;
        }

        public Criteria andSyncTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("syncType >=", value, "syncType");
            return (Criteria) this;
        }

        public Criteria andSyncTypeLessThan(Byte value) {
            addCriterion("syncType <", value, "syncType");
            return (Criteria) this;
        }

        public Criteria andSyncTypeLessThanOrEqualTo(Byte value) {
            addCriterion("syncType <=", value, "syncType");
            return (Criteria) this;
        }

        public Criteria andSyncTypeIn(List<Byte> values) {
            addCriterion("syncType in", values, "syncType");
            return (Criteria) this;
        }

        public Criteria andSyncTypeNotIn(List<Byte> values) {
            addCriterion("syncType not in", values, "syncType");
            return (Criteria) this;
        }

        public Criteria andSyncTypeBetween(Byte value1, Byte value2) {
            addCriterion("syncType between", value1, value2, "syncType");
            return (Criteria) this;
        }

        public Criteria andSyncTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("syncType not between", value1, value2, "syncType");
            return (Criteria) this;
        }

        public Criteria andSyncTimeIsNull() {
            addCriterion("syncTime is null");
            return (Criteria) this;
        }

        public Criteria andSyncTimeIsNotNull() {
            addCriterion("syncTime is not null");
            return (Criteria) this;
        }

        public Criteria andSyncTimeEqualTo(Date value) {
            addCriterion("syncTime =", value, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeNotEqualTo(Date value) {
            addCriterion("syncTime <>", value, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeGreaterThan(Date value) {
            addCriterion("syncTime >", value, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("syncTime >=", value, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeLessThan(Date value) {
            addCriterion("syncTime <", value, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeLessThanOrEqualTo(Date value) {
            addCriterion("syncTime <=", value, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeIn(List<Date> values) {
            addCriterion("syncTime in", values, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeNotIn(List<Date> values) {
            addCriterion("syncTime not in", values, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeBetween(Date value1, Date value2) {
            addCriterion("syncTime between", value1, value2, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeNotBetween(Date value1, Date value2) {
            addCriterion("syncTime not between", value1, value2, "syncTime");
            return (Criteria) this;
        }

        public Criteria andCityIdIsNull() {
            addCriterion("cityId is null");
            return (Criteria) this;
        }

        public Criteria andCityIdIsNotNull() {
            addCriterion("cityId is not null");
            return (Criteria) this;
        }

        public Criteria andCityIdEqualTo(Integer value) {
            addCriterion("cityId =", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdNotEqualTo(Integer value) {
            addCriterion("cityId <>", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdGreaterThan(Integer value) {
            addCriterion("cityId >", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("cityId >=", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdLessThan(Integer value) {
            addCriterion("cityId <", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdLessThanOrEqualTo(Integer value) {
            addCriterion("cityId <=", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdIn(List<Integer> values) {
            addCriterion("cityId in", values, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdNotIn(List<Integer> values) {
            addCriterion("cityId not in", values, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdBetween(Integer value1, Integer value2) {
            addCriterion("cityId between", value1, value2, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdNotBetween(Integer value1, Integer value2) {
            addCriterion("cityId not between", value1, value2, "cityId");
            return (Criteria) this;
        }

        public Criteria andCarOwnerIsNull() {
            addCriterion("carOwner is null");
            return (Criteria) this;
        }

        public Criteria andCarOwnerIsNotNull() {
            addCriterion("carOwner is not null");
            return (Criteria) this;
        }

        public Criteria andCarOwnerEqualTo(String value) {
            addCriterion("carOwner =", value, "carOwner");
            return (Criteria) this;
        }

        public Criteria andCarOwnerNotEqualTo(String value) {
            addCriterion("carOwner <>", value, "carOwner");
            return (Criteria) this;
        }

        public Criteria andCarOwnerGreaterThan(String value) {
            addCriterion("carOwner >", value, "carOwner");
            return (Criteria) this;
        }

        public Criteria andCarOwnerGreaterThanOrEqualTo(String value) {
            addCriterion("carOwner >=", value, "carOwner");
            return (Criteria) this;
        }

        public Criteria andCarOwnerLessThan(String value) {
            addCriterion("carOwner <", value, "carOwner");
            return (Criteria) this;
        }

        public Criteria andCarOwnerLessThanOrEqualTo(String value) {
            addCriterion("carOwner <=", value, "carOwner");
            return (Criteria) this;
        }

        public Criteria andCarOwnerLike(String value) {
            addCriterion("carOwner like", value, "carOwner");
            return (Criteria) this;
        }

        public Criteria andCarOwnerNotLike(String value) {
            addCriterion("carOwner not like", value, "carOwner");
            return (Criteria) this;
        }

        public Criteria andCarOwnerIn(List<String> values) {
            addCriterion("carOwner in", values, "carOwner");
            return (Criteria) this;
        }

        public Criteria andCarOwnerNotIn(List<String> values) {
            addCriterion("carOwner not in", values, "carOwner");
            return (Criteria) this;
        }

        public Criteria andCarOwnerBetween(String value1, String value2) {
            addCriterion("carOwner between", value1, value2, "carOwner");
            return (Criteria) this;
        }

        public Criteria andCarOwnerNotBetween(String value1, String value2) {
            addCriterion("carOwner not between", value1, value2, "carOwner");
            return (Criteria) this;
        }

        public Criteria andCarOwnerAddrIsNull() {
            addCriterion("carOwnerAddr is null");
            return (Criteria) this;
        }

        public Criteria andCarOwnerAddrIsNotNull() {
            addCriterion("carOwnerAddr is not null");
            return (Criteria) this;
        }

        public Criteria andCarOwnerAddrEqualTo(String value) {
            addCriterion("carOwnerAddr =", value, "carOwnerAddr");
            return (Criteria) this;
        }

        public Criteria andCarOwnerAddrNotEqualTo(String value) {
            addCriterion("carOwnerAddr <>", value, "carOwnerAddr");
            return (Criteria) this;
        }

        public Criteria andCarOwnerAddrGreaterThan(String value) {
            addCriterion("carOwnerAddr >", value, "carOwnerAddr");
            return (Criteria) this;
        }

        public Criteria andCarOwnerAddrGreaterThanOrEqualTo(String value) {
            addCriterion("carOwnerAddr >=", value, "carOwnerAddr");
            return (Criteria) this;
        }

        public Criteria andCarOwnerAddrLessThan(String value) {
            addCriterion("carOwnerAddr <", value, "carOwnerAddr");
            return (Criteria) this;
        }

        public Criteria andCarOwnerAddrLessThanOrEqualTo(String value) {
            addCriterion("carOwnerAddr <=", value, "carOwnerAddr");
            return (Criteria) this;
        }

        public Criteria andCarOwnerAddrLike(String value) {
            addCriterion("carOwnerAddr like", value, "carOwnerAddr");
            return (Criteria) this;
        }

        public Criteria andCarOwnerAddrNotLike(String value) {
            addCriterion("carOwnerAddr not like", value, "carOwnerAddr");
            return (Criteria) this;
        }

        public Criteria andCarOwnerAddrIn(List<String> values) {
            addCriterion("carOwnerAddr in", values, "carOwnerAddr");
            return (Criteria) this;
        }

        public Criteria andCarOwnerAddrNotIn(List<String> values) {
            addCriterion("carOwnerAddr not in", values, "carOwnerAddr");
            return (Criteria) this;
        }

        public Criteria andCarOwnerAddrBetween(String value1, String value2) {
            addCriterion("carOwnerAddr between", value1, value2, "carOwnerAddr");
            return (Criteria) this;
        }

        public Criteria andCarOwnerAddrNotBetween(String value1, String value2) {
            addCriterion("carOwnerAddr not between", value1, value2, "carOwnerAddr");
            return (Criteria) this;
        }

        public Criteria andCarUseTypeIsNull() {
            addCriterion("carUseType is null");
            return (Criteria) this;
        }

        public Criteria andCarUseTypeIsNotNull() {
            addCriterion("carUseType is not null");
            return (Criteria) this;
        }

        public Criteria andCarUseTypeEqualTo(String value) {
            addCriterion("carUseType =", value, "carUseType");
            return (Criteria) this;
        }

        public Criteria andCarUseTypeNotEqualTo(String value) {
            addCriterion("carUseType <>", value, "carUseType");
            return (Criteria) this;
        }

        public Criteria andCarUseTypeGreaterThan(String value) {
            addCriterion("carUseType >", value, "carUseType");
            return (Criteria) this;
        }

        public Criteria andCarUseTypeGreaterThanOrEqualTo(String value) {
            addCriterion("carUseType >=", value, "carUseType");
            return (Criteria) this;
        }

        public Criteria andCarUseTypeLessThan(String value) {
            addCriterion("carUseType <", value, "carUseType");
            return (Criteria) this;
        }

        public Criteria andCarUseTypeLessThanOrEqualTo(String value) {
            addCriterion("carUseType <=", value, "carUseType");
            return (Criteria) this;
        }

        public Criteria andCarUseTypeLike(String value) {
            addCriterion("carUseType like", value, "carUseType");
            return (Criteria) this;
        }

        public Criteria andCarUseTypeNotLike(String value) {
            addCriterion("carUseType not like", value, "carUseType");
            return (Criteria) this;
        }

        public Criteria andCarUseTypeIn(List<String> values) {
            addCriterion("carUseType in", values, "carUseType");
            return (Criteria) this;
        }

        public Criteria andCarUseTypeNotIn(List<String> values) {
            addCriterion("carUseType not in", values, "carUseType");
            return (Criteria) this;
        }

        public Criteria andCarUseTypeBetween(String value1, String value2) {
            addCriterion("carUseType between", value1, value2, "carUseType");
            return (Criteria) this;
        }

        public Criteria andCarUseTypeNotBetween(String value1, String value2) {
            addCriterion("carUseType not between", value1, value2, "carUseType");
            return (Criteria) this;
        }

        public Criteria andCarEngineNoIsNull() {
            addCriterion("carEngineNo is null");
            return (Criteria) this;
        }

        public Criteria andCarEngineNoIsNotNull() {
            addCriterion("carEngineNo is not null");
            return (Criteria) this;
        }

        public Criteria andCarEngineNoEqualTo(String value) {
            addCriterion("carEngineNo =", value, "carEngineNo");
            return (Criteria) this;
        }

        public Criteria andCarEngineNoNotEqualTo(String value) {
            addCriterion("carEngineNo <>", value, "carEngineNo");
            return (Criteria) this;
        }

        public Criteria andCarEngineNoGreaterThan(String value) {
            addCriterion("carEngineNo >", value, "carEngineNo");
            return (Criteria) this;
        }

        public Criteria andCarEngineNoGreaterThanOrEqualTo(String value) {
            addCriterion("carEngineNo >=", value, "carEngineNo");
            return (Criteria) this;
        }

        public Criteria andCarEngineNoLessThan(String value) {
            addCriterion("carEngineNo <", value, "carEngineNo");
            return (Criteria) this;
        }

        public Criteria andCarEngineNoLessThanOrEqualTo(String value) {
            addCriterion("carEngineNo <=", value, "carEngineNo");
            return (Criteria) this;
        }

        public Criteria andCarEngineNoLike(String value) {
            addCriterion("carEngineNo like", value, "carEngineNo");
            return (Criteria) this;
        }

        public Criteria andCarEngineNoNotLike(String value) {
            addCriterion("carEngineNo not like", value, "carEngineNo");
            return (Criteria) this;
        }

        public Criteria andCarEngineNoIn(List<String> values) {
            addCriterion("carEngineNo in", values, "carEngineNo");
            return (Criteria) this;
        }

        public Criteria andCarEngineNoNotIn(List<String> values) {
            addCriterion("carEngineNo not in", values, "carEngineNo");
            return (Criteria) this;
        }

        public Criteria andCarEngineNoBetween(String value1, String value2) {
            addCriterion("carEngineNo between", value1, value2, "carEngineNo");
            return (Criteria) this;
        }

        public Criteria andCarEngineNoNotBetween(String value1, String value2) {
            addCriterion("carEngineNo not between", value1, value2, "carEngineNo");
            return (Criteria) this;
        }

        public Criteria andCarVinIsNull() {
            addCriterion("carVin is null");
            return (Criteria) this;
        }

        public Criteria andCarVinIsNotNull() {
            addCriterion("carVin is not null");
            return (Criteria) this;
        }

        public Criteria andCarVinEqualTo(String value) {
            addCriterion("carVin =", value, "carVin");
            return (Criteria) this;
        }

        public Criteria andCarVinNotEqualTo(String value) {
            addCriterion("carVin <>", value, "carVin");
            return (Criteria) this;
        }

        public Criteria andCarVinGreaterThan(String value) {
            addCriterion("carVin >", value, "carVin");
            return (Criteria) this;
        }

        public Criteria andCarVinGreaterThanOrEqualTo(String value) {
            addCriterion("carVin >=", value, "carVin");
            return (Criteria) this;
        }

        public Criteria andCarVinLessThan(String value) {
            addCriterion("carVin <", value, "carVin");
            return (Criteria) this;
        }

        public Criteria andCarVinLessThanOrEqualTo(String value) {
            addCriterion("carVin <=", value, "carVin");
            return (Criteria) this;
        }

        public Criteria andCarVinLike(String value) {
            addCriterion("carVin like", value, "carVin");
            return (Criteria) this;
        }

        public Criteria andCarVinNotLike(String value) {
            addCriterion("carVin not like", value, "carVin");
            return (Criteria) this;
        }

        public Criteria andCarVinIn(List<String> values) {
            addCriterion("carVin in", values, "carVin");
            return (Criteria) this;
        }

        public Criteria andCarVinNotIn(List<String> values) {
            addCriterion("carVin not in", values, "carVin");
            return (Criteria) this;
        }

        public Criteria andCarVinBetween(String value1, String value2) {
            addCriterion("carVin between", value1, value2, "carVin");
            return (Criteria) this;
        }

        public Criteria andCarVinNotBetween(String value1, String value2) {
            addCriterion("carVin not between", value1, value2, "carVin");
            return (Criteria) this;
        }

        public Criteria andCarRegTimeIsNull() {
            addCriterion("carRegTime is null");
            return (Criteria) this;
        }

        public Criteria andCarRegTimeIsNotNull() {
            addCriterion("carRegTime is not null");
            return (Criteria) this;
        }

        public Criteria andCarRegTimeEqualTo(Date value) {
            addCriterionForJDBCDate("carRegTime =", value, "carRegTime");
            return (Criteria) this;
        }

        public Criteria andCarRegTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("carRegTime <>", value, "carRegTime");
            return (Criteria) this;
        }

        public Criteria andCarRegTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("carRegTime >", value, "carRegTime");
            return (Criteria) this;
        }

        public Criteria andCarRegTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("carRegTime >=", value, "carRegTime");
            return (Criteria) this;
        }

        public Criteria andCarRegTimeLessThan(Date value) {
            addCriterionForJDBCDate("carRegTime <", value, "carRegTime");
            return (Criteria) this;
        }

        public Criteria andCarRegTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("carRegTime <=", value, "carRegTime");
            return (Criteria) this;
        }

        public Criteria andCarRegTimeIn(List<Date> values) {
            addCriterionForJDBCDate("carRegTime in", values, "carRegTime");
            return (Criteria) this;
        }

        public Criteria andCarRegTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("carRegTime not in", values, "carRegTime");
            return (Criteria) this;
        }

        public Criteria andCarRegTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("carRegTime between", value1, value2, "carRegTime");
            return (Criteria) this;
        }

        public Criteria andCarRegTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("carRegTime not between", value1, value2, "carRegTime");
            return (Criteria) this;
        }

        public Criteria andCarIssueTimeIsNull() {
            addCriterion("carIssueTime is null");
            return (Criteria) this;
        }

        public Criteria andCarIssueTimeIsNotNull() {
            addCriterion("carIssueTime is not null");
            return (Criteria) this;
        }

        public Criteria andCarIssueTimeEqualTo(Date value) {
            addCriterionForJDBCDate("carIssueTime =", value, "carIssueTime");
            return (Criteria) this;
        }

        public Criteria andCarIssueTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("carIssueTime <>", value, "carIssueTime");
            return (Criteria) this;
        }

        public Criteria andCarIssueTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("carIssueTime >", value, "carIssueTime");
            return (Criteria) this;
        }

        public Criteria andCarIssueTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("carIssueTime >=", value, "carIssueTime");
            return (Criteria) this;
        }

        public Criteria andCarIssueTimeLessThan(Date value) {
            addCriterionForJDBCDate("carIssueTime <", value, "carIssueTime");
            return (Criteria) this;
        }

        public Criteria andCarIssueTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("carIssueTime <=", value, "carIssueTime");
            return (Criteria) this;
        }

        public Criteria andCarIssueTimeIn(List<Date> values) {
            addCriterionForJDBCDate("carIssueTime in", values, "carIssueTime");
            return (Criteria) this;
        }

        public Criteria andCarIssueTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("carIssueTime not in", values, "carIssueTime");
            return (Criteria) this;
        }

        public Criteria andCarIssueTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("carIssueTime between", value1, value2, "carIssueTime");
            return (Criteria) this;
        }

        public Criteria andCarIssueTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("carIssueTime not between", value1, value2, "carIssueTime");
            return (Criteria) this;
        }

        public Criteria andBrandIdIsNull() {
            addCriterion("brandId is null");
            return (Criteria) this;
        }

        public Criteria andBrandIdIsNotNull() {
            addCriterion("brandId is not null");
            return (Criteria) this;
        }

        public Criteria andBrandIdEqualTo(Integer value) {
            addCriterion("brandId =", value, "brandId");
            return (Criteria) this;
        }

        public Criteria andBrandIdNotEqualTo(Integer value) {
            addCriterion("brandId <>", value, "brandId");
            return (Criteria) this;
        }

        public Criteria andBrandIdGreaterThan(Integer value) {
            addCriterion("brandId >", value, "brandId");
            return (Criteria) this;
        }

        public Criteria andBrandIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("brandId >=", value, "brandId");
            return (Criteria) this;
        }

        public Criteria andBrandIdLessThan(Integer value) {
            addCriterion("brandId <", value, "brandId");
            return (Criteria) this;
        }

        public Criteria andBrandIdLessThanOrEqualTo(Integer value) {
            addCriterion("brandId <=", value, "brandId");
            return (Criteria) this;
        }

        public Criteria andBrandIdIn(List<Integer> values) {
            addCriterion("brandId in", values, "brandId");
            return (Criteria) this;
        }

        public Criteria andBrandIdNotIn(List<Integer> values) {
            addCriterion("brandId not in", values, "brandId");
            return (Criteria) this;
        }

        public Criteria andBrandIdBetween(Integer value1, Integer value2) {
            addCriterion("brandId between", value1, value2, "brandId");
            return (Criteria) this;
        }

        public Criteria andBrandIdNotBetween(Integer value1, Integer value2) {
            addCriterion("brandId not between", value1, value2, "brandId");
            return (Criteria) this;
        }

        public Criteria andBrandNameIsNull() {
            addCriterion("brandName is null");
            return (Criteria) this;
        }

        public Criteria andBrandNameIsNotNull() {
            addCriterion("brandName is not null");
            return (Criteria) this;
        }

        public Criteria andBrandNameEqualTo(String value) {
            addCriterion("brandName =", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameNotEqualTo(String value) {
            addCriterion("brandName <>", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameGreaterThan(String value) {
            addCriterion("brandName >", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameGreaterThanOrEqualTo(String value) {
            addCriterion("brandName >=", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameLessThan(String value) {
            addCriterion("brandName <", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameLessThanOrEqualTo(String value) {
            addCriterion("brandName <=", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameLike(String value) {
            addCriterion("brandName like", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameNotLike(String value) {
            addCriterion("brandName not like", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameIn(List<String> values) {
            addCriterion("brandName in", values, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameNotIn(List<String> values) {
            addCriterion("brandName not in", values, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameBetween(String value1, String value2) {
            addCriterion("brandName between", value1, value2, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameNotBetween(String value1, String value2) {
            addCriterion("brandName not between", value1, value2, "brandName");
            return (Criteria) this;
        }

        public Criteria andCarColorIsNull() {
            addCriterion("carColor is null");
            return (Criteria) this;
        }

        public Criteria andCarColorIsNotNull() {
            addCriterion("carColor is not null");
            return (Criteria) this;
        }

        public Criteria andCarColorEqualTo(Byte value) {
            addCriterion("carColor =", value, "carColor");
            return (Criteria) this;
        }

        public Criteria andCarColorNotEqualTo(Byte value) {
            addCriterion("carColor <>", value, "carColor");
            return (Criteria) this;
        }

        public Criteria andCarColorGreaterThan(Byte value) {
            addCriterion("carColor >", value, "carColor");
            return (Criteria) this;
        }

        public Criteria andCarColorGreaterThanOrEqualTo(Byte value) {
            addCriterion("carColor >=", value, "carColor");
            return (Criteria) this;
        }

        public Criteria andCarColorLessThan(Byte value) {
            addCriterion("carColor <", value, "carColor");
            return (Criteria) this;
        }

        public Criteria andCarColorLessThanOrEqualTo(Byte value) {
            addCriterion("carColor <=", value, "carColor");
            return (Criteria) this;
        }

        public Criteria andCarColorIn(List<Byte> values) {
            addCriterion("carColor in", values, "carColor");
            return (Criteria) this;
        }

        public Criteria andCarColorNotIn(List<Byte> values) {
            addCriterion("carColor not in", values, "carColor");
            return (Criteria) this;
        }

        public Criteria andCarColorBetween(Byte value1, Byte value2) {
            addCriterion("carColor between", value1, value2, "carColor");
            return (Criteria) this;
        }

        public Criteria andCarColorNotBetween(Byte value1, Byte value2) {
            addCriterion("carColor not between", value1, value2, "carColor");
            return (Criteria) this;
        }

        public Criteria andCarPowerIsNull() {
            addCriterion("carPower is null");
            return (Criteria) this;
        }

        public Criteria andCarPowerIsNotNull() {
            addCriterion("carPower is not null");
            return (Criteria) this;
        }

        public Criteria andCarPowerEqualTo(Byte value) {
            addCriterion("carPower =", value, "carPower");
            return (Criteria) this;
        }

        public Criteria andCarPowerNotEqualTo(Byte value) {
            addCriterion("carPower <>", value, "carPower");
            return (Criteria) this;
        }

        public Criteria andCarPowerGreaterThan(Byte value) {
            addCriterion("carPower >", value, "carPower");
            return (Criteria) this;
        }

        public Criteria andCarPowerGreaterThanOrEqualTo(Byte value) {
            addCriterion("carPower >=", value, "carPower");
            return (Criteria) this;
        }

        public Criteria andCarPowerLessThan(Byte value) {
            addCriterion("carPower <", value, "carPower");
            return (Criteria) this;
        }

        public Criteria andCarPowerLessThanOrEqualTo(Byte value) {
            addCriterion("carPower <=", value, "carPower");
            return (Criteria) this;
        }

        public Criteria andCarPowerIn(List<Byte> values) {
            addCriterion("carPower in", values, "carPower");
            return (Criteria) this;
        }

        public Criteria andCarPowerNotIn(List<Byte> values) {
            addCriterion("carPower not in", values, "carPower");
            return (Criteria) this;
        }

        public Criteria andCarPowerBetween(Byte value1, Byte value2) {
            addCriterion("carPower between", value1, value2, "carPower");
            return (Criteria) this;
        }

        public Criteria andCarPowerNotBetween(Byte value1, Byte value2) {
            addCriterion("carPower not between", value1, value2, "carPower");
            return (Criteria) this;
        }

        public Criteria andCarImgIsNull() {
            addCriterion("carImg is null");
            return (Criteria) this;
        }

        public Criteria andCarImgIsNotNull() {
            addCriterion("carImg is not null");
            return (Criteria) this;
        }

        public Criteria andCarImgEqualTo(String value) {
            addCriterion("carImg =", value, "carImg");
            return (Criteria) this;
        }

        public Criteria andCarImgNotEqualTo(String value) {
            addCriterion("carImg <>", value, "carImg");
            return (Criteria) this;
        }

        public Criteria andCarImgGreaterThan(String value) {
            addCriterion("carImg >", value, "carImg");
            return (Criteria) this;
        }

        public Criteria andCarImgGreaterThanOrEqualTo(String value) {
            addCriterion("carImg >=", value, "carImg");
            return (Criteria) this;
        }

        public Criteria andCarImgLessThan(String value) {
            addCriterion("carImg <", value, "carImg");
            return (Criteria) this;
        }

        public Criteria andCarImgLessThanOrEqualTo(String value) {
            addCriterion("carImg <=", value, "carImg");
            return (Criteria) this;
        }

        public Criteria andCarImgLike(String value) {
            addCriterion("carImg like", value, "carImg");
            return (Criteria) this;
        }

        public Criteria andCarImgNotLike(String value) {
            addCriterion("carImg not like", value, "carImg");
            return (Criteria) this;
        }

        public Criteria andCarImgIn(List<String> values) {
            addCriterion("carImg in", values, "carImg");
            return (Criteria) this;
        }

        public Criteria andCarImgNotIn(List<String> values) {
            addCriterion("carImg not in", values, "carImg");
            return (Criteria) this;
        }

        public Criteria andCarImgBetween(String value1, String value2) {
            addCriterion("carImg between", value1, value2, "carImg");
            return (Criteria) this;
        }

        public Criteria andCarImgNotBetween(String value1, String value2) {
            addCriterion("carImg not between", value1, value2, "carImg");
            return (Criteria) this;
        }

        public Criteria andVerifyStartIsNull() {
            addCriterion("verifyStart is null");
            return (Criteria) this;
        }

        public Criteria andVerifyStartIsNotNull() {
            addCriterion("verifyStart is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyStartEqualTo(Date value) {
            addCriterionForJDBCDate("verifyStart =", value, "verifyStart");
            return (Criteria) this;
        }

        public Criteria andVerifyStartNotEqualTo(Date value) {
            addCriterionForJDBCDate("verifyStart <>", value, "verifyStart");
            return (Criteria) this;
        }

        public Criteria andVerifyStartGreaterThan(Date value) {
            addCriterionForJDBCDate("verifyStart >", value, "verifyStart");
            return (Criteria) this;
        }

        public Criteria andVerifyStartGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("verifyStart >=", value, "verifyStart");
            return (Criteria) this;
        }

        public Criteria andVerifyStartLessThan(Date value) {
            addCriterionForJDBCDate("verifyStart <", value, "verifyStart");
            return (Criteria) this;
        }

        public Criteria andVerifyStartLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("verifyStart <=", value, "verifyStart");
            return (Criteria) this;
        }

        public Criteria andVerifyStartIn(List<Date> values) {
            addCriterionForJDBCDate("verifyStart in", values, "verifyStart");
            return (Criteria) this;
        }

        public Criteria andVerifyStartNotIn(List<Date> values) {
            addCriterionForJDBCDate("verifyStart not in", values, "verifyStart");
            return (Criteria) this;
        }

        public Criteria andVerifyStartBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("verifyStart between", value1, value2, "verifyStart");
            return (Criteria) this;
        }

        public Criteria andVerifyStartNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("verifyStart not between", value1, value2, "verifyStart");
            return (Criteria) this;
        }

        public Criteria andVerifyEndIsNull() {
            addCriterion("verifyEnd is null");
            return (Criteria) this;
        }

        public Criteria andVerifyEndIsNotNull() {
            addCriterion("verifyEnd is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyEndEqualTo(Date value) {
            addCriterionForJDBCDate("verifyEnd =", value, "verifyEnd");
            return (Criteria) this;
        }

        public Criteria andVerifyEndNotEqualTo(Date value) {
            addCriterionForJDBCDate("verifyEnd <>", value, "verifyEnd");
            return (Criteria) this;
        }

        public Criteria andVerifyEndGreaterThan(Date value) {
            addCriterionForJDBCDate("verifyEnd >", value, "verifyEnd");
            return (Criteria) this;
        }

        public Criteria andVerifyEndGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("verifyEnd >=", value, "verifyEnd");
            return (Criteria) this;
        }

        public Criteria andVerifyEndLessThan(Date value) {
            addCriterionForJDBCDate("verifyEnd <", value, "verifyEnd");
            return (Criteria) this;
        }

        public Criteria andVerifyEndLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("verifyEnd <=", value, "verifyEnd");
            return (Criteria) this;
        }

        public Criteria andVerifyEndIn(List<Date> values) {
            addCriterionForJDBCDate("verifyEnd in", values, "verifyEnd");
            return (Criteria) this;
        }

        public Criteria andVerifyEndNotIn(List<Date> values) {
            addCriterionForJDBCDate("verifyEnd not in", values, "verifyEnd");
            return (Criteria) this;
        }

        public Criteria andVerifyEndBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("verifyEnd between", value1, value2, "verifyEnd");
            return (Criteria) this;
        }

        public Criteria andVerifyEndNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("verifyEnd not between", value1, value2, "verifyEnd");
            return (Criteria) this;
        }

        public Criteria andVerifyFeeIsNull() {
            addCriterion("verifyFee is null");
            return (Criteria) this;
        }

        public Criteria andVerifyFeeIsNotNull() {
            addCriterion("verifyFee is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyFeeEqualTo(Integer value) {
            addCriterion("verifyFee =", value, "verifyFee");
            return (Criteria) this;
        }

        public Criteria andVerifyFeeNotEqualTo(Integer value) {
            addCriterion("verifyFee <>", value, "verifyFee");
            return (Criteria) this;
        }

        public Criteria andVerifyFeeGreaterThan(Integer value) {
            addCriterion("verifyFee >", value, "verifyFee");
            return (Criteria) this;
        }

        public Criteria andVerifyFeeGreaterThanOrEqualTo(Integer value) {
            addCriterion("verifyFee >=", value, "verifyFee");
            return (Criteria) this;
        }

        public Criteria andVerifyFeeLessThan(Integer value) {
            addCriterion("verifyFee <", value, "verifyFee");
            return (Criteria) this;
        }

        public Criteria andVerifyFeeLessThanOrEqualTo(Integer value) {
            addCriterion("verifyFee <=", value, "verifyFee");
            return (Criteria) this;
        }

        public Criteria andVerifyFeeIn(List<Integer> values) {
            addCriterion("verifyFee in", values, "verifyFee");
            return (Criteria) this;
        }

        public Criteria andVerifyFeeNotIn(List<Integer> values) {
            addCriterion("verifyFee not in", values, "verifyFee");
            return (Criteria) this;
        }

        public Criteria andVerifyFeeBetween(Integer value1, Integer value2) {
            addCriterion("verifyFee between", value1, value2, "verifyFee");
            return (Criteria) this;
        }

        public Criteria andVerifyFeeNotBetween(Integer value1, Integer value2) {
            addCriterion("verifyFee not between", value1, value2, "verifyFee");
            return (Criteria) this;
        }

        public Criteria andVerifyStateIsNull() {
            addCriterion("verifyState is null");
            return (Criteria) this;
        }

        public Criteria andVerifyStateIsNotNull() {
            addCriterion("verifyState is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyStateEqualTo(Byte value) {
            addCriterion("verifyState =", value, "verifyState");
            return (Criteria) this;
        }

        public Criteria andVerifyStateNotEqualTo(Byte value) {
            addCriterion("verifyState <>", value, "verifyState");
            return (Criteria) this;
        }

        public Criteria andVerifyStateGreaterThan(Byte value) {
            addCriterion("verifyState >", value, "verifyState");
            return (Criteria) this;
        }

        public Criteria andVerifyStateGreaterThanOrEqualTo(Byte value) {
            addCriterion("verifyState >=", value, "verifyState");
            return (Criteria) this;
        }

        public Criteria andVerifyStateLessThan(Byte value) {
            addCriterion("verifyState <", value, "verifyState");
            return (Criteria) this;
        }

        public Criteria andVerifyStateLessThanOrEqualTo(Byte value) {
            addCriterion("verifyState <=", value, "verifyState");
            return (Criteria) this;
        }

        public Criteria andVerifyStateIn(List<Byte> values) {
            addCriterion("verifyState in", values, "verifyState");
            return (Criteria) this;
        }

        public Criteria andVerifyStateNotIn(List<Byte> values) {
            addCriterion("verifyState not in", values, "verifyState");
            return (Criteria) this;
        }

        public Criteria andVerifyStateBetween(Byte value1, Byte value2) {
            addCriterion("verifyState between", value1, value2, "verifyState");
            return (Criteria) this;
        }

        public Criteria andVerifyStateNotBetween(Byte value1, Byte value2) {
            addCriterion("verifyState not between", value1, value2, "verifyState");
            return (Criteria) this;
        }

        public Criteria andInsuranceTypeIsNull() {
            addCriterion("insuranceType is null");
            return (Criteria) this;
        }

        public Criteria andInsuranceTypeIsNotNull() {
            addCriterion("insuranceType is not null");
            return (Criteria) this;
        }

        public Criteria andInsuranceTypeEqualTo(Byte value) {
            addCriterion("insuranceType =", value, "insuranceType");
            return (Criteria) this;
        }

        public Criteria andInsuranceTypeNotEqualTo(Byte value) {
            addCriterion("insuranceType <>", value, "insuranceType");
            return (Criteria) this;
        }

        public Criteria andInsuranceTypeGreaterThan(Byte value) {
            addCriterion("insuranceType >", value, "insuranceType");
            return (Criteria) this;
        }

        public Criteria andInsuranceTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("insuranceType >=", value, "insuranceType");
            return (Criteria) this;
        }

        public Criteria andInsuranceTypeLessThan(Byte value) {
            addCriterion("insuranceType <", value, "insuranceType");
            return (Criteria) this;
        }

        public Criteria andInsuranceTypeLessThanOrEqualTo(Byte value) {
            addCriterion("insuranceType <=", value, "insuranceType");
            return (Criteria) this;
        }

        public Criteria andInsuranceTypeIn(List<Byte> values) {
            addCriterion("insuranceType in", values, "insuranceType");
            return (Criteria) this;
        }

        public Criteria andInsuranceTypeNotIn(List<Byte> values) {
            addCriterion("insuranceType not in", values, "insuranceType");
            return (Criteria) this;
        }

        public Criteria andInsuranceTypeBetween(Byte value1, Byte value2) {
            addCriterion("insuranceType between", value1, value2, "insuranceType");
            return (Criteria) this;
        }

        public Criteria andInsuranceTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("insuranceType not between", value1, value2, "insuranceType");
            return (Criteria) this;
        }

        public Criteria andInsuranceMoneyIsNull() {
            addCriterion("insuranceMoney is null");
            return (Criteria) this;
        }

        public Criteria andInsuranceMoneyIsNotNull() {
            addCriterion("insuranceMoney is not null");
            return (Criteria) this;
        }

        public Criteria andInsuranceMoneyEqualTo(Integer value) {
            addCriterion("insuranceMoney =", value, "insuranceMoney");
            return (Criteria) this;
        }

        public Criteria andInsuranceMoneyNotEqualTo(Integer value) {
            addCriterion("insuranceMoney <>", value, "insuranceMoney");
            return (Criteria) this;
        }

        public Criteria andInsuranceMoneyGreaterThan(Integer value) {
            addCriterion("insuranceMoney >", value, "insuranceMoney");
            return (Criteria) this;
        }

        public Criteria andInsuranceMoneyGreaterThanOrEqualTo(Integer value) {
            addCriterion("insuranceMoney >=", value, "insuranceMoney");
            return (Criteria) this;
        }

        public Criteria andInsuranceMoneyLessThan(Integer value) {
            addCriterion("insuranceMoney <", value, "insuranceMoney");
            return (Criteria) this;
        }

        public Criteria andInsuranceMoneyLessThanOrEqualTo(Integer value) {
            addCriterion("insuranceMoney <=", value, "insuranceMoney");
            return (Criteria) this;
        }

        public Criteria andInsuranceMoneyIn(List<Integer> values) {
            addCriterion("insuranceMoney in", values, "insuranceMoney");
            return (Criteria) this;
        }

        public Criteria andInsuranceMoneyNotIn(List<Integer> values) {
            addCriterion("insuranceMoney not in", values, "insuranceMoney");
            return (Criteria) this;
        }

        public Criteria andInsuranceMoneyBetween(Integer value1, Integer value2) {
            addCriterion("insuranceMoney between", value1, value2, "insuranceMoney");
            return (Criteria) this;
        }

        public Criteria andInsuranceMoneyNotBetween(Integer value1, Integer value2) {
            addCriterion("insuranceMoney not between", value1, value2, "insuranceMoney");
            return (Criteria) this;
        }

        public Criteria andInsuranceStartIsNull() {
            addCriterion("insuranceStart is null");
            return (Criteria) this;
        }

        public Criteria andInsuranceStartIsNotNull() {
            addCriterion("insuranceStart is not null");
            return (Criteria) this;
        }

        public Criteria andInsuranceStartEqualTo(Date value) {
            addCriterionForJDBCDate("insuranceStart =", value, "insuranceStart");
            return (Criteria) this;
        }

        public Criteria andInsuranceStartNotEqualTo(Date value) {
            addCriterionForJDBCDate("insuranceStart <>", value, "insuranceStart");
            return (Criteria) this;
        }

        public Criteria andInsuranceStartGreaterThan(Date value) {
            addCriterionForJDBCDate("insuranceStart >", value, "insuranceStart");
            return (Criteria) this;
        }

        public Criteria andInsuranceStartGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("insuranceStart >=", value, "insuranceStart");
            return (Criteria) this;
        }

        public Criteria andInsuranceStartLessThan(Date value) {
            addCriterionForJDBCDate("insuranceStart <", value, "insuranceStart");
            return (Criteria) this;
        }

        public Criteria andInsuranceStartLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("insuranceStart <=", value, "insuranceStart");
            return (Criteria) this;
        }

        public Criteria andInsuranceStartIn(List<Date> values) {
            addCriterionForJDBCDate("insuranceStart in", values, "insuranceStart");
            return (Criteria) this;
        }

        public Criteria andInsuranceStartNotIn(List<Date> values) {
            addCriterionForJDBCDate("insuranceStart not in", values, "insuranceStart");
            return (Criteria) this;
        }

        public Criteria andInsuranceStartBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("insuranceStart between", value1, value2, "insuranceStart");
            return (Criteria) this;
        }

        public Criteria andInsuranceStartNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("insuranceStart not between", value1, value2, "insuranceStart");
            return (Criteria) this;
        }

        public Criteria andInsuranceEndIsNull() {
            addCriterion("insuranceEnd is null");
            return (Criteria) this;
        }

        public Criteria andInsuranceEndIsNotNull() {
            addCriterion("insuranceEnd is not null");
            return (Criteria) this;
        }

        public Criteria andInsuranceEndEqualTo(Date value) {
            addCriterionForJDBCDate("insuranceEnd =", value, "insuranceEnd");
            return (Criteria) this;
        }

        public Criteria andInsuranceEndNotEqualTo(Date value) {
            addCriterionForJDBCDate("insuranceEnd <>", value, "insuranceEnd");
            return (Criteria) this;
        }

        public Criteria andInsuranceEndGreaterThan(Date value) {
            addCriterionForJDBCDate("insuranceEnd >", value, "insuranceEnd");
            return (Criteria) this;
        }

        public Criteria andInsuranceEndGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("insuranceEnd >=", value, "insuranceEnd");
            return (Criteria) this;
        }

        public Criteria andInsuranceEndLessThan(Date value) {
            addCriterionForJDBCDate("insuranceEnd <", value, "insuranceEnd");
            return (Criteria) this;
        }

        public Criteria andInsuranceEndLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("insuranceEnd <=", value, "insuranceEnd");
            return (Criteria) this;
        }

        public Criteria andInsuranceEndIn(List<Date> values) {
            addCriterionForJDBCDate("insuranceEnd in", values, "insuranceEnd");
            return (Criteria) this;
        }

        public Criteria andInsuranceEndNotIn(List<Date> values) {
            addCriterionForJDBCDate("insuranceEnd not in", values, "insuranceEnd");
            return (Criteria) this;
        }

        public Criteria andInsuranceEndBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("insuranceEnd between", value1, value2, "insuranceEnd");
            return (Criteria) this;
        }

        public Criteria andInsuranceEndNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("insuranceEnd not between", value1, value2, "insuranceEnd");
            return (Criteria) this;
        }

        public Criteria andInsuranceCoIsNull() {
            addCriterion("insuranceCo is null");
            return (Criteria) this;
        }

        public Criteria andInsuranceCoIsNotNull() {
            addCriterion("insuranceCo is not null");
            return (Criteria) this;
        }

        public Criteria andInsuranceCoEqualTo(String value) {
            addCriterion("insuranceCo =", value, "insuranceCo");
            return (Criteria) this;
        }

        public Criteria andInsuranceCoNotEqualTo(String value) {
            addCriterion("insuranceCo <>", value, "insuranceCo");
            return (Criteria) this;
        }

        public Criteria andInsuranceCoGreaterThan(String value) {
            addCriterion("insuranceCo >", value, "insuranceCo");
            return (Criteria) this;
        }

        public Criteria andInsuranceCoGreaterThanOrEqualTo(String value) {
            addCriterion("insuranceCo >=", value, "insuranceCo");
            return (Criteria) this;
        }

        public Criteria andInsuranceCoLessThan(String value) {
            addCriterion("insuranceCo <", value, "insuranceCo");
            return (Criteria) this;
        }

        public Criteria andInsuranceCoLessThanOrEqualTo(String value) {
            addCriterion("insuranceCo <=", value, "insuranceCo");
            return (Criteria) this;
        }

        public Criteria andInsuranceCoLike(String value) {
            addCriterion("insuranceCo like", value, "insuranceCo");
            return (Criteria) this;
        }

        public Criteria andInsuranceCoNotLike(String value) {
            addCriterion("insuranceCo not like", value, "insuranceCo");
            return (Criteria) this;
        }

        public Criteria andInsuranceCoIn(List<String> values) {
            addCriterion("insuranceCo in", values, "insuranceCo");
            return (Criteria) this;
        }

        public Criteria andInsuranceCoNotIn(List<String> values) {
            addCriterion("insuranceCo not in", values, "insuranceCo");
            return (Criteria) this;
        }

        public Criteria andInsuranceCoBetween(String value1, String value2) {
            addCriterion("insuranceCo between", value1, value2, "insuranceCo");
            return (Criteria) this;
        }

        public Criteria andInsuranceCoNotBetween(String value1, String value2) {
            addCriterion("insuranceCo not between", value1, value2, "insuranceCo");
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