package com.lili.school.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WechatEnrollPackageExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public WechatEnrollPackageExample() {
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

        public Criteria andMarketPriceIsNull() {
            addCriterion("marketPrice is null");
            return (Criteria) this;
        }

        public Criteria andMarketPriceIsNotNull() {
            addCriterion("marketPrice is not null");
            return (Criteria) this;
        }

        public Criteria andMarketPriceEqualTo(Integer value) {
            addCriterion("marketPrice =", value, "marketPrice");
            return (Criteria) this;
        }

        public Criteria andMarketPriceNotEqualTo(Integer value) {
            addCriterion("marketPrice <>", value, "marketPrice");
            return (Criteria) this;
        }

        public Criteria andMarketPriceGreaterThan(Integer value) {
            addCriterion("marketPrice >", value, "marketPrice");
            return (Criteria) this;
        }

        public Criteria andMarketPriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("marketPrice >=", value, "marketPrice");
            return (Criteria) this;
        }

        public Criteria andMarketPriceLessThan(Integer value) {
            addCriterion("marketPrice <", value, "marketPrice");
            return (Criteria) this;
        }

        public Criteria andMarketPriceLessThanOrEqualTo(Integer value) {
            addCriterion("marketPrice <=", value, "marketPrice");
            return (Criteria) this;
        }

        public Criteria andMarketPriceIn(List<Integer> values) {
            addCriterion("marketPrice in", values, "marketPrice");
            return (Criteria) this;
        }

        public Criteria andMarketPriceNotIn(List<Integer> values) {
            addCriterion("marketPrice not in", values, "marketPrice");
            return (Criteria) this;
        }

        public Criteria andMarketPriceBetween(Integer value1, Integer value2) {
            addCriterion("marketPrice between", value1, value2, "marketPrice");
            return (Criteria) this;
        }

        public Criteria andMarketPriceNotBetween(Integer value1, Integer value2) {
            addCriterion("marketPrice not between", value1, value2, "marketPrice");
            return (Criteria) this;
        }

        public Criteria andHoursPriceIsNull() {
            addCriterion("hoursPrice is null");
            return (Criteria) this;
        }

        public Criteria andHoursPriceIsNotNull() {
            addCriterion("hoursPrice is not null");
            return (Criteria) this;
        }

        public Criteria andHoursPriceEqualTo(Integer value) {
            addCriterion("hoursPrice =", value, "hoursPrice");
            return (Criteria) this;
        }

        public Criteria andHoursPriceNotEqualTo(Integer value) {
            addCriterion("hoursPrice <>", value, "hoursPrice");
            return (Criteria) this;
        }

        public Criteria andHoursPriceGreaterThan(Integer value) {
            addCriterion("hoursPrice >", value, "hoursPrice");
            return (Criteria) this;
        }

        public Criteria andHoursPriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("hoursPrice >=", value, "hoursPrice");
            return (Criteria) this;
        }

        public Criteria andHoursPriceLessThan(Integer value) {
            addCriterion("hoursPrice <", value, "hoursPrice");
            return (Criteria) this;
        }

        public Criteria andHoursPriceLessThanOrEqualTo(Integer value) {
            addCriterion("hoursPrice <=", value, "hoursPrice");
            return (Criteria) this;
        }

        public Criteria andHoursPriceIn(List<Integer> values) {
            addCriterion("hoursPrice in", values, "hoursPrice");
            return (Criteria) this;
        }

        public Criteria andHoursPriceNotIn(List<Integer> values) {
            addCriterion("hoursPrice not in", values, "hoursPrice");
            return (Criteria) this;
        }

        public Criteria andHoursPriceBetween(Integer value1, Integer value2) {
            addCriterion("hoursPrice between", value1, value2, "hoursPrice");
            return (Criteria) this;
        }

        public Criteria andHoursPriceNotBetween(Integer value1, Integer value2) {
            addCriterion("hoursPrice not between", value1, value2, "hoursPrice");
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

        public Criteria andProcPicIsNull() {
            addCriterion("procPic is null");
            return (Criteria) this;
        }

        public Criteria andProcPicIsNotNull() {
            addCriterion("procPic is not null");
            return (Criteria) this;
        }

        public Criteria andProcPicEqualTo(String value) {
            addCriterion("procPic =", value, "procPic");
            return (Criteria) this;
        }

        public Criteria andProcPicNotEqualTo(String value) {
            addCriterion("procPic <>", value, "procPic");
            return (Criteria) this;
        }

        public Criteria andProcPicGreaterThan(String value) {
            addCriterion("procPic >", value, "procPic");
            return (Criteria) this;
        }

        public Criteria andProcPicGreaterThanOrEqualTo(String value) {
            addCriterion("procPic >=", value, "procPic");
            return (Criteria) this;
        }

        public Criteria andProcPicLessThan(String value) {
            addCriterion("procPic <", value, "procPic");
            return (Criteria) this;
        }

        public Criteria andProcPicLessThanOrEqualTo(String value) {
            addCriterion("procPic <=", value, "procPic");
            return (Criteria) this;
        }

        public Criteria andProcPicLike(String value) {
            addCriterion("procPic like", value, "procPic");
            return (Criteria) this;
        }

        public Criteria andProcPicNotLike(String value) {
            addCriterion("procPic not like", value, "procPic");
            return (Criteria) this;
        }

        public Criteria andProcPicIn(List<String> values) {
            addCriterion("procPic in", values, "procPic");
            return (Criteria) this;
        }

        public Criteria andProcPicNotIn(List<String> values) {
            addCriterion("procPic not in", values, "procPic");
            return (Criteria) this;
        }

        public Criteria andProcPicBetween(String value1, String value2) {
            addCriterion("procPic between", value1, value2, "procPic");
            return (Criteria) this;
        }

        public Criteria andProcPicNotBetween(String value1, String value2) {
            addCriterion("procPic not between", value1, value2, "procPic");
            return (Criteria) this;
        }

        public Criteria andFirstStepIsNull() {
            addCriterion("firstStep is null");
            return (Criteria) this;
        }

        public Criteria andFirstStepIsNotNull() {
            addCriterion("firstStep is not null");
            return (Criteria) this;
        }

        public Criteria andFirstStepEqualTo(Integer value) {
            addCriterion("firstStep =", value, "firstStep");
            return (Criteria) this;
        }

        public Criteria andFirstStepNotEqualTo(Integer value) {
            addCriterion("firstStep <>", value, "firstStep");
            return (Criteria) this;
        }

        public Criteria andFirstStepGreaterThan(Integer value) {
            addCriterion("firstStep >", value, "firstStep");
            return (Criteria) this;
        }

        public Criteria andFirstStepGreaterThanOrEqualTo(Integer value) {
            addCriterion("firstStep >=", value, "firstStep");
            return (Criteria) this;
        }

        public Criteria andFirstStepLessThan(Integer value) {
            addCriterion("firstStep <", value, "firstStep");
            return (Criteria) this;
        }

        public Criteria andFirstStepLessThanOrEqualTo(Integer value) {
            addCriterion("firstStep <=", value, "firstStep");
            return (Criteria) this;
        }

        public Criteria andFirstStepIn(List<Integer> values) {
            addCriterion("firstStep in", values, "firstStep");
            return (Criteria) this;
        }

        public Criteria andFirstStepNotIn(List<Integer> values) {
            addCriterion("firstStep not in", values, "firstStep");
            return (Criteria) this;
        }

        public Criteria andFirstStepBetween(Integer value1, Integer value2) {
            addCriterion("firstStep between", value1, value2, "firstStep");
            return (Criteria) this;
        }

        public Criteria andFirstStepNotBetween(Integer value1, Integer value2) {
            addCriterion("firstStep not between", value1, value2, "firstStep");
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

        public Criteria andFeatureIsNull() {
            addCriterion("feature is null");
            return (Criteria) this;
        }

        public Criteria andFeatureIsNotNull() {
            addCriterion("feature is not null");
            return (Criteria) this;
        }

        public Criteria andFeatureEqualTo(String value) {
            addCriterion("feature =", value, "feature");
            return (Criteria) this;
        }

        public Criteria andFeatureNotEqualTo(String value) {
            addCriterion("feature <>", value, "feature");
            return (Criteria) this;
        }

        public Criteria andFeatureGreaterThan(String value) {
            addCriterion("feature >", value, "feature");
            return (Criteria) this;
        }

        public Criteria andFeatureGreaterThanOrEqualTo(String value) {
            addCriterion("feature >=", value, "feature");
            return (Criteria) this;
        }

        public Criteria andFeatureLessThan(String value) {
            addCriterion("feature <", value, "feature");
            return (Criteria) this;
        }

        public Criteria andFeatureLessThanOrEqualTo(String value) {
            addCriterion("feature <=", value, "feature");
            return (Criteria) this;
        }

        public Criteria andFeatureLike(String value) {
            addCriterion("feature like", value, "feature");
            return (Criteria) this;
        }

        public Criteria andFeatureNotLike(String value) {
            addCriterion("feature not like", value, "feature");
            return (Criteria) this;
        }

        public Criteria andFeatureIn(List<String> values) {
            addCriterion("feature in", values, "feature");
            return (Criteria) this;
        }

        public Criteria andFeatureNotIn(List<String> values) {
            addCriterion("feature not in", values, "feature");
            return (Criteria) this;
        }

        public Criteria andFeatureBetween(String value1, String value2) {
            addCriterion("feature between", value1, value2, "feature");
            return (Criteria) this;
        }

        public Criteria andFeatureNotBetween(String value1, String value2) {
            addCriterion("feature not between", value1, value2, "feature");
            return (Criteria) this;
        }

        public Criteria andTransferStyleIsNull() {
            addCriterion("transferStyle is null");
            return (Criteria) this;
        }

        public Criteria andTransferStyleIsNotNull() {
            addCriterion("transferStyle is not null");
            return (Criteria) this;
        }

        public Criteria andTransferStyleEqualTo(String value) {
            addCriterion("transferStyle =", value, "transferStyle");
            return (Criteria) this;
        }

        public Criteria andTransferStyleNotEqualTo(String value) {
            addCriterion("transferStyle <>", value, "transferStyle");
            return (Criteria) this;
        }

        public Criteria andTransferStyleGreaterThan(String value) {
            addCriterion("transferStyle >", value, "transferStyle");
            return (Criteria) this;
        }

        public Criteria andTransferStyleGreaterThanOrEqualTo(String value) {
            addCriterion("transferStyle >=", value, "transferStyle");
            return (Criteria) this;
        }

        public Criteria andTransferStyleLessThan(String value) {
            addCriterion("transferStyle <", value, "transferStyle");
            return (Criteria) this;
        }

        public Criteria andTransferStyleLessThanOrEqualTo(String value) {
            addCriterion("transferStyle <=", value, "transferStyle");
            return (Criteria) this;
        }

        public Criteria andTransferStyleLike(String value) {
            addCriterion("transferStyle like", value, "transferStyle");
            return (Criteria) this;
        }

        public Criteria andTransferStyleNotLike(String value) {
            addCriterion("transferStyle not like", value, "transferStyle");
            return (Criteria) this;
        }

        public Criteria andTransferStyleIn(List<String> values) {
            addCriterion("transferStyle in", values, "transferStyle");
            return (Criteria) this;
        }

        public Criteria andTransferStyleNotIn(List<String> values) {
            addCriterion("transferStyle not in", values, "transferStyle");
            return (Criteria) this;
        }

        public Criteria andTransferStyleBetween(String value1, String value2) {
            addCriterion("transferStyle between", value1, value2, "transferStyle");
            return (Criteria) this;
        }

        public Criteria andTransferStyleNotBetween(String value1, String value2) {
            addCriterion("transferStyle not between", value1, value2, "transferStyle");
            return (Criteria) this;
        }

        public Criteria andTrainStyleIsNull() {
            addCriterion("trainStyle is null");
            return (Criteria) this;
        }

        public Criteria andTrainStyleIsNotNull() {
            addCriterion("trainStyle is not null");
            return (Criteria) this;
        }

        public Criteria andTrainStyleEqualTo(String value) {
            addCriterion("trainStyle =", value, "trainStyle");
            return (Criteria) this;
        }

        public Criteria andTrainStyleNotEqualTo(String value) {
            addCriterion("trainStyle <>", value, "trainStyle");
            return (Criteria) this;
        }

        public Criteria andTrainStyleGreaterThan(String value) {
            addCriterion("trainStyle >", value, "trainStyle");
            return (Criteria) this;
        }

        public Criteria andTrainStyleGreaterThanOrEqualTo(String value) {
            addCriterion("trainStyle >=", value, "trainStyle");
            return (Criteria) this;
        }

        public Criteria andTrainStyleLessThan(String value) {
            addCriterion("trainStyle <", value, "trainStyle");
            return (Criteria) this;
        }

        public Criteria andTrainStyleLessThanOrEqualTo(String value) {
            addCriterion("trainStyle <=", value, "trainStyle");
            return (Criteria) this;
        }

        public Criteria andTrainStyleLike(String value) {
            addCriterion("trainStyle like", value, "trainStyle");
            return (Criteria) this;
        }

        public Criteria andTrainStyleNotLike(String value) {
            addCriterion("trainStyle not like", value, "trainStyle");
            return (Criteria) this;
        }

        public Criteria andTrainStyleIn(List<String> values) {
            addCriterion("trainStyle in", values, "trainStyle");
            return (Criteria) this;
        }

        public Criteria andTrainStyleNotIn(List<String> values) {
            addCriterion("trainStyle not in", values, "trainStyle");
            return (Criteria) this;
        }

        public Criteria andTrainStyleBetween(String value1, String value2) {
            addCriterion("trainStyle between", value1, value2, "trainStyle");
            return (Criteria) this;
        }

        public Criteria andTrainStyleNotBetween(String value1, String value2) {
            addCriterion("trainStyle not between", value1, value2, "trainStyle");
            return (Criteria) this;
        }

        public Criteria andCarStyleIsNull() {
            addCriterion("carStyle is null");
            return (Criteria) this;
        }

        public Criteria andCarStyleIsNotNull() {
            addCriterion("carStyle is not null");
            return (Criteria) this;
        }

        public Criteria andCarStyleEqualTo(String value) {
            addCriterion("carStyle =", value, "carStyle");
            return (Criteria) this;
        }

        public Criteria andCarStyleNotEqualTo(String value) {
            addCriterion("carStyle <>", value, "carStyle");
            return (Criteria) this;
        }

        public Criteria andCarStyleGreaterThan(String value) {
            addCriterion("carStyle >", value, "carStyle");
            return (Criteria) this;
        }

        public Criteria andCarStyleGreaterThanOrEqualTo(String value) {
            addCriterion("carStyle >=", value, "carStyle");
            return (Criteria) this;
        }

        public Criteria andCarStyleLessThan(String value) {
            addCriterion("carStyle <", value, "carStyle");
            return (Criteria) this;
        }

        public Criteria andCarStyleLessThanOrEqualTo(String value) {
            addCriterion("carStyle <=", value, "carStyle");
            return (Criteria) this;
        }

        public Criteria andCarStyleLike(String value) {
            addCriterion("carStyle like", value, "carStyle");
            return (Criteria) this;
        }

        public Criteria andCarStyleNotLike(String value) {
            addCriterion("carStyle not like", value, "carStyle");
            return (Criteria) this;
        }

        public Criteria andCarStyleIn(List<String> values) {
            addCriterion("carStyle in", values, "carStyle");
            return (Criteria) this;
        }

        public Criteria andCarStyleNotIn(List<String> values) {
            addCriterion("carStyle not in", values, "carStyle");
            return (Criteria) this;
        }

        public Criteria andCarStyleBetween(String value1, String value2) {
            addCriterion("carStyle between", value1, value2, "carStyle");
            return (Criteria) this;
        }

        public Criteria andCarStyleNotBetween(String value1, String value2) {
            addCriterion("carStyle not between", value1, value2, "carStyle");
            return (Criteria) this;
        }

        public Criteria andHoursIsNull() {
            addCriterion("hours is null");
            return (Criteria) this;
        }

        public Criteria andHoursIsNotNull() {
            addCriterion("hours is not null");
            return (Criteria) this;
        }

        public Criteria andHoursEqualTo(Integer value) {
            addCriterion("hours =", value, "hours");
            return (Criteria) this;
        }

        public Criteria andHoursNotEqualTo(Integer value) {
            addCriterion("hours <>", value, "hours");
            return (Criteria) this;
        }

        public Criteria andHoursGreaterThan(Integer value) {
            addCriterion("hours >", value, "hours");
            return (Criteria) this;
        }

        public Criteria andHoursGreaterThanOrEqualTo(Integer value) {
            addCriterion("hours >=", value, "hours");
            return (Criteria) this;
        }

        public Criteria andHoursLessThan(Integer value) {
            addCriterion("hours <", value, "hours");
            return (Criteria) this;
        }

        public Criteria andHoursLessThanOrEqualTo(Integer value) {
            addCriterion("hours <=", value, "hours");
            return (Criteria) this;
        }

        public Criteria andHoursIn(List<Integer> values) {
            addCriterion("hours in", values, "hours");
            return (Criteria) this;
        }

        public Criteria andHoursNotIn(List<Integer> values) {
            addCriterion("hours not in", values, "hours");
            return (Criteria) this;
        }

        public Criteria andHoursBetween(Integer value1, Integer value2) {
            addCriterion("hours between", value1, value2, "hours");
            return (Criteria) this;
        }

        public Criteria andHoursNotBetween(Integer value1, Integer value2) {
            addCriterion("hours not between", value1, value2, "hours");
            return (Criteria) this;
        }

        public Criteria andMailAddressIsNull() {
            addCriterion("mailAddress is null");
            return (Criteria) this;
        }

        public Criteria andMailAddressIsNotNull() {
            addCriterion("mailAddress is not null");
            return (Criteria) this;
        }

        public Criteria andMailAddressEqualTo(String value) {
            addCriterion("mailAddress =", value, "mailAddress");
            return (Criteria) this;
        }

        public Criteria andMailAddressNotEqualTo(String value) {
            addCriterion("mailAddress <>", value, "mailAddress");
            return (Criteria) this;
        }

        public Criteria andMailAddressGreaterThan(String value) {
            addCriterion("mailAddress >", value, "mailAddress");
            return (Criteria) this;
        }

        public Criteria andMailAddressGreaterThanOrEqualTo(String value) {
            addCriterion("mailAddress >=", value, "mailAddress");
            return (Criteria) this;
        }

        public Criteria andMailAddressLessThan(String value) {
            addCriterion("mailAddress <", value, "mailAddress");
            return (Criteria) this;
        }

        public Criteria andMailAddressLessThanOrEqualTo(String value) {
            addCriterion("mailAddress <=", value, "mailAddress");
            return (Criteria) this;
        }

        public Criteria andMailAddressLike(String value) {
            addCriterion("mailAddress like", value, "mailAddress");
            return (Criteria) this;
        }

        public Criteria andMailAddressNotLike(String value) {
            addCriterion("mailAddress not like", value, "mailAddress");
            return (Criteria) this;
        }

        public Criteria andMailAddressIn(List<String> values) {
            addCriterion("mailAddress in", values, "mailAddress");
            return (Criteria) this;
        }

        public Criteria andMailAddressNotIn(List<String> values) {
            addCriterion("mailAddress not in", values, "mailAddress");
            return (Criteria) this;
        }

        public Criteria andMailAddressBetween(String value1, String value2) {
            addCriterion("mailAddress between", value1, value2, "mailAddress");
            return (Criteria) this;
        }

        public Criteria andMailAddressNotBetween(String value1, String value2) {
            addCriterion("mailAddress not between", value1, value2, "mailAddress");
            return (Criteria) this;
        }

        public Criteria andCstateIsNull() {
            addCriterion("cstate is null");
            return (Criteria) this;
        }

        public Criteria andCstateIsNotNull() {
            addCriterion("cstate is not null");
            return (Criteria) this;
        }

        public Criteria andCstateEqualTo(Integer value) {
            addCriterion("cstate =", value, "cstate");
            return (Criteria) this;
        }

        public Criteria andCstateNotEqualTo(Integer value) {
            addCriterion("cstate <>", value, "cstate");
            return (Criteria) this;
        }

        public Criteria andCstateGreaterThan(Integer value) {
            addCriterion("cstate >", value, "cstate");
            return (Criteria) this;
        }

        public Criteria andCstateGreaterThanOrEqualTo(Integer value) {
            addCriterion("cstate >=", value, "cstate");
            return (Criteria) this;
        }

        public Criteria andCstateLessThan(Integer value) {
            addCriterion("cstate <", value, "cstate");
            return (Criteria) this;
        }

        public Criteria andCstateLessThanOrEqualTo(Integer value) {
            addCriterion("cstate <=", value, "cstate");
            return (Criteria) this;
        }

        public Criteria andCstateIn(List<Integer> values) {
            addCriterion("cstate in", values, "cstate");
            return (Criteria) this;
        }

        public Criteria andCstateNotIn(List<Integer> values) {
            addCriterion("cstate not in", values, "cstate");
            return (Criteria) this;
        }

        public Criteria andCstateBetween(Integer value1, Integer value2) {
            addCriterion("cstate between", value1, value2, "cstate");
            return (Criteria) this;
        }

        public Criteria andCstateNotBetween(Integer value1, Integer value2) {
            addCriterion("cstate not between", value1, value2, "cstate");
            return (Criteria) this;
        }

        public Criteria andOstateIsNull() {
            addCriterion("ostate is null");
            return (Criteria) this;
        }

        public Criteria andOstateIsNotNull() {
            addCriterion("ostate is not null");
            return (Criteria) this;
        }

        public Criteria andOstateEqualTo(Integer value) {
            addCriterion("ostate =", value, "ostate");
            return (Criteria) this;
        }

        public Criteria andOstateNotEqualTo(Integer value) {
            addCriterion("ostate <>", value, "ostate");
            return (Criteria) this;
        }

        public Criteria andOstateGreaterThan(Integer value) {
            addCriterion("ostate >", value, "ostate");
            return (Criteria) this;
        }

        public Criteria andOstateGreaterThanOrEqualTo(Integer value) {
            addCriterion("ostate >=", value, "ostate");
            return (Criteria) this;
        }

        public Criteria andOstateLessThan(Integer value) {
            addCriterion("ostate <", value, "ostate");
            return (Criteria) this;
        }

        public Criteria andOstateLessThanOrEqualTo(Integer value) {
            addCriterion("ostate <=", value, "ostate");
            return (Criteria) this;
        }

        public Criteria andOstateIn(List<Integer> values) {
            addCriterion("ostate in", values, "ostate");
            return (Criteria) this;
        }

        public Criteria andOstateNotIn(List<Integer> values) {
            addCriterion("ostate not in", values, "ostate");
            return (Criteria) this;
        }

        public Criteria andOstateBetween(Integer value1, Integer value2) {
            addCriterion("ostate between", value1, value2, "ostate");
            return (Criteria) this;
        }

        public Criteria andOstateNotBetween(Integer value1, Integer value2) {
            addCriterion("ostate not between", value1, value2, "ostate");
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

        public Criteria andGraduateNumIsNull() {
            addCriterion("graduateNum is null");
            return (Criteria) this;
        }

        public Criteria andGraduateNumIsNotNull() {
            addCriterion("graduateNum is not null");
            return (Criteria) this;
        }

        public Criteria andGraduateNumEqualTo(Integer value) {
            addCriterion("graduateNum =", value, "graduateNum");
            return (Criteria) this;
        }

        public Criteria andGraduateNumNotEqualTo(Integer value) {
            addCriterion("graduateNum <>", value, "graduateNum");
            return (Criteria) this;
        }

        public Criteria andGraduateNumGreaterThan(Integer value) {
            addCriterion("graduateNum >", value, "graduateNum");
            return (Criteria) this;
        }

        public Criteria andGraduateNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("graduateNum >=", value, "graduateNum");
            return (Criteria) this;
        }

        public Criteria andGraduateNumLessThan(Integer value) {
            addCriterion("graduateNum <", value, "graduateNum");
            return (Criteria) this;
        }

        public Criteria andGraduateNumLessThanOrEqualTo(Integer value) {
            addCriterion("graduateNum <=", value, "graduateNum");
            return (Criteria) this;
        }

        public Criteria andGraduateNumIn(List<Integer> values) {
            addCriterion("graduateNum in", values, "graduateNum");
            return (Criteria) this;
        }

        public Criteria andGraduateNumNotIn(List<Integer> values) {
            addCriterion("graduateNum not in", values, "graduateNum");
            return (Criteria) this;
        }

        public Criteria andGraduateNumBetween(Integer value1, Integer value2) {
            addCriterion("graduateNum between", value1, value2, "graduateNum");
            return (Criteria) this;
        }

        public Criteria andGraduateNumNotBetween(Integer value1, Integer value2) {
            addCriterion("graduateNum not between", value1, value2, "graduateNum");
            return (Criteria) this;
        }

        public Criteria andPassRateIsNull() {
            addCriterion("passRate is null");
            return (Criteria) this;
        }

        public Criteria andPassRateIsNotNull() {
            addCriterion("passRate is not null");
            return (Criteria) this;
        }

        public Criteria andPassRateEqualTo(Float value) {
            addCriterion("passRate =", value, "passRate");
            return (Criteria) this;
        }

        public Criteria andPassRateNotEqualTo(Float value) {
            addCriterion("passRate <>", value, "passRate");
            return (Criteria) this;
        }

        public Criteria andPassRateGreaterThan(Float value) {
            addCriterion("passRate >", value, "passRate");
            return (Criteria) this;
        }

        public Criteria andPassRateGreaterThanOrEqualTo(Float value) {
            addCriterion("passRate >=", value, "passRate");
            return (Criteria) this;
        }

        public Criteria andPassRateLessThan(Float value) {
            addCriterion("passRate <", value, "passRate");
            return (Criteria) this;
        }

        public Criteria andPassRateLessThanOrEqualTo(Float value) {
            addCriterion("passRate <=", value, "passRate");
            return (Criteria) this;
        }

        public Criteria andPassRateIn(List<Float> values) {
            addCriterion("passRate in", values, "passRate");
            return (Criteria) this;
        }

        public Criteria andPassRateNotIn(List<Float> values) {
            addCriterion("passRate not in", values, "passRate");
            return (Criteria) this;
        }

        public Criteria andPassRateBetween(Float value1, Float value2) {
            addCriterion("passRate between", value1, value2, "passRate");
            return (Criteria) this;
        }

        public Criteria andPassRateNotBetween(Float value1, Float value2) {
            addCriterion("passRate not between", value1, value2, "passRate");
            return (Criteria) this;
        }

        public Criteria andRefuseIsNull() {
            addCriterion("refuse is null");
            return (Criteria) this;
        }

        public Criteria andRefuseIsNotNull() {
            addCriterion("refuse is not null");
            return (Criteria) this;
        }

        public Criteria andRefuseEqualTo(String value) {
            addCriterion("refuse =", value, "refuse");
            return (Criteria) this;
        }

        public Criteria andRefuseNotEqualTo(String value) {
            addCriterion("refuse <>", value, "refuse");
            return (Criteria) this;
        }

        public Criteria andRefuseGreaterThan(String value) {
            addCriterion("refuse >", value, "refuse");
            return (Criteria) this;
        }

        public Criteria andRefuseGreaterThanOrEqualTo(String value) {
            addCriterion("refuse >=", value, "refuse");
            return (Criteria) this;
        }

        public Criteria andRefuseLessThan(String value) {
            addCriterion("refuse <", value, "refuse");
            return (Criteria) this;
        }

        public Criteria andRefuseLessThanOrEqualTo(String value) {
            addCriterion("refuse <=", value, "refuse");
            return (Criteria) this;
        }

        public Criteria andRefuseLike(String value) {
            addCriterion("refuse like", value, "refuse");
            return (Criteria) this;
        }

        public Criteria andRefuseNotLike(String value) {
            addCriterion("refuse not like", value, "refuse");
            return (Criteria) this;
        }

        public Criteria andRefuseIn(List<String> values) {
            addCriterion("refuse in", values, "refuse");
            return (Criteria) this;
        }

        public Criteria andRefuseNotIn(List<String> values) {
            addCriterion("refuse not in", values, "refuse");
            return (Criteria) this;
        }

        public Criteria andRefuseBetween(String value1, String value2) {
            addCriterion("refuse between", value1, value2, "refuse");
            return (Criteria) this;
        }

        public Criteria andRefuseNotBetween(String value1, String value2) {
            addCriterion("refuse not between", value1, value2, "refuse");
            return (Criteria) this;
        }

        public Criteria andCTypeIsNull() {
            addCriterion("cType is null");
            return (Criteria) this;
        }

        public Criteria andCTypeIsNotNull() {
            addCriterion("cType is not null");
            return (Criteria) this;
        }

        public Criteria andCTypeEqualTo(Integer value) {
            addCriterion("cType =", value, "cType");
            return (Criteria) this;
        }

        public Criteria andCTypeNotEqualTo(Integer value) {
            addCriterion("cType <>", value, "cType");
            return (Criteria) this;
        }

        public Criteria andCTypeGreaterThan(Integer value) {
            addCriterion("cType >", value, "cType");
            return (Criteria) this;
        }

        public Criteria andCTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("cType >=", value, "cType");
            return (Criteria) this;
        }

        public Criteria andCTypeLessThan(Integer value) {
            addCriterion("cType <", value, "cType");
            return (Criteria) this;
        }

        public Criteria andCTypeLessThanOrEqualTo(Integer value) {
            addCriterion("cType <=", value, "cType");
            return (Criteria) this;
        }

        public Criteria andCTypeIn(List<Integer> values) {
            addCriterion("cType in", values, "cType");
            return (Criteria) this;
        }

        public Criteria andCTypeNotIn(List<Integer> values) {
            addCriterion("cType not in", values, "cType");
            return (Criteria) this;
        }

        public Criteria andCTypeBetween(Integer value1, Integer value2) {
            addCriterion("cType between", value1, value2, "cType");
            return (Criteria) this;
        }

        public Criteria andCTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("cType not between", value1, value2, "cType");
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