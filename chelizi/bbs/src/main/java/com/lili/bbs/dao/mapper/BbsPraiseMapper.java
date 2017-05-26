package com.lili.bbs.dao.mapper;

import java.util.List;

import com.lili.bbs.dto.BbsPraise;

public interface BbsPraiseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BbsPraise record);

    int insertSelective(BbsPraise record);

    BbsPraise selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BbsPraise record);

    int updateByPrimaryKey(BbsPraise record);

	List<BbsPraise> getPraiseList(BbsPraise praise);

	Integer countIsPraiseByUser(BbsPraise bbsPraise);

	BbsPraise queryPraiseInfo(BbsPraise bbsPraise);

	void updatePraiseUserInfo(BbsPraise praise);
}