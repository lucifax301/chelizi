package com.lili.order.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentClassExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;
    
    protected int pageSize;
    
    protected int pageNo;
    
    

    public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public StudentClassExample() {
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

        public Criteria andOrderIdIsNull() {
            addCriterion("orderId is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("orderId is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("orderId =", value, "orderId");
            return (Criteria) this;
        }
        
        public Criteria andOrderDirectEqualTo(Integer value) {
            addCriterion(" direct =", value, "direct");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(String value) {
            addCriterion("orderId <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(String value) {
            addCriterion("orderId >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("orderId >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(String value) {
            addCriterion("orderId <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(String value) {
            addCriterion("orderId <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLike(String value) {
            addCriterion("orderId like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotLike(String value) {
            addCriterion("orderId not like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<String> values) {
            addCriterion("orderId in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<String> values) {
            addCriterion("orderId not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(String value1, String value2) {
            addCriterion("orderId between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(String value1, String value2) {
            addCriterion("orderId not between", value1, value2, "orderId");
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

        public Criteria andStuNameIsNull() {
            addCriterion("stuName is null");
            return (Criteria) this;
        }

        public Criteria andStuNameIsNotNull() {
            addCriterion("stuName is not null");
            return (Criteria) this;
        }

        public Criteria andStuNameEqualTo(String value) {
            addCriterion("stuName =", value, "stuName");
            return (Criteria) this;
        }

        public Criteria andStuNameNotEqualTo(String value) {
            addCriterion("stuName <>", value, "stuName");
            return (Criteria) this;
        }

        public Criteria andStuNameGreaterThan(String value) {
            addCriterion("stuName >", value, "stuName");
            return (Criteria) this;
        }

        public Criteria andStuNameGreaterThanOrEqualTo(String value) {
            addCriterion("stuName >=", value, "stuName");
            return (Criteria) this;
        }

        public Criteria andStuNameLessThan(String value) {
            addCriterion("stuName <", value, "stuName");
            return (Criteria) this;
        }

        public Criteria andStuNameLessThanOrEqualTo(String value) {
            addCriterion("stuName <=", value, "stuName");
            return (Criteria) this;
        }

        public Criteria andStuNameLike(String value) {
            addCriterion("stuName like", value, "stuName");
            return (Criteria) this;
        }

        public Criteria andStuNameNotLike(String value) {
            addCriterion("stuName not like", value, "stuName");
            return (Criteria) this;
        }

        public Criteria andStuNameIn(List<String> values) {
            addCriterion("stuName in", values, "stuName");
            return (Criteria) this;
        }

        public Criteria andStuNameNotIn(List<String> values) {
            addCriterion("stuName not in", values, "stuName");
            return (Criteria) this;
        }

        public Criteria andStuNameBetween(String value1, String value2) {
            addCriterion("stuName between", value1, value2, "stuName");
            return (Criteria) this;
        }

        public Criteria andStuNameNotBetween(String value1, String value2) {
            addCriterion("stuName not between", value1, value2, "stuName");
            return (Criteria) this;
        }

        public Criteria andStuImgIsNull() {
            addCriterion("stuImg is null");
            return (Criteria) this;
        }

        public Criteria andStuImgIsNotNull() {
            addCriterion("stuImg is not null");
            return (Criteria) this;
        }

        public Criteria andStuImgEqualTo(String value) {
            addCriterion("stuImg =", value, "stuImg");
            return (Criteria) this;
        }

        public Criteria andStuImgNotEqualTo(String value) {
            addCriterion("stuImg <>", value, "stuImg");
            return (Criteria) this;
        }

        public Criteria andStuImgGreaterThan(String value) {
            addCriterion("stuImg >", value, "stuImg");
            return (Criteria) this;
        }

        public Criteria andStuImgGreaterThanOrEqualTo(String value) {
            addCriterion("stuImg >=", value, "stuImg");
            return (Criteria) this;
        }

        public Criteria andStuImgLessThan(String value) {
            addCriterion("stuImg <", value, "stuImg");
            return (Criteria) this;
        }

        public Criteria andStuImgLessThanOrEqualTo(String value) {
            addCriterion("stuImg <=", value, "stuImg");
            return (Criteria) this;
        }

        public Criteria andStuImgLike(String value) {
            addCriterion("stuImg like", value, "stuImg");
            return (Criteria) this;
        }

        public Criteria andStuImgNotLike(String value) {
            addCriterion("stuImg not like", value, "stuImg");
            return (Criteria) this;
        }

        public Criteria andStuImgIn(List<String> values) {
            addCriterion("stuImg in", values, "stuImg");
            return (Criteria) this;
        }

        public Criteria andStuImgNotIn(List<String> values) {
            addCriterion("stuImg not in", values, "stuImg");
            return (Criteria) this;
        }

        public Criteria andStuImgBetween(String value1, String value2) {
            addCriterion("stuImg between", value1, value2, "stuImg");
            return (Criteria) this;
        }

        public Criteria andStuImgNotBetween(String value1, String value2) {
            addCriterion("stuImg not between", value1, value2, "stuImg");
            return (Criteria) this;
        }

        public Criteria andStuMobileIsNull() {
            addCriterion("stuMobile is null");
            return (Criteria) this;
        }

        public Criteria andStuMobileIsNotNull() {
            addCriterion("stuMobile is not null");
            return (Criteria) this;
        }

        public Criteria andStuMobileEqualTo(String value) {
            addCriterion("stuMobile =", value, "stuMobile");
            return (Criteria) this;
        }

        public Criteria andStuMobileNotEqualTo(String value) {
            addCriterion("stuMobile <>", value, "stuMobile");
            return (Criteria) this;
        }

        public Criteria andStuMobileGreaterThan(String value) {
            addCriterion("stuMobile >", value, "stuMobile");
            return (Criteria) this;
        }

        public Criteria andStuMobileGreaterThanOrEqualTo(String value) {
            addCriterion("stuMobile >=", value, "stuMobile");
            return (Criteria) this;
        }

        public Criteria andStuMobileLessThan(String value) {
            addCriterion("stuMobile <", value, "stuMobile");
            return (Criteria) this;
        }

        public Criteria andStuMobileLessThanOrEqualTo(String value) {
            addCriterion("stuMobile <=", value, "stuMobile");
            return (Criteria) this;
        }

        public Criteria andStuMobileLike(String value) {
            addCriterion("stuMobile like", value, "stuMobile");
            return (Criteria) this;
        }

        public Criteria andStuMobileNotLike(String value) {
            addCriterion("stuMobile not like", value, "stuMobile");
            return (Criteria) this;
        }

        public Criteria andStuMobileIn(List<String> values) {
            addCriterion("stuMobile in", values, "stuMobile");
            return (Criteria) this;
        }

        public Criteria andStuMobileNotIn(List<String> values) {
            addCriterion("stuMobile not in", values, "stuMobile");
            return (Criteria) this;
        }

        public Criteria andStuMobileBetween(String value1, String value2) {
            addCriterion("stuMobile between", value1, value2, "stuMobile");
            return (Criteria) this;
        }

        public Criteria andStuMobileNotBetween(String value1, String value2) {
            addCriterion("stuMobile not between", value1, value2, "stuMobile");
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

        public Criteria andCourseIdIsNull() {
            addCriterion("courseId is null");
            return (Criteria) this;
        }

        public Criteria andCourseIdIsNotNull() {
            addCriterion("courseId is not null");
            return (Criteria) this;
        }

        public Criteria andCourseIdEqualTo(Integer value) {
            addCriterion("courseId =", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotEqualTo(Integer value) {
            addCriterion("courseId <>", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdGreaterThan(Integer value) {
            addCriterion("courseId >", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("courseId >=", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdLessThan(Integer value) {
            addCriterion("courseId <", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdLessThanOrEqualTo(Integer value) {
            addCriterion("courseId <=", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdIn(List<Integer> values) {
            addCriterion("courseId in", values, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotIn(List<Integer> values) {
            addCriterion("courseId not in", values, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdBetween(Integer value1, Integer value2) {
            addCriterion("courseId between", value1, value2, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotBetween(Integer value1, Integer value2) {
            addCriterion("courseId not between", value1, value2, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseNameIsNull() {
            addCriterion("courseName is null");
            return (Criteria) this;
        }

        public Criteria andCourseNameIsNotNull() {
            addCriterion("courseName is not null");
            return (Criteria) this;
        }

        public Criteria andCourseNameEqualTo(String value) {
            addCriterion("courseName =", value, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameNotEqualTo(String value) {
            addCriterion("courseName <>", value, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameGreaterThan(String value) {
            addCriterion("courseName >", value, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameGreaterThanOrEqualTo(String value) {
            addCriterion("courseName >=", value, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameLessThan(String value) {
            addCriterion("courseName <", value, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameLessThanOrEqualTo(String value) {
            addCriterion("courseName <=", value, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameLike(String value) {
            addCriterion("courseName like", value, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameNotLike(String value) {
            addCriterion("courseName not like", value, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameIn(List<String> values) {
            addCriterion("courseName in", values, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameNotIn(List<String> values) {
            addCriterion("courseName not in", values, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameBetween(String value1, String value2) {
            addCriterion("courseName between", value1, value2, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameNotBetween(String value1, String value2) {
            addCriterion("courseName not between", value1, value2, "courseName");
            return (Criteria) this;
        }

        public Criteria andCstartIsNull() {
            addCriterion("cstart is null");
            return (Criteria) this;
        }

        public Criteria andCstartIsNotNull() {
            addCriterion("cstart is not null");
            return (Criteria) this;
        }

        public Criteria andCstartEqualTo(Date value) {
            addCriterion("cstart =", value, "cstart");
            return (Criteria) this;
        }

        public Criteria andCstartNotEqualTo(Date value) {
            addCriterion("cstart <>", value, "cstart");
            return (Criteria) this;
        }

        public Criteria andCstartGreaterThan(Date value) {
            addCriterion("cstart >", value, "cstart");
            return (Criteria) this;
        }

        public Criteria andCstartGreaterThanOrEqualTo(Date value) {
            addCriterion("cstart >=", value, "cstart");
            return (Criteria) this;
        }

        public Criteria andCstartLessThan(Date value) {
            addCriterion("cstart <", value, "cstart");
            return (Criteria) this;
        }

        public Criteria andCstartLessThanOrEqualTo(Date value) {
            addCriterion("cstart <=", value, "cstart");
            return (Criteria) this;
        }

        public Criteria andCstartIn(List<Date> values) {
            addCriterion("cstart in", values, "cstart");
            return (Criteria) this;
        }

        public Criteria andCstartNotIn(List<Date> values) {
            addCriterion("cstart not in", values, "cstart");
            return (Criteria) this;
        }

        public Criteria andCstartBetween(Date value1, Date value2) {
            addCriterion("cstart between", value1, value2, "cstart");
            return (Criteria) this;
        }

        public Criteria andCstartNotBetween(Date value1, Date value2) {
            addCriterion("cstart not between", value1, value2, "cstart");
            return (Criteria) this;
        }

        public Criteria andCendIsNull() {
            addCriterion("cend is null");
            return (Criteria) this;
        }

        public Criteria andCendIsNotNull() {
            addCriterion("cend is not null");
            return (Criteria) this;
        }

        public Criteria andCendEqualTo(Date value) {
            addCriterion("cend =", value, "cend");
            return (Criteria) this;
        }

        public Criteria andCendNotEqualTo(Date value) {
            addCriterion("cend <>", value, "cend");
            return (Criteria) this;
        }

        public Criteria andCendGreaterThan(Date value) {
            addCriterion("cend >", value, "cend");
            return (Criteria) this;
        }

        public Criteria andCendGreaterThanOrEqualTo(Date value) {
            addCriterion("cend >=", value, "cend");
            return (Criteria) this;
        }

        public Criteria andCendLessThan(Date value) {
            addCriterion("cend <", value, "cend");
            return (Criteria) this;
        }

        public Criteria andCendLessThanOrEqualTo(Date value) {
            addCriterion("cend <=", value, "cend");
            return (Criteria) this;
        }

        public Criteria andCendIn(List<Date> values) {
            addCriterion("cend in", values, "cend");
            return (Criteria) this;
        }

        public Criteria andCendNotIn(List<Date> values) {
            addCriterion("cend not in", values, "cend");
            return (Criteria) this;
        }

        public Criteria andCendBetween(Date value1, Date value2) {
            addCriterion("cend between", value1, value2, "cend");
            return (Criteria) this;
        }

        public Criteria andCendNotBetween(Date value1, Date value2) {
            addCriterion("cend not between", value1, value2, "cend");
            return (Criteria) this;
        }

        public Criteria andClznumIsNull() {
            addCriterion("clznum is null");
            return (Criteria) this;
        }

        public Criteria andClznumIsNotNull() {
            addCriterion("clznum is not null");
            return (Criteria) this;
        }

        public Criteria andClznumEqualTo(Byte value) {
            addCriterion("clznum =", value, "clznum");
            return (Criteria) this;
        }

        public Criteria andClznumNotEqualTo(Byte value) {
            addCriterion("clznum <>", value, "clznum");
            return (Criteria) this;
        }

        public Criteria andClznumGreaterThan(Byte value) {
            addCriterion("clznum >", value, "clznum");
            return (Criteria) this;
        }

        public Criteria andClznumGreaterThanOrEqualTo(Byte value) {
            addCriterion("clznum >=", value, "clznum");
            return (Criteria) this;
        }

        public Criteria andClznumLessThan(Byte value) {
            addCriterion("clznum <", value, "clznum");
            return (Criteria) this;
        }

        public Criteria andClznumLessThanOrEqualTo(Byte value) {
            addCriterion("clznum <=", value, "clznum");
            return (Criteria) this;
        }

        public Criteria andClznumIn(List<Byte> values) {
            addCriterion("clznum in", values, "clznum");
            return (Criteria) this;
        }

        public Criteria andClznumNotIn(List<Byte> values) {
            addCriterion("clznum not in", values, "clznum");
            return (Criteria) this;
        }

        public Criteria andClznumBetween(Byte value1, Byte value2) {
            addCriterion("clznum between", value1, value2, "clznum");
            return (Criteria) this;
        }

        public Criteria andClznumNotBetween(Byte value1, Byte value2) {
            addCriterion("clznum not between", value1, value2, "clznum");
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

        public Criteria andLgeIsNull() {
            addCriterion("lge is null");
            return (Criteria) this;
        }

        public Criteria andLgeIsNotNull() {
            addCriterion("lge is not null");
            return (Criteria) this;
        }

        public Criteria andLgeEqualTo(Double value) {
            addCriterion("lge =", value, "lge");
            return (Criteria) this;
        }

        public Criteria andLgeNotEqualTo(Double value) {
            addCriterion("lge <>", value, "lge");
            return (Criteria) this;
        }

        public Criteria andLgeGreaterThan(Double value) {
            addCriterion("lge >", value, "lge");
            return (Criteria) this;
        }

        public Criteria andLgeGreaterThanOrEqualTo(Double value) {
            addCriterion("lge >=", value, "lge");
            return (Criteria) this;
        }

        public Criteria andLgeLessThan(Double value) {
            addCriterion("lge <", value, "lge");
            return (Criteria) this;
        }

        public Criteria andLgeLessThanOrEqualTo(Double value) {
            addCriterion("lge <=", value, "lge");
            return (Criteria) this;
        }

        public Criteria andLgeIn(List<Double> values) {
            addCriterion("lge in", values, "lge");
            return (Criteria) this;
        }

        public Criteria andLgeNotIn(List<Double> values) {
            addCriterion("lge not in", values, "lge");
            return (Criteria) this;
        }

        public Criteria andLgeBetween(Double value1, Double value2) {
            addCriterion("lge between", value1, value2, "lge");
            return (Criteria) this;
        }

        public Criteria andLgeNotBetween(Double value1, Double value2) {
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

        public Criteria andLaeEqualTo(Double value) {
            addCriterion("lae =", value, "lae");
            return (Criteria) this;
        }

        public Criteria andLaeNotEqualTo(Double value) {
            addCriterion("lae <>", value, "lae");
            return (Criteria) this;
        }

        public Criteria andLaeGreaterThan(Double value) {
            addCriterion("lae >", value, "lae");
            return (Criteria) this;
        }

        public Criteria andLaeGreaterThanOrEqualTo(Double value) {
            addCriterion("lae >=", value, "lae");
            return (Criteria) this;
        }

        public Criteria andLaeLessThan(Double value) {
            addCriterion("lae <", value, "lae");
            return (Criteria) this;
        }

        public Criteria andLaeLessThanOrEqualTo(Double value) {
            addCriterion("lae <=", value, "lae");
            return (Criteria) this;
        }

        public Criteria andLaeIn(List<Double> values) {
            addCriterion("lae in", values, "lae");
            return (Criteria) this;
        }

        public Criteria andLaeNotIn(List<Double> values) {
            addCriterion("lae not in", values, "lae");
            return (Criteria) this;
        }

        public Criteria andLaeBetween(Double value1, Double value2) {
            addCriterion("lae between", value1, value2, "lae");
            return (Criteria) this;
        }

        public Criteria andLaeNotBetween(Double value1, Double value2) {
            addCriterion("lae not between", value1, value2, "lae");
            return (Criteria) this;
        }

        public Criteria andPlaceInfoIsNull() {
            addCriterion("placeInfo is null");
            return (Criteria) this;
        }

        public Criteria andPlaceInfoIsNotNull() {
            addCriterion("placeInfo is not null");
            return (Criteria) this;
        }

        public Criteria andPlaceInfoEqualTo(String value) {
            addCriterion("placeInfo =", value, "placeInfo");
            return (Criteria) this;
        }

        public Criteria andPlaceInfoNotEqualTo(String value) {
            addCriterion("placeInfo <>", value, "placeInfo");
            return (Criteria) this;
        }

        public Criteria andPlaceInfoGreaterThan(String value) {
            addCriterion("placeInfo >", value, "placeInfo");
            return (Criteria) this;
        }

        public Criteria andPlaceInfoGreaterThanOrEqualTo(String value) {
            addCriterion("placeInfo >=", value, "placeInfo");
            return (Criteria) this;
        }

        public Criteria andPlaceInfoLessThan(String value) {
            addCriterion("placeInfo <", value, "placeInfo");
            return (Criteria) this;
        }

        public Criteria andPlaceInfoLessThanOrEqualTo(String value) {
            addCriterion("placeInfo <=", value, "placeInfo");
            return (Criteria) this;
        }

        public Criteria andPlaceInfoLike(String value) {
            addCriterion("placeInfo like", value, "placeInfo");
            return (Criteria) this;
        }

        public Criteria andPlaceInfoNotLike(String value) {
            addCriterion("placeInfo not like", value, "placeInfo");
            return (Criteria) this;
        }

        public Criteria andPlaceInfoIn(List<String> values) {
            addCriterion("placeInfo in", values, "placeInfo");
            return (Criteria) this;
        }

        public Criteria andPlaceInfoNotIn(List<String> values) {
            addCriterion("placeInfo not in", values, "placeInfo");
            return (Criteria) this;
        }

        public Criteria andPlaceInfoBetween(String value1, String value2) {
            addCriterion("placeInfo between", value1, value2, "placeInfo");
            return (Criteria) this;
        }

        public Criteria andPlaceInfoNotBetween(String value1, String value2) {
            addCriterion("placeInfo not between", value1, value2, "placeInfo");
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

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Byte value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Byte value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Byte value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Byte value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Byte value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Byte value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Byte> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Byte> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Byte value1, Byte value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Byte value1, Byte value2) {
            addCriterion("state not between", value1, value2, "state");
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
        
        /** 手动添加vo查询条件 **/
        public Criteria andLastMinutesGreaterThanOrEqualTo(Integer value) {
            addCriterion("TIMESTAMPDIFF(MINUTE, ctime, NOW()) >=", value, "lastMinutes");
            return (Criteria) this;
        }
        public Criteria andAcceptNumEqualTo(Integer value) {
        	addCriterion("(	SELECT count(scp.coachId)	FROM t_student_class_pool scp	WHERE	scp.orderId = sc.orderId AND scp.state = 1) =", value, "acceptNum");
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