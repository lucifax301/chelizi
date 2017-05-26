package com.lili.school.mapper.dao;

import java.util.List;

import com.lili.coach.dto.BrandCar;
import com.lili.school.model.BrandCarBDTO;

public interface BrandCarMapper {
    int deleteByPrimaryKey(Integer seqid);

    int insert(BrandCar brandCar);

    int insertSelective(BrandCar brandCar);

    BrandCar selectByPrimaryKey(Integer seqid);

    int updateByPrimaryKeySelective(BrandCar brandCar);

    int updateByPrimaryKey(BrandCar brandCar);
    
    List<BrandCar> getAll(BrandCar brandCar);
    
    List<BrandCar> getAllBrandName();
    
    List<BrandCar> getCarByBrand(String brandname);
    
    int countAll();
    
    List<BrandCar> findBatch(BrandCarBDTO dto);
}