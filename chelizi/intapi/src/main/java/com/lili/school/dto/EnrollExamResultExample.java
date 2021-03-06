package com.lili.school.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EnrollExamResultExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EnrollExamResultExample() {
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

        public Criteria andTableNoIsNull() {
            addCriterion("tableNo is null");
            return (Criteria) this;
        }

        public Criteria andTableNoIsNotNull() {
            addCriterion("tableNo is not null");
            return (Criteria) this;
        }

        public Criteria andTableNoEqualTo(String value) {
            addCriterion("tableNo =", value, "tableNo");
            return (Criteria) this;
        }

        public Criteria andTableNoNotEqualTo(String value) {
            addCriterion("tableNo <>", value, "tableNo");
            return (Criteria) this;
        }

        public Criteria andTableNoGreaterThan(String value) {
            addCriterion("tableNo >", value, "tableNo");
            return (Criteria) this;
        }

        public Criteria andTableNoGreaterThanOrEqualTo(String value) {
            addCriterion("tableNo >=", value, "tableNo");
            return (Criteria) this;
        }

        public Criteria andTableNoLessThan(String value) {
            addCriterion("tableNo <", value, "tableNo");
            return (Criteria) this;
        }

        public Criteria andTableNoLessThanOrEqualTo(String value) {
            addCriterion("tableNo <=", value, "tableNo");
            return (Criteria) this;
        }

        public Criteria andTableNoLike(String value) {
            addCriterion("tableNo like", value, "tableNo");
            return (Criteria) this;
        }

        public Criteria andTableNoNotLike(String value) {
            addCriterion("tableNo not like", value, "tableNo");
            return (Criteria) this;
        }

        public Criteria andTableNoIn(List<String> values) {
            addCriterion("tableNo in", values, "tableNo");
            return (Criteria) this;
        }

        public Criteria andTableNoNotIn(List<String> values) {
            addCriterion("tableNo not in", values, "tableNo");
            return (Criteria) this;
        }

        public Criteria andTableNoBetween(String value1, String value2) {
            addCriterion("tableNo between", value1, value2, "tableNo");
            return (Criteria) this;
        }

        public Criteria andTableNoNotBetween(String value1, String value2) {
            addCriterion("tableNo not between", value1, value2, "tableNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoIsNull() {
            addCriterion("flowNo is null");
            return (Criteria) this;
        }

        public Criteria andFlowNoIsNotNull() {
            addCriterion("flowNo is not null");
            return (Criteria) this;
        }

        public Criteria andFlowNoEqualTo(Long value) {
            addCriterion("flowNo =", value, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoNotEqualTo(Long value) {
            addCriterion("flowNo <>", value, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoGreaterThan(Long value) {
            addCriterion("flowNo >", value, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoGreaterThanOrEqualTo(Long value) {
            addCriterion("flowNo >=", value, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoLessThan(Long value) {
            addCriterion("flowNo <", value, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoLessThanOrEqualTo(Long value) {
            addCriterion("flowNo <=", value, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoIn(List<Long> values) {
            addCriterion("flowNo in", values, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoNotIn(List<Long> values) {
            addCriterion("flowNo not in", values, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoBetween(Long value1, Long value2) {
            addCriterion("flowNo between", value1, value2, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoNotBetween(Long value1, Long value2) {
            addCriterion("flowNo not between", value1, value2, "flowNo");
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

        public Criteria andStudentIdIsNull() {
            addCriterion("studentId is null");
            return (Criteria) this;
        }

        public Criteria andStudentIdIsNotNull() {
            addCriterion("studentId is not null");
            return (Criteria) this;
        }

        public Criteria andStudentIdEqualTo(Long value) {
            addCriterion("studentId =", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdNotEqualTo(Long value) {
            addCriterion("studentId <>", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdGreaterThan(Long value) {
            addCriterion("studentId >", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdGreaterThanOrEqualTo(Long value) {
            addCriterion("studentId >=", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdLessThan(Long value) {
            addCriterion("studentId <", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdLessThanOrEqualTo(Long value) {
            addCriterion("studentId <=", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdIn(List<Long> values) {
            addCriterion("studentId in", values, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdNotIn(List<Long> values) {
            addCriterion("studentId not in", values, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdBetween(Long value1, Long value2) {
            addCriterion("studentId between", value1, value2, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdNotBetween(Long value1, Long value2) {
            addCriterion("studentId not between", value1, value2, "studentId");
            return (Criteria) this;
        }

        public Criteria andIdNumberIsNull() {
            addCriterion("idNumber is null");
            return (Criteria) this;
        }

        public Criteria andIdNumberIsNotNull() {
            addCriterion("idNumber is not null");
            return (Criteria) this;
        }

        public Criteria andIdNumberEqualTo(String value) {
            addCriterion("idNumber =", value, "idNumber");
            return (Criteria) this;
        }

        public Criteria andIdNumberNotEqualTo(String value) {
            addCriterion("idNumber <>", value, "idNumber");
            return (Criteria) this;
        }

        public Criteria andIdNumberGreaterThan(String value) {
            addCriterion("idNumber >", value, "idNumber");
            return (Criteria) this;
        }

        public Criteria andIdNumberGreaterThanOrEqualTo(String value) {
            addCriterion("idNumber >=", value, "idNumber");
            return (Criteria) this;
        }

        public Criteria andIdNumberLessThan(String value) {
            addCriterion("idNumber <", value, "idNumber");
            return (Criteria) this;
        }

        public Criteria andIdNumberLessThanOrEqualTo(String value) {
            addCriterion("idNumber <=", value, "idNumber");
            return (Criteria) this;
        }

        public Criteria andIdNumberLike(String value) {
            addCriterion("idNumber like", value, "idNumber");
            return (Criteria) this;
        }

        public Criteria andIdNumberNotLike(String value) {
            addCriterion("idNumber not like", value, "idNumber");
            return (Criteria) this;
        }

        public Criteria andIdNumberIn(List<String> values) {
            addCriterion("idNumber in", values, "idNumber");
            return (Criteria) this;
        }

        public Criteria andIdNumberNotIn(List<String> values) {
            addCriterion("idNumber not in", values, "idNumber");
            return (Criteria) this;
        }

        public Criteria andIdNumberBetween(String value1, String value2) {
            addCriterion("idNumber between", value1, value2, "idNumber");
            return (Criteria) this;
        }

        public Criteria andIdNumberNotBetween(String value1, String value2) {
            addCriterion("idNumber not between", value1, value2, "idNumber");
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

        public Criteria andSchoolNameIsNull() {
            addCriterion("schoolName is null");
            return (Criteria) this;
        }

        public Criteria andSchoolNameIsNotNull() {
            addCriterion("schoolName is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolNameEqualTo(String value) {
            addCriterion("schoolName =", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameNotEqualTo(String value) {
            addCriterion("schoolName <>", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameGreaterThan(String value) {
            addCriterion("schoolName >", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameGreaterThanOrEqualTo(String value) {
            addCriterion("schoolName >=", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameLessThan(String value) {
            addCriterion("schoolName <", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameLessThanOrEqualTo(String value) {
            addCriterion("schoolName <=", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameLike(String value) {
            addCriterion("schoolName like", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameNotLike(String value) {
            addCriterion("schoolName not like", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameIn(List<String> values) {
            addCriterion("schoolName in", values, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameNotIn(List<String> values) {
            addCriterion("schoolName not in", values, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameBetween(String value1, String value2) {
            addCriterion("schoolName between", value1, value2, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameNotBetween(String value1, String value2) {
            addCriterion("schoolName not between", value1, value2, "schoolName");
            return (Criteria) this;
        }

        public Criteria andExamDateIsNull() {
            addCriterion("examDate is null");
            return (Criteria) this;
        }

        public Criteria andExamDateIsNotNull() {
            addCriterion("examDate is not null");
            return (Criteria) this;
        }

        public Criteria andExamDateEqualTo(Date value) {
            addCriterion("examDate =", value, "examDate");
            return (Criteria) this;
        }

        public Criteria andExamDateNotEqualTo(Date value) {
            addCriterion("examDate <>", value, "examDate");
            return (Criteria) this;
        }

        public Criteria andExamDateGreaterThan(Date value) {
            addCriterion("examDate >", value, "examDate");
            return (Criteria) this;
        }

        public Criteria andExamDateGreaterThanOrEqualTo(Date value) {
            addCriterion("examDate >=", value, "examDate");
            return (Criteria) this;
        }

        public Criteria andExamDateLessThan(Date value) {
            addCriterion("examDate <", value, "examDate");
            return (Criteria) this;
        }

        public Criteria andExamDateLessThanOrEqualTo(Date value) {
            addCriterion("examDate <=", value, "examDate");
            return (Criteria) this;
        }

        public Criteria andExamDateIn(List<Date> values) {
            addCriterion("examDate in", values, "examDate");
            return (Criteria) this;
        }

        public Criteria andExamDateNotIn(List<Date> values) {
            addCriterion("examDate not in", values, "examDate");
            return (Criteria) this;
        }

        public Criteria andExamDateBetween(Date value1, Date value2) {
            addCriterion("examDate between", value1, value2, "examDate");
            return (Criteria) this;
        }

        public Criteria andExamDateNotBetween(Date value1, Date value2) {
            addCriterion("examDate not between", value1, value2, "examDate");
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

        public Criteria andScoreEqualTo(Integer value) {
            addCriterion("score =", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotEqualTo(Integer value) {
            addCriterion("score <>", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThan(Integer value) {
            addCriterion("score >", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("score >=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThan(Integer value) {
            addCriterion("score <", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThanOrEqualTo(Integer value) {
            addCriterion("score <=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreIn(List<Integer> values) {
            addCriterion("score in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotIn(List<Integer> values) {
            addCriterion("score not in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreBetween(Integer value1, Integer value2) {
            addCriterion("score between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotBetween(Integer value1, Integer value2) {
            addCriterion("score not between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andApplystateIsNull() {
            addCriterion("applystate is null");
            return (Criteria) this;
        }

        public Criteria andApplystateIsNotNull() {
            addCriterion("applystate is not null");
            return (Criteria) this;
        }

        public Criteria andApplystateEqualTo(Integer value) {
            addCriterion("applystate =", value, "applystate");
            return (Criteria) this;
        }

        public Criteria andApplystateNotEqualTo(Integer value) {
            addCriterion("applystate <>", value, "applystate");
            return (Criteria) this;
        }

        public Criteria andApplystateGreaterThan(Integer value) {
            addCriterion("applystate >", value, "applystate");
            return (Criteria) this;
        }

        public Criteria andApplystateGreaterThanOrEqualTo(Integer value) {
            addCriterion("applystate >=", value, "applystate");
            return (Criteria) this;
        }

        public Criteria andApplystateLessThan(Integer value) {
            addCriterion("applystate <", value, "applystate");
            return (Criteria) this;
        }

        public Criteria andApplystateLessThanOrEqualTo(Integer value) {
            addCriterion("applystate <=", value, "applystate");
            return (Criteria) this;
        }

        public Criteria andApplystateIn(List<Integer> values) {
            addCriterion("applystate in", values, "applystate");
            return (Criteria) this;
        }

        public Criteria andApplystateNotIn(List<Integer> values) {
            addCriterion("applystate not in", values, "applystate");
            return (Criteria) this;
        }

        public Criteria andApplystateBetween(Integer value1, Integer value2) {
            addCriterion("applystate between", value1, value2, "applystate");
            return (Criteria) this;
        }

        public Criteria andApplystateNotBetween(Integer value1, Integer value2) {
            addCriterion("applystate not between", value1, value2, "applystate");
            return (Criteria) this;
        }

        public Criteria andImportStateIsNull() {
            addCriterion("importState is null");
            return (Criteria) this;
        }

        public Criteria andImportStateIsNotNull() {
            addCriterion("importState is not null");
            return (Criteria) this;
        }

        public Criteria andImportStateEqualTo(Integer value) {
            addCriterion("importState =", value, "importState");
            return (Criteria) this;
        }

        public Criteria andImportStateNotEqualTo(Integer value) {
            addCriterion("importState <>", value, "importState");
            return (Criteria) this;
        }

        public Criteria andImportStateGreaterThan(Integer value) {
            addCriterion("importState >", value, "importState");
            return (Criteria) this;
        }

        public Criteria andImportStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("importState >=", value, "importState");
            return (Criteria) this;
        }

        public Criteria andImportStateLessThan(Integer value) {
            addCriterion("importState <", value, "importState");
            return (Criteria) this;
        }

        public Criteria andImportStateLessThanOrEqualTo(Integer value) {
            addCriterion("importState <=", value, "importState");
            return (Criteria) this;
        }

        public Criteria andImportStateIn(List<Integer> values) {
            addCriterion("importState in", values, "importState");
            return (Criteria) this;
        }

        public Criteria andImportStateNotIn(List<Integer> values) {
            addCriterion("importState not in", values, "importState");
            return (Criteria) this;
        }

        public Criteria andImportStateBetween(Integer value1, Integer value2) {
            addCriterion("importState between", value1, value2, "importState");
            return (Criteria) this;
        }

        public Criteria andImportStateNotBetween(Integer value1, Integer value2) {
            addCriterion("importState not between", value1, value2, "importState");
            return (Criteria) this;
        }

        public Criteria andSubjectIdIsNull() {
            addCriterion("subjectId is null");
            return (Criteria) this;
        }

        public Criteria andSubjectIdIsNotNull() {
            addCriterion("subjectId is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectIdEqualTo(Integer value) {
            addCriterion("subjectId =", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdNotEqualTo(Integer value) {
            addCriterion("subjectId <>", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdGreaterThan(Integer value) {
            addCriterion("subjectId >", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("subjectId >=", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdLessThan(Integer value) {
            addCriterion("subjectId <", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdLessThanOrEqualTo(Integer value) {
            addCriterion("subjectId <=", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdIn(List<Integer> values) {
            addCriterion("subjectId in", values, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdNotIn(List<Integer> values) {
            addCriterion("subjectId not in", values, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdBetween(Integer value1, Integer value2) {
            addCriterion("subjectId between", value1, value2, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdNotBetween(Integer value1, Integer value2) {
            addCriterion("subjectId not between", value1, value2, "subjectId");
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