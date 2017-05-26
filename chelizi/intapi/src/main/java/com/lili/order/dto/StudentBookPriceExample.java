package com.lili.order.dto;

import java.util.ArrayList;
import java.util.List;

public class StudentBookPriceExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public StudentBookPriceExample() {
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

        public Criteria andRegionIdIsNull() {
            addCriterion("regionId is null");
            return (Criteria) this;
        }

        public Criteria andRegionIdIsNotNull() {
            addCriterion("regionId is not null");
            return (Criteria) this;
        }

        public Criteria andRegionIdEqualTo(Integer value) {
            addCriterion("regionId =", value, "regionId");
            return (Criteria) this;
        }

        public Criteria andRegionIdNotEqualTo(Integer value) {
            addCriterion("regionId <>", value, "regionId");
            return (Criteria) this;
        }

        public Criteria andRegionIdGreaterThan(Integer value) {
            addCriterion("regionId >", value, "regionId");
            return (Criteria) this;
        }

        public Criteria andRegionIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("regionId >=", value, "regionId");
            return (Criteria) this;
        }

        public Criteria andRegionIdLessThan(Integer value) {
            addCriterion("regionId <", value, "regionId");
            return (Criteria) this;
        }

        public Criteria andRegionIdLessThanOrEqualTo(Integer value) {
            addCriterion("regionId <=", value, "regionId");
            return (Criteria) this;
        }

        public Criteria andRegionIdIn(List<Integer> values) {
            addCriterion("regionId in", values, "regionId");
            return (Criteria) this;
        }

        public Criteria andRegionIdNotIn(List<Integer> values) {
            addCriterion("regionId not in", values, "regionId");
            return (Criteria) this;
        }

        public Criteria andRegionIdBetween(Integer value1, Integer value2) {
            addCriterion("regionId between", value1, value2, "regionId");
            return (Criteria) this;
        }

        public Criteria andRegionIdNotBetween(Integer value1, Integer value2) {
            addCriterion("regionId not between", value1, value2, "regionId");
            return (Criteria) this;
        }

        public Criteria andCourseTmpIdIsNull() {
            addCriterion("courseTmpId is null");
            return (Criteria) this;
        }

        public Criteria andCourseTmpIdIsNotNull() {
            addCriterion("courseTmpId is not null");
            return (Criteria) this;
        }

        public Criteria andCourseTmpIdEqualTo(Integer value) {
            addCriterion("courseTmpId =", value, "courseTmpId");
            return (Criteria) this;
        }

        public Criteria andCourseTmpIdNotEqualTo(Integer value) {
            addCriterion("courseTmpId <>", value, "courseTmpId");
            return (Criteria) this;
        }

        public Criteria andCourseTmpIdGreaterThan(Integer value) {
            addCriterion("courseTmpId >", value, "courseTmpId");
            return (Criteria) this;
        }

        public Criteria andCourseTmpIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("courseTmpId >=", value, "courseTmpId");
            return (Criteria) this;
        }

        public Criteria andCourseTmpIdLessThan(Integer value) {
            addCriterion("courseTmpId <", value, "courseTmpId");
            return (Criteria) this;
        }

        public Criteria andCourseTmpIdLessThanOrEqualTo(Integer value) {
            addCriterion("courseTmpId <=", value, "courseTmpId");
            return (Criteria) this;
        }

        public Criteria andCourseTmpIdIn(List<Integer> values) {
            addCriterion("courseTmpId in", values, "courseTmpId");
            return (Criteria) this;
        }

        public Criteria andCourseTmpIdNotIn(List<Integer> values) {
            addCriterion("courseTmpId not in", values, "courseTmpId");
            return (Criteria) this;
        }

        public Criteria andCourseTmpIdBetween(Integer value1, Integer value2) {
            addCriterion("courseTmpId between", value1, value2, "courseTmpId");
            return (Criteria) this;
        }

        public Criteria andCourseTmpIdNotBetween(Integer value1, Integer value2) {
            addCriterion("courseTmpId not between", value1, value2, "courseTmpId");
            return (Criteria) this;
        }

        public Criteria andDltypeIsNull() {
            addCriterion("dltype is null");
            return (Criteria) this;
        }

        public Criteria andDltypeIsNotNull() {
            addCriterion("dltype is not null");
            return (Criteria) this;
        }

        public Criteria andDltypeEqualTo(Byte value) {
            addCriterion("dltype =", value, "dltype");
            return (Criteria) this;
        }

        public Criteria andDltypeNotEqualTo(Byte value) {
            addCriterion("dltype <>", value, "dltype");
            return (Criteria) this;
        }

        public Criteria andDltypeGreaterThan(Byte value) {
            addCriterion("dltype >", value, "dltype");
            return (Criteria) this;
        }

        public Criteria andDltypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("dltype >=", value, "dltype");
            return (Criteria) this;
        }

        public Criteria andDltypeLessThan(Byte value) {
            addCriterion("dltype <", value, "dltype");
            return (Criteria) this;
        }

        public Criteria andDltypeLessThanOrEqualTo(Byte value) {
            addCriterion("dltype <=", value, "dltype");
            return (Criteria) this;
        }

        public Criteria andDltypeIn(List<Byte> values) {
            addCriterion("dltype in", values, "dltype");
            return (Criteria) this;
        }

        public Criteria andDltypeNotIn(List<Byte> values) {
            addCriterion("dltype not in", values, "dltype");
            return (Criteria) this;
        }

        public Criteria andDltypeBetween(Byte value1, Byte value2) {
            addCriterion("dltype between", value1, value2, "dltype");
            return (Criteria) this;
        }

        public Criteria andDltypeNotBetween(Byte value1, Byte value2) {
            addCriterion("dltype not between", value1, value2, "dltype");
            return (Criteria) this;
        }

        public Criteria andDateRuleIsNull() {
            addCriterion("dateRule is null");
            return (Criteria) this;
        }

        public Criteria andDateRuleIsNotNull() {
            addCriterion("dateRule is not null");
            return (Criteria) this;
        }

        public Criteria andDateRuleEqualTo(String value) {
            addCriterion("dateRule =", value, "dateRule");
            return (Criteria) this;
        }

        public Criteria andDateRuleNotEqualTo(String value) {
            addCriterion("dateRule <>", value, "dateRule");
            return (Criteria) this;
        }

        public Criteria andDateRuleGreaterThan(String value) {
            addCriterion("dateRule >", value, "dateRule");
            return (Criteria) this;
        }

        public Criteria andDateRuleGreaterThanOrEqualTo(String value) {
            addCriterion("dateRule >=", value, "dateRule");
            return (Criteria) this;
        }

        public Criteria andDateRuleLessThan(String value) {
            addCriterion("dateRule <", value, "dateRule");
            return (Criteria) this;
        }

        public Criteria andDateRuleLessThanOrEqualTo(String value) {
            addCriterion("dateRule <=", value, "dateRule");
            return (Criteria) this;
        }

        public Criteria andDateRuleLike(String value) {
            addCriterion("dateRule like", value, "dateRule");
            return (Criteria) this;
        }

        public Criteria andDateRuleNotLike(String value) {
            addCriterion("dateRule not like", value, "dateRule");
            return (Criteria) this;
        }

        public Criteria andDateRuleIn(List<String> values) {
            addCriterion("dateRule in", values, "dateRule");
            return (Criteria) this;
        }

        public Criteria andDateRuleNotIn(List<String> values) {
            addCriterion("dateRule not in", values, "dateRule");
            return (Criteria) this;
        }

        public Criteria andDateRuleBetween(String value1, String value2) {
            addCriterion("dateRule between", value1, value2, "dateRule");
            return (Criteria) this;
        }

        public Criteria andDateRuleNotBetween(String value1, String value2) {
            addCriterion("dateRule not between", value1, value2, "dateRule");
            return (Criteria) this;
        }

        public Criteria andPriceIsNull() {
            addCriterion("price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(Integer value) {
            addCriterion("price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(Integer value) {
            addCriterion("price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(Integer value) {
            addCriterion("price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(Integer value) {
            addCriterion("price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(Integer value) {
            addCriterion("price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<Integer> values) {
            addCriterion("price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<Integer> values) {
            addCriterion("price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(Integer value1, Integer value2) {
            addCriterion("price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(Integer value1, Integer value2) {
            addCriterion("price not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
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