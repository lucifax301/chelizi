package com.lili.coach.mapper.dao;

import java.util.List;

import com.lili.coach.dto.BrandCar;

public interface BrandCarMapper {
    int deleteByPrimaryKey(Integer seqid);

    int insert(BrandCar brandCar);

    int insertSelective(BrandCar brandCar);

    BrandCar selectByPrimaryKey(Integer seqid);

    int updateByPrimaryKeySelective(BrandCar brandCar);

    int updateByPrimaryKey(BrandCar brandCar);
    
    List<BrandCar> getAll(BrandCar brandCar);
    
    int countAll();
}