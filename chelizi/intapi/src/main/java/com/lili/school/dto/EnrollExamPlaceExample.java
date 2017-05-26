package com.lili.school.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class EnrollExamPlaceExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EnrollExamPlaceExample() {
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

        public Criteria andPlaceidIsNull() {
            addCriterion("placeId is null");
            return (Criteria) this;
        }

        public Criteria andPlaceidIsNotNull() {
            addCriterion("placeId is not null");
            return (Criteria) this;
        }

        public Criteria andPlaceidEqualTo(Integer value) {
            addCriterion("placeId =", value, "placeid");
            return (Criteria) this;
        }

        public Criteria andPlaceidNotEqualTo(Integer value) {
            addCriterion("placeId <>", value, "placeid");
            return (Criteria) this;
        }

        public Criteria andPlaceidGreaterThan(Integer value) {
            addCriterion("placeId >", value, "placeid");
            return (Criteria) this;
        }

        public Criteria andPlaceidGreaterThanOrEqualTo(Integer value) {
            addCriterion("placeId >=", value, "placeid");
            return (Criteria) this;
        }

        public Criteria andPlaceidLessThan(Integer value) {
            addCriterion("placeId <", value, "placeid");
            return (Criteria) this;
        }

        public Criteria andPlaceidLessThanOrEqualTo(Integer value) {
            addCriterion("placeId <=", value, "placeid");
            return (Criteria) this;
        }

        public Criteria andPlaceidIn(List<Integer> values) {
            addCriterion("placeId in", values, "placeid");
            return (Criteria) this;
        }

        public Criteria andPlaceidNotIn(List<Integer> values) {
            addCriterion("placeId not in", values, "placeid");
            return (Criteria) this;
        }

        public Criteria andPlaceidBetween(Integer value1, Integer value2) {
            addCriterion("placeId between", value1, value2, "placeid");
            return (Criteria) this;
        }

        public Criteria andPlaceidNotBetween(Integer value1, Integer value2) {
            addCriterion("placeId not between", value1, value2, "placeid");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andCityidIsNull() {
            addCriterion("cityId is null");
            return (Criteria) this;
        }

        public Criteria andCityidIsNotNull() {
            addCriterion("cityId is not null");
            return (Criteria) this;
        }

        public Criteria andCityidEqualTo(Integer value) {
            addCriterion("cityId =", value, "cityid");
            return (Criteria) this;
        }

        public Criteria andCityidNotEqualTo(Integer value) {
            addCriterion("cityId <>", value, "cityid");
            return (Criteria) this;
        }

        public Criteria andCityidGreaterThan(Integer value) {
            addCriterion("cityId >", value, "cityid");
            return (Criteria) this;
        }

        public Criteria andCityidGreaterThanOrEqualTo(Integer value) {
            addCriterion("cityId >=", value, "cityid");
            return (Criteria) this;
        }

        public Criteria andCityidLessThan(Integer value) {
            addCriterion("cityId <", value, "cityid");
            return (Criteria) this;
        }

        public Criteria andCityidLessThanOrEqualTo(Integer value) {
            addCriterion("cityId <=", value, "cityid");
            return (Criteria) this;
        }

        public Criteria andCityidIn(List<Integer> values) {
            addCriterion("cityId in", values, "cityid");
            return (Criteria) this;
        }

        public Criteria andCityidNotIn(List<Integer> values) {
            addCriterion("cityId not in", values, "cityid");
            return (Criteria) this;
        }

        public Criteria andCityidBetween(Integer value1, Integer value2) {
            addCriterion("cityId between", value1, value2, "cityid");
            return (Criteria) this;
        }

        public Criteria andCityidNotBetween(Integer value1, Integer value2) {
            addCriterion("cityId not between", value1, value2, "cityid");
            return (Criteria) this;
        }

        public Criteria andLgeIsNull() {
            addCriterion("lge is null");
            return (Criteria) this;
        }

        public Criteria andLgeIsNotNull() {
            addCriterion("lge is not null");
            return (Criteria) this;
        }

        public Criteria andLgeEqualTo(BigDecimal value) {
            addCriterion("lge =", value, "lge");
            return (Criteria) this;
        }

        public Criteria andLgeNotEqualTo(BigDecimal value) {
            addCriterion("lge <>", value, "lge");
            return (Criteria) this;
        }

        public Criteria andLgeGreaterThan(BigDecimal value) {
            addCriterion("lge >", value, "lge");
            return (Criteria) this;
        }

        public Criteria andLgeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("lge >=", value, "lge");
            return (Criteria) this;
        }

        public Criteria andLgeLessThan(BigDecimal value) {
            addCriterion("lge <", value, "lge");
            return (Criteria) this;
        }

        public Criteria andLgeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("lge <=", value, "lge");
            return (Criteria) this;
        }

        public Criteria andLgeIn(List<BigDecimal> values) {
            addCriterion("lge in", values, "lge");
            return (Criteria) this;
        }

        public Criteria andLgeNotIn(List<BigDecimal> values) {
            addCriterion("lge not in", values, "lge");
            return (Criteria) this;
        }

        public Criteria andLgeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("lge between", value1, value2, "lge");
            return (Criteria) this;
        }

        public Criteria andLgeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("lge not between", value1, value2, "lge");
            return (Criteria) this;
        }

        public Criteria andLaeIsNull() {
            addCriterion("lae is null");
            return (Criteria) this;
        }

        public Criteria andLaeIsNotNull() {
            addCriterion("lae is not null");
            return (Criteria) this;
        }

        public Criteria andLaeEqualTo(BigDecimal value) {
            addCriterion("lae =", value, "lae");
            return (Criteria) this;
        }

        public Criteria andLaeNotEqualTo(BigDecimal value) {
            addCriterion("lae <>", value, "lae");
            return (Criteria) this;
        }

        public Criteria andLaeGreaterThan(BigDecimal value) {
            addCriterion("lae >", value, "lae");
            return (Criteria) this;
        }

        public Criteria andLaeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("lae >=", value, "lae");
            return (Criteria) this;
        }

        public Criteria andLaeLessThan(BigDecimal value) {
            addCriterion("lae <", value, "lae");
            return (Criteria) this;
        }

        public Criteria andLaeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("lae <=", value, "lae");
            return (Criteria) this;
        }

        public Criteria andLaeIn(List<BigDecimal> values) {
            addCriterion("lae in", values, "lae");
            return (Criteria) this;
        }

        public Criteria andLaeNotIn(List<BigDecimal> values) {
            addCriterion("lae not in", values, "lae");
            return (Criteria) this;
        }

        public Criteria andLaeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("lae between", value1, value2, "lae");
            return (Criteria) this;
        }

        public Criteria andLaeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("lae not between", value1, value2, "lae");
            return (Criteria) this;
        }

        public Criteria andPosdescIsNull() {
            addCriterion("posDesc is null");
            return (Criteria) this;
        }

        public Criteria andPosdescIsNotNull() {
            addCriterion("posDesc is not null");
            return (Criteria) this;
        }

        public Criteria andPosdescEqualTo(String value) {
            addCriterion("posDesc =", value, "posdesc");
            return (Criteria) this;
        }

        public Criteria andPosdescNotEqualTo(String value) {
            addCriterion("posDesc <>", value, "posdesc");
            return (Criteria) this;
        }

        public Criteria andPosdescGreaterThan(String value) {
            addCriterion("posDesc >", value, "posdesc");
            return (Criteria) this;
        }

        public Criteria andPosdescGreaterThanOrEqualTo(String value) {
            addCriterion("posDesc >=", value, "posdesc");
            return (Criteria) this;
        }

        public Criteria andPosdescLessThan(String value) {
            addCriterion("posDesc <", value, "posdesc");
            return (Criteria) this;
        }

        public Criteria andPosdescLessThanOrEqualTo(String value) {
            addCriterion("posDesc <=", value, "posdesc");
            return (Criteria) this;
        }

        public Criteria andPosdescLike(String value) {
            addCriterion("posDesc like", value, "posdesc");
            return (Criteria) this;
        }

        public Criteria andPosdescNotLike(String value) {
            addCriterion("posDesc not like", value, "posdesc");
            return (Criteria) this;
        }

        public Criteria andPosdescIn(List<String> values) {
            addCriterion("posDesc in", values, "posdesc");
            return (Criteria) this;
        }

        public Criteria andPosdescNotIn(List<String> values) {
            addCriterion("posDesc not in", values, "posdesc");
            return (Criteria) this;
        }

        public Criteria andPosdescBetween(String value1, String value2) {
            addCriterion("posDesc between", value1, value2, "posdesc");
            return (Criteria) this;
        }

        public Criteria andPosdescNotBetween(String value1, String value2) {
            addCriterion("posDesc not between", value1, value2, "posdesc");
            return (Criteria) this;
        }

        public Criteria andPhonenumIsNull() {
            addCriterion("phoneNum is null");
            return (Criteria) this;
        }

        public Criteria andPhonenumIsNotNull() {
            addCriterion("phoneNum is not null");
            return (Criteria) this;
        }

        public Criteria andPhonenumEqualTo(String value) {
            addCriterion("phoneNum =", value, "phonenum");
            return (Criteria) this;
        }

        public Criteria andPhonenumNotEqualTo(String value) {
            addCriterion("phoneNum <>", value, "phonenum");
            return (Criteria) this;
        }

        public Criteria andPhonenumGreaterThan(String value) {
            addCriterion("phoneNum >", value, "phonenum");
            return (Criteria) this;
        }

        public Criteria andPhonenumGreaterThanOrEqualTo(String value) {
            addCriterion("phoneNum >=", value, "phonenum");
            return (Criteria) this;
        }

        public Criteria andPhonenumLessThan(String value) {
            addCriterion("phoneNum <", value, "phonenum");
            return (Criteria) this;
        }

        public Criteria andPhonenumLessThanOrEqualTo(String value) {
            addCriterion("phoneNum <=", value, "phonenum");
            return (Criteria) this;
        }

        public Criteria andPhonenumLike(String value) {
            addCriterion("phoneNum like", value, "phonenum");
            return (Criteria) this;
        }

        public Criteria andPhonenumNotLike(String value) {
            addCriterion("phoneNum not like", value, "phonenum");
            return (Criteria) this;
        }

        public Criteria andPhonenumIn(List<String> values) {
            addCriterion("phoneNum in", values, "phonenum");
            return (Criteria) this;
        }

        public Criteria andPhonenumNotIn(List<String> values) {
            addCriterion("phoneNum not in", values, "phonenum");
            return (Criteria) this;
        }

        public Criteria andPhonenumBetween(String value1, String value2) {
            addCriterion("phoneNum between", value1, value2, "phonenum");
            return (Criteria) this;
        }

        public Criteria andPhonenumNotBetween(String value1, String value2) {
            addCriterion("phoneNum not between", value1, value2, "phonenum");
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