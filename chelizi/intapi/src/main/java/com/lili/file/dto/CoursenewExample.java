package com.lili.file.dto;

import java.util.ArrayList;
import java.util.List;

public class CoursenewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CoursenewExample() {
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

        public Criteria andCoursenewnoIsNull() {
            addCriterion("coursenewNo is null");
            return (Criteria) this;
        }

        public Criteria andCoursenewnoIsNotNull() {
            addCriterion("coursenewNo is not null");
            return (Criteria) this;
        }

        public Criteria andCoursenewnoEqualTo(String value) {
            addCriterion("coursenewNo =", value, "coursenewno");
            return (Criteria) this;
        }

        public Criteria andCoursenewnoNotEqualTo(String value) {
            addCriterion("coursenewNo <>", value, "coursenewno");
            return (Criteria) this;
        }

        public Criteria andCoursenewnoGreaterThan(String value) {
            addCriterion("coursenewNo >", value, "coursenewno");
            return (Criteria) this;
        }

        public Criteria andCoursenewnoGreaterThanOrEqualTo(String value) {
            addCriterion("coursenewNo >=", value, "coursenewno");
            return (Criteria) this;
        }

        public Criteria andCoursenewnoLessThan(String value) {
            addCriterion("coursenewNo <", value, "coursenewno");
            return (Criteria) this;
        }

        public Criteria andCoursenewnoLessThanOrEqualTo(String value) {
            addCriterion("coursenewNo <=", value, "coursenewno");
            return (Criteria) this;
        }

        public Criteria andCoursenewnoLike(String value) {
            addCriterion("coursenewNo like", value, "coursenewno");
            return (Criteria) this;
        }

        public Criteria andCoursenewnoNotLike(String value) {
            addCriterion("coursenewNo not like", value, "coursenewno");
            return (Criteria) this;
        }

        public Criteria andCoursenewnoIn(List<String> values) {
            addCriterion("coursenewNo in", values, "coursenewno");
            return (Criteria) this;
        }

        public Criteria andCoursenewnoNotIn(List<String> values) {
            addCriterion("coursenewNo not in", values, "coursenewno");
            return (Criteria) this;
        }

        public Criteria andCoursenewnoBetween(String value1, String value2) {
            addCriterion("coursenewNo between", value1, value2, "coursenewno");
            return (Criteria) this;
        }

        public Criteria andCoursenewnoNotBetween(String value1, String value2) {
            addCriterion("coursenewNo not between", value1, value2, "coursenewno");
            return (Criteria) this;
        }

        public Criteria andCoursenewnameIsNull() {
            addCriterion("coursenewName is null");
            return (Criteria) this;
        }

        public Criteria andCoursenewnameIsNotNull() {
            addCriterion("coursenewName is not null");
            return (Criteria) this;
        }

        public Criteria andCoursenewnameEqualTo(String value) {
            addCriterion("coursenewName =", value, "coursenewname");
            return (Criteria) this;
        }

        public Criteria andCoursenewnameNotEqualTo(String value) {
            addCriterion("coursenewName <>", value, "coursenewname");
            return (Criteria) this;
        }

        public Criteria andCoursenewnameGreaterThan(String value) {
            addCriterion("coursenewName >", value, "coursenewname");
            return (Criteria) this;
        }

        public Criteria andCoursenewnameGreaterThanOrEqualTo(String value) {
            addCriterion("coursenewName >=", value, "coursenewname");
            return (Criteria) this;
        }

        public Criteria andCoursenewnameLessThan(String value) {
            addCriterion("coursenewName <", value, "coursenewname");
            return (Criteria) this;
        }

        public Criteria andCoursenewnameLessThanOrEqualTo(String value) {
            addCriterion("coursenewName <=", value, "coursenewname");
            return (Criteria) this;
        }

        public Criteria andCoursenewnameLike(String value) {
            addCriterion("coursenewName like", value, "coursenewname");
            return (Criteria) this;
        }

        public Criteria andCoursenewnameNotLike(String value) {
            addCriterion("coursenewName not like", value, "coursenewname");
            return (Criteria) this;
        }

        public Criteria andCoursenewnameIn(List<String> values) {
            addCriterion("coursenewName in", values, "coursenewname");
            return (Criteria) this;
        }

        public Criteria andCoursenewnameNotIn(List<String> values) {
            addCriterion("coursenewName not in", values, "coursenewname");
            return (Criteria) this;
        }

        public Criteria andCoursenewnameBetween(String value1, String value2) {
            addCriterion("coursenewName between", value1, value2, "coursenewname");
            return (Criteria) this;
        }

        public Criteria andCoursenewnameNotBetween(String value1, String value2) {
            addCriterion("coursenewName not between", value1, value2, "coursenewname");
            return (Criteria) this;
        }

        public Criteria andCoursetemidIsNull() {
            addCriterion("courseTemId is null");
            return (Criteria) this;
        }

        public Criteria andCoursetemidIsNotNull() {
            addCriterion("courseTemId is not null");
            return (Criteria) this;
        }

        public Criteria andCoursetemidEqualTo(Integer value) {
            addCriterion("courseTemId =", value, "coursetemid");
            return (Criteria) this;
        }

        public Criteria andCoursetemidNotEqualTo(Integer value) {
            addCriterion("courseTemId <>", value, "coursetemid");
            return (Criteria) this;
        }

        public Criteria andCoursetemidGreaterThan(Integer value) {
            addCriterion("courseTemId >", value, "coursetemid");
            return (Criteria) this;
        }

        public Criteria andCoursetemidGreaterThanOrEqualTo(Integer value) {
            addCriterion("courseTemId >=", value, "coursetemid");
            return (Criteria) this;
        }

        public Criteria andCoursetemidLessThan(Integer value) {
            addCriterion("courseTemId <", value, "coursetemid");
            return (Criteria) this;
        }

        public Criteria andCoursetemidLessThanOrEqualTo(Integer value) {
            addCriterion("courseTemId <=", value, "coursetemid");
            return (Criteria) this;
        }

        public Criteria andCoursetemidIn(List<Integer> values) {
            addCriterion("courseTemId in", values, "coursetemid");
            return (Criteria) this;
        }

        public Criteria andCoursetemidNotIn(List<Integer> values) {
            addCriterion("courseTemId not in", values, "coursetemid");
            return (Criteria) this;
        }

        public Criteria andCoursetemidBetween(Integer value1, Integer value2) {
            addCriterion("courseTemId between", value1, value2, "coursetemid");
            return (Criteria) this;
        }

        public Criteria andCoursetemidNotBetween(Integer value1, Integer value2) {
            addCriterion("courseTemId not between", value1, value2, "coursetemid");
            return (Criteria) this;
        }

        public Criteria andLevel1IsNull() {
            addCriterion("level1 is null");
            return (Criteria) this;
        }

        public Criteria andLevel1IsNotNull() {
            addCriterion("level1 is not null");
            return (Criteria) this;
        }

        public Criteria andLevel1EqualTo(String value) {
            addCriterion("level1 =", value, "level1");
            return (Criteria) this;
        }

        public Criteria andLevel1NotEqualTo(String value) {
            addCriterion("level1 <>", value, "level1");
            return (Criteria) this;
        }

        public Criteria andLevel1GreaterThan(String value) {
            addCriterion("level1 >", value, "level1");
            return (Criteria) this;
        }

        public Criteria andLevel1GreaterThanOrEqualTo(String value) {
            addCriterion("level1 >=", value, "level1");
            return (Criteria) this;
        }

        public Criteria andLevel1LessThan(String value) {
            addCriterion("level1 <", value, "level1");
            return (Criteria) this;
        }

        public Criteria andLevel1LessThanOrEqualTo(String value) {
            addCriterion("level1 <=", value, "level1");
            return (Criteria) this;
        }

        public Criteria andLevel1Like(String value) {
            addCriterion("level1 like", value, "level1");
            return (Criteria) this;
        }

        public Criteria andLevel1NotLike(String value) {
            addCriterion("level1 not like", value, "level1");
            return (Criteria) this;
        }

        public Criteria andLevel1In(List<String> values) {
            addCriterion("level1 in", values, "level1");
            return (Criteria) this;
        }

        public Criteria andLevel1NotIn(List<String> values) {
            addCriterion("level1 not in", values, "level1");
            return (Criteria) this;
        }

        public Criteria andLevel1Between(String value1, String value2) {
            addCriterion("level1 between", value1, value2, "level1");
            return (Criteria) this;
        }

        public Criteria andLevel1NotBetween(String value1, String value2) {
            addCriterion("level1 not between", value1, value2, "level1");
            return (Criteria) this;
        }

        public Criteria andLevel2IsNull() {
            addCriterion("level2 is null");
            return (Criteria) this;
        }

        public Criteria andLevel2IsNotNull() {
            addCriterion("level2 is not null");
            return (Criteria) this;
        }

        public Criteria andLevel2EqualTo(String value) {
            addCriterion("level2 =", value, "level2");
            return (Criteria) this;
        }

        public Criteria andLevel2NotEqualTo(String value) {
            addCriterion("level2 <>", value, "level2");
            return (Criteria) this;
        }

        public Criteria andLevel2GreaterThan(String value) {
            addCriterion("level2 >", value, "level2");
            return (Criteria) this;
        }

        public Criteria andLevel2GreaterThanOrEqualTo(String value) {
            addCriterion("level2 >=", value, "level2");
            return (Criteria) this;
        }

        public Criteria andLevel2LessThan(String value) {
            addCriterion("level2 <", value, "level2");
            return (Criteria) this;
        }

        public Criteria andLevel2LessThanOrEqualTo(String value) {
            addCriterion("level2 <=", value, "level2");
            return (Criteria) this;
        }

        public Criteria andLevel2Like(String value) {
            addCriterion("level2 like", value, "level2");
            return (Criteria) this;
        }

        public Criteria andLevel2NotLike(String value) {
            addCriterion("level2 not like", value, "level2");
            return (Criteria) this;
        }

        public Criteria andLevel2In(List<String> values) {
            addCriterion("level2 in", values, "level2");
            return (Criteria) this;
        }

        public Criteria andLevel2NotIn(List<String> values) {
            addCriterion("level2 not in", values, "level2");
            return (Criteria) this;
        }

        public Criteria andLevel2Between(String value1, String value2) {
            addCriterion("level2 between", value1, value2, "level2");
            return (Criteria) this;
        }

        public Criteria andLevel2NotBetween(String value1, String value2) {
            addCriterion("level2 not between", value1, value2, "level2");
            return (Criteria) this;
        }

        public Criteria andSubjectIsNull() {
            addCriterion("subject is null");
            return (Criteria) this;
        }

        public Criteria andSubjectIsNotNull() {
            addCriterion("subject is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectEqualTo(String value) {
            addCriterion("subject =", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotEqualTo(String value) {
            addCriterion("subject <>", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectGreaterThan(String value) {
            addCriterion("subject >", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectGreaterThanOrEqualTo(String value) {
            addCriterion("subject >=", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectLessThan(String value) {
            addCriterion("subject <", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectLessThanOrEqualTo(String value) {
            addCriterion("subject <=", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectLike(String value) {
            addCriterion("subject like", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotLike(String value) {
            addCriterion("subject not like", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectIn(List<String> values) {
            addCriterion("subject in", values, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotIn(List<String> values) {
            addCriterion("subject not in", values, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectBetween(String value1, String value2) {
            addCriterion("subject between", value1, value2, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotBetween(String value1, String value2) {
            addCriterion("subject not between", value1, value2, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectidIsNull() {
            addCriterion("subjectId is null");
            return (Criteria) this;
        }

        public Criteria andSubjectidIsNotNull() {
            addCriterion("subjectId is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectidEqualTo(Integer value) {
            addCriterion("subjectId =", value, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidNotEqualTo(Integer value) {
            addCriterion("subjectId <>", value, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidGreaterThan(Integer value) {
            addCriterion("subjectId >", value, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidGreaterThanOrEqualTo(Integer value) {
            addCriterion("subjectId >=", value, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidLessThan(Integer value) {
            addCriterion("subjectId <", value, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidLessThanOrEqualTo(Integer value) {
            addCriterion("subjectId <=", value, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidIn(List<Integer> values) {
            addCriterion("subjectId in", values, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidNotIn(List<Integer> values) {
            addCriterion("subjectId not in", values, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidBetween(Integer value1, Integer value2) {
            addCriterion("subjectId between", value1, value2, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidNotBetween(Integer value1, Integer value2) {
            addCriterion("subjectId not between", value1, value2, "subjectid");
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

        public Criteria andDescriptonIsNull() {
            addCriterion("descripton is null");
            return (Criteria) this;
        }

        public Criteria andDescriptonIsNotNull() {
            addCriterion("descripton is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptonEqualTo(String value) {
            addCriterion("descripton =", value, "descripton");
            return (Criteria) this;
        }

        public Criteria andDescriptonNotEqualTo(String value) {
            addCriterion("descripton <>", value, "descripton");
            return (Criteria) this;
        }

        public Criteria andDescriptonGreaterThan(String value) {
            addCriterion("descripton >", value, "descripton");
            return (Criteria) this;
        }

        public Criteria andDescriptonGreaterThanOrEqualTo(String value) {
            addCriterion("descripton >=", value, "descripton");
            return (Criteria) this;
        }

        public Criteria andDescriptonLessThan(String value) {
            addCriterion("descripton <", value, "descripton");
            return (Criteria) this;
        }

        public Criteria andDescriptonLessThanOrEqualTo(String value) {
            addCriterion("descripton <=", value, "descripton");
            return (Criteria) this;
        }

        public Criteria andDescriptonLike(String value) {
            addCriterion("descripton like", value, "descripton");
            return (Criteria) this;
        }

        public Criteria andDescriptonNotLike(String value) {
            addCriterion("descripton not like", value, "descripton");
            return (Criteria) this;
        }

        public Criteria andDescriptonIn(List<String> values) {
            addCriterion("descripton in", values, "descripton");
            return (Criteria) this;
        }

        public Criteria andDescriptonNotIn(List<String> values) {
            addCriterion("descripton not in", values, "descripton");
            return (Criteria) this;
        }

        public Criteria andDescriptonBetween(String value1, String value2) {
            addCriterion("descripton between", value1, value2, "descripton");
            return (Criteria) this;
        }

        public Criteria andDescriptonNotBetween(String value1, String value2) {
            addCriterion("descripton not between", value1, value2, "descripton");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andPeriodIsNull() {
            addCriterion("period is null");
            return (Criteria) this;
        }

        public Criteria andPeriodIsNotNull() {
            addCriterion("period is not null");
            return (Criteria) this;
        }

        public Criteria andPeriodEqualTo(String value) {
            addCriterion("period =", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodNotEqualTo(String value) {
            addCriterion("period <>", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodGreaterThan(String value) {
            addCriterion("period >", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodGreaterThanOrEqualTo(String value) {
            addCriterion("period >=", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodLessThan(String value) {
            addCriterion("period <", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodLessThanOrEqualTo(String value) {
            addCriterion("period <=", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodLike(String value) {
            addCriterion("period like", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodNotLike(String value) {
            addCriterion("period not like", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodIn(List<String> values) {
            addCriterion("period in", values, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodNotIn(List<String> values) {
            addCriterion("period not in", values, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodBetween(String value1, String value2) {
            addCriterion("period between", value1, value2, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodNotBetween(String value1, String value2) {
            addCriterion("period not between", value1, value2, "period");
            return (Criteria) this;
        }

        public Criteria andDrtypeIsNull() {
            addCriterion("drType is null");
            return (Criteria) this;
        }

        public Criteria andDrtypeIsNotNull() {
            addCriterion("drType is not null");
            return (Criteria) this;
        }

        public Criteria andDrtypeEqualTo(Byte value) {
            addCriterion("drType =", value, "drtype");
            return (Criteria) this;
        }

        public Criteria andDrtypeNotEqualTo(Byte value) {
            addCriterion("drType <>", value, "drtype");
            return (Criteria) this;
        }

        public Criteria andDrtypeGreaterThan(Byte value) {
            addCriterion("drType >", value, "drtype");
            return (Criteria) this;
        }

        public Criteria andDrtypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("drType >=", value, "drtype");
            return (Criteria) this;
        }

        public Criteria andDrtypeLessThan(Byte value) {
            addCriterion("drType <", value, "drtype");
            return (Criteria) this;
        }

        public Criteria andDrtypeLessThanOrEqualTo(Byte value) {
            addCriterion("drType <=", value, "drtype");
            return (Criteria) this;
        }

        public Criteria andDrtypeIn(List<Byte> values) {
            addCriterion("drType in", values, "drtype");
            return (Criteria) this;
        }

        public Criteria andDrtypeNotIn(List<Byte> values) {
            addCriterion("drType not in", values, "drtype");
            return (Criteria) this;
        }

        public Criteria andDrtypeBetween(Byte value1, Byte value2) {
            addCriterion("drType between", value1, value2, "drtype");
            return (Criteria) this;
        }

        public Criteria andDrtypeNotBetween(Byte value1, Byte value2) {
            addCriterion("drType not between", value1, value2, "drtype");
            return (Criteria) this;
        }

        public Criteria andTagsIsNull() {
            addCriterion("tags is null");
            return (Criteria) this;
        }

        public Criteria andTagsIsNotNull() {
            addCriterion("tags is not null");
            return (Criteria) this;
        }

        public Criteria andTagsEqualTo(String value) {
            addCriterion("tags =", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsNotEqualTo(String value) {
            addCriterion("tags <>", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsGreaterThan(String value) {
            addCriterion("tags >", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsGreaterThanOrEqualTo(String value) {
            addCriterion("tags >=", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsLessThan(String value) {
            addCriterion("tags <", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsLessThanOrEqualTo(String value) {
            addCriterion("tags <=", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsLike(String value) {
            addCriterion("tags like", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsNotLike(String value) {
            addCriterion("tags not like", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsIn(List<String> values) {
            addCriterion("tags in", values, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsNotIn(List<String> values) {
            addCriterion("tags not in", values, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsBetween(String value1, String value2) {
            addCriterion("tags between", value1, value2, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsNotBetween(String value1, String value2) {
            addCriterion("tags not between", value1, value2, "tags");
            return (Criteria) this;
        }

        public Criteria andPrice1IsNull() {
            addCriterion("price1 is null");
            return (Criteria) this;
        }

        public Criteria andPrice1IsNotNull() {
            addCriterion("price1 is not null");
            return (Criteria) this;
        }

        public Criteria andPrice1EqualTo(Integer value) {
            addCriterion("price1 =", value, "price1");
            return (Criteria) this;
        }

        public Criteria andPrice1NotEqualTo(Integer value) {
            addCriterion("price1 <>", value, "price1");
            return (Criteria) this;
        }

        public Criteria andPrice1GreaterThan(Integer value) {
            addCriterion("price1 >", value, "price1");
            return (Criteria) this;
        }

        public Criteria andPrice1GreaterThanOrEqualTo(Integer value) {
            addCriterion("price1 >=", value, "price1");
            return (Criteria) this;
        }

        public Criteria andPrice1LessThan(Integer value) {
            addCriterion("price1 <", value, "price1");
            return (Criteria) this;
        }

        public Criteria andPrice1LessThanOrEqualTo(Integer value) {
            addCriterion("price1 <=", value, "price1");
            return (Criteria) this;
        }

        public Criteria andPrice1In(List<Integer> values) {
            addCriterion("price1 in", values, "price1");
            return (Criteria) this;
        }

        public Criteria andPrice1NotIn(List<Integer> values) {
            addCriterion("price1 not in", values, "price1");
            return (Criteria) this;
        }

        public Criteria andPrice1Between(Integer value1, Integer value2) {
            addCriterion("price1 between", value1, value2, "price1");
            return (Criteria) this;
        }

        public Criteria andPrice1NotBetween(Integer value1, Integer value2) {
            addCriterion("price1 not between", value1, value2, "price1");
            return (Criteria) this;
        }

        public Criteria andPrice2IsNull() {
            addCriterion("price2 is null");
            return (Criteria) this;
        }

        public Criteria andPrice2IsNotNull() {
            addCriterion("price2 is not null");
            return (Criteria) this;
        }

        public Criteria andPrice2EqualTo(Integer value) {
            addCriterion("price2 =", value, "price2");
            return (Criteria) this;
        }

        public Criteria andPrice2NotEqualTo(Integer value) {
            addCriterion("price2 <>", value, "price2");
            return (Criteria) this;
        }

        public Criteria andPrice2GreaterThan(Integer value) {
            addCriterion("price2 >", value, "price2");
            return (Criteria) this;
        }

        public Criteria andPrice2GreaterThanOrEqualTo(Integer value) {
            addCriterion("price2 >=", value, "price2");
            return (Criteria) this;
        }

        public Criteria andPrice2LessThan(Integer value) {
            addCriterion("price2 <", value, "price2");
            return (Criteria) this;
        }

        public Criteria andPrice2LessThanOrEqualTo(Integer value) {
            addCriterion("price2 <=", value, "price2");
            return (Criteria) this;
        }

        public Criteria andPrice2In(List<Integer> values) {
            addCriterion("price2 in", values, "price2");
            return (Criteria) this;
        }

        public Criteria andPrice2NotIn(List<Integer> values) {
            addCriterion("price2 not in", values, "price2");
            return (Criteria) this;
        }

        public Criteria andPrice2Between(Integer value1, Integer value2) {
            addCriterion("price2 between", value1, value2, "price2");
            return (Criteria) this;
        }

        public Criteria andPrice2NotBetween(Integer value1, Integer value2) {
            addCriterion("price2 not between", value1, value2, "price2");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
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

        public Criteria andOtypesIsNull() {
            addCriterion("otypes is null");
            return (Criteria) this;
        }

        public Criteria andOtypesIsNotNull() {
            addCriterion("otypes is not null");
            return (Criteria) this;
        }

        public Criteria andOtypesEqualTo(String value) {
            addCriterion("otypes =", value, "otypes");
            return (Criteria) this;
        }

        public Criteria andOtypesNotEqualTo(String value) {
            addCriterion("otypes <>", value, "otypes");
            return (Criteria) this;
        }

        public Criteria andOtypesGreaterThan(String value) {
            addCriterion("otypes >", value, "otypes");
            return (Criteria) this;
        }

        public Criteria andOtypesGreaterThanOrEqualTo(String value) {
            addCriterion("otypes >=", value, "otypes");
            return (Criteria) this;
        }

        public Criteria andOtypesLessThan(String value) {
            addCriterion("otypes <", value, "otypes");
            return (Criteria) this;
        }

        public Criteria andOtypesLessThanOrEqualTo(String value) {
            addCriterion("otypes <=", value, "otypes");
            return (Criteria) this;
        }

        public Criteria andOtypesLike(String value) {
            addCriterion("otypes like", value, "otypes");
            return (Criteria) this;
        }

        public Criteria andOtypesNotLike(String value) {
            addCriterion("otypes not like", value, "otypes");
            return (Criteria) this;
        }

        public Criteria andOtypesIn(List<String> values) {
            addCriterion("otypes in", values, "otypes");
            return (Criteria) this;
        }

        public Criteria andOtypesNotIn(List<String> values) {
            addCriterion("otypes not in", values, "otypes");
            return (Criteria) this;
        }

        public Criteria andOtypesBetween(String value1, String value2) {
            addCriterion("otypes between", value1, value2, "otypes");
            return (Criteria) this;
        }

        public Criteria andOtypesNotBetween(String value1, String value2) {
            addCriterion("otypes not between", value1, value2, "otypes");
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