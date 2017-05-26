package com.lili.school.mapper.dao;

import java.util.List;

import com.lili.school.model.SchoolExtend;

public interface SchoolExtendMapper {
	SchoolExtend queryBySchoolId(SchoolExtend record);
	
	SchoolExtend queryExtendInfo(SchoolExtend SchoolExtend);
	
	SchoolExtend selectByPrimaryKey(Integer id);
	
    int deleteByPrimaryKey(Integer id);

    int insert(SchoolExtend record);

    int insertSelective(SchoolExtend record);

    void updatepPotocolOpen(SchoolExtend SchoolExtend);
    
    void updatePotocolOpenInit(SchoolExtend SchoolExtend);
    
    void updateIsChange(SchoolExtend dto);
    
    int updateByPrimaryKeySelective(SchoolExtend record);

    int updateByPrimaryKey(SchoolExtend record);

	void batchUpdateSchoolExtendList(List<SchoolExtend> updateSchoolExtendList);

	List<SchoolExtend> queryBurse(SchoolExtend dto);

	void updateIsDel(SchoolExtend schoolExtend);

	void batchUpdateNotUseList(List<SchoolExtend> updateSchoolExtendList);

	void batchUpdateNotUsePassList(List<SchoolExtend> updateSchoolExtendList);

	List<SchoolExtend> querySchoolBurseList(String id);

}