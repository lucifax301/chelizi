package com.lili.school.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EnrollPackageTemplateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EnrollPackageTemplateExample() {
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

        public Criteria andSelfTestIsNull() {
            addCriterion("self_test is null");
            return (Criteria) this;
        }

        public Criteria andSelfTestIsNotNull() {
            addCriterion("self_test is not null");
            return (Criteria) this;
        }

        public Criteria andSelfTestEqualTo(Byte value) {
            addCriterion("self_test =", value, "selfTest");
            return (Criteria) this;
        }

        public Criteria andSelfTestNotEqualTo(Byte value) {
            addCriterion("self_test <>", value, "selfTest");
            return (Criteria) this;
        }

        public Criteria andSelfTestGreaterThan(Byte value) {
            addCriterion("self_test >", value, "selfTest");
            return (Criteria) this;
        }

        public Criteria andSelfTestGreaterThanOrEqualTo(Byte value) {
            addCriterion("self_test >=", value, "selfTest");
            return (Criteria) this;
        }

        public Criteria andSelfTestLessThan(Byte value) {
            addCriterion("self_test <", value, "selfTest");
            return (Criteria) this;
        }

        public Criteria andSelfTestLessThanOrEqualTo(Byte value) {
            addCriterion("self_test <=", value, "selfTest");
            return (Criteria) this;
        }

        public Criteria andSelfTestIn(List<Byte> values) {
            addCriterion("self_test in", values, "selfTest");
            return (Criteria) this;
        }

        public Criteria andSelfTestNotIn(List<Byte> values) {
            addCriterion("self_test not in", values, "selfTest");
            return (Criteria) this;
        }

        public Criteria andSelfTestBetween(Byte value1, Byte value2) {
            addCriterion("self_test between", value1, value2, "selfTest");
            return (Criteria) this;
        }

        public Criteria andSelfTestNotBetween(Byte value1, Byte value2) {
            addCriterion("self_test not between", value1, value2, "selfTest");
            return (Criteria) this;
        }

        public Criteria andProcPicIsNull() {
            addCriterion("proc_pic is null");
            return (Criteria) this;
        }

        public Criteria andProcPicIsNotNull() {
            addCriterion("proc_pic is not null");
            return (Criteria) this;
        }

        public Criteria andProcPicEqualTo(String value) {
            addCriterion("proc_pic =", value, "procPic");
            return (Criteria) this;
        }

        public Criteria andProcPicNotEqualTo(String value) {
            addCriterion("proc_pic <>", value, "procPic");
            return (Criteria) this;
        }

        public Criteria andProcPicGreaterThan(String value) {
            addCriterion("proc_pic >", value, "procPic");
            return (Criteria) this;
        }

        public Criteria andProcPicGreaterThanOrEqualTo(String value) {
            addCriterion("proc_pic >=", value, "procPic");
            return (Criteria) this;
        }

        public Criteria andProcPicLessThan(String value) {
            addCriterion("proc_pic <", value, "procPic");
            return (Criteria) this;
        }

        public Criteria andProcPicLessThanOrEqualTo(String value) {
            addCriterion("proc_pic <=", value, "procPic");
            return (Criteria) this;
        }

        public Criteria andProcPicLike(String value) {
            addCriterion("proc_pic like", value, "procPic");
            return (Criteria) this;
        }

        public Criteria andProcPicNotLike(String value) {
            addCriterion("proc_pic not like", value, "procPic");
            return (Criteria) this;
        }

        public Criteria andProcPicIn(List<String> values) {
            addCriterion("proc_pic in", values, "procPic");
            return (Criteria) this;
        }

        public Criteria andProcPicNotIn(List<String> values) {
            addCriterion("proc_pic not in", values, "procPic");
            return (Criteria) this;
        }

        public Criteria andProcPicBetween(String value1, String value2) {
            addCriterion("proc_pic between", value1, value2, "procPic");
            return (Criteria) this;
        }

        public Criteria andProcPicNotBetween(String value1, String value2) {
            addCriterion("proc_pic not between", value1, value2, "procPic");
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

        public Criteria andFirstStepIsNull() {
            addCriterion("first_step is null");
            return (Criteria) this;
        }

        public Criteria andFirstStepIsNotNull() {
            addCriterion("first_step is not null");
            return (Criteria) this;
        }

        public Criteria andFirstStepEqualTo(Integer value) {
            addCriterion("first_step =", value, "firstStep");
            return (Criteria) this;
        }

        public Criteria andFirstStepNotEqualTo(Integer value) {
            addCriterion("first_step <>", value, "firstStep");
            return (Criteria) this;
        }

        public Criteria andFirstStepGreaterThan(Integer value) {
            addCriterion("first_step >", value, "firstStep");
            return (Criteria) this;
        }

        public Criteria andFirstStepGreaterThanOrEqualTo(Integer value) {
            addCriterion("first_step >=", value, "firstStep");
            return (Criteria) this;
        }

        public Criteria andFirstStepLessThan(Integer value) {
            addCriterion("first_step <", value, "firstStep");
            return (Criteria) this;
        }

        public Criteria andFirstStepLessThanOrEqualTo(Integer value) {
            addCriterion("first_step <=", value, "firstStep");
            return (Criteria) this;
        }

        public Criteria andFirstStepIn(List<Integer> values) {
            addCriterion("first_step in", values, "firstStep");
            return (Criteria) this;
        }

        public Criteria andFirstStepNotIn(List<Integer> values) {
            addCriterion("first_step not in", values, "firstStep");
            return (Criteria) this;
        }

        public Criteria andFirstStepBetween(Integer value1, Integer value2) {
            addCriterion("first_step between", value1, value2, "firstStep");
            return (Criteria) this;
        }

        public Criteria andFirstStepNotBetween(Integer value1, Integer value2) {
            addCriterion("first_step not between", value1, value2, "firstStep");
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

        public Criteria andTmptotalpriceIsNull() {
            addCriterion("tmpTotalPrice is null");
            return (Criteria) this;
        }

        public Criteria andTmptotalpriceIsNotNull() {
            addCriterion("tmpTotalPrice is not null");
            return (Criteria) this;
        }

        public Criteria andTmptotalpriceEqualTo(String value) {
            addCriterion("tmpTotalPrice =", value, "tmptotalprice");
            return (Criteria) this;
        }

        public Criteria andTmptotalpriceNotEqualTo(String value) {
            addCriterion("tmpTotalPrice <>", value, "tmptotalprice");
            return (Criteria) this;
        }

        public Criteria andTmptotalpriceGreaterThan(String value) {
            addCriterion("tmpTotalPrice >", value, "tmptotalprice");
            return (Criteria) this;
        }

        public Criteria andTmptotalpriceGreaterThanOrEqualTo(String value) {
            addCriterion("tmpTotalPrice >=", value, "tmptotalprice");
            return (Criteria) this;
        }

        public Criteria andTmptotalpriceLessThan(String value) {
            addCriterion("tmpTotalPrice <", value, "tmptotalprice");
            return (Criteria) this;
        }

        public Criteria andTmptotalpriceLessThanOrEqualTo(String value) {
            addCriterion("tmpTotalPrice <=", value, "tmptotalprice");
            return (Criteria) this;
        }

        public Criteria andTmptotalpriceLike(String value) {
            addCriterion("tmpTotalPrice like", value, "tmptotalprice");
            return (Criteria) this;
        }

        public Criteria andTmptotalpriceNotLike(String value) {
            addCriterion("tmpTotalPrice not like", value, "tmptotalprice");
            return (Criteria) this;
        }

        public Criteria andTmptotalpriceIn(List<String> values) {
            addCriterion("tmpTotalPrice in", values, "tmptotalprice");
            return (Criteria) this;
        }

        public Criteria andTmptotalpriceNotIn(List<String> values) {
            addCriterion("tmpTotalPrice not in", values, "tmptotalprice");
            return (Criteria) this;
        }

        public Criteria andTmptotalpriceBetween(String value1, String value2) {
            addCriterion("tmpTotalPrice between", value1, value2, "tmptotalprice");
            return (Criteria) this;
        }

        public Criteria andTmptotalpriceNotBetween(String value1, String value2) {
            addCriterion("tmpTotalPrice not between", value1, value2, "tmptotalprice");
            return (Criteria) this;
        }

        public Criteria andTmpcourseIsNull() {
            addCriterion("tmpCourse is null");
            return (Criteria) this;
        }

        public Criteria andTmpcourseIsNotNull() {
            addCriterion("tmpCourse is not null");
            return (Criteria) this;
        }

        public Criteria andTmpcourseEqualTo(String value) {
            addCriterion("tmpCourse =", value, "tmpcourse");
            return (Criteria) this;
        }

        public Criteria andTmpcourseNotEqualTo(String value) {
            addCriterion("tmpCourse <>", value, "tmpcourse");
            return (Criteria) this;
        }

        public Criteria andTmpcourseGreaterThan(String value) {
            addCriterion("tmpCourse >", value, "tmpcourse");
            return (Criteria) this;
        }

        public Criteria andTmpcourseGreaterThanOrEqualTo(String value) {
            addCriterion("tmpCourse >=", value, "tmpcourse");
            return (Criteria) this;
        }

        public Criteria andTmpcourseLessThan(String value) {
            addCriterion("tmpCourse <", value, "tmpcourse");
            return (Criteria) this;
        }

        public Criteria andTmpcourseLessThanOrEqualTo(String value) {
            addCriterion("tmpCourse <=", value, "tmpcourse");
            return (Criteria) this;
        }

        public Criteria andTmpcourseLike(String value) {
            addCriterion("tmpCourse like", value, "tmpcourse");
            return (Criteria) this;
        }

        public Criteria andTmpcourseNotLike(String value) {
            addCriterion("tmpCourse not like", value, "tmpcourse");
            return (Criteria) this;
        }

        public Criteria andTmpcourseIn(List<String> values) {
            addCriterion("tmpCourse in", values, "tmpcourse");
            return (Criteria) this;
        }

        public Criteria andTmpcourseNotIn(List<String> values) {
            addCriterion("tmpCourse not in", values, "tmpcourse");
            return (Criteria) this;
        }

        public Criteria andTmpcourseBetween(String value1, String value2) {
            addCriterion("tmpCourse between", value1, value2, "tmpcourse");
            return (Criteria) this;
        }

        public Criteria andTmpcourseNotBetween(String value1, String value2) {
            addCriterion("tmpCourse not between", value1, value2, "tmpcourse");
            return (Criteria) this;
        }

        public Criteria andTmpcoursepriceIsNull() {
            addCriterion("tmpCoursePrice is null");
            return (Criteria) this;
        }

        public Criteria andTmpcoursepriceIsNotNull() {
            addCriterion("tmpCoursePrice is not null");
            return (Criteria) this;
        }

        public Criteria andTmpcoursepriceEqualTo(String value) {
            addCriterion("tmpCoursePrice =", value, "tmpcourseprice");
            return (Criteria) this;
        }

        public Criteria andTmpcoursepriceNotEqualTo(String value) {
            addCriterion("tmpCoursePrice <>", value, "tmpcourseprice");
            return (Criteria) this;
        }

        public Criteria andTmpcoursepriceGreaterThan(String value) {
            addCriterion("tmpCoursePrice >", value, "tmpcourseprice");
            return (Criteria) this;
        }

        public Criteria andTmpcoursepriceGreaterThanOrEqualTo(String value) {
            addCriterion("tmpCoursePrice >=", value, "tmpcourseprice");
            return (Criteria) this;
        }

        public Criteria andTmpcoursepriceLessThan(String value) {
            addCriterion("tmpCoursePrice <", value, "tmpcourseprice");
            return (Criteria) this;
        }

        public Criteria andTmpcoursepriceLessThanOrEqualTo(String value) {
            addCriterion("tmpCoursePrice <=", value, "tmpcourseprice");
            return (Criteria) this;
        }

        public Criteria andTmpcoursepriceLike(String value) {
            addCriterion("tmpCoursePrice like", value, "tmpcourseprice");
            return (Criteria) this;
        }

        public Criteria andTmpcoursepriceNotLike(String value) {
            addCriterion("tmpCoursePrice not like", value, "tmpcourseprice");
            return (Criteria) this;
        }

        public Criteria andTmpcoursepriceIn(List<String> values) {
            addCriterion("tmpCoursePrice in", values, "tmpcourseprice");
            return (Criteria) this;
        }

        public Criteria andTmpcoursepriceNotIn(List<String> values) {
            addCriterion("tmpCoursePrice not in", values, "tmpcourseprice");
            return (Criteria) this;
        }

        public Criteria andTmpcoursepriceBetween(String value1, String value2) {
            addCriterion("tmpCoursePrice between", value1, value2, "tmpcourseprice");
            return (Criteria) this;
        }

        public Criteria andTmpcoursepriceNotBetween(String value1, String value2) {
            addCriterion("tmpCoursePrice not between", value1, value2, "tmpcourseprice");
            return (Criteria) this;
        }

        public Criteria andTmpestimateIsNull() {
            addCriterion("tmpEstimate is null");
            return (Criteria) this;
        }

        public Criteria andTmpestimateIsNotNull() {
            addCriterion("tmpEstimate is not null");
            return (Criteria) this;
        }

        public Criteria andTmpestimateEqualTo(String value) {
            addCriterion("tmpEstimate =", value, "tmpestimate");
            return (Criteria) this;
        }

        public Criteria andTmpestimateNotEqualTo(String value) {
            addCriterion("tmpEstimate <>", value, "tmpestimate");
            return (Criteria) this;
        }

        public Criteria andTmpestimateGreaterThan(String value) {
            addCriterion("tmpEstimate >", value, "tmpestimate");
            return (Criteria) this;
        }

        public Criteria andTmpestimateGreaterThanOrEqualTo(String value) {
            addCriterion("tmpEstimate >=", value, "tmpestimate");
            return (Criteria) this;
        }

        public Criteria andTmpestimateLessThan(String value) {
            addCriterion("tmpEstimate <", value, "tmpestimate");
            return (Criteria) this;
        }

        public Criteria andTmpestimateLessThanOrEqualTo(String value) {
            addCriterion("tmpEstimate <=", value, "tmpestimate");
            return (Criteria) this;
        }

        public Criteria andTmpestimateLike(String value) {
            addCriterion("tmpEstimate like", value, "tmpestimate");
            return (Criteria) this;
        }

        public Criteria andTmpestimateNotLike(String value) {
            addCriterion("tmpEstimate not like", value, "tmpestimate");
            return (Criteria) this;
        }

        public Criteria andTmpestimateIn(List<String> values) {
            addCriterion("tmpEstimate in", values, "tmpestimate");
            return (Criteria) this;
        }

        public Criteria andTmpestimateNotIn(List<String> values) {
            addCriterion("tmpEstimate not in", values, "tmpestimate");
            return (Criteria) this;
        }

        public Criteria andTmpestimateBetween(String value1, String value2) {
            addCriterion("tmpEstimate between", value1, value2, "tmpestimate");
            return (Criteria) this;
        }

        public Criteria andTmpestimateNotBetween(String value1, String value2) {
            addCriterion("tmpEstimate not between", value1, value2, "tmpestimate");
            return (Criteria) this;
        }

        public Criteria andTmpnoteIsNull() {
            addCriterion("tmpNote is null");
            return (Criteria) this;
        }

        public Criteria andTmpnoteIsNotNull() {
            addCriterion("tmpNote is not null");
            return (Criteria) this;
        }

        public Criteria andTmpnoteEqualTo(String value) {
            addCriterion("tmpNote =", value, "tmpnote");
            return (Criteria) this;
        }

        public Criteria andTmpnoteNotEqualTo(String value) {
            addCriterion("tmpNote <>", value, "tmpnote");
            return (Criteria) this;
        }

        public Criteria andTmpnoteGreaterThan(String value) {
            addCriterion("tmpNote >", value, "tmpnote");
            return (Criteria) this;
        }

        public Criteria andTmpnoteGreaterThanOrEqualTo(String value) {
            addCriterion("tmpNote >=", value, "tmpnote");
            return (Criteria) this;
        }

        public Criteria andTmpnoteLessThan(String value) {
            addCriterion("tmpNote <", value, "tmpnote");
            return (Criteria) this;
        }

        public Criteria andTmpnoteLessThanOrEqualTo(String value) {
            addCriterion("tmpNote <=", value, "tmpnote");
            return (Criteria) this;
        }

        public Criteria andTmpnoteLike(String value) {
            addCriterion("tmpNote like", value, "tmpnote");
            return (Criteria) this;
        }

        public Criteria andTmpnoteNotLike(String value) {
            addCriterion("tmpNote not like", value, "tmpnote");
            return (Criteria) this;
        }

        public Criteria andTmpnoteIn(List<String> values) {
            addCriterion("tmpNote in", values, "tmpnote");
            return (Criteria) this;
        }

        public Criteria andTmpnoteNotIn(List<String> values) {
            addCriterion("tmpNote not in", values, "tmpnote");
            return (Criteria) this;
        }

        public Criteria andTmpnoteBetween(String value1, String value2) {
            addCriterion("tmpNote between", value1, value2, "tmpnote");
            return (Criteria) this;
        }

        public Criteria andTmpnoteNotBetween(String value1, String value2) {
            addCriterion("tmpNote not between", value1, value2, "tmpnote");
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