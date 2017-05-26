package com.lili.file.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PosterExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PosterExample() {
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

        public Criteria andPosteridIsNull() {
            addCriterion("posterId is null");
            return (Criteria) this;
        }

        public Criteria andPosteridIsNotNull() {
            addCriterion("posterId is not null");
            return (Criteria) this;
        }

        public Criteria andPosteridEqualTo(Integer value) {
            addCriterion("posterId =", value, "posterid");
            return (Criteria) this;
        }

        public Criteria andPosteridNotEqualTo(Integer value) {
            addCriterion("posterId <>", value, "posterid");
            return (Criteria) this;
        }

        public Criteria andPosteridGreaterThan(Integer value) {
            addCriterion("posterId >", value, "posterid");
            return (Criteria) this;
        }

        public Criteria andPosteridGreaterThanOrEqualTo(Integer value) {
            addCriterion("posterId >=", value, "posterid");
            return (Criteria) this;
        }

        public Criteria andPosteridLessThan(Integer value) {
            addCriterion("posterId <", value, "posterid");
            return (Criteria) this;
        }

        public Criteria andPosteridLessThanOrEqualTo(Integer value) {
            addCriterion("posterId <=", value, "posterid");
            return (Criteria) this;
        }

        public Criteria andPosteridIn(List<Integer> values) {
            addCriterion("posterId in", values, "posterid");
            return (Criteria) this;
        }

        public Criteria andPosteridNotIn(List<Integer> values) {
            addCriterion("posterId not in", values, "posterid");
            return (Criteria) this;
        }

        public Criteria andPosteridBetween(Integer value1, Integer value2) {
            addCriterion("posterId between", value1, value2, "posterid");
            return (Criteria) this;
        }

        public Criteria andPosteridNotBetween(Integer value1, Integer value2) {
            addCriterion("posterId not between", value1, value2, "posterid");
            return (Criteria) this;
        }

        public Criteria andPosternameIsNull() {
            addCriterion("posterName is null");
            return (Criteria) this;
        }

        public Criteria andPosternameIsNotNull() {
            addCriterion("posterName is not null");
            return (Criteria) this;
        }

        public Criteria andPosternameEqualTo(String value) {
            addCriterion("posterName =", value, "postername");
            return (Criteria) this;
        }

        public Criteria andPosternameNotEqualTo(String value) {
            addCriterion("posterName <>", value, "postername");
            return (Criteria) this;
        }

        public Criteria andPosternameGreaterThan(String value) {
            addCriterion("posterName >", value, "postername");
            return (Criteria) this;
        }

        public Criteria andPosternameGreaterThanOrEqualTo(String value) {
            addCriterion("posterName >=", value, "postername");
            return (Criteria) this;
        }

        public Criteria andPosternameLessThan(String value) {
            addCriterion("posterName <", value, "postername");
            return (Criteria) this;
        }

        public Criteria andPosternameLessThanOrEqualTo(String value) {
            addCriterion("posterName <=", value, "postername");
            return (Criteria) this;
        }

        public Criteria andPosternameLike(String value) {
            addCriterion("posterName like", value, "postername");
            return (Criteria) this;
        }

        public Criteria andPosternameNotLike(String value) {
            addCriterion("posterName not like", value, "postername");
            return (Criteria) this;
        }

        public Criteria andPosternameIn(List<String> values) {
            addCriterion("posterName in", values, "postername");
            return (Criteria) this;
        }

        public Criteria andPosternameNotIn(List<String> values) {
            addCriterion("posterName not in", values, "postername");
            return (Criteria) this;
        }

        public Criteria andPosternameBetween(String value1, String value2) {
            addCriterion("posterName between", value1, value2, "postername");
            return (Criteria) this;
        }

        public Criteria andPosternameNotBetween(String value1, String value2) {
            addCriterion("posterName not between", value1, value2, "postername");
            return (Criteria) this;
        }

        public Criteria andPosterpicIsNull() {
            addCriterion("posterPic is null");
            return (Criteria) this;
        }

        public Criteria andPosterpicIsNotNull() {
            addCriterion("posterPic is not null");
            return (Criteria) this;
        }

        public Criteria andPosterpicEqualTo(String value) {
            addCriterion("posterPic =", value, "posterpic");
            return (Criteria) this;
        }

        public Criteria andPosterpicNotEqualTo(String value) {
            addCriterion("posterPic <>", value, "posterpic");
            return (Criteria) this;
        }

        public Criteria andPosterpicGreaterThan(String value) {
            addCriterion("posterPic >", value, "posterpic");
            return (Criteria) this;
        }

        public Criteria andPosterpicGreaterThanOrEqualTo(String value) {
            addCriterion("posterPic >=", value, "posterpic");
            return (Criteria) this;
        }

        public Criteria andPosterpicLessThan(String value) {
            addCriterion("posterPic <", value, "posterpic");
            return (Criteria) this;
        }

        public Criteria andPosterpicLessThanOrEqualTo(String value) {
            addCriterion("posterPic <=", value, "posterpic");
            return (Criteria) this;
        }

        public Criteria andPosterpicLike(String value) {
            addCriterion("posterPic like", value, "posterpic");
            return (Criteria) this;
        }

        public Criteria andPosterpicNotLike(String value) {
            addCriterion("posterPic not like", value, "posterpic");
            return (Criteria) this;
        }

        public Criteria andPosterpicIn(List<String> values) {
            addCriterion("posterPic in", values, "posterpic");
            return (Criteria) this;
        }

        public Criteria andPosterpicNotIn(List<String> values) {
            addCriterion("posterPic not in", values, "posterpic");
            return (Criteria) this;
        }

        public Criteria andPosterpicBetween(String value1, String value2) {
            addCriterion("posterPic between", value1, value2, "posterpic");
            return (Criteria) this;
        }

        public Criteria andPosterpicNotBetween(String value1, String value2) {
            addCriterion("posterPic not between", value1, value2, "posterpic");
            return (Criteria) this;
        }

        public Criteria andPosterpic2IsNull() {
            addCriterion("posterPic2 is null");
            return (Criteria) this;
        }

        public Criteria andPosterpic2IsNotNull() {
            addCriterion("posterPic2 is not null");
            return (Criteria) this;
        }

        public Criteria andPosterpic2EqualTo(String value) {
            addCriterion("posterPic2 =", value, "posterpic2");
            return (Criteria) this;
        }

        public Criteria andPosterpic2NotEqualTo(String value) {
            addCriterion("posterPic2 <>", value, "posterpic2");
            return (Criteria) this;
        }

        public Criteria andPosterpic2GreaterThan(String value) {
            addCriterion("posterPic2 >", value, "posterpic2");
            return (Criteria) this;
        }

        public Criteria andPosterpic2GreaterThanOrEqualTo(String value) {
            addCriterion("posterPic2 >=", value, "posterpic2");
            return (Criteria) this;
        }

        public Criteria andPosterpic2LessThan(String value) {
            addCriterion("posterPic2 <", value, "posterpic2");
            return (Criteria) this;
        }

        public Criteria andPosterpic2LessThanOrEqualTo(String value) {
            addCriterion("posterPic2 <=", value, "posterpic2");
            return (Criteria) this;
        }

        public Criteria andPosterpic2Like(String value) {
            addCriterion("posterPic2 like", value, "posterpic2");
            return (Criteria) this;
        }

        public Criteria andPosterpic2NotLike(String value) {
            addCriterion("posterPic2 not like", value, "posterpic2");
            return (Criteria) this;
        }

        public Criteria andPosterpic2In(List<String> values) {
            addCriterion("posterPic2 in", values, "posterpic2");
            return (Criteria) this;
        }

        public Criteria andPosterpic2NotIn(List<String> values) {
            addCriterion("posterPic2 not in", values, "posterpic2");
            return (Criteria) this;
        }

        public Criteria andPosterpic2Between(String value1, String value2) {
            addCriterion("posterPic2 between", value1, value2, "posterpic2");
            return (Criteria) this;
        }

        public Criteria andPosterpic2NotBetween(String value1, String value2) {
            addCriterion("posterPic2 not between", value1, value2, "posterpic2");
            return (Criteria) this;
        }

        public Criteria andPosterpic3IsNull() {
            addCriterion("posterPic3 is null");
            return (Criteria) this;
        }

        public Criteria andPosterpic3IsNotNull() {
            addCriterion("posterPic3 is not null");
            return (Criteria) this;
        }

        public Criteria andPosterpic3EqualTo(String value) {
            addCriterion("posterPic3 =", value, "posterpic3");
            return (Criteria) this;
        }

        public Criteria andPosterpic3NotEqualTo(String value) {
            addCriterion("posterPic3 <>", value, "posterpic3");
            return (Criteria) this;
        }

        public Criteria andPosterpic3GreaterThan(String value) {
            addCriterion("posterPic3 >", value, "posterpic3");
            return (Criteria) this;
        }

        public Criteria andPosterpic3GreaterThanOrEqualTo(String value) {
            addCriterion("posterPic3 >=", value, "posterpic3");
            return (Criteria) this;
        }

        public Criteria andPosterpic3LessThan(String value) {
            addCriterion("posterPic3 <", value, "posterpic3");
            return (Criteria) this;
        }

        public Criteria andPosterpic3LessThanOrEqualTo(String value) {
            addCriterion("posterPic3 <=", value, "posterpic3");
            return (Criteria) this;
        }

        public Criteria andPosterpic3Like(String value) {
            addCriterion("posterPic3 like", value, "posterpic3");
            return (Criteria) this;
        }

        public Criteria andPosterpic3NotLike(String value) {
            addCriterion("posterPic3 not like", value, "posterpic3");
            return (Criteria) this;
        }

        public Criteria andPosterpic3In(List<String> values) {
            addCriterion("posterPic3 in", values, "posterpic3");
            return (Criteria) this;
        }

        public Criteria andPosterpic3NotIn(List<String> values) {
            addCriterion("posterPic3 not in", values, "posterpic3");
            return (Criteria) this;
        }

        public Criteria andPosterpic3Between(String value1, String value2) {
            addCriterion("posterPic3 between", value1, value2, "posterpic3");
            return (Criteria) this;
        }

        public Criteria andPosterpic3NotBetween(String value1, String value2) {
            addCriterion("posterPic3 not between", value1, value2, "posterpic3");
            return (Criteria) this;
        }

        public Criteria andPosterurlIsNull() {
            addCriterion("posterUrl is null");
            return (Criteria) this;
        }

        public Criteria andPosterurlIsNotNull() {
            addCriterion("posterUrl is not null");
            return (Criteria) this;
        }

        public Criteria andPosterurlEqualTo(String value) {
            addCriterion("posterUrl =", value, "posterurl");
            return (Criteria) this;
        }

        public Criteria andPosterurlNotEqualTo(String value) {
            addCriterion("posterUrl <>", value, "posterurl");
            return (Criteria) this;
        }

        public Criteria andPosterurlGreaterThan(String value) {
            addCriterion("posterUrl >", value, "posterurl");
            return (Criteria) this;
        }

        public Criteria andPosterurlGreaterThanOrEqualTo(String value) {
            addCriterion("posterUrl >=", value, "posterurl");
            return (Criteria) this;
        }

        public Criteria andPosterurlLessThan(String value) {
            addCriterion("posterUrl <", value, "posterurl");
            return (Criteria) this;
        }

        public Criteria andPosterurlLessThanOrEqualTo(String value) {
            addCriterion("posterUrl <=", value, "posterurl");
            return (Criteria) this;
        }

        public Criteria andPosterurlLike(String value) {
            addCriterion("posterUrl like", value, "posterurl");
            return (Criteria) this;
        }

        public Criteria andPosterurlNotLike(String value) {
            addCriterion("posterUrl not like", value, "posterurl");
            return (Criteria) this;
        }

        public Criteria andPosterurlIn(List<String> values) {
            addCriterion("posterUrl in", values, "posterurl");
            return (Criteria) this;
        }

        public Criteria andPosterurlNotIn(List<String> values) {
            addCriterion("posterUrl not in", values, "posterurl");
            return (Criteria) this;
        }

        public Criteria andPosterurlBetween(String value1, String value2) {
            addCriterion("posterUrl between", value1, value2, "posterurl");
            return (Criteria) this;
        }

        public Criteria andPosterurlNotBetween(String value1, String value2) {
            addCriterion("posterUrl not between", value1, value2, "posterurl");
            return (Criteria) this;
        }

        public Criteria andPosterurl2IsNull() {
            addCriterion("posterUrl2 is null");
            return (Criteria) this;
        }

        public Criteria andPosterurl2IsNotNull() {
            addCriterion("posterUrl2 is not null");
            return (Criteria) this;
        }

        public Criteria andPosterurl2EqualTo(String value) {
            addCriterion("posterUrl2 =", value, "posterurl2");
            return (Criteria) this;
        }

        public Criteria andPosterurl2NotEqualTo(String value) {
            addCriterion("posterUrl2 <>", value, "posterurl2");
            return (Criteria) this;
        }

        public Criteria andPosterurl2GreaterThan(String value) {
            addCriterion("posterUrl2 >", value, "posterurl2");
            return (Criteria) this;
        }

        public Criteria andPosterurl2GreaterThanOrEqualTo(String value) {
            addCriterion("posterUrl2 >=", value, "posterurl2");
            return (Criteria) this;
        }

        public Criteria andPosterurl2LessThan(String value) {
            addCriterion("posterUrl2 <", value, "posterurl2");
            return (Criteria) this;
        }

        public Criteria andPosterurl2LessThanOrEqualTo(String value) {
            addCriterion("posterUrl2 <=", value, "posterurl2");
            return (Criteria) this;
        }

        public Criteria andPosterurl2Like(String value) {
            addCriterion("posterUrl2 like", value, "posterurl2");
            return (Criteria) this;
        }

        public Criteria andPosterurl2NotLike(String value) {
            addCriterion("posterUrl2 not like", value, "posterurl2");
            return (Criteria) this;
        }

        public Criteria andPosterurl2In(List<String> values) {
            addCriterion("posterUrl2 in", values, "posterurl2");
            return (Criteria) this;
        }

        public Criteria andPosterurl2NotIn(List<String> values) {
            addCriterion("posterUrl2 not in", values, "posterurl2");
            return (Criteria) this;
        }

        public Criteria andPosterurl2Between(String value1, String value2) {
            addCriterion("posterUrl2 between", value1, value2, "posterurl2");
            return (Criteria) this;
        }

        public Criteria andPosterurl2NotBetween(String value1, String value2) {
            addCriterion("posterUrl2 not between", value1, value2, "posterurl2");
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

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Byte value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Byte value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Byte value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Byte value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Byte value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Byte> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Byte> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Byte value1, Byte value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("type not between", value1, value2, "type");
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