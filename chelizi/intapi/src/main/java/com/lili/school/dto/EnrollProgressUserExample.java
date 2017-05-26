package com.lili.school.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EnrollProgressUserExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EnrollProgressUserExample() {
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

        public Criteria andStudentIdIsNull() {
            addCriterion("student_id is null");
            return (Criteria) this;
        }

        public Criteria andStudentIdIsNotNull() {
            addCriterion("student_id is not null");
            return (Criteria) this;
        }

        public Criteria andStudentIdEqualTo(Long value) {
            addCriterion("student_id =", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdNotEqualTo(Long value) {
            addCriterion("student_id <>", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdGreaterThan(Long value) {
            addCriterion("student_id >", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdGreaterThanOrEqualTo(Long value) {
            addCriterion("student_id >=", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdLessThan(Long value) {
            addCriterion("student_id <", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdLessThanOrEqualTo(Long value) {
            addCriterion("student_id <=", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdIn(List<Long> values) {
            addCriterion("student_id in", values, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdNotIn(List<Long> values) {
            addCriterion("student_id not in", values, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdBetween(Long value1, Long value2) {
            addCriterion("student_id between", value1, value2, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdNotBetween(Long value1, Long value2) {
            addCriterion("student_id not between", value1, value2, "studentId");
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

        public Criteria andStepTimesIsNull() {
            addCriterion("step_times is null");
            return (Criteria) this;
        }

        public Criteria andStepTimesIsNotNull() {
            addCriterion("step_times is not null");
            return (Criteria) this;
        }

        public Criteria andStepTimesEqualTo(Integer value) {
            addCriterion("step_times =", value, "stepTimes");
            return (Criteria) this;
        }

        public Criteria andStepTimesNotEqualTo(Integer value) {
            addCriterion("step_times <>", value, "stepTimes");
            return (Criteria) this;
        }

        public Criteria andStepTimesGreaterThan(Integer value) {
            addCriterion("step_times >", value, "stepTimes");
            return (Criteria) this;
        }

        public Criteria andStepTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("step_times >=", value, "stepTimes");
            return (Criteria) this;
        }

        public Criteria andStepTimesLessThan(Integer value) {
            addCriterion("step_times <", value, "stepTimes");
            return (Criteria) this;
        }

        public Criteria andStepTimesLessThanOrEqualTo(Integer value) {
            addCriterion("step_times <=", value, "stepTimes");
            return (Criteria) this;
        }

        public Criteria andStepTimesIn(List<Integer> values) {
            addCriterion("step_times in", values, "stepTimes");
            return (Criteria) this;
        }

        public Criteria andStepTimesNotIn(List<Integer> values) {
            addCriterion("step_times not in", values, "stepTimes");
            return (Criteria) this;
        }

        public Criteria andStepTimesBetween(Integer value1, Integer value2) {
            addCriterion("step_times between", value1, value2, "stepTimes");
            return (Criteria) this;
        }

        public Criteria andStepTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("step_times not between", value1, value2, "stepTimes");
            return (Criteria) this;
        }

        public Criteria andPayStateIsNull() {
            addCriterion("pay_state is null");
            return (Criteria) this;
        }

        public Criteria andPayStateIsNotNull() {
            addCriterion("pay_state is not null");
            return (Criteria) this;
        }

        public Criteria andPayStateEqualTo(Byte value) {
            addCriterion("pay_state =", value, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateNotEqualTo(Byte value) {
            addCriterion("pay_state <>", value, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateGreaterThan(Byte value) {
            addCriterion("pay_state >", value, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateGreaterThanOrEqualTo(Byte value) {
            addCriterion("pay_state >=", value, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateLessThan(Byte value) {
            addCriterion("pay_state <", value, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateLessThanOrEqualTo(Byte value) {
            addCriterion("pay_state <=", value, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateIn(List<Byte> values) {
            addCriterion("pay_state in", values, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateNotIn(List<Byte> values) {
            addCriterion("pay_state not in", values, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateBetween(Byte value1, Byte value2) {
            addCriterion("pay_state between", value1, value2, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateNotBetween(Byte value1, Byte value2) {
            addCriterion("pay_state not between", value1, value2, "payState");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNull() {
            addCriterion("pay_time is null");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNotNull() {
            addCriterion("pay_time is not null");
            return (Criteria) this;
        }

        public Criteria andPayTimeEqualTo(Date value) {
            addCriterion("pay_time =", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotEqualTo(Date value) {
            addCriterion("pay_time <>", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThan(Date value) {
            addCriterion("pay_time >", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("pay_time >=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThan(Date value) {
            addCriterion("pay_time <", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThanOrEqualTo(Date value) {
            addCriterion("pay_time <=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeIn(List<Date> values) {
            addCriterion("pay_time in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotIn(List<Date> values) {
            addCriterion("pay_time not in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeBetween(Date value1, Date value2) {
            addCriterion("pay_time between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotBetween(Date value1, Date value2) {
            addCriterion("pay_time not between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andProcessStateIsNull() {
            addCriterion("process_state is null");
            return (Criteria) this;
        }

        public Criteria andProcessStateIsNotNull() {
            addCriterion("process_state is not null");
            return (Criteria) this;
        }

        public Criteria andProcessStateEqualTo(Byte value) {
            addCriterion("process_state =", value, "processState");
            return (Criteria) this;
        }

        public Criteria andProcessStateNotEqualTo(Byte value) {
            addCriterion("process_state <>", value, "processState");
            return (Criteria) this;
        }

        public Criteria andProcessStateGreaterThan(Byte value) {
            addCriterion("process_state >", value, "processState");
            return (Criteria) this;
        }

        public Criteria andProcessStateGreaterThanOrEqualTo(Byte value) {
            addCriterion("process_state >=", value, "processState");
            return (Criteria) this;
        }

        public Criteria andProcessStateLessThan(Byte value) {
            addCriterion("process_state <", value, "processState");
            return (Criteria) this;
        }

        public Criteria andProcessStateLessThanOrEqualTo(Byte value) {
            addCriterion("process_state <=", value, "processState");
            return (Criteria) this;
        }

        public Criteria andProcessStateIn(List<Byte> values) {
            addCriterion("process_state in", values, "processState");
            return (Criteria) this;
        }

        public Criteria andProcessStateNotIn(List<Byte> values) {
            addCriterion("process_state not in", values, "processState");
            return (Criteria) this;
        }

        public Criteria andProcessStateBetween(Byte value1, Byte value2) {
            addCriterion("process_state between", value1, value2, "processState");
            return (Criteria) this;
        }

        public Criteria andProcessStateNotBetween(Byte value1, Byte value2) {
            addCriterion("process_state not between", value1, value2, "processState");
            return (Criteria) this;
        }

        public Criteria andResultIsNull() {
            addCriterion("result is null");
            return (Criteria) this;
        }

        public Criteria andResultIsNotNull() {
            addCriterion("result is not null");
            return (Criteria) this;
        }

        public Criteria andResultEqualTo(String value) {
            addCriterion("result =", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotEqualTo(String value) {
            addCriterion("result <>", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThan(String value) {
            addCriterion("result >", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThanOrEqualTo(String value) {
            addCriterion("result >=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThan(String value) {
            addCriterion("result <", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThanOrEqualTo(String value) {
            addCriterion("result <=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLike(String value) {
            addCriterion("result like", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotLike(String value) {
            addCriterion("result not like", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultIn(List<String> values) {
            addCriterion("result in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotIn(List<String> values) {
            addCriterion("result not in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultBetween(String value1, String value2) {
            addCriterion("result between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotBetween(String value1, String value2) {
            addCriterion("result not between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andIsreadIsNull() {
            addCriterion("isread is null");
            return (Criteria) this;
        }

        public Criteria andIsreadIsNotNull() {
            addCriterion("isread is not null");
            return (Criteria) this;
        }

        public Criteria andIsreadEqualTo(Byte value) {
            addCriterion("isread =", value, "isread");
            return (Criteria) this;
        }

        public Criteria andIsreadNotEqualTo(Byte value) {
            addCriterion("isread <>", value, "isread");
            return (Criteria) this;
        }

        public Criteria andIsreadGreaterThan(Byte value) {
            addCriterion("isread >", value, "isread");
            return (Criteria) this;
        }

        public Criteria andIsreadGreaterThanOrEqualTo(Byte value) {
            addCriterion("isread >=", value, "isread");
            return (Criteria) this;
        }

        public Criteria andIsreadLessThan(Byte value) {
            addCriterion("isread <", value, "isread");
            return (Criteria) this;
        }

        public Criteria andIsreadLessThanOrEqualTo(Byte value) {
            addCriterion("isread <=", value, "isread");
            return (Criteria) this;
        }

        public Criteria andIsreadIn(List<Byte> values) {
            addCriterion("isread in", values, "isread");
            return (Criteria) this;
        }

        public Criteria andIsreadNotIn(List<Byte> values) {
            addCriterion("isread not in", values, "isread");
            return (Criteria) this;
        }

        public Criteria andIsreadBetween(Byte value1, Byte value2) {
            addCriterion("isread between", value1, value2, "isread");
            return (Criteria) this;
        }

        public Criteria andIsreadNotBetween(Byte value1, Byte value2) {
            addCriterion("isread not between", value1, value2, "isread");
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

        public Criteria andNeedShowIsNull() {
            addCriterion("need_show is null");
            return (Criteria) this;
        }

        public Criteria andNeedShowIsNotNull() {
            addCriterion("need_show is not null");
            return (Criteria) this;
        }

        public Criteria andNeedShowEqualTo(Byte value) {
            addCriterion("need_show =", value, "needShow");
            return (Criteria) this;
        }

        public Criteria andNeedShowNotEqualTo(Byte value) {
            addCriterion("need_show <>", value, "needShow");
            return (Criteria) this;
        }

        public Criteria andNeedShowGreaterThan(Byte value) {
            addCriterion("need_show >", value, "needShow");
            return (Criteria) this;
        }

        public Criteria andNeedShowGreaterThanOrEqualTo(Byte value) {
            addCriterion("need_show >=", value, "needShow");
            return (Criteria) this;
        }

        public Criteria andNeedShowLessThan(Byte value) {
            addCriterion("need_show <", value, "needShow");
            return (Criteria) this;
        }

        public Criteria andNeedShowLessThanOrEqualTo(Byte value) {
            addCriterion("need_show <=", value, "needShow");
            return (Criteria) this;
        }

        public Criteria andNeedShowIn(List<Byte> values) {
            addCriterion("need_show in", values, "needShow");
            return (Criteria) this;
        }

        public Criteria andNeedShowNotIn(List<Byte> values) {
            addCriterion("need_show not in", values, "needShow");
            return (Criteria) this;
        }

        public Criteria andNeedShowBetween(Byte value1, Byte value2) {
            addCriterion("need_show between", value1, value2, "needShow");
            return (Criteria) this;
        }

        public Criteria andNeedShowNotBetween(Byte value1, Byte value2) {
            addCriterion("need_show not between", value1, value2, "needShow");
            return (Criteria) this;
        }

        public Criteria andOutDataIsNull() {
            addCriterion("out_data is null");
            return (Criteria) this;
        }

        public Criteria andOutDataIsNotNull() {
            addCriterion("out_data is not null");
            return (Criteria) this;
        }

        public Criteria andOutDataEqualTo(String value) {
            addCriterion("out_data =", value, "outData");
            return (Criteria) this;
        }

        public Criteria andOutDataNotEqualTo(String value) {
            addCriterion("out_data <>", value, "outData");
            return (Criteria) this;
        }

        public Criteria andOutDataGreaterThan(String value) {
            addCriterion("out_data >", value, "outData");
            return (Criteria) this;
        }

        public Criteria andOutDataGreaterThanOrEqualTo(String value) {
            addCriterion("out_data >=", value, "outData");
            return (Criteria) this;
        }

        public Criteria andOutDataLessThan(String value) {
            addCriterion("out_data <", value, "outData");
            return (Criteria) this;
        }

        public Criteria andOutDataLessThanOrEqualTo(String value) {
            addCriterion("out_data <=", value, "outData");
            return (Criteria) this;
        }

        public Criteria andOutDataLike(String value) {
            addCriterion("out_data like", value, "outData");
            return (Criteria) this;
        }

        public Criteria andOutDataNotLike(String value) {
            addCriterion("out_data not like", value, "outData");
            return (Criteria) this;
        }

        public Criteria andOutDataIn(List<String> values) {
            addCriterion("out_data in", values, "outData");
            return (Criteria) this;
        }

        public Criteria andOutDataNotIn(List<String> values) {
            addCriterion("out_data not in", values, "outData");
            return (Criteria) this;
        }

        public Criteria andOutDataBetween(String value1, String value2) {
            addCriterion("out_data between", value1, value2, "outData");
            return (Criteria) this;
        }

        public Criteria andOutDataNotBetween(String value1, String value2) {
            addCriterion("out_data not between", value1, value2, "outData");
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

        public Criteria andSchoolIdIsNull() {
            addCriterion("school_id is null");
            return (Criteria) this;
        }

        public Criteria andSchoolIdIsNotNull() {
            addCriterion("school_id is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolIdEqualTo(Integer value) {
            addCriterion("school_id =", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdNotEqualTo(Integer value) {
            addCriterion("school_id <>", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdGreaterThan(Integer value) {
            addCriterion("school_id >", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("school_id >=", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdLessThan(Integer value) {
            addCriterion("school_id <", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdLessThanOrEqualTo(Integer value) {
            addCriterion("school_id <=", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdIn(List<Integer> values) {
            addCriterion("school_id in", values, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdNotIn(List<Integer> values) {
            addCriterion("school_id not in", values, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdBetween(Integer value1, Integer value2) {
            addCriterion("school_id between", value1, value2, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdNotBetween(Integer value1, Integer value2) {
            addCriterion("school_id not between", value1, value2, "schoolId");
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

        public Criteria andNextTitleIsNull() {
            addCriterion("next_title is null");
            return (Criteria) this;
        }

        public Criteria andNextTitleIsNotNull() {
            addCriterion("next_title is not null");
            return (Criteria) this;
        }

        public Criteria andNextTitleEqualTo(String value) {
            addCriterion("next_title =", value, "nextTitle");
            return (Criteria) this;
        }

        public Criteria andNextTitleNotEqualTo(String value) {
            addCriterion("next_title <>", value, "nextTitle");
            return (Criteria) this;
        }

        public Criteria andNextTitleGreaterThan(String value) {
            addCriterion("next_title >", value, "nextTitle");
            return (Criteria) this;
        }

        public Criteria andNextTitleGreaterThanOrEqualTo(String value) {
            addCriterion("next_title >=", value, "nextTitle");
            return (Criteria) this;
        }

        public Criteria andNextTitleLessThan(String value) {
            addCriterion("next_title <", value, "nextTitle");
            return (Criteria) this;
        }

        public Criteria andNextTitleLessThanOrEqualTo(String value) {
            addCriterion("next_title <=", value, "nextTitle");
            return (Criteria) this;
        }

        public Criteria andNextTitleLike(String value) {
            addCriterion("next_title like", value, "nextTitle");
            return (Criteria) this;
        }

        public Criteria andNextTitleNotLike(String value) {
            addCriterion("next_title not like", value, "nextTitle");
            return (Criteria) this;
        }

        public Criteria andNextTitleIn(List<String> values) {
            addCriterion("next_title in", values, "nextTitle");
            return (Criteria) this;
        }

        public Criteria andNextTitleNotIn(List<String> values) {
            addCriterion("next_title not in", values, "nextTitle");
            return (Criteria) this;
        }

        public Criteria andNextTitleBetween(String value1, String value2) {
            addCriterion("next_title between", value1, value2, "nextTitle");
            return (Criteria) this;
        }

        public Criteria andNextTitleNotBetween(String value1, String value2) {
            addCriterion("next_title not between", value1, value2, "nextTitle");
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

        public Criteria andIsdelIsNull() {
            addCriterion("isdel is null");
            return (Criteria) this;
        }

        public Criteria andIsdelIsNotNull() {
            addCriterion("isdel is not null");
            return (Criteria) this;
        }

        public Criteria andIsdelEqualTo(Byte value) {
            addCriterion("isdel =", value, "isdel");
            return (Criteria) this;
        }

        public Criteria andIsdelNotEqualTo(Byte value) {
            addCriterion("isdel <>", value, "isdel");
            return (Criteria) this;
        }

        public Criteria andIsdelGreaterThan(Byte value) {
            addCriterion("isdel >", value, "isdel");
            return (Criteria) this;
        }

        public Criteria andIsdelGreaterThanOrEqualTo(Byte value) {
            addCriterion("isdel >=", value, "isdel");
            return (Criteria) this;
        }

        public Criteria andIsdelLessThan(Byte value) {
            addCriterion("isdel <", value, "isdel");
            return (Criteria) this;
        }

        public Criteria andIsdelLessThanOrEqualTo(Byte value) {
            addCriterion("isdel <=", value, "isdel");
            return (Criteria) this;
        }

        public Criteria andIsdelIn(List<Byte> values) {
            addCriterion("isdel in", values, "isdel");
            return (Criteria) this;
        }

        public Criteria andIsdelNotIn(List<Byte> values) {
            addCriterion("isdel not in", values, "isdel");
            return (Criteria) this;
        }

        public Criteria andIsdelBetween(Byte value1, Byte value2) {
            addCriterion("isdel between", value1, value2, "isdel");
            return (Criteria) this;
        }

        public Criteria andIsdelNotBetween(Byte value1, Byte value2) {
            addCriterion("isdel not between", value1, value2, "isdel");
            return (Criteria) this;
        }

        public Criteria andCuidIsNull() {
            addCriterion("cuid is null");
            return (Criteria) this;
        }

        public Criteria andCuidIsNotNull() {
            addCriterion("cuid is not null");
            return (Criteria) this;
        }

        public Criteria andCuidEqualTo(Long value) {
            addCriterion("cuid =", value, "cuid");
            return (Criteria) this;
        }

        public Criteria andCuidNotEqualTo(Long value) {
            addCriterion("cuid <>", value, "cuid");
            return (Criteria) this;
        }

        public Criteria andCuidGreaterThan(Long value) {
            addCriterion("cuid >", value, "cuid");
            return (Criteria) this;
        }

        public Criteria andCuidGreaterThanOrEqualTo(Long value) {
            addCriterion("cuid >=", value, "cuid");
            return (Criteria) this;
        }

        public Criteria andCuidLessThan(Long value) {
            addCriterion("cuid <", value, "cuid");
            return (Criteria) this;
        }

        public Criteria andCuidLessThanOrEqualTo(Long value) {
            addCriterion("cuid <=", value, "cuid");
            return (Criteria) this;
        }

        public Criteria andCuidIn(List<Long> values) {
            addCriterion("cuid in", values, "cuid");
            return (Criteria) this;
        }

        public Criteria andCuidNotIn(List<Long> values) {
            addCriterion("cuid not in", values, "cuid");
            return (Criteria) this;
        }

        public Criteria andCuidBetween(Long value1, Long value2) {
            addCriterion("cuid between", value1, value2, "cuid");
            return (Criteria) this;
        }

        public Criteria andCuidNotBetween(Long value1, Long value2) {
            addCriterion("cuid not between", value1, value2, "cuid");
            return (Criteria) this;
        }

        public Criteria andMuidIsNull() {
            addCriterion("muid is null");
            return (Criteria) this;
        }

        public Criteria andMuidIsNotNull() {
            addCriterion("muid is not null");
            return (Criteria) this;
        }

        public Criteria andMuidEqualTo(Long value) {
            addCriterion("muid =", value, "muid");
            return (Criteria) this;
        }

        public Criteria andMuidNotEqualTo(Long value) {
            addCriterion("muid <>", value, "muid");
            return (Criteria) this;
        }

        public Criteria andMuidGreaterThan(Long value) {
            addCriterion("muid >", value, "muid");
            return (Criteria) this;
        }

        public Criteria andMuidGreaterThanOrEqualTo(Long value) {
            addCriterion("muid >=", value, "muid");
            return (Criteria) this;
        }

        public Criteria andMuidLessThan(Long value) {
            addCriterion("muid <", value, "muid");
            return (Criteria) this;
        }

        public Criteria andMuidLessThanOrEqualTo(Long value) {
            addCriterion("muid <=", value, "muid");
            return (Criteria) this;
        }

        public Criteria andMuidIn(List<Long> values) {
            addCriterion("muid in", values, "muid");
            return (Criteria) this;
        }

        public Criteria andMuidNotIn(List<Long> values) {
            addCriterion("muid not in", values, "muid");
            return (Criteria) this;
        }

        public Criteria andMuidBetween(Long value1, Long value2) {
            addCriterion("muid between", value1, value2, "muid");
            return (Criteria) this;
        }

        public Criteria andMuidNotBetween(Long value1, Long value2) {
            addCriterion("muid not between", value1, value2, "muid");
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