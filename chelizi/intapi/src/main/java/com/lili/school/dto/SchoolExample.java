package com.lili.school.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SchoolExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SchoolExample() {
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

        public Criteria andCoachCountIsNull() {
            addCriterion("coachCount is null");
            return (Criteria) this;
        }

        public Criteria andCoachCountIsNotNull() {
            addCriterion("coachCount is not null");
            return (Criteria) this;
        }

        public Criteria andCoachCountEqualTo(Integer value) {
            addCriterion("coachCount =", value, "coachCount");
            return (Criteria) this;
        }

        public Criteria andCoachCountNotEqualTo(Integer value) {
            addCriterion("coachCount <>", value, "coachCount");
            return (Criteria) this;
        }

        public Criteria andCoachCountGreaterThan(Integer value) {
            addCriterion("coachCount >", value, "coachCount");
            return (Criteria) this;
        }

        public Criteria andCoachCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("coachCount >=", value, "coachCount");
            return (Criteria) this;
        }

        public Criteria andCoachCountLessThan(Integer value) {
            addCriterion("coachCount <", value, "coachCount");
            return (Criteria) this;
        }

        public Criteria andCoachCountLessThanOrEqualTo(Integer value) {
            addCriterion("coachCount <=", value, "coachCount");
            return (Criteria) this;
        }

        public Criteria andCoachCountIn(List<Integer> values) {
            addCriterion("coachCount in", values, "coachCount");
            return (Criteria) this;
        }

        public Criteria andCoachCountNotIn(List<Integer> values) {
            addCriterion("coachCount not in", values, "coachCount");
            return (Criteria) this;
        }

        public Criteria andCoachCountBetween(Integer value1, Integer value2) {
            addCriterion("coachCount between", value1, value2, "coachCount");
            return (Criteria) this;
        }

        public Criteria andCoachCountNotBetween(Integer value1, Integer value2) {
            addCriterion("coachCount not between", value1, value2, "coachCount");
            return (Criteria) this;
        }

        public Criteria andCarCountIsNull() {
            addCriterion("carCount is null");
            return (Criteria) this;
        }

        public Criteria andCarCountIsNotNull() {
            addCriterion("carCount is not null");
            return (Criteria) this;
        }

        public Criteria andCarCountEqualTo(Integer value) {
            addCriterion("carCount =", value, "carCount");
            return (Criteria) this;
        }

        public Criteria andCarCountNotEqualTo(Integer value) {
            addCriterion("carCount <>", value, "carCount");
            return (Criteria) this;
        }

        public Criteria andCarCountGreaterThan(Integer value) {
            addCriterion("carCount >", value, "carCount");
            return (Criteria) this;
        }

        public Criteria andCarCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("carCount >=", value, "carCount");
            return (Criteria) this;
        }

        public Criteria andCarCountLessThan(Integer value) {
            addCriterion("carCount <", value, "carCount");
            return (Criteria) this;
        }

        public Criteria andCarCountLessThanOrEqualTo(Integer value) {
            addCriterion("carCount <=", value, "carCount");
            return (Criteria) this;
        }

        public Criteria andCarCountIn(List<Integer> values) {
            addCriterion("carCount in", values, "carCount");
            return (Criteria) this;
        }

        public Criteria andCarCountNotIn(List<Integer> values) {
            addCriterion("carCount not in", values, "carCount");
            return (Criteria) this;
        }

        public Criteria andCarCountBetween(Integer value1, Integer value2) {
            addCriterion("carCount between", value1, value2, "carCount");
            return (Criteria) this;
        }

        public Criteria andCarCountNotBetween(Integer value1, Integer value2) {
            addCriterion("carCount not between", value1, value2, "carCount");
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

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andPhoneNumIsNull() {
            addCriterion("phoneNum is null");
            return (Criteria) this;
        }

        public Criteria andPhoneNumIsNotNull() {
            addCriterion("phoneNum is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneNumEqualTo(String value) {
            addCriterion("phoneNum =", value, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumNotEqualTo(String value) {
            addCriterion("phoneNum <>", value, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumGreaterThan(String value) {
            addCriterion("phoneNum >", value, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumGreaterThanOrEqualTo(String value) {
            addCriterion("phoneNum >=", value, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumLessThan(String value) {
            addCriterion("phoneNum <", value, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumLessThanOrEqualTo(String value) {
            addCriterion("phoneNum <=", value, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumLike(String value) {
            addCriterion("phoneNum like", value, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumNotLike(String value) {
            addCriterion("phoneNum not like", value, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumIn(List<String> values) {
            addCriterion("phoneNum in", values, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumNotIn(List<String> values) {
            addCriterion("phoneNum not in", values, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumBetween(String value1, String value2) {
            addCriterion("phoneNum between", value1, value2, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumNotBetween(String value1, String value2) {
            addCriterion("phoneNum not between", value1, value2, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andJstIdIsNull() {
            addCriterion("jstId is null");
            return (Criteria) this;
        }

        public Criteria andJstIdIsNotNull() {
            addCriterion("jstId is not null");
            return (Criteria) this;
        }

        public Criteria andJstIdEqualTo(String value) {
            addCriterion("jstId =", value, "jstId");
            return (Criteria) this;
        }

        public Criteria andJstIdNotEqualTo(String value) {
            addCriterion("jstId <>", value, "jstId");
            return (Criteria) this;
        }

        public Criteria andJstIdGreaterThan(String value) {
            addCriterion("jstId >", value, "jstId");
            return (Criteria) this;
        }

        public Criteria andJstIdGreaterThanOrEqualTo(String value) {
            addCriterion("jstId >=", value, "jstId");
            return (Criteria) this;
        }

        public Criteria andJstIdLessThan(String value) {
            addCriterion("jstId <", value, "jstId");
            return (Criteria) this;
        }

        public Criteria andJstIdLessThanOrEqualTo(String value) {
            addCriterion("jstId <=", value, "jstId");
            return (Criteria) this;
        }

        public Criteria andJstIdLike(String value) {
            addCriterion("jstId like", value, "jstId");
            return (Criteria) this;
        }

        public Criteria andJstIdNotLike(String value) {
            addCriterion("jstId not like", value, "jstId");
            return (Criteria) this;
        }

        public Criteria andJstIdIn(List<String> values) {
            addCriterion("jstId in", values, "jstId");
            return (Criteria) this;
        }

        public Criteria andJstIdNotIn(List<String> values) {
            addCriterion("jstId not in", values, "jstId");
            return (Criteria) this;
        }

        public Criteria andJstIdBetween(String value1, String value2) {
            addCriterion("jstId between", value1, value2, "jstId");
            return (Criteria) this;
        }

        public Criteria andJstIdNotBetween(String value1, String value2) {
            addCriterion("jstId not between", value1, value2, "jstId");
            return (Criteria) this;
        }

        public Criteria andTellIsNull() {
            addCriterion("tell is null");
            return (Criteria) this;
        }

        public Criteria andTellIsNotNull() {
            addCriterion("tell is not null");
            return (Criteria) this;
        }

        public Criteria andTellEqualTo(String value) {
            addCriterion("tell =", value, "tell");
            return (Criteria) this;
        }

        public Criteria andTellNotEqualTo(String value) {
            addCriterion("tell <>", value, "tell");
            return (Criteria) this;
        }

        public Criteria andTellGreaterThan(String value) {
            addCriterion("tell >", value, "tell");
            return (Criteria) this;
        }

        public Criteria andTellGreaterThanOrEqualTo(String value) {
            addCriterion("tell >=", value, "tell");
            return (Criteria) this;
        }

        public Criteria andTellLessThan(String value) {
            addCriterion("tell <", value, "tell");
            return (Criteria) this;
        }

        public Criteria andTellLessThanOrEqualTo(String value) {
            addCriterion("tell <=", value, "tell");
            return (Criteria) this;
        }

        public Criteria andTellLike(String value) {
            addCriterion("tell like", value, "tell");
            return (Criteria) this;
        }

        public Criteria andTellNotLike(String value) {
            addCriterion("tell not like", value, "tell");
            return (Criteria) this;
        }

        public Criteria andTellIn(List<String> values) {
            addCriterion("tell in", values, "tell");
            return (Criteria) this;
        }

        public Criteria andTellNotIn(List<String> values) {
            addCriterion("tell not in", values, "tell");
            return (Criteria) this;
        }

        public Criteria andTellBetween(String value1, String value2) {
            addCriterion("tell between", value1, value2, "tell");
            return (Criteria) this;
        }

        public Criteria andTellNotBetween(String value1, String value2) {
            addCriterion("tell not between", value1, value2, "tell");
            return (Criteria) this;
        }

        public Criteria andScoreIsNull() {
            addCriterion("score is null");
            return (Criteria) this;
        }

        public Criteria andScoreIsNotNull() {
            addCriterion("score is not null");
            return (Criteria) this;
        }

        public Criteria andScoreEqualTo(Double value) {
            addCriterion("score =", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotEqualTo(Double value) {
            addCriterion("score <>", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThan(Double value) {
            addCriterion("score >", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThanOrEqualTo(Double value) {
            addCriterion("score >=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThan(Double value) {
            addCriterion("score <", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThanOrEqualTo(Double value) {
            addCriterion("score <=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreIn(List<Double> values) {
            addCriterion("score in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotIn(List<Double> values) {
            addCriterion("score not in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreBetween(Double value1, Double value2) {
            addCriterion("score between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotBetween(Double value1, Double value2) {
            addCriterion("score not between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andLogoIsNull() {
            addCriterion("logo is null");
            return (Criteria) this;
        }

        public Criteria andLogoIsNotNull() {
            addCriterion("logo is not null");
            return (Criteria) this;
        }

        public Criteria andLogoEqualTo(String value) {
            addCriterion("logo =", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotEqualTo(String value) {
            addCriterion("logo <>", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoGreaterThan(String value) {
            addCriterion("logo >", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoGreaterThanOrEqualTo(String value) {
            addCriterion("logo >=", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoLessThan(String value) {
            addCriterion("logo <", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoLessThanOrEqualTo(String value) {
            addCriterion("logo <=", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoLike(String value) {
            addCriterion("logo like", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotLike(String value) {
            addCriterion("logo not like", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoIn(List<String> values) {
            addCriterion("logo in", values, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotIn(List<String> values) {
            addCriterion("logo not in", values, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoBetween(String value1, String value2) {
            addCriterion("logo between", value1, value2, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotBetween(String value1, String value2) {
            addCriterion("logo not between", value1, value2, "logo");
            return (Criteria) this;
        }

        public Criteria andIconIsNull() {
            addCriterion("icon is null");
            return (Criteria) this;
        }

        public Criteria andIconIsNotNull() {
            addCriterion("icon is not null");
            return (Criteria) this;
        }

        public Criteria andIconEqualTo(String value) {
            addCriterion("icon =", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconNotEqualTo(String value) {
            addCriterion("icon <>", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconGreaterThan(String value) {
            addCriterion("icon >", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconGreaterThanOrEqualTo(String value) {
            addCriterion("icon >=", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconLessThan(String value) {
            addCriterion("icon <", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconLessThanOrEqualTo(String value) {
            addCriterion("icon <=", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconLike(String value) {
            addCriterion("icon like", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconNotLike(String value) {
            addCriterion("icon not like", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconIn(List<String> values) {
            addCriterion("icon in", values, "icon");
            return (Criteria) this;
        }

        public Criteria andIconNotIn(List<String> values) {
            addCriterion("icon not in", values, "icon");
            return (Criteria) this;
        }

        public Criteria andIconBetween(String value1, String value2) {
            addCriterion("icon between", value1, value2, "icon");
            return (Criteria) this;
        }

        public Criteria andIconNotBetween(String value1, String value2) {
            addCriterion("icon not between", value1, value2, "icon");
            return (Criteria) this;
        }

        public Criteria andTrfieldCountIsNull() {
            addCriterion("trfieldCount is null");
            return (Criteria) this;
        }

        public Criteria andTrfieldCountIsNotNull() {
            addCriterion("trfieldCount is not null");
            return (Criteria) this;
        }

        public Criteria andTrfieldCountEqualTo(Integer value) {
            addCriterion("trfieldCount =", value, "trfieldCount");
            return (Criteria) this;
        }

        public Criteria andTrfieldCountNotEqualTo(Integer value) {
            addCriterion("trfieldCount <>", value, "trfieldCount");
            return (Criteria) this;
        }

        public Criteria andTrfieldCountGreaterThan(Integer value) {
            addCriterion("trfieldCount >", value, "trfieldCount");
            return (Criteria) this;
        }

        public Criteria andTrfieldCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("trfieldCount >=", value, "trfieldCount");
            return (Criteria) this;
        }

        public Criteria andTrfieldCountLessThan(Integer value) {
            addCriterion("trfieldCount <", value, "trfieldCount");
            return (Criteria) this;
        }

        public Criteria andTrfieldCountLessThanOrEqualTo(Integer value) {
            addCriterion("trfieldCount <=", value, "trfieldCount");
            return (Criteria) this;
        }

        public Criteria andTrfieldCountIn(List<Integer> values) {
            addCriterion("trfieldCount in", values, "trfieldCount");
            return (Criteria) this;
        }

        public Criteria andTrfieldCountNotIn(List<Integer> values) {
            addCriterion("trfieldCount not in", values, "trfieldCount");
            return (Criteria) this;
        }

        public Criteria andTrfieldCountBetween(Integer value1, Integer value2) {
            addCriterion("trfieldCount between", value1, value2, "trfieldCount");
            return (Criteria) this;
        }

        public Criteria andTrfieldCountNotBetween(Integer value1, Integer value2) {
            addCriterion("trfieldCount not between", value1, value2, "trfieldCount");
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

        public Criteria andOrderNumIsNull() {
            addCriterion("orderNum is null");
            return (Criteria) this;
        }

        public Criteria andOrderNumIsNotNull() {
            addCriterion("orderNum is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNumEqualTo(Integer value) {
            addCriterion("orderNum =", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotEqualTo(Integer value) {
            addCriterion("orderNum <>", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumGreaterThan(Integer value) {
            addCriterion("orderNum >", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("orderNum >=", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumLessThan(Integer value) {
            addCriterion("orderNum <", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumLessThanOrEqualTo(Integer value) {
            addCriterion("orderNum <=", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumIn(List<Integer> values) {
            addCriterion("orderNum in", values, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotIn(List<Integer> values) {
            addCriterion("orderNum not in", values, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumBetween(Integer value1, Integer value2) {
            addCriterion("orderNum between", value1, value2, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotBetween(Integer value1, Integer value2) {
            addCriterion("orderNum not between", value1, value2, "orderNum");
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

        public Criteria andStoreCountIsNull() {
            addCriterion("storeCount is null");
            return (Criteria) this;
        }

        public Criteria andStoreCountIsNotNull() {
            addCriterion("storeCount is not null");
            return (Criteria) this;
        }

        public Criteria andStoreCountEqualTo(Integer value) {
            addCriterion("storeCount =", value, "storeCount");
            return (Criteria) this;
        }

        public Criteria andStoreCountNotEqualTo(Integer value) {
            addCriterion("storeCount <>", value, "storeCount");
            return (Criteria) this;
        }

        public Criteria andStoreCountGreaterThan(Integer value) {
            addCriterion("storeCount >", value, "storeCount");
            return (Criteria) this;
        }

        public Criteria andStoreCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("storeCount >=", value, "storeCount");
            return (Criteria) this;
        }

        public Criteria andStoreCountLessThan(Integer value) {
            addCriterion("storeCount <", value, "storeCount");
            return (Criteria) this;
        }

        public Criteria andStoreCountLessThanOrEqualTo(Integer value) {
            addCriterion("storeCount <=", value, "storeCount");
            return (Criteria) this;
        }

        public Criteria andStoreCountIn(List<Integer> values) {
            addCriterion("storeCount in", values, "storeCount");
            return (Criteria) this;
        }

        public Criteria andStoreCountNotIn(List<Integer> values) {
            addCriterion("storeCount not in", values, "storeCount");
            return (Criteria) this;
        }

        public Criteria andStoreCountBetween(Integer value1, Integer value2) {
            addCriterion("storeCount between", value1, value2, "storeCount");
            return (Criteria) this;
        }

        public Criteria andStoreCountNotBetween(Integer value1, Integer value2) {
            addCriterion("storeCount not between", value1, value2, "storeCount");
            return (Criteria) this;
        }

        public Criteria andPackageCountIsNull() {
            addCriterion("packageCount is null");
            return (Criteria) this;
        }

        public Criteria andPackageCountIsNotNull() {
            addCriterion("packageCount is not null");
            return (Criteria) this;
        }

        public Criteria andPackageCountEqualTo(Integer value) {
            addCriterion("packageCount =", value, "packageCount");
            return (Criteria) this;
        }

        public Criteria andPackageCountNotEqualTo(Integer value) {
            addCriterion("packageCount <>", value, "packageCount");
            return (Criteria) this;
        }

        public Criteria andPackageCountGreaterThan(Integer value) {
            addCriterion("packageCount >", value, "packageCount");
            return (Criteria) this;
        }

        public Criteria andPackageCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("packageCount >=", value, "packageCount");
            return (Criteria) this;
        }

        public Criteria andPackageCountLessThan(Integer value) {
            addCriterion("packageCount <", value, "packageCount");
            return (Criteria) this;
        }

        public Criteria andPackageCountLessThanOrEqualTo(Integer value) {
            addCriterion("packageCount <=", value, "packageCount");
            return (Criteria) this;
        }

        public Criteria andPackageCountIn(List<Integer> values) {
            addCriterion("packageCount in", values, "packageCount");
            return (Criteria) this;
        }

        public Criteria andPackageCountNotIn(List<Integer> values) {
            addCriterion("packageCount not in", values, "packageCount");
            return (Criteria) this;
        }

        public Criteria andPackageCountBetween(Integer value1, Integer value2) {
            addCriterion("packageCount between", value1, value2, "packageCount");
            return (Criteria) this;
        }

        public Criteria andPackageCountNotBetween(Integer value1, Integer value2) {
            addCriterion("packageCount not between", value1, value2, "packageCount");
            return (Criteria) this;
        }

        public Criteria andShowStateIsNull() {
            addCriterion("showState is null");
            return (Criteria) this;
        }

        public Criteria andShowStateIsNotNull() {
            addCriterion("showState is not null");
            return (Criteria) this;
        }

        public Criteria andShowStateEqualTo(Integer value) {
            addCriterion("showState =", value, "showState");
            return (Criteria) this;
        }

        public Criteria andShowStateNotEqualTo(Integer value) {
            addCriterion("showState <>", value, "showState");
            return (Criteria) this;
        }

        public Criteria andShowStateGreaterThan(Integer value) {
            addCriterion("showState >", value, "showState");
            return (Criteria) this;
        }

        public Criteria andShowStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("showState >=", value, "showState");
            return (Criteria) this;
        }

        public Criteria andShowStateLessThan(Integer value) {
            addCriterion("showState <", value, "showState");
            return (Criteria) this;
        }

        public Criteria andShowStateLessThanOrEqualTo(Integer value) {
            addCriterion("showState <=", value, "showState");
            return (Criteria) this;
        }

        public Criteria andShowStateIn(List<Integer> values) {
            addCriterion("showState in", values, "showState");
            return (Criteria) this;
        }

        public Criteria andShowStateNotIn(List<Integer> values) {
            addCriterion("showState not in", values, "showState");
            return (Criteria) this;
        }

        public Criteria andShowStateBetween(Integer value1, Integer value2) {
            addCriterion("showState between", value1, value2, "showState");
            return (Criteria) this;
        }

        public Criteria andShowStateNotBetween(Integer value1, Integer value2) {
            addCriterion("showState not between", value1, value2, "showState");
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