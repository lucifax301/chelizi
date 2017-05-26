package com.lili.bbs.dao.mapper;

import java.util.List;

import com.lili.bbs.dto.BbsTopic;

public interface BbsTopicMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BbsTopic record);

    int insertSelective(BbsTopic record);

    BbsTopic selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BbsTopic record);

    int updateByPrimaryKey(BbsTopic record);

	List<BbsTopic> queryBBSTopicList(BbsTopic bbsTopic);

	BbsTopic queryBBSTopic(BbsTopic bbsTopic);

	Integer countBbsTopicSize(BbsTopic bbsTopic);

	int updateTopicBbsNum(BbsTopic topic);
}