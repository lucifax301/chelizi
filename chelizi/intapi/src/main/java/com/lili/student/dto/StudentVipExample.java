package com.lili.student.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentVipExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public StudentVipExample() {
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

        public Criteria andVipIdIsNull() {
            addCriterion("vipId is null");
            return (Criteria) this;
        }

        public Criteria andVipIdIsNotNull() {
            addCriterion("vipId is not null");
            return (Criteria) this;
        }

        public Criteria andVipIdEqualTo(Integer value) {
            addCriterion("vipId =", value, "vipId");
            return (Criteria) this;
        }

        public Criteria andVipIdNotEqualTo(Integer value) {
            addCriterion("vipId <>", value, "vipId");
            return (Criteria) this;
        }

        public Criteria andVipIdGreaterThan(Integer value) {
            addCriterion("vipId >", value, "vipId");
            return (Criteria) this;
        }

        public Criteria andVipIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("vipId >=", value, "vipId");
            return (Criteria) this;
        }

        public Criteria andVipIdLessThan(Integer value) {
            addCriterion("vipId <", value, "vipId");
            return (Criteria) this;
        }

        public Criteria andVipIdLessThanOrEqualTo(Integer value) {
            addCriterion("vipId <=", value, "vipId");
            return (Criteria) this;
        }

        public Criteria andVipIdIn(List<Integer> values) {
            addCriterion("vipId in", values, "vipId");
            return (Criteria) this;
        }

        public Criteria andVipIdNotIn(List<Integer> values) {
            addCriterion("vipId not in", values, "vipId");
            return (Criteria) this;
        }

        public Criteria andVipIdBetween(Integer value1, Integer value2) {
            addCriterion("vipId between", value1, value2, "vipId");
            return (Criteria) this;
        }

        public Criteria andVipIdNotBetween(Integer value1, Integer value2) {
            addCriterion("vipId not between", value1, value2, "vipId");
            return (Criteria) this;
        }

        public Criteria andVipNameIsNull() {
            addCriterion("vipName is null");
            return (Criteria) this;
        }

        public Criteria andVipNameIsNotNull() {
            addCriterion("vipName is not null");
            return (Criteria) this;
        }

        public Criteria andVipNameEqualTo(String value) {
            addCriterion("vipName =", value, "vipName");
            return (Criteria) this;
        }

        public Criteria andVipNameNotEqualTo(String value) {
            addCriterion("vipName <>", value, "vipName");
            return (Criteria) this;
        }

        public Criteria andVipNameGreaterThan(String value) {
            addCriterion("vipName >", value, "vipName");
            return (Criteria) this;
        }

        public Criteria andVipNameGreaterThanOrEqualTo(String value) {
            addCriterion("vipName >=", value, "vipName");
            return (Criteria) this;
        }

        public Criteria andVipNameLessThan(String value) {
            addCriterion("vipName <", value, "vipName");
            return (Criteria) this;
        }

        public Criteria andVipNameLessThanOrEqualTo(String value) {
            addCriterion("vipName <=", value, "vipName");
            return (Criteria) this;
        }

        public Criteria andVipNameLike(String value) {
            addCriterion("vipName like", value, "vipName");
            return (Criteria) this;
        }

        public Criteria andVipNameNotLike(String value) {
            addCriterion("vipName not like", value, "vipName");
            return (Criteria) this;
        }

        public Criteria andVipNameIn(List<String> values) {
            addCriterion("vipName in", values, "vipName");
            return (Criteria) this;
        }

        public Criteria andVipNameNotIn(List<String> values) {
            addCriterion("vipName not in", values, "vipName");
            return (Criteria) this;
        }

        public Criteria andVipNameBetween(String value1, String value2) {
            addCriterion("vipName between", value1, value2, "vipName");
            return (Criteria) this;
        }

        public Criteria andVipNameNotBetween(String value1, String value2) {
            addCriterion("vipName not between", value1, value2, "vipName");
            return (Criteria) this;
        }

        public Criteria andVipNickNameIsNull() {
            addCriterion("vipNickName is null");
            return (Criteria) this;
        }

        public Criteria andVipNickNameIsNotNull() {
            addCriterion("vipNickName is not null");
            return (Criteria) this;
        }

        public Criteria andVipNickNameEqualTo(String value) {
            addCriterion("vipNickName =", value, "vipNickName");
            return (Criteria) this;
        }

        public Criteria andVipNickNameNotEqualTo(String value) {
            addCriterion("vipNickName <>", value, "vipNickName");
            return (Criteria) this;
        }

        public Criteria andVipNickNameGreaterThan(String value) {
            addCriterion("vipNickName >", value, "vipNickName");
            return (Criteria) this;
        }

        public Criteria andVipNickNameGreaterThanOrEqualTo(String value) {
            addCriterion("vipNickName >=", value, "vipNickName");
            return (Criteria) this;
        }

        public Criteria andVipNickNameLessThan(String value) {
            addCriterion("vipNickName <", value, "vipNickName");
            return (Criteria) this;
        }

        public Criteria andVipNickNameLessThanOrEqualTo(String value) {
            addCriterion("vipNickName <=", value, "vipNickName");
            return (Criteria) this;
        }

        public Criteria andVipNickNameLike(String value) {
            addCriterion("vipNickName like", value, "vipNickName");
            return (Criteria) this;
        }

        public Criteria andVipNickNameNotLike(String value) {
            addCriterion("vipNickName not like", value, "vipNickName");
            return (Criteria) this;
        }

        public Criteria andVipNickNameIn(List<String> values) {
            addCriterion("vipNickName in", values, "vipNickName");
            return (Criteria) this;
        }

        public Criteria andVipNickNameNotIn(List<String> values) {
            addCriterion("vipNickName not in", values, "vipNickName");
            return (Criteria) this;
        }

        public Criteria andVipNickNameBetween(String value1, String value2) {
            addCriterion("vipNickName between", value1, value2, "vipNickName");
            return (Criteria) this;
        }

        public Criteria andVipNickNameNotBetween(String value1, String value2) {
            addCriterion("vipNickName not between", value1, value2, "vipNickName");
            return (Criteria) this;
        }

        public Criteria andVipCustomerManagerIsNull() {
            addCriterion("vipCustomerManager is null");
            return (Criteria) this;
        }

        public Criteria andVipCustomerManagerIsNotNull() {
            addCriterion("vipCustomerManager is not null");
            return (Criteria) this;
        }

        public Criteria andVipCustomerManagerEqualTo(String value) {
            addCriterion("vipCustomerManager =", value, "vipCustomerManager");
            return (Criteria) this;
        }

        public Criteria andVipCustomerManagerNotEqualTo(String value) {
            addCriterion("vipCustomerManager <>", value, "vipCustomerManager");
            return (Criteria) this;
        }

        public Criteria andVipCustomerManagerGreaterThan(String value) {
            addCriterion("vipCustomerManager >", value, "vipCustomerManager");
            return (Criteria) this;
        }

        public Criteria andVipCustomerManagerGreaterThanOrEqualTo(String value) {
            addCriterion("vipCustomerManager >=", value, "vipCustomerManager");
            return (Criteria) this;
        }

        public Criteria andVipCustomerManagerLessThan(String value) {
            addCriterion("vipCustomerManager <", value, "vipCustomerManager");
            return (Criteria) this;
        }

        public Criteria andVipCustomerManagerLessThanOrEqualTo(String value) {
            addCriterion("vipCustomerManager <=", value, "vipCustomerManager");
            return (Criteria) this;
        }

        public Criteria andVipCustomerManagerLike(String value) {
            addCriterion("vipCustomerManager like", value, "vipCustomerManager");
            return (Criteria) this;
        }

        public Criteria andVipCustomerManagerNotLike(String value) {
            addCriterion("vipCustomerManager not like", value, "vipCustomerManager");
            return (Criteria) this;
        }

        public Criteria andVipCustomerManagerIn(List<String> values) {
            addCriterion("vipCustomerManager in", values, "vipCustomerManager");
            return (Criteria) this;
        }

        public Criteria andVipCustomerManagerNotIn(List<String> values) {
            addCriterion("vipCustomerManager not in", values, "vipCustomerManager");
            return (Criteria) this;
        }

        public Criteria andVipCustomerManagerBetween(String value1, String value2) {
            addCriterion("vipCustomerManager between", value1, value2, "vipCustomerManager");
            return (Criteria) this;
        }

        public Criteria andVipCustomerManagerNotBetween(String value1, String value2) {
            addCriterion("vipCustomerManager not between", value1, value2, "vipCustomerManager");
            return (Criteria) this;
        }

        public Criteria andVipContactPhoneIsNull() {
            addCriterion("vipContactPhone is null");
            return (Criteria) this;
        }

        public Criteria andVipContactPhoneIsNotNull() {
            addCriterion("vipContactPhone is not null");
            return (Criteria) this;
        }

        public Criteria andVipContactPhoneEqualTo(String value) {
            addCriterion("vipContactPhone =", value, "vipContactPhone");
            return (Criteria) this;
        }

        public Criteria andVipContactPhoneNotEqualTo(String value) {
            addCriterion("vipContactPhone <>", value, "vipContactPhone");
            return (Criteria) this;
        }

        public Criteria andVipContactPhoneGreaterThan(String value) {
            addCriterion("vipContactPhone >", value, "vipContactPhone");
            return (Criteria) this;
        }

        public Criteria andVipContactPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("vipContactPhone >=", value, "vipContactPhone");
            return (Criteria) this;
        }

        public Criteria andVipContactPhoneLessThan(String value) {
            addCriterion("vipContactPhone <", value, "vipContactPhone");
            return (Criteria) this;
        }

        public Criteria andVipContactPhoneLessThanOrEqualTo(String value) {
            addCriterion("vipContactPhone <=", value, "vipContactPhone");
            return (Criteria) this;
        }

        public Criteria andVipContactPhoneLike(String value) {
            addCriterion("vipContactPhone like", value, "vipContactPhone");
            return (Criteria) this;
        }

        public Criteria andVipContactPhoneNotLike(String value) {
            addCriterion("vipContactPhone not like", value, "vipContactPhone");
            return (Criteria) this;
        }

        public Criteria andVipContactPhoneIn(List<String> values) {
            addCriterion("vipContactPhone in", values, "vipContactPhone");
            return (Criteria) this;
        }

        public Criteria andVipContactPhoneNotIn(List<String> values) {
            addCriterion("vipContactPhone not in", values, "vipContactPhone");
            return (Criteria) this;
        }

        public Criteria andVipContactPhoneBetween(String value1, String value2) {
            addCriterion("vipContactPhone between", value1, value2, "vipContactPhone");
            return (Criteria) this;
        }

        public Criteria andVipContactPhoneNotBetween(String value1, String value2) {
            addCriterion("vipContactPhone not between", value1, value2, "vipContactPhone");
            return (Criteria) this;
        }

        public Criteria andCitysIsNull() {
            addCriterion("citys is null");
            return (Criteria) this;
        }

        public Criteria andCitysIsNotNull() {
            addCriterion("citys is not null");
            return (Criteria) this;
        }

        public Criteria andCitysEqualTo(String value) {
            addCriterion("citys =", value, "citys");
            return (Criteria) this;
        }

        public Criteria andCitysNotEqualTo(String value) {
            addCriterion("citys <>", value, "citys");
            return (Criteria) this;
        }

        public Criteria andCitysGreaterThan(String value) {
            addCriterion("citys >", value, "citys");
            return (Criteria) this;
        }

        public Criteria andCitysGreaterThanOrEqualTo(String value) {
            addCriterion("citys >=", value, "citys");
            return (Criteria) this;
        }

        public Criteria andCitysLessThan(String value) {
            addCriterion("citys <", value, "citys");
            return (Criteria) this;
        }

        public Criteria andCitysLessThanOrEqualTo(String value) {
            addCriterion("citys <=", value, "citys");
            return (Criteria) this;
        }

        public Criteria andCitysLike(String value) {
            addCriterion("citys like", value, "citys");
            return (Criteria) this;
        }

        public Criteria andCitysNotLike(String value) {
            addCriterion("citys not like", value, "citys");
            return (Criteria) this;
        }

        public Criteria andCitysIn(List<String> values) {
            addCriterion("citys in", values, "citys");
            return (Criteria) this;
        }

        public Criteria andCitysNotIn(List<String> values) {
            addCriterion("citys not in", values, "citys");
            return (Criteria) this;
        }

        public Criteria andCitysBetween(String value1, String value2) {
            addCriterion("citys between", value1, value2, "citys");
            return (Criteria) this;
        }

        public Criteria andCitysNotBetween(String value1, String value2) {
            addCriterion("citys not between", value1, value2, "citys");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNull() {
            addCriterion("operator is null");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNotNull() {
            addCriterion("operator is not null");
            return (Criteria) this;
        }

        public Criteria andOperatorEqualTo(String value) {
            addCriterion("operator =", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotEqualTo(String value) {
            addCriterion("operator <>", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThan(String value) {
            addCriterion("operator >", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThanOrEqualTo(String value) {
            addCriterion("operator >=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThan(String value) {
            addCriterion("operator <", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThanOrEqualTo(String value) {
            addCriterion("operator <=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLike(String value) {
            addCriterion("operator like", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotLike(String value) {
            addCriterion("operator not like", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorIn(List<String> values) {
            addCriterion("operator in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotIn(List<String> values) {
            addCriterion("operator not in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorBetween(String value1, String value2) {
            addCriterion("operator between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotBetween(String value1, String value2) {
            addCriterion("operator not between", value1, value2, "operator");
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