package com.lili.bbs.dao.mapper;

import java.util.List;

import com.lili.bbs.dto.BbsWord;

public interface BbsWordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BbsWord record);

    int insertSelective(BbsWord record);

    BbsWord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BbsWord record);

    int updateByPrimaryKey(BbsWord record);

	List<BbsWord> getBBSWordList(BbsWord bbsWord);

	BbsWord queryBbsWord(BbsWord bbsWord);

	Integer countBbsWordSize(BbsWord bbsWord);

}