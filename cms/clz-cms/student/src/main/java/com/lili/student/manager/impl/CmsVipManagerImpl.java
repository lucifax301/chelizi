package com.lili.student.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.PageUtil;
import com.lili.student.manager.ICmsVipManager;
import com.lili.student.mapper.dao.cms.VipCustomExportDao;
import com.lili.student.mapper.dao.cms.VipCustomExportDetailDao;
import com.lili.student.model.VipCustomExport;
import com.lili.student.model.VipCustomExportDetail;

public class CmsVipManagerImpl implements ICmsVipManager {

	@Autowired
	VipCustomExportDao vipCustomExportDao;
	
	@Autowired
	VipCustomExportDetailDao vipCustomExportDetailDao;
	
	@Override
	public PagedResult<VipCustomExport> queryVipCustomExportList(VipCustomExport vipCustomExport) throws Exception {
		PageUtil.startPage(vipCustomExport.getPageNo(), vipCustomExport.getPageSize());
		return BeanUtil.toPagedResult(vipCustomExportDao.queryList(vipCustomExport));
	}

	@Override
	public Long insertVipCustomExport(VipCustomExport vipCustomExport) throws Exception {
		return vipCustomExportDao.insertSelective(vipCustomExport);
	}

	@Override
	public PagedResult<VipCustomExportDetail> queryVipCustomExportDetailList(
			VipCustomExportDetail vipCustomExportDetail) throws Exception {
		PageUtil.startPage(vipCustomExportDetail.getPageNo(), vipCustomExportDetail.getPageSize());
		return BeanUtil.toPagedResult(vipCustomExportDetailDao.queryList(vipCustomExportDetail));
	}

	@Override
	public Long insertVipCustomExportDetail(List<VipCustomExportDetail> vipCustomExportDetail) throws Exception {
		return vipCustomExportDetailDao.insertList(vipCustomExportDetail);
	}

	@Override
	public VipCustomExport queryVipCustomExport(VipCustomExport vipCustomExport) {
		return vipCustomExportDao.queryVipCustomExport(vipCustomExport);
	}

	@Override
	public void updateVipCustomExport(VipCustomExport vipCustomExport) {
		try {
			vipCustomExportDao.updateByPrimaryKeySelective(vipCustomExport);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void submit(VipCustomExport vipCustomExport) {
		try {
			vipCustomExport.setStatus(2);
			vipCustomExportDao.updateByPrimaryKeySelective(vipCustomExport);
			VipCustomExportDetail record = new VipCustomExportDetail();
			record.setExportId(vipCustomExport.getId());
			record.setStatus(1);
			vipCustomExportDetailDao.updateByExportId(record);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void giveUp(VipCustomExport vipCustomExport) {
		try {
			vipCustomExport.setStatus(3);
			vipCustomExportDao.updateByPrimaryKeySelective(vipCustomExport);
			VipCustomExportDetail record = new VipCustomExportDetail();
			record.setExportId(vipCustomExport.getId());
			record.setStatus(3);
			vipCustomExportDetailDao.updateByExportId(record);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<VipCustomExportDetail> queryVipCustomDetailList(VipCustomExport vipCustomExport) {
		List<VipCustomExportDetail> vipCustomExportDetailList = null;
		try {
			VipCustomExportDetail vipCustomExportDetail = new VipCustomExportDetail();
			vipCustomExportDetail.setExportId(vipCustomExport.getId());
			vipCustomExportDetailList = vipCustomExportDetailDao.queryList(vipCustomExportDetail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vipCustomExportDetailList;
	}

}
