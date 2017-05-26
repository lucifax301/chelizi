package com.lili.student.mapper.dao.cms;


import java.util.List;

import com.lili.student.model.VipCustomExportDetail;

public interface VipCustomExportDetailDao {
    Long deleteByPrimaryKey(Integer id);

    Long insert(VipCustomExportDetail record);

    Long insertSelective(VipCustomExportDetail record);

    VipCustomExportDetail selectByPrimaryKey(Integer id);

    Long updateByPrimaryKeySelective(VipCustomExportDetail record);
    
    Long updateByExportId(VipCustomExportDetail record);

    Long updateByPrimaryKey(VipCustomExportDetail record);
    
    Long insertList(List<VipCustomExportDetail> record);
    
    List<VipCustomExportDetail> queryList(VipCustomExportDetail record);
}