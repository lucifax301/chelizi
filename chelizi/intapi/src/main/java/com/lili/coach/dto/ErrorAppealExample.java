package com.lili.coach.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ErrorAppealExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ErrorAppealExample() {
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

        public Criteria andAppealContentIsNull() {
            addCriterion("appealContent is null");
            return (Criteria) this;
        }

        public Criteria andAppealContentIsNotNull() {
            addCriterion("appealContent is not null");
            return (Criteria) this;
        }

        public Criteria andAppealContentEqualTo(String value) {
            addCriterion("appealContent =", value, "appealContent");
            return (Criteria) this;
        }

        public Criteria andAppealContentNotEqualTo(String value) {
            addCriterion("appealContent <>", value, "appealContent");
            return (Criteria) this;
        }

        public Criteria andAppealContentGreaterThan(String value) {
            addCriterion("appealContent >", value, "appealContent");
            return (Criteria) this;
        }

        public Criteria andAppealContentGreaterThanOrEqualTo(String value) {
            addCriterion("appealContent >=", value, "appealContent");
            return (Criteria) this;
        }

        public Criteria andAppealContentLessThan(String value) {
            addCriterion("appealContent <", value, "appealContent");
            return (Criteria) this;
        }

        public Criteria andAppealContentLessThanOrEqualTo(String value) {
            addCriterion("appealContent <=", value, "appealContent");
            return (Criteria) this;
        }

        public Criteria andAppealContentLike(String value) {
            addCriterion("appealContent like", value, "appealContent");
            return (Criteria) this;
        }

        public Criteria andAppealContentNotLike(String value) {
            addCriterion("appealContent not like", value, "appealContent");
            return (Criteria) this;
        }

        public Criteria andAppealContentIn(List<String> values) {
            addCriterion("appealContent in", values, "appealContent");
            return (Criteria) this;
        }

        public Criteria andAppealContentNotIn(List<String> values) {
            addCriterion("appealContent not in", values, "appealContent");
            return (Criteria) this;
        }

        public Criteria andAppealContentBetween(String value1, String value2) {
            addCriterion("appealContent between", value1, value2, "appealContent");
            return (Criteria) this;
        }

        public Criteria andAppealContentNotBetween(String value1, String value2) {
            addCriterion("appealContent not between", value1, value2, "appealContent");
            return (Criteria) this;
        }

        public Criteria andItemIdsIsNull() {
            addCriterion("itemIds is null");
            return (Criteria) this;
        }

        public Criteria andItemIdsIsNotNull() {
            addCriterion("itemIds is not null");
            return (Criteria) this;
        }

        public Criteria andItemIdsEqualTo(String value) {
            addCriterion("itemIds =", value, "itemIds");
            return (Criteria) this;
        }

        public Criteria andItemIdsNotEqualTo(String value) {
            addCriterion("itemIds <>", value, "itemIds");
            return (Criteria) this;
        }

        public Criteria andItemIdsGreaterThan(String value) {
            addCriterion("itemIds >", value, "itemIds");
            return (Criteria) this;
        }

        public Criteria andItemIdsGreaterThanOrEqualTo(String value) {
            addCriterion("itemIds >=", value, "itemIds");
            return (Criteria) this;
        }

        public Criteria andItemIdsLessThan(String value) {
            addCriterion("itemIds <", value, "itemIds");
            return (Criteria) this;
        }

        public Criteria andItemIdsLessThanOrEqualTo(String value) {
            addCriterion("itemIds <=", value, "itemIds");
            return (Criteria) this;
        }

        public Criteria andItemIdsLike(String value) {
            addCriterion("itemIds like", value, "itemIds");
            return (Criteria) this;
        }

        public Criteria andItemIdsNotLike(String value) {
            addCriterion("itemIds not like", value, "itemIds");
            return (Criteria) this;
        }

        public Criteria andItemIdsIn(List<String> values) {
            addCriterion("itemIds in", values, "itemIds");
            return (Criteria) this;
        }

        public Criteria andItemIdsNotIn(List<String> values) {
            addCriterion("itemIds not in", values, "itemIds");
            return (Criteria) this;
        }

        public Criteria andItemIdsBetween(String value1, String value2) {
            addCriterion("itemIds between", value1, value2, "itemIds");
            return (Criteria) this;
        }

        public Criteria andItemIdsNotBetween(String value1, String value2) {
            addCriterion("itemIds not between", value1, value2, "itemIds");
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

        public Criteria andCheckStatusIsNull() {
            addCriterion("checkStatus is null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIsNotNull() {
            addCriterion("checkStatus is not null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusEqualTo(Byte value) {
            addCriterion("checkStatus =", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotEqualTo(Byte value) {
            addCriterion("checkStatus <>", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThan(Byte value) {
            addCriterion("checkStatus >", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("checkStatus >=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThan(Byte value) {
            addCriterion("checkStatus <", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThanOrEqualTo(Byte value) {
            addCriterion("checkStatus <=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIn(List<Byte> values) {
            addCriterion("checkStatus in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotIn(List<Byte> values) {
            addCriterion("checkStatus not in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusBetween(Byte value1, Byte value2) {
            addCriterion("checkStatus between", value1, value2, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("checkStatus not between", value1, value2, "checkStatus");
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