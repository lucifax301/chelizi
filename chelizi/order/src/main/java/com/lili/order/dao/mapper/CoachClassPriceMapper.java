package com.lili.order.dao.mapper;

import java.util.List;

import com.lili.order.dao.po.CoachClassPricePo;

public interface CoachClassPriceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CoachClassPricePo record);

    int insertSelective(CoachClassPricePo record);

    CoachClassPricePo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CoachClassPricePo record);

    int updateByPrimaryKey(CoachClassPricePo record);

	List<CoachClassPricePo> queryCoachPrice(Long coachId);

	List<CoachClassPricePo> queryCoachPriceList(CoachClassPricePo coachClassPricePo);
}