package com.lili.student.manager;

import java.util.List;

import com.lili.cms.entity.PagedResult;
import com.lili.student.model.VipCustomExport;
import com.lili.student.model.VipCustomExportDetail;

public interface ICmsVipManager {


    public PagedResult<VipCustomExport> queryVipCustomExportList(VipCustomExport vipCustomExport) throws Exception;
    
    public Long insertVipCustomExport(VipCustomExport vipCustomExport) throws Exception;
    
    public PagedResult<VipCustomExportDetail> queryVipCustomExportDetailList(VipCustomExportDetail vipCustomExportDetail) throws Exception;
    
    public Long insertVipCustomExportDetail(List<VipCustomExportDetail> vipCustomExportDetail) throws Exception;

	public VipCustomExport queryVipCustomExport(VipCustomExport vipCustomExport);

	public void updateVipCustomExport(VipCustomExport vipCustomExport);

	public void submit(VipCustomExport vipCustomExport);

	public void giveUp(VipCustomExport vipCustomExport);

	public List<VipCustomExportDetail> queryVipCustomDetailList(VipCustomExport vipCustomExport);
    
}
