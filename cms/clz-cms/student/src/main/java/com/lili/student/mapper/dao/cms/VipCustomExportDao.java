package com.lili.student.mapper.dao.cms;


import java.util.List;

import com.lili.student.model.VipCustomExport;

public interface VipCustomExportDao {
	Long deleteByPrimaryKey(Integer id);

    Long insert(VipCustomExport record);

    Long insertSelective(VipCustomExport record);

    VipCustomExport selectByPrimaryKey(Integer id);
    
    List<VipCustomExport> queryList(VipCustomExport record);

    Long updateByPrimaryKeySelective(VipCustomExport record);

    Long updateByPrimaryKey(VipCustomExport record);

	VipCustomExport queryVipCustomExport(VipCustomExport vipCustomExport);

}