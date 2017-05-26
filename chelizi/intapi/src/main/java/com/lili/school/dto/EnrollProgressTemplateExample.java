package com.lili.school.dto;

import java.util.ArrayList;
import java.util.List;

public class EnrollProgressTemplateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EnrollProgressTemplateExample() {
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

        public Criteria andCpidIsNull() {
            addCriterion("cpid is null");
            return (Criteria) this;
        }

        public Criteria andCpidIsNotNull() {
            addCriterion("cpid is not null");
            return (Criteria) this;
        }

        public Criteria andCpidEqualTo(Integer value) {
            addCriterion("cpid =", value, "cpid");
            return (Criteria) this;
        }

        public Criteria andCpidNotEqualTo(Integer value) {
            addCriterion("cpid <>", value, "cpid");
            return (Criteria) this;
        }

        public Criteria andCpidGreaterThan(Integer value) {
            addCriterion("cpid >", value, "cpid");
            return (Criteria) this;
        }

        public Criteria andCpidGreaterThanOrEqualTo(Integer value) {
            addCriterion("cpid >=", value, "cpid");
            return (Criteria) this;
        }

        public Criteria andCpidLessThan(Integer value) {
            addCriterion("cpid <", value, "cpid");
            return (Criteria) this;
        }

        public Criteria andCpidLessThanOrEqualTo(Integer value) {
            addCriterion("cpid <=", value, "cpid");
            return (Criteria) this;
        }

        public Criteria andCpidIn(List<Integer> values) {
            addCriterion("cpid in", values, "cpid");
            return (Criteria) this;
        }

        public Criteria andCpidNotIn(List<Integer> values) {
            addCriterion("cpid not in", values, "cpid");
            return (Criteria) this;
        }

        public Criteria andCpidBetween(Integer value1, Integer value2) {
            addCriterion("cpid between", value1, value2, "cpid");
            return (Criteria) this;
        }

        public Criteria andCpidNotBetween(Integer value1, Integer value2) {
            addCriterion("cpid not between", value1, value2, "cpid");
            return (Criteria) this;
        }

        public Criteria andTtidIsNull() {
            addCriterion("ttid is null");
            return (Criteria) this;
        }

        public Criteria andTtidIsNotNull() {
            addCriterion("ttid is not null");
            return (Criteria) this;
        }

        public Criteria andTtidEqualTo(Integer value) {
            addCriterion("ttid =", value, "ttid");
            return (Criteria) this;
        }

        public Criteria andTtidNotEqualTo(Integer value) {
            addCriterion("ttid <>", value, "ttid");
            return (Criteria) this;
        }

        public Criteria andTtidGreaterThan(Integer value) {
            addCriterion("ttid >", value, "ttid");
            return (Criteria) this;
        }

        public Criteria andTtidGreaterThanOrEqualTo(Integer value) {
            addCriterion("ttid >=", value, "ttid");
            return (Criteria) this;
        }

        public Criteria andTtidLessThan(Integer value) {
            addCriterion("ttid <", value, "ttid");
            return (Criteria) this;
        }

        public Criteria andTtidLessThanOrEqualTo(Integer value) {
            addCriterion("ttid <=", value, "ttid");
            return (Criteria) this;
        }

        public Criteria andTtidIn(List<Integer> values) {
            addCriterion("ttid in", values, "ttid");
            return (Criteria) this;
        }

        public Criteria andTtidNotIn(List<Integer> values) {
            addCriterion("ttid not in", values, "ttid");
            return (Criteria) this;
        }

        public Criteria andTtidBetween(Integer value1, Integer value2) {
            addCriterion("ttid between", value1, value2, "ttid");
            return (Criteria) this;
        }

        public Criteria andTtidNotBetween(Integer value1, Integer value2) {
            addCriterion("ttid not between", value1, value2, "ttid");
            return (Criteria) this;
        }

        public Criteria andCityIdIsNull() {
            addCriterion("city_id is null");
            return (Criteria) this;
        }

        public Criteria andCityIdIsNotNull() {
            addCriterion("city_id is not null");
            return (Criteria) this;
        }

        public Criteria andCityIdEqualTo(Integer value) {
            addCriterion("city_id =", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdNotEqualTo(Integer value) {
            addCriterion("city_id <>", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdGreaterThan(Integer value) {
            addCriterion("city_id >", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("city_id >=", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdLessThan(Integer value) {
            addCriterion("city_id <", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdLessThanOrEqualTo(Integer value) {
            addCriterion("city_id <=", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdIn(List<Integer> values) {
            addCriterion("city_id in", values, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdNotIn(List<Integer> values) {
            addCriterion("city_id not in", values, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdBetween(Integer value1, Integer value2) {
            addCriterion("city_id between", value1, value2, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdNotBetween(Integer value1, Integer value2) {
            addCriterion("city_id not between", value1, value2, "cityId");
            return (Criteria) this;
        }

        public Criteria andStepIdIsNull() {
            addCriterion("step_id is null");
            return (Criteria) this;
        }

        public Criteria andStepIdIsNotNull() {
            addCriterion("step_id is not null");
            return (Criteria) this;
        }

        public Criteria andStepIdEqualTo(Integer value) {
            addCriterion("step_id =", value, "stepId");
            return (Criteria) this;
        }

        public Criteria andStepIdNotEqualTo(Integer value) {
            addCriterion("step_id <>", value, "stepId");
            return (Criteria) this;
        }

        public Criteria andStepIdGreaterThan(Integer value) {
            addCriterion("step_id >", value, "stepId");
            return (Criteria) this;
        }

        public Criteria andStepIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("step_id >=", value, "stepId");
            return (Criteria) this;
        }

        public Criteria andStepIdLessThan(Integer value) {
            addCriterion("step_id <", value, "stepId");
            return (Criteria) this;
        }

        public Criteria andStepIdLessThanOrEqualTo(Integer value) {
            addCriterion("step_id <=", value, "stepId");
            return (Criteria) this;
        }

        public Criteria andStepIdIn(List<Integer> values) {
            addCriterion("step_id in", values, "stepId");
            return (Criteria) this;
        }

        public Criteria andStepIdNotIn(List<Integer> values) {
            addCriterion("step_id not in", values, "stepId");
            return (Criteria) this;
        }

        public Criteria andStepIdBetween(Integer value1, Integer value2) {
            addCriterion("step_id between", value1, value2, "stepId");
            return (Criteria) this;
        }

        public Criteria andStepIdNotBetween(Integer value1, Integer value2) {
            addCriterion("step_id not between", value1, value2, "stepId");
            return (Criteria) this;
        }

        public Criteria andStepTypeIsNull() {
            addCriterion("step_type is null");
            return (Criteria) this;
        }

        public Criteria andStepTypeIsNotNull() {
            addCriterion("step_type is not null");
            return (Criteria) this;
        }

        public Criteria andStepTypeEqualTo(Integer value) {
            addCriterion("step_type =", value, "stepType");
            return (Criteria) this;
        }

        public Criteria andStepTypeNotEqualTo(Integer value) {
            addCriterion("step_type <>", value, "stepType");
            return (Criteria) this;
        }

        public Criteria andStepTypeGreaterThan(Integer value) {
            addCriterion("step_type >", value, "stepType");
            return (Criteria) this;
        }

        public Criteria andStepTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("step_type >=", value, "stepType");
            return (Criteria) this;
        }

        public Criteria andStepTypeLessThan(Integer value) {
            addCriterion("step_type <", value, "stepType");
            return (Criteria) this;
        }

        public Criteria andStepTypeLessThanOrEqualTo(Integer value) {
            addCriterion("step_type <=", value, "stepType");
            return (Criteria) this;
        }

        public Criteria andStepTypeIn(List<Integer> values) {
            addCriterion("step_type in", values, "stepType");
            return (Criteria) this;
        }

        public Criteria andStepTypeNotIn(List<Integer> values) {
            addCriterion("step_type not in", values, "stepType");
            return (Criteria) this;
        }

        public Criteria andStepTypeBetween(Integer value1, Integer value2) {
            addCriterion("step_type between", value1, value2, "stepType");
            return (Criteria) this;
        }

        public Criteria andStepTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("step_type not between", value1, value2, "stepType");
            return (Criteria) this;
        }

        public Criteria andNextStepIsNull() {
            addCriterion("next_step is null");
            return (Criteria) this;
        }

        public Criteria andNextStepIsNotNull() {
            addCriterion("next_step is not null");
            return (Criteria) this;
        }

        public Criteria andNextStepEqualTo(Integer value) {
            addCriterion("next_step =", value, "nextStep");
            return (Criteria) this;
        }

        public Criteria andNextStepNotEqualTo(Integer value) {
            addCriterion("next_step <>", value, "nextStep");
            return (Criteria) this;
        }

        public Criteria andNextStepGreaterThan(Integer value) {
            addCriterion("next_step >", value, "nextStep");
            return (Criteria) this;
        }

        public Criteria andNextStepGreaterThanOrEqualTo(Integer value) {
            addCriterion("next_step >=", value, "nextStep");
            return (Criteria) this;
        }

        public Criteria andNextStepLessThan(Integer value) {
            addCriterion("next_step <", value, "nextStep");
            return (Criteria) this;
        }

        public Criteria andNextStepLessThanOrEqualTo(Integer value) {
            addCriterion("next_step <=", value, "nextStep");
            return (Criteria) this;
        }

        public Criteria andNextStepIn(List<Integer> values) {
            addCriterion("next_step in", values, "nextStep");
            return (Criteria) this;
        }

        public Criteria andNextStepNotIn(List<Integer> values) {
            addCriterion("next_step not in", values, "nextStep");
            return (Criteria) this;
        }

        public Criteria andNextStepBetween(Integer value1, Integer value2) {
            addCriterion("next_step between", value1, value2, "nextStep");
            return (Criteria) this;
        }

        public Criteria andNextStepNotBetween(Integer value1, Integer value2) {
            addCriterion("next_step not between", value1, value2, "nextStep");
            return (Criteria) this;
        }

        public Criteria andRetryStepIsNull() {
            addCriterion("retry_step is null");
            return (Criteria) this;
        }

        public Criteria andRetryStepIsNotNull() {
            addCriterion("retry_step is not null");
            return (Criteria) this;
        }

        public Criteria andRetryStepEqualTo(Integer value) {
            addCriterion("retry_step =", value, "retryStep");
            return (Criteria) this;
        }

        public Criteria andRetryStepNotEqualTo(Integer value) {
            addCriterion("retry_step <>", value, "retryStep");
            return (Criteria) this;
        }

        public Criteria andRetryStepGreaterThan(Integer value) {
            addCriterion("retry_step >", value, "retryStep");
            return (Criteria) this;
        }

        public Criteria andRetryStepGreaterThanOrEqualTo(Integer value) {
            addCriterion("retry_step >=", value, "retryStep");
            return (Criteria) this;
        }

        public Criteria andRetryStepLessThan(Integer value) {
            addCriterion("retry_step <", value, "retryStep");
            return (Criteria) this;
        }

        public Criteria andRetryStepLessThanOrEqualTo(Integer value) {
            addCriterion("retry_step <=", value, "retryStep");
            return (Criteria) this;
        }

        public Criteria andRetryStepIn(List<Integer> values) {
            addCriterion("retry_step in", values, "retryStep");
            return (Criteria) this;
        }

        public Criteria andRetryStepNotIn(List<Integer> values) {
            addCriterion("retry_step not in", values, "retryStep");
            return (Criteria) this;
        }

        public Criteria andRetryStepBetween(Integer value1, Integer value2) {
            addCriterion("retry_step between", value1, value2, "retryStep");
            return (Criteria) this;
        }

        public Criteria andRetryStepNotBetween(Integer value1, Integer value2) {
            addCriterion("retry_step not between", value1, value2, "retryStep");
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

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andDpageIsNull() {
            addCriterion("dpage is null");
            return (Criteria) this;
        }

        public Criteria andDpageIsNotNull() {
            addCriterion("dpage is not null");
            return (Criteria) this;
        }

        public Criteria andDpageEqualTo(String value) {
            addCriterion("dpage =", value, "dpage");
            return (Criteria) this;
        }

        public Criteria andDpageNotEqualTo(String value) {
            addCriterion("dpage <>", value, "dpage");
            return (Criteria) this;
        }

        public Criteria andDpageGreaterThan(String value) {
            addCriterion("dpage >", value, "dpage");
            return (Criteria) this;
        }

        public Criteria andDpageGreaterThanOrEqualTo(String value) {
            addCriterion("dpage >=", value, "dpage");
            return (Criteria) this;
        }

        public Criteria andDpageLessThan(String value) {
            addCriterion("dpage <", value, "dpage");
            return (Criteria) this;
        }

        public Criteria andDpageLessThanOrEqualTo(String value) {
            addCriterion("dpage <=", value, "dpage");
            return (Criteria) this;
        }

        public Criteria andDpageLike(String value) {
            addCriterion("dpage like", value, "dpage");
            return (Criteria) this;
        }

        public Criteria andDpageNotLike(String value) {
            addCriterion("dpage not like", value, "dpage");
            return (Criteria) this;
        }

        public Criteria andDpageIn(List<String> values) {
            addCriterion("dpage in", values, "dpage");
            return (Criteria) this;
        }

        public Criteria andDpageNotIn(List<String> values) {
            addCriterion("dpage not in", values, "dpage");
            return (Criteria) this;
        }

        public Criteria andDpageBetween(String value1, String value2) {
            addCriterion("dpage between", value1, value2, "dpage");
            return (Criteria) this;
        }

        public Criteria andDpageNotBetween(String value1, String value2) {
            addCriterion("dpage not between", value1, value2, "dpage");
            return (Criteria) this;
        }

        public Criteria andBpageIsNull() {
            addCriterion("bpage is null");
            return (Criteria) this;
        }

        public Criteria andBpageIsNotNull() {
            addCriterion("bpage is not null");
            return (Criteria) this;
        }

        public Criteria andBpageEqualTo(String value) {
            addCriterion("bpage =", value, "bpage");
            return (Criteria) this;
        }

        public Criteria andBpageNotEqualTo(String value) {
            addCriterion("bpage <>", value, "bpage");
            return (Criteria) this;
        }

        public Criteria andBpageGreaterThan(String value) {
            addCriterion("bpage >", value, "bpage");
            return (Criteria) this;
        }

        public Criteria andBpageGreaterThanOrEqualTo(String value) {
            addCriterion("bpage >=", value, "bpage");
            return (Criteria) this;
        }

        public Criteria andBpageLessThan(String value) {
            addCriterion("bpage <", value, "bpage");
            return (Criteria) this;
        }

        public Criteria andBpageLessThanOrEqualTo(String value) {
            addCriterion("bpage <=", value, "bpage");
            return (Criteria) this;
        }

        public Criteria andBpageLike(String value) {
            addCriterion("bpage like", value, "bpage");
            return (Criteria) this;
        }

        public Criteria andBpageNotLike(String value) {
            addCriterion("bpage not like", value, "bpage");
            return (Criteria) this;
        }

        public Criteria andBpageIn(List<String> values) {
            addCriterion("bpage in", values, "bpage");
            return (Criteria) this;
        }

        public Criteria andBpageNotIn(List<String> values) {
            addCriterion("bpage not in", values, "bpage");
            return (Criteria) this;
        }

        public Criteria andBpageBetween(String value1, String value2) {
            addCriterion("bpage between", value1, value2, "bpage");
            return (Criteria) this;
        }

        public Criteria andBpageNotBetween(String value1, String value2) {
            addCriterion("bpage not between", value1, value2, "bpage");
            return (Criteria) this;
        }

        public Criteria andPreDocIsNull() {
            addCriterion("pre_doc is null");
            return (Criteria) this;
        }

        public Criteria andPreDocIsNotNull() {
            addCriterion("pre_doc is not null");
            return (Criteria) this;
        }

        public Criteria andPreDocEqualTo(String value) {
            addCriterion("pre_doc =", value, "preDoc");
            return (Criteria) this;
        }

        public Criteria andPreDocNotEqualTo(String value) {
            addCriterion("pre_doc <>", value, "preDoc");
            return (Criteria) this;
        }

        public Criteria andPreDocGreaterThan(String value) {
            addCriterion("pre_doc >", value, "preDoc");
            return (Criteria) this;
        }

        public Criteria andPreDocGreaterThanOrEqualTo(String value) {
            addCriterion("pre_doc >=", value, "preDoc");
            return (Criteria) this;
        }

        public Criteria andPreDocLessThan(String value) {
            addCriterion("pre_doc <", value, "preDoc");
            return (Criteria) this;
        }

        public Criteria andPreDocLessThanOrEqualTo(String value) {
            addCriterion("pre_doc <=", value, "preDoc");
            return (Criteria) this;
        }

        public Criteria andPreDocLike(String value) {
            addCriterion("pre_doc like", value, "preDoc");
            return (Criteria) this;
        }

        public Criteria andPreDocNotLike(String value) {
            addCriterion("pre_doc not like", value, "preDoc");
            return (Criteria) this;
        }

        public Criteria andPreDocIn(List<String> values) {
            addCriterion("pre_doc in", values, "preDoc");
            return (Criteria) this;
        }

        public Criteria andPreDocNotIn(List<String> values) {
            addCriterion("pre_doc not in", values, "preDoc");
            return (Criteria) this;
        }

        public Criteria andPreDocBetween(String value1, String value2) {
            addCriterion("pre_doc between", value1, value2, "preDoc");
            return (Criteria) this;
        }

        public Criteria andPreDocNotBetween(String value1, String value2) {
            addCriterion("pre_doc not between", value1, value2, "preDoc");
            return (Criteria) this;
        }

        public Criteria andNoneDocIsNull() {
            addCriterion("none_doc is null");
            return (Criteria) this;
        }

        public Criteria andNoneDocIsNotNull() {
            addCriterion("none_doc is not null");
            return (Criteria) this;
        }

        public Criteria andNoneDocEqualTo(String value) {
            addCriterion("none_doc =", value, "noneDoc");
            return (Criteria) this;
        }

        public Criteria andNoneDocNotEqualTo(String value) {
            addCriterion("none_doc <>", value, "noneDoc");
            return (Criteria) this;
        }

        public Criteria andNoneDocGreaterThan(String value) {
            addCriterion("none_doc >", value, "noneDoc");
            return (Criteria) this;
        }

        public Criteria andNoneDocGreaterThanOrEqualTo(String value) {
            addCriterion("none_doc >=", value, "noneDoc");
            return (Criteria) this;
        }

        public Criteria andNoneDocLessThan(String value) {
            addCriterion("none_doc <", value, "noneDoc");
            return (Criteria) this;
        }

        public Criteria andNoneDocLessThanOrEqualTo(String value) {
            addCriterion("none_doc <=", value, "noneDoc");
            return (Criteria) this;
        }

        public Criteria andNoneDocLike(String value) {
            addCriterion("none_doc like", value, "noneDoc");
            return (Criteria) this;
        }

        public Criteria andNoneDocNotLike(String value) {
            addCriterion("none_doc not like", value, "noneDoc");
            return (Criteria) this;
        }

        public Criteria andNoneDocIn(List<String> values) {
            addCriterion("none_doc in", values, "noneDoc");
            return (Criteria) this;
        }

        public Criteria andNoneDocNotIn(List<String> values) {
            addCriterion("none_doc not in", values, "noneDoc");
            return (Criteria) this;
        }

        public Criteria andNoneDocBetween(String value1, String value2) {
            addCriterion("none_doc between", value1, value2, "noneDoc");
            return (Criteria) this;
        }

        public Criteria andNoneDocNotBetween(String value1, String value2) {
            addCriterion("none_doc not between", value1, value2, "noneDoc");
            return (Criteria) this;
        }

        public Criteria andSubmitDocIsNull() {
            addCriterion("submit_doc is null");
            return (Criteria) this;
        }

        public Criteria andSubmitDocIsNotNull() {
            addCriterion("submit_doc is not null");
            return (Criteria) this;
        }

        public Criteria andSubmitDocEqualTo(String value) {
            addCriterion("submit_doc =", value, "submitDoc");
            return (Criteria) this;
        }

        public Criteria andSubmitDocNotEqualTo(String value) {
            addCriterion("submit_doc <>", value, "submitDoc");
            return (Criteria) this;
        }

        public Criteria andSubmitDocGreaterThan(String value) {
            addCriterion("submit_doc >", value, "submitDoc");
            return (Criteria) this;
        }

        public Criteria andSubmitDocGreaterThanOrEqualTo(String value) {
            addCriterion("submit_doc >=", value, "submitDoc");
            return (Criteria) this;
        }

        public Criteria andSubmitDocLessThan(String value) {
            addCriterion("submit_doc <", value, "submitDoc");
            return (Criteria) this;
        }

        public Criteria andSubmitDocLessThanOrEqualTo(String value) {
            addCriterion("submit_doc <=", value, "submitDoc");
            return (Criteria) this;
        }

        public Criteria andSubmitDocLike(String value) {
            addCriterion("submit_doc like", value, "submitDoc");
            return (Criteria) this;
        }

        public Criteria andSubmitDocNotLike(String value) {
            addCriterion("submit_doc not like", value, "submitDoc");
            return (Criteria) this;
        }

        public Criteria andSubmitDocIn(List<String> values) {
            addCriterion("submit_doc in", values, "submitDoc");
            return (Criteria) this;
        }

        public Criteria andSubmitDocNotIn(List<String> values) {
            addCriterion("submit_doc not in", values, "submitDoc");
            return (Criteria) this;
        }

        public Criteria andSubmitDocBetween(String value1, String value2) {
            addCriterion("submit_doc between", value1, value2, "submitDoc");
            return (Criteria) this;
        }

        public Criteria andSubmitDocNotBetween(String value1, String value2) {
            addCriterion("submit_doc not between", value1, value2, "submitDoc");
            return (Criteria) this;
        }

        public Criteria andSuccDocIsNull() {
            addCriterion("succ_doc is null");
            return (Criteria) this;
        }

        public Criteria andSuccDocIsNotNull() {
            addCriterion("succ_doc is not null");
            return (Criteria) this;
        }

        public Criteria andSuccDocEqualTo(String value) {
            addCriterion("succ_doc =", value, "succDoc");
            return (Criteria) this;
        }

        public Criteria andSuccDocNotEqualTo(String value) {
            addCriterion("succ_doc <>", value, "succDoc");
            return (Criteria) this;
        }

        public Criteria andSuccDocGreaterThan(String value) {
            addCriterion("succ_doc >", value, "succDoc");
            return (Criteria) this;
        }

        public Criteria andSuccDocGreaterThanOrEqualTo(String value) {
            addCriterion("succ_doc >=", value, "succDoc");
            return (Criteria) this;
        }

        public Criteria andSuccDocLessThan(String value) {
            addCriterion("succ_doc <", value, "succDoc");
            return (Criteria) this;
        }

        public Criteria andSuccDocLessThanOrEqualTo(String value) {
            addCriterion("succ_doc <=", value, "succDoc");
            return (Criteria) this;
        }

        public Criteria andSuccDocLike(String value) {
            addCriterion("succ_doc like", value, "succDoc");
            return (Criteria) this;
        }

        public Criteria andSuccDocNotLike(String value) {
            addCriterion("succ_doc not like", value, "succDoc");
            return (Criteria) this;
        }

        public Criteria andSuccDocIn(List<String> values) {
            addCriterion("succ_doc in", values, "succDoc");
            return (Criteria) this;
        }

        public Criteria andSuccDocNotIn(List<String> values) {
            addCriterion("succ_doc not in", values, "succDoc");
            return (Criteria) this;
        }

        public Criteria andSuccDocBetween(String value1, String value2) {
            addCriterion("succ_doc between", value1, value2, "succDoc");
            return (Criteria) this;
        }

        public Criteria andSuccDocNotBetween(String value1, String value2) {
            addCriterion("succ_doc not between", value1, value2, "succDoc");
            return (Criteria) this;
        }

        public Criteria andFailDocIsNull() {
            addCriterion("fail_doc is null");
            return (Criteria) this;
        }

        public Criteria andFailDocIsNotNull() {
            addCriterion("fail_doc is not null");
            return (Criteria) this;
        }

        public Criteria andFailDocEqualTo(String value) {
            addCriterion("fail_doc =", value, "failDoc");
            return (Criteria) this;
        }

        public Criteria andFailDocNotEqualTo(String value) {
            addCriterion("fail_doc <>", value, "failDoc");
            return (Criteria) this;
        }

        public Criteria andFailDocGreaterThan(String value) {
            addCriterion("fail_doc >", value, "failDoc");
            return (Criteria) this;
        }

        public Criteria andFailDocGreaterThanOrEqualTo(String value) {
            addCriterion("fail_doc >=", value, "failDoc");
            return (Criteria) this;
        }

        public Criteria andFailDocLessThan(String value) {
            addCriterion("fail_doc <", value, "failDoc");
            return (Criteria) this;
        }

        public Criteria andFailDocLessThanOrEqualTo(String value) {
            addCriterion("fail_doc <=", value, "failDoc");
            return (Criteria) this;
        }

        public Criteria andFailDocLike(String value) {
            addCriterion("fail_doc like", value, "failDoc");
            return (Criteria) this;
        }

        public Criteria andFailDocNotLike(String value) {
            addCriterion("fail_doc not like", value, "failDoc");
            return (Criteria) this;
        }

        public Criteria andFailDocIn(List<String> values) {
            addCriterion("fail_doc in", values, "failDoc");
            return (Criteria) this;
        }

        public Criteria andFailDocNotIn(List<String> values) {
            addCriterion("fail_doc not in", values, "failDoc");
            return (Criteria) this;
        }

        public Criteria andFailDocBetween(String value1, String value2) {
            addCriterion("fail_doc between", value1, value2, "failDoc");
            return (Criteria) this;
        }

        public Criteria andFailDocNotBetween(String value1, String value2) {
            addCriterion("fail_doc not between", value1, value2, "failDoc");
            return (Criteria) this;
        }

        public Criteria andNoneRecIsNull() {
            addCriterion("none_rec is null");
            return (Criteria) this;
        }

        public Criteria andNoneRecIsNotNull() {
            addCriterion("none_rec is not null");
            return (Criteria) this;
        }

        public Criteria andNoneRecEqualTo(Byte value) {
            addCriterion("none_rec =", value, "noneRec");
            return (Criteria) this;
        }

        public Criteria andNoneRecNotEqualTo(Byte value) {
            addCriterion("none_rec <>", value, "noneRec");
            return (Criteria) this;
        }

        public Criteria andNoneRecGreaterThan(Byte value) {
            addCriterion("none_rec >", value, "noneRec");
            return (Criteria) this;
        }

        public Criteria andNoneRecGreaterThanOrEqualTo(Byte value) {
            addCriterion("none_rec >=", value, "noneRec");
            return (Criteria) this;
        }

        public Criteria andNoneRecLessThan(Byte value) {
            addCriterion("none_rec <", value, "noneRec");
            return (Criteria) this;
        }

        public Criteria andNoneRecLessThanOrEqualTo(Byte value) {
            addCriterion("none_rec <=", value, "noneRec");
            return (Criteria) this;
        }

        public Criteria andNoneRecIn(List<Byte> values) {
            addCriterion("none_rec in", values, "noneRec");
            return (Criteria) this;
        }

        public Criteria andNoneRecNotIn(List<Byte> values) {
            addCriterion("none_rec not in", values, "noneRec");
            return (Criteria) this;
        }

        public Criteria andNoneRecBetween(Byte value1, Byte value2) {
            addCriterion("none_rec between", value1, value2, "noneRec");
            return (Criteria) this;
        }

        public Criteria andNoneRecNotBetween(Byte value1, Byte value2) {
            addCriterion("none_rec not between", value1, value2, "noneRec");
            return (Criteria) this;
        }

        public Criteria andSubmitRecIsNull() {
            addCriterion("submit_rec is null");
            return (Criteria) this;
        }

        public Criteria andSubmitRecIsNotNull() {
            addCriterion("submit_rec is not null");
            return (Criteria) this;
        }

        public Criteria andSubmitRecEqualTo(Byte value) {
            addCriterion("submit_rec =", value, "submitRec");
            return (Criteria) this;
        }

        public Criteria andSubmitRecNotEqualTo(Byte value) {
            addCriterion("submit_rec <>", value, "submitRec");
            return (Criteria) this;
        }

        public Criteria andSubmitRecGreaterThan(Byte value) {
            addCriterion("submit_rec >", value, "submitRec");
            return (Criteria) this;
        }

        public Criteria andSubmitRecGreaterThanOrEqualTo(Byte value) {
            addCriterion("submit_rec >=", value, "submitRec");
            return (Criteria) this;
        }

        public Criteria andSubmitRecLessThan(Byte value) {
            addCriterion("submit_rec <", value, "submitRec");
            return (Criteria) this;
        }

        public Criteria andSubmitRecLessThanOrEqualTo(Byte value) {
            addCriterion("submit_rec <=", value, "submitRec");
            return (Criteria) this;
        }

        public Criteria andSubmitRecIn(List<Byte> values) {
            addCriterion("submit_rec in", values, "submitRec");
            return (Criteria) this;
        }

        public Criteria andSubmitRecNotIn(List<Byte> values) {
            addCriterion("submit_rec not in", values, "submitRec");
            return (Criteria) this;
        }

        public Criteria andSubmitRecBetween(Byte value1, Byte value2) {
            addCriterion("submit_rec between", value1, value2, "submitRec");
            return (Criteria) this;
        }

        public Criteria andSubmitRecNotBetween(Byte value1, Byte value2) {
            addCriterion("submit_rec not between", value1, value2, "submitRec");
            return (Criteria) this;
        }

        public Criteria andSuccRecIsNull() {
            addCriterion("succ_rec is null");
            return (Criteria) this;
        }

        public Criteria andSuccRecIsNotNull() {
            addCriterion("succ_rec is not null");
            return (Criteria) this;
        }

        public Criteria andSuccRecEqualTo(Byte value) {
            addCriterion("succ_rec =", value, "succRec");
            return (Criteria) this;
        }

        public Criteria andSuccRecNotEqualTo(Byte value) {
            addCriterion("succ_rec <>", value, "succRec");
            return (Criteria) this;
        }

        public Criteria andSuccRecGreaterThan(Byte value) {
            addCriterion("succ_rec >", value, "succRec");
            return (Criteria) this;
        }

        public Criteria andSuccRecGreaterThanOrEqualTo(Byte value) {
            addCriterion("succ_rec >=", value, "succRec");
            return (Criteria) this;
        }

        public Criteria andSuccRecLessThan(Byte value) {
            addCriterion("succ_rec <", value, "succRec");
            return (Criteria) this;
        }

        public Criteria andSuccRecLessThanOrEqualTo(Byte value) {
            addCriterion("succ_rec <=", value, "succRec");
            return (Criteria) this;
        }

        public Criteria andSuccRecIn(List<Byte> values) {
            addCriterion("succ_rec in", values, "succRec");
            return (Criteria) this;
        }

        public Criteria andSuccRecNotIn(List<Byte> values) {
            addCriterion("succ_rec not in", values, "succRec");
            return (Criteria) this;
        }

        public Criteria andSuccRecBetween(Byte value1, Byte value2) {
            addCriterion("succ_rec between", value1, value2, "succRec");
            return (Criteria) this;
        }

        public Criteria andSuccRecNotBetween(Byte value1, Byte value2) {
            addCriterion("succ_rec not between", value1, value2, "succRec");
            return (Criteria) this;
        }

        public Criteria andFailRecIsNull() {
            addCriterion("fail_rec is null");
            return (Criteria) this;
        }

        public Criteria andFailRecIsNotNull() {
            addCriterion("fail_rec is not null");
            return (Criteria) this;
        }

        public Criteria andFailRecEqualTo(Byte value) {
            addCriterion("fail_rec =", value, "failRec");
            return (Criteria) this;
        }

        public Criteria andFailRecNotEqualTo(Byte value) {
            addCriterion("fail_rec <>", value, "failRec");
            return (Criteria) this;
        }

        public Criteria andFailRecGreaterThan(Byte value) {
            addCriterion("fail_rec >", value, "failRec");
            return (Criteria) this;
        }

        public Criteria andFailRecGreaterThanOrEqualTo(Byte value) {
            addCriterion("fail_rec >=", value, "failRec");
            return (Criteria) this;
        }

        public Criteria andFailRecLessThan(Byte value) {
            addCriterion("fail_rec <", value, "failRec");
            return (Criteria) this;
        }

        public Criteria andFailRecLessThanOrEqualTo(Byte value) {
            addCriterion("fail_rec <=", value, "failRec");
            return (Criteria) this;
        }

        public Criteria andFailRecIn(List<Byte> values) {
            addCriterion("fail_rec in", values, "failRec");
            return (Criteria) this;
        }

        public Criteria andFailRecNotIn(List<Byte> values) {
            addCriterion("fail_rec not in", values, "failRec");
            return (Criteria) this;
        }

        public Criteria andFailRecBetween(Byte value1, Byte value2) {
            addCriterion("fail_rec between", value1, value2, "failRec");
            return (Criteria) this;
        }

        public Criteria andFailRecNotBetween(Byte value1, Byte value2) {
            addCriterion("fail_rec not between", value1, value2, "failRec");
            return (Criteria) this;
        }

        public Criteria andSubmitPushIsNull() {
            addCriterion("submit_push is null");
            return (Criteria) this;
        }

        public Criteria andSubmitPushIsNotNull() {
            addCriterion("submit_push is not null");
            return (Criteria) this;
        }

        public Criteria andSubmitPushEqualTo(String value) {
            addCriterion("submit_push =", value, "submitPush");
            return (Criteria) this;
        }

        public Criteria andSubmitPushNotEqualTo(String value) {
            addCriterion("submit_push <>", value, "submitPush");
            return (Criteria) this;
        }

        public Criteria andSubmitPushGreaterThan(String value) {
            addCriterion("submit_push >", value, "submitPush");
            return (Criteria) this;
        }

        public Criteria andSubmitPushGreaterThanOrEqualTo(String value) {
            addCriterion("submit_push >=", value, "submitPush");
            return (Criteria) this;
        }

        public Criteria andSubmitPushLessThan(String value) {
            addCriterion("submit_push <", value, "submitPush");
            return (Criteria) this;
        }

        public Criteria andSubmitPushLessThanOrEqualTo(String value) {
            addCriterion("submit_push <=", value, "submitPush");
            return (Criteria) this;
        }

        public Criteria andSubmitPushLike(String value) {
            addCriterion("submit_push like", value, "submitPush");
            return (Criteria) this;
        }

        public Criteria andSubmitPushNotLike(String value) {
            addCriterion("submit_push not like", value, "submitPush");
            return (Criteria) this;
        }

        public Criteria andSubmitPushIn(List<String> values) {
            addCriterion("submit_push in", values, "submitPush");
            return (Criteria) this;
        }

        public Criteria andSubmitPushNotIn(List<String> values) {
            addCriterion("submit_push not in", values, "submitPush");
            return (Criteria) this;
        }

        public Criteria andSubmitPushBetween(String value1, String value2) {
            addCriterion("submit_push between", value1, value2, "submitPush");
            return (Criteria) this;
        }

        public Criteria andSubmitPushNotBetween(String value1, String value2) {
            addCriterion("submit_push not between", value1, value2, "submitPush");
            return (Criteria) this;
        }

        public Criteria andSuccPushIsNull() {
            addCriterion("succ_push is null");
            return (Criteria) this;
        }

        public Criteria andSuccPushIsNotNull() {
            addCriterion("succ_push is not null");
            return (Criteria) this;
        }

        public Criteria andSuccPushEqualTo(String value) {
            addCriterion("succ_push =", value, "succPush");
            return (Criteria) this;
        }

        public Criteria andSuccPushNotEqualTo(String value) {
            addCriterion("succ_push <>", value, "succPush");
            return (Criteria) this;
        }

        public Criteria andSuccPushGreaterThan(String value) {
            addCriterion("succ_push >", value, "succPush");
            return (Criteria) this;
        }

        public Criteria andSuccPushGreaterThanOrEqualTo(String value) {
            addCriterion("succ_push >=", value, "succPush");
            return (Criteria) this;
        }

        public Criteria andSuccPushLessThan(String value) {
            addCriterion("succ_push <", value, "succPush");
            return (Criteria) this;
        }

        public Criteria andSuccPushLessThanOrEqualTo(String value) {
            addCriterion("succ_push <=", value, "succPush");
            return (Criteria) this;
        }

        public Criteria andSuccPushLike(String value) {
            addCriterion("succ_push like", value, "succPush");
            return (Criteria) this;
        }

        public Criteria andSuccPushNotLike(String value) {
            addCriterion("succ_push not like", value, "succPush");
            return (Criteria) this;
        }

        public Criteria andSuccPushIn(List<String> values) {
            addCriterion("succ_push in", values, "succPush");
            return (Criteria) this;
        }

        public Criteria andSuccPushNotIn(List<String> values) {
            addCriterion("succ_push not in", values, "succPush");
            return (Criteria) this;
        }

        public Criteria andSuccPushBetween(String value1, String value2) {
            addCriterion("succ_push between", value1, value2, "succPush");
            return (Criteria) this;
        }

        public Criteria andSuccPushNotBetween(String value1, String value2) {
            addCriterion("succ_push not between", value1, value2, "succPush");
            return (Criteria) this;
        }

        public Criteria andFailPushIsNull() {
            addCriterion("fail_push is null");
            return (Criteria) this;
        }

        public Criteria andFailPushIsNotNull() {
            addCriterion("fail_push is not null");
            return (Criteria) this;
        }

        public Criteria andFailPushEqualTo(String value) {
            addCriterion("fail_push =", value, "failPush");
            return (Criteria) this;
        }

        public Criteria andFailPushNotEqualTo(String value) {
            addCriterion("fail_push <>", value, "failPush");
            return (Criteria) this;
        }

        public Criteria andFailPushGreaterThan(String value) {
            addCriterion("fail_push >", value, "failPush");
            return (Criteria) this;
        }

        public Criteria andFailPushGreaterThanOrEqualTo(String value) {
            addCriterion("fail_push >=", value, "failPush");
            return (Criteria) this;
        }

        public Criteria andFailPushLessThan(String value) {
            addCriterion("fail_push <", value, "failPush");
            return (Criteria) this;
        }

        public Criteria andFailPushLessThanOrEqualTo(String value) {
            addCriterion("fail_push <=", value, "failPush");
            return (Criteria) this;
        }

        public Criteria andFailPushLike(String value) {
            addCriterion("fail_push like", value, "failPush");
            return (Criteria) this;
        }

        public Criteria andFailPushNotLike(String value) {
            addCriterion("fail_push not like", value, "failPush");
            return (Criteria) this;
        }

        public Criteria andFailPushIn(List<String> values) {
            addCriterion("fail_push in", values, "failPush");
            return (Criteria) this;
        }

        public Criteria andFailPushNotIn(List<String> values) {
            addCriterion("fail_push not in", values, "failPush");
            return (Criteria) this;
        }

        public Criteria andFailPushBetween(String value1, String value2) {
            addCriterion("fail_push between", value1, value2, "failPush");
            return (Criteria) this;
        }

        public Criteria andFailPushNotBetween(String value1, String value2) {
            addCriterion("fail_push not between", value1, value2, "failPush");
            return (Criteria) this;
        }

        public Criteria andPreDisplayIsNull() {
            addCriterion("pre_display is null");
            return (Criteria) this;
        }

        public Criteria andPreDisplayIsNotNull() {
            addCriterion("pre_display is not null");
            return (Criteria) this;
        }

        public Criteria andPreDisplayEqualTo(Byte value) {
            addCriterion("pre_display =", value, "preDisplay");
            return (Criteria) this;
        }

        public Criteria andPreDisplayNotEqualTo(Byte value) {
            addCriterion("pre_display <>", value, "preDisplay");
            return (Criteria) this;
        }

        public Criteria andPreDisplayGreaterThan(Byte value) {
            addCriterion("pre_display >", value, "preDisplay");
            return (Criteria) this;
        }

        public Criteria andPreDisplayGreaterThanOrEqualTo(Byte value) {
            addCriterion("pre_display >=", value, "preDisplay");
            return (Criteria) this;
        }

        public Criteria andPreDisplayLessThan(Byte value) {
            addCriterion("pre_display <", value, "preDisplay");
            return (Criteria) this;
        }

        public Criteria andPreDisplayLessThanOrEqualTo(Byte value) {
            addCriterion("pre_display <=", value, "preDisplay");
            return (Criteria) this;
        }

        public Criteria andPreDisplayIn(List<Byte> values) {
            addCriterion("pre_display in", values, "preDisplay");
            return (Criteria) this;
        }

        public Criteria andPreDisplayNotIn(List<Byte> values) {
            addCriterion("pre_display not in", values, "preDisplay");
            return (Criteria) this;
        }

        public Criteria andPreDisplayBetween(Byte value1, Byte value2) {
            addCriterion("pre_display between", value1, value2, "preDisplay");
            return (Criteria) this;
        }

        public Criteria andPreDisplayNotBetween(Byte value1, Byte value2) {
            addCriterion("pre_display not between", value1, value2, "preDisplay");
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

        public Criteria andRedIsNull() {
            addCriterion("red is null");
            return (Criteria) this;
        }

        public Criteria andRedIsNotNull() {
            addCriterion("red is not null");
            return (Criteria) this;
        }

        public Criteria andRedEqualTo(Byte value) {
            addCriterion("red =", value, "red");
            return (Criteria) this;
        }

        public Criteria andRedNotEqualTo(Byte value) {
            addCriterion("red <>", value, "red");
            return (Criteria) this;
        }

        public Criteria andRedGreaterThan(Byte value) {
            addCriterion("red >", value, "red");
            return (Criteria) this;
        }

        public Criteria andRedGreaterThanOrEqualTo(Byte value) {
            addCriterion("red >=", value, "red");
            return (Criteria) this;
        }

        public Criteria andRedLessThan(Byte value) {
            addCriterion("red <", value, "red");
            return (Criteria) this;
        }

        public Criteria andRedLessThanOrEqualTo(Byte value) {
            addCriterion("red <=", value, "red");
            return (Criteria) this;
        }

        public Criteria andRedIn(List<Byte> values) {
            addCriterion("red in", values, "red");
            return (Criteria) this;
        }

        public Criteria andRedNotIn(List<Byte> values) {
            addCriterion("red not in", values, "red");
            return (Criteria) this;
        }

        public Criteria andRedBetween(Byte value1, Byte value2) {
            addCriterion("red between", value1, value2, "red");
            return (Criteria) this;
        }

        public Criteria andRedNotBetween(Byte value1, Byte value2) {
            addCriterion("red not between", value1, value2, "red");
            return (Criteria) this;
        }

        public Criteria andRecoCourseIsNull() {
            addCriterion("reco_course is null");
            return (Criteria) this;
        }

        public Criteria andRecoCourseIsNotNull() {
            addCriterion("reco_course is not null");
            return (Criteria) this;
        }

        public Criteria andRecoCourseEqualTo(String value) {
            addCriterion("reco_course =", value, "recoCourse");
            return (Criteria) this;
        }

        public Criteria andRecoCourseNotEqualTo(String value) {
            addCriterion("reco_course <>", value, "recoCourse");
            return (Criteria) this;
        }

        public Criteria andRecoCourseGreaterThan(String value) {
            addCriterion("reco_course >", value, "recoCourse");
            return (Criteria) this;
        }

        public Criteria andRecoCourseGreaterThanOrEqualTo(String value) {
            addCriterion("reco_course >=", value, "recoCourse");
            return (Criteria) this;
        }

        public Criteria andRecoCourseLessThan(String value) {
            addCriterion("reco_course <", value, "recoCourse");
            return (Criteria) this;
        }

        public Criteria andRecoCourseLessThanOrEqualTo(String value) {
            addCriterion("reco_course <=", value, "recoCourse");
            return (Criteria) this;
        }

        public Criteria andRecoCourseLike(String value) {
            addCriterion("reco_course like", value, "recoCourse");
            return (Criteria) this;
        }

        public Criteria andRecoCourseNotLike(String value) {
            addCriterion("reco_course not like", value, "recoCourse");
            return (Criteria) this;
        }

        public Criteria andRecoCourseIn(List<String> values) {
            addCriterion("reco_course in", values, "recoCourse");
            return (Criteria) this;
        }

        public Criteria andRecoCourseNotIn(List<String> values) {
            addCriterion("reco_course not in", values, "recoCourse");
            return (Criteria) this;
        }

        public Criteria andRecoCourseBetween(String value1, String value2) {
            addCriterion("reco_course between", value1, value2, "recoCourse");
            return (Criteria) this;
        }

        public Criteria andRecoCourseNotBetween(String value1, String value2) {
            addCriterion("reco_course not between", value1, value2, "recoCourse");
            return (Criteria) this;
        }

        public Criteria andOtherCourseIsNull() {
            addCriterion("other_course is null");
            return (Criteria) this;
        }

        public Criteria andOtherCourseIsNotNull() {
            addCriterion("other_course is not null");
            return (Criteria) this;
        }

        public Criteria andOtherCourseEqualTo(String value) {
            addCriterion("other_course =", value, "otherCourse");
            return (Criteria) this;
        }

        public Criteria andOtherCourseNotEqualTo(String value) {
            addCriterion("other_course <>", value, "otherCourse");
            return (Criteria) this;
        }

        public Criteria andOtherCourseGreaterThan(String value) {
            addCriterion("other_course >", value, "otherCourse");
            return (Criteria) this;
        }

        public Criteria andOtherCourseGreaterThanOrEqualTo(String value) {
            addCriterion("other_course >=", value, "otherCourse");
            return (Criteria) this;
        }

        public Criteria andOtherCourseLessThan(String value) {
            addCriterion("other_course <", value, "otherCourse");
            return (Criteria) this;
        }

        public Criteria andOtherCourseLessThanOrEqualTo(String value) {
            addCriterion("other_course <=", value, "otherCourse");
            return (Criteria) this;
        }

        public Criteria andOtherCourseLike(String value) {
            addCriterion("other_course like", value, "otherCourse");
            return (Criteria) this;
        }

        public Criteria andOtherCourseNotLike(String value) {
            addCriterion("other_course not like", value, "otherCourse");
            return (Criteria) this;
        }

        public Criteria andOtherCourseIn(List<String> values) {
            addCriterion("other_course in", values, "otherCourse");
            return (Criteria) this;
        }

        public Criteria andOtherCourseNotIn(List<String> values) {
            addCriterion("other_course not in", values, "otherCourse");
            return (Criteria) this;
        }

        public Criteria andOtherCourseBetween(String value1, String value2) {
            addCriterion("other_course between", value1, value2, "otherCourse");
            return (Criteria) this;
        }

        public Criteria andOtherCourseNotBetween(String value1, String value2) {
            addCriterion("other_course not between", value1, value2, "otherCourse");
            return (Criteria) this;
        }

        public Criteria andOrderByIsNull() {
            addCriterion("order_by is null");
            return (Criteria) this;
        }

        public Criteria andOrderByIsNotNull() {
            addCriterion("order_by is not null");
            return (Criteria) this;
        }

        public Criteria andOrderByEqualTo(Integer value) {
            addCriterion("order_by =", value, "orderBy");
            return (Criteria) this;
        }

        public Criteria andOrderByNotEqualTo(Integer value) {
            addCriterion("order_by <>", value, "orderBy");
            return (Criteria) this;
        }

        public Criteria andOrderByGreaterThan(Integer value) {
            addCriterion("order_by >", value, "orderBy");
            return (Criteria) this;
        }

        public Criteria andOrderByGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_by >=", value, "orderBy");
            return (Criteria) this;
        }

        public Criteria andOrderByLessThan(Integer value) {
            addCriterion("order_by <", value, "orderBy");
            return (Criteria) this;
        }

        public Criteria andOrderByLessThanOrEqualTo(Integer value) {
            addCriterion("order_by <=", value, "orderBy");
            return (Criteria) this;
        }

        public Criteria andOrderByIn(List<Integer> values) {
            addCriterion("order_by in", values, "orderBy");
            return (Criteria) this;
        }

        public Criteria andOrderByNotIn(List<Integer> values) {
            addCriterion("order_by not in", values, "orderBy");
            return (Criteria) this;
        }

        public Criteria andOrderByBetween(Integer value1, Integer value2) {
            addCriterion("order_by between", value1, value2, "orderBy");
            return (Criteria) this;
        }

        public Criteria andOrderByNotBetween(Integer value1, Integer value2) {
            addCriterion("order_by not between", value1, value2, "orderBy");
            return (Criteria) this;
        }

        public Criteria andRecoveryIsNull() {
            addCriterion("recovery is null");
            return (Criteria) this;
        }

        public Criteria andRecoveryIsNotNull() {
            addCriterion("recovery is not null");
            return (Criteria) this;
        }

        public Criteria andRecoveryEqualTo(Byte value) {
            addCriterion("recovery =", value, "recovery");
            return (Criteria) this;
        }

        public Criteria andRecoveryNotEqualTo(Byte value) {
            addCriterion("recovery <>", value, "recovery");
            return (Criteria) this;
        }

        public Criteria andRecoveryGreaterThan(Byte value) {
            addCriterion("recovery >", value, "recovery");
            return (Criteria) this;
        }

        public Criteria andRecoveryGreaterThanOrEqualTo(Byte value) {
            addCriterion("recovery >=", value, "recovery");
            return (Criteria) this;
        }

        public Criteria andRecoveryLessThan(Byte value) {
            addCriterion("recovery <", value, "recovery");
            return (Criteria) this;
        }

        public Criteria andRecoveryLessThanOrEqualTo(Byte value) {
            addCriterion("recovery <=", value, "recovery");
            return (Criteria) this;
        }

        public Criteria andRecoveryIn(List<Byte> values) {
            addCriterion("recovery in", values, "recovery");
            return (Criteria) this;
        }

        public Criteria andRecoveryNotIn(List<Byte> values) {
            addCriterion("recovery not in", values, "recovery");
            return (Criteria) this;
        }

        public Criteria andRecoveryBetween(Byte value1, Byte value2) {
            addCriterion("recovery between", value1, value2, "recovery");
            return (Criteria) this;
        }

        public Criteria andRecoveryNotBetween(Byte value1, Byte value2) {
            addCriterion("recovery not between", value1, value2, "recovery");
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